/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecondaryWorldData
/*     */   extends WorldData
/*     */ {
/*     */   private final WorldData b;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SecondaryWorldData(WorldData paramWorldData)
/*     */   {
/*  23 */     this.b = paramWorldData;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a()
/*     */   {
/*  28 */     return this.b.a();
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  33 */     return this.b.a(paramNBTTagCompound);
/*     */   }
/*     */   
/*     */   public long getSeed()
/*     */   {
/*  38 */     return this.b.getSeed();
/*     */   }
/*     */   
/*     */   public int c()
/*     */   {
/*  43 */     return this.b.c();
/*     */   }
/*     */   
/*     */   public int d()
/*     */   {
/*  48 */     return this.b.d();
/*     */   }
/*     */   
/*     */   public int e()
/*     */   {
/*  53 */     return this.b.e();
/*     */   }
/*     */   
/*     */   public long getTime()
/*     */   {
/*  58 */     return this.b.getTime();
/*     */   }
/*     */   
/*     */   public long getDayTime()
/*     */   {
/*  63 */     return this.b.getDayTime();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NBTTagCompound i()
/*     */   {
/*  73 */     return this.b.i();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  83 */     return this.b.getName();
/*     */   }
/*     */   
/*     */   public int l()
/*     */   {
/*  88 */     return this.b.l();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isThundering()
/*     */   {
/*  98 */     return this.b.isThundering();
/*     */   }
/*     */   
/*     */   public int getThunderDuration()
/*     */   {
/* 103 */     return this.b.getThunderDuration();
/*     */   }
/*     */   
/*     */   public boolean hasStorm()
/*     */   {
/* 108 */     return this.b.hasStorm();
/*     */   }
/*     */   
/*     */   public int getWeatherDuration()
/*     */   {
/* 113 */     return this.b.getWeatherDuration();
/*     */   }
/*     */   
/*     */   public WorldSettings.EnumGamemode getGameType()
/*     */   {
/* 118 */     return this.b.getGameType();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTime(long paramLong) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDayTime(long paramLong) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSpawn(BlockPosition paramBlockPosition) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void a(String paramString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void e(int paramInt) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThundering(boolean paramBoolean) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThunderDuration(int paramInt) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStorm(boolean paramBoolean) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWeatherDuration(int paramInt) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean shouldGenerateMapFeatures()
/*     */   {
/* 187 */     return this.b.shouldGenerateMapFeatures();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isHardcore()
/*     */   {
/* 195 */     return this.b.isHardcore();
/*     */   }
/*     */   
/*     */   public WorldType getType()
/*     */   {
/* 200 */     return this.b.getType();
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(WorldType paramWorldType) {}
/*     */   
/*     */ 
/*     */   public boolean v()
/*     */   {
/* 209 */     return this.b.v();
/*     */   }
/*     */   
/*     */ 
/*     */   public void c(boolean paramBoolean) {}
/*     */   
/*     */ 
/*     */   public boolean w()
/*     */   {
/* 218 */     return this.b.w();
/*     */   }
/*     */   
/*     */ 
/*     */   public void d(boolean paramBoolean) {}
/*     */   
/*     */ 
/*     */   public GameRules x()
/*     */   {
/* 227 */     return this.b.x();
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty()
/*     */   {
/* 232 */     return this.b.getDifficulty();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDifficulty(EnumDifficulty paramEnumDifficulty) {}
/*     */   
/*     */ 
/*     */   public boolean isDifficultyLocked()
/*     */   {
/* 241 */     return this.b.isDifficultyLocked();
/*     */   }
/*     */   
/*     */   public void e(boolean paramBoolean) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SecondaryWorldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */