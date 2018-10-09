/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenMonument
/*     */   extends StructureGenerator
/*     */ {
/*  24 */   private int f = 32;
/*  25 */   private int g = 5;
/*     */   
/*  27 */   public static final List<BiomeBase> d = Arrays.asList(new BiomeBase[] { BiomeBase.OCEAN, BiomeBase.DEEP_OCEAN, BiomeBase.RIVER, BiomeBase.FROZEN_OCEAN, BiomeBase.FROZEN_RIVER });
/*     */   
/*  29 */   private static final List<BiomeBase.BiomeMeta> h = Lists.newArrayList();
/*     */   
/*  31 */   static { h.add(new BiomeBase.BiomeMeta(EntityGuardian.class, 1, 2, 4)); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WorldGenMonument(Map<String, String> paramMap)
/*     */   {
/*  39 */     this();
/*  40 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  41 */       if (((String)localEntry.getKey()).equals("spacing")) {
/*  42 */         this.f = MathHelper.a((String)localEntry.getValue(), this.f, 1);
/*  43 */       } else if (((String)localEntry.getKey()).equals("separation")) {
/*  44 */         this.g = MathHelper.a((String)localEntry.getValue(), this.g, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String a()
/*     */   {
/*  51 */     return "Monument";
/*     */   }
/*     */   
/*     */   protected boolean a(int paramInt1, int paramInt2)
/*     */   {
/*  56 */     int i = paramInt1;
/*  57 */     int j = paramInt2;
/*  58 */     if (paramInt1 < 0) {
/*  59 */       paramInt1 -= this.f - 1;
/*     */     }
/*  61 */     if (paramInt2 < 0) {
/*  62 */       paramInt2 -= this.f - 1;
/*     */     }
/*     */     
/*  65 */     int k = paramInt1 / this.f;
/*  66 */     int m = paramInt2 / this.f;
/*     */     
/*  68 */     Random localRandom = this.c.a(k, m, 10387313);
/*  69 */     k *= this.f;
/*  70 */     m *= this.f;
/*  71 */     k += (localRandom.nextInt(this.f - this.g) + localRandom.nextInt(this.f - this.g)) / 2;
/*  72 */     m += (localRandom.nextInt(this.f - this.g) + localRandom.nextInt(this.f - this.g)) / 2;
/*     */     
/*  74 */     paramInt1 = i;
/*  75 */     paramInt2 = j;
/*  76 */     if ((paramInt1 == k) && (paramInt2 == m))
/*     */     {
/*  78 */       if (this.c.getWorldChunkManager().getBiome(new BlockPosition(paramInt1 * 16 + 8, 64, paramInt2 * 16 + 8), null) != BiomeBase.DEEP_OCEAN) {
/*  79 */         return false;
/*     */       }
/*     */       
/*  82 */       boolean bool = this.c.getWorldChunkManager().a(paramInt1 * 16 + 8, paramInt2 * 16 + 8, 29, d);
/*  83 */       if (bool) {
/*  84 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart b(int paramInt1, int paramInt2)
/*     */   {
/*  93 */     return new WorldGenMonumentStart(this.c, this.b, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public static class WorldGenMonumentStart extends StructureStart {
/*  97 */     private Set<ChunkCoordIntPair> c = Sets.newHashSet();
/*     */     
/*     */     private boolean d;
/*     */     
/*     */ 
/*     */     public WorldGenMonumentStart() {}
/*     */     
/*     */     public WorldGenMonumentStart(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
/*     */     {
/* 106 */       super(paramInt2);
/* 107 */       b(paramWorld, paramRandom, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */ 
/*     */     private void b(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
/*     */     {
/* 113 */       paramRandom.setSeed(paramWorld.getSeed());
/* 114 */       long l1 = paramRandom.nextLong();
/* 115 */       long l2 = paramRandom.nextLong();
/* 116 */       long l3 = paramInt1 * l1;
/* 117 */       long l4 = paramInt2 * l2;
/* 118 */       paramRandom.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
/*     */       
/* 120 */       int i = paramInt1 * 16 + 8 - 29;
/* 121 */       int j = paramInt2 * 16 + 8 - 29;
/* 122 */       EnumDirection localEnumDirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom);
/*     */       
/* 124 */       this.a.add(new WorldGenMonumentPieces.WorldGenMonumentPiece1(paramRandom, i, j, localEnumDirection));
/* 125 */       c();
/*     */       
/* 127 */       this.d = true;
/*     */     }
/*     */     
/*     */     public void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox)
/*     */     {
/* 132 */       if (!this.d) {
/* 133 */         this.a.clear();
/* 134 */         b(paramWorld, paramRandom, e(), f());
/*     */       }
/* 136 */       super.a(paramWorld, paramRandom, paramStructureBoundingBox);
/*     */     }
/*     */     
/*     */     public boolean a(ChunkCoordIntPair paramChunkCoordIntPair)
/*     */     {
/* 141 */       if (this.c.contains(paramChunkCoordIntPair)) {
/* 142 */         return false;
/*     */       }
/* 144 */       return super.a(paramChunkCoordIntPair);
/*     */     }
/*     */     
/*     */     public void b(ChunkCoordIntPair paramChunkCoordIntPair)
/*     */     {
/* 149 */       super.b(paramChunkCoordIntPair);
/*     */       
/* 151 */       this.c.add(paramChunkCoordIntPair);
/*     */     }
/*     */     
/*     */     public void a(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 156 */       super.a(paramNBTTagCompound);
/*     */       
/* 158 */       NBTTagList localNBTTagList = new NBTTagList();
/* 159 */       for (ChunkCoordIntPair localChunkCoordIntPair : this.c) {
/* 160 */         NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 161 */         localNBTTagCompound.setInt("X", localChunkCoordIntPair.x);
/* 162 */         localNBTTagCompound.setInt("Z", localChunkCoordIntPair.z);
/* 163 */         localNBTTagList.add(localNBTTagCompound);
/*     */       }
/* 165 */       paramNBTTagCompound.set("Processed", localNBTTagList);
/*     */     }
/*     */     
/*     */     public void b(NBTTagCompound paramNBTTagCompound)
/*     */     {
/* 170 */       super.b(paramNBTTagCompound);
/*     */       
/* 172 */       if (paramNBTTagCompound.hasKeyOfType("Processed", 9)) {
/* 173 */         NBTTagList localNBTTagList = paramNBTTagCompound.getList("Processed", 10);
/* 174 */         for (int i = 0; i < localNBTTagList.size(); i++) {
/* 175 */           NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
/* 176 */           this.c.add(new ChunkCoordIntPair(localNBTTagCompound.getInt("X"), localNBTTagCompound.getInt("Z")));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> b() {
/* 183 */     return h;
/*     */   }
/*     */   
/*     */   public WorldGenMonument() {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMonument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */