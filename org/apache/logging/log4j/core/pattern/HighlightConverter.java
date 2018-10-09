/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.Level;
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
/*     */ @Plugin(name="highlight", category="Converter")
/*     */ @ConverterKeys({"highlight"})
/*     */ public final class HighlightConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*  75 */   private static final EnumMap<Level, String> DEFAULT_STYLES = new EnumMap(Level.class);
/*     */   
/*  77 */   private static final EnumMap<Level, String> LOGBACK_STYLES = new EnumMap(Level.class);
/*     */   
/*     */   private static final String STYLE_KEY = "STYLE";
/*     */   
/*     */   private static final String STYLE_KEY_DEFAULT = "DEFAULT";
/*     */   
/*     */   private static final String STYLE_KEY_LOGBACK = "LOGBACK";
/*     */   
/*  85 */   private static final Map<String, EnumMap<Level, String>> STYLES = new HashMap();
/*     */   private final EnumMap<Level, String> levelStyles;
/*     */   private final List<PatternFormatter> patternFormatters;
/*     */   
/*  89 */   static { DEFAULT_STYLES.put(Level.FATAL, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  90 */     DEFAULT_STYLES.put(Level.ERROR, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  91 */     DEFAULT_STYLES.put(Level.WARN, AnsiEscape.createSequence(new String[] { "YELLOW" }));
/*  92 */     DEFAULT_STYLES.put(Level.INFO, AnsiEscape.createSequence(new String[] { "GREEN" }));
/*  93 */     DEFAULT_STYLES.put(Level.DEBUG, AnsiEscape.createSequence(new String[] { "CYAN" }));
/*  94 */     DEFAULT_STYLES.put(Level.TRACE, AnsiEscape.createSequence(new String[] { "BLACK" }));
/*     */     
/*  96 */     LOGBACK_STYLES.put(Level.FATAL, AnsiEscape.createSequence(new String[] { "BLINK", "BRIGHT", "RED" }));
/*  97 */     LOGBACK_STYLES.put(Level.ERROR, AnsiEscape.createSequence(new String[] { "BRIGHT", "RED" }));
/*  98 */     LOGBACK_STYLES.put(Level.WARN, AnsiEscape.createSequence(new String[] { "RED" }));
/*  99 */     LOGBACK_STYLES.put(Level.INFO, AnsiEscape.createSequence(new String[] { "BLUE" }));
/* 100 */     LOGBACK_STYLES.put(Level.DEBUG, AnsiEscape.createSequence((String[])null));
/* 101 */     LOGBACK_STYLES.put(Level.TRACE, AnsiEscape.createSequence((String[])null));
/*     */     
/* 103 */     STYLES.put("DEFAULT", DEFAULT_STYLES);
/* 104 */     STYLES.put("LOGBACK", LOGBACK_STYLES);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static EnumMap<Level, String> createLevelStyleMap(String[] paramArrayOfString)
/*     */   {
/* 132 */     if (paramArrayOfString.length < 2) {
/* 133 */       return DEFAULT_STYLES;
/*     */     }
/* 135 */     Map localMap = AnsiEscape.createMap(paramArrayOfString[1], new String[] { "STYLE" });
/* 136 */     EnumMap localEnumMap = new EnumMap(DEFAULT_STYLES);
/* 137 */     for (Map.Entry localEntry : localMap.entrySet()) {
/* 138 */       String str1 = ((String)localEntry.getKey()).toUpperCase(Locale.ENGLISH);
/* 139 */       String str2 = (String)localEntry.getValue();
/* 140 */       Object localObject; if ("STYLE".equalsIgnoreCase(str1)) {
/* 141 */         localObject = (EnumMap)STYLES.get(str2.toUpperCase(Locale.ENGLISH));
/* 142 */         if (localObject == null) {
/* 143 */           LOGGER.error("Unknown level style: " + str2 + ". Use one of " + Arrays.toString(STYLES.keySet().toArray()));
/*     */         }
/*     */         else {
/* 146 */           localEnumMap.putAll((Map)localObject);
/*     */         }
/*     */       } else {
/* 149 */         localObject = Level.valueOf(str1);
/* 150 */         if (localObject == null) {
/* 151 */           LOGGER.error("Unknown level name: " + str1 + ". Use one of " + Arrays.toString(DEFAULT_STYLES.keySet().toArray()));
/*     */         }
/*     */         else {
/* 154 */           localEnumMap.put((Enum)localObject, str2);
/*     */         }
/*     */       }
/*     */     }
/* 158 */     return localEnumMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static HighlightConverter newInstance(Configuration paramConfiguration, String[] paramArrayOfString)
/*     */   {
/* 170 */     if (paramArrayOfString.length < 1) {
/* 171 */       LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + paramArrayOfString.length);
/* 172 */       return null;
/*     */     }
/* 174 */     if (paramArrayOfString[0] == null) {
/* 175 */       LOGGER.error("No pattern supplied on style");
/* 176 */       return null;
/*     */     }
/* 178 */     PatternParser localPatternParser = PatternLayout.createPatternParser(paramConfiguration);
/* 179 */     List localList = localPatternParser.parse(paramArrayOfString[0]);
/* 180 */     return new HighlightConverter(localList, createLevelStyleMap(paramArrayOfString));
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
/*     */   private HighlightConverter(List<PatternFormatter> paramList, EnumMap<Level, String> paramEnumMap)
/*     */   {
/* 194 */     super("style", "style");
/* 195 */     this.patternFormatters = paramList;
/* 196 */     this.levelStyles = paramEnumMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/* 204 */     StringBuilder localStringBuilder = new StringBuilder();
/* 205 */     for (PatternFormatter localPatternFormatter : this.patternFormatters) {
/* 206 */       localPatternFormatter.format(paramLogEvent, localStringBuilder);
/*     */     }
/*     */     
/* 209 */     if (localStringBuilder.length() > 0) {
/* 210 */       paramStringBuilder.append((String)this.levelStyles.get(paramLogEvent.getLevel())).append(localStringBuilder.toString()).append(AnsiEscape.getDefaultStyle());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean handlesThrowable()
/*     */   {
/* 217 */     for (PatternFormatter localPatternFormatter : this.patternFormatters) {
/* 218 */       if (localPatternFormatter.handlesThrowable()) {
/* 219 */         return true;
/*     */       }
/*     */     }
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\HighlightConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */