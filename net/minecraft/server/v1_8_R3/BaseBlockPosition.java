/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseBlockPosition
/*     */   implements Comparable<BaseBlockPosition>
/*     */ {
/*  12 */   public static final BaseBlockPosition ZERO = new BaseBlockPosition(0, 0, 0);
/*     */   private final int a;
/*     */   private final int c;
/*     */   private final int d;
/*     */   
/*     */   public BaseBlockPosition(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  19 */     this.a = paramInt1;
/*  20 */     this.c = paramInt2;
/*  21 */     this.d = paramInt3;
/*     */   }
/*     */   
/*     */   public BaseBlockPosition(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  25 */     this(MathHelper.floor(paramDouble1), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3));
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  30 */     if (this == paramObject) {
/*  31 */       return true;
/*     */     }
/*  33 */     if (!(paramObject instanceof BaseBlockPosition)) {
/*  34 */       return false;
/*     */     }
/*     */     
/*  37 */     BaseBlockPosition localBaseBlockPosition = (BaseBlockPosition)paramObject;
/*     */     
/*  39 */     if (getX() != localBaseBlockPosition.getX()) {
/*  40 */       return false;
/*     */     }
/*  42 */     if (getY() != localBaseBlockPosition.getY()) {
/*  43 */       return false;
/*     */     }
/*  45 */     if (getZ() != localBaseBlockPosition.getZ()) {
/*  46 */       return false;
/*     */     }
/*     */     
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  54 */     return (getY() + getZ() * 31) * 31 + getX();
/*     */   }
/*     */   
/*     */   public int g(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/*  59 */     if (getY() == paramBaseBlockPosition.getY()) {
/*  60 */       if (getZ() == paramBaseBlockPosition.getZ()) {
/*  61 */         return getX() - paramBaseBlockPosition.getX();
/*     */       }
/*  63 */       return getZ() - paramBaseBlockPosition.getZ();
/*     */     }
/*  65 */     return getY() - paramBaseBlockPosition.getY();
/*     */   }
/*     */   
/*     */   public int getX() {
/*  69 */     return this.a;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  73 */     return this.c;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  77 */     return this.d;
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
/*     */   public BaseBlockPosition d(BaseBlockPosition paramBaseBlockPosition)
/*     */   {
/* 187 */     return new BaseBlockPosition(getY() * paramBaseBlockPosition.getZ() - getZ() * paramBaseBlockPosition.getY(), getZ() * paramBaseBlockPosition.getX() - getX() * paramBaseBlockPosition.getZ(), getX() * paramBaseBlockPosition.getY() - getY() * paramBaseBlockPosition.getX());
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
/*     */   public double c(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 203 */     double d1 = getX() - paramDouble1;
/* 204 */     double d2 = getY() - paramDouble2;
/* 205 */     double d3 = getZ() - paramDouble3;
/* 206 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */   
/*     */   public double d(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 210 */     double d1 = getX() + 0.5D - paramDouble1;
/* 211 */     double d2 = getY() + 0.5D - paramDouble2;
/* 212 */     double d3 = getZ() + 0.5D - paramDouble3;
/* 213 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */   
/*     */   public double i(BaseBlockPosition paramBaseBlockPosition) {
/* 217 */     return c(paramBaseBlockPosition.getX(), paramBaseBlockPosition.getY(), paramBaseBlockPosition.getZ());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 222 */     return Objects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BaseBlockPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */