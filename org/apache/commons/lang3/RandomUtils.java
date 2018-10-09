/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomUtils
/*     */ {
/*  34 */   private static final Random RANDOM = new Random();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] nextBytes(int paramInt)
/*     */   {
/*  63 */     Validate.isTrue(paramInt >= 0, "Count cannot be negative.", new Object[0]);
/*     */     
/*  65 */     byte[] arrayOfByte = new byte[paramInt];
/*  66 */     RANDOM.nextBytes(arrayOfByte);
/*  67 */     return arrayOfByte;
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
/*     */   public static int nextInt(int paramInt1, int paramInt2)
/*     */   {
/*  85 */     Validate.isTrue(paramInt2 >= paramInt1, "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/*  87 */     Validate.isTrue(paramInt1 >= 0, "Both range values must be non-negative.", new Object[0]);
/*     */     
/*  89 */     if (paramInt1 == paramInt2) {
/*  90 */       return paramInt1;
/*     */     }
/*     */     
/*  93 */     return paramInt1 + RANDOM.nextInt(paramInt2 - paramInt1);
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
/*     */   public static long nextLong(long paramLong1, long paramLong2)
/*     */   {
/* 111 */     Validate.isTrue(paramLong2 >= paramLong1, "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 113 */     Validate.isTrue(paramLong1 >= 0L, "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 115 */     if (paramLong1 == paramLong2) {
/* 116 */       return paramLong1;
/*     */     }
/*     */     
/* 119 */     return nextDouble(paramLong1, paramLong2);
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
/*     */   public static double nextDouble(double paramDouble1, double paramDouble2)
/*     */   {
/* 138 */     Validate.isTrue(paramDouble2 >= paramDouble1, "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 140 */     Validate.isTrue(paramDouble1 >= 0.0D, "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 142 */     if (paramDouble1 == paramDouble2) {
/* 143 */       return paramDouble1;
/*     */     }
/*     */     
/* 146 */     return paramDouble1 + (paramDouble2 - paramDouble1) * RANDOM.nextDouble();
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
/*     */   public static float nextFloat(float paramFloat1, float paramFloat2)
/*     */   {
/* 164 */     Validate.isTrue(paramFloat2 >= paramFloat1, "Start value must be smaller or equal to end value.", new Object[0]);
/*     */     
/* 166 */     Validate.isTrue(paramFloat1 >= 0.0F, "Both range values must be non-negative.", new Object[0]);
/*     */     
/* 168 */     if (paramFloat1 == paramFloat2) {
/* 169 */       return paramFloat1;
/*     */     }
/*     */     
/* 172 */     return paramFloat1 + (paramFloat2 - paramFloat1) * RANDOM.nextFloat();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\RandomUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */