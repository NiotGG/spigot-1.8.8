/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ 
/*     */ public class EntityWolf extends EntityTameableAnimal
/*     */ {
/*     */   private float bo;
/*     */   private float bp;
/*     */   private boolean bq;
/*     */   private boolean br;
/*     */   private float bs;
/*     */   private float bt;
/*     */   
/*     */   public EntityWolf(World world)
/*     */   {
/*  20 */     super(world);
/*  21 */     setSize(0.6F, 0.8F);
/*  22 */     ((Navigation)getNavigation()).a(true);
/*  23 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  24 */     this.goalSelector.a(2, this.bm);
/*  25 */     this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
/*  26 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
/*  27 */     this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  28 */     this.goalSelector.a(6, new PathfinderGoalBreed(this, 1.0D));
/*  29 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  30 */     this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
/*  31 */     this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  32 */     this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
/*  33 */     this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
/*  34 */     this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
/*  35 */     this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  36 */     this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntityAnimal.class, false, new Predicate() {
/*     */       public boolean a(Entity entity) {
/*  38 */         return ((entity instanceof EntitySheep)) || ((entity instanceof EntityRabbit));
/*     */       }
/*     */       
/*     */       public boolean apply(Object object) {
/*  42 */         return a((Entity)object);
/*     */       }
/*  44 */     }));
/*  45 */     this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySkeleton.class, false));
/*  46 */     setTamed(false);
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  50 */     super.initAttributes();
/*  51 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/*  52 */     if (isTamed()) {
/*  53 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
/*     */     } else {
/*  55 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*     */     }
/*     */     
/*  58 */     getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
/*  59 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
/*     */   }
/*     */   
/*     */   public void setGoalTarget(EntityLiving entityliving) {
/*  63 */     super.setGoalTarget(entityliving);
/*  64 */     if (entityliving == null) {
/*  65 */       setAngry(false);
/*  66 */     } else if (!isTamed()) {
/*  67 */       setAngry(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setGoalTarget(EntityLiving entityliving, EntityTargetEvent.TargetReason reason, boolean fire)
/*     */   {
/*  75 */     super.setGoalTarget(entityliving, reason, fire);
/*  76 */     if (entityliving == null) {
/*  77 */       setAngry(false);
/*  78 */     } else if (!isTamed()) {
/*  79 */       setAngry(true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void E()
/*     */   {
/*  85 */     this.datawatcher.watch(18, Float.valueOf(getHealth()));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  89 */     super.h();
/*  90 */     this.datawatcher.a(18, new Float(getHealth()));
/*  91 */     this.datawatcher.a(19, new Byte((byte)0));
/*  92 */     this.datawatcher.a(20, new Byte((byte)EnumColor.RED.getColorIndex()));
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  96 */     makeSound("mob.wolf.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 100 */     super.b(nbttagcompound);
/* 101 */     nbttagcompound.setBoolean("Angry", isAngry());
/* 102 */     nbttagcompound.setByte("CollarColor", (byte)getCollarColor().getInvColorIndex());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 106 */     super.a(nbttagcompound);
/* 107 */     setAngry(nbttagcompound.getBoolean("Angry"));
/* 108 */     if (nbttagcompound.hasKeyOfType("CollarColor", 99)) {
/* 109 */       setCollarColor(EnumColor.fromInvColorIndex(nbttagcompound.getByte("CollarColor")));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected String z()
/*     */   {
/* 116 */     return this.random.nextInt(3) == 0 ? "mob.wolf.panting" : (isTamed()) && (this.datawatcher.getFloat(18) < getMaxHealth() / 2.0F) ? "mob.wolf.whine" : isAngry() ? "mob.wolf.growl" : "mob.wolf.bark";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 120 */     return "mob.wolf.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 124 */     return "mob.wolf.death";
/*     */   }
/*     */   
/*     */   protected float bB() {
/* 128 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 132 */     return Item.getById(-1);
/*     */   }
/*     */   
/*     */   public void m() {
/* 136 */     super.m();
/* 137 */     if ((!this.world.isClientSide) && (this.bq) && (!this.br) && (!cf()) && (this.onGround)) {
/* 138 */       this.br = true;
/* 139 */       this.bs = 0.0F;
/* 140 */       this.bt = 0.0F;
/* 141 */       this.world.broadcastEntityEffect(this, (byte)8);
/*     */     }
/*     */     
/* 144 */     if ((!this.world.isClientSide) && (getGoalTarget() == null) && (isAngry())) {
/* 145 */       setAngry(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 151 */     super.t_();
/* 152 */     this.bp = this.bo;
/* 153 */     if (cx()) {
/* 154 */       this.bo += (1.0F - this.bo) * 0.4F;
/*     */     } else {
/* 156 */       this.bo += (0.0F - this.bo) * 0.4F;
/*     */     }
/*     */     
/* 159 */     if (U()) {
/* 160 */       this.bq = true;
/* 161 */       this.br = false;
/* 162 */       this.bs = 0.0F;
/* 163 */       this.bt = 0.0F;
/* 164 */     } else if (((this.bq) || (this.br)) && (this.br)) {
/* 165 */       if (this.bs == 0.0F) {
/* 166 */         makeSound("mob.wolf.shake", bB(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 169 */       this.bt = this.bs;
/* 170 */       this.bs += 0.05F;
/* 171 */       if (this.bt >= 2.0F) {
/* 172 */         this.bq = false;
/* 173 */         this.br = false;
/* 174 */         this.bt = 0.0F;
/* 175 */         this.bs = 0.0F;
/*     */       }
/*     */       
/* 178 */       if (this.bs > 0.4F) {
/* 179 */         float f = (float)getBoundingBox().b;
/* 180 */         int i = (int)(MathHelper.sin((this.bs - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 182 */         for (int j = 0; j < i; j++) {
/* 183 */           float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 184 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/*     */           
/* 186 */           this.world.addParticle(EnumParticle.WATER_SPLASH, this.locX + f1, f + 0.8F, this.locZ + f2, this.motX, this.motY, this.motZ, new int[0]);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 194 */     return this.length * 0.8F;
/*     */   }
/*     */   
/*     */   public int bQ() {
/* 198 */     return isSitting() ? 20 : super.bQ();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 202 */     if (isInvulnerable(damagesource)) {
/* 203 */       return false;
/*     */     }
/* 205 */     Entity entity = damagesource.getEntity();
/*     */     
/*     */ 
/*     */ 
/* 209 */     if ((entity != null) && (!(entity instanceof EntityHuman)) && (!(entity instanceof EntityArrow))) {
/* 210 */       f = (f + 1.0F) / 2.0F;
/*     */     }
/*     */     
/* 213 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity)
/*     */   {
/* 218 */     boolean flag = entity.damageEntity(DamageSource.mobAttack(this), (int)getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue());
/*     */     
/* 220 */     if (flag) {
/* 221 */       a(this, entity);
/*     */     }
/*     */     
/* 224 */     return flag;
/*     */   }
/*     */   
/*     */   public void setTamed(boolean flag) {
/* 228 */     super.setTamed(flag);
/* 229 */     if (flag) {
/* 230 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
/*     */     } else {
/* 232 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*     */     }
/*     */     
/* 235 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 239 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 241 */     if (isTamed()) {
/* 242 */       if (itemstack != null) {
/* 243 */         if ((itemstack.getItem() instanceof ItemFood)) {
/* 244 */           ItemFood itemfood = (ItemFood)itemstack.getItem();
/*     */           
/* 246 */           if ((itemfood.g()) && (this.datawatcher.getFloat(18) < 20.0F)) {
/* 247 */             if (!entityhuman.abilities.canInstantlyBuild) {
/* 248 */               itemstack.count -= 1;
/*     */             }
/*     */             
/* 251 */             heal(itemfood.getNutrition(itemstack), org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.EATING);
/* 252 */             if (itemstack.count <= 0) {
/* 253 */               entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */             }
/*     */             
/* 256 */             return true;
/*     */           }
/* 258 */         } else if (itemstack.getItem() == Items.DYE) {
/* 259 */           EnumColor enumcolor = EnumColor.fromInvColorIndex(itemstack.getData());
/*     */           
/* 261 */           if (enumcolor != getCollarColor()) {
/* 262 */             setCollarColor(enumcolor);
/* 263 */             if (!entityhuman.abilities.canInstantlyBuild) { if (--itemstack.count <= 0) {
/* 264 */                 entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */               }
/*     */             }
/* 267 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 272 */       if ((e(entityhuman)) && (!this.world.isClientSide) && (!d(itemstack))) {
/* 273 */         this.bm.setSitting(!isSitting());
/* 274 */         this.aY = false;
/* 275 */         this.navigation.n();
/* 276 */         setGoalTarget(null, EntityTargetEvent.TargetReason.FORGOT_TARGET, true);
/*     */       }
/* 278 */     } else if ((itemstack != null) && (itemstack.getItem() == Items.BONE) && (!isAngry())) {
/* 279 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 280 */         itemstack.count -= 1;
/*     */       }
/*     */       
/* 283 */       if (itemstack.count <= 0) {
/* 284 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */       }
/*     */       
/* 287 */       if (!this.world.isClientSide)
/*     */       {
/* 289 */         if ((this.random.nextInt(3) == 0) && (!CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled())) {
/* 290 */           setTamed(true);
/* 291 */           this.navigation.n();
/* 292 */           setGoalTarget(null, EntityTargetEvent.TargetReason.FORGOT_TARGET, true);
/* 293 */           this.bm.setSitting(true);
/* 294 */           setHealth(getMaxHealth());
/* 295 */           setOwnerUUID(entityhuman.getUniqueID().toString());
/* 296 */           l(true);
/* 297 */           this.world.broadcastEntityEffect(this, (byte)7);
/*     */         } else {
/* 299 */           l(false);
/* 300 */           this.world.broadcastEntityEffect(this, (byte)6);
/*     */         }
/*     */       }
/*     */       
/* 304 */       return true;
/*     */     }
/*     */     
/* 307 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public boolean d(ItemStack itemstack) {
/* 311 */     return !(itemstack.getItem() instanceof ItemFood) ? false : itemstack == null ? false : ((ItemFood)itemstack.getItem()).g();
/*     */   }
/*     */   
/*     */   public int bV() {
/* 315 */     return 8;
/*     */   }
/*     */   
/*     */   public boolean isAngry() {
/* 319 */     return (this.datawatcher.getByte(16) & 0x2) != 0;
/*     */   }
/*     */   
/*     */   public void setAngry(boolean flag) {
/* 323 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 325 */     if (flag) {
/* 326 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     } else {
/* 328 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     }
/*     */   }
/*     */   
/*     */   public EnumColor getCollarColor()
/*     */   {
/* 334 */     return EnumColor.fromInvColorIndex(this.datawatcher.getByte(20) & 0xF);
/*     */   }
/*     */   
/*     */   public void setCollarColor(EnumColor enumcolor) {
/* 338 */     this.datawatcher.watch(20, Byte.valueOf((byte)(enumcolor.getInvColorIndex() & 0xF)));
/*     */   }
/*     */   
/*     */   public EntityWolf b(EntityAgeable entityageable) {
/* 342 */     EntityWolf entitywolf = new EntityWolf(this.world);
/* 343 */     String s = getOwnerUUID();
/*     */     
/* 345 */     if ((s != null) && (s.trim().length() > 0)) {
/* 346 */       entitywolf.setOwnerUUID(s);
/* 347 */       entitywolf.setTamed(true);
/*     */     }
/*     */     
/* 350 */     return entitywolf;
/*     */   }
/*     */   
/*     */   public void p(boolean flag) {
/* 354 */     if (flag) {
/* 355 */       this.datawatcher.watch(19, Byte.valueOf((byte)1));
/*     */     } else {
/* 357 */       this.datawatcher.watch(19, Byte.valueOf((byte)0));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal entityanimal)
/*     */   {
/* 363 */     if (entityanimal == this)
/* 364 */       return false;
/* 365 */     if (!isTamed())
/* 366 */       return false;
/* 367 */     if (!(entityanimal instanceof EntityWolf)) {
/* 368 */       return false;
/*     */     }
/* 370 */     EntityWolf entitywolf = (EntityWolf)entityanimal;
/*     */     
/* 372 */     return entitywolf.isTamed();
/*     */   }
/*     */   
/*     */   public boolean cx()
/*     */   {
/* 377 */     return this.datawatcher.getByte(19) == 1;
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 381 */     return !isTamed();
/*     */   }
/*     */   
/*     */   public boolean a(EntityLiving entityliving, EntityLiving entityliving1) {
/* 385 */     if ((!(entityliving instanceof EntityCreeper)) && (!(entityliving instanceof EntityGhast))) {
/* 386 */       if ((entityliving instanceof EntityWolf)) {
/* 387 */         EntityWolf entitywolf = (EntityWolf)entityliving;
/*     */         
/* 389 */         if ((entitywolf.isTamed()) && (entitywolf.getOwner() == entityliving1)) {
/* 390 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 394 */       return (!(entityliving instanceof EntityHuman)) || (!(entityliving1 instanceof EntityHuman)) || (((EntityHuman)entityliving1).a((EntityHuman)entityliving));
/*     */     }
/* 396 */     return false;
/*     */   }
/*     */   
/*     */   public boolean cb()
/*     */   {
/* 401 */     return (!isAngry()) && (super.cb());
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 405 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityWolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */