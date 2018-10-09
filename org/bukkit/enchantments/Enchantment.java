/*     */ package org.bukkit.enchantments;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.command.defaults.EnchantCommand;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  16 */   public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentWrapper(0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  21 */   public static final Enchantment PROTECTION_FIRE = new EnchantmentWrapper(1);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  26 */   public static final Enchantment PROTECTION_FALL = new EnchantmentWrapper(2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  31 */   public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentWrapper(3);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  36 */   public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentWrapper(4);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  41 */   public static final Enchantment OXYGEN = new EnchantmentWrapper(5);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  46 */   public static final Enchantment WATER_WORKER = new EnchantmentWrapper(6);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */   public static final Enchantment THORNS = new EnchantmentWrapper(7);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  56 */   public static final Enchantment DEPTH_STRIDER = new EnchantmentWrapper(8);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  61 */   public static final Enchantment DAMAGE_ALL = new EnchantmentWrapper(16);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  66 */   public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWrapper(17);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  71 */   public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWrapper(18);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  76 */   public static final Enchantment KNOCKBACK = new EnchantmentWrapper(19);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  81 */   public static final Enchantment FIRE_ASPECT = new EnchantmentWrapper(20);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  86 */   public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentWrapper(21);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  91 */   public static final Enchantment DIG_SPEED = new EnchantmentWrapper(32);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  97 */   public static final Enchantment SILK_TOUCH = new EnchantmentWrapper(33);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 102 */   public static final Enchantment DURABILITY = new EnchantmentWrapper(34);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 107 */   public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentWrapper(35);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 112 */   public static final Enchantment ARROW_DAMAGE = new EnchantmentWrapper(48);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 117 */   public static final Enchantment ARROW_KNOCKBACK = new EnchantmentWrapper(49);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 122 */   public static final Enchantment ARROW_FIRE = new EnchantmentWrapper(50);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 127 */   public static final Enchantment ARROW_INFINITE = new EnchantmentWrapper(51);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 132 */   public static final Enchantment LUCK = new EnchantmentWrapper(61);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 137 */   public static final Enchantment LURE = new EnchantmentWrapper(62);
/*     */   
/* 139 */   private static final Map<Integer, Enchantment> byId = new HashMap();
/* 140 */   private static final Map<String, Enchantment> byName = new HashMap();
/* 141 */   private static boolean acceptingNew = true;
/*     */   private final int id;
/*     */   
/*     */   public Enchantment(int id) {
/* 145 */     this.id = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getId()
/*     */   {
/* 156 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getName();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int getMaxLevel();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract int getStartLevel();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract EnchantmentTarget getItemTarget();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean conflictsWith(Enchantment paramEnchantment);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean canEnchantItem(ItemStack paramItemStack);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 209 */     if (obj == null) {
/* 210 */       return false;
/*     */     }
/* 212 */     if (!(obj instanceof Enchantment)) {
/* 213 */       return false;
/*     */     }
/* 215 */     Enchantment other = (Enchantment)obj;
/* 216 */     if (this.id != other.id) {
/* 217 */       return false;
/*     */     }
/* 219 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 224 */     return this.id;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 229 */     return "Enchantment[" + this.id + ", " + getName() + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void registerEnchantment(Enchantment enchantment)
/*     */   {
/* 240 */     if ((byId.containsKey(Integer.valueOf(enchantment.id))) || (byName.containsKey(enchantment.getName())))
/* 241 */       throw new IllegalArgumentException("Cannot set already-set enchantment");
/* 242 */     if (!isAcceptingRegistrations()) {
/* 243 */       throw new IllegalStateException("No longer accepting new enchantments (can only be done by the server implementation)");
/*     */     }
/*     */     
/* 246 */     byId.put(Integer.valueOf(enchantment.id), enchantment);
/* 247 */     byName.put(enchantment.getName(), enchantment);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isAcceptingRegistrations()
/*     */   {
/* 256 */     return acceptingNew;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void stopAcceptingRegistrations()
/*     */   {
/* 263 */     acceptingNew = false;
/* 264 */     EnchantCommand.buildEnchantments();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Enchantment getById(int id)
/*     */   {
/* 276 */     return (Enchantment)byId.get(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Enchantment getByName(String name)
/*     */   {
/* 286 */     return (Enchantment)byName.get(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Enchantment[] values()
/*     */   {
/* 295 */     return (Enchantment[])byId.values().toArray(new Enchantment[byId.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\enchantments\Enchantment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */