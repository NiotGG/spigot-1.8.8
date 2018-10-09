/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerRiverMix extends GenLayer
/*    */ {
/*    */   private GenLayer c;
/*    */   private GenLayer d;
/*    */   
/*    */   public GenLayerRiverMix(long paramLong, GenLayer paramGenLayer1, GenLayer paramGenLayer2)
/*    */   {
/* 10 */     super(paramLong);
/* 11 */     this.c = paramGenLayer1;
/* 12 */     this.d = paramGenLayer2;
/*    */   }
/*    */   
/*    */   public void a(long paramLong)
/*    */   {
/* 17 */     this.c.a(paramLong);
/* 18 */     this.d.a(paramLong);
/* 19 */     super.a(paramLong);
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 24 */     int[] arrayOfInt1 = this.c.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 25 */     int[] arrayOfInt2 = this.d.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     
/* 27 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 28 */     for (int i = 0; i < paramInt3 * paramInt4; i++) {
/* 29 */       if ((arrayOfInt1[i] == BiomeBase.OCEAN.id) || (arrayOfInt1[i] == BiomeBase.DEEP_OCEAN.id)) {
/* 30 */         arrayOfInt3[i] = arrayOfInt1[i];
/*    */       }
/* 32 */       else if (arrayOfInt2[i] == BiomeBase.RIVER.id) {
/* 33 */         if (arrayOfInt1[i] == BiomeBase.ICE_PLAINS.id) {
/* 34 */           arrayOfInt3[i] = BiomeBase.FROZEN_RIVER.id;
/* 35 */         } else if ((arrayOfInt1[i] == BiomeBase.MUSHROOM_ISLAND.id) || (arrayOfInt1[i] == BiomeBase.MUSHROOM_SHORE.id)) {
/* 36 */           arrayOfInt3[i] = BiomeBase.MUSHROOM_SHORE.id;
/*    */         } else {
/* 38 */           arrayOfInt2[i] &= 0xFF;
/*    */         }
/*    */       } else {
/* 41 */         arrayOfInt3[i] = arrayOfInt1[i];
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 46 */     return arrayOfInt3;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerRiverMix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */