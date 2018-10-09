/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalFleeSun
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private World f;
/*    */   
/*    */   public PathfinderGoalFleeSun(EntityCreature paramEntityCreature, double paramDouble)
/*    */   {
/* 18 */     this.a = paramEntityCreature;
/* 19 */     this.e = paramDouble;
/* 20 */     this.f = paramEntityCreature.world;
/* 21 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 26 */     if (!this.f.w()) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (!this.a.isBurning()) {
/* 30 */       return false;
/*    */     }
/* 32 */     if (!this.f.i(new BlockPosition(this.a.locX, this.a.getBoundingBox().b, this.a.locZ))) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     Vec3D localVec3D = f();
/* 37 */     if (localVec3D == null) {
/* 38 */       return false;
/*    */     }
/* 40 */     this.b = localVec3D.a;
/* 41 */     this.c = localVec3D.b;
/* 42 */     this.d = localVec3D.c;
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 48 */     return !this.a.getNavigation().m();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 53 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */   
/*    */   private Vec3D f() {
/* 57 */     Random localRandom = this.a.bc();
/* 58 */     BlockPosition localBlockPosition1 = new BlockPosition(this.a.locX, this.a.getBoundingBox().b, this.a.locZ);
/*    */     
/* 60 */     for (int i = 0; i < 10; i++) {
/* 61 */       BlockPosition localBlockPosition2 = localBlockPosition1.a(localRandom.nextInt(20) - 10, localRandom.nextInt(6) - 3, localRandom.nextInt(20) - 10);
/*    */       
/* 63 */       if ((!this.f.i(localBlockPosition2)) && (this.a.a(localBlockPosition2) < 0.0F)) {
/* 64 */         return new Vec3D(localBlockPosition2.getX(), localBlockPosition2.getY(), localBlockPosition2.getZ());
/*    */       }
/*    */     }
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalFleeSun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */