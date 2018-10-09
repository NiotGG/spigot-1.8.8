/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class EnchantmentThorns extends Enchantment
/*    */ {
/*    */   public EnchantmentThorns(int i, MinecraftKey minecraftkey, int j) {
/*  8 */     super(i, minecraftkey, j, EnchantmentSlotType.ARMOR_TORSO);
/*  9 */     c("thorns");
/*    */   }
/*    */   
/*    */   public int a(int i) {
/* 13 */     return 10 + 20 * (i - 1);
/*    */   }
/*    */   
/*    */   public int b(int i) {
/* 17 */     return super.a(i) + 50;
/*    */   }
/*    */   
/*    */   public int getMaxLevel() {
/* 21 */     return 3;
/*    */   }
/*    */   
/*    */   public boolean canEnchant(ItemStack itemstack) {
/* 25 */     return (itemstack.getItem() instanceof ItemArmor) ? true : super.canEnchant(itemstack);
/*    */   }
/*    */   
/*    */   public void b(EntityLiving entityliving, Entity entity, int i) {
/* 29 */     Random random = entityliving.bc();
/* 30 */     ItemStack itemstack = EnchantmentManager.a(Enchantment.THORNS, entityliving);
/*    */     
/* 32 */     if ((entity != null) && (a(i, random))) {
/* 33 */       if (entity != null) {
/* 34 */         entity.damageEntity(DamageSource.a(entityliving), b(i, random));
/* 35 */         entity.makeSound("damage.thorns", 0.5F, 1.0F);
/*    */       }
/*    */       
/* 38 */       if (itemstack != null) {
/* 39 */         itemstack.damage(3, entityliving);
/*    */       }
/* 41 */     } else if (itemstack != null) {
/* 42 */       itemstack.damage(1, entityliving);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean a(int i, Random random)
/*    */   {
/* 48 */     return i > 0;
/*    */   }
/*    */   
/*    */   public static int b(int i, Random random) {
/* 52 */     return i > 10 ? i - 10 : 1 + random.nextInt(4);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentThorns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */