package org.bukkit;

import org.bukkit.block.Biome;

public abstract interface ChunkSnapshot
{
  public abstract int getX();
  
  public abstract int getZ();
  
  public abstract String getWorldName();
  
  @Deprecated
  public abstract int getBlockTypeId(int paramInt1, int paramInt2, int paramInt3);
  
  @Deprecated
  public abstract int getBlockData(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getBlockSkyLight(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getBlockEmittedLight(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int getHighestBlockYAt(int paramInt1, int paramInt2);
  
  public abstract Biome getBiome(int paramInt1, int paramInt2);
  
  public abstract double getRawBiomeTemperature(int paramInt1, int paramInt2);
  
  public abstract double getRawBiomeRainfall(int paramInt1, int paramInt2);
  
  public abstract long getCaptureFullTime();
  
  public abstract boolean isSectionEmpty(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\ChunkSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */