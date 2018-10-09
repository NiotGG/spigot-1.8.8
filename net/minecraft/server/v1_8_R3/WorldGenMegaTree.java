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
/*     */ public class WorldGenMegaTree
/*     */   extends WorldGenMegaTreeAbstract
/*     */ {
/*  19 */   private static final IBlockData e = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
/*  20 */   private static final IBlockData f = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  21 */   private static final IBlockData g = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.PODZOL);
/*     */   private boolean h;
/*     */   
/*     */   public WorldGenMegaTree(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  26 */     super(paramBoolean1, 13, 15, e, f);
/*  27 */     this.h = paramBoolean2;
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  32 */     int i = a(paramRandom);
/*  33 */     if (!a(paramWorld, paramRandom, paramBlockPosition, i)) {
/*  34 */       return false;
/*     */     }
/*     */     
/*  37 */     a(paramWorld, paramBlockPosition.getX(), paramBlockPosition.getZ(), paramBlockPosition.getY() + i, 0, paramRandom);
/*     */     
/*  39 */     for (int j = 0; j < i; j++) {
/*  40 */       Block localBlock = paramWorld.getType(paramBlockPosition.up(j)).getBlock();
/*  41 */       if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
/*  42 */         a(paramWorld, paramBlockPosition.up(j), this.b);
/*     */       }
/*  44 */       if (j < i - 1) {
/*  45 */         localBlock = paramWorld.getType(paramBlockPosition.a(1, j, 0)).getBlock();
/*  46 */         if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
/*  47 */           a(paramWorld, paramBlockPosition.a(1, j, 0), this.b);
/*     */         }
/*  49 */         localBlock = paramWorld.getType(paramBlockPosition.a(1, j, 1)).getBlock();
/*  50 */         if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
/*  51 */           a(paramWorld, paramBlockPosition.a(1, j, 1), this.b);
/*     */         }
/*  53 */         localBlock = paramWorld.getType(paramBlockPosition.a(0, j, 1)).getBlock();
/*  54 */         if ((localBlock.getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) {
/*  55 */           a(paramWorld, paramBlockPosition.a(0, j, 1), this.b);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  60 */     return true;
/*     */   }
/*     */   
/*     */   private void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Random paramRandom) {
/*  64 */     int i = paramRandom.nextInt(5) + (this.h ? this.a : 3);
/*     */     
/*  66 */     int j = 0;
/*  67 */     for (int k = paramInt3 - i; k <= paramInt3; k++) {
/*  68 */       int m = paramInt3 - k;
/*  69 */       int n = paramInt4 + MathHelper.d(m / i * 3.5F);
/*  70 */       a(paramWorld, new BlockPosition(paramInt1, k, paramInt2), n + ((m > 0) && (n == j) && ((k & 0x1) == 0) ? 1 : 0));
/*  71 */       j = n;
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  77 */     b(paramWorld, paramBlockPosition.west().north());
/*  78 */     b(paramWorld, paramBlockPosition.east(2).north());
/*  79 */     b(paramWorld, paramBlockPosition.west().south(2));
/*  80 */     b(paramWorld, paramBlockPosition.east(2).south(2));
/*     */     
/*  82 */     for (int i = 0; i < 5; i++) {
/*  83 */       int j = paramRandom.nextInt(64);
/*  84 */       int k = j % 8;
/*  85 */       int m = j / 8;
/*  86 */       if ((k == 0) || (k == 7) || (m == 0) || (m == 7)) {
/*  87 */         b(paramWorld, paramBlockPosition.a(-3 + k, 0, -3 + m));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void b(World paramWorld, BlockPosition paramBlockPosition) {
/*  93 */     for (int i = -2; i <= 2; i++) {
/*  94 */       for (int j = -2; j <= 2; j++) {
/*  95 */         if ((Math.abs(i) != 2) || (Math.abs(j) != 2)) {
/*  96 */           c(paramWorld, paramBlockPosition.a(i, 0, j));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void c(World paramWorld, BlockPosition paramBlockPosition) {
/* 103 */     for (int i = 2; i >= -3; i--) {
/* 104 */       BlockPosition localBlockPosition = paramBlockPosition.up(i);
/* 105 */       Block localBlock = paramWorld.getType(localBlockPosition).getBlock();
/* 106 */       if ((localBlock == Blocks.GRASS) || (localBlock == Blocks.DIRT)) {
/* 107 */         a(paramWorld, localBlockPosition, g);
/*     */       } else {
/* 109 */         if ((localBlock.getMaterial() != Material.AIR) && (i < 0)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMegaTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */