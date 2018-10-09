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
/*    */ public class ItemSign
/*    */   extends Item
/*    */ {
/*    */   public ItemSign()
/*    */   {
/* 19 */     this.maxStackSize = 16;
/* 20 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 25 */     if (paramEnumDirection == EnumDirection.DOWN) {
/* 26 */       return false;
/*    */     }
/* 28 */     if (!paramWorld.getType(paramBlockPosition).getBlock().getMaterial().isBuildable()) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     paramBlockPosition = paramBlockPosition.shift(paramEnumDirection);
/*    */     
/* 34 */     if (!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) {
/* 35 */       return false;
/*    */     }
/* 37 */     if (!Blocks.STANDING_SIGN.canPlace(paramWorld, paramBlockPosition)) {
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     if (paramWorld.isClientSide) {
/* 42 */       return true;
/*    */     }
/*    */     
/* 45 */     if (paramEnumDirection == EnumDirection.UP) {
/* 46 */       int i = MathHelper.floor((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
/* 47 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.STANDING_SIGN.getBlockData().set(BlockFloorSign.ROTATION, Integer.valueOf(i)), 3);
/*    */     } else {
/* 49 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.WALL_SIGN.getBlockData().set(BlockWallSign.FACING, paramEnumDirection), 3);
/*    */     }
/*    */     
/* 52 */     paramItemStack.count -= 1;
/* 53 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 54 */     if (((localTileEntity instanceof TileEntitySign)) && 
/* 55 */       (!ItemBlock.a(paramWorld, paramEntityHuman, paramBlockPosition, paramItemStack))) {
/* 56 */       paramEntityHuman.openSign((TileEntitySign)localTileEntity);
/*    */     }
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */