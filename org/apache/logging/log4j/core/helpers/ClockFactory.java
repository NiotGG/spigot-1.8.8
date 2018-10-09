/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ 
/*    */ public final class ClockFactory
/*    */ {
/*    */   public static final String PROPERTY_NAME = "log4j.Clock";
/* 31 */   private static final StatusLogger LOGGER = ;
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
/*    */ 
/*    */   public static Clock getClock()
/*    */   {
/* 58 */     return createClock();
/*    */   }
/*    */   
/*    */   private static Clock createClock() {
/* 62 */     String str1 = System.getProperty("log4j.Clock");
/* 63 */     if ((str1 == null) || ("SystemClock".equals(str1))) {
/* 64 */       LOGGER.debug("Using default SystemClock for timestamps");
/* 65 */       return new SystemClock();
/*    */     }
/* 67 */     if ((CachedClock.class.getName().equals(str1)) || ("CachedClock".equals(str1)))
/*    */     {
/* 69 */       LOGGER.debug("Using specified CachedClock for timestamps");
/* 70 */       return CachedClock.instance();
/*    */     }
/* 72 */     if ((CoarseCachedClock.class.getName().equals(str1)) || ("CoarseCachedClock".equals(str1)))
/*    */     {
/* 74 */       LOGGER.debug("Using specified CoarseCachedClock for timestamps");
/* 75 */       return CoarseCachedClock.instance();
/*    */     }
/*    */     try {
/* 78 */       Clock localClock = (Clock)Class.forName(str1).newInstance();
/* 79 */       LOGGER.debug("Using {} for timestamps", new Object[] { str1 });
/* 80 */       return localClock;
/*    */     } catch (Exception localException) {
/* 82 */       String str2 = "Could not create {}: {}, using default SystemClock for timestamps";
/* 83 */       LOGGER.error("Could not create {}: {}, using default SystemClock for timestamps", new Object[] { str1, localException }); }
/* 84 */     return new SystemClock();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\ClockFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */