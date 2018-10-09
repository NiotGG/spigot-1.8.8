/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenHugeMushroom
/*     */   extends WorldGenerator
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   public WorldGenHugeMushroom(Block paramBlock)
/*     */   {
/*  16 */     super(true);
/*  17 */     this.a = paramBlock;
/*     */   }
/*     */   
/*     */   public WorldGenHugeMushroom() {
/*  21 */     super(false);
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  26 */     if (this.a == null) {
/*  27 */       this.a = (paramRandom.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK);
/*     */     }
/*     */     
/*  30 */     int i = paramRandom.nextInt(3) + 4;
/*     */     
/*  32 */     int j = 1;
/*  33 */     if ((paramBlockPosition.getY() < 1) || (paramBlockPosition.getY() + i + 1 >= 256))
/*  34 */       return false;
/*     */     int n;
/*     */     int i1;
/*  37 */     for (int k = paramBlockPosition.getY(); k <= paramBlockPosition.getY() + 1 + i; k++) {
/*  38 */       localMutableBlockPosition1 = 3;
/*  39 */       if (k <= paramBlockPosition.getY() + 3) {
/*  40 */         localMutableBlockPosition1 = 0;
/*     */       }
/*  42 */       localMutableBlockPosition2 = new BlockPosition.MutableBlockPosition();
/*  43 */       for (n = paramBlockPosition.getX() - localMutableBlockPosition1; (n <= paramBlockPosition.getX() + localMutableBlockPosition1) && (j != 0); n++) {
/*  44 */         for (i1 = paramBlockPosition.getZ() - localMutableBlockPosition1; (i1 <= paramBlockPosition.getZ() + localMutableBlockPosition1) && (j != 0); i1++) {
/*  45 */           if ((k >= 0) && (k < 256)) {
/*  46 */             Block localBlock3 = paramWorld.getType(localMutableBlockPosition2.c(n, k, i1)).getBlock();
/*  47 */             if ((localBlock3.getMaterial() != Material.AIR) && (localBlock3.getMaterial() != Material.LEAVES)) {
/*  48 */               j = 0;
/*     */             }
/*     */           } else {
/*  51 */             j = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  57 */     if (j == 0) {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     Block localBlock1 = paramWorld.getType(paramBlockPosition.down()).getBlock();
/*  62 */     if ((localBlock1 != Blocks.DIRT) && (localBlock1 != Blocks.GRASS) && (localBlock1 != Blocks.MYCELIUM)) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     BlockPosition.MutableBlockPosition localMutableBlockPosition1 = paramBlockPosition.getY() + i;
/*  67 */     if (this.a == Blocks.RED_MUSHROOM_BLOCK) {
/*  68 */       localMutableBlockPosition1 = paramBlockPosition.getY() + i - 3;
/*     */     }
/*  70 */     for (BlockPosition.MutableBlockPosition localMutableBlockPosition2 = localMutableBlockPosition1; localMutableBlockPosition2 <= paramBlockPosition.getY() + i; localMutableBlockPosition2++) {
/*  71 */       n = 1;
/*  72 */       if (localMutableBlockPosition2 < paramBlockPosition.getY() + i) {
/*  73 */         n++;
/*     */       }
/*  75 */       if (this.a == Blocks.BROWN_MUSHROOM_BLOCK) {
/*  76 */         n = 3;
/*     */       }
/*  78 */       i1 = paramBlockPosition.getX() - n;
/*  79 */       int i2 = paramBlockPosition.getX() + n;
/*  80 */       int i3 = paramBlockPosition.getZ() - n;
/*  81 */       int i4 = paramBlockPosition.getZ() + n;
/*  82 */       for (int i5 = i1; i5 <= i2; i5++) {
/*  83 */         for (int i6 = i3; i6 <= i4; i6++) {
/*  84 */           int i7 = 5;
/*  85 */           if (i5 == i1) {
/*  86 */             i7--;
/*  87 */           } else if (i5 == i2) {
/*  88 */             i7++;
/*     */           }
/*  90 */           if (i6 == i3) {
/*  91 */             i7 -= 3;
/*  92 */           } else if (i6 == i4) {
/*  93 */             i7 += 3;
/*     */           }
/*     */           
/*  96 */           BlockHugeMushroom.EnumHugeMushroomVariant localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.a(i7);
/*     */           
/*  98 */           if ((this.a == Blocks.BROWN_MUSHROOM_BLOCK) || (localMutableBlockPosition2 < paramBlockPosition.getY() + i)) {
/*  99 */             if (((i5 == i1) || (i5 == i2)) && ((i6 == i3) || (i6 == i4))) {
/*     */               continue;
/*     */             }
/* 102 */             if ((i5 == paramBlockPosition.getX() - (n - 1)) && (i6 == i3)) {
/* 103 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST;
/*     */             }
/* 105 */             if ((i5 == i1) && (i6 == paramBlockPosition.getZ() - (n - 1))) {
/* 106 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_WEST;
/*     */             }
/*     */             
/* 109 */             if ((i5 == paramBlockPosition.getX() + (n - 1)) && (i6 == i3)) {
/* 110 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST;
/*     */             }
/* 112 */             if ((i5 == i2) && (i6 == paramBlockPosition.getZ() - (n - 1))) {
/* 113 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.NORTH_EAST;
/*     */             }
/*     */             
/* 116 */             if ((i5 == paramBlockPosition.getX() - (n - 1)) && (i6 == i4)) {
/* 117 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST;
/*     */             }
/* 119 */             if ((i5 == i1) && (i6 == paramBlockPosition.getZ() + (n - 1))) {
/* 120 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_WEST;
/*     */             }
/*     */             
/* 123 */             if ((i5 == paramBlockPosition.getX() + (n - 1)) && (i6 == i4)) {
/* 124 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST;
/*     */             }
/* 126 */             if ((i5 == i2) && (i6 == paramBlockPosition.getZ() + (n - 1))) {
/* 127 */               localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.SOUTH_EAST;
/*     */             }
/*     */           }
/*     */           
/* 131 */           if ((localEnumHugeMushroomVariant == BlockHugeMushroom.EnumHugeMushroomVariant.CENTER) && (localMutableBlockPosition2 < paramBlockPosition.getY() + i)) {
/* 132 */             localEnumHugeMushroomVariant = BlockHugeMushroom.EnumHugeMushroomVariant.ALL_INSIDE;
/*     */           }
/* 134 */           if ((paramBlockPosition.getY() >= paramBlockPosition.getY() + i - 1) || (localEnumHugeMushroomVariant != BlockHugeMushroom.EnumHugeMushroomVariant.ALL_INSIDE)) {
/* 135 */             BlockPosition localBlockPosition = new BlockPosition(i5, localMutableBlockPosition2, i6);
/* 136 */             if (!paramWorld.getType(localBlockPosition).getBlock().o()) {
/* 137 */               a(paramWorld, localBlockPosition, this.a.getBlockData().set(BlockHugeMushroom.VARIANT, localEnumHugeMushroomVariant));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 143 */     for (int m = 0; m < i; m++) {
/* 144 */       Block localBlock2 = paramWorld.getType(paramBlockPosition.up(m)).getBlock();
/* 145 */       if (!localBlock2.o()) {
/* 146 */         a(paramWorld, paramBlockPosition.up(m), this.a.getBlockData().set(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.STEM));
/*     */       }
/*     */     }
/* 149 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenHugeMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */