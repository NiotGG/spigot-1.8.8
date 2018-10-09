/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityGhast extends EntityFlying implements IMonster
/*     */ {
/*   7 */   private int a = 1;
/*     */   
/*     */   public EntityGhast(World world) {
/*  10 */     super(world);
/*  11 */     setSize(4.0F, 4.0F);
/*  12 */     this.fireProof = true;
/*  13 */     this.b_ = 5;
/*  14 */     this.moveController = new ControllerGhast(this);
/*  15 */     this.goalSelector.a(5, new PathfinderGoalGhastIdleMove(this));
/*  16 */     this.goalSelector.a(7, new PathfinderGoalGhastMoveTowardsTarget(this));
/*  17 */     this.goalSelector.a(7, new PathfinderGoalGhastAttackTarget(this));
/*  18 */     this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/*  22 */     this.datawatcher.watch(16, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public int cf() {
/*  26 */     return this.a;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  30 */     super.t_();
/*  31 */     if ((!this.world.isClientSide) && (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)) {
/*  32 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f)
/*     */   {
/*  38 */     if (isInvulnerable(damagesource))
/*  39 */       return false;
/*  40 */     if (("fireball".equals(damagesource.p())) && ((damagesource.getEntity() instanceof EntityHuman))) {
/*  41 */       super.damageEntity(damagesource, 1000.0F);
/*  42 */       ((EntityHuman)damagesource.getEntity()).b(AchievementList.z);
/*  43 */       return true;
/*     */     }
/*  45 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  50 */     super.h();
/*  51 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  55 */     super.initAttributes();
/*  56 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  57 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(100.0D);
/*     */   }
/*     */   
/*     */   protected String z() {
/*  61 */     return "mob.ghast.moan";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  65 */     return "mob.ghast.scream";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  69 */     return "mob.ghast.death";
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  73 */     return Items.GUNPOWDER;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  77 */     int j = this.random.nextInt(2) + this.random.nextInt(1 + i);
/*     */     
/*     */ 
/*     */ 
/*  81 */     for (int k = 0; k < j; k++) {
/*  82 */       a(Items.GHAST_TEAR, 1);
/*     */     }
/*     */     
/*  85 */     j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/*  87 */     for (k = 0; k < j; k++) {
/*  88 */       a(Items.GUNPOWDER, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected float bB()
/*     */   {
/*  94 */     return 10.0F;
/*     */   }
/*     */   
/*     */   public boolean bR() {
/*  98 */     return (this.random.nextInt(20) == 0) && (super.bR()) && (this.world.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */   
/*     */   public int bV() {
/* 102 */     return 1;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 106 */     super.b(nbttagcompound);
/* 107 */     nbttagcompound.setInt("ExplosionPower", this.a);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 111 */     super.a(nbttagcompound);
/* 112 */     if (nbttagcompound.hasKeyOfType("ExplosionPower", 99)) {
/* 113 */       this.a = nbttagcompound.getInt("ExplosionPower");
/*     */     }
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 119 */     return 2.6F;
/*     */   }
/*     */   
/*     */   static class PathfinderGoalGhastAttackTarget extends PathfinderGoal
/*     */   {
/*     */     private EntityGhast b;
/*     */     public int a;
/*     */     
/*     */     public PathfinderGoalGhastAttackTarget(EntityGhast entityghast) {
/* 128 */       this.b = entityghast;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 132 */       return this.b.getGoalTarget() != null;
/*     */     }
/*     */     
/*     */     public void c() {
/* 136 */       this.a = 0;
/*     */     }
/*     */     
/*     */     public void d() {
/* 140 */       this.b.a(false);
/*     */     }
/*     */     
/*     */     public void e() {
/* 144 */       EntityLiving entityliving = this.b.getGoalTarget();
/* 145 */       double d0 = 64.0D;
/*     */       
/* 147 */       if ((entityliving.h(this.b) < d0 * d0) && (this.b.hasLineOfSight(entityliving))) {
/* 148 */         World world = this.b.world;
/*     */         
/* 150 */         this.a += 1;
/* 151 */         if (this.a == 10) {
/* 152 */           world.a(null, 1007, new BlockPosition(this.b), 0);
/*     */         }
/*     */         
/* 155 */         if (this.a == 20) {
/* 156 */           double d1 = 4.0D;
/* 157 */           Vec3D vec3d = this.b.d(1.0F);
/* 158 */           double d2 = entityliving.locX - (this.b.locX + vec3d.a * d1);
/* 159 */           double d3 = entityliving.getBoundingBox().b + entityliving.length / 2.0F - (0.5D + this.b.locY + this.b.length / 2.0F);
/* 160 */           double d4 = entityliving.locZ - (this.b.locZ + vec3d.c * d1);
/*     */           
/* 162 */           world.a(null, 1008, new BlockPosition(this.b), 0);
/* 163 */           EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.b, d2, d3, d4);
/*     */           
/*     */ 
/*     */ 
/* 167 */           entitylargefireball.bukkitYield = (entitylargefireball.yield = this.b.cf());
/* 168 */           entitylargefireball.locX = (this.b.locX + vec3d.a * d1);
/* 169 */           entitylargefireball.locY = (this.b.locY + this.b.length / 2.0F + 0.5D);
/* 170 */           entitylargefireball.locZ = (this.b.locZ + vec3d.c * d1);
/* 171 */           world.addEntity(entitylargefireball);
/* 172 */           this.a = -40;
/*     */         }
/* 174 */       } else if (this.a > 0) {
/* 175 */         this.a -= 1;
/*     */       }
/*     */       
/* 178 */       this.b.a(this.a > 10);
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalGhastMoveTowardsTarget extends PathfinderGoal
/*     */   {
/*     */     private EntityGhast a;
/*     */     
/*     */     public PathfinderGoalGhastMoveTowardsTarget(EntityGhast entityghast) {
/* 187 */       this.a = entityghast;
/* 188 */       a(2);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 192 */       return true;
/*     */     }
/*     */     
/*     */     public void e() {
/* 196 */       if (this.a.getGoalTarget() == null) {
/* 197 */         this.a.aI = (this.a.yaw = -(float)MathHelper.b(this.a.motX, this.a.motZ) * 180.0F / 3.1415927F);
/*     */       } else {
/* 199 */         EntityLiving entityliving = this.a.getGoalTarget();
/* 200 */         double d0 = 64.0D;
/*     */         
/* 202 */         if (entityliving.h(this.a) < d0 * d0) {
/* 203 */           double d1 = entityliving.locX - this.a.locX;
/* 204 */           double d2 = entityliving.locZ - this.a.locZ;
/*     */           
/* 206 */           this.a.aI = (this.a.yaw = -(float)MathHelper.b(d1, d2) * 180.0F / 3.1415927F);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalGhastIdleMove extends PathfinderGoal
/*     */   {
/*     */     private EntityGhast a;
/*     */     
/*     */     public PathfinderGoalGhastIdleMove(EntityGhast entityghast)
/*     */     {
/* 218 */       this.a = entityghast;
/* 219 */       a(1);
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 223 */       ControllerMove controllermove = this.a.getControllerMove();
/*     */       
/* 225 */       if (!controllermove.a()) {
/* 226 */         return true;
/*     */       }
/* 228 */       double d0 = controllermove.d() - this.a.locX;
/* 229 */       double d1 = controllermove.e() - this.a.locY;
/* 230 */       double d2 = controllermove.f() - this.a.locZ;
/* 231 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */       
/* 233 */       return (d3 < 1.0D) || (d3 > 3600.0D);
/*     */     }
/*     */     
/*     */     public boolean b()
/*     */     {
/* 238 */       return false;
/*     */     }
/*     */     
/*     */     public void c() {
/* 242 */       Random random = this.a.bc();
/* 243 */       double d0 = this.a.locX + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
/* 244 */       double d1 = this.a.locY + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
/* 245 */       double d2 = this.a.locZ + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
/*     */       
/* 247 */       this.a.getControllerMove().a(d0, d1, d2, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class ControllerGhast extends ControllerMove
/*     */   {
/*     */     private EntityGhast g;
/*     */     private int h;
/*     */     
/*     */     public ControllerGhast(EntityGhast entityghast) {
/* 257 */       super();
/* 258 */       this.g = entityghast;
/*     */     }
/*     */     
/*     */     public void c() {
/* 262 */       if (this.f) {
/* 263 */         double d0 = this.b - this.g.locX;
/* 264 */         double d1 = this.c - this.g.locY;
/* 265 */         double d2 = this.d - this.g.locZ;
/* 266 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 268 */         if (this.h-- <= 0) {
/* 269 */           this.h += this.g.bc().nextInt(5) + 2;
/* 270 */           d3 = MathHelper.sqrt(d3);
/* 271 */           if (b(this.b, this.c, this.d, d3)) {
/* 272 */             this.g.motX += d0 / d3 * 0.1D;
/* 273 */             this.g.motY += d1 / d3 * 0.1D;
/* 274 */             this.g.motZ += d2 / d3 * 0.1D;
/*     */           } else {
/* 276 */             this.f = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean b(double d0, double d1, double d2, double d3)
/*     */     {
/* 284 */       double d4 = (d0 - this.g.locX) / d3;
/* 285 */       double d5 = (d1 - this.g.locY) / d3;
/* 286 */       double d6 = (d2 - this.g.locZ) / d3;
/* 287 */       AxisAlignedBB axisalignedbb = this.g.getBoundingBox();
/*     */       
/* 289 */       for (int i = 1; i < d3; i++) {
/* 290 */         axisalignedbb = axisalignedbb.c(d4, d5, d6);
/* 291 */         if (!this.g.world.getCubes(this.g, axisalignedbb).isEmpty()) {
/* 292 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 296 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityGhast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */