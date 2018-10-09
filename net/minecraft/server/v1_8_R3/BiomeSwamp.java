/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeSwamp
/*    */   extends BiomeBase
/*    */ {
/*    */   protected BiomeSwamp(int paramInt)
/*    */   {
/* 18 */     super(paramInt);
/* 19 */     this.as.A = 2;
/* 20 */     this.as.B = 1;
/* 21 */     this.as.D = 1;
/* 22 */     this.as.E = 8;
/* 23 */     this.as.F = 10;
/* 24 */     this.as.J = 1;
/* 25 */     this.as.z = 4;
/* 26 */     this.as.I = 0;
/* 27 */     this.as.H = 0;
/* 28 */     this.as.C = 5;
/*    */     
/* 30 */     this.ar = 14745518;
/*    */     
/* 32 */     this.at.add(new BiomeBase.BiomeMeta(EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 37 */     return this.aC;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockFlowers.EnumFlowerVarient a(Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 56 */     return BlockFlowers.EnumFlowerVarient.BLUE_ORCHID;
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*    */   {
/* 61 */     double d = af.a(paramInt1 * 0.25D, paramInt2 * 0.25D);
/* 62 */     if (d > 0.0D) {
/* 63 */       int i = paramInt1 & 0xF;
/* 64 */       int j = paramInt2 & 0xF;
/* 65 */       for (int k = 255; k >= 0; k--) {
/* 66 */         if (paramChunkSnapshot.a(j, k, i).getBlock().getMaterial() != Material.AIR) {
/* 67 */           if ((k != 62) || (paramChunkSnapshot.a(j, k, i).getBlock() == Blocks.WATER)) break;
/* 68 */           paramChunkSnapshot.a(j, k, i, Blocks.WATER.getBlockData());
/* 69 */           if (d >= 0.12D) break;
/* 70 */           paramChunkSnapshot.a(j, k + 1, i, Blocks.WATERLILY.getBlockData()); break;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 78 */     b(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeSwamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */