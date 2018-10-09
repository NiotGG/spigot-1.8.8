/*    */ package org.apache.logging.log4j.core.appender.rolling;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*    */ @Plugin(name="TimeBasedTriggeringPolicy", category="Core", printObject=true)
/*    */ public final class TimeBasedTriggeringPolicy
/*    */   implements TriggeringPolicy
/*    */ {
/*    */   private long nextRollover;
/*    */   private final int interval;
/*    */   private final boolean modulate;
/*    */   private RollingFileManager manager;
/*    */   
/*    */   private TimeBasedTriggeringPolicy(int paramInt, boolean paramBoolean)
/*    */   {
/* 38 */     this.interval = paramInt;
/* 39 */     this.modulate = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void initialize(RollingFileManager paramRollingFileManager)
/*    */   {
/* 48 */     this.manager = paramRollingFileManager;
/* 49 */     this.nextRollover = paramRollingFileManager.getPatternProcessor().getNextTime(paramRollingFileManager.getFileTime(), this.interval, this.modulate);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isTriggeringEvent(LogEvent paramLogEvent)
/*    */   {
/* 59 */     if (this.manager.getFileSize() == 0L) {
/* 60 */       return false;
/*    */     }
/* 62 */     long l = System.currentTimeMillis();
/* 63 */     if (l > this.nextRollover) {
/* 64 */       this.nextRollover = this.manager.getPatternProcessor().getNextTime(l, this.interval, this.modulate);
/* 65 */       return true;
/*    */     }
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return "TimeBasedTriggeringPolicy";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static TimeBasedTriggeringPolicy createPolicy(@PluginAttribute("interval") String paramString1, @PluginAttribute("modulate") String paramString2)
/*    */   {
/* 85 */     int i = Integers.parseInt(paramString1, 1);
/* 86 */     boolean bool = Boolean.parseBoolean(paramString2);
/* 87 */     return new TimeBasedTriggeringPolicy(i, bool);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\TimeBasedTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */