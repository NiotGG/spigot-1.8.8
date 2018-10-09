/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class EnchantmentWaterWorker extends Enchantment
/*    */ {
/*    */   public EnchantmentWaterWorker(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/*  7 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.ARMOR_HEAD);
/*  8 */     c("waterWorker");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 13 */     return 1;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 18 */     return a(paramInt) + 40;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 23 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentWaterWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */