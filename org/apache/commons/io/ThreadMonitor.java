/*     */ package org.apache.commons.io;
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
/*     */ class ThreadMonitor
/*     */   implements Runnable
/*     */ {
/*     */   private final Thread thread;
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
/*     */   private final long timeout;
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
/*     */   public static Thread start(long paramLong)
/*     */   {
/*  55 */     return start(Thread.currentThread(), paramLong);
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
/*     */   public static Thread start(Thread paramThread, long paramLong)
/*     */   {
/*  68 */     Thread localThread = null;
/*  69 */     if (paramLong > 0L) {
/*  70 */       ThreadMonitor localThreadMonitor = new ThreadMonitor(paramThread, paramLong);
/*  71 */       localThread = new Thread(localThreadMonitor, ThreadMonitor.class.getSimpleName());
/*  72 */       localThread.setDaemon(true);
/*  73 */       localThread.start();
/*     */     }
/*  75 */     return localThread;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void stop(Thread paramThread)
/*     */   {
/*  84 */     if (paramThread != null) {
/*  85 */       paramThread.interrupt();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ThreadMonitor(Thread paramThread, long paramLong)
/*     */   {
/*  96 */     this.thread = paramThread;
/*  97 */     this.timeout = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/* 108 */       Thread.sleep(this.timeout);
/* 109 */       this.thread.interrupt();
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\ThreadMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */