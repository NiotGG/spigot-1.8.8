/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBarrier
/*    */   extends Block
/*    */ {
/*    */   protected BlockBarrier()
/*    */   {
/* 10 */     super(Material.BANNER);
/* 11 */     x();
/* 12 */     b(6000001.0F);
/* 13 */     K();
/* 14 */     this.t = true;
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 19 */     return -1;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBarrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */