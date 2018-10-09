package org.bukkit.entity;

import org.bukkit.projectiles.ProjectileSource;

public abstract interface Projectile
  extends Entity
{
  public abstract ProjectileSource getShooter();
  
  public abstract void setShooter(ProjectileSource paramProjectileSource);
  
  public abstract boolean doesBounce();
  
  public abstract void setBounce(boolean paramBoolean);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Projectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */