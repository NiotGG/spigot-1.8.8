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
/*     */ public class WorldGenAcaciaTree
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*  16 */   private static final IBlockData a = Blocks.LOG2.getBlockData().set(BlockLog2.VARIANT, BlockWood.EnumLogVariant.ACACIA);
/*  17 */   private static final IBlockData b = Blocks.LEAVES2.getBlockData().set(BlockLeaves2.VARIANT, BlockWood.EnumLogVariant.ACACIA).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenAcaciaTree(boolean paramBoolean) {
/*  20 */     super(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  25 */     Material localMaterial1 = paramRandom.nextInt(3) + paramRandom.nextInt(3) + 5;
/*     */     
/*  27 */     int i = 1;
/*  28 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + localMaterial1 + 1 > 256)) {
/*  29 */       return false;
/*     */     }
/*     */     
/*  32 */     for (int j = paramBlockPosition.getY(); j <= paramBlockPosition.getY() + 1 + localMaterial1; j++) {
/*  33 */       int k = 1;
/*  34 */       if (j == paramBlockPosition.getY()) {
/*  35 */         k = 0;
/*     */       }
/*  37 */       if (j >= paramBlockPosition.getY() + 1 + localMaterial1 - 2) {
/*  38 */         k = 2;
/*     */       }
/*  40 */       BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/*  41 */       for (n = paramBlockPosition.getX() - k; (n <= paramBlockPosition.getX() + k) && (i != 0); n++) {
/*  42 */         for (i1 = paramBlockPosition.getZ() - k; (i1 <= paramBlockPosition.getZ() + k) && (i != 0); i1++) {
/*  43 */           if ((j >= 0) && (j < 256)) {
/*  44 */             if (!a(paramWorld.getType(localMutableBlockPosition.c(n, j, i1)).getBlock())) {
/*  45 */               i = 0;
/*     */             }
/*     */           } else {
/*  48 */             i = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  54 */     if (i == 0) {
/*  55 */       return false;
/*     */     }
/*     */     
/*  58 */     Block localBlock = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  59 */     if (((localBlock != Blocks.GRASS) && (localBlock != Blocks.DIRT)) || (paramBlockPosition.getY() >= 256 - localMaterial1 - 1)) {
/*  60 */       return false;
/*     */     }
/*     */     
/*  63 */     a(paramWorld, paramBlockPosition.down());
/*     */     
/*  65 */     EnumDirection localEnumDirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom);
/*  66 */     int m = localMaterial1 - paramRandom.nextInt(4) - 1;
/*  67 */     int n = 3 - paramRandom.nextInt(3);
/*     */     
/*  69 */     int i1 = paramBlockPosition.getX();int i2 = paramBlockPosition.getZ();
/*  70 */     int i3 = 0;
/*  71 */     Object localObject2; for (int i4 = 0; i4 < localMaterial1; i4++) {
/*  72 */       i5 = paramBlockPosition.getY() + i4;
/*  73 */       if ((i4 >= m) && (n > 0)) {
/*  74 */         i1 += localEnumDirection.getAdjacentX();
/*  75 */         i2 += localEnumDirection.getAdjacentZ();
/*  76 */         n--;
/*     */       }
/*  78 */       BlockPosition localBlockPosition1 = new BlockPosition(i1, i5, i2);
/*  79 */       localObject2 = paramWorld.getType(localBlockPosition1).getBlock().getMaterial();
/*  80 */       if ((localObject2 == Material.AIR) || (localObject2 == Material.LEAVES)) {
/*  81 */         b(paramWorld, localBlockPosition1);
/*  82 */         i3 = i5;
/*     */       }
/*     */     }
/*     */     
/*  86 */     Object localObject1 = new BlockPosition(i1, i3, i2);
/*  87 */     int i6; for (int i5 = -3; i5 <= 3; i5++) {
/*  88 */       for (i6 = -3; i6 <= 3; i6++) {
/*  89 */         if ((Math.abs(i5) != 3) || (Math.abs(i6) != 3))
/*     */         {
/*     */ 
/*  92 */           c(paramWorld, ((BlockPosition)localObject1).a(i5, 0, i6));
/*     */         }
/*     */       }
/*     */     }
/*  96 */     localObject1 = ((BlockPosition)localObject1).up();
/*     */     
/*  98 */     for (i5 = -1; i5 <= 1; i5++) {
/*  99 */       for (i6 = -1; i6 <= 1; i6++) {
/* 100 */         c(paramWorld, ((BlockPosition)localObject1).a(i5, 0, i6));
/*     */       }
/*     */     }
/*     */     
/* 104 */     c(paramWorld, ((BlockPosition)localObject1).east(2));
/* 105 */     c(paramWorld, ((BlockPosition)localObject1).west(2));
/* 106 */     c(paramWorld, ((BlockPosition)localObject1).south(2));
/* 107 */     c(paramWorld, ((BlockPosition)localObject1).north(2));
/*     */     
/*     */ 
/* 110 */     i1 = paramBlockPosition.getX();
/* 111 */     i2 = paramBlockPosition.getZ();
/* 112 */     localObject1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(paramRandom);
/* 113 */     if (localObject1 != localEnumDirection) {
/* 114 */       i5 = m - paramRandom.nextInt(2) - 1;
/* 115 */       i6 = 1 + paramRandom.nextInt(3);
/*     */       
/* 117 */       i3 = 0;
/* 118 */       int i7; for (localObject2 = i5; (localObject2 < localMaterial1) && (i6 > 0); i6--) {
/* 119 */         if (localObject2 >= 1)
/*     */         {
/*     */ 
/* 122 */           i7 = paramBlockPosition.getY() + localObject2;
/* 123 */           i1 += ((EnumDirection)localObject1).getAdjacentX();
/* 124 */           i2 += ((EnumDirection)localObject1).getAdjacentZ();
/* 125 */           BlockPosition localBlockPosition2 = new BlockPosition(i1, i7, i2);
/* 126 */           Material localMaterial2 = paramWorld.getType(localBlockPosition2).getBlock().getMaterial();
/* 127 */           if ((localMaterial2 == Material.AIR) || (localMaterial2 == Material.LEAVES)) {
/* 128 */             b(paramWorld, localBlockPosition2);
/* 129 */             i3 = i7;
/*     */           }
/*     */         }
/* 118 */         localObject2++;
/*     */       }
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
/*     */ 
/* 132 */       if (i3 > 0) {
/* 133 */         localObject2 = new BlockPosition(i1, i3, i2);
/* 134 */         int i8; for (i7 = -2; i7 <= 2; i7++) {
/* 135 */           for (i8 = -2; i8 <= 2; i8++) {
/* 136 */             if ((Math.abs(i7) != 2) || (Math.abs(i8) != 2))
/*     */             {
/*     */ 
/* 139 */               c(paramWorld, ((BlockPosition)localObject2).a(i7, 0, i8)); }
/*     */           }
/*     */         }
/* 142 */         localObject2 = ((BlockPosition)localObject2).up();
/* 143 */         for (i7 = -1; i7 <= 1; i7++) {
/* 144 */           for (i8 = -1; i8 <= 1; i8++) {
/* 145 */             c(paramWorld, ((BlockPosition)localObject2).a(i7, 0, i8));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 151 */     return true;
/*     */   }
/*     */   
/*     */   private void b(World paramWorld, BlockPosition paramBlockPosition) {
/* 155 */     a(paramWorld, paramBlockPosition, a);
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, BlockPosition paramBlockPosition) {
/* 159 */     Material localMaterial = paramWorld.getType(paramBlockPosition).getBlock().getMaterial();
/* 160 */     if ((localMaterial == Material.AIR) || (localMaterial == Material.LEAVES)) {
/* 161 */       a(paramWorld, paramBlockPosition, b);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenAcaciaTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */