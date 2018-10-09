/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.pattern.ArrayPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.DatePatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.FormattingInfo;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
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
/*     */ public class PatternProcessor
/*     */ {
/*     */   private static final String KEY = "FileConverter";
/*     */   private static final char YEAR_CHAR = 'y';
/*     */   private static final char MONTH_CHAR = 'M';
/*  42 */   private static final char[] WEEK_CHARS = { 'w', 'W' };
/*  43 */   private static final char[] DAY_CHARS = { 'D', 'd', 'F', 'E' };
/*  44 */   private static final char[] HOUR_CHARS = { 'H', 'K', 'h', 'k' };
/*     */   
/*     */   private static final char MINUTE_CHAR = 'm';
/*     */   
/*     */   private static final char SECOND_CHAR = 's';
/*     */   private static final char MILLIS_CHAR = 'S';
/*     */   private final ArrayPatternConverter[] patternConverters;
/*     */   private final FormattingInfo[] patternFields;
/*  52 */   private long prevFileTime = 0L;
/*  53 */   private long nextFileTime = 0L;
/*     */   
/*  55 */   private RolloverFrequency frequency = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public PatternProcessor(String paramString)
/*     */   {
/*  62 */     PatternParser localPatternParser = createPatternParser();
/*  63 */     ArrayList localArrayList1 = new ArrayList();
/*  64 */     ArrayList localArrayList2 = new ArrayList();
/*  65 */     localPatternParser.parse(paramString, localArrayList1, localArrayList2);
/*  66 */     FormattingInfo[] arrayOfFormattingInfo = new FormattingInfo[localArrayList2.size()];
/*  67 */     this.patternFields = ((FormattingInfo[])localArrayList2.toArray(arrayOfFormattingInfo));
/*  68 */     ArrayPatternConverter[] arrayOfArrayPatternConverter1 = new ArrayPatternConverter[localArrayList1.size()];
/*  69 */     this.patternConverters = ((ArrayPatternConverter[])localArrayList1.toArray(arrayOfArrayPatternConverter1));
/*     */     
/*  71 */     for (ArrayPatternConverter localArrayPatternConverter : this.patternConverters) {
/*  72 */       if ((localArrayPatternConverter instanceof DatePatternConverter)) {
/*  73 */         DatePatternConverter localDatePatternConverter = (DatePatternConverter)localArrayPatternConverter;
/*  74 */         this.frequency = calculateFrequency(localDatePatternConverter.getPattern());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getNextTime(long paramLong, int paramInt, boolean paramBoolean)
/*     */   {
/*  87 */     this.prevFileTime = this.nextFileTime;
/*     */     
/*     */ 
/*  90 */     if (this.frequency == null) {
/*  91 */       throw new IllegalStateException("Pattern does not contain a date");
/*     */     }
/*  93 */     Calendar localCalendar1 = Calendar.getInstance();
/*  94 */     localCalendar1.setTimeInMillis(paramLong);
/*  95 */     Calendar localCalendar2 = Calendar.getInstance();
/*  96 */     localCalendar2.set(localCalendar1.get(1), 0, 1, 0, 0, 0);
/*  97 */     localCalendar2.set(14, 0);
/*  98 */     if (this.frequency == RolloverFrequency.ANNUALLY) {
/*  99 */       increment(localCalendar2, 1, paramInt, paramBoolean);
/* 100 */       l = localCalendar2.getTimeInMillis();
/* 101 */       localCalendar2.add(1, -1);
/* 102 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 103 */       return l;
/*     */     }
/* 105 */     if (this.frequency == RolloverFrequency.MONTHLY) {
/* 106 */       increment(localCalendar2, 2, paramInt, paramBoolean);
/* 107 */       l = localCalendar2.getTimeInMillis();
/* 108 */       localCalendar2.add(2, -1);
/* 109 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 110 */       return l;
/*     */     }
/* 112 */     if (this.frequency == RolloverFrequency.WEEKLY) {
/* 113 */       increment(localCalendar2, 3, paramInt, paramBoolean);
/* 114 */       l = localCalendar2.getTimeInMillis();
/* 115 */       localCalendar2.add(3, -1);
/* 116 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 117 */       return l;
/*     */     }
/* 119 */     localCalendar2.set(6, localCalendar1.get(6));
/* 120 */     if (this.frequency == RolloverFrequency.DAILY) {
/* 121 */       increment(localCalendar2, 6, paramInt, paramBoolean);
/* 122 */       l = localCalendar2.getTimeInMillis();
/* 123 */       localCalendar2.add(6, -1);
/* 124 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 125 */       return l;
/*     */     }
/* 127 */     localCalendar2.set(10, localCalendar1.get(10));
/* 128 */     if (this.frequency == RolloverFrequency.HOURLY) {
/* 129 */       increment(localCalendar2, 10, paramInt, paramBoolean);
/* 130 */       l = localCalendar2.getTimeInMillis();
/* 131 */       localCalendar2.add(10, -1);
/* 132 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 133 */       return l;
/*     */     }
/* 135 */     localCalendar2.set(12, localCalendar1.get(12));
/* 136 */     if (this.frequency == RolloverFrequency.EVERY_MINUTE) {
/* 137 */       increment(localCalendar2, 12, paramInt, paramBoolean);
/* 138 */       l = localCalendar2.getTimeInMillis();
/* 139 */       localCalendar2.add(12, -1);
/* 140 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 141 */       return l;
/*     */     }
/* 143 */     localCalendar2.set(13, localCalendar1.get(13));
/* 144 */     if (this.frequency == RolloverFrequency.EVERY_SECOND) {
/* 145 */       increment(localCalendar2, 13, paramInt, paramBoolean);
/* 146 */       l = localCalendar2.getTimeInMillis();
/* 147 */       localCalendar2.add(13, -1);
/* 148 */       this.nextFileTime = localCalendar2.getTimeInMillis();
/* 149 */       return l;
/*     */     }
/* 151 */     increment(localCalendar2, 14, paramInt, paramBoolean);
/* 152 */     long l = localCalendar2.getTimeInMillis();
/* 153 */     localCalendar2.add(14, -1);
/* 154 */     this.nextFileTime = localCalendar2.getTimeInMillis();
/* 155 */     return l;
/*     */   }
/*     */   
/*     */   private void increment(Calendar paramCalendar, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 159 */     int i = paramBoolean ? paramInt2 - paramCalendar.get(paramInt1) % paramInt2 : paramInt2;
/* 160 */     paramCalendar.add(paramInt1, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void formatFileName(StringBuilder paramStringBuilder, Object paramObject)
/*     */   {
/* 169 */     long l = this.prevFileTime == 0L ? System.currentTimeMillis() : this.prevFileTime;
/* 170 */     formatFileName(paramStringBuilder, new Object[] { new Date(l), paramObject });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void formatFileName(StrSubstitutor paramStrSubstitutor, StringBuilder paramStringBuilder, Object paramObject)
/*     */   {
/* 180 */     long l = this.prevFileTime == 0L ? System.currentTimeMillis() : this.prevFileTime;
/* 181 */     formatFileName(paramStringBuilder, new Object[] { new Date(l), paramObject });
/* 182 */     Log4jLogEvent localLog4jLogEvent = new Log4jLogEvent(l);
/* 183 */     String str = paramStrSubstitutor.replace(localLog4jLogEvent, paramStringBuilder);
/* 184 */     paramStringBuilder.setLength(0);
/* 185 */     paramStringBuilder.append(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void formatFileName(StringBuilder paramStringBuilder, Object... paramVarArgs)
/*     */   {
/* 194 */     for (int i = 0; i < this.patternConverters.length; i++) {
/* 195 */       int j = paramStringBuilder.length();
/* 196 */       this.patternConverters[i].format(paramStringBuilder, paramVarArgs);
/*     */       
/* 198 */       if (this.patternFields[i] != null) {
/* 199 */         this.patternFields[i].format(j, paramStringBuilder);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private RolloverFrequency calculateFrequency(String paramString) {
/* 205 */     if (patternContains(paramString, 'S')) {
/* 206 */       return RolloverFrequency.EVERY_MILLISECOND;
/*     */     }
/* 208 */     if (patternContains(paramString, 's')) {
/* 209 */       return RolloverFrequency.EVERY_SECOND;
/*     */     }
/* 211 */     if (patternContains(paramString, 'm')) {
/* 212 */       return RolloverFrequency.EVERY_MINUTE;
/*     */     }
/* 214 */     if (patternContains(paramString, HOUR_CHARS)) {
/* 215 */       return RolloverFrequency.HOURLY;
/*     */     }
/* 217 */     if (patternContains(paramString, DAY_CHARS)) {
/* 218 */       return RolloverFrequency.DAILY;
/*     */     }
/* 220 */     if (patternContains(paramString, WEEK_CHARS)) {
/* 221 */       return RolloverFrequency.WEEKLY;
/*     */     }
/* 223 */     if (patternContains(paramString, 'M')) {
/* 224 */       return RolloverFrequency.MONTHLY;
/*     */     }
/* 226 */     if (patternContains(paramString, 'y')) {
/* 227 */       return RolloverFrequency.ANNUALLY;
/*     */     }
/* 229 */     return null;
/*     */   }
/*     */   
/*     */   private PatternParser createPatternParser()
/*     */   {
/* 234 */     return new PatternParser(null, "FileConverter", null);
/*     */   }
/*     */   
/*     */   private boolean patternContains(String paramString, char... paramVarArgs) {
/* 238 */     for (char c : paramVarArgs) {
/* 239 */       if (patternContains(paramString, c)) {
/* 240 */         return true;
/*     */       }
/*     */     }
/* 243 */     return false;
/*     */   }
/*     */   
/*     */   private boolean patternContains(String paramString, char paramChar) {
/* 247 */     return paramString.indexOf(paramChar) >= 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\PatternProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */