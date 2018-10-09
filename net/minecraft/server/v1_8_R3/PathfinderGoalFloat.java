/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalFloat extends PathfinderGoal
/*    */ {
/*    */   private EntityInsentient a;
/*    */   
/*    */   public PathfinderGoalFloat(EntityInsentient paramEntityInsentient)
/*    */   {
/* 11 */     this.a = paramEntityInsentient;
/* 12 */     a(4);
/* 13 */     ((Navigation)paramEntityInsentient.getNavigation()).d(true);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 18 */     return (this.a.V()) || (this.a.ab());
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 23 */     if (this.a.bc().nextFloat() < 0.8F) {
/* 24 */       this.a.getControllerJump().a();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */