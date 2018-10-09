package org.bukkit.projectiles;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public abstract interface ProjectileSource
{
  public abstract <T extends Projectile> T launchProjectile(Class<? extends T> paramClass);
  
  public abstract <T extends Projectile> T launchProjectile(Class<? extends T> paramClass, Vector paramVector);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\projectiles\ProjectileSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */