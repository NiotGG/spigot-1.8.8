/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
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
/*     */ public class EntityPigZombie
/*     */   extends EntityZombie
/*     */ {
/*  25 */   private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  26 */   private static final AttributeModifier c = new AttributeModifier(b, "Attacking speed boost", 0.05D, 0).a(false);
/*     */   
/*     */   public int angerLevel;
/*     */   private int soundDelay;
/*     */   private UUID hurtBy;
/*     */   
/*     */   public EntityPigZombie(World paramWorld)
/*     */   {
/*  34 */     super(paramWorld);
/*  35 */     this.fireProof = true;
/*     */   }
/*     */   
/*     */   public void b(EntityLiving paramEntityLiving)
/*     */   {
/*  40 */     super.b(paramEntityLiving);
/*  41 */     if (paramEntityLiving != null) {
/*  42 */       this.hurtBy = paramEntityLiving.getUniqueID();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void n()
/*     */   {
/*  48 */     this.targetSelector.a(1, new PathfinderGoalAngerOther(this));
/*  49 */     this.targetSelector.a(2, new PathfinderGoalAnger(this));
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  54 */     super.initAttributes();
/*     */     
/*  56 */     getAttributeInstance(a).setValue(0.0D);
/*  57 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
/*  58 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5.0D);
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/*  63 */     super.t_();
/*     */   }
/*     */   
/*     */   protected void E()
/*     */   {
/*  68 */     AttributeInstance localAttributeInstance = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*  69 */     if (cm()) {
/*  70 */       if ((!isBaby()) && (!localAttributeInstance.a(c))) {
/*  71 */         localAttributeInstance.b(c);
/*     */       }
/*  73 */       this.angerLevel -= 1;
/*  74 */     } else if (localAttributeInstance.a(c)) {
/*  75 */       localAttributeInstance.c(c);
/*     */     }
/*     */     
/*  78 */     if ((this.soundDelay > 0) && 
/*  79 */       (--this.soundDelay == 0)) {
/*  80 */       makeSound("mob.zombiepig.zpigangry", bB() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*     */ 
/*  84 */     if ((this.angerLevel > 0) && 
/*  85 */       (this.hurtBy != null) && (getLastDamager() == null)) {
/*  86 */       EntityHuman localEntityHuman = this.world.b(this.hurtBy);
/*  87 */       b(localEntityHuman);
/*  88 */       this.killer = localEntityHuman;
/*  89 */       this.lastDamageByPlayerTime = be();
/*     */     }
/*     */     
/*     */ 
/*  93 */     super.E();
/*     */   }
/*     */   
/*     */   public boolean bR()
/*     */   {
/*  98 */     return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
/*     */   }
/*     */   
/*     */   public boolean canSpawn()
/*     */   {
/* 103 */     return (this.world.a(getBoundingBox(), this)) && (this.world.getCubes(this, getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(getBoundingBox()));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 108 */     super.b(paramNBTTagCompound);
/* 109 */     paramNBTTagCompound.setShort("Anger", (short)this.angerLevel);
/* 110 */     if (this.hurtBy != null) {
/* 111 */       paramNBTTagCompound.setString("HurtBy", this.hurtBy.toString());
/*     */     } else {
/* 113 */       paramNBTTagCompound.setString("HurtBy", "");
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 119 */     super.a(paramNBTTagCompound);
/* 120 */     this.angerLevel = paramNBTTagCompound.getShort("Anger");
/* 121 */     String str = paramNBTTagCompound.getString("HurtBy");
/* 122 */     if (str.length() > 0) {
/* 123 */       this.hurtBy = UUID.fromString(str);
/*     */       
/* 125 */       EntityHuman localEntityHuman = this.world.b(this.hurtBy);
/* 126 */       b(localEntityHuman);
/* 127 */       if (localEntityHuman != null) {
/* 128 */         this.killer = localEntityHuman;
/* 129 */         this.lastDamageByPlayerTime = be();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat)
/*     */   {
/* 136 */     if (isInvulnerable(paramDamageSource)) {
/* 137 */       return false;
/*     */     }
/* 139 */     Entity localEntity = paramDamageSource.getEntity();
/* 140 */     if ((localEntity instanceof EntityHuman)) {
/* 141 */       b(localEntity);
/*     */     }
/* 143 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */   
/*     */   private void b(Entity paramEntity) {
/* 147 */     this.angerLevel = (400 + this.random.nextInt(400));
/* 148 */     this.soundDelay = this.random.nextInt(40);
/* 149 */     if ((paramEntity instanceof EntityLiving)) {
/* 150 */       b((EntityLiving)paramEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean cm() {
/* 155 */     return this.angerLevel > 0;
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/* 160 */     return "mob.zombiepig.zpig";
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/* 165 */     return "mob.zombiepig.zpighurt";
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/* 170 */     return "mob.zombiepig.zpigdeath";
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt)
/*     */   {
/* 175 */     int i = this.random.nextInt(2 + paramInt);
/* 176 */     for (int j = 0; j < i; j++) {
/* 177 */       a(Items.ROTTEN_FLESH, 1);
/*     */     }
/* 179 */     i = this.random.nextInt(2 + paramInt);
/* 180 */     for (j = 0; j < i; j++) {
/* 181 */       a(Items.GOLD_NUGGET, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman paramEntityHuman)
/*     */   {
/* 187 */     return false;
/*     */   }
/*     */   
/*     */   protected void getRareDrop()
/*     */   {
/* 192 */     a(Items.GOLD_INGOT, 1);
/*     */   }
/*     */   
/*     */   protected void a(DifficultyDamageScaler paramDifficultyDamageScaler)
/*     */   {
/* 197 */     setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
/*     */   }
/*     */   
/*     */ 
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler paramDifficultyDamageScaler, GroupDataEntity paramGroupDataEntity)
/*     */   {
/* 203 */     super.prepare(paramDifficultyDamageScaler, paramGroupDataEntity);
/* 204 */     setVillager(false);
/* 205 */     return paramGroupDataEntity;
/*     */   }
/*     */   
/*     */   static class PathfinderGoalAngerOther extends PathfinderGoalHurtByTarget {
/*     */     public PathfinderGoalAngerOther(EntityPigZombie paramEntityPigZombie) {
/* 210 */       super(true, new Class[0]);
/*     */     }
/*     */     
/*     */     protected void a(EntityCreature paramEntityCreature, EntityLiving paramEntityLiving)
/*     */     {
/* 215 */       super.a(paramEntityCreature, paramEntityLiving);
/*     */       
/* 217 */       if ((paramEntityCreature instanceof EntityPigZombie)) {
/* 218 */         EntityPigZombie.a((EntityPigZombie)paramEntityCreature, paramEntityLiving);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalAnger extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
/*     */     public PathfinderGoalAnger(EntityPigZombie paramEntityPigZombie) {
/* 225 */       super(EntityHuman.class, true);
/*     */     }
/*     */     
/*     */     public boolean a()
/*     */     {
/* 230 */       return (((EntityPigZombie)this.e).cm()) && (super.a());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPigZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */