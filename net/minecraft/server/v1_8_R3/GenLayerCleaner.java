/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerCleaner extends GenLayer {
/*    */   public GenLayerCleaner(long paramLong, GenLayer paramGenLayer) {
/*  5 */     super(paramLong);
/*  6 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 11 */     int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */     
/* 13 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 14 */     for (int i = 0; i < paramInt4; i++) {
/* 15 */       for (int j = 0; j < paramInt3; j++) {
/* 16 */         a(j + paramInt1, i + paramInt2);
/* 17 */         arrayOfInt2[(j + i * paramInt3)] = (arrayOfInt1[(j + i * paramInt3)] > 0 ? a(299999) + 2 : 0);
/*    */       }
/*    */     }
/*    */     
/* 21 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */