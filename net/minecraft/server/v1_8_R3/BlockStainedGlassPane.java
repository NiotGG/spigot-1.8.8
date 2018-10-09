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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStainedGlassPane
/*    */   extends BlockThin
/*    */ {
/* 19 */   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.of("color", EnumColor.class);
/*    */   
/*    */   public BlockStainedGlassPane() {
/* 22 */     super(Material.SHATTERABLE, false);
/* 23 */     j(this.blockStateList.getBlockData().set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)).set(COLOR, EnumColor.WHITE));
/* 24 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public int getDropData(IBlockData paramIBlockData)
/*    */   {
/* 29 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 41 */     return ((EnumColor)paramIBlockData.get(COLOR)).e();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 51 */     return getBlockData().set(COLOR, EnumColor.fromColorIndex(paramInt));
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 57 */     return ((EnumColor)paramIBlockData.get(COLOR)).getColorIndex();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 62 */     return new BlockStateList(this, new IBlockState[] { NORTH, EAST, WEST, SOUTH, COLOR });
/*    */   }
/*    */   
/*    */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 67 */     if (!paramWorld.isClientSide) {
/* 68 */       BlockBeacon.f(paramWorld, paramBlockPosition);
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 74 */     if (!paramWorld.isClientSide) {
/* 75 */       BlockBeacon.f(paramWorld, paramBlockPosition);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStainedGlassPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */