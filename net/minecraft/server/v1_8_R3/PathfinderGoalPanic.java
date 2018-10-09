/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalPanic extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature b;
/*    */   protected double a;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/*    */   public PathfinderGoalPanic(EntityCreature entitycreature, double d0) {
/* 12 */     this.b = entitycreature;
/* 13 */     this.a = d0;
/* 14 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 18 */     if ((this.b.getLastDamager() == null) && (!this.b.isBurning())) {
/* 19 */       return false;
/*    */     }
/* 21 */     Vec3D vec3d = RandomPositionGenerator.a(this.b, 5, 4);
/*    */     
/* 23 */     if (vec3d == null) {
/* 24 */       return false;
/*    */     }
/* 26 */     this.c = vec3d.a;
/* 27 */     this.d = vec3d.b;
/* 28 */     this.e = vec3d.c;
/* 29 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void c()
/*    */   {
/* 35 */     this.b.getNavigation().a(this.c, this.d, this.e, this.a);
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 40 */     if (this.b.ticksLived - this.b.hurtTimestamp > 100) {
/* 41 */       this.b.b(null);
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     return !this.b.getNavigation().m();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalPanic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */