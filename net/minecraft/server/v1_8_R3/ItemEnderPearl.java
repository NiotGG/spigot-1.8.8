/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class ItemEnderPearl
/*    */   extends Item
/*    */ {
/*    */   public ItemEnderPearl()
/*    */   {
/* 10 */     this.maxStackSize = 16;
/* 11 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 16 */     if (paramEntityHuman.abilities.canInstantlyBuild) {
/* 17 */       return paramItemStack;
/*    */     }
/*    */     
/* 20 */     paramItemStack.count -= 1;
/* 21 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 22 */     if (!paramWorld.isClientSide) {
/* 23 */       paramWorld.addEntity(new EntityEnderPearl(paramWorld, paramEntityHuman));
/*    */     }
/* 25 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 26 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */