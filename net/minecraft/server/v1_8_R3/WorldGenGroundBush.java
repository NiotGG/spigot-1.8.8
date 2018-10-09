/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenGroundBush extends WorldGenTrees
/*    */ {
/*    */   private final IBlockData a;
/*    */   private final IBlockData b;
/*    */   
/*    */   public WorldGenGroundBush(IBlockData iblockdata, IBlockData iblockdata1) {
/* 11 */     super(false);
/* 12 */     this.b = iblockdata;
/* 13 */     this.a = iblockdata1;
/*    */   }
/*    */   
/*    */   public boolean generate(World world, Random random, BlockPosition blockposition)
/*    */   {
/*    */     Block block;
/* 19 */     while ((((block = world.getType(blockposition).getBlock()).getMaterial() == Material.AIR) || (block.getMaterial() == Material.LEAVES)) && (blockposition.getY() > 0)) { Block block;
/* 20 */       blockposition = blockposition.down();
/*    */     }
/*    */     
/* 23 */     Block block1 = world.getType(blockposition).getBlock();
/*    */     
/* 25 */     if ((block1 == Blocks.DIRT) || (block1 == Blocks.GRASS)) {
/* 26 */       blockposition = blockposition.up();
/* 27 */       a(world, blockposition, this.b);
/*    */       
/* 29 */       for (int i = blockposition.getY(); i <= blockposition.getY() + 2; i++) {
/* 30 */         int j = i - blockposition.getY();
/* 31 */         int k = 2 - j;
/*    */         
/* 33 */         for (int l = blockposition.getX() - k; l <= blockposition.getX() + k; l++) {
/* 34 */           int i1 = l - blockposition.getX();
/*    */           
/* 36 */           for (int j1 = blockposition.getZ() - k; j1 <= blockposition.getZ() + k; j1++) {
/* 37 */             int k1 = j1 - blockposition.getZ();
/*    */             
/* 39 */             if ((Math.abs(i1) != k) || (Math.abs(k1) != k) || (random.nextInt(2) != 0)) {
/* 40 */               BlockPosition blockposition1 = new BlockPosition(l, i, j1);
/*    */               
/* 42 */               if (!world.getType(blockposition1).getBlock().o()) {
/* 43 */                 a(world, blockposition1, this.a);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     else {
/* 51 */       return false;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenGroundBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */