/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentLootBonus extends Enchantment
/*    */ {
/*    */   protected EnchantmentLootBonus(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, paramEnchantmentSlotType);
/*    */     
/*  9 */     if (paramEnchantmentSlotType == EnchantmentSlotType.DIGGER) {
/* 10 */       c("lootBonusDigger");
/* 11 */     } else if (paramEnchantmentSlotType == EnchantmentSlotType.FISHING_ROD) {
/* 12 */       c("lootBonusFishing");
/*    */     } else {
/* 14 */       c("lootBonus");
/*    */     }
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 20 */     return 15 + (paramInt - 1) * 9;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 25 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 30 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment)
/*    */   {
/* 35 */     return (super.a(paramEnchantment)) && (paramEnchantment.id != SILK_TOUCH.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentLootBonus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */