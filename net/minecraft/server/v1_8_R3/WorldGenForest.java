/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ public class WorldGenForest
/*    */   extends WorldGenTreeAbstract
/*    */ {
/* 19 */   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.BIRCH);
/* 20 */   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.BIRCH).set(BlockLeaves1.CHECK_DECAY, Boolean.valueOf(false));
/*    */   private boolean c;
/*    */   
/*    */   public WorldGenForest(boolean paramBoolean1, boolean paramBoolean2)
/*    */   {
/* 25 */     super(paramBoolean1);
/* 26 */     this.c = paramBoolean2;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 31 */     int i = paramRandom.nextInt(3) + 5;
/* 32 */     if (this.c) {
/* 33 */       i += paramRandom.nextInt(7);
/*    */     }
/*    */     
/* 36 */     int j = 1;
/* 37 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 > 256))
/* 38 */       return false;
/*    */     int i1;
/*    */     int i2;
/* 41 */     for (int k = paramBlockPosition.getY(); k <= paramBlockPosition.getY() + 1 + i; k++) {
/* 42 */       m = 1;
/* 43 */       if (k == paramBlockPosition.getY()) {
/* 44 */         m = 0;
/*    */       }
/* 46 */       if (k >= paramBlockPosition.getY() + 1 + i - 2) {
/* 47 */         m = 2;
/*    */       }
/* 49 */       BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 50 */       for (i1 = paramBlockPosition.getX() - m; (i1 <= paramBlockPosition.getX() + m) && (j != 0); i1++) {
/* 51 */         for (i2 = paramBlockPosition.getZ() - m; (i2 <= paramBlockPosition.getZ() + m) && (j != 0); i2++) {
/* 52 */           if ((k >= 0) && (k < 256)) {
/* 53 */             if (!a(paramWorld.getType(localMutableBlockPosition.c(i1, k, i2)).getBlock())) {
/* 54 */               j = 0;
/*    */             }
/*    */           } else {
/* 57 */             j = 0;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 63 */     if (j == 0) {
/* 64 */       return false;
/*    */     }
/*    */     
/* 67 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/* 68 */     if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.FARMLAND)) || (paramBlockPosition.getY() >= 256 - i - 1)) {
/* 69 */       return false;
/*    */     }
/*    */     
/* 72 */     a(paramWorld, paramBlockPosition.down());
/*    */     
/* 74 */     for (int m = paramBlockPosition.getY() - 3 + i; m <= paramBlockPosition.getY() + i; m++) {
/* 75 */       int n = m - (paramBlockPosition.getY() + i);
/* 76 */       i1 = 1 - n / 2;
/* 77 */       for (i2 = paramBlockPosition.getX() - i1; i2 <= paramBlockPosition.getX() + i1; i2++) {
/* 78 */         int i3 = i2 - paramBlockPosition.getX();
/* 79 */         for (int i4 = paramBlockPosition.getZ() - i1; i4 <= paramBlockPosition.getZ() + i1; i4++) {
/* 80 */           int i5 = i4 - paramBlockPosition.getZ();
/* 81 */           if ((Math.abs(i3) != i1) || (Math.abs(i5) != i1) || ((paramRandom.nextInt(2) != 0) && (n != 0)))
/*    */           {
/*    */ 
/* 84 */             BlockPosition localBlockPosition = new BlockPosition(i2, m, i4);
/* 85 */             Block localBlock3 = paramWorld.getType(localBlockPosition).getBlock();
/* 86 */             if ((localBlock3.getMaterial() == Material.AIR) || (localBlock3.getMaterial() == Material.LEAVES))
/* 87 */               a(paramWorld, localBlockPosition, b);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 92 */     for (m = 0; m < i; m++) {
/* 93 */       Block localBlock2 = paramWorld.getType(paramBlockPosition.up(m)).getBlock();
/* 94 */       if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES)) {
/* 95 */         a(paramWorld, paramBlockPosition.up(m), a);
/*    */       }
/*    */     }
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenForest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */