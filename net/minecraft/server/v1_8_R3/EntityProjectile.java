/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class EntityProjectile extends Entity implements IProjectile
/*     */ {
/*   8 */   private int blockX = -1;
/*   9 */   private int blockY = -1;
/*  10 */   private int blockZ = -1;
/*     */   private Block inBlockId;
/*     */   protected boolean inGround;
/*     */   public int shake;
/*     */   public EntityLiving shooter;
/*     */   public String shooterName;
/*     */   private int i;
/*     */   private int ar;
/*     */   
/*     */   public EntityProjectile(World world) {
/*  20 */     super(world);
/*  21 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public EntityProjectile(World world, EntityLiving entityliving) {
/*  27 */     super(world);
/*  28 */     this.shooter = entityliving;
/*  29 */     this.projectileSource = ((org.bukkit.entity.LivingEntity)entityliving.getBukkitEntity());
/*  30 */     setSize(0.25F, 0.25F);
/*  31 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  32 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  33 */     this.locY -= 0.10000000149011612D;
/*  34 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  35 */     setPosition(this.locX, this.locY, this.locZ);
/*  36 */     float f = 0.4F;
/*     */     
/*  38 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  39 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  40 */     this.motY = (-MathHelper.sin((this.pitch + l()) / 180.0F * 3.1415927F) * f);
/*  41 */     shoot(this.motX, this.motY, this.motZ, j(), 1.0F);
/*     */   }
/*     */   
/*     */   public EntityProjectile(World world, double d0, double d1, double d2) {
/*  45 */     super(world);
/*  46 */     this.i = 0;
/*  47 */     setSize(0.25F, 0.25F);
/*  48 */     setPosition(d0, d1, d2);
/*     */   }
/*     */   
/*     */   protected float j() {
/*  52 */     return 1.5F;
/*     */   }
/*     */   
/*     */   protected float l() {
/*  56 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void shoot(double d0, double d1, double d2, float f, float f1) {
/*  60 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  62 */     d0 /= f2;
/*  63 */     d1 /= f2;
/*  64 */     d2 /= f2;
/*  65 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  66 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  67 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  68 */     d0 *= f;
/*  69 */     d1 *= f;
/*  70 */     d2 *= f;
/*  71 */     this.motX = d0;
/*  72 */     this.motY = d1;
/*  73 */     this.motZ = d2;
/*  74 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  76 */     this.lastYaw = (this.yaw = (float)(MathHelper.b(d0, d2) * 180.0D / 3.1415927410125732D));
/*  77 */     this.lastPitch = (this.pitch = (float)(MathHelper.b(d1, f3) * 180.0D / 3.1415927410125732D));
/*  78 */     this.i = 0;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  82 */     this.P = this.locX;
/*  83 */     this.Q = this.locY;
/*  84 */     this.R = this.locZ;
/*  85 */     super.t_();
/*  86 */     if (this.shake > 0) {
/*  87 */       this.shake -= 1;
/*     */     }
/*     */     
/*  90 */     if (this.inGround) {
/*  91 */       if (this.world.getType(new BlockPosition(this.blockX, this.blockY, this.blockZ)).getBlock() == this.inBlockId) {
/*  92 */         this.i += 1;
/*  93 */         if (this.i == 1200) {
/*  94 */           die();
/*     */         }
/*     */         
/*  97 */         return;
/*     */       }
/*     */       
/* 100 */       this.inGround = false;
/* 101 */       this.motX *= this.random.nextFloat() * 0.2F;
/* 102 */       this.motY *= this.random.nextFloat() * 0.2F;
/* 103 */       this.motZ *= this.random.nextFloat() * 0.2F;
/* 104 */       this.i = 0;
/* 105 */       this.ar = 0;
/*     */     } else {
/* 107 */       this.ar += 1;
/*     */     }
/*     */     
/* 110 */     Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 111 */     Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 112 */     MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);
/*     */     
/* 114 */     vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 115 */     vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 116 */     if (movingobjectposition != null) {
/* 117 */       vec3d1 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */     }
/*     */     
/* 120 */     if (!this.world.isClientSide) {
/* 121 */       Entity entity = null;
/* 122 */       List list = this.world.getEntities(this, getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 123 */       double d0 = 0.0D;
/* 124 */       EntityLiving entityliving = getShooter();
/*     */       
/* 126 */       for (int i = 0; i < list.size(); i++) {
/* 127 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 129 */         if ((entity1.ad()) && ((entity1 != entityliving) || (this.ar >= 5))) {
/* 130 */           float f = 0.3F;
/* 131 */           AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(f, f, f);
/* 132 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 134 */           if (movingobjectposition1 != null) {
/* 135 */             double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
/*     */             
/* 137 */             if ((d1 < d0) || (d0 == 0.0D)) {
/* 138 */               entity = entity1;
/* 139 */               d0 = d1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 145 */       if (entity != null) {
/* 146 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */     }
/*     */     
/* 150 */     if (movingobjectposition != null) {
/* 151 */       if ((movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) && (this.world.getType(movingobjectposition.a()).getBlock() == Blocks.PORTAL)) {
/* 152 */         d(movingobjectposition.a());
/*     */       } else {
/* 154 */         a(movingobjectposition);
/*     */         
/* 156 */         if (this.dead) {
/* 157 */           org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callProjectileHitEvent(this);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 163 */     this.locX += this.motX;
/* 164 */     this.locY += this.motY;
/* 165 */     this.locZ += this.motZ;
/* 166 */     float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 168 */     this.yaw = ((float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/*     */     
/* 170 */     for (this.pitch = ((float)(MathHelper.b(this.motY, f1) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
/*     */     
/*     */ 
/*     */ 
/* 174 */     while (this.pitch - this.lastPitch >= 180.0F) {
/* 175 */       this.lastPitch += 360.0F;
/*     */     }
/*     */     
/* 178 */     while (this.yaw - this.lastYaw < -180.0F) {
/* 179 */       this.lastYaw -= 360.0F;
/*     */     }
/*     */     
/* 182 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 183 */       this.lastYaw += 360.0F;
/*     */     }
/*     */     
/* 186 */     this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 187 */     this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 188 */     float f2 = 0.99F;
/* 189 */     float f3 = m();
/*     */     
/* 191 */     if (V()) {
/* 192 */       for (int j = 0; j < 4; j++) {
/* 193 */         float f4 = 0.25F;
/*     */         
/* 195 */         this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * f4, this.locY - this.motY * f4, this.locZ - this.motZ * f4, this.motX, this.motY, this.motZ, new int[0]);
/*     */       }
/*     */       
/* 198 */       f2 = 0.8F;
/*     */     }
/*     */     
/* 201 */     this.motX *= f2;
/* 202 */     this.motY *= f2;
/* 203 */     this.motZ *= f2;
/* 204 */     this.motY -= f3;
/* 205 */     setPosition(this.locX, this.locY, this.locZ);
/*     */   }
/*     */   
/*     */   protected float m() {
/* 209 */     return 0.03F;
/*     */   }
/*     */   
/*     */   protected abstract void a(MovingObjectPosition paramMovingObjectPosition);
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 215 */     nbttagcompound.setShort("xTile", (short)this.blockX);
/* 216 */     nbttagcompound.setShort("yTile", (short)this.blockY);
/* 217 */     nbttagcompound.setShort("zTile", (short)this.blockZ);
/* 218 */     MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.inBlockId);
/*     */     
/* 220 */     nbttagcompound.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
/* 221 */     nbttagcompound.setByte("shake", (byte)this.shake);
/* 222 */     nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 223 */     if (((this.shooterName == null) || (this.shooterName.length() == 0)) && ((this.shooter instanceof EntityHuman))) {
/* 224 */       this.shooterName = this.shooter.getName();
/*     */     }
/*     */     
/* 227 */     nbttagcompound.setString("ownerName", this.shooterName == null ? "" : this.shooterName);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 231 */     this.blockX = nbttagcompound.getShort("xTile");
/* 232 */     this.blockY = nbttagcompound.getShort("yTile");
/* 233 */     this.blockZ = nbttagcompound.getShort("zTile");
/* 234 */     if (nbttagcompound.hasKeyOfType("inTile", 8)) {
/* 235 */       this.inBlockId = Block.getByName(nbttagcompound.getString("inTile"));
/*     */     } else {
/* 237 */       this.inBlockId = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/*     */     }
/*     */     
/* 240 */     this.shake = (nbttagcompound.getByte("shake") & 0xFF);
/* 241 */     this.inGround = (nbttagcompound.getByte("inGround") == 1);
/* 242 */     this.shooter = null;
/* 243 */     this.shooterName = nbttagcompound.getString("ownerName");
/* 244 */     if ((this.shooterName != null) && (this.shooterName.length() == 0)) {
/* 245 */       this.shooterName = null;
/*     */     }
/*     */     
/* 248 */     this.shooter = getShooter();
/*     */   }
/*     */   
/*     */   public EntityLiving getShooter() {
/* 252 */     if ((this.shooter == null) && (this.shooterName != null) && (this.shooterName.length() > 0)) {
/* 253 */       this.shooter = this.world.a(this.shooterName);
/* 254 */       if ((this.shooter == null) && ((this.world instanceof WorldServer))) {
/*     */         try {
/* 256 */           Entity entity = ((WorldServer)this.world).getEntity(java.util.UUID.fromString(this.shooterName));
/*     */           
/* 258 */           if ((entity instanceof EntityLiving)) {
/* 259 */             this.shooter = ((EntityLiving)entity);
/*     */           }
/*     */         } catch (Throwable localThrowable) {
/* 262 */           this.shooter = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 267 */     return this.shooter;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */