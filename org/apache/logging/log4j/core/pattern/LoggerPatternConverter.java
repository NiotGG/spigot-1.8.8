/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
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
/*    */ 
/*    */ @Plugin(name="LoggerPatternConverter", category="Converter")
/*    */ @ConverterKeys({"c", "logger"})
/*    */ public final class LoggerPatternConverter
/*    */   extends NamePatternConverter
/*    */ {
/* 32 */   private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private LoggerPatternConverter(String[] paramArrayOfString)
/*    */   {
/* 41 */     super("Logger", "logger", paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static LoggerPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 52 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 53 */       return INSTANCE;
/*    */     }
/*    */     
/* 56 */     return new LoggerPatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 64 */     paramStringBuilder.append(abbreviate(paramLogEvent.getLoggerName()));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LoggerPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */