/*      */ package org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.FieldPosition;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FastDatePrinter
/*      */   implements DatePrinter, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final int FULL = 0;
/*      */   public static final int LONG = 1;
/*      */   public static final int MEDIUM = 2;
/*      */   public static final int SHORT = 3;
/*      */   private final String mPattern;
/*      */   private final TimeZone mTimeZone;
/*      */   private final Locale mLocale;
/*      */   private transient Rule[] mRules;
/*      */   private transient int mMaxLengthEstimate;
/*      */   
/*      */   protected FastDatePrinter(String paramString, TimeZone paramTimeZone, Locale paramLocale)
/*      */   {
/*  138 */     this.mPattern = paramString;
/*  139 */     this.mTimeZone = paramTimeZone;
/*  140 */     this.mLocale = paramLocale;
/*      */     
/*  142 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void init()
/*      */   {
/*  149 */     List localList = parsePattern();
/*  150 */     this.mRules = ((Rule[])localList.toArray(new Rule[localList.size()]));
/*      */     
/*  152 */     int i = 0;
/*  153 */     int j = this.mRules.length; for (;;) { j--; if (j < 0) break;
/*  154 */       i += this.mRules[j].estimateLength();
/*      */     }
/*      */     
/*  157 */     this.mMaxLengthEstimate = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected List<Rule> parsePattern()
/*      */   {
/*  169 */     DateFormatSymbols localDateFormatSymbols = new DateFormatSymbols(this.mLocale);
/*  170 */     ArrayList localArrayList = new ArrayList();
/*      */     
/*  172 */     String[] arrayOfString1 = localDateFormatSymbols.getEras();
/*  173 */     String[] arrayOfString2 = localDateFormatSymbols.getMonths();
/*  174 */     String[] arrayOfString3 = localDateFormatSymbols.getShortMonths();
/*  175 */     String[] arrayOfString4 = localDateFormatSymbols.getWeekdays();
/*  176 */     String[] arrayOfString5 = localDateFormatSymbols.getShortWeekdays();
/*  177 */     String[] arrayOfString6 = localDateFormatSymbols.getAmPmStrings();
/*      */     
/*  179 */     int i = this.mPattern.length();
/*  180 */     int[] arrayOfInt = new int[1];
/*      */     
/*  182 */     for (int j = 0; j < i; j++) {
/*  183 */       arrayOfInt[0] = j;
/*  184 */       String str1 = parseToken(this.mPattern, arrayOfInt);
/*  185 */       j = arrayOfInt[0];
/*      */       
/*  187 */       int k = str1.length();
/*  188 */       if (k == 0) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*  193 */       int m = str1.charAt(0);
/*      */       Object localObject;
/*  195 */       switch (m) {
/*      */       case 71: 
/*  197 */         localObject = new TextField(0, arrayOfString1);
/*  198 */         break;
/*      */       case 121: 
/*  200 */         if (k == 2) {
/*  201 */           localObject = TwoDigitYearField.INSTANCE;
/*      */         } else {
/*  203 */           localObject = selectNumberRule(1, k < 4 ? 4 : k);
/*      */         }
/*  205 */         break;
/*      */       case 77: 
/*  207 */         if (k >= 4) {
/*  208 */           localObject = new TextField(2, arrayOfString2);
/*  209 */         } else if (k == 3) {
/*  210 */           localObject = new TextField(2, arrayOfString3);
/*  211 */         } else if (k == 2) {
/*  212 */           localObject = TwoDigitMonthField.INSTANCE;
/*      */         } else {
/*  214 */           localObject = UnpaddedMonthField.INSTANCE;
/*      */         }
/*  216 */         break;
/*      */       case 100: 
/*  218 */         localObject = selectNumberRule(5, k);
/*  219 */         break;
/*      */       case 104: 
/*  221 */         localObject = new TwelveHourField(selectNumberRule(10, k));
/*  222 */         break;
/*      */       case 72: 
/*  224 */         localObject = selectNumberRule(11, k);
/*  225 */         break;
/*      */       case 109: 
/*  227 */         localObject = selectNumberRule(12, k);
/*  228 */         break;
/*      */       case 115: 
/*  230 */         localObject = selectNumberRule(13, k);
/*  231 */         break;
/*      */       case 83: 
/*  233 */         localObject = selectNumberRule(14, k);
/*  234 */         break;
/*      */       case 69: 
/*  236 */         localObject = new TextField(7, k < 4 ? arrayOfString5 : arrayOfString4);
/*  237 */         break;
/*      */       case 68: 
/*  239 */         localObject = selectNumberRule(6, k);
/*  240 */         break;
/*      */       case 70: 
/*  242 */         localObject = selectNumberRule(8, k);
/*  243 */         break;
/*      */       case 119: 
/*  245 */         localObject = selectNumberRule(3, k);
/*  246 */         break;
/*      */       case 87: 
/*  248 */         localObject = selectNumberRule(4, k);
/*  249 */         break;
/*      */       case 97: 
/*  251 */         localObject = new TextField(9, arrayOfString6);
/*  252 */         break;
/*      */       case 107: 
/*  254 */         localObject = new TwentyFourHourField(selectNumberRule(11, k));
/*  255 */         break;
/*      */       case 75: 
/*  257 */         localObject = selectNumberRule(10, k);
/*  258 */         break;
/*      */       case 122: 
/*  260 */         if (k >= 4) {
/*  261 */           localObject = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 1);
/*      */         } else {
/*  263 */           localObject = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 0);
/*      */         }
/*  265 */         break;
/*      */       case 90: 
/*  267 */         if (k == 1) {
/*  268 */           localObject = TimeZoneNumberRule.INSTANCE_NO_COLON;
/*      */         } else {
/*  270 */           localObject = TimeZoneNumberRule.INSTANCE_COLON;
/*      */         }
/*  272 */         break;
/*      */       case 39: 
/*  274 */         String str2 = str1.substring(1);
/*  275 */         if (str2.length() == 1) {
/*  276 */           localObject = new CharacterLiteral(str2.charAt(0));
/*      */         } else {
/*  278 */           localObject = new StringLiteral(str2);
/*      */         }
/*  280 */         break;
/*      */       case 40: case 41: case 42: case 43: case 44: case 45: case 46: case 47: case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 65: case 66: case 67: case 73: case 74: case 76: case 78: case 79: case 80: case 81: case 82: case 84: case 85: case 86: case 88: case 89: case 91: case 92: case 93: case 94: case 95: case 96: case 98: case 99: case 101: case 102: case 103: case 105: case 106: case 108: case 110: case 111: case 112: case 113: case 114: case 116: case 117: case 118: case 120: default: 
/*  282 */         throw new IllegalArgumentException("Illegal pattern component: " + str1);
/*      */       }
/*      */       
/*  285 */       localArrayList.add(localObject);
/*      */     }
/*      */     
/*  288 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String parseToken(String paramString, int[] paramArrayOfInt)
/*      */   {
/*  299 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/*  301 */     int i = paramArrayOfInt[0];
/*  302 */     int j = paramString.length();
/*      */     
/*  304 */     char c1 = paramString.charAt(i);
/*  305 */     if (((c1 >= 'A') && (c1 <= 'Z')) || ((c1 >= 'a') && (c1 <= 'z')))
/*      */     {
/*      */ 
/*  308 */       localStringBuilder.append(c1);
/*      */     }
/*  310 */     while (i + 1 < j) {
/*  311 */       char c2 = paramString.charAt(i + 1);
/*  312 */       if (c2 == c1) {
/*  313 */         localStringBuilder.append(c1);
/*  314 */         i++;
/*      */         
/*      */ 
/*      */ 
/*  318 */         continue;
/*      */         
/*      */ 
/*  321 */         localStringBuilder.append('\'');
/*      */         
/*  323 */         c2 = '\000';
/*  325 */         for (; 
/*  325 */             i < j; i++) {
/*  326 */           c1 = paramString.charAt(i);
/*      */           
/*  328 */           if (c1 == '\'') {
/*  329 */             if ((i + 1 < j) && (paramString.charAt(i + 1) == '\''))
/*      */             {
/*  331 */               i++;
/*  332 */               localStringBuilder.append(c1);
/*      */             } else {
/*  334 */               c2 = c2 == 0 ? '\001' : '\000';
/*      */             }
/*  336 */           } else { if ((c2 == 0) && (((c1 >= 'A') && (c1 <= 'Z')) || ((c1 >= 'a') && (c1 <= 'z'))))
/*      */             {
/*  338 */               i--;
/*  339 */               break;
/*      */             }
/*  341 */             localStringBuilder.append(c1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  346 */     paramArrayOfInt[0] = i;
/*  347 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected NumberRule selectNumberRule(int paramInt1, int paramInt2)
/*      */   {
/*  358 */     switch (paramInt2) {
/*      */     case 1: 
/*  360 */       return new UnpaddedNumberField(paramInt1);
/*      */     case 2: 
/*  362 */       return new TwoDigitNumberField(paramInt1);
/*      */     }
/*  364 */     return new PaddedNumberField(paramInt1, paramInt2);
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
/*      */   public StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*      */   {
/*  381 */     if ((paramObject instanceof Date))
/*  382 */       return format((Date)paramObject, paramStringBuffer);
/*  383 */     if ((paramObject instanceof Calendar))
/*  384 */       return format((Calendar)paramObject, paramStringBuffer);
/*  385 */     if ((paramObject instanceof Long)) {
/*  386 */       return format(((Long)paramObject).longValue(), paramStringBuffer);
/*      */     }
/*  388 */     throw new IllegalArgumentException("Unknown class: " + (paramObject == null ? "<null>" : paramObject.getClass().getName()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String format(long paramLong)
/*      */   {
/*  398 */     GregorianCalendar localGregorianCalendar = newCalendar();
/*  399 */     localGregorianCalendar.setTimeInMillis(paramLong);
/*  400 */     return applyRulesToString(localGregorianCalendar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String applyRulesToString(Calendar paramCalendar)
/*      */   {
/*  409 */     return applyRules(paramCalendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private GregorianCalendar newCalendar()
/*      */   {
/*  418 */     return new GregorianCalendar(this.mTimeZone, this.mLocale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String format(Date paramDate)
/*      */   {
/*  426 */     GregorianCalendar localGregorianCalendar = newCalendar();
/*  427 */     localGregorianCalendar.setTime(paramDate);
/*  428 */     return applyRulesToString(localGregorianCalendar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String format(Calendar paramCalendar)
/*      */   {
/*  436 */     return format(paramCalendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuffer format(long paramLong, StringBuffer paramStringBuffer)
/*      */   {
/*  444 */     return format(new Date(paramLong), paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuffer format(Date paramDate, StringBuffer paramStringBuffer)
/*      */   {
/*  452 */     GregorianCalendar localGregorianCalendar = newCalendar();
/*  453 */     localGregorianCalendar.setTime(paramDate);
/*  454 */     return applyRules(localGregorianCalendar, paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuffer format(Calendar paramCalendar, StringBuffer paramStringBuffer)
/*      */   {
/*  462 */     return applyRules(paramCalendar, paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected StringBuffer applyRules(Calendar paramCalendar, StringBuffer paramStringBuffer)
/*      */   {
/*  474 */     for (Rule localRule : this.mRules) {
/*  475 */       localRule.appendTo(paramStringBuffer, paramCalendar);
/*      */     }
/*  477 */     return paramStringBuffer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPattern()
/*      */   {
/*  487 */     return this.mPattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public TimeZone getTimeZone()
/*      */   {
/*  495 */     return this.mTimeZone;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Locale getLocale()
/*      */   {
/*  503 */     return this.mLocale;
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
/*      */   public int getMaxLengthEstimate()
/*      */   {
/*  516 */     return this.mMaxLengthEstimate;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  529 */     if (!(paramObject instanceof FastDatePrinter)) {
/*  530 */       return false;
/*      */     }
/*  532 */     FastDatePrinter localFastDatePrinter = (FastDatePrinter)paramObject;
/*  533 */     return (this.mPattern.equals(localFastDatePrinter.mPattern)) && (this.mTimeZone.equals(localFastDatePrinter.mTimeZone)) && (this.mLocale.equals(localFastDatePrinter.mLocale));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  545 */     return this.mPattern.hashCode() + 13 * (this.mTimeZone.hashCode() + 13 * this.mLocale.hashCode());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  555 */     return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  569 */     paramObjectInputStream.defaultReadObject();
/*  570 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static abstract interface Rule
/*      */   {
/*      */     public abstract int estimateLength();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static abstract interface NumberRule
/*      */     extends FastDatePrinter.Rule
/*      */   {
/*      */     public abstract void appendTo(StringBuffer paramStringBuffer, int paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class CharacterLiteral
/*      */     implements FastDatePrinter.Rule
/*      */   {
/*      */     private final char mValue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     CharacterLiteral(char paramChar)
/*      */     {
/*  621 */       this.mValue = paramChar;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  629 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  637 */       paramStringBuffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class StringLiteral
/*      */     implements FastDatePrinter.Rule
/*      */   {
/*      */     private final String mValue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     StringLiteral(String paramString)
/*      */     {
/*  654 */       this.mValue = paramString;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  662 */       return this.mValue.length();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  670 */       paramStringBuffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class TextField
/*      */     implements FastDatePrinter.Rule
/*      */   {
/*      */     private final int mField;
/*      */     
/*      */ 
/*      */     private final String[] mValues;
/*      */     
/*      */ 
/*      */ 
/*      */     TextField(int paramInt, String[] paramArrayOfString)
/*      */     {
/*  689 */       this.mField = paramInt;
/*  690 */       this.mValues = paramArrayOfString;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  698 */       int i = 0;
/*  699 */       int j = this.mValues.length; for (;;) { j--; if (j < 0) break;
/*  700 */         int k = this.mValues[j].length();
/*  701 */         if (k > i) {
/*  702 */           i = k;
/*      */         }
/*      */       }
/*  705 */       return i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  713 */       paramStringBuffer.append(this.mValues[paramCalendar.get(this.mField)]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class UnpaddedNumberField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*      */     private final int mField;
/*      */     
/*      */ 
/*      */ 
/*      */     UnpaddedNumberField(int paramInt)
/*      */     {
/*  729 */       this.mField = paramInt;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  737 */       return 4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  745 */       appendTo(paramStringBuffer, paramCalendar.get(this.mField));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*  753 */       if (paramInt < 10) {
/*  754 */         paramStringBuffer.append((char)(paramInt + 48));
/*  755 */       } else if (paramInt < 100) {
/*  756 */         paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  757 */         paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */       } else {
/*  759 */         paramStringBuffer.append(Integer.toString(paramInt));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class UnpaddedMonthField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*  768 */     static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  783 */       return 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  791 */       appendTo(paramStringBuffer, paramCalendar.get(2) + 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*  799 */       if (paramInt < 10) {
/*  800 */         paramStringBuffer.append((char)(paramInt + 48));
/*      */       } else {
/*  802 */         paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  803 */         paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class PaddedNumberField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*      */     private final int mField;
/*      */     
/*      */ 
/*      */     private final int mSize;
/*      */     
/*      */ 
/*      */ 
/*      */     PaddedNumberField(int paramInt1, int paramInt2)
/*      */     {
/*  822 */       if (paramInt2 < 3)
/*      */       {
/*  824 */         throw new IllegalArgumentException();
/*      */       }
/*  826 */       this.mField = paramInt1;
/*  827 */       this.mSize = paramInt2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  835 */       return 4;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  843 */       appendTo(paramStringBuffer, paramCalendar.get(this.mField));
/*      */     }
/*      */     
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*      */       int i;
/*      */       
/*  851 */       if (paramInt < 100) {
/*  852 */         i = this.mSize; for (;;) { i--; if (i < 2) break;
/*  853 */           paramStringBuffer.append('0');
/*      */         }
/*  855 */         paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  856 */         paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */       }
/*      */       else {
/*  859 */         if (paramInt < 1000) {
/*  860 */           i = 3;
/*      */         } else {
/*  862 */           Validate.isTrue(paramInt > -1, "Negative values should not be possible", paramInt);
/*  863 */           i = Integer.toString(paramInt).length();
/*      */         }
/*  865 */         int j = this.mSize; for (;;) { j--; if (j < i) break;
/*  866 */           paramStringBuffer.append('0');
/*      */         }
/*  868 */         paramStringBuffer.append(Integer.toString(paramInt));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class TwoDigitNumberField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*      */     private final int mField;
/*      */     
/*      */ 
/*      */ 
/*      */     TwoDigitNumberField(int paramInt)
/*      */     {
/*  885 */       this.mField = paramInt;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  893 */       return 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  901 */       appendTo(paramStringBuffer, paramCalendar.get(this.mField));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*  909 */       if (paramInt < 100) {
/*  910 */         paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  911 */         paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */       } else {
/*  913 */         paramStringBuffer.append(Integer.toString(paramInt));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class TwoDigitYearField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*  922 */     static final TwoDigitYearField INSTANCE = new TwoDigitYearField();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  936 */       return 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  944 */       appendTo(paramStringBuffer, paramCalendar.get(1) % 100);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*  952 */       paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  953 */       paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class TwoDigitMonthField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*  961 */     static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/*  975 */       return 2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/*  983 */       appendTo(paramStringBuffer, paramCalendar.get(2) + 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public final void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/*  991 */       paramStringBuffer.append((char)(paramInt / 10 + 48));
/*  992 */       paramStringBuffer.append((char)(paramInt % 10 + 48));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class TwelveHourField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     TwelveHourField(FastDatePrinter.NumberRule paramNumberRule)
/*      */     {
/* 1009 */       this.mRule = paramNumberRule;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/* 1017 */       return this.mRule.estimateLength();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/* 1025 */       int i = paramCalendar.get(10);
/* 1026 */       if (i == 0) {
/* 1027 */         i = paramCalendar.getLeastMaximum(10) + 1;
/*      */       }
/* 1029 */       this.mRule.appendTo(paramStringBuffer, i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/* 1037 */       this.mRule.appendTo(paramStringBuffer, paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class TwentyFourHourField
/*      */     implements FastDatePrinter.NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     TwentyFourHourField(FastDatePrinter.NumberRule paramNumberRule)
/*      */     {
/* 1054 */       this.mRule = paramNumberRule;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/* 1062 */       return this.mRule.estimateLength();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/* 1070 */       int i = paramCalendar.get(11);
/* 1071 */       if (i == 0) {
/* 1072 */         i = paramCalendar.getMaximum(11) + 1;
/*      */       }
/* 1074 */       this.mRule.appendTo(paramStringBuffer, i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, int paramInt)
/*      */     {
/* 1082 */       this.mRule.appendTo(paramStringBuffer, paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/* 1088 */   private static final ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = new ConcurrentHashMap(7);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String getTimeZoneDisplay(TimeZone paramTimeZone, boolean paramBoolean, int paramInt, Locale paramLocale)
/*      */   {
/* 1100 */     TimeZoneDisplayKey localTimeZoneDisplayKey = new TimeZoneDisplayKey(paramTimeZone, paramBoolean, paramInt, paramLocale);
/* 1101 */     Object localObject = (String)cTimeZoneDisplayCache.get(localTimeZoneDisplayKey);
/* 1102 */     if (localObject == null)
/*      */     {
/* 1104 */       localObject = paramTimeZone.getDisplayName(paramBoolean, paramInt, paramLocale);
/* 1105 */       String str = (String)cTimeZoneDisplayCache.putIfAbsent(localTimeZoneDisplayKey, localObject);
/* 1106 */       if (str != null) {
/* 1107 */         localObject = str;
/*      */       }
/*      */     }
/* 1110 */     return (String)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class TimeZoneNameRule
/*      */     implements FastDatePrinter.Rule
/*      */   {
/*      */     private final Locale mLocale;
/*      */     
/*      */ 
/*      */     private final int mStyle;
/*      */     
/*      */     private final String mStandard;
/*      */     
/*      */     private final String mDaylight;
/*      */     
/*      */ 
/*      */     TimeZoneNameRule(TimeZone paramTimeZone, Locale paramLocale, int paramInt)
/*      */     {
/* 1130 */       this.mLocale = paramLocale;
/* 1131 */       this.mStyle = paramInt;
/*      */       
/* 1133 */       this.mStandard = FastDatePrinter.getTimeZoneDisplay(paramTimeZone, false, paramInt, paramLocale);
/* 1134 */       this.mDaylight = FastDatePrinter.getTimeZoneDisplay(paramTimeZone, true, paramInt, paramLocale);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/* 1145 */       return Math.max(this.mStandard.length(), this.mDaylight.length());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/* 1153 */       TimeZone localTimeZone = paramCalendar.getTimeZone();
/* 1154 */       if ((localTimeZone.useDaylightTime()) && (paramCalendar.get(16) != 0))
/*      */       {
/* 1156 */         paramStringBuffer.append(FastDatePrinter.getTimeZoneDisplay(localTimeZone, true, this.mStyle, this.mLocale));
/*      */       } else {
/* 1158 */         paramStringBuffer.append(FastDatePrinter.getTimeZoneDisplay(localTimeZone, false, this.mStyle, this.mLocale));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class TimeZoneNumberRule
/*      */     implements FastDatePrinter.Rule
/*      */   {
/* 1168 */     static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
/* 1169 */     static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
/*      */     
/*      */ 
/*      */ 
/*      */     final boolean mColon;
/*      */     
/*      */ 
/*      */ 
/*      */     TimeZoneNumberRule(boolean paramBoolean)
/*      */     {
/* 1179 */       this.mColon = paramBoolean;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int estimateLength()
/*      */     {
/* 1187 */       return 5;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public void appendTo(StringBuffer paramStringBuffer, Calendar paramCalendar)
/*      */     {
/* 1195 */       int i = paramCalendar.get(15) + paramCalendar.get(16);
/*      */       
/* 1197 */       if (i < 0) {
/* 1198 */         paramStringBuffer.append('-');
/* 1199 */         i = -i;
/*      */       } else {
/* 1201 */         paramStringBuffer.append('+');
/*      */       }
/*      */       
/* 1204 */       int j = i / 3600000;
/* 1205 */       paramStringBuffer.append((char)(j / 10 + 48));
/* 1206 */       paramStringBuffer.append((char)(j % 10 + 48));
/*      */       
/* 1208 */       if (this.mColon) {
/* 1209 */         paramStringBuffer.append(':');
/*      */       }
/*      */       
/* 1212 */       int k = i / 60000 - 60 * j;
/* 1213 */       paramStringBuffer.append((char)(k / 10 + 48));
/* 1214 */       paramStringBuffer.append((char)(k % 10 + 48));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class TimeZoneDisplayKey
/*      */   {
/*      */     private final TimeZone mTimeZone;
/*      */     
/*      */ 
/*      */ 
/*      */     private final int mStyle;
/*      */     
/*      */ 
/*      */ 
/*      */     private final Locale mLocale;
/*      */     
/*      */ 
/*      */ 
/*      */     TimeZoneDisplayKey(TimeZone paramTimeZone, boolean paramBoolean, int paramInt, Locale paramLocale)
/*      */     {
/* 1237 */       this.mTimeZone = paramTimeZone;
/* 1238 */       if (paramBoolean) {
/* 1239 */         this.mStyle = (paramInt | 0x80000000);
/*      */       } else {
/* 1241 */         this.mStyle = paramInt;
/*      */       }
/* 1243 */       this.mLocale = paramLocale;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 1251 */       return (this.mStyle * 31 + this.mLocale.hashCode()) * 31 + this.mTimeZone.hashCode();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/* 1259 */       if (this == paramObject) {
/* 1260 */         return true;
/*      */       }
/* 1262 */       if ((paramObject instanceof TimeZoneDisplayKey)) {
/* 1263 */         TimeZoneDisplayKey localTimeZoneDisplayKey = (TimeZoneDisplayKey)paramObject;
/* 1264 */         return (this.mTimeZone.equals(localTimeZoneDisplayKey.mTimeZone)) && (this.mStyle == localTimeZoneDisplayKey.mStyle) && (this.mLocale.equals(localTimeZoneDisplayKey.mLocale));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1269 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\FastDatePrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */