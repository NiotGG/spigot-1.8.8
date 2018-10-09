/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.RuntimeMXBean;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name="RelativeTimePatternConverter", category="Converter")
/*    */ @ConverterKeys({"r", "relative"})
/*    */ public class RelativeTimePatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 33 */   private long lastTimestamp = Long.MIN_VALUE;
/* 34 */   private final long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
/*    */   
/*    */   private String relative;
/*    */   
/*    */ 
/*    */   public RelativeTimePatternConverter()
/*    */   {
/* 41 */     super("Time", "time");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static RelativeTimePatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 52 */     return new RelativeTimePatternConverter();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 60 */     long l = paramLogEvent.getMillis();
/*    */     
/* 62 */     synchronized (this) {
/* 63 */       if (l != this.lastTimestamp) {
/* 64 */         this.lastTimestamp = l;
/* 65 */         this.relative = Long.toString(l - this.startTime);
/*    */       }
/*    */     }
/* 68 */     paramStringBuilder.append(this.relative);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\RelativeTimePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */