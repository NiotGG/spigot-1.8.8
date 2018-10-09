/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntitySpider extends EntityMonster
/*     */ {
/*     */   public EntitySpider(World world) {
/*   8 */     super(world);
/*   9 */     setSize(1.4F, 0.9F);
/*  10 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  11 */     this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
/*  12 */     this.goalSelector.a(4, new PathfinderGoalSpiderMeleeAttack(this, EntityHuman.class));
/*  13 */     this.goalSelector.a(4, new PathfinderGoalSpiderMeleeAttack(this, EntityIronGolem.class));
/*  14 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
/*  15 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  16 */     this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
/*  17 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  18 */     this.targetSelector.a(2, new PathfinderGoalSpiderNearestAttackableTarget(this, EntityHuman.class));
/*  19 */     this.targetSelector.a(3, new PathfinderGoalSpiderNearestAttackableTarget(this, EntityIronGolem.class));
/*     */   }
/*     */   
/*     */   public double an() {
/*  23 */     return this.length * 0.5F;
/*     */   }
/*     */   
/*     */   protected NavigationAbstract b(World world) {
/*  27 */     return new NavigationSpider(this, world);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  31 */     super.h();
/*  32 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void t_() {
/*  36 */     super.t_();
/*  37 */     if (!this.world.isClientSide) {
/*  38 */       a(this.positionChanged);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  44 */     super.initAttributes();
/*  45 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
/*  46 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/*     */   }
/*     */   
/*     */   protected String z() {
/*  50 */     return "mob.spider.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  54 */     return "mob.spider.say";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  58 */     return "mob.spider.death";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  62 */     makeSound("mob.spider.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  66 */     return Items.STRING;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  70 */     super.dropDeathLoot(flag, i);
/*  71 */     if ((flag) && ((this.random.nextInt(3) == 0) || (this.random.nextInt(1 + i) > 0))) {
/*  72 */       a(Items.SPIDER_EYE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean k_()
/*     */   {
/*  78 */     return n();
/*     */   }
/*     */   
/*     */   public void aA() {}
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/*  84 */     return EnumMonsterType.ARTHROPOD;
/*     */   }
/*     */   
/*     */   public boolean d(MobEffect mobeffect) {
/*  88 */     return mobeffect.getEffectId() == MobEffectList.POISON.id ? false : super.d(mobeffect);
/*     */   }
/*     */   
/*     */   public boolean n() {
/*  92 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/*  96 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/*  98 */     if (flag) {
/*  99 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 101 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     }
/*     */     
/* 104 */     this.datawatcher.watch(16, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
/* 108 */     Object object = super.prepare(difficultydamagescaler, groupdataentity);
/*     */     
/* 110 */     if (this.world.random.nextInt(100) == 0) {
/* 111 */       EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
/*     */       
/* 113 */       entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 114 */       entityskeleton.prepare(difficultydamagescaler, null);
/* 115 */       this.world.addEntity(entityskeleton, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.JOCKEY);
/* 116 */       entityskeleton.mount(this);
/*     */     }
/*     */     
/* 119 */     if (object == null) {
/* 120 */       object = new GroupDataSpider();
/* 121 */       if ((this.world.getDifficulty() == EnumDifficulty.HARD) && (this.world.random.nextFloat() < 0.1F * difficultydamagescaler.c())) {
/* 122 */         ((GroupDataSpider)object).a(this.world.random);
/*     */       }
/*     */     }
/*     */     
/* 126 */     if ((object instanceof GroupDataSpider)) {
/* 127 */       int i = ((GroupDataSpider)object).a;
/*     */       
/* 129 */       if ((i > 0) && (MobEffectList.byId[i] != null)) {
/* 130 */         addEffect(new MobEffect(i, Integer.MAX_VALUE));
/*     */       }
/*     */     }
/*     */     
/* 134 */     return (GroupDataEntity)object;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 138 */     return 0.65F;
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSpiderNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget
/*     */   {
/*     */     public PathfinderGoalSpiderNearestAttackableTarget(EntitySpider entityspider, Class<T> oclass) {
/* 144 */       super(oclass, true);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 148 */       float f = this.e.c(1.0F);
/*     */       
/* 150 */       return f >= 0.5F ? false : super.a();
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSpiderMeleeAttack extends PathfinderGoalMeleeAttack
/*     */   {
/*     */     public PathfinderGoalSpiderMeleeAttack(EntitySpider entityspider, Class<? extends Entity> oclass) {
/* 157 */       super(oclass, 1.0D, true);
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 161 */       float f = this.b.c(1.0F);
/*     */       
/* 163 */       if ((f >= 0.5F) && (this.b.bc().nextInt(100) == 0)) {
/* 164 */         this.b.setGoalTarget(null);
/* 165 */         return false;
/*     */       }
/* 167 */       return super.b();
/*     */     }
/*     */     
/*     */     protected double a(EntityLiving entityliving)
/*     */     {
/* 172 */       return 4.0F + entityliving.width;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GroupDataSpider
/*     */     implements GroupDataEntity
/*     */   {
/*     */     public int a;
/*     */     
/*     */     public void a(Random random)
/*     */     {
/* 183 */       int i = random.nextInt(5);
/*     */       
/* 185 */       if (i <= 1) {
/* 186 */         this.a = MobEffectList.FASTER_MOVEMENT.id;
/* 187 */       } else if (i <= 2) {
/* 188 */         this.a = MobEffectList.INCREASE_DAMAGE.id;
/* 189 */       } else if (i <= 3) {
/* 190 */         this.a = MobEffectList.REGENERATION.id;
/* 191 */       } else if (i <= 4) {
/* 192 */         this.a = MobEffectList.INVISIBILITY.id;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */