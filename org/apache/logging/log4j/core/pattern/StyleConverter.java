/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="style", category="Converter")
/*     */ @ConverterKeys({"style"})
/*     */ public final class StyleConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private final List<PatternFormatter> patternFormatters;
/*     */   private final String style;
/*     */   
/*     */   private StyleConverter(List<PatternFormatter> paramList, String paramString)
/*     */   {
/*  43 */     super("style", "style");
/*  44 */     this.patternFormatters = paramList;
/*  45 */     this.style = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StyleConverter newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */   {
/*  57 */     if (paramArrayOfString.length < 1) {
/*  58 */       LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + paramArrayOfString.length);
/*  59 */       return null;
/*     */     }
/*  61 */     if (paramArrayOfString[0] == null) {
/*  62 */       LOGGER.error("No pattern supplied on style");
/*  63 */       return null;
/*     */     }
/*  65 */     if (paramArrayOfString[1] == null) {
/*  66 */       LOGGER.error("No style attributes provided");
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     PatternParser localPatternParser = PatternLayout.createPatternParser(paramConfiguration);
/*  71 */     List localList = localPatternParser.parse(paramArrayOfString[0]);
/*  72 */     String str = AnsiEscape.createSequence(paramArrayOfString[1].split("\\s*,\\s*"));
/*  73 */     return new StyleConverter(localList, str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/*  82 */     StringBuilder localStringBuilder = new StringBuilder();
/*  83 */     for (PatternFormatter localPatternFormatter : this.patternFormatters) {
/*  84 */       localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */     }
/*     */     
/*  87 */     if (localStringBuilder.length() > 0) {
/*  88 */       paramStringBuilder.append(this.style).append(localStringBuilder.toString()).append(AnsiEscape.getDefaultStyle());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean handlesThrowable()
/*     */   {
/*  94 */     for (PatternFormatter localPatternFormatter : this.patternFormatters) {
/*  95 */       if (localPatternFormatter.handlesThrowable()) {
/*  96 */         return true;
/*     */       }
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     StringBuilder localStringBuilder = new StringBuilder();
/* 110 */     localStringBuilder.append(super.toString());
/* 111 */     localStringBuilder.append("[style=");
/* 112 */     localStringBuilder.append(this.style);
/* 113 */     localStringBuilder.append(", patternFormatters=");
/* 114 */     localStringBuilder.append(this.patternFormatters);
/* 115 */     localStringBuilder.append("]");
/* 116 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\StyleConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */