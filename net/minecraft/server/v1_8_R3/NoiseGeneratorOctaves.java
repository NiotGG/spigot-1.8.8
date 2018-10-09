/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGeneratorPerlin[] a;
/*    */   private int b;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random paramRandom, int paramInt)
/*    */   {
/* 16 */     this.b = paramInt;
/* 17 */     this.a = new NoiseGeneratorPerlin[paramInt];
/* 18 */     for (int i = 0; i < paramInt; i++) {
/* 19 */       this.a[i] = new NoiseGeneratorPerlin(paramRandom);
/*    */     }
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
/*    */ 
/*    */   public double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, double paramDouble1, double paramDouble2, double paramDouble3)
/*    */   {
/* 49 */     if (paramArrayOfDouble == null) {
/* 50 */       paramArrayOfDouble = new double[paramInt4 * paramInt5 * paramInt6];
/*    */     } else {
/* 52 */       for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 53 */         paramArrayOfDouble[i] = 0.0D;
/*    */       }
/*    */     }
/*    */     
/* 57 */     double d1 = 1.0D;
/*    */     
/* 59 */     for (int j = 0; j < this.b; j++) {
/* 60 */       double d2 = paramInt1 * d1 * paramDouble1;
/* 61 */       double d3 = paramInt2 * d1 * paramDouble2;
/* 62 */       double d4 = paramInt3 * d1 * paramDouble3;
/* 63 */       long l1 = MathHelper.d(d2);
/* 64 */       long l2 = MathHelper.d(d4);
/* 65 */       d2 -= l1;
/* 66 */       d4 -= l2;
/* 67 */       l1 %= 16777216L;
/* 68 */       l2 %= 16777216L;
/* 69 */       d2 += l1;
/* 70 */       d4 += l2;
/* 71 */       this.a[j].a(paramArrayOfDouble, d2, d3, d4, paramInt4, paramInt5, paramInt6, paramDouble1 * d1, paramDouble2 * d1, paramDouble3 * d1, d1);
/* 72 */       d1 /= 2.0D;
/*    */     }
/*    */     
/* 75 */     return paramArrayOfDouble;
/*    */   }
/*    */   
/*    */   public double[] a(double[] paramArrayOfDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 79 */     return a(paramArrayOfDouble, paramInt1, 10, paramInt2, paramInt3, 1, paramInt4, paramDouble1, 1.0D, paramDouble2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NoiseGeneratorOctaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */