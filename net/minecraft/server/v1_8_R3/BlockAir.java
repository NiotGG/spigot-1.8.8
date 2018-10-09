/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockAir
/*    */   extends Block
/*    */ {
/*    */   protected BlockAir()
/*    */   {
/* 13 */     super(Material.AIR);
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 18 */     return -1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public boolean a(IBlockData paramIBlockData, boolean paramBoolean)
/*    */   {
/* 35 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void dropNaturally(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, float paramFloat, int paramInt) {}
/*    */   
/*    */ 
/*    */   public boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockAir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */