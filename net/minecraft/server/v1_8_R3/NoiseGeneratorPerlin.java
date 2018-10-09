/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGeneratorPerlin extends NoiseGenerator {
/*   6 */   private int[] d = new int['Ȁ'];
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*     */   
/*     */   public NoiseGeneratorPerlin() {
/*  12 */     this(new Random());
/*     */   }
/*     */   
/*     */   public NoiseGeneratorPerlin(Random paramRandom) {
/*  16 */     this.a = (paramRandom.nextDouble() * 256.0D);
/*  17 */     this.b = (paramRandom.nextDouble() * 256.0D);
/*  18 */     this.c = (paramRandom.nextDouble() * 256.0D);
/*  19 */     for (int j = 0; j < 256; j++) {
/*  20 */       this.d[j] = j;
/*     */     }
/*     */     
/*  23 */     for (j = 0; j < 256; j++) {
/*  24 */       int k = paramRandom.nextInt(256 - j) + j;
/*  25 */       int m = this.d[j];
/*  26 */       this.d[j] = this.d[k];
/*  27 */       this.d[k] = m;
/*     */       
/*  29 */       this.d[(j + 256)] = this.d[j];
/*     */     }
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
/*     */   public final double b(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  84 */     return paramDouble2 + paramDouble1 * (paramDouble3 - paramDouble2);
/*     */   }
/*     */   
/*  87 */   private static final double[] e = { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  88 */   private static final double[] f = { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
/*  89 */   private static final double[] g = { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*  90 */   private static final double[] h = { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  91 */   private static final double[] i = { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2) {
/*  94 */     int j = paramInt & 0xF;
/*  95 */     return h[j] * paramDouble1 + i[j] * paramDouble2;
/*     */   }
/*     */   
/*     */   public final double a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  99 */     int j = paramInt & 0xF;
/* 100 */     return e[j] * paramDouble1 + f[j] * paramDouble2 + g[j] * paramDouble3;
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2, int paramInt3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7)
/*     */   {
/*     */     double d6;
/*     */     
/*     */     int i6;
/*     */     
/*     */     int i7;
/*     */     
/*     */     double d7;
/* 113 */     if (paramInt2 == 1) {
/* 114 */       j = 0;int k = 0;int m = 0;n = 0;
/* 115 */       double d1 = 0.0D;double d2 = 0.0D;
/* 116 */       i1 = 0;
/* 117 */       double d3 = 1.0D / paramDouble7;
/* 118 */       for (int i2 = 0; i2 < paramInt1; i2++) {
/* 119 */         d4 = paramDouble1 + i2 * paramDouble4 + this.a;
/* 120 */         int i3 = (int)d4;
/* 121 */         if (d4 < i3) {
/* 122 */           i3--;
/*     */         }
/* 124 */         int i4 = i3 & 0xFF;
/* 125 */         d4 -= i3;
/* 126 */         d5 = d4 * d4 * d4 * (d4 * (d4 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 128 */         for (i5 = 0; i5 < paramInt3; i5++) {
/* 129 */           d6 = paramDouble3 + i5 * paramDouble6 + this.c;
/* 130 */           i6 = (int)d6;
/* 131 */           if (d6 < i6) {
/* 132 */             i6--;
/*     */           }
/* 134 */           i7 = i6 & 0xFF;
/* 135 */           d6 -= i6;
/* 136 */           d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 138 */           j = this.d[i4] + 0;
/* 139 */           k = this.d[j] + i7;
/* 140 */           m = this.d[(i4 + 1)] + 0;
/* 141 */           n = this.d[m] + i7;
/* 142 */           d1 = b(d5, a(this.d[k], d4, d6), a(this.d[n], d4 - 1.0D, 0.0D, d6));
/* 143 */           d2 = b(d5, a(this.d[(k + 1)], d4, 0.0D, d6 - 1.0D), a(this.d[(n + 1)], d4 - 1.0D, 0.0D, d6 - 1.0D));
/*     */           
/* 145 */           double d8 = b(d7, d1, d2);
/*     */           
/* 147 */           paramArrayOfDouble[(i1++)] += d8 * d3;
/*     */         }
/*     */       }
/* 150 */       return;
/*     */     }
/* 152 */     int j = 0;
/* 153 */     double d9 = 1.0D / paramDouble7;
/* 154 */     int n = -1;
/* 155 */     int i8 = 0;int i9 = 0;int i10 = 0;int i11 = 0;int i1 = 0;int i12 = 0;
/* 156 */     double d10 = 0.0D;double d4 = 0.0D;double d11 = 0.0D;double d5 = 0.0D;
/*     */     
/* 158 */     for (int i5 = 0; i5 < paramInt1; i5++) {
/* 159 */       d6 = paramDouble1 + i5 * paramDouble4 + this.a;
/* 160 */       i6 = (int)d6;
/* 161 */       if (d6 < i6) {
/* 162 */         i6--;
/*     */       }
/* 164 */       i7 = i6 & 0xFF;
/* 165 */       d6 -= i6;
/* 166 */       d7 = d6 * d6 * d6 * (d6 * (d6 * 6.0D - 15.0D) + 10.0D);
/*     */       
/* 168 */       for (int i13 = 0; i13 < paramInt3; i13++) {
/* 169 */         double d12 = paramDouble3 + i13 * paramDouble6 + this.c;
/* 170 */         int i14 = (int)d12;
/* 171 */         if (d12 < i14) {
/* 172 */           i14--;
/*     */         }
/* 174 */         int i15 = i14 & 0xFF;
/* 175 */         d12 -= i14;
/* 176 */         double d13 = d12 * d12 * d12 * (d12 * (d12 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 178 */         for (int i16 = 0; i16 < paramInt2; i16++) {
/* 179 */           double d14 = paramDouble2 + i16 * paramDouble5 + this.b;
/* 180 */           int i17 = (int)d14;
/* 181 */           if (d14 < i17) {
/* 182 */             i17--;
/*     */           }
/* 184 */           int i18 = i17 & 0xFF;
/* 185 */           d14 -= i17;
/* 186 */           double d15 = d14 * d14 * d14 * (d14 * (d14 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 188 */           if ((i16 == 0) || (i18 != n)) {
/* 189 */             n = i18;
/* 190 */             i8 = this.d[i7] + i18;
/* 191 */             i9 = this.d[i8] + i15;
/* 192 */             i10 = this.d[(i8 + 1)] + i15;
/* 193 */             i11 = this.d[(i7 + 1)] + i18;
/* 194 */             i1 = this.d[i11] + i15;
/* 195 */             i12 = this.d[(i11 + 1)] + i15;
/* 196 */             d10 = b(d7, a(this.d[i9], d6, d14, d12), a(this.d[i1], d6 - 1.0D, d14, d12));
/* 197 */             d4 = b(d7, a(this.d[i10], d6, d14 - 1.0D, d12), a(this.d[i12], d6 - 1.0D, d14 - 1.0D, d12));
/* 198 */             d11 = b(d7, a(this.d[(i9 + 1)], d6, d14, d12 - 1.0D), a(this.d[(i1 + 1)], d6 - 1.0D, d14, d12 - 1.0D));
/* 199 */             d5 = b(d7, a(this.d[(i10 + 1)], d6, d14 - 1.0D, d12 - 1.0D), a(this.d[(i12 + 1)], d6 - 1.0D, d14 - 1.0D, d12 - 1.0D));
/*     */           }
/*     */           
/* 202 */           double d16 = b(d15, d10, d4);
/* 203 */           double d17 = b(d15, d11, d5);
/* 204 */           double d18 = b(d13, d16, d17);
/*     */           
/* 206 */           paramArrayOfDouble[(j++)] += d18 * d9;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NoiseGeneratorPerlin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */