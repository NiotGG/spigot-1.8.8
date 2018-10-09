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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenTrees
/*     */   extends WorldGenTreeAbstract
/*     */ {
/*  21 */   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.OAK);
/*  22 */   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   private final int c;
/*     */   private final boolean d;
/*     */   private final IBlockData e;
/*     */   private final IBlockData f;
/*     */   
/*     */   public WorldGenTrees(boolean paramBoolean)
/*     */   {
/*  30 */     this(paramBoolean, 4, a, b, false);
/*     */   }
/*     */   
/*     */   public WorldGenTrees(boolean paramBoolean1, int paramInt, IBlockData paramIBlockData1, IBlockData paramIBlockData2, boolean paramBoolean2) {
/*  34 */     super(paramBoolean1);
/*  35 */     this.c = paramInt;
/*  36 */     this.e = paramIBlockData1;
/*  37 */     this.f = paramIBlockData2;
/*  38 */     this.d = paramBoolean2;
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  43 */     int i = paramRandom.nextInt(3) + this.c;
/*     */     
/*  45 */     int j = 1;
/*  46 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 > 256)) {
/*  47 */       return false;
/*     */     }
/*     */     int i2;
/*  50 */     for (int k = paramBlockPosition.getY(); k <= paramBlockPosition.getY() + 1 + i; k++) {
/*  51 */       m = 1;
/*  52 */       if (k == paramBlockPosition.getY()) {
/*  53 */         m = 0;
/*     */       }
/*  55 */       if (k >= paramBlockPosition.getY() + 1 + i - 2) {
/*  56 */         m = 2;
/*     */       }
/*  58 */       BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/*  59 */       for (i1 = paramBlockPosition.getX() - m; (i1 <= paramBlockPosition.getX() + m) && (j != 0); i1++) {
/*  60 */         for (i2 = paramBlockPosition.getZ() - m; (i2 <= paramBlockPosition.getZ() + m) && (j != 0); i2++) {
/*  61 */           if ((k >= 0) && (k < 256)) {
/*  62 */             if (!a(paramWorld.getType(localMutableBlockPosition.c(i1, k, i2)).getBlock())) {
/*  63 */               j = 0;
/*     */             }
/*     */           } else {
/*  66 */             j = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  72 */     if (j == 0) {
/*  73 */       return false;
/*     */     }
/*     */     
/*  76 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  77 */     if (((localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.FARMLAND)) || (paramBlockPosition.getY() >= 256 - i - 1)) {
/*  78 */       return false;
/*     */     }
/*     */     
/*  81 */     a(paramWorld, paramBlockPosition.down());
/*     */     
/*  83 */     int m = 3;
/*  84 */     int n = 0;
/*  85 */     int i4; int i6; int i7; BlockPosition localBlockPosition2; Object localObject2; for (int i1 = paramBlockPosition.getY() - m + i; i1 <= paramBlockPosition.getY() + i; i1++) {
/*  86 */       i2 = i1 - (paramBlockPosition.getY() + i);
/*  87 */       i4 = n + 1 - i2 / 2;
/*  88 */       for (int i5 = paramBlockPosition.getX() - i4; i5 <= paramBlockPosition.getX() + i4; i5++) {
/*  89 */         i6 = i5 - paramBlockPosition.getX();
/*  90 */         for (i7 = paramBlockPosition.getZ() - i4; i7 <= paramBlockPosition.getZ() + i4; i7++) {
/*  91 */           int i8 = i7 - paramBlockPosition.getZ();
/*  92 */           if ((Math.abs(i6) != i4) || (Math.abs(i8) != i4) || ((paramRandom.nextInt(2) != 0) && (i2 != 0)))
/*     */           {
/*     */ 
/*  95 */             localBlockPosition2 = new BlockPosition(i5, i1, i7);
/*  96 */             localObject2 = paramWorld.getType(localBlockPosition2).getBlock();
/*  97 */             if ((((Block)localObject2).getMaterial() == Material.AIR) || (((Block)localObject2).getMaterial() == Material.LEAVES) || (((Block)localObject2).getMaterial() == Material.REPLACEABLE_PLANT))
/*  98 */               a(paramWorld, localBlockPosition2, this.f);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 103 */     for (i1 = 0; i1 < i; i1++) {
/* 104 */       Block localBlock2 = paramWorld.getType(paramBlockPosition.up(i1)).getBlock();
/* 105 */       if ((localBlock2.getMaterial() == Material.AIR) || (localBlock2.getMaterial() == Material.LEAVES) || (localBlock2.getMaterial() == Material.REPLACEABLE_PLANT)) {
/* 106 */         a(paramWorld, paramBlockPosition.up(i1), this.e);
/* 107 */         if ((this.d) && (i1 > 0)) {
/* 108 */           if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramBlockPosition.a(-1, i1, 0)))) {
/* 109 */             a(paramWorld, paramBlockPosition.a(-1, i1, 0), BlockVine.EAST);
/*     */           }
/* 111 */           if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramBlockPosition.a(1, i1, 0)))) {
/* 112 */             a(paramWorld, paramBlockPosition.a(1, i1, 0), BlockVine.WEST);
/*     */           }
/* 114 */           if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramBlockPosition.a(0, i1, -1)))) {
/* 115 */             a(paramWorld, paramBlockPosition.a(0, i1, -1), BlockVine.SOUTH);
/*     */           }
/* 117 */           if ((paramRandom.nextInt(3) > 0) && (paramWorld.isEmpty(paramBlockPosition.a(0, i1, 1)))) {
/* 118 */             a(paramWorld, paramBlockPosition.a(0, i1, 1), BlockVine.NORTH);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 124 */     if (this.d) { Object localObject1;
/* 125 */       for (i1 = paramBlockPosition.getY() - 3 + i; i1 <= paramBlockPosition.getY() + i; i1++) {
/* 126 */         int i3 = i1 - (paramBlockPosition.getY() + i);
/* 127 */         i4 = 2 - i3 / 2;
/* 128 */         localObject1 = new BlockPosition.MutableBlockPosition();
/* 129 */         for (i6 = paramBlockPosition.getX() - i4; i6 <= paramBlockPosition.getX() + i4; i6++) {
/* 130 */           for (i7 = paramBlockPosition.getZ() - i4; i7 <= paramBlockPosition.getZ() + i4; i7++) {
/* 131 */             ((BlockPosition.MutableBlockPosition)localObject1).c(i6, i1, i7);
/*     */             
/* 133 */             if (paramWorld.getType((BlockPosition)localObject1).getBlock().getMaterial() == Material.LEAVES) {
/* 134 */               BlockPosition localBlockPosition1 = ((BlockPosition.MutableBlockPosition)localObject1).west();
/* 135 */               localBlockPosition2 = ((BlockPosition.MutableBlockPosition)localObject1).east();
/* 136 */               localObject2 = ((BlockPosition.MutableBlockPosition)localObject1).north();
/* 137 */               BlockPosition localBlockPosition3 = ((BlockPosition.MutableBlockPosition)localObject1).south();
/*     */               
/* 139 */               if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition1).getBlock().getMaterial() == Material.AIR)) {
/* 140 */                 b(paramWorld, localBlockPosition1, BlockVine.EAST);
/*     */               }
/* 142 */               if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition2).getBlock().getMaterial() == Material.AIR)) {
/* 143 */                 b(paramWorld, localBlockPosition2, BlockVine.WEST);
/*     */               }
/* 145 */               if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType((BlockPosition)localObject2).getBlock().getMaterial() == Material.AIR)) {
/* 146 */                 b(paramWorld, (BlockPosition)localObject2, BlockVine.SOUTH);
/*     */               }
/* 148 */               if ((paramRandom.nextInt(4) == 0) && (paramWorld.getType(localBlockPosition3).getBlock().getMaterial() == Material.AIR)) {
/* 149 */                 b(paramWorld, localBlockPosition3, BlockVine.NORTH);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 157 */       if ((paramRandom.nextInt(5) == 0) && (i > 5)) {
/* 158 */         for (i1 = 0; i1 < 2; i1++) {
/* 159 */           for (EnumDirection localEnumDirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
/* 160 */             if (paramRandom.nextInt(4 - i1) == 0) {
/* 161 */               localObject1 = localEnumDirection.opposite();
/* 162 */               a(paramWorld, paramRandom.nextInt(3), paramBlockPosition.a(((EnumDirection)localObject1).getAdjacentX(), i - 5 + i1, ((EnumDirection)localObject1).getAdjacentZ()), localEnumDirection);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, int paramInt, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection) {
/* 172 */     a(paramWorld, paramBlockPosition, Blocks.COCOA.getBlockData().set(BlockCocoa.AGE, Integer.valueOf(paramInt)).set(BlockCocoa.FACING, paramEnumDirection));
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, BlockPosition paramBlockPosition, BlockStateBoolean paramBlockStateBoolean) {
/* 176 */     a(paramWorld, paramBlockPosition, Blocks.VINE.getBlockData().set(paramBlockStateBoolean, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   private void b(World paramWorld, BlockPosition paramBlockPosition, BlockStateBoolean paramBlockStateBoolean) {
/* 180 */     a(paramWorld, paramBlockPosition, paramBlockStateBoolean);
/* 181 */     int i = 4;
/*     */     
/* 183 */     paramBlockPosition = paramBlockPosition.down();
/* 184 */     while ((paramWorld.getType(paramBlockPosition).getBlock().getMaterial() == Material.AIR) && (i > 0)) {
/* 185 */       a(paramWorld, paramBlockPosition, paramBlockStateBoolean);
/* 186 */       paramBlockPosition = paramBlockPosition.down();
/* 187 */       i--;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenTrees.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */