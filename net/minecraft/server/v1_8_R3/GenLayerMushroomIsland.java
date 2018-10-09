/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerMushroomIsland extends GenLayer
/*    */ {
/*    */   public GenLayerMushroomIsland(long paramLong, GenLayer paramGenLayer)
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
/* 22 */         int i2 = arrayOfInt1[(i1 + 0 + (n + 0) * k)];
/* 23 */         int i3 = arrayOfInt1[(i1 + 2 + (n + 0) * k)];
/* 24 */         int i4 = arrayOfInt1[(i1 + 0 + (n + 2) * k)];
/* 25 */         int i5 = arrayOfInt1[(i1 + 2 + (n + 2) * k)];
/* 26 */         int i6 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/* 27 */         a(i1 + paramInt1, n + paramInt2);
/* 28 */         if ((i6 == 0) && (i2 == 0) && (i3 == 0) && (i4 == 0) && (i5 == 0) && (a(100) == 0)) {
/* 29 */           arrayOfInt2[(i1 + n * paramInt3)] = BiomeBase.MUSHROOM_ISLAND.id;
/*    */         } else {
/* 31 */           arrayOfInt2[(i1 + n * paramInt3)] = i6;
/*    */         }
/*    */       }
/*    */     }
/* 35 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerMushroomIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */