/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalSit extends PathfinderGoal
/*    */ {
/*    */   private EntityTameableAnimal entity;
/*    */   private boolean willSit;
/*    */   
/*    */   public PathfinderGoalSit(EntityTameableAnimal entitytameableanimal) {
/*  9 */     this.entity = entitytameableanimal;
/* 10 */     a(5);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 14 */     if (!this.entity.isTamed())
/* 15 */       return (this.willSit) && (this.entity.getGoalTarget() == null);
/* 16 */     if (this.entity.V())
/* 17 */       return false;
/* 18 */     if (!this.entity.onGround) {
/* 19 */       return false;
/*    */     }
/* 21 */     EntityLiving entityliving = this.entity.getOwner();
/*    */     
/* 23 */     return (this.entity.h(entityliving) < 144.0D) && (entityliving.getLastDamager() != null) ? false : entityliving == null ? true : this.willSit;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 28 */     this.entity.getNavigation().n();
/* 29 */     this.entity.setSitting(true);
/*    */   }
/*    */   
/*    */   public void d() {
/* 33 */     this.entity.setSitting(false);
/*    */   }
/*    */   
/*    */   public void setSitting(boolean flag) {
/* 37 */     this.willSit = flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalSit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */