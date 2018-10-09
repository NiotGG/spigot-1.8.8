/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class WorldGenMegaTreeAbstract extends WorldGenTreeAbstract
/*     */ {
/*     */   protected final int a;
/*     */   protected final IBlockData b;
/*     */   protected final IBlockData c;
/*     */   protected int d;
/*     */   
/*     */   public WorldGenMegaTreeAbstract(boolean flag, int i, int j, IBlockData iblockdata, IBlockData iblockdata1) {
/*  13 */     super(flag);
/*  14 */     this.a = i;
/*  15 */     this.d = j;
/*  16 */     this.b = iblockdata;
/*  17 */     this.c = iblockdata1;
/*     */   }
/*     */   
/*     */   protected int a(Random random) {
/*  21 */     int i = random.nextInt(3) + this.a;
/*     */     
/*  23 */     if (this.d > 1) {
/*  24 */       i += random.nextInt(this.d);
/*     */     }
/*     */     
/*  27 */     return i;
/*     */   }
/*     */   
/*     */   private boolean c(World world, BlockPosition blockposition, int i) {
/*  31 */     boolean flag = true;
/*     */     
/*  33 */     if ((blockposition.getY() >= 1) && (blockposition.getY() + i + 1 <= 256)) {
/*  34 */       for (int j = 0; j <= 1 + i; j++) {
/*  35 */         byte b0 = 2;
/*     */         
/*  37 */         if (j == 0) {
/*  38 */           b0 = 1;
/*  39 */         } else if (j >= 1 + i - 2) {
/*  40 */           b0 = 2;
/*     */         }
/*     */         
/*  43 */         for (int k = -b0; (k <= b0) && (flag); k++) {
/*  44 */           for (int l = -b0; (l <= b0) && (flag); l++) {
/*  45 */             if ((blockposition.getY() + j < 0) || (blockposition.getY() + j >= 256) || ((!a(world.getType(blockposition.a(k, j, l)).getBlock())) && (world.getType(blockposition.a(k, j, l)).getBlock() != Blocks.SAPLING))) {
/*  46 */               flag = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  52 */       return flag;
/*     */     }
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(BlockPosition blockposition, World world)
/*     */   {
/*  59 */     BlockPosition blockposition1 = blockposition.down();
/*  60 */     Block block = world.getType(blockposition1).getBlock();
/*     */     
/*  62 */     if (((block == Blocks.GRASS) || (block == Blocks.DIRT)) && (blockposition.getY() >= 2)) {
/*  63 */       a(world, blockposition1);
/*  64 */       a(world, blockposition1.east());
/*  65 */       a(world, blockposition1.south());
/*  66 */       a(world, blockposition1.south().east());
/*  67 */       return true;
/*     */     }
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean a(World world, Random random, BlockPosition blockposition, int i)
/*     */   {
/*  74 */     return (c(world, blockposition, i)) && (a(blockposition, world));
/*     */   }
/*     */   
/*     */   protected void a(World world, BlockPosition blockposition, int i) {
/*  78 */     int j = i * i;
/*     */     
/*  80 */     for (int k = -i; k <= i + 1; k++) {
/*  81 */       for (int l = -i; l <= i + 1; l++) {
/*  82 */         int i1 = k - 1;
/*  83 */         int j1 = l - 1;
/*     */         
/*  85 */         if ((k * k + l * l <= j) || (i1 * i1 + j1 * j1 <= j) || (k * k + j1 * j1 <= j) || (i1 * i1 + l * l <= j)) {
/*  86 */           BlockPosition blockposition1 = blockposition.a(k, 0, l);
/*  87 */           Material material = world.getType(blockposition1).getBlock().getMaterial();
/*     */           
/*  89 */           if ((material == Material.AIR) || (material == Material.LEAVES)) {
/*  90 */             a(world, blockposition1, this.c);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(World world, BlockPosition blockposition, int i)
/*     */   {
/*  99 */     int j = i * i;
/*     */     
/* 101 */     for (int k = -i; k <= i; k++) {
/* 102 */       for (int l = -i; l <= i; l++) {
/* 103 */         if (k * k + l * l <= j) {
/* 104 */           BlockPosition blockposition1 = blockposition.a(k, 0, l);
/* 105 */           Material material = world.getType(blockposition1).getBlock().getMaterial();
/*     */           
/* 107 */           if ((material == Material.AIR) || (material == Material.LEAVES)) {
/* 108 */             a(world, blockposition1, this.c);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMegaTreeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */