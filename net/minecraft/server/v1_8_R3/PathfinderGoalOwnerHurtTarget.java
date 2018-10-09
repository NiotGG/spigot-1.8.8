/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*    */ 
/*    */ public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {
/*    */   EntityTameableAnimal a;
/*    */   EntityLiving b;
/*    */   private int c;
/*    */   
/* 10 */   public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal entitytameableanimal) { super(entitytameableanimal, false);
/* 11 */     this.a = entitytameableanimal;
/* 12 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 16 */     if (!this.a.isTamed()) {
/* 17 */       return false;
/*    */     }
/* 19 */     EntityLiving entityliving = this.a.getOwner();
/*    */     
/* 21 */     if (entityliving == null) {
/* 22 */       return false;
/*    */     }
/* 24 */     this.b = entityliving.bf();
/* 25 */     int i = entityliving.bg();
/*    */     
/* 27 */     return (i != this.c) && (a(this.b, false)) && (this.a.a(this.b, entityliving));
/*    */   }
/*    */   
/*    */ 
/*    */   public void c()
/*    */   {
/* 33 */     this.e.setGoalTarget(this.b, EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET, true);
/* 34 */     EntityLiving entityliving = this.a.getOwner();
/*    */     
/* 36 */     if (entityliving != null) {
/* 37 */       this.c = entityliving.bg();
/*    */     }
/*    */     
/* 40 */     super.c();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalOwnerHurtTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */