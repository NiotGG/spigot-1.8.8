/*     */ package org.apache.commons.lang3.math;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IEEE754rUtils
/*     */ {
/*     */   public static double min(double[] paramArrayOfDouble)
/*     */   {
/*  39 */     if (paramArrayOfDouble == null)
/*  40 */       throw new IllegalArgumentException("The Array must not be null");
/*  41 */     if (paramArrayOfDouble.length == 0) {
/*  42 */       throw new IllegalArgumentException("Array cannot be empty.");
/*     */     }
/*     */     
/*     */ 
/*  46 */     double d = paramArrayOfDouble[0];
/*  47 */     for (int i = 1; i < paramArrayOfDouble.length; i++) {
/*  48 */       d = min(paramArrayOfDouble[i], d);
/*     */     }
/*     */     
/*  51 */     return d;
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
/*     */   public static float min(float[] paramArrayOfFloat)
/*     */   {
/*  64 */     if (paramArrayOfFloat == null)
/*  65 */       throw new IllegalArgumentException("The Array must not be null");
/*  66 */     if (paramArrayOfFloat.length == 0) {
/*  67 */       throw new IllegalArgumentException("Array cannot be empty.");
/*     */     }
/*     */     
/*     */ 
/*  71 */     float f = paramArrayOfFloat[0];
/*  72 */     for (int i = 1; i < paramArrayOfFloat.length; i++) {
/*  73 */       f = min(paramArrayOfFloat[i], f);
/*     */     }
/*     */     
/*  76 */     return f;
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
/*     */   public static double min(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  90 */     return min(min(paramDouble1, paramDouble2), paramDouble3);
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
/*     */   public static double min(double paramDouble1, double paramDouble2)
/*     */   {
/* 103 */     if (Double.isNaN(paramDouble1)) {
/* 104 */       return paramDouble2;
/*     */     }
/* 106 */     if (Double.isNaN(paramDouble2)) {
/* 107 */       return paramDouble1;
/*     */     }
/* 109 */     return Math.min(paramDouble1, paramDouble2);
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
/*     */   public static float min(float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 124 */     return min(min(paramFloat1, paramFloat2), paramFloat3);
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
/*     */   public static float min(float paramFloat1, float paramFloat2)
/*     */   {
/* 137 */     if (Float.isNaN(paramFloat1)) {
/* 138 */       return paramFloat2;
/*     */     }
/* 140 */     if (Float.isNaN(paramFloat2)) {
/* 141 */       return paramFloat1;
/*     */     }
/* 143 */     return Math.min(paramFloat1, paramFloat2);
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
/*     */   public static double max(double[] paramArrayOfDouble)
/*     */   {
/* 157 */     if (paramArrayOfDouble == null)
/* 158 */       throw new IllegalArgumentException("The Array must not be null");
/* 159 */     if (paramArrayOfDouble.length == 0) {
/* 160 */       throw new IllegalArgumentException("Array cannot be empty.");
/*     */     }
/*     */     
/*     */ 
/* 164 */     double d = paramArrayOfDouble[0];
/* 165 */     for (int i = 1; i < paramArrayOfDouble.length; i++) {
/* 166 */       d = max(paramArrayOfDouble[i], d);
/*     */     }
/*     */     
/* 169 */     return d;
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
/*     */   public static float max(float[] paramArrayOfFloat)
/*     */   {
/* 182 */     if (paramArrayOfFloat == null)
/* 183 */       throw new IllegalArgumentException("The Array must not be null");
/* 184 */     if (paramArrayOfFloat.length == 0) {
/* 185 */       throw new IllegalArgumentException("Array cannot be empty.");
/*     */     }
/*     */     
/*     */ 
/* 189 */     float f = paramArrayOfFloat[0];
/* 190 */     for (int i = 1; i < paramArrayOfFloat.length; i++) {
/* 191 */       f = max(paramArrayOfFloat[i], f);
/*     */     }
/*     */     
/* 194 */     return f;
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
/*     */   public static double max(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 208 */     return max(max(paramDouble1, paramDouble2), paramDouble3);
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
/*     */   public static double max(double paramDouble1, double paramDouble2)
/*     */   {
/* 221 */     if (Double.isNaN(paramDouble1)) {
/* 222 */       return paramDouble2;
/*     */     }
/* 224 */     if (Double.isNaN(paramDouble2)) {
/* 225 */       return paramDouble1;
/*     */     }
/* 227 */     return Math.max(paramDouble1, paramDouble2);
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
/*     */   public static float max(float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 242 */     return max(max(paramFloat1, paramFloat2), paramFloat3);
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
/*     */   public static float max(float paramFloat1, float paramFloat2)
/*     */   {
/* 255 */     if (Float.isNaN(paramFloat1)) {
/* 256 */       return paramFloat2;
/*     */     }
/* 258 */     if (Float.isNaN(paramFloat2)) {
/* 259 */       return paramFloat1;
/*     */     }
/* 261 */     return Math.max(paramFloat1, paramFloat2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\math\IEEE754rUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */