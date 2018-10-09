/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Navigation
/*     */   extends NavigationAbstract
/*     */ {
/*     */   protected PathfinderNormal a;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean f;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Navigation(EntityInsentient paramEntityInsentient, World paramWorld)
/*     */   {
/*  23 */     super(paramEntityInsentient, paramWorld);
/*     */   }
/*     */   
/*     */   protected Pathfinder a()
/*     */   {
/*  28 */     this.a = new PathfinderNormal();
/*  29 */     this.a.a(true);
/*  30 */     return new Pathfinder(this.a);
/*     */   }
/*     */   
/*     */   protected boolean b()
/*     */   {
/*  35 */     return (this.b.onGround) || ((h()) && (o())) || ((this.b.au()) && ((this.b instanceof EntityZombie)) && ((this.b.vehicle instanceof EntityChicken)));
/*     */   }
/*     */   
/*     */   protected Vec3D c()
/*     */   {
/*  40 */     return new Vec3D(this.b.locX, p(), this.b.locZ);
/*     */   }
/*     */   
/*     */   private int p() {
/*  44 */     if ((!this.b.V()) || (!h())) {
/*  45 */       return (int)(this.b.getBoundingBox().b + 0.5D);
/*     */     }
/*     */     
/*  48 */     int i = (int)this.b.getBoundingBox().b;
/*  49 */     Block localBlock = this.c.getType(new BlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ))).getBlock();
/*  50 */     int j = 0;
/*  51 */     while ((localBlock == Blocks.FLOWING_WATER) || (localBlock == Blocks.WATER)) {
/*  52 */       i++;
/*  53 */       localBlock = this.c.getType(new BlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ))).getBlock();
/*  54 */       j++; if (j > 16) {
/*  55 */         return (int)this.b.getBoundingBox().b;
/*     */       }
/*     */     }
/*  58 */     return i;
/*     */   }
/*     */   
/*     */   protected void d()
/*     */   {
/*  63 */     super.d();
/*  64 */     if (this.f) {
/*  65 */       if (this.c.i(new BlockPosition(MathHelper.floor(this.b.locX), (int)(this.b.getBoundingBox().b + 0.5D), MathHelper.floor(this.b.locZ)))) {
/*  66 */         return;
/*     */       }
/*     */       
/*  69 */       for (int i = 0; i < this.d.d(); i++) {
/*  70 */         PathPoint localPathPoint = this.d.a(i);
/*  71 */         if (this.c.i(new BlockPosition(localPathPoint.a, localPathPoint.b, localPathPoint.c))) {
/*  72 */           this.d.b(i - 1);
/*  73 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean a(Vec3D paramVec3D1, Vec3D paramVec3D2, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  81 */     int i = MathHelper.floor(paramVec3D1.a);
/*  82 */     int j = MathHelper.floor(paramVec3D1.c);
/*     */     
/*  84 */     double d1 = paramVec3D2.a - paramVec3D1.a;
/*  85 */     double d2 = paramVec3D2.c - paramVec3D1.c;
/*  86 */     double d3 = d1 * d1 + d2 * d2;
/*  87 */     if (d3 < 1.0E-8D) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     double d4 = 1.0D / Math.sqrt(d3);
/*  92 */     d1 *= d4;
/*  93 */     d2 *= d4;
/*     */     
/*  95 */     paramInt1 += 2;
/*  96 */     paramInt3 += 2;
/*  97 */     if (!a(i, (int)paramVec3D1.b, j, paramInt1, paramInt2, paramInt3, paramVec3D1, d1, d2)) {
/*  98 */       return false;
/*     */     }
/* 100 */     paramInt1 -= 2;
/* 101 */     paramInt3 -= 2;
/*     */     
/* 103 */     double d5 = 1.0D / Math.abs(d1);
/* 104 */     double d6 = 1.0D / Math.abs(d2);
/*     */     
/* 106 */     double d7 = i * 1 - paramVec3D1.a;
/* 107 */     double d8 = j * 1 - paramVec3D1.c;
/* 108 */     if (d1 >= 0.0D) {
/* 109 */       d7 += 1.0D;
/*     */     }
/* 111 */     if (d2 >= 0.0D) {
/* 112 */       d8 += 1.0D;
/*     */     }
/* 114 */     d7 /= d1;
/* 115 */     d8 /= d2;
/*     */     
/* 117 */     int k = d1 < 0.0D ? -1 : 1;
/* 118 */     int m = d2 < 0.0D ? -1 : 1;
/* 119 */     int n = MathHelper.floor(paramVec3D2.a);
/* 120 */     int i1 = MathHelper.floor(paramVec3D2.c);
/* 121 */     int i2 = n - i;
/* 122 */     int i3 = i1 - j;
/* 123 */     while ((i2 * k > 0) || (i3 * m > 0)) {
/* 124 */       if (d7 < d8) {
/* 125 */         d7 += d5;
/* 126 */         i += k;
/* 127 */         i2 = n - i;
/*     */       } else {
/* 129 */         d8 += d6;
/* 130 */         j += m;
/* 131 */         i3 = i1 - j;
/*     */       }
/*     */       
/* 134 */       if (!a(i, (int)paramVec3D1.b, j, paramInt1, paramInt2, paramInt3, paramVec3D1, d1, d2)) {
/* 135 */         return false;
/*     */       }
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   private boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Vec3D paramVec3D, double paramDouble1, double paramDouble2) {
/* 142 */     int i = paramInt1 - paramInt4 / 2;
/* 143 */     int j = paramInt3 - paramInt6 / 2;
/*     */     
/* 145 */     if (!b(i, paramInt2, j, paramInt4, paramInt5, paramInt6, paramVec3D, paramDouble1, paramDouble2)) {
/* 146 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 150 */     for (int k = i; k < i + paramInt4; k++) {
/* 151 */       for (int m = j; m < j + paramInt6; m++) {
/* 152 */         double d1 = k + 0.5D - paramVec3D.a;
/* 153 */         double d2 = m + 0.5D - paramVec3D.c;
/* 154 */         if (d1 * paramDouble1 + d2 * paramDouble2 >= 0.0D)
/*     */         {
/*     */ 
/* 157 */           Block localBlock = this.c.getType(new BlockPosition(k, paramInt2 - 1, m)).getBlock();
/* 158 */           Material localMaterial = localBlock.getMaterial();
/* 159 */           if (localMaterial == Material.AIR) {
/* 160 */             return false;
/*     */           }
/* 162 */           if ((localMaterial == Material.WATER) && (!this.b.V())) {
/* 163 */             return false;
/*     */           }
/* 165 */           if (localMaterial == Material.LAVA) {
/* 166 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   private boolean b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Vec3D paramVec3D, double paramDouble1, double paramDouble2) {
/* 175 */     for (BlockPosition localBlockPosition : BlockPosition.a(new BlockPosition(paramInt1, paramInt2, paramInt3), new BlockPosition(paramInt1 + paramInt4 - 1, paramInt2 + paramInt5 - 1, paramInt3 + paramInt6 - 1))) {
/* 176 */       double d1 = localBlockPosition.getX() + 0.5D - paramVec3D.a;
/* 177 */       double d2 = localBlockPosition.getZ() + 0.5D - paramVec3D.c;
/* 178 */       if (d1 * paramDouble1 + d2 * paramDouble2 >= 0.0D)
/*     */       {
/*     */ 
/* 181 */         Block localBlock = this.c.getType(localBlockPosition).getBlock();
/* 182 */         if (!localBlock.b(this.c, localBlockPosition))
/* 183 */           return false;
/*     */       }
/*     */     }
/* 186 */     return true;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 190 */     this.a.c(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 194 */     return this.a.e();
/*     */   }
/*     */   
/*     */   public void b(boolean paramBoolean) {
/* 198 */     this.a.b(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void c(boolean paramBoolean)
/*     */   {
/* 206 */     this.a.a(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 210 */     return this.a.b();
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/* 214 */     this.a.d(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean h() {
/* 218 */     return this.a.d();
/*     */   }
/*     */   
/*     */   public void e(boolean paramBoolean) {
/* 222 */     this.f = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Navigation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */