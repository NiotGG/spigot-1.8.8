/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class EnchantmentDurability
/*    */   extends Enchantment
/*    */ {
/*    */   protected EnchantmentDurability(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2)
/*    */   {
/* 11 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.BREAKABLE);
/*    */     
/* 13 */     c("durability");
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 18 */     return 5 + (paramInt - 1) * 8;
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 23 */     return super.a(paramInt) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 28 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack)
/*    */   {
/* 33 */     if (paramItemStack.e()) {
/* 34 */       return true;
/*    */     }
/* 36 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */   
/*    */   public static boolean a(ItemStack paramItemStack, int paramInt, Random paramRandom) {
/* 40 */     if (((paramItemStack.getItem() instanceof ItemArmor)) && (paramRandom.nextFloat() < 0.6F)) {
/* 41 */       return false;
/*    */     }
/* 43 */     return paramRandom.nextInt(paramInt + 1) > 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentDurability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */