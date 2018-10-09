/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import gnu.trove.map.hash.TObjectIntHashMap;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class ServerStatisticManager extends StatisticManager
/*     */ {
/*  24 */   private static final Logger b = ;
/*     */   private final MinecraftServer c;
/*     */   private final File d;
/*  27 */   private final Set<Statistic> e = Sets.newHashSet();
/*  28 */   private int f = 65236;
/*  29 */   private boolean g = false;
/*     */   
/*     */   public ServerStatisticManager(MinecraftServer minecraftserver, File file) {
/*  32 */     this.c = minecraftserver;
/*  33 */     this.d = file;
/*     */     
/*  35 */     for (String name : SpigotConfig.forcedStats.keySet())
/*     */     {
/*  37 */       StatisticWrapper wrapper = new StatisticWrapper();
/*  38 */       wrapper.a(SpigotConfig.forcedStats.get(name));
/*  39 */       this.a.put(StatisticList.getStatistic(name), wrapper);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  45 */     if (this.d.isFile()) {
/*     */       try {
/*  47 */         this.a.clear();
/*  48 */         this.a.putAll(a(FileUtils.readFileToString(this.d)));
/*     */       } catch (IOException ioexception) {
/*  50 */         b.error("Couldn't read statistics file " + this.d, ioexception);
/*     */       } catch (com.google.gson.JsonParseException jsonparseexception) {
/*  52 */         b.error("Couldn't parse statistics file " + this.d, jsonparseexception);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b()
/*     */   {
/*  59 */     if (SpigotConfig.disableStatSaving) return;
/*     */     try {
/*  61 */       FileUtils.writeStringToFile(this.d, a(this.a));
/*     */     } catch (IOException ioexception) {
/*  63 */       b.error("Couldn't save stats", ioexception);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setStatistic(EntityHuman entityhuman, Statistic statistic, int i)
/*     */   {
/*  69 */     if (SpigotConfig.disableStatSaving) return;
/*  70 */     int j = statistic.d() ? getStatisticValue(statistic) : 0;
/*     */     
/*  72 */     super.setStatistic(entityhuman, statistic, i);
/*  73 */     this.e.add(statistic);
/*  74 */     if ((statistic.d()) && (j == 0) && (i > 0)) {
/*  75 */       this.g = true;
/*  76 */       if (this.c.aB()) {
/*  77 */         this.c.getPlayerList().sendMessage(new ChatMessage("chat.type.achievement", new Object[] { entityhuman.getScoreboardDisplayName(), statistic.j() }));
/*     */       }
/*     */     }
/*     */     
/*  81 */     if ((statistic.d()) && (j > 0) && (i == 0)) {
/*  82 */       this.g = true;
/*  83 */       if (this.c.aB()) {
/*  84 */         this.c.getPlayerList().sendMessage(new ChatMessage("chat.type.achievement.taken", new Object[] { entityhuman.getScoreboardDisplayName(), statistic.j() }));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Statistic> c()
/*     */   {
/*  91 */     java.util.HashSet hashset = Sets.newHashSet(this.e);
/*     */     
/*  93 */     this.e.clear();
/*  94 */     this.g = false;
/*  95 */     return hashset;
/*     */   }
/*     */   
/*     */   public Map<Statistic, StatisticWrapper> a(String s) {
/*  99 */     JsonElement jsonelement = new JsonParser().parse(s);
/*     */     
/* 101 */     if (!jsonelement.isJsonObject()) {
/* 102 */       return Maps.newHashMap();
/*     */     }
/* 104 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 105 */     HashMap hashmap = Maps.newHashMap();
/* 106 */     Iterator iterator = jsonobject.entrySet().iterator();
/*     */     
/* 108 */     while (iterator.hasNext()) {
/* 109 */       Map.Entry entry = (Map.Entry)iterator.next();
/* 110 */       Statistic statistic = StatisticList.getStatistic((String)entry.getKey());
/*     */       
/* 112 */       if (statistic != null) {
/* 113 */         StatisticWrapper statisticwrapper = new StatisticWrapper();
/*     */         
/* 115 */         if ((((JsonElement)entry.getValue()).isJsonPrimitive()) && (((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber())) {
/* 116 */           statisticwrapper.a(((JsonElement)entry.getValue()).getAsInt());
/* 117 */         } else if (((JsonElement)entry.getValue()).isJsonObject()) {
/* 118 */           JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 120 */           if ((jsonobject1.has("value")) && (jsonobject1.get("value").isJsonPrimitive()) && (jsonobject1.get("value").getAsJsonPrimitive().isNumber())) {
/* 121 */             statisticwrapper.a(jsonobject1.getAsJsonPrimitive("value").getAsInt());
/*     */           }
/*     */           
/* 124 */           if ((jsonobject1.has("progress")) && (statistic.l() != null)) {
/*     */             try {
/* 126 */               Constructor constructor = statistic.l().getConstructor(new Class[0]);
/* 127 */               IJsonStatistic ijsonstatistic = (IJsonStatistic)constructor.newInstance(new Object[0]);
/*     */               
/* 129 */               ijsonstatistic.a(jsonobject1.get("progress"));
/* 130 */               statisticwrapper.a(ijsonstatistic);
/*     */             } catch (Throwable throwable) {
/* 132 */               b.warn("Invalid statistic progress in " + this.d, throwable);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 137 */         hashmap.put(statistic, statisticwrapper);
/*     */       } else {
/* 139 */         b.warn("Invalid statistic in " + this.d + ": Don't know what " + (String)entry.getKey() + " is");
/*     */       }
/*     */     }
/*     */     
/* 143 */     return hashmap;
/*     */   }
/*     */   
/*     */   public static String a(Map<Statistic, StatisticWrapper> map)
/*     */   {
/* 148 */     JsonObject jsonobject = new JsonObject();
/* 149 */     Iterator iterator = map.entrySet().iterator();
/*     */     
/* 151 */     while (iterator.hasNext()) {
/* 152 */       Map.Entry entry = (Map.Entry)iterator.next();
/*     */       
/* 154 */       if (((StatisticWrapper)entry.getValue()).b() != null) {
/* 155 */         JsonObject jsonobject1 = new JsonObject();
/*     */         
/* 157 */         jsonobject1.addProperty("value", Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));
/*     */         try
/*     */         {
/* 160 */           jsonobject1.add("progress", ((StatisticWrapper)entry.getValue()).b().a());
/*     */         } catch (Throwable throwable) {
/* 162 */           b.warn("Couldn't save statistic " + ((Statistic)entry.getKey()).e() + ": error serializing progress", throwable);
/*     */         }
/*     */         
/* 165 */         jsonobject.add(((Statistic)entry.getKey()).name, jsonobject1);
/*     */       } else {
/* 167 */         jsonobject.addProperty(((Statistic)entry.getKey()).name, Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));
/*     */       }
/*     */     }
/*     */     
/* 171 */     return jsonobject.toString();
/*     */   }
/*     */   
/*     */   public void d() {
/* 175 */     Iterator iterator = this.a.keySet().iterator();
/*     */     
/* 177 */     while (iterator.hasNext()) {
/* 178 */       Statistic statistic = (Statistic)iterator.next();
/*     */       
/* 180 */       this.e.add(statistic);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer)
/*     */   {
/* 186 */     int i = this.c.at();
/* 187 */     HashMap hashmap = Maps.newHashMap();
/*     */     
/* 189 */     if ((this.g) || (i - this.f > 300)) {
/* 190 */       this.f = i;
/* 191 */       Iterator iterator = c().iterator();
/*     */       
/* 193 */       while (iterator.hasNext()) {
/* 194 */         Statistic statistic = (Statistic)iterator.next();
/*     */         
/* 196 */         hashmap.put(statistic, Integer.valueOf(getStatisticValue(statistic)));
/*     */       }
/*     */     }
/*     */     
/* 200 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
/*     */   }
/*     */   
/*     */   public void updateStatistics(EntityPlayer entityplayer) {
/* 204 */     HashMap hashmap = Maps.newHashMap();
/* 205 */     Iterator iterator = AchievementList.e.iterator();
/*     */     
/* 207 */     while (iterator.hasNext()) {
/* 208 */       Achievement achievement = (Achievement)iterator.next();
/*     */       
/* 210 */       if (hasAchievement(achievement)) {
/* 211 */         hashmap.put(achievement, Integer.valueOf(getStatisticValue(achievement)));
/* 212 */         this.e.remove(achievement);
/*     */       }
/*     */     }
/*     */     
/* 216 */     entityplayer.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 220 */     return this.g;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerStatisticManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */