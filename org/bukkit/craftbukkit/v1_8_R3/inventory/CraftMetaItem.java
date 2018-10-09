/*      */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*      */ 
/*      */ import com.google.common.base.Strings;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableMap.Builder;
/*      */ import com.google.common.collect.Sets;
/*      */ import gnu.trove.map.hash.TObjectDoubleHashMap;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.annotation.Retention;
/*      */ import java.lang.annotation.RetentionPolicy;
/*      */ import java.lang.annotation.Target;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*      */ import net.minecraft.server.v1_8_R3.IAttribute;
/*      */ import net.minecraft.server.v1_8_R3.NBTBase;
/*      */ import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
/*      */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*      */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*      */ import net.minecraft.server.v1_8_R3.NBTTagString;
/*      */ import org.apache.commons.codec.binary.Base64;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*      */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*      */ import org.bukkit.configuration.serialization.SerializableAs;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.Overridden;
/*      */ import org.bukkit.enchantments.Enchantment;
/*      */ import org.bukkit.inventory.ItemFlag;
/*      */ import org.bukkit.inventory.meta.ItemMeta;
/*      */ import org.bukkit.inventory.meta.ItemMeta.Spigot;
/*      */ import org.bukkit.inventory.meta.Repairable;
/*      */ import org.spigotmc.ValidateUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @DelegateDeserialization(SerializableMeta.class)
/*      */ class CraftMetaItem
/*      */   implements ItemMeta, Repairable
/*      */ {
/*      */   static class ItemMetaKey
/*      */   {
/*      */     final String BUKKIT;
/*      */     final String NBT;
/*      */     
/*      */     @Retention(RetentionPolicy.SOURCE)
/*      */     @Target({java.lang.annotation.ElementType.FIELD})
/*      */     static @interface Specific
/*      */     {
/*      */       To value();
/*      */       
/*      */       public static enum To
/*      */       {
/*   87 */         BUKKIT, 
/*   88 */         NBT;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ItemMetaKey(String both)
/*      */     {
/*   98 */       this(both, both);
/*      */     }
/*      */     
/*      */     ItemMetaKey(String nbt, String bukkit) {
/*  102 */       this.NBT = nbt;
/*  103 */       this.BUKKIT = bukkit;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @SerializableAs("ItemMeta")
/*      */   public static class SerializableMeta
/*      */     implements ConfigurationSerializable
/*      */   {
/*      */     static final String TYPE_FIELD = "meta-type";
/*      */     
/*  115 */     static final ImmutableMap<Class<? extends CraftMetaItem>, String> classMap = ImmutableMap.builder()
/*  116 */       .put(CraftMetaBanner.class, "BANNER")
/*  117 */       .put(CraftMetaBlockState.class, "TILE_ENTITY")
/*  118 */       .put(CraftMetaBook.class, "BOOK")
/*  119 */       .put(CraftMetaBookSigned.class, "BOOK_SIGNED")
/*  120 */       .put(CraftMetaSkull.class, "SKULL")
/*  121 */       .put(CraftMetaLeatherArmor.class, "LEATHER_ARMOR")
/*  122 */       .put(CraftMetaMap.class, "MAP")
/*  123 */       .put(CraftMetaPotion.class, "POTION")
/*  124 */       .put(CraftMetaEnchantedBook.class, "ENCHANTED")
/*  125 */       .put(CraftMetaFirework.class, "FIREWORK")
/*  126 */       .put(CraftMetaCharge.class, "FIREWORK_EFFECT")
/*  127 */       .put(CraftMetaItem.class, "UNSPECIFIC")
/*  128 */       .build();
/*      */     
/*  130 */     static { ImmutableMap.Builder<String, Constructor<? extends CraftMetaItem>> classConstructorBuilder = ImmutableMap.builder();
/*  131 */       for (Map.Entry<Class<? extends CraftMetaItem>, String> mapping : classMap.entrySet()) {
/*      */         try {
/*  133 */           classConstructorBuilder.put((String)mapping.getValue(), ((Class)mapping.getKey()).getDeclaredConstructor(new Class[] { Map.class }));
/*      */         } catch (NoSuchMethodException e) {
/*  135 */           throw new AssertionError(e);
/*      */         }
/*      */       }
/*  138 */       constructorMap = classConstructorBuilder.build();
/*      */     }
/*      */     
/*      */     static final ImmutableMap<String, Constructor<? extends CraftMetaItem>> constructorMap;
/*      */     public static ItemMeta deserialize(Map<String, Object> map)
/*      */       throws Throwable
/*      */     {
/*  145 */       Validate.notNull(map, "Cannot deserialize null map");
/*      */       
/*  147 */       String type = getString(map, "meta-type", false);
/*  148 */       Constructor<? extends CraftMetaItem> constructor = (Constructor)constructorMap.get(type);
/*      */       
/*  150 */       if (constructor == null) {
/*  151 */         throw new IllegalArgumentException(type + " is not a valid " + "meta-type");
/*      */       }
/*      */       try
/*      */       {
/*  155 */         return (ItemMeta)constructor.newInstance(new Object[] { map });
/*      */       } catch (InstantiationException e) {
/*  157 */         throw new AssertionError(e);
/*      */       } catch (IllegalAccessException e) {
/*  159 */         throw new AssertionError(e);
/*      */       } catch (InvocationTargetException e) {
/*  161 */         throw e.getCause();
/*      */       }
/*      */     }
/*      */     
/*      */     public Map<String, Object> serialize() {
/*  166 */       throw new AssertionError();
/*      */     }
/*      */     
/*      */     static String getString(Map<?, ?> map, Object field, boolean nullable) {
/*  170 */       return (String)getObject(String.class, map, field, nullable);
/*      */     }
/*      */     
/*      */     static boolean getBoolean(Map<?, ?> map, Object field) {
/*  174 */       Boolean value = (Boolean)getObject(Boolean.class, map, field, true);
/*  175 */       return (value != null) && (value.booleanValue());
/*      */     }
/*      */     
/*      */     static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable) {
/*  179 */       Object object = map.get(field);
/*      */       
/*  181 */       if (clazz.isInstance(object)) {
/*  182 */         return (T)clazz.cast(object);
/*      */       }
/*  184 */       if (object == null) {
/*  185 */         if (!nullable) {
/*  186 */           throw new NoSuchElementException(map + " does not contain " + field);
/*      */         }
/*  188 */         return null;
/*      */       }
/*  190 */       throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
/*      */     }
/*      */   }
/*      */   
/*  194 */   static final ItemMetaKey NAME = new ItemMetaKey("Name", "display-name");
/*      */   
/*  196 */   static final ItemMetaKey DISPLAY = new ItemMetaKey("display");
/*  197 */   static final ItemMetaKey LORE = new ItemMetaKey("Lore", "lore");
/*  198 */   static final ItemMetaKey ENCHANTMENTS = new ItemMetaKey("ench", "enchants");
/*      */   
/*  200 */   static final ItemMetaKey ENCHANTMENTS_ID = new ItemMetaKey("id");
/*      */   
/*  202 */   static final ItemMetaKey ENCHANTMENTS_LVL = new ItemMetaKey("lvl");
/*  203 */   static final ItemMetaKey REPAIR = new ItemMetaKey("RepairCost", "repair-cost");
/*      */   
/*  205 */   static final ItemMetaKey ATTRIBUTES = new ItemMetaKey("AttributeModifiers");
/*      */   
/*  207 */   static final ItemMetaKey ATTRIBUTES_IDENTIFIER = new ItemMetaKey("AttributeName");
/*      */   
/*  209 */   static final ItemMetaKey ATTRIBUTES_NAME = new ItemMetaKey("Name");
/*      */   
/*  211 */   static final ItemMetaKey ATTRIBUTES_VALUE = new ItemMetaKey("Amount");
/*      */   
/*  213 */   static final ItemMetaKey ATTRIBUTES_TYPE = new ItemMetaKey("Operation");
/*      */   
/*  215 */   static final ItemMetaKey ATTRIBUTES_UUID_HIGH = new ItemMetaKey("UUIDMost");
/*      */   
/*  217 */   static final ItemMetaKey ATTRIBUTES_UUID_LOW = new ItemMetaKey("UUIDLeast");
/*      */   
/*  219 */   static final ItemMetaKey HIDEFLAGS = new ItemMetaKey("HideFlags", "ItemFlags");
/*      */   
/*  221 */   static final ItemMetaKey UNBREAKABLE = new ItemMetaKey("Unbreakable");
/*      */   
/*      */   private String displayName;
/*      */   
/*      */   private List<String> lore;
/*      */   private Map<Enchantment, Integer> enchantments;
/*      */   private int repairCost;
/*      */   private int hideFlag;
/*  229 */   private static final Set<String> HANDLED_TAGS = Sets.newHashSet();
/*      */   
/*  231 */   private final Map<String, NBTBase> unhandledTags = new HashMap();
/*      */   
/*      */   CraftMetaItem(CraftMetaItem meta) {
/*  234 */     if (meta == null) {
/*  235 */       return;
/*      */     }
/*      */     
/*  238 */     this.displayName = meta.displayName;
/*      */     
/*  240 */     if (meta.hasLore()) {
/*  241 */       this.lore = new ArrayList(meta.lore);
/*      */     }
/*      */     
/*  244 */     if (meta.enchantments != null) {
/*  245 */       this.enchantments = new HashMap(meta.enchantments);
/*      */     }
/*      */     
/*  248 */     this.repairCost = meta.repairCost;
/*  249 */     this.hideFlag = meta.hideFlag;
/*  250 */     this.unhandledTags.putAll(meta.unhandledTags);
/*  251 */     this.spigot.setUnbreakable(meta.spigot.isUnbreakable());
/*      */   }
/*      */   
/*      */   CraftMetaItem(NBTTagCompound tag) {
/*  255 */     if (tag.hasKey(DISPLAY.NBT)) {
/*  256 */       NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
/*      */       
/*  258 */       if (display.hasKey(NAME.NBT)) {
/*  259 */         this.displayName = ValidateUtils.limit(display.getString(NAME.NBT), 1024);
/*      */       }
/*      */       
/*  262 */       if (display.hasKey(LORE.NBT)) {
/*  263 */         NBTTagList list = display.getList(LORE.NBT, 8);
/*  264 */         this.lore = new ArrayList(list.size());
/*      */         
/*  266 */         for (int index = 0; index < list.size(); index++) {
/*  267 */           String line = ValidateUtils.limit(list.getString(index), 1024);
/*  268 */           this.lore.add(line);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  273 */     this.enchantments = buildEnchantments(tag, ENCHANTMENTS);
/*      */     
/*  275 */     if (tag.hasKey(REPAIR.NBT)) {
/*  276 */       this.repairCost = tag.getInt(REPAIR.NBT);
/*      */     }
/*      */     
/*  279 */     if (tag.hasKey(HIDEFLAGS.NBT)) {
/*  280 */       this.hideFlag = tag.getInt(HIDEFLAGS.NBT);
/*      */     }
/*      */     TObjectDoubleHashMap<String> attributeTracker;
/*  283 */     if ((tag.get(ATTRIBUTES.NBT) instanceof NBTTagList)) {
/*  284 */       NBTTagList save = null;
/*  285 */       NBTTagList nbttaglist = tag.getList(ATTRIBUTES.NBT, 10);
/*      */       
/*      */ 
/*  288 */       attributeTracker = new TObjectDoubleHashMap();
/*  289 */       TObjectDoubleHashMap<String> attributeTrackerX = new TObjectDoubleHashMap();
/*  290 */       Map<String, IAttribute> attributesByName = new HashMap();
/*  291 */       attributeTracker.put("generic.maxHealth", 20.0D);
/*  292 */       attributesByName.put("generic.maxHealth", GenericAttributes.maxHealth);
/*  293 */       attributeTracker.put("generic.followRange", 32.0D);
/*  294 */       attributesByName.put("generic.followRange", GenericAttributes.FOLLOW_RANGE);
/*  295 */       attributeTracker.put("generic.knockbackResistance", 0.0D);
/*  296 */       attributesByName.put("generic.knockbackResistance", GenericAttributes.c);
/*  297 */       attributeTracker.put("generic.movementSpeed", 0.7D);
/*  298 */       attributesByName.put("generic.movementSpeed", GenericAttributes.MOVEMENT_SPEED);
/*  299 */       attributeTracker.put("generic.attackDamage", 1.0D);
/*  300 */       attributesByName.put("generic.attackDamage", GenericAttributes.ATTACK_DAMAGE);
/*  301 */       NBTTagList oldList = nbttaglist;
/*  302 */       nbttaglist = new NBTTagList();
/*      */       
/*  304 */       List<NBTTagCompound> op0 = new ArrayList();
/*  305 */       List<NBTTagCompound> op1 = new ArrayList();
/*  306 */       List<NBTTagCompound> op2 = new ArrayList();
/*      */       NBTTagCompound nbttagcompound;
/*  308 */       for (int i = 0; i < oldList.size(); i++)
/*      */       {
/*  310 */         nbttagcompound = oldList.get(i);
/*  311 */         if (nbttagcompound != null)
/*      */         {
/*  313 */           if (nbttagcompound.hasKeyOfType(ATTRIBUTES_UUID_HIGH.NBT, 99))
/*      */           {
/*      */ 
/*      */ 
/*  317 */             if (nbttagcompound.hasKeyOfType(ATTRIBUTES_UUID_LOW.NBT, 99))
/*      */             {
/*      */ 
/*      */ 
/*  321 */               if (((nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString)) && (CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT))))
/*      */               {
/*      */ 
/*      */ 
/*  325 */                 if (((nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString)) && (!nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty()))
/*      */                 {
/*      */ 
/*      */ 
/*  329 */                   if (nbttagcompound.hasKeyOfType(ATTRIBUTES_VALUE.NBT, 99))
/*      */                   {
/*      */ 
/*      */ 
/*  333 */                     if ((nbttagcompound.hasKeyOfType(ATTRIBUTES_TYPE.NBT, 99)) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*  338 */                       switch (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT))
/*      */                       {
/*      */                       case 0: 
/*  341 */                         op0.add(nbttagcompound);
/*  342 */                         break;
/*      */                       case 1: 
/*  344 */                         op1.add(nbttagcompound);
/*  345 */                         break;
/*      */                       case 2: 
/*  347 */                         op2.add(nbttagcompound);
/*      */                       } } } } } } }
/*      */         }
/*      */       }
/*  351 */       for (NBTTagCompound nbtTagCompound : op0)
/*      */       {
/*  353 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/*  354 */         if (attributeTracker.containsKey(name))
/*      */         {
/*  356 */           double val = attributeTracker.get(name);
/*  357 */           val += nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/*  358 */           if (val == ((IAttribute)attributesByName.get(name)).a(val))
/*      */           {
/*      */ 
/*      */ 
/*  362 */             attributeTracker.put(name, val); }
/*      */         } else {
/*  364 */           nbttaglist.add(nbtTagCompound);
/*      */         } }
/*  366 */       for (String name : attributeTracker.keySet())
/*      */       {
/*  368 */         attributeTrackerX.put(name, attributeTracker.get(name));
/*      */       }
/*  370 */       for (NBTTagCompound nbtTagCompound : op1)
/*      */       {
/*  372 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/*  373 */         if (attributeTracker.containsKey(name))
/*      */         {
/*  375 */           double val = attributeTracker.get(name);
/*  376 */           double valX = attributeTrackerX.get(name);
/*  377 */           val += valX * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/*  378 */           if (val == ((IAttribute)attributesByName.get(name)).a(val))
/*      */           {
/*      */ 
/*      */ 
/*  382 */             attributeTracker.put(name, val); }
/*      */         } else {
/*  384 */           nbttaglist.add(nbtTagCompound);
/*      */         } }
/*  386 */       for (NBTTagCompound nbtTagCompound : op2)
/*      */       {
/*  388 */         String name = nbtTagCompound.getString(ATTRIBUTES_IDENTIFIER.NBT);
/*  389 */         if (attributeTracker.containsKey(name))
/*      */         {
/*  391 */           double val = attributeTracker.get(name);
/*  392 */           val += val * nbtTagCompound.getDouble(ATTRIBUTES_VALUE.NBT);
/*  393 */           if (val == ((IAttribute)attributesByName.get(name)).a(val))
/*      */           {
/*      */ 
/*      */ 
/*  397 */             attributeTracker.put(name, val); }
/*      */         } else {
/*  399 */           nbttaglist.add(nbtTagCompound);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  404 */       for (int i = 0; i < nbttaglist.size(); i++)
/*  405 */         if ((nbttaglist.get(i) instanceof NBTTagCompound))
/*      */         {
/*      */ 
/*  408 */           NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*      */           
/*  410 */           if (nbttagcompound.hasKeyOfType(ATTRIBUTES_UUID_HIGH.NBT, 99))
/*      */           {
/*      */ 
/*  413 */             if (nbttagcompound.hasKeyOfType(ATTRIBUTES_UUID_LOW.NBT, 99))
/*      */             {
/*      */ 
/*  416 */               if (((nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT) instanceof NBTTagString)) && (CraftItemFactory.KNOWN_NBT_ATTRIBUTE_NAMES.contains(nbttagcompound.getString(ATTRIBUTES_IDENTIFIER.NBT))))
/*      */               {
/*      */ 
/*  419 */                 if (((nbttagcompound.get(ATTRIBUTES_NAME.NBT) instanceof NBTTagString)) && (!nbttagcompound.getString(ATTRIBUTES_NAME.NBT).isEmpty()))
/*      */                 {
/*      */ 
/*  422 */                   if (nbttagcompound.hasKeyOfType(ATTRIBUTES_VALUE.NBT, 99))
/*      */                   {
/*      */ 
/*  425 */                     if ((nbttagcompound.hasKeyOfType(ATTRIBUTES_TYPE.NBT, 99)) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) >= 0) && (nbttagcompound.getInt(ATTRIBUTES_TYPE.NBT) <= 2))
/*      */                     {
/*      */ 
/*      */ 
/*  429 */                       if (save == null) {
/*  430 */                         save = new NBTTagList();
/*      */                       }
/*      */                       
/*  433 */                       NBTTagCompound entry = new NBTTagCompound();
/*  434 */                       entry.set(ATTRIBUTES_UUID_HIGH.NBT, nbttagcompound.get(ATTRIBUTES_UUID_HIGH.NBT));
/*  435 */                       entry.set(ATTRIBUTES_UUID_LOW.NBT, nbttagcompound.get(ATTRIBUTES_UUID_LOW.NBT));
/*  436 */                       entry.set(ATTRIBUTES_IDENTIFIER.NBT, nbttagcompound.get(ATTRIBUTES_IDENTIFIER.NBT));
/*  437 */                       entry.set(ATTRIBUTES_NAME.NBT, nbttagcompound.get(ATTRIBUTES_NAME.NBT));
/*  438 */                       entry.set(ATTRIBUTES_VALUE.NBT, nbttagcompound.get(ATTRIBUTES_VALUE.NBT));
/*  439 */                       entry.set(ATTRIBUTES_TYPE.NBT, nbttagcompound.get(ATTRIBUTES_TYPE.NBT));
/*  440 */                       save.add(entry);
/*      */                     } } } } } }
/*      */         }
/*  443 */       this.unhandledTags.put(ATTRIBUTES.NBT, save);
/*      */     }
/*      */     
/*  446 */     Set<String> keys = tag.c();
/*  447 */     for (String key : keys) {
/*  448 */       if (!getHandledTags().contains(key)) {
/*  449 */         this.unhandledTags.put(key, tag.get(key));
/*      */       }
/*      */     }
/*      */     
/*  453 */     if (tag.hasKey(UNBREAKABLE.NBT))
/*      */     {
/*  455 */       this.spigot.setUnbreakable(tag.getBoolean(UNBREAKABLE.NBT));
/*      */     }
/*      */   }
/*      */   
/*      */   static Map<Enchantment, Integer> buildEnchantments(NBTTagCompound tag, ItemMetaKey key)
/*      */   {
/*  461 */     if (!tag.hasKey(key.NBT)) {
/*  462 */       return null;
/*      */     }
/*      */     
/*  465 */     NBTTagList ench = tag.getList(key.NBT, 10);
/*  466 */     Map<Enchantment, Integer> enchantments = new HashMap(ench.size());
/*      */     
/*  468 */     for (int i = 0; i < ench.size(); i++) {
/*  469 */       int id = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_ID.NBT);
/*  470 */       int level = 0xFFFF & ench.get(i).getShort(ENCHANTMENTS_LVL.NBT);
/*      */       
/*      */ 
/*  473 */       Enchantment e = Enchantment.getById(id);
/*  474 */       if (e != null)
/*      */       {
/*  476 */         enchantments.put(e, Integer.valueOf(level));
/*      */       }
/*      */     }
/*  479 */     return enchantments;
/*      */   }
/*      */   
/*      */   CraftMetaItem(Map<String, Object> map) {
/*  483 */     setDisplayName(SerializableMeta.getString(map, NAME.BUKKIT, true));
/*      */     
/*  485 */     Iterable<?> lore = (Iterable)SerializableMeta.getObject(Iterable.class, map, LORE.BUKKIT, true);
/*  486 */     if (lore != null) {
/*  487 */       safelyAdd(lore, this.lore = new ArrayList(), Integer.MAX_VALUE);
/*      */     }
/*      */     
/*  490 */     this.enchantments = buildEnchantments(map, ENCHANTMENTS);
/*      */     
/*  492 */     Integer repairCost = (Integer)SerializableMeta.getObject(Integer.class, map, REPAIR.BUKKIT, true);
/*  493 */     if (repairCost != null) {
/*  494 */       setRepairCost(repairCost.intValue());
/*      */     }
/*      */     
/*  497 */     Set hideFlags = (Set)SerializableMeta.getObject(Set.class, map, HIDEFLAGS.BUKKIT, true);
/*  498 */     if (hideFlags != null) {
/*  499 */       for (Object hideFlagObject : hideFlags) {
/*  500 */         String hideFlagString = (String)hideFlagObject;
/*      */         try {
/*  502 */           ItemFlag hideFlatEnum = ItemFlag.valueOf(hideFlagString);
/*  503 */           addItemFlags(new ItemFlag[] { hideFlatEnum });
/*      */         }
/*      */         catch (IllegalArgumentException localIllegalArgumentException) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  511 */     Boolean unbreakable = (Boolean)SerializableMeta.getObject(Boolean.class, map, UNBREAKABLE.BUKKIT, true);
/*  512 */     if (unbreakable != null)
/*      */     {
/*  514 */       this.spigot.setUnbreakable(unbreakable.booleanValue());
/*      */     }
/*      */     
/*      */ 
/*  518 */     String internal = SerializableMeta.getString(map, "internal", true);
/*  519 */     if (internal != null) {
/*  520 */       ByteArrayInputStream buf = new ByteArrayInputStream(Base64.decodeBase64(internal));
/*      */       try {
/*  522 */         NBTTagCompound tag = NBTCompressedStreamTools.a(buf);
/*  523 */         deserializeInternal(tag);
/*  524 */         Set<String> keys = tag.c();
/*  525 */         for (String key : keys) {
/*  526 */           if (!getHandledTags().contains(key)) {
/*  527 */             this.unhandledTags.put(key, tag.get(key));
/*      */           }
/*      */         }
/*      */       } catch (IOException ex) {
/*  531 */         Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, ex);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void deserializeInternal(NBTTagCompound tag) {}
/*      */   
/*      */   static Map<Enchantment, Integer> buildEnchantments(Map<String, Object> map, ItemMetaKey key)
/*      */   {
/*  540 */     Map<?, ?> ench = (Map)SerializableMeta.getObject(Map.class, map, key.BUKKIT, true);
/*  541 */     if (ench == null) {
/*  542 */       return null;
/*      */     }
/*      */     
/*  545 */     Map<Enchantment, Integer> enchantments = new HashMap(ench.size());
/*  546 */     for (Map.Entry<?, ?> entry : ench.entrySet()) {
/*  547 */       Enchantment enchantment = Enchantment.getByName(entry.getKey().toString());
/*      */       
/*  549 */       if ((enchantment != null) && ((entry.getValue() instanceof Integer))) {
/*  550 */         enchantments.put(enchantment, (Integer)entry.getValue());
/*      */       }
/*      */     }
/*      */     
/*  554 */     return enchantments;
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   void applyToItem(NBTTagCompound itemTag) {
/*  559 */     if (hasDisplayName()) {
/*  560 */       setDisplayTag(itemTag, NAME.NBT, new NBTTagString(this.displayName));
/*      */     }
/*      */     
/*  563 */     if (hasLore()) {
/*  564 */       setDisplayTag(itemTag, LORE.NBT, createStringList(this.lore));
/*      */     }
/*      */     
/*  567 */     if (this.hideFlag != 0) {
/*  568 */       itemTag.setInt(HIDEFLAGS.NBT, this.hideFlag);
/*      */     }
/*      */     
/*  571 */     applyEnchantments(this.enchantments, itemTag, ENCHANTMENTS);
/*      */     
/*      */ 
/*  574 */     if (this.spigot.isUnbreakable())
/*      */     {
/*  576 */       itemTag.setBoolean(UNBREAKABLE.NBT, true);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  581 */     if (hasRepairCost()) {
/*  582 */       itemTag.setInt(REPAIR.NBT, this.repairCost);
/*      */     }
/*      */     
/*  585 */     for (Map.Entry<String, NBTBase> e : this.unhandledTags.entrySet()) {
/*  586 */       itemTag.set((String)e.getKey(), (NBTBase)e.getValue());
/*      */     }
/*      */   }
/*      */   
/*      */   static NBTTagList createStringList(List<String> list) {
/*  591 */     if ((list == null) || (list.isEmpty())) {
/*  592 */       return null;
/*      */     }
/*      */     
/*  595 */     NBTTagList tagList = new NBTTagList();
/*  596 */     for (String value : list) {
/*  597 */       tagList.add(new NBTTagString(value));
/*      */     }
/*      */     
/*  600 */     return tagList;
/*      */   }
/*      */   
/*      */   static void applyEnchantments(Map<Enchantment, Integer> enchantments, NBTTagCompound tag, ItemMetaKey key) {
/*  604 */     if (enchantments == null) {
/*  605 */       return;
/*      */     }
/*      */     
/*  608 */     NBTTagList list = new NBTTagList();
/*      */     
/*  610 */     for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
/*  611 */       NBTTagCompound subtag = new NBTTagCompound();
/*      */       
/*  613 */       subtag.setShort(ENCHANTMENTS_ID.NBT, (short)((Enchantment)entry.getKey()).getId());
/*  614 */       subtag.setShort(ENCHANTMENTS_LVL.NBT, ((Integer)entry.getValue()).shortValue());
/*      */       
/*  616 */       list.add(subtag);
/*      */     }
/*      */     
/*  619 */     tag.set(key.NBT, list);
/*      */   }
/*      */   
/*      */   void setDisplayTag(NBTTagCompound tag, String key, NBTBase value) {
/*  623 */     NBTTagCompound display = tag.getCompound(DISPLAY.NBT);
/*      */     
/*  625 */     if (!tag.hasKey(DISPLAY.NBT)) {
/*  626 */       tag.set(DISPLAY.NBT, display);
/*      */     }
/*      */     
/*  629 */     display.set(key, value);
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   boolean applicableTo(Material type) {
/*  634 */     return type != Material.AIR;
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   boolean isEmpty() {
/*  639 */     return (!hasDisplayName()) && (!hasEnchants()) && (!hasLore()) && (!hasRepairCost()) && (this.unhandledTags.isEmpty()) && (this.hideFlag == 0) && (!this.spigot.isUnbreakable());
/*      */   }
/*      */   
/*      */   public String getDisplayName() {
/*  643 */     return this.displayName;
/*      */   }
/*      */   
/*      */   public final void setDisplayName(String name) {
/*  647 */     this.displayName = name;
/*      */   }
/*      */   
/*      */   public boolean hasDisplayName() {
/*  651 */     return !Strings.isNullOrEmpty(this.displayName);
/*      */   }
/*      */   
/*      */   public boolean hasLore() {
/*  655 */     return (this.lore != null) && (!this.lore.isEmpty());
/*      */   }
/*      */   
/*      */   public boolean hasRepairCost() {
/*  659 */     return this.repairCost > 0;
/*      */   }
/*      */   
/*      */   public boolean hasEnchant(Enchantment ench) {
/*  663 */     return (hasEnchants()) && (this.enchantments.containsKey(ench));
/*      */   }
/*      */   
/*      */   public int getEnchantLevel(Enchantment ench) {
/*  667 */     Integer level = hasEnchants() ? (Integer)this.enchantments.get(ench) : null;
/*  668 */     if (level == null) {
/*  669 */       return 0;
/*      */     }
/*  671 */     return level.intValue();
/*      */   }
/*      */   
/*      */   public Map<Enchantment, Integer> getEnchants() {
/*  675 */     return hasEnchants() ? ImmutableMap.copyOf(this.enchantments) : ImmutableMap.of();
/*      */   }
/*      */   
/*      */   public boolean addEnchant(Enchantment ench, int level, boolean ignoreRestrictions) {
/*  679 */     if (this.enchantments == null) {
/*  680 */       this.enchantments = new HashMap(4);
/*      */     }
/*      */     
/*  683 */     if ((ignoreRestrictions) || ((level >= ench.getStartLevel()) && (level <= ench.getMaxLevel()))) {
/*  684 */       Integer old = (Integer)this.enchantments.put(ench, Integer.valueOf(level));
/*  685 */       return (old == null) || (old.intValue() != level);
/*      */     }
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   public boolean removeEnchant(Enchantment ench)
/*      */   {
/*  692 */     boolean b = (hasEnchants()) && (this.enchantments.remove(ench) != null);
/*  693 */     if ((this.enchantments != null) && (this.enchantments.isEmpty()))
/*      */     {
/*  695 */       this.enchantments = null;
/*      */     }
/*  697 */     return b;
/*      */   }
/*      */   
/*      */   public boolean hasEnchants()
/*      */   {
/*  702 */     return (this.enchantments != null) && (!this.enchantments.isEmpty());
/*      */   }
/*      */   
/*      */   public boolean hasConflictingEnchant(Enchantment ench) {
/*  706 */     return checkConflictingEnchants(this.enchantments, ench);
/*      */   }
/*      */   
/*      */   public void addItemFlags(ItemFlag... hideFlags) {
/*      */     ItemFlag[] arrayOfItemFlag;
/*  711 */     int i = (arrayOfItemFlag = hideFlags).length; for (int j = 0; j < i; j++) { ItemFlag f = arrayOfItemFlag[j];
/*  712 */       this.hideFlag |= getBitModifier(f);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeItemFlags(ItemFlag... hideFlags) {
/*      */     ItemFlag[] arrayOfItemFlag;
/*  718 */     int i = (arrayOfItemFlag = hideFlags).length; for (int j = 0; j < i; j++) { ItemFlag f = arrayOfItemFlag[j];
/*  719 */       this.hideFlag &= (getBitModifier(f) ^ 0xFFFFFFFF);
/*      */     }
/*      */   }
/*      */   
/*      */   public Set<ItemFlag> getItemFlags()
/*      */   {
/*  725 */     Set<ItemFlag> currentFlags = EnumSet.noneOf(ItemFlag.class);
/*      */     ItemFlag[] arrayOfItemFlag;
/*  727 */     int i = (arrayOfItemFlag = ItemFlag.values()).length; for (int j = 0; j < i; j++) { ItemFlag f = arrayOfItemFlag[j];
/*  728 */       if (hasItemFlag(f)) {
/*  729 */         currentFlags.add(f);
/*      */       }
/*      */     }
/*      */     
/*  733 */     return currentFlags;
/*      */   }
/*      */   
/*      */   public boolean hasItemFlag(ItemFlag flag)
/*      */   {
/*  738 */     int bitModifier = getBitModifier(flag);
/*  739 */     return (this.hideFlag & bitModifier) == bitModifier;
/*      */   }
/*      */   
/*      */   private byte getBitModifier(ItemFlag hideFlag) {
/*  743 */     return (byte)(1 << hideFlag.ordinal());
/*      */   }
/*      */   
/*      */   public List<String> getLore() {
/*  747 */     return this.lore == null ? null : new ArrayList(this.lore);
/*      */   }
/*      */   
/*      */   public void setLore(List<String> lore) {
/*  751 */     if (lore == null) {
/*  752 */       this.lore = null;
/*      */     }
/*  754 */     else if (this.lore == null) {
/*  755 */       safelyAdd(lore, this.lore = new ArrayList(lore.size()), Integer.MAX_VALUE);
/*      */     } else {
/*  757 */       this.lore.clear();
/*  758 */       safelyAdd(lore, this.lore, Integer.MAX_VALUE);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getRepairCost()
/*      */   {
/*  764 */     return this.repairCost;
/*      */   }
/*      */   
/*      */   public void setRepairCost(int cost) {
/*  768 */     this.repairCost = cost;
/*      */   }
/*      */   
/*      */   public final boolean equals(Object object)
/*      */   {
/*  773 */     if (object == null) {
/*  774 */       return false;
/*      */     }
/*  776 */     if (object == this) {
/*  777 */       return true;
/*      */     }
/*  779 */     if (!(object instanceof CraftMetaItem)) {
/*  780 */       return false;
/*      */     }
/*  782 */     return CraftItemFactory.instance().equals(this, (ItemMeta)object);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Overridden
/*      */   boolean equalsCommon(CraftMetaItem that)
/*      */   {
/*  792 */     return (hasDisplayName() ? (that.hasDisplayName()) && (this.displayName.equals(that.displayName)) : !that.hasDisplayName()) && 
/*  793 */       (hasEnchants() ? (that.hasEnchants()) && (this.enchantments.equals(that.enchantments)) : !that.hasEnchants()) && 
/*  794 */       (hasLore() ? (that.hasLore()) && (this.lore.equals(that.lore)) : !that.hasLore()) && 
/*  795 */       (hasRepairCost() ? (that.hasRepairCost()) && (this.repairCost == that.repairCost) : !that.hasRepairCost()) && 
/*  796 */       (this.unhandledTags.equals(that.unhandledTags)) && 
/*  797 */       (this.hideFlag == that.hideFlag) && 
/*  798 */       (this.spigot.isUnbreakable() == that.spigot.isUnbreakable());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Overridden
/*      */   boolean notUncommon(CraftMetaItem meta)
/*      */   {
/*  808 */     return true;
/*      */   }
/*      */   
/*      */   public final int hashCode()
/*      */   {
/*  813 */     return applyHash();
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   int applyHash() {
/*  818 */     int hash = 3;
/*  819 */     hash = 61 * hash + (hasDisplayName() ? this.displayName.hashCode() : 0);
/*  820 */     hash = 61 * hash + (hasLore() ? this.lore.hashCode() : 0);
/*  821 */     hash = 61 * hash + (hasEnchants() ? this.enchantments.hashCode() : 0);
/*  822 */     hash = 61 * hash + (hasRepairCost() ? this.repairCost : 0);
/*  823 */     hash = 61 * hash + this.unhandledTags.hashCode();
/*  824 */     hash = 61 * hash + this.hideFlag;
/*  825 */     hash = 61 * hash + (this.spigot.isUnbreakable() ? 1231 : 1237);
/*  826 */     return hash;
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   public CraftMetaItem clone()
/*      */   {
/*      */     try {
/*  833 */       CraftMetaItem clone = (CraftMetaItem)super.clone();
/*  834 */       if (this.lore != null) {
/*  835 */         clone.lore = new ArrayList(this.lore);
/*      */       }
/*  837 */       if (this.enchantments != null) {
/*  838 */         clone.enchantments = new HashMap(this.enchantments);
/*      */       }
/*  840 */       clone.hideFlag = this.hideFlag;
/*  841 */       return clone;
/*      */     } catch (CloneNotSupportedException e) {
/*  843 */       throw new Error(e);
/*      */     }
/*      */   }
/*      */   
/*      */   public final Map<String, Object> serialize() {
/*  848 */     ImmutableMap.Builder<String, Object> map = ImmutableMap.builder();
/*  849 */     map.put("meta-type", SerializableMeta.classMap.get(getClass()));
/*  850 */     serialize(map);
/*  851 */     return map.build();
/*      */   }
/*      */   
/*      */   @Overridden
/*      */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder) {
/*  856 */     if (hasDisplayName()) {
/*  857 */       builder.put(NAME.BUKKIT, this.displayName);
/*      */     }
/*      */     
/*  860 */     if (hasLore()) {
/*  861 */       builder.put(LORE.BUKKIT, ImmutableList.copyOf(this.lore));
/*      */     }
/*      */     
/*  864 */     serializeEnchantments(this.enchantments, builder, ENCHANTMENTS);
/*      */     
/*  866 */     if (hasRepairCost()) {
/*  867 */       builder.put(REPAIR.BUKKIT, Integer.valueOf(this.repairCost));
/*      */     }
/*      */     
/*      */ 
/*  871 */     if (this.spigot.isUnbreakable())
/*      */     {
/*  873 */       builder.put(UNBREAKABLE.BUKKIT, Boolean.valueOf(true));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  878 */     Set<String> hideFlags = new HashSet();
/*  879 */     for (ItemFlag hideFlagEnum : getItemFlags()) {
/*  880 */       hideFlags.add(hideFlagEnum.name());
/*      */     }
/*  882 */     if (!hideFlags.isEmpty()) {
/*  883 */       builder.put(HIDEFLAGS.BUKKIT, hideFlags);
/*      */     }
/*      */     
/*  886 */     Map<String, NBTBase> internalTags = new HashMap(this.unhandledTags);
/*  887 */     serializeInternal(internalTags);
/*  888 */     if (!internalTags.isEmpty()) {
/*  889 */       NBTTagCompound internal = new NBTTagCompound();
/*  890 */       for (Map.Entry<String, NBTBase> e : internalTags.entrySet()) {
/*  891 */         internal.set((String)e.getKey(), (NBTBase)e.getValue());
/*      */       }
/*      */       try {
/*  894 */         ByteArrayOutputStream buf = new ByteArrayOutputStream();
/*  895 */         NBTCompressedStreamTools.a(internal, buf);
/*  896 */         builder.put("internal", Base64.encodeBase64String(buf.toByteArray()));
/*      */       } catch (IOException ex) {
/*  898 */         Logger.getLogger(CraftMetaItem.class.getName()).log(Level.SEVERE, null, ex);
/*      */       }
/*      */     }
/*      */     
/*  902 */     return builder;
/*      */   }
/*      */   
/*      */   void serializeInternal(Map<String, NBTBase> unhandledTags) {}
/*      */   
/*      */   static void serializeEnchantments(Map<Enchantment, Integer> enchantments, ImmutableMap.Builder<String, Object> builder, ItemMetaKey key)
/*      */   {
/*  909 */     if ((enchantments == null) || (enchantments.isEmpty())) {
/*  910 */       return;
/*      */     }
/*      */     
/*  913 */     ImmutableMap.Builder<String, Integer> enchants = ImmutableMap.builder();
/*  914 */     for (Map.Entry<? extends Enchantment, Integer> enchant : enchantments.entrySet()) {
/*  915 */       enchants.put(((Enchantment)enchant.getKey()).getName(), (Integer)enchant.getValue());
/*      */     }
/*      */     
/*  918 */     builder.put(key.BUKKIT, enchants.build());
/*      */   }
/*      */   
/*      */   static void safelyAdd(Iterable<?> addFrom, Collection<String> addTo, int maxItemLength) {
/*  922 */     if (addFrom == null) {
/*  923 */       return;
/*      */     }
/*      */     
/*  926 */     for (Object object : addFrom) {
/*  927 */       if (!(object instanceof String)) {
/*  928 */         if (object != null) {
/*  929 */           throw new IllegalArgumentException(addFrom + " cannot contain non-string " + object.getClass().getName());
/*      */         }
/*      */         
/*  932 */         addTo.add("");
/*      */       } else {
/*  934 */         String page = object.toString();
/*      */         
/*  936 */         if (page.length() > maxItemLength) {
/*  937 */           page = page.substring(0, maxItemLength);
/*      */         }
/*      */         
/*  940 */         addTo.add(page);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static boolean checkConflictingEnchants(Map<Enchantment, Integer> enchantments, Enchantment ench) {
/*  946 */     if ((enchantments == null) || (enchantments.isEmpty())) {
/*  947 */       return false;
/*      */     }
/*      */     
/*  950 */     for (Enchantment enchant : enchantments.keySet()) {
/*  951 */       if (enchant.conflictsWith(ench)) {
/*  952 */         return true;
/*      */       }
/*      */     }
/*      */     
/*  956 */     return false;
/*      */   }
/*      */   
/*      */   public final String toString()
/*      */   {
/*  961 */     return (String)SerializableMeta.classMap.get(getClass()) + "_META:" + serialize();
/*      */   }
/*      */   
/*      */   public static Set<String> getHandledTags() {
/*  965 */     synchronized (HANDLED_TAGS) {
/*  966 */       if (HANDLED_TAGS.isEmpty()) {
/*  967 */         HANDLED_TAGS.addAll(Arrays.asList(new String[] {
/*  968 */           UNBREAKABLE.NBT, 
/*  969 */           DISPLAY.NBT, 
/*  970 */           REPAIR.NBT, 
/*  971 */           ENCHANTMENTS.NBT, 
/*  972 */           HIDEFLAGS.NBT, 
/*  973 */           CraftMetaMap.MAP_SCALING.NBT, 
/*  974 */           CraftMetaPotion.POTION_EFFECTS.NBT, 
/*  975 */           CraftMetaSkull.SKULL_OWNER.NBT, 
/*  976 */           CraftMetaSkull.SKULL_PROFILE.NBT, 
/*  977 */           CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT, 
/*  978 */           CraftMetaBook.BOOK_TITLE.NBT, 
/*  979 */           CraftMetaBook.BOOK_AUTHOR.NBT, 
/*  980 */           CraftMetaBook.BOOK_PAGES.NBT, 
/*  981 */           CraftMetaBook.RESOLVED.NBT, 
/*  982 */           CraftMetaBook.GENERATION.NBT, 
/*  983 */           CraftMetaFirework.FIREWORKS.NBT, 
/*  984 */           CraftMetaEnchantedBook.STORED_ENCHANTMENTS.NBT, 
/*  985 */           CraftMetaCharge.EXPLOSION.NBT, 
/*  986 */           CraftMetaBlockState.BLOCK_ENTITY_TAG.NBT }));
/*      */       }
/*      */       
/*  989 */       return HANDLED_TAGS;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*  994 */   private final ItemMeta.Spigot spigot = new ItemMeta.Spigot()
/*      */   {
/*      */     private boolean unbreakable;
/*      */     
/*      */ 
/*      */     public void setUnbreakable(boolean setUnbreakable)
/*      */     {
/* 1001 */       this.unbreakable = setUnbreakable;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isUnbreakable()
/*      */     {
/* 1007 */       return this.unbreakable;
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*      */   public ItemMeta.Spigot spigot()
/*      */   {
/* 1014 */     return this.spigot;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */