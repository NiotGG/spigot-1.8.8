/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BlockContainer
/*    */   extends Block
/*    */   implements IContainer
/*    */ {
/*    */   protected BlockContainer(Material paramMaterial)
/*    */   {
/* 13 */     this(paramMaterial, paramMaterial.r());
/*    */   }
/*    */   
/*    */   protected BlockContainer(Material paramMaterial, MaterialMapColor paramMaterialMapColor) {
/* 17 */     super(paramMaterial, paramMaterialMapColor);
/* 18 */     this.isTileEntity = true;
/*    */   }
/*    */   
/*    */   protected boolean a(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection) {
/* 22 */     return paramWorld.getType(paramBlockPosition.shift(paramEnumDirection)).getBlock().getMaterial() == Material.CACTUS;
/*    */   }
/*    */   
/*    */   protected boolean e(World paramWorld, BlockPosition paramBlockPosition) {
/* 26 */     return (a(paramWorld, paramBlockPosition, EnumDirection.NORTH)) || (a(paramWorld, paramBlockPosition, EnumDirection.SOUTH)) || (a(paramWorld, paramBlockPosition, EnumDirection.WEST)) || (a(paramWorld, paramBlockPosition, EnumDirection.EAST));
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 31 */     return -1;
/*    */   }
/*    */   
/*    */   public void remove(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 36 */     super.remove(paramWorld, paramBlockPosition, paramIBlockData);
/* 37 */     paramWorld.t(paramBlockPosition);
/*    */   }
/*    */   
/*    */   public boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt1, int paramInt2)
/*    */   {
/* 42 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramInt1, paramInt2);
/*    */     
/* 44 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 45 */     if (localTileEntity == null) {
/* 46 */       return false;
/*    */     }
/* 48 */     return localTileEntity.c(paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */