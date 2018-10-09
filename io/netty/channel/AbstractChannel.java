/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.DefaultAttributeMap;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.OneTimeTask;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ import java.util.concurrent.RejectedExecutionException;
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
/*     */ public abstract class AbstractChannel
/*     */   extends DefaultAttributeMap
/*     */   implements Channel
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
/*     */   
/*  42 */   static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = new ClosedChannelException();
/*  43 */   static final NotYetConnectedException NOT_YET_CONNECTED_EXCEPTION = new NotYetConnectedException();
/*     */   private MessageSizeEstimator.Handle estimatorHandle;
/*     */   
/*  46 */   static { CLOSED_CHANNEL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  47 */     NOT_YET_CONNECTED_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*     */ 
/*     */   private final Channel parent;
/*     */   
/*  53 */   private final long hashCode = ThreadLocalRandom.current().nextLong();
/*     */   private final Channel.Unsafe unsafe;
/*     */   private final DefaultChannelPipeline pipeline;
/*  56 */   private final ChannelFuture succeededFuture = new SucceededChannelFuture(this, null);
/*  57 */   private final VoidChannelPromise voidPromise = new VoidChannelPromise(this, true);
/*  58 */   private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
/*  59 */   private final CloseFuture closeFuture = new CloseFuture(this);
/*     */   
/*     */ 
/*     */   private volatile SocketAddress localAddress;
/*     */   
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   private volatile EventLoop eventLoop;
/*     */   
/*     */   private volatile boolean registered;
/*     */   
/*     */   private boolean strValActive;
/*     */   
/*     */   private String strVal;
/*     */   
/*     */ 
/*     */   protected AbstractChannel(Channel paramChannel)
/*     */   {
/*  77 */     this.parent = paramChannel;
/*  78 */     this.unsafe = newUnsafe();
/*  79 */     this.pipeline = new DefaultChannelPipeline(this);
/*     */   }
/*     */   
/*     */   public boolean isWritable()
/*     */   {
/*  84 */     ChannelOutboundBuffer localChannelOutboundBuffer = this.unsafe.outboundBuffer();
/*  85 */     return (localChannelOutboundBuffer != null) && (localChannelOutboundBuffer.isWritable());
/*     */   }
/*     */   
/*     */   public Channel parent()
/*     */   {
/*  90 */     return this.parent;
/*     */   }
/*     */   
/*     */   public ChannelPipeline pipeline()
/*     */   {
/*  95 */     return this.pipeline;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/* 100 */     return config().getAllocator();
/*     */   }
/*     */   
/*     */   public EventLoop eventLoop()
/*     */   {
/* 105 */     EventLoop localEventLoop = this.eventLoop;
/* 106 */     if (localEventLoop == null) {
/* 107 */       throw new IllegalStateException("channel not registered to an event loop");
/*     */     }
/* 109 */     return localEventLoop;
/*     */   }
/*     */   
/*     */   public SocketAddress localAddress()
/*     */   {
/* 114 */     SocketAddress localSocketAddress = this.localAddress;
/* 115 */     if (localSocketAddress == null) {
/*     */       try {
/* 117 */         this.localAddress = (localSocketAddress = unsafe().localAddress());
/*     */       }
/*     */       catch (Throwable localThrowable) {
/* 120 */         return null;
/*     */       }
/*     */     }
/* 123 */     return localSocketAddress;
/*     */   }
/*     */   
/*     */   protected void invalidateLocalAddress() {
/* 127 */     this.localAddress = null;
/*     */   }
/*     */   
/*     */   public SocketAddress remoteAddress()
/*     */   {
/* 132 */     SocketAddress localSocketAddress = this.remoteAddress;
/* 133 */     if (localSocketAddress == null) {
/*     */       try {
/* 135 */         this.remoteAddress = (localSocketAddress = unsafe().remoteAddress());
/*     */       }
/*     */       catch (Throwable localThrowable) {
/* 138 */         return null;
/*     */       }
/*     */     }
/* 141 */     return localSocketAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void invalidateRemoteAddress()
/*     */   {
/* 148 */     this.remoteAddress = null;
/*     */   }
/*     */   
/*     */   public boolean isRegistered()
/*     */   {
/* 153 */     return this.registered;
/*     */   }
/*     */   
/*     */   public ChannelFuture bind(SocketAddress paramSocketAddress)
/*     */   {
/* 158 */     return this.pipeline.bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress)
/*     */   {
/* 163 */     return this.pipeline.connect(paramSocketAddress);
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */   {
/* 168 */     return this.pipeline.connect(paramSocketAddress1, paramSocketAddress2);
/*     */   }
/*     */   
/*     */   public ChannelFuture disconnect()
/*     */   {
/* 173 */     return this.pipeline.disconnect();
/*     */   }
/*     */   
/*     */   public ChannelFuture close()
/*     */   {
/* 178 */     return this.pipeline.close();
/*     */   }
/*     */   
/*     */   public ChannelFuture deregister()
/*     */   {
/* 183 */     return this.pipeline.deregister();
/*     */   }
/*     */   
/*     */   public Channel flush()
/*     */   {
/* 188 */     this.pipeline.flush();
/* 189 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture bind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */   {
/* 194 */     return this.pipeline.bind(paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */   {
/* 199 */     return this.pipeline.connect(paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 204 */     return this.pipeline.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture disconnect(ChannelPromise paramChannelPromise)
/*     */   {
/* 209 */     return this.pipeline.disconnect(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture close(ChannelPromise paramChannelPromise)
/*     */   {
/* 214 */     return this.pipeline.close(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture deregister(ChannelPromise paramChannelPromise)
/*     */   {
/* 219 */     return this.pipeline.deregister(paramChannelPromise);
/*     */   }
/*     */   
/*     */   public Channel read()
/*     */   {
/* 224 */     this.pipeline.read();
/* 225 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture write(Object paramObject)
/*     */   {
/* 230 */     return this.pipeline.write(paramObject);
/*     */   }
/*     */   
/*     */   public ChannelFuture write(Object paramObject, ChannelPromise paramChannelPromise)
/*     */   {
/* 235 */     return this.pipeline.write(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object paramObject)
/*     */   {
/* 240 */     return this.pipeline.writeAndFlush(paramObject);
/*     */   }
/*     */   
/*     */   public ChannelFuture writeAndFlush(Object paramObject, ChannelPromise paramChannelPromise)
/*     */   {
/* 245 */     return this.pipeline.writeAndFlush(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public ChannelPromise newPromise()
/*     */   {
/* 250 */     return new DefaultChannelPromise(this);
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise newProgressivePromise()
/*     */   {
/* 255 */     return new DefaultChannelProgressivePromise(this);
/*     */   }
/*     */   
/*     */   public ChannelFuture newSucceededFuture()
/*     */   {
/* 260 */     return this.succeededFuture;
/*     */   }
/*     */   
/*     */   public ChannelFuture newFailedFuture(Throwable paramThrowable)
/*     */   {
/* 265 */     return new FailedChannelFuture(this, null, paramThrowable);
/*     */   }
/*     */   
/*     */   public ChannelFuture closeFuture()
/*     */   {
/* 270 */     return this.closeFuture;
/*     */   }
/*     */   
/*     */   public Channel.Unsafe unsafe()
/*     */   {
/* 275 */     return this.unsafe;
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
/*     */   public final int hashCode()
/*     */   {
/* 288 */     return (int)this.hashCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/* 297 */     return this == paramObject;
/*     */   }
/*     */   
/*     */   public final int compareTo(Channel paramChannel)
/*     */   {
/* 302 */     if (this == paramChannel) {
/* 303 */       return 0;
/*     */     }
/*     */     
/* 306 */     long l = this.hashCode - paramChannel.hashCode();
/* 307 */     if (l > 0L) {
/* 308 */       return 1;
/*     */     }
/* 310 */     if (l < 0L) {
/* 311 */       return -1;
/*     */     }
/*     */     
/* 314 */     l = System.identityHashCode(this) - System.identityHashCode(paramChannel);
/* 315 */     if (l != 0L) {
/* 316 */       return (int)l;
/*     */     }
/*     */     
/*     */ 
/* 320 */     throw new Error();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 331 */     boolean bool = isActive();
/* 332 */     if ((this.strValActive == bool) && (this.strVal != null)) {
/* 333 */       return this.strVal;
/*     */     }
/*     */     
/* 336 */     SocketAddress localSocketAddress1 = remoteAddress();
/* 337 */     SocketAddress localSocketAddress2 = localAddress();
/* 338 */     if (localSocketAddress1 != null) {
/*     */       SocketAddress localSocketAddress3;
/*     */       SocketAddress localSocketAddress4;
/* 341 */       if (this.parent == null) {
/* 342 */         localSocketAddress3 = localSocketAddress2;
/* 343 */         localSocketAddress4 = localSocketAddress1;
/*     */       } else {
/* 345 */         localSocketAddress3 = localSocketAddress1;
/* 346 */         localSocketAddress4 = localSocketAddress2;
/*     */       }
/* 348 */       this.strVal = String.format("[id: 0x%08x, %s %s %s]", new Object[] { Integer.valueOf((int)this.hashCode), localSocketAddress3, bool ? "=>" : ":>", localSocketAddress4 });
/* 349 */     } else if (localSocketAddress2 != null) {
/* 350 */       this.strVal = String.format("[id: 0x%08x, %s]", new Object[] { Integer.valueOf((int)this.hashCode), localSocketAddress2 });
/*     */     } else {
/* 352 */       this.strVal = String.format("[id: 0x%08x]", new Object[] { Integer.valueOf((int)this.hashCode) });
/*     */     }
/*     */     
/* 355 */     this.strValActive = bool;
/* 356 */     return this.strVal;
/*     */   }
/*     */   
/*     */   public final ChannelPromise voidPromise()
/*     */   {
/* 361 */     return this.voidPromise;
/*     */   }
/*     */   
/*     */   final MessageSizeEstimator.Handle estimatorHandle() {
/* 365 */     if (this.estimatorHandle == null) {
/* 366 */       this.estimatorHandle = config().getMessageSizeEstimator().newHandle();
/*     */     }
/* 368 */     return this.estimatorHandle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract class AbstractUnsafe
/*     */     implements Channel.Unsafe
/*     */   {
/* 376 */     private ChannelOutboundBuffer outboundBuffer = new ChannelOutboundBuffer(AbstractChannel.this);
/*     */     private boolean inFlush0;
/*     */     
/*     */     protected AbstractUnsafe() {}
/*     */     
/* 381 */     public final ChannelOutboundBuffer outboundBuffer() { return this.outboundBuffer; }
/*     */     
/*     */ 
/*     */     public final SocketAddress localAddress()
/*     */     {
/* 386 */       return AbstractChannel.this.localAddress0();
/*     */     }
/*     */     
/*     */     public final SocketAddress remoteAddress()
/*     */     {
/* 391 */       return AbstractChannel.this.remoteAddress0();
/*     */     }
/*     */     
/*     */     public final void register(EventLoop paramEventLoop, final ChannelPromise paramChannelPromise)
/*     */     {
/* 396 */       if (paramEventLoop == null) {
/* 397 */         throw new NullPointerException("eventLoop");
/*     */       }
/* 399 */       if (AbstractChannel.this.isRegistered()) {
/* 400 */         paramChannelPromise.setFailure(new IllegalStateException("registered to an event loop already"));
/* 401 */         return;
/*     */       }
/* 403 */       if (!AbstractChannel.this.isCompatible(paramEventLoop)) {
/* 404 */         paramChannelPromise.setFailure(new IllegalStateException("incompatible event loop type: " + paramEventLoop.getClass().getName()));
/*     */         
/* 406 */         return;
/*     */       }
/*     */       
/* 409 */       AbstractChannel.this.eventLoop = paramEventLoop;
/*     */       
/* 411 */       if (paramEventLoop.inEventLoop()) {
/* 412 */         register0(paramChannelPromise);
/*     */       } else {
/*     */         try {
/* 415 */           paramEventLoop.execute(new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 418 */               AbstractChannel.AbstractUnsafe.this.register0(paramChannelPromise);
/*     */             }
/*     */           });
/*     */         } catch (Throwable localThrowable) {
/* 422 */           AbstractChannel.logger.warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", AbstractChannel.this, localThrowable);
/*     */           
/*     */ 
/* 425 */           closeForcibly();
/* 426 */           AbstractChannel.this.closeFuture.setClosed();
/* 427 */           safeSetFailure(paramChannelPromise, localThrowable);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void register0(ChannelPromise paramChannelPromise)
/*     */     {
/*     */       try
/*     */       {
/* 436 */         if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 437 */           return;
/*     */         }
/* 439 */         AbstractChannel.this.doRegister();
/* 440 */         AbstractChannel.this.registered = true;
/* 441 */         safeSetSuccess(paramChannelPromise);
/* 442 */         AbstractChannel.this.pipeline.fireChannelRegistered();
/* 443 */         if (AbstractChannel.this.isActive()) {
/* 444 */           AbstractChannel.this.pipeline.fireChannelActive();
/*     */         }
/*     */       }
/*     */       catch (Throwable localThrowable) {
/* 448 */         closeForcibly();
/* 449 */         AbstractChannel.this.closeFuture.setClosed();
/* 450 */         safeSetFailure(paramChannelPromise, localThrowable);
/*     */       }
/*     */     }
/*     */     
/*     */     public final void bind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */     {
/* 456 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/* 457 */         return;
/*     */       }
/*     */       
/*     */ 
/* 461 */       if ((!PlatformDependent.isWindows()) && (!PlatformDependent.isRoot()) && (Boolean.TRUE.equals(AbstractChannel.this.config().getOption(ChannelOption.SO_BROADCAST))) && ((paramSocketAddress instanceof InetSocketAddress)) && (!((InetSocketAddress)paramSocketAddress).getAddress().isAnyLocalAddress()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 467 */         AbstractChannel.logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + paramSocketAddress + ") anyway as requested.");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 473 */       boolean bool = AbstractChannel.this.isActive();
/*     */       try {
/* 475 */         AbstractChannel.this.doBind(paramSocketAddress);
/*     */       } catch (Throwable localThrowable) {
/* 477 */         safeSetFailure(paramChannelPromise, localThrowable);
/* 478 */         closeIfClosed();
/* 479 */         return;
/*     */       }
/*     */       
/* 482 */       if ((!bool) && (AbstractChannel.this.isActive())) {
/* 483 */         invokeLater(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 486 */             AbstractChannel.this.pipeline.fireChannelActive();
/*     */           }
/*     */         });
/*     */       }
/*     */       
/* 491 */       safeSetSuccess(paramChannelPromise);
/*     */     }
/*     */     
/*     */     public final void disconnect(ChannelPromise paramChannelPromise)
/*     */     {
/* 496 */       if (!paramChannelPromise.setUncancellable()) {
/* 497 */         return;
/*     */       }
/*     */       
/* 500 */       boolean bool = AbstractChannel.this.isActive();
/*     */       try {
/* 502 */         AbstractChannel.this.doDisconnect();
/*     */       } catch (Throwable localThrowable) {
/* 504 */         safeSetFailure(paramChannelPromise, localThrowable);
/* 505 */         closeIfClosed();
/* 506 */         return;
/*     */       }
/*     */       
/* 509 */       if ((bool) && (!AbstractChannel.this.isActive())) {
/* 510 */         invokeLater(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 513 */             AbstractChannel.this.pipeline.fireChannelInactive();
/*     */           }
/*     */         });
/*     */       }
/*     */       
/* 518 */       safeSetSuccess(paramChannelPromise);
/* 519 */       closeIfClosed();
/*     */     }
/*     */     
/*     */     public final void close(final ChannelPromise paramChannelPromise)
/*     */     {
/* 524 */       if (!paramChannelPromise.setUncancellable()) {
/* 525 */         return;
/*     */       }
/*     */       
/* 528 */       if (this.inFlush0) {
/* 529 */         invokeLater(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 532 */             AbstractChannel.AbstractUnsafe.this.close(paramChannelPromise);
/*     */           }
/* 534 */         });
/* 535 */         return;
/*     */       }
/*     */       
/* 538 */       if (AbstractChannel.this.closeFuture.isDone())
/*     */       {
/* 540 */         safeSetSuccess(paramChannelPromise);
/* 541 */         return;
/*     */       }
/*     */       
/* 544 */       boolean bool = AbstractChannel.this.isActive();
/* 545 */       ChannelOutboundBuffer localChannelOutboundBuffer = this.outboundBuffer;
/* 546 */       this.outboundBuffer = null;
/*     */       try
/*     */       {
/* 549 */         AbstractChannel.this.doClose();
/* 550 */         AbstractChannel.this.closeFuture.setClosed();
/* 551 */         safeSetSuccess(paramChannelPromise);
/*     */       } catch (Throwable localThrowable) {
/* 553 */         AbstractChannel.this.closeFuture.setClosed();
/* 554 */         safeSetFailure(paramChannelPromise, localThrowable);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 559 */         localChannelOutboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 560 */         localChannelOutboundBuffer.close(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */       }
/*     */       finally {
/* 563 */         if ((bool) && (!AbstractChannel.this.isActive())) {
/* 564 */           invokeLater(new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 567 */               AbstractChannel.this.pipeline.fireChannelInactive();
/*     */             }
/*     */           });
/*     */         }
/*     */         
/* 572 */         deregister(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     public final void closeForcibly()
/*     */     {
/*     */       try {
/* 579 */         AbstractChannel.this.doClose();
/*     */       } catch (Exception localException) {
/* 581 */         AbstractChannel.logger.warn("Failed to close a channel.", localException);
/*     */       }
/*     */     }
/*     */     
/*     */     public final void deregister(ChannelPromise paramChannelPromise)
/*     */     {
/* 587 */       if (!paramChannelPromise.setUncancellable()) {
/* 588 */         return;
/*     */       }
/*     */       
/* 591 */       if (!AbstractChannel.this.registered) {
/* 592 */         safeSetSuccess(paramChannelPromise);
/* 593 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 597 */         AbstractChannel.this.doDeregister();
/*     */       } catch (Throwable localThrowable) {
/* 599 */         AbstractChannel.logger.warn("Unexpected exception occurred while deregistering a channel.", localThrowable);
/*     */       } finally {
/* 601 */         if (AbstractChannel.this.registered) {
/* 602 */           AbstractChannel.this.registered = false;
/* 603 */           invokeLater(new OneTimeTask()
/*     */           {
/*     */             public void run() {
/* 606 */               AbstractChannel.this.pipeline.fireChannelUnregistered();
/*     */             }
/* 608 */           });
/* 609 */           safeSetSuccess(paramChannelPromise);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 614 */           safeSetSuccess(paramChannelPromise);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public final void beginRead()
/*     */     {
/* 621 */       if (!AbstractChannel.this.isActive()) {
/* 622 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 626 */         AbstractChannel.this.doBeginRead();
/*     */       } catch (Exception localException) {
/* 628 */         invokeLater(new OneTimeTask()
/*     */         {
/*     */           public void run() {
/* 631 */             AbstractChannel.this.pipeline.fireExceptionCaught(localException);
/*     */           }
/* 633 */         });
/* 634 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */     
/*     */     public final void write(Object paramObject, ChannelPromise paramChannelPromise)
/*     */     {
/* 640 */       ChannelOutboundBuffer localChannelOutboundBuffer = this.outboundBuffer;
/* 641 */       if (localChannelOutboundBuffer == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 646 */         safeSetFailure(paramChannelPromise, AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */         
/* 648 */         ReferenceCountUtil.release(paramObject); return;
/*     */       }
/*     */       
/*     */       int i;
/*     */       try
/*     */       {
/* 654 */         paramObject = AbstractChannel.this.filterOutboundMessage(paramObject);
/* 655 */         i = AbstractChannel.this.estimatorHandle().size(paramObject);
/* 656 */         if (i < 0) {
/* 657 */           i = 0;
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 660 */         safeSetFailure(paramChannelPromise, localThrowable);
/* 661 */         ReferenceCountUtil.release(paramObject);
/* 662 */         return;
/*     */       }
/*     */       
/* 665 */       localChannelOutboundBuffer.addMessage(paramObject, i, paramChannelPromise);
/*     */     }
/*     */     
/*     */     public final void flush()
/*     */     {
/* 670 */       ChannelOutboundBuffer localChannelOutboundBuffer = this.outboundBuffer;
/* 671 */       if (localChannelOutboundBuffer == null) {
/* 672 */         return;
/*     */       }
/*     */       
/* 675 */       localChannelOutboundBuffer.addFlush();
/* 676 */       flush0();
/*     */     }
/*     */     
/*     */     protected void flush0() {
/* 680 */       if (this.inFlush0)
/*     */       {
/* 682 */         return;
/*     */       }
/*     */       
/* 685 */       ChannelOutboundBuffer localChannelOutboundBuffer = this.outboundBuffer;
/* 686 */       if ((localChannelOutboundBuffer == null) || (localChannelOutboundBuffer.isEmpty())) {
/* 687 */         return;
/*     */       }
/*     */       
/* 690 */       this.inFlush0 = true;
/*     */       
/*     */ 
/* 693 */       if (!AbstractChannel.this.isActive()) {
/*     */         try {
/* 695 */           if (AbstractChannel.this.isOpen()) {
/* 696 */             localChannelOutboundBuffer.failFlushed(AbstractChannel.NOT_YET_CONNECTED_EXCEPTION);
/*     */           } else {
/* 698 */             localChannelOutboundBuffer.failFlushed(AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/*     */           }
/*     */         } finally {
/* 701 */           this.inFlush0 = false;
/*     */         }
/* 703 */         return;
/*     */       }
/*     */       try
/*     */       {
/* 707 */         AbstractChannel.this.doWrite(localChannelOutboundBuffer);
/*     */       } catch (Throwable localThrowable) {
/* 709 */         localChannelOutboundBuffer.failFlushed(localThrowable);
/* 710 */         if (((localThrowable instanceof IOException)) && (AbstractChannel.this.config().isAutoClose())) {
/* 711 */           close(voidPromise());
/*     */         }
/*     */       } finally {
/* 714 */         this.inFlush0 = false;
/*     */       }
/*     */     }
/*     */     
/*     */     public final ChannelPromise voidPromise()
/*     */     {
/* 720 */       return AbstractChannel.this.unsafeVoidPromise;
/*     */     }
/*     */     
/*     */     protected final boolean ensureOpen(ChannelPromise paramChannelPromise) {
/* 724 */       if (AbstractChannel.this.isOpen()) {
/* 725 */         return true;
/*     */       }
/*     */       
/* 728 */       safeSetFailure(paramChannelPromise, AbstractChannel.CLOSED_CHANNEL_EXCEPTION);
/* 729 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected final void safeSetSuccess(ChannelPromise paramChannelPromise)
/*     */     {
/* 736 */       if ((!(paramChannelPromise instanceof VoidChannelPromise)) && (!paramChannelPromise.trySuccess())) {
/* 737 */         AbstractChannel.logger.warn("Failed to mark a promise as success because it is done already: {}", paramChannelPromise);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected final void safeSetFailure(ChannelPromise paramChannelPromise, Throwable paramThrowable)
/*     */     {
/* 745 */       if ((!(paramChannelPromise instanceof VoidChannelPromise)) && (!paramChannelPromise.tryFailure(paramThrowable))) {
/* 746 */         AbstractChannel.logger.warn("Failed to mark a promise as failure because it's done already: {}", paramChannelPromise, paramThrowable);
/*     */       }
/*     */     }
/*     */     
/*     */     protected final void closeIfClosed() {
/* 751 */       if (AbstractChannel.this.isOpen()) {
/* 752 */         return;
/*     */       }
/* 754 */       close(voidPromise());
/*     */     }
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
/*     */     private void invokeLater(Runnable paramRunnable)
/*     */     {
/*     */       try
/*     */       {
/* 770 */         AbstractChannel.this.eventLoop().execute(paramRunnable);
/*     */       } catch (RejectedExecutionException localRejectedExecutionException) {
/* 772 */         AbstractChannel.logger.warn("Can't invoke task later as EventLoop rejected it", localRejectedExecutionException);
/*     */       }
/*     */     }
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
/* 840 */   protected Object filterOutboundMessage(Object paramObject)
/* 840 */     throws Exception { return paramObject; }
/*     */   
/*     */   protected abstract AbstractUnsafe newUnsafe();
/*     */   
/*     */   protected abstract boolean isCompatible(EventLoop paramEventLoop);
/*     */   
/* 846 */   static final class CloseFuture extends DefaultChannelPromise { CloseFuture(AbstractChannel paramAbstractChannel) { super(); }
/*     */     
/*     */ 
/*     */     public ChannelPromise setSuccess()
/*     */     {
/* 851 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public ChannelPromise setFailure(Throwable paramThrowable)
/*     */     {
/* 856 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public boolean trySuccess()
/*     */     {
/* 861 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     public boolean tryFailure(Throwable paramThrowable)
/*     */     {
/* 866 */       throw new IllegalStateException();
/*     */     }
/*     */     
/*     */     boolean setClosed() {
/* 870 */       return super.trySuccess();
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract SocketAddress localAddress0();
/*     */   
/*     */   protected abstract SocketAddress remoteAddress0();
/*     */   
/*     */   protected void doRegister()
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */   protected abstract void doBind(SocketAddress paramSocketAddress)
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void doDisconnect()
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void doClose()
/*     */     throws Exception;
/*     */   
/*     */   protected void doDeregister()
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */   protected abstract void doBeginRead()
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\AbstractChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */