/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.enchantments.CraftEnchantment;
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  12 */   private static final Enchantment[] byId = new Enchantment['Ā'];
/*     */   public static final Enchantment[] b;
/*  14 */   private static final Map<MinecraftKey, Enchantment> E = Maps.newHashMap();
/*  15 */   public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentProtection(0, new MinecraftKey("protection"), 10, 0);
/*  16 */   public static final Enchantment PROTECTION_FIRE = new EnchantmentProtection(1, new MinecraftKey("fire_protection"), 5, 1);
/*  17 */   public static final Enchantment PROTECTION_FALL = new EnchantmentProtection(2, new MinecraftKey("feather_falling"), 5, 2);
/*  18 */   public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentProtection(3, new MinecraftKey("blast_protection"), 2, 3);
/*  19 */   public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentProtection(4, new MinecraftKey("projectile_protection"), 5, 4);
/*  20 */   public static final Enchantment OXYGEN = new EnchantmentOxygen(5, new MinecraftKey("respiration"), 2);
/*  21 */   public static final Enchantment WATER_WORKER = new EnchantmentWaterWorker(6, new MinecraftKey("aqua_affinity"), 2);
/*  22 */   public static final Enchantment THORNS = new EnchantmentThorns(7, new MinecraftKey("thorns"), 1);
/*  23 */   public static final Enchantment DEPTH_STRIDER = new EnchantmentDepthStrider(8, new MinecraftKey("depth_strider"), 2);
/*  24 */   public static final Enchantment DAMAGE_ALL = new EnchantmentWeaponDamage(16, new MinecraftKey("sharpness"), 10, 0);
/*  25 */   public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWeaponDamage(17, new MinecraftKey("smite"), 5, 1);
/*  26 */   public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWeaponDamage(18, new MinecraftKey("bane_of_arthropods"), 5, 2);
/*  27 */   public static final Enchantment KNOCKBACK = new EnchantmentKnockback(19, new MinecraftKey("knockback"), 5);
/*  28 */   public static final Enchantment FIRE_ASPECT = new EnchantmentFire(20, new MinecraftKey("fire_aspect"), 2);
/*  29 */   public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentLootBonus(21, new MinecraftKey("looting"), 2, EnchantmentSlotType.WEAPON);
/*  30 */   public static final Enchantment DIG_SPEED = new EnchantmentDigging(32, new MinecraftKey("efficiency"), 10);
/*  31 */   public static final Enchantment SILK_TOUCH = new EnchantmentSilkTouch(33, new MinecraftKey("silk_touch"), 1);
/*  32 */   public static final Enchantment DURABILITY = new EnchantmentDurability(34, new MinecraftKey("unbreaking"), 5);
/*  33 */   public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentLootBonus(35, new MinecraftKey("fortune"), 2, EnchantmentSlotType.DIGGER);
/*  34 */   public static final Enchantment ARROW_DAMAGE = new EnchantmentArrowDamage(48, new MinecraftKey("power"), 10);
/*  35 */   public static final Enchantment ARROW_KNOCKBACK = new EnchantmentArrowKnockback(49, new MinecraftKey("punch"), 2);
/*  36 */   public static final Enchantment ARROW_FIRE = new EnchantmentFlameArrows(50, new MinecraftKey("flame"), 2);
/*  37 */   public static final Enchantment ARROW_INFINITE = new EnchantmentInfiniteArrows(51, new MinecraftKey("infinity"), 1);
/*  38 */   public static final Enchantment LUCK = new EnchantmentLootBonus(61, new MinecraftKey("luck_of_the_sea"), 2, EnchantmentSlotType.FISHING_ROD);
/*  39 */   public static final Enchantment LURE = new EnchantmentLure(62, new MinecraftKey("lure"), 2, EnchantmentSlotType.FISHING_ROD);
/*     */   public final int id;
/*     */   private final int weight;
/*     */   public EnchantmentSlotType slot;
/*     */   protected String name;
/*     */   
/*     */   public static Enchantment getById(int i) {
/*  46 */     return (i >= 0) && (i < byId.length) ? byId[i] : null;
/*     */   }
/*     */   
/*     */   protected Enchantment(int i, MinecraftKey minecraftkey, int j, EnchantmentSlotType enchantmentslottype) {
/*  50 */     this.id = i;
/*  51 */     this.weight = j;
/*  52 */     this.slot = enchantmentslottype;
/*  53 */     if (byId[i] != null) {
/*  54 */       throw new IllegalArgumentException("Duplicate enchantment id!");
/*     */     }
/*  56 */     byId[i] = this;
/*  57 */     E.put(minecraftkey, this);
/*     */     
/*     */ 
/*  60 */     org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment(this));
/*     */   }
/*     */   
/*     */   public static Enchantment getByName(String s) {
/*  64 */     return (Enchantment)E.get(new MinecraftKey(s));
/*     */   }
/*     */   
/*     */   public static Set<MinecraftKey> getEffects() {
/*  68 */     return E.keySet();
/*     */   }
/*     */   
/*     */   public int getRandomWeight() {
/*  72 */     return this.weight;
/*     */   }
/*     */   
/*     */   public int getStartLevel() {
/*  76 */     return 1;
/*     */   }
/*     */   
/*     */   public int getMaxLevel() {
/*  80 */     return 1;
/*     */   }
/*     */   
/*     */   public int a(int i) {
/*  84 */     return 1 + i * 10;
/*     */   }
/*     */   
/*     */   public int b(int i) {
/*  88 */     return a(i) + 5;
/*     */   }
/*     */   
/*     */   public int a(int i, DamageSource damagesource) {
/*  92 */     return 0;
/*     */   }
/*     */   
/*     */   public float a(int i, EnumMonsterType enummonstertype) {
/*  96 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean a(Enchantment enchantment) {
/* 100 */     return this != enchantment;
/*     */   }
/*     */   
/*     */   public Enchantment c(String s) {
/* 104 */     this.name = s;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public String a() {
/* 109 */     return "enchantment." + this.name;
/*     */   }
/*     */   
/*     */   public String d(int i) {
/* 113 */     String s = LocaleI18n.get(a());
/*     */     
/* 115 */     return s + " " + LocaleI18n.get(new StringBuilder("enchantment.level.").append(i).toString());
/*     */   }
/*     */   
/*     */   public boolean canEnchant(ItemStack itemstack) {
/* 119 */     return this.slot.canEnchant(itemstack.getItem());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 127 */     ArrayList arraylist = Lists.newArrayList();
/* 128 */     Enchantment[] aenchantment = byId;
/* 129 */     int i = aenchantment.length;
/*     */     
/* 131 */     for (int j = 0; j < i; j++) {
/* 132 */       Enchantment enchantment = aenchantment[j];
/*     */       
/* 134 */       if (enchantment != null) {
/* 135 */         arraylist.add(enchantment);
/*     */       }
/*     */     }
/*     */     
/* 139 */     b = (Enchantment[])arraylist.toArray(new Enchantment[arraylist.size()]);
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, Entity entity, int i) {}
/*     */   
/*     */   public void b(EntityLiving entityliving, Entity entity, int i) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Enchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */