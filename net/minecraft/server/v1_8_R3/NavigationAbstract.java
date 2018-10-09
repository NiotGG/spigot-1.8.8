/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public abstract class NavigationAbstract
/*     */ {
/*     */   protected EntityInsentient b;
/*     */   protected World c;
/*     */   protected PathEntity d;
/*     */   protected double e;
/*     */   private final AttributeInstance a;
/*     */   private int f;
/*     */   private int g;
/*  32 */   private Vec3D h = new Vec3D(0.0D, 0.0D, 0.0D);
/*  33 */   private float i = 1.0F;
/*     */   private final Pathfinder j;
/*     */   
/*     */   public NavigationAbstract(EntityInsentient paramEntityInsentient, World paramWorld)
/*     */   {
/*  38 */     this.b = paramEntityInsentient;
/*  39 */     this.c = paramWorld;
/*  40 */     this.a = paramEntityInsentient.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
/*  41 */     this.j = a();
/*     */   }
/*     */   
/*     */   protected abstract Pathfinder a();
/*     */   
/*     */   public void a(double paramDouble) {
/*  47 */     this.e = paramDouble;
/*     */   }
/*     */   
/*     */   public float i() {
/*  51 */     return (float)this.a.getValue();
/*     */   }
/*     */   
/*     */   public final PathEntity a(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  56 */     return a(new BlockPosition(MathHelper.floor(paramDouble1), (int)paramDouble2, MathHelper.floor(paramDouble3)));
/*     */   }
/*     */   
/*     */   public PathEntity a(BlockPosition paramBlockPosition)
/*     */   {
/*  61 */     if (!b()) {
/*  62 */       return null;
/*     */     }
/*     */     
/*  65 */     float f1 = i();
/*  66 */     this.c.methodProfiler.a("pathfind");
/*  67 */     BlockPosition localBlockPosition = new BlockPosition(this.b);
/*  68 */     int k = (int)(f1 + 8.0F);
/*     */     
/*  70 */     ChunkCache localChunkCache = new ChunkCache(this.c, localBlockPosition.a(-k, -k, -k), localBlockPosition.a(k, k, k), 0);
/*  71 */     PathEntity localPathEntity = this.j.a(localChunkCache, this.b, paramBlockPosition, f1);
/*  72 */     this.c.methodProfiler.b();
/*  73 */     return localPathEntity;
/*     */   }
/*     */   
/*     */   public boolean a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  77 */     PathEntity localPathEntity = a(MathHelper.floor(paramDouble1), (int)paramDouble2, MathHelper.floor(paramDouble3));
/*  78 */     return a(localPathEntity, paramDouble4);
/*     */   }
/*     */   
/*     */   public void a(float paramFloat) {
/*  82 */     this.i = paramFloat;
/*     */   }
/*     */   
/*     */   public PathEntity a(Entity paramEntity)
/*     */   {
/*  87 */     if (!b()) {
/*  88 */       return null;
/*     */     }
/*     */     
/*  91 */     float f1 = i();
/*  92 */     this.c.methodProfiler.a("pathfind");
/*  93 */     BlockPosition localBlockPosition = new BlockPosition(this.b).up();
/*  94 */     int k = (int)(f1 + 16.0F);
/*     */     
/*  96 */     ChunkCache localChunkCache = new ChunkCache(this.c, localBlockPosition.a(-k, -k, -k), localBlockPosition.a(k, k, k), 0);
/*  97 */     PathEntity localPathEntity = this.j.a(localChunkCache, this.b, paramEntity, f1);
/*  98 */     this.c.methodProfiler.b();
/*  99 */     return localPathEntity;
/*     */   }
/*     */   
/*     */   public boolean a(Entity paramEntity, double paramDouble) {
/* 103 */     PathEntity localPathEntity = a(paramEntity);
/* 104 */     if (localPathEntity != null) {
/* 105 */       return a(localPathEntity, paramDouble);
/*     */     }
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(PathEntity paramPathEntity, double paramDouble)
/*     */   {
/* 112 */     if (paramPathEntity == null) {
/* 113 */       this.d = null;
/* 114 */       return false;
/*     */     }
/* 116 */     if (!paramPathEntity.a(this.d)) {
/* 117 */       this.d = paramPathEntity;
/*     */     }
/* 119 */     d();
/* 120 */     if (this.d.d() == 0) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     this.e = paramDouble;
/* 125 */     Vec3D localVec3D = c();
/* 126 */     this.g = this.f;
/* 127 */     this.h = localVec3D;
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   public PathEntity j() {
/* 132 */     return this.d;
/*     */   }
/*     */   
/*     */   public void k() {
/* 136 */     this.f += 1;
/* 137 */     if (m()) {
/* 138 */       return;
/*     */     }
/*     */     
/* 141 */     if (b()) {
/* 142 */       l();
/*     */     }
/* 144 */     else if ((this.d != null) && (this.d.e() < this.d.d())) {
/* 145 */       localVec3D = c();
/* 146 */       localObject = this.d.a(this.b, this.d.e());
/* 147 */       if ((localVec3D.b > ((Vec3D)localObject).b) && (!this.b.onGround) && (MathHelper.floor(localVec3D.a) == MathHelper.floor(((Vec3D)localObject).a)) && (MathHelper.floor(localVec3D.c) == MathHelper.floor(((Vec3D)localObject).c))) {
/* 148 */         this.d.c(this.d.e() + 1);
/*     */       }
/*     */     }
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
/* 165 */     if (m()) {
/* 166 */       return;
/*     */     }
/* 168 */     Vec3D localVec3D = this.d.a(this.b);
/* 169 */     if (localVec3D == null) {
/* 170 */       return;
/*     */     }
/*     */     
/* 173 */     Object localObject = new AxisAlignedBB(localVec3D.a, localVec3D.b, localVec3D.c, localVec3D.a, localVec3D.b, localVec3D.c).grow(0.5D, 0.5D, 0.5D);
/* 174 */     List localList = this.c.getCubes(this.b, ((AxisAlignedBB)localObject).a(0.0D, -1.0D, 0.0D));
/* 175 */     double d1 = -1.0D;
/* 176 */     localObject = ((AxisAlignedBB)localObject).c(0.0D, 1.0D, 0.0D);
/* 177 */     for (AxisAlignedBB localAxisAlignedBB : localList) {
/* 178 */       d1 = localAxisAlignedBB.b((AxisAlignedBB)localObject, d1);
/*     */     }
/*     */     
/* 181 */     this.b.getControllerMove().a(localVec3D.a, localVec3D.b + d1, localVec3D.c, this.e);
/*     */   }
/*     */   
/*     */   protected void l() {
/* 185 */     Vec3D localVec3D1 = c();
/*     */     
/*     */ 
/* 188 */     int k = this.d.d();
/* 189 */     for (int m = this.d.e(); m < this.d.d(); m++) {
/* 190 */       if (this.d.a(m).b != (int)localVec3D1.b) {
/* 191 */         k = m;
/* 192 */         break;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 198 */     float f1 = this.b.width * this.b.width * this.i;
/* 199 */     for (int n = this.d.e(); n < k; n++) {
/* 200 */       Vec3D localVec3D2 = this.d.a(this.b, n);
/* 201 */       if (localVec3D1.distanceSquared(localVec3D2) < f1) {
/* 202 */         this.d.c(n + 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 207 */     n = MathHelper.f(this.b.width);
/* 208 */     int i1 = (int)this.b.length + 1;
/* 209 */     int i2 = n;
/* 210 */     for (int i3 = k - 1; i3 >= this.d.e(); i3--) {
/* 211 */       if (a(localVec3D1, this.d.a(this.b, i3), n, i1, i2)) {
/* 212 */         this.d.c(i3);
/* 213 */         break;
/*     */       }
/*     */     }
/*     */     
/* 217 */     a(localVec3D1);
/*     */   }
/*     */   
/*     */   protected void a(Vec3D paramVec3D)
/*     */   {
/* 222 */     if (this.f - this.g > 100) {
/* 223 */       if (paramVec3D.distanceSquared(this.h) < 2.25D) {
/* 224 */         n();
/*     */       }
/* 226 */       this.g = this.f;
/* 227 */       this.h = paramVec3D;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean m() {
/* 232 */     return (this.d == null) || (this.d.b());
/*     */   }
/*     */   
/*     */   public void n() {
/* 236 */     this.d = null;
/*     */   }
/*     */   
/*     */   protected abstract Vec3D c();
/*     */   
/*     */   protected abstract boolean b();
/*     */   
/*     */   protected boolean o() {
/* 244 */     return (this.b.V()) || (this.b.ab());
/*     */   }
/*     */   
/*     */   protected void d() {}
/*     */   
/*     */   protected abstract boolean a(Vec3D paramVec3D1, Vec3D paramVec3D2, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NavigationAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */