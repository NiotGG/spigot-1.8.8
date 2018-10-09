/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import gnu.trove.map.hash.TObjectIntHashMap;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*      */ import org.bukkit.entity.HumanEntity;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ 
/*      */ public class Chunk
/*      */ {
/*   22 */   private static final org.apache.logging.log4j.Logger c = ;
/*      */   private final ChunkSection[] sections;
/*      */   private final byte[] e;
/*      */   private final int[] f;
/*      */   private final boolean[] g;
/*      */   private boolean h;
/*      */   public final World world;
/*      */   public final int[] heightMap;
/*      */   public final int locX;
/*      */   public final int locZ;
/*      */   private boolean k;
/*      */   public final Map<BlockPosition, TileEntity> tileEntities;
/*      */   public final List<Entity>[] entitySlices;
/*      */   private boolean done;
/*      */   private boolean lit;
/*      */   private boolean p;
/*      */   private boolean q;
/*      */   private boolean r;
/*      */   private long lastSaved;
/*      */   private int t;
/*      */   private long u;
/*      */   private int v;
/*      */   private ConcurrentLinkedQueue<BlockPosition> w;
/*   45 */   protected TObjectIntHashMap<Class> entityCount = new TObjectIntHashMap();
/*      */   
/*      */ 
/*   48 */   private int neighbors = 4096;
/*      */   public org.bukkit.Chunk bukkitChunk;
/*      */   
/*   51 */   public boolean areNeighborsLoaded(int radius) { switch (radius) {
/*      */     case 2: 
/*   53 */       return this.neighbors == 33554431;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     case 1: 
/*   60 */       return (this.neighbors & 0x739C0) == 473536;
/*      */     }
/*   62 */     throw new UnsupportedOperationException(String.valueOf(radius));
/*      */   }
/*      */   
/*      */   public void setNeighborLoaded(int x, int z)
/*      */   {
/*   67 */     this.neighbors |= 1 << x * 5 + 12 + z;
/*      */   }
/*      */   
/*      */   public void setNeighborUnloaded(int x, int z) {
/*   71 */     this.neighbors &= (1 << x * 5 + 12 + z ^ 0xFFFFFFFF);
/*      */   }
/*      */   
/*      */   public Chunk(World world, int i, int j)
/*      */   {
/*   76 */     this.sections = new ChunkSection[16];
/*   77 */     this.e = new byte['Ā'];
/*   78 */     this.f = new int['Ā'];
/*   79 */     this.g = new boolean['Ā'];
/*   80 */     this.tileEntities = com.google.common.collect.Maps.newHashMap();
/*   81 */     this.v = 4096;
/*   82 */     this.w = com.google.common.collect.Queues.newConcurrentLinkedQueue();
/*   83 */     this.entitySlices = new List[16];
/*   84 */     this.world = world;
/*   85 */     this.locX = i;
/*   86 */     this.locZ = j;
/*   87 */     this.heightMap = new int['Ā'];
/*      */     
/*   89 */     for (int k = 0; k < this.entitySlices.length; k++) {
/*   90 */       this.entitySlices[k] = new org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList();
/*      */     }
/*      */     
/*   93 */     Arrays.fill(this.f, 64537);
/*   94 */     Arrays.fill(this.e, (byte)-1);
/*      */     
/*      */ 
/*   97 */     if (!(this instanceof EmptyChunk)) {
/*   98 */       this.bukkitChunk = new org.bukkit.craftbukkit.v1_8_R3.CraftChunk(this);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Chunk(World world, ChunkSnapshot chunksnapshot, int i, int j)
/*      */   {
/*  107 */     this(world, i, j);
/*  108 */     short short0 = 256;
/*  109 */     boolean flag = !world.worldProvider.o();
/*      */     
/*  111 */     for (int k = 0; k < 16; k++) {
/*  112 */       for (int l = 0; l < 16; l++) {
/*  113 */         for (int i1 = 0; i1 < short0; i1++) {
/*  114 */           int j1 = k * short0 * 16 | l * short0 | i1;
/*  115 */           IBlockData iblockdata = chunksnapshot.a(j1);
/*      */           
/*  117 */           if (iblockdata.getBlock().getMaterial() != Material.AIR) {
/*  118 */             int k1 = i1 >> 4;
/*      */             
/*  120 */             if (this.sections[k1] == null) {
/*  121 */               this.sections[k1] = new ChunkSection(k1 << 4, flag);
/*      */             }
/*      */             
/*  124 */             this.sections[k1].setType(k, i1 & 0xF, l, iblockdata);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean a(int i, int j)
/*      */   {
/*  133 */     return (i == this.locX) && (j == this.locZ);
/*      */   }
/*      */   
/*      */   public int f(BlockPosition blockposition) {
/*  137 */     return b(blockposition.getX() & 0xF, blockposition.getZ() & 0xF);
/*      */   }
/*      */   
/*      */   public int b(int i, int j) {
/*  141 */     return this.heightMap[(j << 4 | i)];
/*      */   }
/*      */   
/*      */   public int g() {
/*  145 */     for (int i = this.sections.length - 1; i >= 0; i--) {
/*  146 */       if (this.sections[i] != null) {
/*  147 */         return this.sections[i].getYPosition();
/*      */       }
/*      */     }
/*      */     
/*  151 */     return 0;
/*      */   }
/*      */   
/*      */   public ChunkSection[] getSections() {
/*  155 */     return this.sections;
/*      */   }
/*      */   
/*      */   public void initLighting() {
/*  159 */     int i = g();
/*      */     
/*  161 */     this.t = Integer.MAX_VALUE;
/*      */     
/*  163 */     for (int j = 0; j < 16; j++) {
/*  164 */       int k = 0;
/*      */       
/*  166 */       while (k < 16) {
/*  167 */         this.f[(j + (k << 4))] = 64537;
/*  168 */         int l = i + 16;
/*      */         
/*      */ 
/*  171 */         while (l > 0) {
/*  172 */           if (e(j, l - 1, k) == 0) {
/*  173 */             l--;
/*      */           }
/*      */           else
/*      */           {
/*  177 */             this.heightMap[(k << 4 | j)] = l;
/*  178 */             if (l < this.t) {
/*  179 */               this.t = l;
/*      */             }
/*      */           }
/*      */         }
/*  183 */         if (!this.world.worldProvider.o()) {
/*  184 */           l = 15;
/*  185 */           int i1 = i + 16 - 1;
/*      */           do
/*      */           {
/*  188 */             int j1 = e(j, i1, k);
/*      */             
/*  190 */             if ((j1 == 0) && (l != 15)) {
/*  191 */               j1 = 1;
/*      */             }
/*      */             
/*  194 */             l -= j1;
/*  195 */             if (l > 0) {
/*  196 */               ChunkSection chunksection = this.sections[(i1 >> 4)];
/*      */               
/*  198 */               if (chunksection != null) {
/*  199 */                 chunksection.a(j, i1 & 0xF, k, l);
/*  200 */                 this.world.n(new BlockPosition((this.locX << 4) + j, i1, (this.locZ << 4) + k));
/*      */               }
/*      */             }
/*      */             
/*  204 */             i1--;
/*  205 */           } while ((i1 > 0) && (l > 0));
/*      */         }
/*      */         
/*  208 */         k++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  214 */     this.q = true;
/*      */   }
/*      */   
/*      */   private void d(int i, int j) {
/*  218 */     this.g[(i + j * 16)] = true;
/*  219 */     this.k = true;
/*      */   }
/*      */   
/*      */   private void h(boolean flag) {
/*  223 */     this.world.methodProfiler.a("recheckGaps");
/*  224 */     if (this.world.areChunksLoaded(new BlockPosition(this.locX * 16 + 8, 0, this.locZ * 16 + 8), 16)) {
/*  225 */       for (int i = 0; i < 16; i++) {
/*  226 */         for (int j = 0; j < 16; j++) {
/*  227 */           if (this.g[(i + j * 16)] != 0) {
/*  228 */             this.g[(i + j * 16)] = false;
/*  229 */             int k = b(i, j);
/*  230 */             int l = this.locX * 16 + i;
/*  231 */             int i1 = this.locZ * 16 + j;
/*  232 */             int j1 = Integer.MAX_VALUE;
/*      */             
/*      */ 
/*      */             EnumDirection enumdirection;
/*      */             
/*  237 */             for (Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); iterator.hasNext(); j1 = Math.min(j1, this.world.b(l + enumdirection.getAdjacentX(), i1 + enumdirection.getAdjacentZ()))) {
/*  238 */               enumdirection = (EnumDirection)iterator.next();
/*      */             }
/*      */             
/*  241 */             c(l, i1, j1);
/*  242 */             iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*      */             
/*  244 */             while (iterator.hasNext()) {
/*  245 */               EnumDirection enumdirection = (EnumDirection)iterator.next();
/*  246 */               c(l + enumdirection.getAdjacentX(), i1 + enumdirection.getAdjacentZ(), k);
/*      */             }
/*      */             
/*  249 */             if (flag) {
/*  250 */               this.world.methodProfiler.b();
/*  251 */               return;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  257 */       this.k = false;
/*      */     }
/*      */     
/*  260 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   private void c(int i, int j, int k) {
/*  264 */     int l = this.world.getHighestBlockYAt(new BlockPosition(i, 0, j)).getY();
/*      */     
/*  266 */     if (l > k) {
/*  267 */       a(i, j, k, l + 1);
/*  268 */     } else if (l < k) {
/*  269 */       a(i, j, l, k + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void a(int i, int j, int k, int l)
/*      */   {
/*  275 */     if ((l > k) && (this.world.areChunksLoaded(new BlockPosition(i, 0, j), 16))) {
/*  276 */       for (int i1 = k; i1 < l; i1++) {
/*  277 */         this.world.c(EnumSkyBlock.SKY, new BlockPosition(i, i1, j));
/*      */       }
/*      */       
/*  280 */       this.q = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void d(int i, int j, int k)
/*      */   {
/*  286 */     int l = this.heightMap[(k << 4 | i)] & 0xFF;
/*  287 */     int i1 = l;
/*      */     
/*  289 */     if (j > l) {
/*  290 */       i1 = j;
/*      */     }
/*      */     
/*  293 */     while ((i1 > 0) && (e(i, i1 - 1, k) == 0)) {
/*  294 */       i1--;
/*      */     }
/*      */     
/*  297 */     if (i1 != l) {
/*  298 */       this.world.a(i + this.locX * 16, k + this.locZ * 16, i1, l);
/*  299 */       this.heightMap[(k << 4 | i)] = i1;
/*  300 */       int j1 = this.locX * 16 + i;
/*  301 */       int k1 = this.locZ * 16 + k;
/*      */       
/*      */ 
/*      */ 
/*  305 */       if (!this.world.worldProvider.o())
/*      */       {
/*      */ 
/*  308 */         if (i1 < l) {
/*  309 */           for (int l1 = i1; l1 < l; l1++) {
/*  310 */             ChunkSection chunksection = this.sections[(l1 >> 4)];
/*  311 */             if (chunksection != null) {
/*  312 */               chunksection.a(i, l1 & 0xF, k, 15);
/*  313 */               this.world.n(new BlockPosition((this.locX << 4) + i, l1, (this.locZ << 4) + k));
/*      */             }
/*      */           }
/*      */         } else {
/*  317 */           for (l1 = l; l1 < i1; l1++) {
/*  318 */             ChunkSection chunksection = this.sections[(l1 >> 4)];
/*  319 */             if (chunksection != null) {
/*  320 */               chunksection.a(i, l1 & 0xF, k, 0);
/*  321 */               this.world.n(new BlockPosition((this.locX << 4) + i, l1, (this.locZ << 4) + k));
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  326 */         int l1 = 15;
/*      */         
/*  328 */         while ((i1 > 0) && (l1 > 0)) {
/*  329 */           i1--;
/*  330 */           int i2 = e(i, i1, k);
/*  331 */           if (i2 == 0) {
/*  332 */             i2 = 1;
/*      */           }
/*      */           
/*  335 */           l1 -= i2;
/*  336 */           if (l1 < 0) {
/*  337 */             l1 = 0;
/*      */           }
/*      */           
/*  340 */           ChunkSection chunksection1 = this.sections[(i1 >> 4)];
/*      */           
/*  342 */           if (chunksection1 != null) {
/*  343 */             chunksection1.a(i, i1 & 0xF, k, l1);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  348 */       int l1 = this.heightMap[(k << 4 | i)];
/*  349 */       int i2 = l;
/*  350 */       int j2 = l1;
/*      */       
/*  352 */       if (l1 < l) {
/*  353 */         i2 = l1;
/*  354 */         j2 = l;
/*      */       }
/*      */       
/*  357 */       if (l1 < this.t) {
/*  358 */         this.t = l1;
/*      */       }
/*      */       
/*  361 */       if (!this.world.worldProvider.o()) {
/*  362 */         Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*      */         
/*  364 */         while (iterator.hasNext()) {
/*  365 */           EnumDirection enumdirection = (EnumDirection)iterator.next();
/*      */           
/*  367 */           a(j1 + enumdirection.getAdjacentX(), k1 + enumdirection.getAdjacentZ(), i2, j2);
/*      */         }
/*      */         
/*  370 */         a(j1, k1, i2, j2);
/*      */       }
/*      */       
/*  373 */       this.q = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public int b(BlockPosition blockposition) {
/*  378 */     return getType(blockposition).p();
/*      */   }
/*      */   
/*      */   private int e(int i, int j, int k) {
/*  382 */     return getType(i, j, k).p();
/*      */   }
/*      */   
/*      */   private Block getType(int i, int j, int k) {
/*  386 */     Block block = Blocks.AIR;
/*      */     
/*  388 */     if ((j >= 0) && (j >> 4 < this.sections.length)) {
/*  389 */       ChunkSection chunksection = this.sections[(j >> 4)];
/*      */       
/*  391 */       if (chunksection != null) {
/*      */         try {
/*  393 */           block = chunksection.b(i, j & 0xF, k);
/*      */         } catch (Throwable throwable) {
/*  395 */           CrashReport crashreport = CrashReport.a(throwable, "Getting block");
/*      */           
/*  397 */           throw new ReportedException(crashreport);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  402 */     return block;
/*      */   }
/*      */   
/*      */   public Block getTypeAbs(final int i, final int j, final int k) {
/*      */     try {
/*  407 */       return getType(i & 0xF, j, k & 0xF);
/*      */     } catch (ReportedException reportedexception) {
/*  409 */       CrashReportSystemDetails crashreportsystemdetails = reportedexception.a().a("Block being got");
/*      */       
/*  411 */       crashreportsystemdetails.a("Location", new Callable() {
/*      */         public String a() throws Exception {
/*  413 */           return CrashReportSystemDetails.a(new BlockPosition(Chunk.this.locX * 16 + i, j, Chunk.this.locZ * 16 + k));
/*      */         }
/*      */         
/*      */         public Object call() throws Exception {
/*  417 */           return a();
/*      */         }
/*  419 */       });
/*  420 */       throw reportedexception;
/*      */     }
/*      */   }
/*      */   
/*      */   public Block getType(final BlockPosition blockposition) {
/*      */     try {
/*  426 */       return getType(blockposition.getX() & 0xF, blockposition.getY(), blockposition.getZ() & 0xF);
/*      */     } catch (ReportedException reportedexception) {
/*  428 */       CrashReportSystemDetails crashreportsystemdetails = reportedexception.a().a("Block being got");
/*      */       
/*  430 */       crashreportsystemdetails.a("Location", new Callable() {
/*      */         public String a() throws Exception {
/*  432 */           return CrashReportSystemDetails.a(blockposition);
/*      */         }
/*      */         
/*      */         public Object call() throws Exception {
/*  436 */           return a();
/*      */         }
/*  438 */       });
/*  439 */       throw reportedexception;
/*      */     }
/*      */   }
/*      */   
/*      */   public IBlockData getBlockData(final BlockPosition blockposition) {
/*  444 */     if (this.world.G() == WorldType.DEBUG_ALL_BLOCK_STATES) {
/*  445 */       IBlockData iblockdata = null;
/*      */       
/*  447 */       if (blockposition.getY() == 60) {
/*  448 */         iblockdata = Blocks.BARRIER.getBlockData();
/*      */       }
/*      */       
/*  451 */       if (blockposition.getY() == 70) {
/*  452 */         iblockdata = ChunkProviderDebug.b(blockposition.getX(), blockposition.getZ());
/*      */       }
/*      */       
/*  455 */       return iblockdata == null ? Blocks.AIR.getBlockData() : iblockdata;
/*      */     }
/*      */     try {
/*  458 */       if ((blockposition.getY() >= 0) && (blockposition.getY() >> 4 < this.sections.length)) {
/*  459 */         ChunkSection chunksection = this.sections[(blockposition.getY() >> 4)];
/*      */         
/*  461 */         if (chunksection != null) {
/*  462 */           int i = blockposition.getX() & 0xF;
/*  463 */           int j = blockposition.getY() & 0xF;
/*  464 */           int k = blockposition.getZ() & 0xF;
/*      */           
/*  466 */           return chunksection.getType(i, j, k);
/*      */         }
/*      */       }
/*      */       
/*  470 */       return Blocks.AIR.getBlockData();
/*      */     } catch (Throwable throwable) {
/*  472 */       CrashReport crashreport = CrashReport.a(throwable, "Getting block state");
/*  473 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
/*      */       
/*  475 */       crashreportsystemdetails.a("Location", new Callable() {
/*      */         public String a() throws Exception {
/*  477 */           return CrashReportSystemDetails.a(blockposition);
/*      */         }
/*      */         
/*      */         public Object call() throws Exception {
/*  481 */           return a();
/*      */         }
/*  483 */       });
/*  484 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   private int g(int i, int j, int k)
/*      */   {
/*  490 */     if (j >> 4 >= this.sections.length) {
/*  491 */       return 0;
/*      */     }
/*  493 */     ChunkSection chunksection = this.sections[(j >> 4)];
/*      */     
/*  495 */     return chunksection != null ? chunksection.c(i, j & 0xF, k) : 0;
/*      */   }
/*      */   
/*      */   public int c(BlockPosition blockposition)
/*      */   {
/*  500 */     return g(blockposition.getX() & 0xF, blockposition.getY(), blockposition.getZ() & 0xF);
/*      */   }
/*      */   
/*      */   public IBlockData a(BlockPosition blockposition, IBlockData iblockdata) {
/*  504 */     int i = blockposition.getX() & 0xF;
/*  505 */     int j = blockposition.getY();
/*  506 */     int k = blockposition.getZ() & 0xF;
/*  507 */     int l = k << 4 | i;
/*      */     
/*  509 */     if (j >= this.f[l] - 1) {
/*  510 */       this.f[l] = 64537;
/*      */     }
/*      */     
/*  513 */     int i1 = this.heightMap[l];
/*  514 */     IBlockData iblockdata1 = getBlockData(blockposition);
/*      */     
/*  516 */     if (iblockdata1 == iblockdata) {
/*  517 */       return null;
/*      */     }
/*  519 */     Block block = iblockdata.getBlock();
/*  520 */     Block block1 = iblockdata1.getBlock();
/*  521 */     ChunkSection chunksection = this.sections[(j >> 4)];
/*  522 */     boolean flag = false;
/*      */     
/*  524 */     if (chunksection == null) {
/*  525 */       if (block == Blocks.AIR) {
/*  526 */         return null;
/*      */       }
/*      */       
/*  529 */       chunksection = this.sections[(j >> 4)] = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.o());
/*  530 */       flag = j >= i1;
/*      */     }
/*      */     
/*  533 */     chunksection.setType(i, j & 0xF, k, iblockdata);
/*  534 */     if (block1 != block) {
/*  535 */       if (!this.world.isClientSide) {
/*  536 */         block1.remove(this.world, blockposition, iblockdata1);
/*  537 */       } else if ((block1 instanceof IContainer)) {
/*  538 */         this.world.t(blockposition);
/*      */       }
/*      */     }
/*      */     
/*  542 */     if (chunksection.b(i, j & 0xF, k) != block) {
/*  543 */       return null;
/*      */     }
/*  545 */     if (flag) {
/*  546 */       initLighting();
/*      */     } else {
/*  548 */       int j1 = block.p();
/*  549 */       int k1 = block1.p();
/*      */       
/*  551 */       if (j1 > 0) {
/*  552 */         if (j >= i1) {
/*  553 */           d(i, j + 1, k);
/*      */         }
/*  555 */       } else if (j == i1 - 1) {
/*  556 */         d(i, j, k);
/*      */       }
/*      */       
/*  559 */       if ((j1 != k1) && ((j1 < k1) || (getBrightness(EnumSkyBlock.SKY, blockposition) > 0) || (getBrightness(EnumSkyBlock.BLOCK, blockposition) > 0))) {
/*  560 */         d(i, k);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  566 */     if ((block1 instanceof IContainer)) {
/*  567 */       TileEntity tileentity = a(blockposition, EnumTileEntityState.CHECK);
/*  568 */       if (tileentity != null) {
/*  569 */         tileentity.E();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  574 */     if ((!this.world.isClientSide) && (block1 != block) && ((!this.world.captureBlockStates) || ((block instanceof BlockContainer)))) {
/*  575 */       block.onPlace(this.world, blockposition, iblockdata);
/*      */     }
/*      */     
/*  578 */     if ((block instanceof IContainer)) {
/*  579 */       TileEntity tileentity = a(blockposition, EnumTileEntityState.CHECK);
/*  580 */       if (tileentity == null) {
/*  581 */         tileentity = ((IContainer)block).a(this.world, block.toLegacyData(iblockdata));
/*  582 */         this.world.setTileEntity(blockposition, tileentity);
/*      */       }
/*      */       
/*  585 */       if (tileentity != null) {
/*  586 */         tileentity.E();
/*      */       }
/*      */     }
/*      */     
/*  590 */     this.q = true;
/*  591 */     return iblockdata1;
/*      */   }
/*      */   
/*      */ 
/*      */   public int getBrightness(EnumSkyBlock enumskyblock, BlockPosition blockposition)
/*      */   {
/*  597 */     int i = blockposition.getX() & 0xF;
/*  598 */     int j = blockposition.getY();
/*  599 */     int k = blockposition.getZ() & 0xF;
/*  600 */     ChunkSection chunksection = this.sections[(j >> 4)];
/*      */     
/*  602 */     return enumskyblock == EnumSkyBlock.BLOCK ? chunksection.e(i, j & 0xF, k) : enumskyblock == EnumSkyBlock.SKY ? chunksection.d(i, j & 0xF, k) : this.world.worldProvider.o() ? 0 : chunksection == null ? 0 : d(blockposition) ? enumskyblock.c : enumskyblock.c;
/*      */   }
/*      */   
/*      */   public void a(EnumSkyBlock enumskyblock, BlockPosition blockposition, int i) {
/*  606 */     int j = blockposition.getX() & 0xF;
/*  607 */     int k = blockposition.getY();
/*  608 */     int l = blockposition.getZ() & 0xF;
/*  609 */     ChunkSection chunksection = this.sections[(k >> 4)];
/*      */     
/*  611 */     if (chunksection == null) {
/*  612 */       chunksection = this.sections[(k >> 4)] = new ChunkSection(k >> 4 << 4, !this.world.worldProvider.o());
/*  613 */       initLighting();
/*      */     }
/*      */     
/*  616 */     this.q = true;
/*  617 */     if (enumskyblock == EnumSkyBlock.SKY) {
/*  618 */       if (!this.world.worldProvider.o()) {
/*  619 */         chunksection.a(j, k & 0xF, l, i);
/*      */       }
/*  621 */     } else if (enumskyblock == EnumSkyBlock.BLOCK) {
/*  622 */       chunksection.b(j, k & 0xF, l, i);
/*      */     }
/*      */   }
/*      */   
/*      */   public int a(BlockPosition blockposition, int i)
/*      */   {
/*  628 */     int j = blockposition.getX() & 0xF;
/*  629 */     int k = blockposition.getY();
/*  630 */     int l = blockposition.getZ() & 0xF;
/*  631 */     ChunkSection chunksection = this.sections[(k >> 4)];
/*      */     
/*  633 */     if (chunksection == null) {
/*  634 */       return (!this.world.worldProvider.o()) && (i < EnumSkyBlock.SKY.c) ? EnumSkyBlock.SKY.c - i : 0;
/*      */     }
/*  636 */     int i1 = this.world.worldProvider.o() ? 0 : chunksection.d(j, k & 0xF, l);
/*      */     
/*  638 */     i1 -= i;
/*  639 */     int j1 = chunksection.e(j, k & 0xF, l);
/*      */     
/*  641 */     if (j1 > i1) {
/*  642 */       i1 = j1;
/*      */     }
/*      */     
/*  645 */     return i1;
/*      */   }
/*      */   
/*      */   public void a(Entity entity)
/*      */   {
/*  650 */     this.r = true;
/*  651 */     int i = MathHelper.floor(entity.locX / 16.0D);
/*  652 */     int j = MathHelper.floor(entity.locZ / 16.0D);
/*      */     
/*  654 */     if ((i != this.locX) || (j != this.locZ))
/*      */     {
/*  656 */       Bukkit.getLogger().warning("Wrong location for " + entity + " in world '" + this.world.getWorld().getName() + "'!");
/*      */       
/*  658 */       Bukkit.getLogger().warning("Entity is at " + entity.locX + "," + entity.locZ + " (chunk " + i + "," + j + ") but was stored in chunk " + this.locX + "," + this.locZ);
/*      */       
/*  660 */       entity.die();
/*      */     }
/*      */     
/*  663 */     int k = MathHelper.floor(entity.locY / 16.0D);
/*      */     
/*  665 */     if (k < 0) {
/*  666 */       k = 0;
/*      */     }
/*      */     
/*  669 */     if (k >= this.entitySlices.length) {
/*  670 */       k = this.entitySlices.length - 1;
/*      */     }
/*      */     
/*  673 */     entity.ad = true;
/*  674 */     entity.ae = this.locX;
/*  675 */     entity.af = k;
/*  676 */     entity.ag = this.locZ;
/*  677 */     this.entitySlices[k].add(entity);
/*      */     
/*      */ 
/*  680 */     if ((entity instanceof EntityInsentient)) {
/*  681 */       EntityInsentient entityinsentient = (EntityInsentient)entity;
/*  682 */       if ((entityinsentient.isTypeNotPersistent()) && (entityinsentient.isPersistent()))
/*      */         return;
/*      */     }
/*      */     EnumCreatureType[] arrayOfEnumCreatureType;
/*  686 */     int i = (arrayOfEnumCreatureType = EnumCreatureType.values()).length; for (int j = 0; j < i; j++) { EnumCreatureType creatureType = arrayOfEnumCreatureType[j];
/*      */       
/*  688 */       if (creatureType.a().isAssignableFrom(entity.getClass()))
/*      */       {
/*  690 */         this.entityCount.adjustOrPutValue(creatureType.a(), 1, 1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(Entity entity)
/*      */   {
/*  697 */     a(entity, entity.af);
/*      */   }
/*      */   
/*      */   public void a(Entity entity, int i) {
/*  701 */     if (i < 0) {
/*  702 */       i = 0;
/*      */     }
/*      */     
/*  705 */     if (i >= this.entitySlices.length) {
/*  706 */       i = this.entitySlices.length - 1;
/*      */     }
/*      */     
/*  709 */     this.entitySlices[i].remove(entity);
/*      */     
/*      */ 
/*  712 */     if ((entity instanceof EntityInsentient)) {
/*  713 */       EntityInsentient entityinsentient = (EntityInsentient)entity;
/*  714 */       if ((entityinsentient.isTypeNotPersistent()) && (entityinsentient.isPersistent()))
/*      */         return;
/*      */     }
/*      */     EnumCreatureType[] arrayOfEnumCreatureType;
/*  718 */     int i = (arrayOfEnumCreatureType = EnumCreatureType.values()).length; for (int j = 0; j < i; j++) { EnumCreatureType creatureType = arrayOfEnumCreatureType[j];
/*      */       
/*  720 */       if (creatureType.a().isAssignableFrom(entity.getClass()))
/*      */       {
/*  722 */         this.entityCount.adjustValue(creatureType.a(), -1);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean d(BlockPosition blockposition)
/*      */   {
/*  729 */     int i = blockposition.getX() & 0xF;
/*  730 */     int j = blockposition.getY();
/*  731 */     int k = blockposition.getZ() & 0xF;
/*      */     
/*  733 */     return j >= this.heightMap[(k << 4 | i)];
/*      */   }
/*      */   
/*      */   private TileEntity i(BlockPosition blockposition) {
/*  737 */     Block block = getType(blockposition);
/*      */     
/*  739 */     return !block.isTileEntity() ? null : ((IContainer)block).a(this.world, c(blockposition));
/*      */   }
/*      */   
/*      */   public TileEntity a(BlockPosition blockposition, EnumTileEntityState chunk_enumtileentitystate)
/*      */   {
/*  744 */     TileEntity tileentity = null;
/*  745 */     if (this.world.captureBlockStates) {
/*  746 */       tileentity = (TileEntity)this.world.capturedTileEntities.get(blockposition);
/*      */     }
/*  748 */     if (tileentity == null) {
/*  749 */       tileentity = (TileEntity)this.tileEntities.get(blockposition);
/*      */     }
/*      */     
/*      */ 
/*  753 */     if (tileentity == null) {
/*  754 */       if (chunk_enumtileentitystate == EnumTileEntityState.IMMEDIATE) {
/*  755 */         tileentity = i(blockposition);
/*  756 */         this.world.setTileEntity(blockposition, tileentity);
/*  757 */       } else if (chunk_enumtileentitystate == EnumTileEntityState.QUEUED) {
/*  758 */         this.w.add(blockposition);
/*      */       }
/*  760 */     } else if (tileentity.x()) {
/*  761 */       this.tileEntities.remove(blockposition);
/*  762 */       return null;
/*      */     }
/*      */     
/*  765 */     return tileentity;
/*      */   }
/*      */   
/*      */   public void a(TileEntity tileentity) {
/*  769 */     a(tileentity.getPosition(), tileentity);
/*  770 */     if (this.h) {
/*  771 */       this.world.a(tileentity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(BlockPosition blockposition, TileEntity tileentity)
/*      */   {
/*  777 */     tileentity.a(this.world);
/*  778 */     tileentity.a(blockposition);
/*  779 */     if ((getType(blockposition) instanceof IContainer)) {
/*  780 */       if (this.tileEntities.containsKey(blockposition)) {
/*  781 */         ((TileEntity)this.tileEntities.get(blockposition)).y();
/*      */       }
/*      */       
/*  784 */       tileentity.D();
/*  785 */       this.tileEntities.put(blockposition, tileentity);
/*      */     }
/*      */     else {
/*  788 */       System.out.println("Attempted to place a tile entity (" + tileentity + ") at " + tileentity.position.getX() + "," + tileentity.position.getY() + "," + tileentity.position.getZ() + 
/*  789 */         " (" + org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(getType(blockposition)) + ") where there was no entity tile!");
/*  790 */       System.out.println("Chunk coordinates: " + this.locX * 16 + "," + this.locZ * 16);
/*  791 */       new Exception().printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public void e(BlockPosition blockposition)
/*      */   {
/*  797 */     if (this.h) {
/*  798 */       TileEntity tileentity = (TileEntity)this.tileEntities.remove(blockposition);
/*      */       
/*  800 */       if (tileentity != null) {
/*  801 */         tileentity.y();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void addEntities()
/*      */   {
/*  808 */     this.h = true;
/*  809 */     this.world.a(this.tileEntities.values());
/*      */     
/*  811 */     for (int i = 0; i < this.entitySlices.length; i++) {
/*  812 */       Iterator iterator = this.entitySlices[i].iterator();
/*      */       
/*  814 */       while (iterator.hasNext()) {
/*  815 */         Entity entity = (Entity)iterator.next();
/*      */         
/*  817 */         entity.ah();
/*      */       }
/*      */       
/*  820 */       this.world.b(this.entitySlices[i]);
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeEntities()
/*      */   {
/*  826 */     this.h = false;
/*  827 */     Iterator iterator = this.tileEntities.values().iterator();
/*      */     
/*  829 */     while (iterator.hasNext()) {
/*  830 */       TileEntity tileentity = (TileEntity)iterator.next();
/*      */       
/*  832 */       if ((tileentity instanceof IInventory))
/*      */       {
/*  834 */         for (HumanEntity h : Lists.newArrayList(((IInventory)tileentity).getViewers()))
/*      */         {
/*  836 */           if ((h instanceof CraftHumanEntity))
/*      */           {
/*  838 */             ((CraftHumanEntity)h).getHandle().closeInventory();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  844 */       this.world.b(tileentity);
/*      */     }
/*      */     
/*  847 */     for (int i = 0; i < this.entitySlices.length; i++)
/*      */     {
/*  849 */       List<Entity> newList = Lists.newArrayList(this.entitySlices[i]);
/*  850 */       Object iter = newList.iterator();
/*  851 */       while (((Iterator)iter).hasNext()) {
/*  852 */         Entity entity = (Entity)((Iterator)iter).next();
/*      */         
/*  854 */         if ((entity instanceof IInventory))
/*      */         {
/*  856 */           for (HumanEntity h : Lists.newArrayList(((IInventory)entity).getViewers()))
/*      */           {
/*  858 */             if ((h instanceof CraftHumanEntity))
/*      */             {
/*  860 */               ((CraftHumanEntity)h).getHandle().closeInventory();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  868 */         if ((entity instanceof EntityPlayer)) {
/*  869 */           ((Iterator)iter).remove();
/*      */         }
/*      */       }
/*      */       
/*  873 */       this.world.c(newList);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void e()
/*      */   {
/*  880 */     this.q = true;
/*      */   }
/*      */   
/*      */   public void a(Entity entity, AxisAlignedBB axisalignedbb, List<Entity> list, Predicate<? super Entity> predicate) {
/*  884 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/*  885 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*      */     
/*  887 */     i = MathHelper.clamp(i, 0, this.entitySlices.length - 1);
/*  888 */     j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);
/*      */     
/*  890 */     for (int k = i; k <= j; k++) {
/*  891 */       if (!this.entitySlices[k].isEmpty()) {
/*  892 */         Iterator iterator = this.entitySlices[k].iterator();
/*      */         
/*  894 */         while (iterator.hasNext()) {
/*  895 */           Entity entity1 = (Entity)iterator.next();
/*      */           
/*  897 */           if ((entity1.getBoundingBox().b(axisalignedbb)) && (entity1 != entity)) {
/*  898 */             if ((predicate == null) || (predicate.apply(entity1))) {
/*  899 */               list.add(entity1);
/*      */             }
/*      */             
/*  902 */             Entity[] aentity = entity1.aB();
/*      */             
/*  904 */             if (aentity != null) {
/*  905 */               for (int l = 0; l < aentity.length; l++) {
/*  906 */                 entity1 = aentity[l];
/*  907 */                 if ((entity1 != entity) && (entity1.getBoundingBox().b(axisalignedbb)) && ((predicate == null) || (predicate.apply(entity1)))) {
/*  908 */                   list.add(entity1);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public <T extends Entity> void a(Class<? extends T> oclass, AxisAlignedBB axisalignedbb, List<T> list, Predicate<? super T> predicate)
/*      */   {
/*  920 */     int i = MathHelper.floor((axisalignedbb.b - 2.0D) / 16.0D);
/*  921 */     int j = MathHelper.floor((axisalignedbb.e + 2.0D) / 16.0D);
/*      */     
/*  923 */     i = MathHelper.clamp(i, 0, this.entitySlices.length - 1);
/*  924 */     j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);
/*      */     
/*  926 */     for (int k = i; k <= j; k++) {
/*  927 */       Iterator iterator = this.entitySlices[k].iterator();
/*      */       
/*  929 */       while (iterator.hasNext()) {
/*  930 */         Entity entity = (Entity)iterator.next();
/*      */         
/*  932 */         if ((oclass.isInstance(entity)) && (entity.getBoundingBox().b(axisalignedbb)) && ((predicate == null) || (predicate.apply(entity)))) {
/*  933 */           list.add(entity);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean a(boolean flag)
/*      */   {
/*  941 */     if (flag) {
/*  942 */       if (((this.r) && (this.world.getTime() != this.lastSaved)) || (this.q)) {
/*  943 */         return true;
/*      */       }
/*  945 */     } else if ((this.r) && (this.world.getTime() >= this.lastSaved + MinecraftServer.getServer().autosavePeriod * 4)) {
/*  946 */       return true;
/*      */     }
/*      */     
/*  949 */     return this.q;
/*      */   }
/*      */   
/*      */   public Random a(long i) {
/*  953 */     return new Random(this.world.getSeed() + this.locX * this.locX * 4987142 + this.locX * 5947611 + this.locZ * this.locZ * 4392871L + this.locZ * 389711 ^ i);
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  957 */     return false;
/*      */   }
/*      */   
/*      */   public void loadNearby(IChunkProvider ichunkprovider, IChunkProvider ichunkprovider1, int i, int j) {
/*  961 */     this.world.timings.syncChunkLoadPostTimer.startTiming();
/*  962 */     boolean flag = ichunkprovider.isChunkLoaded(i, j - 1);
/*  963 */     boolean flag1 = ichunkprovider.isChunkLoaded(i + 1, j);
/*  964 */     boolean flag2 = ichunkprovider.isChunkLoaded(i, j + 1);
/*  965 */     boolean flag3 = ichunkprovider.isChunkLoaded(i - 1, j);
/*  966 */     boolean flag4 = ichunkprovider.isChunkLoaded(i - 1, j - 1);
/*  967 */     boolean flag5 = ichunkprovider.isChunkLoaded(i + 1, j + 1);
/*  968 */     boolean flag6 = ichunkprovider.isChunkLoaded(i - 1, j + 1);
/*  969 */     boolean flag7 = ichunkprovider.isChunkLoaded(i + 1, j - 1);
/*      */     
/*  971 */     if ((flag1) && (flag2) && (flag5)) {
/*  972 */       if (!this.done) {
/*  973 */         ichunkprovider.getChunkAt(ichunkprovider1, i, j);
/*      */       } else {
/*  975 */         ichunkprovider.a(ichunkprovider1, this, i, j);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  981 */     if ((flag3) && (flag2) && (flag6)) {
/*  982 */       Chunk chunk = ichunkprovider.getOrCreateChunk(i - 1, j);
/*  983 */       if (!chunk.done) {
/*  984 */         ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j);
/*      */       } else {
/*  986 */         ichunkprovider.a(ichunkprovider1, chunk, i - 1, j);
/*      */       }
/*      */     }
/*      */     
/*  990 */     if ((flag) && (flag1) && (flag7)) {
/*  991 */       Chunk chunk = ichunkprovider.getOrCreateChunk(i, j - 1);
/*  992 */       if (!chunk.done) {
/*  993 */         ichunkprovider.getChunkAt(ichunkprovider1, i, j - 1);
/*      */       } else {
/*  995 */         ichunkprovider.a(ichunkprovider1, chunk, i, j - 1);
/*      */       }
/*      */     }
/*      */     
/*  999 */     if ((flag4) && (flag) && (flag3)) {
/* 1000 */       Chunk chunk = ichunkprovider.getOrCreateChunk(i - 1, j - 1);
/* 1001 */       if (!chunk.done) {
/* 1002 */         ichunkprovider.getChunkAt(ichunkprovider1, i - 1, j - 1);
/*      */       } else {
/* 1004 */         ichunkprovider.a(ichunkprovider1, chunk, i - 1, j - 1);
/*      */       }
/*      */     }
/*      */     
/* 1008 */     this.world.timings.syncChunkLoadPostTimer.stopTiming();
/*      */   }
/*      */   
/*      */   public BlockPosition h(BlockPosition blockposition) {
/* 1012 */     int i = blockposition.getX() & 0xF;
/* 1013 */     int j = blockposition.getZ() & 0xF;
/* 1014 */     int k = i | j << 4;
/* 1015 */     BlockPosition blockposition1 = new BlockPosition(blockposition.getX(), this.f[k], blockposition.getZ());
/*      */     
/* 1017 */     if (blockposition1.getY() == 64537) {
/* 1018 */       int l = g() + 15;
/*      */       
/* 1020 */       blockposition1 = new BlockPosition(blockposition.getX(), l, blockposition.getZ());
/* 1021 */       int i1 = -1;
/*      */       
/* 1023 */       while ((blockposition1.getY() > 0) && (i1 == -1)) {
/* 1024 */         Block block = getType(blockposition1);
/* 1025 */         Material material = block.getMaterial();
/*      */         
/* 1027 */         if ((!material.isSolid()) && (!material.isLiquid())) {
/* 1028 */           blockposition1 = blockposition1.down();
/*      */         } else {
/* 1030 */           i1 = blockposition1.getY() + 1;
/*      */         }
/*      */       }
/*      */       
/* 1034 */       this.f[k] = i1;
/*      */     }
/*      */     
/* 1037 */     return new BlockPosition(blockposition.getX(), this.f[k], blockposition.getZ());
/*      */   }
/*      */   
/*      */   public void b(boolean flag) {
/* 1041 */     if ((this.k) && (!this.world.worldProvider.o()) && (!flag)) {
/* 1042 */       h(this.world.isClientSide);
/*      */     }
/*      */     
/* 1045 */     this.p = true;
/* 1046 */     if ((!this.lit) && (this.done) && (this.world.spigotConfig.randomLightUpdates)) {
/* 1047 */       n();
/*      */     }
/*      */     
/* 1050 */     while (!this.w.isEmpty()) {
/* 1051 */       BlockPosition blockposition = (BlockPosition)this.w.poll();
/*      */       
/* 1053 */       if ((a(blockposition, EnumTileEntityState.CHECK) == null) && (getType(blockposition).isTileEntity())) {
/* 1054 */         TileEntity tileentity = i(blockposition);
/*      */         
/* 1056 */         this.world.setTileEntity(blockposition, tileentity);
/* 1057 */         this.world.b(blockposition, blockposition);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean mustSave;
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean isReady()
/*      */   {
/* 1071 */     return true;
/*      */   }
/*      */   
/*      */   public ChunkCoordIntPair j()
/*      */   {
/* 1076 */     return new ChunkCoordIntPair(this.locX, this.locZ);
/*      */   }
/*      */   
/*      */   public boolean c(int i, int j) {
/* 1080 */     if (i < 0) {
/* 1081 */       i = 0;
/*      */     }
/*      */     
/* 1084 */     if (j >= 256) {
/* 1085 */       j = 255;
/*      */     }
/*      */     
/* 1088 */     for (int k = i; k <= j; k += 16) {
/* 1089 */       ChunkSection chunksection = this.sections[(k >> 4)];
/*      */       
/* 1091 */       if ((chunksection != null) && (!chunksection.a())) {
/* 1092 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1096 */     return true;
/*      */   }
/*      */   
/*      */   public void a(ChunkSection[] achunksection) {
/* 1100 */     if (this.sections.length != achunksection.length) {
/* 1101 */       c.warn("Could not set level chunk sections, array length is " + achunksection.length + " instead of " + this.sections.length);
/*      */     } else {
/* 1103 */       for (int i = 0; i < this.sections.length; i++) {
/* 1104 */         this.sections[i] = achunksection[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public BiomeBase getBiome(BlockPosition blockposition, WorldChunkManager worldchunkmanager)
/*      */   {
/* 1111 */     int i = blockposition.getX() & 0xF;
/* 1112 */     int j = blockposition.getZ() & 0xF;
/* 1113 */     int k = this.e[(j << 4 | i)] & 0xFF;
/*      */     
/*      */ 
/* 1116 */     if (k == 255) {
/* 1117 */       BiomeBase biomebase = worldchunkmanager.getBiome(blockposition, BiomeBase.PLAINS);
/* 1118 */       k = biomebase.id;
/* 1119 */       this.e[(j << 4 | i)] = ((byte)(k & 0xFF));
/*      */     }
/*      */     
/* 1122 */     BiomeBase biomebase = BiomeBase.getBiome(k);
/* 1123 */     return biomebase == null ? BiomeBase.PLAINS : biomebase;
/*      */   }
/*      */   
/*      */   public byte[] getBiomeIndex() {
/* 1127 */     return this.e;
/*      */   }
/*      */   
/*      */   public void a(byte[] abyte) {
/* 1131 */     if (this.e.length != abyte.length) {
/* 1132 */       c.warn("Could not set level chunk biomes, array length is " + abyte.length + " instead of " + this.e.length);
/*      */     } else {
/* 1134 */       for (int i = 0; i < this.e.length; i++) {
/* 1135 */         this.e[i] = abyte[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void l()
/*      */   {
/* 1142 */     this.v = 0;
/*      */   }
/*      */   
/*      */   public void m() {
/* 1146 */     BlockPosition blockposition = new BlockPosition(this.locX << 4, 0, this.locZ << 4);
/*      */     
/* 1148 */     for (int i = 0; i < 8; i++) {
/* 1149 */       if (this.v >= 4096) {
/* 1150 */         return;
/*      */       }
/*      */       
/* 1153 */       int j = this.v % 16;
/* 1154 */       int k = this.v / 16 % 16;
/* 1155 */       int l = this.v / 256;
/*      */       
/* 1157 */       this.v += 1;
/*      */       
/* 1159 */       for (int i1 = 0; i1 < 16; i1++) {
/* 1160 */         BlockPosition blockposition1 = blockposition.a(k, (j << 4) + i1, l);
/* 1161 */         boolean flag = (i1 == 0) || (i1 == 15) || (k == 0) || (k == 15) || (l == 0) || (l == 15);
/*      */         
/* 1163 */         if (((this.sections[j] == null) && (flag)) || ((this.sections[j] != null) && (this.sections[j].b(k, i1, l).getMaterial() == Material.AIR))) {
/* 1164 */           EnumDirection[] aenumdirection = EnumDirection.values();
/* 1165 */           int j1 = aenumdirection.length;
/*      */           
/* 1167 */           for (int k1 = 0; k1 < j1; k1++) {
/* 1168 */             EnumDirection enumdirection = aenumdirection[k1];
/* 1169 */             BlockPosition blockposition2 = blockposition1.shift(enumdirection);
/*      */             
/* 1171 */             if (this.world.getType(blockposition2).getBlock().r() > 0) {
/* 1172 */               this.world.x(blockposition2);
/*      */             }
/*      */           }
/*      */           
/* 1176 */           this.world.x(blockposition1);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void n()
/*      */   {
/* 1184 */     this.done = true;
/* 1185 */     this.lit = true;
/* 1186 */     BlockPosition blockposition = new BlockPosition(this.locX << 4, 0, this.locZ << 4);
/*      */     
/* 1188 */     if (!this.world.worldProvider.o()) {
/* 1189 */       if (this.world.areChunksLoadedBetween(blockposition.a(-1, 0, -1), blockposition.a(16, this.world.F(), 16)))
/*      */       {
/* 1191 */         for (int i = 0; i < 16; i++) {
/* 1192 */           for (int j = 0; j < 16; j++) {
/* 1193 */             if (!e(i, j)) {
/* 1194 */               this.lit = false;
/* 1195 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1200 */         if (this.lit) {
/* 1201 */           Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*      */           
/* 1203 */           while (iterator.hasNext()) {
/* 1204 */             EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 1205 */             int k = enumdirection.c() == EnumDirection.EnumAxisDirection.POSITIVE ? 16 : 1;
/*      */             
/* 1207 */             this.world.getChunkAtWorldCoords(blockposition.shift(enumdirection, k)).a(enumdirection.opposite());
/*      */           }
/*      */           
/* 1210 */           y();
/*      */         }
/*      */       } else {
/* 1213 */         this.lit = false;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void y()
/*      */   {
/* 1220 */     for (int i = 0; i < this.g.length; i++) {
/* 1221 */       this.g[i] = true;
/*      */     }
/*      */     
/* 1224 */     h(false);
/*      */   }
/*      */   
/*      */   private void a(EnumDirection enumdirection) {
/* 1228 */     if (this.done)
/*      */     {
/*      */ 
/* 1231 */       if (enumdirection == EnumDirection.EAST) {
/* 1232 */         for (int i = 0; i < 16; i++) {
/* 1233 */           e(15, i);
/*      */         }
/* 1235 */       } else if (enumdirection == EnumDirection.WEST) {
/* 1236 */         for (int i = 0; i < 16; i++) {
/* 1237 */           e(0, i);
/*      */         }
/* 1239 */       } else if (enumdirection == EnumDirection.SOUTH) {
/* 1240 */         for (int i = 0; i < 16; i++) {
/* 1241 */           e(i, 15);
/*      */         }
/* 1243 */       } else if (enumdirection == EnumDirection.NORTH) {
/* 1244 */         for (int i = 0; i < 16; i++) {
/* 1245 */           e(i, 0);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean e(int i, int j)
/*      */   {
/* 1253 */     int k = g();
/* 1254 */     boolean flag = false;
/* 1255 */     boolean flag1 = false;
/* 1256 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition((this.locX << 4) + i, 0, (this.locZ << 4) + j);
/*      */     
/*      */ 
/*      */ 
/* 1260 */     for (int l = k + 16 - 1; (l > this.world.F()) || ((l > 0) && (!flag1)); l--) {
/* 1261 */       blockposition_mutableblockposition.c(blockposition_mutableblockposition.getX(), l, blockposition_mutableblockposition.getZ());
/* 1262 */       int i1 = b(blockposition_mutableblockposition);
/*      */       
/* 1264 */       if ((i1 == 255) && (blockposition_mutableblockposition.getY() < this.world.F())) {
/* 1265 */         flag1 = true;
/*      */       }
/*      */       
/* 1268 */       if ((!flag) && (i1 > 0)) {
/* 1269 */         flag = true;
/* 1270 */       } else if ((flag) && (i1 == 0) && (!this.world.x(blockposition_mutableblockposition))) {
/* 1271 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1275 */     for (l = blockposition_mutableblockposition.getY(); l > 0; l--) {
/* 1276 */       blockposition_mutableblockposition.c(blockposition_mutableblockposition.getX(), l, blockposition_mutableblockposition.getZ());
/* 1277 */       if (getType(blockposition_mutableblockposition).r() > 0) {
/* 1278 */         this.world.x(blockposition_mutableblockposition);
/*      */       }
/*      */     }
/*      */     
/* 1282 */     return true;
/*      */   }
/*      */   
/*      */   public boolean o() {
/* 1286 */     return this.h;
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 1290 */     return this.world;
/*      */   }
/*      */   
/*      */   public int[] q() {
/* 1294 */     return this.heightMap;
/*      */   }
/*      */   
/*      */   public void a(int[] aint) {
/* 1298 */     if (this.heightMap.length != aint.length) {
/* 1299 */       c.warn("Could not set level chunk heightmap, array length is " + aint.length + " instead of " + this.heightMap.length);
/*      */     } else {
/* 1301 */       for (int i = 0; i < this.heightMap.length; i++) {
/* 1302 */         this.heightMap[i] = aint[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public Map<BlockPosition, TileEntity> getTileEntities()
/*      */   {
/* 1309 */     return this.tileEntities;
/*      */   }
/*      */   
/*      */   public List<Entity>[] getEntitySlices() {
/* 1313 */     return this.entitySlices;
/*      */   }
/*      */   
/*      */   public boolean isDone() {
/* 1317 */     return this.done;
/*      */   }
/*      */   
/*      */   public void d(boolean flag) {
/* 1321 */     this.done = flag;
/*      */   }
/*      */   
/*      */   public boolean u() {
/* 1325 */     return this.lit;
/*      */   }
/*      */   
/*      */   public void e(boolean flag) {
/* 1329 */     this.lit = flag;
/*      */   }
/*      */   
/*      */   public void f(boolean flag) {
/* 1333 */     this.q = flag;
/*      */   }
/*      */   
/*      */   public void g(boolean flag) {
/* 1337 */     this.r = flag;
/*      */   }
/*      */   
/*      */   public void setLastSaved(long i) {
/* 1341 */     this.lastSaved = i;
/*      */   }
/*      */   
/*      */   public int v() {
/* 1345 */     return this.t;
/*      */   }
/*      */   
/*      */   public long w() {
/* 1349 */     return this.u;
/*      */   }
/*      */   
/*      */   public void c(long i) {
/* 1353 */     this.u = i;
/*      */   }
/*      */   
/*      */   public static enum EnumTileEntityState
/*      */   {
/* 1358 */     IMMEDIATE,  QUEUED,  CHECK;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */