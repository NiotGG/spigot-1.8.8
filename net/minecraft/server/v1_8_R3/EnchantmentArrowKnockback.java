/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentArrowKnockback extends Enchantment
/*    */ {
/*    */   public EnchantmentArrowKnockback(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.BOW);
/*  8 */     c("arrowKnockback");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 13 */     return 12 + (paramInt - 1) * 20;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 18 */     return a(paramInt) + 25;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 23 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentArrowKnockback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */