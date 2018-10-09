/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemCarrotStick
/*    */   extends Item
/*    */ {
/*    */   public ItemCarrotStick()
/*    */   {
/* 10 */     a(CreativeModeTab.e);
/* 11 */     c(1);
/* 12 */     setMaxDurability(25);
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
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*    */   {
/* 27 */     if ((paramEntityHuman.au()) && ((paramEntityHuman.vehicle instanceof EntityPig))) {
/* 28 */       EntityPig localEntityPig = (EntityPig)paramEntityHuman.vehicle;
/*    */       
/* 30 */       if ((localEntityPig.cm().h()) && (paramItemStack.j() - paramItemStack.getData() >= 7)) {
/* 31 */         localEntityPig.cm().g();
/* 32 */         paramItemStack.damage(7, paramEntityHuman);
/*    */         
/* 34 */         if (paramItemStack.count == 0) {
/* 35 */           ItemStack localItemStack = new ItemStack(Items.FISHING_ROD);
/* 36 */           localItemStack.setTag(paramItemStack.getTag());
/* 37 */           return localItemStack;
/*    */         }
/*    */       }
/*    */     }
/* 41 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*    */     
/* 43 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemCarrotStick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */