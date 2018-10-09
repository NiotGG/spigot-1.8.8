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
/*    */ @Plugin(name="LineLocationPatternConverter", category="Converter")
/*    */ @ConverterKeys({"L", "line"})
/*    */ public final class LineLocationPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 31 */   private static final LineLocationPatternConverter INSTANCE = new LineLocationPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private LineLocationPatternConverter()
/*    */   {
/* 38 */     super("Line", "line");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static LineLocationPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 49 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 57 */     StackTraceElement localStackTraceElement = paramLogEvent.getSource();
/*    */     
/* 59 */     if (localStackTraceElement != null) {
/* 60 */       paramStringBuilder.append(localStackTraceElement.getLineNumber());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LineLocationPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */