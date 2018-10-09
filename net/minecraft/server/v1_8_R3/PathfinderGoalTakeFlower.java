/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalTakeFlower
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   private EntityIronGolem b;
/*    */   private int c;
/*    */   private boolean d;
/*    */   
/*    */   public PathfinderGoalTakeFlower(EntityVillager paramEntityVillager)
/*    */   {
/* 16 */     this.a = paramEntityVillager;
/* 17 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 22 */     if (this.a.getAge() >= 0) {
/* 23 */       return false;
/*    */     }
/* 25 */     if (!this.a.world.w()) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     List localList = this.a.world.a(EntityIronGolem.class, this.a.getBoundingBox().grow(6.0D, 2.0D, 6.0D));
/* 30 */     if (localList.isEmpty()) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     for (EntityIronGolem localEntityIronGolem : localList) {
/* 35 */       if (localEntityIronGolem.cm() > 0) {
/* 36 */         this.b = localEntityIronGolem;
/* 37 */         break;
/*    */       }
/*    */     }
/* 40 */     return this.b != null;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 45 */     return this.b.cm() > 0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 50 */     this.c = this.a.bc().nextInt(320);
/* 51 */     this.d = false;
/* 52 */     this.b.getNavigation().n();
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 57 */     this.b = null;
/* 58 */     this.a.getNavigation().n();
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 63 */     this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
/* 64 */     if (this.b.cm() == this.c) {
/* 65 */       this.a.getNavigation().a(this.b, 0.5D);
/* 66 */       this.d = true;
/*    */     }
/*    */     
/* 69 */     if ((this.d) && 
/* 70 */       (this.a.h(this.b) < 4.0D)) {
/* 71 */       this.b.a(false);
/* 72 */       this.a.getNavigation().n();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTakeFlower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */