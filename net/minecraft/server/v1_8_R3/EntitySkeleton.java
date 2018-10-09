/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ 
/*     */ public class EntitySkeleton extends EntityMonster implements IRangedEntity
/*     */ {
/*   9 */   private PathfinderGoalArrowAttack a = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
/*  10 */   private PathfinderGoalMeleeAttack b = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);
/*     */   
/*     */   public EntitySkeleton(World world) {
/*  13 */     super(world);
/*  14 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  15 */     this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
/*  16 */     this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
/*  17 */     this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
/*  18 */     this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
/*  19 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  20 */     this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
/*  21 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  22 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*  23 */     this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
/*  24 */     if ((world != null) && (!world.isClientSide)) {
/*  25 */       n();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  31 */     super.initAttributes();
/*  32 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  36 */     super.h();
/*  37 */     this.datawatcher.a(13, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   protected String z() {
/*  41 */     return "mob.skeleton.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  45 */     return "mob.skeleton.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  49 */     return "mob.skeleton.death";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  53 */     makeSound("mob.skeleton.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean r(Entity entity) {
/*  57 */     if (super.r(entity)) {
/*  58 */       if ((getSkeletonType() == 1) && ((entity instanceof EntityLiving))) {
/*  59 */         ((EntityLiving)entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
/*     */       }
/*     */       
/*  62 */       return true;
/*     */     }
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType()
/*     */   {
/*  69 */     return EnumMonsterType.UNDEAD;
/*     */   }
/*     */   
/*     */   public void m() {
/*  73 */     if ((this.world.w()) && (!this.world.isClientSide)) {
/*  74 */       float f = c(1.0F);
/*  75 */       BlockPosition blockposition = new BlockPosition(this.locX, Math.round(this.locY), this.locZ);
/*     */       
/*  77 */       if ((f > 0.5F) && (this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) && (this.world.i(blockposition))) {
/*  78 */         boolean flag = true;
/*  79 */         ItemStack itemstack = getEquipment(4);
/*     */         
/*  81 */         if (itemstack != null) {
/*  82 */           if (itemstack.e()) {
/*  83 */             itemstack.setData(itemstack.h() + this.random.nextInt(2));
/*  84 */             if (itemstack.h() >= itemstack.j()) {
/*  85 */               b(itemstack);
/*  86 */               setEquipment(4, null);
/*     */             }
/*     */           }
/*     */           
/*  90 */           flag = false;
/*     */         }
/*     */         
/*  93 */         if (flag)
/*     */         {
/*  95 */           EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity(), 8);
/*  96 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/*  98 */           if (!event.isCancelled()) {
/*  99 */             setOnFire(event.getDuration());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 106 */     if ((this.world.isClientSide) && (getSkeletonType() == 1)) {
/* 107 */       setSize(0.72F, 2.535F);
/*     */     }
/*     */     
/* 110 */     super.m();
/*     */   }
/*     */   
/*     */   public void ak() {
/* 114 */     super.ak();
/* 115 */     if ((this.vehicle instanceof EntityCreature)) {
/* 116 */       EntityCreature entitycreature = (EntityCreature)this.vehicle;
/*     */       
/* 118 */       this.aI = entitycreature.aI;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void die(DamageSource damagesource)
/*     */   {
/* 125 */     if (((damagesource.i() instanceof EntityArrow)) && ((damagesource.getEntity() instanceof EntityHuman))) {
/* 126 */       EntityHuman entityhuman = (EntityHuman)damagesource.getEntity();
/* 127 */       double d0 = entityhuman.locX - this.locX;
/* 128 */       double d1 = entityhuman.locZ - this.locZ;
/*     */       
/* 130 */       if (d0 * d0 + d1 * d1 >= 2500.0D) {
/* 131 */         entityhuman.b(AchievementList.v);
/*     */       }
/* 133 */     } else if (((damagesource.getEntity() instanceof EntityCreeper)) && (((EntityCreeper)damagesource.getEntity()).isPowered()) && (((EntityCreeper)damagesource.getEntity()).cp())) {
/* 134 */       ((EntityCreeper)damagesource.getEntity()).cq();
/*     */       
/*     */ 
/* 137 */       this.headDrop = new ItemStack(Items.SKULL, 1, getSkeletonType() == 1 ? 1 : 0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 142 */     super.die(damagesource);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void dropDeathLoot(boolean flag, int i)
/*     */   {
/* 153 */     super.dropDeathLoot(flag, i);
/*     */     
/*     */ 
/*     */ 
/* 157 */     if (getSkeletonType() == 1) {
/* 158 */       int j = this.random.nextInt(3 + i) - 1;
/*     */       
/* 160 */       for (int k = 0; k < j; k++) {
/* 161 */         a(Items.COAL, 1);
/*     */       }
/*     */     } else {
/* 164 */       j = this.random.nextInt(3 + i);
/*     */       
/* 166 */       for (k = 0; k < j; k++) {
/* 167 */         a(Items.ARROW, 1);
/*     */       }
/*     */     }
/*     */     
/* 171 */     int j = this.random.nextInt(3 + i);
/*     */     
/* 173 */     for (int k = 0; k < j; k++) {
/* 174 */       a(Items.BONE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getRareDrop()
/*     */   {
/* 180 */     if (getSkeletonType() == 1) {
/* 181 */       a(new ItemStack(Items.SKULL, 1, 1), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(DifficultyDamageScaler difficultydamagescaler)
/*     */   {
/* 187 */     super.a(difficultydamagescaler);
/* 188 */     setEquipment(0, new ItemStack(Items.BOW));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 192 */     groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
/* 193 */     if (((this.world.worldProvider instanceof WorldProviderHell)) && (bc().nextInt(5) > 0)) {
/* 194 */       this.goalSelector.a(4, this.b);
/* 195 */       setSkeletonType(1);
/* 196 */       setEquipment(0, new ItemStack(Items.STONE_SWORD));
/* 197 */       getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
/*     */     } else {
/* 199 */       this.goalSelector.a(4, this.a);
/* 200 */       a(difficultydamagescaler);
/* 201 */       b(difficultydamagescaler);
/*     */     }
/*     */     
/* 204 */     j(this.random.nextFloat() < 0.55F * difficultydamagescaler.c());
/* 205 */     if (getEquipment(4) == null) {
/* 206 */       java.util.Calendar calendar = this.world.Y();
/*     */       
/* 208 */       if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31) && (this.random.nextFloat() < 0.25F)) {
/* 209 */         setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
/* 210 */         this.dropChances[4] = 0.0F;
/*     */       }
/*     */     }
/*     */     
/* 214 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public void n() {
/* 218 */     this.goalSelector.a(this.b);
/* 219 */     this.goalSelector.a(this.a);
/* 220 */     ItemStack itemstack = bA();
/*     */     
/* 222 */     if ((itemstack != null) && (itemstack.getItem() == Items.BOW)) {
/* 223 */       this.goalSelector.a(4, this.a);
/*     */     } else {
/* 225 */       this.goalSelector.a(4, this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, float f)
/*     */   {
/* 231 */     EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, 14 - this.world.getDifficulty().a() * 4);
/* 232 */     int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bA());
/* 233 */     int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bA());
/*     */     
/* 235 */     entityarrow.b(f * 2.0F + this.random.nextGaussian() * 0.25D + this.world.getDifficulty().a() * 0.11F);
/* 236 */     if (i > 0) {
/* 237 */       entityarrow.b(entityarrow.j() + i * 0.5D + 0.5D);
/*     */     }
/*     */     
/* 240 */     if (j > 0) {
/* 241 */       entityarrow.setKnockbackStrength(j);
/*     */     }
/*     */     
/* 244 */     if ((EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, bA()) > 0) || (getSkeletonType() == 1))
/*     */     {
/* 246 */       EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
/* 247 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 249 */       if (!event.isCancelled()) {
/* 250 */         entityarrow.setOnFire(event.getDuration());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 256 */     EntityShootBowEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityShootBowEvent(this, bA(), entityarrow, 0.8F);
/* 257 */     if (event.isCancelled()) {
/* 258 */       event.getProjectile().remove();
/* 259 */       return;
/*     */     }
/*     */     
/* 262 */     if (event.getProjectile() == entityarrow.getBukkitEntity()) {
/* 263 */       this.world.addEntity(entityarrow);
/*     */     }
/*     */     
/*     */ 
/* 267 */     makeSound("random.bow", 1.0F, 1.0F / (bc().nextFloat() * 0.4F + 0.8F));
/*     */   }
/*     */   
/*     */   public int getSkeletonType()
/*     */   {
/* 272 */     return this.datawatcher.getByte(13);
/*     */   }
/*     */   
/*     */   public void setSkeletonType(int i) {
/* 276 */     this.datawatcher.watch(13, Byte.valueOf((byte)i));
/* 277 */     this.fireProof = (i == 1);
/* 278 */     if (i == 1) {
/* 279 */       setSize(0.72F, 2.535F);
/*     */     } else {
/* 281 */       setSize(0.6F, 1.95F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 287 */     super.a(nbttagcompound);
/* 288 */     if (nbttagcompound.hasKeyOfType("SkeletonType", 99)) {
/* 289 */       byte b0 = nbttagcompound.getByte("SkeletonType");
/*     */       
/* 291 */       setSkeletonType(b0);
/*     */     }
/*     */     
/* 294 */     n();
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 298 */     super.b(nbttagcompound);
/* 299 */     nbttagcompound.setByte("SkeletonType", (byte)getSkeletonType());
/*     */   }
/*     */   
/*     */   public void setEquipment(int i, ItemStack itemstack) {
/* 303 */     super.setEquipment(i, itemstack);
/* 304 */     if ((!this.world.isClientSide) && (i == 0)) {
/* 305 */       n();
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 311 */     return getSkeletonType() == 1 ? super.getHeadHeight() : 1.74F;
/*     */   }
/*     */   
/*     */   public double am() {
/* 315 */     return isBaby() ? 0.0D : -0.35D;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySkeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */