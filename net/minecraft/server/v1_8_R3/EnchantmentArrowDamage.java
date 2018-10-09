/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentArrowDamage extends Enchantment
/*    */ {
/*    */   public EnchantmentArrowDamage(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.BOW);
/*  8 */     c("arrowDamage");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 13 */     return 1 + (paramInt - 1) * 10;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 18 */     return a(paramInt) + 15;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 23 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentArrowDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */