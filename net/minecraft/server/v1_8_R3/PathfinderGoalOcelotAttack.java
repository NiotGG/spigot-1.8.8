/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalOcelotAttack
/*    */   extends PathfinderGoal
/*    */ {
/*    */   World a;
/*    */   
/*    */   EntityInsentient b;
/*    */   
/*    */   EntityLiving c;
/*    */   
/*    */   int d;
/*    */   
/*    */ 
/*    */   public PathfinderGoalOcelotAttack(EntityInsentient paramEntityInsentient)
/*    */   {
/* 18 */     this.b = paramEntityInsentient;
/* 19 */     this.a = paramEntityInsentient.world;
/* 20 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 25 */     EntityLiving localEntityLiving = this.b.getGoalTarget();
/* 26 */     if (localEntityLiving == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     this.c = localEntityLiving;
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 35 */     if (!this.c.isAlive()) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (this.b.h(this.c) > 225.0D) {
/* 39 */       return false;
/*    */     }
/* 41 */     return (!this.b.getNavigation().m()) || (a());
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 46 */     this.c = null;
/* 47 */     this.b.getNavigation().n();
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 52 */     this.b.getControllerLook().a(this.c, 30.0F, 30.0F);
/*    */     
/* 54 */     double d1 = this.b.width * 2.0F * (this.b.width * 2.0F);
/* 55 */     double d2 = this.b.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
/*    */     
/* 57 */     double d3 = 0.8D;
/* 58 */     if ((d2 > d1) && (d2 < 16.0D)) {
/* 59 */       d3 = 1.33D;
/* 60 */     } else if (d2 < 225.0D) {
/* 61 */       d3 = 0.6D;
/*    */     }
/*    */     
/* 64 */     this.b.getNavigation().a(this.c, d3);
/*    */     
/* 66 */     this.d = Math.max(this.d - 1, 0);
/*    */     
/* 68 */     if (d2 > d1) {
/* 69 */       return;
/*    */     }
/* 71 */     if (this.d > 0) {
/* 72 */       return;
/*    */     }
/* 74 */     this.d = 20;
/* 75 */     this.b.r(this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalOcelotAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */