/*     */ package org.bukkit.craftbukkit.v1_8_R3.generator;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.generator.ChunkGenerator.ChunkData;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CraftChunkData
/*     */   implements ChunkGenerator.ChunkData
/*     */ {
/*     */   private final int maxHeight;
/*     */   private final char[][] sections;
/*     */   
/*     */   public CraftChunkData(World world)
/*     */   {
/*  21 */     this(world.getMaxHeight());
/*     */   }
/*     */   
/*     */   CraftChunkData(int maxHeight) {
/*  25 */     if (maxHeight > 256) {
/*  26 */       throw new IllegalArgumentException("World height exceeded max chunk height");
/*     */     }
/*  28 */     this.maxHeight = maxHeight;
/*     */     
/*  30 */     this.sections = new char[16][];
/*     */   }
/*     */   
/*     */   public int getMaxHeight()
/*     */   {
/*  35 */     return this.maxHeight;
/*     */   }
/*     */   
/*     */   public void setBlock(int x, int y, int z, Material material)
/*     */   {
/*  40 */     setBlock(x, y, z, material.getId());
/*     */   }
/*     */   
/*     */   public void setBlock(int x, int y, int z, MaterialData material)
/*     */   {
/*  45 */     setBlock(x, y, z, material.getItemTypeId(), material.getData());
/*     */   }
/*     */   
/*     */   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Material material)
/*     */   {
/*  50 */     setRegion(xMin, yMin, zMin, xMax, yMax, zMax, material.getId());
/*     */   }
/*     */   
/*     */   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, MaterialData material)
/*     */   {
/*  55 */     setRegion(xMin, yMin, zMin, xMax, yMax, zMax, material.getItemTypeId(), material.getData());
/*     */   }
/*     */   
/*     */   public Material getType(int x, int y, int z)
/*     */   {
/*  60 */     return Material.getMaterial(getTypeId(x, y, z));
/*     */   }
/*     */   
/*     */   public MaterialData getTypeAndData(int x, int y, int z)
/*     */   {
/*  65 */     return getType(x, y, z).getNewData(getData(x, y, z));
/*     */   }
/*     */   
/*     */   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, int blockId)
/*     */   {
/*  70 */     setRegion(xMin, yMin, zMin, xMax, yMax, zMax, blockId, 0);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, int blockId, int data)
/*     */   {
/*  76 */     if ((xMin > 15) || (yMin >= this.maxHeight) || (zMin > 15)) {
/*  77 */       return;
/*     */     }
/*  79 */     if (xMin < 0) {
/*  80 */       xMin = 0;
/*     */     }
/*  82 */     if (yMin < 0) {
/*  83 */       yMin = 0;
/*     */     }
/*  85 */     if (zMin < 0) {
/*  86 */       zMin = 0;
/*     */     }
/*  88 */     if (xMax > 16) {
/*  89 */       xMax = 16;
/*     */     }
/*  91 */     if (yMax > this.maxHeight) {
/*  92 */       yMax = this.maxHeight;
/*     */     }
/*  94 */     if (zMax > 16) {
/*  95 */       zMax = 16;
/*     */     }
/*  97 */     if ((xMin >= xMax) || (yMin >= yMax) || (zMin >= zMax)) {
/*  98 */       return;
/*     */     }
/* 100 */     char typeChar = (char)(blockId << 4 | data);
/* 101 */     if ((xMin == 0) && (xMax == 16)) {
/* 102 */       if ((zMin == 0) && (zMax == 16)) {
/* 103 */         for (int y = yMin & 0xF0; y < yMax; y += 16) {
/* 104 */           char[] section = getChunkSection(y, true);
/* 105 */           if (y <= yMin) {
/* 106 */             if (y + 16 > yMax)
/*     */             {
/* 108 */               Arrays.fill(section, (yMin & 0xF) << 8, (yMax & 0xF) << 8, typeChar);
/*     */             }
/*     */             else {
/* 111 */               Arrays.fill(section, (yMin & 0xF) << 8, 4096, typeChar);
/*     */             }
/* 113 */           } else if (y + 16 >= yMax)
/*     */           {
/* 115 */             Arrays.fill(section, 0, (yMax & 0xF) << 8, typeChar);
/*     */           }
/*     */           else {
/* 118 */             Arrays.fill(section, 0, 4096, typeChar);
/*     */           }
/*     */         }
/*     */       } else {
/* 122 */         for (int y = yMin; y < yMax; y++) {
/* 123 */           char[] section = getChunkSection(y, true);
/* 124 */           int offsetBase = (y & 0xF) << 8;
/* 125 */           int min = offsetBase | zMin << 4;
/* 126 */           int max = offsetBase | zMax << 4;
/* 127 */           Arrays.fill(section, min, max, typeChar);
/*     */         }
/*     */       }
/*     */     } else {
/* 131 */       for (int y = yMin; y < yMax; y++) {
/* 132 */         char[] section = getChunkSection(y, true);
/* 133 */         int offsetBase = (y & 0xF) << 8;
/* 134 */         for (int z = zMin; z < zMax; z++) {
/* 135 */           int offset = offsetBase | z << 4;
/* 136 */           Arrays.fill(section, offset | xMin, offset | xMax, typeChar);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlock(int x, int y, int z, int blockId)
/*     */   {
/* 144 */     setBlock(x, y, z, blockId, (byte)0);
/*     */   }
/*     */   
/*     */   public void setBlock(int x, int y, int z, int blockId, byte data)
/*     */   {
/* 149 */     setBlock(x, y, z, (char)(blockId << 4 | data));
/*     */   }
/*     */   
/*     */   public int getTypeId(int x, int y, int z)
/*     */   {
/* 154 */     if ((x != (x & 0xF)) || (y < 0) || (y >= this.maxHeight) || (z != (z & 0xF))) {
/* 155 */       return 0;
/*     */     }
/* 157 */     char[] section = getChunkSection(y, false);
/* 158 */     if (section == null) {
/* 159 */       return 0;
/*     */     }
/* 161 */     return section[((y & 0xF) << 8 | z << 4 | x)] >> '\004';
/*     */   }
/*     */   
/*     */ 
/*     */   public byte getData(int x, int y, int z)
/*     */   {
/* 167 */     if ((x != (x & 0xF)) || (y < 0) || (y >= this.maxHeight) || (z != (z & 0xF))) {
/* 168 */       return 0;
/*     */     }
/* 170 */     char[] section = getChunkSection(y, false);
/* 171 */     if (section == null) {
/* 172 */       return 0;
/*     */     }
/* 174 */     return (byte)(section[((y & 0xF) << 8 | z << 4 | x)] & 0xF);
/*     */   }
/*     */   
/*     */   private void setBlock(int x, int y, int z, char type)
/*     */   {
/* 179 */     if ((x != (x & 0xF)) || (y < 0) || (y >= this.maxHeight) || (z != (z & 0xF))) {
/* 180 */       return;
/*     */     }
/* 182 */     char[] section = getChunkSection(y, true);
/* 183 */     section[((y & 0xF) << 8 | z << 4 | x)] = type;
/*     */   }
/*     */   
/*     */   private char[] getChunkSection(int y, boolean create) {
/* 187 */     char[] section = this.sections[(y >> 4)];
/* 188 */     if ((create) && (section == null)) {
/* 189 */       this.sections[(y >> 4)] = (section = new char['က']);
/*     */     }
/* 191 */     return section;
/*     */   }
/*     */   
/*     */   char[][] getRawChunkData() {
/* 195 */     return this.sections;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\generator\CraftChunkData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */