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
/*     */ 
/*     */ 
/*     */ public class EntityBlaze
/*     */   extends EntityMonster
/*     */ {
/*  21 */   private float a = 0.5F;
/*     */   
/*     */   private int b;
/*     */   
/*     */   public EntityBlaze(World paramWorld)
/*     */   {
/*  27 */     super(paramWorld);
/*     */     
/*  29 */     this.fireProof = true;
/*  30 */     this.b_ = 10;
/*     */     
/*  32 */     this.goalSelector.a(4, new PathfinderGoalBlazeFireball(this));
/*  33 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  34 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  35 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  36 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*     */     
/*  38 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
/*  39 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  44 */     super.initAttributes();
/*  45 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(6.0D);
/*  46 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
/*  47 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(48.0D);
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  52 */     super.h();
/*     */     
/*  54 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/*  59 */     return "mob.blaze.breathe";
/*     */   }
/*     */   
/*     */   protected String bo()
/*     */   {
/*  64 */     return "mob.blaze.hit";
/*     */   }
/*     */   
/*     */   protected String bp()
/*     */   {
/*  69 */     return "mob.blaze.death";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float c(float paramFloat)
/*     */   {
/*  79 */     return 1.0F;
/*     */   }
/*     */   
/*     */ 
/*     */   public void m()
/*     */   {
/*  85 */     if ((!this.onGround) && (this.motY < 0.0D)) {
/*  86 */       this.motY *= 0.6D;
/*     */     }
/*     */     
/*  89 */     if (this.world.isClientSide) {
/*  90 */       if ((this.random.nextInt(24) == 0) && (!R())) {
/*  91 */         this.world.a(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.fire", 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*  93 */       for (int i = 0; i < 2; i++) {
/*  94 */         this.world.addParticle(EnumParticle.SMOKE_LARGE, this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/*  98 */     super.m();
/*     */   }
/*     */   
/*     */   protected void E()
/*     */   {
/* 103 */     if (U()) {
/* 104 */       damageEntity(DamageSource.DROWN, 1.0F);
/*     */     }
/*     */     
/* 107 */     this.b -= 1;
/* 108 */     if (this.b <= 0) {
/* 109 */       this.b = 100;
/* 110 */       this.a = (0.5F + (float)this.random.nextGaussian() * 3.0F);
/*     */     }
/*     */     
/* 113 */     EntityLiving localEntityLiving = getGoalTarget();
/* 114 */     if ((localEntityLiving != null) && (localEntityLiving.locY + localEntityLiving.getHeadHeight() > this.locY + getHeadHeight() + this.a)) {
/* 115 */       this.motY += (0.30000001192092896D - this.motY) * 0.30000001192092896D;
/* 116 */       this.ai = true;
/*     */     }
/*     */     
/* 119 */     super.E();
/*     */   }
/*     */   
/*     */ 
/*     */   public void e(float paramFloat1, float paramFloat2) {}
/*     */   
/*     */ 
/*     */   protected Item getLoot()
/*     */   {
/* 128 */     return Items.BLAZE_ROD;
/*     */   }
/*     */   
/*     */   public boolean isBurning()
/*     */   {
/* 133 */     return n();
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt)
/*     */   {
/* 138 */     if (paramBoolean) {
/* 139 */       int i = this.random.nextInt(2 + paramInt);
/* 140 */       for (int j = 0; j < i; j++) {
/* 141 */         a(Items.BLAZE_ROD, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 147 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 151 */     byte b1 = this.datawatcher.getByte(16);
/* 152 */     if (paramBoolean) {
/* 153 */       b1 = (byte)(b1 | 0x1);
/*     */     } else {
/* 155 */       b1 = (byte)(b1 & 0xFFFFFFFE);
/*     */     }
/* 157 */     this.datawatcher.watch(16, Byte.valueOf(b1));
/*     */   }
/*     */   
/*     */   protected boolean n_()
/*     */   {
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   static class PathfinderGoalBlazeFireball extends PathfinderGoal {
/*     */     private EntityBlaze a;
/*     */     private int b;
/*     */     private int c;
/*     */     
/*     */     public PathfinderGoalBlazeFireball(EntityBlaze paramEntityBlaze) {
/* 171 */       this.a = paramEntityBlaze;
/*     */       
/* 173 */       a(3);
/*     */     }
/*     */     
/*     */     public boolean a()
/*     */     {
/* 178 */       EntityLiving localEntityLiving = this.a.getGoalTarget();
/* 179 */       if ((localEntityLiving == null) || (!localEntityLiving.isAlive())) {
/* 180 */         return false;
/*     */       }
/*     */       
/* 183 */       return true;
/*     */     }
/*     */     
/*     */     public void c()
/*     */     {
/* 188 */       this.b = 0;
/*     */     }
/*     */     
/*     */     public void d()
/*     */     {
/* 193 */       this.a.a(false);
/*     */     }
/*     */     
/*     */     public void e()
/*     */     {
/* 198 */       this.c -= 1;
/*     */       
/* 200 */       EntityLiving localEntityLiving = this.a.getGoalTarget();
/*     */       
/* 202 */       double d1 = this.a.h(localEntityLiving);
/*     */       
/* 204 */       if (d1 < 4.0D)
/*     */       {
/* 206 */         if (this.c <= 0) {
/* 207 */           this.c = 20;
/* 208 */           this.a.r(localEntityLiving);
/*     */         }
/* 210 */         this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0D);
/* 211 */       } else if (d1 < 256.0D) {
/* 212 */         double d2 = localEntityLiving.locX - this.a.locX;
/* 213 */         double d3 = localEntityLiving.getBoundingBox().b + localEntityLiving.length / 2.0F - (this.a.locY + this.a.length / 2.0F);
/* 214 */         double d4 = localEntityLiving.locZ - this.a.locZ;
/*     */         
/* 216 */         if (this.c <= 0) {
/* 217 */           this.b += 1;
/* 218 */           if (this.b == 1) {
/* 219 */             this.c = 60;
/* 220 */             this.a.a(true);
/* 221 */           } else if (this.b <= 4) {
/* 222 */             this.c = 6;
/*     */           } else {
/* 224 */             this.c = 100;
/* 225 */             this.b = 0;
/* 226 */             this.a.a(false);
/*     */           }
/*     */           
/* 229 */           if (this.b > 1) {
/* 230 */             float f = MathHelper.c(MathHelper.sqrt(d1)) * 0.5F;
/*     */             
/* 232 */             this.a.world.a(null, 1009, new BlockPosition((int)this.a.locX, (int)this.a.locY, (int)this.a.locZ), 0);
/* 233 */             for (int i = 0; i < 1; i++) {
/* 234 */               EntitySmallFireball localEntitySmallFireball = new EntitySmallFireball(this.a.world, this.a, d2 + this.a.bc().nextGaussian() * f, d3, d4 + this.a.bc().nextGaussian() * f);
/* 235 */               localEntitySmallFireball.locY = (this.a.locY + this.a.length / 2.0F + 0.5D);
/* 236 */               this.a.world.addEntity(localEntitySmallFireball);
/*     */             }
/*     */           }
/*     */         }
/* 240 */         this.a.getControllerLook().a(localEntityLiving, 10.0F, 10.0F);
/*     */       } else {
/* 242 */         this.a.getNavigation().n();
/* 243 */         this.a.getControllerMove().a(localEntityLiving.locX, localEntityLiving.locY, localEntityLiving.locZ, 1.0D);
/*     */       }
/*     */       
/* 246 */       super.e();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityBlaze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */