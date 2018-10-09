/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DurationFormatUtils
/*     */ {
/*     */   public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
/*     */   
/*     */   public static String formatDurationHMS(long paramLong)
/*     */   {
/*  82 */     return formatDuration(paramLong, "H:mm:ss.SSS");
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
/*     */   public static String formatDurationISO(long paramLong)
/*     */   {
/*  97 */     return formatDuration(paramLong, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
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
/*     */   public static String formatDuration(long paramLong, String paramString)
/*     */   {
/* 111 */     return formatDuration(paramLong, paramString, true);
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
/*     */   public static String formatDuration(long paramLong, String paramString, boolean paramBoolean)
/*     */   {
/* 128 */     Token[] arrayOfToken = lexx(paramString);
/*     */     
/* 130 */     long l1 = 0L;
/* 131 */     long l2 = 0L;
/* 132 */     long l3 = 0L;
/* 133 */     long l4 = 0L;
/* 134 */     long l5 = paramLong;
/*     */     
/* 136 */     if (Token.containsTokenWithValue(arrayOfToken, d)) {
/* 137 */       l1 = l5 / 86400000L;
/* 138 */       l5 -= l1 * 86400000L;
/*     */     }
/* 140 */     if (Token.containsTokenWithValue(arrayOfToken, H)) {
/* 141 */       l2 = l5 / 3600000L;
/* 142 */       l5 -= l2 * 3600000L;
/*     */     }
/* 144 */     if (Token.containsTokenWithValue(arrayOfToken, m)) {
/* 145 */       l3 = l5 / 60000L;
/* 146 */       l5 -= l3 * 60000L;
/*     */     }
/* 148 */     if (Token.containsTokenWithValue(arrayOfToken, s)) {
/* 149 */       l4 = l5 / 1000L;
/* 150 */       l5 -= l4 * 1000L;
/*     */     }
/*     */     
/* 153 */     return format(arrayOfToken, 0L, 0L, l1, l2, l3, l4, l5, paramBoolean);
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
/*     */   public static String formatDurationWords(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 175 */     Object localObject = formatDuration(paramLong, "d' days 'H' hours 'm' minutes 's' seconds'");
/* 176 */     String str; if (paramBoolean1)
/*     */     {
/* 178 */       localObject = " " + (String)localObject;
/* 179 */       str = StringUtils.replaceOnce((String)localObject, " 0 days", "");
/* 180 */       if (str.length() != ((String)localObject).length()) {
/* 181 */         localObject = str;
/* 182 */         str = StringUtils.replaceOnce((String)localObject, " 0 hours", "");
/* 183 */         if (str.length() != ((String)localObject).length()) {
/* 184 */           localObject = str;
/* 185 */           str = StringUtils.replaceOnce((String)localObject, " 0 minutes", "");
/* 186 */           localObject = str;
/* 187 */           if (str.length() != ((String)localObject).length()) {
/* 188 */             localObject = StringUtils.replaceOnce(str, " 0 seconds", "");
/*     */           }
/*     */         }
/*     */       }
/* 192 */       if (((String)localObject).length() != 0)
/*     */       {
/* 194 */         localObject = ((String)localObject).substring(1);
/*     */       }
/*     */     }
/* 197 */     if (paramBoolean2) {
/* 198 */       str = StringUtils.replaceOnce((String)localObject, " 0 seconds", "");
/* 199 */       if (str.length() != ((String)localObject).length()) {
/* 200 */         localObject = str;
/* 201 */         str = StringUtils.replaceOnce((String)localObject, " 0 minutes", "");
/* 202 */         if (str.length() != ((String)localObject).length()) {
/* 203 */           localObject = str;
/* 204 */           str = StringUtils.replaceOnce((String)localObject, " 0 hours", "");
/* 205 */           if (str.length() != ((String)localObject).length()) {
/* 206 */             localObject = StringUtils.replaceOnce(str, " 0 days", "");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 212 */     localObject = " " + (String)localObject;
/* 213 */     localObject = StringUtils.replaceOnce((String)localObject, " 1 seconds", " 1 second");
/* 214 */     localObject = StringUtils.replaceOnce((String)localObject, " 1 minutes", " 1 minute");
/* 215 */     localObject = StringUtils.replaceOnce((String)localObject, " 1 hours", " 1 hour");
/* 216 */     localObject = StringUtils.replaceOnce((String)localObject, " 1 days", " 1 day");
/* 217 */     return ((String)localObject).trim();
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
/*     */   public static String formatPeriodISO(long paramLong1, long paramLong2)
/*     */   {
/* 231 */     return formatPeriod(paramLong1, paramLong2, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
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
/*     */   public static String formatPeriod(long paramLong1, long paramLong2, String paramString)
/*     */   {
/* 244 */     return formatPeriod(paramLong1, paramLong2, paramString, true, TimeZone.getDefault());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String formatPeriod(long paramLong1, long paramLong2, String paramString, boolean paramBoolean, TimeZone paramTimeZone)
/*     */   {
/* 279 */     Token[] arrayOfToken = lexx(paramString);
/*     */     
/*     */ 
/*     */ 
/* 283 */     Calendar localCalendar1 = Calendar.getInstance(paramTimeZone);
/* 284 */     localCalendar1.setTime(new Date(paramLong1));
/* 285 */     Calendar localCalendar2 = Calendar.getInstance(paramTimeZone);
/* 286 */     localCalendar2.setTime(new Date(paramLong2));
/*     */     
/*     */ 
/* 289 */     int i = localCalendar2.get(14) - localCalendar1.get(14);
/* 290 */     int j = localCalendar2.get(13) - localCalendar1.get(13);
/* 291 */     int k = localCalendar2.get(12) - localCalendar1.get(12);
/* 292 */     int n = localCalendar2.get(11) - localCalendar1.get(11);
/* 293 */     int i1 = localCalendar2.get(5) - localCalendar1.get(5);
/* 294 */     int i2 = localCalendar2.get(2) - localCalendar1.get(2);
/* 295 */     int i3 = localCalendar2.get(1) - localCalendar1.get(1);
/*     */     
/*     */ 
/* 298 */     while (i < 0) {
/* 299 */       i += 1000;
/* 300 */       j--;
/*     */     }
/* 302 */     while (j < 0) {
/* 303 */       j += 60;
/* 304 */       k--;
/*     */     }
/* 306 */     while (k < 0) {
/* 307 */       k += 60;
/* 308 */       n--;
/*     */     }
/* 310 */     while (n < 0) {
/* 311 */       n += 24;
/* 312 */       i1--;
/*     */     }
/*     */     
/* 315 */     if (Token.containsTokenWithValue(arrayOfToken, M)) {
/* 316 */       while (i1 < 0) {
/* 317 */         i1 += localCalendar1.getActualMaximum(5);
/* 318 */         i2--;
/* 319 */         localCalendar1.add(2, 1);
/*     */       }
/*     */       
/* 322 */       while (i2 < 0) {
/* 323 */         i2 += 12;
/* 324 */         i3--;
/*     */       }
/*     */       
/* 327 */       if ((!Token.containsTokenWithValue(arrayOfToken, y)) && (i3 != 0)) {
/* 328 */         while (i3 != 0) {
/* 329 */           i2 += 12 * i3;
/* 330 */           i3 = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 336 */       if (!Token.containsTokenWithValue(arrayOfToken, y)) {
/* 337 */         int i4 = localCalendar2.get(1);
/* 338 */         if (i2 < 0)
/*     */         {
/* 340 */           i4--;
/*     */         }
/*     */         
/* 343 */         while (localCalendar1.get(1) != i4) {
/* 344 */           i1 += localCalendar1.getActualMaximum(6) - localCalendar1.get(6);
/*     */           
/*     */ 
/* 347 */           if (((localCalendar1 instanceof GregorianCalendar)) && (localCalendar1.get(2) == 1) && (localCalendar1.get(5) == 29))
/*     */           {
/*     */ 
/* 350 */             i1++;
/*     */           }
/*     */           
/* 353 */           localCalendar1.add(1, 1);
/*     */           
/* 355 */           i1 += localCalendar1.get(6);
/*     */         }
/*     */         
/* 358 */         i3 = 0;
/*     */       }
/*     */       
/* 361 */       while (localCalendar1.get(2) != localCalendar2.get(2)) {
/* 362 */         i1 += localCalendar1.getActualMaximum(5);
/* 363 */         localCalendar1.add(2, 1);
/*     */       }
/*     */       
/* 366 */       i2 = 0;
/*     */       
/* 368 */       while (i1 < 0) {
/* 369 */         i1 += localCalendar1.getActualMaximum(5);
/* 370 */         i2--;
/* 371 */         localCalendar1.add(2, 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 380 */     if (!Token.containsTokenWithValue(arrayOfToken, d)) {
/* 381 */       n += 24 * i1;
/* 382 */       i1 = 0;
/*     */     }
/* 384 */     if (!Token.containsTokenWithValue(arrayOfToken, H)) {
/* 385 */       k += 60 * n;
/* 386 */       n = 0;
/*     */     }
/* 388 */     if (!Token.containsTokenWithValue(arrayOfToken, m)) {
/* 389 */       j += 60 * k;
/* 390 */       k = 0;
/*     */     }
/* 392 */     if (!Token.containsTokenWithValue(arrayOfToken, s)) {
/* 393 */       i += 1000 * j;
/* 394 */       j = 0;
/*     */     }
/*     */     
/* 397 */     return format(arrayOfToken, i3, i2, i1, n, k, j, i, paramBoolean);
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
/*     */   static String format(Token[] paramArrayOfToken, long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, long paramLong6, long paramLong7, boolean paramBoolean)
/*     */   {
/* 417 */     StringBuilder localStringBuilder = new StringBuilder();
/* 418 */     int i = 0;
/* 419 */     int j = paramArrayOfToken.length;
/* 420 */     for (int k = 0; k < j; k++) {
/* 421 */       Token localToken = paramArrayOfToken[k];
/* 422 */       Object localObject = localToken.getValue();
/* 423 */       int n = localToken.getCount();
/* 424 */       if ((localObject instanceof StringBuilder)) {
/* 425 */         localStringBuilder.append(localObject.toString());
/*     */       }
/* 427 */       else if (localObject == y) {
/* 428 */         localStringBuilder.append(paddedValue(paramLong1, paramBoolean, n));
/* 429 */         i = 0;
/* 430 */       } else if (localObject == M) {
/* 431 */         localStringBuilder.append(paddedValue(paramLong2, paramBoolean, n));
/* 432 */         i = 0;
/* 433 */       } else if (localObject == d) {
/* 434 */         localStringBuilder.append(paddedValue(paramLong3, paramBoolean, n));
/* 435 */         i = 0;
/* 436 */       } else if (localObject == H) {
/* 437 */         localStringBuilder.append(paddedValue(paramLong4, paramBoolean, n));
/* 438 */         i = 0;
/* 439 */       } else if (localObject == m) {
/* 440 */         localStringBuilder.append(paddedValue(paramLong5, paramBoolean, n));
/* 441 */         i = 0;
/* 442 */       } else if (localObject == s) {
/* 443 */         localStringBuilder.append(paddedValue(paramLong6, paramBoolean, n));
/* 444 */         i = 1;
/* 445 */       } else if (localObject == S) {
/* 446 */         if (i != 0)
/*     */         {
/* 448 */           int i1 = paramBoolean ? Math.max(3, n) : 3;
/* 449 */           localStringBuilder.append(paddedValue(paramLong7, true, i1));
/*     */         } else {
/* 451 */           localStringBuilder.append(paddedValue(paramLong7, paramBoolean, n));
/*     */         }
/* 453 */         i = 0;
/*     */       }
/*     */     }
/*     */     
/* 457 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static String paddedValue(long paramLong, boolean paramBoolean, int paramInt)
/*     */   {
/* 462 */     String str = Long.toString(paramLong);
/* 463 */     return paramBoolean ? StringUtils.leftPad(str, paramInt, '0') : str;
/*     */   }
/*     */   
/* 466 */   static final Object y = "y";
/* 467 */   static final Object M = "M";
/* 468 */   static final Object d = "d";
/* 469 */   static final Object H = "H";
/* 470 */   static final Object m = "m";
/* 471 */   static final Object s = "s";
/* 472 */   static final Object S = "S";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Token[] lexx(String paramString)
/*     */   {
/* 481 */     ArrayList localArrayList = new ArrayList(paramString.length());
/*     */     
/* 483 */     int i = 0;
/*     */     
/*     */ 
/* 486 */     StringBuilder localStringBuilder = null;
/* 487 */     Object localObject1 = null;
/* 488 */     for (int j = 0; j < paramString.length(); j++) {
/* 489 */       char c = paramString.charAt(j);
/* 490 */       if ((i != 0) && (c != '\'')) {
/* 491 */         localStringBuilder.append(c);
/*     */       }
/*     */       else {
/* 494 */         Object localObject2 = null;
/* 495 */         switch (c)
/*     */         {
/*     */         case '\'': 
/* 498 */           if (i != 0) {
/* 499 */             localStringBuilder = null;
/* 500 */             i = 0;
/*     */           } else {
/* 502 */             localStringBuilder = new StringBuilder();
/* 503 */             localArrayList.add(new Token(localStringBuilder));
/* 504 */             i = 1;
/*     */           }
/* 506 */           break;
/*     */         case 'y': 
/* 508 */           localObject2 = y;
/* 509 */           break;
/*     */         case 'M': 
/* 511 */           localObject2 = M;
/* 512 */           break;
/*     */         case 'd': 
/* 514 */           localObject2 = d;
/* 515 */           break;
/*     */         case 'H': 
/* 517 */           localObject2 = H;
/* 518 */           break;
/*     */         case 'm': 
/* 520 */           localObject2 = m;
/* 521 */           break;
/*     */         case 's': 
/* 523 */           localObject2 = s;
/* 524 */           break;
/*     */         case 'S': 
/* 526 */           localObject2 = S;
/* 527 */           break;
/*     */         default: 
/* 529 */           if (localStringBuilder == null) {
/* 530 */             localStringBuilder = new StringBuilder();
/* 531 */             localArrayList.add(new Token(localStringBuilder));
/*     */           }
/* 533 */           localStringBuilder.append(c);
/*     */         }
/*     */         
/* 536 */         if (localObject2 != null) {
/* 537 */           if ((localObject1 != null) && (((Token)localObject1).getValue() == localObject2)) {
/* 538 */             ((Token)localObject1).increment();
/*     */           } else {
/* 540 */             Token localToken = new Token(localObject2);
/* 541 */             localArrayList.add(localToken);
/* 542 */             localObject1 = localToken;
/*     */           }
/* 544 */           localStringBuilder = null;
/*     */         }
/*     */       } }
/* 547 */     if (i != 0) {
/* 548 */       throw new IllegalArgumentException("Unmatched quote in format: " + paramString);
/*     */     }
/* 550 */     return (Token[])localArrayList.toArray(new Token[localArrayList.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static class Token
/*     */   {
/*     */     private final Object value;
/*     */     
/*     */ 
/*     */     private int count;
/*     */     
/*     */ 
/*     */ 
/*     */     static boolean containsTokenWithValue(Token[] paramArrayOfToken, Object paramObject)
/*     */     {
/* 567 */       int i = paramArrayOfToken.length;
/* 568 */       for (int j = 0; j < i; j++) {
/* 569 */         if (paramArrayOfToken[j].getValue() == paramObject) {
/* 570 */           return true;
/*     */         }
/*     */       }
/* 573 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Token(Object paramObject)
/*     */     {
/* 585 */       this.value = paramObject;
/* 586 */       this.count = 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Token(Object paramObject, int paramInt)
/*     */     {
/* 597 */       this.value = paramObject;
/* 598 */       this.count = paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     void increment()
/*     */     {
/* 605 */       this.count += 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     int getCount()
/*     */     {
/* 614 */       return this.count;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Object getValue()
/*     */     {
/* 623 */       return this.value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 634 */       if ((paramObject instanceof Token)) {
/* 635 */         Token localToken = (Token)paramObject;
/* 636 */         if (this.value.getClass() != localToken.value.getClass()) {
/* 637 */           return false;
/*     */         }
/* 639 */         if (this.count != localToken.count) {
/* 640 */           return false;
/*     */         }
/* 642 */         if ((this.value instanceof StringBuilder))
/* 643 */           return this.value.toString().equals(localToken.value.toString());
/* 644 */         if ((this.value instanceof Number)) {
/* 645 */           return this.value.equals(localToken.value);
/*     */         }
/* 647 */         return this.value == localToken.value;
/*     */       }
/*     */       
/* 650 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 662 */       return this.value.hashCode();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 672 */       return StringUtils.repeat(this.value.toString(), this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\DurationFormatUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */