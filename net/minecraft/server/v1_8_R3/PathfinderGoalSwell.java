/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalSwell extends PathfinderGoal
/*    */ {
/*    */   EntityCreeper a;
/*    */   EntityLiving b;
/*    */   
/*    */   public PathfinderGoalSwell(EntityCreeper entitycreeper) {
/*  9 */     this.a = entitycreeper;
/* 10 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 14 */     EntityLiving entityliving = this.a.getGoalTarget();
/*    */     
/* 16 */     return (this.a.cm() > 0) || ((entityliving != null) && (this.a.h(entityliving) < 9.0D));
/*    */   }
/*    */   
/*    */   public void c() {
/* 20 */     this.a.getNavigation().n();
/* 21 */     this.b = this.a.getGoalTarget();
/*    */   }
/*    */   
/*    */   public void d() {
/* 25 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e() {
/* 29 */     if (this.b == null) {
/* 30 */       this.a.a(-1);
/* 31 */     } else if (this.a.h(this.b) > 49.0D) {
/* 32 */       this.a.a(-1);
/* 33 */     } else if (!this.a.getEntitySenses().a(this.b)) {
/* 34 */       this.a.a(-1);
/*    */     } else {
/* 36 */       this.a.a(1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalSwell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */