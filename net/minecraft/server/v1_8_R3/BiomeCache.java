/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeCache
/*    */ {
/*    */   private final WorldChunkManager a;
/*    */   private long b;
/*    */   
/*    */   public class BiomeCacheBlock
/*    */   {
/* 19 */     public float[] a = new float['Ā'];
/* 20 */     public BiomeBase[] b = new BiomeBase['Ā'];
/*    */     public int c;
/*    */     public int d;
/*    */     public long e;
/*    */     
/* 25 */     public BiomeCacheBlock(int paramInt1, int paramInt2) { this.c = paramInt1;
/* 26 */       this.d = paramInt2;
/* 27 */       BiomeCache.a(BiomeCache.this).getWetness(this.a, paramInt1 << 4, paramInt2 << 4, 16, 16);
/* 28 */       BiomeCache.a(BiomeCache.this).a(this.b, paramInt1 << 4, paramInt2 << 4, 16, 16, false);
/*    */     }
/*    */     
/*    */     public BiomeBase a(int paramInt1, int paramInt2) {
/* 32 */       return this.b[(paramInt1 & 0xF | (paramInt2 & 0xF) << 4)];
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 40 */   private LongHashMap<BiomeCacheBlock> c = new LongHashMap();
/* 41 */   private List<BiomeCacheBlock> d = Lists.newArrayList();
/*    */   
/*    */   public BiomeCache(WorldChunkManager paramWorldChunkManager) {
/* 44 */     this.a = paramWorldChunkManager;
/*    */   }
/*    */   
/*    */   public BiomeCacheBlock a(int paramInt1, int paramInt2) {
/* 48 */     paramInt1 >>= 4;
/* 49 */     paramInt2 >>= 4;
/* 50 */     long l = paramInt1 & 0xFFFFFFFF | (paramInt2 & 0xFFFFFFFF) << 32;
/* 51 */     BiomeCacheBlock localBiomeCacheBlock = (BiomeCacheBlock)this.c.getEntry(l);
/* 52 */     if (localBiomeCacheBlock == null) {
/* 53 */       localBiomeCacheBlock = new BiomeCacheBlock(paramInt1, paramInt2);
/* 54 */       this.c.put(l, localBiomeCacheBlock);
/* 55 */       this.d.add(localBiomeCacheBlock);
/*    */     }
/* 57 */     localBiomeCacheBlock.e = MinecraftServer.az();
/* 58 */     return localBiomeCacheBlock;
/*    */   }
/*    */   
/*    */   public BiomeBase a(int paramInt1, int paramInt2, BiomeBase paramBiomeBase) {
/* 62 */     BiomeBase localBiomeBase = a(paramInt1, paramInt2).a(paramInt1, paramInt2);
/* 63 */     if (localBiomeBase == null) {
/* 64 */       return paramBiomeBase;
/*    */     }
/* 66 */     return localBiomeBase;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a()
/*    */   {
/* 74 */     long l1 = MinecraftServer.az();
/* 75 */     long l2 = l1 - this.b;
/* 76 */     if ((l2 > 7500L) || (l2 < 0L)) {
/* 77 */       this.b = l1;
/*    */       
/* 79 */       for (int i = 0; i < this.d.size(); i++) {
/* 80 */         BiomeCacheBlock localBiomeCacheBlock = (BiomeCacheBlock)this.d.get(i);
/* 81 */         long l3 = l1 - localBiomeCacheBlock.e;
/* 82 */         if ((l3 > 30000L) || (l3 < 0L)) {
/* 83 */           this.d.remove(i--);
/* 84 */           long l4 = localBiomeCacheBlock.c & 0xFFFFFFFF | (localBiomeCacheBlock.d & 0xFFFFFFFF) << 32;
/* 85 */           this.c.remove(l4);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public BiomeBase[] c(int paramInt1, int paramInt2) {
/* 92 */     return a(paramInt1, paramInt2).b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */