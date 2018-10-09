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
/*    */ @Plugin(name="FullLocationPatternConverter", category="Converter")
/*    */ @ConverterKeys({"l", "location"})
/*    */ public final class FullLocationPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 32 */   private static final FullLocationPatternConverter INSTANCE = new FullLocationPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private FullLocationPatternConverter()
/*    */   {
/* 39 */     super("Full Location", "fullLocation");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static FullLocationPatternConverter newInstance(String[] paramArrayOfString)
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
/* 60 */       paramStringBuilder.append(localStackTraceElement.toString());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\FullLocationPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */