/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerIcePlains extends GenLayer {
/*    */   public GenLayerIcePlains(long paramLong, GenLayer paramGenLayer) {
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
/* 20 */         int i2 = arrayOfInt1[(i1 + 1 + (n + 1 - 1) * (paramInt3 + 2))];
/* 21 */         int i3 = arrayOfInt1[(i1 + 1 + 1 + (n + 1) * (paramInt3 + 2))];
/* 22 */         int i4 = arrayOfInt1[(i1 + 1 - 1 + (n + 1) * (paramInt3 + 2))];
/* 23 */         int i5 = arrayOfInt1[(i1 + 1 + (n + 1 + 1) * (paramInt3 + 2))];
/*    */         
/* 25 */         int i6 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/* 26 */         arrayOfInt2[(i1 + n * paramInt3)] = i6;
/* 27 */         a(i1 + paramInt1, n + paramInt2);
/* 28 */         if ((i6 == 0) && (i2 == 0) && (i3 == 0) && (i4 == 0) && (i5 == 0) && (a(2) == 0)) {
/* 29 */           arrayOfInt2[(i1 + n * paramInt3)] = 1;
/*    */         }
/*    */       }
/*    */     }
/* 33 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerIcePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */