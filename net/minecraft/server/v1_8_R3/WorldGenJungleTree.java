/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenJungleTree
/*     */   extends WorldGenMegaTreeAbstract
/*     */ {
/*     */   public WorldGenJungleTree(boolean paramBoolean, int paramInt1, int paramInt2, IBlockData paramIBlockData1, IBlockData paramIBlockData2)
/*     */   {
/*  15 */     super(paramBoolean, paramInt1, paramInt2, paramIBlockData1, paramIBlockData2);
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  20 */     int i = a(paramRandom);
/*  21 */     if (!a(paramWorld, paramRandom, paramBlockPosition, i)) {
/*  22 */       return false;
/*     */     }
/*     */     
/*  25 */     c(paramWorld, paramBlockPosition.up(i), 2);
/*     */     
/*  27 */     int j = paramBlockPosition.getY() + i - 2 - paramRandom.nextInt(4);
/*     */     
/*     */ 
/*  30 */     while (j > paramBlockPosition.getY() + i / 2) {
/*  31 */       float f = paramRandom.nextFloat() * 3.1415927F * 2.0F;
/*  32 */       int m = paramBlockPosition.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
/*  33 */       int n = paramBlockPosition.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);
/*     */       
/*  35 */       for (int i1 = 0; i1 < 5; i1++) {
/*  36 */         m = paramBlockPosition.getX() + (int)(1.5F + MathHelper.cos(f) * i1);
/*  37 */         n = paramBlockPosition.getZ() + (int)(1.5F + MathHelper.sin(f) * i1);
/*  38 */         a(paramWorld, new BlockPosition(m, j - 3 + i1 / 2, n), this.b);
/*     */       }
/*  40 */       i1 = 1 + paramRandom.nextInt(2);
/*  41 */       int i2 = j;
/*  42 */       for (int i3 = i2 - i1; i3 <= i2; i3++) {
/*  43 */         int i4 = i3 - i2;
/*  44 */         b(paramWorld, new BlockPosition(m, i3, n), 1 - i4);
/*     */       }
/*     */       
/*  47 */       j -= 2 + paramRandom.nextInt(4);
/*     */     }
/*     */     
/*  50 */     for (int k = 0; k < i; k++) {
/*  51 */       BlockPosition localBlockPosition1 = paramBlockPosition.up(k);
/*  52 */       if (a(paramWorld.getType(localBlockPosition1).getBlock())) {
/*  53 */         a(paramWorld, localBlockPosition1, this.b);
/*  54 */         if (k > 0) {
/*  55 */           a(paramWorld, paramRandom, localBlockPosition1.west(), BlockVine.EAST);
/*  56 */           a(paramWorld, paramRandom, localBlockPosition1.north(), BlockVine.SOUTH);
/*     */         }
/*     */       }
/*     */       
/*  60 */       if (k < i - 1) {
/*  61 */         BlockPosition localBlockPosition2 = localBlockPosition1.east();
/*  62 */         if (a(paramWorld.getType(localBlockPosition2).getBlock())) {
/*  63 */           a(paramWorld, localBlockPosition2, this.b);
/*  64 */           if (k > 0) {
/*  65 */             a(paramWorld, paramRandom, localBlockPosition2.east(), BlockVine.WEST);
/*  66 */             a(paramWorld, paramRandom, localBlockPosition2.north(), BlockVine.SOUTH);
/*     */           }
/*     */         }
/*     */         
/*  70 */         BlockPosition localBlockPosition3 = localBlockPosition1.south().east();
/*  71 */         if (a(paramWorld.getType(localBlockPosition3).getBlock())) {
/*  72 */           a(paramWorld, localBlockPosition3, this.b);
/*  73 */           if (k > 0) {
/*  74 */             a(paramWorld, paramRandom, localBlockPosition3.east(), BlockVine.WEST);
/*  75 */             a(paramWorld, paramRandom, localBlockPosition3.south(), BlockVine.NORTH);
/*     */           }
/*     */         }
/*     */         
/*  79 */         BlockPosition localBlockPosition4 = localBlockPosition1.south();
/*  80 */         if (a(paramWorld.getType(localBlockPosition4).getBlock())) {
/*  81 */           a(paramWorld, localBlockPosition4, this.b);
/*  82 */           if (k > 0) {
/*  83 */             a(paramWorld, paramRandom, localBlockPosition4.west(), BlockVine.EAST);
/*  84 */             a(paramWorld, paramRandom, localBlockPosition4.south(), BlockVine.NORTH);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition, BlockStateBoolean paramBlockStateBoolean) {
/*  94 */     if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramBlockPosition))) {
/*  95 */       a(paramWorld, paramBlockPosition, Blocks.VINE.getBlockData().set(paramBlockStateBoolean, Boolean.valueOf(true)));
/*     */     }
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, BlockPosition paramBlockPosition, int paramInt) {
/* 100 */     int i = 2;
/* 101 */     for (int j = -i; j <= 0; j++) {
/* 102 */       a(paramWorld, paramBlockPosition.up(j), paramInt + 1 - j);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenJungleTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */