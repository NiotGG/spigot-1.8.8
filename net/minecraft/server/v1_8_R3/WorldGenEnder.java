/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenEnder
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public WorldGenEnder(Block paramBlock)
/*    */   {
/* 16 */     this.a = paramBlock;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 21 */     if ((!paramWorld.isEmpty(paramBlockPosition)) || (paramWorld.getType(paramBlockPosition.down()).getBlock() != this.a)) {
/* 22 */       return false;
/*    */     }
/* 24 */     int i = paramRandom.nextInt(32) + 6;
/* 25 */     int j = paramRandom.nextInt(4) + 1;
/* 26 */     BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 27 */     int m; int n; int i1; for (int k = paramBlockPosition.getX() - j; k <= paramBlockPosition.getX() + j; k++) {
/* 28 */       for (m = paramBlockPosition.getZ() - j; m <= paramBlockPosition.getZ() + j; m++) {
/* 29 */         n = k - paramBlockPosition.getX();
/* 30 */         i1 = m - paramBlockPosition.getZ();
/* 31 */         if ((n * n + i1 * i1 <= j * j + 1) && 
/* 32 */           (paramWorld.getType(localMutableBlockPosition.c(k, paramBlockPosition.getY() - 1, m)).getBlock() != this.a)) {
/* 33 */           return false;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 38 */     for (k = paramBlockPosition.getY(); k < paramBlockPosition.getY() + i; k++) {
/* 39 */       if (k >= 256) break;
/* 40 */       for (m = paramBlockPosition.getX() - j; m <= paramBlockPosition.getX() + j; m++) {
/* 41 */         for (n = paramBlockPosition.getZ() - j; n <= paramBlockPosition.getZ() + j; n++) {
/* 42 */           i1 = m - paramBlockPosition.getX();
/* 43 */           int i2 = n - paramBlockPosition.getZ();
/* 44 */           if (i1 * i1 + i2 * i2 <= j * j + 1) {
/* 45 */             paramWorld.setTypeAndData(new BlockPosition(m, k, n), Blocks.OBSIDIAN.getBlockData(), 2);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 54 */     EntityEnderCrystal localEntityEnderCrystal = new EntityEnderCrystal(paramWorld);
/* 55 */     localEntityEnderCrystal.setPositionRotation(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + i, paramBlockPosition.getZ() + 0.5F, paramRandom.nextFloat() * 360.0F, 0.0F);
/* 56 */     paramWorld.addEntity(localEntityEnderCrystal);
/* 57 */     paramWorld.setTypeAndData(paramBlockPosition.up(i), Blocks.BEDROCK.getBlockData(), 2);
/*    */     
/* 59 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenEnder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */