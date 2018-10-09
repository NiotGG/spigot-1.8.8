/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*     */ @Plugin(name="DatePatternConverter", category="Converter")
/*     */ @ConverterKeys({"d", "date"})
/*     */ public final class DatePatternConverter
/*     */   extends LogEventPatternConverter
/*     */   implements ArrayPatternConverter
/*     */ {
/*     */   private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
/*     */   private static final String COMPACT_FORMAT = "COMPACT";
/*     */   private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
/*     */   private static final String DATE_AND_TIME_FORMAT = "DATE";
/*     */   private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
/*     */   private static final String ISO8601_FORMAT = "ISO8601";
/*     */   private static final String ISO8601_BASIC_FORMAT = "ISO8601_BASIC";
/*     */   private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
/*     */   private static final String ISO8601_BASIC_PATTERN = "yyyyMMdd HHmmss,SSS";
/*     */   private static final String COMPACT_PATTERN = "yyyyMMddHHmmssSSS";
/*     */   private String cachedDate;
/*     */   private long lastTimestamp;
/*     */   private final SimpleDateFormat simpleFormat;
/*     */   
/*     */   private DatePatternConverter(String[] paramArrayOfString)
/*     */   {
/*  99 */     super("Date", "date");
/*     */     
/*     */     String str1;
/*     */     
/* 103 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
/*     */     {
/*     */ 
/* 106 */       str1 = null;
/*     */     } else {
/* 108 */       str1 = paramArrayOfString[0];
/*     */     }
/*     */     
/*     */     String str2;
/*     */     
/* 113 */     if ((str1 == null) || (str1.equalsIgnoreCase("ISO8601"))) {
/* 114 */       str2 = "yyyy-MM-dd HH:mm:ss,SSS";
/* 115 */     } else if (str1.equalsIgnoreCase("ISO8601_BASIC")) {
/* 116 */       str2 = "yyyyMMdd HHmmss,SSS";
/* 117 */     } else if (str1.equalsIgnoreCase("ABSOLUTE")) {
/* 118 */       str2 = "HH:mm:ss,SSS";
/* 119 */     } else if (str1.equalsIgnoreCase("DATE")) {
/* 120 */       str2 = "dd MMM yyyy HH:mm:ss,SSS";
/* 121 */     } else if (str1.equalsIgnoreCase("COMPACT")) {
/* 122 */       str2 = "yyyyMMddHHmmssSSS";
/*     */     } else {
/* 124 */       str2 = str1;
/*     */     }
/*     */     
/*     */     SimpleDateFormat localSimpleDateFormat;
/*     */     try
/*     */     {
/* 130 */       localSimpleDateFormat = new SimpleDateFormat(str2);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 132 */       LOGGER.warn("Could not instantiate SimpleDateFormat with pattern " + str1, localIllegalArgumentException);
/*     */       
/*     */ 
/* 135 */       localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/*     */     }
/*     */     
/*     */ 
/* 139 */     if ((paramArrayOfString != null) && (paramArrayOfString.length > 1)) {
/* 140 */       TimeZone localTimeZone = TimeZone.getTimeZone(paramArrayOfString[1]);
/* 141 */       localSimpleDateFormat.setTimeZone(localTimeZone);
/*     */     }
/* 143 */     this.simpleFormat = localSimpleDateFormat;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DatePatternConverter newInstance(String[] paramArrayOfString)
/*     */   {
/* 153 */     return new DatePatternConverter(paramArrayOfString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*     */   {
/* 161 */     long l = paramLogEvent.getMillis();
/*     */     
/* 163 */     synchronized (this) {
/* 164 */       if (l != this.lastTimestamp) {
/* 165 */         this.lastTimestamp = l;
/* 166 */         this.cachedDate = this.simpleFormat.format(Long.valueOf(l));
/*     */       }
/*     */     }
/* 169 */     paramStringBuilder.append(this.cachedDate);
/*     */   }
/*     */   
/*     */   public void format(StringBuilder paramStringBuilder, Object... paramVarArgs)
/*     */   {
/* 174 */     for (Object localObject : paramVarArgs) {
/* 175 */       if ((localObject instanceof Date)) {
/* 176 */         format(localObject, paramStringBuilder);
/* 177 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(Object paramObject, StringBuilder paramStringBuilder)
/*     */   {
/* 187 */     if ((paramObject instanceof Date)) {
/* 188 */       format((Date)paramObject, paramStringBuilder);
/*     */     }
/*     */     
/* 191 */     super.format(paramObject, paramStringBuilder);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(Date paramDate, StringBuilder paramStringBuilder)
/*     */   {
/* 201 */     synchronized (this) {
/* 202 */       paramStringBuilder.append(this.simpleFormat.format(Long.valueOf(paramDate.getTime())));
/*     */     }
/*     */   }
/*     */   
/*     */   public String getPattern() {
/* 207 */     return this.simpleFormat.toPattern();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\DatePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */