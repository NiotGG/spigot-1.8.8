/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
/*     */ 
/*     */ 
/*     */ public class PlayerChunkMap
/*     */ {
/*  20 */   private static final Logger a = ;
/*     */   private final WorldServer world;
/*  22 */   private final List<EntityPlayer> managedPlayers = Lists.newArrayList();
/*  23 */   private final LongHashMap<PlayerChunk> d = new LongHashMap();
/*  24 */   private final Queue<PlayerChunk> e = new ConcurrentLinkedQueue();
/*  25 */   private final Queue<PlayerChunk> f = new ConcurrentLinkedQueue();
/*     */   private int g;
/*     */   private long h;
/*  28 */   private final int[][] i = { { 1 }, { 0, 1 }, { -1 }, { 0, -1 } };
/*     */   private boolean wasNotEmpty;
/*     */   
/*     */   public PlayerChunkMap(WorldServer worldserver, int viewDistance) {
/*  32 */     this.world = worldserver;
/*  33 */     a(viewDistance);
/*     */   }
/*     */   
/*     */   public WorldServer a() {
/*  37 */     return this.world;
/*     */   }
/*     */   
/*     */   public void flush() {
/*  41 */     long i = this.world.getTime();
/*     */     
/*     */ 
/*     */ 
/*  45 */     if (i - this.h > 8000L) {
/*  46 */       this.h = i;
/*     */       
/*     */ 
/*  49 */       Iterator iterator = this.f.iterator();
/*  50 */       while (iterator.hasNext()) {
/*  51 */         PlayerChunk playerchunkmap_playerchunk = (PlayerChunk)iterator.next();
/*  52 */         playerchunkmap_playerchunk.b();
/*  53 */         playerchunkmap_playerchunk.a();
/*     */       }
/*     */     } else {
/*  56 */       Iterator iterator = this.e.iterator();
/*  57 */       while (iterator.hasNext()) {
/*  58 */         PlayerChunk playerchunkmap_playerchunk = (PlayerChunk)iterator.next();
/*  59 */         playerchunkmap_playerchunk.b();
/*  60 */         iterator.remove();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  66 */     if (this.managedPlayers.isEmpty()) {
/*  67 */       if (!this.wasNotEmpty) return;
/*  68 */       WorldProvider worldprovider = this.world.worldProvider;
/*     */       
/*  70 */       if (!worldprovider.e()) {
/*  71 */         this.world.chunkProviderServer.b();
/*     */       }
/*     */       
/*  74 */       this.wasNotEmpty = false;
/*     */     } else {
/*  76 */       this.wasNotEmpty = true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean a(int i, int j)
/*     */   {
/*  83 */     long k = i + 2147483647L | j + 2147483647L << 32;
/*     */     
/*  85 */     return this.d.getEntry(k) != null;
/*     */   }
/*     */   
/*     */   private PlayerChunk a(int i, int j, boolean flag) {
/*  89 */     long k = i + 2147483647L | j + 2147483647L << 32;
/*  90 */     PlayerChunk playerchunkmap_playerchunk = (PlayerChunk)this.d.getEntry(k);
/*     */     
/*  92 */     if ((playerchunkmap_playerchunk == null) && (flag)) {
/*  93 */       playerchunkmap_playerchunk = new PlayerChunk(i, j);
/*  94 */       this.d.put(k, playerchunkmap_playerchunk);
/*  95 */       this.f.add(playerchunkmap_playerchunk);
/*     */     }
/*     */     
/*  98 */     return playerchunkmap_playerchunk;
/*     */   }
/*     */   
/*     */   public final boolean isChunkInUse(int x, int z)
/*     */   {
/* 103 */     PlayerChunk pi = a(x, z, false);
/* 104 */     if (pi != null) {
/* 105 */       return pi.b.size() > 0;
/*     */     }
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public void flagDirty(BlockPosition blockposition)
/*     */   {
/* 112 */     int i = blockposition.getX() >> 4;
/* 113 */     int j = blockposition.getZ() >> 4;
/* 114 */     PlayerChunk playerchunkmap_playerchunk = a(i, j, false);
/*     */     
/* 116 */     if (playerchunkmap_playerchunk != null) {
/* 117 */       playerchunkmap_playerchunk.a(blockposition.getX() & 0xF, blockposition.getY(), blockposition.getZ() & 0xF);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addPlayer(EntityPlayer entityplayer)
/*     */   {
/* 123 */     int i = (int)entityplayer.locX >> 4;
/* 124 */     int j = (int)entityplayer.locZ >> 4;
/*     */     
/* 126 */     entityplayer.d = entityplayer.locX;
/* 127 */     entityplayer.e = entityplayer.locZ;
/*     */     
/*     */ 
/* 130 */     List<ChunkCoordIntPair> chunkList = new LinkedList();
/*     */     int l;
/* 132 */     for (int k = i - this.g; k <= i + this.g; k++) {
/* 133 */       for (l = j - this.g; l <= j + this.g; l++) {
/* 134 */         chunkList.add(new ChunkCoordIntPair(k, l));
/*     */       }
/*     */     }
/*     */     
/* 138 */     Collections.sort(chunkList, new ChunkCoordComparator(entityplayer));
/* 139 */     for (ChunkCoordIntPair pair : chunkList) {
/* 140 */       a(pair.x, pair.z, true).a(entityplayer);
/*     */     }
/*     */     
/*     */ 
/* 144 */     this.managedPlayers.add(entityplayer);
/* 145 */     b(entityplayer);
/*     */   }
/*     */   
/*     */   public void b(EntityPlayer entityplayer) {
/* 149 */     ArrayList arraylist = Lists.newArrayList(entityplayer.chunkCoordIntPairQueue);
/* 150 */     int i = 0;
/* 151 */     int j = this.g;
/* 152 */     int k = (int)entityplayer.locX >> 4;
/* 153 */     int l = (int)entityplayer.locZ >> 4;
/* 154 */     int i1 = 0;
/* 155 */     int j1 = 0;
/* 156 */     ChunkCoordIntPair chunkcoordintpair = a(k, l, true).location;
/*     */     
/* 158 */     entityplayer.chunkCoordIntPairQueue.clear();
/* 159 */     if (arraylist.contains(chunkcoordintpair)) {
/* 160 */       entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 165 */     for (int k1 = 1; k1 <= j * 2; k1++) {
/* 166 */       for (int l1 = 0; l1 < 2; l1++) {
/* 167 */         int[] aint = this.i[(i++ % 4)];
/*     */         
/* 169 */         for (int i2 = 0; i2 < k1; i2++) {
/* 170 */           i1 += aint[0];
/* 171 */           j1 += aint[1];
/* 172 */           chunkcoordintpair = a(k + i1, l + j1, true).location;
/* 173 */           if (arraylist.contains(chunkcoordintpair)) {
/* 174 */             entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 180 */     i %= 4;
/*     */     
/* 182 */     for (k1 = 0; k1 < j * 2; k1++) {
/* 183 */       i1 += this.i[i][0];
/* 184 */       j1 += this.i[i][1];
/* 185 */       chunkcoordintpair = a(k + i1, l + j1, true).location;
/* 186 */       if (arraylist.contains(chunkcoordintpair)) {
/* 187 */         entityplayer.chunkCoordIntPairQueue.add(chunkcoordintpair);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePlayer(EntityPlayer entityplayer)
/*     */   {
/* 194 */     int i = (int)entityplayer.d >> 4;
/* 195 */     int j = (int)entityplayer.e >> 4;
/*     */     
/* 197 */     for (int k = i - this.g; k <= i + this.g; k++) {
/* 198 */       for (int l = j - this.g; l <= j + this.g; l++) {
/* 199 */         PlayerChunk playerchunkmap_playerchunk = a(k, l, false);
/*     */         
/* 201 */         if (playerchunkmap_playerchunk != null) {
/* 202 */           playerchunkmap_playerchunk.b(entityplayer);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 207 */     this.managedPlayers.remove(entityplayer);
/*     */   }
/*     */   
/*     */   private boolean a(int i, int j, int k, int l, int i1) {
/* 211 */     int j1 = i - k;
/* 212 */     int k1 = j - l;
/*     */     
/* 214 */     return (k1 >= -i1) && (k1 <= i1);
/*     */   }
/*     */   
/*     */   public void movePlayer(EntityPlayer entityplayer) {
/* 218 */     int i = (int)entityplayer.locX >> 4;
/* 219 */     int j = (int)entityplayer.locZ >> 4;
/* 220 */     double d0 = entityplayer.d - entityplayer.locX;
/* 221 */     double d1 = entityplayer.e - entityplayer.locZ;
/* 222 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 224 */     if (d2 >= 64.0D) {
/* 225 */       int k = (int)entityplayer.d >> 4;
/* 226 */       int l = (int)entityplayer.e >> 4;
/* 227 */       int i1 = this.g;
/* 228 */       int j1 = i - k;
/* 229 */       int k1 = j - l;
/* 230 */       List<ChunkCoordIntPair> chunksToLoad = new LinkedList();
/*     */       
/* 232 */       if ((j1 != 0) || (k1 != 0)) { int i2;
/* 233 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 234 */           for (i2 = j - i1; i2 <= j + i1; i2++) {
/* 235 */             if (!a(l1, i2, k, l, i1)) {
/* 236 */               chunksToLoad.add(new ChunkCoordIntPair(l1, i2));
/*     */             }
/*     */             
/* 239 */             if (!a(l1 - j1, i2 - k1, i, j, i1)) {
/* 240 */               PlayerChunk playerchunkmap_playerchunk = a(l1 - j1, i2 - k1, false);
/*     */               
/* 242 */               if (playerchunkmap_playerchunk != null) {
/* 243 */                 playerchunkmap_playerchunk.b(entityplayer);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 249 */         b(entityplayer);
/* 250 */         entityplayer.d = entityplayer.locX;
/* 251 */         entityplayer.e = entityplayer.locZ;
/*     */         
/*     */ 
/* 254 */         Collections.sort(chunksToLoad, new ChunkCoordComparator(entityplayer));
/* 255 */         for (ChunkCoordIntPair pair : chunksToLoad) {
/* 256 */           a(pair.x, pair.z, true).a(entityplayer);
/*     */         }
/*     */         
/* 259 */         if ((j1 > 1) || (j1 < -1) || (k1 > 1) || (k1 < -1)) {
/* 260 */           Collections.sort(entityplayer.chunkCoordIntPairQueue, new ChunkCoordComparator(entityplayer));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityPlayer entityplayer, int i, int j)
/*     */   {
/* 268 */     PlayerChunk playerchunkmap_playerchunk = a(i, j, false);
/*     */     
/* 270 */     return (playerchunkmap_playerchunk != null) && (playerchunkmap_playerchunk.b.contains(entityplayer)) && (!entityplayer.chunkCoordIntPairQueue.contains(playerchunkmap_playerchunk.location));
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 274 */     i = MathHelper.clamp(i, 3, 32);
/* 275 */     if (i != this.g) {
/* 276 */       int j = i - this.g;
/* 277 */       ArrayList arraylist = Lists.newArrayList(this.managedPlayers);
/* 278 */       Iterator iterator = arraylist.iterator();
/*     */       
/* 280 */       while (iterator.hasNext()) {
/* 281 */         EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/* 282 */         int k = (int)entityplayer.locX >> 4;
/* 283 */         int l = (int)entityplayer.locZ >> 4;
/*     */         
/*     */ 
/*     */ 
/* 287 */         if (j > 0) {
/* 288 */           for (int i1 = k - i; i1 <= k + i; i1++) {
/* 289 */             for (int j1 = l - i; j1 <= l + i; j1++) {
/* 290 */               PlayerChunk playerchunkmap_playerchunk = a(i1, j1, true);
/*     */               
/* 292 */               if (!playerchunkmap_playerchunk.b.contains(entityplayer)) {
/* 293 */                 playerchunkmap_playerchunk.a(entityplayer);
/*     */               }
/*     */             }
/*     */           }
/*     */         } else {
/* 298 */           for (int i1 = k - this.g; i1 <= k + this.g; i1++) {
/* 299 */             for (int j1 = l - this.g; j1 <= l + this.g; j1++) {
/* 300 */               if (!a(i1, j1, k, l, i)) {
/* 301 */                 a(i1, j1, true).b(entityplayer);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 308 */       this.g = i;
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getFurthestViewableBlock(int i) {
/* 313 */     return i * 16 - 16;
/*     */   }
/*     */   
/*     */   class PlayerChunk
/*     */   {
/* 318 */     private final List<EntityPlayer> b = Lists.newArrayList();
/*     */     private final ChunkCoordIntPair location;
/* 320 */     private short[] dirtyBlocks = new short[64];
/*     */     
/*     */     private int dirtyCount;
/*     */     
/*     */     private int f;
/*     */     private long g;
/* 326 */     private final HashMap<EntityPlayer, Runnable> players = new HashMap();
/* 327 */     private boolean loaded = false;
/* 328 */     private Runnable loadedRunnable = new Runnable() {
/*     */       public void run() {
/* 330 */         PlayerChunkMap.PlayerChunk.this.loaded = true;
/*     */       }
/*     */     };
/*     */     
/*     */     public PlayerChunk(int i, int j)
/*     */     {
/* 336 */       this.location = new ChunkCoordIntPair(i, j);
/* 337 */       PlayerChunkMap.this.a().chunkProviderServer.getChunkAt(i, j, this.loadedRunnable);
/*     */     }
/*     */     
/*     */     public void a(final EntityPlayer entityplayer) {
/* 341 */       if (this.b.contains(entityplayer)) {
/* 342 */         PlayerChunkMap.a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { entityplayer, Integer.valueOf(this.location.x), Integer.valueOf(this.location.z) });
/*     */       } else {
/* 344 */         if (this.b.isEmpty()) {
/* 345 */           this.g = PlayerChunkMap.this.world.getTime();
/*     */         }
/*     */         
/* 348 */         this.b.add(entityplayer);
/*     */         
/*     */         Runnable playerRunnable;
/* 351 */         if (this.loaded) {
/* 352 */           Runnable playerRunnable = null;
/* 353 */           entityplayer.chunkCoordIntPairQueue.add(this.location);
/*     */         } else {
/* 355 */           playerRunnable = new Runnable() {
/*     */             public void run() {
/* 357 */               entityplayer.chunkCoordIntPairQueue.add(PlayerChunkMap.PlayerChunk.this.location);
/*     */             }
/* 359 */           };
/* 360 */           PlayerChunkMap.this.a().chunkProviderServer.getChunkAt(this.location.x, this.location.z, playerRunnable);
/*     */         }
/*     */         
/* 363 */         this.players.put(entityplayer, playerRunnable);
/*     */       }
/*     */     }
/*     */     
/*     */     public void b(EntityPlayer entityplayer)
/*     */     {
/* 369 */       if (this.b.contains(entityplayer))
/*     */       {
/* 371 */         if (!this.loaded) {
/* 372 */           ChunkIOExecutor.dropQueuedChunkLoad(PlayerChunkMap.this.a(), this.location.x, this.location.z, (Runnable)this.players.get(entityplayer));
/* 373 */           this.b.remove(entityplayer);
/* 374 */           this.players.remove(entityplayer);
/*     */           
/* 376 */           if (this.b.isEmpty()) {
/* 377 */             ChunkIOExecutor.dropQueuedChunkLoad(PlayerChunkMap.this.a(), this.location.x, this.location.z, this.loadedRunnable);
/* 378 */             long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32;
/* 379 */             PlayerChunkMap.this.d.remove(i);
/* 380 */             PlayerChunkMap.this.f.remove(this);
/*     */           }
/*     */           
/* 383 */           return;
/*     */         }
/*     */         
/* 386 */         Chunk chunk = PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z);
/*     */         
/* 388 */         if (chunk.isReady()) {
/* 389 */           entityplayer.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0));
/*     */         }
/*     */         
/* 392 */         this.players.remove(entityplayer);
/* 393 */         this.b.remove(entityplayer);
/* 394 */         entityplayer.chunkCoordIntPairQueue.remove(this.location);
/* 395 */         if (this.b.isEmpty()) {
/* 396 */           long i = this.location.x + 2147483647L | this.location.z + 2147483647L << 32;
/*     */           
/* 398 */           a(chunk);
/* 399 */           PlayerChunkMap.this.d.remove(i);
/* 400 */           PlayerChunkMap.this.f.remove(this);
/* 401 */           if (this.dirtyCount > 0) {
/* 402 */             PlayerChunkMap.this.e.remove(this);
/*     */           }
/*     */           
/* 405 */           PlayerChunkMap.this.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void a()
/*     */     {
/* 412 */       a(PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z));
/*     */     }
/*     */     
/*     */     private void a(Chunk chunk) {
/* 416 */       chunk.c(chunk.w() + PlayerChunkMap.this.world.getTime() - this.g);
/* 417 */       this.g = PlayerChunkMap.this.world.getTime();
/*     */     }
/*     */     
/*     */     public void a(int i, int j, int k) {
/* 421 */       if (this.dirtyCount == 0) {
/* 422 */         PlayerChunkMap.this.e.add(this);
/*     */       }
/*     */       
/* 425 */       this.f |= 1 << (j >> 4);
/* 426 */       if (this.dirtyCount < 64) {
/* 427 */         short short0 = (short)(i << 12 | k << 8 | j);
/*     */         
/* 429 */         for (int l = 0; l < this.dirtyCount; l++) {
/* 430 */           if (this.dirtyBlocks[l] == short0) {
/* 431 */             return;
/*     */           }
/*     */         }
/*     */         
/* 435 */         this.dirtyBlocks[(this.dirtyCount++)] = short0;
/*     */       }
/*     */     }
/*     */     
/*     */     public void a(Packet packet)
/*     */     {
/* 441 */       for (int i = 0; i < this.b.size(); i++) {
/* 442 */         EntityPlayer entityplayer = (EntityPlayer)this.b.get(i);
/*     */         
/* 444 */         if (!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
/* 445 */           entityplayer.playerConnection.sendPacket(packet);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void b()
/*     */     {
/* 452 */       if (this.dirtyCount != 0)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 457 */         if (this.dirtyCount == 1) {
/* 458 */           int i = (this.dirtyBlocks[0] >> 12 & 0xF) + this.location.x * 16;
/* 459 */           int j = this.dirtyBlocks[0] & 0xFF;
/* 460 */           int k = (this.dirtyBlocks[0] >> 8 & 0xF) + this.location.z * 16;
/* 461 */           BlockPosition blockposition = new BlockPosition(i, j, k);
/*     */           
/* 463 */           a(new PacketPlayOutBlockChange(PlayerChunkMap.this.world, blockposition));
/* 464 */           if (PlayerChunkMap.this.world.getType(blockposition).getBlock().isTileEntity()) {
/* 465 */             a(PlayerChunkMap.this.world.getTileEntity(blockposition));
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 470 */         else if (this.dirtyCount == 64) {
/* 471 */           int i = this.location.x * 16;
/* 472 */           int j = this.location.z * 16;
/* 473 */           a(new PacketPlayOutMapChunk(PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z), false, this.f));
/*     */           
/* 475 */           for (int k = 0; k < 16; k++) {
/* 476 */             if ((this.f & 1 << k) != 0) {
/* 477 */               int l = k << 4;
/* 478 */               List list = PlayerChunkMap.this.world.getTileEntities(i, l, j, i + 16, l + 16, j + 16);
/*     */               
/* 480 */               for (int i1 = 0; i1 < list.size(); i1++) {
/* 481 */                 a((TileEntity)list.get(i1));
/*     */               }
/*     */             }
/*     */           }
/*     */         } else {
/* 486 */           a(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z)));
/*     */           
/* 488 */           for (int i = 0; i < this.dirtyCount; i++) {
/* 489 */             int j = (this.dirtyBlocks[i] >> 12 & 0xF) + this.location.x * 16;
/* 490 */             int k = this.dirtyBlocks[i] & 0xFF;
/* 491 */             int l = (this.dirtyBlocks[i] >> 8 & 0xF) + this.location.z * 16;
/* 492 */             BlockPosition blockposition1 = new BlockPosition(j, k, l);
/*     */             
/* 494 */             if (PlayerChunkMap.this.world.getType(blockposition1).getBlock().isTileEntity()) {
/* 495 */               a(PlayerChunkMap.this.world.getTileEntity(blockposition1));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 501 */         this.dirtyCount = 0;
/* 502 */         this.f = 0;
/*     */       }
/*     */     }
/*     */     
/*     */     private void a(TileEntity tileentity) {
/* 507 */       if (tileentity != null) {
/* 508 */         Packet packet = tileentity.getUpdatePacket();
/*     */         
/* 510 */         if (packet != null) {
/* 511 */           a(packet);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ChunkCoordComparator implements Comparator<ChunkCoordIntPair>
/*     */   {
/*     */     private int x;
/*     */     private int z;
/*     */     
/*     */     public ChunkCoordComparator(EntityPlayer entityplayer)
/*     */     {
/* 524 */       this.x = ((int)entityplayer.locX >> 4);
/* 525 */       this.z = ((int)entityplayer.locZ >> 4);
/*     */     }
/*     */     
/*     */     public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b) {
/* 529 */       if (a.equals(b)) {
/* 530 */         return 0;
/*     */       }
/*     */       
/*     */ 
/* 534 */       int ax = a.x - this.x;
/* 535 */       int az = a.z - this.z;
/* 536 */       int bx = b.x - this.x;
/* 537 */       int bz = b.z - this.z;
/*     */       
/* 539 */       int result = (ax - bx) * (ax + bx) + (az - bz) * (az + bz);
/* 540 */       if (result != 0) {
/* 541 */         return result;
/*     */       }
/*     */       
/* 544 */       if (ax < 0) {
/* 545 */         if (bx < 0) {
/* 546 */           return bz - az;
/*     */         }
/* 548 */         return -1;
/*     */       }
/*     */       
/* 551 */       if (bx < 0) {
/* 552 */         return 1;
/*     */       }
/* 554 */       return az - bz;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerChunkMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */