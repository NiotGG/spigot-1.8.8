/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentProtection
/*     */   extends Enchantment
/*     */ {
/*  15 */   private static final String[] E = { "all", "fire", "fall", "explosion", "projectile" };
/*     */   
/*     */ 
/*     */ 
/*  19 */   private static final int[] F = { 1, 10, 5, 5, 3 };
/*     */   
/*     */ 
/*     */ 
/*  23 */   private static final int[] G = { 11, 8, 6, 8, 6 };
/*     */   
/*     */ 
/*     */ 
/*  27 */   private static final int[] H = { 20, 12, 10, 12, 15 };
/*     */   
/*     */   public final int a;
/*     */   
/*     */ 
/*     */   public EnchantmentProtection(int paramInt1, MinecraftKey paramMinecraftKey, int paramInt2, int paramInt3)
/*     */   {
/*  34 */     super(paramInt1, paramMinecraftKey, paramInt2, EnchantmentSlotType.ARMOR);
/*  35 */     this.a = paramInt3;
/*     */     
/*  37 */     if (paramInt3 == 2) {
/*  38 */       this.slot = EnchantmentSlotType.ARMOR_FEET;
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(int paramInt)
/*     */   {
/*  44 */     return F[this.a] + (paramInt - 1) * G[this.a];
/*     */   }
/*     */   
/*     */   public int b(int paramInt)
/*     */   {
/*  49 */     return a(paramInt) + H[this.a];
/*     */   }
/*     */   
/*     */   public int getMaxLevel()
/*     */   {
/*  54 */     return 4;
/*     */   }
/*     */   
/*     */   public int a(int paramInt, DamageSource paramDamageSource)
/*     */   {
/*  59 */     if (paramDamageSource.ignoresInvulnerability()) {
/*  60 */       return 0;
/*     */     }
/*     */     
/*  63 */     float f = (6 + paramInt * paramInt) / 3.0F;
/*     */     
/*  65 */     if (this.a == 0) {
/*  66 */       return MathHelper.d(f * 0.75F);
/*     */     }
/*  68 */     if ((this.a == 1) && (paramDamageSource.o())) {
/*  69 */       return MathHelper.d(f * 1.25F);
/*     */     }
/*  71 */     if ((this.a == 2) && (paramDamageSource == DamageSource.FALL)) {
/*  72 */       return MathHelper.d(f * 2.5F);
/*     */     }
/*  74 */     if ((this.a == 3) && (paramDamageSource.isExplosion())) {
/*  75 */       return MathHelper.d(f * 1.5F);
/*     */     }
/*  77 */     if ((this.a == 4) && (paramDamageSource.a())) {
/*  78 */       return MathHelper.d(f * 1.5F);
/*     */     }
/*  80 */     return 0;
/*     */   }
/*     */   
/*     */   public String a()
/*     */   {
/*  85 */     return "enchantment.protect." + E[this.a];
/*     */   }
/*     */   
/*     */   public boolean a(Enchantment paramEnchantment)
/*     */   {
/*  90 */     if ((paramEnchantment instanceof EnchantmentProtection)) {
/*  91 */       EnchantmentProtection localEnchantmentProtection = (EnchantmentProtection)paramEnchantment;
/*     */       
/*  93 */       if (localEnchantmentProtection.a == this.a) {
/*  94 */         return false;
/*     */       }
/*  96 */       if ((this.a == 2) || (localEnchantmentProtection.a == 2)) {
/*  97 */         return true;
/*     */       }
/*  99 */       return false;
/*     */     }
/* 101 */     return super.a(paramEnchantment);
/*     */   }
/*     */   
/*     */   public static int a(Entity paramEntity, int paramInt) {
/* 105 */     int i = EnchantmentManager.a(Enchantment.PROTECTION_FIRE.id, paramEntity.getEquipment());
/*     */     
/* 107 */     if (i > 0) {
/* 108 */       paramInt -= MathHelper.d(paramInt * (i * 0.15F));
/*     */     }
/*     */     
/* 111 */     return paramInt;
/*     */   }
/*     */   
/*     */   public static double a(Entity paramEntity, double paramDouble) {
/* 115 */     int i = EnchantmentManager.a(Enchantment.PROTECTION_EXPLOSIONS.id, paramEntity.getEquipment());
/*     */     
/* 117 */     if (i > 0) {
/* 118 */       paramDouble -= MathHelper.floor(paramDouble * (i * 0.15F));
/*     */     }
/*     */     
/* 121 */     return paramDouble;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentProtection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */