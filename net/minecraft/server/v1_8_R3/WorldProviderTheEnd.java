/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderTheEnd
/*    */   extends WorldProvider
/*    */ {
/*    */   public void b()
/*    */   {
/* 14 */     this.c = new WorldChunkManagerHell(BiomeBase.SKY, 0.0F);
/* 15 */     this.dimension = 1;
/* 16 */     this.e = true;
/*    */   }
/*    */   
/*    */   public IChunkProvider getChunkProvider()
/*    */   {
/* 21 */     return new ChunkProviderTheEnd(this.b, this.b.getSeed());
/*    */   }
/*    */   
/*    */   public float a(long paramLong, float paramFloat)
/*    */   {
/* 26 */     return 0.0F;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean e()
/*    */   {
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean canSpawn(int paramInt1, int paramInt2)
/*    */   {
/* 72 */     return this.b.c(new BlockPosition(paramInt1, 0, paramInt2)).getMaterial().isSolid();
/*    */   }
/*    */   
/*    */   public BlockPosition h()
/*    */   {
/* 77 */     return new BlockPosition(100, 50, 0);
/*    */   }
/*    */   
/*    */   public int getSeaLevel()
/*    */   {
/* 82 */     return 50;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 92 */     return "The End";
/*    */   }
/*    */   
/*    */   public String getSuffix()
/*    */   {
/* 97 */     return "_end";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldProviderTheEnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */