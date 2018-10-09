/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnchantmentSlotType
/*    */ {
/*    */   private EnchantmentSlotType() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean canEnchant(Item paramItem)
/*    */   {
/* 21 */     if (this == ALL) {
/* 22 */       return true;
/*    */     }
/* 24 */     if ((this == BREAKABLE) && (paramItem.usesDurability())) {
/* 25 */       return true;
/*    */     }
/*    */     
/* 28 */     if ((paramItem instanceof ItemArmor)) {
/* 29 */       if (this == ARMOR) {
/* 30 */         return true;
/*    */       }
/* 32 */       ItemArmor localItemArmor = (ItemArmor)paramItem;
/* 33 */       if (localItemArmor.b == 0) {
/* 34 */         return this == ARMOR_HEAD;
/*    */       }
/* 36 */       if (localItemArmor.b == 2) {
/* 37 */         return this == ARMOR_LEGS;
/*    */       }
/* 39 */       if (localItemArmor.b == 1) {
/* 40 */         return this == ARMOR_TORSO;
/*    */       }
/* 42 */       if (localItemArmor.b == 3) {
/* 43 */         return this == ARMOR_FEET;
/*    */       }
/* 45 */       return false; }
/* 46 */     if ((paramItem instanceof ItemSword))
/* 47 */       return this == WEAPON;
/* 48 */     if ((paramItem instanceof ItemTool))
/* 49 */       return this == DIGGER;
/* 50 */     if ((paramItem instanceof ItemBow))
/* 51 */       return this == BOW;
/* 52 */     if ((paramItem instanceof ItemFishingRod)) {
/* 53 */       return this == FISHING_ROD;
/*    */     }
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentSlotType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */