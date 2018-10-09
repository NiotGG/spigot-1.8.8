/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class ItemExpBottle
/*    */   extends Item
/*    */ {
/*    */   public ItemExpBottle()
/*    */   {
/* 10 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 20 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 21 */       paramItemStack.count -= 1;
/*    */     }
/* 23 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 24 */     if (!paramWorld.isClientSide) {
/* 25 */       paramWorld.addEntity(new EntityThrownExpBottle(paramWorld, paramEntityHuman));
/*    */     }
/* 27 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 28 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */