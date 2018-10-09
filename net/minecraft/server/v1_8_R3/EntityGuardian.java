/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class EntityGuardian
/*     */   extends EntityMonster
/*     */ {
/*     */   private float a;
/*     */   private float b;
/*     */   private float c;
/*     */   private float bm;
/*     */   private float bn;
/*     */   private EntityLiving bo;
/*     */   private int bp;
/*     */   private boolean bq;
/*     */   public PathfinderGoalRandomStroll goalRandomStroll;
/*     */   
/*     */   public EntityGuardian(World paramWorld)
/*     */   {
/*  63 */     super(paramWorld);
/*     */     
/*  65 */     this.b_ = 10;
/*  66 */     setSize(0.85F, 0.85F);
/*     */     
/*     */ 
/*     */ 
/*  70 */     this.goalSelector.a(4, new PathfinderGoalGuardianAttack(this));
/*  71 */     PathfinderGoalMoveTowardsRestriction localPathfinderGoalMoveTowardsRestriction; this.goalSelector.a(5, localPathfinderGoalMoveTowardsRestriction = new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  72 */     this.goalSelector.a(7, this.goalRandomStroll = new PathfinderGoalRandomStroll(this, 1.0D, 80));
/*  73 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  74 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));
/*  75 */     this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
/*     */     
/*     */ 
/*  78 */     this.goalRandomStroll.a(3);
/*  79 */     localPathfinderGoalMoveTowardsRestriction.a(3);
/*     */     
/*  81 */     this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityLiving.class, 10, true, false, new EntitySelectorGuardianTargetHumanSquid(this)));
/*     */     
/*  83 */     this.moveController = new ControllerMoveGuardian(this);
/*     */     
/*  85 */     this.b = (this.a = this.random.nextFloat());
/*     */   }
/*     */   
/*     */   public void initAttributes()
/*     */   {
/*  90 */     super.initAttributes();
/*  91 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D);
/*  92 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.5D);
/*  93 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0D);
/*  94 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(30.0D);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  99 */     super.a(paramNBTTagCompound);
/*     */     
/* 101 */     setElder(paramNBTTagCompound.getBoolean("Elder"));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 106 */     super.b(paramNBTTagCompound);
/*     */     
/* 108 */     paramNBTTagCompound.setBoolean("Elder", isElder());
/*     */   }
/*     */   
/*     */   protected NavigationAbstract b(World paramWorld)
/*     */   {
/* 113 */     return new NavigationGuardian(this, paramWorld);
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/* 118 */     super.h();
/*     */     
/* 120 */     this.datawatcher.a(16, Integer.valueOf(0));
/* 121 */     this.datawatcher.a(17, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   private boolean a(int paramInt) {
/* 125 */     return (this.datawatcher.getInt(16) & paramInt) != 0;
/*     */   }
/*     */   
/*     */   private void a(int paramInt, boolean paramBoolean) {
/* 129 */     int i = this.datawatcher.getInt(16);
/* 130 */     if (paramBoolean) {
/* 131 */       this.datawatcher.watch(16, Integer.valueOf(i | paramInt));
/*     */     } else {
/* 133 */       this.datawatcher.watch(16, Integer.valueOf(i & (paramInt ^ 0xFFFFFFFF)));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 138 */     return a(2);
/*     */   }
/*     */   
/*     */   private void l(boolean paramBoolean) {
/* 142 */     a(2, paramBoolean);
/*     */   }
/*     */   
/*     */   public int cm() {
/* 146 */     if (isElder()) {
/* 147 */       return 60;
/*     */     }
/* 149 */     return 80;
/*     */   }
/*     */   
/*     */   public boolean isElder() {
/* 153 */     return a(4);
/*     */   }
/*     */   
/*     */   public void setElder(boolean paramBoolean) {
/* 157 */     a(4, paramBoolean);
/*     */     
/* 159 */     if (paramBoolean) {
/* 160 */       setSize(1.9975F, 1.9975F);
/* 161 */       getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/* 162 */       getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(8.0D);
/* 163 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(80.0D);
/* 164 */       bX();
/*     */       
/*     */ 
/* 167 */       this.goalRandomStroll.setTimeBetweenMovement(400);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void b(int paramInt)
/*     */   {
/* 177 */     this.datawatcher.watch(17, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public boolean cp() {
/* 181 */     return this.datawatcher.getInt(17) != 0;
/*     */   }
/*     */   
/*     */   public EntityLiving cq() {
/* 185 */     if (!cp()) {
/* 186 */       return null;
/*     */     }
/* 188 */     if (this.world.isClientSide) {
/* 189 */       if (this.bo != null) {
/* 190 */         return this.bo;
/*     */       }
/* 192 */       Entity localEntity = this.world.a(this.datawatcher.getInt(17));
/* 193 */       if ((localEntity instanceof EntityLiving)) {
/* 194 */         this.bo = ((EntityLiving)localEntity);
/* 195 */         return this.bo;
/*     */       }
/* 197 */       return null;
/*     */     }
/* 199 */     return getGoalTarget();
/*     */   }
/*     */   
/*     */ 
/*     */   public void i(int paramInt)
/*     */   {
/* 205 */     super.i(paramInt);
/*     */     
/* 207 */     if (paramInt == 16) {
/* 208 */       if ((isElder()) && (this.width < 1.0F)) {
/* 209 */         setSize(1.9975F, 1.9975F);
/*     */       }
/* 211 */     } else if (paramInt == 17) {
/* 212 */       this.bp = 0;
/* 213 */       this.bo = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public int w()
/*     */   {
/* 219 */     return 160;
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/* 224 */     if (!V()) {
/* 225 */       return "mob.guardian.land.idle";
/*     */     }
/* 227 */     if (isElder()) {
/* 228 */       return "mob.guardian.elder.idle";
/*     */     }
/* 230 */     return "mob.guardian.idle";
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/* 235 */     if (!V()) {
/* 236 */       return "mob.guardian.land.hit";
/*     */     }
/* 238 */     if (isElder()) {
/* 239 */       return "mob.guardian.elder.hit";
/*     */     }
/* 241 */     return "mob.guardian.hit";
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/* 246 */     if (!V()) {
/* 247 */       return "mob.guardian.land.death";
/*     */     }
/* 249 */     if (isElder()) {
/* 250 */       return "mob.guardian.elder.death";
/*     */     }
/* 252 */     return "mob.guardian.death";
/*     */   }
/*     */   
/*     */   protected boolean s_()
/*     */   {
/* 257 */     return false;
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 262 */     return this.length * 0.5F;
/*     */   }
/*     */   
/*     */   public float a(BlockPosition paramBlockPosition)
/*     */   {
/* 267 */     if (this.world.getType(paramBlockPosition).getBlock().getMaterial() == Material.WATER) {
/* 268 */       return 10.0F + this.world.o(paramBlockPosition) - 0.5F;
/*     */     }
/* 270 */     return super.a(paramBlockPosition);
/*     */   }
/*     */   
/*     */   public void m()
/*     */   {
/* 275 */     if (this.world.isClientSide)
/*     */     {
/* 277 */       this.b = this.a;
/* 278 */       if (!V()) {
/* 279 */         this.c = 2.0F;
/* 280 */         if ((this.motY > 0.0D) && (this.bq) && (!R())) {
/* 281 */           this.world.a(this.locX, this.locY, this.locZ, "mob.guardian.flop", 1.0F, 1.0F, false);
/*     */         }
/* 283 */         this.bq = ((this.motY < 0.0D) && (this.world.d(new BlockPosition(this).down(), false)));
/* 284 */       } else if (n()) {
/* 285 */         if (this.c < 0.5F) {
/* 286 */           this.c = 4.0F;
/*     */         } else {
/* 288 */           this.c += (0.5F - this.c) * 0.1F;
/*     */         }
/*     */       } else {
/* 291 */         this.c += (0.125F - this.c) * 0.2F;
/*     */       }
/* 293 */       this.a += this.c;
/*     */       
/*     */ 
/* 296 */       this.bn = this.bm;
/* 297 */       if (!V()) {
/* 298 */         this.bm = this.random.nextFloat();
/* 299 */       } else if (n()) {
/* 300 */         this.bm += (0.0F - this.bm) * 0.25F;
/*     */       } else {
/* 302 */         this.bm += (1.0F - this.bm) * 0.06F;
/*     */       }
/*     */       Object localObject;
/* 305 */       if ((n()) && (V())) {
/* 306 */         localObject = d(0.0F);
/* 307 */         for (int i = 0; i < 2; i++) {
/* 308 */           this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + (this.random.nextDouble() - 0.5D) * this.width - ((Vec3D)localObject).a * 1.5D, this.locY + this.random.nextDouble() * this.length - ((Vec3D)localObject).b * 1.5D, this.locZ + (this.random.nextDouble() - 0.5D) * this.width - ((Vec3D)localObject).c * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 312 */       if (cp()) {
/* 313 */         if (this.bp < cm()) {
/* 314 */           this.bp += 1;
/*     */         }
/* 316 */         localObject = cq();
/* 317 */         if (localObject != null) {
/* 318 */           getControllerLook().a((Entity)localObject, 90.0F, 90.0F);
/* 319 */           getControllerLook().a();
/*     */           
/* 321 */           double d1 = q(0.0F);
/* 322 */           double d2 = ((EntityLiving)localObject).locX - this.locX;
/* 323 */           double d3 = ((EntityLiving)localObject).locY + ((EntityLiving)localObject).length * 0.5F - (this.locY + getHeadHeight());
/* 324 */           double d4 = ((EntityLiving)localObject).locZ - this.locZ;
/* 325 */           double d5 = Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4);
/* 326 */           d2 /= d5;
/* 327 */           d3 /= d5;
/* 328 */           d4 /= d5;
/* 329 */           double d6 = this.random.nextDouble();
/* 330 */           while (d6 < d5) {
/* 331 */             d6 += 1.8D - d1 + this.random.nextDouble() * (1.7D - d1);
/* 332 */             this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + d2 * d6, this.locY + d3 * d6 + getHeadHeight(), this.locZ + d4 * d6, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 338 */     if (this.inWater) {
/* 339 */       setAirTicks(300);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 346 */     else if (this.onGround) {
/* 347 */       this.motY += 0.5D;
/* 348 */       this.motX += (this.random.nextFloat() * 2.0F - 1.0F) * 0.4F;
/* 349 */       this.motZ += (this.random.nextFloat() * 2.0F - 1.0F) * 0.4F;
/* 350 */       this.yaw = (this.random.nextFloat() * 360.0F);
/* 351 */       this.onGround = false;
/* 352 */       this.ai = true;
/*     */     }
/*     */     
/*     */ 
/* 356 */     if (cp()) {
/* 357 */       this.yaw = this.aK;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 362 */     super.m();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float q(float paramFloat)
/*     */   {
/* 374 */     return (this.bp + paramFloat) / cm();
/*     */   }
/*     */   
/*     */   protected void E()
/*     */   {
/* 379 */     super.E();
/*     */     
/* 381 */     if (isElder())
/*     */     {
/* 383 */       int i = 1200;
/* 384 */       int j = 1200;
/* 385 */       int k = 6000;
/* 386 */       int m = 2;
/* 387 */       MobEffectList localMobEffectList; if ((this.ticksLived + getId()) % 1200 == 0) {
/* 388 */         localMobEffectList = MobEffectList.SLOWER_DIG;
/*     */         
/* 390 */         List localList = this.world.b(EntityPlayer.class, new Predicate()
/*     */         {
/*     */           public boolean a(EntityPlayer paramAnonymousEntityPlayer) {
/* 393 */             return (EntityGuardian.this.h(paramAnonymousEntityPlayer) < 2500.0D) && (paramAnonymousEntityPlayer.playerInteractManager.c());
/*     */           }
/*     */         });
/* 396 */         for (EntityPlayer localEntityPlayer : localList) {
/* 397 */           if ((!localEntityPlayer.hasEffect(localMobEffectList)) || (localEntityPlayer.getEffect(localMobEffectList).getAmplifier() < 2) || (localEntityPlayer.getEffect(localMobEffectList).getDuration() < 1200)) {
/* 398 */             localEntityPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(10, 0.0F));
/* 399 */             localEntityPlayer.addEffect(new MobEffect(localMobEffectList.id, 6000, 2));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 405 */       if (!ck()) {
/* 406 */         a(new BlockPosition(this), 16);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt)
/*     */   {
/* 413 */     int i = this.random.nextInt(3) + this.random.nextInt(paramInt + 1);
/* 414 */     if (i > 0) {
/* 415 */       a(new ItemStack(Items.PRISMARINE_SHARD, i, 0), 1.0F);
/*     */     }
/* 417 */     if (this.random.nextInt(3 + paramInt) > 1) {
/* 418 */       a(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.COD.a()), 1.0F);
/* 419 */     } else if (this.random.nextInt(3 + paramInt) > 1) {
/* 420 */       a(new ItemStack(Items.PRISMARINE_CRYSTALS, 1, 0), 1.0F);
/*     */     }
/*     */     
/* 423 */     if ((paramBoolean) && 
/* 424 */       (isElder())) {
/* 425 */       a(new ItemStack(Blocks.SPONGE, 1, 1), 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void getRareDrop()
/*     */   {
/* 433 */     ItemStack localItemStack = ((PossibleFishingResult)WeightedRandom.a(this.random, EntityFishingHook.j())).a(this.random);
/* 434 */     a(localItemStack, 1.0F);
/*     */   }
/*     */   
/*     */   protected boolean n_()
/*     */   {
/* 439 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canSpawn()
/*     */   {
/* 444 */     return (this.world.a(getBoundingBox(), this)) && (this.world.getCubes(this, getBoundingBox()).isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean bR()
/*     */   {
/* 450 */     return ((this.random.nextInt(20) == 0) || (!this.world.j(new BlockPosition(this)))) && (super.bR());
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
/*     */   {
/* 455 */     if ((!n()) && (!paramDamageSource.isMagic()) && ((paramDamageSource.i() instanceof EntityLiving))) {
/* 456 */       EntityLiving localEntityLiving = (EntityLiving)paramDamageSource.i();
/*     */       
/*     */ 
/* 459 */       if (!paramDamageSource.isExplosion()) {
/* 460 */         localEntityLiving.damageEntity(DamageSource.a(this), 2.0F);
/* 461 */         localEntityLiving.makeSound("damage.thorns", 0.5F, 1.0F);
/*     */       }
/*     */     }
/* 464 */     this.goalRandomStroll.f();
/* 465 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */   
/*     */   public int bQ()
/*     */   {
/* 470 */     return 180;
/*     */   }
/*     */   
/*     */   public void g(float paramFloat1, float paramFloat2)
/*     */   {
/* 475 */     if (bM()) {
/* 476 */       if (V()) {
/* 477 */         a(paramFloat1, paramFloat2, 0.1F);
/* 478 */         move(this.motX, this.motY, this.motZ);
/*     */         
/* 480 */         this.motX *= 0.8999999761581421D;
/* 481 */         this.motY *= 0.8999999761581421D;
/* 482 */         this.motZ *= 0.8999999761581421D;
/* 483 */         if ((!n()) && (getGoalTarget() == null)) {
/* 484 */           this.motY -= 0.005D;
/*     */         }
/*     */       } else {
/* 487 */         super.g(paramFloat1, paramFloat2);
/*     */       }
/*     */     } else {
/* 490 */       super.g(paramFloat1, paramFloat2);
/*     */     }
/*     */   }
/*     */   
/*     */   static class EntitySelectorGuardianTargetHumanSquid implements Predicate<EntityLiving> {
/*     */     private EntityGuardian a;
/*     */     
/*     */     public EntitySelectorGuardianTargetHumanSquid(EntityGuardian paramEntityGuardian) {
/* 498 */       this.a = paramEntityGuardian;
/*     */     }
/*     */     
/*     */     public boolean a(EntityLiving paramEntityLiving)
/*     */     {
/* 503 */       return (((paramEntityLiving instanceof EntityHuman)) || ((paramEntityLiving instanceof EntitySquid))) && (paramEntityLiving.h(this.a) > 9.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalGuardianAttack extends PathfinderGoal {
/*     */     private EntityGuardian a;
/*     */     private int b;
/*     */     
/*     */     public PathfinderGoalGuardianAttack(EntityGuardian paramEntityGuardian) {
/* 512 */       this.a = paramEntityGuardian;
/*     */       
/* 514 */       a(3);
/*     */     }
/*     */     
/*     */     public boolean a()
/*     */     {
/* 519 */       EntityLiving localEntityLiving = this.a.getGoalTarget();
/* 520 */       if ((localEntityLiving == null) || (!localEntityLiving.isAlive())) {
/* 521 */         return false;
/*     */       }
/*     */       
/* 524 */       return true;
/*     */     }
/*     */     
/*     */     public boolean b()
/*     */     {
/* 529 */       return (super.b()) && ((this.a.isElder()) || (this.a.h(this.a.getGoalTarget()) > 9.0D));
/*     */     }
/*     */     
/*     */     public void c()
/*     */     {
/* 534 */       this.b = -10;
/* 535 */       this.a.getNavigation().n();
/* 536 */       this.a.getControllerLook().a(this.a.getGoalTarget(), 90.0F, 90.0F);
/*     */       
/*     */ 
/* 539 */       this.a.ai = true;
/*     */     }
/*     */     
/*     */     public void d()
/*     */     {
/* 544 */       EntityGuardian.a(this.a, 0);
/* 545 */       this.a.setGoalTarget(null);
/*     */       
/* 547 */       EntityGuardian.a(this.a).f();
/*     */     }
/*     */     
/*     */     public void e()
/*     */     {
/* 552 */       EntityLiving localEntityLiving = this.a.getGoalTarget();
/*     */       
/* 554 */       this.a.getNavigation().n();
/* 555 */       this.a.getControllerLook().a(localEntityLiving, 90.0F, 90.0F);
/*     */       
/* 557 */       if (!this.a.hasLineOfSight(localEntityLiving)) {
/* 558 */         this.a.setGoalTarget(null);
/* 559 */         return;
/*     */       }
/*     */       
/*     */ 
/* 563 */       this.b += 1;
/* 564 */       if (this.b == 0)
/*     */       {
/* 566 */         EntityGuardian.a(this.a, this.a.getGoalTarget().getId());
/* 567 */         this.a.world.broadcastEntityEffect(this.a, (byte)21);
/* 568 */       } else if (this.b >= this.a.cm()) {
/* 569 */         float f = 1.0F;
/* 570 */         if (this.a.world.getDifficulty() == EnumDifficulty.HARD) {
/* 571 */           f += 2.0F;
/*     */         }
/* 573 */         if (this.a.isElder()) {
/* 574 */           f += 2.0F;
/*     */         }
/* 576 */         localEntityLiving.damageEntity(DamageSource.b(this.a, this.a), f);
/* 577 */         localEntityLiving.damageEntity(DamageSource.mobAttack(this.a), (float)this.a.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
/* 578 */         this.a.setGoalTarget(null);
/* 579 */       } else if ((this.b < 60) || (this.b % 20 != 0)) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 584 */       super.e();
/*     */     }
/*     */   }
/*     */   
/*     */   static class ControllerMoveGuardian extends ControllerMove {
/*     */     private EntityGuardian g;
/*     */     
/*     */     public ControllerMoveGuardian(EntityGuardian paramEntityGuardian) {
/* 592 */       super();
/* 593 */       this.g = paramEntityGuardian;
/*     */     }
/*     */     
/*     */     public void c()
/*     */     {
/* 598 */       if ((!this.f) || (this.g.getNavigation().m()))
/*     */       {
/* 600 */         this.g.k(0.0F);
/* 601 */         EntityGuardian.a(this.g, false);
/* 602 */         return;
/*     */       }
/*     */       
/* 605 */       double d1 = this.b - this.g.locX;
/* 606 */       double d2 = this.c - this.g.locY;
/* 607 */       double d3 = this.d - this.g.locZ;
/* 608 */       double d4 = d1 * d1 + d2 * d2 + d3 * d3;
/* 609 */       d4 = MathHelper.sqrt(d4);
/* 610 */       d2 /= d4;
/*     */       
/* 612 */       float f1 = (float)(MathHelper.b(d3, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/*     */       
/* 614 */       this.g.yaw = a(this.g.yaw, f1, 30.0F);
/* 615 */       this.g.aI = this.g.yaw;
/*     */       
/* 617 */       float f2 = (float)(this.e * this.g.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue());
/* 618 */       this.g.k(this.g.bI() + (f2 - this.g.bI()) * 0.125F);
/* 619 */       double d5 = Math.sin((this.g.ticksLived + this.g.getId()) * 0.5D) * 0.05D;
/* 620 */       double d6 = Math.cos(this.g.yaw * 3.1415927F / 180.0F);
/* 621 */       double d7 = Math.sin(this.g.yaw * 3.1415927F / 180.0F);
/* 622 */       this.g.motX += d5 * d6;
/* 623 */       this.g.motZ += d5 * d7;
/*     */       
/* 625 */       d5 = Math.sin((this.g.ticksLived + this.g.getId()) * 0.75D) * 0.05D;
/* 626 */       this.g.motY += d5 * (d7 + d6) * 0.25D;
/* 627 */       this.g.motY += this.g.bI() * d2 * 0.1D;
/*     */       
/* 629 */       ControllerLook localControllerLook = this.g.getControllerLook();
/* 630 */       double d8 = this.g.locX + d1 / d4 * 2.0D;
/* 631 */       double d9 = this.g.getHeadHeight() + this.g.locY + d2 / d4 * 1.0D;
/* 632 */       double d10 = this.g.locZ + d3 / d4 * 2.0D;
/* 633 */       double d11 = localControllerLook.e();
/* 634 */       double d12 = localControllerLook.f();
/* 635 */       double d13 = localControllerLook.g();
/* 636 */       if (!localControllerLook.b()) {
/* 637 */         d11 = d8;
/* 638 */         d12 = d9;
/* 639 */         d13 = d10;
/*     */       }
/* 641 */       this.g.getControllerLook().a(d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, d13 + (d10 - d13) * 0.125D, 10.0F, 40.0F);
/* 642 */       EntityGuardian.a(this.g, true);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */