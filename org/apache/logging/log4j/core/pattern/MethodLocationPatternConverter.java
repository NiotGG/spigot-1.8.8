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
/*    */ @Plugin(name="MethodLocationPatternConverter", category="Converter")
/*    */ @ConverterKeys({"M", "method"})
/*    */ public final class MethodLocationPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 32 */   private static final MethodLocationPatternConverter INSTANCE = new MethodLocationPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private MethodLocationPatternConverter()
/*    */   {
/* 39 */     super("Method", "method");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MethodLocationPatternConverter newInstance(String[] paramArrayOfString)
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
/* 60 */       paramStringBuilder.append(localStackTraceElement.getMethodName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\MethodLocationPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */