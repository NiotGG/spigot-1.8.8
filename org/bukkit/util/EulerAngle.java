/*     */ package org.bukkit.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EulerAngle
/*     */ {
/*  12 */   public static final EulerAngle ZERO = new EulerAngle(0.0D, 0.0D, 0.0D);
/*     */   
/*     */ 
/*     */   private final double x;
/*     */   
/*     */ 
/*     */   private final double y;
/*     */   
/*     */ 
/*     */   private final double z;
/*     */   
/*     */ 
/*     */ 
/*     */   public EulerAngle(double x, double y, double z)
/*     */   {
/*  27 */     this.x = x;
/*  28 */     this.y = y;
/*  29 */     this.z = z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getX()
/*     */   {
/*  38 */     return this.x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getY()
/*     */   {
/*  47 */     return this.y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getZ()
/*     */   {
/*  56 */     return this.z;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EulerAngle setX(double x)
/*     */   {
/*  67 */     return new EulerAngle(x, this.y, this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EulerAngle setY(double y)
/*     */   {
/*  78 */     return new EulerAngle(this.x, y, this.z);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EulerAngle setZ(double z)
/*     */   {
/*  89 */     return new EulerAngle(this.x, this.y, z);
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
/*     */   public EulerAngle add(double x, double y, double z)
/*     */   {
/* 102 */     return new EulerAngle(
/* 103 */       this.x + x, 
/* 104 */       this.y + y, 
/* 105 */       this.z + z);
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
/*     */   public EulerAngle subtract(double x, double y, double z)
/*     */   {
/* 119 */     return add(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 124 */     if (this == o) return true;
/* 125 */     if ((o == null) || (getClass() != o.getClass())) { return false;
/*     */     }
/* 127 */     EulerAngle that = (EulerAngle)o;
/*     */     
/* 129 */     return (Double.compare(that.x, this.x) == 0) && 
/* 130 */       (Double.compare(that.y, this.y) == 0) && 
/* 131 */       (Double.compare(that.z, this.z) == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 139 */     long temp = Double.doubleToLongBits(this.x);
/* 140 */     int result = (int)(temp ^ temp >>> 32);
/* 141 */     temp = Double.doubleToLongBits(this.y);
/* 142 */     result = 31 * result + (int)(temp ^ temp >>> 32);
/* 143 */     temp = Double.doubleToLongBits(this.z);
/* 144 */     result = 31 * result + (int)(temp ^ temp >>> 32);
/* 145 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\EulerAngle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */