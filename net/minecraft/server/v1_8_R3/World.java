/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import gnu.trove.map.hash.TLongShortHashMap;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.World.Environment;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*      */ import org.bukkit.event.Cancellable;
/*      */ import org.bukkit.event.block.BlockCanBuildEvent;
/*      */ import org.bukkit.event.block.BlockPhysicsEvent;
/*      */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.spigotmc.ActivationRange;
/*      */ import org.spigotmc.AsyncCatcher;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.SpigotWorldConfig;
/*      */ import org.spigotmc.TickLimiter;
/*      */ 
/*      */ public abstract class World implements IBlockAccess
/*      */ {
/*   38 */   private int a = 63;
/*      */   
/*      */   protected boolean e;
/*   41 */   public final List<Entity> entityList = new ArrayList()
/*      */   {
/*      */ 
/*      */     public Entity remove(int index)
/*      */     {
/*   46 */       guard();
/*   47 */       return (Entity)super.remove(index);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/*   53 */       guard();
/*   54 */       return super.remove(o);
/*      */     }
/*      */     
/*      */     private void guard()
/*      */     {
/*   59 */       if (World.this.guardEntityList)
/*      */       {
/*   61 */         throw new java.util.ConcurrentModificationException();
/*      */       }
/*      */     }
/*      */   };
/*      */   
/*   66 */   protected final List<Entity> g = Lists.newArrayList();
/*   67 */   public final List<TileEntity> h = Lists.newArrayList();
/*   68 */   public final List<TileEntity> tileEntityList = Lists.newArrayList();
/*   69 */   private final List<TileEntity> b = Lists.newArrayList();
/*   70 */   private final List<TileEntity> c = Lists.newArrayList();
/*   71 */   public final List<EntityHuman> players = Lists.newArrayList();
/*   72 */   public final List<Entity> k = Lists.newArrayList();
/*   73 */   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
/*   74 */   private long d = 16777215L;
/*      */   private int I;
/*   76 */   protected int m = new Random().nextInt();
/*   77 */   protected final int n = 1013904223;
/*      */   protected float o;
/*      */   protected float p;
/*      */   protected float q;
/*      */   protected float r;
/*      */   private int J;
/*   83 */   public final Random random = new Random();
/*      */   public WorldProvider worldProvider;
/*   85 */   protected List<IWorldAccess> u = Lists.newArrayList();
/*      */   protected IChunkProvider chunkProvider;
/*      */   protected final IDataManager dataManager;
/*      */   public WorldData worldData;
/*      */   protected boolean isLoading;
/*      */   public PersistentCollection worldMaps;
/*      */   protected PersistentVillage villages;
/*      */   public final MethodProfiler methodProfiler;
/*   93 */   private final Calendar K = Calendar.getInstance();
/*   94 */   public Scoreboard scoreboard = new Scoreboard();
/*      */   
/*      */   public final boolean isClientSide;
/*      */   
/*      */   private int L;
/*      */   
/*      */   public boolean allowMonsters;
/*      */   
/*      */   public boolean allowAnimals;
/*      */   private boolean M;
/*      */   private final WorldBorder N;
/*      */   int[] H;
/*      */   private final CraftWorld world;
/*      */   public boolean pvpMode;
/*  108 */   public boolean keepSpawnInMemory = true;
/*      */   
/*      */   public ChunkGenerator generator;
/*  111 */   public boolean captureBlockStates = false;
/*  112 */   public boolean captureTreeGeneration = false;
/*  113 */   public ArrayList<BlockState> capturedBlockStates = new ArrayList()
/*      */   {
/*      */     public boolean add(BlockState blockState) {
/*  116 */       Iterator<BlockState> blockStateIterator = iterator();
/*  117 */       while (blockStateIterator.hasNext()) {
/*  118 */         BlockState blockState1 = (BlockState)blockStateIterator.next();
/*  119 */         if (blockState1.getLocation().equals(blockState.getLocation())) {
/*  120 */           return false;
/*      */         }
/*      */       }
/*      */       
/*  124 */       return super.add(blockState);
/*      */     }
/*      */   };
/*      */   
/*      */   public long ticksPerAnimalSpawns;
/*      */   
/*      */   public long ticksPerMonsterSpawns;
/*      */   public boolean populating;
/*      */   private int tickPosition;
/*      */   private boolean guardEntityList;
/*      */   protected final TLongShortHashMap chunkTickList;
/*  135 */   protected float growthOdds = 100.0F;
/*  136 */   protected float modifiedOdds = 100.0F;
/*      */   private final byte chunkTickRadius;
/*      */   public static boolean haveWeSilencedAPhysicsCrash;
/*      */   public static String blockLocation;
/*      */   private TickLimiter entityLimiter;
/*      */   private TickLimiter tileLimiter;
/*      */   private int tileTickPosition;
/*      */   public final SpigotWorldConfig spigotConfig;
/*      */   public final SpigotTimings.WorldTimingsHandler timings;
/*      */   
/*  146 */   public static long chunkToKey(int x, int z) { long k = (x & 0xFFFF0000) << 16 | (x & 0xFFFF) << 0;
/*  147 */     k |= (z & 0xFFFF0000) << 32 | (z & 0xFFFF) << 16;
/*  148 */     return k;
/*      */   }
/*      */   
/*      */   public static int keyToX(long k)
/*      */   {
/*  153 */     return (int)(k >> 16 & 0xFFFFFFFFFFFF0000 | k & 0xFFFF);
/*      */   }
/*      */   
/*      */   public static int keyToZ(long k)
/*      */   {
/*  158 */     return (int)(k >> 32 & 0xFFFF0000 | k >> 16 & 0xFFFF);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CraftWorld getWorld()
/*      */   {
/*  167 */     return this.world;
/*      */   }
/*      */   
/*      */   public CraftServer getServer() {
/*  171 */     return (CraftServer)org.bukkit.Bukkit.getServer();
/*      */   }
/*      */   
/*      */   public Chunk getChunkIfLoaded(int x, int z) {
/*  175 */     return ((ChunkProviderServer)this.chunkProvider).getChunkIfLoaded(x, z);
/*      */   }
/*      */   
/*      */   protected World(IDataManager idatamanager, WorldData worlddata, WorldProvider worldprovider, MethodProfiler methodprofiler, boolean flag, ChunkGenerator gen, World.Environment env) {
/*  179 */     this.spigotConfig = new SpigotWorldConfig(worlddata.getName());
/*  180 */     this.generator = gen;
/*  181 */     this.world = new CraftWorld((WorldServer)this, gen, env);
/*  182 */     this.ticksPerAnimalSpawns = getServer().getTicksPerAnimalSpawns();
/*  183 */     this.ticksPerMonsterSpawns = getServer().getTicksPerMonsterSpawns();
/*      */     
/*      */ 
/*  186 */     this.chunkTickRadius = ((byte)(getServer().getViewDistance() < 7 ? getServer().getViewDistance() : 7));
/*  187 */     this.chunkTickList = new TLongShortHashMap(this.spigotConfig.chunksPerTick * 5, 0.7F, Long.MIN_VALUE, (short)Short.MIN_VALUE);
/*  188 */     this.chunkTickList.setAutoCompactionFactor(0.0F);
/*      */     
/*      */ 
/*  191 */     this.L = this.random.nextInt(12000);
/*  192 */     this.allowMonsters = true;
/*  193 */     this.allowAnimals = true;
/*  194 */     this.H = new int[32768];
/*  195 */     this.dataManager = idatamanager;
/*  196 */     this.methodProfiler = methodprofiler;
/*  197 */     this.worldData = worlddata;
/*  198 */     this.worldProvider = worldprovider;
/*  199 */     this.isClientSide = flag;
/*  200 */     this.N = worldprovider.getWorldBorder();
/*      */     
/*      */ 
/*  203 */     this.N.a(new IWorldBorderListener() {
/*      */       public void a(WorldBorder worldborder, double d0) {
/*  205 */         World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE), World.this);
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, double d0, double d1, long i) {
/*  209 */         World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE), World.this);
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, double d0, double d1) {
/*  213 */         World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER), World.this);
/*      */       }
/*      */       
/*      */       public void a(WorldBorder worldborder, int i) {
/*  217 */         World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME), World.this);
/*      */       }
/*      */       
/*      */       public void b(WorldBorder worldborder, int i) {
/*  221 */         World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS), World.this);
/*      */       }
/*      */       
/*      */       public void b(WorldBorder worldborder, double d0) {}
/*      */       
/*      */       public void c(WorldBorder worldborder, double d0) {}
/*  227 */     });
/*  228 */     getServer().addWorld(this.world);
/*      */     
/*  230 */     this.timings = new SpigotTimings.WorldTimingsHandler(this);
/*  231 */     this.entityLimiter = new TickLimiter(this.spigotConfig.entityMaxTickTime);
/*  232 */     this.tileLimiter = new TickLimiter(this.spigotConfig.tileMaxTickTime);
/*      */   }
/*      */   
/*      */   public World b() {
/*  236 */     return this;
/*      */   }
/*      */   
/*      */   public BiomeBase getBiome(final BlockPosition blockposition) {
/*  240 */     if (isLoaded(blockposition)) {
/*  241 */       Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */       try
/*      */       {
/*  244 */         return chunk.getBiome(blockposition, this.worldProvider.m());
/*      */       } catch (Throwable throwable) {
/*  246 */         CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
/*  247 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Coordinates of biome request");
/*      */         
/*  249 */         crashreportsystemdetails.a("Location", new Callable() {
/*      */           public String a() throws Exception {
/*  251 */             return CrashReportSystemDetails.a(blockposition);
/*      */           }
/*      */           
/*      */           public Object call() throws Exception {
/*  255 */             return a();
/*      */           }
/*  257 */         });
/*  258 */         throw new ReportedException(crashreport);
/*      */       }
/*      */     }
/*  261 */     return this.worldProvider.m().getBiome(blockposition, BiomeBase.PLAINS);
/*      */   }
/*      */   
/*      */   public WorldChunkManager getWorldChunkManager()
/*      */   {
/*  266 */     return this.worldProvider.m();
/*      */   }
/*      */   
/*      */   protected abstract IChunkProvider k();
/*      */   
/*      */   public void a(WorldSettings worldsettings) {
/*  272 */     this.worldData.d(true);
/*      */   }
/*      */   
/*      */ 
/*      */   public Block c(BlockPosition blockposition)
/*      */   {
/*  278 */     for (BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), F(), blockposition.getZ()); !isEmpty(blockposition1.up()); blockposition1 = blockposition1.up()) {}
/*      */     
/*      */ 
/*      */ 
/*  282 */     return getType(blockposition1).getBlock();
/*      */   }
/*      */   
/*      */   private boolean isValidLocation(BlockPosition blockposition) {
/*  286 */     return (blockposition.getX() >= -30000000) && (blockposition.getZ() >= -30000000) && (blockposition.getX() < 30000000) && (blockposition.getZ() < 30000000) && (blockposition.getY() >= 0) && (blockposition.getY() < 256);
/*      */   }
/*      */   
/*      */   public boolean isEmpty(BlockPosition blockposition) {
/*  290 */     return getType(blockposition).getBlock().getMaterial() == Material.AIR;
/*      */   }
/*      */   
/*      */   public boolean isLoaded(BlockPosition blockposition) {
/*  294 */     return a(blockposition, true);
/*      */   }
/*      */   
/*      */   public boolean a(BlockPosition blockposition, boolean flag) {
/*  298 */     return !isValidLocation(blockposition) ? false : isChunkLoaded(blockposition.getX() >> 4, blockposition.getZ() >> 4, flag);
/*      */   }
/*      */   
/*      */   public boolean areChunksLoaded(BlockPosition blockposition, int i) {
/*  302 */     return areChunksLoaded(blockposition, i, true);
/*      */   }
/*      */   
/*      */   public boolean areChunksLoaded(BlockPosition blockposition, int i, boolean flag) {
/*  306 */     return isAreaLoaded(blockposition.getX() - i, blockposition.getY() - i, blockposition.getZ() - i, blockposition.getX() + i, blockposition.getY() + i, blockposition.getZ() + i, flag);
/*      */   }
/*      */   
/*      */   public boolean areChunksLoadedBetween(BlockPosition blockposition, BlockPosition blockposition1) {
/*  310 */     return areChunksLoadedBetween(blockposition, blockposition1, true);
/*      */   }
/*      */   
/*      */   public boolean areChunksLoadedBetween(BlockPosition blockposition, BlockPosition blockposition1, boolean flag) {
/*  314 */     return isAreaLoaded(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), flag);
/*      */   }
/*      */   
/*      */   public boolean a(StructureBoundingBox structureboundingbox) {
/*  318 */     return b(structureboundingbox, true);
/*      */   }
/*      */   
/*      */   public boolean b(StructureBoundingBox structureboundingbox, boolean flag) {
/*  322 */     return isAreaLoaded(structureboundingbox.a, structureboundingbox.b, structureboundingbox.c, structureboundingbox.d, structureboundingbox.e, structureboundingbox.f, flag);
/*      */   }
/*      */   
/*      */   private boolean isAreaLoaded(int i, int j, int k, int l, int i1, int j1, boolean flag) {
/*  326 */     if ((i1 >= 0) && (j < 256)) {
/*  327 */       i >>= 4;
/*  328 */       k >>= 4;
/*  329 */       l >>= 4;
/*  330 */       j1 >>= 4;
/*      */       
/*  332 */       for (int k1 = i; k1 <= l; k1++) {
/*  333 */         for (int l1 = k; l1 <= j1; l1++) {
/*  334 */           if (!isChunkLoaded(k1, l1, flag)) {
/*  335 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  340 */       return true;
/*      */     }
/*  342 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isChunkLoaded(int i, int j, boolean flag)
/*      */   {
/*  347 */     return (this.chunkProvider.isChunkLoaded(i, j)) && ((flag) || (!this.chunkProvider.getOrCreateChunk(i, j).isEmpty()));
/*      */   }
/*      */   
/*      */   public Chunk getChunkAtWorldCoords(BlockPosition blockposition) {
/*  351 */     return getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4);
/*      */   }
/*      */   
/*      */   public Chunk getChunkAt(int i, int j) {
/*  355 */     return this.chunkProvider.getOrCreateChunk(i, j);
/*      */   }
/*      */   
/*      */   public boolean setTypeAndData(BlockPosition blockposition, IBlockData iblockdata, int i)
/*      */   {
/*  360 */     if (this.captureTreeGeneration) {
/*  361 */       BlockState blockstate = null;
/*  362 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  363 */       while (it.hasNext()) {
/*  364 */         BlockState previous = (BlockState)it.next();
/*  365 */         if ((previous.getX() == blockposition.getX()) && (previous.getY() == blockposition.getY()) && (previous.getZ() == blockposition.getZ())) {
/*  366 */           blockstate = previous;
/*  367 */           it.remove();
/*  368 */           break;
/*      */         }
/*      */       }
/*  371 */       if (blockstate == null) {
/*  372 */         blockstate = CraftBlockState.getBlockState(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), i);
/*      */       }
/*  374 */       blockstate.setTypeId(CraftMagicNumbers.getId(iblockdata.getBlock()));
/*  375 */       blockstate.setRawData((byte)iblockdata.getBlock().toLegacyData(iblockdata));
/*  376 */       this.capturedBlockStates.add(blockstate);
/*  377 */       return true;
/*      */     }
/*      */     
/*  380 */     if (!isValidLocation(blockposition))
/*  381 */       return false;
/*  382 */     if ((!this.isClientSide) && (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)) {
/*  383 */       return false;
/*      */     }
/*  385 */     Chunk chunk = getChunkAtWorldCoords(blockposition);
/*  386 */     Block block = iblockdata.getBlock();
/*      */     
/*      */ 
/*  389 */     BlockState blockstate = null;
/*  390 */     if (this.captureBlockStates) {
/*  391 */       blockstate = CraftBlockState.getBlockState(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), i);
/*  392 */       this.capturedBlockStates.add(blockstate);
/*      */     }
/*      */     
/*      */ 
/*  396 */     IBlockData iblockdata1 = chunk.a(blockposition, iblockdata);
/*      */     
/*  398 */     if (iblockdata1 == null)
/*      */     {
/*  400 */       if (this.captureBlockStates) {
/*  401 */         this.capturedBlockStates.remove(blockstate);
/*      */       }
/*      */       
/*  404 */       return false;
/*      */     }
/*  406 */     Block block1 = iblockdata1.getBlock();
/*      */     
/*  408 */     if ((block.p() != block1.p()) || (block.r() != block1.r())) {
/*  409 */       this.methodProfiler.a("checkLight");
/*  410 */       x(blockposition);
/*  411 */       this.methodProfiler.b();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  428 */     if (!this.captureBlockStates)
/*      */     {
/*  430 */       notifyAndUpdatePhysics(blockposition, chunk, block1, block, i);
/*      */     }
/*      */     
/*      */ 
/*  434 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void notifyAndUpdatePhysics(BlockPosition blockposition, Chunk chunk, Block oldBlock, Block newBLock, int flag)
/*      */   {
/*  441 */     if (((flag & 0x2) != 0) && ((chunk == null) || (chunk.isReady()))) {
/*  442 */       notify(blockposition);
/*      */     }
/*      */     
/*  445 */     if ((!this.isClientSide) && ((flag & 0x1) != 0)) {
/*  446 */       update(blockposition, oldBlock);
/*  447 */       if (newBLock.isComplexRedstone()) {
/*  448 */         updateAdjacentComparators(blockposition, newBLock);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean setAir(BlockPosition blockposition)
/*      */   {
/*  455 */     return setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
/*      */   }
/*      */   
/*      */   public boolean setAir(BlockPosition blockposition, boolean flag) {
/*  459 */     IBlockData iblockdata = getType(blockposition);
/*  460 */     Block block = iblockdata.getBlock();
/*      */     
/*  462 */     if (block.getMaterial() == Material.AIR) {
/*  463 */       return false;
/*      */     }
/*  465 */     triggerEffect(2001, blockposition, Block.getCombinedId(iblockdata));
/*  466 */     if (flag) {
/*  467 */       block.b(this, blockposition, iblockdata, 0);
/*      */     }
/*      */     
/*  470 */     return setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
/*      */   }
/*      */   
/*      */   public boolean setTypeUpdate(BlockPosition blockposition, IBlockData iblockdata)
/*      */   {
/*  475 */     return setTypeAndData(blockposition, iblockdata, 3);
/*      */   }
/*      */   
/*      */   public void notify(BlockPosition blockposition) {
/*  479 */     for (int i = 0; i < this.u.size(); i++) {
/*  480 */       ((IWorldAccess)this.u.get(i)).a(blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public void update(BlockPosition blockposition, Block block)
/*      */   {
/*  486 */     if (this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES)
/*      */     {
/*  488 */       if (this.populating) {
/*  489 */         return;
/*      */       }
/*      */       
/*  492 */       applyPhysics(blockposition, block);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void a(int i, int j, int k, int l)
/*      */   {
/*  500 */     if (k > l) {
/*  501 */       int i1 = l;
/*  502 */       l = k;
/*  503 */       k = i1;
/*      */     }
/*      */     
/*  506 */     if (!this.worldProvider.o()) {
/*  507 */       for (int i1 = k; i1 <= l; i1++) {
/*  508 */         c(EnumSkyBlock.SKY, new BlockPosition(i, i1, j));
/*      */       }
/*      */     }
/*      */     
/*  512 */     b(i, k, j, i, l, j);
/*      */   }
/*      */   
/*      */   public void b(BlockPosition blockposition, BlockPosition blockposition1) {
/*  516 */     b(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/*      */   }
/*      */   
/*      */   public void b(int i, int j, int k, int l, int i1, int j1) {
/*  520 */     for (int k1 = 0; k1 < this.u.size(); k1++) {
/*  521 */       ((IWorldAccess)this.u.get(k1)).a(i, j, k, l, i1, j1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void applyPhysics(BlockPosition blockposition, Block block)
/*      */   {
/*  527 */     d(blockposition.west(), block);
/*  528 */     d(blockposition.east(), block);
/*  529 */     d(blockposition.down(), block);
/*  530 */     d(blockposition.up(), block);
/*  531 */     d(blockposition.north(), block);
/*  532 */     d(blockposition.south(), block);
/*  533 */     this.spigotConfig.antiXrayInstance.updateNearbyBlocks(this, blockposition);
/*      */   }
/*      */   
/*      */   public void a(BlockPosition blockposition, Block block, EnumDirection enumdirection) {
/*  537 */     if (enumdirection != EnumDirection.WEST) {
/*  538 */       d(blockposition.west(), block);
/*      */     }
/*      */     
/*  541 */     if (enumdirection != EnumDirection.EAST) {
/*  542 */       d(blockposition.east(), block);
/*      */     }
/*      */     
/*  545 */     if (enumdirection != EnumDirection.DOWN) {
/*  546 */       d(blockposition.down(), block);
/*      */     }
/*      */     
/*  549 */     if (enumdirection != EnumDirection.UP) {
/*  550 */       d(blockposition.up(), block);
/*      */     }
/*      */     
/*  553 */     if (enumdirection != EnumDirection.NORTH) {
/*  554 */       d(blockposition.north(), block);
/*      */     }
/*      */     
/*  557 */     if (enumdirection != EnumDirection.SOUTH) {
/*  558 */       d(blockposition.south(), block);
/*      */     }
/*      */   }
/*      */   
/*      */   public void d(BlockPosition blockposition, final Block block)
/*      */   {
/*  564 */     if (!this.isClientSide) {
/*  565 */       IBlockData iblockdata = getType(blockposition);
/*      */       
/*      */       try
/*      */       {
/*  569 */         CraftWorld world = ((WorldServer)this).getWorld();
/*  570 */         if (world != null) {
/*  571 */           BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), CraftMagicNumbers.getId(block));
/*  572 */           getServer().getPluginManager().callEvent(event);
/*      */           
/*  574 */           if (event.isCancelled()) {
/*  575 */             return;
/*      */           }
/*      */         }
/*      */         
/*  579 */         iblockdata.getBlock().doPhysics(this, blockposition, iblockdata, block);
/*      */       } catch (StackOverflowError localStackOverflowError) {
/*  581 */         haveWeSilencedAPhysicsCrash = true;
/*  582 */         blockLocation = blockposition.getX() + ", " + blockposition.getY() + ", " + blockposition.getZ();
/*      */       } catch (Throwable throwable) {
/*  584 */         CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
/*  585 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
/*      */         
/*  587 */         crashreportsystemdetails.a("Source block type", new Callable() {
/*      */           public String a() throws Exception {
/*      */             try {
/*  590 */               return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getId(block)), block.a(), block.getClass().getCanonicalName() });
/*      */             } catch (Throwable localThrowable) {}
/*  592 */             return "ID #" + Block.getId(block);
/*      */           }
/*      */           
/*      */           public Object call() throws Exception
/*      */           {
/*  597 */             return a();
/*      */           }
/*  599 */         });
/*  600 */         CrashReportSystemDetails.a(crashreportsystemdetails, blockposition, iblockdata);
/*  601 */         throw new ReportedException(crashreport);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean a(BlockPosition blockposition, Block block) {
/*  607 */     return false;
/*      */   }
/*      */   
/*      */   public boolean i(BlockPosition blockposition) {
/*  611 */     return getChunkAtWorldCoords(blockposition).d(blockposition);
/*      */   }
/*      */   
/*      */   public boolean j(BlockPosition blockposition) {
/*  615 */     if (blockposition.getY() >= F()) {
/*  616 */       return i(blockposition);
/*      */     }
/*  618 */     BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), F(), blockposition.getZ());
/*      */     
/*  620 */     if (!i(blockposition1)) {
/*  621 */       return false;
/*      */     }
/*  623 */     for (blockposition1 = blockposition1.down(); blockposition1.getY() > blockposition.getY(); blockposition1 = blockposition1.down()) {
/*  624 */       Block block = getType(blockposition1).getBlock();
/*      */       
/*  626 */       if ((block.p() > 0) && (!block.getMaterial().isLiquid())) {
/*  627 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  631 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public int k(BlockPosition blockposition)
/*      */   {
/*  637 */     if (blockposition.getY() < 0) {
/*  638 */       return 0;
/*      */     }
/*  640 */     if (blockposition.getY() >= 256) {
/*  641 */       blockposition = new BlockPosition(blockposition.getX(), 255, blockposition.getZ());
/*      */     }
/*      */     
/*  644 */     return getChunkAtWorldCoords(blockposition).a(blockposition, 0);
/*      */   }
/*      */   
/*      */   public int getLightLevel(BlockPosition blockposition)
/*      */   {
/*  649 */     return c(blockposition, true);
/*      */   }
/*      */   
/*      */   public int c(BlockPosition blockposition, boolean flag) {
/*  653 */     if ((blockposition.getX() >= -30000000) && (blockposition.getZ() >= -30000000) && (blockposition.getX() < 30000000) && (blockposition.getZ() < 30000000)) {
/*  654 */       if ((flag) && (getType(blockposition).getBlock().s())) {
/*  655 */         int i = c(blockposition.up(), false);
/*  656 */         int j = c(blockposition.east(), false);
/*  657 */         int k = c(blockposition.west(), false);
/*  658 */         int l = c(blockposition.south(), false);
/*  659 */         int i1 = c(blockposition.north(), false);
/*      */         
/*  661 */         if (j > i) {
/*  662 */           i = j;
/*      */         }
/*      */         
/*  665 */         if (k > i) {
/*  666 */           i = k;
/*      */         }
/*      */         
/*  669 */         if (l > i) {
/*  670 */           i = l;
/*      */         }
/*      */         
/*  673 */         if (i1 > i) {
/*  674 */           i = i1;
/*      */         }
/*      */         
/*  677 */         return i; }
/*  678 */       if (blockposition.getY() < 0) {
/*  679 */         return 0;
/*      */       }
/*  681 */       if (blockposition.getY() >= 256) {
/*  682 */         blockposition = new BlockPosition(blockposition.getX(), 255, blockposition.getZ());
/*      */       }
/*      */       
/*  685 */       Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */       
/*  687 */       return chunk.a(blockposition, this.I);
/*      */     }
/*      */     
/*  690 */     return 15;
/*      */   }
/*      */   
/*      */   public BlockPosition getHighestBlockYAt(BlockPosition blockposition)
/*      */   {
/*      */     int i;
/*      */     int i;
/*  697 */     if ((blockposition.getX() >= -30000000) && (blockposition.getZ() >= -30000000) && (blockposition.getX() < 30000000) && (blockposition.getZ() < 30000000)) { int i;
/*  698 */       if (isChunkLoaded(blockposition.getX() >> 4, blockposition.getZ() >> 4, true)) {
/*  699 */         i = getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4).b(blockposition.getX() & 0xF, blockposition.getZ() & 0xF);
/*      */       } else {
/*  701 */         i = 0;
/*      */       }
/*      */     } else {
/*  704 */       i = F() + 1;
/*      */     }
/*      */     
/*  707 */     return new BlockPosition(blockposition.getX(), i, blockposition.getZ());
/*      */   }
/*      */   
/*      */   public int b(int i, int j) {
/*  711 */     if ((i >= -30000000) && (j >= -30000000) && (i < 30000000) && (j < 30000000)) {
/*  712 */       if (!isChunkLoaded(i >> 4, j >> 4, true)) {
/*  713 */         return 0;
/*      */       }
/*  715 */       Chunk chunk = getChunkAt(i >> 4, j >> 4);
/*      */       
/*  717 */       return chunk.v();
/*      */     }
/*      */     
/*  720 */     return F() + 1;
/*      */   }
/*      */   
/*      */   public int b(EnumSkyBlock enumskyblock, BlockPosition blockposition)
/*      */   {
/*  725 */     if (blockposition.getY() < 0) {
/*  726 */       blockposition = new BlockPosition(blockposition.getX(), 0, blockposition.getZ());
/*      */     }
/*      */     
/*  729 */     if (!isValidLocation(blockposition))
/*  730 */       return enumskyblock.c;
/*  731 */     if (!isLoaded(blockposition)) {
/*  732 */       return enumskyblock.c;
/*      */     }
/*  734 */     Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */     
/*  736 */     return chunk.getBrightness(enumskyblock, blockposition);
/*      */   }
/*      */   
/*      */   public void a(EnumSkyBlock enumskyblock, BlockPosition blockposition, int i)
/*      */   {
/*  741 */     if ((isValidLocation(blockposition)) && 
/*  742 */       (isLoaded(blockposition))) {
/*  743 */       Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */       
/*  745 */       chunk.a(enumskyblock, blockposition, i);
/*  746 */       n(blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public void n(BlockPosition blockposition)
/*      */   {
/*  752 */     for (int i = 0; i < this.u.size(); i++) {
/*  753 */       ((IWorldAccess)this.u.get(i)).b(blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public float o(BlockPosition blockposition)
/*      */   {
/*  759 */     return this.worldProvider.p()[getLightLevel(blockposition)];
/*      */   }
/*      */   
/*      */ 
/*      */   public IBlockData getType(BlockPosition blockposition)
/*      */   {
/*  765 */     return getType(blockposition, true);
/*      */   }
/*      */   
/*      */   public IBlockData getType(BlockPosition blockposition, boolean useCaptured)
/*      */   {
/*  770 */     if ((this.captureTreeGeneration) && (useCaptured))
/*      */     {
/*  772 */       Iterator<BlockState> it = this.capturedBlockStates.iterator();
/*  773 */       while (it.hasNext()) {
/*  774 */         BlockState previous = (BlockState)it.next();
/*  775 */         if ((previous.getX() == blockposition.getX()) && (previous.getY() == blockposition.getY()) && (previous.getZ() == blockposition.getZ())) {
/*  776 */           return CraftMagicNumbers.getBlock(previous.getTypeId()).fromLegacyData(previous.getRawData());
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  781 */     if (!isValidLocation(blockposition)) {
/*  782 */       return Blocks.AIR.getBlockData();
/*      */     }
/*  784 */     Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */     
/*  786 */     return chunk.getBlockData(blockposition);
/*      */   }
/*      */   
/*      */   public boolean w()
/*      */   {
/*  791 */     return this.I < 4;
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1) {
/*  795 */     return rayTrace(vec3d, vec3d1, false, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag) {
/*  799 */     return rayTrace(vec3d, vec3d1, flag, false, false);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTrace(Vec3D vec3d, Vec3D vec3d1, boolean flag, boolean flag1, boolean flag2) {
/*  803 */     if ((!Double.isNaN(vec3d.a)) && (!Double.isNaN(vec3d.b)) && (!Double.isNaN(vec3d.c))) {
/*  804 */       if ((!Double.isNaN(vec3d1.a)) && (!Double.isNaN(vec3d1.b)) && (!Double.isNaN(vec3d1.c))) {
/*  805 */         int i = MathHelper.floor(vec3d1.a);
/*  806 */         int j = MathHelper.floor(vec3d1.b);
/*  807 */         int k = MathHelper.floor(vec3d1.c);
/*  808 */         int l = MathHelper.floor(vec3d.a);
/*  809 */         int i1 = MathHelper.floor(vec3d.b);
/*  810 */         int j1 = MathHelper.floor(vec3d.c);
/*  811 */         BlockPosition blockposition = new BlockPosition(l, i1, j1);
/*  812 */         IBlockData iblockdata = getType(blockposition);
/*  813 */         Block block = iblockdata.getBlock();
/*      */         
/*  815 */         if (((!flag1) || (block.a(this, blockposition, iblockdata) != null)) && (block.a(iblockdata, flag))) {
/*  816 */           MovingObjectPosition movingobjectposition = block.a(this, blockposition, vec3d, vec3d1);
/*      */           
/*  818 */           if (movingobjectposition != null) {
/*  819 */             return movingobjectposition;
/*      */           }
/*      */         }
/*      */         
/*  823 */         MovingObjectPosition movingobjectposition1 = null;
/*  824 */         int k1 = 200;
/*      */         
/*  826 */         while (k1-- >= 0) {
/*  827 */           if ((Double.isNaN(vec3d.a)) || (Double.isNaN(vec3d.b)) || (Double.isNaN(vec3d.c))) {
/*  828 */             return null;
/*      */           }
/*      */           
/*  831 */           if ((l == i) && (i1 == j) && (j1 == k)) {
/*  832 */             return flag2 ? movingobjectposition1 : null;
/*      */           }
/*      */           
/*  835 */           boolean flag3 = true;
/*  836 */           boolean flag4 = true;
/*  837 */           boolean flag5 = true;
/*  838 */           double d0 = 999.0D;
/*  839 */           double d1 = 999.0D;
/*  840 */           double d2 = 999.0D;
/*      */           
/*  842 */           if (i > l) {
/*  843 */             d0 = l + 1.0D;
/*  844 */           } else if (i < l) {
/*  845 */             d0 = l + 0.0D;
/*      */           } else {
/*  847 */             flag3 = false;
/*      */           }
/*      */           
/*  850 */           if (j > i1) {
/*  851 */             d1 = i1 + 1.0D;
/*  852 */           } else if (j < i1) {
/*  853 */             d1 = i1 + 0.0D;
/*      */           } else {
/*  855 */             flag4 = false;
/*      */           }
/*      */           
/*  858 */           if (k > j1) {
/*  859 */             d2 = j1 + 1.0D;
/*  860 */           } else if (k < j1) {
/*  861 */             d2 = j1 + 0.0D;
/*      */           } else {
/*  863 */             flag5 = false;
/*      */           }
/*      */           
/*  866 */           double d3 = 999.0D;
/*  867 */           double d4 = 999.0D;
/*  868 */           double d5 = 999.0D;
/*  869 */           double d6 = vec3d1.a - vec3d.a;
/*  870 */           double d7 = vec3d1.b - vec3d.b;
/*  871 */           double d8 = vec3d1.c - vec3d.c;
/*      */           
/*  873 */           if (flag3) {
/*  874 */             d3 = (d0 - vec3d.a) / d6;
/*      */           }
/*      */           
/*  877 */           if (flag4) {
/*  878 */             d4 = (d1 - vec3d.b) / d7;
/*      */           }
/*      */           
/*  881 */           if (flag5) {
/*  882 */             d5 = (d2 - vec3d.c) / d8;
/*      */           }
/*      */           
/*  885 */           if (d3 == -0.0D) {
/*  886 */             d3 = -1.0E-4D;
/*      */           }
/*      */           
/*  889 */           if (d4 == -0.0D) {
/*  890 */             d4 = -1.0E-4D;
/*      */           }
/*      */           
/*  893 */           if (d5 == -0.0D) {
/*  894 */             d5 = -1.0E-4D;
/*      */           }
/*      */           
/*      */           EnumDirection enumdirection;
/*      */           
/*  899 */           if ((d3 < d4) && (d3 < d5)) {
/*  900 */             EnumDirection enumdirection = i > l ? EnumDirection.WEST : EnumDirection.EAST;
/*  901 */             vec3d = new Vec3D(d0, vec3d.b + d7 * d3, vec3d.c + d8 * d3);
/*  902 */           } else if (d4 < d5) {
/*  903 */             EnumDirection enumdirection = j > i1 ? EnumDirection.DOWN : EnumDirection.UP;
/*  904 */             vec3d = new Vec3D(vec3d.a + d6 * d4, d1, vec3d.c + d8 * d4);
/*      */           } else {
/*  906 */             enumdirection = k > j1 ? EnumDirection.NORTH : EnumDirection.SOUTH;
/*  907 */             vec3d = new Vec3D(vec3d.a + d6 * d5, vec3d.b + d7 * d5, d2);
/*      */           }
/*      */           
/*  910 */           l = MathHelper.floor(vec3d.a) - (enumdirection == EnumDirection.EAST ? 1 : 0);
/*  911 */           i1 = MathHelper.floor(vec3d.b) - (enumdirection == EnumDirection.UP ? 1 : 0);
/*  912 */           j1 = MathHelper.floor(vec3d.c) - (enumdirection == EnumDirection.SOUTH ? 1 : 0);
/*  913 */           blockposition = new BlockPosition(l, i1, j1);
/*  914 */           IBlockData iblockdata1 = getType(blockposition);
/*  915 */           Block block1 = iblockdata1.getBlock();
/*      */           
/*  917 */           if ((!flag1) || (block1.a(this, blockposition, iblockdata1) != null)) {
/*  918 */             if (block1.a(iblockdata1, flag)) {
/*  919 */               MovingObjectPosition movingobjectposition2 = block1.a(this, blockposition, vec3d, vec3d1);
/*      */               
/*  921 */               if (movingobjectposition2 != null) {
/*  922 */                 return movingobjectposition2;
/*      */               }
/*      */             } else {
/*  925 */               movingobjectposition1 = new MovingObjectPosition(MovingObjectPosition.EnumMovingObjectType.MISS, vec3d, enumdirection, blockposition);
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  930 */         return flag2 ? movingobjectposition1 : null;
/*      */       }
/*  932 */       return null;
/*      */     }
/*      */     
/*  935 */     return null;
/*      */   }
/*      */   
/*      */   public void makeSound(Entity entity, String s, float f, float f1)
/*      */   {
/*  940 */     for (int i = 0; i < this.u.size(); i++) {
/*  941 */       ((IWorldAccess)this.u.get(i)).a(s, entity.locX, entity.locY, entity.locZ, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(EntityHuman entityhuman, String s, float f, float f1)
/*      */   {
/*  947 */     for (int i = 0; i < this.u.size(); i++) {
/*  948 */       ((IWorldAccess)this.u.get(i)).a(entityhuman, s, entityhuman.locX, entityhuman.locY, entityhuman.locZ, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void makeSound(double d0, double d1, double d2, String s, float f, float f1)
/*      */   {
/*  954 */     for (int i = 0; i < this.u.size(); i++) {
/*  955 */       ((IWorldAccess)this.u.get(i)).a(s, d0, d1, d2, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(double d0, double d1, double d2, String s, float f, float f1, boolean flag) {}
/*      */   
/*      */   public void a(BlockPosition blockposition, String s)
/*      */   {
/*  963 */     for (int i = 0; i < this.u.size(); i++) {
/*  964 */       ((IWorldAccess)this.u.get(i)).a(s, blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addParticle(EnumParticle enumparticle, double d0, double d1, double d2, double d3, double d4, double d5, int... aint)
/*      */   {
/*  970 */     a(enumparticle.c(), enumparticle.e(), d0, d1, d2, d3, d4, d5, aint);
/*      */   }
/*      */   
/*      */   private void a(int i, boolean flag, double d0, double d1, double d2, double d3, double d4, double d5, int... aint) {
/*  974 */     for (int j = 0; j < this.u.size(); j++) {
/*  975 */       ((IWorldAccess)this.u.get(j)).a(i, flag, d0, d1, d2, d3, d4, d5, aint);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean strikeLightning(Entity entity)
/*      */   {
/*  981 */     this.k.add(entity);
/*  982 */     return true;
/*      */   }
/*      */   
/*      */   public boolean addEntity(Entity entity)
/*      */   {
/*  987 */     return addEntity(entity, CreatureSpawnEvent.SpawnReason.DEFAULT);
/*      */   }
/*      */   
/*      */   public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
/*  991 */     AsyncCatcher.catchOp("entity add");
/*  992 */     if (entity == null) { return false;
/*      */     }
/*  994 */     int i = MathHelper.floor(entity.locX / 16.0D);
/*  995 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/*  996 */     boolean flag = entity.attachedToPlayer;
/*      */     
/*  998 */     if ((entity instanceof EntityHuman)) {
/*  999 */       flag = true;
/*      */     }
/*      */     
/*      */ 
/* 1003 */     Cancellable event = null;
/* 1004 */     if (((entity instanceof EntityLiving)) && (!(entity instanceof EntityPlayer))) {
/* 1005 */       boolean isAnimal = ((entity instanceof EntityAnimal)) || ((entity instanceof EntityWaterAnimal)) || ((entity instanceof EntityGolem));
/* 1006 */       boolean isMonster = ((entity instanceof EntityMonster)) || ((entity instanceof EntityGhast)) || ((entity instanceof EntitySlime));
/*      */       
/* 1008 */       if ((spawnReason != CreatureSpawnEvent.SpawnReason.CUSTOM) && (
/* 1009 */         ((isAnimal) && (!this.allowAnimals)) || ((isMonster) && (!this.allowMonsters)))) {
/* 1010 */         entity.dead = true;
/* 1011 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1015 */       event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)entity, spawnReason);
/* 1016 */     } else if ((entity instanceof EntityItem)) {
/* 1017 */       event = CraftEventFactory.callItemSpawnEvent((EntityItem)entity);
/* 1018 */     } else if ((entity.getBukkitEntity() instanceof org.bukkit.entity.Projectile))
/*      */     {
/* 1020 */       event = CraftEventFactory.callProjectileLaunchEvent(entity);
/*      */ 
/*      */     }
/* 1023 */     else if ((entity instanceof EntityExperienceOrb)) {
/* 1024 */       EntityExperienceOrb xp = (EntityExperienceOrb)entity;
/* 1025 */       double radius = this.spigotConfig.expMerge;
/* 1026 */       if (radius > 0.0D) {
/* 1027 */         List<Entity> entities = getEntities(entity, entity.getBoundingBox().grow(radius, radius, radius));
/* 1028 */         for (Entity e : entities) {
/* 1029 */           if ((e instanceof EntityExperienceOrb)) {
/* 1030 */             EntityExperienceOrb loopItem = (EntityExperienceOrb)e;
/* 1031 */             if (!loopItem.dead) {
/* 1032 */               xp.value += loopItem.value;
/* 1033 */               loopItem.die();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1040 */     if ((event != null) && ((event.isCancelled()) || (entity.dead))) {
/* 1041 */       entity.dead = true;
/* 1042 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1046 */     if ((!flag) && (!isChunkLoaded(i, j, true))) {
/* 1047 */       entity.dead = true;
/* 1048 */       return false;
/*      */     }
/* 1050 */     if ((entity instanceof EntityHuman)) {
/* 1051 */       EntityHuman entityhuman = (EntityHuman)entity;
/*      */       
/* 1053 */       this.players.add(entityhuman);
/* 1054 */       everyoneSleeping();
/*      */     }
/*      */     
/* 1057 */     getChunkAt(i, j).a(entity);
/* 1058 */     this.entityList.add(entity);
/* 1059 */     a(entity);
/* 1060 */     return true;
/*      */   }
/*      */   
/*      */   protected void a(Entity entity)
/*      */   {
/* 1065 */     for (int i = 0; i < this.u.size(); i++) {
/* 1066 */       ((IWorldAccess)this.u.get(i)).a(entity);
/*      */     }
/*      */     
/* 1069 */     entity.valid = true;
/*      */   }
/*      */   
/*      */   protected void b(Entity entity) {
/* 1073 */     for (int i = 0; i < this.u.size(); i++) {
/* 1074 */       ((IWorldAccess)this.u.get(i)).b(entity);
/*      */     }
/*      */     
/* 1077 */     entity.valid = false;
/*      */   }
/*      */   
/*      */   public void kill(Entity entity) {
/* 1081 */     if (entity.passenger != null) {
/* 1082 */       entity.passenger.mount(null);
/*      */     }
/*      */     
/* 1085 */     if (entity.vehicle != null) {
/* 1086 */       entity.mount(null);
/*      */     }
/*      */     
/* 1089 */     entity.die();
/* 1090 */     if ((entity instanceof EntityHuman)) {
/* 1091 */       this.players.remove(entity);
/*      */       
/* 1093 */       for (Object o : this.worldMaps.c)
/*      */       {
/* 1095 */         if ((o instanceof WorldMap))
/*      */         {
/* 1097 */           WorldMap map = (WorldMap)o;
/* 1098 */           map.i.remove(entity);
/* 1099 */           for (Iterator<WorldMap.WorldMapHumanTracker> iter = map.g.iterator(); iter.hasNext();)
/*      */           {
/* 1101 */             if (((WorldMap.WorldMapHumanTracker)iter.next()).trackee == entity)
/*      */             {
/* 1103 */               iter.remove();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1109 */       everyoneSleeping();
/* 1110 */       b(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeEntity(Entity entity)
/*      */   {
/* 1116 */     AsyncCatcher.catchOp("entity remove");
/* 1117 */     entity.die();
/* 1118 */     if ((entity instanceof EntityHuman)) {
/* 1119 */       this.players.remove(entity);
/* 1120 */       everyoneSleeping();
/*      */     }
/*      */     
/* 1123 */     if (!this.guardEntityList) {
/* 1124 */       int i = entity.ae;
/* 1125 */       int j = entity.ag;
/*      */       
/* 1127 */       if ((entity.ad) && (isChunkLoaded(i, j, true))) {
/* 1128 */         getChunkAt(i, j).b(entity);
/*      */       }
/*      */       
/*      */ 
/* 1132 */       int index = this.entityList.indexOf(entity);
/* 1133 */       if (index != -1) {
/* 1134 */         if (index <= this.tickPosition) {
/* 1135 */           this.tickPosition -= 1;
/*      */         }
/* 1137 */         this.entityList.remove(index);
/*      */       }
/*      */     }
/*      */     
/* 1141 */     b(entity);
/*      */   }
/*      */   
/*      */   public void addIWorldAccess(IWorldAccess iworldaccess) {
/* 1145 */     this.u.add(iworldaccess);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> getCubes(Entity entity, AxisAlignedBB axisalignedbb) {
/* 1149 */     ArrayList arraylist = Lists.newArrayList();
/* 1150 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1151 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1152 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1153 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1154 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1155 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/* 1156 */     WorldBorder worldborder = getWorldBorder();
/* 1157 */     boolean flag = entity.aT();
/* 1158 */     boolean flag1 = a(worldborder, entity);
/* 1159 */     Blocks.STONE.getBlockData();
/* 1160 */     new BlockPosition.MutableBlockPosition();
/*      */     
/*      */ 
/* 1163 */     int ystart = k - 1 < 0 ? 0 : k - 1;
/* 1164 */     for (int chunkx = i >> 4; chunkx <= j - 1 >> 4; chunkx++)
/*      */     {
/* 1166 */       int cx = chunkx << 4;
/* 1167 */       for (int chunkz = i1 >> 4; chunkz <= j1 - 1 >> 4; chunkz++)
/*      */       {
/* 1169 */         if (isChunkLoaded(chunkx, chunkz, true))
/*      */         {
/*      */ 
/*      */ 
/* 1173 */           int cz = chunkz << 4;
/* 1174 */           Chunk chunk = getChunkAt(chunkx, chunkz);
/*      */           
/* 1176 */           int xstart = i < cx ? cx : i;
/* 1177 */           int xend = j < cx + 16 ? j : cx + 16;
/* 1178 */           int zstart = i1 < cz ? cz : i1;
/* 1179 */           int zend = j1 < cz + 16 ? j1 : cz + 16;
/*      */           
/* 1181 */           for (int x = xstart; x < xend; x++)
/*      */           {
/* 1183 */             for (int z = zstart; z < zend; z++)
/*      */             {
/* 1185 */               for (int y = ystart; y < l; y++)
/*      */               {
/* 1187 */                 BlockPosition blockposition = new BlockPosition(x, y, z);
/*      */                 
/* 1189 */                 if ((flag) && (flag1)) {
/* 1190 */                   entity.h(false);
/* 1191 */                 } else if ((!flag) && (!flag1)) {
/* 1192 */                   entity.h(true);
/*      */                 }
/*      */                 IBlockData block;
/*      */                 IBlockData block;
/* 1196 */                 if ((!getWorldBorder().a(blockposition)) && (flag1)) {
/* 1197 */                   block = Blocks.STONE.getBlockData();
/*      */                 }
/*      */                 else {
/* 1200 */                   block = chunk.getBlockData(blockposition);
/*      */                 }
/* 1202 */                 if (block != null)
/*      */                 {
/* 1204 */                   block.getBlock().a(this, blockposition, block, axisalignedbb, arraylist, entity);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1213 */     double d0 = 0.25D;
/* 1214 */     List list = getEntities(entity, axisalignedbb.grow(d0, d0, d0));
/*      */     
/* 1216 */     for (int j2 = 0; j2 < list.size(); j2++) {
/* 1217 */       if ((entity.passenger != list) && (entity.vehicle != list)) {
/* 1218 */         AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).S();
/*      */         
/* 1220 */         if ((axisalignedbb1 != null) && (axisalignedbb1.b(axisalignedbb))) {
/* 1221 */           arraylist.add(axisalignedbb1);
/*      */         }
/*      */         
/* 1224 */         axisalignedbb1 = entity.j((Entity)list.get(j2));
/* 1225 */         if ((axisalignedbb1 != null) && (axisalignedbb1.b(axisalignedbb))) {
/* 1226 */           arraylist.add(axisalignedbb1);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1231 */     return arraylist;
/*      */   }
/*      */   
/*      */   public boolean a(WorldBorder worldborder, Entity entity) {
/* 1235 */     double d0 = worldborder.b();
/* 1236 */     double d1 = worldborder.c();
/* 1237 */     double d2 = worldborder.d();
/* 1238 */     double d3 = worldborder.e();
/*      */     
/* 1240 */     if (entity.aT()) {
/* 1241 */       d0 += 1.0D;
/* 1242 */       d1 += 1.0D;
/* 1243 */       d2 -= 1.0D;
/* 1244 */       d3 -= 1.0D;
/*      */     } else {
/* 1246 */       d0 -= 1.0D;
/* 1247 */       d1 -= 1.0D;
/* 1248 */       d2 += 1.0D;
/* 1249 */       d3 += 1.0D;
/*      */     }
/*      */     
/* 1252 */     return (entity.locX > d0) && (entity.locX < d2) && (entity.locZ > d1) && (entity.locZ < d3);
/*      */   }
/*      */   
/*      */   public List<AxisAlignedBB> a(AxisAlignedBB axisalignedbb) {
/* 1256 */     ArrayList arraylist = Lists.newArrayList();
/* 1257 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1258 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1259 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1260 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1261 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1262 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/* 1263 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1265 */     for (int k1 = i; k1 < j; k1++) {
/* 1266 */       for (int l1 = i1; l1 < j1; l1++) {
/* 1267 */         if (isLoaded(blockposition_mutableblockposition.c(k1, 64, l1))) {
/* 1268 */           for (int i2 = k - 1; i2 < l; i2++) {
/* 1269 */             blockposition_mutableblockposition.c(k1, i2, l1);
/*      */             IBlockData iblockdata;
/*      */             IBlockData iblockdata;
/* 1272 */             if ((k1 >= -30000000) && (k1 < 30000000) && (l1 >= -30000000) && (l1 < 30000000)) {
/* 1273 */               iblockdata = getType(blockposition_mutableblockposition);
/*      */             } else {
/* 1275 */               iblockdata = Blocks.BEDROCK.getBlockData();
/*      */             }
/*      */             
/* 1278 */             iblockdata.getBlock().a(this, blockposition_mutableblockposition, iblockdata, axisalignedbb, arraylist, null);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1284 */     return arraylist;
/*      */   }
/*      */   
/*      */   public int a(float f) {
/* 1288 */     float f1 = c(f);
/* 1289 */     float f2 = 1.0F - (MathHelper.cos(f1 * 3.1415927F * 2.0F) * 2.0F + 0.5F);
/*      */     
/* 1291 */     f2 = MathHelper.a(f2, 0.0F, 1.0F);
/* 1292 */     f2 = 1.0F - f2;
/* 1293 */     f2 = (float)(f2 * (1.0D - j(f) * 5.0F / 16.0D));
/* 1294 */     f2 = (float)(f2 * (1.0D - h(f) * 5.0F / 16.0D));
/* 1295 */     f2 = 1.0F - f2;
/* 1296 */     return (int)(f2 * 11.0F);
/*      */   }
/*      */   
/*      */   public float c(float f) {
/* 1300 */     return this.worldProvider.a(this.worldData.getDayTime(), f);
/*      */   }
/*      */   
/*      */   public float y() {
/* 1304 */     return WorldProvider.a[this.worldProvider.a(this.worldData.getDayTime())];
/*      */   }
/*      */   
/*      */   public float d(float f) {
/* 1308 */     float f1 = c(f);
/*      */     
/* 1310 */     return f1 * 3.1415927F * 2.0F;
/*      */   }
/*      */   
/*      */   public BlockPosition q(BlockPosition blockposition) {
/* 1314 */     return getChunkAtWorldCoords(blockposition).h(blockposition);
/*      */   }
/*      */   
/*      */   public BlockPosition r(BlockPosition blockposition) {
/* 1318 */     Chunk chunk = getChunkAtWorldCoords(blockposition);
/*      */     
/*      */ 
/*      */     BlockPosition blockposition2;
/*      */     
/* 1323 */     for (BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), chunk.g() + 16, blockposition.getZ()); blockposition1.getY() >= 0; blockposition1 = blockposition2) {
/* 1324 */       blockposition2 = blockposition1.down();
/* 1325 */       Material material = chunk.getType(blockposition2).getMaterial();
/*      */       
/* 1327 */       if ((material.isSolid()) && (material != Material.LEAVES)) {
/*      */         break;
/*      */       }
/*      */     }
/*      */     
/* 1332 */     return blockposition1;
/*      */   }
/*      */   
/*      */   public void a(BlockPosition blockposition, Block block, int i) {}
/*      */   
/*      */   public void a(BlockPosition blockposition, Block block, int i, int j) {}
/*      */   
/*      */   public void b(BlockPosition blockposition, Block block, int i, int j) {}
/*      */   
/*      */   /* Error */
/*      */   public void tickEntities()
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   4: ldc_w 1398
/*      */     //   7: invokevirtual 685	net/minecraft/server/v1_8_R3/MethodProfiler:a	(Ljava/lang/String;)V
/*      */     //   10: aload_0
/*      */     //   11: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   14: ldc_w 1400
/*      */     //   17: invokevirtual 685	net/minecraft/server/v1_8_R3/MethodProfiler:a	(Ljava/lang/String;)V
/*      */     //   20: iconst_0
/*      */     //   21: istore_1
/*      */     //   22: goto +119 -> 141
/*      */     //   25: aload_0
/*      */     //   26: getfield 236	net/minecraft/server/v1_8_R3/World:k	Ljava/util/List;
/*      */     //   29: iload_1
/*      */     //   30: invokeinterface 753 2 0
/*      */     //   35: checkcast 1045	net/minecraft/server/v1_8_R3/Entity
/*      */     //   38: astore_2
/*      */     //   39: aload_2
/*      */     //   40: ifnonnull +6 -> 46
/*      */     //   43: goto +95 -> 138
/*      */     //   46: aload_2
/*      */     //   47: dup
/*      */     //   48: getfield 1403	net/minecraft/server/v1_8_R3/Entity:ticksLived	I
/*      */     //   51: iconst_1
/*      */     //   52: iadd
/*      */     //   53: putfield 1403	net/minecraft/server/v1_8_R3/Entity:ticksLived	I
/*      */     //   56: aload_2
/*      */     //   57: invokevirtual 1406	net/minecraft/server/v1_8_R3/Entity:t_	()V
/*      */     //   60: goto +57 -> 117
/*      */     //   63: astore_3
/*      */     //   64: aload_3
/*      */     //   65: ldc_w 1408
/*      */     //   68: invokestatic 449	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   71: astore 4
/*      */     //   73: aload 4
/*      */     //   75: ldc_w 1410
/*      */     //   78: invokevirtual 454	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;
/*      */     //   81: astore 5
/*      */     //   83: aload_2
/*      */     //   84: ifnonnull +17 -> 101
/*      */     //   87: aload 5
/*      */     //   89: ldc_w 1412
/*      */     //   92: ldc_w 1414
/*      */     //   95: invokevirtual 1417	net/minecraft/server/v1_8_R3/CrashReportSystemDetails:a	(Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   98: goto +9 -> 107
/*      */     //   101: aload_2
/*      */     //   102: aload 5
/*      */     //   104: invokevirtual 1421	net/minecraft/server/v1_8_R3/Entity:appendEntityCrashDetails	(Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;)V
/*      */     //   107: new 466	net/minecraft/server/v1_8_R3/ReportedException
/*      */     //   110: dup
/*      */     //   111: aload 4
/*      */     //   113: invokespecial 469	net/minecraft/server/v1_8_R3/ReportedException:<init>	(Lnet/minecraft/server/v1_8_R3/CrashReport;)V
/*      */     //   116: athrow
/*      */     //   117: aload_2
/*      */     //   118: getfield 1139	net/minecraft/server/v1_8_R3/Entity:dead	Z
/*      */     //   121: ifeq +17 -> 138
/*      */     //   124: aload_0
/*      */     //   125: getfield 236	net/minecraft/server/v1_8_R3/World:k	Ljava/util/List;
/*      */     //   128: iload_1
/*      */     //   129: iinc 1 -1
/*      */     //   132: invokeinterface 1268 2 0
/*      */     //   137: pop
/*      */     //   138: iinc 1 1
/*      */     //   141: iload_1
/*      */     //   142: aload_0
/*      */     //   143: getfield 236	net/minecraft/server/v1_8_R3/World:k	Ljava/util/List;
/*      */     //   146: invokeinterface 760 1 0
/*      */     //   151: if_icmplt -126 -> 25
/*      */     //   154: aload_0
/*      */     //   155: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   158: ldc_w 1422
/*      */     //   161: invokevirtual 1424	net/minecraft/server/v1_8_R3/MethodProfiler:c	(Ljava/lang/String;)V
/*      */     //   164: aload_0
/*      */     //   165: getfield 216	net/minecraft/server/v1_8_R3/World:entityList	Ljava/util/List;
/*      */     //   168: aload_0
/*      */     //   169: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   172: invokeinterface 1428 2 0
/*      */     //   177: pop
/*      */     //   178: iconst_0
/*      */     //   179: istore_1
/*      */     //   180: goto +60 -> 240
/*      */     //   183: aload_0
/*      */     //   184: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   187: iload_1
/*      */     //   188: invokeinterface 753 2 0
/*      */     //   193: checkcast 1045	net/minecraft/server/v1_8_R3/Entity
/*      */     //   196: astore_2
/*      */     //   197: aload_2
/*      */     //   198: getfield 1253	net/minecraft/server/v1_8_R3/Entity:ae	I
/*      */     //   201: istore_3
/*      */     //   202: aload_2
/*      */     //   203: getfield 1256	net/minecraft/server/v1_8_R3/Entity:ag	I
/*      */     //   206: istore 6
/*      */     //   208: aload_2
/*      */     //   209: getfield 1259	net/minecraft/server/v1_8_R3/Entity:ad	Z
/*      */     //   212: ifeq +25 -> 237
/*      */     //   215: aload_0
/*      */     //   216: iload_3
/*      */     //   217: iload 6
/*      */     //   219: iconst_1
/*      */     //   220: invokevirtual 555	net/minecraft/server/v1_8_R3/World:isChunkLoaded	(IIZ)Z
/*      */     //   223: ifeq +14 -> 237
/*      */     //   226: aload_0
/*      */     //   227: iload_3
/*      */     //   228: iload 6
/*      */     //   230: invokevirtual 610	net/minecraft/server/v1_8_R3/World:getChunkAt	(II)Lnet/minecraft/server/v1_8_R3/Chunk;
/*      */     //   233: aload_2
/*      */     //   234: invokevirtual 1260	net/minecraft/server/v1_8_R3/Chunk:b	(Lnet/minecraft/server/v1_8_R3/Entity;)V
/*      */     //   237: iinc 1 1
/*      */     //   240: iload_1
/*      */     //   241: aload_0
/*      */     //   242: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   245: invokeinterface 760 1 0
/*      */     //   250: if_icmplt -67 -> 183
/*      */     //   253: iconst_0
/*      */     //   254: istore_1
/*      */     //   255: goto +23 -> 278
/*      */     //   258: aload_0
/*      */     //   259: aload_0
/*      */     //   260: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   263: iload_1
/*      */     //   264: invokeinterface 753 2 0
/*      */     //   269: checkcast 1045	net/minecraft/server/v1_8_R3/Entity
/*      */     //   272: invokevirtual 1240	net/minecraft/server/v1_8_R3/World:b	(Lnet/minecraft/server/v1_8_R3/Entity;)V
/*      */     //   275: iinc 1 1
/*      */     //   278: iload_1
/*      */     //   279: aload_0
/*      */     //   280: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   283: invokeinterface 760 1 0
/*      */     //   288: if_icmplt -30 -> 258
/*      */     //   291: aload_0
/*      */     //   292: getfield 224	net/minecraft/server/v1_8_R3/World:g	Ljava/util/List;
/*      */     //   295: invokeinterface 1431 1 0
/*      */     //   300: aload_0
/*      */     //   301: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   304: ldc_w 1433
/*      */     //   307: invokevirtual 1424	net/minecraft/server/v1_8_R3/MethodProfiler:c	(Ljava/lang/String;)V
/*      */     //   310: aload_0
/*      */     //   311: invokestatic 1438	org/spigotmc/ActivationRange:activateEntities	(Lnet/minecraft/server/v1_8_R3/World;)V
/*      */     //   314: aload_0
/*      */     //   315: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   318: getfield 1442	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:entityTick	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   321: invokevirtual 1447	org/spigotmc/CustomTimingsHandler:startTiming	()V
/*      */     //   324: aload_0
/*      */     //   325: iconst_1
/*      */     //   326: putfield 1250	net/minecraft/server/v1_8_R3/World:guardEntityList	Z
/*      */     //   329: iconst_0
/*      */     //   330: istore 7
/*      */     //   332: aload_0
/*      */     //   333: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   336: ifge +8 -> 344
/*      */     //   339: aload_0
/*      */     //   340: iconst_0
/*      */     //   341: putfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   344: aload_0
/*      */     //   345: getfield 410	net/minecraft/server/v1_8_R3/World:entityLimiter	Lorg/spigotmc/TickLimiter;
/*      */     //   348: invokevirtual 1450	org/spigotmc/TickLimiter:initTick	()V
/*      */     //   351: goto +287 -> 638
/*      */     //   354: aload_0
/*      */     //   355: aload_0
/*      */     //   356: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   359: aload_0
/*      */     //   360: getfield 216	net/minecraft/server/v1_8_R3/World:entityList	Ljava/util/List;
/*      */     //   363: invokeinterface 760 1 0
/*      */     //   368: if_icmpge +10 -> 378
/*      */     //   371: aload_0
/*      */     //   372: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   375: goto +4 -> 379
/*      */     //   378: iconst_0
/*      */     //   379: putfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   382: aload_0
/*      */     //   383: getfield 216	net/minecraft/server/v1_8_R3/World:entityList	Ljava/util/List;
/*      */     //   386: aload_0
/*      */     //   387: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   390: invokeinterface 753 2 0
/*      */     //   395: checkcast 1045	net/minecraft/server/v1_8_R3/Entity
/*      */     //   398: astore_2
/*      */     //   399: aload_2
/*      */     //   400: getfield 1221	net/minecraft/server/v1_8_R3/Entity:vehicle	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   403: ifnull +40 -> 443
/*      */     //   406: aload_2
/*      */     //   407: getfield 1221	net/minecraft/server/v1_8_R3/Entity:vehicle	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   410: getfield 1139	net/minecraft/server/v1_8_R3/Entity:dead	Z
/*      */     //   413: ifne +17 -> 430
/*      */     //   416: aload_2
/*      */     //   417: getfield 1221	net/minecraft/server/v1_8_R3/Entity:vehicle	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   420: getfield 1215	net/minecraft/server/v1_8_R3/Entity:passenger	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   423: aload_2
/*      */     //   424: if_acmpne +6 -> 430
/*      */     //   427: goto +198 -> 625
/*      */     //   430: aload_2
/*      */     //   431: getfield 1221	net/minecraft/server/v1_8_R3/Entity:vehicle	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   434: aconst_null
/*      */     //   435: putfield 1215	net/minecraft/server/v1_8_R3/Entity:passenger	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   438: aload_2
/*      */     //   439: aconst_null
/*      */     //   440: putfield 1221	net/minecraft/server/v1_8_R3/Entity:vehicle	Lnet/minecraft/server/v1_8_R3/Entity;
/*      */     //   443: aload_0
/*      */     //   444: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   447: ldc_w 1452
/*      */     //   450: invokevirtual 685	net/minecraft/server/v1_8_R3/MethodProfiler:a	(Ljava/lang/String;)V
/*      */     //   453: aload_2
/*      */     //   454: getfield 1139	net/minecraft/server/v1_8_R3/Entity:dead	Z
/*      */     //   457: ifne +61 -> 518
/*      */     //   460: getstatic 1455	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings:tickEntityTimer	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   463: invokevirtual 1447	org/spigotmc/CustomTimingsHandler:startTiming	()V
/*      */     //   466: aload_0
/*      */     //   467: aload_2
/*      */     //   468: invokevirtual 1457	net/minecraft/server/v1_8_R3/World:g	(Lnet/minecraft/server/v1_8_R3/Entity;)V
/*      */     //   471: getstatic 1455	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings:tickEntityTimer	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   474: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   477: goto +41 -> 518
/*      */     //   480: astore 8
/*      */     //   482: aload 8
/*      */     //   484: ldc_w 1408
/*      */     //   487: invokestatic 449	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   490: astore 4
/*      */     //   492: aload 4
/*      */     //   494: ldc_w 1410
/*      */     //   497: invokevirtual 454	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;
/*      */     //   500: astore 5
/*      */     //   502: aload_2
/*      */     //   503: aload 5
/*      */     //   505: invokevirtual 1421	net/minecraft/server/v1_8_R3/Entity:appendEntityCrashDetails	(Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;)V
/*      */     //   508: new 466	net/minecraft/server/v1_8_R3/ReportedException
/*      */     //   511: dup
/*      */     //   512: aload 4
/*      */     //   514: invokespecial 469	net/minecraft/server/v1_8_R3/ReportedException:<init>	(Lnet/minecraft/server/v1_8_R3/CrashReport;)V
/*      */     //   517: athrow
/*      */     //   518: aload_0
/*      */     //   519: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   522: invokevirtual 689	net/minecraft/server/v1_8_R3/MethodProfiler:b	()V
/*      */     //   525: aload_0
/*      */     //   526: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   529: ldc_w 1422
/*      */     //   532: invokevirtual 685	net/minecraft/server/v1_8_R3/MethodProfiler:a	(Ljava/lang/String;)V
/*      */     //   535: aload_2
/*      */     //   536: getfield 1139	net/minecraft/server/v1_8_R3/Entity:dead	Z
/*      */     //   539: ifeq +79 -> 618
/*      */     //   542: aload_2
/*      */     //   543: getfield 1253	net/minecraft/server/v1_8_R3/Entity:ae	I
/*      */     //   546: istore_3
/*      */     //   547: aload_2
/*      */     //   548: getfield 1256	net/minecraft/server/v1_8_R3/Entity:ag	I
/*      */     //   551: istore 6
/*      */     //   553: aload_2
/*      */     //   554: getfield 1259	net/minecraft/server/v1_8_R3/Entity:ad	Z
/*      */     //   557: ifeq +25 -> 582
/*      */     //   560: aload_0
/*      */     //   561: iload_3
/*      */     //   562: iload 6
/*      */     //   564: iconst_1
/*      */     //   565: invokevirtual 555	net/minecraft/server/v1_8_R3/World:isChunkLoaded	(IIZ)Z
/*      */     //   568: ifeq +14 -> 582
/*      */     //   571: aload_0
/*      */     //   572: iload_3
/*      */     //   573: iload 6
/*      */     //   575: invokevirtual 610	net/minecraft/server/v1_8_R3/World:getChunkAt	(II)Lnet/minecraft/server/v1_8_R3/Chunk;
/*      */     //   578: aload_2
/*      */     //   579: invokevirtual 1260	net/minecraft/server/v1_8_R3/Chunk:b	(Lnet/minecraft/server/v1_8_R3/Entity;)V
/*      */     //   582: aload_0
/*      */     //   583: iconst_0
/*      */     //   584: putfield 1250	net/minecraft/server/v1_8_R3/World:guardEntityList	Z
/*      */     //   587: aload_0
/*      */     //   588: getfield 216	net/minecraft/server/v1_8_R3/World:entityList	Ljava/util/List;
/*      */     //   591: aload_0
/*      */     //   592: dup
/*      */     //   593: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   596: dup_x1
/*      */     //   597: iconst_1
/*      */     //   598: isub
/*      */     //   599: putfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   602: invokeinterface 1268 2 0
/*      */     //   607: pop
/*      */     //   608: aload_0
/*      */     //   609: iconst_1
/*      */     //   610: putfield 1250	net/minecraft/server/v1_8_R3/World:guardEntityList	Z
/*      */     //   613: aload_0
/*      */     //   614: aload_2
/*      */     //   615: invokevirtual 1240	net/minecraft/server/v1_8_R3/World:b	(Lnet/minecraft/server/v1_8_R3/Entity;)V
/*      */     //   618: aload_0
/*      */     //   619: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   622: invokevirtual 689	net/minecraft/server/v1_8_R3/MethodProfiler:b	()V
/*      */     //   625: aload_0
/*      */     //   626: dup
/*      */     //   627: getfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   630: iconst_1
/*      */     //   631: iadd
/*      */     //   632: putfield 1266	net/minecraft/server/v1_8_R3/World:tickPosition	I
/*      */     //   635: iinc 7 1
/*      */     //   638: iload 7
/*      */     //   640: aload_0
/*      */     //   641: getfield 216	net/minecraft/server/v1_8_R3/World:entityList	Ljava/util/List;
/*      */     //   644: invokeinterface 760 1 0
/*      */     //   649: if_icmpge +21 -> 670
/*      */     //   652: iload 7
/*      */     //   654: bipush 10
/*      */     //   656: irem
/*      */     //   657: ifne -303 -> 354
/*      */     //   660: aload_0
/*      */     //   661: getfield 410	net/minecraft/server/v1_8_R3/World:entityLimiter	Lorg/spigotmc/TickLimiter;
/*      */     //   664: invokevirtual 1463	org/spigotmc/TickLimiter:shouldContinue	()Z
/*      */     //   667: ifne -313 -> 354
/*      */     //   670: aload_0
/*      */     //   671: iconst_0
/*      */     //   672: putfield 1250	net/minecraft/server/v1_8_R3/World:guardEntityList	Z
/*      */     //   675: aload_0
/*      */     //   676: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   679: getfield 1442	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:entityTick	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   682: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   685: aload_0
/*      */     //   686: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   689: ldc_w 1465
/*      */     //   692: invokevirtual 1424	net/minecraft/server/v1_8_R3/MethodProfiler:c	(Ljava/lang/String;)V
/*      */     //   695: aload_0
/*      */     //   696: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   699: getfield 1468	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:tileEntityTick	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   702: invokevirtual 1447	org/spigotmc/CustomTimingsHandler:startTiming	()V
/*      */     //   705: aload_0
/*      */     //   706: iconst_1
/*      */     //   707: putfield 1470	net/minecraft/server/v1_8_R3/World:M	Z
/*      */     //   710: aload_0
/*      */     //   711: getfield 232	net/minecraft/server/v1_8_R3/World:c	Ljava/util/List;
/*      */     //   714: invokeinterface 1471 1 0
/*      */     //   719: ifne +40 -> 759
/*      */     //   722: aload_0
/*      */     //   723: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   726: aload_0
/*      */     //   727: getfield 232	net/minecraft/server/v1_8_R3/World:c	Ljava/util/List;
/*      */     //   730: invokeinterface 1428 2 0
/*      */     //   735: pop
/*      */     //   736: aload_0
/*      */     //   737: getfield 226	net/minecraft/server/v1_8_R3/World:h	Ljava/util/List;
/*      */     //   740: aload_0
/*      */     //   741: getfield 232	net/minecraft/server/v1_8_R3/World:c	Ljava/util/List;
/*      */     //   744: invokeinterface 1428 2 0
/*      */     //   749: pop
/*      */     //   750: aload_0
/*      */     //   751: getfield 232	net/minecraft/server/v1_8_R3/World:c	Ljava/util/List;
/*      */     //   754: invokeinterface 1431 1 0
/*      */     //   759: iconst_0
/*      */     //   760: istore 8
/*      */     //   762: aload_0
/*      */     //   763: getfield 415	net/minecraft/server/v1_8_R3/World:tileLimiter	Lorg/spigotmc/TickLimiter;
/*      */     //   766: invokevirtual 1450	org/spigotmc/TickLimiter:initTick	()V
/*      */     //   769: goto +305 -> 1074
/*      */     //   772: aload_0
/*      */     //   773: aload_0
/*      */     //   774: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   777: aload_0
/*      */     //   778: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   781: invokeinterface 760 1 0
/*      */     //   786: if_icmpge +10 -> 796
/*      */     //   789: aload_0
/*      */     //   790: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   793: goto +4 -> 797
/*      */     //   796: iconst_0
/*      */     //   797: putfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   800: aload_0
/*      */     //   801: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   804: aload_0
/*      */     //   805: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   808: invokeinterface 753 2 0
/*      */     //   813: checkcast 1475	net/minecraft/server/v1_8_R3/TileEntity
/*      */     //   816: astore 9
/*      */     //   818: aload 9
/*      */     //   820: ifnonnull +43 -> 863
/*      */     //   823: aload_0
/*      */     //   824: invokevirtual 319	net/minecraft/server/v1_8_R3/World:getServer	()Lorg/bukkit/craftbukkit/v1_8_R3/CraftServer;
/*      */     //   827: invokevirtual 1479	org/bukkit/craftbukkit/v1_8_R3/CraftServer:getLogger	()Ljava/util/logging/Logger;
/*      */     //   830: ldc_w 1481
/*      */     //   833: invokevirtual 1486	java/util/logging/Logger:severe	(Ljava/lang/String;)V
/*      */     //   836: iinc 8 -1
/*      */     //   839: aload_0
/*      */     //   840: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   843: aload_0
/*      */     //   844: dup
/*      */     //   845: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   848: dup_x1
/*      */     //   849: iconst_1
/*      */     //   850: isub
/*      */     //   851: putfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   854: invokeinterface 1268 2 0
/*      */     //   859: pop
/*      */     //   860: goto +201 -> 1061
/*      */     //   863: aload 9
/*      */     //   865: invokevirtual 1488	net/minecraft/server/v1_8_R3/TileEntity:x	()Z
/*      */     //   868: ifne +120 -> 988
/*      */     //   871: aload 9
/*      */     //   873: invokevirtual 1491	net/minecraft/server/v1_8_R3/TileEntity:t	()Z
/*      */     //   876: ifeq +112 -> 988
/*      */     //   879: aload 9
/*      */     //   881: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   884: astore 10
/*      */     //   886: aload_0
/*      */     //   887: aload 10
/*      */     //   889: invokevirtual 432	net/minecraft/server/v1_8_R3/World:isLoaded	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Z
/*      */     //   892: ifeq +96 -> 988
/*      */     //   895: aload_0
/*      */     //   896: getfield 387	net/minecraft/server/v1_8_R3/World:N	Lnet/minecraft/server/v1_8_R3/WorldBorder;
/*      */     //   899: aload 10
/*      */     //   901: invokevirtual 1298	net/minecraft/server/v1_8_R3/WorldBorder:a	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Z
/*      */     //   904: ifeq +84 -> 988
/*      */     //   907: aload 9
/*      */     //   909: getfield 1497	net/minecraft/server/v1_8_R3/TileEntity:tickTimer	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   912: invokevirtual 1447	org/spigotmc/CustomTimingsHandler:startTiming	()V
/*      */     //   915: aload 9
/*      */     //   917: checkcast 1499	net/minecraft/server/v1_8_R3/IUpdatePlayerListBox
/*      */     //   920: invokeinterface 1501 1 0
/*      */     //   925: goto +55 -> 980
/*      */     //   928: astore 11
/*      */     //   930: aload 11
/*      */     //   932: ldc_w 1503
/*      */     //   935: invokestatic 449	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/Throwable;Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReport;
/*      */     //   938: astore 12
/*      */     //   940: aload 12
/*      */     //   942: ldc_w 1505
/*      */     //   945: invokevirtual 454	net/minecraft/server/v1_8_R3/CrashReport:a	(Ljava/lang/String;)Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;
/*      */     //   948: astore 13
/*      */     //   950: aload 9
/*      */     //   952: aload 13
/*      */     //   954: invokevirtual 1507	net/minecraft/server/v1_8_R3/TileEntity:a	(Lnet/minecraft/server/v1_8_R3/CrashReportSystemDetails;)V
/*      */     //   957: new 466	net/minecraft/server/v1_8_R3/ReportedException
/*      */     //   960: dup
/*      */     //   961: aload 12
/*      */     //   963: invokespecial 469	net/minecraft/server/v1_8_R3/ReportedException:<init>	(Lnet/minecraft/server/v1_8_R3/CrashReport;)V
/*      */     //   966: athrow
/*      */     //   967: astore 14
/*      */     //   969: aload 9
/*      */     //   971: getfield 1497	net/minecraft/server/v1_8_R3/TileEntity:tickTimer	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   974: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   977: aload 14
/*      */     //   979: athrow
/*      */     //   980: aload 9
/*      */     //   982: getfield 1497	net/minecraft/server/v1_8_R3/TileEntity:tickTimer	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   985: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   988: aload 9
/*      */     //   990: invokevirtual 1488	net/minecraft/server/v1_8_R3/TileEntity:x	()Z
/*      */     //   993: ifeq +68 -> 1061
/*      */     //   996: iinc 8 -1
/*      */     //   999: aload_0
/*      */     //   1000: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   1003: aload_0
/*      */     //   1004: dup
/*      */     //   1005: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   1008: dup_x1
/*      */     //   1009: iconst_1
/*      */     //   1010: isub
/*      */     //   1011: putfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   1014: invokeinterface 1268 2 0
/*      */     //   1019: pop
/*      */     //   1020: aload_0
/*      */     //   1021: getfield 226	net/minecraft/server/v1_8_R3/World:h	Ljava/util/List;
/*      */     //   1024: aload 9
/*      */     //   1026: invokeinterface 1223 2 0
/*      */     //   1031: pop
/*      */     //   1032: aload_0
/*      */     //   1033: aload 9
/*      */     //   1035: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1038: invokevirtual 432	net/minecraft/server/v1_8_R3/World:isLoaded	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Z
/*      */     //   1041: ifeq +20 -> 1061
/*      */     //   1044: aload_0
/*      */     //   1045: aload 9
/*      */     //   1047: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1050: invokevirtual 436	net/minecraft/server/v1_8_R3/World:getChunkAtWorldCoords	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Lnet/minecraft/server/v1_8_R3/Chunk;
/*      */     //   1053: aload 9
/*      */     //   1055: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1058: invokevirtual 1509	net/minecraft/server/v1_8_R3/Chunk:e	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)V
/*      */     //   1061: aload_0
/*      */     //   1062: dup
/*      */     //   1063: getfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   1066: iconst_1
/*      */     //   1067: iadd
/*      */     //   1068: putfield 1473	net/minecraft/server/v1_8_R3/World:tileTickPosition	I
/*      */     //   1071: iinc 8 1
/*      */     //   1074: iload 8
/*      */     //   1076: aload_0
/*      */     //   1077: getfield 228	net/minecraft/server/v1_8_R3/World:tileEntityList	Ljava/util/List;
/*      */     //   1080: invokeinterface 760 1 0
/*      */     //   1085: if_icmpge +21 -> 1106
/*      */     //   1088: iload 8
/*      */     //   1090: bipush 10
/*      */     //   1092: irem
/*      */     //   1093: ifne -321 -> 772
/*      */     //   1096: aload_0
/*      */     //   1097: getfield 415	net/minecraft/server/v1_8_R3/World:tileLimiter	Lorg/spigotmc/TickLimiter;
/*      */     //   1100: invokevirtual 1463	org/spigotmc/TickLimiter:shouldContinue	()Z
/*      */     //   1103: ifne -331 -> 772
/*      */     //   1106: aload_0
/*      */     //   1107: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   1110: getfield 1468	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:tileEntityTick	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   1113: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   1116: aload_0
/*      */     //   1117: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   1120: getfield 1512	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:tileEntityPending	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   1123: invokevirtual 1447	org/spigotmc/CustomTimingsHandler:startTiming	()V
/*      */     //   1126: aload_0
/*      */     //   1127: iconst_0
/*      */     //   1128: putfield 1470	net/minecraft/server/v1_8_R3/World:M	Z
/*      */     //   1131: aload_0
/*      */     //   1132: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   1135: ldc_w 1514
/*      */     //   1138: invokevirtual 1424	net/minecraft/server/v1_8_R3/MethodProfiler:c	(Ljava/lang/String;)V
/*      */     //   1141: aload_0
/*      */     //   1142: getfield 230	net/minecraft/server/v1_8_R3/World:b	Ljava/util/List;
/*      */     //   1145: invokeinterface 1471 1 0
/*      */     //   1150: ifne +99 -> 1249
/*      */     //   1153: iconst_0
/*      */     //   1154: istore 9
/*      */     //   1156: goto +70 -> 1226
/*      */     //   1159: aload_0
/*      */     //   1160: getfield 230	net/minecraft/server/v1_8_R3/World:b	Ljava/util/List;
/*      */     //   1163: iload 9
/*      */     //   1165: invokeinterface 753 2 0
/*      */     //   1170: checkcast 1475	net/minecraft/server/v1_8_R3/TileEntity
/*      */     //   1173: astore 10
/*      */     //   1175: aload 10
/*      */     //   1177: invokevirtual 1488	net/minecraft/server/v1_8_R3/TileEntity:x	()Z
/*      */     //   1180: ifne +43 -> 1223
/*      */     //   1183: aload_0
/*      */     //   1184: aload 10
/*      */     //   1186: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1189: invokevirtual 432	net/minecraft/server/v1_8_R3/World:isLoaded	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Z
/*      */     //   1192: ifeq +22 -> 1214
/*      */     //   1195: aload_0
/*      */     //   1196: aload 10
/*      */     //   1198: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1201: invokevirtual 436	net/minecraft/server/v1_8_R3/World:getChunkAtWorldCoords	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)Lnet/minecraft/server/v1_8_R3/Chunk;
/*      */     //   1204: aload 10
/*      */     //   1206: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1209: aload 10
/*      */     //   1211: invokevirtual 1517	net/minecraft/server/v1_8_R3/Chunk:a	(Lnet/minecraft/server/v1_8_R3/BlockPosition;Lnet/minecraft/server/v1_8_R3/TileEntity;)V
/*      */     //   1214: aload_0
/*      */     //   1215: aload 10
/*      */     //   1217: invokevirtual 1494	net/minecraft/server/v1_8_R3/TileEntity:getPosition	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*      */     //   1220: invokevirtual 712	net/minecraft/server/v1_8_R3/World:notify	(Lnet/minecraft/server/v1_8_R3/BlockPosition;)V
/*      */     //   1223: iinc 9 1
/*      */     //   1226: iload 9
/*      */     //   1228: aload_0
/*      */     //   1229: getfield 230	net/minecraft/server/v1_8_R3/World:b	Ljava/util/List;
/*      */     //   1232: invokeinterface 760 1 0
/*      */     //   1237: if_icmplt -78 -> 1159
/*      */     //   1240: aload_0
/*      */     //   1241: getfield 230	net/minecraft/server/v1_8_R3/World:b	Ljava/util/List;
/*      */     //   1244: invokeinterface 1431 1 0
/*      */     //   1249: aload_0
/*      */     //   1250: getfield 400	net/minecraft/server/v1_8_R3/World:timings	Lorg/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler;
/*      */     //   1253: getfield 1512	org/bukkit/craftbukkit/v1_8_R3/SpigotTimings$WorldTimingsHandler:tileEntityPending	Lorg/spigotmc/CustomTimingsHandler;
/*      */     //   1256: invokevirtual 1460	org/spigotmc/CustomTimingsHandler:stopTiming	()V
/*      */     //   1259: aload_0
/*      */     //   1260: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   1263: invokevirtual 689	net/minecraft/server/v1_8_R3/MethodProfiler:b	()V
/*      */     //   1266: aload_0
/*      */     //   1267: getfield 375	net/minecraft/server/v1_8_R3/World:methodProfiler	Lnet/minecraft/server/v1_8_R3/MethodProfiler;
/*      */     //   1270: invokevirtual 689	net/minecraft/server/v1_8_R3/MethodProfiler:b	()V
/*      */     //   1273: return
/*      */     // Line number table:
/*      */     //   Java source line #1342	-> byte code offset #0
/*      */     //   Java source line #1343	-> byte code offset #10
/*      */     //   Java source line #1350	-> byte code offset #20
/*      */     //   Java source line #1351	-> byte code offset #25
/*      */     //   Java source line #1353	-> byte code offset #39
/*      */     //   Java source line #1354	-> byte code offset #43
/*      */     //   Java source line #1359	-> byte code offset #46
/*      */     //   Java source line #1360	-> byte code offset #56
/*      */     //   Java source line #1361	-> byte code offset #60
/*      */     //   Java source line #1362	-> byte code offset #64
/*      */     //   Java source line #1363	-> byte code offset #73
/*      */     //   Java source line #1364	-> byte code offset #83
/*      */     //   Java source line #1365	-> byte code offset #87
/*      */     //   Java source line #1366	-> byte code offset #98
/*      */     //   Java source line #1367	-> byte code offset #101
/*      */     //   Java source line #1370	-> byte code offset #107
/*      */     //   Java source line #1373	-> byte code offset #117
/*      */     //   Java source line #1374	-> byte code offset #124
/*      */     //   Java source line #1350	-> byte code offset #138
/*      */     //   Java source line #1378	-> byte code offset #154
/*      */     //   Java source line #1379	-> byte code offset #164
/*      */     //   Java source line #1384	-> byte code offset #178
/*      */     //   Java source line #1385	-> byte code offset #183
/*      */     //   Java source line #1386	-> byte code offset #197
/*      */     //   Java source line #1387	-> byte code offset #202
/*      */     //   Java source line #1388	-> byte code offset #208
/*      */     //   Java source line #1389	-> byte code offset #226
/*      */     //   Java source line #1384	-> byte code offset #237
/*      */     //   Java source line #1393	-> byte code offset #253
/*      */     //   Java source line #1394	-> byte code offset #258
/*      */     //   Java source line #1393	-> byte code offset #275
/*      */     //   Java source line #1397	-> byte code offset #291
/*      */     //   Java source line #1398	-> byte code offset #300
/*      */     //   Java source line #1400	-> byte code offset #310
/*      */     //   Java source line #1401	-> byte code offset #314
/*      */     //   Java source line #1402	-> byte code offset #324
/*      */     //   Java source line #1404	-> byte code offset #329
/*      */     //   Java source line #1405	-> byte code offset #332
/*      */     //   Java source line #1406	-> byte code offset #344
/*      */     //   Java source line #1407	-> byte code offset #351
/*      */     //   Java source line #1409	-> byte code offset #354
/*      */     //   Java source line #1410	-> byte code offset #382
/*      */     //   Java source line #1412	-> byte code offset #399
/*      */     //   Java source line #1413	-> byte code offset #406
/*      */     //   Java source line #1414	-> byte code offset #427
/*      */     //   Java source line #1417	-> byte code offset #430
/*      */     //   Java source line #1418	-> byte code offset #438
/*      */     //   Java source line #1421	-> byte code offset #443
/*      */     //   Java source line #1422	-> byte code offset #453
/*      */     //   Java source line #1424	-> byte code offset #460
/*      */     //   Java source line #1425	-> byte code offset #466
/*      */     //   Java source line #1426	-> byte code offset #471
/*      */     //   Java source line #1427	-> byte code offset #477
/*      */     //   Java source line #1428	-> byte code offset #482
/*      */     //   Java source line #1429	-> byte code offset #492
/*      */     //   Java source line #1430	-> byte code offset #502
/*      */     //   Java source line #1431	-> byte code offset #508
/*      */     //   Java source line #1435	-> byte code offset #518
/*      */     //   Java source line #1436	-> byte code offset #525
/*      */     //   Java source line #1437	-> byte code offset #535
/*      */     //   Java source line #1438	-> byte code offset #542
/*      */     //   Java source line #1439	-> byte code offset #547
/*      */     //   Java source line #1440	-> byte code offset #553
/*      */     //   Java source line #1441	-> byte code offset #571
/*      */     //   Java source line #1444	-> byte code offset #582
/*      */     //   Java source line #1445	-> byte code offset #587
/*      */     //   Java source line #1446	-> byte code offset #608
/*      */     //   Java source line #1447	-> byte code offset #613
/*      */     //   Java source line #1450	-> byte code offset #618
/*      */     //   Java source line #1408	-> byte code offset #625
/*      */     //   Java source line #1407	-> byte code offset #638
/*      */     //   Java source line #1452	-> byte code offset #670
/*      */     //   Java source line #1454	-> byte code offset #675
/*      */     //   Java source line #1455	-> byte code offset #685
/*      */     //   Java source line #1456	-> byte code offset #695
/*      */     //   Java source line #1457	-> byte code offset #705
/*      */     //   Java source line #1459	-> byte code offset #710
/*      */     //   Java source line #1460	-> byte code offset #722
/*      */     //   Java source line #1461	-> byte code offset #736
/*      */     //   Java source line #1462	-> byte code offset #750
/*      */     //   Java source line #1467	-> byte code offset #759
/*      */     //   Java source line #1468	-> byte code offset #762
/*      */     //   Java source line #1469	-> byte code offset #769
/*      */     //   Java source line #1471	-> byte code offset #772
/*      */     //   Java source line #1472	-> byte code offset #800
/*      */     //   Java source line #1474	-> byte code offset #818
/*      */     //   Java source line #1475	-> byte code offset #823
/*      */     //   Java source line #1476	-> byte code offset #836
/*      */     //   Java source line #1477	-> byte code offset #839
/*      */     //   Java source line #1478	-> byte code offset #860
/*      */     //   Java source line #1482	-> byte code offset #863
/*      */     //   Java source line #1483	-> byte code offset #879
/*      */     //   Java source line #1485	-> byte code offset #886
/*      */     //   Java source line #1487	-> byte code offset #907
/*      */     //   Java source line #1488	-> byte code offset #915
/*      */     //   Java source line #1489	-> byte code offset #925
/*      */     //   Java source line #1490	-> byte code offset #930
/*      */     //   Java source line #1491	-> byte code offset #940
/*      */     //   Java source line #1493	-> byte code offset #950
/*      */     //   Java source line #1494	-> byte code offset #957
/*      */     //   Java source line #1497	-> byte code offset #967
/*      */     //   Java source line #1498	-> byte code offset #969
/*      */     //   Java source line #1499	-> byte code offset #977
/*      */     //   Java source line #1498	-> byte code offset #980
/*      */     //   Java source line #1504	-> byte code offset #988
/*      */     //   Java source line #1505	-> byte code offset #996
/*      */     //   Java source line #1506	-> byte code offset #999
/*      */     //   Java source line #1507	-> byte code offset #1020
/*      */     //   Java source line #1508	-> byte code offset #1032
/*      */     //   Java source line #1509	-> byte code offset #1044
/*      */     //   Java source line #1470	-> byte code offset #1061
/*      */     //   Java source line #1469	-> byte code offset #1074
/*      */     //   Java source line #1514	-> byte code offset #1106
/*      */     //   Java source line #1515	-> byte code offset #1116
/*      */     //   Java source line #1516	-> byte code offset #1126
/*      */     //   Java source line #1525	-> byte code offset #1131
/*      */     //   Java source line #1526	-> byte code offset #1141
/*      */     //   Java source line #1527	-> byte code offset #1153
/*      */     //   Java source line #1528	-> byte code offset #1159
/*      */     //   Java source line #1530	-> byte code offset #1175
/*      */     //   Java source line #1537	-> byte code offset #1183
/*      */     //   Java source line #1538	-> byte code offset #1195
/*      */     //   Java source line #1541	-> byte code offset #1214
/*      */     //   Java source line #1527	-> byte code offset #1223
/*      */     //   Java source line #1545	-> byte code offset #1240
/*      */     //   Java source line #1548	-> byte code offset #1249
/*      */     //   Java source line #1549	-> byte code offset #1259
/*      */     //   Java source line #1550	-> byte code offset #1266
/*      */     //   Java source line #1551	-> byte code offset #1273
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	1274	0	this	World
/*      */     //   21	258	1	i	int
/*      */     //   38	80	2	entity	Entity
/*      */     //   196	38	2	entity	Entity
/*      */     //   398	217	2	entity	Entity
/*      */     //   63	2	3	throwable	Throwable
/*      */     //   201	27	3	j	int
/*      */     //   546	27	3	j	int
/*      */     //   71	41	4	crashreport	CrashReport
/*      */     //   490	23	4	crashreport	CrashReport
/*      */     //   81	22	5	crashreportsystemdetails	CrashReportSystemDetails
/*      */     //   500	4	5	crashreportsystemdetails	CrashReportSystemDetails
/*      */     //   206	23	6	k	int
/*      */     //   551	23	6	k	int
/*      */     //   330	323	7	entitiesThisCycle	int
/*      */     //   480	3	8	throwable1	Throwable
/*      */     //   760	329	8	tilesThisCycle	int
/*      */     //   816	238	9	tileentity	TileEntity
/*      */     //   1154	73	9	l	int
/*      */     //   884	16	10	blockposition	BlockPosition
/*      */     //   1173	43	10	tileentity1	TileEntity
/*      */     //   928	3	11	throwable2	Throwable
/*      */     //   938	24	12	crashreport1	CrashReport
/*      */     //   948	5	13	crashreportsystemdetails1	CrashReportSystemDetails
/*      */     //   967	11	14	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   46	60	63	java/lang/Throwable
/*      */     //   460	477	480	java/lang/Throwable
/*      */     //   907	925	928	java/lang/Throwable
/*      */     //   907	967	967	finally
/*      */   }
/*      */   
/*      */   public boolean a(TileEntity tileentity)
/*      */   {
/* 1554 */     boolean flag = this.h.add(tileentity);
/*      */     
/* 1556 */     if ((flag) && ((tileentity instanceof IUpdatePlayerListBox))) {
/* 1557 */       this.tileEntityList.add(tileentity);
/*      */     }
/*      */     
/* 1560 */     return flag;
/*      */   }
/*      */   
/*      */   public void a(Collection<TileEntity> collection) {
/* 1564 */     if (this.M) {
/* 1565 */       this.b.addAll(collection);
/*      */     } else {
/* 1567 */       Iterator iterator = collection.iterator();
/*      */       
/* 1569 */       while (iterator.hasNext()) {
/* 1570 */         TileEntity tileentity = (TileEntity)iterator.next();
/*      */         
/* 1572 */         this.h.add(tileentity);
/* 1573 */         if ((tileentity instanceof IUpdatePlayerListBox)) {
/* 1574 */           this.tileEntityList.add(tileentity);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void g(Entity entity)
/*      */   {
/* 1582 */     entityJoinedWorld(entity, true);
/*      */   }
/*      */   
/*      */   public void entityJoinedWorld(Entity entity, boolean flag) {
/* 1586 */     MathHelper.floor(entity.locX);
/* 1587 */     MathHelper.floor(entity.locZ);
/*      */     
/*      */ 
/*      */ 
/* 1591 */     if (!ActivationRange.checkIfActive(entity)) {
/* 1592 */       entity.ticksLived += 1;
/* 1593 */       entity.inactiveTick();
/*      */     } else {
/* 1595 */       entity.tickTimer.startTiming();
/*      */       
/* 1597 */       entity.P = entity.locX;
/* 1598 */       entity.Q = entity.locY;
/* 1599 */       entity.R = entity.locZ;
/* 1600 */       entity.lastYaw = entity.yaw;
/* 1601 */       entity.lastPitch = entity.pitch;
/* 1602 */       if ((flag) && (entity.ad)) {
/* 1603 */         entity.ticksLived += 1;
/* 1604 */         if (entity.vehicle != null) {
/* 1605 */           entity.ak();
/*      */         } else {
/* 1607 */           entity.t_();
/*      */         }
/*      */       }
/*      */       
/* 1611 */       this.methodProfiler.a("chunkCheck");
/* 1612 */       if ((Double.isNaN(entity.locX)) || (Double.isInfinite(entity.locX))) {
/* 1613 */         entity.locX = entity.P;
/*      */       }
/*      */       
/* 1616 */       if ((Double.isNaN(entity.locY)) || (Double.isInfinite(entity.locY))) {
/* 1617 */         entity.locY = entity.Q;
/*      */       }
/*      */       
/* 1620 */       if ((Double.isNaN(entity.locZ)) || (Double.isInfinite(entity.locZ))) {
/* 1621 */         entity.locZ = entity.R;
/*      */       }
/*      */       
/* 1624 */       if ((Double.isNaN(entity.pitch)) || (Double.isInfinite(entity.pitch))) {
/* 1625 */         entity.pitch = entity.lastPitch;
/*      */       }
/*      */       
/* 1628 */       if ((Double.isNaN(entity.yaw)) || (Double.isInfinite(entity.yaw))) {
/* 1629 */         entity.yaw = entity.lastYaw;
/*      */       }
/*      */       
/* 1632 */       int k = MathHelper.floor(entity.locX / 16.0D);
/* 1633 */       int l = MathHelper.floor(entity.locY / 16.0D);
/* 1634 */       int i1 = MathHelper.floor(entity.locZ / 16.0D);
/*      */       
/* 1636 */       if ((!entity.ad) || (entity.ae != k) || (entity.af != l) || (entity.ag != i1)) {
/* 1637 */         if ((entity.ad) && (isChunkLoaded(entity.ae, entity.ag, true))) {
/* 1638 */           getChunkAt(entity.ae, entity.ag).a(entity, entity.af);
/*      */         }
/*      */         
/* 1641 */         if (isChunkLoaded(k, i1, true)) {
/* 1642 */           entity.ad = true;
/* 1643 */           getChunkAt(k, i1).a(entity);
/*      */         } else {
/* 1645 */           entity.ad = false;
/*      */         }
/*      */       }
/*      */       
/* 1649 */       this.methodProfiler.b();
/* 1650 */       if ((flag) && (entity.ad) && (entity.passenger != null)) {
/* 1651 */         if ((!entity.passenger.dead) && (entity.passenger.vehicle == entity)) {
/* 1652 */           g(entity.passenger);
/*      */         } else {
/* 1654 */           entity.passenger.vehicle = null;
/* 1655 */           entity.passenger = null;
/*      */         }
/*      */       }
/*      */       
/* 1659 */       entity.tickTimer.stopTiming();
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb) {
/* 1664 */     return a(axisalignedbb, null);
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Entity entity) {
/* 1668 */     List list = getEntities(null, axisalignedbb);
/*      */     
/* 1670 */     for (int i = 0; i < list.size(); i++) {
/* 1671 */       Entity entity1 = (Entity)list.get(i);
/*      */       
/* 1673 */       if ((!entity1.dead) && (entity1.k) && (entity1 != entity) && ((entity == null) || ((entity.vehicle != entity1) && (entity.passenger != entity1)))) {
/* 1674 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1678 */     return true;
/*      */   }
/*      */   
/*      */   public boolean c(AxisAlignedBB axisalignedbb) {
/* 1682 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1683 */     int j = MathHelper.floor(axisalignedbb.d);
/* 1684 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1685 */     int l = MathHelper.floor(axisalignedbb.e);
/* 1686 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1687 */     int j1 = MathHelper.floor(axisalignedbb.f);
/* 1688 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1690 */     for (int k1 = i; k1 <= j; k1++) {
/* 1691 */       for (int l1 = k; l1 <= l; l1++) {
/* 1692 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1693 */           Block block = getType(blockposition_mutableblockposition.c(k1, l1, i2)).getBlock();
/*      */           
/* 1695 */           if (block.getMaterial() != Material.AIR) {
/* 1696 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1702 */     return false;
/*      */   }
/*      */   
/*      */   public boolean containsLiquid(AxisAlignedBB axisalignedbb) {
/* 1706 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1707 */     int j = MathHelper.floor(axisalignedbb.d);
/* 1708 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1709 */     int l = MathHelper.floor(axisalignedbb.e);
/* 1710 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1711 */     int j1 = MathHelper.floor(axisalignedbb.f);
/* 1712 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1714 */     for (int k1 = i; k1 <= j; k1++) {
/* 1715 */       for (int l1 = k; l1 <= l; l1++) {
/* 1716 */         for (int i2 = i1; i2 <= j1; i2++) {
/* 1717 */           Block block = getType(blockposition_mutableblockposition.c(k1, l1, i2)).getBlock();
/*      */           
/* 1719 */           if (block.getMaterial().isLiquid()) {
/* 1720 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1726 */     return false;
/*      */   }
/*      */   
/*      */   public boolean e(AxisAlignedBB axisalignedbb) {
/* 1730 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1731 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1732 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1733 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1734 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1735 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1737 */     if (isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1738 */       BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */       
/* 1740 */       for (int k1 = i; k1 < j; k1++) {
/* 1741 */         for (int l1 = k; l1 < l; l1++) {
/* 1742 */           for (int i2 = i1; i2 < j1; i2++) {
/* 1743 */             Block block = getType(blockposition_mutableblockposition.c(k1, l1, i2)).getBlock();
/*      */             
/* 1745 */             if ((block == Blocks.FIRE) || (block == Blocks.FLOWING_LAVA) || (block == Blocks.LAVA)) {
/* 1746 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1753 */     return false;
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material, Entity entity) {
/* 1757 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1758 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1759 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1760 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1761 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1762 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/*      */     
/* 1764 */     if (!isAreaLoaded(i, k, i1, j, l, j1, true)) {
/* 1765 */       return false;
/*      */     }
/* 1767 */     boolean flag = false;
/* 1768 */     Vec3D vec3d = new Vec3D(0.0D, 0.0D, 0.0D);
/* 1769 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1771 */     for (int k1 = i; k1 < j; k1++) {
/* 1772 */       for (int l1 = k; l1 < l; l1++) {
/* 1773 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1774 */           blockposition_mutableblockposition.c(k1, l1, i2);
/* 1775 */           IBlockData iblockdata = getType(blockposition_mutableblockposition);
/* 1776 */           Block block = iblockdata.getBlock();
/*      */           
/* 1778 */           if (block.getMaterial() == material) {
/* 1779 */             double d0 = l1 + 1 - BlockFluids.b(((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue());
/*      */             
/* 1781 */             if (l >= d0) {
/* 1782 */               flag = true;
/* 1783 */               vec3d = block.a(this, blockposition_mutableblockposition, entity, vec3d);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1790 */     if ((vec3d.b() > 0.0D) && (entity.aL())) {
/* 1791 */       vec3d = vec3d.a();
/* 1792 */       double d1 = 0.014D;
/*      */       
/* 1794 */       entity.motX += vec3d.a * d1;
/* 1795 */       entity.motY += vec3d.b * d1;
/* 1796 */       entity.motZ += vec3d.c * d1;
/*      */     }
/*      */     
/* 1799 */     return flag;
/*      */   }
/*      */   
/*      */   public boolean a(AxisAlignedBB axisalignedbb, Material material)
/*      */   {
/* 1804 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1805 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1806 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1807 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1808 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1809 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/* 1810 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1812 */     for (int k1 = i; k1 < j; k1++) {
/* 1813 */       for (int l1 = k; l1 < l; l1++) {
/* 1814 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1815 */           if (getType(blockposition_mutableblockposition.c(k1, l1, i2)).getBlock().getMaterial() == material) {
/* 1816 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1822 */     return false;
/*      */   }
/*      */   
/*      */   public boolean b(AxisAlignedBB axisalignedbb, Material material) {
/* 1826 */     int i = MathHelper.floor(axisalignedbb.a);
/* 1827 */     int j = MathHelper.floor(axisalignedbb.d + 1.0D);
/* 1828 */     int k = MathHelper.floor(axisalignedbb.b);
/* 1829 */     int l = MathHelper.floor(axisalignedbb.e + 1.0D);
/* 1830 */     int i1 = MathHelper.floor(axisalignedbb.c);
/* 1831 */     int j1 = MathHelper.floor(axisalignedbb.f + 1.0D);
/* 1832 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*      */     
/* 1834 */     for (int k1 = i; k1 < j; k1++) {
/* 1835 */       for (int l1 = k; l1 < l; l1++) {
/* 1836 */         for (int i2 = i1; i2 < j1; i2++) {
/* 1837 */           IBlockData iblockdata = getType(blockposition_mutableblockposition.c(k1, l1, i2));
/* 1838 */           Block block = iblockdata.getBlock();
/*      */           
/* 1840 */           if (block.getMaterial() == material) {
/* 1841 */             int j2 = ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue();
/* 1842 */             double d0 = l1 + 1;
/*      */             
/* 1844 */             if (j2 < 8) {
/* 1845 */               d0 = l1 + 1 - j2 / 8.0D;
/*      */             }
/*      */             
/* 1848 */             if (d0 >= axisalignedbb.b) {
/* 1849 */               return true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1856 */     return false;
/*      */   }
/*      */   
/*      */   public Explosion explode(Entity entity, double d0, double d1, double d2, float f, boolean flag) {
/* 1860 */     return createExplosion(entity, d0, d1, d2, f, false, flag);
/*      */   }
/*      */   
/*      */   public Explosion createExplosion(Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
/* 1864 */     Explosion explosion = new Explosion(this, entity, d0, d1, d2, f, flag, flag1);
/*      */     
/* 1866 */     explosion.a();
/* 1867 */     explosion.a(true);
/* 1868 */     return explosion;
/*      */   }
/*      */   
/*      */   public float a(Vec3D vec3d, AxisAlignedBB axisalignedbb) {
/* 1872 */     double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
/* 1873 */     double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
/* 1874 */     double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);
/* 1875 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 1876 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */     
/* 1878 */     if ((d0 >= 0.0D) && (d1 >= 0.0D) && (d2 >= 0.0D)) {
/* 1879 */       int i = 0;
/* 1880 */       int j = 0;
/*      */       
/* 1882 */       for (float f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/* 1883 */         for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/* 1884 */           for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/* 1885 */             double d5 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * f;
/* 1886 */             double d6 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * f1;
/* 1887 */             double d7 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * f2;
/*      */             
/* 1889 */             if (rayTrace(new Vec3D(d5 + d3, d6, d7 + d4), vec3d) == null) {
/* 1890 */               i++;
/*      */             }
/*      */             
/* 1893 */             j++;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1898 */       return i / j;
/*      */     }
/* 1900 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public boolean douseFire(EntityHuman entityhuman, BlockPosition blockposition, EnumDirection enumdirection)
/*      */   {
/* 1905 */     blockposition = blockposition.shift(enumdirection);
/* 1906 */     if (getType(blockposition).getBlock() == Blocks.FIRE) {
/* 1907 */       a(entityhuman, 1004, blockposition, 0);
/* 1908 */       setAir(blockposition);
/* 1909 */       return true;
/*      */     }
/* 1911 */     return false;
/*      */   }
/*      */   
/*      */ 
/* 1915 */   public Map<BlockPosition, TileEntity> capturedTileEntities = com.google.common.collect.Maps.newHashMap();
/*      */   
/*      */   public TileEntity getTileEntity(BlockPosition blockposition) {
/* 1918 */     if (!isValidLocation(blockposition)) {
/* 1919 */       return null;
/*      */     }
/*      */     
/* 1922 */     if (this.capturedTileEntities.containsKey(blockposition)) {
/* 1923 */       return (TileEntity)this.capturedTileEntities.get(blockposition);
/*      */     }
/*      */     
/*      */ 
/* 1927 */     TileEntity tileentity = null;
/*      */     
/*      */ 
/*      */ 
/* 1931 */     if (this.M) {
/* 1932 */       for (int i = 0; i < this.b.size(); i++) {
/* 1933 */         TileEntity tileentity1 = (TileEntity)this.b.get(i);
/* 1934 */         if ((!tileentity1.x()) && (tileentity1.getPosition().equals(blockposition))) {
/* 1935 */           tileentity = tileentity1;
/* 1936 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1941 */     if (tileentity == null) {
/* 1942 */       tileentity = getChunkAtWorldCoords(blockposition).a(blockposition, Chunk.EnumTileEntityState.IMMEDIATE);
/*      */     }
/*      */     
/* 1945 */     if (tileentity == null) {
/* 1946 */       for (int i = 0; i < this.b.size(); i++) {
/* 1947 */         TileEntity tileentity1 = (TileEntity)this.b.get(i);
/* 1948 */         if ((!tileentity1.x()) && (tileentity1.getPosition().equals(blockposition))) {
/* 1949 */           tileentity = tileentity1;
/* 1950 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1955 */     return tileentity;
/*      */   }
/*      */   
/*      */   public void setTileEntity(BlockPosition blockposition, TileEntity tileentity)
/*      */   {
/* 1960 */     if ((tileentity != null) && (!tileentity.x()))
/*      */     {
/* 1962 */       if (this.captureBlockStates) {
/* 1963 */         tileentity.a(this);
/* 1964 */         tileentity.a(blockposition);
/* 1965 */         this.capturedTileEntities.put(blockposition, tileentity);
/* 1966 */         return;
/*      */       }
/*      */       
/* 1969 */       if (this.M) {
/* 1970 */         tileentity.a(blockposition);
/* 1971 */         Iterator iterator = this.b.iterator();
/*      */         
/* 1973 */         while (iterator.hasNext()) {
/* 1974 */           TileEntity tileentity1 = (TileEntity)iterator.next();
/*      */           
/* 1976 */           if (tileentity1.getPosition().equals(blockposition)) {
/* 1977 */             tileentity1.y();
/* 1978 */             iterator.remove();
/*      */           }
/*      */         }
/*      */         
/* 1982 */         tileentity.a(this);
/* 1983 */         this.b.add(tileentity);
/*      */       } else {
/* 1985 */         a(tileentity);
/* 1986 */         getChunkAtWorldCoords(blockposition).a(blockposition, tileentity);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void t(BlockPosition blockposition)
/*      */   {
/* 1993 */     TileEntity tileentity = getTileEntity(blockposition);
/*      */     
/* 1995 */     if ((tileentity != null) && (this.M)) {
/* 1996 */       tileentity.y();
/* 1997 */       this.b.remove(tileentity);
/*      */     } else {
/* 1999 */       if (tileentity != null) {
/* 2000 */         this.b.remove(tileentity);
/* 2001 */         this.h.remove(tileentity);
/* 2002 */         this.tileEntityList.remove(tileentity);
/*      */       }
/*      */       
/* 2005 */       getChunkAtWorldCoords(blockposition).e(blockposition);
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(TileEntity tileentity)
/*      */   {
/* 2011 */     this.c.add(tileentity);
/*      */   }
/*      */   
/*      */   public boolean u(BlockPosition blockposition) {
/* 2015 */     IBlockData iblockdata = getType(blockposition);
/* 2016 */     AxisAlignedBB axisalignedbb = iblockdata.getBlock().a(this, blockposition, iblockdata);
/*      */     
/* 2018 */     return (axisalignedbb != null) && (axisalignedbb.a() >= 1.0D);
/*      */   }
/*      */   
/*      */   public static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 2022 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/* 2023 */     Block block = iblockdata.getBlock();
/*      */     
/* 2025 */     return (block.getMaterial().k()) && (block.d());
/*      */   }
/*      */   
/*      */   public boolean d(BlockPosition blockposition, boolean flag) {
/* 2029 */     if (!isValidLocation(blockposition)) {
/* 2030 */       return flag;
/*      */     }
/* 2032 */     Chunk chunk = this.chunkProvider.getChunkAt(blockposition);
/*      */     
/* 2034 */     if (chunk.isEmpty()) {
/* 2035 */       return flag;
/*      */     }
/* 2037 */     Block block = getType(blockposition).getBlock();
/*      */     
/* 2039 */     return (block.getMaterial().k()) && (block.d());
/*      */   }
/*      */   
/*      */ 
/*      */   public void B()
/*      */   {
/* 2045 */     int i = a(1.0F);
/*      */     
/* 2047 */     if (i != this.I) {
/* 2048 */       this.I = i;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSpawnFlags(boolean flag, boolean flag1)
/*      */   {
/* 2054 */     this.allowMonsters = flag;
/* 2055 */     this.allowAnimals = flag1;
/*      */   }
/*      */   
/*      */   public void doTick() {
/* 2059 */     p();
/*      */   }
/*      */   
/*      */   protected void C() {
/* 2063 */     if (this.worldData.hasStorm()) {
/* 2064 */       this.p = 1.0F;
/* 2065 */       if (this.worldData.isThundering()) {
/* 2066 */         this.r = 1.0F;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void p()
/*      */   {
/* 2073 */     if ((!this.worldProvider.o()) && 
/* 2074 */       (!this.isClientSide)) {
/* 2075 */       int i = this.worldData.A();
/*      */       
/* 2077 */       if (i > 0) {
/* 2078 */         i--;
/* 2079 */         this.worldData.i(i);
/* 2080 */         this.worldData.setThunderDuration(this.worldData.isThundering() ? 1 : 2);
/* 2081 */         this.worldData.setWeatherDuration(this.worldData.hasStorm() ? 1 : 2);
/*      */       }
/*      */       
/* 2084 */       int j = this.worldData.getThunderDuration();
/*      */       
/* 2086 */       if (j <= 0) {
/* 2087 */         if (this.worldData.isThundering()) {
/* 2088 */           this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
/*      */         } else {
/* 2090 */           this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
/*      */         }
/*      */       } else {
/* 2093 */         j--;
/* 2094 */         this.worldData.setThunderDuration(j);
/* 2095 */         if (j <= 0) {
/* 2096 */           this.worldData.setThundering(!this.worldData.isThundering());
/*      */         }
/*      */       }
/*      */       
/* 2100 */       this.q = this.r;
/* 2101 */       if (this.worldData.isThundering()) {
/* 2102 */         this.r = ((float)(this.r + 0.01D));
/*      */       } else {
/* 2104 */         this.r = ((float)(this.r - 0.01D));
/*      */       }
/*      */       
/* 2107 */       this.r = MathHelper.a(this.r, 0.0F, 1.0F);
/* 2108 */       int k = this.worldData.getWeatherDuration();
/*      */       
/* 2110 */       if (k <= 0) {
/* 2111 */         if (this.worldData.hasStorm()) {
/* 2112 */           this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
/*      */         } else {
/* 2114 */           this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
/*      */         }
/*      */       } else {
/* 2117 */         k--;
/* 2118 */         this.worldData.setWeatherDuration(k);
/* 2119 */         if (k <= 0) {
/* 2120 */           this.worldData.setStorm(!this.worldData.hasStorm());
/*      */         }
/*      */       }
/*      */       
/* 2124 */       this.o = this.p;
/* 2125 */       if (this.worldData.hasStorm()) {
/* 2126 */         this.p = ((float)(this.p + 0.01D));
/*      */       } else {
/* 2128 */         this.p = ((float)(this.p - 0.01D));
/*      */       }
/*      */       
/* 2131 */       this.p = MathHelper.a(this.p, 0.0F, 1.0F);
/*      */       
/*      */ 
/* 2134 */       for (int idx = 0; idx < this.players.size(); idx++) {
/* 2135 */         if (((EntityPlayer)this.players.get(idx)).world == this) {
/* 2136 */           ((EntityPlayer)this.players.get(idx)).tickWeather();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected void D()
/*      */   {
/* 2146 */     this.methodProfiler.a("buildList");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2155 */     int optimalChunks = this.spigotConfig.chunksPerTick;
/*      */     
/* 2157 */     if (optimalChunks > 0)
/*      */     {
/* 2159 */       int chunksPerPlayer = Math.min(200, Math.max(1, (int)((optimalChunks - this.players.size()) / this.players.size() + 0.5D)));
/* 2160 */       int randRange = 3 + chunksPerPlayer / 30;
/*      */       
/* 2162 */       randRange = randRange > this.chunkTickRadius ? this.chunkTickRadius : randRange;
/*      */       
/* 2164 */       this.growthOdds = (this.modifiedOdds = Math.max(35.0F, Math.min(100.0F, (chunksPerPlayer + 1) * 100.0F / 15.0F)));
/*      */       
/* 2166 */       for (int i = 0; i < this.players.size(); i++) {
/* 2167 */         EntityHuman entityhuman = (EntityHuman)this.players.get(i);
/* 2168 */         int j = MathHelper.floor(entityhuman.locX / 16.0D);
/* 2169 */         int k = MathHelper.floor(entityhuman.locZ / 16.0D);
/* 2170 */         int l = q();
/*      */         
/*      */ 
/* 2173 */         long key = chunkToKey(j, k);
/* 2174 */         int existingPlayers = Math.max(0, this.chunkTickList.get(key));
/* 2175 */         this.chunkTickList.put(key, (short)(existingPlayers + 1));
/*      */         
/*      */ 
/* 2178 */         for (int chunk = 0; chunk < chunksPerPlayer; chunk++)
/*      */         {
/* 2180 */           int dx = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
/* 2181 */           int dz = (this.random.nextBoolean() ? 1 : -1) * this.random.nextInt(randRange);
/* 2182 */           long hash = chunkToKey(dx + j, dz + k);
/* 2183 */           if ((!this.chunkTickList.contains(hash)) && (this.chunkProvider.isChunkLoaded(dx + j, dz + k)))
/*      */           {
/* 2185 */             this.chunkTickList.put(hash, (short)-1);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2192 */     this.methodProfiler.b();
/* 2193 */     if (this.L > 0) {
/* 2194 */       this.L -= 1;
/*      */     }
/*      */     
/* 2197 */     this.methodProfiler.a("playerCheckLight");
/* 2198 */     if ((this.spigotConfig.randomLightUpdates) && (!this.players.isEmpty())) {
/* 2199 */       int i = this.random.nextInt(this.players.size());
/* 2200 */       EntityHuman entityhuman = (EntityHuman)this.players.get(i);
/* 2201 */       int j = MathHelper.floor(entityhuman.locX) + this.random.nextInt(11) - 5;
/* 2202 */       int k = MathHelper.floor(entityhuman.locY) + this.random.nextInt(11) - 5;
/* 2203 */       int l = MathHelper.floor(entityhuman.locZ) + this.random.nextInt(11) - 5;
/* 2204 */       x(new BlockPosition(j, k, l));
/*      */     }
/*      */     
/* 2207 */     this.methodProfiler.b();
/*      */   }
/*      */   
/*      */   protected abstract int q();
/*      */   
/*      */   protected void a(int i, int j, Chunk chunk) {
/* 2213 */     this.methodProfiler.c("moodSound");
/* 2214 */     if ((this.L == 0) && (!this.isClientSide)) {
/* 2215 */       this.m = (this.m * 3 + 1013904223);
/* 2216 */       int k = this.m >> 2;
/* 2217 */       int l = k & 0xF;
/* 2218 */       int i1 = k >> 8 & 0xF;
/* 2219 */       int j1 = k >> 16 & 0xFF;
/* 2220 */       BlockPosition blockposition = new BlockPosition(l, j1, i1);
/* 2221 */       Block block = chunk.getType(blockposition);
/*      */       
/* 2223 */       l += i;
/* 2224 */       i1 += j;
/* 2225 */       if ((block.getMaterial() == Material.AIR) && (k(blockposition) <= this.random.nextInt(8)) && (b(EnumSkyBlock.SKY, blockposition) <= 0)) {
/* 2226 */         EntityHuman entityhuman = findNearbyPlayer(l + 0.5D, j1 + 0.5D, i1 + 0.5D, 8.0D);
/*      */         
/* 2228 */         if ((entityhuman != null) && (entityhuman.e(l + 0.5D, j1 + 0.5D, i1 + 0.5D) > 4.0D)) {
/* 2229 */           makeSound(l + 0.5D, j1 + 0.5D, i1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
/* 2230 */           this.L = (this.random.nextInt(12000) + 6000);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2235 */     this.methodProfiler.c("checkLight");
/* 2236 */     chunk.m();
/*      */   }
/*      */   
/*      */   protected void h() {
/* 2240 */     D();
/*      */   }
/*      */   
/*      */   public void a(Block block, BlockPosition blockposition, Random random) {
/* 2244 */     this.e = true;
/* 2245 */     block.b(this, blockposition, getType(blockposition), random);
/* 2246 */     this.e = false;
/*      */   }
/*      */   
/*      */   public boolean v(BlockPosition blockposition) {
/* 2250 */     return e(blockposition, false);
/*      */   }
/*      */   
/*      */   public boolean w(BlockPosition blockposition) {
/* 2254 */     return e(blockposition, true);
/*      */   }
/*      */   
/*      */   public boolean e(BlockPosition blockposition, boolean flag) {
/* 2258 */     BiomeBase biomebase = getBiome(blockposition);
/* 2259 */     float f = biomebase.a(blockposition);
/*      */     
/* 2261 */     if (f > 0.15F) {
/* 2262 */       return false;
/*      */     }
/* 2264 */     if ((blockposition.getY() >= 0) && (blockposition.getY() < 256) && (b(EnumSkyBlock.BLOCK, blockposition) < 10)) {
/* 2265 */       IBlockData iblockdata = getType(blockposition);
/* 2266 */       Block block = iblockdata.getBlock();
/*      */       
/* 2268 */       if (((block == Blocks.WATER) || (block == Blocks.FLOWING_WATER)) && (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0)) {
/* 2269 */         if (!flag) {
/* 2270 */           return true;
/*      */         }
/*      */         
/* 2273 */         boolean flag1 = (F(blockposition.west())) && (F(blockposition.east())) && (F(blockposition.north())) && (F(blockposition.south()));
/*      */         
/* 2275 */         if (!flag1) {
/* 2276 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean F(BlockPosition blockposition)
/*      */   {
/* 2286 */     return getType(blockposition).getBlock().getMaterial() == Material.WATER;
/*      */   }
/*      */   
/*      */   public boolean f(BlockPosition blockposition, boolean flag) {
/* 2290 */     BiomeBase biomebase = getBiome(blockposition);
/* 2291 */     float f = biomebase.a(blockposition);
/*      */     
/* 2293 */     if (f > 0.15F)
/* 2294 */       return false;
/* 2295 */     if (!flag) {
/* 2296 */       return true;
/*      */     }
/* 2298 */     if ((blockposition.getY() >= 0) && (blockposition.getY() < 256) && (b(EnumSkyBlock.BLOCK, blockposition) < 10)) {
/* 2299 */       Block block = getType(blockposition).getBlock();
/*      */       
/* 2301 */       if ((block.getMaterial() == Material.AIR) && (Blocks.SNOW_LAYER.canPlace(this, blockposition))) {
/* 2302 */         return true;
/*      */       }
/*      */     }
/*      */     
/* 2306 */     return false;
/*      */   }
/*      */   
/*      */   public boolean x(BlockPosition blockposition)
/*      */   {
/* 2311 */     boolean flag = false;
/*      */     
/* 2313 */     if (!this.worldProvider.o()) {
/* 2314 */       flag |= c(EnumSkyBlock.SKY, blockposition);
/*      */     }
/*      */     
/* 2317 */     flag |= c(EnumSkyBlock.BLOCK, blockposition);
/* 2318 */     return flag;
/*      */   }
/*      */   
/*      */   private int a(BlockPosition blockposition, EnumSkyBlock enumskyblock) {
/* 2322 */     if ((enumskyblock == EnumSkyBlock.SKY) && (i(blockposition))) {
/* 2323 */       return 15;
/*      */     }
/* 2325 */     Block block = getType(blockposition).getBlock();
/* 2326 */     int i = enumskyblock == EnumSkyBlock.SKY ? 0 : block.r();
/* 2327 */     int j = block.p();
/*      */     
/* 2329 */     if ((j >= 15) && (block.r() > 0)) {
/* 2330 */       j = 1;
/*      */     }
/*      */     
/* 2333 */     if (j < 1) {
/* 2334 */       j = 1;
/*      */     }
/*      */     
/* 2337 */     if (j >= 15)
/* 2338 */       return 0;
/* 2339 */     if (i >= 14) {
/* 2340 */       return i;
/*      */     }
/* 2342 */     EnumDirection[] aenumdirection = EnumDirection.values();
/* 2343 */     int k = aenumdirection.length;
/*      */     
/* 2345 */     for (int l = 0; l < k; l++) {
/* 2346 */       EnumDirection enumdirection = aenumdirection[l];
/* 2347 */       BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 2348 */       int i1 = b(enumskyblock, blockposition1) - j;
/*      */       
/* 2350 */       if (i1 > i) {
/* 2351 */         i = i1;
/*      */       }
/*      */       
/* 2354 */       if (i >= 14) {
/* 2355 */         return i;
/*      */       }
/*      */     }
/*      */     
/* 2359 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean c(EnumSkyBlock enumskyblock, BlockPosition blockposition)
/*      */   {
/* 2366 */     Chunk chunk = getChunkIfLoaded(blockposition.getX() >> 4, blockposition.getZ() >> 4);
/* 2367 */     if ((chunk == null) || (!chunk.areNeighborsLoaded(1)))
/*      */     {
/* 2369 */       return false;
/*      */     }
/* 2371 */     int i = 0;
/* 2372 */     int j = 0;
/*      */     
/* 2374 */     this.methodProfiler.a("getBrightness");
/* 2375 */     int k = b(enumskyblock, blockposition);
/* 2376 */     int l = a(blockposition, enumskyblock);
/* 2377 */     int i1 = blockposition.getX();
/* 2378 */     int j1 = blockposition.getY();
/* 2379 */     int k1 = blockposition.getZ();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2389 */     if (l > k) {
/* 2390 */       this.H[(j++)] = 133152;
/* 2391 */     } else if (l < k) {
/* 2392 */       this.H[(j++)] = (0x20820 | k << 18);
/*      */       
/* 2394 */       while (i < j) {
/* 2395 */         int l1 = this.H[(i++)];
/* 2396 */         int i2 = (l1 & 0x3F) - 32 + i1;
/* 2397 */         int j2 = (l1 >> 6 & 0x3F) - 32 + j1;
/* 2398 */         int k2 = (l1 >> 12 & 0x3F) - 32 + k1;
/* 2399 */         int l3 = l1 >> 18 & 0xF;
/* 2400 */         BlockPosition blockposition1 = new BlockPosition(i2, j2, k2);
/*      */         
/* 2402 */         int l2 = b(enumskyblock, blockposition1);
/* 2403 */         if (l2 == l3) {
/* 2404 */           a(enumskyblock, blockposition1, 0);
/* 2405 */           if (l3 > 0) {
/* 2406 */             int i3 = MathHelper.a(i2 - i1);
/* 2407 */             int j3 = MathHelper.a(j2 - j1);
/* 2408 */             int k3 = MathHelper.a(k2 - k1);
/* 2409 */             if (i3 + j3 + k3 < 17) {
/* 2410 */               BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/* 2411 */               EnumDirection[] aenumdirection = EnumDirection.values();
/* 2412 */               int i4 = aenumdirection.length;
/*      */               
/* 2414 */               for (int j4 = 0; j4 < i4; j4++) {
/* 2415 */                 EnumDirection enumdirection = aenumdirection[j4];
/* 2416 */                 int k4 = i2 + enumdirection.getAdjacentX();
/* 2417 */                 int l4 = j2 + enumdirection.getAdjacentY();
/* 2418 */                 int i5 = k2 + enumdirection.getAdjacentZ();
/*      */                 
/* 2420 */                 blockposition_mutableblockposition.c(k4, l4, i5);
/* 2421 */                 int j5 = Math.max(1, getType(blockposition_mutableblockposition).getBlock().p());
/*      */                 
/* 2423 */                 l2 = b(enumskyblock, blockposition_mutableblockposition);
/* 2424 */                 if ((l2 == l3 - j5) && (j < this.H.length)) {
/* 2425 */                   this.H[(j++)] = (k4 - i1 + 32 | l4 - j1 + 32 << 6 | i5 - k1 + 32 << 12 | l3 - j5 << 18);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2433 */       i = 0;
/*      */     }
/*      */     
/* 2436 */     this.methodProfiler.b();
/* 2437 */     this.methodProfiler.a("checkedPosition < toCheckCount");
/*      */     
/* 2439 */     while (i < j) {
/* 2440 */       int l1 = this.H[(i++)];
/* 2441 */       int i2 = (l1 & 0x3F) - 32 + i1;
/* 2442 */       int j2 = (l1 >> 6 & 0x3F) - 32 + j1;
/* 2443 */       int k2 = (l1 >> 12 & 0x3F) - 32 + k1;
/* 2444 */       BlockPosition blockposition2 = new BlockPosition(i2, j2, k2);
/* 2445 */       int k5 = b(enumskyblock, blockposition2);
/*      */       
/* 2447 */       int l2 = a(blockposition2, enumskyblock);
/* 2448 */       if (l2 != k5) {
/* 2449 */         a(enumskyblock, blockposition2, l2);
/* 2450 */         if (l2 > k5) {
/* 2451 */           int i3 = Math.abs(i2 - i1);
/* 2452 */           int j3 = Math.abs(j2 - j1);
/* 2453 */           int k3 = Math.abs(k2 - k1);
/* 2454 */           boolean flag = j < this.H.length - 6;
/*      */           
/* 2456 */           if ((i3 + j3 + k3 < 17) && (flag)) {
/* 2457 */             if (b(enumskyblock, blockposition2.west()) < l2) {
/* 2458 */               this.H[(j++)] = (i2 - 1 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - k1 + 32 << 12));
/*      */             }
/*      */             
/* 2461 */             if (b(enumskyblock, blockposition2.east()) < l2) {
/* 2462 */               this.H[(j++)] = (i2 + 1 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - k1 + 32 << 12));
/*      */             }
/*      */             
/* 2465 */             if (b(enumskyblock, blockposition2.down()) < l2) {
/* 2466 */               this.H[(j++)] = (i2 - i1 + 32 + (j2 - 1 - j1 + 32 << 6) + (k2 - k1 + 32 << 12));
/*      */             }
/*      */             
/* 2469 */             if (b(enumskyblock, blockposition2.up()) < l2) {
/* 2470 */               this.H[(j++)] = (i2 - i1 + 32 + (j2 + 1 - j1 + 32 << 6) + (k2 - k1 + 32 << 12));
/*      */             }
/*      */             
/* 2473 */             if (b(enumskyblock, blockposition2.north()) < l2) {
/* 2474 */               this.H[(j++)] = (i2 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 - 1 - k1 + 32 << 12));
/*      */             }
/*      */             
/* 2477 */             if (b(enumskyblock, blockposition2.south()) < l2) {
/* 2478 */               this.H[(j++)] = (i2 - i1 + 32 + (j2 - j1 + 32 << 6) + (k2 + 1 - k1 + 32 << 12));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2485 */     this.methodProfiler.b();
/* 2486 */     return true;
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag)
/*      */   {
/* 2491 */     return false;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> a(Chunk chunk, boolean flag) {
/* 2495 */     return null;
/*      */   }
/*      */   
/*      */   public List<NextTickListEntry> a(StructureBoundingBox structureboundingbox, boolean flag) {
/* 2499 */     return null;
/*      */   }
/*      */   
/*      */   public List<Entity> getEntities(Entity entity, AxisAlignedBB axisalignedbb) {
/* 2503 */     return a(entity, axisalignedbb, IEntitySelector.d);
/*      */   }
/*      */   
/*      */   public List<Entity> a(Entity entity, AxisAlignedBB axisalignedbb, Predicate<? super Entity> predicate) {
/* 2507 */     ArrayList arraylist = Lists.newArrayList();
/* 2508 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2509 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2510 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2511 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/*      */     
/* 2513 */     for (int i1 = i; i1 <= j; i1++) {
/* 2514 */       for (int j1 = k; j1 <= l; j1++) {
/* 2515 */         if (isChunkLoaded(i1, j1, true)) {
/* 2516 */           getChunkAt(i1, j1).a(entity, axisalignedbb, arraylist, predicate);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2521 */     return arraylist;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> a(Class<? extends T> oclass, Predicate<? super T> predicate) {
/* 2525 */     ArrayList arraylist = Lists.newArrayList();
/* 2526 */     Iterator iterator = this.entityList.iterator();
/*      */     
/* 2528 */     while (iterator.hasNext()) {
/* 2529 */       Entity entity = (Entity)iterator.next();
/*      */       
/* 2531 */       if ((oclass.isAssignableFrom(entity.getClass())) && (predicate.apply(entity))) {
/* 2532 */         arraylist.add(entity);
/*      */       }
/*      */     }
/*      */     
/* 2536 */     return arraylist;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> b(Class<? extends T> oclass, Predicate<? super T> predicate) {
/* 2540 */     ArrayList arraylist = Lists.newArrayList();
/* 2541 */     Iterator iterator = this.players.iterator();
/*      */     
/* 2543 */     while (iterator.hasNext()) {
/* 2544 */       Entity entity = (Entity)iterator.next();
/*      */       
/* 2546 */       if ((oclass.isAssignableFrom(entity.getClass())) && (predicate.apply(entity))) {
/* 2547 */         arraylist.add(entity);
/*      */       }
/*      */     }
/*      */     
/* 2551 */     return arraylist;
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb) {
/* 2555 */     return a(oclass, axisalignedbb, IEntitySelector.d);
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<T> a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, Predicate<? super T> predicate) {
/* 2559 */     int i = MathHelper.floor((axisalignedbb.a - 2.0D) / 16.0D);
/* 2560 */     int j = MathHelper.floor((axisalignedbb.d + 2.0D) / 16.0D);
/* 2561 */     int k = MathHelper.floor((axisalignedbb.c - 2.0D) / 16.0D);
/* 2562 */     int l = MathHelper.floor((axisalignedbb.f + 2.0D) / 16.0D);
/* 2563 */     ArrayList arraylist = Lists.newArrayList();
/*      */     
/* 2565 */     for (int i1 = i; i1 <= j; i1++) {
/* 2566 */       for (int j1 = k; j1 <= l; j1++) {
/* 2567 */         if (isChunkLoaded(i1, j1, true)) {
/* 2568 */           getChunkAt(i1, j1).a(oclass, axisalignedbb, arraylist, predicate);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2573 */     return arraylist;
/*      */   }
/*      */   
/*      */   public <T extends Entity> T a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, T t0) {
/* 2577 */     List list = a(oclass, axisalignedbb);
/* 2578 */     Entity entity = null;
/* 2579 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 2581 */     for (int i = 0; i < list.size(); i++) {
/* 2582 */       Entity entity1 = (Entity)list.get(i);
/*      */       
/* 2584 */       if ((entity1 != t0) && (IEntitySelector.d.apply(entity1))) {
/* 2585 */         double d1 = t0.h(entity1);
/*      */         
/* 2587 */         if (d1 <= d0) {
/* 2588 */           entity = entity1;
/* 2589 */           d0 = d1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2594 */     return entity;
/*      */   }
/*      */   
/*      */   public Entity a(int i) {
/* 2598 */     return (Entity)this.entitiesById.get(i);
/*      */   }
/*      */   
/*      */   public void b(BlockPosition blockposition, TileEntity tileentity) {
/* 2602 */     if (isLoaded(blockposition)) {
/* 2603 */       getChunkAtWorldCoords(blockposition).e();
/*      */     }
/*      */   }
/*      */   
/*      */   public int a(Class<?> oclass)
/*      */   {
/* 2609 */     int i = 0;
/* 2610 */     Iterator iterator = this.entityList.iterator();
/*      */     
/* 2612 */     while (iterator.hasNext()) {
/* 2613 */       Entity entity = (Entity)iterator.next();
/*      */       
/* 2615 */       if ((entity instanceof EntityInsentient)) {
/* 2616 */         EntityInsentient entityinsentient = (EntityInsentient)entity;
/* 2617 */         if ((entityinsentient.isTypeNotPersistent()) && (entityinsentient.isPersistent())) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 2622 */       else if (oclass.isAssignableFrom(entity.getClass()))
/*      */       {
/*      */ 
/* 2625 */         i++;
/*      */       }
/*      */     }
/*      */     
/* 2629 */     return i;
/*      */   }
/*      */   
/*      */   public void b(Collection<Entity> collection) {
/* 2633 */     AsyncCatcher.catchOp("entity world add");
/*      */     
/*      */ 
/* 2636 */     Iterator iterator = collection.iterator();
/*      */     
/* 2638 */     while (iterator.hasNext()) {
/* 2639 */       Entity entity = (Entity)iterator.next();
/*      */       
/* 2641 */       if (entity != null)
/*      */       {
/*      */ 
/* 2644 */         this.entityList.add(entity);
/*      */         
/* 2646 */         a(entity);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void c(Collection<Entity> collection) {
/* 2652 */     this.g.addAll(collection);
/*      */   }
/*      */   
/*      */   public boolean a(Block block, BlockPosition blockposition, boolean flag, EnumDirection enumdirection, Entity entity, ItemStack itemstack) {
/* 2656 */     Block block1 = getType(blockposition).getBlock();
/* 2657 */     AxisAlignedBB axisalignedbb = flag ? null : block.a(this, blockposition, block.getBlockData());
/*      */     
/*      */ 
/* 2660 */     boolean defaultReturn = (axisalignedbb == null) || (a(axisalignedbb, entity));
/* 2661 */     BlockCanBuildEvent event = new BlockCanBuildEvent(getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), CraftMagicNumbers.getId(block), defaultReturn);
/* 2662 */     getServer().getPluginManager().callEvent(event);
/*      */     
/* 2664 */     return event.isBuildable();
/*      */   }
/*      */   
/*      */   public int F()
/*      */   {
/* 2669 */     return this.a;
/*      */   }
/*      */   
/*      */   public void b(int i) {
/* 2673 */     this.a = i;
/*      */   }
/*      */   
/*      */   public int getBlockPower(BlockPosition blockposition, EnumDirection enumdirection) {
/* 2677 */     IBlockData iblockdata = getType(blockposition);
/*      */     
/* 2679 */     return iblockdata.getBlock().b(this, blockposition, iblockdata, enumdirection);
/*      */   }
/*      */   
/*      */   public WorldType G() {
/* 2683 */     return this.worldData.getType();
/*      */   }
/*      */   
/*      */   public int getBlockPower(BlockPosition blockposition) {
/* 2687 */     byte b0 = 0;
/* 2688 */     int i = Math.max(b0, getBlockPower(blockposition.down(), EnumDirection.DOWN));
/*      */     
/* 2690 */     if (i >= 15) {
/* 2691 */       return i;
/*      */     }
/* 2693 */     i = Math.max(i, getBlockPower(blockposition.up(), EnumDirection.UP));
/* 2694 */     if (i >= 15) {
/* 2695 */       return i;
/*      */     }
/* 2697 */     i = Math.max(i, getBlockPower(blockposition.north(), EnumDirection.NORTH));
/* 2698 */     if (i >= 15) {
/* 2699 */       return i;
/*      */     }
/* 2701 */     i = Math.max(i, getBlockPower(blockposition.south(), EnumDirection.SOUTH));
/* 2702 */     if (i >= 15) {
/* 2703 */       return i;
/*      */     }
/* 2705 */     i = Math.max(i, getBlockPower(blockposition.west(), EnumDirection.WEST));
/* 2706 */     if (i >= 15) {
/* 2707 */       return i;
/*      */     }
/* 2709 */     i = Math.max(i, getBlockPower(blockposition.east(), EnumDirection.EAST));
/* 2710 */     return i >= 15 ? i : i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isBlockFacePowered(BlockPosition blockposition, EnumDirection enumdirection)
/*      */   {
/* 2719 */     return getBlockFacePower(blockposition, enumdirection) > 0;
/*      */   }
/*      */   
/*      */   public int getBlockFacePower(BlockPosition blockposition, EnumDirection enumdirection) {
/* 2723 */     IBlockData iblockdata = getType(blockposition);
/* 2724 */     Block block = iblockdata.getBlock();
/*      */     
/* 2726 */     return block.isOccluding() ? getBlockPower(blockposition) : block.a(this, blockposition, iblockdata, enumdirection);
/*      */   }
/*      */   
/*      */   public boolean isBlockIndirectlyPowered(BlockPosition blockposition) {
/* 2730 */     return getBlockFacePower(blockposition.down(), EnumDirection.DOWN) > 0;
/*      */   }
/*      */   
/*      */   public int A(BlockPosition blockposition) {
/* 2734 */     int i = 0;
/* 2735 */     EnumDirection[] aenumdirection = EnumDirection.values();
/* 2736 */     int j = aenumdirection.length;
/*      */     
/* 2738 */     for (int k = 0; k < j; k++) {
/* 2739 */       EnumDirection enumdirection = aenumdirection[k];
/* 2740 */       int l = getBlockFacePower(blockposition.shift(enumdirection), enumdirection);
/*      */       
/* 2742 */       if (l >= 15) {
/* 2743 */         return 15;
/*      */       }
/*      */       
/* 2746 */       if (l > i) {
/* 2747 */         i = l;
/*      */       }
/*      */     }
/*      */     
/* 2751 */     return i;
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyPlayer(Entity entity, double d0) {
/* 2755 */     return findNearbyPlayer(entity.locX, entity.locY, entity.locZ, d0);
/*      */   }
/*      */   
/*      */   public EntityHuman findNearbyPlayer(double d0, double d1, double d2, double d3) {
/* 2759 */     double d4 = -1.0D;
/* 2760 */     EntityHuman entityhuman = null;
/*      */     
/* 2762 */     for (int i = 0; i < this.players.size(); i++) {
/* 2763 */       EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
/*      */       
/* 2765 */       if ((entityhuman1 != null) && (!entityhuman1.dead))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2770 */         if (IEntitySelector.d.apply(entityhuman1)) {
/* 2771 */           double d5 = entityhuman1.e(d0, d1, d2);
/*      */           
/* 2773 */           if (((d3 < 0.0D) || (d5 < d3 * d3)) && ((d4 == -1.0D) || (d5 < d4))) {
/* 2774 */             d4 = d5;
/* 2775 */             entityhuman = entityhuman1;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2780 */     return entityhuman;
/*      */   }
/*      */   
/*      */   public boolean isPlayerNearby(double d0, double d1, double d2, double d3) {
/* 2784 */     for (int i = 0; i < this.players.size(); i++) {
/* 2785 */       EntityHuman entityhuman = (EntityHuman)this.players.get(i);
/*      */       
/* 2787 */       if (IEntitySelector.d.apply(entityhuman)) {
/* 2788 */         double d4 = entityhuman.e(d0, d1, d2);
/*      */         
/* 2790 */         if ((d3 < 0.0D) || (d4 < d3 * d3)) {
/* 2791 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2796 */     return false;
/*      */   }
/*      */   
/*      */   public EntityHuman a(String s) {
/* 2800 */     for (int i = 0; i < this.players.size(); i++) {
/* 2801 */       EntityHuman entityhuman = (EntityHuman)this.players.get(i);
/*      */       
/* 2803 */       if (s.equals(entityhuman.getName())) {
/* 2804 */         return entityhuman;
/*      */       }
/*      */     }
/*      */     
/* 2808 */     return null;
/*      */   }
/*      */   
/*      */   public EntityHuman b(UUID uuid) {
/* 2812 */     for (int i = 0; i < this.players.size(); i++) {
/* 2813 */       EntityHuman entityhuman = (EntityHuman)this.players.get(i);
/*      */       
/* 2815 */       if (uuid.equals(entityhuman.getUniqueID())) {
/* 2816 */         return entityhuman;
/*      */       }
/*      */     }
/*      */     
/* 2820 */     return null;
/*      */   }
/*      */   
/*      */   public void checkSession() throws ExceptionWorldConflict {
/* 2824 */     this.dataManager.checkSession();
/*      */   }
/*      */   
/*      */   public long getSeed() {
/* 2828 */     return this.worldData.getSeed();
/*      */   }
/*      */   
/*      */   public long getTime() {
/* 2832 */     return this.worldData.getTime();
/*      */   }
/*      */   
/*      */   public long getDayTime() {
/* 2836 */     return this.worldData.getDayTime();
/*      */   }
/*      */   
/*      */   public void setDayTime(long i) {
/* 2840 */     this.worldData.setDayTime(i);
/*      */   }
/*      */   
/*      */   public BlockPosition getSpawn() {
/* 2844 */     BlockPosition blockposition = new BlockPosition(this.worldData.c(), this.worldData.d(), this.worldData.e());
/*      */     
/* 2846 */     if (!getWorldBorder().a(blockposition)) {
/* 2847 */       blockposition = getHighestBlockYAt(new BlockPosition(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*      */     }
/*      */     
/* 2850 */     return blockposition;
/*      */   }
/*      */   
/*      */   public void B(BlockPosition blockposition) {
/* 2854 */     this.worldData.setSpawn(blockposition);
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman, BlockPosition blockposition) {
/* 2858 */     return true;
/*      */   }
/*      */   
/*      */   public void broadcastEntityEffect(Entity entity, byte b0) {}
/*      */   
/*      */   public IChunkProvider N() {
/* 2864 */     return this.chunkProvider;
/*      */   }
/*      */   
/*      */   public void playBlockAction(BlockPosition blockposition, Block block, int i, int j) {
/* 2868 */     block.a(this, blockposition, getType(blockposition), i, j);
/*      */   }
/*      */   
/*      */   public IDataManager getDataManager() {
/* 2872 */     return this.dataManager;
/*      */   }
/*      */   
/*      */   public WorldData getWorldData() {
/* 2876 */     return this.worldData;
/*      */   }
/*      */   
/*      */   public GameRules getGameRules() {
/* 2880 */     return this.worldData.x();
/*      */   }
/*      */   
/*      */ 
/*      */   public void everyoneSleeping() {}
/*      */   
/*      */ 
/*      */   public void checkSleepStatus()
/*      */   {
/* 2889 */     if (!this.isClientSide) {
/* 2890 */       everyoneSleeping();
/*      */     }
/*      */   }
/*      */   
/*      */   public float h(float f)
/*      */   {
/* 2896 */     return (this.q + (this.r - this.q) * f) * j(f);
/*      */   }
/*      */   
/*      */   public float j(float f) {
/* 2900 */     return this.o + (this.p - this.o) * f;
/*      */   }
/*      */   
/*      */   public boolean R() {
/* 2904 */     return h(1.0F) > 0.9D;
/*      */   }
/*      */   
/*      */   public boolean S() {
/* 2908 */     return j(1.0F) > 0.2D;
/*      */   }
/*      */   
/*      */   public boolean isRainingAt(BlockPosition blockposition) {
/* 2912 */     if (!S())
/* 2913 */       return false;
/* 2914 */     if (!i(blockposition))
/* 2915 */       return false;
/* 2916 */     if (q(blockposition).getY() > blockposition.getY()) {
/* 2917 */       return false;
/*      */     }
/* 2919 */     BiomeBase biomebase = getBiome(blockposition);
/*      */     
/* 2921 */     return f(blockposition, false) ? false : biomebase.d() ? false : biomebase.e();
/*      */   }
/*      */   
/*      */   public boolean D(BlockPosition blockposition)
/*      */   {
/* 2926 */     BiomeBase biomebase = getBiome(blockposition);
/*      */     
/* 2928 */     return biomebase.f();
/*      */   }
/*      */   
/*      */   public PersistentCollection T() {
/* 2932 */     return this.worldMaps;
/*      */   }
/*      */   
/*      */   public void a(String s, PersistentBase persistentbase) {
/* 2936 */     this.worldMaps.a(s, persistentbase);
/*      */   }
/*      */   
/*      */   public PersistentBase a(Class<? extends PersistentBase> oclass, String s) {
/* 2940 */     return this.worldMaps.get(oclass, s);
/*      */   }
/*      */   
/*      */   public int b(String s) {
/* 2944 */     return this.worldMaps.a(s);
/*      */   }
/*      */   
/*      */   public void a(int i, BlockPosition blockposition, int j) {
/* 2948 */     for (int k = 0; k < this.u.size(); k++) {
/* 2949 */       ((IWorldAccess)this.u.get(k)).a(i, blockposition, j);
/*      */     }
/*      */   }
/*      */   
/*      */   public void triggerEffect(int i, BlockPosition blockposition, int j)
/*      */   {
/* 2955 */     a(null, i, blockposition, j);
/*      */   }
/*      */   
/*      */   public void a(EntityHuman entityhuman, int i, BlockPosition blockposition, int j) {
/*      */     try {
/* 2960 */       for (int k = 0; k < this.u.size(); k++) {
/* 2961 */         ((IWorldAccess)this.u.get(k)).a(entityhuman, i, blockposition, j);
/*      */       }
/*      */     }
/*      */     catch (Throwable throwable) {
/* 2965 */       CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
/* 2966 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
/*      */       
/* 2968 */       crashreportsystemdetails.a("Block coordinates", CrashReportSystemDetails.a(blockposition));
/* 2969 */       crashreportsystemdetails.a("Event source", entityhuman);
/* 2970 */       crashreportsystemdetails.a("Event type", Integer.valueOf(i));
/* 2971 */       crashreportsystemdetails.a("Event data", Integer.valueOf(j));
/* 2972 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getHeight() {
/* 2977 */     return 256;
/*      */   }
/*      */   
/*      */   public int V() {
/* 2981 */     return this.worldProvider.o() ? 128 : 256;
/*      */   }
/*      */   
/*      */   public Random a(int i, int j, int k) {
/* 2985 */     long l = i * 341873128712L + j * 132897987541L + getWorldData().getSeed() + k;
/*      */     
/* 2987 */     this.random.setSeed(l);
/* 2988 */     return this.random;
/*      */   }
/*      */   
/*      */   public BlockPosition a(String s, BlockPosition blockposition) {
/* 2992 */     return N().findNearestMapFeature(this, s, blockposition);
/*      */   }
/*      */   
/*      */   public CrashReportSystemDetails a(CrashReport crashreport) {
/* 2996 */     CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
/*      */     
/* 2998 */     crashreportsystemdetails.a("Level name", this.worldData == null ? "????" : this.worldData.getName());
/* 2999 */     crashreportsystemdetails.a("All players", new Callable() {
/*      */       public String a() {
/* 3001 */         return World.this.players.size() + " total; " + World.this.players.toString();
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 3005 */         return a();
/*      */       }
/* 3007 */     });
/* 3008 */     crashreportsystemdetails.a("Chunk stats", new Callable() {
/*      */       public String a() {
/* 3010 */         return World.this.chunkProvider.getName();
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 3014 */         return a();
/*      */       }
/*      */     });
/*      */     try
/*      */     {
/* 3019 */       this.worldData.a(crashreportsystemdetails);
/*      */     } catch (Throwable throwable) {
/* 3021 */       crashreportsystemdetails.a("Level Data Unobtainable", throwable);
/*      */     }
/*      */     
/* 3024 */     return crashreportsystemdetails;
/*      */   }
/*      */   
/*      */   public void c(int i, BlockPosition blockposition, int j) {
/* 3028 */     for (int k = 0; k < this.u.size(); k++) {
/* 3029 */       IWorldAccess iworldaccess = (IWorldAccess)this.u.get(k);
/*      */       
/* 3031 */       iworldaccess.b(i, blockposition, j);
/*      */     }
/*      */   }
/*      */   
/*      */   public Calendar Y()
/*      */   {
/* 3037 */     if (getTime() % 600L == 0L) {
/* 3038 */       this.K.setTimeInMillis(MinecraftServer.az());
/*      */     }
/*      */     
/* 3041 */     return this.K;
/*      */   }
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3045 */     return this.scoreboard;
/*      */   }
/*      */   
/*      */   public void updateAdjacentComparators(BlockPosition blockposition, Block block) {
/* 3049 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*      */     
/* 3051 */     while (iterator.hasNext()) {
/* 3052 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 3053 */       BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*      */       
/* 3055 */       if (isLoaded(blockposition1)) {
/* 3056 */         IBlockData iblockdata = getType(blockposition1);
/*      */         
/* 3058 */         if (Blocks.UNPOWERED_COMPARATOR.e(iblockdata.getBlock())) {
/* 3059 */           iblockdata.getBlock().doPhysics(this, blockposition1, iblockdata, block);
/* 3060 */         } else if (iblockdata.getBlock().isOccluding()) {
/* 3061 */           blockposition1 = blockposition1.shift(enumdirection);
/* 3062 */           iblockdata = getType(blockposition1);
/* 3063 */           if (Blocks.UNPOWERED_COMPARATOR.e(iblockdata.getBlock())) {
/* 3064 */             iblockdata.getBlock().doPhysics(this, blockposition1, iblockdata, block);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public DifficultyDamageScaler E(BlockPosition blockposition)
/*      */   {
/* 3073 */     long i = 0L;
/* 3074 */     float f = 0.0F;
/*      */     
/* 3076 */     if (isLoaded(blockposition)) {
/* 3077 */       f = y();
/* 3078 */       i = getChunkAtWorldCoords(blockposition).w();
/*      */     }
/*      */     
/* 3081 */     return new DifficultyDamageScaler(getDifficulty(), getDayTime(), i, f);
/*      */   }
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/* 3085 */     return getWorldData().getDifficulty();
/*      */   }
/*      */   
/*      */   public int ab() {
/* 3089 */     return this.I;
/*      */   }
/*      */   
/*      */   public void c(int i) {
/* 3093 */     this.I = i;
/*      */   }
/*      */   
/*      */   public void d(int i) {
/* 3097 */     this.J = i;
/*      */   }
/*      */   
/*      */   public boolean ad() {
/* 3101 */     return this.isLoading;
/*      */   }
/*      */   
/*      */   public PersistentVillage ae() {
/* 3105 */     return this.villages;
/*      */   }
/*      */   
/*      */   public WorldBorder getWorldBorder() {
/* 3109 */     return this.N;
/*      */   }
/*      */   
/*      */   public boolean c(int i, int j) {
/* 3113 */     BlockPosition blockposition = getSpawn();
/* 3114 */     int k = i * 16 + 8 - blockposition.getX();
/* 3115 */     int l = j * 16 + 8 - blockposition.getZ();
/* 3116 */     short short0 = 128;
/*      */     
/* 3118 */     return (k >= -short0) && (k <= short0) && (l >= -short0) && (l <= short0) && (this.keepSpawnInMemory);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */