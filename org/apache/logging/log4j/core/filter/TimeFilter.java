/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="TimeFilter", category="Core", elementType="filter", printObject=true)
/*     */ public final class TimeFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private static final long HOUR_MS = 3600000L;
/*     */   private static final long MINUTE_MS = 60000L;
/*     */   private static final long SECOND_MS = 1000L;
/*     */   private final long start;
/*     */   private final long end;
/*     */   private final TimeZone timezone;
/*     */   
/*     */   private TimeFilter(long paramLong1, long paramLong2, TimeZone paramTimeZone, Filter.Result paramResult1, Filter.Result paramResult2)
/*     */   {
/*  65 */     super(paramResult1, paramResult2);
/*  66 */     this.start = paramLong1;
/*  67 */     this.end = paramLong2;
/*  68 */     this.timezone = paramTimeZone;
/*     */   }
/*     */   
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/*  73 */     Calendar localCalendar = Calendar.getInstance(this.timezone);
/*  74 */     localCalendar.setTimeInMillis(paramLogEvent.getMillis());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  79 */     long l = localCalendar.get(11) * 3600000L + localCalendar.get(12) * 60000L + localCalendar.get(13) * 1000L + localCalendar.get(14);
/*     */     
/*     */ 
/*     */ 
/*  83 */     return (l >= this.start) && (l < this.end) ? this.onMatch : this.onMismatch;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  88 */     StringBuilder localStringBuilder = new StringBuilder();
/*  89 */     localStringBuilder.append("start=").append(this.start);
/*  90 */     localStringBuilder.append(", end=").append(this.end);
/*  91 */     localStringBuilder.append(", timezone=").append(this.timezone.toString());
/*  92 */     return localStringBuilder.toString();
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
/*     */   @PluginFactory
/*     */   public static TimeFilter createFilter(@PluginAttribute("start") String paramString1, @PluginAttribute("end") String paramString2, @PluginAttribute("timezone") String paramString3, @PluginAttribute("onMatch") String paramString4, @PluginAttribute("onMismatch") String paramString5)
/*     */   {
/* 111 */     SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
/* 112 */     long l1 = 0L;
/* 113 */     if (paramString1 != null) {
/* 114 */       localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
/*     */       try {
/* 116 */         l1 = localSimpleDateFormat.parse(paramString1).getTime();
/*     */       } catch (ParseException localParseException1) {
/* 118 */         LOGGER.warn("Error parsing start value " + paramString1, localParseException1);
/*     */       }
/*     */     }
/* 121 */     long l2 = Long.MAX_VALUE;
/* 122 */     if (paramString2 != null) {
/* 123 */       localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
/*     */       try {
/* 125 */         l2 = localSimpleDateFormat.parse(paramString2).getTime();
/*     */       } catch (ParseException localParseException2) {
/* 127 */         LOGGER.warn("Error parsing start value " + paramString2, localParseException2);
/*     */       }
/*     */     }
/* 130 */     TimeZone localTimeZone = paramString3 == null ? TimeZone.getDefault() : TimeZone.getTimeZone(paramString3);
/* 131 */     Filter.Result localResult1 = Filter.Result.toResult(paramString4, Filter.Result.NEUTRAL);
/* 132 */     Filter.Result localResult2 = Filter.Result.toResult(paramString5, Filter.Result.DENY);
/* 133 */     return new TimeFilter(l1, l2, localTimeZone, localResult1, localResult2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\TimeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */