/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public abstract class EntityInsentient extends EntityLiving
/*     */ {
/*     */   public int a_;
/*     */   protected int b_;
/*     */   private ControllerLook lookController;
/*     */   protected ControllerMove moveController;
/*     */   protected ControllerJump g;
/*     */   private EntityAIBodyControl b;
/*     */   protected NavigationAbstract navigation;
/*     */   public PathfinderGoalSelector goalSelector;
/*     */   public PathfinderGoalSelector targetSelector;
/*     */   private EntityLiving goalTarget;
/*     */   private EntitySenses bk;
/*  29 */   private ItemStack[] equipment = new ItemStack[5];
/*  30 */   public float[] dropChances = new float[5];
/*     */   public boolean canPickUpLoot;
/*     */   public boolean persistent;
/*     */   private boolean bo;
/*     */   private Entity bp;
/*     */   private NBTTagCompound bq;
/*     */   
/*     */   public EntityInsentient(World world) {
/*  38 */     super(world);
/*  39 */     this.goalSelector = new PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
/*  40 */     this.targetSelector = new PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
/*  41 */     this.lookController = new ControllerLook(this);
/*  42 */     this.moveController = new ControllerMove(this);
/*  43 */     this.g = new ControllerJump(this);
/*  44 */     this.b = new EntityAIBodyControl(this);
/*  45 */     this.navigation = b(world);
/*  46 */     this.bk = new EntitySenses(this);
/*     */     
/*  48 */     for (int i = 0; i < this.dropChances.length; i++) {
/*  49 */       this.dropChances[i] = 0.085F;
/*     */     }
/*     */     
/*     */ 
/*  53 */     this.persistent = (!isTypeNotPersistent());
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  58 */     super.initAttributes();
/*  59 */     getAttributeMap().b(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/*     */   }
/*     */   
/*     */   protected NavigationAbstract b(World world) {
/*  63 */     return new Navigation(this, world);
/*     */   }
/*     */   
/*     */   public ControllerLook getControllerLook() {
/*  67 */     return this.lookController;
/*     */   }
/*     */   
/*     */   public ControllerMove getControllerMove() {
/*  71 */     return this.moveController;
/*     */   }
/*     */   
/*     */   public ControllerJump getControllerJump() {
/*  75 */     return this.g;
/*     */   }
/*     */   
/*     */   public NavigationAbstract getNavigation() {
/*  79 */     return this.navigation;
/*     */   }
/*     */   
/*     */   public EntitySenses getEntitySenses() {
/*  83 */     return this.bk;
/*     */   }
/*     */   
/*     */   public EntityLiving getGoalTarget() {
/*  87 */     return this.goalTarget;
/*     */   }
/*     */   
/*     */   public void setGoalTarget(EntityLiving entityliving)
/*     */   {
/*  92 */     setGoalTarget(entityliving, EntityTargetEvent.TargetReason.UNKNOWN, true);
/*     */   }
/*     */   
/*     */   public void setGoalTarget(EntityLiving entityliving, EntityTargetEvent.TargetReason reason, boolean fireEvent) {
/*  96 */     if (getGoalTarget() == entityliving) return;
/*  97 */     if (fireEvent) {
/*  98 */       if ((reason == EntityTargetEvent.TargetReason.UNKNOWN) && (getGoalTarget() != null) && (entityliving == null)) {
/*  99 */         reason = getGoalTarget().isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
/*     */       }
/* 101 */       if (reason == EntityTargetEvent.TargetReason.UNKNOWN) {
/* 102 */         this.world.getServer().getLogger().log(java.util.logging.Level.WARNING, "Unknown target reason, please report on the issue tracker", new Exception());
/*     */       }
/* 104 */       CraftLivingEntity ctarget = null;
/* 105 */       if (entityliving != null) {
/* 106 */         ctarget = (CraftLivingEntity)entityliving.getBukkitEntity();
/*     */       }
/* 108 */       EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent(getBukkitEntity(), ctarget, reason);
/* 109 */       this.world.getServer().getPluginManager().callEvent(event);
/* 110 */       if (event.isCancelled()) {
/* 111 */         return;
/*     */       }
/*     */       
/* 114 */       if (event.getTarget() != null) {
/* 115 */         entityliving = ((CraftLivingEntity)event.getTarget()).getHandle();
/*     */       } else {
/* 117 */         entityliving = null;
/*     */       }
/*     */     }
/* 120 */     this.goalTarget = entityliving;
/*     */   }
/*     */   
/*     */   public boolean a(Class<? extends EntityLiving> oclass)
/*     */   {
/* 125 */     return oclass != EntityGhast.class;
/*     */   }
/*     */   
/*     */   public void v() {}
/*     */   
/*     */   protected void h() {
/* 131 */     super.h();
/* 132 */     this.datawatcher.a(15, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int w() {
/* 136 */     return 80;
/*     */   }
/*     */   
/*     */   public void x() {
/* 140 */     String s = z();
/*     */     
/* 142 */     if (s != null) {
/* 143 */       makeSound(s, bB(), bC());
/*     */     }
/*     */   }
/*     */   
/*     */   public void K()
/*     */   {
/* 149 */     super.K();
/* 150 */     this.world.methodProfiler.a("mobBaseTick");
/* 151 */     if ((isAlive()) && (this.random.nextInt(1000) < this.a_++)) {
/* 152 */       this.a_ = (-w());
/* 153 */       x();
/*     */     }
/*     */     
/* 156 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/* 160 */     if (this.b_ > 0) {
/* 161 */       int i = this.b_;
/* 162 */       ItemStack[] aitemstack = getEquipment();
/*     */       
/* 164 */       for (int j = 0; j < aitemstack.length; j++) {
/* 165 */         if ((aitemstack[j] != null) && (this.dropChances[j] <= 1.0F)) {
/* 166 */           i += 1 + this.random.nextInt(3);
/*     */         }
/*     */       }
/*     */       
/* 170 */       return i;
/*     */     }
/* 172 */     return this.b_;
/*     */   }
/*     */   
/*     */   public void y()
/*     */   {
/* 177 */     if (this.world.isClientSide) {
/* 178 */       for (int i = 0; i < 20; i++) {
/* 179 */         double d0 = this.random.nextGaussian() * 0.02D;
/* 180 */         double d1 = this.random.nextGaussian() * 0.02D;
/* 181 */         double d2 = this.random.nextGaussian() * 0.02D;
/* 182 */         double d3 = 10.0D;
/*     */         
/* 184 */         this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width - d0 * d3, this.locY + this.random.nextFloat() * this.length - d1 * d3, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width - d2 * d3, d0, d1, d2, new int[0]);
/*     */       }
/*     */     } else {
/* 187 */       this.world.broadcastEntityEffect(this, (byte)20);
/*     */     }
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 193 */     super.t_();
/* 194 */     if (!this.world.isClientSide) {
/* 195 */       ca();
/*     */     }
/*     */   }
/*     */   
/*     */   protected float h(float f, float f1)
/*     */   {
/* 201 */     this.b.a();
/* 202 */     return f1;
/*     */   }
/*     */   
/*     */   protected String z() {
/* 206 */     return null;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 210 */     return null;
/*     */   }
/*     */   
/* 213 */   protected ItemStack headDrop = null;
/*     */   
/* 215 */   protected void dropDeathLoot(boolean flag, int i) { Item item = getLoot();
/*     */     
/* 217 */     if (item != null) {
/* 218 */       int j = this.random.nextInt(3);
/*     */       
/* 220 */       if (i > 0) {
/* 221 */         j += this.random.nextInt(i + 1);
/*     */       }
/*     */       
/* 224 */       for (int k = 0; k < j; k++) {
/* 225 */         a(item, 1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 230 */     if (this.headDrop != null) {
/* 231 */       a(this.headDrop, 0.0F);
/* 232 */       this.headDrop = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 238 */     super.b(nbttagcompound);
/* 239 */     nbttagcompound.setBoolean("CanPickUpLoot", bY());
/* 240 */     nbttagcompound.setBoolean("PersistenceRequired", this.persistent);
/* 241 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*     */ 
/*     */ 
/* 245 */     for (int i = 0; i < this.equipment.length; i++) {
/* 246 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 247 */       if (this.equipment[i] != null) {
/* 248 */         this.equipment[i].save(nbttagcompound1);
/*     */       }
/*     */       
/* 251 */       nbttaglist.add(nbttagcompound1);
/*     */     }
/*     */     
/* 254 */     nbttagcompound.set("Equipment", nbttaglist);
/* 255 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 257 */     for (int j = 0; j < this.dropChances.length; j++) {
/* 258 */       nbttaglist1.add(new NBTTagFloat(this.dropChances[j]));
/*     */     }
/*     */     
/* 261 */     nbttagcompound.set("DropChances", nbttaglist1);
/* 262 */     nbttagcompound.setBoolean("Leashed", this.bo);
/* 263 */     if (this.bp != null) {
/* 264 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 265 */       if ((this.bp instanceof EntityLiving)) {
/* 266 */         nbttagcompound1.setLong("UUIDMost", this.bp.getUniqueID().getMostSignificantBits());
/* 267 */         nbttagcompound1.setLong("UUIDLeast", this.bp.getUniqueID().getLeastSignificantBits());
/* 268 */       } else if ((this.bp instanceof EntityHanging)) {
/* 269 */         BlockPosition blockposition = ((EntityHanging)this.bp).getBlockPosition();
/*     */         
/* 271 */         nbttagcompound1.setInt("X", blockposition.getX());
/* 272 */         nbttagcompound1.setInt("Y", blockposition.getY());
/* 273 */         nbttagcompound1.setInt("Z", blockposition.getZ());
/*     */       }
/*     */       
/* 276 */       nbttagcompound.set("Leash", nbttagcompound1);
/*     */     }
/*     */     
/* 279 */     if (ce()) {
/* 280 */       nbttagcompound.setBoolean("NoAI", ce());
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 286 */     super.a(nbttagcompound);
/*     */     
/*     */ 
/* 289 */     if (nbttagcompound.hasKeyOfType("CanPickUpLoot", 1)) {
/* 290 */       boolean data = nbttagcompound.getBoolean("CanPickUpLoot");
/* 291 */       if ((isLevelAtLeast(nbttagcompound, 1)) || (data)) {
/* 292 */         j(data);
/*     */       }
/*     */     }
/*     */     
/* 296 */     boolean data = nbttagcompound.getBoolean("PersistenceRequired");
/* 297 */     if ((isLevelAtLeast(nbttagcompound, 1)) || (data)) {
/* 298 */       this.persistent = data;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 304 */     if (nbttagcompound.hasKeyOfType("Equipment", 9)) {
/* 305 */       NBTTagList nbttaglist = nbttagcompound.getList("Equipment", 10);
/*     */       
/* 307 */       for (int i = 0; i < this.equipment.length; i++) {
/* 308 */         this.equipment[i] = ItemStack.createStack(nbttaglist.get(i));
/*     */       }
/*     */     }
/*     */     
/* 312 */     if (nbttagcompound.hasKeyOfType("DropChances", 9)) {
/* 313 */       NBTTagList nbttaglist = nbttagcompound.getList("DropChances", 5);
/*     */       
/* 315 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 316 */         this.dropChances[i] = nbttaglist.e(i);
/*     */       }
/*     */     }
/*     */     
/* 320 */     this.bo = nbttagcompound.getBoolean("Leashed");
/* 321 */     if ((this.bo) && (nbttagcompound.hasKeyOfType("Leash", 10))) {
/* 322 */       this.bq = nbttagcompound.getCompound("Leash");
/*     */     }
/*     */     
/* 325 */     k(nbttagcompound.getBoolean("NoAI"));
/*     */   }
/*     */   
/*     */   public void n(float f) {
/* 329 */     this.ba = f;
/*     */   }
/*     */   
/*     */   public void k(float f) {
/* 333 */     super.k(f);
/* 334 */     n(f);
/*     */   }
/*     */   
/*     */   public void m() {
/* 338 */     super.m();
/* 339 */     this.world.methodProfiler.a("looting");
/* 340 */     if ((!this.world.isClientSide) && (bY()) && (!this.aP) && (this.world.getGameRules().getBoolean("mobGriefing"))) {
/* 341 */       List list = this.world.a(EntityItem.class, getBoundingBox().grow(1.0D, 0.0D, 1.0D));
/* 342 */       Iterator iterator = list.iterator();
/*     */       
/* 344 */       while (iterator.hasNext()) {
/* 345 */         EntityItem entityitem = (EntityItem)iterator.next();
/*     */         
/* 347 */         if ((!entityitem.dead) && (entityitem.getItemStack() != null) && (!entityitem.s())) {
/* 348 */           a(entityitem);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 353 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected void a(EntityItem entityitem) {
/* 357 */     ItemStack itemstack = entityitem.getItemStack();
/* 358 */     int i = c(itemstack);
/*     */     
/* 360 */     if (i > -1) {
/* 361 */       boolean flag = true;
/* 362 */       ItemStack itemstack1 = getEquipment(i);
/*     */       
/* 364 */       if (itemstack1 != null) {
/* 365 */         if (i == 0) {
/* 366 */           if (((itemstack.getItem() instanceof ItemSword)) && (!(itemstack1.getItem() instanceof ItemSword))) {
/* 367 */             flag = true;
/* 368 */           } else if (((itemstack.getItem() instanceof ItemSword)) && ((itemstack1.getItem() instanceof ItemSword))) {
/* 369 */             ItemSword itemsword = (ItemSword)itemstack.getItem();
/* 370 */             ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*     */             
/* 372 */             if (itemsword.g() == itemsword1.g()) {
/* 373 */               flag = (itemstack.getData() > itemstack1.getData()) || ((itemstack.hasTag()) && (!itemstack1.hasTag()));
/*     */             } else {
/* 375 */               flag = itemsword.g() > itemsword1.g();
/*     */             }
/* 377 */           } else if (((itemstack.getItem() instanceof ItemBow)) && ((itemstack1.getItem() instanceof ItemBow))) {
/* 378 */             flag = (itemstack.hasTag()) && (!itemstack1.hasTag());
/*     */           } else {
/* 380 */             flag = false;
/*     */           }
/* 382 */         } else if (((itemstack.getItem() instanceof ItemArmor)) && (!(itemstack1.getItem() instanceof ItemArmor))) {
/* 383 */           flag = true;
/* 384 */         } else if (((itemstack.getItem() instanceof ItemArmor)) && ((itemstack1.getItem() instanceof ItemArmor))) {
/* 385 */           ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/* 386 */           ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*     */           
/* 388 */           if (itemarmor.c == itemarmor1.c) {
/* 389 */             flag = (itemstack.getData() > itemstack1.getData()) || ((itemstack.hasTag()) && (!itemstack1.hasTag()));
/*     */           } else {
/* 391 */             flag = itemarmor.c > itemarmor1.c;
/*     */           }
/*     */         } else {
/* 394 */           flag = false;
/*     */         }
/*     */       }
/*     */       
/* 398 */       if ((flag) && (a(itemstack))) {
/* 399 */         if ((itemstack1 != null) && (this.random.nextFloat() - 0.1F < this.dropChances[i])) {
/* 400 */           a(itemstack1, 0.0F);
/*     */         }
/*     */         
/* 403 */         if ((itemstack.getItem() == Items.DIAMOND) && (entityitem.n() != null)) {
/* 404 */           EntityHuman entityhuman = this.world.a(entityitem.n());
/*     */           
/* 406 */           if (entityhuman != null) {
/* 407 */             entityhuman.b(AchievementList.x);
/*     */           }
/*     */         }
/*     */         
/* 411 */         setEquipment(i, itemstack);
/* 412 */         this.dropChances[i] = 2.0F;
/* 413 */         this.persistent = true;
/* 414 */         receive(entityitem, 1);
/* 415 */         entityitem.die();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean a(ItemStack itemstack)
/*     */   {
/* 422 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 426 */     return true;
/*     */   }
/*     */   
/*     */   protected void D() {
/* 430 */     if (this.persistent) {
/* 431 */       this.ticksFarFromPlayer = 0;
/*     */     } else {
/* 433 */       EntityHuman entityhuman = this.world.findNearbyPlayer(this, -1.0D);
/*     */       
/* 435 */       if (entityhuman != null) {
/* 436 */         double d0 = entityhuman.locX - this.locX;
/* 437 */         double d1 = entityhuman.locY - this.locY;
/* 438 */         double d2 = entityhuman.locZ - this.locZ;
/* 439 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 441 */         if (d3 > 16384.0D) {
/* 442 */           die();
/*     */         }
/*     */         
/* 445 */         if ((this.ticksFarFromPlayer > 600) && (this.random.nextInt(800) == 0) && (d3 > 1024.0D)) {
/* 446 */           die();
/* 447 */         } else if (d3 < 1024.0D) {
/* 448 */           this.ticksFarFromPlayer = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void doTick()
/*     */   {
/* 456 */     this.ticksFarFromPlayer += 1;
/* 457 */     this.world.methodProfiler.a("checkDespawn");
/* 458 */     D();
/* 459 */     this.world.methodProfiler.b();
/*     */     
/* 461 */     if (this.fromMobSpawner)
/*     */     {
/* 463 */       return;
/*     */     }
/*     */     
/* 466 */     this.world.methodProfiler.a("sensing");
/* 467 */     this.bk.a();
/* 468 */     this.world.methodProfiler.b();
/* 469 */     this.world.methodProfiler.a("targetSelector");
/* 470 */     this.targetSelector.a();
/* 471 */     this.world.methodProfiler.b();
/* 472 */     this.world.methodProfiler.a("goalSelector");
/* 473 */     this.goalSelector.a();
/* 474 */     this.world.methodProfiler.b();
/* 475 */     this.world.methodProfiler.a("navigation");
/* 476 */     this.navigation.k();
/* 477 */     this.world.methodProfiler.b();
/* 478 */     this.world.methodProfiler.a("mob tick");
/* 479 */     E();
/* 480 */     this.world.methodProfiler.b();
/* 481 */     this.world.methodProfiler.a("controls");
/* 482 */     this.world.methodProfiler.a("move");
/* 483 */     this.moveController.c();
/* 484 */     this.world.methodProfiler.c("look");
/* 485 */     this.lookController.a();
/* 486 */     this.world.methodProfiler.c("jump");
/* 487 */     this.g.b();
/* 488 */     this.world.methodProfiler.b();
/* 489 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected void E() {}
/*     */   
/*     */   public int bQ() {
/* 495 */     return 40;
/*     */   }
/*     */   
/*     */   public void a(Entity entity, float f, float f1) {
/* 499 */     double d0 = entity.locX - this.locX;
/* 500 */     double d1 = entity.locZ - this.locZ;
/*     */     double d2;
/*     */     double d2;
/* 503 */     if ((entity instanceof EntityLiving)) {
/* 504 */       EntityLiving entityliving = (EntityLiving)entity;
/*     */       
/* 506 */       d2 = entityliving.locY + entityliving.getHeadHeight() - (this.locY + getHeadHeight());
/*     */     } else {
/* 508 */       d2 = (entity.getBoundingBox().b + entity.getBoundingBox().e) / 2.0D - (this.locY + getHeadHeight());
/*     */     }
/*     */     
/* 511 */     double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/* 512 */     float f2 = (float)(MathHelper.b(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 513 */     float f3 = (float)-(MathHelper.b(d2, d3) * 180.0D / 3.1415927410125732D);
/*     */     
/* 515 */     this.pitch = b(this.pitch, f3, f1);
/* 516 */     this.yaw = b(this.yaw, f2, f);
/*     */   }
/*     */   
/*     */   private float b(float f, float f1, float f2) {
/* 520 */     float f3 = MathHelper.g(f1 - f);
/*     */     
/* 522 */     if (f3 > f2) {
/* 523 */       f3 = f2;
/*     */     }
/*     */     
/* 526 */     if (f3 < -f2) {
/* 527 */       f3 = -f2;
/*     */     }
/*     */     
/* 530 */     return f + f3;
/*     */   }
/*     */   
/*     */   public boolean bR() {
/* 534 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 538 */     return (this.world.a(getBoundingBox(), this)) && (this.world.getCubes(this, getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(getBoundingBox()));
/*     */   }
/*     */   
/*     */   public int bV() {
/* 542 */     return 4;
/*     */   }
/*     */   
/*     */   public int aE() {
/* 546 */     if (getGoalTarget() == null) {
/* 547 */       return 3;
/*     */     }
/* 549 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/*     */     
/* 551 */     i -= (3 - this.world.getDifficulty().a()) * 4;
/* 552 */     if (i < 0) {
/* 553 */       i = 0;
/*     */     }
/*     */     
/* 556 */     return i + 3;
/*     */   }
/*     */   
/*     */   public ItemStack bA()
/*     */   {
/* 561 */     return this.equipment[0];
/*     */   }
/*     */   
/*     */   public ItemStack getEquipment(int i) {
/* 565 */     return this.equipment[i];
/*     */   }
/*     */   
/*     */   public ItemStack q(int i) {
/* 569 */     return this.equipment[(i + 1)];
/*     */   }
/*     */   
/*     */   public void setEquipment(int i, ItemStack itemstack) {
/* 573 */     this.equipment[i] = itemstack;
/*     */   }
/*     */   
/*     */   public ItemStack[] getEquipment() {
/* 577 */     return this.equipment;
/*     */   }
/*     */   
/*     */   protected void dropEquipment(boolean flag, int i) {
/* 581 */     for (int j = 0; j < getEquipment().length; j++) {
/* 582 */       ItemStack itemstack = getEquipment(j);
/* 583 */       boolean flag1 = this.dropChances[j] > 1.0F;
/*     */       
/* 585 */       if ((itemstack != null) && ((flag) || (flag1)) && (this.random.nextFloat() - i * 0.01F < this.dropChances[j])) {
/* 586 */         if ((!flag1) && (itemstack.e())) {
/* 587 */           int k = Math.max(itemstack.j() - 25, 1);
/* 588 */           int l = itemstack.j() - this.random.nextInt(this.random.nextInt(k) + 1);
/*     */           
/* 590 */           if (l > k) {
/* 591 */             l = k;
/*     */           }
/*     */           
/* 594 */           if (l < 1) {
/* 595 */             l = 1;
/*     */           }
/*     */           
/* 598 */           itemstack.setData(l);
/*     */         }
/*     */         
/* 601 */         a(itemstack, 0.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(DifficultyDamageScaler difficultydamagescaler)
/*     */   {
/* 608 */     if (this.random.nextFloat() < 0.15F * difficultydamagescaler.c()) {
/* 609 */       int i = this.random.nextInt(2);
/* 610 */       float f = this.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;
/*     */       
/* 612 */       if (this.random.nextFloat() < 0.095F) {
/* 613 */         i++;
/*     */       }
/*     */       
/* 616 */       if (this.random.nextFloat() < 0.095F) {
/* 617 */         i++;
/*     */       }
/*     */       
/* 620 */       if (this.random.nextFloat() < 0.095F) {
/* 621 */         i++;
/*     */       }
/*     */       
/* 624 */       for (int j = 3; j >= 0; j--) {
/* 625 */         ItemStack itemstack = q(j);
/*     */         
/* 627 */         if ((j < 3) && (this.random.nextFloat() < f)) {
/*     */           break;
/*     */         }
/*     */         
/* 631 */         if (itemstack == null) {
/* 632 */           Item item = a(j + 1, i);
/*     */           
/* 634 */           if (item != null) {
/* 635 */             setEquipment(j + 1, new ItemStack(item));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int c(ItemStack itemstack)
/*     */   {
/* 644 */     if ((itemstack.getItem() != Item.getItemOf(Blocks.PUMPKIN)) && (itemstack.getItem() != Items.SKULL)) {
/* 645 */       if ((itemstack.getItem() instanceof ItemArmor)) {
/* 646 */         switch (((ItemArmor)itemstack.getItem()).b) {
/*     */         case 0: 
/* 648 */           return 4;
/*     */         
/*     */         case 1: 
/* 651 */           return 3;
/*     */         
/*     */         case 2: 
/* 654 */           return 2;
/*     */         
/*     */         case 3: 
/* 657 */           return 1;
/*     */         }
/*     */         
/*     */       }
/* 661 */       return 0;
/*     */     }
/* 663 */     return 4;
/*     */   }
/*     */   
/*     */   public static Item a(int i, int j)
/*     */   {
/* 668 */     switch (i) {
/*     */     case 4: 
/* 670 */       if (j == 0)
/* 671 */         return Items.LEATHER_HELMET;
/* 672 */       if (j == 1)
/* 673 */         return Items.GOLDEN_HELMET;
/* 674 */       if (j == 2)
/* 675 */         return Items.CHAINMAIL_HELMET;
/* 676 */       if (j == 3)
/* 677 */         return Items.IRON_HELMET;
/* 678 */       if (j == 4) {
/* 679 */         return Items.DIAMOND_HELMET;
/*     */       }
/*     */     
/*     */     case 3: 
/* 683 */       if (j == 0)
/* 684 */         return Items.LEATHER_CHESTPLATE;
/* 685 */       if (j == 1)
/* 686 */         return Items.GOLDEN_CHESTPLATE;
/* 687 */       if (j == 2)
/* 688 */         return Items.CHAINMAIL_CHESTPLATE;
/* 689 */       if (j == 3)
/* 690 */         return Items.IRON_CHESTPLATE;
/* 691 */       if (j == 4) {
/* 692 */         return Items.DIAMOND_CHESTPLATE;
/*     */       }
/*     */     
/*     */     case 2: 
/* 696 */       if (j == 0)
/* 697 */         return Items.LEATHER_LEGGINGS;
/* 698 */       if (j == 1)
/* 699 */         return Items.GOLDEN_LEGGINGS;
/* 700 */       if (j == 2)
/* 701 */         return Items.CHAINMAIL_LEGGINGS;
/* 702 */       if (j == 3)
/* 703 */         return Items.IRON_LEGGINGS;
/* 704 */       if (j == 4) {
/* 705 */         return Items.DIAMOND_LEGGINGS;
/*     */       }
/*     */     
/*     */     case 1: 
/* 709 */       if (j == 0)
/* 710 */         return Items.LEATHER_BOOTS;
/* 711 */       if (j == 1)
/* 712 */         return Items.GOLDEN_BOOTS;
/* 713 */       if (j == 2)
/* 714 */         return Items.CHAINMAIL_BOOTS;
/* 715 */       if (j == 3)
/* 716 */         return Items.IRON_BOOTS;
/* 717 */       if (j == 4) {
/* 718 */         return Items.DIAMOND_BOOTS;
/*     */       }
/*     */       break;
/*     */     }
/* 722 */     return null;
/*     */   }
/*     */   
/*     */   protected void b(DifficultyDamageScaler difficultydamagescaler)
/*     */   {
/* 727 */     float f = difficultydamagescaler.c();
/*     */     
/* 729 */     if ((bA() != null) && (this.random.nextFloat() < 0.25F * f)) {
/* 730 */       EnchantmentManager.a(this.random, bA(), (int)(5.0F + f * this.random.nextInt(18)));
/*     */     }
/*     */     
/* 733 */     for (int i = 0; i < 4; i++) {
/* 734 */       ItemStack itemstack = q(i);
/*     */       
/* 736 */       if ((itemstack != null) && (this.random.nextFloat() < 0.5F * f)) {
/* 737 */         EnchantmentManager.a(this.random, itemstack, (int)(5.0F + f * this.random.nextInt(18)));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity)
/*     */   {
/* 744 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).b(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, 1));
/* 745 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public boolean bW() {
/* 749 */     return false;
/*     */   }
/*     */   
/*     */   public void bX() {
/* 753 */     this.persistent = true;
/*     */   }
/*     */   
/*     */   public void a(int i, float f) {
/* 757 */     this.dropChances[i] = f;
/*     */   }
/*     */   
/*     */   public boolean bY() {
/* 761 */     return this.canPickUpLoot;
/*     */   }
/*     */   
/*     */   public void j(boolean flag) {
/* 765 */     this.canPickUpLoot = flag;
/*     */   }
/*     */   
/*     */   public boolean isPersistent() {
/* 769 */     return this.persistent;
/*     */   }
/*     */   
/*     */   public final boolean e(EntityHuman entityhuman) {
/* 773 */     if ((cc()) && (getLeashHolder() == entityhuman))
/*     */     {
/* 775 */       if (CraftEventFactory.callPlayerUnleashEntityEvent(this, entityhuman).isCancelled()) {
/* 776 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 777 */         return false;
/*     */       }
/*     */       
/* 780 */       unleash(true, !entityhuman.abilities.canInstantlyBuild);
/* 781 */       return true;
/*     */     }
/* 783 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 785 */     if ((itemstack != null) && (itemstack.getItem() == Items.LEAD) && (cb())) {
/* 786 */       if ((!(this instanceof EntityTameableAnimal)) || (!((EntityTameableAnimal)this).isTamed()))
/*     */       {
/* 788 */         if (CraftEventFactory.callPlayerLeashEntityEvent(this, entityhuman, entityhuman).isCancelled()) {
/* 789 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 790 */           return false;
/*     */         }
/*     */         
/* 793 */         setLeashHolder(entityhuman, true);
/* 794 */         itemstack.count -= 1;
/* 795 */         return true;
/*     */       }
/*     */       
/* 798 */       if (((EntityTameableAnimal)this).e(entityhuman))
/*     */       {
/* 800 */         if (CraftEventFactory.callPlayerLeashEntityEvent(this, entityhuman, entityhuman).isCancelled()) {
/* 801 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 802 */           return false;
/*     */         }
/*     */         
/* 805 */         setLeashHolder(entityhuman, true);
/* 806 */         itemstack.count -= 1;
/* 807 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 811 */     return a(entityhuman) ? true : super.e(entityhuman);
/*     */   }
/*     */   
/*     */   protected boolean a(EntityHuman entityhuman)
/*     */   {
/* 816 */     return false;
/*     */   }
/*     */   
/*     */   protected void ca() {
/* 820 */     if (this.bq != null) {
/* 821 */       n();
/*     */     }
/*     */     
/* 824 */     if (this.bo) {
/* 825 */       if (!isAlive()) {
/* 826 */         this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.PLAYER_UNLEASH));
/* 827 */         unleash(true, true);
/*     */       }
/*     */       
/* 830 */       if ((this.bp == null) || (this.bp.dead)) {
/* 831 */         this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.HOLDER_GONE));
/* 832 */         unleash(true, true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void unleash(boolean flag, boolean flag1) {
/* 838 */     if (this.bo) {
/* 839 */       this.bo = false;
/* 840 */       this.bp = null;
/* 841 */       if ((!this.world.isClientSide) && (flag1)) {
/* 842 */         a(Items.LEAD, 1);
/*     */       }
/*     */       
/* 845 */       if ((!this.world.isClientSide) && (flag) && ((this.world instanceof WorldServer))) {
/* 846 */         ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAttachEntity(1, this, null));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean cb()
/*     */   {
/* 853 */     return (!cc()) && (!(this instanceof IMonster));
/*     */   }
/*     */   
/*     */   public boolean cc() {
/* 857 */     return this.bo;
/*     */   }
/*     */   
/*     */   public Entity getLeashHolder() {
/* 861 */     return this.bp;
/*     */   }
/*     */   
/*     */   public void setLeashHolder(Entity entity, boolean flag) {
/* 865 */     this.bo = true;
/* 866 */     this.bp = entity;
/* 867 */     if ((!this.world.isClientSide) && (flag) && ((this.world instanceof WorldServer))) {
/* 868 */       ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAttachEntity(1, this, this.bp));
/*     */     }
/*     */   }
/*     */   
/*     */   private void n()
/*     */   {
/* 874 */     if ((this.bo) && (this.bq != null)) {
/* 875 */       if ((this.bq.hasKeyOfType("UUIDMost", 4)) && (this.bq.hasKeyOfType("UUIDLeast", 4))) {
/* 876 */         UUID uuid = new UUID(this.bq.getLong("UUIDMost"), this.bq.getLong("UUIDLeast"));
/* 877 */         List list = this.world.a(EntityLiving.class, getBoundingBox().grow(10.0D, 10.0D, 10.0D));
/* 878 */         Iterator iterator = list.iterator();
/*     */         
/* 880 */         while (iterator.hasNext()) {
/* 881 */           EntityLiving entityliving = (EntityLiving)iterator.next();
/*     */           
/* 883 */           if (entityliving.getUniqueID().equals(uuid)) {
/* 884 */             this.bp = entityliving;
/* 885 */             break;
/*     */           }
/*     */         }
/* 888 */       } else if ((this.bq.hasKeyOfType("X", 99)) && (this.bq.hasKeyOfType("Y", 99)) && (this.bq.hasKeyOfType("Z", 99))) {
/* 889 */         BlockPosition blockposition = new BlockPosition(this.bq.getInt("X"), this.bq.getInt("Y"), this.bq.getInt("Z"));
/* 890 */         EntityLeash entityleash = EntityLeash.b(this.world, blockposition);
/*     */         
/* 892 */         if (entityleash == null) {
/* 893 */           entityleash = EntityLeash.a(this.world, blockposition);
/*     */         }
/*     */         
/* 896 */         this.bp = entityleash;
/*     */       } else {
/* 898 */         this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.UNKNOWN));
/* 899 */         unleash(false, true);
/*     */       }
/*     */     }
/*     */     
/* 903 */     this.bq = null;
/*     */   }
/*     */   
/*     */   public boolean d(int i, ItemStack itemstack) {
/*     */     int j;
/*     */     int j;
/* 909 */     if (i == 99) {
/* 910 */       j = 0;
/*     */     } else {
/* 912 */       j = i - 100 + 1;
/* 913 */       if ((j < 0) || (j >= this.equipment.length)) {
/* 914 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 918 */     if ((itemstack != null) && (c(itemstack) != j) && ((j != 4) || (!(itemstack.getItem() instanceof ItemBlock)))) {
/* 919 */       return false;
/*     */     }
/* 921 */     setEquipment(j, itemstack);
/* 922 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bM()
/*     */   {
/* 927 */     return (super.bM()) && (!ce());
/*     */   }
/*     */   
/*     */   public void k(boolean flag) {
/* 931 */     this.datawatcher.watch(15, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public boolean ce() {
/* 935 */     return this.datawatcher.getByte(15) != 0;
/*     */   }
/*     */   
/*     */   public static enum EnumEntityPositionType
/*     */   {
/* 940 */     ON_GROUND,  IN_AIR,  IN_WATER;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityInsentient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */