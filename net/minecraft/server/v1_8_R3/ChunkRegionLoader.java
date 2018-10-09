/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ public class ChunkRegionLoader implements IChunkLoader, IAsyncChunkSaver
/*     */ {
/*  19 */   private static final Logger a = ;
/*  20 */   private Map<ChunkCoordIntPair, NBTTagCompound> b = new ConcurrentHashMap();
/*  21 */   private Set<ChunkCoordIntPair> c = java.util.Collections.newSetFromMap(new ConcurrentHashMap());
/*     */   private final File d;
/*  23 */   private boolean e = false;
/*     */   
/*     */   public ChunkRegionLoader(File file) {
/*  26 */     this.d = file;
/*     */   }
/*     */   
/*     */   public boolean chunkExists(World world, int i, int j)
/*     */   {
/*  31 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/*     */     
/*  33 */     if ((this.c.contains(chunkcoordintpair)) && 
/*  34 */       (this.b.containsKey(chunkcoordintpair))) {
/*  35 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  39 */     return RegionFileCache.a(this.d, i, j).chunkExists(i & 0x1F, j & 0x1F);
/*     */   }
/*     */   
/*     */   public Chunk a(World world, int i, int j)
/*     */     throws IOException
/*     */   {
/*  45 */     world.timings.syncChunkLoadDataTimer.startTiming();
/*  46 */     Object[] data = loadChunk(world, i, j);
/*  47 */     world.timings.syncChunkLoadDataTimer.stopTiming();
/*  48 */     if (data != null) {
/*  49 */       Chunk chunk = (Chunk)data[0];
/*  50 */       NBTTagCompound nbttagcompound = (NBTTagCompound)data[1];
/*  51 */       loadEntities(chunk, nbttagcompound.getCompound("Level"), world);
/*  52 */       return chunk;
/*     */     }
/*     */     
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   public Object[] loadChunk(World world, int i, int j) throws IOException
/*     */   {
/*  60 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
/*  61 */     NBTTagCompound nbttagcompound = (NBTTagCompound)this.b.get(chunkcoordintpair);
/*     */     
/*  63 */     if (nbttagcompound == null) {
/*  64 */       java.io.DataInputStream datainputstream = RegionFileCache.c(this.d, i, j);
/*     */       
/*  66 */       if (datainputstream == null) {
/*  67 */         return null;
/*     */       }
/*     */       
/*  70 */       nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
/*     */     }
/*     */     
/*  73 */     return a(world, i, j, nbttagcompound);
/*     */   }
/*     */   
/*     */   protected Object[] a(World world, int i, int j, NBTTagCompound nbttagcompound) {
/*  77 */     if (!nbttagcompound.hasKeyOfType("Level", 10)) {
/*  78 */       a.error("Chunk file at " + i + "," + j + " is missing level data, skipping");
/*  79 */       return null;
/*     */     }
/*  81 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Level");
/*     */     
/*  83 */     if (!nbttagcompound1.hasKeyOfType("Sections", 9)) {
/*  84 */       a.error("Chunk file at " + i + "," + j + " is missing block data, skipping");
/*  85 */       return null;
/*     */     }
/*  87 */     Chunk chunk = a(world, nbttagcompound1);
/*     */     
/*  89 */     if (!chunk.a(i, j)) {
/*  90 */       a.error("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.locX + ", " + chunk.locZ + ")");
/*  91 */       nbttagcompound1.setInt("xPos", i);
/*  92 */       nbttagcompound1.setInt("zPos", j);
/*     */       
/*     */ 
/*  95 */       NBTTagList tileEntities = nbttagcompound.getCompound("Level").getList("TileEntities", 10);
/*  96 */       if (tileEntities != null) {
/*  97 */         for (int te = 0; te < tileEntities.size(); te++) {
/*  98 */           NBTTagCompound tileEntity = tileEntities.get(te);
/*  99 */           int x = tileEntity.getInt("x") - chunk.locX * 16;
/* 100 */           int z = tileEntity.getInt("z") - chunk.locZ * 16;
/* 101 */           tileEntity.setInt("x", i * 16 + x);
/* 102 */           tileEntity.setInt("z", j * 16 + z);
/*     */         }
/*     */       }
/*     */       
/* 106 */       chunk = a(world, nbttagcompound1);
/*     */     }
/*     */     
/*     */ 
/* 110 */     Object[] data = new Object[2];
/* 111 */     data[0] = chunk;
/* 112 */     data[1] = nbttagcompound;
/* 113 */     return data;
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(World world, Chunk chunk)
/*     */     throws IOException, ExceptionWorldConflict
/*     */   {
/* 120 */     world.checkSession();
/*     */     try
/*     */     {
/* 123 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 124 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 126 */       nbttagcompound.set("Level", nbttagcompound1);
/* 127 */       a(chunk, world, nbttagcompound1);
/* 128 */       a(chunk.j(), nbttagcompound);
/*     */     } catch (Exception exception) {
/* 130 */       a.error("Failed to save chunk", exception);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound)
/*     */   {
/* 136 */     if (!this.c.contains(chunkcoordintpair)) {
/* 137 */       this.b.put(chunkcoordintpair, nbttagcompound);
/*     */     }
/*     */     
/* 140 */     FileIOThread.a().a(this);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean c()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 45	net/minecraft/server/v1_8_R3/ChunkRegionLoader:b	Ljava/util/Map;
/*     */     //   4: invokeinterface 295 1 0
/*     */     //   9: ifeq +37 -> 46
/*     */     //   12: aload_0
/*     */     //   13: getfield 55	net/minecraft/server/v1_8_R3/ChunkRegionLoader:e	Z
/*     */     //   16: ifeq +28 -> 44
/*     */     //   19: getstatic 36	net/minecraft/server/v1_8_R3/ChunkRegionLoader:a	Lorg/apache/logging/log4j/Logger;
/*     */     //   22: ldc_w 297
/*     */     //   25: iconst_1
/*     */     //   26: anewarray 4	java/lang/Object
/*     */     //   29: dup
/*     */     //   30: iconst_0
/*     */     //   31: aload_0
/*     */     //   32: getfield 57	net/minecraft/server/v1_8_R3/ChunkRegionLoader:d	Ljava/io/File;
/*     */     //   35: invokevirtual 302	java/io/File:getName	()Ljava/lang/String;
/*     */     //   38: aastore
/*     */     //   39: invokeinterface 306 3 0
/*     */     //   44: iconst_0
/*     */     //   45: ireturn
/*     */     //   46: aload_0
/*     */     //   47: getfield 45	net/minecraft/server/v1_8_R3/ChunkRegionLoader:b	Ljava/util/Map;
/*     */     //   50: invokeinterface 310 1 0
/*     */     //   55: invokeinterface 314 1 0
/*     */     //   60: invokeinterface 320 1 0
/*     */     //   65: checkcast 64	net/minecraft/server/v1_8_R3/ChunkCoordIntPair
/*     */     //   68: astore_1
/*     */     //   69: aload_0
/*     */     //   70: getfield 53	net/minecraft/server/v1_8_R3/ChunkRegionLoader:c	Ljava/util/Set;
/*     */     //   73: aload_1
/*     */     //   74: invokeinterface 323 2 0
/*     */     //   79: pop
/*     */     //   80: aload_0
/*     */     //   81: getfield 45	net/minecraft/server/v1_8_R3/ChunkRegionLoader:b	Ljava/util/Map;
/*     */     //   84: aload_1
/*     */     //   85: invokeinterface 326 2 0
/*     */     //   90: checkcast 124	net/minecraft/server/v1_8_R3/NBTTagCompound
/*     */     //   93: astore_2
/*     */     //   94: aload_2
/*     */     //   95: ifnull +25 -> 120
/*     */     //   98: aload_0
/*     */     //   99: aload_1
/*     */     //   100: aload_2
/*     */     //   101: invokespecial 328	net/minecraft/server/v1_8_R3/ChunkRegionLoader:b	(Lnet/minecraft/server/v1_8_R3/ChunkCoordIntPair;Lnet/minecraft/server/v1_8_R3/NBTTagCompound;)V
/*     */     //   104: goto +16 -> 120
/*     */     //   107: astore_3
/*     */     //   108: getstatic 36	net/minecraft/server/v1_8_R3/ChunkRegionLoader:a	Lorg/apache/logging/log4j/Logger;
/*     */     //   111: ldc_w 274
/*     */     //   114: aload_3
/*     */     //   115: invokeinterface 277 3 0
/*     */     //   120: iconst_1
/*     */     //   121: istore 4
/*     */     //   123: goto +19 -> 142
/*     */     //   126: astore 5
/*     */     //   128: aload_0
/*     */     //   129: getfield 53	net/minecraft/server/v1_8_R3/ChunkRegionLoader:c	Ljava/util/Set;
/*     */     //   132: aload_1
/*     */     //   133: invokeinterface 332 2 0
/*     */     //   138: pop
/*     */     //   139: aload 5
/*     */     //   141: athrow
/*     */     //   142: aload_0
/*     */     //   143: getfield 53	net/minecraft/server/v1_8_R3/ChunkRegionLoader:c	Ljava/util/Set;
/*     */     //   146: aload_1
/*     */     //   147: invokeinterface 332 2 0
/*     */     //   152: pop
/*     */     //   153: iload 4
/*     */     //   155: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #144	-> byte code offset #0
/*     */     //   Java source line #145	-> byte code offset #12
/*     */     //   Java source line #146	-> byte code offset #19
/*     */     //   Java source line #149	-> byte code offset #44
/*     */     //   Java source line #151	-> byte code offset #46
/*     */     //   Java source line #156	-> byte code offset #69
/*     */     //   Java source line #157	-> byte code offset #80
/*     */     //   Java source line #159	-> byte code offset #94
/*     */     //   Java source line #161	-> byte code offset #98
/*     */     //   Java source line #162	-> byte code offset #104
/*     */     //   Java source line #163	-> byte code offset #108
/*     */     //   Java source line #167	-> byte code offset #120
/*     */     //   Java source line #168	-> byte code offset #123
/*     */     //   Java source line #169	-> byte code offset #128
/*     */     //   Java source line #170	-> byte code offset #139
/*     */     //   Java source line #169	-> byte code offset #142
/*     */     //   Java source line #172	-> byte code offset #153
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	156	0	this	ChunkRegionLoader
/*     */     //   68	79	1	chunkcoordintpair	ChunkCoordIntPair
/*     */     //   93	8	2	nbttagcompound	NBTTagCompound
/*     */     //   107	8	3	exception	Exception
/*     */     //   121	3	4	flag	boolean
/*     */     //   142	12	4	flag	boolean
/*     */     //   126	14	5	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   98	104	107	java/lang/Exception
/*     */     //   69	126	126	finally
/*     */   }
/*     */   
/*     */   private void b(ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound)
/*     */     throws IOException
/*     */   {
/* 177 */     DataOutputStream dataoutputstream = RegionFileCache.d(this.d, chunkcoordintpair.x, chunkcoordintpair.z);
/*     */     
/* 179 */     NBTCompressedStreamTools.a(nbttagcompound, dataoutputstream);
/* 180 */     dataoutputstream.close();
/*     */   }
/*     */   
/*     */   public void b(World world, Chunk chunk)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   public void a() {}
/*     */   
/*     */   /* Error */
/*     */   public void b()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: iconst_1
/*     */     //   2: putfield 55	net/minecraft/server/v1_8_R3/ChunkRegionLoader:e	Z
/*     */     //   5: aload_0
/*     */     //   6: invokevirtual 352	net/minecraft/server/v1_8_R3/ChunkRegionLoader:c	()Z
/*     */     //   9: ifeq -4 -> 5
/*     */     //   12: goto -7 -> 5
/*     */     //   15: astore_1
/*     */     //   16: aload_0
/*     */     //   17: iconst_0
/*     */     //   18: putfield 55	net/minecraft/server/v1_8_R3/ChunkRegionLoader:e	Z
/*     */     //   21: aload_1
/*     */     //   22: athrow
/*     */     // Line number table:
/*     */     //   Java source line #189	-> byte code offset #0
/*     */     //   Java source line #192	-> byte code offset #5
/*     */     //   Java source line #191	-> byte code offset #12
/*     */     //   Java source line #196	-> byte code offset #15
/*     */     //   Java source line #197	-> byte code offset #16
/*     */     //   Java source line #198	-> byte code offset #21
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	23	0	this	ChunkRegionLoader
/*     */     //   15	7	1	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	15	15	finally
/*     */   }
/*     */   
/*     */   private void a(Chunk chunk, World world, NBTTagCompound nbttagcompound)
/*     */   {
/* 203 */     nbttagcompound.setByte("V", (byte)1);
/* 204 */     nbttagcompound.setInt("xPos", chunk.locX);
/* 205 */     nbttagcompound.setInt("zPos", chunk.locZ);
/* 206 */     nbttagcompound.setLong("LastUpdate", world.getTime());
/* 207 */     nbttagcompound.setIntArray("HeightMap", chunk.q());
/* 208 */     nbttagcompound.setBoolean("TerrainPopulated", chunk.isDone());
/* 209 */     nbttagcompound.setBoolean("LightPopulated", chunk.u());
/* 210 */     nbttagcompound.setLong("InhabitedTime", chunk.w());
/* 211 */     ChunkSection[] achunksection = chunk.getSections();
/* 212 */     NBTTagList nbttaglist = new NBTTagList();
/* 213 */     boolean flag = !world.worldProvider.o();
/* 214 */     ChunkSection[] achunksection1 = achunksection;
/* 215 */     int i = achunksection.length;
/*     */     
/*     */ 
/*     */ 
/* 219 */     for (int j = 0; j < i; j++) {
/* 220 */       ChunkSection chunksection = achunksection1[j];
/*     */       
/* 222 */       if (chunksection != null) {
/* 223 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 224 */         nbttagcompound1.setByte("Y", (byte)(chunksection.getYPosition() >> 4 & 0xFF));
/* 225 */         byte[] abyte = new byte[chunksection.getIdArray().length];
/* 226 */         NibbleArray nibblearray = new NibbleArray();
/* 227 */         NibbleArray nibblearray1 = null;
/*     */         
/* 229 */         for (int k = 0; k < chunksection.getIdArray().length; k++) {
/* 230 */           char c0 = chunksection.getIdArray()[k];
/* 231 */           int l = k & 0xF;
/* 232 */           int i1 = k >> 8 & 0xF;
/* 233 */           int j1 = k >> 4 & 0xF;
/*     */           
/* 235 */           if (c0 >> '\f' != 0) {
/* 236 */             if (nibblearray1 == null) {
/* 237 */               nibblearray1 = new NibbleArray();
/*     */             }
/*     */             
/* 240 */             nibblearray1.a(l, i1, j1, c0 >> '\f');
/*     */           }
/*     */           
/* 243 */           abyte[k] = ((byte)(c0 >> '\004' & 0xFF));
/* 244 */           nibblearray.a(l, i1, j1, c0 & 0xF);
/*     */         }
/*     */         
/* 247 */         nbttagcompound1.setByteArray("Blocks", abyte);
/* 248 */         nbttagcompound1.setByteArray("Data", nibblearray.a());
/* 249 */         if (nibblearray1 != null) {
/* 250 */           nbttagcompound1.setByteArray("Add", nibblearray1.a());
/*     */         }
/*     */         
/* 253 */         nbttagcompound1.setByteArray("BlockLight", chunksection.getEmittedLightArray().a());
/* 254 */         if (flag) {
/* 255 */           nbttagcompound1.setByteArray("SkyLight", chunksection.getSkyLightArray().a());
/*     */         } else {
/* 257 */           nbttagcompound1.setByteArray("SkyLight", new byte[chunksection.getEmittedLightArray().a().length]);
/*     */         }
/*     */         
/* 260 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 264 */     nbttagcompound.set("Sections", nbttaglist);
/* 265 */     nbttagcompound.setByteArray("Biomes", chunk.getBiomeIndex());
/* 266 */     chunk.g(false);
/* 267 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/*     */ 
/*     */ 
/* 271 */     for (i = 0; i < chunk.getEntitySlices().length; i++) {
/* 272 */       Iterator iterator = chunk.getEntitySlices()[i].iterator();
/*     */       
/* 274 */       while (iterator.hasNext()) {
/* 275 */         Entity entity = (Entity)iterator.next();
/*     */         
/* 277 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 278 */         if (entity.d(nbttagcompound1)) {
/* 279 */           chunk.g(true);
/* 280 */           nbttaglist1.add(nbttagcompound1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 285 */     nbttagcompound.set("Entities", nbttaglist1);
/* 286 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 288 */     Iterator iterator = chunk.getTileEntities().values().iterator();
/*     */     
/* 290 */     while (iterator.hasNext()) {
/* 291 */       TileEntity tileentity = (TileEntity)iterator.next();
/*     */       
/* 293 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 294 */       tileentity.b(nbttagcompound1);
/* 295 */       nbttaglist2.add(nbttagcompound1);
/*     */     }
/*     */     
/* 298 */     nbttagcompound.set("TileEntities", nbttaglist2);
/* 299 */     List list = world.a(chunk, false);
/*     */     
/* 301 */     if (list != null) {
/* 302 */       long k1 = world.getTime();
/* 303 */       NBTTagList nbttaglist3 = new NBTTagList();
/* 304 */       Iterator iterator1 = list.iterator();
/*     */       
/* 306 */       while (iterator1.hasNext()) {
/* 307 */         NextTickListEntry nextticklistentry = (NextTickListEntry)iterator1.next();
/* 308 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 309 */         MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(nextticklistentry.a());
/*     */         
/* 311 */         nbttagcompound2.setString("i", minecraftkey == null ? "" : minecraftkey.toString());
/* 312 */         nbttagcompound2.setInt("x", nextticklistentry.a.getX());
/* 313 */         nbttagcompound2.setInt("y", nextticklistentry.a.getY());
/* 314 */         nbttagcompound2.setInt("z", nextticklistentry.a.getZ());
/* 315 */         nbttagcompound2.setInt("t", (int)(nextticklistentry.b - k1));
/* 316 */         nbttagcompound2.setInt("p", nextticklistentry.c);
/* 317 */         nbttaglist3.add(nbttagcompound2);
/*     */       }
/*     */       
/* 320 */       nbttagcompound.set("TileTicks", nbttaglist3);
/*     */     }
/*     */   }
/*     */   
/*     */   private Chunk a(World world, NBTTagCompound nbttagcompound)
/*     */   {
/* 326 */     int i = nbttagcompound.getInt("xPos");
/* 327 */     int j = nbttagcompound.getInt("zPos");
/* 328 */     Chunk chunk = new Chunk(world, i, j);
/*     */     
/* 330 */     chunk.a(nbttagcompound.getIntArray("HeightMap"));
/* 331 */     chunk.d(nbttagcompound.getBoolean("TerrainPopulated"));
/* 332 */     chunk.e(nbttagcompound.getBoolean("LightPopulated"));
/* 333 */     chunk.c(nbttagcompound.getLong("InhabitedTime"));
/* 334 */     NBTTagList nbttaglist = nbttagcompound.getList("Sections", 10);
/* 335 */     byte b0 = 16;
/* 336 */     ChunkSection[] achunksection = new ChunkSection[b0];
/* 337 */     boolean flag = !world.worldProvider.o();
/*     */     
/* 339 */     for (int k = 0; k < nbttaglist.size(); k++) {
/* 340 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(k);
/* 341 */       byte b1 = nbttagcompound1.getByte("Y");
/* 342 */       ChunkSection chunksection = new ChunkSection(b1 << 4, flag);
/* 343 */       byte[] abyte = nbttagcompound1.getByteArray("Blocks");
/* 344 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound1.getByteArray("Data"));
/* 345 */       NibbleArray nibblearray1 = nbttagcompound1.hasKeyOfType("Add", 7) ? new NibbleArray(nbttagcompound1.getByteArray("Add")) : null;
/* 346 */       char[] achar = new char[abyte.length];
/*     */       
/* 348 */       for (int l = 0; l < achar.length; l++) {
/* 349 */         int i1 = l & 0xF;
/* 350 */         int j1 = l >> 8 & 0xF;
/* 351 */         int k1 = l >> 4 & 0xF;
/* 352 */         int l1 = nibblearray1 != null ? nibblearray1.a(i1, j1, k1) : 0;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 357 */         int ex = l1;
/* 358 */         int id = abyte[l] & 0xFF;
/* 359 */         int data = nibblearray.a(i1, j1, k1);
/* 360 */         int packed = ex << 12 | id << 4 | data;
/* 361 */         if (Block.d.a(packed) == null) {
/* 362 */           Block block = Block.getById(ex << 8 | id);
/* 363 */           if (block != null) {
/*     */             try {
/* 365 */               data = block.toLegacyData(block.fromLegacyData(data));
/*     */             } catch (Exception localException) {
/* 367 */               data = block.toLegacyData(block.getBlockData());
/*     */             }
/* 369 */             packed = ex << 12 | id << 4 | data;
/*     */           }
/*     */         }
/* 372 */         achar[l] = ((char)packed);
/*     */       }
/*     */       
/*     */ 
/* 376 */       chunksection.a(achar);
/* 377 */       chunksection.a(new NibbleArray(nbttagcompound1.getByteArray("BlockLight")));
/* 378 */       if (flag) {
/* 379 */         chunksection.b(new NibbleArray(nbttagcompound1.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 382 */       chunksection.recalcBlockCounts();
/* 383 */       achunksection[b1] = chunksection;
/*     */     }
/*     */     
/* 386 */     chunk.a(achunksection);
/* 387 */     if (nbttagcompound.hasKeyOfType("Biomes", 7)) {
/* 388 */       chunk.a(nbttagcompound.getByteArray("Biomes"));
/*     */     }
/*     */     
/*     */ 
/* 392 */     return chunk;
/*     */   }
/*     */   
/*     */   public void loadEntities(Chunk chunk, NBTTagCompound nbttagcompound, World world)
/*     */   {
/* 397 */     world.timings.syncChunkLoadEntitiesTimer.startTiming();
/* 398 */     NBTTagList nbttaglist1 = nbttagcompound.getList("Entities", 10);
/*     */     
/* 400 */     if (nbttaglist1 != null) {
/* 401 */       for (int i2 = 0; i2 < nbttaglist1.size(); i2++) {
/* 402 */         NBTTagCompound nbttagcompound2 = nbttaglist1.get(i2);
/* 403 */         Entity entity = EntityTypes.a(nbttagcompound2, world);
/*     */         
/* 405 */         chunk.g(true);
/* 406 */         if (entity != null) {
/* 407 */           chunk.a(entity);
/* 408 */           Entity entity1 = entity;
/*     */           
/* 410 */           for (NBTTagCompound nbttagcompound3 = nbttagcompound2; nbttagcompound3.hasKeyOfType("Riding", 10); nbttagcompound3 = nbttagcompound3.getCompound("Riding")) {
/* 411 */             Entity entity2 = EntityTypes.a(nbttagcompound3.getCompound("Riding"), world);
/*     */             
/* 413 */             if (entity2 != null) {
/* 414 */               chunk.a(entity2);
/* 415 */               entity1.mount(entity2);
/*     */             }
/*     */             
/* 418 */             entity1 = entity2;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 423 */     world.timings.syncChunkLoadEntitiesTimer.stopTiming();
/* 424 */     world.timings.syncChunkLoadTileEntitiesTimer.startTiming();
/* 425 */     NBTTagList nbttaglist2 = nbttagcompound.getList("TileEntities", 10);
/*     */     
/* 427 */     if (nbttaglist2 != null) {
/* 428 */       for (int j2 = 0; j2 < nbttaglist2.size(); j2++) {
/* 429 */         NBTTagCompound nbttagcompound4 = nbttaglist2.get(j2);
/* 430 */         TileEntity tileentity = TileEntity.c(nbttagcompound4);
/*     */         
/* 432 */         if (tileentity != null) {
/* 433 */           chunk.a(tileentity);
/*     */         }
/*     */       }
/*     */     }
/* 437 */     world.timings.syncChunkLoadTileEntitiesTimer.stopTiming();
/* 438 */     world.timings.syncChunkLoadTileTicksTimer.startTiming();
/*     */     
/* 440 */     if (nbttagcompound.hasKeyOfType("TileTicks", 9)) {
/* 441 */       NBTTagList nbttaglist3 = nbttagcompound.getList("TileTicks", 10);
/*     */       
/* 443 */       if (nbttaglist3 != null) {
/* 444 */         for (int k2 = 0; k2 < nbttaglist3.size(); k2++) {
/* 445 */           NBTTagCompound nbttagcompound5 = nbttaglist3.get(k2);
/*     */           Block block;
/*     */           Block block;
/* 448 */           if (nbttagcompound5.hasKeyOfType("i", 8)) {
/* 449 */             block = Block.getByName(nbttagcompound5.getString("i"));
/*     */           } else {
/* 451 */             block = Block.getById(nbttagcompound5.getInt("i"));
/*     */           }
/*     */           
/* 454 */           world.b(new BlockPosition(nbttagcompound5.getInt("x"), nbttagcompound5.getInt("y"), nbttagcompound5.getInt("z")), block, nbttagcompound5.getInt("t"), nbttagcompound5.getInt("p"));
/*     */         }
/*     */       }
/*     */     }
/* 458 */     world.timings.syncChunkLoadTileTicksTimer.stopTiming();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkRegionLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */