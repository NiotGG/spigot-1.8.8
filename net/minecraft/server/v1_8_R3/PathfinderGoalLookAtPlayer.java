/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalLookAtPlayer
/*    */   extends PathfinderGoal
/*    */ {
/*    */   protected EntityInsentient a;
/*    */   protected Entity b;
/*    */   protected float c;
/*    */   private int e;
/*    */   private float f;
/*    */   protected Class<? extends Entity> d;
/*    */   
/*    */   public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class<? extends Entity> paramClass, float paramFloat)
/*    */   {
/* 17 */     this.a = paramEntityInsentient;
/* 18 */     this.d = paramClass;
/* 19 */     this.c = paramFloat;
/* 20 */     this.f = 0.02F;
/* 21 */     a(2);
/*    */   }
/*    */   
/*    */   public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class<? extends Entity> paramClass, float paramFloat1, float paramFloat2) {
/* 25 */     this.a = paramEntityInsentient;
/* 26 */     this.d = paramClass;
/* 27 */     this.c = paramFloat1;
/* 28 */     this.f = paramFloat2;
/* 29 */     a(2);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 34 */     if (this.a.bc().nextFloat() >= this.f) {
/* 35 */       return false;
/*    */     }
/*    */     
/* 38 */     if (this.a.getGoalTarget() != null)
/* 39 */       this.b = this.a.getGoalTarget();
/* 40 */     if (this.d == EntityHuman.class) {
/* 41 */       this.b = this.a.world.findNearbyPlayer(this.a, this.c);
/*    */     } else {
/* 43 */       this.b = this.a.world.a(this.d, this.a.getBoundingBox().grow(this.c, 3.0D, this.c), this.a);
/*    */     }
/*    */     
/* 46 */     return this.b != null;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 51 */     if (!this.b.isAlive()) {
/* 52 */       return false;
/*    */     }
/* 54 */     if (this.a.h(this.b) > this.c * this.c) {
/* 55 */       return false;
/*    */     }
/* 57 */     return this.e > 0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 62 */     this.e = (40 + this.a.bc().nextInt(40));
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 67 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 72 */     this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0F, this.a.bQ());
/* 73 */     this.e -= 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalLookAtPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */