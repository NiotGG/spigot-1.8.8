/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalRandomLookaround extends PathfinderGoal {
/*    */   private EntityInsentient a;
/*    */   private double b;
/*    */   private double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalRandomLookaround(EntityInsentient paramEntityInsentient) {
/* 12 */     this.a = paramEntityInsentient;
/* 13 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 18 */     return this.a.bc().nextFloat() < 0.02F;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 23 */     return this.d >= 0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 28 */     double d1 = 6.283185307179586D * this.a.bc().nextDouble();
/* 29 */     this.b = Math.cos(d1);
/* 30 */     this.c = Math.sin(d1);
/* 31 */     this.d = (20 + this.a.bc().nextInt(20));
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 36 */     this.d -= 1;
/* 37 */     this.a.getControllerLook().a(this.a.locX + this.b, this.a.locY + this.a.getHeadHeight(), this.a.locZ + this.c, 10.0F, this.a.bQ());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalRandomLookaround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */