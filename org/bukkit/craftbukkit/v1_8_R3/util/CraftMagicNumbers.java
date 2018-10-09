/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.server.v1_8_R3.Blocks;
/*     */ import net.minecraft.server.v1_8_R3.Item;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftKey;
/*     */ import net.minecraft.server.v1_8_R3.MojangsonParseException;
/*     */ import net.minecraft.server.v1_8_R3.MojangsonParser;
/*     */ import net.minecraft.server.v1_8_R3.RegistryMaterials;
/*     */ import net.minecraft.server.v1_8_R3.StatisticList;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.UnsafeValues;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftStatistic;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CraftMagicNumbers
/*     */   implements UnsafeValues
/*     */ {
/*  31 */   public static final UnsafeValues INSTANCE = new CraftMagicNumbers();
/*     */   
/*     */ 
/*     */   public static net.minecraft.server.v1_8_R3.Block getBlock(org.bukkit.block.Block block)
/*     */   {
/*  36 */     return getBlock(block.getType());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static net.minecraft.server.v1_8_R3.Block getBlock(int id)
/*     */   {
/*  42 */     return getBlock(Material.getMaterial(id));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static int getId(net.minecraft.server.v1_8_R3.Block block)
/*     */   {
/*  48 */     return net.minecraft.server.v1_8_R3.Block.getId(block);
/*     */   }
/*     */   
/*     */   public static Material getMaterial(net.minecraft.server.v1_8_R3.Block block) {
/*  52 */     return Material.getMaterial(net.minecraft.server.v1_8_R3.Block.getId(block));
/*     */   }
/*     */   
/*     */   public static Item getItem(Material material)
/*     */   {
/*  57 */     Item item = Item.getById(material.getId());
/*  58 */     return item;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static Item getItem(int id)
/*     */   {
/*  64 */     return Item.getById(id);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static int getId(Item item)
/*     */   {
/*  70 */     return Item.getId(item);
/*     */   }
/*     */   
/*     */   public static Material getMaterial(Item item)
/*     */   {
/*  75 */     Material material = Material.getMaterial(Item.getId(item));
/*     */     
/*  77 */     if (material == null) {
/*  78 */       return Material.AIR;
/*     */     }
/*     */     
/*  81 */     return material;
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Block getBlock(Material material)
/*     */   {
/*  86 */     net.minecraft.server.v1_8_R3.Block block = net.minecraft.server.v1_8_R3.Block.getById(material.getId());
/*     */     
/*  88 */     if (block == null) {
/*  89 */       return Blocks.AIR;
/*     */     }
/*     */     
/*  92 */     return block;
/*     */   }
/*     */   
/*     */   public Material getMaterialFromInternalName(String name)
/*     */   {
/*  97 */     return getMaterial((Item)Item.REGISTRY.get(new MinecraftKey(name)));
/*     */   }
/*     */   
/*     */   public List<String> tabCompleteInternalMaterialName(String token, List<String> completions)
/*     */   {
/* 102 */     ArrayList<String> results = Lists.newArrayList();
/* 103 */     for (MinecraftKey key : Item.REGISTRY.keySet()) {
/* 104 */       results.add(key.toString());
/*     */     }
/* 106 */     return (List)StringUtil.copyPartialMatches(token, results, completions);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack modifyItemStack(org.bukkit.inventory.ItemStack stack, String arguments)
/*     */   {
/* 111 */     net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
/*     */     try
/*     */     {
/* 114 */       nmsStack.setTag(MojangsonParser.parse(arguments));
/*     */     } catch (MojangsonParseException ex) {
/* 116 */       Logger.getLogger(CraftMagicNumbers.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     
/* 119 */     stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));
/*     */     
/* 121 */     return stack;
/*     */   }
/*     */   
/*     */   public org.bukkit.Statistic getStatisticFromInternalName(String name)
/*     */   {
/* 126 */     return CraftStatistic.getBukkitStatisticByName(name);
/*     */   }
/*     */   
/*     */   public Achievement getAchievementFromInternalName(String name)
/*     */   {
/* 131 */     return CraftStatistic.getBukkitAchievementByName(name);
/*     */   }
/*     */   
/*     */   public List<String> tabCompleteInternalStatisticOrAchievementName(String token, List<String> completions)
/*     */   {
/* 136 */     List<String> matches = new ArrayList();
/* 137 */     Iterator iterator = StatisticList.stats.iterator();
/* 138 */     while (iterator.hasNext()) {
/* 139 */       String statistic = ((net.minecraft.server.v1_8_R3.Statistic)iterator.next()).name;
/* 140 */       if (statistic.startsWith(token)) {
/* 141 */         matches.add(statistic);
/*     */       }
/*     */     }
/* 144 */     return matches;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\CraftMagicNumbers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */