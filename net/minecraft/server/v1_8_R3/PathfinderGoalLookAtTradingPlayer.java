/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalLookAtTradingPlayer
/*    */   extends PathfinderGoalLookAtPlayer
/*    */ {
/*    */   private final EntityVillager e;
/*    */   
/*    */   public PathfinderGoalLookAtTradingPlayer(EntityVillager paramEntityVillager)
/*    */   {
/* 10 */     super(paramEntityVillager, EntityHuman.class, 8.0F);
/* 11 */     this.e = paramEntityVillager;
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 16 */     if (this.e.co()) {
/* 17 */       this.b = this.e.v_();
/* 18 */       return true;
/*    */     }
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalLookAtTradingPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */