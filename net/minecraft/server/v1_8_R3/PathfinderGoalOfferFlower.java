/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalOfferFlower
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityIronGolem a;
/*    */   private EntityVillager b;
/*    */   private int c;
/*    */   
/*    */   public PathfinderGoalOfferFlower(EntityIronGolem paramEntityIronGolem)
/*    */   {
/* 15 */     this.a = paramEntityIronGolem;
/* 16 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 21 */     if (!this.a.world.w()) {
/* 22 */       return false;
/*    */     }
/* 24 */     if (this.a.bc().nextInt(8000) != 0) {
/* 25 */       return false;
/*    */     }
/* 27 */     this.b = ((EntityVillager)this.a.world.a(EntityVillager.class, this.a.getBoundingBox().grow(6.0D, 2.0D, 6.0D), this.a));
/* 28 */     return this.b != null;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 33 */     return this.c > 0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 38 */     this.c = 400;
/* 39 */     this.a.a(true);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 44 */     this.a.a(false);
/* 45 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 50 */     this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
/* 51 */     this.c -= 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalOfferFlower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */