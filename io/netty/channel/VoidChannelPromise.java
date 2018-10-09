/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.AbstractFuture;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ 
/*     */ final class VoidChannelPromise
/*     */   extends AbstractFuture<Void>
/*     */   implements ChannelPromise
/*     */ {
/*     */   private final Channel channel;
/*     */   private final boolean fireException;
/*     */   
/*     */   VoidChannelPromise(Channel paramChannel, boolean paramBoolean)
/*     */   {
/*  35 */     if (paramChannel == null) {
/*  36 */       throw new NullPointerException("channel");
/*     */     }
/*  38 */     this.channel = paramChannel;
/*  39 */     this.fireException = paramBoolean;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/*  44 */     fail();
/*  45 */     return this;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/*  50 */     fail();
/*  51 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public VoidChannelPromise removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/*  57 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public VoidChannelPromise removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise await() throws InterruptedException
/*     */   {
/*  68 */     if (Thread.interrupted()) {
/*  69 */       throw new InterruptedException();
/*     */     }
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  76 */     fail();
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public boolean await(long paramLong)
/*     */   {
/*  82 */     fail();
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise awaitUninterruptibly()
/*     */   {
/*  88 */     fail();
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  94 */     fail();
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public boolean awaitUninterruptibly(long paramLong)
/*     */   {
/* 100 */     fail();
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public Channel channel()
/*     */   {
/* 106 */     return this.channel;
/*     */   }
/*     */   
/*     */   public boolean isDone()
/*     */   {
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isSuccess()
/*     */   {
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setUncancellable()
/*     */   {
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCancellable()
/*     */   {
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCancelled()
/*     */   {
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public Throwable cause()
/*     */   {
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise sync()
/*     */   {
/* 141 */     fail();
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise syncUninterruptibly()
/*     */   {
/* 147 */     fail();
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise setFailure(Throwable paramThrowable) {
/* 152 */     fireException(paramThrowable);
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   public VoidChannelPromise setSuccess()
/*     */   {
/* 158 */     return this;
/*     */   }
/*     */   
/*     */   public boolean tryFailure(Throwable paramThrowable)
/*     */   {
/* 163 */     fireException(paramThrowable);
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public boolean cancel(boolean paramBoolean)
/*     */   {
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public boolean trySuccess()
/*     */   {
/* 174 */     return false;
/*     */   }
/*     */   
/*     */   private static void fail() {
/* 178 */     throw new IllegalStateException("void future");
/*     */   }
/*     */   
/*     */   public VoidChannelPromise setSuccess(Void paramVoid)
/*     */   {
/* 183 */     return this;
/*     */   }
/*     */   
/*     */   public boolean trySuccess(Void paramVoid)
/*     */   {
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public Void getNow()
/*     */   {
/* 193 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fireException(Throwable paramThrowable)
/*     */   {
/* 201 */     if ((this.fireException) && (this.channel.isRegistered())) {
/* 202 */       this.channel.pipeline().fireExceptionCaught(paramThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\VoidChannelPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */