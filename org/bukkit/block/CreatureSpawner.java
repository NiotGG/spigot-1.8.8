package org.bukkit.block;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;

public abstract interface CreatureSpawner
  extends BlockState
{
  @Deprecated
  public abstract CreatureType getCreatureType();
  
  public abstract EntityType getSpawnedType();
  
  public abstract void setSpawnedType(EntityType paramEntityType);
  
  @Deprecated
  public abstract void setCreatureType(CreatureType paramCreatureType);
  
  @Deprecated
  public abstract String getCreatureTypeId();
  
  public abstract void setCreatureTypeByName(String paramString);
  
  public abstract String getCreatureTypeName();
  
  @Deprecated
  public abstract void setCreatureTypeId(String paramString);
  
  public abstract int getDelay();
  
  public abstract void setDelay(int paramInt);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\CreatureSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */