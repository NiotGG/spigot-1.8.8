/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.DefaultProgressivePromise;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ public class DefaultChannelProgressivePromise
/*     */   extends DefaultProgressivePromise<Void>
/*     */   implements ChannelProgressivePromise, ChannelFlushPromiseNotifier.FlushCheckpoint
/*     */ {
/*     */   private final Channel channel;
/*     */   private long checkpoint;
/*     */   
/*     */   public DefaultChannelProgressivePromise(Channel paramChannel)
/*     */   {
/*  42 */     this.channel = paramChannel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultChannelProgressivePromise(Channel paramChannel, EventExecutor paramEventExecutor)
/*     */   {
/*  52 */     super(paramEventExecutor);
/*  53 */     this.channel = paramChannel;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor()
/*     */   {
/*  58 */     EventExecutor localEventExecutor = super.executor();
/*  59 */     if (localEventExecutor == null) {
/*  60 */       return channel().eventLoop();
/*     */     }
/*  62 */     return localEventExecutor;
/*     */   }
/*     */   
/*     */ 
/*     */   public Channel channel()
/*     */   {
/*  68 */     return this.channel;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise setSuccess()
/*     */   {
/*  73 */     return setSuccess(null);
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise setSuccess(Void paramVoid)
/*     */   {
/*  78 */     super.setSuccess(paramVoid);
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public boolean trySuccess()
/*     */   {
/*  84 */     return trySuccess(null);
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise setFailure(Throwable paramThrowable)
/*     */   {
/*  89 */     super.setFailure(paramThrowable);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise setProgress(long paramLong1, long paramLong2)
/*     */   {
/*  95 */     super.setProgress(paramLong1, paramLong2);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/* 101 */     super.addListener(paramGenericFutureListener);
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/* 107 */     super.addListeners(paramVarArgs);
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/* 113 */     super.removeListener(paramGenericFutureListener);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelProgressivePromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/* 120 */     super.removeListeners(paramVarArgs);
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise sync() throws InterruptedException
/*     */   {
/* 126 */     super.sync();
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise syncUninterruptibly()
/*     */   {
/* 132 */     super.syncUninterruptibly();
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise await() throws InterruptedException
/*     */   {
/* 138 */     super.await();
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise awaitUninterruptibly()
/*     */   {
/* 144 */     super.awaitUninterruptibly();
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public long flushCheckpoint()
/*     */   {
/* 150 */     return this.checkpoint;
/*     */   }
/*     */   
/*     */   public void flushCheckpoint(long paramLong)
/*     */   {
/* 155 */     this.checkpoint = paramLong;
/*     */   }
/*     */   
/*     */   public ChannelProgressivePromise promise()
/*     */   {
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   protected void checkDeadLock()
/*     */   {
/* 165 */     if (channel().isRegistered()) {
/* 166 */       super.checkDeadLock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultChannelProgressivePromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */