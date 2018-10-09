/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentKnockback extends Enchantment
/*    */ {
/*    */   protected EnchantmentKnockback(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.WEAPON);
/*    */     
/*  9 */     c("knockback");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 14 */     return 5 + 20 * (paramInt - 1);
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 19 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 24 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentKnockback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */