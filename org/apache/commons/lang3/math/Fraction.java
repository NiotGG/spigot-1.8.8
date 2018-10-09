/*     */ package org.apache.commons.lang3.math;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Fraction
/*     */   extends Number
/*     */   implements Comparable<Fraction>
/*     */ {
/*     */   private static final long serialVersionUID = 65382027393090L;
/*  47 */   public static final Fraction ZERO = new Fraction(0, 1);
/*     */   
/*     */ 
/*     */ 
/*  51 */   public static final Fraction ONE = new Fraction(1, 1);
/*     */   
/*     */ 
/*     */ 
/*  55 */   public static final Fraction ONE_HALF = new Fraction(1, 2);
/*     */   
/*     */ 
/*     */ 
/*  59 */   public static final Fraction ONE_THIRD = new Fraction(1, 3);
/*     */   
/*     */ 
/*     */ 
/*  63 */   public static final Fraction TWO_THIRDS = new Fraction(2, 3);
/*     */   
/*     */ 
/*     */ 
/*  67 */   public static final Fraction ONE_QUARTER = new Fraction(1, 4);
/*     */   
/*     */ 
/*     */ 
/*  71 */   public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
/*     */   
/*     */ 
/*     */ 
/*  75 */   public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
/*     */   
/*     */ 
/*     */ 
/*  79 */   public static final Fraction ONE_FIFTH = new Fraction(1, 5);
/*     */   
/*     */ 
/*     */ 
/*  83 */   public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
/*     */   
/*     */ 
/*     */ 
/*  87 */   public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
/*     */   
/*     */ 
/*     */ 
/*  91 */   public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int numerator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int denominator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 106 */   private transient int hashCode = 0;
/*     */   
/*     */ 
/*     */ 
/* 110 */   private transient String toString = null;
/*     */   
/*     */ 
/*     */ 
/* 114 */   private transient String toProperString = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Fraction(int paramInt1, int paramInt2)
/*     */   {
/* 125 */     this.numerator = paramInt1;
/* 126 */     this.denominator = paramInt2;
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
/*     */   public static Fraction getFraction(int paramInt1, int paramInt2)
/*     */   {
/* 142 */     if (paramInt2 == 0) {
/* 143 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 145 */     if (paramInt2 < 0) {
/* 146 */       if ((paramInt1 == Integer.MIN_VALUE) || (paramInt2 == Integer.MIN_VALUE))
/*     */       {
/* 148 */         throw new ArithmeticException("overflow: can't negate");
/*     */       }
/* 150 */       paramInt1 = -paramInt1;
/* 151 */       paramInt2 = -paramInt2;
/*     */     }
/* 153 */     return new Fraction(paramInt1, paramInt2);
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
/*     */   public static Fraction getFraction(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 173 */     if (paramInt3 == 0) {
/* 174 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 176 */     if (paramInt3 < 0) {
/* 177 */       throw new ArithmeticException("The denominator must not be negative");
/*     */     }
/* 179 */     if (paramInt2 < 0) {
/* 180 */       throw new ArithmeticException("The numerator must not be negative");
/*     */     }
/*     */     long l;
/* 183 */     if (paramInt1 < 0) {
/* 184 */       l = paramInt1 * paramInt3 - paramInt2;
/*     */     } else {
/* 186 */       l = paramInt1 * paramInt3 + paramInt2;
/*     */     }
/* 188 */     if ((l < -2147483648L) || (l > 2147483647L))
/*     */     {
/* 190 */       throw new ArithmeticException("Numerator too large to represent as an Integer.");
/*     */     }
/* 192 */     return new Fraction((int)l, paramInt3);
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
/*     */   public static Fraction getReducedFraction(int paramInt1, int paramInt2)
/*     */   {
/* 210 */     if (paramInt2 == 0) {
/* 211 */       throw new ArithmeticException("The denominator must not be zero");
/*     */     }
/* 213 */     if (paramInt1 == 0) {
/* 214 */       return ZERO;
/*     */     }
/*     */     
/* 217 */     if ((paramInt2 == Integer.MIN_VALUE) && ((paramInt1 & 0x1) == 0)) {
/* 218 */       paramInt1 /= 2;paramInt2 /= 2;
/*     */     }
/* 220 */     if (paramInt2 < 0) {
/* 221 */       if ((paramInt1 == Integer.MIN_VALUE) || (paramInt2 == Integer.MIN_VALUE))
/*     */       {
/* 223 */         throw new ArithmeticException("overflow: can't negate");
/*     */       }
/* 225 */       paramInt1 = -paramInt1;
/* 226 */       paramInt2 = -paramInt2;
/*     */     }
/*     */     
/* 229 */     int i = greatestCommonDivisor(paramInt1, paramInt2);
/* 230 */     paramInt1 /= i;
/* 231 */     paramInt2 /= i;
/* 232 */     return new Fraction(paramInt1, paramInt2);
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
/*     */   public static Fraction getFraction(double paramDouble)
/*     */   {
/* 250 */     int i = paramDouble < 0.0D ? -1 : 1;
/* 251 */     paramDouble = Math.abs(paramDouble);
/* 252 */     if ((paramDouble > 2.147483647E9D) || (Double.isNaN(paramDouble))) {
/* 253 */       throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
/*     */     }
/*     */     
/* 256 */     int j = (int)paramDouble;
/* 257 */     paramDouble -= j;
/*     */     
/* 259 */     int k = 0;
/* 260 */     int m = 1;
/* 261 */     int n = 1;
/* 262 */     int i1 = 0;
/* 263 */     int i2 = 0;
/* 264 */     int i3 = 0;
/* 265 */     int i4 = (int)paramDouble;
/* 266 */     int i5 = 0;
/* 267 */     double d1 = 1.0D;
/* 268 */     double d2 = 0.0D;
/* 269 */     double d3 = paramDouble - i4;
/* 270 */     double d4 = 0.0D;
/* 271 */     double d5 = Double.MAX_VALUE;
/*     */     
/* 273 */     int i6 = 1;
/*     */     double d6;
/*     */     do {
/* 276 */       d6 = d5;
/* 277 */       i5 = (int)(d1 / d3);
/* 278 */       d2 = d3;
/* 279 */       d4 = d1 - i5 * d3;
/* 280 */       i2 = i4 * n + k;
/* 281 */       i3 = i4 * i1 + m;
/* 282 */       double d7 = i2 / i3;
/* 283 */       d5 = Math.abs(paramDouble - d7);
/*     */       
/* 285 */       i4 = i5;
/* 286 */       d1 = d2;
/* 287 */       d3 = d4;
/* 288 */       k = n;
/* 289 */       m = i1;
/* 290 */       n = i2;
/* 291 */       i1 = i3;
/* 292 */       i6++;
/*     */     }
/* 294 */     while ((d6 > d5) && (i3 <= 10000) && (i3 > 0) && (i6 < 25));
/* 295 */     if (i6 == 25) {
/* 296 */       throw new ArithmeticException("Unable to convert double to fraction");
/*     */     }
/* 298 */     return getReducedFraction((k + j * m) * i, m);
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
/*     */   public static Fraction getFraction(String paramString)
/*     */   {
/* 320 */     if (paramString == null) {
/* 321 */       throw new IllegalArgumentException("The string must not be null");
/*     */     }
/*     */     
/* 324 */     int i = paramString.indexOf('.');
/* 325 */     if (i >= 0) {
/* 326 */       return getFraction(Double.parseDouble(paramString));
/*     */     }
/*     */     
/*     */ 
/* 330 */     i = paramString.indexOf(' ');
/* 331 */     if (i > 0) {
/* 332 */       j = Integer.parseInt(paramString.substring(0, i));
/* 333 */       paramString = paramString.substring(i + 1);
/* 334 */       i = paramString.indexOf('/');
/* 335 */       if (i < 0) {
/* 336 */         throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
/*     */       }
/* 338 */       k = Integer.parseInt(paramString.substring(0, i));
/* 339 */       int m = Integer.parseInt(paramString.substring(i + 1));
/* 340 */       return getFraction(j, k, m);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 345 */     i = paramString.indexOf('/');
/* 346 */     if (i < 0)
/*     */     {
/* 348 */       return getFraction(Integer.parseInt(paramString), 1);
/*     */     }
/* 350 */     int j = Integer.parseInt(paramString.substring(0, i));
/* 351 */     int k = Integer.parseInt(paramString.substring(i + 1));
/* 352 */     return getFraction(j, k);
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
/*     */   public int getNumerator()
/*     */   {
/* 368 */     return this.numerator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDenominator()
/*     */   {
/* 377 */     return this.denominator;
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
/*     */   public int getProperNumerator()
/*     */   {
/* 392 */     return Math.abs(this.numerator % this.denominator);
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
/*     */   public int getProperWhole()
/*     */   {
/* 407 */     return this.numerator / this.denominator;
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
/*     */   public int intValue()
/*     */   {
/* 421 */     return this.numerator / this.denominator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 432 */     return this.numerator / this.denominator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 443 */     return this.numerator / this.denominator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 454 */     return this.numerator / this.denominator;
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
/*     */   public Fraction reduce()
/*     */   {
/* 470 */     if (this.numerator == 0) {
/* 471 */       return equals(ZERO) ? this : ZERO;
/*     */     }
/* 473 */     int i = greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
/* 474 */     if (i == 1) {
/* 475 */       return this;
/*     */     }
/* 477 */     return getFraction(this.numerator / i, this.denominator / i);
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
/*     */   public Fraction invert()
/*     */   {
/* 490 */     if (this.numerator == 0) {
/* 491 */       throw new ArithmeticException("Unable to invert zero.");
/*     */     }
/* 493 */     if (this.numerator == Integer.MIN_VALUE) {
/* 494 */       throw new ArithmeticException("overflow: can't negate numerator");
/*     */     }
/* 496 */     if (this.numerator < 0) {
/* 497 */       return new Fraction(-this.denominator, -this.numerator);
/*     */     }
/* 499 */     return new Fraction(this.denominator, this.numerator);
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
/*     */   public Fraction negate()
/*     */   {
/* 512 */     if (this.numerator == Integer.MIN_VALUE) {
/* 513 */       throw new ArithmeticException("overflow: too large to negate");
/*     */     }
/* 515 */     return new Fraction(-this.numerator, this.denominator);
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
/*     */   public Fraction abs()
/*     */   {
/* 528 */     if (this.numerator >= 0) {
/* 529 */       return this;
/*     */     }
/* 531 */     return negate();
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
/*     */   public Fraction pow(int paramInt)
/*     */   {
/* 547 */     if (paramInt == 1)
/* 548 */       return this;
/* 549 */     if (paramInt == 0)
/* 550 */       return ONE;
/* 551 */     if (paramInt < 0) {
/* 552 */       if (paramInt == Integer.MIN_VALUE) {
/* 553 */         return invert().pow(2).pow(-(paramInt / 2));
/*     */       }
/* 555 */       return invert().pow(-paramInt);
/*     */     }
/* 557 */     Fraction localFraction = multiplyBy(this);
/* 558 */     if (paramInt % 2 == 0) {
/* 559 */       return localFraction.pow(paramInt / 2);
/*     */     }
/* 561 */     return localFraction.pow(paramInt / 2).multiplyBy(this);
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
/*     */   private static int greatestCommonDivisor(int paramInt1, int paramInt2)
/*     */   {
/* 578 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 579 */       if ((paramInt1 == Integer.MIN_VALUE) || (paramInt2 == Integer.MIN_VALUE)) {
/* 580 */         throw new ArithmeticException("overflow: gcd is 2^31");
/*     */       }
/* 582 */       return Math.abs(paramInt1) + Math.abs(paramInt2);
/*     */     }
/*     */     
/* 585 */     if ((Math.abs(paramInt1) == 1) || (Math.abs(paramInt2) == 1)) {
/* 586 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 592 */     if (paramInt1 > 0) paramInt1 = -paramInt1;
/* 593 */     if (paramInt2 > 0) { paramInt2 = -paramInt2;
/*     */     }
/* 595 */     for (int i = 0; 
/* 596 */         ((paramInt1 & 0x1) == 0) && ((paramInt2 & 0x1) == 0) && (i < 31); 
/* 597 */         i++) { paramInt1 /= 2;paramInt2 /= 2;
/*     */     }
/* 599 */     if (i == 31) {
/* 600 */       throw new ArithmeticException("overflow: gcd is 2^31");
/*     */     }
/*     */     
/*     */ 
/* 604 */     int j = (paramInt1 & 0x1) == 1 ? paramInt2 : -(paramInt1 / 2);
/*     */     
/*     */ 
/*     */ 
/*     */     do
/*     */     {
/* 610 */       while ((j & 0x1) == 0) {
/* 611 */         j /= 2;
/*     */       }
/*     */       
/* 614 */       if (j > 0) {
/* 615 */         paramInt1 = -j;
/*     */       } else {
/* 617 */         paramInt2 = j;
/*     */       }
/*     */       
/* 620 */       j = (paramInt2 - paramInt1) / 2;
/*     */ 
/*     */     }
/* 623 */     while (j != 0);
/* 624 */     return -paramInt1 * (1 << i);
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
/*     */   private static int mulAndCheck(int paramInt1, int paramInt2)
/*     */   {
/* 640 */     long l = paramInt1 * paramInt2;
/* 641 */     if ((l < -2147483648L) || (l > 2147483647L))
/*     */     {
/* 643 */       throw new ArithmeticException("overflow: mul");
/*     */     }
/* 645 */     return (int)l;
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
/*     */   private static int mulPosAndCheck(int paramInt1, int paramInt2)
/*     */   {
/* 659 */     long l = paramInt1 * paramInt2;
/* 660 */     if (l > 2147483647L) {
/* 661 */       throw new ArithmeticException("overflow: mulPos");
/*     */     }
/* 663 */     return (int)l;
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
/*     */   private static int addAndCheck(int paramInt1, int paramInt2)
/*     */   {
/* 676 */     long l = paramInt1 + paramInt2;
/* 677 */     if ((l < -2147483648L) || (l > 2147483647L))
/*     */     {
/* 679 */       throw new ArithmeticException("overflow: add");
/*     */     }
/* 681 */     return (int)l;
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
/*     */   private static int subAndCheck(int paramInt1, int paramInt2)
/*     */   {
/* 694 */     long l = paramInt1 - paramInt2;
/* 695 */     if ((l < -2147483648L) || (l > 2147483647L))
/*     */     {
/* 697 */       throw new ArithmeticException("overflow: add");
/*     */     }
/* 699 */     return (int)l;
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
/*     */   public Fraction add(Fraction paramFraction)
/*     */   {
/* 713 */     return addSub(paramFraction, true);
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
/*     */   public Fraction subtract(Fraction paramFraction)
/*     */   {
/* 727 */     return addSub(paramFraction, false);
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
/*     */   private Fraction addSub(Fraction paramFraction, boolean paramBoolean)
/*     */   {
/* 741 */     if (paramFraction == null) {
/* 742 */       throw new IllegalArgumentException("The fraction must not be null");
/*     */     }
/*     */     
/* 745 */     if (this.numerator == 0) {
/* 746 */       return paramBoolean ? paramFraction : paramFraction.negate();
/*     */     }
/* 748 */     if (paramFraction.numerator == 0) {
/* 749 */       return this;
/*     */     }
/*     */     
/*     */ 
/* 753 */     int i = greatestCommonDivisor(this.denominator, paramFraction.denominator);
/* 754 */     if (i == 1)
/*     */     {
/* 756 */       int j = mulAndCheck(this.numerator, paramFraction.denominator);
/* 757 */       int k = mulAndCheck(paramFraction.numerator, this.denominator);
/* 758 */       return new Fraction(paramBoolean ? addAndCheck(j, k) : subAndCheck(j, k), mulPosAndCheck(this.denominator, paramFraction.denominator));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 765 */     BigInteger localBigInteger1 = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(paramFraction.denominator / i));
/*     */     
/* 767 */     BigInteger localBigInteger2 = BigInteger.valueOf(paramFraction.numerator).multiply(BigInteger.valueOf(this.denominator / i));
/*     */     
/* 769 */     BigInteger localBigInteger3 = paramBoolean ? localBigInteger1.add(localBigInteger2) : localBigInteger1.subtract(localBigInteger2);
/*     */     
/*     */ 
/* 772 */     int m = localBigInteger3.mod(BigInteger.valueOf(i)).intValue();
/* 773 */     int n = m == 0 ? i : greatestCommonDivisor(m, i);
/*     */     
/*     */ 
/* 776 */     BigInteger localBigInteger4 = localBigInteger3.divide(BigInteger.valueOf(n));
/* 777 */     if (localBigInteger4.bitLength() > 31) {
/* 778 */       throw new ArithmeticException("overflow: numerator too large after multiply");
/*     */     }
/*     */     
/* 781 */     return new Fraction(localBigInteger4.intValue(), mulPosAndCheck(this.denominator / i, paramFraction.denominator / n));
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
/*     */   public Fraction multiplyBy(Fraction paramFraction)
/*     */   {
/* 797 */     if (paramFraction == null) {
/* 798 */       throw new IllegalArgumentException("The fraction must not be null");
/*     */     }
/* 800 */     if ((this.numerator == 0) || (paramFraction.numerator == 0)) {
/* 801 */       return ZERO;
/*     */     }
/*     */     
/*     */ 
/* 805 */     int i = greatestCommonDivisor(this.numerator, paramFraction.denominator);
/* 806 */     int j = greatestCommonDivisor(paramFraction.numerator, this.denominator);
/* 807 */     return getReducedFraction(mulAndCheck(this.numerator / i, paramFraction.numerator / j), mulPosAndCheck(this.denominator / j, paramFraction.denominator / i));
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
/*     */   public Fraction divideBy(Fraction paramFraction)
/*     */   {
/* 823 */     if (paramFraction == null) {
/* 824 */       throw new IllegalArgumentException("The fraction must not be null");
/*     */     }
/* 826 */     if (paramFraction.numerator == 0) {
/* 827 */       throw new ArithmeticException("The fraction to divide by must not be zero");
/*     */     }
/* 829 */     return multiplyBy(paramFraction.invert());
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 845 */     if (paramObject == this) {
/* 846 */       return true;
/*     */     }
/* 848 */     if (!(paramObject instanceof Fraction)) {
/* 849 */       return false;
/*     */     }
/* 851 */     Fraction localFraction = (Fraction)paramObject;
/* 852 */     return (getNumerator() == localFraction.getNumerator()) && (getDenominator() == localFraction.getDenominator());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 863 */     if (this.hashCode == 0)
/*     */     {
/* 865 */       this.hashCode = (37 * (629 + getNumerator()) + getDenominator());
/*     */     }
/* 867 */     return this.hashCode;
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
/*     */   public int compareTo(Fraction paramFraction)
/*     */   {
/* 884 */     if (this == paramFraction) {
/* 885 */       return 0;
/*     */     }
/* 887 */     if ((this.numerator == paramFraction.numerator) && (this.denominator == paramFraction.denominator)) {
/* 888 */       return 0;
/*     */     }
/*     */     
/*     */ 
/* 892 */     long l1 = this.numerator * paramFraction.denominator;
/* 893 */     long l2 = paramFraction.numerator * this.denominator;
/* 894 */     if (l1 == l2)
/* 895 */       return 0;
/* 896 */     if (l1 < l2) {
/* 897 */       return -1;
/*     */     }
/* 899 */     return 1;
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
/*     */   public String toString()
/*     */   {
/* 912 */     if (this.toString == null) {
/* 913 */       this.toString = (32 + getNumerator() + '/' + getDenominator());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 918 */     return this.toString;
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
/*     */   public String toProperString()
/*     */   {
/* 931 */     if (this.toProperString == null) {
/* 932 */       if (this.numerator == 0) {
/* 933 */         this.toProperString = "0";
/* 934 */       } else if (this.numerator == this.denominator) {
/* 935 */         this.toProperString = "1";
/* 936 */       } else if (this.numerator == -1 * this.denominator) {
/* 937 */         this.toProperString = "-1";
/* 938 */       } else if ((this.numerator > 0 ? -this.numerator : this.numerator) < -this.denominator)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 943 */         int i = getProperNumerator();
/* 944 */         if (i == 0) {
/* 945 */           this.toProperString = Integer.toString(getProperWhole());
/*     */         } else {
/* 947 */           this.toProperString = (32 + getProperWhole() + ' ' + i + '/' + getDenominator());
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 953 */         this.toProperString = (32 + getNumerator() + '/' + getDenominator());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 958 */     return this.toProperString;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\math\Fraction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */