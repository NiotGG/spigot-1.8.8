/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalTradeWithPlayer
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   
/*    */ 
/*    */   public PathfinderGoalTradeWithPlayer(EntityVillager paramEntityVillager)
/*    */   {
/* 12 */     this.a = paramEntityVillager;
/* 13 */     a(5);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 18 */     if (!this.a.isAlive()) {
/* 19 */       return false;
/*    */     }
/* 21 */     if (this.a.V()) {
/* 22 */       return false;
/*    */     }
/* 24 */     if (!this.a.onGround) {
/* 25 */       return false;
/*    */     }
/* 27 */     if (this.a.velocityChanged) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     EntityHuman localEntityHuman = this.a.v_();
/* 32 */     if (localEntityHuman == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     if (this.a.h(localEntityHuman) > 16.0D)
/*    */     {
/* 39 */       return false;
/*    */     }
/*    */     
/* 42 */     if (!(localEntityHuman.activeContainer instanceof Container))
/*    */     {
/* 44 */       return false;
/*    */     }
/*    */     
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 52 */     this.a.getNavigation().n();
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 57 */     this.a.a_(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTradeWithPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */