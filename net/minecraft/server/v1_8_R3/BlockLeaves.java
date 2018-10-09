/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.LeavesDecayEvent;
/*     */ 
/*     */ public abstract class BlockLeaves extends BlockTransparent
/*     */ {
/*   9 */   public static final BlockStateBoolean DECAYABLE = BlockStateBoolean.of("decayable");
/*  10 */   public static final BlockStateBoolean CHECK_DECAY = BlockStateBoolean.of("check_decay");
/*     */   int[] N;
/*     */   
/*     */   public BlockLeaves() {
/*  14 */     super(Material.LEAVES, false);
/*  15 */     a(true);
/*  16 */     a(CreativeModeTab.c);
/*  17 */     c(0.2F);
/*  18 */     e(1);
/*  19 */     a(h);
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  23 */     byte b0 = 1;
/*  24 */     int i = b0 + 1;
/*  25 */     int j = blockposition.getX();
/*  26 */     int k = blockposition.getY();
/*  27 */     int l = blockposition.getZ();
/*     */     
/*  29 */     if (world.areChunksLoadedBetween(new BlockPosition(j - i, k - i, l - i), new BlockPosition(j + i, k + i, l + i))) {
/*  30 */       for (int i1 = -b0; i1 <= b0; i1++) {
/*  31 */         for (int j1 = -b0; j1 <= b0; j1++) {
/*  32 */           for (int k1 = -b0; k1 <= b0; k1++) {
/*  33 */             BlockPosition blockposition1 = blockposition.a(i1, j1, k1);
/*  34 */             IBlockData iblockdata1 = world.getType(blockposition1);
/*     */             
/*  36 */             if ((iblockdata1.getBlock().getMaterial() == Material.LEAVES) && (!((Boolean)iblockdata1.get(CHECK_DECAY)).booleanValue())) {
/*  37 */               world.setTypeAndData(blockposition1, iblockdata1.set(CHECK_DECAY, Boolean.valueOf(true)), 4);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  47 */     if ((!world.isClientSide) && 
/*  48 */       (((Boolean)iblockdata.get(CHECK_DECAY)).booleanValue()) && (((Boolean)iblockdata.get(DECAYABLE)).booleanValue())) {
/*  49 */       byte b0 = 4;
/*  50 */       int i = b0 + 1;
/*  51 */       int j = blockposition.getX();
/*  52 */       int k = blockposition.getY();
/*  53 */       int l = blockposition.getZ();
/*  54 */       byte b1 = 32;
/*  55 */       int i1 = b1 * b1;
/*  56 */       int j1 = b1 / 2;
/*     */       
/*  58 */       if (this.N == null) {
/*  59 */         this.N = new int[b1 * b1 * b1];
/*     */       }
/*     */       
/*  62 */       if (world.areChunksLoadedBetween(new BlockPosition(j - i, k - i, l - i), new BlockPosition(j + i, k + i, l + i))) {
/*  63 */         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */         for (int k1 = -b0; k1 <= b0; k1++) {
/*  70 */           for (int l1 = -b0; l1 <= b0; l1++) {
/*  71 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  72 */               Block block = world.getType(blockposition_mutableblockposition.c(j + k1, k + l1, l + i2)).getBlock();
/*     */               
/*  74 */               if ((block != Blocks.LOG) && (block != Blocks.LOG2)) {
/*  75 */                 if (block.getMaterial() == Material.LEAVES) {
/*  76 */                   this.N[((k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1)] = -2;
/*     */                 } else {
/*  78 */                   this.N[((k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1)] = -1;
/*     */                 }
/*     */               } else {
/*  81 */                 this.N[((k1 + j1) * i1 + (l1 + j1) * b1 + i2 + j1)] = 0;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  87 */         for (k1 = 1; k1 <= 4; k1++) {
/*  88 */           for (int l1 = -b0; l1 <= b0; l1++) {
/*  89 */             for (int i2 = -b0; i2 <= b0; i2++) {
/*  90 */               for (int j2 = -b0; j2 <= b0; j2++) {
/*  91 */                 if (this.N[((l1 + j1) * i1 + (i2 + j1) * b1 + j2 + j1)] == k1 - 1) {
/*  92 */                   if (this.N[((l1 + j1 - 1) * i1 + (i2 + j1) * b1 + j2 + j1)] == -2) {
/*  93 */                     this.N[((l1 + j1 - 1) * i1 + (i2 + j1) * b1 + j2 + j1)] = k1;
/*     */                   }
/*     */                   
/*  96 */                   if (this.N[((l1 + j1 + 1) * i1 + (i2 + j1) * b1 + j2 + j1)] == -2) {
/*  97 */                     this.N[((l1 + j1 + 1) * i1 + (i2 + j1) * b1 + j2 + j1)] = k1;
/*     */                   }
/*     */                   
/* 100 */                   if (this.N[((l1 + j1) * i1 + (i2 + j1 - 1) * b1 + j2 + j1)] == -2) {
/* 101 */                     this.N[((l1 + j1) * i1 + (i2 + j1 - 1) * b1 + j2 + j1)] = k1;
/*     */                   }
/*     */                   
/* 104 */                   if (this.N[((l1 + j1) * i1 + (i2 + j1 + 1) * b1 + j2 + j1)] == -2) {
/* 105 */                     this.N[((l1 + j1) * i1 + (i2 + j1 + 1) * b1 + j2 + j1)] = k1;
/*     */                   }
/*     */                   
/* 108 */                   if (this.N[((l1 + j1) * i1 + (i2 + j1) * b1 + (j2 + j1 - 1))] == -2) {
/* 109 */                     this.N[((l1 + j1) * i1 + (i2 + j1) * b1 + (j2 + j1 - 1))] = k1;
/*     */                   }
/*     */                   
/* 112 */                   if (this.N[((l1 + j1) * i1 + (i2 + j1) * b1 + j2 + j1 + 1)] == -2) {
/* 113 */                     this.N[((l1 + j1) * i1 + (i2 + j1) * b1 + j2 + j1 + 1)] = k1;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 122 */       int k2 = this.N[(j1 * i1 + j1 * b1 + j1)];
/*     */       
/* 124 */       if (k2 >= 0) {
/* 125 */         world.setTypeAndData(blockposition, iblockdata.set(CHECK_DECAY, Boolean.valueOf(false)), 4);
/*     */       } else {
/* 127 */         e(world, blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void e(World world, BlockPosition blockposition)
/*     */   {
/* 136 */     LeavesDecayEvent event = new LeavesDecayEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/* 137 */     world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 139 */     if ((event.isCancelled()) || (world.getType(blockposition).getBlock() != this)) {
/* 140 */       return;
/*     */     }
/*     */     
/* 143 */     b(world, blockposition, world.getType(blockposition), 0);
/* 144 */     world.setAir(blockposition);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/* 148 */     return random.nextInt(20) == 0 ? 1 : 0;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 152 */     return Item.getItemOf(Blocks.SAPLING);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 156 */     if (!world.isClientSide) {
/* 157 */       int j = d(iblockdata);
/*     */       
/* 159 */       if (i > 0) {
/* 160 */         j -= (2 << i);
/* 161 */         if (j < 10) {
/* 162 */           j = 10;
/*     */         }
/*     */       }
/*     */       
/* 166 */       if (world.random.nextInt(j) == 0) {
/* 167 */         Item item = getDropType(iblockdata, world.random, i);
/*     */         
/* 169 */         a(world, blockposition, new ItemStack(item, 1, getDropData(iblockdata)));
/*     */       }
/*     */       
/* 172 */       j = 200;
/* 173 */       if (i > 0) {
/* 174 */         j -= (10 << i);
/* 175 */         if (j < 40) {
/* 176 */           j = 40;
/*     */         }
/*     */       }
/*     */       
/* 180 */       a(world, blockposition, iblockdata, j);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {}
/*     */   
/*     */   protected int d(IBlockData iblockdata)
/*     */   {
/* 188 */     return 20;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 192 */     return !this.R;
/*     */   }
/*     */   
/*     */   public boolean w() {
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public abstract BlockWood.EnumLogVariant b(int paramInt);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLeaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */