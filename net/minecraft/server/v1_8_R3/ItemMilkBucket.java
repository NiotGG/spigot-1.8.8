/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemMilkBucket
/*    */   extends Item
/*    */ {
/*    */   public ItemMilkBucket()
/*    */   {
/* 11 */     c(1);
/* 12 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 17 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 18 */       paramItemStack.count -= 1;
/*    */     }
/*    */     
/* 21 */     if (!paramWorld.isClientSide) {
/* 22 */       paramEntityHuman.removeAllEffects();
/*    */     }
/*    */     
/* 25 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 26 */     if (paramItemStack.count <= 0) {
/* 27 */       return new ItemStack(Items.BUCKET);
/*    */     }
/* 29 */     return paramItemStack;
/*    */   }
/*    */   
/*    */   public int d(ItemStack paramItemStack)
/*    */   {
/* 34 */     return 32;
/*    */   }
/*    */   
/*    */   public EnumAnimation e(ItemStack paramItemStack)
/*    */   {
/* 39 */     return EnumAnimation.DRINK;
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 44 */     paramEntityHuman.a(paramItemStack, d(paramItemStack));
/* 45 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemMilkBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */