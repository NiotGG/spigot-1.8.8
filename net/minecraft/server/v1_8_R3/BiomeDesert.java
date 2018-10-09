/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class BiomeDesert
/*    */   extends BiomeBase
/*    */ {
/*    */   public BiomeDesert(int paramInt)
/*    */   {
/* 12 */     super(paramInt);
/*    */     
/*    */ 
/* 15 */     this.au.clear();
/* 16 */     this.ak = Blocks.SAND.getBlockData();
/* 17 */     this.al = Blocks.SAND.getBlockData();
/*    */     
/* 19 */     this.as.A = 64537;
/* 20 */     this.as.D = 2;
/* 21 */     this.as.F = 50;
/* 22 */     this.as.G = 10;
/*    */     
/* 24 */     this.au.clear();
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 29 */     super.a(paramWorld, paramRandom, paramBlockPosition);
/*    */     
/* 31 */     if (paramRandom.nextInt(1000) == 0) {
/* 32 */       int i = paramRandom.nextInt(16) + 8;
/* 33 */       int j = paramRandom.nextInt(16) + 8;
/* 34 */       BlockPosition localBlockPosition = paramWorld.getHighestBlockYAt(paramBlockPosition.a(i, 0, j)).up();
/*    */       
/* 36 */       new WorldGenDesertWell().generate(paramWorld, paramRandom, localBlockPosition);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeDesert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */