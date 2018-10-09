/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFloorSign
/*    */   extends BlockSign
/*    */ {
/* 10 */   public static final BlockStateInteger ROTATION = BlockStateInteger.of("rotation", 0, 15);
/*    */   
/*    */   public BlockFloorSign() {
/* 13 */     j(this.blockStateList.getBlockData().set(ROTATION, Integer.valueOf(0)));
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 18 */     if (!paramWorld.getType(paramBlockPosition.down()).getBlock().getMaterial().isBuildable()) {
/* 19 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 20 */       paramWorld.setAir(paramBlockPosition);
/*    */     }
/*    */     
/* 23 */     super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 28 */     return getBlockData().set(ROTATION, Integer.valueOf(paramInt));
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 34 */     return ((Integer)paramIBlockData.get(ROTATION)).intValue();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 39 */     return new BlockStateList(this, new IBlockState[] { ROTATION });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFloorSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */