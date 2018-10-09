/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class EnchantmentSilkTouch
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentSilkTouch(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  9 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.DIGGER);
/*    */     
/* 11 */     c("untouching");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 16 */     return 15;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 21 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 26 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment)
/*    */   {
/* 31 */     return (super.a(paramEnchantment)) && (paramEnchantment.id != LOOT_BONUS_BLOCKS.id);
/*    */   }
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack)
/*    */   {
/* 36 */     if (paramItemStack.getItem() == Items.SHEARS) {
/* 37 */       return true;
/*    */     }
/* 39 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentSilkTouch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */