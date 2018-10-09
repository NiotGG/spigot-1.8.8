/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockMinecartTrack
/*    */   extends BlockMinecartTrackAbstract
/*    */ {
/* 11 */   public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.of("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class);
/*    */   
/*    */   protected BlockMinecartTrack() {
/* 14 */     super(false);
/* 15 */     j(this.blockStateList.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH));
/*    */   }
/*    */   
/*    */   protected void b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 20 */     if ((paramBlock.isPowerSource()) && 
/* 21 */       (new BlockMinecartTrackAbstract.MinecartTrackLogic(this, paramWorld, paramBlockPosition, paramIBlockData).a() == 3)) {
/* 22 */       a(paramWorld, paramBlockPosition, paramIBlockData, false);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n()
/*    */   {
/* 29 */     return SHAPE;
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 34 */     return getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(paramInt));
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 40 */     return ((BlockMinecartTrackAbstract.EnumTrackPosition)paramIBlockData.get(SHAPE)).a();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 45 */     return new BlockStateList(this, new IBlockState[] { SHAPE });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMinecartTrack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */