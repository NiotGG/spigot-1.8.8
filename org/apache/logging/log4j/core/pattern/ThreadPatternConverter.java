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
/*    */ @Plugin(name="ThreadPatternConverter", category="Converter")
/*    */ @ConverterKeys({"t", "thread"})
/*    */ public final class ThreadPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 31 */   private static final ThreadPatternConverter INSTANCE = new ThreadPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private ThreadPatternConverter()
/*    */   {
/* 38 */     super("Thread", "thread");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ThreadPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 49 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 57 */     paramStringBuilder.append(paramLogEvent.getThreadName());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ThreadPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */