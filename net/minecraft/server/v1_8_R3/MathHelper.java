/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*  29 */   public static final float a = c(2.0F);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  36 */   private static final float[] b = new float[65536];
/*     */   private static final int[] c;
/*     */   private static final double d;
/*     */   private static final double[] e;
/*     */   private static final double[] f;
/*     */   
/*     */   public static float sin(float paramFloat) {
/*  43 */     return b[((int)(paramFloat * 10430.378F) & 0xFFFF)];
/*     */   }
/*     */   
/*     */   public static float cos(float paramFloat) {
/*  47 */     return b[((int)(paramFloat * 10430.378F + 16384.0F) & 0xFFFF)];
/*     */   }
/*     */   
/*     */   public static float c(float paramFloat) {
/*  51 */     return (float)Math.sqrt(paramFloat);
/*     */   }
/*     */   
/*     */   public static float sqrt(double paramDouble) {
/*  55 */     return (float)Math.sqrt(paramDouble);
/*     */   }
/*     */   
/*     */   public static int d(float paramFloat) {
/*  59 */     int i = (int)paramFloat;
/*  60 */     return paramFloat < i ? i - 1 : i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int floor(double paramDouble)
/*     */   {
/*  68 */     int i = (int)paramDouble;
/*  69 */     return paramDouble < i ? i - 1 : i;
/*     */   }
/*     */   
/*     */   public static long d(double paramDouble) {
/*  73 */     long l = paramDouble;
/*  74 */     return paramDouble < l ? l - 1L : l;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float e(float paramFloat)
/*     */   {
/*  82 */     return paramFloat >= 0.0F ? paramFloat : -paramFloat;
/*     */   }
/*     */   
/*     */   public static int a(int paramInt) {
/*  86 */     return paramInt >= 0 ? paramInt : -paramInt;
/*     */   }
/*     */   
/*     */   public static int f(float paramFloat) {
/*  90 */     int i = (int)paramFloat;
/*  91 */     return paramFloat > i ? i + 1 : i;
/*     */   }
/*     */   
/*     */   public static int f(double paramDouble) {
/*  95 */     int i = (int)paramDouble;
/*  96 */     return paramDouble > i ? i + 1 : i;
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
/*     */   public static int clamp(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 110 */     if (paramInt1 < paramInt2) {
/* 111 */       return paramInt2;
/*     */     }
/* 113 */     if (paramInt1 > paramInt3) {
/* 114 */       return paramInt3;
/*     */     }
/* 116 */     return paramInt1;
/*     */   }
/*     */   
/*     */   public static float a(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 120 */     if (paramFloat1 < paramFloat2) {
/* 121 */       return paramFloat2;
/*     */     }
/* 123 */     if (paramFloat1 > paramFloat3) {
/* 124 */       return paramFloat3;
/*     */     }
/* 126 */     return paramFloat1;
/*     */   }
/*     */   
/*     */   public static double a(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 130 */     if (paramDouble1 < paramDouble2) {
/* 131 */       return paramDouble2;
/*     */     }
/* 133 */     if (paramDouble1 > paramDouble3) {
/* 134 */       return paramDouble3;
/*     */     }
/* 136 */     return paramDouble1;
/*     */   }
/*     */   
/*     */   public static double b(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 140 */     if (paramDouble3 < 0.0D) {
/* 141 */       return paramDouble1;
/*     */     }
/* 143 */     if (paramDouble3 > 1.0D) {
/* 144 */       return paramDouble2;
/*     */     }
/* 146 */     return paramDouble1 + (paramDouble2 - paramDouble1) * paramDouble3;
/*     */   }
/*     */   
/*     */   public static double a(double paramDouble1, double paramDouble2) {
/* 150 */     if (paramDouble1 < 0.0D) {
/* 151 */       paramDouble1 = -paramDouble1;
/*     */     }
/* 153 */     if (paramDouble2 < 0.0D) {
/* 154 */       paramDouble2 = -paramDouble2;
/*     */     }
/* 156 */     return paramDouble1 > paramDouble2 ? paramDouble1 : paramDouble2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int nextInt(Random paramRandom, int paramInt1, int paramInt2)
/*     */   {
/* 167 */     if (paramInt1 >= paramInt2) {
/* 168 */       return paramInt1;
/*     */     }
/* 170 */     return paramRandom.nextInt(paramInt2 - paramInt1 + 1) + paramInt1;
/*     */   }
/*     */   
/*     */   public static float a(Random paramRandom, float paramFloat1, float paramFloat2) {
/* 174 */     if (paramFloat1 >= paramFloat2) {
/* 175 */       return paramFloat1;
/*     */     }
/* 177 */     return paramRandom.nextFloat() * (paramFloat2 - paramFloat1) + paramFloat1;
/*     */   }
/*     */   
/*     */   public static double a(Random paramRandom, double paramDouble1, double paramDouble2) {
/* 181 */     if (paramDouble1 >= paramDouble2) {
/* 182 */       return paramDouble1;
/*     */     }
/* 184 */     return paramRandom.nextDouble() * (paramDouble2 - paramDouble1) + paramDouble1;
/*     */   }
/*     */   
/*     */   public static double a(long[] paramArrayOfLong) {
/* 188 */     long l1 = 0L;
/*     */     
/* 190 */     for (long l2 : paramArrayOfLong) {
/* 191 */       l1 += l2;
/*     */     }
/*     */     
/* 194 */     return l1 / paramArrayOfLong.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static float g(float paramFloat)
/*     */   {
/* 206 */     paramFloat %= 360.0F;
/* 207 */     if (paramFloat >= 180.0F) {
/* 208 */       paramFloat -= 360.0F;
/*     */     }
/* 210 */     if (paramFloat < -180.0F) {
/* 211 */       paramFloat += 360.0F;
/*     */     }
/* 213 */     return paramFloat;
/*     */   }
/*     */   
/*     */   public static double g(double paramDouble) {
/* 217 */     paramDouble %= 360.0D;
/* 218 */     if (paramDouble >= 180.0D) {
/* 219 */       paramDouble -= 360.0D;
/*     */     }
/* 221 */     if (paramDouble < -180.0D) {
/* 222 */       paramDouble += 360.0D;
/*     */     }
/* 224 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt) {
/*     */     try {
/* 229 */       return Integer.parseInt(paramString);
/*     */     } catch (Throwable localThrowable) {}
/* 231 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 236 */     return Math.max(paramInt2, a(paramString, paramInt1));
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble) {
/*     */     try {
/* 241 */       return Double.parseDouble(paramString);
/*     */     } catch (Throwable localThrowable) {}
/* 243 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble1, double paramDouble2)
/*     */   {
/* 248 */     return Math.max(paramDouble2, a(paramString, paramDouble1));
/*     */   }
/*     */   
/*     */   public static int b(int paramInt)
/*     */   {
/* 253 */     int i = paramInt - 1;
/* 254 */     i |= i >> 1;
/* 255 */     i |= i >> 2;
/* 256 */     i |= i >> 4;
/* 257 */     i |= i >> 8;
/* 258 */     i |= i >> 16;
/* 259 */     return i + 1;
/*     */   }
/*     */   
/*     */   private static boolean d(int paramInt)
/*     */   {
/* 264 */     return (paramInt != 0) && ((paramInt & paramInt - 1) == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int e(int paramInt)
/*     */   {
/* 273 */     paramInt = d(paramInt) ? paramInt : b(paramInt);
/* 274 */     return c[((int)(paramInt * 125613361L >> 27) & 0x1F)];
/*     */   }
/*     */   
/*     */   public static int c(int paramInt) {
/* 278 */     return e(paramInt) - (d(paramInt) ? 0 : 1);
/*     */   }
/*     */   
/*     */   public static int c(int paramInt1, int paramInt2) {
/* 282 */     if (paramInt2 == 0) {
/* 283 */       return 0;
/*     */     }
/* 285 */     if (paramInt1 == 0) {
/* 286 */       return paramInt2;
/*     */     }
/*     */     
/* 289 */     if (paramInt1 < 0) {
/* 290 */       paramInt2 *= -1;
/*     */     }
/*     */     
/* 293 */     int i = paramInt1 % paramInt2;
/* 294 */     if (i == 0) {
/* 295 */       return paramInt1;
/*     */     }
/* 297 */     return paramInt1 + paramInt2 - i;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UUID a(Random paramRandom)
/*     */   {
/* 366 */     long l1 = paramRandom.nextLong() & 0xFFFFFFFFFFFF0FFF | 0x4000;
/* 367 */     long l2 = paramRandom.nextLong() & 0x3FFFFFFFFFFFFFFF | 0x8000000000000000;
/* 368 */     return new UUID(l1, l2);
/*     */   }
/*     */   
/*     */   public static double c(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 372 */     return (paramDouble1 - paramDouble2) / (paramDouble3 - paramDouble2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double b(double paramDouble1, double paramDouble2)
/*     */   {
/* 418 */     double d1 = paramDouble2 * paramDouble2 + paramDouble1 * paramDouble1;
/*     */     
/*     */ 
/* 421 */     if (Double.isNaN(d1)) {
/* 422 */       return NaN.0D;
/*     */     }
/*     */     
/*     */ 
/* 426 */     int i = paramDouble1 < 0.0D ? 1 : 0;
/* 427 */     if (i != 0) {
/* 428 */       paramDouble1 = -paramDouble1;
/*     */     }
/* 430 */     int j = paramDouble2 < 0.0D ? 1 : 0;
/* 431 */     if (j != 0) {
/* 432 */       paramDouble2 = -paramDouble2;
/*     */     }
/* 434 */     int k = paramDouble1 > paramDouble2 ? 1 : 0;
/* 435 */     if (k != 0) {
/* 436 */       d2 = paramDouble2;
/* 437 */       paramDouble2 = paramDouble1;
/* 438 */       paramDouble1 = d2;
/*     */     }
/*     */     
/*     */ 
/* 442 */     double d2 = i(d1);
/* 443 */     paramDouble2 *= d2;
/* 444 */     paramDouble1 *= d2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 453 */     double d3 = d + paramDouble1;
/* 454 */     int m = (int)Double.doubleToRawLongBits(d3);
/*     */     
/*     */ 
/* 457 */     double d4 = e[m];
/* 458 */     double d5 = f[m];
/*     */     
/*     */ 
/*     */ 
/* 462 */     double d6 = d3 - d;
/* 463 */     double d7 = paramDouble1 * d5 - paramDouble2 * d6;
/*     */     
/*     */ 
/* 466 */     double d8 = (6.0D + d7 * d7) * d7 * 0.16666666666666666D;
/* 467 */     double d9 = d4 + d8;
/*     */     
/*     */ 
/* 470 */     if (k != 0) {
/* 471 */       d9 = 1.5707963267948966D - d9;
/*     */     }
/* 473 */     if (j != 0) {
/* 474 */       d9 = 3.141592653589793D - d9;
/*     */     }
/* 476 */     if (i != 0) {
/* 477 */       d9 = -d9;
/*     */     }
/*     */     
/* 480 */     return d9;
/*     */   }
/*     */   
/*     */   public static double i(double paramDouble) {
/* 484 */     double d1 = 0.5D * paramDouble;
/* 485 */     long l = Double.doubleToRawLongBits(paramDouble);
/* 486 */     l = 6910469410427058090L - (l >> 1);
/* 487 */     paramDouble = Double.longBitsToDouble(l);
/* 488 */     paramDouble *= (1.5D - d1 * paramDouble * paramDouble);
/* 489 */     return paramDouble;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/*  37 */     for (int i = 0; i < 65536; i++) {
/*  38 */       b[i] = ((float)Math.sin(i * 3.141592653589793D * 2.0D / 65536.0D));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 268 */     c = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 495 */     d = Double.longBitsToDouble(4805340802404319232L);
/* 496 */     e = new double['ā'];
/* 497 */     f = new double['ā'];
/*     */     
/*     */ 
/*     */ 
/* 501 */     for (i = 0; i < 257; i++) {
/* 502 */       double d1 = i / 256.0D;
/* 503 */       double d2 = Math.asin(d1);
/* 504 */       f[i] = Math.cos(d2);
/* 505 */       e[i] = d2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */