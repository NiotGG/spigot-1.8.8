/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
/*     */ import org.bukkit.event.entity.EntityCreatePortalEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class EntityEnderDragon extends EntityInsentient implements IComplex, IMonster
/*     */ {
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*  24 */   public double[][] bk = new double[64][3];
/*  25 */   public int bl = -1;
/*     */   public EntityComplexPart[] children;
/*     */   public EntityComplexPart bn;
/*     */   public EntityComplexPart bo;
/*     */   public EntityComplexPart bp;
/*     */   public EntityComplexPart bq;
/*     */   public EntityComplexPart br;
/*     */   public EntityComplexPart bs;
/*     */   public EntityComplexPart bt;
/*     */   public float bu;
/*     */   public float bv;
/*     */   public boolean bw;
/*     */   public boolean bx;
/*     */   public Entity target;
/*     */   public int by;
/*     */   public EntityEnderCrystal bz;
/*  41 */   private Explosion explosionSource = new Explosion(null, this, NaN.0D, NaN.0D, NaN.0D, NaN.0F, true, true);
/*     */   
/*     */   public EntityEnderDragon(World world) {
/*  44 */     super(world);
/*  45 */     this.children = new EntityComplexPart[] { this.bn = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.bo = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bp = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bq = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.br = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bs = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bt = new EntityComplexPart(this, "wing", 4.0F, 4.0F) };
/*  46 */     setHealth(getMaxHealth());
/*  47 */     setSize(16.0F, 8.0F);
/*  48 */     this.noclip = true;
/*  49 */     this.fireProof = true;
/*  50 */     this.b = 100.0D;
/*  51 */     this.ah = true;
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  55 */     super.initAttributes();
/*  56 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0D);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  60 */     super.h();
/*     */   }
/*     */   
/*     */   public double[] b(int i, float f) {
/*  64 */     if (getHealth() <= 0.0F) {
/*  65 */       f = 0.0F;
/*     */     }
/*     */     
/*  68 */     f = 1.0F - f;
/*  69 */     int j = this.bl - i * 1 & 0x3F;
/*  70 */     int k = this.bl - i * 1 - 1 & 0x3F;
/*  71 */     double[] adouble = new double[3];
/*  72 */     double d0 = this.bk[j][0];
/*  73 */     double d1 = MathHelper.g(this.bk[k][0] - d0);
/*     */     
/*  75 */     adouble[0] = (d0 + d1 * f);
/*  76 */     d0 = this.bk[j][1];
/*  77 */     d1 = this.bk[k][1] - d0;
/*  78 */     adouble[1] = (d0 + d1 * f);
/*  79 */     adouble[2] = (this.bk[j][2] + (this.bk[k][2] - this.bk[j][2]) * f);
/*  80 */     return adouble;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void m()
/*     */   {
/*  87 */     if (this.world.isClientSide) {
/*  88 */       float f = MathHelper.cos(this.bv * 3.1415927F * 2.0F);
/*  89 */       float f1 = MathHelper.cos(this.bu * 3.1415927F * 2.0F);
/*  90 */       if ((f1 <= -0.3F) && (f >= -0.3F) && (!R())) {
/*  91 */         this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
/*     */       }
/*     */     }
/*     */     
/*  95 */     this.bu = this.bv;
/*     */     
/*     */ 
/*  98 */     if (getHealth() <= 0.0F) {
/*  99 */       float f = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 100 */       float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
/* 101 */       float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 102 */       this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX + f, this.locY + 2.0D + f1, this.locZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } else {
/* 104 */       n();
/* 105 */       float f = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
/* 106 */       f *= (float)Math.pow(2.0D, this.motY);
/* 107 */       if (this.bx) {
/* 108 */         this.bv += f * 0.5F;
/*     */       } else {
/* 110 */         this.bv += f;
/*     */       }
/*     */       
/* 113 */       this.yaw = MathHelper.g(this.yaw);
/* 114 */       if (ce()) {
/* 115 */         this.bv = 0.5F;
/*     */       } else {
/* 117 */         if (this.bl < 0) {
/* 118 */           for (int i = 0; i < this.bk.length; i++) {
/* 119 */             this.bk[i][0] = this.yaw;
/* 120 */             this.bk[i][1] = this.locY;
/*     */           }
/*     */         }
/*     */         
/* 124 */         if (++this.bl == this.bk.length) {
/* 125 */           this.bl = 0;
/*     */         }
/*     */         
/* 128 */         this.bk[this.bl][0] = this.yaw;
/* 129 */         this.bk[this.bl][1] = this.locY;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */         if (this.world.isClientSide) {
/* 137 */           if (this.bc > 0) {
/* 138 */             double d3 = this.locX + (this.bd - this.locX) / this.bc;
/* 139 */             double d0 = this.locY + (this.be - this.locY) / this.bc;
/* 140 */             double d1 = this.locZ + (this.bf - this.locZ) / this.bc;
/* 141 */             double d2 = MathHelper.g(this.bg - this.yaw);
/* 142 */             this.yaw = ((float)(this.yaw + d2 / this.bc));
/* 143 */             this.pitch = ((float)(this.pitch + (this.bh - this.pitch) / this.bc));
/* 144 */             this.bc -= 1;
/* 145 */             setPosition(d3, d0, d1);
/* 146 */             setYawPitch(this.yaw, this.pitch);
/*     */           }
/*     */         } else {
/* 149 */           double d3 = this.a - this.locX;
/* 150 */           double d0 = this.b - this.locY;
/* 151 */           double d1 = this.c - this.locZ;
/* 152 */           double d2 = d3 * d3 + d0 * d0 + d1 * d1;
/*     */           
/*     */ 
/* 155 */           if (this.target != null) {
/* 156 */             this.a = this.target.locX;
/* 157 */             this.c = this.target.locZ;
/* 158 */             double d5 = this.a - this.locX;
/* 159 */             double d6 = this.c - this.locZ;
/* 160 */             double d7 = Math.sqrt(d5 * d5 + d6 * d6);
/*     */             
/* 162 */             double d4 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
/* 163 */             if (d4 > 10.0D) {
/* 164 */               d4 = 10.0D;
/*     */             }
/*     */             
/* 167 */             this.b = (this.target.getBoundingBox().b + d4);
/*     */           } else {
/* 169 */             this.a += this.random.nextGaussian() * 2.0D;
/* 170 */             this.c += this.random.nextGaussian() * 2.0D;
/*     */           }
/*     */           
/* 173 */           if ((this.bw) || (d2 < 100.0D) || (d2 > 22500.0D) || (this.positionChanged) || (this.E)) {
/* 174 */             cf();
/*     */           }
/*     */           
/* 177 */           d0 /= MathHelper.sqrt(d3 * d3 + d1 * d1);
/* 178 */           float f3 = 0.6F;
/* 179 */           d0 = MathHelper.a(d0, -f3, f3);
/* 180 */           this.motY += d0 * 0.10000000149011612D;
/* 181 */           this.yaw = MathHelper.g(this.yaw);
/* 182 */           double d8 = 180.0D - MathHelper.b(d3, d1) * 180.0D / 3.1415927410125732D;
/* 183 */           double d9 = MathHelper.g(d8 - this.yaw);
/*     */           
/* 185 */           if (d9 > 50.0D) {
/* 186 */             d9 = 50.0D;
/*     */           }
/*     */           
/* 189 */           if (d9 < -50.0D) {
/* 190 */             d9 = -50.0D;
/*     */           }
/*     */           
/* 193 */           Vec3D vec3d = new Vec3D(this.a - this.locX, this.b - this.locY, this.c - this.locZ).a();
/*     */           
/* 195 */           double d4 = -MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/* 196 */           Vec3D vec3d1 = new Vec3D(MathHelper.sin(this.yaw * 3.1415927F / 180.0F), this.motY, d4).a();
/* 197 */           float f4 = ((float)vec3d1.b(vec3d) + 0.5F) / 1.5F;
/*     */           
/* 199 */           if (f4 < 0.0F) {
/* 200 */             f4 = 0.0F;
/*     */           }
/*     */           
/* 203 */           this.bb *= 0.8F;
/* 204 */           float f5 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0F + 1.0F;
/* 205 */           double d10 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0D + 1.0D;
/*     */           
/* 207 */           if (d10 > 40.0D) {
/* 208 */             d10 = 40.0D;
/*     */           }
/*     */           
/* 211 */           this.bb = ((float)(this.bb + d9 * (0.699999988079071D / d10 / f5)));
/* 212 */           this.yaw += this.bb * 0.1F;
/* 213 */           float f6 = (float)(2.0D / (d10 + 1.0D));
/* 214 */           float f7 = 0.06F;
/*     */           
/* 216 */           a(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));
/* 217 */           if (this.bx) {
/* 218 */             move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
/*     */           } else {
/* 220 */             move(this.motX, this.motY, this.motZ);
/*     */           }
/*     */           
/* 223 */           Vec3D vec3d2 = new Vec3D(this.motX, this.motY, this.motZ).a();
/* 224 */           float f8 = ((float)vec3d2.b(vec3d1) + 1.0F) / 2.0F;
/*     */           
/* 226 */           f8 = 0.8F + 0.15F * f8;
/* 227 */           this.motX *= f8;
/* 228 */           this.motZ *= f8;
/* 229 */           this.motY *= 0.9100000262260437D;
/*     */         }
/*     */         
/* 232 */         this.aI = this.yaw;
/* 233 */         this.bn.width = (this.bn.length = 3.0F);
/* 234 */         this.bp.width = (this.bp.length = 2.0F);
/* 235 */         this.bq.width = (this.bq.length = 2.0F);
/* 236 */         this.br.width = (this.br.length = 2.0F);
/* 237 */         this.bo.length = 3.0F;
/* 238 */         this.bo.width = 5.0F;
/* 239 */         this.bs.length = 2.0F;
/* 240 */         this.bs.width = 4.0F;
/* 241 */         this.bt.length = 3.0F;
/* 242 */         this.bt.width = 4.0F;
/* 243 */         float f1 = (float)(b(5, 1.0F)[1] - b(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
/* 244 */         float f2 = MathHelper.cos(f1);
/* 245 */         float f9 = -MathHelper.sin(f1);
/* 246 */         float f10 = this.yaw * 3.1415927F / 180.0F;
/* 247 */         float f11 = MathHelper.sin(f10);
/* 248 */         float f12 = MathHelper.cos(f10);
/*     */         
/* 250 */         this.bo.t_();
/* 251 */         this.bo.setPositionRotation(this.locX + f11 * 0.5F, this.locY, this.locZ - f12 * 0.5F, 0.0F, 0.0F);
/* 252 */         this.bs.t_();
/* 253 */         this.bs.setPositionRotation(this.locX + f12 * 4.5F, this.locY + 2.0D, this.locZ + f11 * 4.5F, 0.0F, 0.0F);
/* 254 */         this.bt.t_();
/* 255 */         this.bt.setPositionRotation(this.locX - f12 * 4.5F, this.locY + 2.0D, this.locZ - f11 * 4.5F, 0.0F, 0.0F);
/* 256 */         if ((!this.world.isClientSide) && (this.hurtTicks == 0)) {
/* 257 */           a(this.world.getEntities(this, this.bs.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
/* 258 */           a(this.world.getEntities(this, this.bt.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
/* 259 */           b(this.world.getEntities(this, this.bn.getBoundingBox().grow(1.0D, 1.0D, 1.0D)));
/*     */         }
/*     */         
/* 262 */         double[] adouble = b(5, 1.0F);
/* 263 */         double[] adouble1 = b(0, 1.0F);
/*     */         
/* 265 */         float f3 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);
/* 266 */         float f13 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F - this.bb * 0.01F);
/*     */         
/* 268 */         this.bn.t_();
/* 269 */         this.bn.setPositionRotation(this.locX + f3 * 5.5F * f2, this.locY + (adouble1[1] - adouble[1]) * 1.0D + f9 * 5.5F, this.locZ - f13 * 5.5F * f2, 0.0F, 0.0F);
/*     */         
/* 271 */         for (int j = 0; j < 3; j++) {
/* 272 */           EntityComplexPart entitycomplexpart = null;
/*     */           
/* 274 */           if (j == 0) {
/* 275 */             entitycomplexpart = this.bp;
/*     */           }
/*     */           
/* 278 */           if (j == 1) {
/* 279 */             entitycomplexpart = this.bq;
/*     */           }
/*     */           
/* 282 */           if (j == 2) {
/* 283 */             entitycomplexpart = this.br;
/*     */           }
/*     */           
/* 286 */           double[] adouble2 = b(12 + j * 2, 1.0F);
/* 287 */           float f14 = this.yaw * 3.1415927F / 180.0F + b(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
/* 288 */           float f15 = MathHelper.sin(f14);
/* 289 */           float f16 = MathHelper.cos(f14);
/* 290 */           float f17 = 1.5F;
/* 291 */           float f18 = (j + 1) * 2.0F;
/*     */           
/* 293 */           entitycomplexpart.t_();
/* 294 */           entitycomplexpart.setPositionRotation(this.locX - (f11 * f17 + f15 * f18) * f2, this.locY + (adouble2[1] - adouble[1]) * 1.0D - (f18 + f17) * f9 + 1.5D, this.locZ + (f12 * f17 + f16 * f18) * f2, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 297 */         if (!this.world.isClientSide) {
/* 298 */           this.bx = (b(this.bn.getBoundingBox()) | b(this.bo.getBoundingBox()));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void n()
/*     */   {
/* 306 */     if (this.bz != null) {
/* 307 */       if (this.bz.dead) {
/* 308 */         if (!this.world.isClientSide) {
/* 309 */           org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = this.bz;
/* 310 */           a(this.bn, DamageSource.explosion(null), 10.0F);
/* 311 */           org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = null;
/*     */         }
/*     */         
/* 314 */         this.bz = null;
/* 315 */       } else if ((this.ticksLived % 10 == 0) && (getHealth() < getMaxHealth()))
/*     */       {
/* 317 */         EntityRegainHealthEvent event = new EntityRegainHealthEvent(getBukkitEntity(), 1.0D, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
/* 318 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 320 */         if (!event.isCancelled()) {
/* 321 */           setHealth((float)(getHealth() + event.getAmount()));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 327 */     if (this.random.nextInt(10) == 0) {
/* 328 */       float f = 32.0F;
/* 329 */       List list = this.world.a(EntityEnderCrystal.class, getBoundingBox().grow(f, f, f));
/* 330 */       EntityEnderCrystal entityendercrystal = null;
/* 331 */       double d0 = Double.MAX_VALUE;
/* 332 */       Iterator iterator = list.iterator();
/*     */       
/* 334 */       while (iterator.hasNext()) {
/* 335 */         EntityEnderCrystal entityendercrystal1 = (EntityEnderCrystal)iterator.next();
/* 336 */         double d1 = entityendercrystal1.h(this);
/*     */         
/* 338 */         if (d1 < d0) {
/* 339 */           d0 = d1;
/* 340 */           entityendercrystal = entityendercrystal1;
/*     */         }
/*     */       }
/*     */       
/* 344 */       this.bz = entityendercrystal;
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(List<Entity> list)
/*     */   {
/* 350 */     double d0 = (this.bo.getBoundingBox().a + this.bo.getBoundingBox().d) / 2.0D;
/* 351 */     double d1 = (this.bo.getBoundingBox().c + this.bo.getBoundingBox().f) / 2.0D;
/* 352 */     Iterator iterator = list.iterator();
/*     */     
/* 354 */     while (iterator.hasNext()) {
/* 355 */       Entity entity = (Entity)iterator.next();
/*     */       
/* 357 */       if ((entity instanceof EntityLiving)) {
/* 358 */         double d2 = entity.locX - d0;
/* 359 */         double d3 = entity.locZ - d1;
/* 360 */         double d4 = d2 * d2 + d3 * d3;
/*     */         
/* 362 */         entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void b(List<Entity> list)
/*     */   {
/* 369 */     for (int i = 0; i < list.size(); i++) {
/* 370 */       Entity entity = (Entity)list.get(i);
/*     */       
/* 372 */       if ((entity instanceof EntityLiving)) {
/* 373 */         entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
/* 374 */         a(this, entity);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void cf()
/*     */   {
/* 381 */     this.bw = false;
/* 382 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList(this.world.players);
/* 383 */     Iterator iterator = arraylist.iterator();
/*     */     
/* 385 */     while (iterator.hasNext()) {
/* 386 */       if (((EntityHuman)iterator.next()).isSpectator()) {
/* 387 */         iterator.remove();
/*     */       }
/*     */     }
/*     */     
/* 391 */     if ((this.random.nextInt(2) == 0) && (!arraylist.isEmpty()))
/*     */     {
/* 393 */       Entity target = (Entity)this.world.players.get(this.random.nextInt(this.world.players.size()));
/* 394 */       EntityTargetEvent event = new EntityTargetEvent(getBukkitEntity(), target.getBukkitEntity(), org.bukkit.event.entity.EntityTargetEvent.TargetReason.RANDOM_TARGET);
/* 395 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 397 */       if (!event.isCancelled()) {
/* 398 */         if (event.getTarget() == null) {
/* 399 */           this.target = null;
/*     */         } else {
/* 401 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       boolean flag;
/*     */       do {
/* 409 */         this.a = 0.0D;
/* 410 */         this.b = (70.0F + this.random.nextFloat() * 50.0F);
/* 411 */         this.c = 0.0D;
/* 412 */         this.a += this.random.nextFloat() * 120.0F - 60.0F;
/* 413 */         this.c += this.random.nextFloat() * 120.0F - 60.0F;
/* 414 */         double d0 = this.locX - this.a;
/* 415 */         double d1 = this.locY - this.b;
/* 416 */         double d2 = this.locZ - this.c;
/*     */         
/* 418 */         flag = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
/* 419 */       } while (!flag);
/*     */       
/* 421 */       this.target = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private float b(double d0)
/*     */   {
/* 427 */     return (float)MathHelper.g(d0);
/*     */   }
/*     */   
/*     */   private boolean b(AxisAlignedBB axisalignedbb) {
/* 431 */     int i = MathHelper.floor(axisalignedbb.a);
/* 432 */     int j = MathHelper.floor(axisalignedbb.b);
/* 433 */     int k = MathHelper.floor(axisalignedbb.c);
/* 434 */     int l = MathHelper.floor(axisalignedbb.d);
/* 435 */     int i1 = MathHelper.floor(axisalignedbb.e);
/* 436 */     int j1 = MathHelper.floor(axisalignedbb.f);
/* 437 */     boolean flag = false;
/* 438 */     boolean flag1 = false;
/*     */     
/*     */ 
/* 441 */     List<org.bukkit.block.Block> destroyedBlocks = new ArrayList();
/* 442 */     CraftWorld craftWorld = this.world.getWorld();
/*     */     
/*     */     BlockPosition blockposition;
/* 445 */     for (int k1 = i; k1 <= l; k1++) {
/* 446 */       for (int l1 = j; l1 <= i1; l1++) {
/* 447 */         for (int i2 = k; i2 <= j1; i2++) {
/* 448 */           blockposition = new BlockPosition(k1, l1, i2);
/* 449 */           Block block = this.world.getType(blockposition).getBlock();
/*     */           
/* 451 */           if (block.getMaterial() != Material.AIR) {
/* 452 */             if ((block != Blocks.BARRIER) && (block != Blocks.OBSIDIAN) && (block != Blocks.END_STONE) && (block != Blocks.BEDROCK) && (block != Blocks.COMMAND_BLOCK) && (this.world.getGameRules().getBoolean("mobGriefing")))
/*     */             {
/*     */ 
/* 455 */               flag1 = true;
/* 456 */               destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
/*     */             }
/*     */             else {
/* 459 */               flag = true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 466 */     if (flag1)
/*     */     {
/* 468 */       org.bukkit.entity.Entity bukkitEntity = getBukkitEntity();
/* 469 */       EntityExplodeEvent event = new EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0F);
/* 470 */       org.bukkit.Bukkit.getPluginManager().callEvent(event);
/* 471 */       if (event.isCancelled())
/*     */       {
/*     */ 
/* 474 */         return flag; }
/* 475 */       if (event.getYield() == 0.0F)
/*     */       {
/* 477 */         for (org.bukkit.block.Block block : event.blockList()) {
/* 478 */           this.world.setAir(new BlockPosition(block.getX(), block.getY(), block.getZ()));
/*     */         }
/*     */       } else {
/* 481 */         for (org.bukkit.block.Block block : event.blockList()) {
/* 482 */           org.bukkit.Material blockId = block.getType();
/* 483 */           if (blockId != org.bukkit.Material.AIR)
/*     */           {
/*     */ 
/*     */ 
/* 487 */             int blockX = block.getX();
/* 488 */             int blockY = block.getY();
/* 489 */             int blockZ = block.getZ();
/*     */             
/* 491 */             Block nmsBlock = org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(blockId);
/* 492 */             if (nmsBlock.a(this.explosionSource)) {
/* 493 */               nmsBlock.dropNaturally(this.world, new BlockPosition(blockX, blockY, blockZ), nmsBlock.fromLegacyData(block.getData()), event.getYield(), 0);
/*     */             }
/* 495 */             nmsBlock.wasExploded(this.world, new BlockPosition(blockX, blockY, blockZ), this.explosionSource);
/*     */             
/* 497 */             this.world.setAir(new BlockPosition(blockX, blockY, blockZ));
/*     */           }
/*     */         }
/*     */       }
/* 501 */       double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * this.random.nextFloat();
/* 502 */       double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * this.random.nextFloat();
/* 503 */       double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * this.random.nextFloat();
/*     */       
/* 505 */       this.world.addParticle(EnumParticle.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     
/* 508 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
/* 512 */     if (entitycomplexpart != this.bn) {
/* 513 */       f = f / 4.0F + 1.0F;
/*     */     }
/*     */     
/* 516 */     float f1 = this.yaw * 3.1415927F / 180.0F;
/* 517 */     float f2 = MathHelper.sin(f1);
/* 518 */     float f3 = MathHelper.cos(f1);
/*     */     
/* 520 */     this.a = (this.locX + f2 * 5.0F + (this.random.nextFloat() - 0.5F) * 2.0F);
/* 521 */     this.b = (this.locY + this.random.nextFloat() * 3.0F + 1.0D);
/* 522 */     this.c = (this.locZ - f3 * 5.0F + (this.random.nextFloat() - 0.5F) * 2.0F);
/* 523 */     this.target = null;
/* 524 */     if (((damagesource.getEntity() instanceof EntityHuman)) || (damagesource.isExplosion())) {
/* 525 */       dealDamage(damagesource, f);
/*     */     }
/*     */     
/* 528 */     return true;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 532 */     if (((damagesource instanceof EntityDamageSource)) && (((EntityDamageSource)damagesource).w())) {
/* 533 */       dealDamage(damagesource, f);
/*     */     }
/*     */     
/* 536 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean dealDamage(DamageSource damagesource, float f) {
/* 540 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   public void G() {
/* 544 */     die();
/*     */   }
/*     */   
/*     */   protected void aZ() {
/* 548 */     if (this.dead) return;
/* 549 */     this.by += 1;
/* 550 */     if ((this.by >= 180) && (this.by <= 200)) {
/* 551 */       float f = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 552 */       float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
/* 553 */       float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/*     */       
/* 555 */       this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.locX + f, this.locY + 2.0D + f1, this.locZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     
/* 558 */     boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
/*     */     
/*     */ 
/*     */ 
/* 562 */     if (!this.world.isClientSide) {
/* 563 */       if ((this.by > 150) && (this.by % 5 == 0) && (flag)) {
/* 564 */         int i = this.expToDrop / 12;
/*     */         
/* 566 */         while (i > 0) {
/* 567 */           int j = EntityExperienceOrb.getOrbValue(i);
/* 568 */           i -= j;
/* 569 */           this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*     */         }
/*     */       }
/*     */       
/* 573 */       if (this.by == 1)
/*     */       {
/*     */ 
/* 576 */         int viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
/* 577 */         for (EntityPlayer player : MinecraftServer.getServer().getPlayerList().players) {
/* 578 */           double deltaX = this.locX - player.locX;
/* 579 */           double deltaZ = this.locZ - player.locZ;
/* 580 */           double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
/* 581 */           if ((this.world.spigotConfig.dragonDeathSoundRadius <= 0) || (distanceSquared <= this.world.spigotConfig.dragonDeathSoundRadius * this.world.spigotConfig.dragonDeathSoundRadius)) {
/* 582 */             if (distanceSquared > viewDistance * viewDistance) {
/* 583 */               double deltaLength = Math.sqrt(distanceSquared);
/* 584 */               double relativeX = player.locX + deltaX / deltaLength * viewDistance;
/* 585 */               double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
/* 586 */               player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int)relativeX, (int)this.locY, (int)relativeZ), 0, true));
/*     */             } else {
/* 588 */               player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1018, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0, true));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 595 */     move(0.0D, 0.10000000149011612D, 0.0D);
/* 596 */     this.aI = (this.yaw += 20.0F);
/* 597 */     if ((this.by == 200) && (!this.world.isClientSide)) {
/* 598 */       if (flag) {
/* 599 */         int i = this.expToDrop - 10 * this.expToDrop / 12;
/*     */         
/* 601 */         while (i > 0) {
/* 602 */           int j = EntityExperienceOrb.getOrbValue(i);
/* 603 */           i -= j;
/* 604 */           this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
/*     */         }
/*     */       }
/*     */       
/* 608 */       a(new BlockPosition(this.locX, 64.0D, this.locZ));
/* 609 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void a(BlockPosition blockposition)
/*     */   {
/* 620 */     BlockStateListPopulator world = new BlockStateListPopulator(this.world.getWorld());
/*     */     
/* 622 */     for (int i = -1; i <= 32; i++) {
/* 623 */       for (int j = -4; j <= 4; j++) {
/* 624 */         for (int k = -4; k <= 4; k++) {
/* 625 */           double d2 = j * j + k * k;
/*     */           
/* 627 */           if (d2 <= 12.25D) {
/* 628 */             BlockPosition blockposition1 = blockposition.a(j, i, k);
/*     */             
/* 630 */             if (i < 0) {
/* 631 */               if (d2 <= 6.25D) {
/* 632 */                 world.setTypeUpdate(blockposition1, Blocks.BEDROCK.getBlockData());
/*     */               }
/* 634 */             } else if (i > 0) {
/* 635 */               world.setTypeUpdate(blockposition1, Blocks.AIR.getBlockData());
/* 636 */             } else if (d2 > 6.25D) {
/* 637 */               world.setTypeUpdate(blockposition1, Blocks.BEDROCK.getBlockData());
/*     */             } else {
/* 639 */               world.setTypeUpdate(blockposition1, Blocks.END_PORTAL.getBlockData());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 646 */     world.setTypeUpdate(blockposition, Blocks.BEDROCK.getBlockData());
/* 647 */     world.setTypeUpdate(blockposition.up(), Blocks.BEDROCK.getBlockData());
/* 648 */     BlockPosition blockposition2 = blockposition.up(2);
/*     */     
/* 650 */     world.setTypeUpdate(blockposition2, Blocks.BEDROCK.getBlockData());
/* 651 */     world.setTypeUpdate(blockposition2.west(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.EAST));
/* 652 */     world.setTypeUpdate(blockposition2.east(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.WEST));
/* 653 */     world.setTypeUpdate(blockposition2.north(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.SOUTH));
/* 654 */     world.setTypeUpdate(blockposition2.south(), Blocks.TORCH.getBlockData().set(BlockTorch.FACING, EnumDirection.NORTH));
/* 655 */     world.setTypeUpdate(blockposition.up(3), Blocks.BEDROCK.getBlockData());
/* 656 */     world.setTypeUpdate(blockposition.up(4), Blocks.DRAGON_EGG.getBlockData());
/*     */     
/* 658 */     EntityCreatePortalEvent event = new EntityCreatePortalEvent((org.bukkit.entity.LivingEntity)getBukkitEntity(), java.util.Collections.unmodifiableList(world.getList()), org.bukkit.PortalType.ENDER);
/* 659 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 661 */     if (!event.isCancelled()) {
/* 662 */       for (BlockState state : event.getBlocks())
/* 663 */         state.update(true);
/*     */     } else {
/*     */       Iterator it;
/* 666 */       for (??? = event.getBlocks().iterator(); ???.hasNext(); 
/*     */           
/* 668 */           it.hasNext())
/*     */       {
/* 666 */         BlockState state = (BlockState)???.next();
/* 667 */         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(this.world, new BlockPosition(state.getX(), state.getY(), state.getZ()));
/* 668 */         it = this.world.players.iterator(); continue;
/* 669 */         EntityHuman entity = (EntityHuman)it.next();
/* 670 */         if ((entity instanceof EntityPlayer)) {
/* 671 */           ((EntityPlayer)entity).playerConnection.sendPacket(packet);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void D() {}
/*     */   
/*     */   public Entity[] aB()
/*     */   {
/* 682 */     return this.children;
/*     */   }
/*     */   
/*     */   public boolean ad() {
/* 686 */     return false;
/*     */   }
/*     */   
/*     */   public World a() {
/* 690 */     return this.world;
/*     */   }
/*     */   
/*     */   protected String z() {
/* 694 */     return "mob.enderdragon.growl";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 698 */     return "mob.enderdragon.hit";
/*     */   }
/*     */   
/*     */   protected float bB() {
/* 702 */     return 5.0F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getExpReward()
/*     */   {
/* 709 */     return 12000;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEnderDragon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */