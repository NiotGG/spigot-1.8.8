/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.entity.SlimeSplitEvent;
/*     */ 
/*     */ public class EntitySlime extends EntityInsentient implements IMonster
/*     */ {
/*     */   public float a;
/*     */   public float b;
/*     */   public float c;
/*     */   private boolean bk;
/*     */   
/*     */   public EntitySlime(World world)
/*     */   {
/*  15 */     super(world);
/*  16 */     this.moveController = new ControllerMoveSlime(this);
/*  17 */     this.goalSelector.a(1, new PathfinderGoalSlimeRandomJump(this));
/*  18 */     this.goalSelector.a(2, new PathfinderGoalSlimeNearestPlayer(this));
/*  19 */     this.goalSelector.a(3, new PathfinderGoalSlimeRandomDirection(this));
/*  20 */     this.goalSelector.a(5, new PathfinderGoalSlimeIdle(this));
/*  21 */     this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));
/*  22 */     this.targetSelector.a(3, new PathfinderGoalNearestAttackableTargetInsentient(this, EntityIronGolem.class));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  26 */     super.h();
/*  27 */     this.datawatcher.a(16, Byte.valueOf((byte)1));
/*     */   }
/*     */   
/*     */   public void setSize(int i) {
/*  31 */     this.datawatcher.watch(16, Byte.valueOf((byte)i));
/*  32 */     setSize(0.51000005F * i, 0.51000005F * i);
/*  33 */     setPosition(this.locX, this.locY, this.locZ);
/*  34 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(i * i);
/*  35 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.2F + 0.1F * i);
/*  36 */     setHealth(getMaxHealth());
/*  37 */     this.b_ = i;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  41 */     return this.datawatcher.getByte(16);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  45 */     super.b(nbttagcompound);
/*  46 */     nbttagcompound.setInt("Size", getSize() - 1);
/*  47 */     nbttagcompound.setBoolean("wasOnGround", this.bk);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  51 */     super.a(nbttagcompound);
/*  52 */     int i = nbttagcompound.getInt("Size");
/*     */     
/*  54 */     if (i < 0) {
/*  55 */       i = 0;
/*     */     }
/*     */     
/*  58 */     setSize(i + 1);
/*  59 */     this.bk = nbttagcompound.getBoolean("wasOnGround");
/*     */   }
/*     */   
/*     */   protected EnumParticle n() {
/*  63 */     return EnumParticle.SLIME;
/*     */   }
/*     */   
/*     */   protected String ck() {
/*  67 */     return "mob.slime." + (getSize() > 1 ? "big" : "small");
/*     */   }
/*     */   
/*     */   public void t_() {
/*  71 */     if ((!this.world.isClientSide) && (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) && (getSize() > 0)) {
/*  72 */       this.dead = true;
/*     */     }
/*     */     
/*  75 */     this.b += (this.a - this.b) * 0.5F;
/*  76 */     this.c = this.b;
/*  77 */     super.t_();
/*  78 */     if ((this.onGround) && (!this.bk)) {
/*  79 */       int i = getSize();
/*     */       
/*  81 */       for (int j = 0; j < i * 8; j++) {
/*  82 */         float f = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  83 */         float f1 = this.random.nextFloat() * 0.5F + 0.5F;
/*  84 */         float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/*  85 */         float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/*  86 */         World world = this.world;
/*  87 */         EnumParticle enumparticle = n();
/*  88 */         double d0 = this.locX + f2;
/*  89 */         double d1 = this.locZ + f3;
/*     */         
/*  91 */         world.addParticle(enumparticle, d0, getBoundingBox().b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/*  94 */       if (cl()) {
/*  95 */         makeSound(ck(), bB(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/*     */       }
/*     */       
/*  98 */       this.a = -0.5F;
/*  99 */     } else if ((!this.onGround) && (this.bk)) {
/* 100 */       this.a = 1.0F;
/*     */     }
/*     */     
/* 103 */     this.bk = this.onGround;
/* 104 */     ch();
/*     */   }
/*     */   
/*     */   protected void ch() {
/* 108 */     this.a *= 0.6F;
/*     */   }
/*     */   
/*     */   protected int cg() {
/* 112 */     return this.random.nextInt(20) + 10;
/*     */   }
/*     */   
/*     */   protected EntitySlime cf() {
/* 116 */     return new EntitySlime(this.world);
/*     */   }
/*     */   
/*     */   public void i(int i) {
/* 120 */     if (i == 16) {
/* 121 */       int j = getSize();
/*     */       
/* 123 */       setSize(0.51000005F * j, 0.51000005F * j);
/* 124 */       this.yaw = this.aK;
/* 125 */       this.aI = this.aK;
/* 126 */       if ((V()) && (this.random.nextInt(20) == 0)) {
/* 127 */         X();
/*     */       }
/*     */     }
/*     */     
/* 131 */     super.i(i);
/*     */   }
/*     */   
/*     */   public void die() {
/* 135 */     int i = getSize();
/*     */     
/* 137 */     if ((!this.world.isClientSide) && (i > 1) && (getHealth() <= 0.0F)) {
/* 138 */       int j = 2 + this.random.nextInt(3);
/*     */       
/*     */ 
/* 141 */       SlimeSplitEvent event = new SlimeSplitEvent((org.bukkit.entity.Slime)getBukkitEntity(), j);
/* 142 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 144 */       if ((!event.isCancelled()) && (event.getCount() > 0)) {
/* 145 */         j = event.getCount();
/*     */       } else {
/* 147 */         super.die();
/* 148 */         return;
/*     */       }
/*     */       
/*     */ 
/* 152 */       for (int k = 0; k < j; k++) {
/* 153 */         float f = (k % 2 - 0.5F) * i / 4.0F;
/* 154 */         float f1 = (k / 2 - 0.5F) * i / 4.0F;
/* 155 */         EntitySlime entityslime = cf();
/*     */         
/* 157 */         if (hasCustomName()) {
/* 158 */           entityslime.setCustomName(getCustomName());
/*     */         }
/*     */         
/* 161 */         if (isPersistent()) {
/* 162 */           entityslime.bX();
/*     */         }
/*     */         
/* 165 */         entityslime.setSize(i / 2);
/* 166 */         entityslime.setPositionRotation(this.locX + f, this.locY + 0.5D, this.locZ + f1, this.random.nextFloat() * 360.0F, 0.0F);
/* 167 */         this.world.addEntity(entityslime, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
/*     */       }
/*     */     }
/*     */     
/* 171 */     super.die();
/*     */   }
/*     */   
/*     */   public void collide(Entity entity) {
/* 175 */     super.collide(entity);
/* 176 */     if (((entity instanceof EntityIronGolem)) && (ci())) {
/* 177 */       e((EntityLiving)entity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void d(EntityHuman entityhuman)
/*     */   {
/* 183 */     if (ci()) {
/* 184 */       e(entityhuman);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void e(EntityLiving entityliving)
/*     */   {
/* 190 */     int i = getSize();
/*     */     
/* 192 */     if ((hasLineOfSight(entityliving)) && (h(entityliving) < 0.6D * i * 0.6D * i) && (entityliving.damageEntity(DamageSource.mobAttack(this), cj()))) {
/* 193 */       makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 194 */       a(this, entityliving);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 200 */     return 0.625F * this.length;
/*     */   }
/*     */   
/*     */   protected boolean ci() {
/* 204 */     return getSize() > 1;
/*     */   }
/*     */   
/*     */   protected int cj() {
/* 208 */     return getSize();
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 212 */     return "mob.slime." + (getSize() > 1 ? "big" : "small");
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 216 */     return "mob.slime." + (getSize() > 1 ? "big" : "small");
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 220 */     return getSize() == 1 ? Items.SLIME : null;
/*     */   }
/*     */   
/*     */   public boolean bR() {
/* 224 */     BlockPosition blockposition = new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ));
/* 225 */     Chunk chunk = this.world.getChunkAtWorldCoords(blockposition);
/*     */     
/* 227 */     if ((this.world.getWorldData().getType() == WorldType.FLAT) && (this.random.nextInt(4) != 1)) {
/* 228 */       return false;
/*     */     }
/* 230 */     if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
/* 231 */       BiomeBase biomebase = this.world.getBiome(blockposition);
/*     */       
/* 233 */       if ((biomebase == BiomeBase.SWAMPLAND) && (this.locY > 50.0D) && (this.locY < 70.0D) && (this.random.nextFloat() < 0.5F) && (this.random.nextFloat() < this.world.y()) && (this.world.getLightLevel(new BlockPosition(this)) <= this.random.nextInt(8))) {
/* 234 */         return super.bR();
/*     */       }
/*     */       
/* 237 */       if ((this.random.nextInt(10) == 0) && (chunk.a(987234911L).nextInt(10) == 0) && (this.locY < 40.0D)) {
/* 238 */         return super.bR();
/*     */       }
/*     */     }
/*     */     
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   protected float bB()
/*     */   {
/* 247 */     return 0.4F * getSize();
/*     */   }
/*     */   
/*     */   public int bQ() {
/* 251 */     return 0;
/*     */   }
/*     */   
/*     */   protected boolean cn() {
/* 255 */     return getSize() > 0;
/*     */   }
/*     */   
/*     */   protected boolean cl() {
/* 259 */     return getSize() > 2;
/*     */   }
/*     */   
/*     */   protected void bF() {
/* 263 */     this.motY = 0.41999998688697815D;
/* 264 */     this.ai = true;
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 268 */     int i = this.random.nextInt(3);
/*     */     
/* 270 */     if ((i < 2) && (this.random.nextFloat() < 0.5F * difficultydamagescaler.c())) {
/* 271 */       i++;
/*     */     }
/*     */     
/* 274 */     int j = 1 << i;
/*     */     
/* 276 */     setSize(j);
/* 277 */     return super.prepare(difficultydamagescaler, groupdataentity);
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSlimeIdle extends PathfinderGoal
/*     */   {
/*     */     private EntitySlime a;
/*     */     
/*     */     public PathfinderGoalSlimeIdle(EntitySlime entityslime) {
/* 285 */       this.a = entityslime;
/* 286 */       a(5);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 290 */       return true;
/*     */     }
/*     */     
/*     */     public void e() {
/* 294 */       ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSlimeRandomJump extends PathfinderGoal
/*     */   {
/*     */     private EntitySlime a;
/*     */     
/*     */     public PathfinderGoalSlimeRandomJump(EntitySlime entityslime) {
/* 303 */       this.a = entityslime;
/* 304 */       a(5);
/* 305 */       ((Navigation)entityslime.getNavigation()).d(true);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 309 */       return (this.a.V()) || (this.a.ab());
/*     */     }
/*     */     
/*     */     public void e() {
/* 313 */       if (this.a.bc().nextFloat() < 0.8F) {
/* 314 */         this.a.getControllerJump().a();
/*     */       }
/*     */       
/* 317 */       ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(1.2D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSlimeRandomDirection extends PathfinderGoal
/*     */   {
/*     */     private EntitySlime a;
/*     */     private float b;
/*     */     private int c;
/*     */     
/*     */     public PathfinderGoalSlimeRandomDirection(EntitySlime entityslime) {
/* 328 */       this.a = entityslime;
/* 329 */       a(2);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 333 */       return (this.a.getGoalTarget() == null) && ((this.a.onGround) || (this.a.V()) || (this.a.ab()));
/*     */     }
/*     */     
/*     */     public void e() {
/* 337 */       if (--this.c <= 0) {
/* 338 */         this.c = (40 + this.a.bc().nextInt(60));
/* 339 */         this.b = this.a.bc().nextInt(360);
/*     */       }
/*     */       
/* 342 */       ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(this.b, false);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSlimeNearestPlayer extends PathfinderGoal
/*     */   {
/*     */     private EntitySlime a;
/*     */     private int b;
/*     */     
/*     */     public PathfinderGoalSlimeNearestPlayer(EntitySlime entityslime) {
/* 352 */       this.a = entityslime;
/* 353 */       a(2);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 357 */       EntityLiving entityliving = this.a.getGoalTarget();
/*     */       
/* 359 */       return entityliving != null;
/*     */     }
/*     */     
/*     */     public void c() {
/* 363 */       this.b = 300;
/* 364 */       super.c();
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 368 */       EntityLiving entityliving = this.a.getGoalTarget();
/*     */       
/* 370 */       return entityliving != null;
/*     */     }
/*     */     
/*     */     public void e() {
/* 374 */       this.a.a(this.a.getGoalTarget(), 10.0F, 10.0F);
/* 375 */       ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(this.a.yaw, this.a.ci());
/*     */     }
/*     */   }
/*     */   
/*     */   static class ControllerMoveSlime extends ControllerMove
/*     */   {
/*     */     private float g;
/*     */     private int h;
/*     */     private EntitySlime i;
/*     */     private boolean j;
/*     */     
/*     */     public ControllerMoveSlime(EntitySlime entityslime) {
/* 387 */       super();
/* 388 */       this.i = entityslime;
/*     */     }
/*     */     
/*     */     public void a(float f, boolean flag) {
/* 392 */       this.g = f;
/* 393 */       this.j = flag;
/*     */     }
/*     */     
/*     */     public void a(double d0) {
/* 397 */       this.e = d0;
/* 398 */       this.f = true;
/*     */     }
/*     */     
/*     */     public void c() {
/* 402 */       this.a.yaw = a(this.a.yaw, this.g, 30.0F);
/* 403 */       this.a.aK = this.a.yaw;
/* 404 */       this.a.aI = this.a.yaw;
/* 405 */       if (!this.f) {
/* 406 */         this.a.n(0.0F);
/*     */       } else {
/* 408 */         this.f = false;
/* 409 */         if (this.a.onGround) {
/* 410 */           this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
/* 411 */           if (this.h-- <= 0) {
/* 412 */             this.h = this.i.cg();
/* 413 */             if (this.j) {
/* 414 */               this.h /= 3;
/*     */             }
/*     */             
/* 417 */             this.i.getControllerJump().a();
/* 418 */             if (this.i.cn()) {
/* 419 */               this.i.makeSound(this.i.ck(), this.i.bB(), ((this.i.bc().nextFloat() - this.i.bc().nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */             }
/*     */           } else {
/* 422 */             this.i.aZ = (this.i.ba = 0.0F);
/* 423 */             this.a.k(0.0F);
/*     */           }
/*     */         } else {
/* 426 */           this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */