/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerTopSoil extends GenLayer {
/*    */   public GenLayerTopSoil(long paramLong, GenLayer paramGenLayer) {
/*  5 */     super(paramLong);
/*  6 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 11 */     int i = paramInt1 - 1;
/* 12 */     int j = paramInt2 - 1;
/* 13 */     int k = paramInt3 + 2;
/* 14 */     int m = paramInt4 + 2;
/* 15 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 17 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 18 */     for (int n = 0; n < paramInt4; n++) {
/* 19 */       for (int i1 = 0; i1 < paramInt3; i1++) {
/* 20 */         int i2 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/* 21 */         a(i1 + paramInt1, n + paramInt2);
/* 22 */         if (i2 == 0) {
/* 23 */           arrayOfInt2[(i1 + n * paramInt3)] = 0;
/*    */         } else {
/* 25 */           int i3 = a(6);
/* 26 */           if (i3 == 0) {
/* 27 */             i3 = 4;
/* 28 */           } else if (i3 <= 1) {
/* 29 */             i3 = 3;
/*    */           } else {
/* 31 */             i3 = 1;
/*    */           }
/* 33 */           arrayOfInt2[(i1 + n * paramInt3)] = i3;
/*    */         }
/*    */       }
/*    */     }
/* 37 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerTopSoil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */