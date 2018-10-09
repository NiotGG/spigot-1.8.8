/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenPackedIce2
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World world, Random random, BlockPosition blockposition)
/*    */   {
/* 10 */     while ((world.isEmpty(blockposition)) && (blockposition.getY() > 2)) {
/* 11 */       blockposition = blockposition.down();
/*    */     }
/*    */     
/* 14 */     if (world.getType(blockposition).getBlock() != Blocks.SNOW) {
/* 15 */       return false;
/*    */     }
/* 17 */     blockposition = blockposition.up(random.nextInt(4));
/* 18 */     int i = random.nextInt(4) + 7;
/* 19 */     int j = i / 4 + random.nextInt(2);
/*    */     
/* 21 */     if ((j > 1) && (random.nextInt(60) == 0)) {
/* 22 */       blockposition = blockposition.up(10 + random.nextInt(30));
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 28 */     for (int k = 0; k < i; k++) {
/* 29 */       float f = (1.0F - k / i) * j;
/*    */       
/* 31 */       int l = MathHelper.f(f);
/*    */       
/* 33 */       for (int i1 = -l; i1 <= l; i1++) {
/* 34 */         float f1 = MathHelper.a(i1) - 0.25F;
/*    */         
/* 36 */         for (int j1 = -l; j1 <= l; j1++) {
/* 37 */           float f2 = MathHelper.a(j1) - 0.25F;
/*    */           
/* 39 */           if (((i1 == 0) && (j1 == 0)) || ((f1 * f1 + f2 * f2 <= f * f) && (((i1 != -l) && (i1 != l) && (j1 != -l) && (j1 != l)) || (random.nextFloat() <= 0.75F)))) {
/* 40 */             Block block = world.getType(blockposition.a(i1, k, j1)).getBlock();
/*    */             
/* 42 */             if ((block.getMaterial() == Material.AIR) || (block == Blocks.DIRT) || (block == Blocks.SNOW) || (block == Blocks.ICE)) {
/* 43 */               world.setTypeUpdate(blockposition.a(i1, k, j1), Blocks.PACKED_ICE.getBlockData());
/*    */             }
/*    */             
/* 46 */             if ((k != 0) && (l > 1)) {
/* 47 */               block = world.getType(blockposition.a(i1, -k, j1)).getBlock();
/* 48 */               if ((block.getMaterial() == Material.AIR) || (block == Blocks.DIRT) || (block == Blocks.SNOW) || (block == Blocks.ICE)) {
/* 49 */                 world.setTypeUpdate(blockposition.a(i1, -k, j1), Blocks.PACKED_ICE.getBlockData());
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 57 */     k = j - 1;
/* 58 */     if (k < 0) {
/* 59 */       k = 0;
/* 60 */     } else if (k > 1) {
/* 61 */       k = 1;
/*    */     }
/*    */     
/* 64 */     for (int k1 = -k; k1 <= k; k1++) {
/* 65 */       int l = -k;
/*    */       
/* 67 */       while (l <= k) {
/* 68 */         BlockPosition blockposition1 = blockposition.a(k1, -1, l);
/* 69 */         int l1 = 50;
/*    */         
/* 71 */         if ((Math.abs(k1) == 1) && (Math.abs(l) == 1)) {
/* 72 */           l1 = random.nextInt(5);
/*    */         }
/*    */         
/*    */ 
/* 76 */         while (blockposition1.getY() > 50) {
/* 77 */           Block block1 = world.getType(blockposition1).getBlock();
/*    */           
/* 79 */           if ((block1.getMaterial() != Material.AIR) && (block1 != Blocks.DIRT) && (block1 != Blocks.SNOW) && (block1 != Blocks.ICE) && (block1 != Blocks.PACKED_ICE)) break;
/* 80 */           world.setTypeUpdate(blockposition1, Blocks.PACKED_ICE.getBlockData());
/* 81 */           blockposition1 = blockposition1.down();
/* 82 */           l1--;
/* 83 */           if (l1 <= 0) {
/* 84 */             blockposition1 = blockposition1.down(random.nextInt(5) + 1);
/* 85 */             l1 = random.nextInt(5);
/*    */           }
/*    */         }
/*    */         
/*    */ 
/*    */ 
/* 91 */         l++;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 97 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenPackedIce2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */