/*     */ package org.bukkit;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Location
/*     */   implements Cloneable, ConfigurationSerializable
/*     */ {
/*     */   private World world;
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float pitch;
/*     */   private float yaw;
/*     */   
/*     */   public Location(World world, double x, double y, double z)
/*     */   {
/*  32 */     this(world, x, y, z, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location(World world, double x, double y, double z, float yaw, float pitch)
/*     */   {
/*  46 */     this.world = world;
/*  47 */     this.x = x;
/*  48 */     this.y = y;
/*  49 */     this.z = z;
/*  50 */     this.pitch = pitch;
/*  51 */     this.yaw = yaw;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWorld(World world)
/*     */   {
/*  60 */     this.world = world;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public World getWorld()
/*     */   {
/*  69 */     return this.world;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Chunk getChunk()
/*     */   {
/*  78 */     return this.world.getChunkAt(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Block getBlock()
/*     */   {
/*  87 */     return this.world.getBlockAt(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setX(double x)
/*     */   {
/*  96 */     this.x = x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/* 105 */     return this.x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockX()
/*     */   {
/* 115 */     return locToBlock(this.x);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setY(double y)
/*     */   {
/* 124 */     this.y = y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/* 133 */     return this.y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockY()
/*     */   {
/* 143 */     return locToBlock(this.y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setZ(double z)
/*     */   {
/* 152 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 161 */     return this.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockZ()
/*     */   {
/* 171 */     return locToBlock(this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYaw(float yaw)
/*     */   {
/* 189 */     this.yaw = yaw;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 207 */     return this.yaw;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPitch(float pitch)
/*     */   {
/* 223 */     this.pitch = pitch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 239 */     return this.pitch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getDirection()
/*     */   {
/* 250 */     Vector vector = new Vector();
/*     */     
/* 252 */     double rotX = getYaw();
/* 253 */     double rotY = getPitch();
/*     */     
/* 255 */     vector.setY(-Math.sin(Math.toRadians(rotY)));
/*     */     
/* 257 */     double xz = Math.cos(Math.toRadians(rotY));
/*     */     
/* 259 */     vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
/* 260 */     vector.setZ(xz * Math.cos(Math.toRadians(rotX)));
/*     */     
/* 262 */     return vector;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location setDirection(Vector vector)
/*     */   {
/* 282 */     double x = vector.getX();
/* 283 */     double z = vector.getZ();
/*     */     
/* 285 */     if ((x == 0.0D) && (z == 0.0D)) {
/* 286 */       this.pitch = (vector.getY() > 0.0D ? -90 : 90);
/* 287 */       return this;
/*     */     }
/*     */     
/* 290 */     double theta = Math.atan2(-x, z);
/* 291 */     this.yaw = ((float)Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D));
/*     */     
/* 293 */     double x2 = NumberConversions.square(x);
/* 294 */     double z2 = NumberConversions.square(z);
/* 295 */     double xz = Math.sqrt(x2 + z2);
/* 296 */     this.pitch = ((float)Math.toDegrees(Math.atan(-vector.getY() / xz)));
/*     */     
/* 298 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location add(Location vec)
/*     */   {
/* 310 */     if ((vec == null) || (vec.getWorld() != getWorld())) {
/* 311 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 314 */     this.x += vec.x;
/* 315 */     this.y += vec.y;
/* 316 */     this.z += vec.z;
/* 317 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location add(Vector vec)
/*     */   {
/* 328 */     this.x += vec.getX();
/* 329 */     this.y += vec.getY();
/* 330 */     this.z += vec.getZ();
/* 331 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location add(double x, double y, double z)
/*     */   {
/* 344 */     this.x += x;
/* 345 */     this.y += y;
/* 346 */     this.z += z;
/* 347 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location subtract(Location vec)
/*     */   {
/* 359 */     if ((vec == null) || (vec.getWorld() != getWorld())) {
/* 360 */       throw new IllegalArgumentException("Cannot add Locations of differing worlds");
/*     */     }
/*     */     
/* 363 */     this.x -= vec.x;
/* 364 */     this.y -= vec.y;
/* 365 */     this.z -= vec.z;
/* 366 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location subtract(Vector vec)
/*     */   {
/* 377 */     this.x -= vec.getX();
/* 378 */     this.y -= vec.getY();
/* 379 */     this.z -= vec.getZ();
/* 380 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location subtract(double x, double y, double z)
/*     */   {
/* 394 */     this.x -= x;
/* 395 */     this.y -= y;
/* 396 */     this.z -= z;
/* 397 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double length()
/*     */   {
/* 412 */     return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double lengthSquared()
/*     */   {
/* 423 */     return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double distance(Location o)
/*     */   {
/* 439 */     return Math.sqrt(distanceSquared(o));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double distanceSquared(Location o)
/*     */   {
/* 451 */     if (o == null)
/* 452 */       throw new IllegalArgumentException("Cannot measure distance to a null location");
/* 453 */     if ((o.getWorld() == null) || (getWorld() == null))
/* 454 */       throw new IllegalArgumentException("Cannot measure distance to a null world");
/* 455 */     if (o.getWorld() != getWorld()) {
/* 456 */       throw new IllegalArgumentException("Cannot measure distance between " + getWorld().getName() + " and " + o.getWorld().getName());
/*     */     }
/*     */     
/* 459 */     return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location multiply(double m)
/*     */   {
/* 471 */     this.x *= m;
/* 472 */     this.y *= m;
/* 473 */     this.z *= m;
/* 474 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location zero()
/*     */   {
/* 484 */     this.x = 0.0D;
/* 485 */     this.y = 0.0D;
/* 486 */     this.z = 0.0D;
/* 487 */     return this;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 492 */     if (obj == null) {
/* 493 */       return false;
/*     */     }
/* 495 */     if (getClass() != obj.getClass()) {
/* 496 */       return false;
/*     */     }
/* 498 */     Location other = (Location)obj;
/*     */     
/* 500 */     if ((this.world != other.world) && ((this.world == null) || (!this.world.equals(other.world)))) {
/* 501 */       return false;
/*     */     }
/* 503 */     if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
/* 504 */       return false;
/*     */     }
/* 506 */     if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
/* 507 */       return false;
/*     */     }
/* 509 */     if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
/* 510 */       return false;
/*     */     }
/* 512 */     if (Float.floatToIntBits(this.pitch) != Float.floatToIntBits(other.pitch)) {
/* 513 */       return false;
/*     */     }
/* 515 */     if (Float.floatToIntBits(this.yaw) != Float.floatToIntBits(other.yaw)) {
/* 516 */       return false;
/*     */     }
/* 518 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 523 */     int hash = 3;
/*     */     
/* 525 */     hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
/* 526 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
/* 527 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
/* 528 */     hash = 19 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
/* 529 */     hash = 19 * hash + Float.floatToIntBits(this.pitch);
/* 530 */     hash = 19 * hash + Float.floatToIntBits(this.yaw);
/* 531 */     return hash;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 536 */     return "Location{world=" + this.world + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",pitch=" + this.pitch + ",yaw=" + this.yaw + '}';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector toVector()
/*     */   {
/* 546 */     return new Vector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Location clone()
/*     */   {
/*     */     try {
/* 552 */       return (Location)super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 554 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int locToBlock(double loc)
/*     */   {
/* 566 */     return NumberConversions.floor(loc);
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize()
/*     */   {
/* 571 */     Map<String, Object> data = new HashMap();
/* 572 */     data.put("world", this.world.getName());
/*     */     
/* 574 */     data.put("x", Double.valueOf(this.x));
/* 575 */     data.put("y", Double.valueOf(this.y));
/* 576 */     data.put("z", Double.valueOf(this.z));
/*     */     
/* 578 */     data.put("yaw", Float.valueOf(this.yaw));
/* 579 */     data.put("pitch", Float.valueOf(this.pitch));
/*     */     
/* 581 */     return data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Location deserialize(Map<String, Object> args)
/*     */   {
/* 593 */     World world = Bukkit.getWorld((String)args.get("world"));
/* 594 */     if (world == null) {
/* 595 */       throw new IllegalArgumentException("unknown world");
/*     */     }
/*     */     
/* 598 */     return new Location(world, NumberConversions.toDouble(args.get("x")), NumberConversions.toDouble(args.get("y")), NumberConversions.toDouble(args.get("z")), NumberConversions.toFloat(args.get("yaw")), NumberConversions.toFloat(args.get("pitch")));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Location.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */