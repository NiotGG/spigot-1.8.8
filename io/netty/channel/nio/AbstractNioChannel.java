/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractNioChannel
/*     */   extends AbstractChannel
/*     */ {
/*  50 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
/*     */   
/*     */ 
/*     */   private final SelectableChannel ch;
/*     */   
/*     */ 
/*     */   protected final int readInterestOp;
/*     */   
/*     */ 
/*     */   volatile SelectionKey selectionKey;
/*     */   
/*     */ 
/*     */   private volatile boolean inputShutdown;
/*     */   
/*     */   private volatile boolean readPending;
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private ScheduledFuture<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */   
/*     */ 
/*     */   protected AbstractNioChannel(Channel paramChannel, SelectableChannel paramSelectableChannel, int paramInt)
/*     */   {
/*  75 */     super(paramChannel);
/*  76 */     this.ch = paramSelectableChannel;
/*  77 */     this.readInterestOp = paramInt;
/*     */     try {
/*  79 */       paramSelectableChannel.configureBlocking(false);
/*     */     } catch (IOException localIOException1) {
/*     */       try {
/*  82 */         paramSelectableChannel.close();
/*     */       } catch (IOException localIOException2) {
/*  84 */         if (logger.isWarnEnabled()) {
/*  85 */           logger.warn("Failed to close a partially initialized socket.", localIOException2);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  90 */       throw new ChannelException("Failed to enter non-blocking mode.", localIOException1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/*  96 */     return this.ch.isOpen();
/*     */   }
/*     */   
/*     */   public NioUnsafe unsafe()
/*     */   {
/* 101 */     return (NioUnsafe)super.unsafe();
/*     */   }
/*     */   
/*     */   protected SelectableChannel javaChannel() {
/* 105 */     return this.ch;
/*     */   }
/*     */   
/*     */   public NioEventLoop eventLoop()
/*     */   {
/* 110 */     return (NioEventLoop)super.eventLoop();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected SelectionKey selectionKey()
/*     */   {
/* 117 */     assert (this.selectionKey != null);
/* 118 */     return this.selectionKey;
/*     */   }
/*     */   
/*     */   protected boolean isReadPending() {
/* 122 */     return this.readPending;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean paramBoolean) {
/* 126 */     this.readPending = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean isInputShutdown()
/*     */   {
/* 133 */     return this.inputShutdown;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setInputShutdown()
/*     */   {
/* 140 */     this.inputShutdown = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract class AbstractNioUnsafe
/*     */     extends AbstractChannel.AbstractUnsafe
/*     */     implements AbstractNioChannel.NioUnsafe
/*     */   {
/* 165 */     protected AbstractNioUnsafe() { super(); }
/*     */     
/*     */     protected final void removeReadOp() {
/* 168 */       SelectionKey localSelectionKey = AbstractNioChannel.this.selectionKey();
/*     */       
/*     */ 
/*     */ 
/* 172 */       if (!localSelectionKey.isValid()) {
/* 173 */         return;
/*     */       }
/* 175 */       int i = localSelectionKey.interestOps();
/* 176 */       if ((i & AbstractNioChannel.this.readInterestOp) != 0)
/*     */       {
/* 178 */         localSelectionKey.interestOps(i & (AbstractNioChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*     */       }
/*     */     }
/*     */     
/*     */     public final SelectableChannel ch()
/*     */     {
/* 184 */       return AbstractNioChannel.this.javaChannel();
/*     */     }
/*     */     
/*     */ 
/*     */     public final void connect(final SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/* 190 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 191 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 195 */         if (AbstractNioChannel.this.connectPromise != null) {
/* 196 */           throw new IllegalStateException("connection attempt already made");
/*     */         }
/*     */         
/* 199 */         boolean bool = AbstractNioChannel.this.isActive();
/* 200 */         if (AbstractNioChannel.this.doConnect(paramSocketAddress1, paramSocketAddress2)) {
/* 201 */           fulfillConnectPromise(paramChannelPromise, bool);
/*     */         } else {
/* 203 */           AbstractNioChannel.this.connectPromise = paramChannelPromise;
/* 204 */           AbstractNioChannel.this.requestedRemoteAddress = paramSocketAddress1;
/*     */           
/*     */ 
/* 207 */           int i = AbstractNioChannel.this.config().getConnectTimeoutMillis();
/* 208 */           if (i > 0) {
/* 209 */             AbstractNioChannel.this.connectTimeoutFuture = AbstractNioChannel.this.eventLoop().schedule(new OneTimeTask()
/*     */             {
/*     */               public void run() {
/* 212 */                 ChannelPromise localChannelPromise = AbstractNioChannel.this.connectPromise;
/* 213 */                 ConnectTimeoutException localConnectTimeoutException = new ConnectTimeoutException("connection timed out: " + paramSocketAddress1);
/*     */                 
/* 215 */                 if ((localChannelPromise != null) && (localChannelPromise.tryFailure(localConnectTimeoutException)))
/* 216 */                   AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise()); } }, i, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 222 */           paramChannelPromise.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 225 */               if (paramAnonymousChannelFuture.isCancelled()) {
/* 226 */                 if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 227 */                   AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */                 }
/* 229 */                 AbstractNioChannel.this.connectPromise = null;
/* 230 */                 AbstractNioChannel.AbstractNioUnsafe.this.close(AbstractNioChannel.AbstractNioUnsafe.this.voidPromise());
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*     */       } catch (Throwable localThrowable) { Object localObject;
/* 236 */         if ((localThrowable instanceof ConnectException)) {
/* 237 */           ConnectException localConnectException = new ConnectException(localThrowable.getMessage() + ": " + paramSocketAddress1);
/* 238 */           localConnectException.setStackTrace(localThrowable.getStackTrace());
/* 239 */           localObject = localConnectException;
/*     */         }
/* 241 */         paramChannelPromise.tryFailure((Throwable)localObject);
/* 242 */         closeIfClosed();
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise paramChannelPromise, boolean paramBoolean) {
/* 247 */       if (paramChannelPromise == null)
/*     */       {
/* 249 */         return;
/*     */       }
/*     */       
/*     */ 
/* 253 */       boolean bool = paramChannelPromise.trySuccess();
/*     */       
/*     */ 
/*     */ 
/* 257 */       if ((!paramBoolean) && (AbstractNioChannel.this.isActive())) {
/* 258 */         AbstractNioChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */       
/*     */ 
/* 262 */       if (!bool) {
/* 263 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise paramChannelPromise, Throwable paramThrowable) {
/* 268 */       if (paramChannelPromise == null)
/*     */       {
/* 270 */         return;
/*     */       }
/*     */       
/*     */ 
/* 274 */       paramChannelPromise.tryFailure(paramThrowable);
/* 275 */       closeIfClosed();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public final void finishConnect()
/*     */     {
/* 283 */       assert (AbstractNioChannel.this.eventLoop().inEventLoop());
/*     */       try
/*     */       {
/* 286 */         boolean bool = AbstractNioChannel.this.isActive();
/* 287 */         AbstractNioChannel.this.doFinishConnect();
/* 288 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, bool);
/*     */       } catch (Throwable localThrowable) { Object localObject1;
/* 290 */         if ((localThrowable instanceof ConnectException)) {
/* 291 */           ConnectException localConnectException = new ConnectException(localThrowable.getMessage() + ": " + AbstractNioChannel.this.requestedRemoteAddress);
/* 292 */           localConnectException.setStackTrace(localThrowable.getStackTrace());
/* 293 */           localObject1 = localConnectException;
/*     */         }
/*     */         
/* 296 */         fulfillConnectPromise(AbstractNioChannel.this.connectPromise, (Throwable)localObject1);
/*     */       }
/*     */       finally
/*     */       {
/* 300 */         if (AbstractNioChannel.this.connectTimeoutFuture != null) {
/* 301 */           AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
/*     */         }
/* 303 */         AbstractNioChannel.this.connectPromise = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected final void flush0()
/*     */     {
/* 312 */       if (isFlushPending()) {
/* 313 */         return;
/*     */       }
/* 315 */       super.flush0();
/*     */     }
/*     */     
/*     */ 
/*     */     public final void forceFlush()
/*     */     {
/* 321 */       super.flush0();
/*     */     }
/*     */     
/*     */     private boolean isFlushPending() {
/* 325 */       SelectionKey localSelectionKey = AbstractNioChannel.this.selectionKey();
/* 326 */       return (localSelectionKey.isValid()) && ((localSelectionKey.interestOps() & 0x4) != 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/* 332 */     return paramEventLoop instanceof NioEventLoop;
/*     */   }
/*     */   
/*     */   protected void doRegister() throws Exception
/*     */   {
/* 337 */     int i = 0;
/*     */     for (;;) {
/*     */       try {
/* 340 */         this.selectionKey = javaChannel().register(eventLoop().selector, 0, this);
/* 341 */         return;
/*     */       } catch (CancelledKeyException localCancelledKeyException) {
/* 343 */         if (i == 0)
/*     */         {
/*     */ 
/* 346 */           eventLoop().selectNow();
/* 347 */           i = 1;
/*     */         }
/*     */         else
/*     */         {
/* 351 */           throw localCancelledKeyException;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDeregister() throws Exception
/*     */   {
/* 359 */     eventLoop().cancel(selectionKey());
/*     */   }
/*     */   
/*     */   protected void doBeginRead()
/*     */     throws Exception
/*     */   {
/* 365 */     if (this.inputShutdown) {
/* 366 */       return;
/*     */     }
/*     */     
/* 369 */     SelectionKey localSelectionKey = this.selectionKey;
/* 370 */     if (!localSelectionKey.isValid()) {
/* 371 */       return;
/*     */     }
/*     */     
/* 374 */     this.readPending = true;
/*     */     
/* 376 */     int i = localSelectionKey.interestOps();
/* 377 */     if ((i & this.readInterestOp) == 0) {
/* 378 */       localSelectionKey.interestOps(i | this.readInterestOp);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void doFinishConnect()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   protected final ByteBuf newDirectBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 398 */     int i = paramByteBuf.readableBytes();
/* 399 */     if (i == 0) {
/* 400 */       ReferenceCountUtil.safeRelease(paramByteBuf);
/* 401 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 404 */     ByteBufAllocator localByteBufAllocator = alloc();
/* 405 */     if (localByteBufAllocator.isDirectBufferPooled()) {
/* 406 */       localByteBuf = localByteBufAllocator.directBuffer(i);
/* 407 */       localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 408 */       ReferenceCountUtil.safeRelease(paramByteBuf);
/* 409 */       return localByteBuf;
/*     */     }
/*     */     
/* 412 */     ByteBuf localByteBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 413 */     if (localByteBuf != null) {
/* 414 */       localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 415 */       ReferenceCountUtil.safeRelease(paramByteBuf);
/* 416 */       return localByteBuf;
/*     */     }
/*     */     
/*     */ 
/* 420 */     return paramByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final ByteBuf newDirectBuffer(ReferenceCounted paramReferenceCounted, ByteBuf paramByteBuf)
/*     */   {
/* 430 */     int i = paramByteBuf.readableBytes();
/* 431 */     if (i == 0) {
/* 432 */       ReferenceCountUtil.safeRelease(paramReferenceCounted);
/* 433 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 436 */     ByteBufAllocator localByteBufAllocator = alloc();
/* 437 */     if (localByteBufAllocator.isDirectBufferPooled()) {
/* 438 */       localByteBuf = localByteBufAllocator.directBuffer(i);
/* 439 */       localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 440 */       ReferenceCountUtil.safeRelease(paramReferenceCounted);
/* 441 */       return localByteBuf;
/*     */     }
/*     */     
/* 444 */     ByteBuf localByteBuf = ByteBufUtil.threadLocalDirectBuffer();
/* 445 */     if (localByteBuf != null) {
/* 446 */       localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 447 */       ReferenceCountUtil.safeRelease(paramReferenceCounted);
/* 448 */       return localByteBuf;
/*     */     }
/*     */     
/*     */ 
/* 452 */     if (paramReferenceCounted != paramByteBuf)
/*     */     {
/* 454 */       paramByteBuf.retain();
/* 455 */       ReferenceCountUtil.safeRelease(paramReferenceCounted);
/*     */     }
/*     */     
/* 458 */     return paramByteBuf;
/*     */   }
/*     */   
/*     */   public static abstract interface NioUnsafe
/*     */     extends Channel.Unsafe
/*     */   {
/*     */     public abstract SelectableChannel ch();
/*     */     
/*     */     public abstract void finishConnect();
/*     */     
/*     */     public abstract void read();
/*     */     
/*     */     public abstract void forceFlush();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\AbstractNioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */