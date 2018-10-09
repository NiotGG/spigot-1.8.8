/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalDefendVillage extends PathfinderGoalTarget {
/*    */   EntityIronGolem a;
/*    */   EntityLiving b;
/*    */   
/*  9 */   public PathfinderGoalDefendVillage(EntityIronGolem entityirongolem) { super(entityirongolem, false, true);
/* 10 */     this.a = entityirongolem;
/* 11 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 15 */     Village village = this.a.n();
/*    */     
/* 17 */     if (village == null) {
/* 18 */       return false;
/*    */     }
/* 20 */     this.b = village.b(this.a);
/* 21 */     if ((this.b instanceof EntityCreeper))
/* 22 */       return false;
/* 23 */     if (!a(this.b, false)) {
/* 24 */       if (this.e.bc().nextInt(20) == 0) {
/* 25 */         this.b = village.c(this.a);
/* 26 */         return a(this.b, false);
/*    */       }
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void c()
/*    */   {
/* 37 */     this.a.setGoalTarget(this.b, org.bukkit.event.entity.EntityTargetEvent.TargetReason.DEFEND_VILLAGE, true);
/* 38 */     super.c();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalDefendVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */