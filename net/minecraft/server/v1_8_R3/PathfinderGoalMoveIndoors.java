/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveIndoors
/*    */   extends PathfinderGoal
/*    */ {
/* 16 */   private int d = -1; private int c = -1;
/*    */   private VillageDoor b;
/*    */   
/* 19 */   public PathfinderGoalMoveIndoors(EntityCreature paramEntityCreature) { this.a = paramEntityCreature;
/* 20 */     a(1);
/*    */   }
/*    */   
/*    */   private EntityCreature a;
/*    */   public boolean a() {
/* 25 */     BlockPosition localBlockPosition = new BlockPosition(this.a);
/* 26 */     if (((this.a.world.w()) && ((!this.a.world.S()) || (this.a.world.getBiome(localBlockPosition).e()))) || (this.a.world.worldProvider.o())) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (this.a.bc().nextInt(50) != 0) {
/* 30 */       return false;
/*    */     }
/* 32 */     if ((this.c != -1) && (this.a.e(this.c, this.a.locY, this.d) < 4.0D)) {
/* 33 */       return false;
/*    */     }
/* 35 */     Village localVillage = this.a.world.ae().getClosestVillage(localBlockPosition, 14);
/* 36 */     if (localVillage == null) {
/* 37 */       return false;
/*    */     }
/* 39 */     this.b = localVillage.c(localBlockPosition);
/* 40 */     return this.b != null;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 45 */     return !this.a.getNavigation().m();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 50 */     this.c = -1;
/* 51 */     BlockPosition localBlockPosition = this.b.e();
/* 52 */     int i = localBlockPosition.getX();
/* 53 */     int j = localBlockPosition.getY();
/* 54 */     int k = localBlockPosition.getZ();
/*    */     
/* 56 */     if (this.a.b(localBlockPosition) > 256.0D) {
/* 57 */       Vec3D localVec3D = RandomPositionGenerator.a(this.a, 14, 3, new Vec3D(i + 0.5D, j, k + 0.5D));
/* 58 */       if (localVec3D != null) {
/* 59 */         this.a.getNavigation().a(localVec3D.a, localVec3D.b, localVec3D.c, 1.0D);
/*    */       }
/*    */     } else {
/* 62 */       this.a.getNavigation().a(i + 0.5D, j, k + 0.5D, 1.0D);
/*    */     }
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 68 */     this.c = this.b.e().getX();
/* 69 */     this.d = this.b.e().getZ();
/* 70 */     this.b = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMoveIndoors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */