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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CachedClock
/*    */   implements Clock
/*    */ {
/*    */   private static final int UPDATE_THRESHOLD = 1023;
/* 31 */   private static CachedClock instance = new CachedClock();
/* 32 */   private volatile long millis = System.currentTimeMillis();
/* 33 */   private volatile short count = 0;
/* 34 */   private final Thread updater = new Thread("Clock Updater Thread")
/*    */   {
/*    */     public void run() {
/*    */       for (;;) {
/* 38 */         long l = System.currentTimeMillis();
/* 39 */         CachedClock.this.millis = l;
/*    */         
/*    */ 
/* 42 */         LockSupport.parkNanos(1000000L);
/*    */       }
/*    */     }
/*    */   };
/*    */   
/*    */   private CachedClock() {
/* 48 */     this.updater.setDaemon(true);
/* 49 */     this.updater.start();
/*    */   }
/*    */   
/*    */   public static CachedClock instance() {
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long currentTimeMillis()
/*    */   {
/* 69 */     if (((this.count = (short)(this.count + 1)) & 0x3FF) == 1023) {
/* 70 */       this.millis = System.currentTimeMillis();
/*    */     }
/* 72 */     return this.millis;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\CachedClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */