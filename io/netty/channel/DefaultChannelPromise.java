/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.DefaultPromise;
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
/*     */ public class DefaultChannelPromise
/*     */   extends DefaultPromise<Void>
/*     */   implements ChannelPromise, ChannelFlushPromiseNotifier.FlushCheckpoint
/*     */ {
/*     */   private final Channel channel;
/*     */   private long checkpoint;
/*     */   
/*     */   public DefaultChannelPromise(Channel paramChannel)
/*     */   {
/*  40 */     this.channel = paramChannel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultChannelPromise(Channel paramChannel, EventExecutor paramEventExecutor)
/*     */   {
/*  50 */     super(paramEventExecutor);
/*  51 */     this.channel = paramChannel;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor()
/*     */   {
/*  56 */     EventExecutor localEventExecutor = super.executor();
/*  57 */     if (localEventExecutor == null) {
/*  58 */       return channel().eventLoop();
/*     */     }
/*  60 */     return localEventExecutor;
/*     */   }
/*     */   
/*     */ 
/*     */   public Channel channel()
/*     */   {
/*  66 */     return this.channel;
/*     */   }
/*     */   
/*     */   public ChannelPromise setSuccess()
/*     */   {
/*  71 */     return setSuccess(null);
/*     */   }
/*     */   
/*     */   public ChannelPromise setSuccess(Void paramVoid)
/*     */   {
/*  76 */     super.setSuccess(paramVoid);
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public boolean trySuccess()
/*     */   {
/*  82 */     return trySuccess(null);
/*     */   }
/*     */   
/*     */   public ChannelPromise setFailure(Throwable paramThrowable)
/*     */   {
/*  87 */     super.setFailure(paramThrowable);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/*  93 */     super.addListener(paramGenericFutureListener);
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/*  99 */     super.addListeners(paramVarArgs);
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/* 105 */     super.removeListener(paramGenericFutureListener);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/* 111 */     super.removeListeners(paramVarArgs);
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise sync() throws InterruptedException
/*     */   {
/* 117 */     super.sync();
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise syncUninterruptibly()
/*     */   {
/* 123 */     super.syncUninterruptibly();
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise await() throws InterruptedException
/*     */   {
/* 129 */     super.await();
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelPromise awaitUninterruptibly()
/*     */   {
/* 135 */     super.awaitUninterruptibly();
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public long flushCheckpoint()
/*     */   {
/* 141 */     return this.checkpoint;
/*     */   }
/*     */   
/*     */   public void flushCheckpoint(long paramLong)
/*     */   {
/* 146 */     this.checkpoint = paramLong;
/*     */   }
/*     */   
/*     */   public ChannelPromise promise()
/*     */   {
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   protected void checkDeadLock()
/*     */   {
/* 156 */     if (channel().isRegistered()) {
/* 157 */       super.checkDeadLock();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultChannelPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */