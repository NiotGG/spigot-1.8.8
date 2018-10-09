/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentLure extends Enchantment
/*    */ {
/*    */   protected EnchantmentLure(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2, EnchantmentSlotType paramEnchantmentSlotType)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, paramEnchantmentSlotType);
/*    */     
/*  9 */     c("fishingSpeed");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 14 */     return 15 + (paramInt - 1) * 9;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 19 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 24 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentLure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */