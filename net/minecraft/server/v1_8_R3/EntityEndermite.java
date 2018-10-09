/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public class EntityEndermite
/*     */   extends EntityMonster
/*     */ {
/*  19 */   private int a = 0;
/*  20 */   private boolean b = false;
/*     */   
/*     */   public EntityEndermite(World paramWorld) {
/*  23 */     super(paramWorld);
/*  24 */     this.b_ = 3;
/*     */     
/*  26 */     setSize(0.4F, 0.3F);
/*     */     
/*  28 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  29 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
/*  30 */     this.goalSelector.a(3, new PathfinderGoalRandomStroll(this, 1.0D));
/*  31 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  32 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*     */     
/*  34 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  35 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/*  40 */     return 0.1F;
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  45 */     super.initAttributes();
/*     */     
/*  47 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*  48 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
/*  49 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
/*     */   }
/*     */   
/*     */   protected boolean s_()
/*     */   {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/*  59 */     return "mob.silverfish.say";
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/*  64 */     return "mob.silverfish.hit";
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/*  69 */     return "mob.silverfish.kill";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition paramBlockPosition, Block paramBlock)
/*     */   {
/*  74 */     makeSound("mob.silverfish.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot()
/*     */   {
/*  79 */     return null;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  84 */     super.a(paramNBTTagCompound);
/*  85 */     this.a = paramNBTTagCompound.getInt("Lifetime");
/*  86 */     this.b = paramNBTTagCompound.getBoolean("PlayerSpawned");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  91 */     super.b(paramNBTTagCompound);
/*  92 */     paramNBTTagCompound.setInt("Lifetime", this.a);
/*  93 */     paramNBTTagCompound.setBoolean("PlayerSpawned", this.b);
/*     */   }
/*     */   
/*     */ 
/*     */   public void t_()
/*     */   {
/*  99 */     this.aI = this.yaw;
/*     */     
/* 101 */     super.t_();
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 105 */     return this.b;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 109 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */   public void m()
/*     */   {
/* 114 */     super.m();
/*     */     
/* 116 */     if (this.world.isClientSide) {
/* 117 */       for (int i = 0; i < 2; i++) {
/* 118 */         this.world.addParticle(EnumParticle.PORTAL, this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 121 */       if (!isPersistent()) {
/* 122 */         this.a += 1;
/*     */       }
/*     */       
/* 125 */       if (this.a >= 2400) {
/* 126 */         die();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean n_()
/*     */   {
/* 133 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bR()
/*     */   {
/* 138 */     if (super.bR()) {
/* 139 */       EntityHuman localEntityHuman = this.world.findNearbyPlayer(this, 5.0D);
/* 140 */       return localEntityHuman == null;
/*     */     }
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType()
/*     */   {
/* 147 */     return EnumMonsterType.ARTHROPOD;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEndermite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */