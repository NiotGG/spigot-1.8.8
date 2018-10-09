/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockSlowSand
/*    */   extends Block
/*    */ {
/*    */   public BlockSlowSand()
/*    */   {
/* 16 */     super(Material.SAND, MaterialMapColor.B);
/* 17 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */ 
/*    */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 23 */     float f = 0.125F;
/* 24 */     return new AxisAlignedBB(paramBlockPosition.getX(), paramBlockPosition.getY(), paramBlockPosition.getZ(), paramBlockPosition.getX() + 1, paramBlockPosition.getY() + 1 - f, paramBlockPosition.getZ() + 1);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Entity paramEntity)
/*    */   {
/* 29 */     paramEntity.motX *= 0.4D;
/* 30 */     paramEntity.motZ *= 0.4D;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSlowSand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */