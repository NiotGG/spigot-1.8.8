/*     */ package io.netty.util.concurrent;
/*     */ 
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
/*     */ public final class ImmediateEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  24 */   public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
/*     */   
/*  26 */   private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EventExecutorGroup parent()
/*     */   {
/*  35 */     return null;
/*     */   }
/*     */   
/*     */   public boolean inEventLoop()
/*     */   {
/*  40 */     return true;
/*     */   }
/*     */   
/*     */   public boolean inEventLoop(Thread paramThread)
/*     */   {
/*  45 */     return true;
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/*  50 */     return terminationFuture();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/*  55 */     return this.terminationFuture;
/*     */   }
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public void shutdown() {}
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  79 */     return false;
/*     */   }
/*     */   
/*     */   public void execute(Runnable paramRunnable)
/*     */   {
/*  84 */     if (paramRunnable == null) {
/*  85 */       throw new NullPointerException("command");
/*     */     }
/*  87 */     paramRunnable.run();
/*     */   }
/*     */   
/*     */   public <V> Promise<V> newPromise()
/*     */   {
/*  92 */     return new ImmediatePromise(this);
/*     */   }
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise()
/*     */   {
/*  97 */     return new ImmediateProgressivePromise(this);
/*     */   }
/*     */   
/*     */   static class ImmediatePromise<V> extends DefaultPromise<V> {
/*     */     ImmediatePromise(EventExecutor paramEventExecutor) {
/* 102 */       super();
/*     */     }
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */   
/*     */   static class ImmediateProgressivePromise<V>
/*     */     extends DefaultProgressivePromise<V>
/*     */   {
/*     */     ImmediateProgressivePromise(EventExecutor paramEventExecutor)
/*     */     {
/* 113 */       super();
/*     */     }
/*     */     
/*     */     protected void checkDeadLock() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\ImmediateEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */