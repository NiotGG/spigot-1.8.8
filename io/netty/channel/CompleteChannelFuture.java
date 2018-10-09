/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.concurrent.CompleteFuture;
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
/*     */ abstract class CompleteChannelFuture
/*     */   extends CompleteFuture<Void>
/*     */   implements ChannelFuture
/*     */ {
/*     */   private final Channel channel;
/*     */   
/*     */   protected CompleteChannelFuture(Channel paramChannel, EventExecutor paramEventExecutor)
/*     */   {
/*  37 */     super(paramEventExecutor);
/*  38 */     if (paramChannel == null) {
/*  39 */       throw new NullPointerException("channel");
/*     */     }
/*  41 */     this.channel = paramChannel;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor()
/*     */   {
/*  46 */     EventExecutor localEventExecutor = super.executor();
/*  47 */     if (localEventExecutor == null) {
/*  48 */       return channel().eventLoop();
/*     */     }
/*  50 */     return localEventExecutor;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/*  56 */     super.addListener(paramGenericFutureListener);
/*  57 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/*  62 */     super.addListeners(paramVarArgs);
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> paramGenericFutureListener)
/*     */   {
/*  68 */     super.removeListener(paramGenericFutureListener);
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... paramVarArgs)
/*     */   {
/*  74 */     super.removeListeners(paramVarArgs);
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture syncUninterruptibly()
/*     */   {
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture sync() throws InterruptedException
/*     */   {
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture await() throws InterruptedException
/*     */   {
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public ChannelFuture awaitUninterruptibly()
/*     */   {
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public Channel channel()
/*     */   {
/* 100 */     return this.channel;
/*     */   }
/*     */   
/*     */   public Void getNow()
/*     */   {
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\CompleteChannelFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */