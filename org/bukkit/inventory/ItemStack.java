/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemStack
/*     */   implements Cloneable, ConfigurationSerializable
/*     */ {
/*  20 */   private int type = 0;
/*  21 */   private int amount = 0;
/*  22 */   private MaterialData data = null;
/*  23 */   private short durability = 0;
/*     */   
/*     */ 
/*     */   private ItemMeta meta;
/*     */   
/*     */ 
/*     */ 
/*     */   protected ItemStack() {}
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ItemStack(int type)
/*     */   {
/*  37 */     this(type, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack(Material type)
/*     */   {
/*  46 */     this(type, 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ItemStack(int type, int amount)
/*     */   {
/*  58 */     this(type, amount, (short)0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack(Material type, int amount)
/*     */   {
/*  68 */     this(type.getId(), amount);
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
/*     */   public ItemStack(int type, int amount, short damage)
/*     */   {
/*  81 */     this.type = type;
/*  82 */     this.amount = amount;
/*  83 */     this.durability = damage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack(Material type, int amount, short damage)
/*     */   {
/*  94 */     this(type.getId(), amount, damage);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ItemStack(int type, int amount, short damage, Byte data)
/*     */   {
/* 106 */     this.type = type;
/* 107 */     this.amount = amount;
/* 108 */     this.durability = damage;
/* 109 */     if (data != null) {
/* 110 */       createData(data.byteValue());
/* 111 */       this.durability = data.byteValue();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public ItemStack(Material type, int amount, short damage, Byte data)
/*     */   {
/* 124 */     this(type.getId(), amount, damage, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack(ItemStack stack)
/*     */     throws IllegalArgumentException
/*     */   {
/* 135 */     Validate.notNull(stack, "Cannot copy null stack");
/* 136 */     this.type = stack.getTypeId();
/* 137 */     this.amount = stack.getAmount();
/* 138 */     this.durability = stack.getDurability();
/* 139 */     this.data = stack.getData();
/* 140 */     if (stack.hasItemMeta()) {
/* 141 */       setItemMeta0(stack.getItemMeta(), getType0());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getType()
/*     */   {
/* 152 */     return getType0(getTypeId());
/*     */   }
/*     */   
/*     */   private Material getType0() {
/* 156 */     return getType0(this.type);
/*     */   }
/*     */   
/*     */   private static Material getType0(int id) {
/* 160 */     Material material = Material.getMaterial(id);
/* 161 */     return material == null ? Material.AIR : material;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(Material type)
/*     */   {
/* 173 */     Validate.notNull(type, "Material cannot be null");
/* 174 */     setTypeId(type.getId());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getTypeId()
/*     */   {
/* 185 */     return this.type;
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
/*     */   public void setTypeId(int type)
/*     */   {
/* 198 */     this.type = type;
/* 199 */     if (this.meta != null) {
/* 200 */       this.meta = Bukkit.getItemFactory().asMetaFor(this.meta, getType0());
/*     */     }
/* 202 */     createData((byte)0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getAmount()
/*     */   {
/* 211 */     return this.amount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAmount(int amount)
/*     */   {
/* 220 */     this.amount = amount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MaterialData getData()
/*     */   {
/* 229 */     Material mat = getType();
/* 230 */     if ((this.data == null) && (mat != null) && (mat.getData() != null)) {
/* 231 */       this.data = mat.getNewData((byte)getDurability());
/*     */     }
/*     */     
/* 234 */     return this.data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setData(MaterialData data)
/*     */   {
/* 243 */     Material mat = getType();
/*     */     
/* 245 */     if ((data == null) || (mat == null) || (mat.getData() == null)) {
/* 246 */       this.data = data;
/*     */     }
/* 248 */     else if ((data.getClass() == mat.getData()) || (data.getClass() == MaterialData.class)) {
/* 249 */       this.data = data;
/*     */     } else {
/* 251 */       throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDurability(short durability)
/*     */   {
/* 262 */     this.durability = durability;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getDurability()
/*     */   {
/* 271 */     return this.durability;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 282 */     Material material = getType();
/* 283 */     if (material != null) {
/* 284 */       return material.getMaxStackSize();
/*     */     }
/* 286 */     return -1;
/*     */   }
/*     */   
/*     */   private void createData(byte data) {
/* 290 */     Material mat = Material.getMaterial(this.type);
/*     */     
/* 292 */     if (mat == null) {
/* 293 */       this.data = new MaterialData(this.type, data);
/*     */     } else {
/* 295 */       this.data = mat.getNewData(data);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 302 */     StringBuilder toString = new StringBuilder("ItemStack{").append(getType().name()).append(" x ").append(getAmount());
/* 303 */     if (hasItemMeta()) {
/* 304 */       toString.append(", ").append(getItemMeta());
/*     */     }
/* 306 */     return '}';
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 312 */     if (this == obj) {
/* 313 */       return true;
/*     */     }
/* 315 */     if (!(obj instanceof ItemStack)) {
/* 316 */       return false;
/*     */     }
/*     */     
/* 319 */     ItemStack stack = (ItemStack)obj;
/* 320 */     return (getAmount() == stack.getAmount()) && (isSimilar(stack));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSimilar(ItemStack stack)
/*     */   {
/* 332 */     if (stack == null) {
/* 333 */       return false;
/*     */     }
/* 335 */     if (stack == this) {
/* 336 */       return true;
/*     */     }
/* 338 */     return (getTypeId() == stack.getTypeId()) && (getDurability() == stack.getDurability()) && (hasItemMeta() == stack.hasItemMeta()) && ((!hasItemMeta()) || (Bukkit.getItemFactory().equals(getItemMeta(), stack.getItemMeta())));
/*     */   }
/*     */   
/*     */   public ItemStack clone()
/*     */   {
/*     */     try {
/* 344 */       ItemStack itemStack = (ItemStack)super.clone();
/*     */       
/* 346 */       if (this.meta != null) {
/* 347 */         itemStack.meta = this.meta.clone();
/*     */       }
/*     */       
/* 350 */       if (this.data != null) {
/* 351 */         itemStack.data = this.data.clone();
/*     */       }
/*     */       
/* 354 */       return itemStack;
/*     */     } catch (CloneNotSupportedException e) {
/* 356 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public final int hashCode()
/*     */   {
/* 363 */     int hash = 1;
/*     */     
/* 365 */     hash = hash * 31 + getTypeId();
/* 366 */     hash = hash * 31 + getAmount();
/* 367 */     hash = hash * 31 + (getDurability() & 0xFFFF);
/* 368 */     hash = hash * 31 + (hasItemMeta() ? this.meta.hashCode() : this.meta == null ? getItemMeta().hashCode() : 0);
/*     */     
/* 370 */     return hash;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsEnchantment(Enchantment ench)
/*     */   {
/* 380 */     return this.meta == null ? false : this.meta.hasEnchant(ench);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEnchantmentLevel(Enchantment ench)
/*     */   {
/* 390 */     return this.meta == null ? 0 : this.meta.getEnchantLevel(ench);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<Enchantment, Integer> getEnchantments()
/*     */   {
/* 399 */     return this.meta == null ? ImmutableMap.of() : this.meta.getEnchants();
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
/*     */   public void addEnchantments(Map<Enchantment, Integer> enchantments)
/*     */   {
/* 417 */     Validate.notNull(enchantments, "Enchantments cannot be null");
/* 418 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/* 419 */       addEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
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
/*     */   public void addEnchantment(Enchantment ench, int level)
/*     */   {
/* 436 */     Validate.notNull(ench, "Enchantment cannot be null");
/* 437 */     if ((level < ench.getStartLevel()) || (level > ench.getMaxLevel()))
/* 438 */       throw new IllegalArgumentException("Enchantment level is either too low or too high (given " + level + ", bounds are " + ench.getStartLevel() + " to " + ench.getMaxLevel() + ")");
/* 439 */     if (!ench.canEnchantItem(this)) {
/* 440 */       throw new IllegalArgumentException("Specified enchantment cannot be applied to this itemstack");
/*     */     }
/*     */     
/* 443 */     addUnsafeEnchantment(ench, level);
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
/*     */   public void addUnsafeEnchantments(Map<Enchantment, Integer> enchantments)
/*     */   {
/* 457 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/* 458 */       addUnsafeEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
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
/*     */   public void addUnsafeEnchantment(Enchantment ench, int level)
/*     */   {
/* 475 */     (this.meta == null ? (this.meta = Bukkit.getItemFactory().getItemMeta(getType0())) : this.meta).addEnchant(ench, level, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int removeEnchantment(Enchantment ench)
/*     */   {
/* 486 */     int level = getEnchantmentLevel(ench);
/* 487 */     if ((level == 0) || (this.meta == null)) {
/* 488 */       return level;
/*     */     }
/* 490 */     this.meta.removeEnchant(ench);
/* 491 */     return level;
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize()
/*     */   {
/* 496 */     Map<String, Object> result = new LinkedHashMap();
/*     */     
/* 498 */     result.put("type", getType().name());
/*     */     
/* 500 */     if (getDurability() != 0) {
/* 501 */       result.put("damage", Short.valueOf(getDurability()));
/*     */     }
/*     */     
/* 504 */     if (getAmount() != 1) {
/* 505 */       result.put("amount", Integer.valueOf(getAmount()));
/*     */     }
/*     */     
/* 508 */     ItemMeta meta = getItemMeta();
/* 509 */     if (!Bukkit.getItemFactory().equals(meta, null)) {
/* 510 */       result.put("meta", meta);
/*     */     }
/*     */     
/* 513 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ItemStack deserialize(Map<String, Object> args)
/*     */   {
/* 524 */     Material type = Material.getMaterial((String)args.get("type"));
/* 525 */     short damage = 0;
/* 526 */     int amount = 1;
/*     */     
/* 528 */     if (args.containsKey("damage")) {
/* 529 */       damage = ((Number)args.get("damage")).shortValue();
/*     */     }
/*     */     
/* 532 */     if (args.containsKey("amount")) {
/* 533 */       amount = ((Integer)args.get("amount")).intValue();
/*     */     }
/*     */     
/* 536 */     ItemStack result = new ItemStack(type, amount, damage);
/*     */     
/* 538 */     if (args.containsKey("enchantments")) {
/* 539 */       Object raw = args.get("enchantments");
/*     */       
/* 541 */       if ((raw instanceof Map)) {
/* 542 */         Map<?, ?> map = (Map)raw;
/*     */         
/* 544 */         for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 545 */           Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
/*     */           
/* 547 */           if ((enchantment != null) && ((entry.getValue() instanceof Integer))) {
/* 548 */             result.addUnsafeEnchantment(enchantment, ((Integer)entry.getValue()).intValue());
/*     */           }
/*     */         }
/*     */       }
/* 552 */     } else if (args.containsKey("meta")) {
/* 553 */       Object raw = args.get("meta");
/* 554 */       if ((raw instanceof ItemMeta)) {
/* 555 */         result.setItemMeta((ItemMeta)raw);
/*     */       }
/*     */     }
/*     */     
/* 559 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemMeta getItemMeta()
/*     */   {
/* 568 */     return this.meta == null ? Bukkit.getItemFactory().getItemMeta(getType0()) : this.meta.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasItemMeta()
/*     */   {
/* 577 */     return !Bukkit.getItemFactory().equals(this.meta, null);
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
/*     */   public boolean setItemMeta(ItemMeta itemMeta)
/*     */   {
/* 590 */     return setItemMeta0(itemMeta, getType0());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean setItemMeta0(ItemMeta itemMeta, Material material)
/*     */   {
/* 597 */     if (itemMeta == null) {
/* 598 */       this.meta = null;
/* 599 */       return true;
/*     */     }
/* 601 */     if (!Bukkit.getItemFactory().isApplicable(itemMeta, material)) {
/* 602 */       return false;
/*     */     }
/* 604 */     this.meta = Bukkit.getItemFactory().asMetaFor(itemMeta, material);
/* 605 */     if (this.meta == itemMeta) {
/* 606 */       this.meta = itemMeta.clone();
/*     */     }
/*     */     
/* 609 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */