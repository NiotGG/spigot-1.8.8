/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TimeZone;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastDateParser
/*     */   implements DateParser, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  68 */   static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
/*     */   
/*     */ 
/*     */   private final String pattern;
/*     */   
/*     */ 
/*     */   private final TimeZone timeZone;
/*     */   
/*     */ 
/*     */   private final Locale locale;
/*     */   
/*     */   private final int century;
/*     */   
/*     */   private final int startYear;
/*     */   
/*     */   private transient Pattern parsePattern;
/*     */   
/*     */   private transient Strategy[] strategies;
/*     */   
/*     */   private transient String currentFormatField;
/*     */   
/*     */   private transient Strategy nextStrategy;
/*     */   
/*     */ 
/*     */   protected FastDateParser(String paramString, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/*  94 */     this(paramString, paramTimeZone, paramLocale, null);
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
/*     */   protected FastDateParser(String paramString, TimeZone paramTimeZone, Locale paramLocale, Date paramDate)
/*     */   {
/* 109 */     this.pattern = paramString;
/* 110 */     this.timeZone = paramTimeZone;
/* 111 */     this.locale = paramLocale;
/*     */     
/* 113 */     Calendar localCalendar = Calendar.getInstance(paramTimeZone, paramLocale);
/*     */     int i;
/* 115 */     if (paramDate != null) {
/* 116 */       localCalendar.setTime(paramDate);
/* 117 */       i = localCalendar.get(1);
/*     */     }
/* 119 */     else if (paramLocale.equals(JAPANESE_IMPERIAL)) {
/* 120 */       i = 0;
/*     */     }
/*     */     else
/*     */     {
/* 124 */       localCalendar.setTime(new Date());
/* 125 */       i = localCalendar.get(1) - 80;
/*     */     }
/* 127 */     this.century = (i / 100 * 100);
/* 128 */     this.startYear = (i - this.century);
/*     */     
/* 130 */     init(localCalendar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void init(Calendar paramCalendar)
/*     */   {
/* 141 */     StringBuilder localStringBuilder = new StringBuilder();
/* 142 */     ArrayList localArrayList = new ArrayList();
/*     */     
/* 144 */     Matcher localMatcher = formatPattern.matcher(this.pattern);
/* 145 */     if (!localMatcher.lookingAt()) {
/* 146 */       throw new IllegalArgumentException("Illegal pattern character '" + this.pattern.charAt(localMatcher.regionStart()) + "'");
/*     */     }
/*     */     
/*     */ 
/* 150 */     this.currentFormatField = localMatcher.group();
/* 151 */     Strategy localStrategy = getStrategy(this.currentFormatField, paramCalendar);
/*     */     for (;;) {
/* 153 */       localMatcher.region(localMatcher.end(), localMatcher.regionEnd());
/* 154 */       if (!localMatcher.lookingAt()) {
/* 155 */         this.nextStrategy = null;
/* 156 */         break;
/*     */       }
/* 158 */       String str = localMatcher.group();
/* 159 */       this.nextStrategy = getStrategy(str, paramCalendar);
/* 160 */       if (localStrategy.addRegex(this, localStringBuilder)) {
/* 161 */         localArrayList.add(localStrategy);
/*     */       }
/* 163 */       this.currentFormatField = str;
/* 164 */       localStrategy = this.nextStrategy;
/*     */     }
/* 166 */     if (localMatcher.regionStart() != localMatcher.regionEnd()) {
/* 167 */       throw new IllegalArgumentException("Failed to parse \"" + this.pattern + "\" ; gave up at index " + localMatcher.regionStart());
/*     */     }
/* 169 */     if (localStrategy.addRegex(this, localStringBuilder)) {
/* 170 */       localArrayList.add(localStrategy);
/*     */     }
/* 172 */     this.currentFormatField = null;
/* 173 */     this.strategies = ((Strategy[])localArrayList.toArray(new Strategy[localArrayList.size()]));
/* 174 */     this.parsePattern = Pattern.compile(localStringBuilder.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPattern()
/*     */   {
/* 184 */     return this.pattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeZone getTimeZone()
/*     */   {
/* 192 */     return this.timeZone;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 200 */     return this.locale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Pattern getParsePattern()
/*     */   {
/* 209 */     return this.parsePattern;
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
/* 222 */     if (!(paramObject instanceof FastDateParser)) {
/* 223 */       return false;
/*     */     }
/* 225 */     FastDateParser localFastDateParser = (FastDateParser)paramObject;
/* 226 */     return (this.pattern.equals(localFastDateParser.pattern)) && (this.timeZone.equals(localFastDateParser.timeZone)) && (this.locale.equals(localFastDateParser.locale));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 238 */     return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 248 */     return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 262 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 264 */     Calendar localCalendar = Calendar.getInstance(this.timeZone, this.locale);
/* 265 */     init(localCalendar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object parseObject(String paramString)
/*     */     throws ParseException
/*     */   {
/* 273 */     return parse(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Date parse(String paramString)
/*     */     throws ParseException
/*     */   {
/* 281 */     Date localDate = parse(paramString, new ParsePosition(0));
/* 282 */     if (localDate == null)
/*     */     {
/* 284 */       if (this.locale.equals(JAPANESE_IMPERIAL)) {
/* 285 */         throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\n" + "Unparseable date: \"" + paramString + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */       }
/*     */       
/*     */ 
/* 289 */       throw new ParseException("Unparseable date: \"" + paramString + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */     }
/* 291 */     return localDate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object parseObject(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 299 */     return parse(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Date parse(String paramString, ParsePosition paramParsePosition)
/*     */   {
/* 307 */     int i = paramParsePosition.getIndex();
/* 308 */     Matcher localMatcher = this.parsePattern.matcher(paramString.substring(i));
/* 309 */     if (!localMatcher.lookingAt()) {
/* 310 */       return null;
/*     */     }
/*     */     
/* 313 */     Calendar localCalendar = Calendar.getInstance(this.timeZone, this.locale);
/* 314 */     localCalendar.clear();
/*     */     
/* 316 */     for (int j = 0; j < this.strategies.length;) {
/* 317 */       Strategy localStrategy = this.strategies[(j++)];
/* 318 */       localStrategy.setCalendar(this, localCalendar, localMatcher.group(j));
/*     */     }
/* 320 */     paramParsePosition.setIndex(i + localMatcher.end());
/* 321 */     return localCalendar.getTime();
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
/*     */   private static StringBuilder escapeRegex(StringBuilder paramStringBuilder, String paramString, boolean paramBoolean)
/*     */   {
/* 335 */     paramStringBuilder.append("\\Q");
/* 336 */     for (int i = 0; i < paramString.length(); i++) {
/* 337 */       char c = paramString.charAt(i);
/* 338 */       switch (c) {
/*     */       case '\'': 
/* 340 */         if (paramBoolean) {
/* 341 */           i++; if (i == paramString.length()) {
/* 342 */             return paramStringBuilder;
/*     */           }
/* 344 */           c = paramString.charAt(i);
/*     */         }
/*     */         break;
/*     */       case '\\': 
/* 348 */         i++; if (i != paramString.length())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 358 */           paramStringBuilder.append(c);
/* 359 */           c = paramString.charAt(i);
/* 360 */           if (c == 'E') {
/* 361 */             paramStringBuilder.append("E\\\\E\\");
/* 362 */             c = 'Q';
/*     */           }
/*     */         }
/*     */         break;
/*     */       }
/*     */       
/* 368 */       paramStringBuilder.append(c);
/*     */     }
/* 370 */     paramStringBuilder.append("\\E");
/* 371 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Map<String, Integer> getDisplayNames(int paramInt, Calendar paramCalendar, Locale paramLocale)
/*     */   {
/* 383 */     return paramCalendar.getDisplayNames(paramInt, 0, paramLocale);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int adjustYear(int paramInt)
/*     */   {
/* 392 */     int i = this.century + paramInt;
/* 393 */     return paramInt >= this.startYear ? i : i + 100;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isNextNumber()
/*     */   {
/* 401 */     return (this.nextStrategy != null) && (this.nextStrategy.isNumber());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int getFieldWidth()
/*     */   {
/* 409 */     return this.currentFormatField.length();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static abstract class Strategy
/*     */   {
/*     */     boolean isNumber()
/*     */     {
/* 423 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void setCalendar(FastDateParser paramFastDateParser, Calendar paramCalendar, String paramString) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     abstract boolean addRegex(FastDateParser paramFastDateParser, StringBuilder paramStringBuilder);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 451 */   private static final Pattern formatPattern = Pattern.compile("D+|E+|F+|G+|H+|K+|M+|S+|W+|Z+|a+|d+|h+|k+|m+|s+|w+|y+|z+|''|'[^']++(''[^']*+)*+'|[^'A-Za-z]++");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Strategy getStrategy(String paramString, Calendar paramCalendar)
/*     */   {
/* 461 */     switch (paramString.charAt(0)) {
/*     */     case '\'': 
/* 463 */       if (paramString.length() > 2) {
/* 464 */         return new CopyQuotedStrategy(paramString.substring(1, paramString.length() - 1));
/*     */       }
/*     */     case '(': case ')': case '*': case '+': case ',': case '-': case '.': case '/': case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9': case ':': case ';': case '<': case '=': case '>': case '?': case '@': case 'A': case 'B': case 'C': case 'I': case 'J': case 'L': case 'N': case 'O': 
/*     */     case 'P': case 'Q': case 'R': case 'T': case 'U': case 'V': case 'X': case 'Y': case '[': case '\\': case ']': case '^': case '_': case '`': case 'b': case 'c': case 'e': case 'f': case 'g': case 'i': case 'j': case 'l': case 'n': case 'o': case 'p': case 'q': case 'r': case 't': case 'u': case 'v': case 'x': default: 
/* 468 */       return new CopyQuotedStrategy(paramString);
/*     */     case 'D': 
/* 470 */       return DAY_OF_YEAR_STRATEGY;
/*     */     case 'E': 
/* 472 */       return getLocaleSpecificStrategy(7, paramCalendar);
/*     */     case 'F': 
/* 474 */       return DAY_OF_WEEK_IN_MONTH_STRATEGY;
/*     */     case 'G': 
/* 476 */       return getLocaleSpecificStrategy(0, paramCalendar);
/*     */     case 'H': 
/* 478 */       return MODULO_HOUR_OF_DAY_STRATEGY;
/*     */     case 'K': 
/* 480 */       return HOUR_STRATEGY;
/*     */     case 'M': 
/* 482 */       return paramString.length() >= 3 ? getLocaleSpecificStrategy(2, paramCalendar) : NUMBER_MONTH_STRATEGY;
/*     */     case 'S': 
/* 484 */       return MILLISECOND_STRATEGY;
/*     */     case 'W': 
/* 486 */       return WEEK_OF_MONTH_STRATEGY;
/*     */     case 'a': 
/* 488 */       return getLocaleSpecificStrategy(9, paramCalendar);
/*     */     case 'd': 
/* 490 */       return DAY_OF_MONTH_STRATEGY;
/*     */     case 'h': 
/* 492 */       return MODULO_HOUR_STRATEGY;
/*     */     case 'k': 
/* 494 */       return HOUR_OF_DAY_STRATEGY;
/*     */     case 'm': 
/* 496 */       return MINUTE_STRATEGY;
/*     */     case 's': 
/* 498 */       return SECOND_STRATEGY;
/*     */     case 'w': 
/* 500 */       return WEEK_OF_YEAR_STRATEGY;
/*     */     case 'y': 
/* 502 */       return paramString.length() > 2 ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
/*     */     }
/*     */     
/* 505 */     return getLocaleSpecificStrategy(15, paramCalendar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 510 */   private static final ConcurrentMap<Locale, Strategy>[] caches = new ConcurrentMap[17];
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static ConcurrentMap<Locale, Strategy> getCache(int paramInt)
/*     */   {
/* 518 */     synchronized (caches) {
/* 519 */       if (caches[paramInt] == null) {
/* 520 */         caches[paramInt] = new ConcurrentHashMap(3);
/*     */       }
/* 522 */       return caches[paramInt];
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Strategy getLocaleSpecificStrategy(int paramInt, Calendar paramCalendar)
/*     */   {
/* 533 */     ConcurrentMap localConcurrentMap = getCache(paramInt);
/* 534 */     Object localObject = (Strategy)localConcurrentMap.get(this.locale);
/* 535 */     if (localObject == null) {
/* 536 */       localObject = paramInt == 15 ? new TimeZoneStrategy(this.locale) : new TextStrategy(paramInt, paramCalendar, this.locale);
/*     */       
/*     */ 
/* 539 */       Strategy localStrategy = (Strategy)localConcurrentMap.putIfAbsent(this.locale, localObject);
/* 540 */       if (localStrategy != null) {
/* 541 */         return localStrategy;
/*     */       }
/*     */     }
/* 544 */     return (Strategy)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class CopyQuotedStrategy
/*     */     extends FastDateParser.Strategy
/*     */   {
/*     */     private final String formatField;
/*     */     
/*     */ 
/*     */     CopyQuotedStrategy(String paramString)
/*     */     {
/* 557 */       super();
/* 558 */       this.formatField = paramString;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean isNumber()
/*     */     {
/* 566 */       char c = this.formatField.charAt(0);
/* 567 */       if (c == '\'') {
/* 568 */         c = this.formatField.charAt(1);
/*     */       }
/* 570 */       return Character.isDigit(c);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean addRegex(FastDateParser paramFastDateParser, StringBuilder paramStringBuilder)
/*     */     {
/* 578 */       FastDateParser.escapeRegex(paramStringBuilder, this.formatField, true);
/* 579 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class TextStrategy
/*     */     extends FastDateParser.Strategy
/*     */   {
/*     */     private final int field;
/*     */     
/*     */ 
/*     */     private final Map<String, Integer> keyValues;
/*     */     
/*     */ 
/*     */     TextStrategy(int paramInt, Calendar paramCalendar, Locale paramLocale)
/*     */     {
/* 596 */       super();
/* 597 */       this.field = paramInt;
/* 598 */       this.keyValues = FastDateParser.getDisplayNames(paramInt, paramCalendar, paramLocale);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean addRegex(FastDateParser paramFastDateParser, StringBuilder paramStringBuilder)
/*     */     {
/* 606 */       paramStringBuilder.append('(');
/* 607 */       for (String str : this.keyValues.keySet()) {
/* 608 */         FastDateParser.escapeRegex(paramStringBuilder, str, false).append('|');
/*     */       }
/* 610 */       paramStringBuilder.setCharAt(paramStringBuilder.length() - 1, ')');
/* 611 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     void setCalendar(FastDateParser paramFastDateParser, Calendar paramCalendar, String paramString)
/*     */     {
/* 619 */       Integer localInteger = (Integer)this.keyValues.get(paramString);
/* 620 */       if (localInteger == null) {
/* 621 */         StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 622 */         localStringBuilder.append(" not in (");
/* 623 */         for (String str : this.keyValues.keySet()) {
/* 624 */           localStringBuilder.append(str).append(' ');
/*     */         }
/* 626 */         localStringBuilder.setCharAt(localStringBuilder.length() - 1, ')');
/* 627 */         throw new IllegalArgumentException(localStringBuilder.toString());
/*     */       }
/* 629 */       paramCalendar.set(this.field, localInteger.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class NumberStrategy
/*     */     extends FastDateParser.Strategy
/*     */   {
/*     */     private final int field;
/*     */     
/*     */ 
/*     */ 
/*     */     NumberStrategy(int paramInt)
/*     */     {
/* 644 */       super();
/* 645 */       this.field = paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean isNumber()
/*     */     {
/* 653 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean addRegex(FastDateParser paramFastDateParser, StringBuilder paramStringBuilder)
/*     */     {
/* 662 */       if (paramFastDateParser.isNextNumber()) {
/* 663 */         paramStringBuilder.append("(\\p{Nd}{").append(paramFastDateParser.getFieldWidth()).append("}+)");
/*     */       }
/*     */       else {
/* 666 */         paramStringBuilder.append("(\\p{Nd}++)");
/*     */       }
/* 668 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     void setCalendar(FastDateParser paramFastDateParser, Calendar paramCalendar, String paramString)
/*     */     {
/* 676 */       paramCalendar.set(this.field, modify(Integer.parseInt(paramString)));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     int modify(int paramInt)
/*     */     {
/* 685 */       return paramInt;
/*     */     }
/*     */   }
/*     */   
/* 689 */   private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1)
/*     */   {
/*     */ 
/*     */     void setCalendar(FastDateParser paramAnonymousFastDateParser, Calendar paramAnonymousCalendar, String paramAnonymousString)
/*     */     {
/*     */ 
/* 695 */       int i = Integer.parseInt(paramAnonymousString);
/* 696 */       if (i < 100) {
/* 697 */         i = paramAnonymousFastDateParser.adjustYear(i);
/*     */       }
/* 699 */       paramAnonymousCalendar.set(1, i);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   private static class TimeZoneStrategy
/*     */     extends FastDateParser.Strategy
/*     */   {
/*     */     private final String validTimeZoneChars;
/*     */     
/* 709 */     private final SortedMap<String, TimeZone> tzNames = new TreeMap(String.CASE_INSENSITIVE_ORDER);
/*     */     
/*     */ 
/*     */ 
/*     */     private static final int ID = 0;
/*     */     
/*     */ 
/*     */ 
/*     */     private static final int LONG_STD = 1;
/*     */     
/*     */ 
/*     */ 
/*     */     private static final int SHORT_STD = 2;
/*     */     
/*     */ 
/*     */ 
/*     */     private static final int LONG_DST = 3;
/*     */     
/*     */ 
/*     */ 
/*     */     private static final int SHORT_DST = 4;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     TimeZoneStrategy(Locale paramLocale)
/*     */     {
/* 736 */       super();
/* 737 */       String[][] arrayOfString = DateFormatSymbols.getInstance(paramLocale).getZoneStrings();
/* 738 */       for (Object localObject2 : arrayOfString) {
/* 739 */         if (!localObject2[0].startsWith("GMT"))
/*     */         {
/*     */ 
/* 742 */           TimeZone localTimeZone = TimeZone.getTimeZone(localObject2[0]);
/* 743 */           if (!this.tzNames.containsKey(localObject2[1])) {
/* 744 */             this.tzNames.put(localObject2[1], localTimeZone);
/*     */           }
/* 746 */           if (!this.tzNames.containsKey(localObject2[2])) {
/* 747 */             this.tzNames.put(localObject2[2], localTimeZone);
/*     */           }
/* 749 */           if (localTimeZone.useDaylightTime()) {
/* 750 */             if (!this.tzNames.containsKey(localObject2[3])) {
/* 751 */               this.tzNames.put(localObject2[3], localTimeZone);
/*     */             }
/* 753 */             if (!this.tzNames.containsKey(localObject2[4])) {
/* 754 */               this.tzNames.put(localObject2[4], localTimeZone);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 759 */       ??? = new StringBuilder();
/* 760 */       ((StringBuilder)???).append("(GMT[+\\-]\\d{0,1}\\d{2}|[+\\-]\\d{2}:?\\d{2}|");
/* 761 */       for (String str : this.tzNames.keySet()) {
/* 762 */         FastDateParser.escapeRegex((StringBuilder)???, str, false).append('|');
/*     */       }
/* 764 */       ((StringBuilder)???).setCharAt(((StringBuilder)???).length() - 1, ')');
/* 765 */       this.validTimeZoneChars = ((StringBuilder)???).toString();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     boolean addRegex(FastDateParser paramFastDateParser, StringBuilder paramStringBuilder)
/*     */     {
/* 773 */       paramStringBuilder.append(this.validTimeZoneChars);
/* 774 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     void setCalendar(FastDateParser paramFastDateParser, Calendar paramCalendar, String paramString)
/*     */     {
/*     */       TimeZone localTimeZone;
/*     */       
/* 783 */       if ((paramString.charAt(0) == '+') || (paramString.charAt(0) == '-')) {
/* 784 */         localTimeZone = TimeZone.getTimeZone("GMT" + paramString);
/*     */       }
/* 786 */       else if (paramString.startsWith("GMT")) {
/* 787 */         localTimeZone = TimeZone.getTimeZone(paramString);
/*     */       }
/*     */       else {
/* 790 */         localTimeZone = (TimeZone)this.tzNames.get(paramString);
/* 791 */         if (localTimeZone == null) {
/* 792 */           throw new IllegalArgumentException(paramString + " is not a supported timezone name");
/*     */         }
/*     */       }
/* 795 */       paramCalendar.setTimeZone(localTimeZone);
/*     */     }
/*     */   }
/*     */   
/* 799 */   private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2)
/*     */   {
/*     */     int modify(int paramAnonymousInt) {
/* 802 */       return paramAnonymousInt - 1;
/*     */     }
/*     */   };
/* 805 */   private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
/* 806 */   private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
/* 807 */   private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
/* 808 */   private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
/* 809 */   private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
/* 810 */   private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
/* 811 */   private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
/* 812 */   private static final Strategy MODULO_HOUR_OF_DAY_STRATEGY = new NumberStrategy(11)
/*     */   {
/*     */     int modify(int paramAnonymousInt) {
/* 815 */       return paramAnonymousInt % 24;
/*     */     }
/*     */   };
/* 818 */   private static final Strategy MODULO_HOUR_STRATEGY = new NumberStrategy(10)
/*     */   {
/*     */     int modify(int paramAnonymousInt) {
/* 821 */       return paramAnonymousInt % 12;
/*     */     }
/*     */   };
/* 824 */   private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
/* 825 */   private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
/* 826 */   private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
/* 827 */   private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\FastDateParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */