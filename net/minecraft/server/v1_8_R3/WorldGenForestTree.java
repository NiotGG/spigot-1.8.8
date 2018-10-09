/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenForestTree extends WorldGenTreeAbstract
/*     */ {
/*   7 */   private static final IBlockData a = Blocks.LOG2.getBlockData().set(BlockLog2.VARIANT, BlockWood.EnumLogVariant.DARK_OAK);
/*   8 */   private static final IBlockData b = Blocks.LEAVES2.getBlockData().set(BlockLeaves2.VARIANT, BlockWood.EnumLogVariant.DARK_OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenForestTree(boolean flag) {
/*  11 */     super(flag);
/*     */   }
/*     */   
/*     */   public boolean generate(World world, Random random, BlockPosition blockposition) {
/*  15 */     int i = random.nextInt(3) + random.nextInt(2) + 6;
/*  16 */     int j = blockposition.getX();
/*  17 */     int k = blockposition.getY();
/*  18 */     int l = blockposition.getZ();
/*     */     
/*  20 */     if ((k >= 1) && (k + i + 1 < 256)) {
/*  21 */       BlockPosition blockposition1 = blockposition.down();
/*  22 */       Block block = world.getType(blockposition1).getBlock();
/*     */       
/*  24 */       if ((block != Blocks.GRASS) && (block != Blocks.DIRT))
/*  25 */         return false;
/*  26 */       if (!a(world, blockposition, i)) {
/*  27 */         return false;
/*     */       }
/*  29 */       a(world, blockposition1);
/*  30 */       a(world, blockposition1.east());
/*  31 */       a(world, blockposition1.south());
/*  32 */       a(world, blockposition1.south().east());
/*  33 */       EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random);
/*  34 */       int i1 = i - random.nextInt(4);
/*  35 */       int j1 = 2 - random.nextInt(3);
/*  36 */       int k1 = j;
/*  37 */       int l1 = l;
/*  38 */       int i2 = k + i - 1;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*  43 */       for (int j2 = 0; j2 < i; j2++) {
/*  44 */         if ((j2 >= i1) && (j1 > 0)) {
/*  45 */           k1 += enumdirection.getAdjacentX();
/*  46 */           l1 += enumdirection.getAdjacentZ();
/*  47 */           j1--;
/*     */         }
/*     */         
/*  50 */         int k2 = k + j2;
/*  51 */         BlockPosition blockposition2 = new BlockPosition(k1, k2, l1);
/*  52 */         Material material = world.getType(blockposition2).getBlock().getMaterial();
/*     */         
/*  54 */         if ((material == Material.AIR) || (material == Material.LEAVES)) {
/*  55 */           b(world, blockposition2);
/*  56 */           b(world, blockposition2.east());
/*  57 */           b(world, blockposition2.south());
/*  58 */           b(world, blockposition2.east().south());
/*     */         }
/*     */       }
/*     */       
/*  62 */       for (j2 = -2; j2 <= 0; j2++) {
/*  63 */         for (int k2 = -2; k2 <= 0; k2++) {
/*  64 */           byte b0 = -1;
/*     */           
/*  66 */           a(world, k1 + j2, i2 + b0, l1 + k2);
/*  67 */           a(world, 1 + k1 - j2, i2 + b0, l1 + k2);
/*  68 */           a(world, k1 + j2, i2 + b0, 1 + l1 - k2);
/*  69 */           a(world, 1 + k1 - j2, i2 + b0, 1 + l1 - k2);
/*  70 */           if (((j2 > -2) || (k2 > -1)) && ((j2 != -1) || (k2 != -2))) {
/*  71 */             byte b1 = 1;
/*     */             
/*  73 */             a(world, k1 + j2, i2 + b1, l1 + k2);
/*  74 */             a(world, 1 + k1 - j2, i2 + b1, l1 + k2);
/*  75 */             a(world, k1 + j2, i2 + b1, 1 + l1 - k2);
/*  76 */             a(world, 1 + k1 - j2, i2 + b1, 1 + l1 - k2);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  81 */       if (random.nextBoolean()) {
/*  82 */         a(world, k1, i2 + 2, l1);
/*  83 */         a(world, k1 + 1, i2 + 2, l1);
/*  84 */         a(world, k1 + 1, i2 + 2, l1 + 1);
/*  85 */         a(world, k1, i2 + 2, l1 + 1);
/*     */       }
/*     */       
/*  88 */       for (j2 = -3; j2 <= 4; j2++) {
/*  89 */         for (int k2 = -3; k2 <= 4; k2++) {
/*  90 */           if (((j2 != -3) || (k2 != -3)) && ((j2 != -3) || (k2 != 4)) && ((j2 != 4) || (k2 != -3)) && ((j2 != 4) || (k2 != 4)) && ((Math.abs(j2) < 3) || (Math.abs(k2) < 3))) {
/*  91 */             a(world, k1 + j2, i2, l1 + k2);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  96 */       for (j2 = -1; j2 <= 2; j2++) {
/*  97 */         for (int k2 = -1; k2 <= 2; k2++) {
/*  98 */           if (((j2 < 0) || (j2 > 1) || (k2 < 0) || (k2 > 1)) && (random.nextInt(3) <= 0)) {
/*  99 */             int l2 = random.nextInt(3) + 2;
/*     */             
/*     */ 
/*     */ 
/* 103 */             for (int i3 = 0; i3 < l2; i3++) {
/* 104 */               b(world, new BlockPosition(j + j2, i2 - i3 - 1, l + k2));
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 109 */             for (i3 = -1; i3 <= 1; i3++) {
/* 110 */               for (int j3 = -1; j3 <= 1; j3++) {
/* 111 */                 a(world, k1 + j2 + i3, i2, l1 + k2 + j3);
/*     */               }
/*     */             }
/*     */             
/* 115 */             for (i3 = -2; i3 <= 2; i3++) {
/* 116 */               for (int j3 = -2; j3 <= 2; j3++) {
/* 117 */                 if ((Math.abs(i3) != 2) || (Math.abs(j3) != 2)) {
/* 118 */                   a(world, k1 + j2 + i3, i2 - 1, l1 + k2 + j3);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(World world, BlockPosition blockposition, int i)
/*     */   {
/* 134 */     int j = blockposition.getX();
/* 135 */     int k = blockposition.getY();
/* 136 */     int l = blockposition.getZ();
/* 137 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */     
/* 139 */     for (int i1 = 0; i1 <= i + 1; i1++) {
/* 140 */       byte b0 = 1;
/*     */       
/* 142 */       if (i1 == 0) {
/* 143 */         b0 = 0;
/*     */       }
/*     */       
/* 146 */       if (i1 >= i - 1) {
/* 147 */         b0 = 2;
/*     */       }
/*     */       
/* 150 */       for (int j1 = -b0; j1 <= b0; j1++) {
/* 151 */         for (int k1 = -b0; k1 <= b0; k1++) {
/* 152 */           if (!a(world.getType(blockposition_mutableblockposition.c(j + j1, k + i1, l + k1)).getBlock())) {
/* 153 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   private void b(World world, BlockPosition blockposition) {
/* 163 */     if (a(world.getType(blockposition).getBlock())) {
/* 164 */       a(world, blockposition, a);
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k)
/*     */   {
/* 170 */     BlockPosition blockposition = new BlockPosition(i, j, k);
/* 171 */     Block block = world.getType(blockposition).getBlock();
/*     */     
/* 173 */     if (block.getMaterial() == Material.AIR) {
/* 174 */       a(world, blockposition, b);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenForestTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */