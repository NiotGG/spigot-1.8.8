/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
/*     */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public abstract class EntityMinecartAbstract extends Entity implements INamableTileEntity
/*     */ {
/*     */   private boolean a;
/*     */   private String b;
/*  20 */   private static final int[][][] matrix = { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1 }, { 1 } }, { { -1, -1 }, { 1 } }, { { -1 }, { 1, -1 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1 } }, { { 0, 0, 1 }, { -1 } }, { { 0, 0, -1 }, { -1 } }, { { 0, 0, -1 }, { 1 } } };
/*     */   
/*     */   private int d;
/*     */   
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */   private double h;
/*     */   private double i;
/*  29 */   public boolean slowWhenEmpty = true;
/*  30 */   private double derailedX = 0.5D;
/*  31 */   private double derailedY = 0.5D;
/*  32 */   private double derailedZ = 0.5D;
/*  33 */   private double flyingX = 0.95D;
/*  34 */   private double flyingY = 0.95D;
/*  35 */   private double flyingZ = 0.95D;
/*  36 */   public double maxSpeed = 0.4D;
/*     */   
/*     */   public EntityMinecartAbstract(World world)
/*     */   {
/*  40 */     super(world);
/*  41 */     this.k = true;
/*  42 */     setSize(0.98F, 0.7F);
/*     */   }
/*     */   
/*     */   public static EntityMinecartAbstract a(World world, double d0, double d1, double d2, EnumMinecartType entityminecartabstract_enumminecarttype) {
/*  46 */     switch (SyntheticClass_1.a[entityminecartabstract_enumminecarttype.ordinal()]) {
/*     */     case 1: 
/*  48 */       return new EntityMinecartChest(world, d0, d1, d2);
/*     */     
/*     */     case 2: 
/*  51 */       return new EntityMinecartFurnace(world, d0, d1, d2);
/*     */     
/*     */     case 3: 
/*  54 */       return new EntityMinecartTNT(world, d0, d1, d2);
/*     */     
/*     */     case 4: 
/*  57 */       return new EntityMinecartMobSpawner(world, d0, d1, d2);
/*     */     
/*     */     case 5: 
/*  60 */       return new EntityMinecartHopper(world, d0, d1, d2);
/*     */     
/*     */     case 6: 
/*  63 */       return new EntityMinecartCommandBlock(world, d0, d1, d2);
/*     */     }
/*     */     
/*  66 */     return new EntityMinecartRideable(world, d0, d1, d2);
/*     */   }
/*     */   
/*     */   protected boolean s_()
/*     */   {
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   protected void h() {
/*  75 */     this.datawatcher.a(17, new Integer(0));
/*  76 */     this.datawatcher.a(18, new Integer(1));
/*  77 */     this.datawatcher.a(19, new Float(0.0F));
/*  78 */     this.datawatcher.a(20, new Integer(0));
/*  79 */     this.datawatcher.a(21, new Integer(6));
/*  80 */     this.datawatcher.a(22, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB j(Entity entity) {
/*  84 */     return entity.ae() ? entity.getBoundingBox() : null;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB S() {
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   public boolean ae() {
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public EntityMinecartAbstract(World world, double d0, double d1, double d2) {
/*  96 */     this(world);
/*  97 */     setPosition(d0, d1, d2);
/*  98 */     this.motX = 0.0D;
/*  99 */     this.motY = 0.0D;
/* 100 */     this.motZ = 0.0D;
/* 101 */     this.lastX = d0;
/* 102 */     this.lastY = d1;
/* 103 */     this.lastZ = d2;
/*     */     
/* 105 */     this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */   
/*     */   public double an() {
/* 109 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 113 */     if ((!this.world.isClientSide) && (!this.dead)) {
/* 114 */       if (isInvulnerable(damagesource)) {
/* 115 */         return false;
/*     */       }
/*     */       
/* 118 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 119 */       org.bukkit.entity.Entity passenger = damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity();
/*     */       
/* 121 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, f);
/* 122 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 124 */       if (event.isCancelled()) {
/* 125 */         return true;
/*     */       }
/*     */       
/* 128 */       f = (float)event.getDamage();
/*     */       
/* 130 */       k(-r());
/* 131 */       j(10);
/* 132 */       ac();
/* 133 */       setDamage(getDamage() + f * 10.0F);
/* 134 */       boolean flag = ((damagesource.getEntity() instanceof EntityHuman)) && (((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
/*     */       
/* 136 */       if ((flag) || (getDamage() > 40.0F))
/*     */       {
/* 138 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
/* 139 */         this.world.getServer().getPluginManager().callEvent(destroyEvent);
/*     */         
/* 141 */         if (destroyEvent.isCancelled()) {
/* 142 */           setDamage(40.0F);
/* 143 */           return true;
/*     */         }
/*     */         
/* 146 */         if (this.passenger != null) {
/* 147 */           this.passenger.mount(null);
/*     */         }
/*     */         
/* 150 */         if ((flag) && (!hasCustomName())) {
/* 151 */           die();
/*     */         } else {
/* 153 */           a(damagesource);
/*     */         }
/*     */       }
/*     */       
/* 157 */       return true;
/*     */     }
/*     */     
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public void a(DamageSource damagesource)
/*     */   {
/* 165 */     die();
/* 166 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/* 167 */       ItemStack itemstack = new ItemStack(Items.MINECART, 1);
/*     */       
/* 169 */       if (this.b != null) {
/* 170 */         itemstack.c(this.b);
/*     */       }
/*     */       
/* 173 */       a(itemstack, 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean ad()
/*     */   {
/* 179 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void die() {
/* 183 */     super.die();
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 188 */     double prevX = this.locX;
/* 189 */     double prevY = this.locY;
/* 190 */     double prevZ = this.locZ;
/* 191 */     float prevYaw = this.yaw;
/* 192 */     float prevPitch = this.pitch;
/*     */     
/*     */ 
/* 195 */     if (getType() > 0) {
/* 196 */       j(getType() - 1);
/*     */     }
/*     */     
/* 199 */     if (getDamage() > 0.0F) {
/* 200 */       setDamage(getDamage() - 1.0F);
/*     */     }
/*     */     
/* 203 */     if (this.locY < -64.0D) {
/* 204 */       O();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 209 */     if ((!this.world.isClientSide) && ((this.world instanceof WorldServer))) {
/* 210 */       this.world.methodProfiler.a("portal");
/* 211 */       MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
/*     */       
/* 213 */       int i = L();
/* 214 */       if (this.ak)
/*     */       {
/* 216 */         if ((this.vehicle == null) && (this.al++ >= i)) {
/* 217 */           this.al = i;
/* 218 */           this.portalCooldown = aq();
/*     */           byte b0;
/*     */           byte b0;
/* 221 */           if (this.world.worldProvider.getDimension() == -1) {
/* 222 */             b0 = 0;
/*     */           } else {
/* 224 */             b0 = -1;
/*     */           }
/*     */           
/* 227 */           c(b0);
/*     */         }
/*     */         
/* 230 */         this.ak = false;
/*     */       }
/*     */       else {
/* 233 */         if (this.al > 0) {
/* 234 */           this.al -= 4;
/*     */         }
/*     */         
/* 237 */         if (this.al < 0) {
/* 238 */           this.al = 0;
/*     */         }
/*     */       }
/*     */       
/* 242 */       if (this.portalCooldown > 0) {
/* 243 */         this.portalCooldown -= 1;
/*     */       }
/*     */       
/* 246 */       this.world.methodProfiler.b();
/*     */     }
/*     */     
/* 249 */     if (this.world.isClientSide) {
/* 250 */       if (this.d > 0) {
/* 251 */         double d0 = this.locX + (this.e - this.locX) / this.d;
/* 252 */         double d1 = this.locY + (this.f - this.locY) / this.d;
/* 253 */         double d2 = this.locZ + (this.g - this.locZ) / this.d;
/* 254 */         double d3 = MathHelper.g(this.h - this.yaw);
/*     */         
/* 256 */         this.yaw = ((float)(this.yaw + d3 / this.d));
/* 257 */         this.pitch = ((float)(this.pitch + (this.i - this.pitch) / this.d));
/* 258 */         this.d -= 1;
/* 259 */         setPosition(d0, d1, d2);
/* 260 */         setYawPitch(this.yaw, this.pitch);
/*     */       } else {
/* 262 */         setPosition(this.locX, this.locY, this.locZ);
/* 263 */         setYawPitch(this.yaw, this.pitch);
/*     */       }
/*     */     }
/*     */     else {
/* 267 */       this.lastX = this.locX;
/* 268 */       this.lastY = this.locY;
/* 269 */       this.lastZ = this.locZ;
/* 270 */       this.motY -= 0.03999999910593033D;
/* 271 */       int j = MathHelper.floor(this.locX);
/*     */       
/* 273 */       int i = MathHelper.floor(this.locY);
/* 274 */       int k = MathHelper.floor(this.locZ);
/*     */       
/* 276 */       if (BlockMinecartTrackAbstract.e(this.world, new BlockPosition(j, i - 1, k))) {
/* 277 */         i--;
/*     */       }
/*     */       
/* 280 */       BlockPosition blockposition = new BlockPosition(j, i, k);
/* 281 */       IBlockData iblockdata = this.world.getType(blockposition);
/*     */       
/* 283 */       if (BlockMinecartTrackAbstract.d(iblockdata)) {
/* 284 */         a(blockposition, iblockdata);
/* 285 */         if (iblockdata.getBlock() == Blocks.ACTIVATOR_RAIL) {
/* 286 */           a(j, i, k, ((Boolean)iblockdata.get(BlockPoweredRail.POWERED)).booleanValue());
/*     */         }
/*     */       } else {
/* 289 */         n();
/*     */       }
/*     */       
/* 292 */       checkBlockCollisions();
/* 293 */       this.pitch = 0.0F;
/* 294 */       double d4 = this.lastX - this.locX;
/* 295 */       double d5 = this.lastZ - this.locZ;
/*     */       
/* 297 */       if (d4 * d4 + d5 * d5 > 0.001D) {
/* 298 */         this.yaw = ((float)(MathHelper.b(d5, d4) * 180.0D / 3.141592653589793D));
/* 299 */         if (this.a) {
/* 300 */           this.yaw += 180.0F;
/*     */         }
/*     */       }
/*     */       
/* 304 */       double d6 = MathHelper.g(this.yaw - this.lastYaw);
/*     */       
/* 306 */       if ((d6 < -170.0D) || (d6 >= 170.0D)) {
/* 307 */         this.yaw += 180.0F;
/* 308 */         this.a = (!this.a);
/*     */       }
/*     */       
/* 311 */       setYawPitch(this.yaw, this.pitch);
/*     */       
/*     */ 
/* 314 */       org.bukkit.World bworld = this.world.getWorld();
/* 315 */       Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 316 */       Location to = new Location(bworld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 317 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 319 */       this.world.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));
/*     */       
/* 321 */       if (!from.equals(to)) {
/* 322 */         this.world.getServer().getPluginManager().callEvent(new VehicleMoveEvent(vehicle, from, to));
/*     */       }
/*     */       
/*     */ 
/* 326 */       Iterator iterator = this.world.getEntities(this, getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D)).iterator();
/*     */       
/* 328 */       while (iterator.hasNext()) {
/* 329 */         Entity entity = (Entity)iterator.next();
/*     */         
/* 331 */         if ((entity != this.passenger) && (entity.ae()) && ((entity instanceof EntityMinecartAbstract))) {
/* 332 */           entity.collide(this);
/*     */         }
/*     */       }
/*     */       
/* 336 */       if ((this.passenger != null) && (this.passenger.dead)) {
/* 337 */         if (this.passenger.vehicle == this) {
/* 338 */           this.passenger.vehicle = null;
/*     */         }
/*     */         
/* 341 */         this.passenger = null;
/*     */       }
/*     */       
/* 344 */       W();
/*     */     }
/*     */   }
/*     */   
/*     */   protected double m() {
/* 349 */     return this.maxSpeed;
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k, boolean flag) {}
/*     */   
/*     */   protected void n() {
/* 355 */     double d0 = m();
/*     */     
/* 357 */     this.motX = MathHelper.a(this.motX, -d0, d0);
/* 358 */     this.motZ = MathHelper.a(this.motZ, -d0, d0);
/* 359 */     if (this.onGround)
/*     */     {
/* 361 */       this.motX *= this.derailedX;
/* 362 */       this.motY *= this.derailedY;
/* 363 */       this.motZ *= this.derailedZ;
/*     */     }
/*     */     
/*     */ 
/* 367 */     move(this.motX, this.motY, this.motZ);
/* 368 */     if (!this.onGround)
/*     */     {
/* 370 */       this.motX *= this.flyingX;
/* 371 */       this.motY *= this.flyingY;
/* 372 */       this.motZ *= this.flyingZ;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void a(BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 379 */     this.fallDistance = 0.0F;
/* 380 */     Vec3D vec3d = k(this.locX, this.locY, this.locZ);
/*     */     
/* 382 */     this.locY = blockposition.getY();
/* 383 */     boolean flag = false;
/* 384 */     boolean flag1 = false;
/* 385 */     BlockMinecartTrackAbstract blockminecarttrackabstract = (BlockMinecartTrackAbstract)iblockdata.getBlock();
/*     */     
/* 387 */     if (blockminecarttrackabstract == Blocks.GOLDEN_RAIL) {
/* 388 */       flag = ((Boolean)iblockdata.get(BlockPoweredRail.POWERED)).booleanValue();
/* 389 */       flag1 = !flag;
/*     */     }
/*     */     
/*     */ 
/* 393 */     BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(blockminecarttrackabstract.n());
/*     */     
/* 395 */     switch (SyntheticClass_1.b[blockminecarttrackabstract_enumtrackposition.ordinal()]) {
/*     */     case 1: 
/* 397 */       this.motX -= 0.0078125D;
/* 398 */       this.locY += 1.0D;
/* 399 */       break;
/*     */     
/*     */     case 2: 
/* 402 */       this.motX += 0.0078125D;
/* 403 */       this.locY += 1.0D;
/* 404 */       break;
/*     */     
/*     */     case 3: 
/* 407 */       this.motZ += 0.0078125D;
/* 408 */       this.locY += 1.0D;
/* 409 */       break;
/*     */     
/*     */     case 4: 
/* 412 */       this.motZ -= 0.0078125D;
/* 413 */       this.locY += 1.0D;
/*     */     }
/*     */     
/* 416 */     int[][] aint = matrix[blockminecarttrackabstract_enumtrackposition.a()];
/* 417 */     double d1 = aint[1][0] - aint[0][0];
/* 418 */     double d2 = aint[1][2] - aint[0][2];
/* 419 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/* 420 */     double d4 = this.motX * d1 + this.motZ * d2;
/*     */     
/* 422 */     if (d4 < 0.0D) {
/* 423 */       d1 = -d1;
/* 424 */       d2 = -d2;
/*     */     }
/*     */     
/* 427 */     double d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 429 */     if (d5 > 2.0D) {
/* 430 */       d5 = 2.0D;
/*     */     }
/*     */     
/* 433 */     this.motX = (d5 * d1 / d3);
/* 434 */     this.motZ = (d5 * d2 / d3);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 440 */     if ((this.passenger instanceof EntityLiving)) {
/* 441 */       double d6 = ((EntityLiving)this.passenger).ba;
/* 442 */       if (d6 > 0.0D) {
/* 443 */         double d7 = -Math.sin(this.passenger.yaw * 3.1415927F / 180.0F);
/* 444 */         double d8 = Math.cos(this.passenger.yaw * 3.1415927F / 180.0F);
/* 445 */         double d9 = this.motX * this.motX + this.motZ * this.motZ;
/* 446 */         if (d9 < 0.01D) {
/* 447 */           this.motX += d7 * 0.1D;
/* 448 */           this.motZ += d8 * 0.1D;
/* 449 */           flag1 = false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 454 */     if (flag1) {
/* 455 */       double d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 456 */       if (d6 < 0.03D) {
/* 457 */         this.motX *= 0.0D;
/* 458 */         this.motY *= 0.0D;
/* 459 */         this.motZ *= 0.0D;
/*     */       } else {
/* 461 */         this.motX *= 0.5D;
/* 462 */         this.motY *= 0.0D;
/* 463 */         this.motZ *= 0.5D;
/*     */       }
/*     */     }
/*     */     
/* 467 */     double d6 = 0.0D;
/* 468 */     double d7 = blockposition.getX() + 0.5D + aint[0][0] * 0.5D;
/* 469 */     double d8 = blockposition.getZ() + 0.5D + aint[0][2] * 0.5D;
/* 470 */     double d9 = blockposition.getX() + 0.5D + aint[1][0] * 0.5D;
/* 471 */     double d10 = blockposition.getZ() + 0.5D + aint[1][2] * 0.5D;
/*     */     
/* 473 */     d1 = d9 - d7;
/* 474 */     d2 = d10 - d8;
/*     */     
/*     */ 
/*     */ 
/* 478 */     if (d1 == 0.0D) {
/* 479 */       this.locX = (blockposition.getX() + 0.5D);
/* 480 */       d6 = this.locZ - blockposition.getZ();
/* 481 */     } else if (d2 == 0.0D) {
/* 482 */       this.locZ = (blockposition.getZ() + 0.5D);
/* 483 */       d6 = this.locX - blockposition.getX();
/*     */     } else {
/* 485 */       double d11 = this.locX - d7;
/* 486 */       double d12 = this.locZ - d8;
/* 487 */       d6 = (d11 * d1 + d12 * d2) * 2.0D;
/*     */     }
/*     */     
/* 490 */     this.locX = (d7 + d1 * d6);
/* 491 */     this.locZ = (d8 + d2 * d6);
/* 492 */     setPosition(this.locX, this.locY, this.locZ);
/* 493 */     double d11 = this.motX;
/* 494 */     double d12 = this.motZ;
/* 495 */     if (this.passenger != null) {
/* 496 */       d11 *= 0.75D;
/* 497 */       d12 *= 0.75D;
/*     */     }
/*     */     
/* 500 */     double d13 = m();
/*     */     
/* 502 */     d11 = MathHelper.a(d11, -d13, d13);
/* 503 */     d12 = MathHelper.a(d12, -d13, d13);
/* 504 */     move(d11, 0.0D, d12);
/* 505 */     if ((aint[0][1] != 0) && (MathHelper.floor(this.locX) - blockposition.getX() == aint[0][0]) && (MathHelper.floor(this.locZ) - blockposition.getZ() == aint[0][2])) {
/* 506 */       setPosition(this.locX, this.locY + aint[0][1], this.locZ);
/* 507 */     } else if ((aint[1][1] != 0) && (MathHelper.floor(this.locX) - blockposition.getX() == aint[1][0]) && (MathHelper.floor(this.locZ) - blockposition.getZ() == aint[1][2])) {
/* 508 */       setPosition(this.locX, this.locY + aint[1][1], this.locZ);
/*     */     }
/*     */     
/* 511 */     o();
/* 512 */     Vec3D vec3d1 = k(this.locX, this.locY, this.locZ);
/*     */     
/* 514 */     if ((vec3d1 != null) && (vec3d != null)) {
/* 515 */       double d14 = (vec3d.b - vec3d1.b) * 0.05D;
/*     */       
/* 517 */       d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 518 */       if (d5 > 0.0D) {
/* 519 */         this.motX = (this.motX / d5 * (d5 + d14));
/* 520 */         this.motZ = (this.motZ / d5 * (d5 + d14));
/*     */       }
/*     */       
/* 523 */       setPosition(this.locX, vec3d1.b, this.locZ);
/*     */     }
/*     */     
/* 526 */     int i = MathHelper.floor(this.locX);
/* 527 */     int j = MathHelper.floor(this.locZ);
/*     */     
/* 529 */     if ((i != blockposition.getX()) || (j != blockposition.getZ())) {
/* 530 */       d5 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 531 */       this.motX = (d5 * (i - blockposition.getX()));
/* 532 */       this.motZ = (d5 * (j - blockposition.getZ()));
/*     */     }
/*     */     
/* 535 */     if (flag) {
/* 536 */       double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 538 */       if (d15 > 0.01D) {
/* 539 */         double d16 = 0.06D;
/*     */         
/* 541 */         this.motX += this.motX / d15 * d16;
/* 542 */         this.motZ += this.motZ / d15 * d16;
/* 543 */       } else if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) {
/* 544 */         if (this.world.getType(blockposition.west()).getBlock().isOccluding()) {
/* 545 */           this.motX = 0.02D;
/* 546 */         } else if (this.world.getType(blockposition.east()).getBlock().isOccluding()) {
/* 547 */           this.motX = -0.02D;
/*     */         }
/* 549 */       } else if (blockminecarttrackabstract_enumtrackposition == BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) {
/* 550 */         if (this.world.getType(blockposition.north()).getBlock().isOccluding()) {
/* 551 */           this.motZ = 0.02D;
/* 552 */         } else if (this.world.getType(blockposition.south()).getBlock().isOccluding()) {
/* 553 */           this.motZ = -0.02D;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void o()
/*     */   {
/* 561 */     if ((this.passenger != null) || (!this.slowWhenEmpty)) {
/* 562 */       this.motX *= 0.996999979019165D;
/* 563 */       this.motY *= 0.0D;
/* 564 */       this.motZ *= 0.996999979019165D;
/*     */     } else {
/* 566 */       this.motX *= 0.9599999785423279D;
/* 567 */       this.motY *= 0.0D;
/* 568 */       this.motZ *= 0.9599999785423279D;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPosition(double d0, double d1, double d2)
/*     */   {
/* 574 */     this.locX = d0;
/* 575 */     this.locY = d1;
/* 576 */     this.locZ = d2;
/* 577 */     float f = this.width / 2.0F;
/* 578 */     float f1 = this.length;
/*     */     
/* 580 */     a(new AxisAlignedBB(d0 - f, d1, d2 - f, d0 + f, d1 + f1, d2 + f));
/*     */   }
/*     */   
/*     */   public Vec3D k(double d0, double d1, double d2) {
/* 584 */     int i = MathHelper.floor(d0);
/* 585 */     int j = MathHelper.floor(d1);
/* 586 */     int k = MathHelper.floor(d2);
/*     */     
/* 588 */     if (BlockMinecartTrackAbstract.e(this.world, new BlockPosition(i, j - 1, k))) {
/* 589 */       j--;
/*     */     }
/*     */     
/* 592 */     IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
/*     */     
/* 594 */     if (BlockMinecartTrackAbstract.d(iblockdata)) {
/* 595 */       BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n());
/* 596 */       int[][] aint = matrix[blockminecarttrackabstract_enumtrackposition.a()];
/* 597 */       double d3 = 0.0D;
/* 598 */       double d4 = i + 0.5D + aint[0][0] * 0.5D;
/* 599 */       double d5 = j + 0.0625D + aint[0][1] * 0.5D;
/* 600 */       double d6 = k + 0.5D + aint[0][2] * 0.5D;
/* 601 */       double d7 = i + 0.5D + aint[1][0] * 0.5D;
/* 602 */       double d8 = j + 0.0625D + aint[1][1] * 0.5D;
/* 603 */       double d9 = k + 0.5D + aint[1][2] * 0.5D;
/* 604 */       double d10 = d7 - d4;
/* 605 */       double d11 = (d8 - d5) * 2.0D;
/* 606 */       double d12 = d9 - d6;
/*     */       
/* 608 */       if (d10 == 0.0D) {
/* 609 */         d0 = i + 0.5D;
/* 610 */         d3 = d2 - k;
/* 611 */       } else if (d12 == 0.0D) {
/* 612 */         d2 = k + 0.5D;
/* 613 */         d3 = d0 - i;
/*     */       } else {
/* 615 */         double d13 = d0 - d4;
/* 616 */         double d14 = d2 - d6;
/*     */         
/* 618 */         d3 = (d13 * d10 + d14 * d12) * 2.0D;
/*     */       }
/*     */       
/* 621 */       d0 = d4 + d10 * d3;
/* 622 */       d1 = d5 + d11 * d3;
/* 623 */       d2 = d6 + d12 * d3;
/* 624 */       if (d11 < 0.0D) {
/* 625 */         d1 += 1.0D;
/*     */       }
/*     */       
/* 628 */       if (d11 > 0.0D) {
/* 629 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 632 */       return new Vec3D(d0, d1, d2);
/*     */     }
/* 634 */     return null;
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 639 */     if (nbttagcompound.getBoolean("CustomDisplayTile")) {
/* 640 */       int i = nbttagcompound.getInt("DisplayData");
/*     */       
/*     */ 
/* 643 */       if (nbttagcompound.hasKeyOfType("DisplayTile", 8)) {
/* 644 */         Block block = Block.getByName(nbttagcompound.getString("DisplayTile"));
/* 645 */         if (block == null) {
/* 646 */           setDisplayBlock(Blocks.AIR.getBlockData());
/*     */         } else {
/* 648 */           setDisplayBlock(block.fromLegacyData(i));
/*     */         }
/*     */       } else {
/* 651 */         Block block = Block.getById(nbttagcompound.getInt("DisplayTile"));
/* 652 */         if (block == null) {
/* 653 */           setDisplayBlock(Blocks.AIR.getBlockData());
/*     */         } else {
/* 655 */           setDisplayBlock(block.fromLegacyData(i));
/*     */         }
/*     */       }
/*     */       
/* 659 */       SetDisplayBlockOffset(nbttagcompound.getInt("DisplayOffset"));
/*     */     }
/*     */     
/* 662 */     if ((nbttagcompound.hasKeyOfType("CustomName", 8)) && (nbttagcompound.getString("CustomName").length() > 0)) {
/* 663 */       this.b = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 669 */     if (x()) {
/* 670 */       nbttagcompound.setBoolean("CustomDisplayTile", true);
/* 671 */       IBlockData iblockdata = getDisplayBlock();
/* 672 */       MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(iblockdata.getBlock());
/*     */       
/* 674 */       nbttagcompound.setString("DisplayTile", minecraftkey == null ? "" : minecraftkey.toString());
/* 675 */       nbttagcompound.setInt("DisplayData", iblockdata.getBlock().toLegacyData(iblockdata));
/* 676 */       nbttagcompound.setInt("DisplayOffset", getDisplayBlockOffset());
/*     */     }
/*     */     
/* 679 */     if ((this.b != null) && (this.b.length() > 0)) {
/* 680 */       nbttagcompound.setString("CustomName", this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void collide(Entity entity)
/*     */   {
/* 686 */     if ((!this.world.isClientSide) && 
/* 687 */       (!entity.noclip) && (!this.noclip) && 
/* 688 */       (entity != this.passenger))
/*     */     {
/* 690 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 691 */       org.bukkit.entity.Entity hitEntity = entity == null ? null : entity.getBukkitEntity();
/*     */       
/* 693 */       VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
/* 694 */       this.world.getServer().getPluginManager().callEvent(collisionEvent);
/*     */       
/* 696 */       if (collisionEvent.isCancelled()) {
/* 697 */         return;
/*     */       }
/*     */       
/* 700 */       if (((entity instanceof EntityLiving)) && (!(entity instanceof EntityHuman)) && (!(entity instanceof EntityIronGolem)) && (s() == EnumMinecartType.RIDEABLE) && (this.motX * this.motX + this.motZ * this.motZ > 0.01D) && (this.passenger == null) && (entity.vehicle == null)) {
/* 701 */         entity.mount(this);
/*     */       }
/*     */       
/* 704 */       double d0 = entity.locX - this.locX;
/* 705 */       double d1 = entity.locZ - this.locZ;
/* 706 */       double d2 = d0 * d0 + d1 * d1;
/*     */       
/*     */ 
/* 709 */       if ((d2 >= 9.999999747378752E-5D) && (!collisionEvent.isCollisionCancelled())) {
/* 710 */         d2 = MathHelper.sqrt(d2);
/* 711 */         d0 /= d2;
/* 712 */         d1 /= d2;
/* 713 */         double d3 = 1.0D / d2;
/*     */         
/* 715 */         if (d3 > 1.0D) {
/* 716 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 719 */         d0 *= d3;
/* 720 */         d1 *= d3;
/* 721 */         d0 *= 0.10000000149011612D;
/* 722 */         d1 *= 0.10000000149011612D;
/* 723 */         d0 *= (1.0F - this.U);
/* 724 */         d1 *= (1.0F - this.U);
/* 725 */         d0 *= 0.5D;
/* 726 */         d1 *= 0.5D;
/* 727 */         if ((entity instanceof EntityMinecartAbstract)) {
/* 728 */           double d4 = entity.locX - this.locX;
/* 729 */           double d5 = entity.locZ - this.locZ;
/* 730 */           Vec3D vec3d = new Vec3D(d4, 0.0D, d5).a();
/* 731 */           Vec3D vec3d1 = new Vec3D(MathHelper.cos(this.yaw * 3.1415927F / 180.0F), 0.0D, MathHelper.sin(this.yaw * 3.1415927F / 180.0F)).a();
/* 732 */           double d6 = Math.abs(vec3d.b(vec3d1));
/*     */           
/* 734 */           if (d6 < 0.800000011920929D) {
/* 735 */             return;
/*     */           }
/*     */           
/* 738 */           double d7 = entity.motX + this.motX;
/* 739 */           double d8 = entity.motZ + this.motZ;
/*     */           
/* 741 */           if ((((EntityMinecartAbstract)entity).s() == EnumMinecartType.FURNACE) && (s() != EnumMinecartType.FURNACE)) {
/* 742 */             this.motX *= 0.20000000298023224D;
/* 743 */             this.motZ *= 0.20000000298023224D;
/* 744 */             g(entity.motX - d0, 0.0D, entity.motZ - d1);
/* 745 */             entity.motX *= 0.949999988079071D;
/* 746 */             entity.motZ *= 0.949999988079071D;
/* 747 */           } else if ((((EntityMinecartAbstract)entity).s() != EnumMinecartType.FURNACE) && (s() == EnumMinecartType.FURNACE)) {
/* 748 */             entity.motX *= 0.20000000298023224D;
/* 749 */             entity.motZ *= 0.20000000298023224D;
/* 750 */             entity.g(this.motX + d0, 0.0D, this.motZ + d1);
/* 751 */             this.motX *= 0.949999988079071D;
/* 752 */             this.motZ *= 0.949999988079071D;
/*     */           } else {
/* 754 */             d7 /= 2.0D;
/* 755 */             d8 /= 2.0D;
/* 756 */             this.motX *= 0.20000000298023224D;
/* 757 */             this.motZ *= 0.20000000298023224D;
/* 758 */             g(d7 - d0, 0.0D, d8 - d1);
/* 759 */             entity.motX *= 0.20000000298023224D;
/* 760 */             entity.motZ *= 0.20000000298023224D;
/* 761 */             entity.g(d7 + d0, 0.0D, d8 + d1);
/*     */           }
/*     */         } else {
/* 764 */           g(-d0, 0.0D, -d1);
/* 765 */           entity.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDamage(float f)
/*     */   {
/* 775 */     this.datawatcher.watch(19, Float.valueOf(f));
/*     */   }
/*     */   
/*     */   public float getDamage() {
/* 779 */     return this.datawatcher.getFloat(19);
/*     */   }
/*     */   
/*     */   public void j(int i) {
/* 783 */     this.datawatcher.watch(17, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int getType() {
/* 787 */     return this.datawatcher.getInt(17);
/*     */   }
/*     */   
/*     */   public void k(int i) {
/* 791 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int r() {
/* 795 */     return this.datawatcher.getInt(18);
/*     */   }
/*     */   
/*     */   public abstract EnumMinecartType s();
/*     */   
/*     */   public IBlockData getDisplayBlock() {
/* 801 */     return !x() ? u() : Block.getByCombinedId(getDataWatcher().getInt(20));
/*     */   }
/*     */   
/*     */   public IBlockData u() {
/* 805 */     return Blocks.AIR.getBlockData();
/*     */   }
/*     */   
/*     */   public int getDisplayBlockOffset() {
/* 809 */     return !x() ? w() : getDataWatcher().getInt(21);
/*     */   }
/*     */   
/*     */   public int w() {
/* 813 */     return 6;
/*     */   }
/*     */   
/*     */   public void setDisplayBlock(IBlockData iblockdata) {
/* 817 */     getDataWatcher().watch(20, Integer.valueOf(Block.getCombinedId(iblockdata)));
/* 818 */     a(true);
/*     */   }
/*     */   
/*     */   public void SetDisplayBlockOffset(int i) {
/* 822 */     getDataWatcher().watch(21, Integer.valueOf(i));
/* 823 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean x() {
/* 827 */     return getDataWatcher().getByte(22) == 1;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 831 */     getDataWatcher().watch(22, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public void setCustomName(String s) {
/* 835 */     this.b = s;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 839 */     return this.b != null ? this.b : super.getName();
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 843 */     return this.b != null;
/*     */   }
/*     */   
/*     */   public String getCustomName() {
/* 847 */     return this.b;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/* 851 */     if (hasCustomName()) {
/* 852 */       ChatComponentText chatcomponenttext = new ChatComponentText(this.b);
/*     */       
/* 854 */       chatcomponenttext.getChatModifier().setChatHoverable(aQ());
/* 855 */       chatcomponenttext.getChatModifier().setInsertion(getUniqueID().toString());
/* 856 */       return chatcomponenttext;
/*     */     }
/* 858 */     ChatMessage chatmessage = new ChatMessage(getName(), new Object[0]);
/*     */     
/* 860 */     chatmessage.getChatModifier().setChatHoverable(aQ());
/* 861 */     chatmessage.getChatModifier().setInsertion(getUniqueID().toString());
/* 862 */     return chatmessage;
/*     */   }
/*     */   
/*     */ 
/*     */   static class SyntheticClass_1
/*     */   {
/*     */     static final int[] a;
/* 869 */     static final int[] b = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 873 */         b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 879 */         b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 885 */         b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 891 */         b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */ 
/* 896 */       a = new int[EntityMinecartAbstract.EnumMinecartType.values().length];
/*     */       try
/*     */       {
/* 899 */         a[EntityMinecartAbstract.EnumMinecartType.CHEST.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 905 */         a[EntityMinecartAbstract.EnumMinecartType.FURNACE.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */       
/*     */       try
/*     */       {
/* 911 */         a[EntityMinecartAbstract.EnumMinecartType.TNT.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/*     */       
/*     */       try
/*     */       {
/* 917 */         a[EntityMinecartAbstract.EnumMinecartType.SPAWNER.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/*     */       
/*     */       try
/*     */       {
/* 923 */         a[EntityMinecartAbstract.EnumMinecartType.HOPPER.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/*     */       
/*     */       try
/*     */       {
/* 929 */         a[EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumMinecartType
/*     */   {
/* 939 */     RIDEABLE(0, "MinecartRideable"),  CHEST(1, "MinecartChest"),  FURNACE(2, "MinecartFurnace"),  TNT(3, "MinecartTNT"),  SPAWNER(4, "MinecartSpawner"),  HOPPER(5, "MinecartHopper"),  COMMAND_BLOCK(6, "MinecartCommandBlock");
/*     */     
/*     */     private static final Map<Integer, EnumMinecartType> h;
/*     */     private final int i;
/*     */     private final String j;
/*     */     
/*     */     private EnumMinecartType(int i, String s) {
/* 946 */       this.i = i;
/* 947 */       this.j = s;
/*     */     }
/*     */     
/*     */     public int a() {
/* 951 */       return this.i;
/*     */     }
/*     */     
/*     */     public String b() {
/* 955 */       return this.j;
/*     */     }
/*     */     
/*     */     public static EnumMinecartType a(int i) {
/* 959 */       EnumMinecartType entityminecartabstract_enumminecarttype = (EnumMinecartType)h.get(Integer.valueOf(i));
/*     */       
/* 961 */       return entityminecartabstract_enumminecarttype == null ? RIDEABLE : entityminecartabstract_enumminecarttype;
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 941 */       h = com.google.common.collect.Maps.newHashMap();
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
/* 965 */       EnumMinecartType[] aentityminecartabstract_enumminecarttype = values();
/* 966 */       int i = aentityminecartabstract_enumminecarttype.length;
/*     */       
/* 968 */       for (int j = 0; j < i; j++) {
/* 969 */         EnumMinecartType entityminecartabstract_enumminecarttype = aentityminecartabstract_enumminecarttype[j];
/*     */         
/* 971 */         h.put(Integer.valueOf(entityminecartabstract_enumminecarttype.a()), entityminecartabstract_enumminecarttype);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Vector getFlyingVelocityMod()
/*     */   {
/* 979 */     return new Vector(this.flyingX, this.flyingY, this.flyingZ);
/*     */   }
/*     */   
/*     */   public void setFlyingVelocityMod(Vector flying) {
/* 983 */     this.flyingX = flying.getX();
/* 984 */     this.flyingY = flying.getY();
/* 985 */     this.flyingZ = flying.getZ();
/*     */   }
/*     */   
/*     */   public Vector getDerailedVelocityMod() {
/* 989 */     return new Vector(this.derailedX, this.derailedY, this.derailedZ);
/*     */   }
/*     */   
/*     */   public void setDerailedVelocityMod(Vector derailed) {
/* 993 */     this.derailedX = derailed.getX();
/* 994 */     this.derailedY = derailed.getY();
/* 995 */     this.derailedZ = derailed.getZ();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */