/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenStronghold
/*     */   extends StructureGenerator
/*     */ {
/*     */   private List<BiomeBase> d;
/*     */   private boolean f;
/*  23 */   private ChunkCoordIntPair[] g = new ChunkCoordIntPair[3];
/*  24 */   private double h = 32.0D;
/*  25 */   private int i = 3;
/*     */   
/*     */   public WorldGenStronghold()
/*     */   {
/*  29 */     this.d = Lists.newArrayList();
/*  30 */     for (BiomeBase localBiomeBase : BiomeBase.getBiomes()) {
/*  31 */       if ((localBiomeBase != null) && (localBiomeBase.an > 0.0F)) {
/*  32 */         this.d.add(localBiomeBase);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenStronghold(Map<String, String> paramMap) {
/*  38 */     this();
/*  39 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  40 */       if (((String)localEntry.getKey()).equals("distance")) {
/*  41 */         this.h = MathHelper.a((String)localEntry.getValue(), this.h, 1.0D);
/*  42 */       } else if (((String)localEntry.getKey()).equals("count")) {
/*  43 */         this.g = new ChunkCoordIntPair[MathHelper.a((String)localEntry.getValue(), this.g.length, 1)];
/*  44 */       } else if (((String)localEntry.getKey()).equals("spread")) {
/*  45 */         this.i = MathHelper.a((String)localEntry.getValue(), this.i, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String a()
/*     */   {
/*  52 */     return "Stronghold";
/*     */   }
/*     */   
/*     */   protected boolean a(int paramInt1, int paramInt2) {
/*     */     Object localObject1;
/*  57 */     if (!this.f) {
/*  58 */       localObject1 = new Random();
/*     */       
/*  60 */       ((Random)localObject1).setSeed(this.c.getSeed());
/*     */       
/*  62 */       double d1 = ((Random)localObject1).nextDouble() * 3.141592653589793D * 2.0D;
/*  63 */       int j = 1;
/*     */       
/*  65 */       for (int k = 0; k < this.g.length; k++) {
/*  66 */         double d2 = (1.25D * j + ((Random)localObject1).nextDouble()) * (this.h * j);
/*  67 */         int m = (int)Math.round(Math.cos(d1) * d2);
/*  68 */         int n = (int)Math.round(Math.sin(d1) * d2);
/*     */         
/*  70 */         BlockPosition localBlockPosition = this.c.getWorldChunkManager().a((m << 4) + 8, (n << 4) + 8, 112, this.d, (Random)localObject1);
/*  71 */         if (localBlockPosition != null) {
/*  72 */           m = localBlockPosition.getX() >> 4;
/*  73 */           n = localBlockPosition.getZ() >> 4;
/*     */         }
/*     */         
/*  76 */         this.g[k] = new ChunkCoordIntPair(m, n);
/*     */         
/*  78 */         d1 += 6.283185307179586D * j / this.i;
/*     */         
/*  80 */         if (k == this.i) {
/*  81 */           j += 2 + ((Random)localObject1).nextInt(5);
/*  82 */           this.i += 1 + ((Random)localObject1).nextInt(2);
/*     */         }
/*     */       }
/*     */       
/*  86 */       this.f = true;
/*     */     }
/*  88 */     for (Object localObject2 : this.g) {
/*  89 */       if ((paramInt1 == ((ChunkCoordIntPair)localObject2).x) && (paramInt2 == ((ChunkCoordIntPair)localObject2).z)) {
/*  90 */         return true;
/*     */       }
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   protected List<BlockPosition> z_()
/*     */   {
/*  98 */     ArrayList localArrayList = Lists.newArrayList();
/*  99 */     for (ChunkCoordIntPair localChunkCoordIntPair : this.g) {
/* 100 */       if (localChunkCoordIntPair != null) {
/* 101 */         localArrayList.add(localChunkCoordIntPair.a(64));
/*     */       }
/*     */     }
/* 104 */     return localArrayList;
/*     */   }
/*     */   
/*     */   protected StructureStart b(int paramInt1, int paramInt2)
/*     */   {
/* 109 */     WorldGenStronghold2Start localWorldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
/*     */     
/* 111 */     while ((localWorldGenStronghold2Start.b().isEmpty()) || (((WorldGenStrongholdPieces.WorldGenStrongholdStart)localWorldGenStronghold2Start.b().get(0)).b == null))
/*     */     {
/* 113 */       localWorldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 116 */     return localWorldGenStronghold2Start;
/*     */   }
/*     */   
/*     */   public static class WorldGenStronghold2Start extends StructureStart
/*     */   {
/*     */     public WorldGenStronghold2Start() {}
/*     */     
/*     */     public WorldGenStronghold2Start(World paramWorld, Random paramRandom, int paramInt1, int paramInt2)
/*     */     {
/* 125 */       super(paramInt2);
/*     */       
/* 127 */       WorldGenStrongholdPieces.b();
/*     */       
/* 129 */       WorldGenStrongholdPieces.WorldGenStrongholdStart localWorldGenStrongholdStart = new WorldGenStrongholdPieces.WorldGenStrongholdStart(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 130 */       this.a.add(localWorldGenStrongholdStart);
/* 131 */       localWorldGenStrongholdStart.a(localWorldGenStrongholdStart, this.a, paramRandom);
/*     */       
/* 133 */       List localList = localWorldGenStrongholdStart.c;
/* 134 */       while (!localList.isEmpty()) {
/* 135 */         int i = paramRandom.nextInt(localList.size());
/* 136 */         StructurePiece localStructurePiece = (StructurePiece)localList.remove(i);
/* 137 */         localStructurePiece.a(localWorldGenStrongholdStart, this.a, paramRandom);
/*     */       }
/*     */       
/* 140 */       c();
/* 141 */       a(paramWorld, paramRandom, 10);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenStronghold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */