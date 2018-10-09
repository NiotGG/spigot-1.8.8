/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenSwampTree
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*  18 */   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.OAK);
/*  19 */   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves1.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenSwampTree() {
/*  22 */     super(false);
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  27 */     int i = paramRandom.nextInt(4) + 5;
/*  28 */     while (paramWorld.getType(paramBlockPosition.down()).getBlock().getMaterial() == Material.WATER) {
/*  29 */       paramBlockPosition = paramBlockPosition.down();
/*     */     }
/*     */     
/*  32 */     int j = 1;
/*  33 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 > 256))
/*  34 */       return false;
/*     */     int i2;
/*     */     int i3;
/*  37 */     for (int k = paramBlockPosition.getY(); k <= paramBlockPosition.getY() + 1 + i; k++) {
/*  38 */       m = 1;
/*  39 */       if (k == paramBlockPosition.getY()) {
/*  40 */         m = 0;
/*     */       }
/*  42 */       if (k >= paramBlockPosition.getY() + 1 + i - 2) {
/*  43 */         m = 3;
/*     */       }
/*  45 */       BlockPosition.MutableBlockPosition localMutableBlockPosition1 = new BlockPosition.MutableBlockPosition();
/*  46 */       for (i2 = paramBlockPosition.getX() - m; (i2 <= paramBlockPosition.getX() + m) && (j != 0); i2++) {
/*  47 */         for (i3 = paramBlockPosition.getZ() - m; (i3 <= paramBlockPosition.getZ() + m) && (j != 0); i3++) {
/*  48 */           if ((k >= 0) && (k < 256)) {
/*  49 */             Block localBlock3 = paramWorld.getType(localMutableBlockPosition1.c(i2, k, i3)).getBlock();
/*  50 */             if ((localBlock3.getMaterial() != Material.AIR) && (localBlock3.getMaterial() != Material.LEAVES)) {
/*  51 */               if ((localBlock3 == Blocks.WATER) || (localBlock3 == Blocks.FLOWING_WATER)) {
/*  52 */                 if (k > paramBlockPosition.getY()) {
/*  53 */                   j = 0;
/*     */                 }
/*     */               } else {
/*  56 */                 j = 0;
/*     */               }
/*     */             }
/*     */           } else {
/*  60 */             j = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  66 */     if (j == 0) {
/*  67 */       return false;
/*     */     }
/*     */     
/*  70 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  71 */     if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT)) || (paramBlockPosition.getY() >= 256 - i - 1)) {
/*  72 */       return false;
/*     */     }
/*     */     
/*  75 */     a(paramWorld, paramBlockPosition.down());
/*     */     int i4;
/*  77 */     int i5; BlockPosition localBlockPosition2; for (int m = paramBlockPosition.getY() - 3 + i; m <= paramBlockPosition.getY() + i; m++) {
/*  78 */       int n = m - (paramBlockPosition.getY() + i);
/*  79 */       i2 = 2 - n / 2;
/*  80 */       for (i3 = paramBlockPosition.getX() - i2; i3 <= paramBlockPosition.getX() + i2; i3++) {
/*  81 */         i4 = i3 - paramBlockPosition.getX();
/*  82 */         for (i5 = paramBlockPosition.getZ() - i2; i5 <= paramBlockPosition.getZ() + i2; i5++) {
/*  83 */           int i6 = i5 - paramBlockPosition.getZ();
/*  84 */           if ((Math.abs(i4) != i2) || (Math.abs(i6) != i2) || ((paramRandom.nextInt(2) != 0) && (n != 0)))
/*     */           {
/*     */ 
/*  87 */             localBlockPosition2 = new BlockPosition(i3, m, i5);
/*  88 */             if (!paramWorld.getType(localBlockPosition2).getBlock().o()) {
/*  89 */               a(paramWorld, localBlockPosition2, b);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  95 */     for (m = 0; m < i; m++) {
/*  96 */       Block localBlock2 = paramWorld.getType(paramBlockPosition.up(m)).getBlock();
/*  97 */       if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES) || (localBlock2 == Blocks.FLOWING_WATER) || (localBlock2 == Blocks.WATER)) {
/*  98 */         a(paramWorld, paramBlockPosition.up(m), a);
/*     */       }
/*     */     }
/*     */     
/* 102 */     for (m = paramBlockPosition.getY() - 3 + i; m <= paramBlockPosition.getY() + i; m++) {
/* 103 */       int i1 = m - (paramBlockPosition.getY() + i);
/* 104 */       i2 = 2 - i1 / 2;
/* 105 */       BlockPosition.MutableBlockPosition localMutableBlockPosition2 = new BlockPosition.MutableBlockPosition();
/* 106 */       for (i4 = paramBlockPosition.getX() - i2; i4 <= paramBlockPosition.getX() + i2; i4++) {
/* 107 */         for (i5 = paramBlockPosition.getZ() - i2; i5 <= paramBlockPosition.getZ() + i2; i5++) {
/* 108 */           localMutableBlockPosition2.c(i4, m, i5);
/*     */           
/* 110 */           if (paramWorld.getType(localMutableBlockPosition2).getBlock().getMaterial() == Material.LEAVES) {
/* 111 */             BlockPosition localBlockPosition1 = localMutableBlockPosition2.west();
/* 112 */             localBlockPosition2 = localMutableBlockPosition2.east();
/* 113 */             BlockPosition localBlockPosition3 = localMutableBlockPosition2.north();
/* 114 */             BlockPosition localBlockPosition4 = localMutableBlockPosition2.south();
/*     */             
/* 116 */             if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition1).getBlock().getMaterial() == Material.AIR)) {
/* 117 */               a(paramWorld, localBlockPosition1, BlockVine.EAST);
/*     */             }
/* 119 */             if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition2).getBlock().getMaterial() == Material.AIR)) {
/* 120 */               a(paramWorld, localBlockPosition2, BlockVine.WEST);
/*     */             }
/* 122 */             if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition3).getBlock().getMaterial() == Material.AIR)) {
/* 123 */               a(paramWorld, localBlockPosition3, BlockVine.SOUTH);
/*     */             }
/* 125 */             if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition4).getBlock().getMaterial() == Material.AIR)) {
/* 126 */               a(paramWorld, localBlockPosition4, BlockVine.NORTH);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, BlockPosition paramBlockPosition, BlockStateBoolean paramBlockStateBoolean) {
/* 136 */     IBlockData localIBlockData = Blocks.VINE.getBlockData().set(paramBlockStateBoolean, Boolean.valueOf(true));
/* 137 */     a(paramWorld, paramBlockPosition, localIBlockData);
/* 138 */     int i = 4;
/*     */     
/* 140 */     paramBlockPosition = paramBlockPosition.down();
/* 141 */     while ((paramWorld.getType(paramBlockPosition).getBlock().getMaterial() == Material.AIR) && (i > 0)) {
/* 142 */       a(paramWorld, paramBlockPosition, localIBlockData);
/* 143 */       paramBlockPosition = paramBlockPosition.down();
/* 144 */       i--;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenSwampTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */