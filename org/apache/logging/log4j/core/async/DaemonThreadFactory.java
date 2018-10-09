/*    */ package org.apache.logging.log4j.core.async;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DaemonThreadFactory
/*    */   implements ThreadFactory
/*    */ {
/*    */   final ThreadGroup group;
/* 28 */   final AtomicInteger threadNumber = new AtomicInteger(1);
/*    */   final String threadNamePrefix;
/*    */   
/*    */   public DaemonThreadFactory(String paramString) {
/* 32 */     this.threadNamePrefix = paramString;
/* 33 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 34 */     this.group = (localSecurityManager != null ? localSecurityManager.getThreadGroup() : Thread.currentThread().getThreadGroup());
/*    */   }
/*    */   
/*    */ 
/*    */   public Thread newThread(Runnable paramRunnable)
/*    */   {
/* 40 */     Thread localThread = new Thread(this.group, paramRunnable, this.threadNamePrefix + this.threadNumber.getAndIncrement(), 0L);
/*    */     
/* 42 */     if (!localThread.isDaemon()) {
/* 43 */       localThread.setDaemon(true);
/*    */     }
/* 45 */     if (localThread.getPriority() != 5) {
/* 46 */       localThread.setPriority(5);
/*    */     }
/* 48 */     return localThread;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\DaemonThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */