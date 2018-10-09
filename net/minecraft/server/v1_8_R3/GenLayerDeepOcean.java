/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerDeepOcean extends GenLayer
/*    */ {
/*    */   public GenLayerDeepOcean(long paramLong, GenLayer paramGenLayer)
/*    */   {
/*  7 */     super(paramLong);
/*  8 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 13 */     int i = paramInt1 - 1;
/* 14 */     int j = paramInt2 - 1;
/* 15 */     int k = paramInt3 + 2;
/* 16 */     int m = paramInt4 + 2;
/* 17 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 19 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 20 */     for (int n = 0; n < paramInt4; n++) {
/* 21 */       for (int i1 = 0; i1 < paramInt3; i1++) {
/* 22 */         int i2 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * (paramInt3 + 2))];
/* 23 */         int i3 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * (paramInt3 + 2))];
/* 24 */         int i4 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * (paramInt3 + 2))];
/* 25 */         int i5 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * (paramInt3 + 2))];
/*    */         
/* 27 */         int i6 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/* 28 */         int i7 = 0;
/* 29 */         if (i2 == 0) {
/* 30 */           i7++;
/*    */         }
/* 32 */         if (i3 == 0) {
/* 33 */           i7++;
/*    */         }
/* 35 */         if (i4 == 0) {
/* 36 */           i7++;
/*    */         }
/* 38 */         if (i5 == 0) {
/* 39 */           i7++;
/*    */         }
/*    */         
/* 42 */         if ((i6 == 0) && (i7 > 3)) {
/* 43 */           arrayOfInt2[(i1 + n * paramInt3)] = BiomeBase.DEEP_OCEAN.id;
/*    */         } else {
/* 45 */           arrayOfInt2[(i1 + n * paramInt3)] = i6;
/*    */         }
/*    */       }
/*    */     }
/* 49 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerDeepOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */