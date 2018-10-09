/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalFollowOwner
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private EntityTameableAnimal d;
/*     */   
/*     */   private EntityLiving e;
/*     */   
/*     */   World a;
/*     */   
/*     */   private double f;
/*     */   
/*     */   private NavigationAbstract g;
/*     */   
/*     */   private int h;
/*     */   
/*     */   float b;
/*     */   
/*     */   float c;
/*     */   
/*     */   private boolean i;
/*     */   
/*     */ 
/*     */   public PathfinderGoalFollowOwner(EntityTameableAnimal paramEntityTameableAnimal, double paramDouble, float paramFloat1, float paramFloat2)
/*     */   {
/*  29 */     this.d = paramEntityTameableAnimal;
/*  30 */     this.a = paramEntityTameableAnimal.world;
/*  31 */     this.f = paramDouble;
/*  32 */     this.g = paramEntityTameableAnimal.getNavigation();
/*  33 */     this.c = paramFloat1;
/*  34 */     this.b = paramFloat2;
/*  35 */     a(3);
/*     */     
/*  37 */     if (!(paramEntityTameableAnimal.getNavigation() instanceof Navigation)) {
/*  38 */       throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  44 */     EntityLiving localEntityLiving = this.d.getOwner();
/*  45 */     if (localEntityLiving == null) {
/*  46 */       return false;
/*     */     }
/*  48 */     if (((localEntityLiving instanceof EntityHuman)) && (((EntityHuman)localEntityLiving).isSpectator())) {
/*  49 */       return false;
/*     */     }
/*  51 */     if (this.d.isSitting()) {
/*  52 */       return false;
/*     */     }
/*  54 */     if (this.d.h(localEntityLiving) < this.c * this.c) {
/*  55 */       return false;
/*     */     }
/*  57 */     this.e = localEntityLiving;
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  63 */     return (!this.g.m()) && (this.d.h(this.e) > this.b * this.b) && (!this.d.isSitting());
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  68 */     this.h = 0;
/*  69 */     this.i = ((Navigation)this.d.getNavigation()).e();
/*  70 */     ((Navigation)this.d.getNavigation()).a(false);
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  75 */     this.e = null;
/*  76 */     this.g.n();
/*  77 */     ((Navigation)this.d.getNavigation()).a(true);
/*     */   }
/*     */   
/*     */   private boolean a(BlockPosition paramBlockPosition) {
/*  81 */     IBlockData localIBlockData = this.a.getType(paramBlockPosition);
/*  82 */     Block localBlock = localIBlockData.getBlock();
/*  83 */     if (localBlock == Blocks.AIR) {
/*  84 */       return true;
/*     */     }
/*  86 */     return !localBlock.d();
/*     */   }
/*     */   
/*     */   public void e()
/*     */   {
/*  91 */     this.d.getControllerLook().a(this.e, 10.0F, this.d.bQ());
/*  92 */     if (this.d.isSitting()) {
/*  93 */       return;
/*     */     }
/*     */     
/*  96 */     if (--this.h > 0) {
/*  97 */       return;
/*     */     }
/*  99 */     this.h = 10;
/*     */     
/* 101 */     if (this.g.a(this.e, this.f)) {
/* 102 */       return;
/*     */     }
/* 104 */     if (this.d.cc()) {
/* 105 */       return;
/*     */     }
/* 107 */     if (this.d.h(this.e) < 144.0D) {
/* 108 */       return;
/*     */     }
/*     */     
/*     */ 
/* 112 */     int j = MathHelper.floor(this.e.locX) - 2;
/* 113 */     int k = MathHelper.floor(this.e.locZ) - 2;
/* 114 */     int m = MathHelper.floor(this.e.getBoundingBox().b);
/* 115 */     for (int n = 0; n <= 4; n++) {
/* 116 */       for (int i1 = 0; i1 <= 4; i1++) {
/* 117 */         if ((n < 1) || (i1 < 1) || (n > 3) || (i1 > 3))
/*     */         {
/*     */ 
/* 120 */           if ((World.a(this.a, new BlockPosition(j + n, m - 1, k + i1))) && (a(new BlockPosition(j + n, m, k + i1))) && (a(new BlockPosition(j + n, m + 1, k + i1)))) {
/* 121 */             this.d.setPositionRotation(j + n + 0.5F, m, k + i1 + 0.5F, this.d.yaw, this.d.pitch);
/* 122 */             this.g.n();
/* 123 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalFollowOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */