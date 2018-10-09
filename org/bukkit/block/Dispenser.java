package org.bukkit.block;

import org.bukkit.projectiles.BlockProjectileSource;

public abstract interface Dispenser
  extends BlockState, ContainerBlock
{
  public abstract BlockProjectileSource getBlockProjectileSource();
  
  public abstract boolean dispense();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Dispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */