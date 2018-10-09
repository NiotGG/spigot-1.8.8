/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.OptionConverter;
/*     */ import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternFormatter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
/*     */ import org.apache.logging.log4j.core.pattern.RegexReplacement;
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
/*     */ @Plugin(name="PatternLayout", category="Core", elementType="layout", printObject=true)
/*     */ public final class PatternLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
/*     */   public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
/*     */   public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
/*     */   public static final String KEY = "Converter";
/*     */   private List<PatternFormatter> formatters;
/*     */   private final String conversionPattern;
/*     */   private final Configuration config;
/*     */   private final RegexReplacement replace;
/*     */   private final boolean alwaysWriteExceptions;
/*     */   
/*     */   private PatternLayout(Configuration paramConfiguration, RegexReplacement paramRegexReplacement, String paramString, Charset paramCharset, boolean paramBoolean)
/*     */   {
/* 110 */     super(paramCharset);
/* 111 */     this.replace = paramRegexReplacement;
/* 112 */     this.conversionPattern = paramString;
/* 113 */     this.config = paramConfiguration;
/* 114 */     this.alwaysWriteExceptions = paramBoolean;
/* 115 */     PatternParser localPatternParser = createPatternParser(paramConfiguration);
/* 116 */     this.formatters = localPatternParser.parse(paramString == null ? "%m%n" : paramString, this.alwaysWriteExceptions);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConversionPattern(String paramString)
/*     */   {
/* 127 */     String str = OptionConverter.convertSpecialChars(paramString);
/* 128 */     if (str == null) {
/* 129 */       return;
/*     */     }
/* 131 */     PatternParser localPatternParser = createPatternParser(this.config);
/* 132 */     this.formatters = localPatternParser.parse(str, this.alwaysWriteExceptions);
/*     */   }
/*     */   
/*     */   public String getConversionPattern() {
/* 136 */     return this.conversionPattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 149 */     HashMap localHashMap = new HashMap();
/* 150 */     localHashMap.put("structured", "false");
/* 151 */     localHashMap.put("formatType", "conversion");
/* 152 */     localHashMap.put("format", this.conversionPattern);
/* 153 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toSerializable(LogEvent paramLogEvent)
/*     */   {
/* 165 */     StringBuilder localStringBuilder = new StringBuilder();
/* 166 */     for (Object localObject = this.formatters.iterator(); ((Iterator)localObject).hasNext();) { PatternFormatter localPatternFormatter = (PatternFormatter)((Iterator)localObject).next();
/* 167 */       localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */     }
/* 169 */     localObject = localStringBuilder.toString();
/* 170 */     if (this.replace != null) {
/* 171 */       localObject = this.replace.format((String)localObject);
/*     */     }
/* 173 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PatternParser createPatternParser(Configuration paramConfiguration)
/*     */   {
/* 182 */     if (paramConfiguration == null) {
/* 183 */       return new PatternParser(paramConfiguration, "Converter", LogEventPatternConverter.class);
/*     */     }
/* 185 */     PatternParser localPatternParser = (PatternParser)paramConfiguration.getComponent("Converter");
/* 186 */     if (localPatternParser == null) {
/* 187 */       localPatternParser = new PatternParser(paramConfiguration, "Converter", LogEventPatternConverter.class);
/* 188 */       paramConfiguration.addComponent("Converter", localPatternParser);
/* 189 */       localPatternParser = (PatternParser)paramConfiguration.getComponent("Converter");
/*     */     }
/* 191 */     return localPatternParser;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 196 */     return this.conversionPattern;
/*     */   }
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
/*     */   @PluginFactory
/*     */   public static PatternLayout createLayout(@PluginAttribute("pattern") String paramString1, @PluginConfiguration Configuration paramConfiguration, @PluginElement("Replace") RegexReplacement paramRegexReplacement, @PluginAttribute("charset") String paramString2, @PluginAttribute("alwaysWriteExceptions") String paramString3)
/*     */   {
/* 217 */     Charset localCharset = Charsets.getSupportedCharset(paramString2);
/* 218 */     boolean bool = Booleans.parseBoolean(paramString3, true);
/* 219 */     return new PatternLayout(paramConfiguration, paramRegexReplacement, paramString1 == null ? "%m%n" : paramString1, localCharset, bool);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\PatternLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */