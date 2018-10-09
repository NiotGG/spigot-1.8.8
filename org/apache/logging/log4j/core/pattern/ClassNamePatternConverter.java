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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="ClassNamePatternConverter", category="Converter")
/*    */ @ConverterKeys({"C", "class"})
/*    */ public final class ClassNamePatternConverter
/*    */   extends NamePatternConverter
/*    */ {
/*    */   private static final String NA = "?";
/*    */   
/*    */   private ClassNamePatternConverter(String[] paramArrayOfString)
/*    */   {
/* 39 */     super("Class Name", "class name", paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static ClassNamePatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 49 */     return new ClassNamePatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 60 */     StackTraceElement localStackTraceElement = paramLogEvent.getSource();
/* 61 */     if (localStackTraceElement == null) {
/* 62 */       paramStringBuilder.append("?");
/*    */     } else {
/* 64 */       paramStringBuilder.append(abbreviate(localStackTraceElement.getClassName()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ClassNamePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */