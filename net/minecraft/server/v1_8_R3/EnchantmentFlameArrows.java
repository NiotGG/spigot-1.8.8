/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentFlameArrows extends Enchantment
/*    */ {
/*    */   public EnchantmentFlameArrows(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.BOW);
/*  8 */     c("arrowFire");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 13 */     return 20;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 18 */     return 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 23 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentFlameArrows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */