/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockWallSign
/*    */   extends BlockSign
/*    */ {
/* 12 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*    */   
/*    */   public BlockWallSign() {
/* 15 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*    */   {
/* 20 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockAccess.getType(paramBlockPosition).get(FACING);
/*    */     
/* 22 */     float f1 = 0.28125F;
/* 23 */     float f2 = 0.78125F;
/* 24 */     float f3 = 0.0F;
/* 25 */     float f4 = 1.0F;
/*    */     
/* 27 */     float f5 = 0.125F;
/*    */     
/* 29 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 31 */     switch (1.a[localEnumDirection.ordinal()]) {
/*    */     case 1: 
/* 33 */       a(f3, f1, 1.0F - f5, f4, f2, 1.0F);
/* 34 */       break;
/*    */     case 2: 
/* 36 */       a(f3, f1, 0.0F, f4, f2, f5);
/* 37 */       break;
/*    */     case 3: 
/* 39 */       a(1.0F - f5, f1, f3, 1.0F, f2, f4);
/* 40 */       break;
/*    */     case 4: 
/* 42 */       a(0.0F, f1, f3, f5, f2, f4);
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 49 */     EnumDirection localEnumDirection = (EnumDirection)paramIBlockData.get(FACING);
/*    */     
/* 51 */     if (!paramWorld.getType(paramBlockPosition.shift(localEnumDirection.opposite())).getBlock().getMaterial().isBuildable()) {
/* 52 */       b(paramWorld, paramBlockPosition, paramIBlockData, 0);
/* 53 */       paramWorld.setAir(paramBlockPosition);
/*    */     }
/*    */     
/* 56 */     super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 61 */     EnumDirection localEnumDirection = EnumDirection.fromType1(paramInt);
/* 62 */     if (localEnumDirection.k() == EnumDirection.EnumAxis.Y) {
/* 63 */       localEnumDirection = EnumDirection.NORTH;
/*    */     }
/* 65 */     return getBlockData().set(FACING, localEnumDirection);
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 71 */     return ((EnumDirection)paramIBlockData.get(FACING)).a();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 76 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWallSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */