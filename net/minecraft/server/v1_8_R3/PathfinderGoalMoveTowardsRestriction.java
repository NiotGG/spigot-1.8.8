/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveTowardsRestriction
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/*    */   public PathfinderGoalMoveTowardsRestriction(EntityCreature paramEntityCreature, double paramDouble)
/*    */   {
/* 15 */     this.a = paramEntityCreature;
/* 16 */     this.e = paramDouble;
/* 17 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 22 */     if (this.a.cg()) {
/* 23 */       return false;
/*    */     }
/* 25 */     BlockPosition localBlockPosition = this.a.ch();
/* 26 */     Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 7, new Vec3D(localBlockPosition.getX(), localBlockPosition.getY(), localBlockPosition.getZ()));
/* 27 */     if (localVec3D == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     this.b = localVec3D.a;
/* 31 */     this.c = localVec3D.b;
/* 32 */     this.d = localVec3D.c;
/* 33 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 38 */     return !this.a.getNavigation().m();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 43 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMoveTowardsRestriction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */