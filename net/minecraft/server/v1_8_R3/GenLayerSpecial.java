/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class GenLayerSpecial
/*     */   extends GenLayer
/*     */ {
/*     */   private final EnumGenLayerSpecial c;
/*     */   
/*     */ 
/*     */   public GenLayerSpecial(long paramLong, GenLayer paramGenLayer, EnumGenLayerSpecial paramEnumGenLayerSpecial)
/*     */   {
/*  12 */     super(paramLong);
/*  13 */     this.a = paramGenLayer;
/*  14 */     this.c = paramEnumGenLayerSpecial;
/*     */   }
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  19 */     switch (1.a[this.c.ordinal()]) {
/*     */     case 1: 
/*     */     default: 
/*  22 */       return c(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     case 2: 
/*  24 */       return d(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*  26 */     return e(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   private int[] c(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  31 */     int i = paramInt1 - 1;
/*  32 */     int j = paramInt2 - 1;
/*  33 */     int k = 1 + paramInt3 + 1;
/*  34 */     int m = 1 + paramInt4 + 1;
/*  35 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*     */     
/*  37 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/*  39 */     for (int n = 0; n < paramInt4; n++) {
/*  40 */       for (int i1 = 0; i1 < paramInt3; i1++) {
/*  41 */         a(i1 + paramInt1, n + paramInt2);
/*     */         
/*  43 */         int i2 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/*     */         
/*  45 */         if (i2 == 1) {
/*  46 */           int i3 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * k)];
/*  47 */           int i4 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * k)];
/*  48 */           int i5 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * k)];
/*  49 */           int i6 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * k)];
/*     */           
/*  51 */           int i7 = (i3 == 3) || (i4 == 3) || (i5 == 3) || (i6 == 3) ? 1 : 0;
/*  52 */           int i8 = (i3 == 4) || (i4 == 4) || (i5 == 4) || (i6 == 4) ? 1 : 0;
/*  53 */           if ((i7 != 0) || (i8 != 0)) {
/*  54 */             i2 = 2;
/*     */           }
/*     */         }
/*     */         
/*  58 */         arrayOfInt2[(i1 + n * paramInt3)] = i2;
/*     */       }
/*     */     }
/*     */     
/*  62 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private int[] d(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  66 */     int i = paramInt1 - 1;
/*  67 */     int j = paramInt2 - 1;
/*  68 */     int k = 1 + paramInt3 + 1;
/*  69 */     int m = 1 + paramInt4 + 1;
/*  70 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*     */     
/*  72 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/*  74 */     for (int n = 0; n < paramInt4; n++) {
/*  75 */       for (int i1 = 0; i1 < paramInt3; i1++) {
/*  76 */         int i2 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/*     */         
/*  78 */         if (i2 == 4) {
/*  79 */           int i3 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * k)];
/*  80 */           int i4 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * k)];
/*  81 */           int i5 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * k)];
/*  82 */           int i6 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * k)];
/*     */           
/*  84 */           int i7 = (i3 == 2) || (i4 == 2) || (i5 == 2) || (i6 == 2) ? 1 : 0;
/*  85 */           int i8 = (i3 == 1) || (i4 == 1) || (i5 == 1) || (i6 == 1) ? 1 : 0;
/*     */           
/*  87 */           if ((i8 != 0) || (i7 != 0)) {
/*  88 */             i2 = 3;
/*     */           }
/*     */         }
/*     */         
/*  92 */         arrayOfInt2[(i1 + n * paramInt3)] = i2;
/*     */       }
/*     */     }
/*     */     
/*  96 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private int[] e(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 100 */     int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 101 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/* 103 */     for (int i = 0; i < paramInt4; i++) {
/* 104 */       for (int j = 0; j < paramInt3; j++) {
/* 105 */         a(j + paramInt1, i + paramInt2);
/*     */         
/* 107 */         int k = arrayOfInt1[(j + i * paramInt3)];
/*     */         
/* 109 */         if ((k != 0) && (a(13) == 0)) {
/* 110 */           k |= 1 + a(15) << 8 & 0xF00;
/*     */         }
/*     */         
/* 113 */         arrayOfInt2[(j + i * paramInt3)] = k;
/*     */       }
/*     */     }
/*     */     
/* 117 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   public static enum EnumGenLayerSpecial
/*     */   {
/*     */     private EnumGenLayerSpecial() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerSpecial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */