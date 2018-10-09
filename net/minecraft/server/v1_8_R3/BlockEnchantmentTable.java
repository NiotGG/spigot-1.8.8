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
/*    */ public class BlockEnchantmentTable
/*    */   extends BlockContainer
/*    */ {
/*    */   protected BlockEnchantmentTable()
/*    */   {
/* 21 */     super(Material.STONE, MaterialMapColor.D);
/* 22 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/* 23 */     e(0);
/* 24 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 29 */     return false;
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
/*    */   public boolean c()
/*    */   {
/* 60 */     return false;
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 65 */     return 3;
/*    */   }
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt)
/*    */   {
/* 70 */     return new TileEntityEnchantTable();
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 75 */     if (paramWorld.isClientSide) {
/* 76 */       return true;
/*    */     }
/*    */     
/* 79 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 80 */     if ((localTileEntity instanceof TileEntityEnchantTable)) {
/* 81 */       paramEntityHuman.openTileEntity((TileEntityEnchantTable)localTileEntity);
/*    */     }
/*    */     
/* 84 */     return true;
/*    */   }
/*    */   
/*    */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*    */   {
/* 89 */     super.postPlace(paramWorld, paramBlockPosition, paramIBlockData, paramEntityLiving, paramItemStack);
/* 90 */     if (paramItemStack.hasName()) {
/* 91 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 92 */       if ((localTileEntity instanceof TileEntityEnchantTable)) {
/* 93 */         ((TileEntityEnchantTable)localTileEntity).a(paramItemStack.getName());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockEnchantmentTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */