/*     */ package io.netty.util.internal.chmv8;
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
/*     */ public class ForkJoinWorkerThread
/*     */   extends Thread
/*     */ {
/*     */   final ForkJoinPool pool;
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
/*     */   final ForkJoinPool.WorkQueue workQueue;
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
/*     */   protected ForkJoinWorkerThread(ForkJoinPool paramForkJoinPool)
/*     */   {
/*  66 */     super("aForkJoinWorkerThread");
/*  67 */     this.pool = paramForkJoinPool;
/*  68 */     this.workQueue = paramForkJoinPool.registerWorker(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ForkJoinPool getPool()
/*     */   {
/*  77 */     return this.pool;
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
/*     */   public int getPoolIndex()
/*     */   {
/*  91 */     return this.workQueue.poolIndex >>> 1;
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
/*     */   protected void onStart() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void onTermination(Throwable paramThrowable) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 123 */     Object localObject1 = null;
/*     */     try {
/* 125 */       onStart();
/* 126 */       this.pool.runWorker(this.workQueue);
/*     */     } catch (Throwable localThrowable2) {
/* 128 */       localObject1 = localThrowable2;
/*     */     } finally {
/*     */       try {
/* 131 */         onTermination((Throwable)localObject1);
/*     */       } catch (Throwable localThrowable4) {
/* 133 */         if (localObject1 == null)
/* 134 */           localObject1 = localThrowable4;
/*     */       } finally {
/* 136 */         this.pool.deregisterWorker(this, (Throwable)localObject1);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\chmv8\ForkJoinWorkerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */