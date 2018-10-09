/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class PathfinderGoalBreed extends PathfinderGoal
/*     */ {
/*     */   private EntityAnimal d;
/*     */   World a;
/*     */   private EntityAnimal e;
/*     */   int b;
/*     */   double c;
/*     */   
/*     */   public PathfinderGoalBreed(EntityAnimal entityanimal, double d0)
/*     */   {
/*  16 */     this.d = entityanimal;
/*  17 */     this.a = entityanimal.world;
/*  18 */     this.c = d0;
/*  19 */     a(3);
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  23 */     if (!this.d.isInLove()) {
/*  24 */       return false;
/*     */     }
/*  26 */     this.e = f();
/*  27 */     return this.e != null;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  32 */     return (this.e.isAlive()) && (this.e.isInLove()) && (this.b < 60);
/*     */   }
/*     */   
/*     */   public void d() {
/*  36 */     this.e = null;
/*  37 */     this.b = 0;
/*     */   }
/*     */   
/*     */   public void e() {
/*  41 */     this.d.getControllerLook().a(this.e, 10.0F, this.d.bQ());
/*  42 */     this.d.getNavigation().a(this.e, this.c);
/*  43 */     this.b += 1;
/*  44 */     if ((this.b >= 60) && (this.d.h(this.e) < 9.0D)) {
/*  45 */       g();
/*     */     }
/*     */   }
/*     */   
/*     */   private EntityAnimal f()
/*     */   {
/*  51 */     float f = 8.0F;
/*  52 */     java.util.List list = this.a.a(this.d.getClass(), this.d.getBoundingBox().grow(f, f, f));
/*  53 */     double d0 = Double.MAX_VALUE;
/*  54 */     EntityAnimal entityanimal = null;
/*  55 */     Iterator iterator = list.iterator();
/*     */     
/*  57 */     while (iterator.hasNext()) {
/*  58 */       EntityAnimal entityanimal1 = (EntityAnimal)iterator.next();
/*     */       
/*  60 */       if ((this.d.mate(entityanimal1)) && (this.d.h(entityanimal1) < d0)) {
/*  61 */         entityanimal = entityanimal1;
/*  62 */         d0 = this.d.h(entityanimal1);
/*     */       }
/*     */     }
/*     */     
/*  66 */     return entityanimal;
/*     */   }
/*     */   
/*     */   private void g() {
/*  70 */     EntityAgeable entityageable = this.d.createChild(this.e);
/*     */     
/*  72 */     if (entityageable != null)
/*     */     {
/*  74 */       if (((entityageable instanceof EntityTameableAnimal)) && (((EntityTameableAnimal)entityageable).isTamed())) {
/*  75 */         entityageable.persistent = true;
/*     */       }
/*     */       
/*  78 */       EntityHuman entityhuman = this.d.cq();
/*     */       
/*  80 */       if ((entityhuman == null) && (this.e.cq() != null)) {
/*  81 */         entityhuman = this.e.cq();
/*     */       }
/*     */       
/*  84 */       if (entityhuman != null) {
/*  85 */         entityhuman.b(StatisticList.A);
/*  86 */         if ((this.d instanceof EntityCow)) {
/*  87 */           entityhuman.b(AchievementList.H);
/*     */         }
/*     */       }
/*     */       
/*  91 */       this.d.setAgeRaw(6000);
/*  92 */       this.e.setAgeRaw(6000);
/*  93 */       this.d.cs();
/*  94 */       this.e.cs();
/*  95 */       entityageable.setAgeRaw(41536);
/*  96 */       entityageable.setPositionRotation(this.d.locX, this.d.locY, this.d.locZ, 0.0F, 0.0F);
/*  97 */       this.a.addEntity(entityageable, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING);
/*  98 */       Random random = this.d.bc();
/*     */       
/* 100 */       for (int i = 0; i < 7; i++) {
/* 101 */         double d0 = random.nextGaussian() * 0.02D;
/* 102 */         double d1 = random.nextGaussian() * 0.02D;
/* 103 */         double d2 = random.nextGaussian() * 0.02D;
/* 104 */         double d3 = random.nextDouble() * this.d.width * 2.0D - this.d.width;
/* 105 */         double d4 = 0.5D + random.nextDouble() * this.d.length;
/* 106 */         double d5 = random.nextDouble() * this.d.width * 2.0D - this.d.width;
/*     */         
/* 108 */         this.a.addParticle(EnumParticle.HEART, this.d.locX + d3, this.d.locY + d4, this.d.locZ + d5, d0, d1, d2, new int[0]);
/*     */       }
/*     */       
/* 111 */       if (this.a.getGameRules().getBoolean("doMobLoot")) {
/* 112 */         this.a.addEntity(new EntityExperienceOrb(this.a, this.d.locX, this.d.locY, this.d.locZ, random.nextInt(7) + 1));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalBreed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */