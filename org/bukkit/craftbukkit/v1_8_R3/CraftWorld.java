/*      */ package org.bukkit.craftbukkit.v1_8_R3;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.server.v1_8_R3.AxisAlignedBB;
/*      */ import net.minecraft.server.v1_8_R3.BiomeBase;
/*      */ import net.minecraft.server.v1_8_R3.BlockLeaves;
/*      */ import net.minecraft.server.v1_8_R3.BlockLeaves1;
/*      */ import net.minecraft.server.v1_8_R3.BlockLog1;
/*      */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*      */ import net.minecraft.server.v1_8_R3.BlockWood.EnumLogVariant;
/*      */ import net.minecraft.server.v1_8_R3.Blocks;
/*      */ import net.minecraft.server.v1_8_R3.ChunkProviderServer;
/*      */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*      */ import net.minecraft.server.v1_8_R3.EntityFallingBlock;
/*      */ import net.minecraft.server.v1_8_R3.EntityHanging;
/*      */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*      */ import net.minecraft.server.v1_8_R3.EntityLightning;
/*      */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*      */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*      */ import net.minecraft.server.v1_8_R3.EnumParticle;
/*      */ import net.minecraft.server.v1_8_R3.GameRules;
/*      */ import net.minecraft.server.v1_8_R3.IBlockData;
/*      */ import net.minecraft.server.v1_8_R3.WorldData;
/*      */ import net.minecraft.server.v1_8_R3.WorldGenerator;
/*      */ import net.minecraft.server.v1_8_R3.WorldServer;
/*      */ import org.apache.commons.lang.Validate;
/*      */ import org.bukkit.Difficulty;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.TreeType;
/*      */ import org.bukkit.World.Environment;
/*      */ import org.bukkit.World.Spigot;
/*      */ import org.bukkit.block.Biome;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLightningStrike;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.BlockMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.metadata.WorldMetadataStore;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.LongHashSet;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.LongObjectHashMap;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.FallingBlock;
/*      */ import org.bukkit.entity.LightningStrike;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.util.Vector;
/*      */ 
/*      */ public class CraftWorld implements org.bukkit.World
/*      */ {
/*      */   public static final int CUSTOM_DIMENSION_OFFSET = 10;
/*      */   private final WorldServer world;
/*      */   private org.bukkit.WorldBorder worldBorder;
/*      */   private World.Environment environment;
/*   65 */   private final CraftServer server = (CraftServer)org.bukkit.Bukkit.getServer();
/*      */   private final ChunkGenerator generator;
/*   67 */   private final List<org.bukkit.generator.BlockPopulator> populators = new ArrayList();
/*   68 */   private final BlockMetadataStore blockMetadata = new BlockMetadataStore(this);
/*   69 */   private int monsterSpawn = -1;
/*   70 */   private int animalSpawn = -1;
/*   71 */   private int waterAnimalSpawn = -1;
/*   72 */   private int ambientSpawn = -1;
/*   73 */   private int chunkLoadCount = 0;
/*      */   
/*      */   private int chunkGCTickCount;
/*   76 */   private static final Random rand = new Random();
/*      */   
/*      */   public CraftWorld(WorldServer world, ChunkGenerator gen, World.Environment env) {
/*   79 */     this.world = world;
/*   80 */     this.generator = gen;
/*      */     
/*   82 */     this.environment = env;
/*      */     
/*   84 */     if (this.server.chunkGCPeriod > 0) {
/*   85 */       this.chunkGCTickCount = rand.nextInt(this.server.chunkGCPeriod);
/*      */     }
/*      */   }
/*      */   
/*      */   public org.bukkit.block.Block getBlockAt(int x, int y, int z) {
/*   90 */     return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y, z & 0xF);
/*      */   }
/*      */   
/*      */   public int getBlockTypeIdAt(int x, int y, int z) {
/*   94 */     return CraftMagicNumbers.getId(this.world.getType(new BlockPosition(x, y, z)).getBlock());
/*      */   }
/*      */   
/*      */   public int getHighestBlockYAt(int x, int z) {
/*   98 */     if (!isChunkLoaded(x >> 4, z >> 4)) {
/*   99 */       loadChunk(x >> 4, z >> 4);
/*      */     }
/*      */     
/*  102 */     return this.world.getHighestBlockYAt(new BlockPosition(x, 0, z)).getY();
/*      */   }
/*      */   
/*      */   public Location getSpawnLocation() {
/*  106 */     BlockPosition spawn = this.world.getSpawn();
/*  107 */     return new Location(this, spawn.getX(), spawn.getY(), spawn.getZ());
/*      */   }
/*      */   
/*      */   public boolean setSpawnLocation(int x, int y, int z) {
/*      */     try {
/*  112 */       Location previousLocation = getSpawnLocation();
/*  113 */       this.world.worldData.setSpawn(new BlockPosition(x, y, z));
/*      */       
/*      */ 
/*  116 */       org.bukkit.event.world.SpawnChangeEvent event = new org.bukkit.event.world.SpawnChangeEvent(this, previousLocation);
/*  117 */       this.server.getPluginManager().callEvent(event);
/*      */       
/*  119 */       return true;
/*      */     } catch (Exception localException) {}
/*  121 */     return false;
/*      */   }
/*      */   
/*      */   public org.bukkit.Chunk getChunkAt(int x, int z)
/*      */   {
/*  126 */     return this.world.chunkProviderServer.getChunkAt(x, z).bukkitChunk;
/*      */   }
/*      */   
/*      */   public org.bukkit.Chunk getChunkAt(org.bukkit.block.Block block) {
/*  130 */     return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
/*      */   }
/*      */   
/*      */   public boolean isChunkLoaded(int x, int z) {
/*  134 */     return this.world.chunkProviderServer.isChunkLoaded(x, z);
/*      */   }
/*      */   
/*      */   public org.bukkit.Chunk[] getLoadedChunks() {
/*  138 */     Object[] chunks = this.world.chunkProviderServer.chunks.values().toArray();
/*  139 */     org.bukkit.Chunk[] craftChunks = new CraftChunk[chunks.length];
/*      */     
/*  141 */     for (int i = 0; i < chunks.length; i++) {
/*  142 */       net.minecraft.server.v1_8_R3.Chunk chunk = (net.minecraft.server.v1_8_R3.Chunk)chunks[i];
/*  143 */       craftChunks[i] = chunk.bukkitChunk;
/*      */     }
/*      */     
/*  146 */     return craftChunks;
/*      */   }
/*      */   
/*      */   public void loadChunk(int x, int z) {
/*  150 */     loadChunk(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(org.bukkit.Chunk chunk) {
/*  154 */     return unloadChunk(chunk.getX(), chunk.getZ());
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z) {
/*  158 */     return unloadChunk(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z, boolean save) {
/*  162 */     return unloadChunk(x, z, save, false);
/*      */   }
/*      */   
/*      */   public boolean unloadChunkRequest(int x, int z) {
/*  166 */     return unloadChunkRequest(x, z, true);
/*      */   }
/*      */   
/*      */   public boolean unloadChunkRequest(int x, int z, boolean safe) {
/*  170 */     org.spigotmc.AsyncCatcher.catchOp("chunk unload");
/*  171 */     if ((safe) && (isChunkInUse(x, z))) {
/*  172 */       return false;
/*      */     }
/*      */     
/*  175 */     this.world.chunkProviderServer.queueUnload(x, z);
/*      */     
/*  177 */     return true;
/*      */   }
/*      */   
/*      */   public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
/*  181 */     org.spigotmc.AsyncCatcher.catchOp("chunk unload");
/*  182 */     if ((safe) && (isChunkInUse(x, z))) {
/*  183 */       return false;
/*      */     }
/*      */     
/*  186 */     net.minecraft.server.v1_8_R3.Chunk chunk = this.world.chunkProviderServer.getOrCreateChunk(x, z);
/*  187 */     if (chunk.mustSave) {
/*  188 */       save = true;
/*      */     }
/*      */     
/*  191 */     chunk.removeEntities();
/*      */     
/*  193 */     if ((save) && (!(chunk instanceof net.minecraft.server.v1_8_R3.EmptyChunk))) {
/*  194 */       this.world.chunkProviderServer.saveChunk(chunk);
/*  195 */       this.world.chunkProviderServer.saveChunkNOP(chunk);
/*      */     }
/*      */     
/*  198 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*  199 */     this.world.chunkProviderServer.chunks.remove(org.bukkit.craftbukkit.v1_8_R3.util.LongHash.toLong(x, z));
/*      */     
/*  201 */     return true;
/*      */   }
/*      */   
/*      */   public boolean regenerateChunk(int x, int z) {
/*  205 */     unloadChunk(x, z, false, false);
/*      */     
/*  207 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*      */     
/*  209 */     net.minecraft.server.v1_8_R3.Chunk chunk = null;
/*      */     
/*  211 */     if (this.world.chunkProviderServer.chunkProvider == null) {
/*  212 */       chunk = this.world.chunkProviderServer.emptyChunk;
/*      */     } else {
/*  214 */       chunk = this.world.chunkProviderServer.chunkProvider.getOrCreateChunk(x, z);
/*      */     }
/*      */     
/*  217 */     chunkLoadPostProcess(chunk, x, z);
/*      */     
/*  219 */     refreshChunk(x, z);
/*      */     
/*  221 */     return chunk != null;
/*      */   }
/*      */   
/*      */   public boolean refreshChunk(int x, int z) {
/*  225 */     if (!isChunkLoaded(x, z)) {
/*  226 */       return false;
/*      */     }
/*      */     
/*  229 */     int px = x << 4;
/*  230 */     int pz = z << 4;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  235 */     int height = getMaxHeight() / 16;
/*  236 */     for (int idx = 0; idx < 64; idx++) {
/*  237 */       this.world.notify(new BlockPosition(px + idx / height, idx % height * 16, pz));
/*      */     }
/*  239 */     this.world.notify(new BlockPosition(px + 15, height * 16 - 1, pz + 15));
/*      */     
/*  241 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isChunkInUse(int x, int z) {
/*  245 */     return this.world.getPlayerChunkMap().isChunkInUse(x, z);
/*      */   }
/*      */   
/*      */   public boolean loadChunk(int x, int z, boolean generate) {
/*  249 */     org.spigotmc.AsyncCatcher.catchOp("chunk load");
/*  250 */     this.chunkLoadCount += 1;
/*  251 */     if (generate)
/*      */     {
/*  253 */       return this.world.chunkProviderServer.getChunkAt(x, z) != null;
/*      */     }
/*      */     
/*  256 */     this.world.chunkProviderServer.unloadQueue.remove(x, z);
/*  257 */     net.minecraft.server.v1_8_R3.Chunk chunk = (net.minecraft.server.v1_8_R3.Chunk)this.world.chunkProviderServer.chunks.get(org.bukkit.craftbukkit.v1_8_R3.util.LongHash.toLong(x, z));
/*      */     
/*  259 */     if (chunk == null) {
/*  260 */       this.world.timings.syncChunkLoadTimer.startTiming();
/*  261 */       chunk = this.world.chunkProviderServer.loadChunk(x, z);
/*      */       
/*  263 */       chunkLoadPostProcess(chunk, x, z);
/*  264 */       this.world.timings.syncChunkLoadTimer.stopTiming();
/*      */     }
/*  266 */     return chunk != null;
/*      */   }
/*      */   
/*      */   private void chunkLoadPostProcess(net.minecraft.server.v1_8_R3.Chunk chunk, int cx, int cz) {
/*  270 */     if (chunk != null) {
/*  271 */       this.world.chunkProviderServer.chunks.put(org.bukkit.craftbukkit.v1_8_R3.util.LongHash.toLong(cx, cz), chunk);
/*      */       
/*  273 */       chunk.addEntities();
/*      */       
/*      */ 
/*  276 */       for (int x = -2; x < 3; x++) {
/*  277 */         for (int z = -2; z < 3; z++) {
/*  278 */           if ((x != 0) || (z != 0))
/*      */           {
/*      */ 
/*      */ 
/*  282 */             net.minecraft.server.v1_8_R3.Chunk neighbor = this.world.chunkProviderServer.getChunkIfLoaded(chunk.locX + x, chunk.locZ + z);
/*  283 */             if (neighbor != null) {
/*  284 */               neighbor.setNeighborLoaded(-x, -z);
/*  285 */               chunk.setNeighborLoaded(x, z);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  291 */       chunk.loadNearby(this.world.chunkProviderServer, this.world.chunkProviderServer, cx, cz);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isChunkLoaded(org.bukkit.Chunk chunk) {
/*  296 */     return isChunkLoaded(chunk.getX(), chunk.getZ());
/*      */   }
/*      */   
/*      */   public void loadChunk(org.bukkit.Chunk chunk) {
/*  300 */     loadChunk(chunk.getX(), chunk.getZ());
/*  301 */     ((CraftChunk)getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
/*      */   }
/*      */   
/*      */   public WorldServer getHandle() {
/*  305 */     return this.world;
/*      */   }
/*      */   
/*      */   public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {
/*  309 */     Validate.notNull(item, "Cannot drop a Null item.");
/*  310 */     Validate.isTrue(item.getTypeId() != 0, "Cannot drop AIR.");
/*  311 */     net.minecraft.server.v1_8_R3.EntityItem entity = new net.minecraft.server.v1_8_R3.EntityItem(this.world, loc.getX(), loc.getY(), loc.getZ(), org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(item));
/*  312 */     entity.pickupDelay = 10;
/*  313 */     this.world.addEntity(entity);
/*      */     
/*      */ 
/*  316 */     return new org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem(this.world.getServer(), entity);
/*      */   }
/*      */   
/*      */   private static void randomLocationWithinBlock(Location loc, double xs, double ys, double zs) {
/*  320 */     double prevX = loc.getX();
/*  321 */     double prevY = loc.getY();
/*  322 */     double prevZ = loc.getZ();
/*  323 */     loc.add(xs, ys, zs);
/*  324 */     if (loc.getX() < Math.floor(prevX)) {
/*  325 */       loc.setX(Math.floor(prevX));
/*      */     }
/*  327 */     if (loc.getX() >= Math.ceil(prevX)) {
/*  328 */       loc.setX(Math.ceil(prevX - 0.01D));
/*      */     }
/*  330 */     if (loc.getY() < Math.floor(prevY)) {
/*  331 */       loc.setY(Math.floor(prevY));
/*      */     }
/*  333 */     if (loc.getY() >= Math.ceil(prevY)) {
/*  334 */       loc.setY(Math.ceil(prevY - 0.01D));
/*      */     }
/*  336 */     if (loc.getZ() < Math.floor(prevZ)) {
/*  337 */       loc.setZ(Math.floor(prevZ));
/*      */     }
/*  339 */     if (loc.getZ() >= Math.ceil(prevZ)) {
/*  340 */       loc.setZ(Math.ceil(prevZ - 0.01D));
/*      */     }
/*      */   }
/*      */   
/*      */   public org.bukkit.entity.Item dropItemNaturally(Location loc, ItemStack item) {
/*  345 */     double xs = this.world.random.nextFloat() * 0.7F - 0.35D;
/*  346 */     double ys = this.world.random.nextFloat() * 0.7F - 0.35D;
/*  347 */     double zs = this.world.random.nextFloat() * 0.7F - 0.35D;
/*  348 */     loc = loc.clone();
/*      */     
/*      */ 
/*  351 */     randomLocationWithinBlock(loc, xs, ys, zs);
/*  352 */     return dropItem(loc, item);
/*      */   }
/*      */   
/*      */   public org.bukkit.entity.Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
/*  356 */     Validate.notNull(loc, "Can not spawn arrow with a null location");
/*  357 */     Validate.notNull(velocity, "Can not spawn arrow with a null velocity");
/*      */     
/*  359 */     EntityArrow arrow = new EntityArrow(this.world);
/*  360 */     arrow.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
/*  361 */     arrow.shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
/*  362 */     this.world.addEntity(arrow);
/*  363 */     return (org.bukkit.entity.Arrow)arrow.getBukkitEntity();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public LivingEntity spawnCreature(Location loc, org.bukkit.entity.CreatureType creatureType) {
/*  368 */     return spawnCreature(loc, creatureType.toEntityType());
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public LivingEntity spawnCreature(Location loc, EntityType creatureType) {
/*  373 */     Validate.isTrue(creatureType.isAlive(), "EntityType not instance of LivingEntity");
/*  374 */     return (LivingEntity)spawnEntity(loc, creatureType);
/*      */   }
/*      */   
/*      */   public org.bukkit.entity.Entity spawnEntity(Location loc, EntityType entityType) {
/*  378 */     return spawn(loc, entityType.getEntityClass());
/*      */   }
/*      */   
/*      */   public LightningStrike strikeLightning(Location loc) {
/*  382 */     EntityLightning lightning = new EntityLightning(this.world, loc.getX(), loc.getY(), loc.getZ());
/*  383 */     this.world.strikeLightning(lightning);
/*  384 */     return new CraftLightningStrike(this.server, lightning);
/*      */   }
/*      */   
/*      */   public LightningStrike strikeLightningEffect(Location loc) {
/*  388 */     EntityLightning lightning = new EntityLightning(this.world, loc.getX(), loc.getY(), loc.getZ(), true);
/*  389 */     this.world.strikeLightning(lightning);
/*  390 */     return new CraftLightningStrike(this.server, lightning); }
/*      */   
/*      */   public boolean generateTree(Location loc, TreeType type) { WorldGenerator gen;
/*      */     WorldGenerator gen;
/*      */     WorldGenerator gen;
/*  395 */     WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; WorldGenerator gen; switch (type) {
/*      */     case BIG_TREE: 
/*  397 */       gen = new net.minecraft.server.v1_8_R3.WorldGenBigTree(true);
/*  398 */       break;
/*      */     case COCOA_TREE: 
/*  400 */       gen = new net.minecraft.server.v1_8_R3.WorldGenForest(true, false);
/*  401 */       break;
/*      */     case BIRCH: 
/*  403 */       gen = new net.minecraft.server.v1_8_R3.WorldGenTaiga2(true);
/*  404 */       break;
/*      */     case BROWN_MUSHROOM: 
/*  406 */       gen = new net.minecraft.server.v1_8_R3.WorldGenTaiga1();
/*  407 */       break;
/*      */     case DARK_OAK: 
/*  409 */       IBlockData iblockdata1 = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/*  410 */       IBlockData iblockdata2 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  411 */       gen = new net.minecraft.server.v1_8_R3.WorldGenJungleTree(true, 10, 20, iblockdata1, iblockdata2);
/*  412 */       break;
/*      */     case JUNGLE: 
/*  414 */       IBlockData iblockdata1 = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/*  415 */       IBlockData iblockdata2 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  416 */       gen = new net.minecraft.server.v1_8_R3.WorldGenTrees(true, 4 + rand.nextInt(7), iblockdata1, iblockdata2, false);
/*  417 */       break;
/*      */     case JUNGLE_BUSH: 
/*  419 */       IBlockData iblockdata1 = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/*  420 */       IBlockData iblockdata2 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  421 */       gen = new net.minecraft.server.v1_8_R3.WorldGenTrees(true, 4 + rand.nextInt(7), iblockdata1, iblockdata2, true);
/*  422 */       break;
/*      */     case MEGA_REDWOOD: 
/*  424 */       IBlockData iblockdata1 = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
/*  425 */       IBlockData iblockdata2 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  426 */       gen = new net.minecraft.server.v1_8_R3.WorldGenGroundBush(iblockdata1, iblockdata2);
/*  427 */       break;
/*      */     case REDWOOD: 
/*  429 */       gen = new net.minecraft.server.v1_8_R3.WorldGenHugeMushroom(Blocks.RED_MUSHROOM_BLOCK);
/*  430 */       break;
/*      */     case RED_MUSHROOM: 
/*  432 */       gen = new net.minecraft.server.v1_8_R3.WorldGenHugeMushroom(Blocks.BROWN_MUSHROOM_BLOCK);
/*  433 */       break;
/*      */     case SMALL_JUNGLE: 
/*  435 */       gen = new net.minecraft.server.v1_8_R3.WorldGenSwampTree();
/*  436 */       break;
/*      */     case SWAMP: 
/*  438 */       gen = new net.minecraft.server.v1_8_R3.WorldGenAcaciaTree(true);
/*  439 */       break;
/*      */     case TALL_BIRCH: 
/*  441 */       gen = new net.minecraft.server.v1_8_R3.WorldGenForestTree(true);
/*  442 */       break;
/*      */     case TALL_REDWOOD: 
/*  444 */       gen = new net.minecraft.server.v1_8_R3.WorldGenMegaTree(false, rand.nextBoolean());
/*  445 */       break;
/*      */     case TREE: 
/*  447 */       gen = new net.minecraft.server.v1_8_R3.WorldGenForest(true, true);
/*  448 */       break;
/*      */     case ACACIA: 
/*      */     default: 
/*  451 */       gen = new net.minecraft.server.v1_8_R3.WorldGenTrees(true);
/*      */     }
/*      */     
/*      */     
/*  455 */     return gen.generate(this.world, rand, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
/*      */   }
/*      */   
/*      */   public boolean generateTree(Location loc, TreeType type, org.bukkit.BlockChangeDelegate delegate) {
/*  459 */     this.world.captureTreeGeneration = true;
/*  460 */     this.world.captureBlockStates = true;
/*  461 */     boolean grownTree = generateTree(loc, type);
/*  462 */     this.world.captureBlockStates = false;
/*  463 */     this.world.captureTreeGeneration = false;
/*  464 */     if (grownTree) {
/*  465 */       for (BlockState blockstate : this.world.capturedBlockStates) {
/*  466 */         int x = blockstate.getX();
/*  467 */         int y = blockstate.getY();
/*  468 */         int z = blockstate.getZ();
/*  469 */         BlockPosition position = new BlockPosition(x, y, z);
/*  470 */         net.minecraft.server.v1_8_R3.Block oldBlock = this.world.getType(position).getBlock();
/*  471 */         int typeId = blockstate.getTypeId();
/*  472 */         int data = blockstate.getRawData();
/*  473 */         int flag = ((org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState)blockstate).getFlag();
/*  474 */         delegate.setTypeIdAndData(x, y, z, typeId, data);
/*  475 */         net.minecraft.server.v1_8_R3.Block newBlock = this.world.getType(position).getBlock();
/*  476 */         this.world.notifyAndUpdatePhysics(position, null, oldBlock, newBlock, flag);
/*      */       }
/*  478 */       this.world.capturedBlockStates.clear();
/*  479 */       return true;
/*      */     }
/*  481 */     this.world.capturedBlockStates.clear();
/*  482 */     return false;
/*      */   }
/*      */   
/*      */   public net.minecraft.server.v1_8_R3.TileEntity getTileEntityAt(int x, int y, int z)
/*      */   {
/*  487 */     return this.world.getTileEntity(new BlockPosition(x, y, z));
/*      */   }
/*      */   
/*      */   public String getName() {
/*  491 */     return this.world.worldData.getName();
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public long getId() {
/*  496 */     return this.world.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public java.util.UUID getUID() {
/*  500 */     return this.world.getDataManager().getUUID();
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/*  505 */     return "CraftWorld{name=" + getName() + '}';
/*      */   }
/*      */   
/*      */   public long getTime() {
/*  509 */     long time = getFullTime() % 24000L;
/*  510 */     if (time < 0L) time += 24000L;
/*  511 */     return time;
/*      */   }
/*      */   
/*      */   public void setTime(long time) {
/*  515 */     long margin = (time - getFullTime()) % 24000L;
/*  516 */     if (margin < 0L) margin += 24000L;
/*  517 */     setFullTime(getFullTime() + margin);
/*      */   }
/*      */   
/*      */   public long getFullTime() {
/*  521 */     return this.world.getDayTime();
/*      */   }
/*      */   
/*      */   public void setFullTime(long time) {
/*  525 */     this.world.setDayTime(time);
/*      */     
/*      */ 
/*  528 */     for (Player p : getPlayers()) {
/*  529 */       CraftPlayer cp = (CraftPlayer)p;
/*  530 */       if (cp.getHandle().playerConnection != null)
/*      */       {
/*  532 */         cp.getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutUpdateTime(cp.getHandle().world.getTime(), cp.getHandle().getPlayerTime(), cp.getHandle().world.getGameRules().getBoolean("doDaylightCycle"))); }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power) {
/*  537 */     return createExplosion(x, y, z, power, false, true);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
/*  541 */     return createExplosion(x, y, z, power, setFire, true);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
/*  545 */     return !this.world.createExplosion(null, x, y, z, power, setFire, breakBlocks).wasCanceled;
/*      */   }
/*      */   
/*      */   public boolean createExplosion(Location loc, float power) {
/*  549 */     return createExplosion(loc, power, false);
/*      */   }
/*      */   
/*      */   public boolean createExplosion(Location loc, float power, boolean setFire) {
/*  553 */     return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
/*      */   }
/*      */   
/*      */   public World.Environment getEnvironment() {
/*  557 */     return this.environment;
/*      */   }
/*      */   
/*      */   public void setEnvironment(World.Environment env) {
/*  561 */     if (this.environment != env) {
/*  562 */       this.environment = env;
/*  563 */       this.world.worldProvider = net.minecraft.server.v1_8_R3.WorldProvider.byDimension(this.environment.getId());
/*      */     }
/*      */   }
/*      */   
/*      */   public org.bukkit.block.Block getBlockAt(Location location) {
/*  568 */     return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public int getBlockTypeIdAt(Location location) {
/*  572 */     return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public int getHighestBlockYAt(Location location) {
/*  576 */     return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public org.bukkit.Chunk getChunkAt(Location location) {
/*  580 */     return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
/*      */   }
/*      */   
/*      */   public ChunkGenerator getGenerator() {
/*  584 */     return this.generator;
/*      */   }
/*      */   
/*      */   public List<org.bukkit.generator.BlockPopulator> getPopulators() {
/*  588 */     return this.populators;
/*      */   }
/*      */   
/*      */   public org.bukkit.block.Block getHighestBlockAt(int x, int z) {
/*  592 */     return getBlockAt(x, getHighestBlockYAt(x, z), z);
/*      */   }
/*      */   
/*      */   public org.bukkit.block.Block getHighestBlockAt(Location location) {
/*  596 */     return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
/*      */   }
/*      */   
/*      */   public Biome getBiome(int x, int z) {
/*  600 */     return CraftBlock.biomeBaseToBiome(this.world.getBiome(new BlockPosition(x, 0, z)));
/*      */   }
/*      */   
/*      */   public void setBiome(int x, int z, Biome bio) {
/*  604 */     BiomeBase bb = CraftBlock.biomeToBiomeBase(bio);
/*  605 */     if (this.world.isLoaded(new BlockPosition(x, 0, z))) {
/*  606 */       net.minecraft.server.v1_8_R3.Chunk chunk = this.world.getChunkAtWorldCoords(new BlockPosition(x, 0, z));
/*      */       
/*  608 */       if (chunk != null) {
/*  609 */         byte[] biomevals = chunk.getBiomeIndex();
/*  610 */         biomevals[((z & 0xF) << 4 | x & 0xF)] = ((byte)bb.id);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public double getTemperature(int x, int z) {
/*  616 */     return this.world.getBiome(new BlockPosition(x, 0, z)).temperature;
/*      */   }
/*      */   
/*      */   public double getHumidity(int x, int z) {
/*  620 */     return this.world.getBiome(new BlockPosition(x, 0, z)).humidity;
/*      */   }
/*      */   
/*      */   public List<org.bukkit.entity.Entity> getEntities() {
/*  624 */     List<org.bukkit.entity.Entity> list = new ArrayList();
/*      */     
/*  626 */     for (Object o : this.world.entityList) {
/*  627 */       if ((o instanceof net.minecraft.server.v1_8_R3.Entity)) {
/*  628 */         net.minecraft.server.v1_8_R3.Entity mcEnt = (net.minecraft.server.v1_8_R3.Entity)o;
/*  629 */         org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
/*      */         
/*      */ 
/*  632 */         if (bukkitEntity != null) {
/*  633 */           list.add(bukkitEntity);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  638 */     return list;
/*      */   }
/*      */   
/*      */   public List<LivingEntity> getLivingEntities() {
/*  642 */     List<LivingEntity> list = new ArrayList();
/*      */     
/*  644 */     for (Object o : this.world.entityList) {
/*  645 */       if ((o instanceof net.minecraft.server.v1_8_R3.Entity)) {
/*  646 */         net.minecraft.server.v1_8_R3.Entity mcEnt = (net.minecraft.server.v1_8_R3.Entity)o;
/*  647 */         org.bukkit.entity.Entity bukkitEntity = mcEnt.getBukkitEntity();
/*      */         
/*      */ 
/*  650 */         if ((bukkitEntity != null) && ((bukkitEntity instanceof LivingEntity))) {
/*  651 */           list.add((LivingEntity)bukkitEntity);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  656 */     return list;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T>... classes)
/*      */   {
/*  662 */     return getEntitiesByClasses(classes);
/*      */   }
/*      */   
/*      */   public <T extends org.bukkit.entity.Entity> Collection<T> getEntitiesByClass(Class<T> clazz)
/*      */   {
/*  667 */     Collection<T> list = new ArrayList();
/*      */     
/*  669 */     for (Object entity : this.world.entityList) {
/*  670 */       if ((entity instanceof net.minecraft.server.v1_8_R3.Entity)) {
/*  671 */         org.bukkit.entity.Entity bukkitEntity = ((net.minecraft.server.v1_8_R3.Entity)entity).getBukkitEntity();
/*      */         
/*  673 */         if (bukkitEntity != null)
/*      */         {
/*      */ 
/*      */ 
/*  677 */           Class<?> bukkitClass = bukkitEntity.getClass();
/*      */           
/*  679 */           if (clazz.isAssignableFrom(bukkitClass)) {
/*  680 */             list.add(bukkitEntity);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  685 */     return list;
/*      */   }
/*      */   
/*      */   public Collection<org.bukkit.entity.Entity> getEntitiesByClasses(Class<?>... classes) {
/*  689 */     Collection<org.bukkit.entity.Entity> list = new ArrayList();
/*      */     
/*  691 */     for (Object entity : this.world.entityList) {
/*  692 */       if ((entity instanceof net.minecraft.server.v1_8_R3.Entity)) {
/*  693 */         org.bukkit.entity.Entity bukkitEntity = ((net.minecraft.server.v1_8_R3.Entity)entity).getBukkitEntity();
/*      */         
/*  695 */         if (bukkitEntity != null)
/*      */         {
/*      */ 
/*      */ 
/*  699 */           Class<?> bukkitClass = bukkitEntity.getClass();
/*      */           Class[] arrayOfClass;
/*  701 */           int i = (arrayOfClass = classes).length; for (int j = 0; j < i; j++) { Class<?> clazz = arrayOfClass[j];
/*  702 */             if (clazz.isAssignableFrom(bukkitClass)) {
/*  703 */               list.add(bukkitEntity);
/*  704 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  710 */     return list;
/*      */   }
/*      */   
/*      */   public Collection<org.bukkit.entity.Entity> getNearbyEntities(Location location, double x, double y, double z)
/*      */   {
/*  715 */     if ((location == null) || (!location.getWorld().equals(this))) {
/*  716 */       return java.util.Collections.emptyList();
/*      */     }
/*      */     
/*  719 */     AxisAlignedBB bb = new AxisAlignedBB(location.getX() - x, location.getY() - y, location.getZ() - z, location.getX() + x, location.getY() + y, location.getZ() + z);
/*  720 */     List<net.minecraft.server.v1_8_R3.Entity> entityList = getHandle().a(null, bb, null);
/*  721 */     List<org.bukkit.entity.Entity> bukkitEntityList = new ArrayList(entityList.size());
/*  722 */     for (Object entity : entityList) {
/*  723 */       bukkitEntityList.add(((net.minecraft.server.v1_8_R3.Entity)entity).getBukkitEntity());
/*      */     }
/*  725 */     return bukkitEntityList;
/*      */   }
/*      */   
/*      */   public List<Player> getPlayers() {
/*  729 */     List<Player> list = new ArrayList(this.world.players.size());
/*      */     
/*  731 */     for (net.minecraft.server.v1_8_R3.EntityHuman human : this.world.players) {
/*  732 */       org.bukkit.entity.HumanEntity bukkitEntity = human.getBukkitEntity();
/*      */       
/*  734 */       if ((bukkitEntity != null) && ((bukkitEntity instanceof Player))) {
/*  735 */         list.add((Player)bukkitEntity);
/*      */       }
/*      */     }
/*      */     
/*  739 */     return list;
/*      */   }
/*      */   
/*      */   public void save()
/*      */   {
/*  744 */     save(true);
/*      */   }
/*      */   
/*      */   public void save(boolean forceSave) {
/*  748 */     this.server.checkSaveState();
/*      */     try {
/*  750 */       boolean oldSave = this.world.savingDisabled;
/*      */       
/*  752 */       this.world.savingDisabled = false;
/*  753 */       this.world.save(forceSave, null);
/*      */       
/*  755 */       this.world.savingDisabled = oldSave;
/*      */     } catch (net.minecraft.server.v1_8_R3.ExceptionWorldConflict ex) {
/*  757 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isAutoSave() {
/*  762 */     return !this.world.savingDisabled;
/*      */   }
/*      */   
/*      */   public void setAutoSave(boolean value) {
/*  766 */     this.world.savingDisabled = (!value);
/*      */   }
/*      */   
/*      */   public void setDifficulty(Difficulty difficulty) {
/*  770 */     getHandle().worldData.setDifficulty(net.minecraft.server.v1_8_R3.EnumDifficulty.getById(difficulty.getValue()));
/*      */   }
/*      */   
/*      */   public Difficulty getDifficulty() {
/*  774 */     return Difficulty.getByValue(getHandle().getDifficulty().ordinal());
/*      */   }
/*      */   
/*      */   public BlockMetadataStore getBlockMetadata() {
/*  778 */     return this.blockMetadata;
/*      */   }
/*      */   
/*      */   public boolean hasStorm() {
/*  782 */     return this.world.worldData.hasStorm();
/*      */   }
/*      */   
/*      */   public void setStorm(boolean hasStorm) {
/*  786 */     this.world.worldData.setStorm(hasStorm);
/*      */   }
/*      */   
/*      */   public int getWeatherDuration() {
/*  790 */     return this.world.worldData.getWeatherDuration();
/*      */   }
/*      */   
/*      */   public void setWeatherDuration(int duration) {
/*  794 */     this.world.worldData.setWeatherDuration(duration);
/*      */   }
/*      */   
/*      */   public boolean isThundering() {
/*  798 */     return this.world.worldData.isThundering();
/*      */   }
/*      */   
/*      */   public void setThundering(boolean thundering) {
/*  802 */     this.world.worldData.setThundering(thundering);
/*      */   }
/*      */   
/*      */   public int getThunderDuration() {
/*  806 */     return this.world.worldData.getThunderDuration();
/*      */   }
/*      */   
/*      */   public void setThunderDuration(int duration) {
/*  810 */     this.world.worldData.setThunderDuration(duration);
/*      */   }
/*      */   
/*      */   public long getSeed() {
/*  814 */     return this.world.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public boolean getPVP() {
/*  818 */     return this.world.pvpMode;
/*      */   }
/*      */   
/*      */   public void setPVP(boolean pvp) {
/*  822 */     this.world.pvpMode = pvp;
/*      */   }
/*      */   
/*      */   public void playEffect(Player player, Effect effect, int data) {
/*  826 */     playEffect(player.getLocation(), effect, data, 0);
/*      */   }
/*      */   
/*      */   public void playEffect(Location location, Effect effect, int data) {
/*  830 */     playEffect(location, effect, data, 64);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data) {
/*  834 */     playEffect(loc, effect, data, 64);
/*      */   }
/*      */   
/*      */   public <T> void playEffect(Location loc, Effect effect, T data, int radius) {
/*  838 */     if (data != null) {
/*  839 */       Validate.isTrue(data.getClass().isAssignableFrom(effect.getData()), "Wrong kind of data for this effect!");
/*      */     } else {
/*  841 */       Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
/*      */     }
/*      */     
/*  844 */     if ((data != null) && (data.getClass().equals(MaterialData.class))) {
/*  845 */       MaterialData materialData = (MaterialData)data;
/*  846 */       Validate.isTrue(materialData.getItemType().isBlock(), "Material must be block");
/*  847 */       spigot().playEffect(loc, effect, materialData.getItemType().getId(), materialData.getData(), 0.0F, 0.0F, 0.0F, 1.0F, 1, radius);
/*      */     } else {
/*  849 */       int dataValue = data == null ? 0 : CraftEffect.getDataValue(effect, data);
/*  850 */       playEffect(loc, effect, dataValue, radius);
/*      */     }
/*      */   }
/*      */   
/*      */   public void playEffect(Location location, Effect effect, int data, int radius) {
/*  855 */     spigot().playEffect(location, effect, data, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, radius);
/*      */   }
/*      */   
/*      */   public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
/*  859 */     return spawn(location, clazz, CreatureSpawnEvent.SpawnReason.CUSTOM);
/*      */   }
/*      */   
/*      */   public FallingBlock spawnFallingBlock(Location location, org.bukkit.Material material, byte data) throws IllegalArgumentException {
/*  863 */     Validate.notNull(location, "Location cannot be null");
/*  864 */     Validate.notNull(material, "Material cannot be null");
/*  865 */     Validate.isTrue(material.isBlock(), "Material must be a block");
/*      */     
/*  867 */     double x = location.getBlockX() + 0.5D;
/*  868 */     double y = location.getBlockY() + 0.5D;
/*  869 */     double z = location.getBlockZ() + 0.5D;
/*      */     
/*  871 */     EntityFallingBlock entity = new EntityFallingBlock(this.world, x, y, z, net.minecraft.server.v1_8_R3.Block.getById(material.getId()).fromLegacyData(data));
/*  872 */     entity.ticksLived = 1;
/*      */     
/*  874 */     this.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
/*  875 */     return (FallingBlock)entity.getBukkitEntity();
/*      */   }
/*      */   
/*      */   public FallingBlock spawnFallingBlock(Location location, int blockId, byte blockData) throws IllegalArgumentException {
/*  879 */     return spawnFallingBlock(location, org.bukkit.Material.getMaterial(blockId), blockData);
/*      */   }
/*      */   
/*      */   public net.minecraft.server.v1_8_R3.Entity createEntity(Location location, Class<? extends org.bukkit.entity.Entity> clazz) throws IllegalArgumentException
/*      */   {
/*  884 */     if ((location == null) || (clazz == null)) {
/*  885 */       throw new IllegalArgumentException("Location or entity class cannot be null");
/*      */     }
/*      */     
/*  888 */     net.minecraft.server.v1_8_R3.Entity entity = null;
/*      */     
/*  890 */     double x = location.getX();
/*  891 */     double y = location.getY();
/*  892 */     double z = location.getZ();
/*  893 */     float pitch = location.getPitch();
/*  894 */     float yaw = location.getYaw();
/*      */     
/*      */ 
/*  897 */     if (org.bukkit.entity.Boat.class.isAssignableFrom(clazz)) {
/*  898 */       entity = new net.minecraft.server.v1_8_R3.EntityBoat(this.world, x, y, z);
/*  899 */     } else if (FallingBlock.class.isAssignableFrom(clazz)) {
/*  900 */       x = location.getBlockX();
/*  901 */       y = location.getBlockY();
/*  902 */       z = location.getBlockZ();
/*  903 */       IBlockData blockData = this.world.getType(new BlockPosition(x, y, z));
/*  904 */       int type = CraftMagicNumbers.getId(blockData.getBlock());
/*  905 */       int data = blockData.getBlock().toLegacyData(blockData);
/*      */       
/*  907 */       entity = new EntityFallingBlock(this.world, x + 0.5D, y + 0.5D, z + 0.5D, net.minecraft.server.v1_8_R3.Block.getById(type).fromLegacyData(data));
/*  908 */     } else if (org.bukkit.entity.Projectile.class.isAssignableFrom(clazz)) {
/*  909 */       if (org.bukkit.entity.Snowball.class.isAssignableFrom(clazz)) {
/*  910 */         entity = new net.minecraft.server.v1_8_R3.EntitySnowball(this.world, x, y, z);
/*  911 */       } else if (org.bukkit.entity.Egg.class.isAssignableFrom(clazz)) {
/*  912 */         entity = new net.minecraft.server.v1_8_R3.EntityEgg(this.world, x, y, z);
/*  913 */       } else if (org.bukkit.entity.Arrow.class.isAssignableFrom(clazz)) {
/*  914 */         entity = new EntityArrow(this.world);
/*  915 */         entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  916 */       } else if (org.bukkit.entity.ThrownExpBottle.class.isAssignableFrom(clazz)) {
/*  917 */         entity = new net.minecraft.server.v1_8_R3.EntityThrownExpBottle(this.world);
/*  918 */         entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  919 */       } else if (org.bukkit.entity.EnderPearl.class.isAssignableFrom(clazz)) {
/*  920 */         entity = new net.minecraft.server.v1_8_R3.EntityEnderPearl(this.world, null);
/*  921 */         entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  922 */       } else if (org.bukkit.entity.ThrownPotion.class.isAssignableFrom(clazz)) {
/*  923 */         entity = new net.minecraft.server.v1_8_R3.EntityPotion(this.world, x, y, z, org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.POTION, 1)));
/*  924 */       } else if (org.bukkit.entity.Fireball.class.isAssignableFrom(clazz)) {
/*  925 */         if (org.bukkit.entity.SmallFireball.class.isAssignableFrom(clazz)) {
/*  926 */           entity = new net.minecraft.server.v1_8_R3.EntitySmallFireball(this.world);
/*  927 */         } else if (org.bukkit.entity.WitherSkull.class.isAssignableFrom(clazz)) {
/*  928 */           entity = new net.minecraft.server.v1_8_R3.EntityWitherSkull(this.world);
/*      */         } else {
/*  930 */           entity = new net.minecraft.server.v1_8_R3.EntityLargeFireball(this.world);
/*      */         }
/*  932 */         entity.setPositionRotation(x, y, z, yaw, pitch);
/*  933 */         Vector direction = location.getDirection().multiply(10);
/*  934 */         ((net.minecraft.server.v1_8_R3.EntityFireball)entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
/*      */       }
/*  936 */     } else if (org.bukkit.entity.Minecart.class.isAssignableFrom(clazz)) {
/*  937 */       if (org.bukkit.entity.minecart.PoweredMinecart.class.isAssignableFrom(clazz)) {
/*  938 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartFurnace(this.world, x, y, z);
/*  939 */       } else if (org.bukkit.entity.minecart.StorageMinecart.class.isAssignableFrom(clazz)) {
/*  940 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartChest(this.world, x, y, z);
/*  941 */       } else if (org.bukkit.entity.minecart.ExplosiveMinecart.class.isAssignableFrom(clazz)) {
/*  942 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartTNT(this.world, x, y, z);
/*  943 */       } else if (org.bukkit.entity.minecart.HopperMinecart.class.isAssignableFrom(clazz)) {
/*  944 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartHopper(this.world, x, y, z);
/*  945 */       } else if (org.bukkit.entity.minecart.SpawnerMinecart.class.isAssignableFrom(clazz)) {
/*  946 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner(this.world, x, y, z);
/*      */       } else {
/*  948 */         entity = new net.minecraft.server.v1_8_R3.EntityMinecartRideable(this.world, x, y, z);
/*      */       }
/*  950 */     } else if (org.bukkit.entity.EnderSignal.class.isAssignableFrom(clazz)) {
/*  951 */       entity = new net.minecraft.server.v1_8_R3.EntityEnderSignal(this.world, x, y, z);
/*  952 */     } else if (org.bukkit.entity.EnderCrystal.class.isAssignableFrom(clazz)) {
/*  953 */       entity = new net.minecraft.server.v1_8_R3.EntityEnderCrystal(this.world);
/*  954 */       entity.setPositionRotation(x, y, z, 0.0F, 0.0F);
/*  955 */     } else if (LivingEntity.class.isAssignableFrom(clazz)) {
/*  956 */       if (org.bukkit.entity.Chicken.class.isAssignableFrom(clazz)) {
/*  957 */         entity = new net.minecraft.server.v1_8_R3.EntityChicken(this.world);
/*  958 */       } else if (org.bukkit.entity.Cow.class.isAssignableFrom(clazz)) {
/*  959 */         if (org.bukkit.entity.MushroomCow.class.isAssignableFrom(clazz)) {
/*  960 */           entity = new net.minecraft.server.v1_8_R3.EntityMushroomCow(this.world);
/*      */         } else {
/*  962 */           entity = new net.minecraft.server.v1_8_R3.EntityCow(this.world);
/*      */         }
/*  964 */       } else if (org.bukkit.entity.Golem.class.isAssignableFrom(clazz)) {
/*  965 */         if (org.bukkit.entity.Snowman.class.isAssignableFrom(clazz)) {
/*  966 */           entity = new net.minecraft.server.v1_8_R3.EntitySnowman(this.world);
/*  967 */         } else if (org.bukkit.entity.IronGolem.class.isAssignableFrom(clazz)) {
/*  968 */           entity = new net.minecraft.server.v1_8_R3.EntityIronGolem(this.world);
/*      */         }
/*  970 */       } else if (org.bukkit.entity.Creeper.class.isAssignableFrom(clazz)) {
/*  971 */         entity = new net.minecraft.server.v1_8_R3.EntityCreeper(this.world);
/*  972 */       } else if (org.bukkit.entity.Ghast.class.isAssignableFrom(clazz)) {
/*  973 */         entity = new net.minecraft.server.v1_8_R3.EntityGhast(this.world);
/*  974 */       } else if (org.bukkit.entity.Pig.class.isAssignableFrom(clazz)) {
/*  975 */         entity = new net.minecraft.server.v1_8_R3.EntityPig(this.world);
/*  976 */       } else if (!Player.class.isAssignableFrom(clazz))
/*      */       {
/*  978 */         if (org.bukkit.entity.Sheep.class.isAssignableFrom(clazz)) {
/*  979 */           entity = new net.minecraft.server.v1_8_R3.EntitySheep(this.world);
/*  980 */         } else if (org.bukkit.entity.Horse.class.isAssignableFrom(clazz)) {
/*  981 */           entity = new net.minecraft.server.v1_8_R3.EntityHorse(this.world);
/*  982 */         } else if (org.bukkit.entity.Skeleton.class.isAssignableFrom(clazz)) {
/*  983 */           entity = new net.minecraft.server.v1_8_R3.EntitySkeleton(this.world);
/*  984 */         } else if (org.bukkit.entity.Slime.class.isAssignableFrom(clazz)) {
/*  985 */           if (org.bukkit.entity.MagmaCube.class.isAssignableFrom(clazz)) {
/*  986 */             entity = new net.minecraft.server.v1_8_R3.EntityMagmaCube(this.world);
/*      */           } else {
/*  988 */             entity = new net.minecraft.server.v1_8_R3.EntitySlime(this.world);
/*      */           }
/*  990 */         } else if (org.bukkit.entity.Spider.class.isAssignableFrom(clazz)) {
/*  991 */           if (org.bukkit.entity.CaveSpider.class.isAssignableFrom(clazz)) {
/*  992 */             entity = new net.minecraft.server.v1_8_R3.EntityCaveSpider(this.world);
/*      */           } else {
/*  994 */             entity = new net.minecraft.server.v1_8_R3.EntitySpider(this.world);
/*      */           }
/*  996 */         } else if (org.bukkit.entity.Squid.class.isAssignableFrom(clazz)) {
/*  997 */           entity = new net.minecraft.server.v1_8_R3.EntitySquid(this.world);
/*  998 */         } else if (org.bukkit.entity.Tameable.class.isAssignableFrom(clazz)) {
/*  999 */           if (org.bukkit.entity.Wolf.class.isAssignableFrom(clazz)) {
/* 1000 */             entity = new net.minecraft.server.v1_8_R3.EntityWolf(this.world);
/* 1001 */           } else if (org.bukkit.entity.Ocelot.class.isAssignableFrom(clazz)) {
/* 1002 */             entity = new net.minecraft.server.v1_8_R3.EntityOcelot(this.world);
/*      */           }
/* 1004 */         } else if (org.bukkit.entity.PigZombie.class.isAssignableFrom(clazz)) {
/* 1005 */           entity = new net.minecraft.server.v1_8_R3.EntityPigZombie(this.world);
/* 1006 */         } else if (org.bukkit.entity.Zombie.class.isAssignableFrom(clazz)) {
/* 1007 */           entity = new net.minecraft.server.v1_8_R3.EntityZombie(this.world);
/* 1008 */         } else if (org.bukkit.entity.Giant.class.isAssignableFrom(clazz)) {
/* 1009 */           entity = new net.minecraft.server.v1_8_R3.EntityGiantZombie(this.world);
/* 1010 */         } else if (org.bukkit.entity.Silverfish.class.isAssignableFrom(clazz)) {
/* 1011 */           entity = new net.minecraft.server.v1_8_R3.EntitySilverfish(this.world);
/* 1012 */         } else if (org.bukkit.entity.Enderman.class.isAssignableFrom(clazz)) {
/* 1013 */           entity = new net.minecraft.server.v1_8_R3.EntityEnderman(this.world);
/* 1014 */         } else if (org.bukkit.entity.Blaze.class.isAssignableFrom(clazz)) {
/* 1015 */           entity = new net.minecraft.server.v1_8_R3.EntityBlaze(this.world);
/* 1016 */         } else if (org.bukkit.entity.Villager.class.isAssignableFrom(clazz)) {
/* 1017 */           entity = new net.minecraft.server.v1_8_R3.EntityVillager(this.world);
/* 1018 */         } else if (org.bukkit.entity.Witch.class.isAssignableFrom(clazz)) {
/* 1019 */           entity = new net.minecraft.server.v1_8_R3.EntityWitch(this.world);
/* 1020 */         } else if (org.bukkit.entity.Wither.class.isAssignableFrom(clazz)) {
/* 1021 */           entity = new net.minecraft.server.v1_8_R3.EntityWither(this.world);
/* 1022 */         } else if (org.bukkit.entity.ComplexLivingEntity.class.isAssignableFrom(clazz)) {
/* 1023 */           if (org.bukkit.entity.EnderDragon.class.isAssignableFrom(clazz)) {
/* 1024 */             entity = new net.minecraft.server.v1_8_R3.EntityEnderDragon(this.world);
/*      */           }
/* 1026 */         } else if (org.bukkit.entity.Ambient.class.isAssignableFrom(clazz)) {
/* 1027 */           if (org.bukkit.entity.Bat.class.isAssignableFrom(clazz)) {
/* 1028 */             entity = new net.minecraft.server.v1_8_R3.EntityBat(this.world);
/*      */           }
/* 1030 */         } else if (org.bukkit.entity.Rabbit.class.isAssignableFrom(clazz)) {
/* 1031 */           entity = new net.minecraft.server.v1_8_R3.EntityRabbit(this.world);
/* 1032 */         } else if (org.bukkit.entity.Endermite.class.isAssignableFrom(clazz)) {
/* 1033 */           entity = new net.minecraft.server.v1_8_R3.EntityEndermite(this.world);
/* 1034 */         } else if (org.bukkit.entity.Guardian.class.isAssignableFrom(clazz)) {
/* 1035 */           entity = new net.minecraft.server.v1_8_R3.EntityGuardian(this.world);
/* 1036 */         } else if (org.bukkit.entity.ArmorStand.class.isAssignableFrom(clazz)) {
/* 1037 */           entity = new net.minecraft.server.v1_8_R3.EntityArmorStand(this.world, x, y, z);
/*      */         }
/*      */       }
/* 1040 */       if (entity != null) {
/* 1041 */         entity.setLocation(x, y, z, yaw, pitch);
/*      */       }
/* 1043 */     } else if (org.bukkit.entity.Hanging.class.isAssignableFrom(clazz)) {
/* 1044 */       org.bukkit.block.Block block = getBlockAt(location);
/* 1045 */       BlockFace face = BlockFace.SELF;
/*      */       
/* 1047 */       int width = 16;
/* 1048 */       int height = 16;
/*      */       
/* 1050 */       if (org.bukkit.entity.ItemFrame.class.isAssignableFrom(clazz)) {
/* 1051 */         width = 12;
/* 1052 */         height = 12;
/* 1053 */       } else if (org.bukkit.entity.LeashHitch.class.isAssignableFrom(clazz)) {
/* 1054 */         width = 9;
/* 1055 */         height = 9;
/*      */       }
/*      */       
/* 1058 */       BlockFace[] faces = { BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH };
/* 1059 */       BlockPosition pos = new BlockPosition((int)x, (int)y, (int)z);
/* 1060 */       BlockFace[] arrayOfBlockFace1; int i = (arrayOfBlockFace1 = faces).length; for (int j = 0; j < i; j++) { BlockFace dir = arrayOfBlockFace1[j];
/* 1061 */         net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(block.getRelative(dir));
/* 1062 */         if ((nmsBlock.getMaterial().isBuildable()) || (net.minecraft.server.v1_8_R3.BlockDiodeAbstract.d(nmsBlock))) {
/* 1063 */           boolean taken = false;
/* 1064 */           AxisAlignedBB bb = EntityHanging.calculateBoundingBox(pos, CraftBlock.blockFaceToNotch(dir).opposite(), width, height);
/* 1065 */           List<net.minecraft.server.v1_8_R3.Entity> list = this.world.getEntities(null, bb);
/* 1066 */           for (java.util.Iterator<net.minecraft.server.v1_8_R3.Entity> it = list.iterator(); (!taken) && (it.hasNext());) {
/* 1067 */             net.minecraft.server.v1_8_R3.Entity e = (net.minecraft.server.v1_8_R3.Entity)it.next();
/* 1068 */             if ((e instanceof EntityHanging)) {
/* 1069 */               taken = true;
/*      */             }
/*      */           }
/*      */           
/* 1073 */           if (!taken) {
/* 1074 */             face = dir;
/* 1075 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1080 */       EnumDirection dir = CraftBlock.blockFaceToNotch(face).opposite();
/*      */       
/* 1082 */       if (org.bukkit.entity.Painting.class.isAssignableFrom(clazz)) {
/* 1083 */         entity = new net.minecraft.server.v1_8_R3.EntityPainting(this.world, new BlockPosition((int)x, (int)y, (int)z), dir);
/* 1084 */       } else if (org.bukkit.entity.ItemFrame.class.isAssignableFrom(clazz)) {
/* 1085 */         entity = new net.minecraft.server.v1_8_R3.EntityItemFrame(this.world, new BlockPosition((int)x, (int)y, (int)z), dir);
/* 1086 */       } else if (org.bukkit.entity.LeashHitch.class.isAssignableFrom(clazz)) {
/* 1087 */         entity = new net.minecraft.server.v1_8_R3.EntityLeash(this.world, new BlockPosition((int)x, (int)y, (int)z));
/* 1088 */         entity.attachedToPlayer = true;
/*      */       }
/*      */       
/* 1091 */       if ((entity != null) && (!((EntityHanging)entity).survives())) {
/* 1092 */         throw new IllegalArgumentException("Cannot spawn hanging entity for " + clazz.getName() + " at " + location);
/*      */       }
/* 1094 */     } else if (org.bukkit.entity.TNTPrimed.class.isAssignableFrom(clazz)) {
/* 1095 */       entity = new net.minecraft.server.v1_8_R3.EntityTNTPrimed(this.world, x, y, z, null);
/* 1096 */     } else if (org.bukkit.entity.ExperienceOrb.class.isAssignableFrom(clazz)) {
/* 1097 */       entity = new net.minecraft.server.v1_8_R3.EntityExperienceOrb(this.world, x, y, z, 0);
/* 1098 */     } else if (org.bukkit.entity.Weather.class.isAssignableFrom(clazz))
/*      */     {
/* 1100 */       if (LightningStrike.class.isAssignableFrom(clazz)) {
/* 1101 */         entity = new EntityLightning(this.world, x, y, z);
/*      */       }
/*      */     }
/* 1104 */     else if (org.bukkit.entity.Firework.class.isAssignableFrom(clazz)) {
/* 1105 */       entity = new net.minecraft.server.v1_8_R3.EntityFireworks(this.world, x, y, z, null);
/*      */     }
/*      */     
/* 1108 */     if (entity != null)
/*      */     {
/* 1110 */       if ((entity instanceof net.minecraft.server.v1_8_R3.EntityOcelot))
/*      */       {
/* 1112 */         ((net.minecraft.server.v1_8_R3.EntityOcelot)entity).spawnBonus = false;
/*      */       }
/*      */       
/* 1115 */       return entity;
/*      */     }
/*      */     
/* 1118 */     throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
/*      */   }
/*      */   
/*      */   public <T extends org.bukkit.entity.Entity> T addEntity(net.minecraft.server.v1_8_R3.Entity entity, CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException
/*      */   {
/* 1123 */     com.google.common.base.Preconditions.checkArgument(entity != null, "Cannot spawn null entity");
/*      */     
/* 1125 */     if ((entity instanceof EntityInsentient)) {
/* 1126 */       ((EntityInsentient)entity).prepare(getHandle().E(new BlockPosition(entity)), null);
/*      */     }
/*      */     
/* 1129 */     this.world.addEntity(entity, reason);
/* 1130 */     return entity.getBukkitEntity();
/*      */   }
/*      */   
/*      */   public <T extends org.bukkit.entity.Entity> T spawn(Location location, Class<T> clazz, CreatureSpawnEvent.SpawnReason reason) throws IllegalArgumentException {
/* 1134 */     net.minecraft.server.v1_8_R3.Entity entity = createEntity(location, clazz);
/*      */     
/* 1136 */     return addEntity(entity, reason);
/*      */   }
/*      */   
/*      */   public org.bukkit.ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
/* 1140 */     return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
/*      */   }
/*      */   
/*      */   public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
/* 1144 */     this.world.setSpawnFlags(allowMonsters, allowAnimals);
/*      */   }
/*      */   
/*      */   public boolean getAllowAnimals() {
/* 1148 */     return this.world.allowAnimals;
/*      */   }
/*      */   
/*      */   public boolean getAllowMonsters() {
/* 1152 */     return this.world.allowMonsters;
/*      */   }
/*      */   
/*      */   public int getMaxHeight() {
/* 1156 */     return this.world.getHeight();
/*      */   }
/*      */   
/*      */   public int getSeaLevel() {
/* 1160 */     return 64;
/*      */   }
/*      */   
/*      */   public boolean getKeepSpawnInMemory() {
/* 1164 */     return this.world.keepSpawnInMemory;
/*      */   }
/*      */   
/*      */   public void setKeepSpawnInMemory(boolean keepLoaded) {
/* 1168 */     this.world.keepSpawnInMemory = keepLoaded;
/*      */     
/* 1170 */     BlockPosition chunkcoordinates = this.world.getSpawn();
/* 1171 */     int chunkCoordX = chunkcoordinates.getX() >> 4;
/* 1172 */     int chunkCoordZ = chunkcoordinates.getZ() >> 4;
/*      */     
/* 1174 */     for (int x = -12; x <= 12; x++) {
/* 1175 */       for (int z = -12; z <= 12; z++) {
/* 1176 */         if (keepLoaded) {
/* 1177 */           loadChunk(chunkCoordX + x, chunkCoordZ + z);
/*      */         }
/* 1179 */         else if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
/* 1180 */           if ((getHandle().getChunkAt(chunkCoordX + x, chunkCoordZ + z) instanceof net.minecraft.server.v1_8_R3.EmptyChunk)) {
/* 1181 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
/*      */           } else {
/* 1183 */             unloadChunk(chunkCoordX + x, chunkCoordZ + z);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1193 */     return getUID().hashCode();
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj)
/*      */   {
/* 1198 */     if (obj == null) {
/* 1199 */       return false;
/*      */     }
/* 1201 */     if (getClass() != obj.getClass()) {
/* 1202 */       return false;
/*      */     }
/*      */     
/* 1205 */     CraftWorld other = (CraftWorld)obj;
/*      */     
/* 1207 */     return getUID() == other.getUID();
/*      */   }
/*      */   
/*      */   public java.io.File getWorldFolder() {
/* 1211 */     return ((net.minecraft.server.v1_8_R3.WorldNBTStorage)this.world.getDataManager()).getDirectory();
/*      */   }
/*      */   
/*      */   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
/* 1215 */     org.bukkit.plugin.messaging.StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);
/*      */     
/* 1217 */     for (Player player : getPlayers()) {
/* 1218 */       player.sendPluginMessage(source, channel, message);
/*      */     }
/*      */   }
/*      */   
/*      */   public java.util.Set<String> getListeningPluginChannels() {
/* 1223 */     java.util.Set<String> result = new java.util.HashSet();
/*      */     
/* 1225 */     for (Player player : getPlayers()) {
/* 1226 */       result.addAll(player.getListeningPluginChannels());
/*      */     }
/*      */     
/* 1229 */     return result;
/*      */   }
/*      */   
/*      */   public org.bukkit.WorldType getWorldType() {
/* 1233 */     return org.bukkit.WorldType.getByName(this.world.getWorldData().getType().name());
/*      */   }
/*      */   
/*      */   public boolean canGenerateStructures() {
/* 1237 */     return this.world.getWorldData().shouldGenerateMapFeatures();
/*      */   }
/*      */   
/*      */   public long getTicksPerAnimalSpawns() {
/* 1241 */     return this.world.ticksPerAnimalSpawns;
/*      */   }
/*      */   
/*      */   public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
/* 1245 */     this.world.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
/*      */   }
/*      */   
/*      */   public long getTicksPerMonsterSpawns() {
/* 1249 */     return this.world.ticksPerMonsterSpawns;
/*      */   }
/*      */   
/*      */   public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
/* 1253 */     this.world.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
/*      */   }
/*      */   
/*      */   public void setMetadata(String metadataKey, org.bukkit.metadata.MetadataValue newMetadataValue) {
/* 1257 */     this.server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*      */   }
/*      */   
/*      */   public List<org.bukkit.metadata.MetadataValue> getMetadata(String metadataKey) {
/* 1261 */     return this.server.getWorldMetadata().getMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public boolean hasMetadata(String metadataKey) {
/* 1265 */     return this.server.getWorldMetadata().hasMetadata(this, metadataKey);
/*      */   }
/*      */   
/*      */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 1269 */     this.server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*      */   }
/*      */   
/*      */   public int getMonsterSpawnLimit() {
/* 1273 */     if (this.monsterSpawn < 0) {
/* 1274 */       return this.server.getMonsterSpawnLimit();
/*      */     }
/*      */     
/* 1277 */     return this.monsterSpawn;
/*      */   }
/*      */   
/*      */   public void setMonsterSpawnLimit(int limit) {
/* 1281 */     this.monsterSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getAnimalSpawnLimit() {
/* 1285 */     if (this.animalSpawn < 0) {
/* 1286 */       return this.server.getAnimalSpawnLimit();
/*      */     }
/*      */     
/* 1289 */     return this.animalSpawn;
/*      */   }
/*      */   
/*      */   public void setAnimalSpawnLimit(int limit) {
/* 1293 */     this.animalSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getWaterAnimalSpawnLimit() {
/* 1297 */     if (this.waterAnimalSpawn < 0) {
/* 1298 */       return this.server.getWaterAnimalSpawnLimit();
/*      */     }
/*      */     
/* 1301 */     return this.waterAnimalSpawn;
/*      */   }
/*      */   
/*      */   public void setWaterAnimalSpawnLimit(int limit) {
/* 1305 */     this.waterAnimalSpawn = limit;
/*      */   }
/*      */   
/*      */   public int getAmbientSpawnLimit() {
/* 1309 */     if (this.ambientSpawn < 0) {
/* 1310 */       return this.server.getAmbientSpawnLimit();
/*      */     }
/*      */     
/* 1313 */     return this.ambientSpawn;
/*      */   }
/*      */   
/*      */   public void setAmbientSpawnLimit(int limit) {
/* 1317 */     this.ambientSpawn = limit;
/*      */   }
/*      */   
/*      */   public void playSound(Location loc, org.bukkit.Sound sound, float volume, float pitch)
/*      */   {
/* 1322 */     if ((loc == null) || (sound == null)) { return;
/*      */     }
/* 1324 */     double x = loc.getX();
/* 1325 */     double y = loc.getY();
/* 1326 */     double z = loc.getZ();
/*      */     
/* 1328 */     getHandle().makeSound(x, y, z, CraftSound.getSound(sound), volume, pitch);
/*      */   }
/*      */   
/*      */   public String getGameRuleValue(String rule) {
/* 1332 */     return getHandle().getGameRules().get(rule);
/*      */   }
/*      */   
/*      */   public boolean setGameRuleValue(String rule, String value)
/*      */   {
/* 1337 */     if ((rule == null) || (value == null)) { return false;
/*      */     }
/* 1339 */     if (!isGameRule(rule)) { return false;
/*      */     }
/* 1341 */     getHandle().getGameRules().set(rule, value);
/* 1342 */     return true;
/*      */   }
/*      */   
/*      */   public String[] getGameRules() {
/* 1346 */     return getHandle().getGameRules().getGameRules();
/*      */   }
/*      */   
/*      */   public boolean isGameRule(String rule) {
/* 1350 */     return getHandle().getGameRules().contains(rule);
/*      */   }
/*      */   
/*      */   public org.bukkit.WorldBorder getWorldBorder()
/*      */   {
/* 1355 */     if (this.worldBorder == null) {
/* 1356 */       this.worldBorder = new CraftWorldBorder(this);
/*      */     }
/*      */     
/* 1359 */     return this.worldBorder;
/*      */   }
/*      */   
/*      */   public void processChunkGC() {
/* 1363 */     this.chunkGCTickCount += 1;
/*      */     
/* 1365 */     if ((this.chunkLoadCount >= this.server.chunkGCLoadThresh) && (this.server.chunkGCLoadThresh > 0)) {
/* 1366 */       this.chunkLoadCount = 0;
/* 1367 */     } else if ((this.chunkGCTickCount >= this.server.chunkGCPeriod) && (this.server.chunkGCPeriod > 0)) {
/* 1368 */       this.chunkGCTickCount = 0;
/*      */     } else {
/* 1370 */       return;
/*      */     }
/*      */     
/* 1373 */     ChunkProviderServer cps = this.world.chunkProviderServer;
/* 1374 */     for (net.minecraft.server.v1_8_R3.Chunk chunk : cps.chunks.values())
/*      */     {
/* 1376 */       if (!isChunkInUse(chunk.locX, chunk.locZ))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1381 */         if (!cps.unloadQueue.contains(chunk.locX, chunk.locZ))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1386 */           cps.queueUnload(chunk.locX, chunk.locZ); } }
/*      */     }
/*      */   }
/*      */   
/* 1390 */   private final World.Spigot spigot = new World.Spigot()
/*      */   {
/*      */ 
/*      */     public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius)
/*      */     {
/* 1395 */       Validate.notNull(location, "Location cannot be null");
/* 1396 */       Validate.notNull(effect, "Effect cannot be null");
/* 1397 */       Validate.notNull(location.getWorld(), "World cannot be null");
/*      */       net.minecraft.server.v1_8_R3.Packet packet;
/* 1399 */       EnumParticle p; net.minecraft.server.v1_8_R3.Packet packet; if (effect.getType() != org.bukkit.Effect.Type.PARTICLE)
/*      */       {
/* 1401 */         int packetData = effect.getId();
/* 1402 */         packet = new net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent(packetData, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), id, false);
/*      */       }
/*      */       else {
/* 1405 */         EnumParticle particle = null;
/* 1406 */         int[] extra = null;
/* 1407 */         EnumParticle[] arrayOfEnumParticle; int i = (arrayOfEnumParticle = EnumParticle.values()).length; for (int j = 0; j < i; j++) { p = arrayOfEnumParticle[j];
/*      */           
/* 1409 */           if (effect.getName().startsWith(p.b().replace("_", "")))
/*      */           {
/* 1411 */             particle = p;
/* 1412 */             if (effect.getData() == null)
/*      */               break;
/* 1414 */             if (effect.getData().equals(org.bukkit.Material.class))
/*      */             {
/* 1416 */               extra = new int[] { id };
/* 1417 */               break;
/*      */             }
/* 1419 */             extra = new int[] { data << 12 | id & 0xFFF };
/*      */             
/*      */ 
/* 1422 */             break;
/*      */           }
/*      */         }
/* 1425 */         if (extra == null)
/*      */         {
/* 1427 */           extra = new int[0];
/*      */         }
/* 1429 */         packet = new net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles(particle, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount, extra);
/*      */       }
/*      */       
/* 1432 */       radius *= radius;
/* 1433 */       for (Player player : CraftWorld.this.getPlayers())
/*      */       {
/* 1435 */         if (((CraftPlayer)player).getHandle().playerConnection != null)
/*      */         {
/*      */ 
/*      */ 
/* 1439 */           if (location.getWorld().equals(player.getWorld()))
/*      */           {
/*      */ 
/*      */ 
/* 1443 */             int distance = (int)player.getLocation().distanceSquared(location);
/* 1444 */             if (distance <= radius)
/*      */             {
/* 1446 */               ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public void playEffect(Location location, Effect effect) {
/* 1454 */       CraftWorld.this.playEffect(location, effect, 0);
/*      */     }
/*      */     
/*      */ 
/*      */     public LightningStrike strikeLightning(Location loc, boolean isSilent)
/*      */     {
/* 1460 */       EntityLightning lightning = new EntityLightning(CraftWorld.this.world, loc.getX(), loc.getY(), loc.getZ(), false, isSilent);
/* 1461 */       CraftWorld.this.world.strikeLightning(lightning);
/* 1462 */       return new CraftLightningStrike(CraftWorld.this.server, lightning);
/*      */     }
/*      */     
/*      */ 
/*      */     public LightningStrike strikeLightningEffect(Location loc, boolean isSilent)
/*      */     {
/* 1468 */       EntityLightning lightning = new EntityLightning(CraftWorld.this.world, loc.getX(), loc.getY(), loc.getZ(), true, isSilent);
/* 1469 */       CraftWorld.this.world.strikeLightning(lightning);
/* 1470 */       return new CraftLightningStrike(CraftWorld.this.server, lightning);
/*      */     }
/*      */   };
/*      */   
/*      */   public World.Spigot spigot()
/*      */   {
/* 1476 */     return this.spigot;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */