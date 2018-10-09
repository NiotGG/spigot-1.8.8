/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveTowardsTarget
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private EntityLiving b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private double f;
/*    */   private float g;
/*    */   
/*    */   public PathfinderGoalMoveTowardsTarget(EntityCreature paramEntityCreature, double paramDouble, float paramFloat)
/*    */   {
/* 17 */     this.a = paramEntityCreature;
/* 18 */     this.f = paramDouble;
/* 19 */     this.g = paramFloat;
/* 20 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 25 */     this.b = this.a.getGoalTarget();
/* 26 */     if (this.b == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (this.b.h(this.a) > this.g * this.g) {
/* 30 */       return false;
/*    */     }
/* 32 */     Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, new Vec3D(this.b.locX, this.b.locY, this.b.locZ));
/* 33 */     if (localVec3D == null) {
/* 34 */       return false;
/*    */     }
/* 36 */     this.c = localVec3D.a;
/* 37 */     this.d = localVec3D.b;
/* 38 */     this.e = localVec3D.c;
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 44 */     return (!this.a.getNavigation().m()) && (this.b.isAlive()) && (this.b.h(this.a) < this.g * this.g);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 49 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 54 */     this.a.getNavigation().a(this.c, this.d, this.e, this.f);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMoveTowardsTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */