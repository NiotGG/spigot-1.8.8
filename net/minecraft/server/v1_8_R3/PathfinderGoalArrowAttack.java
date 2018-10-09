/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalArrowAttack
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private final EntityInsentient a;
/*    */   
/*    */   private final IRangedEntity b;
/*    */   
/*    */   private EntityLiving c;
/*    */   
/* 13 */   private int d = -1;
/*    */   private double e;
/*    */   private int f;
/*    */   private int g;
/*    */   private int h;
/*    */   private float i;
/*    */   private float j;
/*    */   
/*    */   public PathfinderGoalArrowAttack(IRangedEntity paramIRangedEntity, double paramDouble, int paramInt, float paramFloat) {
/* 22 */     this(paramIRangedEntity, paramDouble, paramInt, paramInt, paramFloat);
/*    */   }
/*    */   
/*    */   public PathfinderGoalArrowAttack(IRangedEntity paramIRangedEntity, double paramDouble, int paramInt1, int paramInt2, float paramFloat) {
/* 26 */     if (!(paramIRangedEntity instanceof EntityLiving)) {
/* 27 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*    */     }
/* 29 */     this.b = paramIRangedEntity;
/* 30 */     this.a = ((EntityInsentient)paramIRangedEntity);
/* 31 */     this.e = paramDouble;
/* 32 */     this.g = paramInt1;
/* 33 */     this.h = paramInt2;
/* 34 */     this.i = paramFloat;
/* 35 */     this.j = (paramFloat * paramFloat);
/* 36 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 41 */     EntityLiving localEntityLiving = this.a.getGoalTarget();
/* 42 */     if (localEntityLiving == null) {
/* 43 */       return false;
/*    */     }
/* 45 */     this.c = localEntityLiving;
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 51 */     return (a()) || (!this.a.getNavigation().m());
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 56 */     this.c = null;
/* 57 */     this.f = 0;
/* 58 */     this.d = -1;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 63 */     double d1 = this.a.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
/* 64 */     boolean bool = this.a.getEntitySenses().a(this.c);
/*    */     
/* 66 */     if (bool) {
/* 67 */       this.f += 1;
/*    */     } else {
/* 69 */       this.f = 0;
/*    */     }
/*    */     
/* 72 */     if ((d1 > this.j) || (this.f < 20)) {
/* 73 */       this.a.getNavigation().a(this.c, this.e);
/*    */     } else {
/* 75 */       this.a.getNavigation().n();
/*    */     }
/*    */     
/* 78 */     this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
/*    */     float f1;
/* 80 */     if (--this.d == 0) {
/* 81 */       if ((d1 > this.j) || (!bool)) {
/* 82 */         return;
/*    */       }
/*    */       
/* 85 */       f1 = MathHelper.sqrt(d1) / this.i;
/* 86 */       float f2 = f1;
/* 87 */       f2 = MathHelper.a(f2, 0.1F, 1.0F);
/*    */       
/* 89 */       this.b.a(this.c, f2);
/* 90 */       this.d = MathHelper.d(f1 * (this.h - this.g) + this.g);
/* 91 */     } else if (this.d < 0) {
/* 92 */       f1 = MathHelper.sqrt(d1) / this.i;
/* 93 */       this.d = MathHelper.d(f1 * (this.h - this.g) + this.g);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalArrowAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */