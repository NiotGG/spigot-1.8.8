/*     */ package org.bukkit.craftbukkit.v1_8_R3.enchantments;
/*     */ 
/*     */ import org.bukkit.enchantments.EnchantmentTarget;
/*     */ import org.bukkit.enchantments.EnchantmentWrapper;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftEnchantment extends org.bukkit.enchantments.Enchantment
/*     */ {
/*     */   private final net.minecraft.server.v1_8_R3.Enchantment target;
/*     */   
/*     */   public CraftEnchantment(net.minecraft.server.v1_8_R3.Enchantment target)
/*     */   {
/*  13 */     super(target.id);
/*  14 */     this.target = target;
/*     */   }
/*     */   
/*     */   public int getMaxLevel()
/*     */   {
/*  19 */     return this.target.getMaxLevel();
/*     */   }
/*     */   
/*     */   public int getStartLevel()
/*     */   {
/*  24 */     return this.target.getStartLevel();
/*     */   }
/*     */   
/*     */   public EnchantmentTarget getItemTarget()
/*     */   {
/*  29 */     switch (this.target.slot) {
/*     */     case ALL: 
/*  31 */       return EnchantmentTarget.ALL;
/*     */     case ARMOR: 
/*  33 */       return EnchantmentTarget.ARMOR;
/*     */     case ARMOR_FEET: 
/*  35 */       return EnchantmentTarget.ARMOR_FEET;
/*     */     case ARMOR_TORSO: 
/*  37 */       return EnchantmentTarget.ARMOR_HEAD;
/*     */     case ARMOR_HEAD: 
/*  39 */       return EnchantmentTarget.ARMOR_LEGS;
/*     */     case ARMOR_LEGS: 
/*  41 */       return EnchantmentTarget.ARMOR_TORSO;
/*     */     case BREAKABLE: 
/*  43 */       return EnchantmentTarget.TOOL;
/*     */     case BOW: 
/*  45 */       return EnchantmentTarget.WEAPON;
/*     */     case WEAPON: 
/*  47 */       return EnchantmentTarget.BOW;
/*     */     case DIGGER: 
/*  49 */       return EnchantmentTarget.FISHING_ROD;
/*     */     }
/*  51 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canEnchantItem(ItemStack item)
/*     */   {
/*  57 */     return this.target.canEnchant(org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item));
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  62 */     switch (this.target.id) {
/*     */     case 0: 
/*  64 */       return "PROTECTION_ENVIRONMENTAL";
/*     */     case 1: 
/*  66 */       return "PROTECTION_FIRE";
/*     */     case 2: 
/*  68 */       return "PROTECTION_FALL";
/*     */     case 3: 
/*  70 */       return "PROTECTION_EXPLOSIONS";
/*     */     case 4: 
/*  72 */       return "PROTECTION_PROJECTILE";
/*     */     case 5: 
/*  74 */       return "OXYGEN";
/*     */     case 6: 
/*  76 */       return "WATER_WORKER";
/*     */     case 7: 
/*  78 */       return "THORNS";
/*     */     case 8: 
/*  80 */       return "DEPTH_STRIDER";
/*     */     case 16: 
/*  82 */       return "DAMAGE_ALL";
/*     */     case 17: 
/*  84 */       return "DAMAGE_UNDEAD";
/*     */     case 18: 
/*  86 */       return "DAMAGE_ARTHROPODS";
/*     */     case 19: 
/*  88 */       return "KNOCKBACK";
/*     */     case 20: 
/*  90 */       return "FIRE_ASPECT";
/*     */     case 21: 
/*  92 */       return "LOOT_BONUS_MOBS";
/*     */     case 32: 
/*  94 */       return "DIG_SPEED";
/*     */     case 33: 
/*  96 */       return "SILK_TOUCH";
/*     */     case 34: 
/*  98 */       return "DURABILITY";
/*     */     case 35: 
/* 100 */       return "LOOT_BONUS_BLOCKS";
/*     */     case 48: 
/* 102 */       return "ARROW_DAMAGE";
/*     */     case 49: 
/* 104 */       return "ARROW_KNOCKBACK";
/*     */     case 50: 
/* 106 */       return "ARROW_FIRE";
/*     */     case 51: 
/* 108 */       return "ARROW_INFINITE";
/*     */     case 61: 
/* 110 */       return "LUCK";
/*     */     case 62: 
/* 112 */       return "LURE";
/*     */     }
/* 114 */     return "UNKNOWN_ENCHANT_" + this.target.id;
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Enchantment getRaw(org.bukkit.enchantments.Enchantment enchantment)
/*     */   {
/* 119 */     if ((enchantment instanceof EnchantmentWrapper)) {
/* 120 */       enchantment = ((EnchantmentWrapper)enchantment).getEnchantment();
/*     */     }
/*     */     
/* 123 */     if ((enchantment instanceof CraftEnchantment)) {
/* 124 */       return ((CraftEnchantment)enchantment).target;
/*     */     }
/*     */     
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public boolean conflictsWith(org.bukkit.enchantments.Enchantment other)
/*     */   {
/* 132 */     if ((other instanceof EnchantmentWrapper)) {
/* 133 */       other = ((EnchantmentWrapper)other).getEnchantment();
/*     */     }
/* 135 */     if (!(other instanceof CraftEnchantment)) {
/* 136 */       return false;
/*     */     }
/* 138 */     CraftEnchantment ench = (CraftEnchantment)other;
/* 139 */     return !this.target.a(ench.target);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\enchantments\CraftEnchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */