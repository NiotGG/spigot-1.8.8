/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSet.Builder;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*     */ import org.bukkit.inventory.ItemFactory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public final class CraftItemFactory implements ItemFactory
/*     */ {
/*  16 */   static final Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  21 */   private static final CraftItemFactory instance = new CraftItemFactory();
/*  22 */   static { ConfigurationSerialization.registerClass(CraftMetaItem.SerializableMeta.class); }
/*  23 */   static final Collection<String> KNOWN_NBT_ATTRIBUTE_NAMES = ImmutableSet.builder()
/*  24 */     .add("generic.attackDamage")
/*  25 */     .add("generic.followRange")
/*  26 */     .add("generic.knockbackResistance")
/*  27 */     .add("generic.maxHealth")
/*  28 */     .add("generic.movementSpeed")
/*  29 */     .add("horse.jumpStrength")
/*  30 */     .add("zombie.spawnReinforcements")
/*  31 */     .build();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isApplicable(ItemMeta meta, ItemStack itemstack)
/*     */   {
/*  38 */     if (itemstack == null) {
/*  39 */       return false;
/*     */     }
/*  41 */     return isApplicable(meta, itemstack.getType());
/*     */   }
/*     */   
/*     */   public boolean isApplicable(ItemMeta meta, Material type) {
/*  45 */     if ((type == null) || (meta == null)) {
/*  46 */       return false;
/*     */     }
/*  48 */     if (!(meta instanceof CraftMetaItem)) {
/*  49 */       throw new IllegalArgumentException("Meta of " + meta.getClass().toString() + " not created by " + CraftItemFactory.class.getName());
/*     */     }
/*     */     
/*  52 */     return ((CraftMetaItem)meta).applicableTo(type);
/*     */   }
/*     */   
/*     */   public ItemMeta getItemMeta(Material material) {
/*  56 */     Validate.notNull(material, "Material cannot be null");
/*  57 */     return getItemMeta(material, null);
/*     */   }
/*     */   
/*     */   private ItemMeta getItemMeta(Material material, CraftMetaItem meta) {
/*  61 */     switch (material) {
/*     */     case ACACIA_DOOR: 
/*  63 */       return null;
/*     */     case STAINED_CLAY: 
/*  65 */       return (meta instanceof CraftMetaBookSigned) ? meta : new CraftMetaBookSigned(meta);
/*     */     case SPRUCE_WOOD_STAIRS: 
/*  67 */       return (meta != null) && (meta.getClass().equals(CraftMetaBook.class)) ? meta : new CraftMetaBook(meta);
/*     */     case STONE_BUTTON: 
/*  69 */       return (meta instanceof CraftMetaSkull) ? meta : new CraftMetaSkull(meta);
/*     */     case PACKED_ICE: 
/*     */     case PAINTING: 
/*     */     case PAPER: 
/*     */     case PISTON_BASE: 
/*  74 */       return (meta instanceof CraftMetaLeatherArmor) ? meta : new CraftMetaLeatherArmor(meta);
/*     */     case SMOOTH_STAIRS: 
/*  76 */       return (meta instanceof CraftMetaPotion) ? meta : new CraftMetaPotion(meta);
/*     */     case SADDLE: 
/*  78 */       return (meta instanceof CraftMetaMap) ? meta : new CraftMetaMap(meta);
/*     */     case STONE_SLAB2: 
/*  80 */       return (meta instanceof CraftMetaFirework) ? meta : new CraftMetaFirework(meta);
/*     */     case STONE_SPADE: 
/*  82 */       return (meta instanceof CraftMetaCharge) ? meta : new CraftMetaCharge(meta);
/*     */     case STONE_SWORD: 
/*  84 */       return (meta instanceof CraftMetaEnchantedBook) ? meta : new CraftMetaEnchantedBook(meta);
/*     */     case WHEAT: 
/*  86 */       return (meta instanceof CraftMetaBanner) ? meta : new CraftMetaBanner(meta);
/*     */     case BLAZE_POWDER: 
/*     */     case BOAT: 
/*     */     case BREWING_STAND: 
/*     */     case CHAINMAIL_LEGGINGS: 
/*     */     case CLAY: 
/*     */     case COBBLESTONE_STAIRS: 
/*     */     case DIAMOND_AXE: 
/*     */     case ENDER_PORTAL_FRAME: 
/*     */     case GLASS_BOTTLE: 
/*     */     case GLOWING_REDSTONE_ORE: 
/*     */     case GOLD_BOOTS: 
/*     */     case GOLD_LEGGINGS: 
/*     */     case GOLD_PICKAXE: 
/*     */     case GOLD_SWORD: 
/*     */     case IRON_FENCE: 
/*     */     case QUARTZ_STAIRS: 
/*     */     case SPECKLED_MELON: 
/*     */     case STANDING_BANNER: 
/*     */     case STORAGE_MINECART: 
/* 106 */       return new CraftMetaBlockState(meta, material);
/*     */     }
/* 108 */     return new CraftMetaItem(meta);
/*     */   }
/*     */   
/*     */   public boolean equals(ItemMeta meta1, ItemMeta meta2)
/*     */   {
/* 113 */     if (meta1 == meta2) {
/* 114 */       return true;
/*     */     }
/* 116 */     if ((meta1 != null) && (!(meta1 instanceof CraftMetaItem))) {
/* 117 */       throw new IllegalArgumentException("First meta of " + meta1.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
/*     */     }
/* 119 */     if ((meta2 != null) && (!(meta2 instanceof CraftMetaItem))) {
/* 120 */       throw new IllegalArgumentException("Second meta " + meta2.getClass().getName() + " does not belong to " + CraftItemFactory.class.getName());
/*     */     }
/* 122 */     if (meta1 == null) {
/* 123 */       return ((CraftMetaItem)meta2).isEmpty();
/*     */     }
/* 125 */     if (meta2 == null) {
/* 126 */       return ((CraftMetaItem)meta1).isEmpty();
/*     */     }
/*     */     
/* 129 */     return equals((CraftMetaItem)meta1, (CraftMetaItem)meta2);
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
/*     */   boolean equals(CraftMetaItem meta1, CraftMetaItem meta2)
/*     */   {
/* 142 */     return (meta1.equalsCommon(meta2)) && (meta1.notUncommon(meta2)) && (meta2.notUncommon(meta1));
/*     */   }
/*     */   
/*     */   public static CraftItemFactory instance() {
/* 146 */     return instance;
/*     */   }
/*     */   
/*     */   public ItemMeta asMetaFor(ItemMeta meta, ItemStack stack) {
/* 150 */     Validate.notNull(stack, "Stack cannot be null");
/* 151 */     return asMetaFor(meta, stack.getType());
/*     */   }
/*     */   
/*     */   public ItemMeta asMetaFor(ItemMeta meta, Material material) {
/* 155 */     Validate.notNull(material, "Material cannot be null");
/* 156 */     if (!(meta instanceof CraftMetaItem)) {
/* 157 */       throw new IllegalArgumentException("Meta of " + (meta != null ? meta.getClass().toString() : "null") + " not created by " + CraftItemFactory.class.getName());
/*     */     }
/* 159 */     return getItemMeta(material, (CraftMetaItem)meta);
/*     */   }
/*     */   
/*     */   public Color getDefaultLeatherColor() {
/* 163 */     return DEFAULT_LEATHER_COLOR;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftItemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */