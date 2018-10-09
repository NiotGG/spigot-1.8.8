/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import gnu.trove.iterator.TLongShortIterator;
/*      */ import gnu.trove.map.hash.TLongShortHashMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.World.Environment;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.generator.InternalChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.generator.NetherChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.generator.NormalChunkGenerator;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.HashTreeSet;
/*      */ import org.bukkit.event.block.BlockFormEvent;
/*      */ import org.bukkit.event.weather.LightningStrikeEvent;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.SpigotWorldConfig;
/*      */ 
/*      */ public class WorldServer extends World implements IAsyncTaskHandler
/*      */ {
/*   34 */   private static final org.apache.logging.log4j.Logger a = ;
/*      */   
/*      */   private final MinecraftServer server;
/*      */   public EntityTracker tracker;
/*      */   private final PlayerChunkMap manager;
/*   39 */   private final HashTreeSet<NextTickListEntry> M = new HashTreeSet();
/*   40 */   private final Map<UUID, Entity> entitiesByUUID = com.google.common.collect.Maps.newHashMap();
/*      */   public ChunkProviderServer chunkProviderServer;
/*      */   public boolean savingDisabled;
/*      */   private boolean O;
/*      */   private int emptyTime;
/*      */   private final PortalTravelAgent Q;
/*   46 */   private final SpawnerCreature R = new SpawnerCreature();
/*   47 */   protected final VillageSiege siegeManager = new VillageSiege(this);
/*   48 */   private BlockActionDataList[] S = { new BlockActionDataList(null), new BlockActionDataList(null) };
/*      */   private int T;
/*   50 */   private static final List<StructurePieceTreasure> U = Lists.newArrayList(new StructurePieceTreasure[] { new StructurePieceTreasure(Items.STICK, 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.PLANKS), 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG), 0, 1, 3, 10), new StructurePieceTreasure(Items.STONE_AXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_AXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.STONE_PICKAXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.APPLE, 0, 2, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 2, 3, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG2), 0, 1, 3, 10) });
/*   51 */   private List<NextTickListEntry> V = Lists.newArrayList();
/*      */   
/*      */   public final int dimension;
/*      */   
/*      */ 
/*      */   public WorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, WorldData worlddata, int i, MethodProfiler methodprofiler, World.Environment env, ChunkGenerator gen)
/*      */   {
/*   58 */     super(idatamanager, worlddata, WorldProvider.byDimension(env.getId()), methodprofiler, false, gen, env);
/*   59 */     this.dimension = i;
/*   60 */     this.pvpMode = minecraftserver.getPVP();
/*   61 */     worlddata.world = this;
/*      */     
/*   63 */     this.server = minecraftserver;
/*   64 */     this.tracker = new EntityTracker(this);
/*   65 */     this.manager = new PlayerChunkMap(this, this.spigotConfig.viewDistance);
/*   66 */     this.worldProvider.a(this);
/*   67 */     this.chunkProvider = k();
/*   68 */     this.Q = new org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent(this);
/*   69 */     B();
/*   70 */     C();
/*   71 */     getWorldBorder().a(minecraftserver.aI());
/*      */   }
/*      */   
/*      */   public World b() {
/*   75 */     this.worldMaps = new PersistentCollection(this.dataManager);
/*   76 */     String s = PersistentVillage.a(this.worldProvider);
/*   77 */     PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, s);
/*      */     
/*   79 */     if (persistentvillage == null) {
/*   80 */       this.villages = new PersistentVillage(this);
/*   81 */       this.worldMaps.a(s, this.villages);
/*      */     } else {
/*   83 */       this.villages = persistentvillage;
/*   84 */       this.villages.a(this);
/*      */     }
/*      */     
/*   87 */     if (getServer().getScoreboardManager() == null) {
/*   88 */       this.scoreboard = new ScoreboardServer(this.server);
/*   89 */       PersistentScoreboard persistentscoreboard = (PersistentScoreboard)this.worldMaps.get(PersistentScoreboard.class, "scoreboard");
/*      */       
/*   91 */       if (persistentscoreboard == null) {
/*   92 */         persistentscoreboard = new PersistentScoreboard();
/*   93 */         this.worldMaps.a("scoreboard", persistentscoreboard);
/*      */       }
/*      */       
/*   96 */       persistentscoreboard.a(this.scoreboard);
/*   97 */       ((ScoreboardServer)this.scoreboard).a(persistentscoreboard);
/*      */     }
/*      */     else {
/*  100 */       this.scoreboard = getServer().getScoreboardManager().getMainScoreboard().getHandle();
/*      */     }
/*      */     
/*  103 */     getWorldBorder().setCenter(this.worldData.C(), this.worldData.D());
/*  104 */     getWorldBorder().setDamageAmount(this.worldData.I());
/*  105 */     getWorldBorder().setDamageBuffer(this.worldData.H());
/*  106 */     getWorldBorder().setWarningDistance(this.worldData.J());
/*  107 */     getWorldBorder().setWarningTime(this.worldData.K());
/*  108 */     if (this.worldData.F() > 0L) {
/*  109 */       getWorldBorder().transitionSizeBetween(this.worldData.E(), this.worldData.G(), this.worldData.F());
/*      */     } else {
/*  111 */       getWorldBorder().setSize(this.worldData.E());
/*      */     }
/*      */     
/*      */ 
/*  115 */     if (this.generator != null) {
/*  116 */       getWorld().getPopulators().addAll(this.generator.getDefaultPopulators(getWorld()));
/*      */     }
/*      */     
/*      */ 
/*  120 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */   public TileEntity getTileEntity(BlockPosition pos)
/*      */   {
/*  126 */     TileEntity result = super.getTileEntity(pos);
/*  127 */     Block type = getType(pos).getBlock();
/*      */     
/*  129 */     if ((type == Blocks.CHEST) || (type == Blocks.TRAPPED_CHEST)) {
/*  130 */       if (!(result instanceof TileEntityChest)) {
/*  131 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  133 */     } else if (type == Blocks.FURNACE) {
/*  134 */       if (!(result instanceof TileEntityFurnace)) {
/*  135 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  137 */     } else if (type == Blocks.DROPPER) {
/*  138 */       if (!(result instanceof TileEntityDropper)) {
/*  139 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  141 */     } else if (type == Blocks.DISPENSER) {
/*  142 */       if (!(result instanceof TileEntityDispenser)) {
/*  143 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  145 */     } else if (type == Blocks.JUKEBOX) {
/*  146 */       if (!(result instanceof BlockJukeBox.TileEntityRecordPlayer)) {
/*  147 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  149 */     } else if (type == Blocks.NOTEBLOCK) {
/*  150 */       if (!(result instanceof TileEntityNote)) {
/*  151 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  153 */     } else if (type == Blocks.MOB_SPAWNER) {
/*  154 */       if (!(result instanceof TileEntityMobSpawner)) {
/*  155 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  157 */     } else if ((type == Blocks.STANDING_SIGN) || (type == Blocks.WALL_SIGN)) {
/*  158 */       if (!(result instanceof TileEntitySign)) {
/*  159 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  161 */     } else if (type == Blocks.ENDER_CHEST) {
/*  162 */       if (!(result instanceof TileEntityEnderChest)) {
/*  163 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  165 */     } else if (type == Blocks.BREWING_STAND) {
/*  166 */       if (!(result instanceof TileEntityBrewingStand)) {
/*  167 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  169 */     } else if (type == Blocks.BEACON) {
/*  170 */       if (!(result instanceof TileEntityBeacon)) {
/*  171 */         result = fixTileEntity(pos, type, result);
/*      */       }
/*  173 */     } else if ((type == Blocks.HOPPER) && 
/*  174 */       (!(result instanceof TileEntityHopper))) {
/*  175 */       result = fixTileEntity(pos, type, result);
/*      */     }
/*      */     
/*      */ 
/*  179 */     return result;
/*      */   }
/*      */   
/*      */   private TileEntity fixTileEntity(BlockPosition pos, Block type, TileEntity found) {
/*  183 */     getServer().getLogger().log(java.util.logging.Level.SEVERE, "Block at {0},{1},{2} is {3} but has {4}. Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.", 
/*  184 */       new Object[] { Integer.valueOf(pos.getX()), Integer.valueOf(pos.getY()), Integer.valueOf(pos.getZ()), org.bukkit.Material.getMaterial(Block.getId(type)).toString(), found });
/*      */     
/*  186 */     if ((type instanceof IContainer)) {
/*  187 */       TileEntity replacement = ((IContainer)type).a(this, type.toLegacyData(getType(pos)));
/*  188 */       replacement.world = this;
/*  189 */       setTileEntity(pos, replacement);
/*  190 */       return replacement;
/*      */     }
/*  192 */     getServer().getLogger().severe("Don't know how to fix for this type... Can't do anything! :(");
/*  193 */     return found;
/*      */   }
/*      */   
/*      */   private boolean canSpawn(int x, int z)
/*      */   {
/*  198 */     if (this.generator != null) {
/*  199 */       return this.generator.canSpawn(getWorld(), x, z);
/*      */     }
/*  201 */     return this.worldProvider.canSpawn(x, z);
/*      */   }
/*      */   
/*      */ 
/*      */   public void doTick()
/*      */   {
/*  207 */     super.doTick();
/*  208 */     if ((getWorldData().isHardcore()) && (getDifficulty() != EnumDifficulty.HARD)) {
/*  209 */       getWorldData().setDifficulty(EnumDifficulty.HARD);
/*      */     }
/*      */     
/*  212 */     this.worldProvider.m().b();
/*  213 */     if (everyoneDeeplySleeping()) {
/*  214 */       if (getGameRules().getBoolean("doDaylightCycle")) {
/*  215 */         long i = this.worldData.getDayTime() + 24000L;
/*      */         
/*  217 */         this.worldData.setDayTime(i - i % 24000L);
/*      */       }
/*      */       
/*  220 */       e();
/*      */     }
/*      */     
/*      */ 
/*  224 */     long time = this.worldData.getTime();
/*  225 */     if ((getGameRules().getBoolean("doMobSpawning")) && (this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES) && ((this.allowMonsters) || (this.allowAnimals)) && ((this instanceof WorldServer)) && (this.players.size() > 0)) {
/*  226 */       this.timings.mobSpawn.startTiming();
/*  227 */       this.R.a(this, (this.allowMonsters) && (this.ticksPerMonsterSpawns != 0L) && (time % this.ticksPerMonsterSpawns == 0L), (this.allowAnimals) && (this.ticksPerAnimalSpawns != 0L) && (time % this.ticksPerAnimalSpawns == 0L), this.worldData.getTime() % 400L == 0L);
/*  228 */       this.timings.mobSpawn.stopTiming();
/*      */     }
/*      */     
/*      */ 
/*  232 */     this.timings.doChunkUnload.startTiming();
/*  233 */     this.methodProfiler.c("chunkSource");
/*  234 */     this.chunkProvider.unloadChunks();
/*  235 */     int j = a(1.0F);
/*      */     
/*  237 */     if (j != ab()) {
/*  238 */       c(j);
/*      */     }
/*      */     
/*  241 */     this.worldData.setTime(this.worldData.getTime() + 1L);
/*  242 */     if (getGameRules().getBoolean("doDaylightCycle")) {
/*  243 */       this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
/*      */     }
/*      */     
/*  246 */     this.timings.doChunkUnload.stopTiming();
/*  247 */     this.methodProfiler.c("tickPending");
/*  248 */     this.timings.doTickPending.startTiming();
/*  249 */     a(false);
/*  250 */     this.timings.doTickPending.stopTiming();
/*  251 */     this.methodProfiler.c("tickBlocks");
/*  252 */     this.timings.doTickTiles.startTiming();
/*  253 */     h();
/*  254 */     this.timings.doTickTiles.stopTiming();
/*  255 */     this.methodProfiler.c("chunkMap");
/*  256 */     this.timings.doChunkMap.startTiming();
/*  257 */     this.manager.flush();
/*  258 */     this.timings.doChunkMap.stopTiming();
/*  259 */     this.methodProfiler.c("village");
/*  260 */     this.timings.doVillages.startTiming();
/*  261 */     this.villages.tick();
/*  262 */     this.siegeManager.a();
/*  263 */     this.timings.doVillages.stopTiming();
/*  264 */     this.methodProfiler.c("portalForcer");
/*  265 */     this.timings.doPortalForcer.startTiming();
/*  266 */     this.Q.a(getTime());
/*  267 */     this.timings.doPortalForcer.stopTiming();
/*  268 */     this.methodProfiler.b();
/*  269 */     this.timings.doSounds.startTiming();
/*  270 */     ak();
/*      */     
/*  272 */     getWorld().processChunkGC();
/*  273 */     this.timings.doChunkGC.stopTiming();
/*      */   }
/*      */   
/*      */   public BiomeBase.BiomeMeta a(EnumCreatureType enumcreaturetype, BlockPosition blockposition) {
/*  277 */     List list = N().getMobsFor(enumcreaturetype, blockposition);
/*      */     
/*  279 */     return (list != null) && (!list.isEmpty()) ? (BiomeBase.BiomeMeta)WeightedRandom.a(this.random, list) : null;
/*      */   }
/*      */   
/*      */   public boolean a(EnumCreatureType enumcreaturetype, BiomeBase.BiomeMeta biomebase_biomemeta, BlockPosition blockposition) {
/*  283 */     List list = N().getMobsFor(enumcreaturetype, blockposition);
/*      */     
/*  285 */     return (list != null) && (!list.isEmpty()) ? list.contains(biomebase_biomemeta) : false;
/*      */   }
/*      */   
/*      */   public void everyoneSleeping() {
/*  289 */     this.O = false;
/*  290 */     if (!this.players.isEmpty()) {
/*  291 */       int i = 0;
/*  292 */       int j = 0;
/*  293 */       Iterator iterator = this.players.iterator();
/*      */       
/*  295 */       while (iterator.hasNext()) {
/*  296 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*      */         
/*  298 */         if (entityhuman.isSpectator()) {
/*  299 */           i++;
/*  300 */         } else if ((entityhuman.isSleeping()) || (entityhuman.fauxSleeping)) {
/*  301 */           j++;
/*      */         }
/*      */       }
/*      */       
/*  305 */       this.O = ((j > 0) && (j >= this.players.size() - i));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void e()
/*      */   {
/*  311 */     this.O = false;
/*  312 */     Iterator iterator = this.players.iterator();
/*      */     
/*  314 */     while (iterator.hasNext()) {
/*  315 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*      */       
/*  317 */       if (entityhuman.isSleeping()) {
/*  318 */         entityhuman.a(false, false, true);
/*      */       }
/*      */     }
/*      */     
/*  322 */     ag();
/*      */   }
/*      */   
/*      */   private void ag() {
/*  326 */     this.worldData.setStorm(false);
/*      */     
/*      */ 
/*      */ 
/*  330 */     if (!this.worldData.hasStorm()) {
/*  331 */       this.worldData.setWeatherDuration(0);
/*      */     }
/*      */     
/*  334 */     this.worldData.setThundering(false);
/*      */     
/*      */ 
/*      */ 
/*  338 */     if (!this.worldData.isThundering()) {
/*  339 */       this.worldData.setThunderDuration(0);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean everyoneDeeplySleeping()
/*      */   {
/*  345 */     if ((this.O) && (!this.isClientSide)) {
/*  346 */       Iterator iterator = this.players.iterator();
/*      */       
/*      */ 
/*  349 */       boolean foundActualSleepers = false;
/*      */       
/*      */       EntityHuman entityhuman;
/*      */       do
/*      */       {
/*  354 */         if (!iterator.hasNext()) {
/*  355 */           return foundActualSleepers;
/*      */         }
/*      */         
/*  358 */         entityhuman = (EntityHuman)iterator.next();
/*      */         
/*      */ 
/*  361 */         if (entityhuman.isDeeplySleeping()) {
/*  362 */           foundActualSleepers = true;
/*      */         }
/*  364 */       } while ((!entityhuman.isSpectator()) || (entityhuman.isDeeplySleeping()) || (entityhuman.fauxSleeping));
/*      */       
/*      */ 
/*  367 */       return false;
/*      */     }
/*  369 */     return false;
/*      */   }
/*      */   
/*      */   protected void h()
/*      */   {
/*  374 */     super.h();
/*  375 */     if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)
/*      */     {
/*  377 */       TLongShortIterator iterator = this.chunkTickList.iterator();
/*      */       
/*  379 */       while (iterator.hasNext()) {
/*  380 */         iterator.advance();
/*  381 */         long chunkCoord = iterator.key();
/*      */         
/*  383 */         getChunkAt(World.keyToX(chunkCoord), World.keyToZ(chunkCoord)).b(false);
/*      */ 
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*  397 */       for (TLongShortIterator iter = this.chunkTickList.iterator(); iter.hasNext();)
/*      */       {
/*  399 */         iter.advance();
/*  400 */         long chunkCoord = iter.key();
/*  401 */         int chunkX = World.keyToX(chunkCoord);
/*  402 */         int chunkZ = World.keyToZ(chunkCoord);
/*      */         
/*  404 */         if ((!this.chunkProvider.isChunkLoaded(chunkX, chunkZ)) || (this.chunkProviderServer.unloadQueue.contains(chunkX, chunkZ)))
/*      */         {
/*  406 */           iter.remove();
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  411 */           int k = chunkX * 16;
/*  412 */           int l = chunkZ * 16;
/*      */           
/*  414 */           this.methodProfiler.a("getChunk");
/*  415 */           Chunk chunk = getChunkAt(chunkX, chunkZ);
/*      */           
/*      */ 
/*  418 */           a(k, l, chunk);
/*  419 */           this.methodProfiler.c("tickChunk");
/*  420 */           chunk.b(false);
/*  421 */           this.methodProfiler.c("thunder");
/*      */           
/*      */ 
/*      */ 
/*  425 */           if ((this.random.nextInt(100000) == 0) && (S()) && (R())) {
/*  426 */             this.m = (this.m * 3 + 1013904223);
/*  427 */             int i1 = this.m >> 2;
/*  428 */             BlockPosition blockposition = a(new BlockPosition(k + (i1 & 0xF), 0, l + (i1 >> 8 & 0xF)));
/*  429 */             if (isRainingAt(blockposition)) {
/*  430 */               strikeLightning(new EntityLightning(this, blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/*      */             }
/*      */           }
/*      */           
/*  434 */           this.methodProfiler.c("iceandsnow");
/*  435 */           if (this.random.nextInt(16) == 0) {
/*  436 */             this.m = (this.m * 3 + 1013904223);
/*  437 */             int i1 = this.m >> 2;
/*  438 */             BlockPosition blockposition = q(new BlockPosition(k + (i1 & 0xF), 0, l + (i1 >> 8 & 0xF)));
/*  439 */             BlockPosition blockposition1 = blockposition.down();
/*      */             
/*  441 */             if (w(blockposition1))
/*      */             {
/*  443 */               BlockState blockState = getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
/*  444 */               blockState.setTypeId(Block.getId(Blocks.ICE));
/*      */               
/*  446 */               BlockFormEvent iceBlockForm = new BlockFormEvent(blockState.getBlock(), blockState);
/*  447 */               getServer().getPluginManager().callEvent(iceBlockForm);
/*  448 */               if (!iceBlockForm.isCancelled()) {
/*  449 */                 blockState.update(true);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  454 */             if ((S()) && (f(blockposition, true)))
/*      */             {
/*  456 */               BlockState blockState = getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
/*  457 */               blockState.setTypeId(Block.getId(Blocks.SNOW_LAYER));
/*      */               
/*  459 */               BlockFormEvent snow = new BlockFormEvent(blockState.getBlock(), blockState);
/*  460 */               getServer().getPluginManager().callEvent(snow);
/*  461 */               if (!snow.isCancelled()) {
/*  462 */                 blockState.update(true);
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  467 */             if ((S()) && (getBiome(blockposition1).e())) {
/*  468 */               getType(blockposition1).getBlock().k(this, blockposition1);
/*      */             }
/*      */           }
/*      */           
/*  472 */           this.methodProfiler.c("tickBlocks");
/*  473 */           int i1 = getGameRules().c("randomTickSpeed");
/*  474 */           if (i1 > 0) {
/*  475 */             ChunkSection[] achunksection = chunk.getSections();
/*  476 */             int j1 = achunksection.length;
/*      */             
/*  478 */             for (int k1 = 0; k1 < j1; k1++) {
/*  479 */               ChunkSection chunksection = achunksection[k1];
/*      */               
/*  481 */               if ((chunksection != null) && (chunksection.shouldTick())) {
/*  482 */                 for (int l1 = 0; l1 < i1; l1++) {
/*  483 */                   this.m = (this.m * 3 + 1013904223);
/*  484 */                   int i2 = this.m >> 2;
/*  485 */                   int j2 = i2 & 0xF;
/*  486 */                   int k2 = i2 >> 8 & 0xF;
/*  487 */                   int l2 = i2 >> 16 & 0xF;
/*      */                   
/*      */ 
/*  490 */                   IBlockData iblockdata = chunksection.getType(j2, l2, k2);
/*  491 */                   Block block = iblockdata.getBlock();
/*      */                   
/*  493 */                   if (block.isTicking())
/*      */                   {
/*  495 */                     block.a(this, new BlockPosition(j2 + k, l2 + chunksection.getYPosition(), k2 + l), iblockdata, this.random);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  505 */     if (this.spigotConfig.clearChunksOnTick)
/*      */     {
/*  507 */       this.chunkTickList.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   protected BlockPosition a(BlockPosition blockposition)
/*      */   {
/*  513 */     BlockPosition blockposition1 = q(blockposition);
/*  514 */     AxisAlignedBB axisalignedbb = new AxisAlignedBB(blockposition1, new BlockPosition(blockposition1.getX(), getHeight(), blockposition1.getZ())).grow(3.0D, 3.0D, 3.0D);
/*  515 */     List list = a(EntityLiving.class, axisalignedbb, new com.google.common.base.Predicate() {
/*      */       public boolean a(EntityLiving entityliving) {
/*  517 */         return (entityliving != null) && (entityliving.isAlive()) && (WorldServer.this.i(entityliving.getChunkCoordinates()));
/*      */       }
/*      */       
/*      */       public boolean apply(Object object) {
/*  521 */         return a((EntityLiving)object);
/*      */       }
/*      */       
/*  524 */     });
/*  525 */     return !list.isEmpty() ? ((EntityLiving)list.get(this.random.nextInt(list.size()))).getChunkCoordinates() : blockposition1;
/*      */   }
/*      */   
/*      */   public boolean a(BlockPosition blockposition, Block block) {
/*  529 */     NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);
/*      */     
/*  531 */     return this.V.contains(nextticklistentry);
/*      */   }
/*      */   
/*      */   public void a(BlockPosition blockposition, Block block, int i) {
/*  535 */     a(blockposition, block, i, 0);
/*      */   }
/*      */   
/*      */   public void a(BlockPosition blockposition, Block block, int i, int j) {
/*  539 */     NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);
/*  540 */     byte b0 = 0;
/*      */     
/*  542 */     if ((this.e) && (block.getMaterial() != Material.AIR)) {
/*  543 */       if (block.N()) {
/*  544 */         b0 = 8;
/*  545 */         if (areChunksLoadedBetween(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
/*  546 */           IBlockData iblockdata = getType(nextticklistentry.a);
/*      */           
/*  548 */           if ((iblockdata.getBlock().getMaterial() != Material.AIR) && (iblockdata.getBlock() == nextticklistentry.a())) {
/*  549 */             iblockdata.getBlock().b(this, nextticklistentry.a, iblockdata, this.random);
/*      */           }
/*      */         }
/*      */         
/*  553 */         return;
/*      */       }
/*      */       
/*  556 */       i = 1;
/*      */     }
/*      */     
/*  559 */     if (areChunksLoadedBetween(blockposition.a(-b0, -b0, -b0), blockposition.a(b0, b0, b0))) {
/*  560 */       if (block.getMaterial() != Material.AIR) {
/*  561 */         nextticklistentry.a(i + this.worldData.getTime());
/*  562 */         nextticklistentry.a(j);
/*      */       }
/*      */       
/*      */ 
/*  566 */       if (!this.M.contains(nextticklistentry)) {
/*  567 */         this.M.add(nextticklistentry);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(BlockPosition blockposition, Block block, int i, int j)
/*      */   {
/*  574 */     NextTickListEntry nextticklistentry = new NextTickListEntry(blockposition, block);
/*      */     
/*  576 */     nextticklistentry.a(j);
/*  577 */     if (block.getMaterial() != Material.AIR) {
/*  578 */       nextticklistentry.a(i + this.worldData.getTime());
/*      */     }
/*      */     
/*      */ 
/*  582 */     if (!this.M.contains(nextticklistentry)) {
/*  583 */       this.M.add(nextticklistentry);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void tickEntities()
/*      */   {
/*  594 */     j();
/*      */     
/*      */ 
/*  597 */     super.tickEntities();
/*  598 */     this.spigotConfig.currentPrimedTnt = 0;
/*      */   }
/*      */   
/*      */   public void j() {
/*  602 */     this.emptyTime = 0;
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag) {
/*  606 */     if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
/*  607 */       return false;
/*      */     }
/*  609 */     int i = this.M.size();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  614 */     if (i > 1000)
/*      */     {
/*  616 */       if (i > 20000) {
/*  617 */         i /= 20;
/*      */       } else {
/*  619 */         i = 1000;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  624 */     this.methodProfiler.a("cleaning");
/*      */     
/*      */ 
/*      */ 
/*  628 */     for (int j = 0; j < i; j++) {
/*  629 */       NextTickListEntry nextticklistentry = (NextTickListEntry)this.M.first();
/*  630 */       if ((!flag) && (nextticklistentry.b > this.worldData.getTime())) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*  635 */       this.M.remove(nextticklistentry);
/*  636 */       this.V.add(nextticklistentry);
/*      */     }
/*      */     
/*  639 */     this.methodProfiler.b();
/*  640 */     this.methodProfiler.a("ticking");
/*  641 */     Iterator iterator = this.V.iterator();
/*      */     
/*  643 */     while (iterator.hasNext()) {
/*  644 */       NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
/*  645 */       iterator.remove();
/*  646 */       byte b0 = 0;
/*      */       
/*  648 */       if (areChunksLoadedBetween(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
/*  649 */         IBlockData iblockdata = getType(nextticklistentry.a);
/*      */         
/*  651 */         if ((iblockdata.getBlock().getMaterial() != Material.AIR) && (Block.a(iblockdata.getBlock(), nextticklistentry.a()))) {
/*      */           try {
/*  653 */             iblockdata.getBlock().b(this, nextticklistentry.a, iblockdata, this.random);
/*      */           } catch (Throwable throwable) {
/*  655 */             CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
/*  656 */             CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
/*      */             
/*  658 */             CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry.a, iblockdata);
/*  659 */             throw new ReportedException(crashreport);
/*      */           }
/*      */         }
/*      */       } else {
/*  663 */         a(nextticklistentry.a, nextticklistentry.a(), 0);
/*      */       }
/*      */     }
/*      */     
/*  667 */     this.methodProfiler.b();
/*  668 */     this.V.clear();
/*  669 */     return !this.M.isEmpty();
/*      */   }
/*      */   
/*      */ 
/*      */   public List<NextTickListEntry> a(Chunk chunk, boolean flag)
/*      */   {
/*  675 */     ChunkCoordIntPair chunkcoordintpair = chunk.j();
/*  676 */     int i = (chunkcoordintpair.x << 4) - 2;
/*  677 */     int j = i + 16 + 2;
/*  678 */     int k = (chunkcoordintpair.z << 4) - 2;
/*  679 */     int l = k + 16 + 2;
/*      */     
/*  681 */     return a(new StructureBoundingBox(i, 0, k, j, 256, l), flag);
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> a(StructureBoundingBox structureboundingbox, boolean flag) {
/*  685 */     ArrayList arraylist = null;
/*      */     
/*  687 */     for (int i = 0; i < 2; i++) {
/*      */       Iterator iterator;
/*      */       Iterator iterator;
/*  690 */       if (i == 0) {
/*  691 */         iterator = this.M.iterator();
/*      */       } else {
/*  693 */         iterator = this.V.iterator();
/*      */       }
/*      */       
/*  696 */       while (iterator.hasNext()) {
/*  697 */         NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
/*  698 */         BlockPosition blockposition = nextticklistentry.a;
/*      */         
/*  700 */         if ((blockposition.getX() >= structureboundingbox.a) && (blockposition.getX() < structureboundingbox.d) && (blockposition.getZ() >= structureboundingbox.c) && (blockposition.getZ() < structureboundingbox.f)) {
/*  701 */           if (flag)
/*      */           {
/*  703 */             iterator.remove();
/*      */           }
/*      */           
/*  706 */           if (arraylist == null) {
/*  707 */             arraylist = Lists.newArrayList();
/*      */           }
/*      */           
/*  710 */           arraylist.add(nextticklistentry);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  715 */     return arraylist;
/*      */   }
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
/*      */   private boolean getSpawnNPCs()
/*      */   {
/*  733 */     return this.server.getSpawnNPCs();
/*      */   }
/*      */   
/*      */   private boolean getSpawnAnimals() {
/*  737 */     return this.server.getSpawnAnimals();
/*      */   }
/*      */   
/*      */   protected IChunkProvider k() {
/*  741 */     IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
/*      */     
/*      */     InternalChunkGenerator gen;
/*      */     
/*      */     InternalChunkGenerator gen;
/*  746 */     if (this.generator != null) {
/*  747 */       gen = new org.bukkit.craftbukkit.v1_8_R3.generator.CustomChunkGenerator(this, getSeed(), this.generator); } else { InternalChunkGenerator gen;
/*  748 */       if ((this.worldProvider instanceof WorldProviderHell)) {
/*  749 */         gen = new NetherChunkGenerator(this, getSeed()); } else { InternalChunkGenerator gen;
/*  750 */         if ((this.worldProvider instanceof WorldProviderTheEnd)) {
/*  751 */           gen = new org.bukkit.craftbukkit.v1_8_R3.generator.SkyLandsChunkGenerator(this, getSeed());
/*      */         } else
/*  753 */           gen = new NormalChunkGenerator(this, getSeed());
/*      */       }
/*      */     }
/*  756 */     this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, gen);
/*      */     
/*  758 */     return this.chunkProviderServer;
/*      */   }
/*      */   
/*      */   public List<TileEntity> getTileEntities(int i, int j, int k, int l, int i1, int j1) {
/*  762 */     ArrayList arraylist = Lists.newArrayList();
/*      */     
/*      */ 
/*  765 */     for (int chunkX = i >> 4; chunkX <= l - 1 >> 4; chunkX++) {
/*  766 */       for (int chunkZ = k >> 4; chunkZ <= j1 - 1 >> 4; chunkZ++) {
/*  767 */         Chunk chunk = getChunkAt(chunkX, chunkZ);
/*  768 */         if (chunk != null)
/*      */         {
/*      */ 
/*  771 */           for (Object te : chunk.tileEntities.values()) {
/*  772 */             TileEntity tileentity = (TileEntity)te;
/*  773 */             if ((tileentity.position.getX() >= i) && (tileentity.position.getY() >= j) && (tileentity.position.getZ() >= k) && (tileentity.position.getX() < l) && (tileentity.position.getY() < i1) && (tileentity.position.getZ() < j1)) {
/*  774 */               arraylist.add(tileentity);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/*  791 */     return arraylist;
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman, BlockPosition blockposition) {
/*  795 */     return (!this.server.a(this, blockposition, entityhuman)) && (getWorldBorder().a(blockposition));
/*      */   }
/*      */   
/*      */   public void a(WorldSettings worldsettings) {
/*  799 */     if (!this.worldData.w()) {
/*      */       try {
/*  801 */         b(worldsettings);
/*  802 */         if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
/*  803 */           aj();
/*      */         }
/*      */         
/*  806 */         super.a(worldsettings);
/*      */       } catch (Throwable throwable) {
/*  808 */         CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");
/*      */         try
/*      */         {
/*  811 */           a(crashreport);
/*      */         }
/*      */         catch (Throwable localThrowable1) {}
/*      */         
/*      */ 
/*  816 */         throw new ReportedException(crashreport);
/*      */       }
/*      */       
/*  819 */       this.worldData.d(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private void aj()
/*      */   {
/*  825 */     this.worldData.f(false);
/*  826 */     this.worldData.c(true);
/*  827 */     this.worldData.setStorm(false);
/*  828 */     this.worldData.setThundering(false);
/*  829 */     this.worldData.i(1000000000);
/*  830 */     this.worldData.setDayTime(6000L);
/*  831 */     this.worldData.setGameType(WorldSettings.EnumGamemode.SPECTATOR);
/*  832 */     this.worldData.g(false);
/*  833 */     this.worldData.setDifficulty(EnumDifficulty.PEACEFUL);
/*  834 */     this.worldData.e(true);
/*  835 */     getGameRules().set("doDaylightCycle", "false");
/*      */   }
/*      */   
/*      */   private void b(WorldSettings worldsettings) {
/*  839 */     if (!this.worldProvider.e()) {
/*  840 */       this.worldData.setSpawn(BlockPosition.ZERO.up(this.worldProvider.getSeaLevel()));
/*  841 */     } else if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
/*  842 */       this.worldData.setSpawn(BlockPosition.ZERO.up());
/*      */     } else {
/*  844 */       this.isLoading = true;
/*  845 */       WorldChunkManager worldchunkmanager = this.worldProvider.m();
/*  846 */       List list = worldchunkmanager.a();
/*  847 */       Random random = new Random(getSeed());
/*  848 */       BlockPosition blockposition = worldchunkmanager.a(0, 0, 256, list, random);
/*  849 */       int i = 0;
/*  850 */       int j = this.worldProvider.getSeaLevel();
/*  851 */       int k = 0;
/*      */       
/*      */ 
/*  854 */       if (this.generator != null) {
/*  855 */         Random rand = new Random(getSeed());
/*  856 */         Location spawn = this.generator.getFixedSpawnLocation(getWorld(), rand);
/*      */         
/*  858 */         if (spawn != null) {
/*  859 */           if (spawn.getWorld() != getWorld()) {
/*  860 */             throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + spawn.getWorld().getName() + ")");
/*      */           }
/*  862 */           this.worldData.setSpawn(new BlockPosition(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ()));
/*  863 */           this.isLoading = false;
/*  864 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  870 */       if (blockposition != null) {
/*  871 */         i = blockposition.getX();
/*  872 */         k = blockposition.getZ();
/*      */       } else {
/*  874 */         a.warn("Unable to find spawn biome");
/*      */       }
/*      */       
/*  877 */       int l = 0;
/*      */       
/*  879 */       while (!canSpawn(i, k)) {
/*  880 */         i += random.nextInt(64) - random.nextInt(64);
/*  881 */         k += random.nextInt(64) - random.nextInt(64);
/*  882 */         l++;
/*  883 */         if (l == 1000) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/*  888 */       this.worldData.setSpawn(new BlockPosition(i, j, k));
/*  889 */       this.isLoading = false;
/*  890 */       if (worldsettings.c()) {
/*  891 */         l();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void l()
/*      */   {
/*  898 */     WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(U, 10);
/*      */     
/*  900 */     for (int i = 0; i < 10; i++) {
/*  901 */       int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
/*  902 */       int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
/*  903 */       BlockPosition blockposition = r(new BlockPosition(j, 0, k)).up();
/*      */       
/*  905 */       if (worldgenbonuschest.generate(this, this.random, blockposition)) {
/*      */         break;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public BlockPosition getDimensionSpawn()
/*      */   {
/*  913 */     return this.worldProvider.h();
/*      */   }
/*      */   
/*      */   public void save(boolean flag, IProgressUpdate iprogressupdate) throws ExceptionWorldConflict {
/*  917 */     if (this.chunkProvider.canSave()) {
/*  918 */       org.bukkit.Bukkit.getPluginManager().callEvent(new org.bukkit.event.world.WorldSaveEvent(getWorld()));
/*  919 */       if (iprogressupdate != null) {
/*  920 */         iprogressupdate.a("Saving level");
/*      */       }
/*      */       
/*  923 */       a();
/*  924 */       if (iprogressupdate != null) {
/*  925 */         iprogressupdate.c("Saving chunks");
/*      */       }
/*      */       
/*  928 */       this.chunkProvider.saveChunks(flag, iprogressupdate);
/*      */       
/*  930 */       Collection arraylist = this.chunkProviderServer.a();
/*  931 */       Iterator iterator = arraylist.iterator();
/*      */       
/*  933 */       while (iterator.hasNext()) {
/*  934 */         Chunk chunk = (Chunk)iterator.next();
/*      */         
/*  936 */         if ((chunk != null) && (!this.manager.a(chunk.locX, chunk.locZ))) {
/*  937 */           this.chunkProviderServer.queueUnload(chunk.locX, chunk.locZ);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void flushSave()
/*      */   {
/*  945 */     if (this.chunkProvider.canSave()) {
/*  946 */       this.chunkProvider.c();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a() throws ExceptionWorldConflict {
/*  951 */     checkSession();
/*  952 */     this.worldData.a(getWorldBorder().getSize());
/*  953 */     this.worldData.d(getWorldBorder().getCenterX());
/*  954 */     this.worldData.c(getWorldBorder().getCenterZ());
/*  955 */     this.worldData.e(getWorldBorder().getDamageBuffer());
/*  956 */     this.worldData.f(getWorldBorder().getDamageAmount());
/*  957 */     this.worldData.j(getWorldBorder().getWarningDistance());
/*  958 */     this.worldData.k(getWorldBorder().getWarningTime());
/*  959 */     this.worldData.b(getWorldBorder().j());
/*  960 */     this.worldData.e(getWorldBorder().i());
/*      */     
/*  962 */     if (!(this instanceof SecondaryWorldServer)) {
/*  963 */       this.worldMaps.a();
/*      */     }
/*  965 */     this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().t());
/*      */   }
/*      */   
/*      */   protected void a(Entity entity)
/*      */   {
/*  970 */     super.a(entity);
/*  971 */     this.entitiesById.a(entity.getId(), entity);
/*  972 */     this.entitiesByUUID.put(entity.getUniqueID(), entity);
/*  973 */     Entity[] aentity = entity.aB();
/*      */     
/*  975 */     if (aentity != null) {
/*  976 */       for (int i = 0; i < aentity.length; i++) {
/*  977 */         this.entitiesById.a(aentity[i].getId(), aentity[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void b(Entity entity)
/*      */   {
/*  984 */     super.b(entity);
/*  985 */     this.entitiesById.d(entity.getId());
/*  986 */     this.entitiesByUUID.remove(entity.getUniqueID());
/*  987 */     Entity[] aentity = entity.aB();
/*      */     
/*  989 */     if (aentity != null) {
/*  990 */       for (int i = 0; i < aentity.length; i++) {
/*  991 */         this.entitiesById.d(aentity[i].getId());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean strikeLightning(Entity entity)
/*      */   {
/*  999 */     LightningStrikeEvent lightning = new LightningStrikeEvent(getWorld(), (org.bukkit.entity.LightningStrike)entity.getBukkitEntity());
/* 1000 */     getServer().getPluginManager().callEvent(lightning);
/*      */     
/* 1002 */     if (lightning.isCancelled()) {
/* 1003 */       return false;
/*      */     }
/* 1005 */     if (super.strikeLightning(entity)) {
/* 1006 */       this.server.getPlayerList().sendPacketNearby(entity.locX, entity.locY, entity.locZ, 512.0D, this.dimension, new PacketPlayOutSpawnEntityWeather(entity));
/*      */       
/* 1008 */       return true;
/*      */     }
/* 1010 */     return false;
/*      */   }
/*      */   
/*      */   public void broadcastEntityEffect(Entity entity, byte b0)
/*      */   {
/* 1015 */     getTracker().sendPacketToEntity(entity, new PacketPlayOutEntityStatus(entity, b0));
/*      */   }
/*      */   
/*      */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1)
/*      */   {
/* 1020 */     Explosion explosion = super.createExplosion(entity, d0, d1, d2, f, flag, flag1);
/*      */     
/* 1022 */     if (explosion.wasCanceled) {
/* 1023 */       return explosion;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1033 */     if (!flag1) {
/* 1034 */       explosion.clearBlocks();
/*      */     }
/*      */     
/* 1037 */     Iterator iterator = this.players.iterator();
/*      */     
/* 1039 */     while (iterator.hasNext()) {
/* 1040 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*      */       
/* 1042 */       if (entityhuman.e(d0, d1, d2) < 4096.0D) {
/* 1043 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(d0, d1, d2, f, explosion.getBlocks(), (Vec3D)explosion.b().get(entityhuman)));
/*      */       }
/*      */     }
/*      */     
/* 1047 */     return explosion;
/*      */   }
/*      */   
/*      */   public void playBlockAction(BlockPosition blockposition, Block block, int i, int j) {
/* 1051 */     BlockActionData blockactiondata = new BlockActionData(blockposition, block, i, j);
/* 1052 */     Iterator iterator = this.S[this.T].iterator();
/*      */     
/*      */     BlockActionData blockactiondata1;
/*      */     do
/*      */     {
/* 1057 */       if (!iterator.hasNext()) {
/* 1058 */         this.S[this.T].add(blockactiondata);
/* 1059 */         return;
/*      */       }
/*      */       
/* 1062 */       blockactiondata1 = (BlockActionData)iterator.next();
/* 1063 */     } while (!blockactiondata1.equals(blockactiondata));
/*      */   }
/*      */   
/*      */   private void ak()
/*      */   {
/* 1068 */     while (!this.S[this.T].isEmpty()) {
/* 1069 */       int i = this.T;
/*      */       
/* 1071 */       this.T ^= 0x1;
/* 1072 */       Iterator iterator = this.S[i].iterator();
/*      */       
/* 1074 */       while (iterator.hasNext()) {
/* 1075 */         BlockActionData blockactiondata = (BlockActionData)iterator.next();
/*      */         
/* 1077 */         if (a(blockactiondata))
/*      */         {
/* 1079 */           this.server.getPlayerList().sendPacketNearby(blockactiondata.a().getX(), blockactiondata.a().getY(), blockactiondata.a().getZ(), 64.0D, this.dimension, new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.d(), blockactiondata.b(), blockactiondata.c()));
/*      */         }
/*      */       }
/*      */       
/* 1083 */       this.S[i].clear();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean a(BlockActionData blockactiondata)
/*      */   {
/* 1089 */     IBlockData iblockdata = getType(blockactiondata.a());
/*      */     
/* 1091 */     return iblockdata.getBlock() == blockactiondata.d() ? iblockdata.getBlock().a(this, blockactiondata.a(), iblockdata, blockactiondata.b(), blockactiondata.c()) : false;
/*      */   }
/*      */   
/*      */   public void saveLevel() {
/* 1095 */     this.dataManager.a();
/*      */   }
/*      */   
/*      */   protected void p() {
/* 1099 */     boolean flag = S();
/*      */     
/* 1101 */     super.p();
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
/*      */ 
/* 1122 */     if (flag != S())
/*      */     {
/* 1124 */       for (int i = 0; i < this.players.size(); i++) {
/* 1125 */         if (((EntityPlayer)this.players.get(i)).world == this) {
/* 1126 */           ((EntityPlayer)this.players.get(i)).setPlayerWeather(!flag ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
/*      */         }
/*      */       }
/*      */     }
/* 1130 */     for (int i = 0; i < this.players.size(); i++) {
/* 1131 */       if (((EntityPlayer)this.players.get(i)).world == this) {
/* 1132 */         ((EntityPlayer)this.players.get(i)).updateWeather(this.o, this.p, this.q, this.r);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected int q()
/*      */   {
/* 1140 */     return this.server.getPlayerList().s();
/*      */   }
/*      */   
/*      */   public MinecraftServer getMinecraftServer() {
/* 1144 */     return this.server;
/*      */   }
/*      */   
/*      */   public EntityTracker getTracker() {
/* 1148 */     return this.tracker;
/*      */   }
/*      */   
/*      */   public PlayerChunkMap getPlayerChunkMap() {
/* 1152 */     return this.manager;
/*      */   }
/*      */   
/*      */   public PortalTravelAgent getTravelAgent() {
/* 1156 */     return this.Q;
/*      */   }
/*      */   
/*      */   public void a(EnumParticle enumparticle, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint) {
/* 1160 */     a(enumparticle, false, d0, d1, d2, i, d3, d4, d5, d6, aint);
/*      */   }
/*      */   
/*      */   public void a(EnumParticle enumparticle, boolean flag, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint)
/*      */   {
/* 1165 */     sendParticles(null, enumparticle, flag, d0, d1, d2, i, d3, d4, d5, d6, aint);
/*      */   }
/*      */   
/*      */   public void sendParticles(EntityPlayer sender, EnumParticle enumparticle, boolean flag, double d0, double d1, double d2, int i, double d3, double d4, double d5, double d6, int... aint)
/*      */   {
/* 1170 */     PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(enumparticle, flag, (float)d0, (float)d1, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, i, aint);
/*      */     
/* 1172 */     for (int j = 0; j < this.players.size(); j++) {
/* 1173 */       EntityPlayer entityplayer = (EntityPlayer)this.players.get(j);
/* 1174 */       if ((sender == null) || (entityplayer.getBukkitEntity().canSee(sender.getBukkitEntity()))) {
/* 1175 */         BlockPosition blockposition = entityplayer.getChunkCoordinates();
/* 1176 */         double d7 = blockposition.c(d0, d1, d2);
/*      */         
/* 1178 */         if ((d7 <= 256.0D) || ((flag) && (d7 <= 65536.0D))) {
/* 1179 */           entityplayer.playerConnection.sendPacket(packetplayoutworldparticles);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public Entity getEntity(UUID uuid) {
/* 1186 */     return (Entity)this.entitiesByUUID.get(uuid);
/*      */   }
/*      */   
/*      */   public com.google.common.util.concurrent.ListenableFuture<Object> postToMainThread(Runnable runnable) {
/* 1190 */     return this.server.postToMainThread(runnable);
/*      */   }
/*      */   
/*      */   public boolean isMainThread() {
/* 1194 */     return this.server.isMainThread();
/*      */   }
/*      */   
/*      */   static class BlockActionDataList extends ArrayList<BlockActionData>
/*      */   {
/*      */     private BlockActionDataList() {}
/*      */     
/*      */     BlockActionDataList(Object object) {
/* 1202 */       this();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */