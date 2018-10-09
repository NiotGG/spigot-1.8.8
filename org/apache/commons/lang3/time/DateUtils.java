/*      */ package org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.text.ParseException;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateUtils
/*      */ {
/*      */   public static final long MILLIS_PER_SECOND = 1000L;
/*      */   public static final long MILLIS_PER_MINUTE = 60000L;
/*      */   public static final long MILLIS_PER_HOUR = 3600000L;
/*      */   public static final long MILLIS_PER_DAY = 86400000L;
/*      */   public static final int SEMI_MONTH = 1001;
/*   76 */   private static final int[][] fields = { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_WEEK_SUNDAY = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_WEEK_MONDAY = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_WEEK_RELATIVE = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_WEEK_CENTER = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_MONTH_SUNDAY = 5;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int RANGE_MONTH_MONDAY = 6;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MODIFY_TRUNCATE = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MODIFY_ROUND = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MODIFY_CEILING = 2;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSameDay(Date paramDate1, Date paramDate2)
/*      */   {
/*  156 */     if ((paramDate1 == null) || (paramDate2 == null)) {
/*  157 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  159 */     Calendar localCalendar1 = Calendar.getInstance();
/*  160 */     localCalendar1.setTime(paramDate1);
/*  161 */     Calendar localCalendar2 = Calendar.getInstance();
/*  162 */     localCalendar2.setTime(paramDate2);
/*  163 */     return isSameDay(localCalendar1, localCalendar2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSameDay(Calendar paramCalendar1, Calendar paramCalendar2)
/*      */   {
/*  180 */     if ((paramCalendar1 == null) || (paramCalendar2 == null)) {
/*  181 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  183 */     return (paramCalendar1.get(0) == paramCalendar2.get(0)) && (paramCalendar1.get(1) == paramCalendar2.get(1)) && (paramCalendar1.get(6) == paramCalendar2.get(6));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSameInstant(Date paramDate1, Date paramDate2)
/*      */   {
/*  201 */     if ((paramDate1 == null) || (paramDate2 == null)) {
/*  202 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  204 */     return paramDate1.getTime() == paramDate2.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSameInstant(Calendar paramCalendar1, Calendar paramCalendar2)
/*      */   {
/*  219 */     if ((paramCalendar1 == null) || (paramCalendar2 == null)) {
/*  220 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  222 */     return paramCalendar1.getTime().getTime() == paramCalendar2.getTime().getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSameLocalTime(Calendar paramCalendar1, Calendar paramCalendar2)
/*      */   {
/*  239 */     if ((paramCalendar1 == null) || (paramCalendar2 == null)) {
/*  240 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  242 */     return (paramCalendar1.get(14) == paramCalendar2.get(14)) && (paramCalendar1.get(13) == paramCalendar2.get(13)) && (paramCalendar1.get(12) == paramCalendar2.get(12)) && (paramCalendar1.get(11) == paramCalendar2.get(11)) && (paramCalendar1.get(6) == paramCalendar2.get(6)) && (paramCalendar1.get(1) == paramCalendar2.get(1)) && (paramCalendar1.get(0) == paramCalendar2.get(0)) && (paramCalendar1.getClass() == paramCalendar2.getClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date parseDate(String paramString, String... paramVarArgs)
/*      */     throws ParseException
/*      */   {
/*  268 */     return parseDate(paramString, null, paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date parseDate(String paramString, Locale paramLocale, String... paramVarArgs)
/*      */     throws ParseException
/*      */   {
/*  291 */     return parseDateWithLeniency(paramString, paramLocale, paramVarArgs, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date parseDateStrictly(String paramString, String... paramVarArgs)
/*      */     throws ParseException
/*      */   {
/*  311 */     return parseDateStrictly(paramString, null, paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date parseDateStrictly(String paramString, Locale paramLocale, String... paramVarArgs)
/*      */     throws ParseException
/*      */   {
/*  333 */     return parseDateWithLeniency(paramString, null, paramVarArgs, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Date parseDateWithLeniency(String paramString, Locale paramLocale, String[] paramArrayOfString, boolean paramBoolean)
/*      */     throws ParseException
/*      */   {
/*  355 */     if ((paramString == null) || (paramArrayOfString == null)) {
/*  356 */       throw new IllegalArgumentException("Date and Patterns must not be null");
/*      */     }
/*      */     
/*      */     SimpleDateFormat localSimpleDateFormat;
/*  360 */     if (paramLocale == null) {
/*  361 */       localSimpleDateFormat = new SimpleDateFormat();
/*      */     } else {
/*  363 */       localSimpleDateFormat = new SimpleDateFormat("", paramLocale);
/*      */     }
/*      */     
/*  366 */     localSimpleDateFormat.setLenient(paramBoolean);
/*  367 */     ParsePosition localParsePosition = new ParsePosition(0);
/*  368 */     for (String str1 : paramArrayOfString)
/*      */     {
/*  370 */       String str2 = str1;
/*      */       
/*      */ 
/*  373 */       if (str1.endsWith("ZZ")) {
/*  374 */         str2 = str2.substring(0, str2.length() - 1);
/*      */       }
/*      */       
/*  377 */       localSimpleDateFormat.applyPattern(str2);
/*  378 */       localParsePosition.setIndex(0);
/*      */       
/*  380 */       String str3 = paramString;
/*      */       
/*  382 */       if (str1.endsWith("ZZ")) {
/*  383 */         str3 = paramString.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
/*      */       }
/*      */       
/*  386 */       Date localDate = localSimpleDateFormat.parse(str3, localParsePosition);
/*  387 */       if ((localDate != null) && (localParsePosition.getIndex() == str3.length())) {
/*  388 */         return localDate;
/*      */       }
/*      */     }
/*  391 */     throw new ParseException("Unable to parse the date: " + paramString, -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addYears(Date paramDate, int paramInt)
/*      */   {
/*  405 */     return add(paramDate, 1, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addMonths(Date paramDate, int paramInt)
/*      */   {
/*  419 */     return add(paramDate, 2, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addWeeks(Date paramDate, int paramInt)
/*      */   {
/*  433 */     return add(paramDate, 3, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addDays(Date paramDate, int paramInt)
/*      */   {
/*  447 */     return add(paramDate, 5, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addHours(Date paramDate, int paramInt)
/*      */   {
/*  461 */     return add(paramDate, 11, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addMinutes(Date paramDate, int paramInt)
/*      */   {
/*  475 */     return add(paramDate, 12, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addSeconds(Date paramDate, int paramInt)
/*      */   {
/*  489 */     return add(paramDate, 13, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date addMilliseconds(Date paramDate, int paramInt)
/*      */   {
/*  503 */     return add(paramDate, 14, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Date add(Date paramDate, int paramInt1, int paramInt2)
/*      */   {
/*  518 */     if (paramDate == null) {
/*  519 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  521 */     Calendar localCalendar = Calendar.getInstance();
/*  522 */     localCalendar.setTime(paramDate);
/*  523 */     localCalendar.add(paramInt1, paramInt2);
/*  524 */     return localCalendar.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setYears(Date paramDate, int paramInt)
/*      */   {
/*  539 */     return set(paramDate, 1, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setMonths(Date paramDate, int paramInt)
/*      */   {
/*  554 */     return set(paramDate, 2, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setDays(Date paramDate, int paramInt)
/*      */   {
/*  569 */     return set(paramDate, 5, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setHours(Date paramDate, int paramInt)
/*      */   {
/*  585 */     return set(paramDate, 11, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setMinutes(Date paramDate, int paramInt)
/*      */   {
/*  600 */     return set(paramDate, 12, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setSeconds(Date paramDate, int paramInt)
/*      */   {
/*  615 */     return set(paramDate, 13, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date setMilliseconds(Date paramDate, int paramInt)
/*      */   {
/*  630 */     return set(paramDate, 14, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Date set(Date paramDate, int paramInt1, int paramInt2)
/*      */   {
/*  647 */     if (paramDate == null) {
/*  648 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*      */     
/*  651 */     Calendar localCalendar = Calendar.getInstance();
/*  652 */     localCalendar.setLenient(false);
/*  653 */     localCalendar.setTime(paramDate);
/*  654 */     localCalendar.set(paramInt1, paramInt2);
/*  655 */     return localCalendar.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar toCalendar(Date paramDate)
/*      */   {
/*  668 */     Calendar localCalendar = Calendar.getInstance();
/*  669 */     localCalendar.setTime(paramDate);
/*  670 */     return localCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date round(Date paramDate, int paramInt)
/*      */   {
/*  701 */     if (paramDate == null) {
/*  702 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  704 */     Calendar localCalendar = Calendar.getInstance();
/*  705 */     localCalendar.setTime(paramDate);
/*  706 */     modify(localCalendar, paramInt, 1);
/*  707 */     return localCalendar.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar round(Calendar paramCalendar, int paramInt)
/*      */   {
/*  738 */     if (paramCalendar == null) {
/*  739 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  741 */     Calendar localCalendar = (Calendar)paramCalendar.clone();
/*  742 */     modify(localCalendar, paramInt, 1);
/*  743 */     return localCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date round(Object paramObject, int paramInt)
/*      */   {
/*  775 */     if (paramObject == null) {
/*  776 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  778 */     if ((paramObject instanceof Date))
/*  779 */       return round((Date)paramObject, paramInt);
/*  780 */     if ((paramObject instanceof Calendar)) {
/*  781 */       return round((Calendar)paramObject, paramInt).getTime();
/*      */     }
/*  783 */     throw new ClassCastException("Could not round " + paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date truncate(Date paramDate, int paramInt)
/*      */   {
/*  804 */     if (paramDate == null) {
/*  805 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  807 */     Calendar localCalendar = Calendar.getInstance();
/*  808 */     localCalendar.setTime(paramDate);
/*  809 */     modify(localCalendar, paramInt, 0);
/*  810 */     return localCalendar.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar truncate(Calendar paramCalendar, int paramInt)
/*      */   {
/*  829 */     if (paramCalendar == null) {
/*  830 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  832 */     Calendar localCalendar = (Calendar)paramCalendar.clone();
/*  833 */     modify(localCalendar, paramInt, 0);
/*  834 */     return localCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date truncate(Object paramObject, int paramInt)
/*      */   {
/*  854 */     if (paramObject == null) {
/*  855 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  857 */     if ((paramObject instanceof Date))
/*  858 */       return truncate((Date)paramObject, paramInt);
/*  859 */     if ((paramObject instanceof Calendar)) {
/*  860 */       return truncate((Calendar)paramObject, paramInt).getTime();
/*      */     }
/*  862 */     throw new ClassCastException("Could not truncate " + paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date ceiling(Date paramDate, int paramInt)
/*      */   {
/*  884 */     if (paramDate == null) {
/*  885 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  887 */     Calendar localCalendar = Calendar.getInstance();
/*  888 */     localCalendar.setTime(paramDate);
/*  889 */     modify(localCalendar, paramInt, 2);
/*  890 */     return localCalendar.getTime();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Calendar ceiling(Calendar paramCalendar, int paramInt)
/*      */   {
/*  910 */     if (paramCalendar == null) {
/*  911 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  913 */     Calendar localCalendar = (Calendar)paramCalendar.clone();
/*  914 */     modify(localCalendar, paramInt, 2);
/*  915 */     return localCalendar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Date ceiling(Object paramObject, int paramInt)
/*      */   {
/*  936 */     if (paramObject == null) {
/*  937 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  939 */     if ((paramObject instanceof Date))
/*  940 */       return ceiling((Date)paramObject, paramInt);
/*  941 */     if ((paramObject instanceof Calendar)) {
/*  942 */       return ceiling((Calendar)paramObject, paramInt).getTime();
/*      */     }
/*  944 */     throw new ClassCastException("Could not find ceiling of for type: " + paramObject.getClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void modify(Calendar paramCalendar, int paramInt1, int paramInt2)
/*      */   {
/*  958 */     if (paramCalendar.get(1) > 280000000) {
/*  959 */       throw new ArithmeticException("Calendar value too large for accurate calculations");
/*      */     }
/*      */     
/*  962 */     if (paramInt1 == 14) {
/*  963 */       return;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  972 */     Date localDate = paramCalendar.getTime();
/*  973 */     long l = localDate.getTime();
/*  974 */     int i = 0;
/*      */     
/*      */ 
/*  977 */     int j = paramCalendar.get(14);
/*  978 */     if ((0 == paramInt2) || (j < 500)) {
/*  979 */       l -= j;
/*      */     }
/*  981 */     if (paramInt1 == 13) {
/*  982 */       i = 1;
/*      */     }
/*      */     
/*      */ 
/*  986 */     int k = paramCalendar.get(13);
/*  987 */     if ((i == 0) && ((0 == paramInt2) || (k < 30))) {
/*  988 */       l -= k * 1000L;
/*      */     }
/*  990 */     if (paramInt1 == 12) {
/*  991 */       i = 1;
/*      */     }
/*      */     
/*      */ 
/*  995 */     int m = paramCalendar.get(12);
/*  996 */     if ((i == 0) && ((0 == paramInt2) || (m < 30))) {
/*  997 */       l -= m * 60000L;
/*      */     }
/*      */     
/*      */ 
/* 1001 */     if (localDate.getTime() != l) {
/* 1002 */       localDate.setTime(l);
/* 1003 */       paramCalendar.setTime(localDate);
/*      */     }
/*      */     
/*      */ 
/* 1007 */     int n = 0;
/* 1008 */     for (int[] arrayOfInt1 : fields) { int i6;
/* 1009 */       for (i6 : arrayOfInt1) {
/* 1010 */         if (i6 == paramInt1)
/*      */         {
/* 1012 */           if ((paramInt2 == 2) || ((paramInt2 == 1) && (n != 0))) {
/* 1013 */             if (paramInt1 == 1001)
/*      */             {
/*      */ 
/*      */ 
/* 1017 */               if (paramCalendar.get(5) == 1) {
/* 1018 */                 paramCalendar.add(5, 15);
/*      */               } else {
/* 1020 */                 paramCalendar.add(5, -15);
/* 1021 */                 paramCalendar.add(2, 1);
/*      */               }
/*      */             }
/* 1024 */             else if (paramInt1 == 9)
/*      */             {
/*      */ 
/*      */ 
/* 1028 */               if (paramCalendar.get(11) == 0) {
/* 1029 */                 paramCalendar.add(11, 12);
/*      */               } else {
/* 1031 */                 paramCalendar.add(11, -12);
/* 1032 */                 paramCalendar.add(5, 1);
/*      */               }
/*      */               
/*      */ 
/*      */             }
/*      */             else {
/* 1038 */               paramCalendar.add(arrayOfInt1[0], 1);
/*      */             }
/*      */           }
/* 1041 */           return;
/*      */         }
/*      */       }
/*      */       
/* 1045 */       int i3 = 0;
/* 1046 */       ??? = 0;
/*      */       
/* 1048 */       switch (paramInt1) {
/*      */       case 1001: 
/* 1050 */         if (arrayOfInt1[0] == 5)
/*      */         {
/*      */ 
/*      */ 
/* 1054 */           i3 = paramCalendar.get(5) - 1;
/*      */           
/*      */ 
/* 1057 */           if (i3 >= 15) {
/* 1058 */             i3 -= 15;
/*      */           }
/*      */           
/* 1061 */           n = i3 > 7 ? 1 : 0;
/* 1062 */           ??? = 1;
/*      */         }
/*      */         break;
/*      */       case 9: 
/* 1066 */         if (arrayOfInt1[0] == 11)
/*      */         {
/*      */ 
/* 1069 */           i3 = paramCalendar.get(11);
/* 1070 */           if (i3 >= 12) {
/* 1071 */             i3 -= 12;
/*      */           }
/* 1073 */           n = i3 >= 6 ? 1 : 0;
/* 1074 */           ??? = 1;
/*      */         }
/*      */         
/*      */         break;
/*      */       }
/*      */       
/* 1080 */       if (??? == 0) {
/* 1081 */         ??? = paramCalendar.getActualMinimum(arrayOfInt1[0]);
/* 1082 */         i6 = paramCalendar.getActualMaximum(arrayOfInt1[0]);
/*      */         
/* 1084 */         i3 = paramCalendar.get(arrayOfInt1[0]) - ???;
/*      */         
/* 1086 */         n = i3 > (i6 - ???) / 2 ? 1 : 0;
/*      */       }
/*      */       
/* 1089 */       if (i3 != 0) {
/* 1090 */         paramCalendar.set(arrayOfInt1[0], paramCalendar.get(arrayOfInt1[0]) - i3);
/*      */       }
/*      */     }
/* 1093 */     throw new IllegalArgumentException("The field " + paramInt1 + " is not supported");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<Calendar> iterator(Date paramDate, int paramInt)
/*      */   {
/* 1123 */     if (paramDate == null) {
/* 1124 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1126 */     Calendar localCalendar = Calendar.getInstance();
/* 1127 */     localCalendar.setTime(paramDate);
/* 1128 */     return iterator(localCalendar, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<Calendar> iterator(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1156 */     if (paramCalendar == null) {
/* 1157 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1159 */     Calendar localCalendar1 = null;
/* 1160 */     Calendar localCalendar2 = null;
/* 1161 */     int i = 1;
/* 1162 */     int j = 7;
/* 1163 */     switch (paramInt)
/*      */     {
/*      */     case 5: 
/*      */     case 6: 
/* 1167 */       localCalendar1 = truncate(paramCalendar, 2);
/*      */       
/* 1169 */       localCalendar2 = (Calendar)localCalendar1.clone();
/* 1170 */       localCalendar2.add(2, 1);
/* 1171 */       localCalendar2.add(5, -1);
/*      */       
/* 1173 */       if (paramInt == 6) {
/* 1174 */         i = 2;
/* 1175 */         j = 1;
/*      */       }
/*      */       
/*      */       break;
/*      */     case 1: 
/*      */     case 2: 
/*      */     case 3: 
/*      */     case 4: 
/* 1183 */       localCalendar1 = truncate(paramCalendar, 5);
/* 1184 */       localCalendar2 = truncate(paramCalendar, 5);
/* 1185 */       switch (paramInt)
/*      */       {
/*      */       case 1: 
/*      */         break;
/*      */       case 2: 
/* 1190 */         i = 2;
/* 1191 */         j = 1;
/* 1192 */         break;
/*      */       case 3: 
/* 1194 */         i = paramCalendar.get(7);
/* 1195 */         j = i - 1;
/* 1196 */         break;
/*      */       case 4: 
/* 1198 */         i = paramCalendar.get(7) - 3;
/* 1199 */         j = paramCalendar.get(7) + 3;
/*      */       }
/*      */       
/* 1202 */       break;
/*      */     
/*      */ 
/*      */     default: 
/* 1206 */       throw new IllegalArgumentException("The range style " + paramInt + " is not valid.");
/*      */     }
/* 1208 */     if (i < 1) {
/* 1209 */       i += 7;
/*      */     }
/* 1211 */     if (i > 7) {
/* 1212 */       i -= 7;
/*      */     }
/* 1214 */     if (j < 1) {
/* 1215 */       j += 7;
/*      */     }
/* 1217 */     if (j > 7) {
/* 1218 */       j -= 7;
/*      */     }
/* 1220 */     while (localCalendar1.get(7) != i) {
/* 1221 */       localCalendar1.add(5, -1);
/*      */     }
/* 1223 */     while (localCalendar2.get(7) != j) {
/* 1224 */       localCalendar2.add(5, 1);
/*      */     }
/* 1226 */     return new DateIterator(localCalendar1, localCalendar2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<?> iterator(Object paramObject, int paramInt)
/*      */   {
/* 1246 */     if (paramObject == null) {
/* 1247 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1249 */     if ((paramObject instanceof Date))
/* 1250 */       return iterator((Date)paramObject, paramInt);
/* 1251 */     if ((paramObject instanceof Calendar)) {
/* 1252 */       return iterator((Calendar)paramObject, paramInt);
/*      */     }
/* 1254 */     throw new ClassCastException("Could not iterate based on " + paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInMilliseconds(Date paramDate, int paramInt)
/*      */   {
/* 1290 */     return getFragment(paramDate, paramInt, TimeUnit.MILLISECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInSeconds(Date paramDate, int paramInt)
/*      */   {
/* 1328 */     return getFragment(paramDate, paramInt, TimeUnit.SECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInMinutes(Date paramDate, int paramInt)
/*      */   {
/* 1366 */     return getFragment(paramDate, paramInt, TimeUnit.MINUTES);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInHours(Date paramDate, int paramInt)
/*      */   {
/* 1404 */     return getFragment(paramDate, paramInt, TimeUnit.HOURS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInDays(Date paramDate, int paramInt)
/*      */   {
/* 1442 */     return getFragment(paramDate, paramInt, TimeUnit.DAYS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInMilliseconds(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1480 */     return getFragment(paramCalendar, paramInt, TimeUnit.MILLISECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInSeconds(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1517 */     return getFragment(paramCalendar, paramInt, TimeUnit.SECONDS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInMinutes(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1555 */     return getFragment(paramCalendar, paramInt, TimeUnit.MINUTES);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInHours(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1593 */     return getFragment(paramCalendar, paramInt, TimeUnit.HOURS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long getFragmentInDays(Calendar paramCalendar, int paramInt)
/*      */   {
/* 1633 */     return getFragment(paramCalendar, paramInt, TimeUnit.DAYS);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long getFragment(Date paramDate, int paramInt, TimeUnit paramTimeUnit)
/*      */   {
/* 1648 */     if (paramDate == null) {
/* 1649 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1651 */     Calendar localCalendar = Calendar.getInstance();
/* 1652 */     localCalendar.setTime(paramDate);
/* 1653 */     return getFragment(localCalendar, paramInt, paramTimeUnit);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long getFragment(Calendar paramCalendar, int paramInt, TimeUnit paramTimeUnit)
/*      */   {
/* 1668 */     if (paramCalendar == null) {
/* 1669 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*      */     
/* 1672 */     long l = 0L;
/*      */     
/* 1674 */     int i = paramTimeUnit == TimeUnit.DAYS ? 0 : 1;
/*      */     
/*      */ 
/* 1677 */     switch (paramInt) {
/*      */     case 1: 
/* 1679 */       l += paramTimeUnit.convert(paramCalendar.get(6) - i, TimeUnit.DAYS);
/* 1680 */       break;
/*      */     case 2: 
/* 1682 */       l += paramTimeUnit.convert(paramCalendar.get(5) - i, TimeUnit.DAYS);
/* 1683 */       break;
/*      */     }
/*      */     
/*      */     
/*      */ 
/* 1688 */     switch (paramInt)
/*      */     {
/*      */ 
/*      */ 
/*      */     case 1: 
/*      */     case 2: 
/*      */     case 5: 
/*      */     case 6: 
/* 1696 */       l += paramTimeUnit.convert(paramCalendar.get(11), TimeUnit.HOURS);
/*      */     
/*      */     case 11: 
/* 1699 */       l += paramTimeUnit.convert(paramCalendar.get(12), TimeUnit.MINUTES);
/*      */     
/*      */     case 12: 
/* 1702 */       l += paramTimeUnit.convert(paramCalendar.get(13), TimeUnit.SECONDS);
/*      */     
/*      */     case 13: 
/* 1705 */       l += paramTimeUnit.convert(paramCalendar.get(14), TimeUnit.MILLISECONDS);
/* 1706 */       break;
/*      */     case 14: 
/*      */       break; case 3: case 4: case 7: case 8: case 9: case 10: default:  throw new IllegalArgumentException("The fragment " + paramInt + " is not supported"); }
/*      */     
/* 1710 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean truncatedEquals(Calendar paramCalendar1, Calendar paramCalendar2, int paramInt)
/*      */   {
/* 1727 */     return truncatedCompareTo(paramCalendar1, paramCalendar2, paramInt) == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean truncatedEquals(Date paramDate1, Date paramDate2, int paramInt)
/*      */   {
/* 1744 */     return truncatedCompareTo(paramDate1, paramDate2, paramInt) == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int truncatedCompareTo(Calendar paramCalendar1, Calendar paramCalendar2, int paramInt)
/*      */   {
/* 1762 */     Calendar localCalendar1 = truncate(paramCalendar1, paramInt);
/* 1763 */     Calendar localCalendar2 = truncate(paramCalendar2, paramInt);
/* 1764 */     return localCalendar1.compareTo(localCalendar2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int truncatedCompareTo(Date paramDate1, Date paramDate2, int paramInt)
/*      */   {
/* 1782 */     Date localDate1 = truncate(paramDate1, paramInt);
/* 1783 */     Date localDate2 = truncate(paramDate2, paramInt);
/* 1784 */     return localDate1.compareTo(localDate2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static class DateIterator
/*      */     implements Iterator<Calendar>
/*      */   {
/*      */     private final Calendar endFinal;
/*      */     
/*      */ 
/*      */ 
/*      */     private final Calendar spot;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     DateIterator(Calendar paramCalendar1, Calendar paramCalendar2)
/*      */     {
/* 1804 */       this.endFinal = paramCalendar2;
/* 1805 */       this.spot = paramCalendar1;
/* 1806 */       this.spot.add(5, -1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean hasNext()
/*      */     {
/* 1816 */       return this.spot.before(this.endFinal);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Calendar next()
/*      */     {
/* 1826 */       if (this.spot.equals(this.endFinal)) {
/* 1827 */         throw new NoSuchElementException();
/*      */       }
/* 1829 */       this.spot.add(5, 1);
/* 1830 */       return (Calendar)this.spot.clone();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void remove()
/*      */     {
/* 1841 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */