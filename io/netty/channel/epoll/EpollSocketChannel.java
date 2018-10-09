/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ConnectTimeoutException;
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EpollSocketChannel
/*     */   extends AbstractEpollChannel
/*     */   implements SocketChannel
/*     */ {
/*  52 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(DefaultFileRegion.class) + ')';
/*     */   
/*     */   private final EpollSocketChannelConfig config;
/*     */   
/*     */   private ChannelPromise connectPromise;
/*     */   
/*     */   private ScheduledFuture<?> connectTimeoutFuture;
/*     */   
/*     */   private SocketAddress requestedRemoteAddress;
/*     */   
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   private volatile InetSocketAddress remote;
/*     */   
/*     */   private volatile boolean inputShutdown;
/*     */   
/*     */   private volatile boolean outputShutdown;
/*     */   
/*     */   EpollSocketChannel(Channel paramChannel, int paramInt)
/*     */   {
/*  72 */     super(paramChannel, paramInt, 1, true);
/*  73 */     this.config = new EpollSocketChannelConfig(this);
/*     */     
/*     */ 
/*  76 */     this.remote = Native.remoteAddress(paramInt);
/*  77 */     this.local = Native.localAddress(paramInt);
/*     */   }
/*     */   
/*     */   public EpollSocketChannel() {
/*  81 */     super(Native.socketStreamFd(), 1);
/*  82 */     this.config = new EpollSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe()
/*     */   {
/*  87 */     return new EpollSocketUnsafe();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*  92 */     return this.local;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/*  97 */     return this.remote;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 102 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 103 */     Native.bind(this.fd, localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/* 104 */     this.local = Native.localAddress(this.fd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean writeBytes(ChannelOutboundBuffer paramChannelOutboundBuffer, ByteBuf paramByteBuf)
/*     */     throws Exception
/*     */   {
/* 112 */     int i = paramByteBuf.readableBytes();
/* 113 */     if (i == 0) {
/* 114 */       paramChannelOutboundBuffer.remove();
/* 115 */       return true;
/*     */     }
/*     */     
/* 118 */     boolean bool = false;
/* 119 */     long l1 = 0L;
/* 120 */     int j; int k; int m; if (paramByteBuf.hasMemoryAddress()) {
/* 121 */       long l2 = paramByteBuf.memoryAddress();
/* 122 */       j = paramByteBuf.readerIndex();
/* 123 */       k = paramByteBuf.writerIndex();
/*     */       for (;;) {
/* 125 */         m = Native.writeAddress(this.fd, l2, j, k);
/* 126 */         if (m > 0) {
/* 127 */           l1 += m;
/* 128 */           if (l1 == i) {
/* 129 */             bool = true;
/* 130 */             break;
/*     */           }
/* 132 */           j += m;
/*     */         }
/*     */         else {
/* 135 */           setEpollOut();
/* 136 */           break;
/*     */         }
/*     */       }
/*     */       
/* 140 */       paramChannelOutboundBuffer.removeBytes(l1);
/* 141 */       return bool; }
/* 142 */     if (paramByteBuf.nioBufferCount() == 1) {
/* 143 */       int n = paramByteBuf.readerIndex();
/* 144 */       ByteBuffer localByteBuffer = paramByteBuf.internalNioBuffer(n, paramByteBuf.readableBytes());
/*     */       for (;;) {
/* 146 */         j = localByteBuffer.position();
/* 147 */         k = localByteBuffer.limit();
/* 148 */         m = Native.write(this.fd, localByteBuffer, j, k);
/* 149 */         if (m > 0) {
/* 150 */           localByteBuffer.position(j + m);
/* 151 */           l1 += m;
/* 152 */           if (l1 == i) {
/* 153 */             bool = true;
/* 154 */             break;
/*     */           }
/*     */         }
/*     */         else {
/* 158 */           setEpollOut();
/* 159 */           break;
/*     */         }
/*     */       }
/*     */       
/* 163 */       paramChannelOutboundBuffer.removeBytes(l1);
/* 164 */       return bool;
/*     */     }
/* 166 */     ByteBuffer[] arrayOfByteBuffer = paramByteBuf.nioBuffers();
/* 167 */     return writeBytesMultiple(paramChannelOutboundBuffer, arrayOfByteBuffer, arrayOfByteBuffer.length, i);
/*     */   }
/*     */   
/*     */   private boolean writeBytesMultiple(ChannelOutboundBuffer paramChannelOutboundBuffer, IovArray paramIovArray)
/*     */     throws IOException
/*     */   {
/* 173 */     long l1 = paramIovArray.size();
/* 174 */     int i = paramIovArray.count();
/*     */     
/* 176 */     assert (l1 != 0L);
/* 177 */     assert (i != 0);
/*     */     
/* 179 */     boolean bool = false;
/* 180 */     long l2 = 0L;
/* 181 */     int j = 0;
/* 182 */     int k = j + i;
/*     */     for (;;) {
/* 184 */       long l3 = Native.writevAddresses(this.fd, paramIovArray.memoryAddress(j), i);
/* 185 */       if (l3 == 0L)
/*     */       {
/* 187 */         setEpollOut();
/* 188 */         break;
/*     */       }
/* 190 */       l1 -= l3;
/* 191 */       l2 += l3;
/*     */       
/* 193 */       if (l1 == 0L)
/*     */       {
/* 195 */         bool = true;
/* 196 */         break;
/*     */       }
/*     */       do
/*     */       {
/* 200 */         long l4 = paramIovArray.processWritten(j, l3);
/* 201 */         if (l4 == -1L) {
/*     */           break;
/*     */         }
/*     */         
/* 205 */         j++;
/* 206 */         i--;
/* 207 */         l3 -= l4;
/*     */       }
/* 209 */       while ((j < k) && (l3 > 0L));
/*     */     }
/*     */     
/* 212 */     paramChannelOutboundBuffer.removeBytes(l2);
/* 213 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean writeBytesMultiple(ChannelOutboundBuffer paramChannelOutboundBuffer, ByteBuffer[] paramArrayOfByteBuffer, int paramInt, long paramLong)
/*     */     throws IOException
/*     */   {
/* 220 */     assert (paramLong != 0L);
/*     */     
/* 222 */     boolean bool = false;
/* 223 */     long l1 = 0L;
/* 224 */     int i = 0;
/* 225 */     int j = i + paramInt;
/*     */     for (;;) {
/* 227 */       long l2 = Native.writev(this.fd, paramArrayOfByteBuffer, i, paramInt);
/* 228 */       if (l2 == 0L)
/*     */       {
/* 230 */         setEpollOut();
/* 231 */         break;
/*     */       }
/* 233 */       paramLong -= l2;
/* 234 */       l1 += l2;
/*     */       
/* 236 */       if (paramLong == 0L)
/*     */       {
/* 238 */         bool = true;
/* 239 */         break;
/*     */       }
/*     */       do {
/* 242 */         ByteBuffer localByteBuffer = paramArrayOfByteBuffer[i];
/* 243 */         int k = localByteBuffer.position();
/* 244 */         int m = localByteBuffer.limit() - k;
/* 245 */         if (m > l2) {
/* 246 */           localByteBuffer.position(k + (int)l2);
/*     */           
/* 248 */           break;
/*     */         }
/* 250 */         i++;
/* 251 */         paramInt--;
/* 252 */         l2 -= m;
/*     */       }
/* 254 */       while ((i < j) && (l2 > 0L));
/*     */     }
/*     */     
/* 257 */     paramChannelOutboundBuffer.removeBytes(l1);
/* 258 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean writeFileRegion(ChannelOutboundBuffer paramChannelOutboundBuffer, DefaultFileRegion paramDefaultFileRegion)
/*     */     throws Exception
/*     */   {
/* 268 */     long l1 = paramDefaultFileRegion.count();
/* 269 */     if (paramDefaultFileRegion.transfered() >= l1) {
/* 270 */       paramChannelOutboundBuffer.remove();
/* 271 */       return true;
/*     */     }
/*     */     
/* 274 */     long l2 = paramDefaultFileRegion.position();
/* 275 */     boolean bool = false;
/* 276 */     long l3 = 0L;
/*     */     
/* 278 */     for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 279 */       long l4 = paramDefaultFileRegion.transfered();
/* 280 */       long l5 = Native.sendfile(this.fd, paramDefaultFileRegion, l2, l4, l1 - l4);
/* 281 */       if (l5 == 0L)
/*     */       {
/* 283 */         setEpollOut();
/* 284 */         break;
/*     */       }
/*     */       
/* 287 */       l3 += l5;
/* 288 */       if (paramDefaultFileRegion.transfered() >= l1) {
/* 289 */         bool = true;
/* 290 */         break;
/*     */       }
/*     */     }
/*     */     
/* 294 */     if (l3 > 0L) {
/* 295 */       paramChannelOutboundBuffer.progress(l3);
/*     */     }
/*     */     
/* 298 */     if (bool) {
/* 299 */       paramChannelOutboundBuffer.remove();
/*     */     }
/* 301 */     return bool;
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 307 */       int i = paramChannelOutboundBuffer.size();
/*     */       
/* 309 */       if (i == 0)
/*     */       {
/* 311 */         clearEpollOut();
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 316 */         if ((i > 1) && ((paramChannelOutboundBuffer.current() instanceof ByteBuf)) ? 
/* 317 */           !doWriteMultiple(paramChannelOutboundBuffer) : 
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 325 */           !doWriteSingle(paramChannelOutboundBuffer)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean doWriteSingle(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 334 */     Object localObject1 = paramChannelOutboundBuffer.current();
/* 335 */     Object localObject2; if ((localObject1 instanceof ByteBuf)) {
/* 336 */       localObject2 = (ByteBuf)localObject1;
/* 337 */       if (!writeBytes(paramChannelOutboundBuffer, (ByteBuf)localObject2))
/*     */       {
/*     */ 
/* 340 */         return false;
/*     */       }
/* 342 */     } else if ((localObject1 instanceof DefaultFileRegion)) {
/* 343 */       localObject2 = (DefaultFileRegion)localObject1;
/* 344 */       if (!writeFileRegion(paramChannelOutboundBuffer, (DefaultFileRegion)localObject2))
/*     */       {
/*     */ 
/* 347 */         return false;
/*     */       }
/*     */     }
/*     */     else {
/* 351 */       throw new Error();
/*     */     }
/*     */     
/* 354 */     return true; }
/*     */   
/*     */   private boolean doWriteMultiple(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception { Object localObject;
/*     */     int i;
/* 358 */     if (PlatformDependent.hasUnsafe())
/*     */     {
/* 360 */       localObject = IovArray.get(paramChannelOutboundBuffer);
/* 361 */       i = ((IovArray)localObject).count();
/* 362 */       if (i >= 1)
/*     */       {
/* 364 */         if (!writeBytesMultiple(paramChannelOutboundBuffer, (IovArray)localObject))
/*     */         {
/*     */ 
/* 367 */           return false;
/*     */         }
/*     */       } else {
/* 370 */         paramChannelOutboundBuffer.removeBytes(0L);
/*     */       }
/*     */     } else {
/* 373 */       localObject = paramChannelOutboundBuffer.nioBuffers();
/* 374 */       i = paramChannelOutboundBuffer.nioBufferCount();
/* 375 */       if (i >= 1)
/*     */       {
/* 377 */         if (!writeBytesMultiple(paramChannelOutboundBuffer, (ByteBuffer[])localObject, i, paramChannelOutboundBuffer.nioBufferSize()))
/*     */         {
/*     */ 
/* 380 */           return false;
/*     */         }
/*     */       } else {
/* 383 */         paramChannelOutboundBuffer.removeBytes(0L);
/*     */       }
/*     */     }
/*     */     
/* 387 */     return true;
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject)
/*     */   {
/* 392 */     if ((paramObject instanceof ByteBuf)) {
/* 393 */       ByteBuf localByteBuf = (ByteBuf)paramObject;
/* 394 */       if ((!localByteBuf.hasMemoryAddress()) && ((PlatformDependent.hasUnsafe()) || (!localByteBuf.isDirect())))
/*     */       {
/*     */ 
/* 397 */         localByteBuf = newDirectBuffer(localByteBuf);
/* 398 */         assert (localByteBuf.hasMemoryAddress());
/*     */       }
/* 400 */       return localByteBuf;
/*     */     }
/*     */     
/* 403 */     if ((paramObject instanceof DefaultFileRegion)) {
/* 404 */       return paramObject;
/*     */     }
/*     */     
/* 407 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */ 
/*     */   public EpollSocketChannelConfig config()
/*     */   {
/* 413 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isInputShutdown()
/*     */   {
/* 418 */     return this.inputShutdown;
/*     */   }
/*     */   
/*     */   public boolean isOutputShutdown()
/*     */   {
/* 423 */     return (this.outputShutdown) || (!isActive());
/*     */   }
/*     */   
/*     */   public ChannelFuture shutdownOutput()
/*     */   {
/* 428 */     return shutdownOutput(newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise paramChannelPromise)
/*     */   {
/* 433 */     EventLoop localEventLoop = eventLoop();
/* 434 */     if (localEventLoop.inEventLoop()) {
/*     */       try {
/* 436 */         Native.shutdown(this.fd, false, true);
/* 437 */         this.outputShutdown = true;
/* 438 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 440 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 443 */       localEventLoop.execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 446 */           EpollSocketChannel.this.shutdownOutput(paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 450 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 455 */   public ServerSocketChannel parent() { return (ServerSocketChannel)super.parent(); }
/*     */   
/*     */   final class EpollSocketUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
/* 458 */     EpollSocketUnsafe() { super(); }
/*     */     
/*     */     private void closeOnRead(ChannelPipeline paramChannelPipeline)
/*     */     {
/* 462 */       EpollSocketChannel.this.inputShutdown = true;
/* 463 */       if (EpollSocketChannel.this.isOpen()) {
/* 464 */         if (Boolean.TRUE.equals(EpollSocketChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 465 */           clearEpollIn0();
/* 466 */           paramChannelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */         } else {
/* 468 */           close(voidPromise());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean handleReadException(ChannelPipeline paramChannelPipeline, ByteBuf paramByteBuf, Throwable paramThrowable, boolean paramBoolean) {
/* 474 */       if (paramByteBuf != null) {
/* 475 */         if (paramByteBuf.isReadable()) {
/* 476 */           this.readPending = false;
/* 477 */           paramChannelPipeline.fireChannelRead(paramByteBuf);
/*     */         } else {
/* 479 */           paramByteBuf.release();
/*     */         }
/*     */       }
/* 482 */       paramChannelPipeline.fireChannelReadComplete();
/* 483 */       paramChannelPipeline.fireExceptionCaught(paramThrowable);
/* 484 */       if ((paramBoolean) || ((paramThrowable instanceof IOException))) {
/* 485 */         closeOnRead(paramChannelPipeline);
/* 486 */         return true;
/*     */       }
/* 488 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public void connect(final SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/* 494 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 495 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 499 */         if (EpollSocketChannel.this.connectPromise != null) {
/* 500 */           throw new IllegalStateException("connection attempt already made");
/*     */         }
/*     */         
/* 503 */         boolean bool = EpollSocketChannel.this.isActive();
/* 504 */         if (doConnect((InetSocketAddress)paramSocketAddress1, (InetSocketAddress)paramSocketAddress2)) {
/* 505 */           fulfillConnectPromise(paramChannelPromise, bool);
/*     */         } else {
/* 507 */           EpollSocketChannel.this.connectPromise = paramChannelPromise;
/* 508 */           EpollSocketChannel.this.requestedRemoteAddress = paramSocketAddress1;
/*     */           
/*     */ 
/* 511 */           int i = EpollSocketChannel.this.config().getConnectTimeoutMillis();
/* 512 */           if (i > 0) {
/* 513 */             EpollSocketChannel.this.connectTimeoutFuture = EpollSocketChannel.this.eventLoop().schedule(new Runnable()
/*     */             {
/*     */               public void run() {
/* 516 */                 ChannelPromise localChannelPromise = EpollSocketChannel.this.connectPromise;
/* 517 */                 ConnectTimeoutException localConnectTimeoutException = new ConnectTimeoutException("connection timed out: " + paramSocketAddress1);
/*     */                 
/* 519 */                 if ((localChannelPromise != null) && (localChannelPromise.tryFailure(localConnectTimeoutException)))
/* 520 */                   EpollSocketChannel.EpollSocketUnsafe.this.close(EpollSocketChannel.EpollSocketUnsafe.this.voidPromise()); } }, i, TimeUnit.MILLISECONDS);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 526 */           paramChannelPromise.addListener(new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 529 */               if (paramAnonymousChannelFuture.isCancelled()) {
/* 530 */                 if (EpollSocketChannel.this.connectTimeoutFuture != null) {
/* 531 */                   EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
/*     */                 }
/* 533 */                 EpollSocketChannel.this.connectPromise = null;
/* 534 */                 EpollSocketChannel.EpollSocketUnsafe.this.close(EpollSocketChannel.EpollSocketUnsafe.this.voidPromise());
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*     */       } catch (Throwable localThrowable) { Object localObject;
/* 540 */         if ((localThrowable instanceof ConnectException)) {
/* 541 */           ConnectException localConnectException = new ConnectException(localThrowable.getMessage() + ": " + paramSocketAddress1);
/* 542 */           localConnectException.setStackTrace(localThrowable.getStackTrace());
/* 543 */           localObject = localConnectException;
/*     */         }
/* 545 */         closeIfClosed();
/* 546 */         paramChannelPromise.tryFailure((Throwable)localObject);
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise paramChannelPromise, boolean paramBoolean) {
/* 551 */       if (paramChannelPromise == null)
/*     */       {
/* 553 */         return;
/*     */       }
/* 555 */       EpollSocketChannel.this.active = true;
/*     */       
/*     */ 
/* 558 */       boolean bool = paramChannelPromise.trySuccess();
/*     */       
/*     */ 
/*     */ 
/* 562 */       if ((!paramBoolean) && (EpollSocketChannel.this.isActive())) {
/* 563 */         EpollSocketChannel.this.pipeline().fireChannelActive();
/*     */       }
/*     */       
/*     */ 
/* 567 */       if (!bool) {
/* 568 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     private void fulfillConnectPromise(ChannelPromise paramChannelPromise, Throwable paramThrowable) {
/* 573 */       if (paramChannelPromise == null)
/*     */       {
/* 575 */         return;
/*     */       }
/*     */       
/*     */ 
/* 579 */       paramChannelPromise.tryFailure(paramThrowable);
/* 580 */       closeIfClosed();
/*     */     }
/*     */     
/*     */ 
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     private void finishConnect()
/*     */     {
/* 587 */       assert (EpollSocketChannel.this.eventLoop().inEventLoop());
/*     */       
/* 589 */       int i = 0;
/*     */       try {
/* 591 */         boolean bool = EpollSocketChannel.this.isActive();
/* 592 */         if (!doFinishConnect()) {
/* 593 */           i = 1;
/*     */         }
/*     */         else
/* 596 */           fulfillConnectPromise(EpollSocketChannel.this.connectPromise, bool);
/*     */       } catch (Throwable localThrowable) { Object localObject1;
/* 598 */         if ((localThrowable instanceof ConnectException)) {
/* 599 */           ConnectException localConnectException = new ConnectException(localThrowable.getMessage() + ": " + EpollSocketChannel.this.requestedRemoteAddress);
/* 600 */           localConnectException.setStackTrace(localThrowable.getStackTrace());
/* 601 */           localObject1 = localConnectException;
/*     */         }
/*     */         
/* 604 */         fulfillConnectPromise(EpollSocketChannel.this.connectPromise, (Throwable)localObject1);
/*     */       } finally {
/* 606 */         if (i == 0)
/*     */         {
/*     */ 
/* 609 */           if (EpollSocketChannel.this.connectTimeoutFuture != null) {
/* 610 */             EpollSocketChannel.this.connectTimeoutFuture.cancel(false);
/*     */           }
/* 612 */           EpollSocketChannel.this.connectPromise = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     void epollOutReady()
/*     */     {
/* 619 */       if (EpollSocketChannel.this.connectPromise != null)
/*     */       {
/* 621 */         finishConnect();
/*     */       } else {
/* 623 */         super.epollOutReady();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private boolean doConnect(InetSocketAddress paramInetSocketAddress1, InetSocketAddress paramInetSocketAddress2)
/*     */       throws Exception
/*     */     {
/* 631 */       if (paramInetSocketAddress2 != null) {
/* 632 */         AbstractEpollChannel.checkResolvable(paramInetSocketAddress2);
/* 633 */         Native.bind(EpollSocketChannel.this.fd, paramInetSocketAddress2.getAddress(), paramInetSocketAddress2.getPort());
/*     */       }
/*     */       
/* 636 */       int i = 0;
/*     */       try {
/* 638 */         AbstractEpollChannel.checkResolvable(paramInetSocketAddress1);
/* 639 */         boolean bool1 = Native.connect(EpollSocketChannel.this.fd, paramInetSocketAddress1.getAddress(), paramInetSocketAddress1.getPort());
/*     */         
/* 641 */         EpollSocketChannel.this.remote = paramInetSocketAddress1;
/* 642 */         EpollSocketChannel.this.local = Native.localAddress(EpollSocketChannel.this.fd);
/* 643 */         if (!bool1) {
/* 644 */           EpollSocketChannel.this.setEpollOut();
/*     */         }
/* 646 */         i = 1;
/* 647 */         return bool1;
/*     */       } finally {
/* 649 */         if (i == 0) {
/* 650 */           EpollSocketChannel.this.doClose();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private boolean doFinishConnect()
/*     */       throws Exception
/*     */     {
/* 659 */       if (Native.finishConnect(EpollSocketChannel.this.fd)) {
/* 660 */         EpollSocketChannel.this.clearEpollOut();
/* 661 */         return true;
/*     */       }
/* 663 */       EpollSocketChannel.this.setEpollOut();
/* 664 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private int doReadBytes(ByteBuf paramByteBuf)
/*     */       throws Exception
/*     */     {
/* 672 */       int i = paramByteBuf.writerIndex();
/*     */       int j;
/* 674 */       if (paramByteBuf.hasMemoryAddress()) {
/* 675 */         j = Native.readAddress(EpollSocketChannel.this.fd, paramByteBuf.memoryAddress(), i, paramByteBuf.capacity());
/*     */       } else {
/* 677 */         ByteBuffer localByteBuffer = paramByteBuf.internalNioBuffer(i, paramByteBuf.writableBytes());
/* 678 */         j = Native.read(EpollSocketChannel.this.fd, localByteBuffer, localByteBuffer.position(), localByteBuffer.limit());
/*     */       }
/* 680 */       if (j > 0) {
/* 681 */         paramByteBuf.writerIndex(i + j);
/*     */       }
/* 683 */       return j;
/*     */     }
/*     */     
/*     */     void epollRdHupReady()
/*     */     {
/* 688 */       if (EpollSocketChannel.this.isActive()) {
/* 689 */         epollInReady();
/*     */       } else {
/* 691 */         closeOnRead(EpollSocketChannel.this.pipeline());
/*     */       }
/*     */     }
/*     */     
/*     */     void epollInReady()
/*     */     {
/* 697 */       EpollSocketChannelConfig localEpollSocketChannelConfig = EpollSocketChannel.this.config();
/* 698 */       ChannelPipeline localChannelPipeline = EpollSocketChannel.this.pipeline();
/* 699 */       ByteBufAllocator localByteBufAllocator = localEpollSocketChannelConfig.getAllocator();
/* 700 */       RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 701 */       if (localHandle == null) {
/* 702 */         this.allocHandle = (localHandle = localEpollSocketChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */       }
/*     */       
/* 705 */       ByteBuf localByteBuf = null;
/* 706 */       boolean bool1 = false;
/*     */       try {
/* 708 */         int i = 0;
/*     */         
/*     */         for (;;)
/*     */         {
/* 712 */           localByteBuf = localHandle.allocate(localByteBufAllocator);
/* 713 */           int j = localByteBuf.writableBytes();
/* 714 */           int k = doReadBytes(localByteBuf);
/* 715 */           if (k <= 0)
/*     */           {
/* 717 */             localByteBuf.release();
/* 718 */             bool1 = k < 0;
/*     */           }
/*     */           else {
/* 721 */             this.readPending = false;
/* 722 */             localChannelPipeline.fireChannelRead(localByteBuf);
/* 723 */             localByteBuf = null;
/*     */             
/* 725 */             if (i >= Integer.MAX_VALUE - k) {
/* 726 */               localHandle.record(i);
/*     */               
/*     */ 
/* 729 */               i = k;
/*     */             } else {
/* 731 */               i += k;
/*     */             }
/*     */             
/* 734 */             if (k < j) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 740 */         localChannelPipeline.fireChannelReadComplete();
/* 741 */         localHandle.record(i);
/*     */         
/* 743 */         if (bool1) {
/* 744 */           closeOnRead(localChannelPipeline);
/* 745 */           bool1 = false;
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 748 */         boolean bool2 = handleReadException(localChannelPipeline, localByteBuf, localThrowable, bool1);
/* 749 */         if (!bool2)
/*     */         {
/*     */ 
/* 752 */           EpollSocketChannel.this.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 755 */               EpollSocketChannel.EpollSocketUnsafe.this.epollInReady();
/*     */             }
/*     */             
/*     */ 
/*     */           });
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 766 */         if ((!localEpollSocketChannelConfig.isAutoRead()) && (!this.readPending)) {
/* 767 */           clearEpollIn0();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */