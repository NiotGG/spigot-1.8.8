/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalRestrictOpenDoor
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   
/*    */   private VillageDoor b;
/*    */   
/*    */ 
/*    */   public PathfinderGoalRestrictOpenDoor(EntityCreature paramEntityCreature)
/*    */   {
/* 14 */     this.a = paramEntityCreature;
/* 15 */     if (!(paramEntityCreature.getNavigation() instanceof Navigation)) {
/* 16 */       throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 22 */     if (this.a.world.w()) {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     BlockPosition localBlockPosition = new BlockPosition(this.a);
/*    */     
/* 28 */     Village localVillage = this.a.world.ae().getClosestVillage(localBlockPosition, 16);
/* 29 */     if (localVillage == null) {
/* 30 */       return false;
/*    */     }
/* 32 */     this.b = localVillage.b(localBlockPosition);
/* 33 */     if (this.b == null) {
/* 34 */       return false;
/*    */     }
/* 36 */     return this.b.b(localBlockPosition) < 2.25D;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 41 */     if (this.a.world.w()) {
/* 42 */       return false;
/*    */     }
/* 44 */     return (!this.b.i()) && (this.b.c(new BlockPosition(this.a)));
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 49 */     ((Navigation)this.a.getNavigation()).b(false);
/* 50 */     ((Navigation)this.a.getNavigation()).c(false);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 55 */     ((Navigation)this.a.getNavigation()).b(true);
/* 56 */     ((Navigation)this.a.getNavigation()).c(true);
/* 57 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 62 */     this.b.b();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalRestrictOpenDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */