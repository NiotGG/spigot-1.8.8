/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*    */ 
/*    */ @Plugin(name="replace", category="Converter")
/*    */ @ConverterKeys({"replace"})
/*    */ public final class RegexReplacementConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final Pattern pattern;
/*    */   private final String substitution;
/*    */   private final List<PatternFormatter> formatters;
/*    */   
/*    */   private RegexReplacementConverter(List<PatternFormatter> paramList, Pattern paramPattern, String paramString)
/*    */   {
/* 48 */     super("replace", "replace");
/* 49 */     this.pattern = paramPattern;
/* 50 */     this.substitution = paramString;
/* 51 */     this.formatters = paramList;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static RegexReplacementConverter newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*    */   {
/* 63 */     if (paramArrayOfString.length != 3) {
/* 64 */       LOGGER.error("Incorrect number of options on replace. Expected 3 received " + paramArrayOfString.length);
/* 65 */       return null;
/*    */     }
/* 67 */     if (paramArrayOfString[0] == null) {
/* 68 */       LOGGER.error("No pattern supplied on replace");
/* 69 */       return null;
/*    */     }
/* 71 */     if (paramArrayOfString[1] == null) {
/* 72 */       LOGGER.error("No regular expression supplied on replace");
/* 73 */       return null;
/*    */     }
/* 75 */     if (paramArrayOfString[2] == null) {
/* 76 */       LOGGER.error("No substitution supplied on replace");
/* 77 */       return null;
/*    */     }
/* 79 */     Pattern localPattern = Pattern.compile(paramArrayOfString[1]);
/* 80 */     PatternParser localPatternParser = PatternLayout.createPatternParser(paramConfiguration);
/* 81 */     List localList = localPatternParser.parse(paramArrayOfString[0]);
/* 82 */     return new RegexReplacementConverter(localList, localPattern, paramArrayOfString[2]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 91 */     StringBuilder localStringBuilder = new StringBuilder();
/* 92 */     for (PatternFormatter localPatternFormatter : this.formatters) {
/* 93 */       localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*    */     }
/* 95 */     paramStringBuilder.append(this.pattern.matcher(localStringBuilder.toString()).replaceAll(this.substitution));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\RegexReplacementConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */