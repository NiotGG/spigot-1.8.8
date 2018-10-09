/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
/*     */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntityBoat extends Entity
/*     */ {
/*     */   private boolean a;
/*     */   private double b;
/*     */   private int c;
/*     */   private double d;
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */   private double h;
/*  27 */   public double maxSpeed = 0.4D;
/*  28 */   public double occupiedDeceleration = 0.2D;
/*  29 */   public double unoccupiedDeceleration = -1.0D;
/*  30 */   public boolean landBoats = false;
/*     */   
/*     */   public void collide(Entity entity)
/*     */   {
/*  34 */     org.bukkit.entity.Entity hitEntity = entity == null ? null : entity.getBukkitEntity();
/*     */     
/*  36 */     VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)getBukkitEntity(), hitEntity);
/*  37 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/*  39 */     if (event.isCancelled()) {
/*  40 */       return;
/*     */     }
/*     */     
/*  43 */     super.collide(entity);
/*     */   }
/*     */   
/*     */   public EntityBoat(World world)
/*     */   {
/*  48 */     super(world);
/*  49 */     this.a = true;
/*  50 */     this.b = 0.07D;
/*  51 */     this.k = true;
/*  52 */     setSize(1.5F, 0.6F);
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   protected void h() {
/*  60 */     this.datawatcher.a(17, new Integer(0));
/*  61 */     this.datawatcher.a(18, new Integer(1));
/*  62 */     this.datawatcher.a(19, new Float(0.0F));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB j(Entity entity) {
/*  66 */     return entity.getBoundingBox();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB S() {
/*  70 */     return getBoundingBox();
/*     */   }
/*     */   
/*     */   public boolean ae() {
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public EntityBoat(World world, double d0, double d1, double d2) {
/*  78 */     this(world);
/*  79 */     setPosition(d0, d1, d2);
/*  80 */     this.motX = 0.0D;
/*  81 */     this.motY = 0.0D;
/*  82 */     this.motZ = 0.0D;
/*  83 */     this.lastX = d0;
/*  84 */     this.lastY = d1;
/*  85 */     this.lastZ = d2;
/*     */     
/*  87 */     this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */   
/*     */   public double an() {
/*  91 */     return -0.3D;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  95 */     if (isInvulnerable(damagesource))
/*  96 */       return false;
/*  97 */     if ((!this.world.isClientSide) && (!this.dead)) {
/*  98 */       if ((this.passenger != null) && (this.passenger == damagesource.getEntity()) && ((damagesource instanceof EntityDamageSourceIndirect))) {
/*  99 */         return false;
/*     */       }
/*     */       
/* 102 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 103 */       org.bukkit.entity.Entity attacker = damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity();
/*     */       
/* 105 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, f);
/* 106 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 108 */       if (event.isCancelled()) {
/* 109 */         return true;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 114 */       b(-m());
/* 115 */       a(10);
/* 116 */       setDamage(j() + f * 10.0F);
/* 117 */       ac();
/* 118 */       boolean flag = ((damagesource.getEntity() instanceof EntityHuman)) && (((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
/*     */       
/* 120 */       if ((flag) || (j() > 40.0F))
/*     */       {
/* 122 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
/* 123 */         this.world.getServer().getPluginManager().callEvent(destroyEvent);
/*     */         
/* 125 */         if (destroyEvent.isCancelled()) {
/* 126 */           setDamage(40.0F);
/* 127 */           return true;
/*     */         }
/*     */         
/* 130 */         if (this.passenger != null) {
/* 131 */           this.passenger.mount(this);
/*     */         }
/*     */         
/* 134 */         if ((!flag) && (this.world.getGameRules().getBoolean("doEntityDrops"))) {
/* 135 */           a(Items.BOAT, 1, 0.0F);
/*     */         }
/*     */         
/* 138 */         die();
/*     */       }
/*     */       
/* 141 */       return true;
/*     */     }
/*     */     
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public boolean ad()
/*     */   {
/* 149 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 154 */     double prevX = this.locX;
/* 155 */     double prevY = this.locY;
/* 156 */     double prevZ = this.locZ;
/* 157 */     float prevYaw = this.yaw;
/* 158 */     float prevPitch = this.pitch;
/*     */     
/* 160 */     super.t_();
/* 161 */     if (l() > 0) {
/* 162 */       a(l() - 1);
/*     */     }
/*     */     
/* 165 */     if (j() > 0.0F) {
/* 166 */       setDamage(j() - 1.0F);
/*     */     }
/*     */     
/* 169 */     this.lastX = this.locX;
/* 170 */     this.lastY = this.locY;
/* 171 */     this.lastZ = this.locZ;
/* 172 */     byte b0 = 5;
/* 173 */     double d0 = 0.0D;
/*     */     
/* 175 */     for (int i = 0; i < b0; i++) {
/* 176 */       double d1 = getBoundingBox().b + (getBoundingBox().e - getBoundingBox().b) * (i + 0) / b0 - 0.125D;
/* 177 */       double d2 = getBoundingBox().b + (getBoundingBox().e - getBoundingBox().b) * (i + 1) / b0 - 0.125D;
/* 178 */       AxisAlignedBB axisalignedbb = new AxisAlignedBB(getBoundingBox().a, d1, getBoundingBox().c, getBoundingBox().d, d2, getBoundingBox().f);
/*     */       
/* 180 */       if (this.world.b(axisalignedbb, Material.WATER)) {
/* 181 */         d0 += 1.0D / b0;
/*     */       }
/*     */     }
/*     */     
/* 185 */     double d3 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 190 */     if (d3 > 0.2975D) {
/* 191 */       double d4 = Math.cos(this.yaw * 3.141592653589793D / 180.0D);
/* 192 */       double d5 = Math.sin(this.yaw * 3.141592653589793D / 180.0D);
/*     */       
/* 194 */       for (int j = 0; j < 1.0D + d3 * 60.0D; j++) {
/* 195 */         double d6 = this.random.nextFloat() * 2.0F - 1.0F;
/* 196 */         double d7 = (this.random.nextInt(2) * 2 - 1) * 0.7D;
/*     */         
/*     */ 
/*     */ 
/* 200 */         if (this.random.nextBoolean()) {
/* 201 */           double d8 = this.locX - d4 * d6 * 0.8D + d5 * d7;
/* 202 */           double d9 = this.locZ - d5 * d6 * 0.8D - d4 * d7;
/* 203 */           this.world.addParticle(EnumParticle.WATER_SPLASH, d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ, new int[0]);
/*     */         } else {
/* 205 */           double d8 = this.locX + d4 + d5 * d6 * 0.7D;
/* 206 */           double d9 = this.locZ + d5 - d4 * d6 * 0.7D;
/* 207 */           this.world.addParticle(EnumParticle.WATER_SPLASH, d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ, new int[0]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 215 */     if ((this.world.isClientSide) && (this.a)) {
/* 216 */       if (this.c > 0) {
/* 217 */         double d4 = this.locX + (this.d - this.locX) / this.c;
/* 218 */         double d5 = this.locY + (this.e - this.locY) / this.c;
/* 219 */         double d10 = this.locZ + (this.f - this.locZ) / this.c;
/* 220 */         double d11 = MathHelper.g(this.g - this.yaw);
/* 221 */         this.yaw = ((float)(this.yaw + d11 / this.c));
/* 222 */         this.pitch = ((float)(this.pitch + (this.h - this.pitch) / this.c));
/* 223 */         this.c -= 1;
/* 224 */         setPosition(d4, d5, d10);
/* 225 */         setYawPitch(this.yaw, this.pitch);
/*     */       } else {
/* 227 */         double d4 = this.locX + this.motX;
/* 228 */         double d5 = this.locY + this.motY;
/* 229 */         double d10 = this.locZ + this.motZ;
/* 230 */         setPosition(d4, d5, d10);
/* 231 */         if (this.onGround) {
/* 232 */           this.motX *= 0.5D;
/* 233 */           this.motY *= 0.5D;
/* 234 */           this.motZ *= 0.5D;
/*     */         }
/*     */         
/* 237 */         this.motX *= 0.9900000095367432D;
/* 238 */         this.motY *= 0.949999988079071D;
/* 239 */         this.motZ *= 0.9900000095367432D;
/*     */       }
/*     */     }
/*     */     else {
/* 243 */       if (d0 < 1.0D) {
/* 244 */         double d4 = d0 * 2.0D - 1.0D;
/* 245 */         this.motY += 0.03999999910593033D * d4;
/*     */       } else {
/* 247 */         if (this.motY < 0.0D) {
/* 248 */           this.motY /= 2.0D;
/*     */         }
/*     */         
/* 251 */         this.motY += 0.007000000216066837D;
/*     */       }
/*     */       
/* 254 */       if ((this.passenger instanceof EntityLiving)) {
/* 255 */         EntityLiving entityliving = (EntityLiving)this.passenger;
/* 256 */         float f = this.passenger.yaw + -entityliving.aZ * 90.0F;
/*     */         
/* 258 */         this.motX += -Math.sin(f * 3.1415927F / 180.0F) * this.b * entityliving.ba * 0.05000000074505806D;
/* 259 */         this.motZ += Math.cos(f * 3.1415927F / 180.0F) * this.b * entityliving.ba * 0.05000000074505806D;
/*     */ 
/*     */       }
/* 262 */       else if (this.unoccupiedDeceleration >= 0.0D) {
/* 263 */         this.motX *= this.unoccupiedDeceleration;
/* 264 */         this.motZ *= this.unoccupiedDeceleration;
/*     */         
/* 266 */         if (this.motX <= 1.0E-5D) {
/* 267 */           this.motX = 0.0D;
/*     */         }
/* 269 */         if (this.motZ <= 1.0E-5D) {
/* 270 */           this.motZ = 0.0D;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 275 */       double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 276 */       if (d4 > 0.35D) {
/* 277 */         double d5 = 0.35D / d4;
/* 278 */         this.motX *= d5;
/* 279 */         this.motZ *= d5;
/* 280 */         d4 = 0.35D;
/*     */       }
/*     */       
/* 283 */       if ((d4 > d3) && (this.b < 0.35D)) {
/* 284 */         this.b += (0.35D - this.b) / 35.0D;
/* 285 */         if (this.b > 0.35D) {
/* 286 */           this.b = 0.35D;
/*     */         }
/*     */       } else {
/* 289 */         this.b -= (this.b - 0.07D) / 35.0D;
/* 290 */         if (this.b < 0.07D) {
/* 291 */           this.b = 0.07D;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 297 */       for (int k = 0; k < 4; k++) {
/* 298 */         int l = MathHelper.floor(this.locX + (k % 2 - 0.5D) * 0.8D);
/*     */         
/* 300 */         int j = MathHelper.floor(this.locZ + (k / 2 - 0.5D) * 0.8D);
/*     */         
/* 302 */         for (int i1 = 0; i1 < 2; i1++) {
/* 303 */           int j1 = MathHelper.floor(this.locY) + i1;
/* 304 */           BlockPosition blockposition = new BlockPosition(l, j1, j);
/* 305 */           Block block = this.world.getType(blockposition).getBlock();
/*     */           
/* 307 */           if (block == Blocks.SNOW_LAYER)
/*     */           {
/* 309 */             if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled())
/*     */             {
/*     */ 
/*     */ 
/* 313 */               this.world.setAir(blockposition);
/* 314 */               this.positionChanged = false;
/* 315 */             } } else if (block == Blocks.WATERLILY)
/*     */           {
/* 317 */             if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled())
/*     */             {
/*     */ 
/*     */ 
/* 321 */               this.world.setAir(blockposition, true);
/* 322 */               this.positionChanged = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 327 */       if ((this.onGround) && (!this.landBoats)) {
/* 328 */         this.motX *= 0.5D;
/* 329 */         this.motY *= 0.5D;
/* 330 */         this.motZ *= 0.5D;
/*     */       }
/*     */       
/* 333 */       move(this.motX, this.motY, this.motZ);
/* 334 */       if ((this.positionChanged) && (d3 > 0.2975D)) {
/* 335 */         if ((!this.world.isClientSide) && (!this.dead))
/*     */         {
/* 337 */           Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 338 */           VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
/* 339 */           this.world.getServer().getPluginManager().callEvent(destroyEvent);
/* 340 */           if (!destroyEvent.isCancelled()) {
/* 341 */             die();
/* 342 */             if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/* 343 */               for (k = 0; k < 3; k++) {
/* 344 */                 a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
/*     */               }
/*     */               
/* 347 */               for (k = 0; k < 2; k++) {
/* 348 */                 a(Items.STICK, 1, 0.0F);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 354 */         this.motX *= 0.9900000095367432D;
/* 355 */         this.motY *= 0.949999988079071D;
/* 356 */         this.motZ *= 0.9900000095367432D;
/*     */       }
/*     */       
/* 359 */       this.pitch = 0.0F;
/* 360 */       double d5 = this.yaw;
/* 361 */       double d10 = this.lastX - this.locX;
/* 362 */       double d11 = this.lastZ - this.locZ;
/* 363 */       if (d10 * d10 + d11 * d11 > 0.001D) {
/* 364 */         d5 = (float)(MathHelper.b(d11, d10) * 180.0D / 3.141592653589793D);
/*     */       }
/*     */       
/* 367 */       double d12 = MathHelper.g(d5 - this.yaw);
/*     */       
/* 369 */       if (d12 > 20.0D) {
/* 370 */         d12 = 20.0D;
/*     */       }
/*     */       
/* 373 */       if (d12 < -20.0D) {
/* 374 */         d12 = -20.0D;
/*     */       }
/*     */       
/* 377 */       this.yaw = ((float)(this.yaw + d12));
/* 378 */       setYawPitch(this.yaw, this.pitch);
/*     */       
/*     */ 
/* 381 */       Server server = this.world.getServer();
/* 382 */       org.bukkit.World bworld = this.world.getWorld();
/*     */       
/* 384 */       Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 385 */       Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 386 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 388 */       server.getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));
/*     */       
/* 390 */       if (!from.equals(to)) {
/* 391 */         VehicleMoveEvent event = new VehicleMoveEvent(vehicle, from, to);
/* 392 */         server.getPluginManager().callEvent(event);
/*     */       }
/*     */       
/* 395 */       if (!this.world.isClientSide) {
/* 396 */         List list = this.world.getEntities(this, getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */         
/* 398 */         if ((list != null) && (!list.isEmpty())) {
/* 399 */           for (int k1 = 0; k1 < list.size(); k1++) {
/* 400 */             Entity entity = (Entity)list.get(k1);
/*     */             
/* 402 */             if ((entity != this.passenger) && (entity.ae()) && ((entity instanceof EntityBoat))) {
/* 403 */               entity.collide(this);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 408 */         if ((this.passenger != null) && (this.passenger.dead)) {
/* 409 */           this.passenger.vehicle = null;
/* 410 */           this.passenger = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void al()
/*     */   {
/* 418 */     if (this.passenger != null) {
/* 419 */       double d0 = Math.cos(this.yaw * 3.141592653589793D / 180.0D) * 0.4D;
/* 420 */       double d1 = Math.sin(this.yaw * 3.141592653589793D / 180.0D) * 0.4D;
/*     */       
/* 422 */       this.passenger.setPosition(this.locX + d0, this.locY + an() + this.passenger.am(), this.locZ + d1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public boolean e(EntityHuman entityhuman) {
/* 431 */     if ((this.passenger != null) && ((this.passenger instanceof EntityHuman)) && (this.passenger != entityhuman)) {
/* 432 */       return true;
/*     */     }
/* 434 */     if (!this.world.isClientSide) {
/* 435 */       entityhuman.mount(this);
/*     */     }
/*     */     
/* 438 */     return true;
/*     */   }
/*     */   
/*     */   protected void a(double d0, boolean flag, Block block, BlockPosition blockposition)
/*     */   {
/* 443 */     if (flag) {
/* 444 */       if (this.fallDistance > 3.0F) {
/* 445 */         e(this.fallDistance, 1.0F);
/* 446 */         if ((!this.world.isClientSide) && (!this.dead))
/*     */         {
/* 448 */           Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 449 */           VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
/* 450 */           this.world.getServer().getPluginManager().callEvent(destroyEvent);
/* 451 */           if (!destroyEvent.isCancelled()) {
/* 452 */             die();
/* 453 */             if (this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */             {
/*     */ 
/* 456 */               for (int i = 0; i < 3; i++) {
/* 457 */                 a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
/*     */               }
/*     */               
/* 460 */               for (i = 0; i < 2; i++) {
/* 461 */                 a(Items.STICK, 1, 0.0F);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 467 */         this.fallDistance = 0.0F;
/*     */       }
/* 469 */     } else if ((this.world.getType(new BlockPosition(this).down()).getBlock().getMaterial() != Material.WATER) && (d0 < 0.0D)) {
/* 470 */       this.fallDistance = ((float)(this.fallDistance - d0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDamage(float f)
/*     */   {
/* 476 */     this.datawatcher.watch(19, Float.valueOf(f));
/*     */   }
/*     */   
/*     */   public float j() {
/* 480 */     return this.datawatcher.getFloat(19);
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 484 */     this.datawatcher.watch(17, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int l() {
/* 488 */     return this.datawatcher.getInt(17);
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 492 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int m() {
/* 496 */     return this.datawatcher.getInt(18);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */