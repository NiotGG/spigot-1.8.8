/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalBeg
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityWolf a;
/*    */   private EntityHuman b;
/*    */   private World c;
/*    */   private float d;
/*    */   private int e;
/*    */   
/*    */   public PathfinderGoalBeg(EntityWolf paramEntityWolf, float paramFloat)
/*    */   {
/* 18 */     this.a = paramEntityWolf;
/* 19 */     this.c = paramEntityWolf.world;
/* 20 */     this.d = paramFloat;
/* 21 */     a(2);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 26 */     this.b = this.c.findNearbyPlayer(this.a, this.d);
/* 27 */     if (this.b == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     return a(this.b);
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 35 */     if (!this.b.isAlive()) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (this.a.h(this.b) > this.d * this.d) {
/* 39 */       return false;
/*    */     }
/* 41 */     return (this.e > 0) && (a(this.b));
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 46 */     this.a.p(true);
/* 47 */     this.e = (40 + this.a.bc().nextInt(40));
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 52 */     this.a.p(false);
/* 53 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 58 */     this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0F, this.a.bQ());
/* 59 */     this.e -= 1;
/*    */   }
/*    */   
/*    */   private boolean a(EntityHuman paramEntityHuman) {
/* 63 */     ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
/* 64 */     if (localItemStack == null) {
/* 65 */       return false;
/*    */     }
/* 67 */     if ((!this.a.isTamed()) && (localItemStack.getItem() == Items.BONE)) {
/* 68 */       return true;
/*    */     }
/* 70 */     return this.a.d(localItemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalBeg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */