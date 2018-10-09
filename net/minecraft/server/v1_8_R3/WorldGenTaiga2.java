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
/*     */ public class WorldGenTaiga2
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*  17 */   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
/*  18 */   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenTaiga2(boolean paramBoolean) {
/*  21 */     super(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  27 */     int i = paramRandom.nextInt(4) + 6;
/*  28 */     int j = 1 + paramRandom.nextInt(2);
/*  29 */     int k = i - j;
/*  30 */     int m = 2 + paramRandom.nextInt(2);
/*     */     
/*  32 */     int n = 1;
/*     */     
/*  34 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 > 256)) {
/*  35 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  39 */     for (int i1 = paramBlockPosition.getY(); (i1 <= paramBlockPosition.getY() + 1 + i) && (n != 0); i1++) {
/*  40 */       i2 = 1;
/*  41 */       if (i1 - paramBlockPosition.getY() < j) {
/*  42 */         i2 = 0;
/*     */       } else {
/*  44 */         i2 = m;
/*     */       }
/*  46 */       BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/*  47 */       for (i4 = paramBlockPosition.getX() - i2; (i4 <= paramBlockPosition.getX() + i2) && (n != 0); i4++) {
/*  48 */         for (i5 = paramBlockPosition.getZ() - i2; (i5 <= paramBlockPosition.getZ() + i2) && (n != 0); i5++) {
/*  49 */           if ((i1 >= 0) && (i1 < 256)) {
/*  50 */             Block localBlock2 = paramWorld.getType(localMutableBlockPosition.c(i4, i1, i5)).getBlock();
/*  51 */             if ((localBlock2.getMaterial() != Material.AIR) && (localBlock2.getMaterial() != Material.LEAVES)) {
/*  52 */               n = 0;
/*     */             }
/*     */           } else {
/*  55 */             n = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  61 */     if (n == 0) {
/*  62 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  66 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  67 */     if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.FARMLAND)) || (paramBlockPosition.getY() >= 256 - i - 1)) {
/*  68 */       return false;
/*     */     }
/*     */     
/*  71 */     a(paramWorld, paramBlockPosition.down());
/*     */     
/*     */ 
/*  74 */     int i2 = paramRandom.nextInt(2);
/*  75 */     int i3 = 1;
/*  76 */     int i4 = 0;
/*  77 */     for (int i5 = 0; i5 <= k; i5++) {
/*  78 */       i6 = paramBlockPosition.getY() + i - i5;
/*     */       
/*  80 */       for (int i7 = paramBlockPosition.getX() - i2; i7 <= paramBlockPosition.getX() + i2; i7++) {
/*  81 */         int i8 = i7 - paramBlockPosition.getX();
/*  82 */         for (int i9 = paramBlockPosition.getZ() - i2; i9 <= paramBlockPosition.getZ() + i2; i9++) {
/*  83 */           int i10 = i9 - paramBlockPosition.getZ();
/*  84 */           if ((Math.abs(i8) != i2) || (Math.abs(i10) != i2) || (i2 <= 0))
/*     */           {
/*     */ 
/*  87 */             BlockPosition localBlockPosition = new BlockPosition(i7, i6, i9);
/*  88 */             if (!paramWorld.getType(localBlockPosition).getBlock().o()) {
/*  89 */               a(paramWorld, localBlockPosition, b);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  94 */       if (i2 >= i3) {
/*  95 */         i2 = i4;
/*  96 */         i4 = 1;
/*  97 */         i3++;
/*  98 */         if (i3 > m) {
/*  99 */           i3 = m;
/*     */         }
/*     */       } else {
/* 102 */         i2++;
/*     */       }
/*     */     }
/* 105 */     i5 = paramRandom.nextInt(3);
/* 106 */     for (int i6 = 0; i6 < i - i5; i6++) {
/* 107 */       Block localBlock3 = paramWorld.getType(paramBlockPosition.up(i6)).getBlock();
/* 108 */       if ((localBlock3.getMaterial() == Material.AIR) || (localBlock3.getMaterial() == Material.LEAVES)) {
/* 109 */         a(paramWorld, paramBlockPosition.up(i6), a);
/*     */       }
/*     */     }
/* 112 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTaiga2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */