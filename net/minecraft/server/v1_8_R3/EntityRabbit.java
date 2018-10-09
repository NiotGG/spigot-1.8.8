/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityRabbit extends EntityAnimal { private PathfinderGoalRabbitAvoidTarget<EntityWolf> bm;
/*   6 */   private int bo = 0;
/*   7 */   private int bp = 0;
/*   8 */   private boolean bq = false;
/*   9 */   private boolean br = false;
/*  10 */   private int bs = 0;
/*     */   private EnumRabbitState bt;
/*     */   private int bu;
/*     */   private EntityHuman bv;
/*     */   
/*     */   public EntityRabbit(World world) {
/*  16 */     super(world);
/*  17 */     this.bt = EnumRabbitState.HOP;
/*  18 */     this.bu = 0;
/*  19 */     this.bv = null;
/*  20 */     setSize(0.6F, 0.7F);
/*  21 */     this.g = new ControllerJumpRabbit(this);
/*  22 */     this.moveController = new ControllerMoveRabbit(this);
/*  23 */     ((Navigation)getNavigation()).a(true);
/*  24 */     initializePathFinderGoals();
/*  25 */     b(0.0D);
/*     */   }
/*     */   
/*     */   public void initializePathFinderGoals()
/*     */   {
/*  30 */     this.navigation.a(2.5F);
/*  31 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  32 */     this.goalSelector.a(1, new PathfinderGoalRabbitPanic(this, 1.33D));
/*  33 */     this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Items.CARROT, false));
/*  34 */     this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Items.GOLDEN_CARROT, false));
/*  35 */     this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Item.getItemOf(Blocks.YELLOW_FLOWER), false));
/*  36 */     this.goalSelector.a(3, new PathfinderGoalBreed(this, 0.8D));
/*  37 */     this.goalSelector.a(5, new PathfinderGoalEatCarrots(this));
/*  38 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.6D));
/*  39 */     this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
/*  40 */     this.bm = new PathfinderGoalRabbitAvoidTarget(this, EntityWolf.class, 16.0F, 1.33D, 1.33D);
/*  41 */     this.goalSelector.a(4, this.bm);
/*     */   }
/*     */   
/*     */   protected float bE()
/*     */   {
/*  46 */     return (this.moveController.a()) && (this.moveController.e() > this.locY + 0.5D) ? 0.5F : this.bt.b();
/*     */   }
/*     */   
/*     */   public void a(EnumRabbitState entityrabbit_enumrabbitstate) {
/*  50 */     this.bt = entityrabbit_enumrabbitstate;
/*     */   }
/*     */   
/*     */   public void b(double d0) {
/*  54 */     getNavigation().a(d0);
/*  55 */     this.moveController.a(this.moveController.d(), this.moveController.e(), this.moveController.f(), d0);
/*     */   }
/*     */   
/*     */   public void a(boolean flag, EnumRabbitState entityrabbit_enumrabbitstate) {
/*  59 */     super.i(flag);
/*  60 */     if (!flag) {
/*  61 */       if (this.bt == EnumRabbitState.ATTACK) {
/*  62 */         this.bt = EnumRabbitState.HOP;
/*     */       }
/*     */     } else {
/*  65 */       b(1.5D * entityrabbit_enumrabbitstate.a());
/*  66 */       makeSound(cm(), bB(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */     }
/*     */     
/*  69 */     this.bq = flag;
/*     */   }
/*     */   
/*     */   public void b(EnumRabbitState entityrabbit_enumrabbitstate) {
/*  73 */     a(true, entityrabbit_enumrabbitstate);
/*  74 */     this.bp = entityrabbit_enumrabbitstate.d();
/*  75 */     this.bo = 0;
/*     */   }
/*     */   
/*     */   public boolean cl() {
/*  79 */     return this.bq;
/*     */   }
/*     */   
/*     */   protected void h() {
/*  83 */     super.h();
/*  84 */     this.datawatcher.a(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void E() {
/*  88 */     if (this.moveController.b() > 0.8D) {
/*  89 */       a(EnumRabbitState.SPRINT);
/*  90 */     } else if (this.bt != EnumRabbitState.ATTACK) {
/*  91 */       a(EnumRabbitState.HOP);
/*     */     }
/*     */     
/*  94 */     if (this.bs > 0) {
/*  95 */       this.bs -= 1;
/*     */     }
/*     */     
/*  98 */     if (this.bu > 0) {
/*  99 */       this.bu -= this.random.nextInt(3);
/* 100 */       if (this.bu < 0) {
/* 101 */         this.bu = 0;
/*     */       }
/*     */     }
/*     */     
/* 105 */     if (this.onGround) {
/* 106 */       if (!this.br) {
/* 107 */         a(false, EnumRabbitState.NONE);
/* 108 */         cw();
/*     */       }
/*     */       
/* 111 */       if ((getRabbitType() == 99) && (this.bs == 0)) {
/* 112 */         EntityLiving entityliving = getGoalTarget();
/*     */         
/* 114 */         if ((entityliving != null) && (h(entityliving) < 16.0D)) {
/* 115 */           a(entityliving.locX, entityliving.locZ);
/* 116 */           this.moveController.a(entityliving.locX, entityliving.locY, entityliving.locZ, this.moveController.b());
/* 117 */           b(EnumRabbitState.ATTACK);
/* 118 */           this.br = true;
/*     */         }
/*     */       }
/*     */       
/* 122 */       ControllerJumpRabbit entityrabbit_controllerjumprabbit = (ControllerJumpRabbit)this.g;
/*     */       
/* 124 */       if (!entityrabbit_controllerjumprabbit.c()) {
/* 125 */         if ((this.moveController.a()) && (this.bs == 0)) {
/* 126 */           PathEntity pathentity = this.navigation.j();
/* 127 */           Vec3D vec3d = new Vec3D(this.moveController.d(), this.moveController.e(), this.moveController.f());
/*     */           
/* 129 */           if ((pathentity != null) && (pathentity.e() < pathentity.d())) {
/* 130 */             vec3d = pathentity.a(this);
/*     */           }
/*     */           
/* 133 */           a(vec3d.a, vec3d.c);
/* 134 */           b(this.bt);
/*     */         }
/* 136 */       } else if (!entityrabbit_controllerjumprabbit.d()) {
/* 137 */         ct();
/*     */       }
/*     */     }
/*     */     
/* 141 */     this.br = this.onGround;
/*     */   }
/*     */   
/*     */   public void Y() {}
/*     */   
/*     */   private void a(double d0, double d1) {
/* 147 */     this.yaw = ((float)(MathHelper.b(d1 - this.locZ, d0 - this.locX) * 180.0D / 3.1415927410125732D) - 90.0F);
/*     */   }
/*     */   
/*     */   private void ct() {
/* 151 */     ((ControllerJumpRabbit)this.g).a(true);
/*     */   }
/*     */   
/*     */   private void cu() {
/* 155 */     ((ControllerJumpRabbit)this.g).a(false);
/*     */   }
/*     */   
/*     */   private void cv() {
/* 159 */     this.bs = co();
/*     */   }
/*     */   
/*     */   private void cw() {
/* 163 */     cv();
/* 164 */     cu();
/*     */   }
/*     */   
/*     */   public void m() {
/* 168 */     super.m();
/* 169 */     if (this.bo != this.bp) {
/* 170 */       if ((this.bo == 0) && (!this.world.isClientSide)) {
/* 171 */         this.world.broadcastEntityEffect(this, (byte)1);
/*     */       }
/*     */       
/* 174 */       this.bo += 1;
/* 175 */     } else if (this.bp != 0) {
/* 176 */       this.bo = 0;
/* 177 */       this.bp = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/* 183 */     super.initAttributes();
/* 184 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/* 185 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 189 */     super.b(nbttagcompound);
/* 190 */     nbttagcompound.setInt("RabbitType", getRabbitType());
/* 191 */     nbttagcompound.setInt("MoreCarrotTicks", this.bu);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 195 */     super.a(nbttagcompound);
/* 196 */     setRabbitType(nbttagcompound.getInt("RabbitType"));
/* 197 */     this.bu = nbttagcompound.getInt("MoreCarrotTicks");
/*     */   }
/*     */   
/*     */   protected String cm() {
/* 201 */     return "mob.rabbit.hop";
/*     */   }
/*     */   
/*     */   protected String z() {
/* 205 */     return "mob.rabbit.idle";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 209 */     return "mob.rabbit.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 213 */     return "mob.rabbit.death";
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/* 217 */     if (getRabbitType() == 99) {
/* 218 */       makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 219 */       return entity.damageEntity(DamageSource.mobAttack(this), 8.0F);
/*     */     }
/* 221 */     return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
/*     */   }
/*     */   
/*     */   public int br()
/*     */   {
/* 226 */     return getRabbitType() == 99 ? 8 : super.br();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 230 */     return isInvulnerable(damagesource) ? false : super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   protected void getRareDrop() {
/* 234 */     a(new ItemStack(Items.RABBIT_FOOT, 1), 0.0F);
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 238 */     int j = this.random.nextInt(2) + this.random.nextInt(1 + i);
/*     */     
/*     */ 
/*     */ 
/* 242 */     for (int k = 0; k < j; k++) {
/* 243 */       a(Items.RABBIT_HIDE, 1);
/*     */     }
/*     */     
/* 246 */     j = this.random.nextInt(2);
/*     */     
/* 248 */     for (k = 0; k < j; k++) {
/* 249 */       if (isBurning()) {
/* 250 */         a(Items.COOKED_RABBIT, 1);
/*     */       } else {
/* 252 */         a(Items.RABBIT, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean a(Item item)
/*     */   {
/* 259 */     return (item == Items.CARROT) || (item == Items.GOLDEN_CARROT) || (item == Item.getItemOf(Blocks.YELLOW_FLOWER));
/*     */   }
/*     */   
/*     */   public EntityRabbit b(EntityAgeable entityageable) {
/* 263 */     EntityRabbit entityrabbit = new EntityRabbit(this.world);
/*     */     
/* 265 */     if ((entityageable instanceof EntityRabbit)) {
/* 266 */       entityrabbit.setRabbitType(this.random.nextBoolean() ? getRabbitType() : ((EntityRabbit)entityageable).getRabbitType());
/*     */     }
/*     */     
/* 269 */     return entityrabbit;
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/* 273 */     return (itemstack != null) && (a(itemstack.getItem()));
/*     */   }
/*     */   
/*     */   public int getRabbitType() {
/* 277 */     return this.datawatcher.getByte(18);
/*     */   }
/*     */   
/*     */   public void setRabbitType(int i) {
/* 281 */     if (i == 99) {
/* 282 */       this.goalSelector.a(this.bm);
/* 283 */       this.goalSelector.a(4, new PathfinderGoalKillerRabbitMeleeAttack(this));
/* 284 */       this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/* 285 */       this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/* 286 */       this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityWolf.class, true));
/* 287 */       if (!hasCustomName()) {
/* 288 */         setCustomName(LocaleI18n.get("entity.KillerBunny.name"));
/*     */       }
/*     */     }
/*     */     
/* 292 */     this.datawatcher.watch(18, Byte.valueOf((byte)i));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 296 */     Object object = super.prepare(difficultydamagescaler, groupdataentity);
/* 297 */     int i = this.random.nextInt(6);
/* 298 */     boolean flag = false;
/*     */     
/* 300 */     if ((object instanceof GroupDataRabbit)) {
/* 301 */       i = ((GroupDataRabbit)object).a;
/* 302 */       flag = true;
/*     */     } else {
/* 304 */       object = new GroupDataRabbit(i);
/*     */     }
/*     */     
/* 307 */     setRabbitType(i);
/* 308 */     if (flag) {
/* 309 */       setAgeRaw(41536);
/*     */     }
/*     */     
/* 312 */     return (GroupDataEntity)object;
/*     */   }
/*     */   
/*     */   private boolean cx() {
/* 316 */     return this.bu == 0;
/*     */   }
/*     */   
/*     */   protected int co() {
/* 320 */     return this.bt.c();
/*     */   }
/*     */   
/*     */   protected void cp() {
/* 324 */     this.world.addParticle(EnumParticle.BLOCK_DUST, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D, new int[] { Block.getCombinedId(Blocks.CARROTS.fromLegacyData(7)) });
/* 325 */     this.bu = 100;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 329 */     return b(entityageable);
/*     */   }
/*     */   
/*     */   static enum EnumRabbitState
/*     */   {
/* 334 */     NONE(0.0F, 0.0F, 30, 1),  HOP(0.8F, 0.2F, 20, 10),  STEP(1.0F, 0.45F, 14, 14),  SPRINT(1.75F, 0.4F, 1, 8),  ATTACK(2.0F, 0.7F, 7, 8);
/*     */     
/*     */     private final float f;
/*     */     private final float g;
/*     */     private final int h;
/*     */     private final int i;
/*     */     
/*     */     private EnumRabbitState(float f, float f1, int i, int j) {
/* 342 */       this.f = f;
/* 343 */       this.g = f1;
/* 344 */       this.h = i;
/* 345 */       this.i = j;
/*     */     }
/*     */     
/*     */     public float a() {
/* 349 */       return this.f;
/*     */     }
/*     */     
/*     */     public float b() {
/* 353 */       return this.g;
/*     */     }
/*     */     
/*     */     public int c() {
/* 357 */       return this.h;
/*     */     }
/*     */     
/*     */     public int d() {
/* 361 */       return this.i;
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalKillerRabbitMeleeAttack extends PathfinderGoalMeleeAttack
/*     */   {
/*     */     public PathfinderGoalKillerRabbitMeleeAttack(EntityRabbit entityrabbit) {
/* 368 */       super(EntityLiving.class, 1.4D, true);
/*     */     }
/*     */     
/*     */     protected double a(EntityLiving entityliving) {
/* 372 */       return 4.0F + entityliving.width;
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalRabbitPanic extends PathfinderGoalPanic
/*     */   {
/*     */     private EntityRabbit b;
/*     */     
/*     */     public PathfinderGoalRabbitPanic(EntityRabbit entityrabbit, double d0) {
/* 381 */       super(d0);
/* 382 */       this.b = entityrabbit;
/*     */     }
/*     */     
/*     */     public void e() {
/* 386 */       super.e();
/* 387 */       this.b.b(this.a);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalEatCarrots extends PathfinderGoalGotoTarget
/*     */   {
/*     */     private final EntityRabbit c;
/*     */     private boolean d;
/* 395 */     private boolean e = false;
/*     */     
/*     */     public PathfinderGoalEatCarrots(EntityRabbit entityrabbit) {
/* 398 */       super(0.699999988079071D, 16);
/* 399 */       this.c = entityrabbit;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 403 */       if (this.a <= 0) {
/* 404 */         if (!this.c.world.getGameRules().getBoolean("mobGriefing")) {
/* 405 */           return false;
/*     */         }
/*     */         
/* 408 */         this.e = false;
/* 409 */         this.d = this.c.cx();
/*     */       }
/*     */       
/* 412 */       return super.a();
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 416 */       return (this.e) && (super.b());
/*     */     }
/*     */     
/*     */     public void c() {
/* 420 */       super.c();
/*     */     }
/*     */     
/*     */     public void d() {
/* 424 */       super.d();
/*     */     }
/*     */     
/*     */     public void e() {
/* 428 */       super.e();
/* 429 */       this.c.getControllerLook().a(this.b.getX() + 0.5D, this.b.getY() + 1, this.b.getZ() + 0.5D, 10.0F, this.c.bQ());
/* 430 */       if (f()) {
/* 431 */         World world = this.c.world;
/* 432 */         BlockPosition blockposition = this.b.up();
/* 433 */         IBlockData iblockdata = world.getType(blockposition);
/* 434 */         Block block = iblockdata.getBlock();
/*     */         
/* 436 */         if ((this.e) && ((block instanceof BlockCarrots)) && (((Integer)iblockdata.get(BlockCarrots.AGE)).intValue() == 7)) {
/* 437 */           world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
/* 438 */           world.setAir(blockposition, true);
/* 439 */           this.c.cp();
/*     */         }
/*     */         
/* 442 */         this.e = false;
/* 443 */         this.a = 10;
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean a(World world, BlockPosition blockposition)
/*     */     {
/* 449 */       Block block = world.getType(blockposition).getBlock();
/*     */       
/* 451 */       if (block == Blocks.FARMLAND) {
/* 452 */         blockposition = blockposition.up();
/* 453 */         IBlockData iblockdata = world.getType(blockposition);
/*     */         
/* 455 */         block = iblockdata.getBlock();
/* 456 */         if (((block instanceof BlockCarrots)) && (((Integer)iblockdata.get(BlockCarrots.AGE)).intValue() == 7) && (this.d) && (!this.e)) {
/* 457 */           this.e = true;
/* 458 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 462 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalRabbitAvoidTarget<T extends Entity> extends PathfinderGoalAvoidTarget<T>
/*     */   {
/*     */     private EntityRabbit c;
/*     */     
/*     */     public PathfinderGoalRabbitAvoidTarget(EntityRabbit entityrabbit, Class<T> oclass, float f, double d0, double d1) {
/* 471 */       super(oclass, f, d0, d1);
/* 472 */       this.c = entityrabbit;
/*     */     }
/*     */     
/*     */     public void e() {
/* 476 */       super.e();
/*     */     }
/*     */   }
/*     */   
/*     */   static class ControllerMoveRabbit extends ControllerMove
/*     */   {
/*     */     private EntityRabbit g;
/*     */     
/*     */     public ControllerMoveRabbit(EntityRabbit entityrabbit) {
/* 485 */       super();
/* 486 */       this.g = entityrabbit;
/*     */     }
/*     */     
/*     */     public void c() {
/* 490 */       if ((this.g.onGround) && (!this.g.cl())) {
/* 491 */         this.g.b(0.0D);
/*     */       }
/*     */       
/* 494 */       super.c();
/*     */     }
/*     */   }
/*     */   
/*     */   public class ControllerJumpRabbit extends ControllerJump
/*     */   {
/*     */     private EntityRabbit c;
/* 501 */     private boolean d = false;
/*     */     
/*     */     public ControllerJumpRabbit(EntityRabbit entityrabbit) {
/* 504 */       super();
/* 505 */       this.c = entityrabbit;
/*     */     }
/*     */     
/*     */     public boolean c() {
/* 509 */       return this.a;
/*     */     }
/*     */     
/*     */     public boolean d() {
/* 513 */       return this.d;
/*     */     }
/*     */     
/*     */     public void a(boolean flag) {
/* 517 */       this.d = flag;
/*     */     }
/*     */     
/*     */     public void b() {
/* 521 */       if (this.a) {
/* 522 */         this.c.b(EntityRabbit.EnumRabbitState.STEP);
/* 523 */         this.a = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GroupDataRabbit implements GroupDataEntity
/*     */   {
/*     */     public int a;
/*     */     
/*     */     public GroupDataRabbit(int i)
/*     */     {
/* 534 */       this.a = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityRabbit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */