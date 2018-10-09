/*     */ package io.netty.channel.local;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.Channel.Unsafe;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.SingleThreadEventExecutor;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collections;
/*     */ import java.util.Queue;
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
/*     */ public class LocalChannel
/*     */   extends AbstractChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private static final int MAX_READER_STACK_DEPTH = 8;
/*     */   
/*  51 */   private final ChannelConfig config = new DefaultChannelConfig(this);
/*  52 */   private final Queue<Object> inboundBuffer = new ArrayDeque();
/*  53 */   private final Runnable readTask = new Runnable()
/*     */   {
/*     */     public void run() {
/*  56 */       ChannelPipeline localChannelPipeline = LocalChannel.this.pipeline();
/*     */       for (;;) {
/*  58 */         Object localObject = LocalChannel.this.inboundBuffer.poll();
/*  59 */         if (localObject == null) {
/*     */           break;
/*     */         }
/*  62 */         localChannelPipeline.fireChannelRead(localObject);
/*     */       }
/*  64 */       localChannelPipeline.fireChannelReadComplete();
/*     */     }
/*     */   };
/*     */   
/*  68 */   private final Runnable shutdownHook = new Runnable()
/*     */   {
/*     */     public void run() {
/*  71 */       LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */     }
/*     */   };
/*     */   private volatile int state;
/*     */   private volatile LocalChannel peer;
/*     */   private volatile LocalAddress localAddress;
/*     */   private volatile LocalAddress remoteAddress;
/*     */   private volatile ChannelPromise connectPromise;
/*     */   private volatile boolean readInProgress;
/*     */   private volatile boolean registerInProgress;
/*     */   
/*     */   public LocalChannel()
/*     */   {
/*  84 */     super(null);
/*     */   }
/*     */   
/*     */   LocalChannel(LocalServerChannel paramLocalServerChannel, LocalChannel paramLocalChannel) {
/*  88 */     super(paramLocalServerChannel);
/*  89 */     this.peer = paramLocalChannel;
/*  90 */     this.localAddress = paramLocalServerChannel.localAddress();
/*  91 */     this.remoteAddress = paramLocalChannel.localAddress();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  96 */     return METADATA;
/*     */   }
/*     */   
/*     */   public ChannelConfig config()
/*     */   {
/* 101 */     return this.config;
/*     */   }
/*     */   
/*     */   public LocalServerChannel parent()
/*     */   {
/* 106 */     return (LocalServerChannel)super.parent();
/*     */   }
/*     */   
/*     */   public LocalAddress localAddress()
/*     */   {
/* 111 */     return (LocalAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public LocalAddress remoteAddress()
/*     */   {
/* 116 */     return (LocalAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 121 */     return this.state < 3;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 126 */     return this.state == 2;
/*     */   }
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe()
/*     */   {
/* 131 */     return new LocalUnsafe(null);
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/* 136 */     return paramEventLoop instanceof SingleThreadEventLoop;
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 141 */     return this.localAddress;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 146 */     return this.remoteAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void doRegister()
/*     */     throws Exception
/*     */   {
/* 156 */     if ((this.peer != null) && (parent() != null))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 163 */       final LocalChannel localLocalChannel = this.peer;
/* 164 */       this.registerInProgress = true;
/* 165 */       this.state = 2;
/*     */       
/* 167 */       localLocalChannel.remoteAddress = parent().localAddress();
/* 168 */       localLocalChannel.state = 2;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 174 */       localLocalChannel.eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 177 */           LocalChannel.this.registerInProgress = false;
/* 178 */           localLocalChannel.pipeline().fireChannelActive();
/* 179 */           localLocalChannel.connectPromise.setSuccess();
/*     */         }
/*     */       });
/*     */     }
/* 183 */     ((SingleThreadEventExecutor)eventLoop()).addShutdownHook(this.shutdownHook);
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 188 */     this.localAddress = LocalChannelRegistry.register(this, this.localAddress, paramSocketAddress);
/*     */     
/*     */ 
/* 191 */     this.state = 1;
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 196 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 201 */     if (this.state <= 2)
/*     */     {
/* 203 */       if (this.localAddress != null) {
/* 204 */         if (parent() == null) {
/* 205 */           LocalChannelRegistry.unregister(this.localAddress);
/*     */         }
/* 207 */         this.localAddress = null;
/*     */       }
/* 209 */       this.state = 3;
/*     */     }
/*     */     
/* 212 */     final LocalChannel localLocalChannel = this.peer;
/* 213 */     if ((localLocalChannel != null) && (localLocalChannel.isActive()))
/*     */     {
/*     */ 
/* 216 */       EventLoop localEventLoop = localLocalChannel.eventLoop();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 222 */       if ((localEventLoop.inEventLoop()) && (!this.registerInProgress)) {
/* 223 */         localLocalChannel.unsafe().close(unsafe().voidPromise());
/*     */       } else {
/* 225 */         localLocalChannel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 228 */             localLocalChannel.unsafe().close(LocalChannel.this.unsafe().voidPromise());
/*     */           }
/*     */         });
/*     */       }
/* 232 */       this.peer = null;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDeregister()
/*     */     throws Exception
/*     */   {
/* 239 */     ((SingleThreadEventExecutor)eventLoop()).removeShutdownHook(this.shutdownHook);
/*     */   }
/*     */   
/*     */   protected void doBeginRead() throws Exception
/*     */   {
/* 244 */     if (this.readInProgress) {
/* 245 */       return;
/*     */     }
/*     */     
/* 248 */     ChannelPipeline localChannelPipeline = pipeline();
/* 249 */     Queue localQueue = this.inboundBuffer;
/* 250 */     if (localQueue.isEmpty()) {
/* 251 */       this.readInProgress = true;
/* 252 */       return;
/*     */     }
/*     */     
/* 255 */     InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.get();
/* 256 */     Integer localInteger = Integer.valueOf(localInternalThreadLocalMap.localChannelReaderStackDepth());
/* 257 */     if (localInteger.intValue() < 8) {
/* 258 */       localInternalThreadLocalMap.setLocalChannelReaderStackDepth(localInteger.intValue() + 1);
/*     */       try {
/*     */         for (;;) {
/* 261 */           Object localObject1 = localQueue.poll();
/* 262 */           if (localObject1 == null) {
/*     */             break;
/*     */           }
/* 265 */           localChannelPipeline.fireChannelRead(localObject1);
/*     */         }
/* 267 */         localChannelPipeline.fireChannelReadComplete();
/*     */       } finally {
/* 269 */         localInternalThreadLocalMap.setLocalChannelReaderStackDepth(localInteger.intValue());
/*     */       }
/*     */     } else {
/* 272 */       eventLoop().execute(this.readTask);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 278 */     if (this.state < 2) {
/* 279 */       throw new NotYetConnectedException();
/*     */     }
/* 281 */     if (this.state > 2) {
/* 282 */       throw new ClosedChannelException();
/*     */     }
/*     */     
/* 285 */     final LocalChannel localLocalChannel = this.peer;
/* 286 */     final ChannelPipeline localChannelPipeline = localLocalChannel.pipeline();
/* 287 */     EventLoop localEventLoop = localLocalChannel.eventLoop();
/*     */     final Object localObject;
/* 289 */     if (localEventLoop == eventLoop()) {
/*     */       for (;;) {
/* 291 */         localObject = paramChannelOutboundBuffer.current();
/* 292 */         if (localObject == null) {
/*     */           break;
/*     */         }
/* 295 */         localLocalChannel.inboundBuffer.add(localObject);
/* 296 */         ReferenceCountUtil.retain(localObject);
/* 297 */         paramChannelOutboundBuffer.remove();
/*     */       }
/* 299 */       finishPeerRead(localLocalChannel, localChannelPipeline);
/*     */     }
/*     */     else {
/* 302 */       localObject = new Object[paramChannelOutboundBuffer.size()];
/* 303 */       for (int i = 0; i < localObject.length; i++) {
/* 304 */         localObject[i] = ReferenceCountUtil.retain(paramChannelOutboundBuffer.current());
/* 305 */         paramChannelOutboundBuffer.remove();
/*     */       }
/*     */       
/* 308 */       localEventLoop.execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 311 */           Collections.addAll(localLocalChannel.inboundBuffer, localObject);
/* 312 */           LocalChannel.finishPeerRead(localLocalChannel, localChannelPipeline);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private static void finishPeerRead(LocalChannel paramLocalChannel, ChannelPipeline paramChannelPipeline) {
/* 319 */     if (paramLocalChannel.readInProgress) {
/* 320 */       paramLocalChannel.readInProgress = false;
/*     */       for (;;) {
/* 322 */         Object localObject = paramLocalChannel.inboundBuffer.poll();
/* 323 */         if (localObject == null) {
/*     */           break;
/*     */         }
/* 326 */         paramChannelPipeline.fireChannelRead(localObject);
/*     */       }
/* 328 */       paramChannelPipeline.fireChannelReadComplete();
/*     */     }
/*     */   }
/*     */   
/* 332 */   private class LocalUnsafe extends AbstractChannel.AbstractUnsafe { private LocalUnsafe() { super(); }
/*     */     
/*     */ 
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/* 337 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 338 */         return;
/*     */       }
/*     */       
/* 341 */       if (LocalChannel.this.state == 2) {
/* 342 */         AlreadyConnectedException localAlreadyConnectedException = new AlreadyConnectedException();
/* 343 */         safeSetFailure(paramChannelPromise, localAlreadyConnectedException);
/* 344 */         LocalChannel.this.pipeline().fireExceptionCaught(localAlreadyConnectedException);
/* 345 */         return;
/*     */       }
/*     */       
/* 348 */       if (LocalChannel.this.connectPromise != null) {
/* 349 */         throw new ConnectionPendingException();
/*     */       }
/*     */       
/* 352 */       LocalChannel.this.connectPromise = paramChannelPromise;
/*     */       
/* 354 */       if (LocalChannel.this.state != 1)
/*     */       {
/* 356 */         if (paramSocketAddress2 == null) {
/* 357 */           paramSocketAddress2 = new LocalAddress(LocalChannel.this);
/*     */         }
/*     */       }
/*     */       
/* 361 */       if (paramSocketAddress2 != null) {
/*     */         try {
/* 363 */           LocalChannel.this.doBind(paramSocketAddress2);
/*     */         } catch (Throwable localThrowable) {
/* 365 */           safeSetFailure(paramChannelPromise, localThrowable);
/* 366 */           close(voidPromise());
/* 367 */           return;
/*     */         }
/*     */       }
/*     */       
/* 371 */       Channel localChannel = LocalChannelRegistry.get(paramSocketAddress1);
/* 372 */       if (!(localChannel instanceof LocalServerChannel)) {
/* 373 */         localObject = new ChannelException("connection refused");
/* 374 */         safeSetFailure(paramChannelPromise, (Throwable)localObject);
/* 375 */         close(voidPromise());
/* 376 */         return;
/*     */       }
/*     */       
/* 379 */       Object localObject = (LocalServerChannel)localChannel;
/* 380 */       LocalChannel.this.peer = ((LocalServerChannel)localObject).serve(LocalChannel.this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */