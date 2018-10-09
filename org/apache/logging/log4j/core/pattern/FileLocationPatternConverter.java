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
/*    */ @Plugin(name="FileLocationPatternConverter", category="Converter")
/*    */ @ConverterKeys({"F", "file"})
/*    */ public final class FileLocationPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 32 */   private static final FileLocationPatternConverter INSTANCE = new FileLocationPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private FileLocationPatternConverter()
/*    */   {
/* 39 */     super("File Location", "file");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static FileLocationPatternConverter newInstance(String[] paramArrayOfString)
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
/* 60 */       paramStringBuilder.append(localStackTraceElement.getFileName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\FileLocationPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */