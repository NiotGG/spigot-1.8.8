/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class NoiseGenerator3
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGenerator3Handler[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGenerator3(Random paramRandom, int paramInt)
/*    */   {
/* 14 */     this.b = paramInt;
/* 15 */     this.a = new NoiseGenerator3Handler[paramInt];
/* 16 */     for (int i = 0; i < paramInt; i++) {
/* 17 */       this.a[i] = new NoiseGenerator3Handler(paramRandom);
/*    */     }
/*    */   }
/*    */   
/*    */   public double a(double paramDouble1, double paramDouble2)
/*    */   {
/* 23 */     double d1 = 0.0D;
/* 24 */     double d2 = 1.0D;
/*    */     
/* 26 */     for (int i = 0; i < this.b; i++) {
/* 27 */       d1 += this.a[i].a(paramDouble1 * d2, paramDouble2 * d2) / d2;
/* 28 */       d2 /= 2.0D;
/*    */     }
/*    */     
/* 31 */     return d1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public double[] a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5)
/*    */   {
/* 47 */     return a(paramArrayOfDouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4, paramDouble5, 0.5D);
/*    */   }
/*    */   
/*    */   public double[] a(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/* 51 */     if ((paramArrayOfDouble == null) || (paramArrayOfDouble.length < paramInt1 * paramInt2)) {
/* 52 */       paramArrayOfDouble = new double[paramInt1 * paramInt2];
/*    */     } else {
/* 54 */       for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 55 */         paramArrayOfDouble[i] = 0.0D;
/*    */       }
/*    */     }
/*    */     
/* 59 */     double d1 = 1.0D;
/* 60 */     double d2 = 1.0D;
/* 61 */     for (int j = 0; j < this.b; j++) {
/* 62 */       this.a[j].a(paramArrayOfDouble, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3 * d2 * d1, paramDouble4 * d2 * d1, 0.55D / d1);
/* 63 */       d2 *= paramDouble5;
/* 64 */       d1 *= paramDouble6;
/*    */     }
/*    */     
/* 67 */     return paramArrayOfDouble;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NoiseGenerator3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */