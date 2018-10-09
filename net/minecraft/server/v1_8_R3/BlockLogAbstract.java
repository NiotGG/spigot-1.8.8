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
/*    */ public abstract class BlockLogAbstract
/*    */   extends BlockRotatable
/*    */ {
/* 18 */   public static final BlockStateEnum<EnumLogRotation> AXIS = BlockStateEnum.of("axis", EnumLogRotation.class);
/*    */   
/*    */   public BlockLogAbstract() {
/* 21 */     super(Material.WOOD);
/* 22 */     a(CreativeModeTab.b);
/* 23 */     c(2.0F);
/* 24 */     a(f);
/*    */   }
/*    */   
/*    */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 29 */     int i = 4;
/* 30 */     int j = i + 1;
/*    */     
/* 32 */     if (!paramWorld.areChunksLoadedBetween(paramBlockPosition.a(-j, -j, -j), paramBlockPosition.a(j, j, j))) {
/* 33 */       return;
/*    */     }
/*    */     
/* 36 */     for (BlockPosition localBlockPosition : BlockPosition.a(paramBlockPosition.a(-i, -i, -i), paramBlockPosition.a(i, i, i))) {
/* 37 */       IBlockData localIBlockData = paramWorld.getType(localBlockPosition);
/* 38 */       if ((localIBlockData.getBlock().getMaterial() == Material.LEAVES) && 
/* 39 */         (!((Boolean)localIBlockData.get(BlockLeaves.CHECK_DECAY)).booleanValue())) {
/* 40 */         paramWorld.setTypeAndData(localBlockPosition, localIBlockData.set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(true)), 4);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*    */   {
/* 51 */     return super.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, paramInt, paramEntityLiving).set(AXIS, EnumLogRotation.a(paramEnumDirection.k()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static enum EnumLogRotation
/*    */     implements INamable
/*    */   {
/*    */     private final String e;
/*    */     
/*    */ 
/*    */     private EnumLogRotation(String paramString)
/*    */     {
/* 64 */       this.e = paramString;
/*    */     }
/*    */     
/*    */     public String toString()
/*    */     {
/* 69 */       return this.e;
/*    */     }
/*    */     
/*    */     public static EnumLogRotation a(EnumDirection.EnumAxis paramEnumAxis) {
/* 73 */       switch (BlockLogAbstract.1.a[paramEnumAxis.ordinal()]) {
/*    */       case 1: 
/* 75 */         return X;
/*    */       case 2: 
/* 77 */         return Y;
/*    */       case 3: 
/* 79 */         return Z;
/*    */       }
/*    */       
/* 82 */       return NONE;
/*    */     }
/*    */     
/*    */     public String getName()
/*    */     {
/* 87 */       return this.e;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLogAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */