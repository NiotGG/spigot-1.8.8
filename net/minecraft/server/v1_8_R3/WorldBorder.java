/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
/*     */ 
/*     */ public class WorldBorder
/*     */ {
/*   9 */   private final List<IWorldBorderListener> a = com.google.common.collect.Lists.newArrayList();
/*  10 */   private double b = 0.0D;
/*  11 */   private double c = 0.0D;
/*  12 */   private double d = 6.0E7D;
/*     */   private double e;
/*     */   private long f;
/*     */   private long g;
/*     */   private int h;
/*     */   private double i;
/*     */   private double j;
/*     */   private int k;
/*     */   private int l;
/*     */   public WorldServer world;
/*     */   
/*     */   public WorldBorder() {
/*  24 */     this.e = this.d;
/*  25 */     this.h = 29999984;
/*  26 */     this.i = 0.2D;
/*  27 */     this.j = 5.0D;
/*  28 */     this.k = 15;
/*  29 */     this.l = 5;
/*     */   }
/*     */   
/*     */   public boolean a(BlockPosition blockposition) {
/*  33 */     return (blockposition.getX() + 1 > b()) && (blockposition.getX() < d()) && (blockposition.getZ() + 1 > c()) && (blockposition.getZ() < e());
/*     */   }
/*     */   
/*     */   public boolean isInBounds(ChunkCoordIntPair chunkcoordintpair)
/*     */   {
/*  38 */     return isInBounds(chunkcoordintpair.x, chunkcoordintpair.z);
/*     */   }
/*     */   
/*     */   public boolean isInBounds(long chunkcoords)
/*     */   {
/*  43 */     return isInBounds(LongHash.msw(chunkcoords), LongHash.lsw(chunkcoords));
/*     */   }
/*     */   
/*     */   public boolean isInBounds(int x, int z)
/*     */   {
/*  48 */     return ((x << 4) + 15 > b()) && (x << 4 < d()) && ((z << 4) + 15 > c()) && (x << 4 < e());
/*     */   }
/*     */   
/*     */   public boolean a(AxisAlignedBB axisalignedbb) {
/*  52 */     return (axisalignedbb.d > b()) && (axisalignedbb.a < d()) && (axisalignedbb.f > c()) && (axisalignedbb.c < e());
/*     */   }
/*     */   
/*     */   public double a(Entity entity) {
/*  56 */     return b(entity.locX, entity.locZ);
/*     */   }
/*     */   
/*     */   public double b(double d0, double d1) {
/*  60 */     double d2 = d1 - c();
/*  61 */     double d3 = e() - d1;
/*  62 */     double d4 = d0 - b();
/*  63 */     double d5 = d() - d0;
/*  64 */     double d6 = Math.min(d4, d5);
/*     */     
/*  66 */     d6 = Math.min(d6, d2);
/*  67 */     return Math.min(d6, d3);
/*     */   }
/*     */   
/*     */   public EnumWorldBorderState getState() {
/*  71 */     return this.e > this.d ? EnumWorldBorderState.GROWING : this.e < this.d ? EnumWorldBorderState.SHRINKING : EnumWorldBorderState.STATIONARY;
/*     */   }
/*     */   
/*     */   public double b() {
/*  75 */     double d0 = getCenterX() - getSize() / 2.0D;
/*     */     
/*  77 */     if (d0 < -this.h) {
/*  78 */       d0 = -this.h;
/*     */     }
/*     */     
/*  81 */     return d0;
/*     */   }
/*     */   
/*     */   public double c() {
/*  85 */     double d0 = getCenterZ() - getSize() / 2.0D;
/*     */     
/*  87 */     if (d0 < -this.h) {
/*  88 */       d0 = -this.h;
/*     */     }
/*     */     
/*  91 */     return d0;
/*     */   }
/*     */   
/*     */   public double d() {
/*  95 */     double d0 = getCenterX() + getSize() / 2.0D;
/*     */     
/*  97 */     if (d0 > this.h) {
/*  98 */       d0 = this.h;
/*     */     }
/*     */     
/* 101 */     return d0;
/*     */   }
/*     */   
/*     */   public double e() {
/* 105 */     double d0 = getCenterZ() + getSize() / 2.0D;
/*     */     
/* 107 */     if (d0 > this.h) {
/* 108 */       d0 = this.h;
/*     */     }
/*     */     
/* 111 */     return d0;
/*     */   }
/*     */   
/*     */   public double getCenterX() {
/* 115 */     return this.b;
/*     */   }
/*     */   
/*     */   public double getCenterZ() {
/* 119 */     return this.c;
/*     */   }
/*     */   
/*     */   public void setCenter(double d0, double d1) {
/* 123 */     this.b = d0;
/* 124 */     this.c = d1;
/* 125 */     Iterator iterator = k().iterator();
/*     */     
/* 127 */     while (iterator.hasNext()) {
/* 128 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 130 */       iworldborderlistener.a(this, d0, d1);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getSize()
/*     */   {
/* 136 */     if (getState() != EnumWorldBorderState.STATIONARY) {
/* 137 */       double d0 = (float)(System.currentTimeMillis() - this.g) / (float)(this.f - this.g);
/*     */       
/* 139 */       if (d0 < 1.0D) {
/* 140 */         return this.d + (this.e - this.d) * d0;
/*     */       }
/*     */       
/* 143 */       setSize(this.e);
/*     */     }
/*     */     
/* 146 */     return this.d;
/*     */   }
/*     */   
/*     */   public long i() {
/* 150 */     return getState() != EnumWorldBorderState.STATIONARY ? this.f - System.currentTimeMillis() : 0L;
/*     */   }
/*     */   
/*     */   public double j() {
/* 154 */     return this.e;
/*     */   }
/*     */   
/*     */   public void setSize(double d0) {
/* 158 */     this.d = d0;
/* 159 */     this.e = d0;
/* 160 */     this.f = System.currentTimeMillis();
/* 161 */     this.g = this.f;
/* 162 */     Iterator iterator = k().iterator();
/*     */     
/* 164 */     while (iterator.hasNext()) {
/* 165 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 167 */       iworldborderlistener.a(this, d0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void transitionSizeBetween(double d0, double d1, long i)
/*     */   {
/* 173 */     this.d = d0;
/* 174 */     this.e = d1;
/* 175 */     this.g = System.currentTimeMillis();
/* 176 */     this.f = (this.g + i);
/* 177 */     Iterator iterator = k().iterator();
/*     */     
/* 179 */     while (iterator.hasNext()) {
/* 180 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 182 */       iworldborderlistener.a(this, d0, d1, i);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List<IWorldBorderListener> k()
/*     */   {
/* 188 */     return com.google.common.collect.Lists.newArrayList(this.a);
/*     */   }
/*     */   
/*     */   public void a(IWorldBorderListener iworldborderlistener) {
/* 192 */     if (this.a.contains(iworldborderlistener)) return;
/* 193 */     this.a.add(iworldborderlistener);
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 197 */     this.h = i;
/*     */   }
/*     */   
/*     */   public int l() {
/* 201 */     return this.h;
/*     */   }
/*     */   
/*     */   public double getDamageBuffer() {
/* 205 */     return this.j;
/*     */   }
/*     */   
/*     */   public void setDamageBuffer(double d0) {
/* 209 */     this.j = d0;
/* 210 */     Iterator iterator = k().iterator();
/*     */     
/* 212 */     while (iterator.hasNext()) {
/* 213 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 215 */       iworldborderlistener.c(this, d0);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getDamageAmount()
/*     */   {
/* 221 */     return this.i;
/*     */   }
/*     */   
/*     */   public void setDamageAmount(double d0) {
/* 225 */     this.i = d0;
/* 226 */     Iterator iterator = k().iterator();
/*     */     
/* 228 */     while (iterator.hasNext()) {
/* 229 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 231 */       iworldborderlistener.b(this, d0);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWarningTime()
/*     */   {
/* 237 */     return this.k;
/*     */   }
/*     */   
/*     */   public void setWarningTime(int i) {
/* 241 */     this.k = i;
/* 242 */     Iterator iterator = k().iterator();
/*     */     
/* 244 */     while (iterator.hasNext()) {
/* 245 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 247 */       iworldborderlistener.a(this, i);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getWarningDistance()
/*     */   {
/* 253 */     return this.l;
/*     */   }
/*     */   
/*     */   public void setWarningDistance(int i) {
/* 257 */     this.l = i;
/* 258 */     Iterator iterator = k().iterator();
/*     */     
/* 260 */     while (iterator.hasNext()) {
/* 261 */       IWorldBorderListener iworldborderlistener = (IWorldBorderListener)iterator.next();
/*     */       
/* 263 */       iworldborderlistener.b(this, i);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */