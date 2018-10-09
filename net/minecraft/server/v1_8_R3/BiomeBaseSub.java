/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeBaseSub
/*    */   extends BiomeBase
/*    */ {
/*    */   protected BiomeBase aE;
/*    */   
/*    */   public BiomeBaseSub(int paramInt, BiomeBase paramBiomeBase)
/*    */   {
/* 15 */     super(paramInt);
/* 16 */     this.aE = paramBiomeBase;
/* 17 */     a(paramBiomeBase.ai, true);
/*    */     
/* 19 */     this.ah = (paramBiomeBase.ah + " M");
/*    */     
/* 21 */     this.ak = paramBiomeBase.ak;
/* 22 */     this.al = paramBiomeBase.al;
/* 23 */     this.am = paramBiomeBase.am;
/* 24 */     this.an = paramBiomeBase.an;
/* 25 */     this.ao = paramBiomeBase.ao;
/* 26 */     this.temperature = paramBiomeBase.temperature;
/* 27 */     this.humidity = paramBiomeBase.humidity;
/* 28 */     this.ar = paramBiomeBase.ar;
/* 29 */     this.ax = paramBiomeBase.ax;
/* 30 */     this.ay = paramBiomeBase.ay;
/*    */     
/* 32 */     this.au = Lists.newArrayList(paramBiomeBase.au);
/* 33 */     this.at = Lists.newArrayList(paramBiomeBase.at);
/* 34 */     this.aw = Lists.newArrayList(paramBiomeBase.aw);
/* 35 */     this.av = Lists.newArrayList(paramBiomeBase.av);
/*    */     
/* 37 */     this.temperature = paramBiomeBase.temperature;
/* 38 */     this.humidity = paramBiomeBase.humidity;
/*    */     
/* 40 */     this.an = (paramBiomeBase.an + 0.1F);
/* 41 */     this.ao = (paramBiomeBase.ao + 0.2F);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/* 46 */     this.aE.as.a(paramWorld, paramRandom, this, paramBlockPosition);
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, ChunkSnapshot paramChunkSnapshot, int paramInt1, int paramInt2, double paramDouble)
/*    */   {
/* 51 */     this.aE.a(paramWorld, paramRandom, paramChunkSnapshot, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */   
/*    */   public float g()
/*    */   {
/* 56 */     return this.aE.g();
/*    */   }
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom)
/*    */   {
/* 61 */     return this.aE.a(paramRandom);
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
/*    */   public Class<? extends BiomeBase> l()
/*    */   {
/* 76 */     return this.aE.l();
/*    */   }
/*    */   
/*    */   public boolean a(BiomeBase paramBiomeBase)
/*    */   {
/* 81 */     return this.aE.a(paramBiomeBase);
/*    */   }
/*    */   
/*    */   public BiomeBase.EnumTemperature m()
/*    */   {
/* 86 */     return this.aE.m();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeBaseSub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */