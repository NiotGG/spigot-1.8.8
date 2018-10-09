/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class LayerIsland extends GenLayer {
/*    */   public LayerIsland(long paramLong) {
/*  5 */     super(paramLong);
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 10 */     int[] arrayOfInt = IntCache.a(paramInt3 * paramInt4);
/* 11 */     for (int i = 0; i < paramInt4; i++) {
/* 12 */       for (int j = 0; j < paramInt3; j++) {
/* 13 */         a(paramInt1 + j, paramInt2 + i);
/* 14 */         arrayOfInt[(j + i * paramInt3)] = (a(10) == 0 ? 1 : 0);
/*    */       }
/*    */     }
/*    */     
/* 18 */     if ((paramInt1 > -paramInt3) && (paramInt1 <= 0) && (paramInt2 > -paramInt4) && (paramInt2 <= 0)) {
/* 19 */       arrayOfInt[(-paramInt1 + -paramInt2 * paramInt3)] = 1;
/*    */     }
/* 21 */     return arrayOfInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LayerIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */