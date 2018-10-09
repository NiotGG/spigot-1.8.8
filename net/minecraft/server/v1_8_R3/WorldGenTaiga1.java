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
/*     */ public class WorldGenTaiga1
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*  17 */   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
/*  18 */   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenTaiga1() {
/*  21 */     super(false);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  27 */     int i = paramRandom.nextInt(5) + 7;
/*  28 */     int j = i - paramRandom.nextInt(2) - 3;
/*  29 */     int k = i - j;
/*  30 */     int m = 1 + paramRandom.nextInt(k + 1);
/*     */     
/*  32 */     int n = 1;
/*     */     
/*  34 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 > 256)) {
/*  35 */       return false;
/*     */     }
/*     */     int i4;
/*     */     int i5;
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
/*  50 */             if (!a(paramWorld.getType(localMutableBlockPosition.c(i4, i1, i5)).getBlock())) {
/*  51 */               n = 0;
/*     */             }
/*     */           } else {
/*  54 */             n = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  60 */     if (n == 0) {
/*  61 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  65 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  66 */     if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT)) || (paramBlockPosition.getY() >= 256 - i - 1)) {
/*  67 */       return false;
/*     */     }
/*     */     
/*  70 */     a(paramWorld, paramBlockPosition.down());
/*     */     
/*     */ 
/*  73 */     int i2 = 0;
/*  74 */     for (int i3 = paramBlockPosition.getY() + i; i3 >= paramBlockPosition.getY() + j; i3--) {
/*  75 */       for (i4 = paramBlockPosition.getX() - i2; i4 <= paramBlockPosition.getX() + i2; i4++) {
/*  76 */         i5 = i4 - paramBlockPosition.getX();
/*  77 */         for (int i6 = paramBlockPosition.getZ() - i2; i6 <= paramBlockPosition.getZ() + i2; i6++) {
/*  78 */           int i7 = i6 - paramBlockPosition.getZ();
/*  79 */           if ((Math.abs(i5) != i2) || (Math.abs(i7) != i2) || (i2 <= 0))
/*     */           {
/*     */ 
/*  82 */             BlockPosition localBlockPosition = new BlockPosition(i4, i3, i6);
/*  83 */             if (!paramWorld.getType(localBlockPosition).getBlock().o()) {
/*  84 */               a(paramWorld, localBlockPosition, b);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  89 */       if ((i2 >= 1) && (i3 == paramBlockPosition.getY() + j + 1)) {
/*  90 */         i2--;
/*  91 */       } else if (i2 < m) {
/*  92 */         i2++;
/*     */       }
/*     */     }
/*  95 */     for (i3 = 0; i3 < i - 1; i3++) {
/*  96 */       Block localBlock2 = paramWorld.getType(paramBlockPosition.up(i3)).getBlock();
/*  97 */       if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES)) {
/*  98 */         a(paramWorld, paramBlockPosition.up(i3), a);
/*     */       }
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTaiga1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */