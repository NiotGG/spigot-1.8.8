/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public abstract class EntityFireball extends Entity
/*     */ {
/*   9 */   private int e = -1;
/*  10 */   private int f = -1;
/*  11 */   private int g = -1;
/*     */   private Block h;
/*     */   private boolean i;
/*     */   public EntityLiving shooter;
/*     */   private int ar;
/*     */   private int as;
/*     */   public double dirX;
/*     */   public double dirY;
/*     */   public double dirZ;
/*  20 */   public float bukkitYield = 1.0F;
/*  21 */   public boolean isIncendiary = true;
/*     */   
/*     */   public EntityFireball(World world) {
/*  24 */     super(world);
/*  25 */     setSize(1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public EntityFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
/*  31 */     super(world);
/*  32 */     setSize(1.0F, 1.0F);
/*  33 */     setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
/*  34 */     setPosition(d0, d1, d2);
/*  35 */     double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */     
/*  37 */     this.dirX = (d3 / d6 * 0.1D);
/*  38 */     this.dirY = (d4 / d6 * 0.1D);
/*  39 */     this.dirZ = (d5 / d6 * 0.1D);
/*     */   }
/*     */   
/*     */   public EntityFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/*  43 */     super(world);
/*  44 */     this.shooter = entityliving;
/*  45 */     this.projectileSource = ((org.bukkit.entity.LivingEntity)entityliving.getBukkitEntity());
/*  46 */     setSize(1.0F, 1.0F);
/*  47 */     setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  48 */     setPosition(this.locX, this.locY, this.locZ);
/*  49 */     this.motX = (this.motY = this.motZ = 0.0D);
/*     */     
/*  51 */     setDirection(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public void setDirection(double d0, double d1, double d2)
/*     */   {
/*  56 */     d0 += this.random.nextGaussian() * 0.4D;
/*  57 */     d1 += this.random.nextGaussian() * 0.4D;
/*  58 */     d2 += this.random.nextGaussian() * 0.4D;
/*  59 */     double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  61 */     this.dirX = (d0 / d3 * 0.1D);
/*  62 */     this.dirY = (d1 / d3 * 0.1D);
/*  63 */     this.dirZ = (d2 / d3 * 0.1D);
/*     */   }
/*     */   
/*     */   public void t_() {
/*  67 */     if ((!this.world.isClientSide) && (((this.shooter != null) && (this.shooter.dead)) || (!this.world.isLoaded(new BlockPosition(this))))) {
/*  68 */       die();
/*     */     } else {
/*  70 */       super.t_();
/*  71 */       setOnFire(1);
/*  72 */       if (this.i) {
/*  73 */         if (this.world.getType(new BlockPosition(this.e, this.f, this.g)).getBlock() == this.h) {
/*  74 */           this.ar += 1;
/*  75 */           if (this.ar == 600) {
/*  76 */             die();
/*     */           }
/*     */           
/*  79 */           return;
/*     */         }
/*     */         
/*  82 */         this.i = false;
/*  83 */         this.motX *= this.random.nextFloat() * 0.2F;
/*  84 */         this.motY *= this.random.nextFloat() * 0.2F;
/*  85 */         this.motZ *= this.random.nextFloat() * 0.2F;
/*  86 */         this.ar = 0;
/*  87 */         this.as = 0;
/*     */       } else {
/*  89 */         this.as += 1;
/*     */       }
/*     */       
/*  92 */       Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/*  93 */       Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*  94 */       MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);
/*     */       
/*  96 */       vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/*  97 */       vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/*  98 */       if (movingobjectposition != null) {
/*  99 */         vec3d1 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */       }
/*     */       
/* 102 */       Entity entity = null;
/* 103 */       List list = this.world.getEntities(this, getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 104 */       double d0 = 0.0D;
/*     */       
/* 106 */       for (int i = 0; i < list.size(); i++) {
/* 107 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 109 */         if ((entity1.ad()) && ((!entity1.k(this.shooter)) || (this.as >= 25))) {
/* 110 */           float f = 0.3F;
/* 111 */           AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(f, f, f);
/* 112 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 114 */           if (movingobjectposition1 != null) {
/* 115 */             double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
/*     */             
/* 117 */             if ((d1 < d0) || (d0 == 0.0D)) {
/* 118 */               entity = entity1;
/* 119 */               d0 = d1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 125 */       if (entity != null) {
/* 126 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 129 */       if (movingobjectposition != null) {
/* 130 */         a(movingobjectposition);
/*     */         
/*     */ 
/* 133 */         if (this.dead) {
/* 134 */           CraftEventFactory.callProjectileHitEvent(this);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 139 */       this.locX += this.motX;
/* 140 */       this.locY += this.motY;
/* 141 */       this.locZ += this.motZ;
/* 142 */       float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 144 */       this.yaw = ((float)(MathHelper.b(this.motZ, this.motX) * 180.0D / 3.1415927410125732D) + 90.0F);
/*     */       
/* 146 */       for (this.pitch = ((float)(MathHelper.b(f1, this.motY) * 180.0D / 3.1415927410125732D) - 90.0F); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
/*     */       
/*     */ 
/*     */ 
/* 150 */       while (this.pitch - this.lastPitch >= 180.0F) {
/* 151 */         this.lastPitch += 360.0F;
/*     */       }
/*     */       
/* 154 */       while (this.yaw - this.lastYaw < -180.0F) {
/* 155 */         this.lastYaw -= 360.0F;
/*     */       }
/*     */       
/* 158 */       while (this.yaw - this.lastYaw >= 180.0F) {
/* 159 */         this.lastYaw += 360.0F;
/*     */       }
/*     */       
/* 162 */       this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 163 */       this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 164 */       float f2 = j();
/*     */       
/* 166 */       if (V()) {
/* 167 */         for (int j = 0; j < 4; j++) {
/* 168 */           float f3 = 0.25F;
/*     */           
/* 170 */           this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * f3, this.locY - this.motY * f3, this.locZ - this.motZ * f3, this.motX, this.motY, this.motZ, new int[0]);
/*     */         }
/*     */         
/* 173 */         f2 = 0.8F;
/*     */       }
/*     */       
/* 176 */       this.motX += this.dirX;
/* 177 */       this.motY += this.dirY;
/* 178 */       this.motZ += this.dirZ;
/* 179 */       this.motX *= f2;
/* 180 */       this.motY *= f2;
/* 181 */       this.motZ *= f2;
/* 182 */       this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 183 */       setPosition(this.locX, this.locY, this.locZ);
/*     */     }
/*     */   }
/*     */   
/*     */   protected float j() {
/* 188 */     return 0.95F;
/*     */   }
/*     */   
/*     */   protected abstract void a(MovingObjectPosition paramMovingObjectPosition);
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 194 */     nbttagcompound.setShort("xTile", (short)this.e);
/* 195 */     nbttagcompound.setShort("yTile", (short)this.f);
/* 196 */     nbttagcompound.setShort("zTile", (short)this.g);
/* 197 */     MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.h);
/*     */     
/* 199 */     nbttagcompound.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
/* 200 */     nbttagcompound.setByte("inGround", (byte)(this.i ? 1 : 0));
/*     */     
/* 202 */     nbttagcompound.set("power", a(new double[] { this.dirX, this.dirY, this.dirZ }));
/* 203 */     nbttagcompound.set("direction", a(new double[] { this.motX, this.motY, this.motZ }));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 207 */     this.e = nbttagcompound.getShort("xTile");
/* 208 */     this.f = nbttagcompound.getShort("yTile");
/* 209 */     this.g = nbttagcompound.getShort("zTile");
/* 210 */     if (nbttagcompound.hasKeyOfType("inTile", 8)) {
/* 211 */       this.h = Block.getByName(nbttagcompound.getString("inTile"));
/*     */     } else {
/* 213 */       this.h = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/*     */     }
/*     */     
/* 216 */     this.i = (nbttagcompound.getByte("inGround") == 1);
/*     */     
/* 218 */     if (nbttagcompound.hasKeyOfType("power", 9)) {
/* 219 */       NBTTagList nbttaglist = nbttagcompound.getList("power", 6);
/*     */       
/* 221 */       this.dirX = nbttaglist.d(0);
/* 222 */       this.dirY = nbttaglist.d(1);
/* 223 */       this.dirZ = nbttaglist.d(2);
/* 224 */     } else if (nbttagcompound.hasKeyOfType("direction", 9)) {
/* 225 */       NBTTagList nbttaglist = nbttagcompound.getList("direction", 6);
/*     */       
/* 227 */       this.motX = nbttaglist.d(0);
/* 228 */       this.motY = nbttaglist.d(1);
/* 229 */       this.motZ = nbttaglist.d(2);
/*     */     }
/*     */     else {
/* 232 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean ad()
/*     */   {
/* 238 */     return true;
/*     */   }
/*     */   
/*     */   public float ao() {
/* 242 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 246 */     if (isInvulnerable(damagesource)) {
/* 247 */       return false;
/*     */     }
/* 249 */     ac();
/* 250 */     if (damagesource.getEntity() != null)
/*     */     {
/* 252 */       if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
/* 253 */         return false;
/*     */       }
/*     */       
/* 256 */       Vec3D vec3d = damagesource.getEntity().ap();
/*     */       
/* 258 */       if (vec3d != null) {
/* 259 */         this.motX = vec3d.a;
/* 260 */         this.motY = vec3d.b;
/* 261 */         this.motZ = vec3d.c;
/* 262 */         this.dirX = (this.motX * 0.1D);
/* 263 */         this.dirY = (this.motY * 0.1D);
/* 264 */         this.dirZ = (this.motZ * 0.1D);
/*     */       }
/*     */       
/* 267 */       if ((damagesource.getEntity() instanceof EntityLiving)) {
/* 268 */         this.shooter = ((EntityLiving)damagesource.getEntity());
/* 269 */         this.projectileSource = ((org.bukkit.projectiles.ProjectileSource)this.shooter.getBukkitEntity());
/*     */       }
/*     */       
/* 272 */       return true;
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public float c(float f)
/*     */   {
/* 280 */     return 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */