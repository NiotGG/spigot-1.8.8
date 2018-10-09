/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.util.concurrent.locks.LockSupport;
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
/*    */ public final class CoarseCachedClock
/*    */   implements Clock
/*    */ {
/* 26 */   private static CoarseCachedClock instance = new CoarseCachedClock();
/* 27 */   private volatile long millis = System.currentTimeMillis();
/*    */   
/* 29 */   private final Thread updater = new Thread("Clock Updater Thread")
/*    */   {
/*    */     public void run() {
/*    */       for (;;) {
/* 33 */         long l = System.currentTimeMillis();
/* 34 */         CoarseCachedClock.this.millis = l;
/*    */         
/*    */ 
/* 37 */         LockSupport.parkNanos(1000000L);
/*    */       }
/*    */     }
/*    */   };
/*    */   
/*    */   private CoarseCachedClock() {
/* 43 */     this.updater.setDaemon(true);
/* 44 */     this.updater.start();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static CoarseCachedClock instance()
/*    */   {
/* 53 */     return instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long currentTimeMillis()
/*    */   {
/* 65 */     return this.millis;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\CoarseCachedClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */