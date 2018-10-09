/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BiomeOcean extends BiomeBase
/*    */ {
/*    */   public BiomeOcean(int paramInt)
/*    */   {
/* 10 */     super(paramInt);
/*    */     
/* 12 */     this.au.clear();
/*    */   }
/*    */   
/*    */   public BiomeBase.EnumTemperature m()
/*    */   {
/* 17 */     return BiomeBase.EnumTemperature.OCEAN;
/*    */   }
/*    */   
/*    */ 
/*    */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*    */   {
/* 23 */     super.a(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */