/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class ItemEgg
/*    */   extends Item
/*    */ {
/*    */   public ItemEgg()
/*    */   {
/* 10 */     this.maxStackSize = 16;
/* 11 */     a(CreativeModeTab.l);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 16 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 17 */       paramItemStack.count -= 1;
/*    */     }
/* 19 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 20 */     if (!paramWorld.isClientSide) {
/* 21 */       paramWorld.addEntity(new EntityEgg(paramWorld, paramEntityHuman));
/*    */     }
/* 23 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 24 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */