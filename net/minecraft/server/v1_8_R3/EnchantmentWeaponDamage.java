/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentWeaponDamage
/*    */   extends Enchantment
/*    */ {
/* 18 */   private static final String[] E = { "all", "undead", "arthropods" };
/*    */   
/*    */ 
/*    */ 
/* 22 */   private static final int[] F = { 1, 5, 5 };
/*    */   
/*    */ 
/*    */ 
/* 26 */   private static final int[] G = { 11, 8, 8 };
/*    */   
/*    */ 
/*    */ 
/* 30 */   private static final int[] H = { 20, 20, 20 };
/*    */   
/*    */   public final int a;
/*    */   
/*    */ 
/*    */   public EnchantmentWeaponDamage(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2, int paramInt3)
/*    */   {
/* 37 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.WEAPON);
/* 38 */     this.a = paramInt3;
/*    */   }
/*    */   
/*    */   public int a(int paramInt)
/*    */   {
/* 43 */     return F[this.a] + (paramInt - 1) * G[this.a];
/*    */   }
/*    */   
/*    */   public int b(int paramInt)
/*    */   {
/* 48 */     return a(paramInt) + H[this.a];
/*    */   }
/*    */   
/*    */   public int getMaxLevel()
/*    */   {
/* 53 */     return 5;
/*    */   }
/*    */   
/*    */   public float a(int paramInt, EnumMonsterType paramEnumMonsterType)
/*    */   {
/* 58 */     if (this.a == 0) {
/* 59 */       return paramInt * 1.25F;
/*    */     }
/* 61 */     if ((this.a == 1) && (paramEnumMonsterType == EnumMonsterType.UNDEAD)) {
/* 62 */       return paramInt * 2.5F;
/*    */     }
/* 64 */     if ((this.a == 2) && (paramEnumMonsterType == EnumMonsterType.ARTHROPOD)) {
/* 65 */       return paramInt * 2.5F;
/*    */     }
/* 67 */     return 0.0F;
/*    */   }
/*    */   
/*    */   public String a()
/*    */   {
/* 72 */     return "enchantment.damage." + E[this.a];
/*    */   }
/*    */   
/*    */   public boolean a(Enchantment paramEnchantment)
/*    */   {
/* 77 */     return !(paramEnchantment instanceof EnchantmentWeaponDamage);
/*    */   }
/*    */   
/*    */   public boolean canEnchant(ItemStack paramItemStack)
/*    */   {
/* 82 */     if ((paramItemStack.getItem() instanceof ItemAxe)) {
/* 83 */       return true;
/*    */     }
/* 85 */     return super.canEnchant(paramItemStack);
/*    */   }
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, Entity paramEntity, int paramInt)
/*    */   {
/* 90 */     if ((paramEntity instanceof EntityLiving)) {
/* 91 */       EntityLiving localEntityLiving = (EntityLiving)paramEntity;
/*    */       
/* 93 */       if ((this.a == 2) && (localEntityLiving.getMonsterType() == EnumMonsterType.ARTHROPOD)) {
/* 94 */         int i = 20 + paramEntityLiving.bc().nextInt(10 * paramInt);
/* 95 */         localEntityLiving.addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, i, 3));
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentWeaponDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */