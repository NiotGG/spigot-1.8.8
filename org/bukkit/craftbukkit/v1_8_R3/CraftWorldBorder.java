/*     */ package org.bukkit.craftbukkit.v1_8_R3;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.bukkit.Location;
/*     */ 
/*     */ public class CraftWorldBorder implements org.bukkit.WorldBorder
/*     */ {
/*     */   private final org.bukkit.World world;
/*     */   private final net.minecraft.server.v1_8_R3.WorldBorder handle;
/*     */   
/*     */   public CraftWorldBorder(CraftWorld world)
/*     */   {
/*  13 */     this.world = world;
/*  14 */     this.handle = world.getHandle().getWorldBorder();
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  19 */     setSize(6.0E7D);
/*  20 */     setDamageAmount(0.2D);
/*  21 */     setDamageBuffer(5.0D);
/*  22 */     setWarningDistance(5);
/*  23 */     setWarningTime(15);
/*  24 */     setCenter(0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public double getSize()
/*     */   {
/*  29 */     return this.handle.getSize();
/*     */   }
/*     */   
/*     */   public void setSize(double newSize)
/*     */   {
/*  34 */     setSize(newSize, 0L);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setSize(double newSize, long time)
/*     */   {
/*  40 */     newSize = Math.min(6.0E7D, Math.max(1.0D, newSize));
/*  41 */     time = Math.min(9223372036854775L, Math.max(0L, time));
/*     */     
/*  43 */     if (time > 0L) {
/*  44 */       this.handle.transitionSizeBetween(this.handle.getSize(), newSize, time * 1000L);
/*     */     } else {
/*  46 */       this.handle.setSize(newSize);
/*     */     }
/*     */   }
/*     */   
/*     */   public Location getCenter()
/*     */   {
/*  52 */     double x = this.handle.getCenterX();
/*  53 */     double z = this.handle.getCenterZ();
/*     */     
/*  55 */     return new Location(this.world, x, 0.0D, z);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setCenter(double x, double z)
/*     */   {
/*  61 */     x = Math.min(3.0E7D, Math.max(-3.0E7D, x));
/*  62 */     z = Math.min(3.0E7D, Math.max(-3.0E7D, z));
/*     */     
/*  64 */     this.handle.setCenter(x, z);
/*     */   }
/*     */   
/*     */   public void setCenter(Location location)
/*     */   {
/*  69 */     setCenter(location.getX(), location.getZ());
/*     */   }
/*     */   
/*     */   public double getDamageBuffer()
/*     */   {
/*  74 */     return this.handle.getDamageBuffer();
/*     */   }
/*     */   
/*     */   public void setDamageBuffer(double blocks)
/*     */   {
/*  79 */     this.handle.setDamageBuffer(blocks);
/*     */   }
/*     */   
/*     */   public double getDamageAmount()
/*     */   {
/*  84 */     return this.handle.getDamageAmount();
/*     */   }
/*     */   
/*     */   public void setDamageAmount(double damage)
/*     */   {
/*  89 */     this.handle.setDamageAmount(damage);
/*     */   }
/*     */   
/*     */   public int getWarningTime()
/*     */   {
/*  94 */     return this.handle.getWarningTime();
/*     */   }
/*     */   
/*     */   public void setWarningTime(int time)
/*     */   {
/*  99 */     this.handle.setWarningTime(time);
/*     */   }
/*     */   
/*     */   public int getWarningDistance()
/*     */   {
/* 104 */     return this.handle.getWarningDistance();
/*     */   }
/*     */   
/*     */   public void setWarningDistance(int distance)
/*     */   {
/* 109 */     this.handle.setWarningDistance(distance);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftWorldBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */