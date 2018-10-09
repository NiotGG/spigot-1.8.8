/*     */ package com.avaje.ebeaninternal.server.lib;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.Monitor;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public final class DaemonThreadPool
/*     */   extends ThreadPoolExecutor
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(DaemonThreadPool.class.getName());
/*     */   
/*  40 */   private final Monitor monitor = new Monitor();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String namePrefix;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int shutdownWaitSeconds;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DaemonThreadPool(int coreSize, long keepAliveSecs, int shutdownWaitSeconds, String namePrefix)
/*     */   {
/*  58 */     super(coreSize, coreSize, keepAliveSecs, TimeUnit.SECONDS, new LinkedBlockingQueue(), new DaemonThreadFactory(namePrefix));
/*  59 */     this.shutdownWaitSeconds = shutdownWaitSeconds;
/*  60 */     this.namePrefix = namePrefix;
/*     */     
/*     */ 
/*  63 */     Runtime.getRuntime().addShutdownHook(new ShutdownHook(null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/*  74 */     synchronized (this.monitor) {
/*  75 */       if (super.isShutdown()) {
/*  76 */         logger.fine("... DaemonThreadPool[" + this.namePrefix + "] already shut down");
/*  77 */         return;
/*     */       }
/*     */       try {
/*  80 */         logger.fine("DaemonThreadPool[" + this.namePrefix + "] shutting down...");
/*  81 */         super.shutdown();
/*  82 */         if (!super.awaitTermination(this.shutdownWaitSeconds, TimeUnit.SECONDS)) {
/*  83 */           logger.info("DaemonThreadPool[" + this.namePrefix + "] shut down timeout exceeded. Terminating running threads.");
/*  84 */           super.shutdownNow();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/*  88 */         String msg = "Error during shutdown of DaemonThreadPool[" + this.namePrefix + "]";
/*  89 */         logger.log(Level.SEVERE, msg, e);
/*  90 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShutdownHook extends Thread
/*     */   {
/*     */     private ShutdownHook() {}
/*     */     
/*     */     public void run()
/*     */     {
/* 101 */       DaemonThreadPool.this.shutdown();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\DaemonThreadPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */