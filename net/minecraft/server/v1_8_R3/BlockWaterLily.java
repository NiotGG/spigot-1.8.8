/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWaterLily
/*    */   extends BlockPlant
/*    */ {
/*    */   protected BlockWaterLily()
/*    */   {
/* 21 */     float f1 = 0.5F;
/* 22 */     float f2 = 0.015625F;
/* 23 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
/* 24 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*    */   {
/* 29 */     if ((paramEntity == null) || (!(paramEntity instanceof EntityBoat))) {
/* 30 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 37 */     return new AxisAlignedBB(paramBlockPosition.getX() + this.minX, paramBlockPosition.getY() + this.minY, paramBlockPosition.getZ() + this.minZ, paramBlockPosition.getX() + this.maxX, paramBlockPosition.getY() + this.maxY, paramBlockPosition.getZ() + this.maxZ);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected boolean c(Block paramBlock)
/*    */   {
/* 57 */     return paramBlock == Blocks.WATER;
/*    */   }
/*    */   
/*    */   public boolean f(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 62 */     if ((paramBlockPosition.getY() < 0) || (paramBlockPosition.getY() >= 256)) {
/* 63 */       return false;
/*    */     }
/* 65 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition.down());
/* 66 */     return (localIBlockData.getBlock().getMaterial() == Material.WATER) && (((Integer)localIBlockData.get(BlockFluids.LEVEL)).intValue() == 0);
/*    */   }
/*    */   
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 71 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWaterLily.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */