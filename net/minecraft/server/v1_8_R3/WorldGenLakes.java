/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenLakes
/*     */   extends WorldGenerator
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   public WorldGenLakes(Block paramBlock)
/*     */   {
/*  17 */     this.a = paramBlock;
/*     */   }
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*     */   {
/*  22 */     paramBlockPosition = paramBlockPosition.a(-8, 0, -8);
/*  23 */     while ((paramBlockPosition.getY() > 5) && (paramWorld.isEmpty(paramBlockPosition))) {
/*  24 */       paramBlockPosition = paramBlockPosition.down();
/*     */     }
/*  26 */     if (paramBlockPosition.getY() <= 4) {
/*  27 */       return false;
/*     */     }
/*     */     
/*  30 */     paramBlockPosition = paramBlockPosition.down(4);
/*     */     
/*  32 */     boolean[] arrayOfBoolean = new boolean['ࠀ'];
/*     */     
/*  34 */     int i = paramRandom.nextInt(4) + 4;
/*  35 */     for (int j = 0; j < i; j++) {
/*  36 */       double d1 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*  37 */       double d2 = paramRandom.nextDouble() * 4.0D + 2.0D;
/*  38 */       double d3 = paramRandom.nextDouble() * 6.0D + 3.0D;
/*     */       
/*  40 */       double d4 = paramRandom.nextDouble() * (16.0D - d1 - 2.0D) + 1.0D + d1 / 2.0D;
/*  41 */       double d5 = paramRandom.nextDouble() * (8.0D - d2 - 4.0D) + 2.0D + d2 / 2.0D;
/*  42 */       double d6 = paramRandom.nextDouble() * (16.0D - d3 - 2.0D) + 1.0D + d3 / 2.0D;
/*     */       
/*  44 */       for (int k = 1; k < 15; k++) {
/*  45 */         for (int m = 1; m < 15; m++)
/*  46 */           for (int n = 1; n < 7; n++) {
/*  47 */             double d7 = (k - d4) / (d1 / 2.0D);
/*  48 */             double d8 = (n - d5) / (d2 / 2.0D);
/*  49 */             double d9 = (m - d6) / (d3 / 2.0D);
/*  50 */             double d10 = d7 * d7 + d8 * d8 + d9 * d9;
/*  51 */             if (d10 < 1.0D)
/*  52 */               arrayOfBoolean[((k * 16 + m) * 8 + n)] = true;
/*     */           }
/*     */       }
/*     */     }
/*     */     int i1;
/*     */     int i2;
/*     */     Object localObject;
/*  59 */     for (j = 0; j < 16; j++) {
/*  60 */       for (i1 = 0; i1 < 16; i1++) {
/*  61 */         for (i2 = 0; i2 < 8; i2++) {
/*  62 */           int i3 = (arrayOfBoolean[((j * 16 + i1) * 8 + i2)] == 0) && (((j < 15) && (arrayOfBoolean[(((j + 1) * 16 + i1) * 8 + i2)] != 0)) || ((j > 0) && (arrayOfBoolean[(((j - 1) * 16 + i1) * 8 + i2)] != 0)) || ((i1 < 15) && (arrayOfBoolean[((j * 16 + (i1 + 1)) * 8 + i2)] != 0)) || ((i1 > 0) && (arrayOfBoolean[((j * 16 + (i1 - 1)) * 8 + i2)] != 0)) || ((i2 < 7) && (arrayOfBoolean[((j * 16 + i1) * 8 + (i2 + 1))] != 0)) || ((i2 > 0) && (arrayOfBoolean[((j * 16 + i1) * 8 + (i2 - 1))] != 0))) ? 1 : 0;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */           if (i3 != 0) {
/*  72 */             localObject = paramWorld.getType(paramBlockPosition.a(j, i2, i1)).getBlock().getMaterial();
/*  73 */             if ((i2 >= 4) && (((Material)localObject).isLiquid())) {
/*  74 */               return false;
/*     */             }
/*  76 */             if ((i2 < 4) && (!((Material)localObject).isBuildable()) && (paramWorld.getType(paramBlockPosition.a(j, i2, i1)).getBlock() != this.a)) {
/*  77 */               return false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  84 */     for (j = 0; j < 16; j++) {
/*  85 */       for (i1 = 0; i1 < 16; i1++) {
/*  86 */         for (i2 = 0; i2 < 8; i2++) {
/*  87 */           if (arrayOfBoolean[((j * 16 + i1) * 8 + i2)] != 0) {
/*  88 */             paramWorld.setTypeAndData(paramBlockPosition.a(j, i2, i1), i2 >= 4 ? Blocks.AIR.getBlockData() : this.a.getBlockData(), 2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  94 */     for (j = 0; j < 16; j++) {
/*  95 */       for (i1 = 0; i1 < 16; i1++) {
/*  96 */         for (i2 = 4; i2 < 8; i2++) {
/*  97 */           if (arrayOfBoolean[((j * 16 + i1) * 8 + i2)] != 0) {
/*  98 */             BlockPosition localBlockPosition = paramBlockPosition.a(j, i2 - 1, i1);
/*     */             
/* 100 */             if ((paramWorld.getType(localBlockPosition).getBlock() == Blocks.DIRT) && (paramWorld.b(EnumSkyBlock.SKY, paramBlockPosition.a(j, i2, i1)) > 0)) {
/* 101 */               localObject = paramWorld.getBiome(localBlockPosition);
/* 102 */               if (((BiomeBase)localObject).ak.getBlock() == Blocks.MYCELIUM) {
/* 103 */                 paramWorld.setTypeAndData(localBlockPosition, Blocks.MYCELIUM.getBlockData(), 2);
/*     */               } else {
/* 105 */                 paramWorld.setTypeAndData(localBlockPosition, Blocks.GRASS.getBlockData(), 2);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 113 */     if (this.a.getMaterial() == Material.LAVA) {
/* 114 */       for (j = 0; j < 16; j++) {
/* 115 */         for (i1 = 0; i1 < 16; i1++) {
/* 116 */           for (i2 = 0; i2 < 8; i2++) {
/* 117 */             int i4 = (arrayOfBoolean[((j * 16 + i1) * 8 + i2)] == 0) && (((j < 15) && (arrayOfBoolean[(((j + 1) * 16 + i1) * 8 + i2)] != 0)) || ((j > 0) && (arrayOfBoolean[(((j - 1) * 16 + i1) * 8 + i2)] != 0)) || ((i1 < 15) && (arrayOfBoolean[((j * 16 + (i1 + 1)) * 8 + i2)] != 0)) || ((i1 > 0) && (arrayOfBoolean[((j * 16 + (i1 - 1)) * 8 + i2)] != 0)) || ((i2 < 7) && (arrayOfBoolean[((j * 16 + i1) * 8 + (i2 + 1))] != 0)) || ((i2 > 0) && (arrayOfBoolean[((j * 16 + i1) * 8 + (i2 - 1))] != 0))) ? 1 : 0;
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 126 */             if ((i4 != 0) && 
/* 127 */               ((i2 < 4) || (paramRandom.nextInt(2) != 0)) && (paramWorld.getType(paramBlockPosition.a(j, i2, i1)).getBlock().getMaterial().isBuildable())) {
/* 128 */               paramWorld.setTypeAndData(paramBlockPosition.a(j, i2, i1), Blocks.STONE.getBlockData(), 2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (this.a.getMaterial() == Material.WATER) {
/* 137 */       for (j = 0; j < 16; j++) {
/* 138 */         for (i1 = 0; i1 < 16; i1++) {
/* 139 */           i2 = 4;
/* 140 */           if (paramWorld.v(paramBlockPosition.a(j, i2, i1))) {
/* 141 */             paramWorld.setTypeAndData(paramBlockPosition.a(j, i2, i1), Blocks.ICE.getBlockData(), 2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenLakes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */