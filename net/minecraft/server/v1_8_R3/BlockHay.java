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
/*    */ public class BlockHay
/*    */   extends BlockRotatable
/*    */ {
/*    */   public BlockHay()
/*    */   {
/* 17 */     super(Material.GRASS, MaterialMapColor.t);
/* 18 */     j(this.blockStateList.getBlockData().set(AXIS, EnumDirection.EnumAxis.Y));
/* 19 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int paramInt)
/*    */   {
/* 24 */     EnumDirection.EnumAxis localEnumAxis = EnumDirection.EnumAxis.Y;
/* 25 */     int i = paramInt & 0xC;
/* 26 */     if (i == 4) {
/* 27 */       localEnumAxis = EnumDirection.EnumAxis.X;
/* 28 */     } else if (i == 8) {
/* 29 */       localEnumAxis = EnumDirection.EnumAxis.Z;
/*    */     }
/* 31 */     return getBlockData().set(AXIS, localEnumAxis);
/*    */   }
/*    */   
/*    */ 
/*    */   public int toLegacyData(IBlockData paramIBlockData)
/*    */   {
/* 37 */     int i = 0;
/*    */     
/* 39 */     EnumDirection.EnumAxis localEnumAxis = (EnumDirection.EnumAxis)paramIBlockData.get(AXIS);
/* 40 */     if (localEnumAxis == EnumDirection.EnumAxis.X) {
/* 41 */       i |= 0x4;
/* 42 */     } else if (localEnumAxis == EnumDirection.EnumAxis.Z) {
/* 43 */       i |= 0x8;
/*    */     }
/*    */     
/* 46 */     return i;
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList()
/*    */   {
/* 51 */     return new BlockStateList(this, new IBlockState[] { AXIS });
/*    */   }
/*    */   
/*    */   protected ItemStack i(IBlockData paramIBlockData)
/*    */   {
/* 56 */     return new ItemStack(Item.getItemOf(this), 1, 0);
/*    */   }
/*    */   
/*    */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*    */   {
/* 61 */     return super.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, paramInt, paramEntityLiving).set(AXIS, paramEnumDirection.k());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockHay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */