/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class EntityZombie extends EntityMonster
/*     */ {
/*  17 */   protected static final IAttribute a = new AttributeRanged(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).a("Spawn Reinforcements Chance");
/*  18 */   private static final UUID b = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  19 */   private static final AttributeModifier c = new AttributeModifier(b, "Baby speed boost", 0.5D, 1);
/*  20 */   private final PathfinderGoalBreakDoor bm = new PathfinderGoalBreakDoor(this);
/*     */   private int bn;
/*  22 */   private boolean bo = false;
/*  23 */   private float bp = -1.0F;
/*     */   private float bq;
/*  25 */   private int lastTick = MinecraftServer.currentTick;
/*     */   
/*     */   public EntityZombie(World world) {
/*  28 */     super(world);
/*  29 */     ((Navigation)getNavigation()).b(true);
/*  30 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  31 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
/*  32 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  33 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  34 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  35 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  36 */     n();
/*  37 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */   
/*     */   protected void n() {
/*  41 */     if (this.world.spigotConfig.zombieAggressiveTowardsVillager) this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
/*  42 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1.0D, true));
/*  43 */     this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
/*  44 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
/*  45 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*  46 */     if (this.world.spigotConfig.zombieAggressiveTowardsVillager) this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, false));
/*  47 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  51 */     super.initAttributes();
/*  52 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(35.0D);
/*  53 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
/*  54 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(3.0D);
/*  55 */     getAttributeMap().b(a).setValue(this.random.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  59 */     super.h();
/*  60 */     getDataWatcher().a(12, Byte.valueOf((byte)0));
/*  61 */     getDataWatcher().a(13, Byte.valueOf((byte)0));
/*  62 */     getDataWatcher().a(14, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int br() {
/*  66 */     int i = super.br() + 2;
/*     */     
/*  68 */     if (i > 20) {
/*  69 */       i = 20;
/*     */     }
/*     */     
/*  72 */     return i;
/*     */   }
/*     */   
/*     */   public boolean cn() {
/*  76 */     return this.bo;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/*  80 */     if (this.bo != flag) {
/*  81 */       this.bo = flag;
/*  82 */       if (flag) {
/*  83 */         this.goalSelector.a(1, this.bm);
/*     */       } else {
/*  85 */         this.goalSelector.a(this.bm);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isBaby()
/*     */   {
/*  92 */     return getDataWatcher().getByte(12) == 1;
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/*  96 */     if (isBaby()) {
/*  97 */       this.b_ = ((int)(this.b_ * 2.5F));
/*     */     }
/*     */     
/* 100 */     return super.getExpValue(entityhuman);
/*     */   }
/*     */   
/*     */   public void setBaby(boolean flag) {
/* 104 */     getDataWatcher().watch(12, Byte.valueOf((byte)(flag ? 1 : 0)));
/* 105 */     if ((this.world != null) && (!this.world.isClientSide)) {
/* 106 */       AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*     */       
/* 108 */       attributeinstance.c(c);
/* 109 */       if (flag) {
/* 110 */         attributeinstance.b(c);
/*     */       }
/*     */     }
/*     */     
/* 114 */     n(flag);
/*     */   }
/*     */   
/*     */   public boolean isVillager() {
/* 118 */     return getDataWatcher().getByte(13) == 1;
/*     */   }
/*     */   
/*     */   public void setVillager(boolean flag) {
/* 122 */     getDataWatcher().watch(13, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public void m() {
/* 126 */     if ((this.world.w()) && (!this.world.isClientSide) && (!isBaby())) {
/* 127 */       float f = c(1.0F);
/* 128 */       BlockPosition blockposition = new BlockPosition(this.locX, Math.round(this.locY), this.locZ);
/*     */       
/* 130 */       if ((f > 0.5F) && (this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) && (this.world.i(blockposition))) {
/* 131 */         boolean flag = true;
/* 132 */         ItemStack itemstack = getEquipment(4);
/*     */         
/* 134 */         if (itemstack != null) {
/* 135 */           if (itemstack.e()) {
/* 136 */             itemstack.setData(itemstack.h() + this.random.nextInt(2));
/* 137 */             if (itemstack.h() >= itemstack.j()) {
/* 138 */               b(itemstack);
/* 139 */               setEquipment(4, null);
/*     */             }
/*     */           }
/*     */           
/* 143 */           flag = false;
/*     */         }
/*     */         
/* 146 */         if (flag)
/*     */         {
/* 148 */           EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity(), 8);
/* 149 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 151 */           if (!event.isCancelled()) {
/* 152 */             setOnFire(event.getDuration());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 159 */     if ((au()) && (getGoalTarget() != null) && ((this.vehicle instanceof EntityChicken))) {
/* 160 */       ((EntityInsentient)this.vehicle).getNavigation().a(getNavigation().j(), 1.5D);
/*     */     }
/*     */     
/* 163 */     super.m();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 167 */     if (super.damageEntity(damagesource, f)) {
/* 168 */       EntityLiving entityliving = getGoalTarget();
/*     */       
/* 170 */       if ((entityliving == null) && ((damagesource.getEntity() instanceof EntityLiving))) {
/* 171 */         entityliving = (EntityLiving)damagesource.getEntity();
/*     */       }
/*     */       
/* 174 */       if ((entityliving != null) && (this.world.getDifficulty() == EnumDifficulty.HARD) && (this.random.nextFloat() < getAttributeInstance(a).getValue())) {
/* 175 */         int i = MathHelper.floor(this.locX);
/* 176 */         int j = MathHelper.floor(this.locY);
/* 177 */         int k = MathHelper.floor(this.locZ);
/* 178 */         EntityZombie entityzombie = new EntityZombie(this.world);
/*     */         
/* 180 */         for (int l = 0; l < 50; l++) {
/* 181 */           int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/* 182 */           int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/* 183 */           int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/*     */           
/* 185 */           if ((World.a(this.world, new BlockPosition(i1, j1 - 1, k1))) && (this.world.getLightLevel(new BlockPosition(i1, j1, k1)) < 10)) {
/* 186 */             entityzombie.setPosition(i1, j1, k1);
/* 187 */             if ((!this.world.isPlayerNearby(i1, j1, k1, 7.0D)) && (this.world.a(entityzombie.getBoundingBox(), entityzombie)) && (this.world.getCubes(entityzombie, entityzombie.getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(entityzombie.getBoundingBox()))) {
/* 188 */               this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.REINFORCEMENTS);
/* 189 */               entityzombie.setGoalTarget(entityliving, EntityTargetEvent.TargetReason.REINFORCEMENT_TARGET, true);
/* 190 */               entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), null);
/* 191 */               getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
/* 192 */               entityzombie.getAttributeInstance(a).b(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
/* 193 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 199 */       return true;
/*     */     }
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 206 */     if ((!this.world.isClientSide) && (cp())) {
/* 207 */       int i = cr();
/*     */       
/*     */ 
/* 210 */       int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 211 */       this.lastTick = MinecraftServer.currentTick;
/* 212 */       i *= elapsedTicks;
/*     */       
/*     */ 
/* 215 */       this.bn -= i;
/* 216 */       if (this.bn <= 0) {
/* 217 */         cq();
/*     */       }
/*     */     }
/*     */     
/* 221 */     super.t_();
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/* 225 */     boolean flag = super.r(entity);
/*     */     
/* 227 */     if (flag) {
/* 228 */       int i = this.world.getDifficulty().a();
/*     */       
/* 230 */       if ((bA() == null) && (isBurning()) && (this.random.nextFloat() < i * 0.3F))
/*     */       {
/* 232 */         EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 2 * i);
/* 233 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 235 */         if (!event.isCancelled()) {
/* 236 */           entity.setOnFire(event.getDuration());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 242 */     return flag;
/*     */   }
/*     */   
/*     */   protected String z() {
/* 246 */     return "mob.zombie.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 250 */     return "mob.zombie.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 254 */     return "mob.zombie.death";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/* 258 */     makeSound("mob.zombie.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 262 */     return Items.ROTTEN_FLESH;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/* 266 */     return EnumMonsterType.UNDEAD;
/*     */   }
/*     */   
/*     */   protected void getRareDrop() {
/* 270 */     switch (this.random.nextInt(3)) {
/*     */     case 0: 
/* 272 */       a(Items.IRON_INGOT, 1);
/* 273 */       break;
/*     */     
/*     */     case 1: 
/* 276 */       a(Items.CARROT, 1);
/* 277 */       break;
/*     */     
/*     */     case 2: 
/* 280 */       a(Items.POTATO, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(DifficultyDamageScaler difficultydamagescaler)
/*     */   {
/* 286 */     super.a(difficultydamagescaler);
/* 287 */     if (this.random.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
/* 288 */       int i = this.random.nextInt(3);
/*     */       
/* 290 */       if (i == 0) {
/* 291 */         setEquipment(0, new ItemStack(Items.IRON_SWORD));
/*     */       } else {
/* 293 */         setEquipment(0, new ItemStack(Items.IRON_SHOVEL));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 300 */     super.b(nbttagcompound);
/* 301 */     if (isBaby()) {
/* 302 */       nbttagcompound.setBoolean("IsBaby", true);
/*     */     }
/*     */     
/* 305 */     if (isVillager()) {
/* 306 */       nbttagcompound.setBoolean("IsVillager", true);
/*     */     }
/*     */     
/* 309 */     nbttagcompound.setInt("ConversionTime", cp() ? this.bn : -1);
/* 310 */     nbttagcompound.setBoolean("CanBreakDoors", cn());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 314 */     super.a(nbttagcompound);
/* 315 */     if (nbttagcompound.getBoolean("IsBaby")) {
/* 316 */       setBaby(true);
/*     */     }
/*     */     
/* 319 */     if (nbttagcompound.getBoolean("IsVillager")) {
/* 320 */       setVillager(true);
/*     */     }
/*     */     
/* 323 */     if ((nbttagcompound.hasKeyOfType("ConversionTime", 99)) && (nbttagcompound.getInt("ConversionTime") > -1)) {
/* 324 */       a(nbttagcompound.getInt("ConversionTime"));
/*     */     }
/*     */     
/* 327 */     a(nbttagcompound.getBoolean("CanBreakDoors"));
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving) {
/* 331 */     super.a(entityliving);
/* 332 */     if (((this.world.getDifficulty() == EnumDifficulty.NORMAL) || (this.world.getDifficulty() == EnumDifficulty.HARD)) && ((entityliving instanceof EntityVillager))) {
/* 333 */       if ((this.world.getDifficulty() != EnumDifficulty.HARD) && (this.random.nextBoolean())) {
/* 334 */         return;
/*     */       }
/*     */       
/* 337 */       EntityInsentient entityinsentient = (EntityInsentient)entityliving;
/* 338 */       EntityZombie entityzombie = new EntityZombie(this.world);
/*     */       
/* 340 */       entityzombie.m(entityliving);
/* 341 */       this.world.kill(entityliving);
/* 342 */       entityzombie.prepare(this.world.E(new BlockPosition(entityzombie)), null);
/* 343 */       entityzombie.setVillager(true);
/* 344 */       if (entityliving.isBaby()) {
/* 345 */         entityzombie.setBaby(true);
/*     */       }
/*     */       
/* 348 */       entityzombie.k(entityinsentient.ce());
/* 349 */       if (entityinsentient.hasCustomName()) {
/* 350 */         entityzombie.setCustomName(entityinsentient.getCustomName());
/* 351 */         entityzombie.setCustomNameVisible(entityinsentient.getCustomNameVisible());
/*     */       }
/*     */       
/* 354 */       this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.INFECTION);
/* 355 */       this.world.a(null, 1016, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 361 */     float f = 1.74F;
/*     */     
/* 363 */     if (isBaby()) {
/* 364 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 367 */     return f;
/*     */   }
/*     */   
/*     */   protected boolean a(ItemStack itemstack) {
/* 371 */     return (itemstack.getItem() == Items.EGG) && (isBaby()) && (au()) ? false : super.a(itemstack);
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 375 */     Object object = super.prepare(difficultydamagescaler, groupdataentity);
/* 376 */     float f = difficultydamagescaler.c();
/*     */     
/* 378 */     j(this.random.nextFloat() < 0.55F * f);
/* 379 */     if (object == null) {
/* 380 */       object = new GroupDataZombie(this.world.random.nextFloat() < 0.05F, this.world.random.nextFloat() < 0.05F, null);
/*     */     }
/*     */     
/* 383 */     if ((object instanceof GroupDataZombie)) {
/* 384 */       GroupDataZombie entityzombie_groupdatazombie = (GroupDataZombie)object;
/*     */       
/* 386 */       if (entityzombie_groupdatazombie.b) {
/* 387 */         setVillager(true);
/*     */       }
/*     */       
/* 390 */       if (entityzombie_groupdatazombie.a) {
/* 391 */         setBaby(true);
/* 392 */         if (this.world.random.nextFloat() < 0.05D) {
/* 393 */           List list = this.world.a(EntityChicken.class, getBoundingBox().grow(5.0D, 3.0D, 5.0D), IEntitySelector.b);
/*     */           
/* 395 */           if (!list.isEmpty()) {
/* 396 */             EntityChicken entitychicken = (EntityChicken)list.get(0);
/*     */             
/* 398 */             entitychicken.l(true);
/* 399 */             mount(entitychicken);
/*     */           }
/* 401 */         } else if (this.world.random.nextFloat() < 0.05D) {
/* 402 */           EntityChicken entitychicken1 = new EntityChicken(this.world);
/*     */           
/* 404 */           entitychicken1.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 405 */           entitychicken1.prepare(difficultydamagescaler, null);
/* 406 */           entitychicken1.l(true);
/* 407 */           this.world.addEntity(entitychicken1, CreatureSpawnEvent.SpawnReason.MOUNT);
/* 408 */           mount(entitychicken1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 413 */     a(this.random.nextFloat() < f * 0.1F);
/* 414 */     a(difficultydamagescaler);
/* 415 */     b(difficultydamagescaler);
/* 416 */     if (getEquipment(4) == null) {
/* 417 */       Calendar calendar = this.world.Y();
/*     */       
/* 419 */       if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31) && (this.random.nextFloat() < 0.25F)) {
/* 420 */         setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
/* 421 */         this.dropChances[4] = 0.0F;
/*     */       }
/*     */     }
/*     */     
/* 425 */     getAttributeInstance(GenericAttributes.c).b(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806D, 0));
/* 426 */     double d0 = this.random.nextDouble() * 1.5D * f;
/*     */     
/* 428 */     if (d0 > 1.0D) {
/* 429 */       getAttributeInstance(GenericAttributes.FOLLOW_RANGE).b(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
/*     */     }
/*     */     
/* 432 */     if (this.random.nextFloat() < f * 0.05F) {
/* 433 */       getAttributeInstance(a).b(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25D + 0.5D, 0));
/* 434 */       getAttributeInstance(GenericAttributes.maxHealth).b(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0D + 1.0D, 2));
/* 435 */       a(true);
/*     */     }
/*     */     
/* 438 */     return (GroupDataEntity)object;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 442 */     ItemStack itemstack = entityhuman.bZ();
/*     */     
/* 444 */     if ((itemstack != null) && (itemstack.getItem() == Items.GOLDEN_APPLE) && (itemstack.getData() == 0) && (isVillager()) && (hasEffect(MobEffectList.WEAKNESS))) {
/* 445 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 446 */         itemstack.count -= 1;
/*     */       }
/*     */       
/* 449 */       if (itemstack.count <= 0) {
/* 450 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */       }
/*     */       
/* 453 */       if (!this.world.isClientSide) {
/* 454 */         a(this.random.nextInt(2401) + 3600);
/*     */       }
/*     */       
/* 457 */       return true;
/*     */     }
/* 459 */     return false;
/*     */   }
/*     */   
/*     */   protected void a(int i)
/*     */   {
/* 464 */     this.bn = i;
/* 465 */     getDataWatcher().watch(14, Byte.valueOf((byte)1));
/* 466 */     removeEffect(MobEffectList.WEAKNESS.id);
/* 467 */     addEffect(new MobEffect(MobEffectList.INCREASE_DAMAGE.id, i, Math.min(this.world.getDifficulty().a() - 1, 0)));
/* 468 */     this.world.broadcastEntityEffect(this, (byte)16);
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 472 */     return !cp();
/*     */   }
/*     */   
/*     */   public boolean cp() {
/* 476 */     return getDataWatcher().getByte(14) == 1;
/*     */   }
/*     */   
/*     */   protected void cq() {
/* 480 */     EntityVillager entityvillager = new EntityVillager(this.world);
/*     */     
/* 482 */     entityvillager.m(this);
/* 483 */     entityvillager.prepare(this.world.E(new BlockPosition(entityvillager)), null);
/* 484 */     entityvillager.cp();
/* 485 */     if (isBaby()) {
/* 486 */       entityvillager.setAgeRaw(41536);
/*     */     }
/*     */     
/* 489 */     this.world.kill(this);
/* 490 */     entityvillager.k(ce());
/* 491 */     if (hasCustomName()) {
/* 492 */       entityvillager.setCustomName(getCustomName());
/* 493 */       entityvillager.setCustomNameVisible(getCustomNameVisible());
/*     */     }
/*     */     
/* 496 */     this.world.addEntity(entityvillager, CreatureSpawnEvent.SpawnReason.CURED);
/* 497 */     entityvillager.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 200, 0));
/* 498 */     this.world.a(null, 1017, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0);
/*     */   }
/*     */   
/*     */   protected int cr() {
/* 502 */     int i = 1;
/*     */     
/* 504 */     if (this.random.nextFloat() < 0.01F) {
/* 505 */       int j = 0;
/* 506 */       BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */       
/* 508 */       for (int k = (int)this.locX - 4; (k < (int)this.locX + 4) && (j < 14); k++) {
/* 509 */         for (int l = (int)this.locY - 4; (l < (int)this.locY + 4) && (j < 14); l++) {
/* 510 */           for (int i1 = (int)this.locZ - 4; (i1 < (int)this.locZ + 4) && (j < 14); i1++) {
/* 511 */             Block block = this.world.getType(blockposition_mutableblockposition.c(k, l, i1)).getBlock();
/*     */             
/* 513 */             if ((block == Blocks.IRON_BARS) || (block == Blocks.BED)) {
/* 514 */               if (this.random.nextFloat() < 0.3F) {
/* 515 */                 i++;
/*     */               }
/*     */               
/* 518 */               j++;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 525 */     return i;
/*     */   }
/*     */   
/*     */   public void n(boolean flag) {
/* 529 */     a(flag ? 0.5F : 1.0F);
/*     */   }
/*     */   
/*     */   public final void setSize(float f, float f1) {
/* 533 */     boolean flag = (this.bp > 0.0F) && (this.bq > 0.0F);
/*     */     
/* 535 */     this.bp = f;
/* 536 */     this.bq = f1;
/* 537 */     if (!flag) {
/* 538 */       a(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void a(float f)
/*     */   {
/* 544 */     super.setSize(this.bp * f, this.bq * f);
/*     */   }
/*     */   
/*     */   public double am() {
/* 548 */     return isBaby() ? 0.0D : -0.35D;
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource)
/*     */   {
/* 553 */     if (((damagesource.getEntity() instanceof EntityCreeper)) && (!(this instanceof EntityPigZombie)) && (((EntityCreeper)damagesource.getEntity()).isPowered()) && (((EntityCreeper)damagesource.getEntity()).cp())) {
/* 554 */       ((EntityCreeper)damagesource.getEntity()).cq();
/*     */       
/*     */ 
/* 557 */       this.headDrop = new ItemStack(Items.SKULL, 1, 2);
/*     */     }
/*     */     
/*     */ 
/* 561 */     super.die(damagesource);
/*     */   }
/*     */   
/*     */   class GroupDataZombie
/*     */     implements GroupDataEntity
/*     */   {
/*     */     public boolean a;
/*     */     public boolean b;
/*     */     
/*     */     private GroupDataZombie(boolean flag, boolean flag1)
/*     */     {
/* 572 */       this.a = false;
/* 573 */       this.b = false;
/* 574 */       this.a = flag;
/* 575 */       this.b = flag1;
/*     */     }
/*     */     
/*     */     GroupDataZombie(boolean flag, boolean flag1, EntityZombie.SyntheticClass_1 entityzombie_syntheticclass_1) {
/* 579 */       this(flag, flag1);
/*     */     }
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1 {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */