/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class WeightedRandomEnchant extends WeightedRandom.WeightedRandomChoice
/*    */ {
/*    */   public final Enchantment enchantment;
/*    */   public final int level;
/*    */   
/*    */   public WeightedRandomEnchant(Enchantment paramEnchantment, int paramInt)
/*    */   {
/* 10 */     super(paramEnchantment.getRandomWeight());
/* 11 */     this.enchantment = paramEnchantment;
/* 12 */     this.level = paramInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WeightedRandomEnchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */