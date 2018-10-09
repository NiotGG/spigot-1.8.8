/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalMeleeAttack
/*     */   extends PathfinderGoal
/*     */ {
/*     */   World a;
/*     */   protected EntityCreature b;
/*     */   int c;
/*     */   double d;
/*     */   boolean e;
/*     */   PathEntity f;
/*     */   Class<? extends Entity> g;
/*     */   private int h;
/*     */   private double i;
/*     */   private double j;
/*     */   private double k;
/*     */   
/*     */   public PathfinderGoalMeleeAttack(EntityCreature paramEntityCreature, Class<? extends Entity> paramClass, double paramDouble, boolean paramBoolean)
/*     */   {
/*  25 */     this(paramEntityCreature, paramDouble, paramBoolean);
/*  26 */     this.g = paramClass;
/*     */   }
/*     */   
/*     */   public PathfinderGoalMeleeAttack(EntityCreature paramEntityCreature, double paramDouble, boolean paramBoolean) {
/*  30 */     this.b = paramEntityCreature;
/*  31 */     this.a = paramEntityCreature.world;
/*  32 */     this.d = paramDouble;
/*  33 */     this.e = paramBoolean;
/*  34 */     a(3);
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  39 */     EntityLiving localEntityLiving = this.b.getGoalTarget();
/*  40 */     if (localEntityLiving == null) {
/*  41 */       return false;
/*     */     }
/*  43 */     if (!localEntityLiving.isAlive()) {
/*  44 */       return false;
/*     */     }
/*  46 */     if ((this.g != null) && (!this.g.isAssignableFrom(localEntityLiving.getClass()))) {
/*  47 */       return false;
/*     */     }
/*  49 */     this.f = this.b.getNavigation().a(localEntityLiving);
/*  50 */     return this.f != null;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  55 */     EntityLiving localEntityLiving = this.b.getGoalTarget();
/*  56 */     if (localEntityLiving == null) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (!localEntityLiving.isAlive()) {
/*  60 */       return false;
/*     */     }
/*  62 */     if (!this.e) {
/*  63 */       if (this.b.getNavigation().m()) {
/*  64 */         return false;
/*     */       }
/*  66 */       return true;
/*     */     }
/*     */     
/*  69 */     if (!this.b.e(new BlockPosition(localEntityLiving))) {
/*  70 */       return false;
/*     */     }
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  77 */     this.b.getNavigation().a(this.f, this.d);
/*  78 */     this.h = 0;
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  83 */     this.b.getNavigation().n();
/*     */   }
/*     */   
/*     */   public void e()
/*     */   {
/*  88 */     EntityLiving localEntityLiving = this.b.getGoalTarget();
/*  89 */     this.b.getControllerLook().a(localEntityLiving, 30.0F, 30.0F);
/*  90 */     double d1 = this.b.e(localEntityLiving.locX, localEntityLiving.getBoundingBox().b, localEntityLiving.locZ);
/*  91 */     double d2 = a(localEntityLiving);
/*  92 */     this.h -= 1;
/*     */     
/*  94 */     if (((this.e) || (this.b.getEntitySenses().a(localEntityLiving))) && 
/*  95 */       (this.h <= 0) && (
/*  96 */       ((this.i == 0.0D) && (this.j == 0.0D) && (this.k == 0.0D)) || (localEntityLiving.e(this.i, this.j, this.k) >= 1.0D) || (this.b.bc().nextFloat() < 0.05F))) {
/*  97 */       this.i = localEntityLiving.locX;
/*  98 */       this.j = localEntityLiving.getBoundingBox().b;
/*  99 */       this.k = localEntityLiving.locZ;
/* 100 */       this.h = (4 + this.b.bc().nextInt(7));
/*     */       
/* 102 */       if (d1 > 1024.0D) {
/* 103 */         this.h += 10;
/* 104 */       } else if (d1 > 256.0D) {
/* 105 */         this.h += 5;
/*     */       }
/*     */       
/* 108 */       if (!this.b.getNavigation().a(localEntityLiving, this.d)) {
/* 109 */         this.h += 15;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 115 */     this.c = Math.max(this.c - 1, 0);
/*     */     
/* 117 */     if ((d1 <= d2) && (this.c <= 0)) {
/* 118 */       this.c = 20;
/* 119 */       if (this.b.bA() != null) {
/* 120 */         this.b.bw();
/*     */       }
/* 122 */       this.b.r(localEntityLiving);
/*     */     }
/*     */   }
/*     */   
/*     */   protected double a(EntityLiving paramEntityLiving) {
/* 127 */     return this.b.width * 2.0F * (this.b.width * 2.0F) + paramEntityLiving.width;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMeleeAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */