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
/*    */ @Plugin(name="NDCPatternConverter", category="Converter")
/*    */ @ConverterKeys({"x", "NDC"})
/*    */ public final class NDCPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 32 */   private static final NDCPatternConverter INSTANCE = new NDCPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private NDCPatternConverter()
/*    */   {
/* 39 */     super("NDC", "ndc");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static NDCPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 48 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 56 */     paramStringBuilder.append(paramLogEvent.getContextStack());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\NDCPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */