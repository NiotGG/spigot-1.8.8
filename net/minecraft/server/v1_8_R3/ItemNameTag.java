/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class ItemNameTag
/*    */   extends Item
/*    */ {
/*    */   public ItemNameTag()
/*    */   {
/*  9 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving)
/*    */   {
/* 14 */     if (!paramItemStack.hasName()) {
/* 15 */       return false;
/*    */     }
/*    */     
/* 18 */     if ((paramEntityLiving instanceof EntityInsentient)) {
/* 19 */       EntityInsentient localEntityInsentient = (EntityInsentient)paramEntityLiving;
/* 20 */       localEntityInsentient.setCustomName(paramItemStack.getName());
/* 21 */       localEntityInsentient.bX();
/* 22 */       paramItemStack.count -= 1;
/* 23 */       return true;
/*    */     }
/*    */     
/* 26 */     return super.a(paramItemStack, paramEntityHuman, paramEntityLiving);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemNameTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */