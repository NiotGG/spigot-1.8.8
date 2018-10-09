/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.helpers.Constants;
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
/*    */ @Plugin(name="LineSeparatorPatternConverter", category="Converter")
/*    */ @ConverterKeys({"n"})
/*    */ public final class LineSeparatorPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 33 */   private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private final String lineSep;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private LineSeparatorPatternConverter()
/*    */   {
/* 45 */     super("Line Sep", "lineSep");
/* 46 */     this.lineSep = Constants.LINE_SEP;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static LineSeparatorPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 55 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 63 */     paramStringBuilder.append(this.lineSep);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LineSeparatorPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */