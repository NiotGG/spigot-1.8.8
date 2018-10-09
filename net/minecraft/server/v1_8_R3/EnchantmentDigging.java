/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class EnchantmentDigging
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentDigging(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  9 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.DIGGER);
/*    */     
/* 11 */     c("digging");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 16 */     return 1 + 10 * (paramInt - 1);
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 21 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 26 */     return 5;
/*    */   }
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack)
/*    */   {
/* 31 */     if (paramItemStack.getItem() == Items.SHEARS) {
/* 32 */       return true;
/*    */     }
/* 34 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentDigging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */