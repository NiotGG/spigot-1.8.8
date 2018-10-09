/*     */ package org.bukkit.potion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Potion
/*     */ {
/*  16 */   private boolean extended = false;
/*  17 */   private boolean splash = false;
/*  18 */   private int level = 1;
/*  19 */   private int name = -1;
/*     */   
/*     */   private PotionType type;
/*     */   private static PotionBrewer brewer;
/*     */   private static final int EXTENDED_BIT = 64;
/*     */   private static final int POTION_BIT = 15;
/*     */   private static final int SPLASH_BIT = 16384;
/*     */   private static final int TIER_BIT = 32;
/*     */   private static final int TIER_SHIFT = 5;
/*     */   private static final int NAME_BIT = 63;
/*     */   
/*     */   public Potion(PotionType type)
/*     */   {
/*  32 */     this.type = type;
/*  33 */     if (type != null) {
/*  34 */       this.name = type.getDamageValue();
/*     */     }
/*  36 */     if ((type == null) || (type == PotionType.WATER)) {
/*  37 */       this.level = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier)
/*     */   {
/*  48 */     this(type, tier == Tier.TWO ? 2 : 1);
/*  49 */     Validate.notNull(type, "Type cannot be null");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier, boolean splash)
/*     */   {
/*  60 */     this(type, tier == Tier.TWO ? 2 : 1, splash);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Potion(PotionType type, Tier tier, boolean splash, boolean extended)
/*     */   {
/*  73 */     this(type, tier, splash);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Potion(PotionType type, int level)
/*     */   {
/*  84 */     this(type);
/*  85 */     Validate.notNull(type, "Type cannot be null");
/*  86 */     Validate.isTrue(type != PotionType.WATER, "Water bottles don't have a level!");
/*  87 */     Validate.isTrue((level > 0) && (level < 3), "Level must be 1 or 2");
/*  88 */     this.level = level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Potion(PotionType type, int level, boolean splash)
/*     */   {
/* 102 */     this(type, level);
/* 103 */     this.splash = splash;
/*     */   }
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
/*     */   @Deprecated
/*     */   public Potion(PotionType type, int level, boolean splash, boolean extended)
/*     */   {
/* 118 */     this(type, level, splash);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Potion(int name)
/*     */   {
/* 128 */     this(PotionType.getByDamageValue(name & 0xF));
/* 129 */     this.name = (name & 0x3F);
/* 130 */     if ((name & 0xF) == 0)
/*     */     {
/* 132 */       this.type = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Potion splash()
/*     */   {
/* 142 */     setSplash(true);
/* 143 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Potion extend()
/*     */   {
/* 152 */     setHasExtendedDuration(true);
/* 153 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void apply(ItemStack to)
/*     */   {
/* 163 */     Validate.notNull(to, "itemstack cannot be null");
/* 164 */     Validate.isTrue(to.getType() == Material.POTION, "given itemstack is not a potion");
/* 165 */     to.setDurability(toDamageValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void apply(LivingEntity to)
/*     */   {
/* 176 */     Validate.notNull(to, "entity cannot be null");
/* 177 */     to.addPotionEffects(getEffects());
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 182 */     if (this == obj) {
/* 183 */       return true;
/*     */     }
/* 185 */     if ((obj == null) || (getClass() != obj.getClass())) {
/* 186 */       return false;
/*     */     }
/* 188 */     Potion other = (Potion)obj;
/* 189 */     return (this.extended == other.extended) && (this.splash == other.splash) && (this.level == other.level) && (this.type == other.type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<PotionEffect> getEffects()
/*     */   {
/* 201 */     if (this.type == null) return ImmutableList.of();
/* 202 */     return getBrewer().getEffectsFromDamage(toDamageValue());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLevel()
/*     */   {
/* 211 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Tier getTier()
/*     */   {
/* 221 */     return this.level == 2 ? Tier.TWO : Tier.ONE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PotionType getType()
/*     */   {
/* 230 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasExtendedDuration()
/*     */   {
/* 239 */     return this.extended;
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 245 */     int result = 31 + this.level;
/* 246 */     result = 31 * result + (this.extended ? 1231 : 1237);
/* 247 */     result = 31 * result + (this.splash ? 1231 : 1237);
/* 248 */     result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
/* 249 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSplash()
/*     */   {
/* 258 */     return this.splash;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHasExtendedDuration(boolean isExtended)
/*     */   {
/* 268 */     Validate.isTrue((this.type == null) || (!this.type.isInstant()), "Instant potions cannot be extended");
/* 269 */     this.extended = isExtended;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSplash(boolean isSplash)
/*     */   {
/* 279 */     this.splash = isSplash;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void setTier(Tier tier)
/*     */   {
/* 290 */     Validate.notNull(tier, "tier cannot be null");
/* 291 */     this.level = (tier == Tier.TWO ? 2 : 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(PotionType type)
/*     */   {
/* 300 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(int level)
/*     */   {
/* 309 */     Validate.notNull(this.type, "No-effect potions don't have a level.");
/* 310 */     int max = this.type.getMaxLevel();
/* 311 */     Validate.isTrue((level > 0) && (level <= max), "Level must be " + (max == 1 ? "" : "between 1 and ") + max + " for this potion");
/* 312 */     this.level = level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public short toDamageValue()
/*     */   {
/* 325 */     if (this.type == PotionType.WATER)
/* 326 */       return 0;
/* 327 */     short damage; short damage; if (this.type == null)
/*     */     {
/* 329 */       damage = (short)(this.name == 0 ? 8192 : this.name);
/*     */     } else {
/* 331 */       damage = (short)(this.level - 1);
/* 332 */       damage = (short)(damage << 5);
/* 333 */       damage = (short)(damage | (short)this.type.getDamageValue());
/*     */     }
/* 335 */     if (this.splash) {
/* 336 */       damage = (short)(damage | 0x4000);
/*     */     }
/* 338 */     if (this.extended) {
/* 339 */       damage = (short)(damage | 0x40);
/*     */     }
/* 341 */     return damage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack toItemStack(int amount)
/*     */   {
/* 352 */     return new ItemStack(Material.POTION, amount, toDamageValue());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static enum Tier {
/* 357 */     ONE(0), 
/* 358 */     TWO(32);
/*     */     
/*     */     private int damageBit;
/*     */     
/*     */     private Tier(int bit) {
/* 363 */       this.damageBit = bit;
/*     */     }
/*     */     
/*     */ 
/* 367 */     public int getDamageBit() { return this.damageBit; }
/*     */     
/*     */     public static Tier getByDamageBit(int damageBit) {
/*     */       Tier[] arrayOfTier;
/* 371 */       int i = (arrayOfTier = values()).length; for (int j = 0; j < i; j++) { Tier tier = arrayOfTier[j];
/* 372 */         if (tier.damageBit == damageBit)
/* 373 */           return tier;
/*     */       }
/* 375 */       return null;
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Potion fromDamage(int damage)
/*     */   {
/* 396 */     PotionType type = PotionType.getByDamageValue(damage & 0xF);
/*     */     Potion potion;
/* 398 */     Potion potion; if ((type == null) || (type == PotionType.WATER)) {
/* 399 */       potion = new Potion(damage & 0x3F);
/*     */     } else {
/* 401 */       int level = (damage & 0x20) >> 5;
/* 402 */       level++;
/* 403 */       potion = new Potion(type, level);
/*     */     }
/* 405 */     if ((damage & 0x4000) > 0) {
/* 406 */       potion = potion.splash();
/*     */     }
/* 408 */     if (((type == null) || (!type.isInstant())) && ((damage & 0x40) > 0)) {
/* 409 */       potion = potion.extend();
/*     */     }
/* 411 */     return potion;
/*     */   }
/*     */   
/*     */   public static Potion fromItemStack(ItemStack item) {
/* 415 */     Validate.notNull(item, "item cannot be null");
/* 416 */     if (item.getType() != Material.POTION)
/* 417 */       throw new IllegalArgumentException("item is not a potion");
/* 418 */     return fromDamage(item.getDurability());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static PotionBrewer getBrewer()
/*     */   {
/* 427 */     return brewer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setPotionBrewer(PotionBrewer other)
/*     */   {
/* 437 */     if (brewer != null)
/* 438 */       throw new IllegalArgumentException("brewer can only be set internally");
/* 439 */     brewer = other;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getNameId()
/*     */   {
/* 449 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\Potion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */