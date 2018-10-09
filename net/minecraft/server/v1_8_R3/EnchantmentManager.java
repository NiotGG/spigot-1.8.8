/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class EnchantmentManager
/*     */ {
/*  15 */   private static final Random a = new Random();
/*  16 */   private static final EnchantmentModifierProtection b = new EnchantmentModifierProtection(null);
/*  17 */   private static final EnchantmentModifierDamage c = new EnchantmentModifierDamage(null);
/*  18 */   private static final EnchantmentModifierThorns d = new EnchantmentModifierThorns(null);
/*  19 */   private static final EnchantmentModifierArthropods e = new EnchantmentModifierArthropods(null);
/*     */   
/*     */   public static int getEnchantmentLevel(int i, ItemStack itemstack) {
/*  22 */     if (itemstack == null) {
/*  23 */       return 0;
/*     */     }
/*  25 */     NBTTagList nbttaglist = itemstack.getEnchantments();
/*     */     
/*  27 */     if (nbttaglist == null) {
/*  28 */       return 0;
/*     */     }
/*  30 */     for (int j = 0; j < nbttaglist.size(); j++) {
/*  31 */       short short0 = nbttaglist.get(j).getShort("id");
/*  32 */       short short1 = nbttaglist.get(j).getShort("lvl");
/*     */       
/*  34 */       if (short0 == i) {
/*  35 */         return short1;
/*     */       }
/*     */     }
/*     */     
/*  39 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public static Map<Integer, Integer> a(ItemStack itemstack)
/*     */   {
/*  45 */     LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
/*  46 */     NBTTagList nbttaglist = itemstack.getItem() == Items.ENCHANTED_BOOK ? Items.ENCHANTED_BOOK.h(itemstack) : itemstack.getEnchantments();
/*     */     
/*  48 */     if (nbttaglist != null) {
/*  49 */       for (int i = 0; i < nbttaglist.size(); i++) {
/*  50 */         short short0 = nbttaglist.get(i).getShort("id");
/*  51 */         short short1 = nbttaglist.get(i).getShort("lvl");
/*     */         
/*  53 */         linkedhashmap.put(Integer.valueOf(short0), Integer.valueOf(short1));
/*     */       }
/*     */     }
/*     */     
/*  57 */     return linkedhashmap;
/*     */   }
/*     */   
/*     */   public static void a(Map<Integer, Integer> map, ItemStack itemstack) {
/*  61 */     NBTTagList nbttaglist = new NBTTagList();
/*  62 */     Iterator iterator = map.keySet().iterator();
/*     */     
/*  64 */     while (iterator.hasNext()) {
/*  65 */       int i = ((Integer)iterator.next()).intValue();
/*  66 */       Enchantment enchantment = Enchantment.getById(i);
/*     */       
/*  68 */       if (enchantment != null) {
/*  69 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/*  71 */         nbttagcompound.setShort("id", (short)i);
/*  72 */         nbttagcompound.setShort("lvl", (short)((Integer)map.get(Integer.valueOf(i))).intValue());
/*  73 */         nbttaglist.add(nbttagcompound);
/*  74 */         if (itemstack.getItem() == Items.ENCHANTED_BOOK) {
/*  75 */           Items.ENCHANTED_BOOK.a(itemstack, new WeightedRandomEnchant(enchantment, ((Integer)map.get(Integer.valueOf(i))).intValue()));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  80 */     if (nbttaglist.size() > 0) {
/*  81 */       if (itemstack.getItem() != Items.ENCHANTED_BOOK) {
/*  82 */         itemstack.a("ench", nbttaglist);
/*     */       }
/*  84 */     } else if (itemstack.hasTag()) {
/*  85 */       itemstack.getTag().remove("ench");
/*     */     }
/*     */   }
/*     */   
/*     */   public static int a(int i, ItemStack[] aitemstack)
/*     */   {
/*  91 */     if (aitemstack == null) {
/*  92 */       return 0;
/*     */     }
/*  94 */     int j = 0;
/*  95 */     ItemStack[] aitemstack1 = aitemstack;
/*  96 */     int k = aitemstack.length;
/*     */     
/*  98 */     for (int l = 0; l < k; l++) {
/*  99 */       ItemStack itemstack = aitemstack1[l];
/* 100 */       int i1 = getEnchantmentLevel(i, itemstack);
/*     */       
/* 102 */       if (i1 > j) {
/* 103 */         j = i1;
/*     */       }
/*     */     }
/*     */     
/* 107 */     return j;
/*     */   }
/*     */   
/*     */   private static void a(EnchantmentModifier enchantmentmanager_enchantmentmodifier, ItemStack itemstack)
/*     */   {
/* 112 */     if (itemstack != null) {
/* 113 */       NBTTagList nbttaglist = itemstack.getEnchantments();
/*     */       
/* 115 */       if (nbttaglist != null) {
/* 116 */         for (int i = 0; i < nbttaglist.size(); i++) {
/* 117 */           short short0 = nbttaglist.get(i).getShort("id");
/* 118 */           short short1 = nbttaglist.get(i).getShort("lvl");
/*     */           
/* 120 */           if (Enchantment.getById(short0) != null) {
/* 121 */             enchantmentmanager_enchantmentmodifier.a(Enchantment.getById(short0), short1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void a(EnchantmentModifier enchantmentmanager_enchantmentmodifier, ItemStack[] aitemstack)
/*     */   {
/* 130 */     ItemStack[] aitemstack1 = aitemstack;
/* 131 */     int i = aitemstack.length;
/*     */     
/* 133 */     for (int j = 0; j < i; j++) {
/* 134 */       ItemStack itemstack = aitemstack1[j];
/*     */       
/* 136 */       a(enchantmentmanager_enchantmentmodifier, itemstack);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int a(ItemStack[] aitemstack, DamageSource damagesource)
/*     */   {
/* 142 */     b.a = 0;
/* 143 */     b.b = damagesource;
/* 144 */     a(b, aitemstack);
/* 145 */     if (b.a > 25) {
/* 146 */       b.a = 25;
/* 147 */     } else if (b.a < 0) {
/* 148 */       b.a = 0;
/*     */     }
/*     */     
/* 151 */     return (b.a + 1 >> 1) + a.nextInt((b.a >> 1) + 1);
/*     */   }
/*     */   
/*     */   public static float a(ItemStack itemstack, EnumMonsterType enummonstertype) {
/* 155 */     c.a = 0.0F;
/* 156 */     c.b = enummonstertype;
/* 157 */     a(c, itemstack);
/* 158 */     return c.a;
/*     */   }
/*     */   
/*     */   public static void a(EntityLiving entityliving, Entity entity) {
/* 162 */     d.b = entity;
/* 163 */     d.a = entityliving;
/* 164 */     if (entityliving != null) {
/* 165 */       a(d, entityliving.getEquipment());
/*     */     }
/*     */     
/* 168 */     if ((entity instanceof EntityHuman)) {
/* 169 */       a(d, entityliving.bA());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void b(EntityLiving entityliving, Entity entity)
/*     */   {
/* 175 */     e.a = entityliving;
/* 176 */     e.b = entity;
/* 177 */     if (entityliving != null) {
/* 178 */       a(e, entityliving.getEquipment());
/*     */     }
/*     */     
/* 181 */     if ((entityliving instanceof EntityHuman)) {
/* 182 */       a(e, entityliving.bA());
/*     */     }
/*     */   }
/*     */   
/*     */   public static int a(EntityLiving entityliving)
/*     */   {
/* 188 */     return getEnchantmentLevel(Enchantment.KNOCKBACK.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static int getFireAspectEnchantmentLevel(EntityLiving entityliving) {
/* 192 */     return getEnchantmentLevel(Enchantment.FIRE_ASPECT.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static int getOxygenEnchantmentLevel(Entity entity) {
/* 196 */     return a(Enchantment.OXYGEN.id, entity.getEquipment());
/*     */   }
/*     */   
/*     */   public static int b(Entity entity) {
/* 200 */     return a(Enchantment.DEPTH_STRIDER.id, entity.getEquipment());
/*     */   }
/*     */   
/*     */   public static int getDigSpeedEnchantmentLevel(EntityLiving entityliving) {
/* 204 */     return getEnchantmentLevel(Enchantment.DIG_SPEED.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static boolean hasSilkTouchEnchantment(EntityLiving entityliving) {
/* 208 */     return getEnchantmentLevel(Enchantment.SILK_TOUCH.id, entityliving.bA()) > 0;
/*     */   }
/*     */   
/*     */   public static int getBonusBlockLootEnchantmentLevel(EntityLiving entityliving) {
/* 212 */     return getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static int g(EntityLiving entityliving) {
/* 216 */     return getEnchantmentLevel(Enchantment.LUCK.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static int h(EntityLiving entityliving) {
/* 220 */     return getEnchantmentLevel(Enchantment.LURE.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static int getBonusMonsterLootEnchantmentLevel(EntityLiving entityliving) {
/* 224 */     return getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS.id, entityliving.bA());
/*     */   }
/*     */   
/*     */   public static boolean j(EntityLiving entityliving) {
/* 228 */     return a(Enchantment.WATER_WORKER.id, entityliving.getEquipment()) > 0;
/*     */   }
/*     */   
/*     */   public static ItemStack a(Enchantment enchantment, EntityLiving entityliving) {
/* 232 */     ItemStack[] aitemstack = entityliving.getEquipment();
/* 233 */     int i = aitemstack.length;
/*     */     
/* 235 */     for (int j = 0; j < i; j++) {
/* 236 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 238 */       if ((itemstack != null) && (getEnchantmentLevel(enchantment.id, itemstack) > 0)) {
/* 239 */         return itemstack;
/*     */       }
/*     */     }
/*     */     
/* 243 */     return null;
/*     */   }
/*     */   
/*     */   public static int a(Random random, int i, int j, ItemStack itemstack) {
/* 247 */     Item item = itemstack.getItem();
/* 248 */     int k = item.b();
/*     */     
/* 250 */     if (k <= 0) {
/* 251 */       return 0;
/*     */     }
/* 253 */     if (j > 15) {
/* 254 */       j = 15;
/*     */     }
/*     */     
/* 257 */     int l = random.nextInt(8) + 1 + (j >> 1) + random.nextInt(j + 1);
/*     */     
/* 259 */     return i == 1 ? l * 2 / 3 + 1 : i == 0 ? Math.max(l / 3, 1) : Math.max(l, j * 2);
/*     */   }
/*     */   
/*     */   public static ItemStack a(Random random, ItemStack itemstack, int i)
/*     */   {
/* 264 */     List list = b(random, itemstack, i);
/* 265 */     boolean flag = itemstack.getItem() == Items.BOOK;
/*     */     
/* 267 */     if (flag) {
/* 268 */       itemstack.setItem(Items.ENCHANTED_BOOK);
/*     */     }
/*     */     
/* 271 */     if (list != null) {
/* 272 */       Iterator iterator = list.iterator();
/*     */       
/* 274 */       while (iterator.hasNext()) {
/* 275 */         WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)iterator.next();
/*     */         
/* 277 */         if (flag) {
/* 278 */           Items.ENCHANTED_BOOK.a(itemstack, weightedrandomenchant);
/*     */         } else {
/* 280 */           itemstack.addEnchantment(weightedrandomenchant.enchantment, weightedrandomenchant.level);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 285 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static List<WeightedRandomEnchant> b(Random random, ItemStack itemstack, int i) {
/* 289 */     Item item = itemstack.getItem();
/* 290 */     int j = item.b();
/*     */     
/* 292 */     if (j <= 0) {
/* 293 */       return null;
/*     */     }
/* 295 */     j /= 2;
/* 296 */     j = 1 + random.nextInt((j >> 1) + 1) + random.nextInt((j >> 1) + 1);
/* 297 */     int k = j + i;
/* 298 */     float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
/* 299 */     int l = (int)(k * (1.0F + f) + 0.5F);
/*     */     
/* 301 */     if (l < 1) {
/* 302 */       l = 1;
/*     */     }
/*     */     
/* 305 */     ArrayList arraylist = null;
/* 306 */     Map map = b(l, itemstack);
/*     */     
/* 308 */     if ((map != null) && (!map.isEmpty())) {
/* 309 */       WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)WeightedRandom.a(random, map.values());
/*     */       
/* 311 */       if (weightedrandomenchant != null) {
/* 312 */         arraylist = com.google.common.collect.Lists.newArrayList();
/* 313 */         arraylist.add(weightedrandomenchant);
/*     */         
/* 315 */         for (int i1 = l; random.nextInt(50) <= i1; i1 >>= 1) {
/* 316 */           Iterator iterator = map.keySet().iterator();
/*     */           
/* 318 */           while (iterator.hasNext()) {
/* 319 */             Integer integer = (Integer)iterator.next();
/* 320 */             boolean flag = true;
/* 321 */             Iterator iterator1 = arraylist.iterator();
/*     */             
/*     */ 
/* 324 */             while (iterator1.hasNext()) {
/* 325 */               WeightedRandomEnchant weightedrandomenchant1 = (WeightedRandomEnchant)iterator1.next();
/*     */               
/* 327 */               if (!weightedrandomenchant1.enchantment.a(Enchantment.getById(integer.intValue())))
/*     */               {
/*     */ 
/*     */ 
/* 331 */                 flag = false;
/*     */               }
/*     */             }
/* 334 */             if (!flag) {
/* 335 */               iterator.remove();
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 341 */           if (!map.isEmpty()) {
/* 342 */             WeightedRandomEnchant weightedrandomenchant2 = (WeightedRandomEnchant)WeightedRandom.a(random, map.values());
/*     */             
/* 344 */             arraylist.add(weightedrandomenchant2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 350 */     return arraylist;
/*     */   }
/*     */   
/*     */   public static Map<Integer, WeightedRandomEnchant> b(int i, ItemStack itemstack)
/*     */   {
/* 355 */     Item item = itemstack.getItem();
/* 356 */     HashMap hashmap = null;
/* 357 */     boolean flag = itemstack.getItem() == Items.BOOK;
/* 358 */     Enchantment[] aenchantment = Enchantment.b;
/* 359 */     int j = aenchantment.length;
/*     */     
/* 361 */     for (int k = 0; k < j; k++) {
/* 362 */       Enchantment enchantment = aenchantment[k];
/*     */       
/* 364 */       if ((enchantment != null) && ((enchantment.slot.canEnchant(item)) || (flag))) {
/* 365 */         for (int l = enchantment.getStartLevel(); l <= enchantment.getMaxLevel(); l++) {
/* 366 */           if ((i >= enchantment.a(l)) && (i <= enchantment.b(l))) {
/* 367 */             if (hashmap == null) {
/* 368 */               hashmap = Maps.newHashMap();
/*     */             }
/*     */             
/* 371 */             hashmap.put(Integer.valueOf(enchantment.id), new WeightedRandomEnchant(enchantment, l));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 377 */     return hashmap;
/*     */   }
/*     */   
/*     */   static final class EnchantmentModifierArthropods
/*     */     implements EnchantmentManager.EnchantmentModifier
/*     */   {
/*     */     public EntityLiving a;
/*     */     public Entity b;
/*     */     
/*     */     private EnchantmentModifierArthropods() {}
/*     */     
/*     */     public void a(Enchantment enchantment, int i)
/*     */     {
/* 390 */       enchantment.a(this.a, this.b, i);
/*     */     }
/*     */     
/*     */     EnchantmentModifierArthropods(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
/* 394 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EnchantmentModifierThorns implements EnchantmentManager.EnchantmentModifier
/*     */   {
/*     */     public EntityLiving a;
/*     */     public Entity b;
/*     */     
/*     */     private EnchantmentModifierThorns() {}
/*     */     
/*     */     public void a(Enchantment enchantment, int i) {
/* 406 */       enchantment.b(this.a, this.b, i);
/*     */     }
/*     */     
/*     */     EnchantmentModifierThorns(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
/* 410 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EnchantmentModifierDamage implements EnchantmentManager.EnchantmentModifier
/*     */   {
/*     */     public float a;
/*     */     public EnumMonsterType b;
/*     */     
/*     */     private EnchantmentModifierDamage() {}
/*     */     
/*     */     public void a(Enchantment enchantment, int i) {
/* 422 */       this.a += enchantment.a(i, this.b);
/*     */     }
/*     */     
/*     */     EnchantmentModifierDamage(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
/* 426 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class EnchantmentModifierProtection implements EnchantmentManager.EnchantmentModifier
/*     */   {
/*     */     public int a;
/*     */     public DamageSource b;
/*     */     
/*     */     private EnchantmentModifierProtection() {}
/*     */     
/*     */     public void a(Enchantment enchantment, int i) {
/* 438 */       this.a += enchantment.a(i, this.b);
/*     */     }
/*     */     
/*     */     EnchantmentModifierProtection(EnchantmentManager.SyntheticClass_1 enchantmentmanager_syntheticclass_1) {
/* 442 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract interface EnchantmentModifier
/*     */   {
/*     */     public abstract void a(Enchantment paramEnchantment, int paramInt);
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1 {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnchantmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */