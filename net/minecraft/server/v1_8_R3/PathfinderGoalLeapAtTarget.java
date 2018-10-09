/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalLeapAtTarget
/*    */   extends PathfinderGoal
/*    */ {
/*    */   EntityInsentient a;
/*    */   EntityLiving b;
/*    */   float c;
/*    */   
/*    */   public PathfinderGoalLeapAtTarget(EntityInsentient paramEntityInsentient, float paramFloat)
/*    */   {
/* 14 */     this.a = paramEntityInsentient;
/* 15 */     this.c = paramFloat;
/* 16 */     a(5);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 21 */     this.b = this.a.getGoalTarget();
/* 22 */     if (this.b == null) {
/* 23 */       return false;
/*    */     }
/* 25 */     double d = this.a.h(this.b);
/* 26 */     if ((d < 4.0D) || (d > 16.0D)) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (!this.a.onGround) {
/* 30 */       return false;
/*    */     }
/* 32 */     if (this.a.bc().nextInt(5) != 0) {
/* 33 */       return false;
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 40 */     return !this.a.onGround;
/*    */   }
/*    */   
/*    */ 
/*    */   public void c()
/*    */   {
/* 46 */     double d1 = this.b.locX - this.a.locX;
/* 47 */     double d2 = this.b.locZ - this.a.locZ;
/* 48 */     float f = MathHelper.sqrt(d1 * d1 + d2 * d2);
/* 49 */     this.a.motX += d1 / f * 0.5D * 0.800000011920929D + this.a.motX * 0.20000000298023224D;
/* 50 */     this.a.motZ += d2 / f * 0.5D * 0.800000011920929D + this.a.motZ * 0.20000000298023224D;
/* 51 */     this.a.motY = this.c;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalLeapAtTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */