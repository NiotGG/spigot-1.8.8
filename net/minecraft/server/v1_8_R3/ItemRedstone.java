/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item
/*    */ {
/*    */   public ItemRedstone()
/*    */   {
/* 12 */     a(CreativeModeTab.d);
/*    */   }
/*    */   
/*    */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 17 */     boolean bool = paramWorld.getType(paramBlockPosition).getBlock().a(paramWorld, paramBlockPosition);
/* 18 */     BlockPosition localBlockPosition = bool ? paramBlockPosition : paramBlockPosition.shift(paramEnumDirection);
/*    */     
/* 20 */     if (!paramEntityHuman.a(localBlockPosition, paramEnumDirection, paramItemStack)) {
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/* 25 */     if (!paramWorld.a(localBlock, localBlockPosition, false, paramEnumDirection, null, paramItemStack)) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     if (Blocks.REDSTONE_WIRE.canPlace(paramWorld, localBlockPosition)) {
/* 30 */       paramItemStack.count -= 1;
/* 31 */       paramWorld.setTypeUpdate(localBlockPosition, Blocks.REDSTONE_WIRE.getBlockData());
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemRedstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */