/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.DefaultAttributeMap;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.RecyclableMpscLinkedQueueNode;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractChannelHandlerContext
/*     */   extends DefaultAttributeMap
/*     */   implements ChannelHandlerContext
/*     */ {
/*     */   volatile AbstractChannelHandlerContext next;
/*     */   volatile AbstractChannelHandlerContext prev;
/*     */   private final boolean inbound;
/*     */   private final boolean outbound;
/*     */   private final AbstractChannel channel;
/*     */   private final DefaultChannelPipeline pipeline;
/*     */   private final String name;
/*     */   private boolean removed;
/*     */   final EventExecutor executor;
/*     */   private ChannelFuture succeededFuture;
/*     */   private volatile Runnable invokeChannelReadCompleteTask;
/*     */   private volatile Runnable invokeReadTask;
/*     */   private volatile Runnable invokeChannelWritableStateChangedTask;
/*     */   private volatile Runnable invokeFlushTask;
/*     */   
/*     */   AbstractChannelHandlerContext(DefaultChannelPipeline paramDefaultChannelPipeline, EventExecutorGroup paramEventExecutorGroup, String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  60 */     if (paramString == null) {
/*  61 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/*  64 */     this.channel = paramDefaultChannelPipeline.channel;
/*  65 */     this.pipeline = paramDefaultChannelPipeline;
/*  66 */     this.name = paramString;
/*     */     
/*  68 */     if (paramEventExecutorGroup != null)
/*     */     {
/*     */ 
/*  71 */       EventExecutor localEventExecutor = (EventExecutor)paramDefaultChannelPipeline.childExecutors.get(paramEventExecutorGroup);
/*  72 */       if (localEventExecutor == null) {
/*  73 */         localEventExecutor = paramEventExecutorGroup.next();
/*  74 */         paramDefaultChannelPipeline.childExecutors.put(paramEventExecutorGroup, localEventExecutor);
/*     */       }
/*  76 */       this.executor = localEventExecutor;
/*     */     } else {
/*  78 */       this.executor = null;
/*     */     }
/*     */     
/*  81 */     this.inbound = paramBoolean1;
/*  82 */     this.outbound = paramBoolean2;
/*     */   }
/*     */   
/*     */   void teardown()
/*     */   {
/*  87 */     EventExecutor localEventExecutor = executor();
/*  88 */     if (localEventExecutor.inEventLoop()) {
/*  89 */       teardown0();
/*     */     } else {
/*  91 */       localEventExecutor.execute(new Runnable()
/*     */       {
/*     */         public void run() {
/*  94 */           AbstractChannelHandlerContext.this.teardown0();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   private void teardown0() {
/* 101 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.prev;
/* 102 */     if (localAbstractChannelHandlerContext != null) {
/* 103 */       synchronized (this.pipeline) {
/* 104 */         this.pipeline.remove0(this);
/*     */       }
/* 106 */       localAbstractChannelHandlerContext.teardown();
/*     */     }
/*     */   }
/*     */   
/*     */   public Channel channel()
/*     */   {
/* 112 */     return this.channel;
/*     */   }
/*     */   
/*     */   public ChannelPipeline pipeline()
/*     */   {
/* 117 */     return this.pipeline;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/* 122 */     return channel().config().getAllocator();
/*     */   }
/*     */   
/*     */   public EventExecutor executor()
/*     */   {
/* 127 */     if (this.executor == null) {
/* 128 */       return channel().eventLoop();
/*     */     }
/* 130 */     return this.executor;
/*     */   }
/*     */   
/*     */ 
/*     */   public String name()
/*     */   {
/* 136 */     return this.name;
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelRegistered()
/*     */   {
/* 141 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 142 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 143 */     if (localEventExecutor.inEventLoop()) {
/* 144 */       localAbstractChannelHandlerContext.invokeChannelRegistered();
/*     */     } else {
/* 146 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 149 */           localAbstractChannelHandlerContext.invokeChannelRegistered();
/*     */         }
/*     */       });
/*     */     }
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRegistered() {
/*     */     try {
/* 158 */       ((ChannelInboundHandler)handler()).channelRegistered(this);
/*     */     } catch (Throwable localThrowable) {
/* 160 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelUnregistered()
/*     */   {
/* 166 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 167 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 168 */     if (localEventExecutor.inEventLoop()) {
/* 169 */       localAbstractChannelHandlerContext.invokeChannelUnregistered();
/*     */     } else {
/* 171 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 174 */           localAbstractChannelHandlerContext.invokeChannelUnregistered();
/*     */         }
/*     */       });
/*     */     }
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelUnregistered() {
/*     */     try {
/* 183 */       ((ChannelInboundHandler)handler()).channelUnregistered(this);
/*     */     } catch (Throwable localThrowable) {
/* 185 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelActive()
/*     */   {
/* 191 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 192 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 193 */     if (localEventExecutor.inEventLoop()) {
/* 194 */       localAbstractChannelHandlerContext.invokeChannelActive();
/*     */     } else {
/* 196 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 199 */           localAbstractChannelHandlerContext.invokeChannelActive();
/*     */         }
/*     */       });
/*     */     }
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelActive() {
/*     */     try {
/* 208 */       ((ChannelInboundHandler)handler()).channelActive(this);
/*     */     } catch (Throwable localThrowable) {
/* 210 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelInactive()
/*     */   {
/* 216 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 217 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 218 */     if (localEventExecutor.inEventLoop()) {
/* 219 */       localAbstractChannelHandlerContext.invokeChannelInactive();
/*     */     } else {
/* 221 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 224 */           localAbstractChannelHandlerContext.invokeChannelInactive();
/*     */         }
/*     */       });
/*     */     }
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelInactive() {
/*     */     try {
/* 233 */       ((ChannelInboundHandler)handler()).channelInactive(this);
/*     */     } catch (Throwable localThrowable) {
/* 235 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireExceptionCaught(final Throwable paramThrowable)
/*     */   {
/* 241 */     if (paramThrowable == null) {
/* 242 */       throw new NullPointerException("cause");
/*     */     }
/*     */     
/* 245 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = this.next;
/*     */     
/* 247 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 248 */     if (localEventExecutor.inEventLoop()) {
/* 249 */       localAbstractChannelHandlerContext.invokeExceptionCaught(paramThrowable);
/*     */     } else {
/*     */       try {
/* 252 */         localEventExecutor.execute(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 255 */             localAbstractChannelHandlerContext.invokeExceptionCaught(paramThrowable);
/*     */           }
/*     */         });
/*     */       } catch (Throwable localThrowable) {
/* 259 */         if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 260 */           DefaultChannelPipeline.logger.warn("Failed to submit an exceptionCaught() event.", localThrowable);
/* 261 */           DefaultChannelPipeline.logger.warn("The exceptionCaught() event that was failed to submit was:", paramThrowable);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeExceptionCaught(Throwable paramThrowable) {
/*     */     try {
/* 271 */       handler().exceptionCaught(this, paramThrowable);
/*     */     } catch (Throwable localThrowable) {
/* 273 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 274 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", paramThrowable);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelHandlerContext fireUserEventTriggered(final Object paramObject)
/*     */   {
/* 283 */     if (paramObject == null) {
/* 284 */       throw new NullPointerException("event");
/*     */     }
/*     */     
/* 287 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 288 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 289 */     if (localEventExecutor.inEventLoop()) {
/* 290 */       localAbstractChannelHandlerContext.invokeUserEventTriggered(paramObject);
/*     */     } else {
/* 292 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 295 */           localAbstractChannelHandlerContext.invokeUserEventTriggered(paramObject);
/*     */         }
/*     */       });
/*     */     }
/* 299 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeUserEventTriggered(Object paramObject) {
/*     */     try {
/* 304 */       ((ChannelInboundHandler)handler()).userEventTriggered(this, paramObject);
/*     */     } catch (Throwable localThrowable) {
/* 306 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelRead(final Object paramObject)
/*     */   {
/* 312 */     if (paramObject == null) {
/* 313 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 316 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 317 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 318 */     if (localEventExecutor.inEventLoop()) {
/* 319 */       localAbstractChannelHandlerContext.invokeChannelRead(paramObject);
/*     */     } else {
/* 321 */       localEventExecutor.execute(new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 324 */           localAbstractChannelHandlerContext.invokeChannelRead(paramObject);
/*     */         }
/*     */       });
/*     */     }
/* 328 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelRead(Object paramObject) {
/*     */     try {
/* 333 */       ((ChannelInboundHandler)handler()).channelRead(this, paramObject);
/*     */     } catch (Throwable localThrowable) {
/* 335 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelReadComplete()
/*     */   {
/* 341 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 342 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 343 */     if (localEventExecutor.inEventLoop()) {
/* 344 */       localAbstractChannelHandlerContext.invokeChannelReadComplete();
/*     */     } else {
/* 346 */       Object localObject = localAbstractChannelHandlerContext.invokeChannelReadCompleteTask;
/* 347 */       if (localObject == null) {
/* 348 */         localAbstractChannelHandlerContext.invokeChannelReadCompleteTask = ( = new Runnable()
/*     */         {
/*     */           public void run() {
/* 351 */             localAbstractChannelHandlerContext.invokeChannelReadComplete();
/*     */           }
/*     */         });
/*     */       }
/* 355 */       localEventExecutor.execute((Runnable)localObject);
/*     */     }
/* 357 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelReadComplete() {
/*     */     try {
/* 362 */       ((ChannelInboundHandler)handler()).channelReadComplete(this);
/*     */     } catch (Throwable localThrowable) {
/* 364 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext fireChannelWritabilityChanged()
/*     */   {
/* 370 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextInbound();
/* 371 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 372 */     if (localEventExecutor.inEventLoop()) {
/* 373 */       localAbstractChannelHandlerContext.invokeChannelWritabilityChanged();
/*     */     } else {
/* 375 */       Object localObject = localAbstractChannelHandlerContext.invokeChannelWritableStateChangedTask;
/* 376 */       if (localObject == null) {
/* 377 */         localAbstractChannelHandlerContext.invokeChannelWritableStateChangedTask = ( = new Runnable()
/*     */         {
/*     */           public void run() {
/* 380 */             localAbstractChannelHandlerContext.invokeChannelWritabilityChanged();
/*     */           }
/*     */         });
/*     */       }
/* 384 */       localEventExecutor.execute((Runnable)localObject);
/*     */     }
/* 386 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeChannelWritabilityChanged() {
/*     */     try {
/* 391 */       ((ChannelInboundHandler)handler()).channelWritabilityChanged(this);
/*     */     } catch (Throwable localThrowable) {
/* 393 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture bind(SocketAddress paramSocketAddress)
/*     */   {
/* 399 */     return bind(paramSocketAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress)
/*     */   {
/* 404 */     return connect(paramSocketAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */   {
/* 409 */     return connect(paramSocketAddress1, paramSocketAddress2, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture disconnect()
/*     */   {
/* 414 */     return disconnect(newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture close()
/*     */   {
/* 419 */     return close(newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture deregister()
/*     */   {
/* 424 */     return deregister(newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture bind(final SocketAddress paramSocketAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 429 */     if (paramSocketAddress == null) {
/* 430 */       throw new NullPointerException("localAddress");
/*     */     }
/* 432 */     if (!validatePromise(paramChannelPromise, false))
/*     */     {
/* 434 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 437 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 438 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 439 */     if (localEventExecutor.inEventLoop()) {
/* 440 */       localAbstractChannelHandlerContext.invokeBind(paramSocketAddress, paramChannelPromise);
/*     */     } else {
/* 442 */       safeExecute(localEventExecutor, new OneTimeTask()
/*     */       {
/*     */ 
/* 445 */         public void run() { localAbstractChannelHandlerContext.invokeBind(paramSocketAddress, paramChannelPromise); } }, paramChannelPromise, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 450 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeBind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise) {
/*     */     try {
/* 455 */       ((ChannelOutboundHandler)handler()).bind(this, paramSocketAddress, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 457 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */   {
/* 463 */     return connect(paramSocketAddress, null, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture connect(final SocketAddress paramSocketAddress1, final SocketAddress paramSocketAddress2, final ChannelPromise paramChannelPromise)
/*     */   {
/* 470 */     if (paramSocketAddress1 == null) {
/* 471 */       throw new NullPointerException("remoteAddress");
/*     */     }
/* 473 */     if (!validatePromise(paramChannelPromise, false))
/*     */     {
/* 475 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 478 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 479 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 480 */     if (localEventExecutor.inEventLoop()) {
/* 481 */       localAbstractChannelHandlerContext.invokeConnect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */     } else {
/* 483 */       safeExecute(localEventExecutor, new OneTimeTask()
/*     */       {
/*     */ 
/* 486 */         public void run() { localAbstractChannelHandlerContext.invokeConnect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise); } }, paramChannelPromise, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 491 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise) {
/*     */     try {
/* 496 */       ((ChannelOutboundHandler)handler()).connect(this, paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 498 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture disconnect(final ChannelPromise paramChannelPromise)
/*     */   {
/* 504 */     if (!validatePromise(paramChannelPromise, false))
/*     */     {
/* 506 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 509 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 510 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 511 */     if (localEventExecutor.inEventLoop())
/*     */     {
/*     */ 
/* 514 */       if (!channel().metadata().hasDisconnect()) {
/* 515 */         localAbstractChannelHandlerContext.invokeClose(paramChannelPromise);
/*     */       } else {
/* 517 */         localAbstractChannelHandlerContext.invokeDisconnect(paramChannelPromise);
/*     */       }
/*     */     } else {
/* 520 */       safeExecute(localEventExecutor, new OneTimeTask()
/*     */       {
/*     */         public void run() {
/* 523 */           if (!AbstractChannelHandlerContext.this.channel().metadata().hasDisconnect()) {
/* 524 */             localAbstractChannelHandlerContext.invokeClose(paramChannelPromise);
/*     */           } else
/* 526 */             localAbstractChannelHandlerContext.invokeDisconnect(paramChannelPromise); } }, paramChannelPromise, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 532 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeDisconnect(ChannelPromise paramChannelPromise) {
/*     */     try {
/* 537 */       ((ChannelOutboundHandler)handler()).disconnect(this, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 539 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise paramChannelPromise)
/*     */   {
/* 545 */     if (!validatePromise(paramChannelPromise, false))
/*     */     {
/* 547 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 550 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 551 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 552 */     if (localEventExecutor.inEventLoop()) {
/* 553 */       localAbstractChannelHandlerContext.invokeClose(paramChannelPromise);
/*     */     } else {
/* 555 */       safeExecute(localEventExecutor, new OneTimeTask()
/*     */       {
/*     */ 
/* 558 */         public void run() { localAbstractChannelHandlerContext.invokeClose(paramChannelPromise); } }, paramChannelPromise, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 563 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeClose(ChannelPromise paramChannelPromise) {
/*     */     try {
/* 568 */       ((ChannelOutboundHandler)handler()).close(this, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 570 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture deregister(final ChannelPromise paramChannelPromise)
/*     */   {
/* 576 */     if (!validatePromise(paramChannelPromise, false))
/*     */     {
/* 578 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 581 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 582 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 583 */     if (localEventExecutor.inEventLoop()) {
/* 584 */       localAbstractChannelHandlerContext.invokeDeregister(paramChannelPromise);
/*     */     } else {
/* 586 */       safeExecute(localEventExecutor, new OneTimeTask()
/*     */       {
/*     */ 
/* 589 */         public void run() { localAbstractChannelHandlerContext.invokeDeregister(paramChannelPromise); } }, paramChannelPromise, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 594 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeDeregister(ChannelPromise paramChannelPromise) {
/*     */     try {
/* 599 */       ((ChannelOutboundHandler)handler()).deregister(this, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 601 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext read()
/*     */   {
/* 607 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 608 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 609 */     if (localEventExecutor.inEventLoop()) {
/* 610 */       localAbstractChannelHandlerContext.invokeRead();
/*     */     } else {
/* 612 */       Object localObject = localAbstractChannelHandlerContext.invokeReadTask;
/* 613 */       if (localObject == null) {
/* 614 */         localAbstractChannelHandlerContext.invokeReadTask = ( = new Runnable()
/*     */         {
/*     */           public void run() {
/* 617 */             localAbstractChannelHandlerContext.invokeRead();
/*     */           }
/*     */         });
/*     */       }
/* 621 */       localEventExecutor.execute((Runnable)localObject);
/*     */     }
/*     */     
/* 624 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeRead() {
/*     */     try {
/* 629 */       ((ChannelOutboundHandler)handler()).read(this);
/*     */     } catch (Throwable localThrowable) {
/* 631 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture write(Object paramObject)
/*     */   {
/* 637 */     return write(paramObject, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture write(Object paramObject, ChannelPromise paramChannelPromise)
/*     */   {
/* 642 */     if (paramObject == null) {
/* 643 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 646 */     if (!validatePromise(paramChannelPromise, true)) {
/* 647 */       ReferenceCountUtil.release(paramObject);
/*     */       
/* 649 */       return paramChannelPromise;
/*     */     }
/* 651 */     write(paramObject, false, paramChannelPromise);
/*     */     
/* 653 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void invokeWrite(Object paramObject, ChannelPromise paramChannelPromise) {
/*     */     try {
/* 658 */       ((ChannelOutboundHandler)handler()).write(this, paramObject, paramChannelPromise);
/*     */     } catch (Throwable localThrowable) {
/* 660 */       notifyOutboundHandlerException(localThrowable, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelHandlerContext flush()
/*     */   {
/* 666 */     final AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 667 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 668 */     if (localEventExecutor.inEventLoop()) {
/* 669 */       localAbstractChannelHandlerContext.invokeFlush();
/*     */     } else {
/* 671 */       Object localObject = localAbstractChannelHandlerContext.invokeFlushTask;
/* 672 */       if (localObject == null) {
/* 673 */         localAbstractChannelHandlerContext.invokeFlushTask = ( = new Runnable()
/*     */         {
/*     */           public void run() {
/* 676 */             localAbstractChannelHandlerContext.invokeFlush();
/*     */           }
/*     */         });
/*     */       }
/* 680 */       safeExecute(localEventExecutor, (Runnable)localObject, this.channel.voidPromise(), null);
/*     */     }
/*     */     
/* 683 */     return this;
/*     */   }
/*     */   
/*     */   private void invokeFlush() {
/*     */     try {
/* 688 */       ((ChannelOutboundHandler)handler()).flush(this);
/*     */     } catch (Throwable localThrowable) {
/* 690 */       notifyHandlerException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object paramObject, ChannelPromise paramChannelPromise)
/*     */   {
/* 696 */     if (paramObject == null) {
/* 697 */       throw new NullPointerException("msg");
/*     */     }
/*     */     
/* 700 */     if (!validatePromise(paramChannelPromise, true)) {
/* 701 */       ReferenceCountUtil.release(paramObject);
/*     */       
/* 703 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 706 */     write(paramObject, true, paramChannelPromise);
/*     */     
/* 708 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void write(Object paramObject, boolean paramBoolean, ChannelPromise paramChannelPromise)
/*     */   {
/* 713 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = findContextOutbound();
/* 714 */     EventExecutor localEventExecutor = localAbstractChannelHandlerContext.executor();
/* 715 */     if (localEventExecutor.inEventLoop()) {
/* 716 */       localAbstractChannelHandlerContext.invokeWrite(paramObject, paramChannelPromise);
/* 717 */       if (paramBoolean) {
/* 718 */         localAbstractChannelHandlerContext.invokeFlush();
/*     */       }
/*     */     } else {
/* 721 */       int i = this.channel.estimatorHandle().size(paramObject);
/* 722 */       Object localObject; if (i > 0) {
/* 723 */         localObject = this.channel.unsafe().outboundBuffer();
/*     */         
/* 725 */         if (localObject != null) {
/* 726 */           ((ChannelOutboundBuffer)localObject).incrementPendingOutboundBytes(i);
/*     */         }
/*     */       }
/*     */       
/* 730 */       if (paramBoolean) {
/* 731 */         localObject = WriteAndFlushTask.newInstance(localAbstractChannelHandlerContext, paramObject, i, paramChannelPromise);
/*     */       } else {
/* 733 */         localObject = WriteTask.newInstance(localAbstractChannelHandlerContext, paramObject, i, paramChannelPromise);
/*     */       }
/* 735 */       safeExecute(localEventExecutor, (Runnable)localObject, paramChannelPromise, paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object paramObject)
/*     */   {
/* 741 */     return writeAndFlush(paramObject, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */   private static void notifyOutboundHandlerException(Throwable paramThrowable, ChannelPromise paramChannelPromise)
/*     */   {
/* 747 */     if ((paramChannelPromise instanceof VoidChannelPromise)) {
/* 748 */       return;
/*     */     }
/*     */     
/* 751 */     if ((!paramChannelPromise.tryFailure(paramThrowable)) && 
/* 752 */       (DefaultChannelPipeline.logger.isWarnEnabled())) {
/* 753 */       DefaultChannelPipeline.logger.warn("Failed to fail the promise because it's done already: {}", paramChannelPromise, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private void notifyHandlerException(Throwable paramThrowable)
/*     */   {
/* 759 */     if (inExceptionCaught(paramThrowable)) {
/* 760 */       if (DefaultChannelPipeline.logger.isWarnEnabled()) {
/* 761 */         DefaultChannelPipeline.logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", paramThrowable);
/*     */       }
/*     */       
/*     */ 
/* 765 */       return;
/*     */     }
/*     */     
/* 768 */     invokeExceptionCaught(paramThrowable);
/*     */   }
/*     */   
/*     */   private static boolean inExceptionCaught(Throwable paramThrowable) {
/*     */     do {
/* 773 */       StackTraceElement[] arrayOfStackTraceElement1 = paramThrowable.getStackTrace();
/* 774 */       if (arrayOfStackTraceElement1 != null) {
/* 775 */         for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
/* 776 */           if (localStackTraceElement == null) {
/*     */             break;
/*     */           }
/* 779 */           if ("exceptionCaught".equals(localStackTraceElement.getMethodName())) {
/* 780 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 785 */       paramThrowable = paramThrowable.getCause();
/* 786 */     } while (paramThrowable != null);
/*     */     
/* 788 */     return false;
/*     */   }
/*     */   
/*     */   public ChannelPromise newPromise()
/*     */   {
/* 793 */     return new DefaultChannelPromise(channel(), executor());
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise()
/*     */   {
/* 798 */     return new DefaultChannelProgressivePromise(channel(), executor());
/*     */   }
/*     */   
/*     */   public ChannelFuture newSucceededFuture()
/*     */   {
/* 803 */     Object localObject = this.succeededFuture;
/* 804 */     if (localObject == null) {
/* 805 */       this.succeededFuture = (localObject = new SucceededChannelFuture(channel(), executor()));
/*     */     }
/* 807 */     return (ChannelFuture)localObject;
/*     */   }
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable paramThrowable)
/*     */   {
/* 812 */     return new FailedChannelFuture(channel(), executor(), paramThrowable);
/*     */   }
/*     */   
/*     */   private boolean validatePromise(ChannelPromise paramChannelPromise, boolean paramBoolean) {
/* 816 */     if (paramChannelPromise == null) {
/* 817 */       throw new NullPointerException("promise");
/*     */     }
/*     */     
/* 820 */     if (paramChannelPromise.isDone())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 825 */       if (paramChannelPromise.isCancelled()) {
/* 826 */         return false;
/*     */       }
/* 828 */       throw new IllegalArgumentException("promise already done: " + paramChannelPromise);
/*     */     }
/*     */     
/* 831 */     if (paramChannelPromise.channel() != channel()) {
/* 832 */       throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", new Object[] { paramChannelPromise.channel(), channel() }));
/*     */     }
/*     */     
/*     */ 
/* 836 */     if (paramChannelPromise.getClass() == DefaultChannelPromise.class) {
/* 837 */       return true;
/*     */     }
/*     */     
/* 840 */     if ((!paramBoolean) && ((paramChannelPromise instanceof VoidChannelPromise))) {
/* 841 */       throw new IllegalArgumentException(StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
/*     */     }
/*     */     
/*     */ 
/* 845 */     if ((paramChannelPromise instanceof AbstractChannel.CloseFuture)) {
/* 846 */       throw new IllegalArgumentException(StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
/*     */     }
/*     */     
/* 849 */     return true;
/*     */   }
/*     */   
/*     */   private AbstractChannelHandlerContext findContextInbound() {
/* 853 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this;
/*     */     do {
/* 855 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.next;
/* 856 */     } while (!localAbstractChannelHandlerContext.inbound);
/* 857 */     return localAbstractChannelHandlerContext;
/*     */   }
/*     */   
/*     */   private AbstractChannelHandlerContext findContextOutbound() {
/* 861 */     AbstractChannelHandlerContext localAbstractChannelHandlerContext = this;
/*     */     do {
/* 863 */       localAbstractChannelHandlerContext = localAbstractChannelHandlerContext.prev;
/* 864 */     } while (!localAbstractChannelHandlerContext.outbound);
/* 865 */     return localAbstractChannelHandlerContext;
/*     */   }
/*     */   
/*     */   public ChannelPromise voidPromise()
/*     */   {
/* 870 */     return this.channel.voidPromise();
/*     */   }
/*     */   
/*     */   void setRemoved() {
/* 874 */     this.removed = true;
/*     */   }
/*     */   
/*     */   public boolean isRemoved()
/*     */   {
/* 879 */     return this.removed;
/*     */   }
/*     */   
/*     */   private static void safeExecute(EventExecutor paramEventExecutor, Runnable paramRunnable, ChannelPromise paramChannelPromise, Object paramObject) {
/*     */     try {
/* 884 */       paramEventExecutor.execute(paramRunnable);
/*     */     } catch (Throwable localThrowable) {
/*     */       try {
/* 887 */         paramChannelPromise.setFailure(localThrowable);
/*     */       } finally {
/* 889 */         if (paramObject != null) {
/* 890 */           ReferenceCountUtil.release(paramObject);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class AbstractWriteTask extends RecyclableMpscLinkedQueueNode<Runnable> implements Runnable {
/*     */     private AbstractChannelHandlerContext ctx;
/*     */     private Object msg;
/*     */     private ChannelPromise promise;
/*     */     private int size;
/*     */     
/*     */     private AbstractWriteTask(Recycler.Handle paramHandle) {
/* 903 */       super();
/*     */     }
/*     */     
/*     */     protected static void init(AbstractWriteTask paramAbstractWriteTask, AbstractChannelHandlerContext paramAbstractChannelHandlerContext, Object paramObject, int paramInt, ChannelPromise paramChannelPromise)
/*     */     {
/* 908 */       paramAbstractWriteTask.ctx = paramAbstractChannelHandlerContext;
/* 909 */       paramAbstractWriteTask.msg = paramObject;
/* 910 */       paramAbstractWriteTask.promise = paramChannelPromise;
/* 911 */       paramAbstractWriteTask.size = paramInt;
/*     */     }
/*     */     
/*     */     public final void run()
/*     */     {
/*     */       try {
/* 917 */         if (this.size > 0) {
/* 918 */           ChannelOutboundBuffer localChannelOutboundBuffer = this.ctx.channel.unsafe().outboundBuffer();
/*     */           
/* 920 */           if (localChannelOutboundBuffer != null) {
/* 921 */             localChannelOutboundBuffer.decrementPendingOutboundBytes(this.size);
/*     */           }
/*     */         }
/* 924 */         write(this.ctx, this.msg, this.promise);
/*     */       }
/*     */       finally {
/* 927 */         this.ctx = null;
/* 928 */         this.msg = null;
/* 929 */         this.promise = null;
/*     */       }
/*     */     }
/*     */     
/*     */     public Runnable value()
/*     */     {
/* 935 */       return this;
/*     */     }
/*     */     
/*     */     protected void write(AbstractChannelHandlerContext paramAbstractChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) {
/* 939 */       paramAbstractChannelHandlerContext.invokeWrite(paramObject, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WriteTask extends AbstractChannelHandlerContext.AbstractWriteTask implements SingleThreadEventLoop.NonWakeupRunnable
/*     */   {
/* 945 */     private static final Recycler<WriteTask> RECYCLER = new Recycler()
/*     */     {
/*     */       protected AbstractChannelHandlerContext.WriteTask newObject(Recycler.Handle paramAnonymousHandle) {
/* 948 */         return new AbstractChannelHandlerContext.WriteTask(paramAnonymousHandle, null);
/*     */       }
/*     */     };
/*     */     
/*     */     private static WriteTask newInstance(AbstractChannelHandlerContext paramAbstractChannelHandlerContext, Object paramObject, int paramInt, ChannelPromise paramChannelPromise)
/*     */     {
/* 954 */       WriteTask localWriteTask = (WriteTask)RECYCLER.get();
/* 955 */       init(localWriteTask, paramAbstractChannelHandlerContext, paramObject, paramInt, paramChannelPromise);
/* 956 */       return localWriteTask;
/*     */     }
/*     */     
/*     */     private WriteTask(Recycler.Handle paramHandle) {
/* 960 */       super(null);
/*     */     }
/*     */     
/*     */     protected void recycle(Recycler.Handle paramHandle)
/*     */     {
/* 965 */       RECYCLER.recycle(this, paramHandle);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class WriteAndFlushTask extends AbstractChannelHandlerContext.AbstractWriteTask
/*     */   {
/* 971 */     private static final Recycler<WriteAndFlushTask> RECYCLER = new Recycler()
/*     */     {
/*     */       protected AbstractChannelHandlerContext.WriteAndFlushTask newObject(Recycler.Handle paramAnonymousHandle) {
/* 974 */         return new AbstractChannelHandlerContext.WriteAndFlushTask(paramAnonymousHandle, null);
/*     */       }
/*     */     };
/*     */     
/*     */     private static WriteAndFlushTask newInstance(AbstractChannelHandlerContext paramAbstractChannelHandlerContext, Object paramObject, int paramInt, ChannelPromise paramChannelPromise)
/*     */     {
/* 980 */       WriteAndFlushTask localWriteAndFlushTask = (WriteAndFlushTask)RECYCLER.get();
/* 981 */       init(localWriteAndFlushTask, paramAbstractChannelHandlerContext, paramObject, paramInt, paramChannelPromise);
/* 982 */       return localWriteAndFlushTask;
/*     */     }
/*     */     
/*     */     private WriteAndFlushTask(Recycler.Handle paramHandle) {
/* 986 */       super(null);
/*     */     }
/*     */     
/*     */     public void write(AbstractChannelHandlerContext paramAbstractChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise)
/*     */     {
/* 991 */       super.write(paramAbstractChannelHandlerContext, paramObject, paramChannelPromise);
/* 992 */       paramAbstractChannelHandlerContext.invokeFlush();
/*     */     }
/*     */     
/*     */     protected void recycle(Recycler.Handle paramHandle)
/*     */     {
/* 997 */       RECYCLER.recycle(this, paramHandle);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\AbstractChannelHandlerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */