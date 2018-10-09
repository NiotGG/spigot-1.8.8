/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldProviderHell
/*    */   extends WorldProvider
/*    */ {
/*    */   public void b()
/*    */   {
/* 14 */     this.c = new WorldChunkManagerHell(BiomeBase.HELL, 0.0F);
/* 15 */     this.d = true;
/* 16 */     this.e = true;
/* 17 */     this.dimension = -1;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void a()
/*    */   {
/* 27 */     float f1 = 0.1F;
/* 28 */     for (int i = 0; i <= 15; i++) {
/* 29 */       float f2 = 1.0F - i / 15.0F;
/* 30 */       this.f[i] = ((1.0F - f2) / (f2 * 3.0F + 1.0F) * (1.0F - f1) + f1);
/*    */     }
/*    */   }
/*    */   
/*    */   public IChunkProvider getChunkProvider()
/*    */   {
/* 36 */     return new ChunkProviderHell(this.b, this.b.getWorldData().shouldGenerateMapFeatures(), this.b.getSeed());
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canSpawn(int paramInt1, int paramInt2)
/*    */   {
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public float a(long paramLong, float paramFloat)
/*    */   {
/* 51 */     return 0.5F;
/*    */   }
/*    */   
/*    */   public boolean e()
/*    */   {
/* 56 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 66 */     return "Nether";
/*    */   }
/*    */   
/*    */   public String getSuffix()
/*    */   {
/* 71 */     return "_nether";
/*    */   }
/*    */   
/*    */   public WorldBorder getWorldBorder()
/*    */   {
/* 76 */     new WorldBorder()
/*    */     {
/*    */       public double getCenterX() {
/* 79 */         return super.getCenterX() / 8.0D;
/*    */       }
/*    */       
/*    */       public double getCenterZ()
/*    */       {
/* 84 */         return super.getCenterZ() / 8.0D;
/*    */       }
/*    */     };
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldProviderHell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */