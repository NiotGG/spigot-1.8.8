/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalRestrictSun
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   
/*    */   public PathfinderGoalRestrictSun(EntityCreature paramEntityCreature)
/*    */   {
/* 10 */     this.a = paramEntityCreature;
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 15 */     return this.a.world.w();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 20 */     ((Navigation)this.a.getNavigation()).e(true);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 25 */     ((Navigation)this.a.getNavigation()).e(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalRestrictSun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */