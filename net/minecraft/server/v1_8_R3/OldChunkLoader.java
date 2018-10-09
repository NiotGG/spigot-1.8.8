/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class OldChunkLoader
/*     */ {
/*     */   public static OldChunk a(NBTTagCompound nbttagcompound) {
/*   6 */     int i = nbttagcompound.getInt("xPos");
/*   7 */     int j = nbttagcompound.getInt("zPos");
/*   8 */     OldChunk oldchunkloader_oldchunk = new OldChunk(i, j);
/*     */     
/*  10 */     oldchunkloader_oldchunk.g = nbttagcompound.getByteArray("Blocks");
/*  11 */     oldchunkloader_oldchunk.f = new OldNibbleArray(nbttagcompound.getByteArray("Data"), 7);
/*  12 */     oldchunkloader_oldchunk.e = new OldNibbleArray(nbttagcompound.getByteArray("SkyLight"), 7);
/*  13 */     oldchunkloader_oldchunk.d = new OldNibbleArray(nbttagcompound.getByteArray("BlockLight"), 7);
/*  14 */     oldchunkloader_oldchunk.c = nbttagcompound.getByteArray("HeightMap");
/*  15 */     oldchunkloader_oldchunk.b = nbttagcompound.getBoolean("TerrainPopulated");
/*  16 */     oldchunkloader_oldchunk.h = nbttagcompound.getList("Entities", 10);
/*  17 */     oldchunkloader_oldchunk.i = nbttagcompound.getList("TileEntities", 10);
/*  18 */     oldchunkloader_oldchunk.j = nbttagcompound.getList("TileTicks", 10);
/*     */     try
/*     */     {
/*  21 */       oldchunkloader_oldchunk.a = nbttagcompound.getLong("LastUpdate");
/*     */     } catch (ClassCastException localClassCastException) {
/*  23 */       oldchunkloader_oldchunk.a = nbttagcompound.getInt("LastUpdate");
/*     */     }
/*     */     
/*  26 */     return oldchunkloader_oldchunk;
/*     */   }
/*     */   
/*     */   public static void a(OldChunk oldchunkloader_oldchunk, NBTTagCompound nbttagcompound, WorldChunkManager worldchunkmanager) {
/*  30 */     nbttagcompound.setInt("xPos", oldchunkloader_oldchunk.k);
/*  31 */     nbttagcompound.setInt("zPos", oldchunkloader_oldchunk.l);
/*  32 */     nbttagcompound.setLong("LastUpdate", oldchunkloader_oldchunk.a);
/*  33 */     int[] aint = new int[oldchunkloader_oldchunk.c.length];
/*     */     
/*  35 */     for (int i = 0; i < oldchunkloader_oldchunk.c.length; i++) {
/*  36 */       aint[i] = oldchunkloader_oldchunk.c[i];
/*     */     }
/*     */     
/*  39 */     nbttagcompound.setIntArray("HeightMap", aint);
/*  40 */     nbttagcompound.setBoolean("TerrainPopulated", oldchunkloader_oldchunk.b);
/*  41 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  46 */     for (int l = 0; l < 8; l++) {
/*  47 */       boolean flag = true;
/*     */       
/*  49 */       for (int j = 0; (j < 16) && (flag); j++) {
/*  50 */         int k = 0;
/*     */         
/*  52 */         while ((k < 16) && (flag)) {
/*  53 */           int i1 = 0;
/*     */           
/*     */ 
/*  56 */           while (i1 < 16) {
/*  57 */             int j1 = j << 11 | i1 << 7 | k + (l << 4);
/*  58 */             byte b0 = oldchunkloader_oldchunk.g[j1];
/*     */             
/*  60 */             if (b0 == 0) {
/*  61 */               i1++;
/*     */             }
/*     */             else
/*     */             {
/*  65 */               flag = false;
/*     */             }
/*     */           }
/*  68 */           k++;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  74 */       if (!flag) {
/*  75 */         byte[] abyte = new byte['က'];
/*  76 */         NibbleArray nibblearray = new NibbleArray();
/*  77 */         NibbleArray nibblearray1 = new NibbleArray();
/*  78 */         NibbleArray nibblearray2 = new NibbleArray();
/*     */         
/*  80 */         for (int k1 = 0; k1 < 16; k1++) {
/*  81 */           for (int l1 = 0; l1 < 16; l1++) {
/*  82 */             for (int i2 = 0; i2 < 16; i2++) {
/*  83 */               int j2 = k1 << 11 | i2 << 7 | l1 + (l << 4);
/*  84 */               byte b1 = oldchunkloader_oldchunk.g[j2];
/*     */               
/*  86 */               abyte[(l1 << 8 | i2 << 4 | k1)] = ((byte)(b1 & 0xFF));
/*  87 */               nibblearray.a(k1, l1, i2, oldchunkloader_oldchunk.f.a(k1, l1 + (l << 4), i2));
/*  88 */               nibblearray1.a(k1, l1, i2, oldchunkloader_oldchunk.e.a(k1, l1 + (l << 4), i2));
/*  89 */               nibblearray2.a(k1, l1, i2, oldchunkloader_oldchunk.d.a(k1, l1 + (l << 4), i2));
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  94 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  96 */         nbttagcompound1.setByte("Y", (byte)(l & 0xFF));
/*  97 */         nbttagcompound1.setByteArray("Blocks", abyte);
/*  98 */         nbttagcompound1.setByteArray("Data", nibblearray.a());
/*  99 */         nbttagcompound1.setByteArray("SkyLight", nibblearray1.a());
/* 100 */         nbttagcompound1.setByteArray("BlockLight", nibblearray2.a());
/* 101 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 105 */     nbttagcompound.set("Sections", nbttaglist);
/* 106 */     byte[] abyte1 = new byte['Ā'];
/* 107 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */     
/* 109 */     for (int j = 0; j < 16; j++) {
/* 110 */       for (int k = 0; k < 16; k++) {
/* 111 */         blockposition_mutableblockposition.c(oldchunkloader_oldchunk.k << 4 | j, 0, oldchunkloader_oldchunk.l << 4 | k);
/* 112 */         abyte1[(k << 4 | j)] = ((byte)(worldchunkmanager.getBiome(blockposition_mutableblockposition, BiomeBase.ad).id & 0xFF));
/*     */       }
/*     */     }
/*     */     
/* 116 */     nbttagcompound.setByteArray("Biomes", abyte1);
/* 117 */     nbttagcompound.set("Entities", oldchunkloader_oldchunk.h);
/* 118 */     nbttagcompound.set("TileEntities", oldchunkloader_oldchunk.i);
/* 119 */     if (oldchunkloader_oldchunk.j != null) {
/* 120 */       nbttagcompound.set("TileTicks", oldchunkloader_oldchunk.j);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OldChunk
/*     */   {
/*     */     public long a;
/*     */     public boolean b;
/*     */     public byte[] c;
/*     */     public OldNibbleArray d;
/*     */     public OldNibbleArray e;
/*     */     public OldNibbleArray f;
/*     */     public byte[] g;
/*     */     public NBTTagList h;
/*     */     public NBTTagList i;
/*     */     public NBTTagList j;
/*     */     public final int k;
/*     */     public final int l;
/*     */     
/*     */     public OldChunk(int i, int j)
/*     */     {
/* 141 */       this.k = i;
/* 142 */       this.l = j;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\OldChunkLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */