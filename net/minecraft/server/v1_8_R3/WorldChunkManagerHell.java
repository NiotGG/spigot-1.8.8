/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldChunkManagerHell extends WorldChunkManager
/*     */ {
/*     */   private BiomeBase b;
/*     */   private float c;
/*     */   
/*     */   public WorldChunkManagerHell(BiomeBase paramBiomeBase, float paramFloat)
/*     */   {
/*  14 */     this.b = paramBiomeBase;
/*  15 */     this.c = paramFloat;
/*     */   }
/*     */   
/*     */   public BiomeBase getBiome(BlockPosition paramBlockPosition)
/*     */   {
/*  20 */     return this.b;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BiomeBase[] getBiomes(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  31 */     if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
/*  32 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  35 */     Arrays.fill(paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.b);
/*     */     
/*  37 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */   
/*     */   public float[] getWetness(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  42 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt3 * paramInt4)) {
/*  43 */       paramArrayOfFloat = new float[paramInt3 * paramInt4];
/*     */     }
/*  45 */     Arrays.fill(paramArrayOfFloat, 0, paramInt3 * paramInt4, this.c);
/*     */     
/*  47 */     return paramArrayOfFloat;
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
/*     */   public BiomeBase[] getBiomeBlock(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  61 */     if ((paramArrayOfBiomeBase == null) || (paramArrayOfBiomeBase.length < paramInt3 * paramInt4)) {
/*  62 */       paramArrayOfBiomeBase = new BiomeBase[paramInt3 * paramInt4];
/*     */     }
/*     */     
/*  65 */     Arrays.fill(paramArrayOfBiomeBase, 0, paramInt3 * paramInt4, this.b);
/*     */     
/*  67 */     return paramArrayOfBiomeBase;
/*     */   }
/*     */   
/*     */   public BiomeBase[] a(BiomeBase[] paramArrayOfBiomeBase, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/*  72 */     return getBiomeBlock(paramArrayOfBiomeBase, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   public BlockPosition a(int paramInt1, int paramInt2, int paramInt3, List<BiomeBase> paramList, Random paramRandom)
/*     */   {
/*  86 */     if (paramList.contains(this.b)) {
/*  87 */       return new BlockPosition(paramInt1 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1), 0, paramInt2 - paramInt3 + paramRandom.nextInt(paramInt3 * 2 + 1));
/*     */     }
/*     */     
/*  90 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, List<BiomeBase> paramList)
/*     */   {
/* 100 */     return paramList.contains(this.b);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldChunkManagerHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */