/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class Vec3D
/*     */ {
/*     */   public final double a;
/*     */   public final double b;
/*     */   public final double c;
/*     */   
/*     */   public Vec3D(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  12 */     if (paramDouble1 == -0.0D) {
/*  13 */       paramDouble1 = 0.0D;
/*     */     }
/*  15 */     if (paramDouble2 == -0.0D) {
/*  16 */       paramDouble2 = 0.0D;
/*     */     }
/*  18 */     if (paramDouble3 == -0.0D) {
/*  19 */       paramDouble3 = 0.0D;
/*     */     }
/*  21 */     this.a = paramDouble1;
/*  22 */     this.b = paramDouble2;
/*  23 */     this.c = paramDouble3;
/*     */   }
/*     */   
/*     */   public Vec3D(BaseBlockPosition paramBaseBlockPosition) {
/*  27 */     this(paramBaseBlockPosition.getX(), paramBaseBlockPosition.getY(), paramBaseBlockPosition.getZ());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vec3D a()
/*     */   {
/*  35 */     double d = MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
/*  36 */     if (d < 1.0E-4D) {
/*  37 */       return new Vec3D(0.0D, 0.0D, 0.0D);
/*     */     }
/*  39 */     return new Vec3D(this.a / d, this.b / d, this.c / d);
/*     */   }
/*     */   
/*     */   public double b(Vec3D paramVec3D) {
/*  43 */     return this.a * paramVec3D.a + this.b * paramVec3D.b + this.c * paramVec3D.c;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vec3D d(Vec3D paramVec3D)
/*     */   {
/*  51 */     return a(paramVec3D.a, paramVec3D.b, paramVec3D.c);
/*     */   }
/*     */   
/*     */   public Vec3D a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  55 */     return add(-paramDouble1, -paramDouble2, -paramDouble3);
/*     */   }
/*     */   
/*     */   public Vec3D e(Vec3D paramVec3D) {
/*  59 */     return add(paramVec3D.a, paramVec3D.b, paramVec3D.c);
/*     */   }
/*     */   
/*     */   public Vec3D add(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  63 */     return new Vec3D(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double distanceSquared(Vec3D paramVec3D)
/*     */   {
/*  74 */     double d1 = paramVec3D.a - this.a;
/*  75 */     double d2 = paramVec3D.b - this.b;
/*  76 */     double d3 = paramVec3D.c - this.c;
/*  77 */     return d1 * d1 + d2 * d2 + d3 * d3;
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
/*     */   public double b()
/*     */   {
/*  92 */     return MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
/*     */   }
/*     */   
/*     */   public Vec3D a(Vec3D paramVec3D, double paramDouble) {
/*  96 */     double d1 = paramVec3D.a - this.a;
/*  97 */     double d2 = paramVec3D.b - this.b;
/*  98 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 100 */     if (d1 * d1 < 1.0000000116860974E-7D) {
/* 101 */       return null;
/*     */     }
/*     */     
/* 104 */     double d4 = (paramDouble - this.a) / d1;
/* 105 */     if ((d4 < 0.0D) || (d4 > 1.0D)) {
/* 106 */       return null;
/*     */     }
/* 108 */     return new Vec3D(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D b(Vec3D paramVec3D, double paramDouble) {
/* 112 */     double d1 = paramVec3D.a - this.a;
/* 113 */     double d2 = paramVec3D.b - this.b;
/* 114 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 116 */     if (d2 * d2 < 1.0000000116860974E-7D) {
/* 117 */       return null;
/*     */     }
/*     */     
/* 120 */     double d4 = (paramDouble - this.b) / d2;
/* 121 */     if ((d4 < 0.0D) || (d4 > 1.0D)) {
/* 122 */       return null;
/*     */     }
/* 124 */     return new Vec3D(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D c(Vec3D paramVec3D, double paramDouble) {
/* 128 */     double d1 = paramVec3D.a - this.a;
/* 129 */     double d2 = paramVec3D.b - this.b;
/* 130 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 132 */     if (d3 * d3 < 1.0000000116860974E-7D) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     double d4 = (paramDouble - this.c) / d3;
/* 137 */     if ((d4 < 0.0D) || (d4 > 1.0D)) {
/* 138 */       return null;
/*     */     }
/* 140 */     return new Vec3D(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 144 */     return "(" + this.a + ", " + this.b + ", " + this.c + ")";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vec3D a(float paramFloat)
/*     */   {
/* 152 */     float f1 = MathHelper.cos(paramFloat);
/* 153 */     float f2 = MathHelper.sin(paramFloat);
/*     */     
/* 155 */     double d1 = this.a;
/* 156 */     double d2 = this.b * f1 + this.c * f2;
/* 157 */     double d3 = this.c * f1 - this.b * f2;
/*     */     
/* 159 */     return new Vec3D(d1, d2, d3);
/*     */   }
/*     */   
/*     */   public Vec3D b(float paramFloat) {
/* 163 */     float f1 = MathHelper.cos(paramFloat);
/* 164 */     float f2 = MathHelper.sin(paramFloat);
/*     */     
/* 166 */     double d1 = this.a * f1 + this.c * f2;
/* 167 */     double d2 = this.b;
/* 168 */     double d3 = this.c * f1 - this.a * f2;
/*     */     
/* 170 */     return new Vec3D(d1, d2, d3);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Vec3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */