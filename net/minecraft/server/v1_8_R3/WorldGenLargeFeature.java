/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class WorldGenLargeFeature extends StructureGenerator
/*     */ {
/*  13 */   private static final List<BiomeBase> d = java.util.Arrays.asList(new BiomeBase[] { BiomeBase.DESERT, BiomeBase.DESERT_HILLS, BiomeBase.JUNGLE, BiomeBase.JUNGLE_HILLS, BiomeBase.SWAMPLAND });
/*     */   private List<BiomeBase.BiomeMeta> f;
/*     */   private int g;
/*     */   private int h;
/*     */   
/*     */   public WorldGenLargeFeature() {
/*  19 */     this.f = com.google.common.collect.Lists.newArrayList();
/*  20 */     this.g = 32;
/*  21 */     this.h = 8;
/*  22 */     this.f.add(new BiomeBase.BiomeMeta(EntityWitch.class, 1, 1, 1));
/*     */   }
/*     */   
/*     */   public WorldGenLargeFeature(Map<String, String> map) {
/*  26 */     this();
/*  27 */     Iterator iterator = map.entrySet().iterator();
/*     */     
/*  29 */     while (iterator.hasNext()) {
/*  30 */       Map.Entry entry = (Map.Entry)iterator.next();
/*     */       
/*  32 */       if (((String)entry.getKey()).equals("distance")) {
/*  33 */         this.g = MathHelper.a((String)entry.getValue(), this.g, this.h + 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String a()
/*     */   {
/*  40 */     return "Temple";
/*     */   }
/*     */   
/*     */   protected boolean a(int i, int j) {
/*  44 */     int k = i;
/*  45 */     int l = j;
/*     */     
/*  47 */     if (i < 0) {
/*  48 */       i -= this.g - 1;
/*     */     }
/*     */     
/*  51 */     if (j < 0) {
/*  52 */       j -= this.g - 1;
/*     */     }
/*     */     
/*  55 */     int i1 = i / this.g;
/*  56 */     int j1 = j / this.g;
/*  57 */     Random random = this.c.a(i1, j1, this.c.spigotConfig.largeFeatureSeed);
/*     */     
/*  59 */     i1 *= this.g;
/*  60 */     j1 *= this.g;
/*  61 */     i1 += random.nextInt(this.g - this.h);
/*  62 */     j1 += random.nextInt(this.g - this.h);
/*  63 */     if ((k == i1) && (l == j1)) {
/*  64 */       BiomeBase biomebase = this.c.getWorldChunkManager().getBiome(new BlockPosition(k * 16 + 8, 0, l * 16 + 8));
/*     */       
/*  66 */       if (biomebase == null) {
/*  67 */         return false;
/*     */       }
/*     */       
/*  70 */       Iterator iterator = d.iterator();
/*     */       
/*  72 */       while (iterator.hasNext()) {
/*  73 */         BiomeBase biomebase1 = (BiomeBase)iterator.next();
/*     */         
/*  75 */         if (biomebase == biomebase1) {
/*  76 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart b(int i, int j) {
/*  85 */     return new WorldGenLargeFeatureStart(this.c, this.b, i, j);
/*     */   }
/*     */   
/*     */   public boolean a(BlockPosition blockposition) {
/*  89 */     StructureStart structurestart = c(blockposition);
/*     */     
/*  91 */     if ((structurestart != null) && ((structurestart instanceof WorldGenLargeFeatureStart)) && (!structurestart.a.isEmpty())) {
/*  92 */       StructurePiece structurepiece = (StructurePiece)structurestart.a.getFirst();
/*     */       
/*  94 */       return structurepiece instanceof WorldGenRegistration.WorldGenWitchHut;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public List<BiomeBase.BiomeMeta> b()
/*     */   {
/* 101 */     return this.f;
/*     */   }
/*     */   
/*     */   public static class WorldGenLargeFeatureStart extends StructureStart
/*     */   {
/*     */     public WorldGenLargeFeatureStart() {}
/*     */     
/*     */     public WorldGenLargeFeatureStart(World world, Random random, int i, int j) {
/* 109 */       super(j);
/* 110 */       BiomeBase biomebase = world.getBiome(new BlockPosition(i * 16 + 8, 0, j * 16 + 8));
/*     */       
/* 112 */       if ((biomebase != BiomeBase.JUNGLE) && (biomebase != BiomeBase.JUNGLE_HILLS)) {
/* 113 */         if (biomebase == BiomeBase.SWAMPLAND) {
/* 114 */           WorldGenRegistration.WorldGenWitchHut worldgenregistration_worldgenwitchhut = new WorldGenRegistration.WorldGenWitchHut(random, i * 16, j * 16);
/*     */           
/* 116 */           this.a.add(worldgenregistration_worldgenwitchhut);
/* 117 */         } else if ((biomebase == BiomeBase.DESERT) || (biomebase == BiomeBase.DESERT_HILLS)) {
/* 118 */           WorldGenRegistration.WorldGenPyramidPiece worldgenregistration_worldgenpyramidpiece = new WorldGenRegistration.WorldGenPyramidPiece(random, i * 16, j * 16);
/*     */           
/* 120 */           this.a.add(worldgenregistration_worldgenpyramidpiece);
/*     */         }
/*     */       } else {
/* 123 */         WorldGenRegistration.WorldGenJungleTemple worldgenregistration_worldgenjungletemple = new WorldGenRegistration.WorldGenJungleTemple(random, i * 16, j * 16);
/*     */         
/* 125 */         this.a.add(worldgenregistration_worldgenjungletemple);
/*     */       }
/*     */       
/* 128 */       c();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenLargeFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */