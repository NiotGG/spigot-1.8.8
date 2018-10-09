/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class AxisAlignedBB
/*     */ {
/*     */   public final double a;
/*     */   public final double b;
/*     */   public final double c;
/*     */   public final double d;
/*     */   public final double e;
/*     */   public final double f;
/*     */   
/*     */   public AxisAlignedBB(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  13 */     this.a = Math.min(paramDouble1, paramDouble4);
/*  14 */     this.b = Math.min(paramDouble2, paramDouble5);
/*  15 */     this.c = Math.min(paramDouble3, paramDouble6);
/*  16 */     this.d = Math.max(paramDouble1, paramDouble4);
/*  17 */     this.e = Math.max(paramDouble2, paramDouble5);
/*  18 */     this.f = Math.max(paramDouble3, paramDouble6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB(BlockPosition paramBlockPosition1, BlockPosition paramBlockPosition2) {
/*  22 */     this.a = paramBlockPosition1.getX();
/*  23 */     this.b = paramBlockPosition1.getY();
/*  24 */     this.c = paramBlockPosition1.getZ();
/*  25 */     this.d = paramBlockPosition2.getX();
/*  26 */     this.e = paramBlockPosition2.getY();
/*  27 */     this.f = paramBlockPosition2.getZ();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  31 */     double d1 = this.a;
/*  32 */     double d2 = this.b;
/*  33 */     double d3 = this.c;
/*  34 */     double d4 = this.d;
/*  35 */     double d5 = this.e;
/*  36 */     double d6 = this.f;
/*     */     
/*  38 */     if (paramDouble1 < 0.0D) {
/*  39 */       d1 += paramDouble1;
/*  40 */     } else if (paramDouble1 > 0.0D) {
/*  41 */       d4 += paramDouble1;
/*     */     }
/*     */     
/*  44 */     if (paramDouble2 < 0.0D) {
/*  45 */       d2 += paramDouble2;
/*  46 */     } else if (paramDouble2 > 0.0D) {
/*  47 */       d5 += paramDouble2;
/*     */     }
/*     */     
/*  50 */     if (paramDouble3 < 0.0D) {
/*  51 */       d3 += paramDouble3;
/*  52 */     } else if (paramDouble3 > 0.0D) {
/*  53 */       d6 += paramDouble3;
/*     */     }
/*     */     
/*  56 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB grow(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  60 */     double d1 = this.a - paramDouble1;
/*  61 */     double d2 = this.b - paramDouble2;
/*  62 */     double d3 = this.c - paramDouble3;
/*  63 */     double d4 = this.d + paramDouble1;
/*  64 */     double d5 = this.e + paramDouble2;
/*  65 */     double d6 = this.f + paramDouble3;
/*     */     
/*  67 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(AxisAlignedBB paramAxisAlignedBB) {
/*  71 */     double d1 = Math.min(this.a, paramAxisAlignedBB.a);
/*  72 */     double d2 = Math.min(this.b, paramAxisAlignedBB.b);
/*  73 */     double d3 = Math.min(this.c, paramAxisAlignedBB.c);
/*  74 */     double d4 = Math.max(this.d, paramAxisAlignedBB.d);
/*  75 */     double d5 = Math.max(this.e, paramAxisAlignedBB.e);
/*  76 */     double d6 = Math.max(this.f, paramAxisAlignedBB.f);
/*     */     
/*  78 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public static AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  82 */     double d1 = Math.min(paramDouble1, paramDouble4);
/*  83 */     double d2 = Math.min(paramDouble2, paramDouble5);
/*  84 */     double d3 = Math.min(paramDouble3, paramDouble6);
/*  85 */     double d4 = Math.max(paramDouble1, paramDouble4);
/*  86 */     double d5 = Math.max(paramDouble2, paramDouble5);
/*  87 */     double d6 = Math.max(paramDouble3, paramDouble6);
/*     */     
/*  89 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  93 */     return new AxisAlignedBB(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3, this.d + paramDouble1, this.e + paramDouble2, this.f + paramDouble3);
/*     */   }
/*     */   
/*     */   public double a(AxisAlignedBB paramAxisAlignedBB, double paramDouble)
/*     */   {
/*  98 */     if ((paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e) || (paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) {
/*  99 */       return paramDouble;
/*     */     }
/*     */     
/*     */     double d1;
/* 103 */     if ((paramDouble > 0.0D) && (paramAxisAlignedBB.d <= this.a)) {
/* 104 */       d1 = this.a - paramAxisAlignedBB.d;
/* 105 */       if (d1 < paramDouble) {
/* 106 */         paramDouble = d1;
/*     */       }
/* 108 */     } else if ((paramDouble < 0.0D) && (paramAxisAlignedBB.a >= this.d)) {
/* 109 */       d1 = this.d - paramAxisAlignedBB.a;
/* 110 */       if (d1 > paramDouble) {
/* 111 */         paramDouble = d1;
/*     */       }
/*     */     }
/*     */     
/* 115 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double b(AxisAlignedBB paramAxisAlignedBB, double paramDouble)
/*     */   {
/* 120 */     if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d) || (paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) {
/* 121 */       return paramDouble;
/*     */     }
/*     */     
/*     */     double d1;
/* 125 */     if ((paramDouble > 0.0D) && (paramAxisAlignedBB.e <= this.b)) {
/* 126 */       d1 = this.b - paramAxisAlignedBB.e;
/* 127 */       if (d1 < paramDouble) {
/* 128 */         paramDouble = d1;
/*     */       }
/* 130 */     } else if ((paramDouble < 0.0D) && (paramAxisAlignedBB.b >= this.e)) {
/* 131 */       d1 = this.e - paramAxisAlignedBB.b;
/* 132 */       if (d1 > paramDouble) {
/* 133 */         paramDouble = d1;
/*     */       }
/*     */     }
/*     */     
/* 137 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double c(AxisAlignedBB paramAxisAlignedBB, double paramDouble)
/*     */   {
/* 142 */     if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d) || (paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e)) {
/* 143 */       return paramDouble;
/*     */     }
/*     */     
/*     */     double d1;
/* 147 */     if ((paramDouble > 0.0D) && (paramAxisAlignedBB.f <= this.c)) {
/* 148 */       d1 = this.c - paramAxisAlignedBB.f;
/* 149 */       if (d1 < paramDouble) {
/* 150 */         paramDouble = d1;
/*     */       }
/* 152 */     } else if ((paramDouble < 0.0D) && (paramAxisAlignedBB.c >= this.f)) {
/* 153 */       d1 = this.f - paramAxisAlignedBB.c;
/* 154 */       if (d1 > paramDouble) {
/* 155 */         paramDouble = d1;
/*     */       }
/*     */     }
/*     */     
/* 159 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public boolean b(AxisAlignedBB paramAxisAlignedBB) {
/* 163 */     if ((paramAxisAlignedBB.d <= this.a) || (paramAxisAlignedBB.a >= this.d)) {
/* 164 */       return false;
/*     */     }
/* 166 */     if ((paramAxisAlignedBB.e <= this.b) || (paramAxisAlignedBB.b >= this.e)) {
/* 167 */       return false;
/*     */     }
/* 169 */     if ((paramAxisAlignedBB.f <= this.c) || (paramAxisAlignedBB.c >= this.f)) {
/* 170 */       return false;
/*     */     }
/* 172 */     return true;
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
/*     */   public boolean a(Vec3D paramVec3D)
/*     */   {
/* 202 */     if ((paramVec3D.a <= this.a) || (paramVec3D.a >= this.d)) {
/* 203 */       return false;
/*     */     }
/* 205 */     if ((paramVec3D.b <= this.b) || (paramVec3D.b >= this.e)) {
/* 206 */       return false;
/*     */     }
/* 208 */     if ((paramVec3D.c <= this.c) || (paramVec3D.c >= this.f)) {
/* 209 */       return false;
/*     */     }
/* 211 */     return true;
/*     */   }
/*     */   
/*     */   public double a() {
/* 215 */     double d1 = this.d - this.a;
/* 216 */     double d2 = this.e - this.b;
/* 217 */     double d3 = this.f - this.c;
/* 218 */     return (d1 + d2 + d3) / 3.0D;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB shrink(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 222 */     double d1 = this.a + paramDouble1;
/* 223 */     double d2 = this.b + paramDouble2;
/* 224 */     double d3 = this.c + paramDouble3;
/* 225 */     double d4 = this.d - paramDouble1;
/* 226 */     double d5 = this.e - paramDouble2;
/* 227 */     double d6 = this.f - paramDouble3;
/*     */     
/* 229 */     return new AxisAlignedBB(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(Vec3D paramVec3D1, Vec3D paramVec3D2)
/*     */   {
/* 234 */     Vec3D localVec3D1 = paramVec3D1.a(paramVec3D2, this.a);
/* 235 */     Vec3D localVec3D2 = paramVec3D1.a(paramVec3D2, this.d);
/*     */     
/* 237 */     Vec3D localVec3D3 = paramVec3D1.b(paramVec3D2, this.b);
/* 238 */     Vec3D localVec3D4 = paramVec3D1.b(paramVec3D2, this.e);
/*     */     
/* 240 */     Vec3D localVec3D5 = paramVec3D1.c(paramVec3D2, this.c);
/* 241 */     Vec3D localVec3D6 = paramVec3D1.c(paramVec3D2, this.f);
/*     */     
/* 243 */     if (!b(localVec3D1)) {
/* 244 */       localVec3D1 = null;
/*     */     }
/* 246 */     if (!b(localVec3D2)) {
/* 247 */       localVec3D2 = null;
/*     */     }
/* 249 */     if (!c(localVec3D3)) {
/* 250 */       localVec3D3 = null;
/*     */     }
/* 252 */     if (!c(localVec3D4)) {
/* 253 */       localVec3D4 = null;
/*     */     }
/* 255 */     if (!d(localVec3D5)) {
/* 256 */       localVec3D5 = null;
/*     */     }
/* 258 */     if (!d(localVec3D6)) {
/* 259 */       localVec3D6 = null;
/*     */     }
/*     */     
/* 262 */     Vec3D localVec3D7 = null;
/*     */     
/* 264 */     if (localVec3D1 != null) {
/* 265 */       localVec3D7 = localVec3D1;
/*     */     }
/* 267 */     if ((localVec3D2 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D2) < paramVec3D1.distanceSquared(localVec3D7)))) {
/* 268 */       localVec3D7 = localVec3D2;
/*     */     }
/* 270 */     if ((localVec3D3 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D3) < paramVec3D1.distanceSquared(localVec3D7)))) {
/* 271 */       localVec3D7 = localVec3D3;
/*     */     }
/* 273 */     if ((localVec3D4 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D4) < paramVec3D1.distanceSquared(localVec3D7)))) {
/* 274 */       localVec3D7 = localVec3D4;
/*     */     }
/* 276 */     if ((localVec3D5 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D5) < paramVec3D1.distanceSquared(localVec3D7)))) {
/* 277 */       localVec3D7 = localVec3D5;
/*     */     }
/* 279 */     if ((localVec3D6 != null) && ((localVec3D7 == null) || (paramVec3D1.distanceSquared(localVec3D6) < paramVec3D1.distanceSquared(localVec3D7)))) {
/* 280 */       localVec3D7 = localVec3D6;
/*     */     }
/*     */     
/* 283 */     if (localVec3D7 == null) {
/* 284 */       return null;
/*     */     }
/*     */     
/* 287 */     EnumDirection localEnumDirection = null;
/*     */     
/* 289 */     if (localVec3D7 == localVec3D1) {
/* 290 */       localEnumDirection = EnumDirection.WEST;
/* 291 */     } else if (localVec3D7 == localVec3D2) {
/* 292 */       localEnumDirection = EnumDirection.EAST;
/* 293 */     } else if (localVec3D7 == localVec3D3) {
/* 294 */       localEnumDirection = EnumDirection.DOWN;
/* 295 */     } else if (localVec3D7 == localVec3D4) {
/* 296 */       localEnumDirection = EnumDirection.UP;
/* 297 */     } else if (localVec3D7 == localVec3D5) {
/* 298 */       localEnumDirection = EnumDirection.NORTH;
/*     */     } else {
/* 300 */       localEnumDirection = EnumDirection.SOUTH;
/*     */     }
/*     */     
/* 303 */     return new MovingObjectPosition(localVec3D7, localEnumDirection);
/*     */   }
/*     */   
/*     */   private boolean b(Vec3D paramVec3D) {
/* 307 */     if (paramVec3D == null) {
/* 308 */       return false;
/*     */     }
/* 310 */     return (paramVec3D.b >= this.b) && (paramVec3D.b <= this.e) && (paramVec3D.c >= this.c) && (paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean c(Vec3D paramVec3D) {
/* 314 */     if (paramVec3D == null) {
/* 315 */       return false;
/*     */     }
/* 317 */     return (paramVec3D.a >= this.a) && (paramVec3D.a <= this.d) && (paramVec3D.c >= this.c) && (paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean d(Vec3D paramVec3D) {
/* 321 */     if (paramVec3D == null) {
/* 322 */       return false;
/*     */     }
/* 324 */     return (paramVec3D.a >= this.a) && (paramVec3D.a <= this.d) && (paramVec3D.b >= this.b) && (paramVec3D.b <= this.e);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 328 */     return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AxisAlignedBB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */