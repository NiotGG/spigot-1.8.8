/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
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
/*     */ final class CachedDateFormat
/*     */   extends DateFormat
/*     */ {
/*     */   public static final int NO_MILLISECONDS = -2;
/*     */   public static final int UNRECOGNIZED_MILLISECONDS = -1;
/*     */   private static final long serialVersionUID = -1253877934598423628L;
/*     */   private static final String DIGITS = "0123456789";
/*     */   private static final int MAGIC1 = 654;
/*     */   private static final String MAGICSTRING1 = "654";
/*     */   private static final int MAGIC2 = 987;
/*     */   private static final String MAGICSTRING2 = "987";
/*     */   private static final String ZERO_STRING = "000";
/*     */   private static final int BUF_SIZE = 50;
/*     */   private static final int DEFAULT_VALIDITY = 1000;
/*     */   private static final int THREE_DIGITS = 100;
/*     */   private static final int TWO_DIGITS = 10;
/*     */   private static final long SLOTS = 1000L;
/*     */   private final DateFormat formatter;
/*     */   private int millisecondStart;
/*     */   private long slotBegin;
/* 114 */   private final StringBuffer cache = new StringBuffer(50);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int expiration;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long previousTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 131 */   private final Date tmpDate = new Date(0L);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CachedDateFormat(DateFormat paramDateFormat, int paramInt)
/*     */   {
/* 143 */     if (paramDateFormat == null) {
/* 144 */       throw new IllegalArgumentException("dateFormat cannot be null");
/*     */     }
/*     */     
/* 147 */     if (paramInt < 0) {
/* 148 */       throw new IllegalArgumentException("expiration must be non-negative");
/*     */     }
/*     */     
/* 151 */     this.formatter = paramDateFormat;
/* 152 */     this.expiration = paramInt;
/* 153 */     this.millisecondStart = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 158 */     this.previousTime = Long.MIN_VALUE;
/* 159 */     this.slotBegin = Long.MIN_VALUE;
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
/*     */   public static int findMillisecondStart(long paramLong, String paramString, DateFormat paramDateFormat)
/*     */   {
/* 173 */     long l = paramLong / 1000L * 1000L;
/*     */     
/* 175 */     if (l > paramLong) {
/* 176 */       l -= 1000L;
/*     */     }
/*     */     
/* 179 */     int i = (int)(paramLong - l);
/*     */     
/* 181 */     int j = 654;
/* 182 */     String str1 = "654";
/*     */     
/* 184 */     if (i == 654) {
/* 185 */       j = 987;
/* 186 */       str1 = "987";
/*     */     }
/*     */     
/* 189 */     String str2 = paramDateFormat.format(new Date(l + j));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 195 */     if (str2.length() != paramString.length()) {
/* 196 */       return -1;
/*     */     }
/*     */     
/* 199 */     for (int k = 0; k < paramString.length(); k++) {
/* 200 */       if (paramString.charAt(k) != str2.charAt(k))
/*     */       {
/*     */ 
/* 203 */         StringBuffer localStringBuffer = new StringBuffer("ABC");
/* 204 */         millisecondFormat(i, localStringBuffer, 0);
/*     */         
/* 206 */         String str3 = paramDateFormat.format(new Date(l));
/*     */         
/*     */ 
/*     */ 
/* 210 */         if ((str3.length() == paramString.length()) && (str1.regionMatches(0, str2, k, str1.length())) && (localStringBuffer.toString().regionMatches(0, paramString, k, str1.length())) && ("000".regionMatches(0, str3, k, "000".length())))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 218 */           return k;
/*     */         }
/* 220 */         return -1;
/*     */       }
/*     */     }
/*     */     
/* 224 */     return -2;
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
/*     */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/* 237 */     format(paramDate.getTime(), paramStringBuffer);
/*     */     
/* 239 */     return paramStringBuffer;
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
/*     */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer)
/*     */   {
/* 254 */     if (paramLong == this.previousTime) {
/* 255 */       paramStringBuffer.append(this.cache);
/*     */       
/* 257 */       return paramStringBuffer;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 264 */     if ((this.millisecondStart != -1) && (paramLong < this.slotBegin + this.expiration) && (paramLong >= this.slotBegin) && (paramLong < this.slotBegin + 1000L))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 272 */       if (this.millisecondStart >= 0) {
/* 273 */         millisecondFormat((int)(paramLong - this.slotBegin), this.cache, this.millisecondStart);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 279 */       this.previousTime = paramLong;
/* 280 */       paramStringBuffer.append(this.cache);
/*     */       
/* 282 */       return paramStringBuffer;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 288 */     this.cache.setLength(0);
/* 289 */     this.tmpDate.setTime(paramLong);
/* 290 */     this.cache.append(this.formatter.format(this.tmpDate));
/* 291 */     paramStringBuffer.append(this.cache);
/* 292 */     this.previousTime = paramLong;
/* 293 */     this.slotBegin = (this.previousTime / 1000L * 1000L);
/*     */     
/* 295 */     if (this.slotBegin > this.previousTime) {
/* 296 */       this.slotBegin -= 1000L;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 303 */     if (this.millisecondStart >= 0) {
/* 304 */       this.millisecondStart = findMillisecondStart(paramLong, this.cache.toString(), this.formatter);
/*     */     }
/*     */     
/*     */ 
/* 308 */     return paramStringBuffer;
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
/*     */   private static void millisecondFormat(int paramInt1, StringBuffer paramStringBuffer, int paramInt2)
/*     */   {
/* 321 */     paramStringBuffer.setCharAt(paramInt2, "0123456789".charAt(paramInt1 / 100));
/* 322 */     paramStringBuffer.setCharAt(paramInt2 + 1, "0123456789".charAt(paramInt1 / 10 % 10));
/* 323 */     paramStringBuffer.setCharAt(paramInt2 + 2, "0123456789".charAt(paramInt1 % 10));
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
/*     */   public void setTimeZone(TimeZone paramTimeZone)
/*     */   {
/* 336 */     this.formatter.setTimeZone(paramTimeZone);
/* 337 */     this.previousTime = Long.MIN_VALUE;
/* 338 */     this.slotBegin = Long.MIN_VALUE;
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
/*     */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 351 */     return this.formatter.parse(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumberFormat getNumberFormat()
/*     */   {
/* 361 */     return this.formatter.getNumberFormat();
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
/*     */   public static int getMaximumCacheValidity(String paramString)
/*     */   {
/* 378 */     int i = paramString.indexOf('S');
/*     */     
/* 380 */     if ((i >= 0) && (i != paramString.lastIndexOf("SSS"))) {
/* 381 */       return 1;
/*     */     }
/*     */     
/* 384 */     return 1000;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\CachedDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */