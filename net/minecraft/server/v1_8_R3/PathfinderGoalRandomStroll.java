/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalRandomStroll extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private int f;
/*    */   private boolean g;
/*    */   
/*    */   public PathfinderGoalRandomStroll(EntityCreature paramEntityCreature, double paramDouble)
/*    */   {
/* 17 */     this(paramEntityCreature, paramDouble, 120);
/*    */   }
/*    */   
/*    */   public PathfinderGoalRandomStroll(EntityCreature paramEntityCreature, double paramDouble, int paramInt) {
/* 21 */     this.a = paramEntityCreature;
/* 22 */     this.e = paramDouble;
/* 23 */     this.f = paramInt;
/* 24 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 29 */     if (!this.g) {
/* 30 */       if (this.a.bh() >= 100) {
/* 31 */         return false;
/*    */       }
/* 33 */       if (this.a.bc().nextInt(this.f) != 0) {
/* 34 */         return false;
/*    */       }
/*    */     }
/*    */     
/* 38 */     Vec3D localVec3D = RandomPositionGenerator.a(this.a, 10, 7);
/* 39 */     if (localVec3D == null) {
/* 40 */       return false;
/*    */     }
/* 42 */     this.b = localVec3D.a;
/* 43 */     this.c = localVec3D.b;
/* 44 */     this.d = localVec3D.c;
/* 45 */     this.g = false;
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 51 */     return !this.a.getNavigation().m();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 56 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */   
/*    */   public void f() {
/* 60 */     this.g = true;
/*    */   }
/*    */   
/*    */   public void setTimeBetweenMovement(int paramInt) {
/* 64 */     this.f = paramInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalRandomStroll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */