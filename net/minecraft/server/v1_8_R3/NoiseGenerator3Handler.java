/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGenerator3Handler {
/*   6 */   private static int[][] e = { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  20 */   public static final double a = Math.sqrt(3.0D);
/*     */   
/*  22 */   private int[] f = new int['Ȁ'];
/*     */   public double b;
/*     */   public double c;
/*     */   public double d;
/*     */   
/*     */   public NoiseGenerator3Handler() {
/*  28 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGenerator3Handler(Random paramRandom) {
/*  32 */     this.b = (paramRandom.nextDouble() * 256.0D);
/*  33 */     this.c = (paramRandom.nextDouble() * 256.0D);
/*  34 */     this.d = (paramRandom.nextDouble() * 256.0D);
/*  35 */     for (int i = 0; i < 256; i++) {
/*  36 */       this.f[i] = i;
/*     */     }
/*     */     
/*  39 */     for (i = 0; i < 256; i++) {
/*  40 */       int j = paramRandom.nextInt(256 - i) + i;
/*  41 */       int k = this.f[i];
/*  42 */       this.f[i] = this.f[j];
/*  43 */       this.f[j] = k;
/*     */       
/*  45 */       this.f[(i + 256)] = this.f[i];
/*     */     }
/*     */   }
/*     */   
/*     */   private static int a(double paramDouble)
/*     */   {
/*  51 */     return paramDouble > 0.0D ? (int)paramDouble : (int)paramDouble - 1;
/*     */   }
/*     */   
/*     */   private static double a(int[] paramArrayOfInt, double paramDouble1, double paramDouble2) {
/*  55 */     return paramArrayOfInt[0] * paramDouble1 + paramArrayOfInt[1] * paramDouble2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double a(double paramDouble1, double paramDouble2)
/*     */   {
/*  66 */     double d1 = 0.5D * (a - 1.0D);
/*  67 */     double d2 = (paramDouble1 + paramDouble2) * d1;
/*  68 */     int i = a(paramDouble1 + d2);
/*  69 */     int j = a(paramDouble2 + d2);
/*  70 */     double d3 = (3.0D - a) / 6.0D;
/*  71 */     double d4 = (i + j) * d3;
/*  72 */     double d5 = i - d4;
/*  73 */     double d6 = j - d4;
/*  74 */     double d7 = paramDouble1 - d5;
/*  75 */     double d8 = paramDouble2 - d6;
/*     */     
/*     */     int k;
/*     */     int m;
/*  79 */     if (d7 > d8) {
/*  80 */       k = 1;
/*  81 */       m = 0;
/*     */     }
/*     */     else {
/*  84 */       k = 0;
/*  85 */       m = 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  90 */     double d9 = d7 - k + d3;
/*  91 */     double d10 = d8 - m + d3;
/*  92 */     double d11 = d7 - 1.0D + 2.0D * d3;
/*  93 */     double d12 = d8 - 1.0D + 2.0D * d3;
/*     */     
/*  95 */     int n = i & 0xFF;
/*  96 */     int i1 = j & 0xFF;
/*  97 */     int i2 = this.f[(n + this.f[i1])] % 12;
/*  98 */     int i3 = this.f[(n + k + this.f[(i1 + m)])] % 12;
/*  99 */     int i4 = this.f[(n + 1 + this.f[(i1 + 1)])] % 12;
/*     */     
/* 101 */     double d13 = 0.5D - d7 * d7 - d8 * d8;
/* 102 */     double d14; if (d13 < 0.0D) {
/* 103 */       d14 = 0.0D;
/*     */     } else {
/* 105 */       d13 *= d13;
/* 106 */       d14 = d13 * d13 * a(e[i2], d7, d8);
/*     */     }
/* 108 */     double d15 = 0.5D - d9 * d9 - d10 * d10;
/* 109 */     double d16; if (d15 < 0.0D) {
/* 110 */       d16 = 0.0D;
/*     */     } else {
/* 112 */       d15 *= d15;
/* 113 */       d16 = d15 * d15 * a(e[i3], d9, d10);
/*     */     }
/* 115 */     double d17 = 0.5D - d11 * d11 - d12 * d12;
/* 116 */     double d18; if (d17 < 0.0D) {
/* 117 */       d18 = 0.0D;
/*     */     } else {
/* 119 */       d17 *= d17;
/* 120 */       d18 = d17 * d17 * a(e[i4], d11, d12);
/*     */     }
/*     */     
/*     */ 
/* 124 */     return 70.0D * (d14 + d16 + d18);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 252 */   private static final double g = 0.5D * (a - 1.0D);
/* 253 */   private static final double h = (3.0D - a) / 6.0D;
/*     */   
/*     */   public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 256 */     int i = 0;
/* 257 */     for (int j = 0; j < paramInt2; j++) {
/* 258 */       double d1 = (paramDouble2 + j) * paramDouble4 + this.c;
/* 259 */       for (int k = 0; k < paramInt1; k++) {
/* 260 */         double d2 = (paramDouble1 + k) * paramDouble3 + this.b;
/*     */         
/*     */ 
/* 263 */         double d3 = (d2 + d1) * g;
/* 264 */         int m = a(d2 + d3);
/* 265 */         int n = a(d1 + d3);
/* 266 */         double d4 = (m + n) * h;
/* 267 */         double d5 = m - d4;
/* 268 */         double d6 = n - d4;
/* 269 */         double d7 = d2 - d5;
/* 270 */         double d8 = d1 - d6;
/*     */         
/*     */         int i1;
/*     */         int i2;
/* 274 */         if (d7 > d8) {
/* 275 */           i1 = 1;
/* 276 */           i2 = 0;
/*     */         }
/*     */         else {
/* 279 */           i1 = 0;
/* 280 */           i2 = 1;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 285 */         double d9 = d7 - i1 + h;
/* 286 */         double d10 = d8 - i2 + h;
/* 287 */         double d11 = d7 - 1.0D + 2.0D * h;
/* 288 */         double d12 = d8 - 1.0D + 2.0D * h;
/*     */         
/* 290 */         int i3 = m & 0xFF;
/* 291 */         int i4 = n & 0xFF;
/* 292 */         int i5 = this.f[(i3 + this.f[i4])] % 12;
/* 293 */         int i6 = this.f[(i3 + i1 + this.f[(i4 + i2)])] % 12;
/* 294 */         int i7 = this.f[(i3 + 1 + this.f[(i4 + 1)])] % 12;
/*     */         
/* 296 */         double d13 = 0.5D - d7 * d7 - d8 * d8;
/* 297 */         double d14; if (d13 < 0.0D) {
/* 298 */           d14 = 0.0D;
/*     */         } else {
/* 300 */           d13 *= d13;
/* 301 */           d14 = d13 * d13 * a(e[i5], d7, d8);
/*     */         }
/* 303 */         double d15 = 0.5D - d9 * d9 - d10 * d10;
/* 304 */         double d16; if (d15 < 0.0D) {
/* 305 */           d16 = 0.0D;
/*     */         } else {
/* 307 */           d15 *= d15;
/* 308 */           d16 = d15 * d15 * a(e[i6], d9, d10);
/*     */         }
/* 310 */         double d17 = 0.5D - d11 * d11 - d12 * d12;
/* 311 */         double d18; if (d17 < 0.0D) {
/* 312 */           d18 = 0.0D;
/*     */         } else {
/* 314 */           d17 *= d17;
/* 315 */           d18 = d17 * d17 * a(e[i7], d11, d12);
/*     */         }
/*     */         
/*     */ 
/* 319 */         paramArrayOfDouble[(i++)] += 70.0D * (d14 + d16 + d18) * paramDouble5;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NoiseGenerator3Handler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */