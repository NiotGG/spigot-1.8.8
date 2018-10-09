/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCache
/*     */   implements IBlockAccess
/*     */ {
/*     */   protected int a;
/*     */   
/*     */ 
/*     */   protected int b;
/*     */   
/*     */ 
/*     */   protected Chunk[][] c;
/*     */   
/*     */ 
/*     */   protected boolean d;
/*     */   
/*     */   protected World e;
/*     */   
/*     */ 
/*     */   public ChunkCache(World paramWorld, BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2, int paramInt)
/*     */   {
/*  24 */     this.e = paramWorld;
/*     */     
/*  26 */     this.a = (paramBlockPosition1.getX() - paramInt >> 4);
/*  27 */     this.b = (paramBlockPosition1.getZ() - paramInt >> 4);
/*  28 */     int i = paramBlockPosition2.getX() + paramInt >> 4;
/*  29 */     int j = paramBlockPosition2.getZ() + paramInt >> 4;
/*     */     
/*  31 */     this.c = new Chunk[i - this.a + 1][j - this.b + 1];
/*     */     
/*  33 */     this.d = true;
/*  34 */     int m; for (int k = this.a; k <= i; k++) {
/*  35 */       for (m = this.b; m <= j; m++) {
/*  36 */         this.c[(k - this.a)][(m - this.b)] = paramWorld.getChunkAt(k, m);
/*     */       }
/*     */     }
/*     */     
/*  40 */     for (k = paramBlockPosition1.getX() >> 4; k <= paramBlockPosition2.getX() >> 4; k++) {
/*  41 */       for (m = paramBlockPosition1.getZ() >> 4; m <= paramBlockPosition2.getZ() >> 4; m++) {
/*  42 */         Chunk localChunk = this.c[(k - this.a)][(m - this.b)];
/*  43 */         if ((localChunk != null) && 
/*  44 */           (!localChunk.c(paramBlockPosition1.getY(), paramBlockPosition2.getY()))) {
/*  45 */           this.d = false;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TileEntity getTileEntity(BlockPosition paramBlockPosition)
/*     */   {
/*  60 */     int i = (paramBlockPosition.getX() >> 4) - this.a;
/*  61 */     int j = (paramBlockPosition.getZ() >> 4) - this.b;
/*     */     
/*  63 */     return this.c[i][j].a(paramBlockPosition, Chunk.EnumTileEntityState.IMMEDIATE);
/*     */   }
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
/*     */   public IBlockData getType(BlockPosition paramBlockPosition)
/*     */   {
/* 134 */     if ((paramBlockPosition.getY() >= 0) && 
/* 135 */       (paramBlockPosition.getY() < 256)) {
/* 136 */       int i = (paramBlockPosition.getX() >> 4) - this.a;
/* 137 */       int j = (paramBlockPosition.getZ() >> 4) - this.b;
/*     */       
/* 139 */       if ((i >= 0) && (i < this.c.length) && (j >= 0) && (j < this.c[i].length)) {
/* 140 */         Chunk localChunk = this.c[i][j];
/* 141 */         if (localChunk != null) {
/* 142 */           return localChunk.getBlockData(paramBlockPosition);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 148 */     return Blocks.AIR.getBlockData();
/*     */   }
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
/*     */   public boolean isEmpty(BlockPosition paramBlockPosition)
/*     */   {
/* 191 */     return getType(paramBlockPosition).getBlock().getMaterial() == Material.AIR;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockPower(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection)
/*     */   {
/* 212 */     IBlockData localIBlockData = getType(paramBlockPosition);
/* 213 */     return localIBlockData.getBlock().b(this, paramBlockPosition, localIBlockData, paramEnumDirection);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChunkCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */