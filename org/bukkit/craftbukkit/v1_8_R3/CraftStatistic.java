/*     */ package org.bukkit.craftbukkit.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.CaseFormat;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import com.google.common.collect.ImmutableBiMap.Builder;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import net.minecraft.server.v1_8_R3.Block;
/*     */ import net.minecraft.server.v1_8_R3.EntityTypes.MonsterEggInfo;
/*     */ import net.minecraft.server.v1_8_R3.Item;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftKey;
/*     */ import net.minecraft.server.v1_8_R3.RegistryBlocks;
/*     */ import net.minecraft.server.v1_8_R3.StatisticList;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.EntityType;
/*     */ 
/*     */ public class CraftStatistic
/*     */ {
/*     */   private static final BiMap<String, org.bukkit.Statistic> statistics;
/*     */   private static final BiMap<String, org.bukkit.Achievement> achievements;
/*     */   
/*     */   static
/*     */   {
/*  25 */     ImmutableMap<String, org.bukkit.Achievement> specialCases = ImmutableMap.builder()
/*  26 */       .put("achievement.buildWorkBench", org.bukkit.Achievement.BUILD_WORKBENCH)
/*  27 */       .put("achievement.diamonds", org.bukkit.Achievement.GET_DIAMONDS)
/*  28 */       .put("achievement.portal", org.bukkit.Achievement.NETHER_PORTAL)
/*  29 */       .put("achievement.ghast", org.bukkit.Achievement.GHAST_RETURN)
/*  30 */       .put("achievement.theEnd", org.bukkit.Achievement.END_PORTAL)
/*  31 */       .put("achievement.theEnd2", org.bukkit.Achievement.THE_END)
/*  32 */       .put("achievement.blazeRod", org.bukkit.Achievement.GET_BLAZE_ROD)
/*  33 */       .put("achievement.potion", org.bukkit.Achievement.BREW_POTION)
/*  34 */       .build();
/*  35 */     ImmutableBiMap.Builder<String, org.bukkit.Statistic> statisticBuilder = ImmutableBiMap.builder();
/*  36 */     ImmutableBiMap.Builder<String, org.bukkit.Achievement> achievementBuilder = ImmutableBiMap.builder();
/*  37 */     Object localObject; int i = (localObject = org.bukkit.Statistic.values()).length; for (int j = 0; j < i; j++) { org.bukkit.Statistic statistic = localObject[j];
/*  38 */       if (statistic == org.bukkit.Statistic.PLAY_ONE_TICK) {
/*  39 */         statisticBuilder.put("stat.playOneMinute", statistic);
/*     */       } else {
/*  41 */         statisticBuilder.put("stat." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, statistic.name()), statistic);
/*     */       }
/*     */     }
/*  44 */     i = (localObject = org.bukkit.Achievement.values()).length; for (j = 0; j < i; j++) { org.bukkit.Achievement achievement = localObject[j];
/*  45 */       if (!specialCases.values().contains(achievement))
/*     */       {
/*     */ 
/*  48 */         achievementBuilder.put("achievement." + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, achievement.name()), achievement);
/*     */       }
/*     */     }
/*  51 */     achievementBuilder.putAll(specialCases);
/*     */     
/*  53 */     statistics = statisticBuilder.build();
/*  54 */     achievements = achievementBuilder.build();
/*     */   }
/*     */   
/*     */ 
/*     */   public static org.bukkit.Achievement getBukkitAchievement(net.minecraft.server.v1_8_R3.Achievement achievement)
/*     */   {
/*  60 */     return getBukkitAchievementByName(achievement.name);
/*     */   }
/*     */   
/*     */   public static org.bukkit.Achievement getBukkitAchievementByName(String name) {
/*  64 */     return (org.bukkit.Achievement)achievements.get(name);
/*     */   }
/*     */   
/*     */   public static org.bukkit.Statistic getBukkitStatistic(net.minecraft.server.v1_8_R3.Statistic statistic) {
/*  68 */     return getBukkitStatisticByName(statistic.name);
/*     */   }
/*     */   
/*     */   public static org.bukkit.Statistic getBukkitStatisticByName(String name) {
/*  72 */     if (name.startsWith("stat.killEntity")) {
/*  73 */       name = "stat.killEntity";
/*     */     }
/*  75 */     if (name.startsWith("stat.entityKilledBy")) {
/*  76 */       name = "stat.entityKilledBy";
/*     */     }
/*  78 */     if (name.startsWith("stat.breakItem")) {
/*  79 */       name = "stat.breakItem";
/*     */     }
/*  81 */     if (name.startsWith("stat.useItem")) {
/*  82 */       name = "stat.useItem";
/*     */     }
/*  84 */     if (name.startsWith("stat.mineBlock")) {
/*  85 */       name = "stat.mineBlock";
/*     */     }
/*  87 */     if (name.startsWith("stat.craftItem")) {
/*  88 */       name = "stat.craftItem";
/*     */     }
/*  90 */     return (org.bukkit.Statistic)statistics.get(name);
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Statistic getNMSStatistic(org.bukkit.Statistic statistic) {
/*  94 */     return StatisticList.getStatistic((String)statistics.inverse().get(statistic));
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Achievement getNMSAchievement(org.bukkit.Achievement achievement) {
/*  98 */     return (net.minecraft.server.v1_8_R3.Achievement)StatisticList.getStatistic((String)achievements.inverse().get(achievement));
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Statistic getMaterialStatistic(org.bukkit.Statistic stat, Material material) {
/*     */     try {
/* 103 */       if (stat == org.bukkit.Statistic.MINE_BLOCK) {
/* 104 */         return StatisticList.MINE_BLOCK_COUNT[material.getId()];
/*     */       }
/* 106 */       if (stat == org.bukkit.Statistic.CRAFT_ITEM) {
/* 107 */         return StatisticList.CRAFT_BLOCK_COUNT[material.getId()];
/*     */       }
/* 109 */       if (stat == org.bukkit.Statistic.USE_ITEM) {
/* 110 */         return StatisticList.USE_ITEM_COUNT[material.getId()];
/*     */       }
/* 112 */       if (stat == org.bukkit.Statistic.BREAK_ITEM) {
/* 113 */         return StatisticList.BREAK_ITEM_COUNT[material.getId()];
/*     */       }
/*     */     } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 116 */       return null;
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   public static net.minecraft.server.v1_8_R3.Statistic getEntityStatistic(org.bukkit.Statistic stat, EntityType entity) {
/* 122 */     EntityTypes.MonsterEggInfo monsteregginfo = (EntityTypes.MonsterEggInfo)net.minecraft.server.v1_8_R3.EntityTypes.eggInfo.get(Integer.valueOf(entity.getTypeId()));
/*     */     
/* 124 */     if (monsteregginfo != null) {
/* 125 */       return monsteregginfo.killEntityStatistic;
/*     */     }
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public static EntityType getEntityTypeFromStatistic(net.minecraft.server.v1_8_R3.Statistic statistic) {
/* 131 */     String statisticString = statistic.name;
/* 132 */     return EntityType.fromName(statisticString.substring(statisticString.lastIndexOf(".") + 1));
/*     */   }
/*     */   
/*     */   public static Material getMaterialFromStatistic(net.minecraft.server.v1_8_R3.Statistic statistic) {
/* 136 */     String statisticString = statistic.name;
/* 137 */     String val = statisticString.substring(statisticString.lastIndexOf(".") + 1);
/* 138 */     Item item = (Item)Item.REGISTRY.get(new MinecraftKey(val));
/* 139 */     if (item != null) {
/* 140 */       return Material.getMaterial(Item.getId(item));
/*     */     }
/* 142 */     Block block = (Block)Block.REGISTRY.get(new MinecraftKey(val));
/* 143 */     if (block != null) {
/* 144 */       return Material.getMaterial(Block.getId(block));
/*     */     }
/*     */     try {
/* 147 */       return Material.getMaterial(Integer.parseInt(val));
/*     */     } catch (NumberFormatException localNumberFormatException) {}
/* 149 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */