/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class ChunkSection
/*     */ {
/*     */   private int yPos;
/*     */   private int nonEmptyBlockCount;
/*     */   private int tickingBlockCount;
/*     */   private char[] blockIds;
/*     */   private NibbleArray emittedLight;
/*     */   private NibbleArray skyLight;
/*     */   
/*     */   public ChunkSection(int i, boolean flag) {
/*  13 */     this.yPos = i;
/*  14 */     this.blockIds = new char['က'];
/*  15 */     this.emittedLight = new NibbleArray();
/*  16 */     if (flag) {
/*  17 */       this.skyLight = new NibbleArray();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public ChunkSection(int y, boolean flag, char[] blockIds)
/*     */   {
/*  24 */     this.yPos = y;
/*  25 */     this.blockIds = blockIds;
/*  26 */     this.emittedLight = new NibbleArray();
/*  27 */     if (flag) {
/*  28 */       this.skyLight = new NibbleArray();
/*     */     }
/*  30 */     recalcBlockCounts();
/*     */   }
/*     */   
/*     */   public IBlockData getType(int i, int j, int k)
/*     */   {
/*  35 */     IBlockData iblockdata = (IBlockData)Block.d.a(this.blockIds[(j << 8 | k << 4 | i)]);
/*     */     
/*  37 */     return iblockdata != null ? iblockdata : Blocks.AIR.getBlockData();
/*     */   }
/*     */   
/*     */   public void setType(int i, int j, int k, IBlockData iblockdata) {
/*  41 */     IBlockData iblockdata1 = getType(i, j, k);
/*  42 */     Block block = iblockdata1.getBlock();
/*  43 */     Block block1 = iblockdata.getBlock();
/*     */     
/*  45 */     if (block != Blocks.AIR) {
/*  46 */       this.nonEmptyBlockCount -= 1;
/*  47 */       if (block.isTicking()) {
/*  48 */         this.tickingBlockCount -= 1;
/*     */       }
/*     */     }
/*     */     
/*  52 */     if (block1 != Blocks.AIR) {
/*  53 */       this.nonEmptyBlockCount += 1;
/*  54 */       if (block1.isTicking()) {
/*  55 */         this.tickingBlockCount += 1;
/*     */       }
/*     */     }
/*     */     
/*  59 */     this.blockIds[(j << 8 | k << 4 | i)] = ((char)Block.d.b(iblockdata));
/*     */   }
/*     */   
/*     */   public Block b(int i, int j, int k) {
/*  63 */     return getType(i, j, k).getBlock();
/*     */   }
/*     */   
/*     */   public int c(int i, int j, int k) {
/*  67 */     IBlockData iblockdata = getType(i, j, k);
/*     */     
/*  69 */     return iblockdata.getBlock().toLegacyData(iblockdata);
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  73 */     return this.nonEmptyBlockCount == 0;
/*     */   }
/*     */   
/*     */   public boolean shouldTick() {
/*  77 */     return this.tickingBlockCount > 0;
/*     */   }
/*     */   
/*     */   public int getYPosition() {
/*  81 */     return this.yPos;
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, int l) {
/*  85 */     this.skyLight.a(i, j, k, l);
/*     */   }
/*     */   
/*     */   public int d(int i, int j, int k) {
/*  89 */     return this.skyLight.a(i, j, k);
/*     */   }
/*     */   
/*     */   public void b(int i, int j, int k, int l) {
/*  93 */     this.emittedLight.a(i, j, k, l);
/*     */   }
/*     */   
/*     */   public int e(int i, int j, int k) {
/*  97 */     return this.emittedLight.a(i, j, k);
/*     */   }
/*     */   
/*     */   public void recalcBlockCounts() {
/* 101 */     this.nonEmptyBlockCount = 0;
/* 102 */     this.tickingBlockCount = 0;
/*     */     
/* 104 */     for (int i = 0; i < 16; i++) {
/* 105 */       for (int j = 0; j < 16; j++) {
/* 106 */         for (int k = 0; k < 16; k++) {
/* 107 */           Block block = b(i, j, k);
/*     */           
/* 109 */           if (block != Blocks.AIR) {
/* 110 */             this.nonEmptyBlockCount += 1;
/* 111 */             if (block.isTicking()) {
/* 112 */               this.tickingBlockCount += 1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public char[] getIdArray()
/*     */   {
/* 122 */     return this.blockIds;
/*     */   }
/*     */   
/*     */   public void a(char[] achar) {
/* 126 */     this.blockIds = achar;
/*     */   }
/*     */   
/*     */   public NibbleArray getEmittedLightArray() {
/* 130 */     return this.emittedLight;
/*     */   }
/*     */   
/*     */   public NibbleArray getSkyLightArray() {
/* 134 */     return this.skyLight;
/*     */   }
/*     */   
/*     */   public void a(NibbleArray nibblearray) {
/* 138 */     this.emittedLight = nibblearray;
/*     */   }
/*     */   
/*     */   public void b(NibbleArray nibblearray) {
/* 142 */     this.skyLight = nibblearray;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */