/*     */ package org.bukkit.util;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SerializableAs("Vector")
/*     */ public class Vector
/*     */   implements Cloneable, ConfigurationSerializable
/*     */ {
/*     */   private static final long serialVersionUID = -2657651106777219169L;
/*  22 */   private static Random random = new Random();
/*     */   
/*     */ 
/*     */   private static final double epsilon = 1.0E-6D;
/*     */   
/*     */ 
/*     */   protected double x;
/*     */   
/*     */   protected double y;
/*     */   
/*     */   protected double z;
/*     */   
/*     */ 
/*     */   public Vector()
/*     */   {
/*  37 */     this.x = 0.0D;
/*  38 */     this.y = 0.0D;
/*  39 */     this.z = 0.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector(int x, int y, int z)
/*     */   {
/*  50 */     this.x = x;
/*  51 */     this.y = y;
/*  52 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector(double x, double y, double z)
/*     */   {
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector(float x, float y, float z)
/*     */   {
/*  76 */     this.x = x;
/*  77 */     this.y = y;
/*  78 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector add(Vector vec)
/*     */   {
/*  88 */     this.x += vec.x;
/*  89 */     this.y += vec.y;
/*  90 */     this.z += vec.z;
/*  91 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector subtract(Vector vec)
/*     */   {
/* 101 */     this.x -= vec.x;
/* 102 */     this.y -= vec.y;
/* 103 */     this.z -= vec.z;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector multiply(Vector vec)
/*     */   {
/* 114 */     this.x *= vec.x;
/* 115 */     this.y *= vec.y;
/* 116 */     this.z *= vec.z;
/* 117 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector divide(Vector vec)
/*     */   {
/* 127 */     this.x /= vec.x;
/* 128 */     this.y /= vec.y;
/* 129 */     this.z /= vec.z;
/* 130 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector copy(Vector vec)
/*     */   {
/* 140 */     this.x = vec.x;
/* 141 */     this.y = vec.y;
/* 142 */     this.z = vec.z;
/* 143 */     return this;
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
/*     */   public double length()
/*     */   {
/* 156 */     return Math.sqrt(NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double lengthSquared()
/*     */   {
/* 165 */     return NumberConversions.square(this.x) + NumberConversions.square(this.y) + NumberConversions.square(this.z);
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
/*     */   public double distance(Vector o)
/*     */   {
/* 179 */     return Math.sqrt(NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double distanceSquared(Vector o)
/*     */   {
/* 189 */     return NumberConversions.square(this.x - o.x) + NumberConversions.square(this.y - o.y) + NumberConversions.square(this.z - o.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float angle(Vector other)
/*     */   {
/* 199 */     double dot = dot(other) / (length() * other.length());
/*     */     
/* 201 */     return (float)Math.acos(dot);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector midpoint(Vector other)
/*     */   {
/* 211 */     this.x = ((this.x + other.x) / 2.0D);
/* 212 */     this.y = ((this.y + other.y) / 2.0D);
/* 213 */     this.z = ((this.z + other.z) / 2.0D);
/* 214 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector getMidpoint(Vector other)
/*     */   {
/* 224 */     double x = (this.x + other.x) / 2.0D;
/* 225 */     double y = (this.y + other.y) / 2.0D;
/* 226 */     double z = (this.z + other.z) / 2.0D;
/* 227 */     return new Vector(x, y, z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector multiply(int m)
/*     */   {
/* 238 */     this.x *= m;
/* 239 */     this.y *= m;
/* 240 */     this.z *= m;
/* 241 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector multiply(double m)
/*     */   {
/* 252 */     this.x *= m;
/* 253 */     this.y *= m;
/* 254 */     this.z *= m;
/* 255 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector multiply(float m)
/*     */   {
/* 266 */     this.x *= m;
/* 267 */     this.y *= m;
/* 268 */     this.z *= m;
/* 269 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double dot(Vector other)
/*     */   {
/* 280 */     return this.x * other.x + this.y * other.y + this.z * other.z;
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
/*     */   public Vector crossProduct(Vector o)
/*     */   {
/* 296 */     double newX = this.y * o.z - o.y * this.z;
/* 297 */     double newY = this.z * o.x - o.z * this.x;
/* 298 */     double newZ = this.x * o.y - o.x * this.y;
/*     */     
/* 300 */     this.x = newX;
/* 301 */     this.y = newY;
/* 302 */     this.z = newZ;
/* 303 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector normalize()
/*     */   {
/* 312 */     double length = length();
/*     */     
/* 314 */     this.x /= length;
/* 315 */     this.y /= length;
/* 316 */     this.z /= length;
/*     */     
/* 318 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector zero()
/*     */   {
/* 327 */     this.x = 0.0D;
/* 328 */     this.y = 0.0D;
/* 329 */     this.z = 0.0D;
/* 330 */     return this;
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
/*     */   public boolean isInAABB(Vector min, Vector max)
/*     */   {
/* 344 */     return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInSphere(Vector origin, double radius)
/*     */   {
/* 355 */     return NumberConversions.square(origin.x - this.x) + NumberConversions.square(origin.y - this.y) + NumberConversions.square(origin.z - this.z) <= NumberConversions.square(radius);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/* 364 */     return this.x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockX()
/*     */   {
/* 374 */     return NumberConversions.floor(this.x);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/* 383 */     return this.y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockY()
/*     */   {
/* 393 */     return NumberConversions.floor(this.y);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/* 402 */     return this.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBlockZ()
/*     */   {
/* 412 */     return NumberConversions.floor(this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setX(int x)
/*     */   {
/* 422 */     this.x = x;
/* 423 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setX(double x)
/*     */   {
/* 433 */     this.x = x;
/* 434 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setX(float x)
/*     */   {
/* 444 */     this.x = x;
/* 445 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setY(int y)
/*     */   {
/* 455 */     this.y = y;
/* 456 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setY(double y)
/*     */   {
/* 466 */     this.y = y;
/* 467 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setY(float y)
/*     */   {
/* 477 */     this.y = y;
/* 478 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setZ(int z)
/*     */   {
/* 488 */     this.z = z;
/* 489 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setZ(double z)
/*     */   {
/* 499 */     this.z = z;
/* 500 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector setZ(float z)
/*     */   {
/* 510 */     this.z = z;
/* 511 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 523 */     if (!(obj instanceof Vector)) {
/* 524 */       return false;
/*     */     }
/*     */     
/* 527 */     Vector other = (Vector)obj;
/*     */     
/* 529 */     return (Math.abs(this.x - other.x) < 1.0E-6D) && (Math.abs(this.y - other.y) < 1.0E-6D) && (Math.abs(this.z - other.z) < 1.0E-6D) && (getClass().equals(obj.getClass()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 539 */     int hash = 7;
/*     */     
/* 541 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.x) ^ Double.doubleToLongBits(this.x) >>> 32);
/* 542 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.y) ^ Double.doubleToLongBits(this.y) >>> 32);
/* 543 */     hash = 79 * hash + (int)(Double.doubleToLongBits(this.z) ^ Double.doubleToLongBits(this.z) >>> 32);
/* 544 */     return hash;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector clone()
/*     */   {
/*     */     try
/*     */     {
/* 555 */       return (Vector)super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 557 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 566 */     return this.x + "," + this.y + "," + this.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location toLocation(World world)
/*     */   {
/* 576 */     return new Location(world, this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Location toLocation(World world, float yaw, float pitch)
/*     */   {
/* 588 */     return new Location(world, this.x, this.y, this.z, yaw, pitch);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockVector toBlockVector()
/*     */   {
/* 597 */     return new BlockVector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static double getEpsilon()
/*     */   {
/* 606 */     return 1.0E-6D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Vector getMinimum(Vector v1, Vector v2)
/*     */   {
/* 617 */     return new Vector(Math.min(v1.x, v2.x), Math.min(v1.y, v2.y), Math.min(v1.z, v2.z));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Vector getMaximum(Vector v1, Vector v2)
/*     */   {
/* 628 */     return new Vector(Math.max(v1.x, v2.x), Math.max(v1.y, v2.y), Math.max(v1.z, v2.z));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Vector getRandom()
/*     */   {
/* 638 */     return new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble());
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 642 */     Map<String, Object> result = new LinkedHashMap();
/*     */     
/* 644 */     result.put("x", Double.valueOf(getX()));
/* 645 */     result.put("y", Double.valueOf(getY()));
/* 646 */     result.put("z", Double.valueOf(getZ()));
/*     */     
/* 648 */     return result;
/*     */   }
/*     */   
/*     */   public static Vector deserialize(Map<String, Object> args) {
/* 652 */     double x = 0.0D;
/* 653 */     double y = 0.0D;
/* 654 */     double z = 0.0D;
/*     */     
/* 656 */     if (args.containsKey("x")) {
/* 657 */       x = ((Double)args.get("x")).doubleValue();
/*     */     }
/* 659 */     if (args.containsKey("y")) {
/* 660 */       y = ((Double)args.get("y")).doubleValue();
/*     */     }
/* 662 */     if (args.containsKey("z")) {
/* 663 */       z = ((Double)args.get("z")).doubleValue();
/*     */     }
/*     */     
/* 666 */     return new Vector(x, y, z);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\Vector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */