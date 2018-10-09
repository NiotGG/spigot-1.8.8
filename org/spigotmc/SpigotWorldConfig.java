/*     */ package org.spigotmc;
/*     */ 
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ public class SpigotWorldConfig
/*     */ {
/*     */   private final String worldName;
/*     */   private final YamlConfiguration config;
/*     */   private boolean verbose;
/*     */   public int chunksPerTick;
/*     */   public boolean clearChunksOnTick;
/*     */   public int cactusModifier;
/*     */   public int caneModifier;
/*     */   public int melonModifier;
/*     */   
/*     */   public SpigotWorldConfig(String worldName) {
/*  17 */     this.worldName = worldName;
/*  18 */     this.config = SpigotConfig.config;
/*  19 */     init();
/*     */   }
/*     */   
/*     */   public void init()
/*     */   {
/*  24 */     this.verbose = getBoolean("verbose", true);
/*     */     
/*  26 */     log("-------- World Settings For [" + this.worldName + "] --------");
/*  27 */     SpigotConfig.readConfig(SpigotWorldConfig.class, this);
/*     */   }
/*     */   
/*     */   private void log(String s)
/*     */   {
/*  32 */     if (this.verbose)
/*     */     {
/*  34 */       org.bukkit.Bukkit.getLogger().info(s);
/*     */     }
/*     */   }
/*     */   
/*     */   private void set(String path, Object val)
/*     */   {
/*  40 */     this.config.set("world-settings.default." + path, val);
/*     */   }
/*     */   
/*     */   private boolean getBoolean(String path, boolean def)
/*     */   {
/*  45 */     this.config.addDefault("world-settings.default." + path, Boolean.valueOf(def));
/*  46 */     return this.config.getBoolean("world-settings." + this.worldName + "." + path, this.config.getBoolean("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private double getDouble(String path, double def)
/*     */   {
/*  51 */     this.config.addDefault("world-settings.default." + path, Double.valueOf(def));
/*  52 */     return this.config.getDouble("world-settings." + this.worldName + "." + path, this.config.getDouble("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private int getInt(String path, int def)
/*     */   {
/*  57 */     this.config.addDefault("world-settings.default." + path, Integer.valueOf(def));
/*  58 */     return this.config.getInt("world-settings." + this.worldName + "." + path, this.config.getInt("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private <T> java.util.List getList(String path, T def)
/*     */   {
/*  63 */     this.config.addDefault("world-settings.default." + path, def);
/*  64 */     return this.config.getList("world-settings." + this.worldName + "." + path, this.config.getList("world-settings.default." + path));
/*     */   }
/*     */   
/*     */   private String getString(String path, String def)
/*     */   {
/*  69 */     this.config.addDefault("world-settings.default." + path, def);
/*  70 */     return this.config.getString("world-settings." + this.worldName + "." + path, this.config.getString("world-settings.default." + path));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void chunksPerTick()
/*     */   {
/*  77 */     this.chunksPerTick = getInt("chunks-per-tick", 650);
/*  78 */     log("Chunks to Grow per Tick: " + this.chunksPerTick);
/*     */     
/*  80 */     this.clearChunksOnTick = getBoolean("clear-tick-list", false);
/*  81 */     log("Clear tick list: " + this.clearChunksOnTick);
/*     */   }
/*     */   
/*     */   public int mushroomModifier;
/*     */   public int pumpkinModifier;
/*     */   public int saplingModifier;
/*     */   public int wheatModifier;
/*     */   public int wartModifier;
/*     */   public double itemMerge;
/*     */   public double expMerge;
/*     */   public int viewDistance;
/*     */   public byte mobSpawnRange;
/*     */   private int getAndValidateGrowth(String crop)
/*     */   {
/*  95 */     int modifier = getInt("growth." + crop.toLowerCase() + "-modifier", 100);
/*  96 */     if (modifier == 0)
/*     */     {
/*  98 */       log("Cannot set " + crop + " growth to zero, defaulting to 100");
/*  99 */       modifier = 100;
/*     */     }
/* 101 */     log(crop + " Growth Modifier: " + modifier + "%");
/*     */     
/* 103 */     return modifier;
/*     */   }
/*     */   
/*     */   private void growthModifiers() {
/* 107 */     this.cactusModifier = getAndValidateGrowth("Cactus");
/* 108 */     this.caneModifier = getAndValidateGrowth("Cane");
/* 109 */     this.melonModifier = getAndValidateGrowth("Melon");
/* 110 */     this.mushroomModifier = getAndValidateGrowth("Mushroom");
/* 111 */     this.pumpkinModifier = getAndValidateGrowth("Pumpkin");
/* 112 */     this.saplingModifier = getAndValidateGrowth("Sapling");
/* 113 */     this.wheatModifier = getAndValidateGrowth("Wheat");
/* 114 */     this.wartModifier = getAndValidateGrowth("NetherWart");
/*     */   }
/*     */   
/*     */ 
/*     */   private void itemMerge()
/*     */   {
/* 120 */     this.itemMerge = getDouble("merge-radius.item", 2.5D);
/* 121 */     log("Item Merge Radius: " + this.itemMerge);
/*     */   }
/*     */   
/*     */ 
/*     */   private void expMerge()
/*     */   {
/* 127 */     this.expMerge = getDouble("merge-radius.exp", 3.0D);
/* 128 */     log("Experience Merge Radius: " + this.expMerge);
/*     */   }
/*     */   
/*     */ 
/*     */   private void viewDistance()
/*     */   {
/* 134 */     this.viewDistance = getInt("view-distance", org.bukkit.Bukkit.getViewDistance());
/* 135 */     log("View Distance: " + this.viewDistance);
/*     */   }
/*     */   
/*     */ 
/*     */   private void mobSpawnRange()
/*     */   {
/* 141 */     this.mobSpawnRange = ((byte)getInt("mob-spawn-range", 4));
/* 142 */     log("Mob Spawn Range: " + this.mobSpawnRange);
/*     */   }
/*     */   
/* 145 */   public int animalActivationRange = 32;
/* 146 */   public int monsterActivationRange = 32;
/* 147 */   public int miscActivationRange = 16;
/*     */   
/*     */   private void activationRange() {
/* 150 */     this.animalActivationRange = getInt("entity-activation-range.animals", this.animalActivationRange);
/* 151 */     this.monsterActivationRange = getInt("entity-activation-range.monsters", this.monsterActivationRange);
/* 152 */     this.miscActivationRange = getInt("entity-activation-range.misc", this.miscActivationRange);
/* 153 */     log("Entity Activation Range: An " + this.animalActivationRange + " / Mo " + this.monsterActivationRange + " / Mi " + this.miscActivationRange);
/*     */   }
/*     */   
/* 156 */   public int playerTrackingRange = 48;
/* 157 */   public int animalTrackingRange = 48;
/* 158 */   public int monsterTrackingRange = 48;
/* 159 */   public int miscTrackingRange = 32;
/* 160 */   public int otherTrackingRange = 64;
/*     */   public int hopperTransfer;
/*     */   
/* 163 */   private void trackingRange() { this.playerTrackingRange = getInt("entity-tracking-range.players", this.playerTrackingRange);
/* 164 */     this.animalTrackingRange = getInt("entity-tracking-range.animals", this.animalTrackingRange);
/* 165 */     this.monsterTrackingRange = getInt("entity-tracking-range.monsters", this.monsterTrackingRange);
/* 166 */     this.miscTrackingRange = getInt("entity-tracking-range.misc", this.miscTrackingRange);
/* 167 */     this.otherTrackingRange = getInt("entity-tracking-range.other", this.otherTrackingRange);
/* 168 */     log("Entity Tracking Range: Pl " + this.playerTrackingRange + " / An " + this.animalTrackingRange + " / Mo " + this.monsterTrackingRange + " / Mi " + this.miscTrackingRange + " / Other " + this.otherTrackingRange); }
/*     */   
/*     */   public int hopperCheck;
/*     */   public int hopperAmount;
/*     */   public boolean randomLightUpdates;
/*     */   public boolean saveStructureInfo;
/*     */   public int itemDespawnRate;
/*     */   public int arrowDespawnRate;
/*     */   
/* 177 */   private void hoppers() { this.hopperTransfer = getInt("ticks-per.hopper-transfer", 8);
/*     */     
/*     */ 
/*     */ 
/* 181 */     this.hopperCheck = getInt("ticks-per.hopper-check", this.hopperTransfer);
/* 182 */     this.hopperAmount = getInt("hopper-amount", 1);
/* 183 */     log("Hopper Transfer: " + this.hopperTransfer + " Hopper Check: " + this.hopperCheck + " Hopper Amount: " + this.hopperAmount);
/*     */   }
/*     */   
/*     */   public boolean antiXray;
/*     */   public int engineMode;
/*     */   
/* 189 */   private void lightUpdates() { this.randomLightUpdates = getBoolean("random-light-updates", false);
/* 190 */     log("Random Lighting Updates: " + this.randomLightUpdates);
/*     */   }
/*     */   
/*     */   public java.util.List<Integer> hiddenBlocks;
/*     */   public java.util.List<Integer> replaceBlocks;
/*     */   public AntiXray antiXrayInstance;
/* 196 */   private void structureInfo() { this.saveStructureInfo = getBoolean("save-structure-info", true);
/* 197 */     log("Structure Info Saving: " + this.saveStructureInfo);
/* 198 */     if (!this.saveStructureInfo)
/*     */     {
/* 200 */       log("*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs!");
/* 201 */       log("*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void itemDespawnRate()
/*     */   {
/* 208 */     this.itemDespawnRate = getInt("item-despawn-rate", 6000);
/* 209 */     log("Item Despawn Rate: " + this.itemDespawnRate);
/*     */   }
/*     */   
/*     */ 
/*     */   private void arrowDespawnRate()
/*     */   {
/* 215 */     this.arrowDespawnRate = getInt("arrow-despawn-rate", 1200);
/* 216 */     log("Arrow Despawn Rate: " + this.arrowDespawnRate);
/*     */   }
/*     */   
/*     */   public boolean zombieAggressiveTowardsVillager;
/*     */   public boolean nerfSpawnerMobs;
/*     */   public boolean enableZombiePigmenPortalSpawns;
/*     */   public int maxBulkChunk;
/*     */   public int maxCollisionsPerEntity;
/*     */   public int dragonDeathSoundRadius;
/*     */   
/* 226 */   private void antiXray() { this.antiXray = getBoolean("anti-xray.enabled", true);
/* 227 */     log("Anti X-Ray: " + this.antiXray);
/*     */     
/* 229 */     this.engineMode = getInt("anti-xray.engine-mode", 1);
/* 230 */     log("\tEngine Mode: " + this.engineMode);
/*     */     
/* 232 */     if (SpigotConfig.version < 5)
/*     */     {
/* 234 */       set("anti-xray.blocks", null);
/*     */     }
/* 236 */     this.hiddenBlocks = getList("anti-xray.hide-blocks", java.util.Arrays.asList(
/* 237 */       new Integer[] {
/* 238 */       Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(48), Integer.valueOf(49), Integer.valueOf(54), Integer.valueOf(56), Integer.valueOf(73), Integer.valueOf(74), Integer.valueOf(82), Integer.valueOf(129), Integer.valueOf(130) }));
/*     */     
/* 240 */     log("\tHidden Blocks: " + this.hiddenBlocks);
/*     */     
/* 242 */     this.replaceBlocks = getList("anti-xray.replace-blocks", java.util.Arrays.asList(
/* 243 */       new Integer[] {
/* 244 */       Integer.valueOf(1), Integer.valueOf(5) }));
/*     */     
/* 246 */     log("\tReplace Blocks: " + this.replaceBlocks);
/*     */     
/* 248 */     this.antiXrayInstance = new AntiXray(this);
/*     */   }
/*     */   
/*     */ 
/*     */   private void zombieAggressiveTowardsVillager()
/*     */   {
/* 254 */     this.zombieAggressiveTowardsVillager = getBoolean("zombie-aggressive-towards-villager", true);
/* 255 */     log("Zombie Aggressive Towards Villager: " + this.zombieAggressiveTowardsVillager);
/*     */   }
/*     */   
/*     */ 
/*     */   private void nerfSpawnerMobs()
/*     */   {
/* 261 */     this.nerfSpawnerMobs = getBoolean("nerf-spawner-mobs", false);
/* 262 */     log("Nerfing mobs spawned from spawners: " + this.nerfSpawnerMobs);
/*     */   }
/*     */   
/*     */ 
/*     */   private void enableZombiePigmenPortalSpawns()
/*     */   {
/* 268 */     this.enableZombiePigmenPortalSpawns = getBoolean("enable-zombie-pigmen-portal-spawns", true);
/* 269 */     log("Allow Zombie Pigmen to spawn from portal blocks: " + this.enableZombiePigmenPortalSpawns);
/*     */   }
/*     */   
/*     */ 
/*     */   private void bulkChunkCount()
/*     */   {
/* 275 */     this.maxBulkChunk = getInt("max-bulk-chunks", 10);
/* 276 */     log("Sending up to " + this.maxBulkChunk + " chunks per packet");
/*     */   }
/*     */   
/*     */ 
/*     */   private void maxEntityCollision()
/*     */   {
/* 282 */     this.maxCollisionsPerEntity = getInt("max-entity-collisions", 8);
/* 283 */     log("Max Entity Collisions: " + this.maxCollisionsPerEntity);
/*     */   }
/*     */   
/*     */ 
/*     */   private void keepDragonDeathPerWorld()
/*     */   {
/* 289 */     this.dragonDeathSoundRadius = getInt("dragon-death-sound-radius", 0);
/*     */   }
/*     */   
/*     */ 
/*     */   private void witherSpawnSoundRadius()
/*     */   {
/* 295 */     this.witherSpawnSoundRadius = getInt("wither-spawn-sound-radius", 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void initWorldGenSeeds()
/*     */   {
/* 302 */     this.villageSeed = getInt("seed-village", 10387312);
/* 303 */     this.largeFeatureSeed = getInt("seed-feature", 14357617);
/* 304 */     log("Custom Map Seeds:  Village: " + this.villageSeed + " Feature: " + this.largeFeatureSeed); }
/*     */   
/*     */   public int witherSpawnSoundRadius;
/*     */   public int villageSeed;
/*     */   public int largeFeatureSeed;
/*     */   public float walkExhaustion;
/*     */   public float sprintExhaustion;
/*     */   public float combatExhaustion;
/*     */   public float regenExhaustion;
/* 313 */   private void initHunger() { this.walkExhaustion = ((float)getDouble("hunger.walk-exhaustion", 0.2D));
/* 314 */     this.sprintExhaustion = ((float)getDouble("hunger.sprint-exhaustion", 0.8D));
/* 315 */     this.combatExhaustion = ((float)getDouble("hunger.combat-exhaustion", 0.3D));
/* 316 */     this.regenExhaustion = ((float)getDouble("hunger.regen-exhaustion", 3.0D));
/*     */   }
/*     */   
/* 319 */   public int currentPrimedTnt = 0;
/*     */   public int maxTntTicksPerTick;
/*     */   
/* 322 */   private void maxTntPerTick() { if (SpigotConfig.version < 7)
/*     */     {
/* 324 */       set("max-tnt-per-tick", Integer.valueOf(100));
/*     */     }
/* 326 */     this.maxTntTicksPerTick = getInt("max-tnt-per-tick", 100);
/* 327 */     log("Max TNT Explosions: " + this.maxTntTicksPerTick);
/*     */   }
/*     */   
/*     */   public int hangingTickFrequency;
/*     */   private void hangingTickFrequency()
/*     */   {
/* 333 */     this.hangingTickFrequency = getInt("hanging-tick-frequency", 100);
/*     */   }
/*     */   
/*     */   public int tileMaxTickTime;
/*     */   public int entityMaxTickTime;
/*     */   private void maxTickTimes()
/*     */   {
/* 340 */     this.tileMaxTickTime = getInt("max-tick-time.tile", 50);
/* 341 */     this.entityMaxTickTime = getInt("max-tick-time.entity", 50);
/* 342 */     log("Tile Max Tick Time: " + this.tileMaxTickTime + "ms Entity max Tick Time: " + this.entityMaxTickTime + "ms");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\SpigotWorldConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */