/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntityArrow extends Entity implements IProjectile
/*     */ {
/*  13 */   private int d = -1;
/*  14 */   private int e = -1;
/*  15 */   private int f = -1;
/*     */   private Block g;
/*     */   private int h;
/*  18 */   public boolean inGround = false;
/*     */   public int fromPlayer;
/*     */   public int shake;
/*     */   public Entity shooter;
/*     */   private int ar;
/*     */   private int as;
/*  24 */   private double damage = 2.0D;
/*     */   
/*     */   public int knockbackStrength;
/*     */   
/*     */ 
/*     */   public void inactiveTick()
/*     */   {
/*  31 */     if (this.inGround)
/*     */     {
/*  33 */       this.ar += 1;
/*     */     }
/*  35 */     super.inactiveTick();
/*     */   }
/*     */   
/*     */   public EntityArrow(World world)
/*     */   {
/*  40 */     super(world);
/*  41 */     this.j = 10.0D;
/*  42 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, double d0, double d1, double d2) {
/*  46 */     super(world);
/*  47 */     this.j = 10.0D;
/*  48 */     setSize(0.5F, 0.5F);
/*  49 */     setPosition(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1) {
/*  53 */     super(world);
/*  54 */     this.j = 10.0D;
/*  55 */     this.shooter = entityliving;
/*  56 */     this.projectileSource = ((LivingEntity)entityliving.getBukkitEntity());
/*  57 */     if ((entityliving instanceof EntityHuman)) {
/*  58 */       this.fromPlayer = 1;
/*     */     }
/*     */     
/*  61 */     this.locY = (entityliving.locY + entityliving.getHeadHeight() - 0.10000000149011612D);
/*  62 */     double d0 = entityliving1.locX - entityliving.locX;
/*  63 */     double d1 = entityliving1.getBoundingBox().b + entityliving1.length / 3.0F - this.locY;
/*  64 */     double d2 = entityliving1.locZ - entityliving.locZ;
/*  65 */     double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  67 */     if (d3 >= 1.0E-7D) {
/*  68 */       float f2 = (float)(MathHelper.b(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/*  69 */       float f3 = (float)-(MathHelper.b(d1, d3) * 180.0D / 3.1415927410125732D);
/*  70 */       double d4 = d0 / d3;
/*  71 */       double d5 = d2 / d3;
/*     */       
/*  73 */       setPositionRotation(entityliving.locX + d4, this.locY, entityliving.locZ + d5, f2, f3);
/*  74 */       float f4 = (float)(d3 * 0.20000000298023224D);
/*     */       
/*  76 */       shoot(d0, d1 + f4, d2, f, f1);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityArrow(World world, EntityLiving entityliving, float f) {
/*  81 */     super(world);
/*  82 */     this.j = 10.0D;
/*  83 */     this.shooter = entityliving;
/*  84 */     this.projectileSource = ((LivingEntity)entityliving.getBukkitEntity());
/*  85 */     if ((entityliving instanceof EntityHuman)) {
/*  86 */       this.fromPlayer = 1;
/*     */     }
/*     */     
/*  89 */     setSize(0.5F, 0.5F);
/*  90 */     setPositionRotation(entityliving.locX, entityliving.locY + entityliving.getHeadHeight(), entityliving.locZ, entityliving.yaw, entityliving.pitch);
/*  91 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  92 */     this.locY -= 0.10000000149011612D;
/*  93 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  94 */     setPosition(this.locX, this.locY, this.locZ);
/*  95 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/*  96 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
/*  97 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
/*  98 */     shoot(this.motX, this.motY, this.motZ, f * 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void h() {
/* 102 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void shoot(double d0, double d1, double d2, float f, float f1) {
/* 106 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/* 108 */     d0 /= f2;
/* 109 */     d1 /= f2;
/* 110 */     d2 /= f2;
/* 111 */     d0 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
/* 112 */     d1 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
/* 113 */     d2 += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.007499999832361937D * f1;
/* 114 */     d0 *= f;
/* 115 */     d1 *= f;
/* 116 */     d2 *= f;
/* 117 */     this.motX = d0;
/* 118 */     this.motY = d1;
/* 119 */     this.motZ = d2;
/* 120 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/* 122 */     this.lastYaw = (this.yaw = (float)(MathHelper.b(d0, d2) * 180.0D / 3.1415927410125732D));
/* 123 */     this.lastPitch = (this.pitch = (float)(MathHelper.b(d1, f3) * 180.0D / 3.1415927410125732D));
/* 124 */     this.ar = 0;
/*     */   }
/*     */   
/*     */   public void t_() {
/* 128 */     super.t_();
/* 129 */     if ((this.lastPitch == 0.0F) && (this.lastYaw == 0.0F)) {
/* 130 */       float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 132 */       this.lastYaw = (this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/* 133 */       this.lastPitch = (this.pitch = (float)(MathHelper.b(this.motY, f) * 180.0D / 3.1415927410125732D));
/*     */     }
/*     */     
/* 136 */     BlockPosition blockposition = new BlockPosition(this.d, this.e, this.f);
/* 137 */     IBlockData iblockdata = this.world.getType(blockposition);
/* 138 */     Block block = iblockdata.getBlock();
/*     */     
/* 140 */     if (block.getMaterial() != Material.AIR) {
/* 141 */       block.updateShape(this.world, blockposition);
/* 142 */       AxisAlignedBB axisalignedbb = block.a(this.world, blockposition, iblockdata);
/*     */       
/* 144 */       if ((axisalignedbb != null) && (axisalignedbb.a(new Vec3D(this.locX, this.locY, this.locZ)))) {
/* 145 */         this.inGround = true;
/*     */       }
/*     */     }
/*     */     
/* 149 */     if (this.shake > 0) {
/* 150 */       this.shake -= 1;
/*     */     }
/*     */     
/* 153 */     if (this.inGround) {
/* 154 */       int i = block.toLegacyData(iblockdata);
/*     */       
/* 156 */       if ((block == this.g) && (i == this.h)) {
/* 157 */         this.ar += 1;
/* 158 */         if (this.ar >= this.world.spigotConfig.arrowDespawnRate) {
/* 159 */           die();
/*     */         }
/*     */       } else {
/* 162 */         this.inGround = false;
/* 163 */         this.motX *= this.random.nextFloat() * 0.2F;
/* 164 */         this.motY *= this.random.nextFloat() * 0.2F;
/* 165 */         this.motZ *= this.random.nextFloat() * 0.2F;
/* 166 */         this.ar = 0;
/* 167 */         this.as = 0;
/*     */       }
/*     */     }
/*     */     else {
/* 171 */       this.as += 1;
/* 172 */       Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 173 */       Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 174 */       MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, false, true, false);
/*     */       
/* 176 */       vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 177 */       vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 178 */       if (movingobjectposition != null) {
/* 179 */         vec3d1 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */       }
/*     */       
/* 182 */       Entity entity = null;
/* 183 */       List list = this.world.getEntities(this, getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 184 */       double d0 = 0.0D;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 189 */       for (int j = 0; j < list.size(); j++) {
/* 190 */         Entity entity1 = (Entity)list.get(j);
/*     */         
/* 192 */         if ((entity1.ad()) && ((entity1 != this.shooter) || (this.as >= 5))) {
/* 193 */           float f1 = 0.3F;
/* 194 */           AxisAlignedBB axisalignedbb1 = entity1.getBoundingBox().grow(f1, f1, f1);
/* 195 */           MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
/*     */           
/* 197 */           if (movingobjectposition1 != null) {
/* 198 */             double d1 = vec3d.distanceSquared(movingobjectposition1.pos);
/*     */             
/* 200 */             if ((d1 < d0) || (d0 == 0.0D)) {
/* 201 */               entity = entity1;
/* 202 */               d0 = d1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 208 */       if (entity != null) {
/* 209 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 212 */       if ((movingobjectposition != null) && (movingobjectposition.entity != null) && ((movingobjectposition.entity instanceof EntityHuman))) {
/* 213 */         EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
/*     */         
/* 215 */         if ((entityhuman.abilities.isInvulnerable) || (((this.shooter instanceof EntityHuman)) && (!((EntityHuman)this.shooter).a(entityhuman)))) {
/* 216 */           movingobjectposition = null;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 223 */       if (movingobjectposition != null) {
/* 224 */         org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callProjectileHitEvent(this);
/* 225 */         if (movingobjectposition.entity != null) {
/* 226 */           float f2 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 227 */           int k = MathHelper.f(f2 * this.damage);
/*     */           
/* 229 */           if (isCritical()) {
/* 230 */             k += this.random.nextInt(k / 2 + 2);
/*     */           }
/*     */           
/*     */           DamageSource damagesource;
/*     */           DamageSource damagesource;
/* 235 */           if (this.shooter == null) {
/* 236 */             damagesource = DamageSource.arrow(this, this);
/*     */           } else {
/* 238 */             damagesource = DamageSource.arrow(this, this.shooter);
/*     */           }
/*     */           
/*     */ 
/* 242 */           if (movingobjectposition.entity.damageEntity(damagesource, k)) {
/* 243 */             if ((isBurning()) && (!(movingobjectposition.entity instanceof EntityEnderman)) && ((!(movingobjectposition.entity instanceof EntityPlayer)) || (!(this.shooter instanceof EntityPlayer)) || (this.world.pvpMode))) {
/* 244 */               EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 5);
/* 245 */               org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
/* 246 */               if (!combustEvent.isCancelled()) {
/* 247 */                 movingobjectposition.entity.setOnFire(combustEvent.getDuration());
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 253 */             if ((movingobjectposition.entity instanceof EntityLiving)) {
/* 254 */               EntityLiving entityliving = (EntityLiving)movingobjectposition.entity;
/*     */               
/* 256 */               if (!this.world.isClientSide) {
/* 257 */                 entityliving.o(entityliving.bv() + 1);
/*     */               }
/*     */               
/* 260 */               if (this.knockbackStrength > 0) {
/* 261 */                 float f3 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 262 */                 if (f3 > 0.0F) {
/* 263 */                   movingobjectposition.entity.g(this.motX * this.knockbackStrength * 0.6000000238418579D / f3, 0.1D, this.motZ * this.knockbackStrength * 0.6000000238418579D / f3);
/*     */                 }
/*     */               }
/*     */               
/* 267 */               if ((this.shooter instanceof EntityLiving)) {
/* 268 */                 EnchantmentManager.a(entityliving, this.shooter);
/* 269 */                 EnchantmentManager.b((EntityLiving)this.shooter, entityliving);
/*     */               }
/*     */               
/* 272 */               if ((this.shooter != null) && (movingobjectposition.entity != this.shooter) && ((movingobjectposition.entity instanceof EntityHuman)) && ((this.shooter instanceof EntityPlayer))) {
/* 273 */                 ((EntityPlayer)this.shooter).playerConnection.sendPacket(new PacketPlayOutGameStateChange(6, 0.0F));
/*     */               }
/*     */             }
/*     */             
/* 277 */             makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 278 */             if (!(movingobjectposition.entity instanceof EntityEnderman)) {
/* 279 */               die();
/*     */             }
/*     */           } else {
/* 282 */             this.motX *= -0.10000000149011612D;
/* 283 */             this.motY *= -0.10000000149011612D;
/* 284 */             this.motZ *= -0.10000000149011612D;
/* 285 */             this.yaw += 180.0F;
/* 286 */             this.lastYaw += 180.0F;
/* 287 */             this.as = 0;
/*     */           }
/*     */         } else {
/* 290 */           BlockPosition blockposition1 = movingobjectposition.a();
/*     */           
/* 292 */           this.d = blockposition1.getX();
/* 293 */           this.e = blockposition1.getY();
/* 294 */           this.f = blockposition1.getZ();
/* 295 */           IBlockData iblockdata1 = this.world.getType(blockposition1);
/*     */           
/* 297 */           this.g = iblockdata1.getBlock();
/* 298 */           this.h = this.g.toLegacyData(iblockdata1);
/* 299 */           this.motX = ((float)(movingobjectposition.pos.a - this.locX));
/* 300 */           this.motY = ((float)(movingobjectposition.pos.b - this.locY));
/* 301 */           this.motZ = ((float)(movingobjectposition.pos.c - this.locZ));
/* 302 */           float f1 = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ);
/* 303 */           this.locX -= this.motX / f1 * 0.05000000074505806D;
/* 304 */           this.locY -= this.motY / f1 * 0.05000000074505806D;
/* 305 */           this.locZ -= this.motZ / f1 * 0.05000000074505806D;
/* 306 */           makeSound("random.bowhit", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
/* 307 */           this.inGround = true;
/* 308 */           this.shake = 7;
/* 309 */           setCritical(false);
/* 310 */           if (this.g.getMaterial() != Material.AIR) {
/* 311 */             this.g.a(this.world, blockposition1, iblockdata1, this);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 316 */       if (isCritical()) {
/* 317 */         for (j = 0; j < 4; j++) {
/* 318 */           this.world.addParticle(EnumParticle.CRIT, this.locX + this.motX * j / 4.0D, this.locY + this.motY * j / 4.0D, this.locZ + this.motZ * j / 4.0D, -this.motX, -this.motY + 0.2D, -this.motZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 322 */       this.locX += this.motX;
/* 323 */       this.locY += this.motY;
/* 324 */       this.locZ += this.motZ;
/* 325 */       float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 326 */       this.yaw = ((float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/*     */       
/* 328 */       for (this.pitch = ((float)(MathHelper.b(this.motY, f2) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
/*     */       
/*     */ 
/*     */ 
/* 332 */       while (this.pitch - this.lastPitch >= 180.0F) {
/* 333 */         this.lastPitch += 360.0F;
/*     */       }
/*     */       
/* 336 */       while (this.yaw - this.lastYaw < -180.0F) {
/* 337 */         this.lastYaw -= 360.0F;
/*     */       }
/*     */       
/* 340 */       while (this.yaw - this.lastYaw >= 180.0F) {
/* 341 */         this.lastYaw += 360.0F;
/*     */       }
/*     */       
/* 344 */       this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 345 */       this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 346 */       float f4 = 0.99F;
/*     */       
/* 348 */       float f1 = 0.05F;
/* 349 */       if (V()) {
/* 350 */         for (int l = 0; l < 4; l++) {
/* 351 */           float f3 = 0.25F;
/* 352 */           this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * f3, this.locY - this.motY * f3, this.locZ - this.motZ * f3, this.motX, this.motY, this.motZ, new int[0]);
/*     */         }
/*     */         
/* 355 */         f4 = 0.6F;
/*     */       }
/*     */       
/* 358 */       if (U()) {
/* 359 */         extinguish();
/*     */       }
/*     */       
/* 362 */       this.motX *= f4;
/* 363 */       this.motY *= f4;
/* 364 */       this.motZ *= f4;
/* 365 */       this.motY -= f1;
/* 366 */       setPosition(this.locX, this.locY, this.locZ);
/* 367 */       checkBlockCollisions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 372 */     nbttagcompound.setShort("xTile", (short)this.d);
/* 373 */     nbttagcompound.setShort("yTile", (short)this.e);
/* 374 */     nbttagcompound.setShort("zTile", (short)this.f);
/* 375 */     nbttagcompound.setShort("life", (short)this.ar);
/* 376 */     MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.g);
/*     */     
/* 378 */     nbttagcompound.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
/* 379 */     nbttagcompound.setByte("inData", (byte)this.h);
/* 380 */     nbttagcompound.setByte("shake", (byte)this.shake);
/* 381 */     nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 382 */     nbttagcompound.setByte("pickup", (byte)this.fromPlayer);
/* 383 */     nbttagcompound.setDouble("damage", this.damage);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 387 */     this.d = nbttagcompound.getShort("xTile");
/* 388 */     this.e = nbttagcompound.getShort("yTile");
/* 389 */     this.f = nbttagcompound.getShort("zTile");
/* 390 */     this.ar = nbttagcompound.getShort("life");
/* 391 */     if (nbttagcompound.hasKeyOfType("inTile", 8)) {
/* 392 */       this.g = Block.getByName(nbttagcompound.getString("inTile"));
/*     */     } else {
/* 394 */       this.g = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/*     */     }
/*     */     
/* 397 */     this.h = (nbttagcompound.getByte("inData") & 0xFF);
/* 398 */     this.shake = (nbttagcompound.getByte("shake") & 0xFF);
/* 399 */     this.inGround = (nbttagcompound.getByte("inGround") == 1);
/* 400 */     if (nbttagcompound.hasKeyOfType("damage", 99)) {
/* 401 */       this.damage = nbttagcompound.getDouble("damage");
/*     */     }
/*     */     
/* 404 */     if (nbttagcompound.hasKeyOfType("pickup", 99)) {
/* 405 */       this.fromPlayer = nbttagcompound.getByte("pickup");
/* 406 */     } else if (nbttagcompound.hasKeyOfType("player", 99)) {
/* 407 */       this.fromPlayer = (nbttagcompound.getBoolean("player") ? 1 : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void d(EntityHuman entityhuman)
/*     */   {
/* 413 */     if ((!this.world.isClientSide) && (this.inGround) && (this.shake <= 0))
/*     */     {
/* 415 */       ItemStack itemstack = new ItemStack(Items.ARROW);
/* 416 */       if ((this.fromPlayer == 1) && (entityhuman.inventory.canHold(itemstack) > 0)) {
/* 417 */         EntityItem item = new EntityItem(this.world, this.locX, this.locY, this.locZ, itemstack);
/*     */         
/* 419 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), new org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem(this.world.getServer(), this, item), 0);
/*     */         
/* 421 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 423 */         if (event.isCancelled()) {
/* 424 */           return;
/*     */         }
/*     */       }
/*     */       
/* 428 */       boolean flag = (this.fromPlayer == 1) || ((this.fromPlayer == 2) && (entityhuman.abilities.canInstantlyBuild));
/*     */       
/* 430 */       if ((this.fromPlayer == 1) && (!entityhuman.inventory.pickup(new ItemStack(Items.ARROW, 1)))) {
/* 431 */         flag = false;
/*     */       }
/*     */       
/* 434 */       if (flag) {
/* 435 */         makeSound("random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 436 */         entityhuman.receive(this, 1);
/* 437 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean s_()
/*     */   {
/* 444 */     return false;
/*     */   }
/*     */   
/*     */   public void b(double d0) {
/* 448 */     this.damage = d0;
/*     */   }
/*     */   
/*     */   public double j() {
/* 452 */     return this.damage;
/*     */   }
/*     */   
/*     */   public void setKnockbackStrength(int i) {
/* 456 */     this.knockbackStrength = i;
/*     */   }
/*     */   
/*     */   public boolean aD() {
/* 460 */     return false;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 464 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public void setCritical(boolean flag) {
/* 468 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 470 */     if (flag) {
/* 471 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 473 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCritical()
/*     */   {
/* 479 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 481 */     return (b0 & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public boolean isInGround()
/*     */   {
/* 486 */     return this.inGround;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */