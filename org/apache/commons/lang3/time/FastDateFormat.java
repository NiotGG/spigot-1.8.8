/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.Format;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
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
/*     */ public class FastDateFormat
/*     */   extends Format
/*     */   implements DateParser, DatePrinter
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   public static final int FULL = 0;
/*     */   public static final int LONG = 1;
/*     */   public static final int MEDIUM = 2;
/*     */   public static final int SHORT = 3;
/*  88 */   private static final FormatCache<FastDateFormat> cache = new FormatCache()
/*     */   {
/*     */     protected FastDateFormat createInstance(String paramAnonymousString, TimeZone paramAnonymousTimeZone, Locale paramAnonymousLocale) {
/*  91 */       return new FastDateFormat(paramAnonymousString, paramAnonymousTimeZone, paramAnonymousLocale);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   private final FastDatePrinter printer;
/*     */   
/*     */ 
/*     */   private final FastDateParser parser;
/*     */   
/*     */ 
/*     */ 
/*     */   public static FastDateFormat getInstance()
/*     */   {
/* 106 */     return (FastDateFormat)cache.getInstance();
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
/*     */   public static FastDateFormat getInstance(String paramString)
/*     */   {
/* 119 */     return (FastDateFormat)cache.getInstance(paramString, null, null);
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
/*     */   public static FastDateFormat getInstance(String paramString, TimeZone paramTimeZone)
/*     */   {
/* 134 */     return (FastDateFormat)cache.getInstance(paramString, paramTimeZone, null);
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
/*     */   public static FastDateFormat getInstance(String paramString, Locale paramLocale)
/*     */   {
/* 148 */     return (FastDateFormat)cache.getInstance(paramString, null, paramLocale);
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
/*     */   public static FastDateFormat getInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 165 */     return (FastDateFormat)cache.getInstance(paramString, paramTimeZone, paramLocale);
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
/*     */   public static FastDateFormat getDateInstance(int paramInt)
/*     */   {
/* 180 */     return (FastDateFormat)cache.getDateInstance(paramInt, null, null);
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
/*     */   public static FastDateFormat getDateInstance(int paramInt, Locale paramLocale)
/*     */   {
/* 195 */     return (FastDateFormat)cache.getDateInstance(paramInt, null, paramLocale);
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
/*     */   public static FastDateFormat getDateInstance(int paramInt, TimeZone paramTimeZone)
/*     */   {
/* 211 */     return (FastDateFormat)cache.getDateInstance(paramInt, paramTimeZone, null);
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
/*     */   public static FastDateFormat getDateInstance(int paramInt, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 227 */     return (FastDateFormat)cache.getDateInstance(paramInt, paramTimeZone, paramLocale);
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
/*     */   public static FastDateFormat getTimeInstance(int paramInt)
/*     */   {
/* 242 */     return (FastDateFormat)cache.getTimeInstance(paramInt, null, null);
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
/*     */   public static FastDateFormat getTimeInstance(int paramInt, Locale paramLocale)
/*     */   {
/* 257 */     return (FastDateFormat)cache.getTimeInstance(paramInt, null, paramLocale);
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
/*     */   public static FastDateFormat getTimeInstance(int paramInt, TimeZone paramTimeZone)
/*     */   {
/* 273 */     return (FastDateFormat)cache.getTimeInstance(paramInt, paramTimeZone, null);
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
/*     */   public static FastDateFormat getTimeInstance(int paramInt, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 289 */     return (FastDateFormat)cache.getTimeInstance(paramInt, paramTimeZone, paramLocale);
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
/*     */   public static FastDateFormat getDateTimeInstance(int paramInt1, int paramInt2)
/*     */   {
/* 305 */     return (FastDateFormat)cache.getDateTimeInstance(paramInt1, paramInt2, null, null);
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
/*     */   public static FastDateFormat getDateTimeInstance(int paramInt1, int paramInt2, Locale paramLocale)
/*     */   {
/* 321 */     return (FastDateFormat)cache.getDateTimeInstance(paramInt1, paramInt2, null, paramLocale);
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
/*     */   public static FastDateFormat getDateTimeInstance(int paramInt1, int paramInt2, TimeZone paramTimeZone)
/*     */   {
/* 338 */     return getDateTimeInstance(paramInt1, paramInt2, paramTimeZone, null);
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
/*     */   public static FastDateFormat getDateTimeInstance(int paramInt1, int paramInt2, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 355 */     return (FastDateFormat)cache.getDateTimeInstance(paramInt1, paramInt2, paramTimeZone, paramLocale);
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
/*     */   protected FastDateFormat(String paramString, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 369 */     this(paramString, paramTimeZone, paramLocale, null);
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
/*     */   protected FastDateFormat(String paramString, TimeZone paramTimeZone, Locale paramLocale, Date paramDate)
/*     */   {
/* 384 */     this.printer = new FastDatePrinter(paramString, paramTimeZone, paramLocale);
/* 385 */     this.parser = new FastDateParser(paramString, paramTimeZone, paramLocale, paramDate);
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
/*     */   public StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/* 401 */     return this.printer.format(paramObject, paramStringBuffer, paramFieldPosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(long paramLong)
/*     */   {
/* 413 */     return this.printer.format(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(Date paramDate)
/*     */   {
/* 424 */     return this.printer.format(paramDate);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String format(Calendar paramCalendar)
/*     */   {
/* 435 */     return this.printer.format(paramCalendar);
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
/*     */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer)
/*     */   {
/* 449 */     return this.printer.format(paramLong, paramStringBuffer);
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
/*     */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer)
/*     */   {
/* 462 */     return this.printer.format(paramDate, paramStringBuffer);
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
/*     */   public StringBuffer format(Calendar paramCalendar, StringBuffer paramStringBuffer)
/*     */   {
/* 475 */     return this.printer.format(paramCalendar, paramStringBuffer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date parse(String paramString)
/*     */     throws ParseException
/*     */   {
/* 487 */     return this.parser.parse(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 495 */     return this.parser.parse(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object parseObject(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 503 */     return this.parser.parseObject(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPattern()
/*     */   {
/* 515 */     return this.printer.getPattern();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 527 */     return this.printer.getTimeZone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 537 */     return this.printer.getLocale();
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
/*     */   public int getMaxLengthEstimate()
/*     */   {
/* 550 */     return this.printer.getMaxLengthEstimate();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 563 */     if (!(paramObject instanceof FastDateFormat)) {
/* 564 */       return false;
/*     */     }
/* 566 */     FastDateFormat localFastDateFormat = (FastDateFormat)paramObject;
/*     */     
/* 568 */     return this.printer.equals(localFastDateFormat.printer);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 578 */     return this.printer.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 588 */     return "FastDateFormat[" + this.printer.getPattern() + "," + this.printer.getLocale() + "," + this.printer.getTimeZone().getID() + "]";
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
/*     */   protected StringBuffer applyRules(Calendar paramCalendar, StringBuffer paramStringBuffer)
/*     */   {
/* 601 */     return this.printer.applyRules(paramCalendar, paramStringBuffer);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\FastDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */