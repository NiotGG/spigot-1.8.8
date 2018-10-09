/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGlassBottle
/*    */   extends Item
/*    */ {
/*    */   public ItemGlassBottle()
/*    */   {
/* 12 */     a(CreativeModeTab.k);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 17 */     MovingObjectPosition localMovingObjectPosition = a(paramWorld, paramEntityHuman, true);
/* 18 */     if (localMovingObjectPosition == null) {
/* 19 */       return paramItemStack;
/*    */     }
/*    */     
/* 22 */     if (localMovingObjectPosition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
/* 23 */       BlockPosition localBlockPosition = localMovingObjectPosition.a();
/*    */       
/* 25 */       if (!paramWorld.a(paramEntityHuman, localBlockPosition)) {
/* 26 */         return paramItemStack;
/*    */       }
/* 28 */       if (!paramEntityHuman.a(localBlockPosition.shift(localMovingObjectPosition.direction), localMovingObjectPosition.direction, paramItemStack)) {
/* 29 */         return paramItemStack;
/*    */       }
/* 31 */       if (paramWorld.getType(localBlockPosition).getBlock().getMaterial() == Material.WATER) {
/* 32 */         paramItemStack.count -= 1;
/* 33 */         paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 34 */         if (paramItemStack.count <= 0) {
/* 35 */           return new ItemStack(Items.POTION);
/*    */         }
/* 37 */         if (!paramEntityHuman.inventory.pickup(new ItemStack(Items.POTION))) {
/* 38 */           paramEntityHuman.drop(new ItemStack(Items.POTION, 1, 0), false);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 44 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemGlassBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */