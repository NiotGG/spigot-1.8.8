/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.EnchantmentManager;
/*     */ import net.minecraft.server.v1_8_R3.Item;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(org.bukkit.inventory.ItemStack.class)
/*     */ public final class CraftItemStack
/*     */   extends org.bukkit.inventory.ItemStack
/*     */ {
/*     */   net.minecraft.server.v1_8_R3.ItemStack handle;
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.ItemStack asNMSCopy(org.bukkit.inventory.ItemStack original)
/*     */   {
/*  32 */     if ((original instanceof CraftItemStack)) {
/*  33 */       CraftItemStack stack = (CraftItemStack)original;
/*  34 */       return stack.handle == null ? null : stack.handle.cloneItemStack();
/*     */     }
/*  36 */     if ((original == null) || (original.getTypeId() <= 0)) {
/*  37 */       return null;
/*     */     }
/*     */     
/*  40 */     Item item = CraftMagicNumbers.getItem(original.getType());
/*     */     
/*  42 */     if (item == null) {
/*  43 */       return null;
/*     */     }
/*     */     
/*  46 */     net.minecraft.server.v1_8_R3.ItemStack stack = new net.minecraft.server.v1_8_R3.ItemStack(item, original.getAmount(), original.getDurability());
/*  47 */     if (original.hasItemMeta()) {
/*  48 */       setItemMeta(stack, original.getItemMeta());
/*     */     }
/*  50 */     return stack;
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.ItemStack copyNMSStack(net.minecraft.server.v1_8_R3.ItemStack original, int amount) {
/*  54 */     net.minecraft.server.v1_8_R3.ItemStack stack = original.cloneItemStack();
/*  55 */     stack.count = amount;
/*  56 */     return stack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static org.bukkit.inventory.ItemStack asBukkitCopy(net.minecraft.server.v1_8_R3.ItemStack original)
/*     */   {
/*  63 */     if (original == null) {
/*  64 */       return new org.bukkit.inventory.ItemStack(Material.AIR);
/*     */     }
/*  66 */     org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(CraftMagicNumbers.getMaterial(original.getItem()), original.count, (short)original.getData());
/*  67 */     if (hasItemMeta(original)) {
/*  68 */       stack.setItemMeta(getItemMeta(original));
/*     */     }
/*  70 */     return stack;
/*     */   }
/*     */   
/*     */   public static CraftItemStack asCraftMirror(net.minecraft.server.v1_8_R3.ItemStack original) {
/*  74 */     return new CraftItemStack(original);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asCraftCopy(org.bukkit.inventory.ItemStack original) {
/*  78 */     if ((original instanceof CraftItemStack)) {
/*  79 */       CraftItemStack stack = (CraftItemStack)original;
/*  80 */       return new CraftItemStack(stack.handle == null ? null : stack.handle.cloneItemStack());
/*     */     }
/*  82 */     return new CraftItemStack(original);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asNewCraftStack(Item item) {
/*  86 */     return asNewCraftStack(item, 1);
/*     */   }
/*     */   
/*     */   public static CraftItemStack asNewCraftStack(Item item, int amount) {
/*  90 */     return new CraftItemStack(CraftMagicNumbers.getMaterial(item), amount, (short)0, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private CraftItemStack(net.minecraft.server.v1_8_R3.ItemStack item)
/*     */   {
/*  99 */     this.handle = item;
/*     */   }
/*     */   
/*     */   private CraftItemStack(org.bukkit.inventory.ItemStack item) {
/* 103 */     this(item.getTypeId(), item.getAmount(), item.getDurability(), item.hasItemMeta() ? item.getItemMeta() : null);
/*     */   }
/*     */   
/*     */   private CraftItemStack(Material type, int amount, short durability, ItemMeta itemMeta) {
/* 107 */     setType(type);
/* 108 */     setAmount(amount);
/* 109 */     setDurability(durability);
/* 110 */     setItemMeta(itemMeta);
/*     */   }
/*     */   
/*     */   private CraftItemStack(int typeId, int amount, short durability, ItemMeta itemMeta) {
/* 114 */     this(Material.getMaterial(typeId), amount, durability, itemMeta);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getTypeId()
/*     */   {
/* 120 */     return this.handle != null ? CraftMagicNumbers.getId(this.handle.getItem()) : 0;
/*     */   }
/*     */   
/*     */   public void setTypeId(int type)
/*     */   {
/* 125 */     if (getTypeId() == type)
/* 126 */       return;
/* 127 */     if (type == 0) {
/* 128 */       this.handle = null;
/* 129 */     } else if (CraftMagicNumbers.getItem(type) == null) {
/* 130 */       this.handle = null;
/* 131 */     } else if (this.handle == null) {
/* 132 */       this.handle = new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(type), 1, 0);
/*     */     } else {
/* 134 */       this.handle.setItem(CraftMagicNumbers.getItem(type));
/* 135 */       if (hasItemMeta())
/*     */       {
/* 137 */         setItemMeta(this.handle, getItemMeta(this.handle));
/*     */       }
/*     */     }
/* 140 */     setData(null);
/*     */   }
/*     */   
/*     */   public int getAmount()
/*     */   {
/* 145 */     return this.handle != null ? this.handle.count : 0;
/*     */   }
/*     */   
/*     */   public void setAmount(int amount)
/*     */   {
/* 150 */     if (this.handle == null) {
/* 151 */       return;
/*     */     }
/* 153 */     if (amount == 0) {
/* 154 */       this.handle = null;
/*     */     } else {
/* 156 */       this.handle.count = amount;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDurability(short durability)
/*     */   {
/* 163 */     if (this.handle != null) {
/* 164 */       this.handle.setData(durability);
/*     */     }
/*     */   }
/*     */   
/*     */   public short getDurability()
/*     */   {
/* 170 */     if (this.handle != null) {
/* 171 */       return (short)this.handle.getData();
/*     */     }
/* 173 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public int getMaxStackSize()
/*     */   {
/* 179 */     return this.handle == null ? Material.AIR.getMaxStackSize() : this.handle.getItem().getMaxStackSize();
/*     */   }
/*     */   
/*     */   public void addUnsafeEnchantment(Enchantment ench, int level)
/*     */   {
/* 184 */     Validate.notNull(ench, "Cannot add null enchantment");
/*     */     
/* 186 */     if (!makeTag(this.handle)) {
/* 187 */       return;
/*     */     }
/* 189 */     NBTTagList list = getEnchantmentList(this.handle);
/* 190 */     if (list == null) {
/* 191 */       list = new NBTTagList();
/* 192 */       this.handle.getTag().set(CraftMetaItem.ENCHANTMENTS.NBT, list);
/*     */     }
/* 194 */     int size = list.size();
/*     */     
/* 196 */     for (int i = 0; i < size; i++) {
/* 197 */       NBTTagCompound tag = list.get(i);
/* 198 */       short id = tag.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 199 */       if (id == ench.getId()) {
/* 200 */         tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
/* 201 */         return;
/*     */       }
/*     */     }
/* 204 */     NBTTagCompound tag = new NBTTagCompound();
/* 205 */     tag.setShort(CraftMetaItem.ENCHANTMENTS_ID.NBT, (short)ench.getId());
/* 206 */     tag.setShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT, (short)level);
/* 207 */     list.add(tag);
/*     */   }
/*     */   
/*     */   static boolean makeTag(net.minecraft.server.v1_8_R3.ItemStack item) {
/* 211 */     if (item == null) {
/* 212 */       return false;
/*     */     }
/*     */     
/* 215 */     if (item.getTag() == null) {
/* 216 */       item.setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 219 */     return true;
/*     */   }
/*     */   
/*     */   public boolean containsEnchantment(Enchantment ench)
/*     */   {
/* 224 */     return getEnchantmentLevel(ench) > 0;
/*     */   }
/*     */   
/*     */   public int getEnchantmentLevel(Enchantment ench)
/*     */   {
/* 229 */     Validate.notNull(ench, "Cannot find null enchantment");
/* 230 */     if (this.handle == null) {
/* 231 */       return 0;
/*     */     }
/* 233 */     return EnchantmentManager.getEnchantmentLevel(ench.getId(), this.handle);
/*     */   }
/*     */   
/*     */   public int removeEnchantment(Enchantment ench)
/*     */   {
/* 238 */     Validate.notNull(ench, "Cannot remove null enchantment");
/*     */     
/* 240 */     NBTTagList list = getEnchantmentList(this.handle);
/* 241 */     if (list == null) {
/* 242 */       return 0;
/*     */     }
/* 244 */     int index = Integer.MIN_VALUE;
/* 245 */     int level = Integer.MIN_VALUE;
/* 246 */     int size = list.size();
/*     */     
/* 248 */     for (int i = 0; i < size; i++) {
/* 249 */       NBTTagCompound enchantment = list.get(i);
/* 250 */       int id = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 251 */       if (id == ench.getId()) {
/* 252 */         index = i;
/* 253 */         level = 0xFFFF & enchantment.getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
/* 254 */         break;
/*     */       }
/*     */     }
/*     */     
/* 258 */     if (index == Integer.MIN_VALUE) {
/* 259 */       return 0;
/*     */     }
/* 261 */     if (size == 1) {
/* 262 */       this.handle.getTag().remove(CraftMetaItem.ENCHANTMENTS.NBT);
/* 263 */       if (this.handle.getTag().isEmpty()) {
/* 264 */         this.handle.setTag(null);
/*     */       }
/* 266 */       return level;
/*     */     }
/*     */     
/*     */ 
/* 270 */     NBTTagList listCopy = new NBTTagList();
/* 271 */     for (int i = 0; i < size; i++) {
/* 272 */       if (i != index) {
/* 273 */         listCopy.add(list.get(i));
/*     */       }
/*     */     }
/* 276 */     this.handle.getTag().set(CraftMetaItem.ENCHANTMENTS.NBT, listCopy);
/*     */     
/* 278 */     return level;
/*     */   }
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantments()
/*     */   {
/* 283 */     return getEnchantments(this.handle);
/*     */   }
/*     */   
/*     */   static Map<Enchantment, Integer> getEnchantments(net.minecraft.server.v1_8_R3.ItemStack item) {
/* 287 */     NBTTagList list = (item != null) && (item.hasEnchantments()) ? item.getEnchantments() : null;
/*     */     
/* 289 */     if ((list == null) || (list.size() == 0)) {
/* 290 */       return ImmutableMap.of();
/*     */     }
/*     */     
/* 293 */     ImmutableMap.Builder<Enchantment, Integer> result = ImmutableMap.builder();
/*     */     
/* 295 */     for (int i = 0; i < list.size(); i++) {
/* 296 */       int id = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_ID.NBT);
/* 297 */       int level = 0xFFFF & list.get(i).getShort(CraftMetaItem.ENCHANTMENTS_LVL.NBT);
/*     */       
/* 299 */       result.put(Enchantment.getById(id), Integer.valueOf(level));
/*     */     }
/*     */     
/* 302 */     return result.build();
/*     */   }
/*     */   
/*     */   static NBTTagList getEnchantmentList(net.minecraft.server.v1_8_R3.ItemStack item) {
/* 306 */     return (item != null) && (item.hasEnchantments()) ? item.getEnchantments() : null;
/*     */   }
/*     */   
/*     */   public CraftItemStack clone()
/*     */   {
/* 311 */     CraftItemStack itemStack = (CraftItemStack)super.clone();
/* 312 */     if (this.handle != null) {
/* 313 */       itemStack.handle = this.handle.cloneItemStack();
/*     */     }
/* 315 */     return itemStack;
/*     */   }
/*     */   
/*     */   public ItemMeta getItemMeta()
/*     */   {
/* 320 */     return getItemMeta(this.handle);
/*     */   }
/*     */   
/*     */   public static ItemMeta getItemMeta(net.minecraft.server.v1_8_R3.ItemStack item) {
/* 324 */     if (!hasItemMeta(item)) {
/* 325 */       return CraftItemFactory.instance().getItemMeta(getType(item));
/*     */     }
/* 327 */     switch (getType(item)) {
/*     */     case STAINED_CLAY: 
/* 329 */       return new CraftMetaBookSigned(item.getTag());
/*     */     case SPRUCE_WOOD_STAIRS: 
/* 331 */       return new CraftMetaBook(item.getTag());
/*     */     case STONE_BUTTON: 
/* 333 */       return new CraftMetaSkull(item.getTag());
/*     */     case PACKED_ICE: 
/*     */     case PAINTING: 
/*     */     case PAPER: 
/*     */     case PISTON_BASE: 
/* 338 */       return new CraftMetaLeatherArmor(item.getTag());
/*     */     case SMOOTH_STAIRS: 
/* 340 */       return new CraftMetaPotion(item.getTag());
/*     */     case SADDLE: 
/* 342 */       return new CraftMetaMap(item.getTag());
/*     */     case STONE_SLAB2: 
/* 344 */       return new CraftMetaFirework(item.getTag());
/*     */     case STONE_SPADE: 
/* 346 */       return new CraftMetaCharge(item.getTag());
/*     */     case STONE_SWORD: 
/* 348 */       return new CraftMetaEnchantedBook(item.getTag());
/*     */     case WHEAT: 
/* 350 */       return new CraftMetaBanner(item.getTag());
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
/* 370 */       return new CraftMetaBlockState(item.getTag(), CraftMagicNumbers.getMaterial(item.getItem()));
/*     */     }
/* 372 */     return new CraftMetaItem(item.getTag());
/*     */   }
/*     */   
/*     */   static Material getType(net.minecraft.server.v1_8_R3.ItemStack item)
/*     */   {
/* 377 */     Material material = Material.getMaterial(item == null ? 0 : CraftMagicNumbers.getId(item.getItem()));
/* 378 */     return material == null ? Material.AIR : material;
/*     */   }
/*     */   
/*     */   public boolean setItemMeta(ItemMeta itemMeta)
/*     */   {
/* 383 */     return setItemMeta(this.handle, itemMeta);
/*     */   }
/*     */   
/*     */   public static boolean setItemMeta(net.minecraft.server.v1_8_R3.ItemStack item, ItemMeta itemMeta) {
/* 387 */     if (item == null) {
/* 388 */       return false;
/*     */     }
/* 390 */     if (CraftItemFactory.instance().equals(itemMeta, null)) {
/* 391 */       item.setTag(null);
/* 392 */       return true;
/*     */     }
/* 394 */     if (!CraftItemFactory.instance().isApplicable(itemMeta, getType(item))) {
/* 395 */       return false;
/*     */     }
/*     */     
/* 398 */     itemMeta = CraftItemFactory.instance().asMetaFor(itemMeta, getType(item));
/* 399 */     if (itemMeta == null) { return true;
/*     */     }
/* 401 */     NBTTagCompound tag = new NBTTagCompound();
/* 402 */     item.setTag(tag);
/*     */     
/* 404 */     ((CraftMetaItem)itemMeta).applyToItem(tag);
/*     */     
/* 406 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSimilar(org.bukkit.inventory.ItemStack stack)
/*     */   {
/* 411 */     if (stack == null) {
/* 412 */       return false;
/*     */     }
/* 414 */     if (stack == this) {
/* 415 */       return true;
/*     */     }
/* 417 */     if (!(stack instanceof CraftItemStack)) {
/* 418 */       return (stack.getClass() == org.bukkit.inventory.ItemStack.class) && (stack.isSimilar(this));
/*     */     }
/*     */     
/* 421 */     CraftItemStack that = (CraftItemStack)stack;
/* 422 */     if (this.handle == that.handle) {
/* 423 */       return true;
/*     */     }
/* 425 */     if ((this.handle == null) || (that.handle == null)) {
/* 426 */       return false;
/*     */     }
/* 428 */     if ((that.getTypeId() != getTypeId()) || (getDurability() != that.getDurability())) {
/* 429 */       return false;
/*     */     }
/* 431 */     return (that.hasItemMeta()) && (this.handle.getTag().equals(that.handle.getTag()));
/*     */   }
/*     */   
/*     */   public boolean hasItemMeta()
/*     */   {
/* 436 */     return hasItemMeta(this.handle);
/*     */   }
/*     */   
/*     */   static boolean hasItemMeta(net.minecraft.server.v1_8_R3.ItemStack item) {
/* 440 */     return (item != null) && (item.getTag() != null) && (!item.getTag().isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */