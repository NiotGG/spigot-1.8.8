package org.bukkit.block;

import java.util.Collection;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;

public abstract interface Block
  extends Metadatable
{
  @Deprecated
  public abstract byte getData();
  
  public abstract Block getRelative(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Block getRelative(BlockFace paramBlockFace);
  
  public abstract Block getRelative(BlockFace paramBlockFace, int paramInt);
  
  public abstract Material getType();
  
  @Deprecated
  public abstract int getTypeId();
  
  public abstract byte getLightLevel();
  
  public abstract byte getLightFromSky();
  
  public abstract byte getLightFromBlocks();
  
  public abstract World getWorld();
  
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract int getZ();
  
  public abstract Location getLocation();
  
  public abstract Location getLocation(Location paramLocation);
  
  public abstract Chunk getChunk();
  
  @Deprecated
  public abstract void setData(byte paramByte);
  
  @Deprecated
  public abstract void setData(byte paramByte, boolean paramBoolean);
  
  public abstract void setType(Material paramMaterial);
  
  public abstract void setType(Material paramMaterial, boolean paramBoolean);
  
  @Deprecated
  public abstract boolean setTypeId(int paramInt);
  
  @Deprecated
  public abstract boolean setTypeId(int paramInt, boolean paramBoolean);
  
  @Deprecated
  public abstract boolean setTypeIdAndData(int paramInt, byte paramByte, boolean paramBoolean);
  
  public abstract BlockFace getFace(Block paramBlock);
  
  public abstract BlockState getState();
  
  public abstract Biome getBiome();
  
  public abstract void setBiome(Biome paramBiome);
  
  public abstract boolean isBlockPowered();
  
  public abstract boolean isBlockIndirectlyPowered();
  
  public abstract boolean isBlockFacePowered(BlockFace paramBlockFace);
  
  public abstract boolean isBlockFaceIndirectlyPowered(BlockFace paramBlockFace);
  
  public abstract int getBlockPower(BlockFace paramBlockFace);
  
  public abstract int getBlockPower();
  
  public abstract boolean isEmpty();
  
  public abstract boolean isLiquid();
  
  public abstract double getTemperature();
  
  public abstract double getHumidity();
  
  public abstract PistonMoveReaction getPistonMoveReaction();
  
  public abstract boolean breakNaturally();
  
  public abstract boolean breakNaturally(ItemStack paramItemStack);
  
  public abstract Collection<ItemStack> getDrops();
  
  public abstract Collection<ItemStack> getDrops(ItemStack paramItemStack);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Block.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */