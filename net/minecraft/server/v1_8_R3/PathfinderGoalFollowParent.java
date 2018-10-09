/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class PathfinderGoalFollowParent extends PathfinderGoal
/*    */ {
/*    */   EntityAnimal a;
/*    */   EntityAnimal b;
/*    */   double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalFollowParent(EntityAnimal paramEntityAnimal, double paramDouble)
/*    */   {
/* 14 */     this.a = paramEntityAnimal;
/* 15 */     this.c = paramDouble;
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 20 */     if (this.a.getAge() >= 0) {
/* 21 */       return false;
/*    */     }
/*    */     
/* 24 */     List localList = this.a.world.a(this.a.getClass(), this.a.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
/*    */     
/* 26 */     Object localObject = null;
/* 27 */     double d1 = Double.MAX_VALUE;
/* 28 */     for (EntityAnimal localEntityAnimal : localList)
/* 29 */       if (localEntityAnimal.getAge() >= 0)
/*    */       {
/*    */ 
/* 32 */         double d2 = this.a.h(localEntityAnimal);
/* 33 */         if (d2 <= d1)
/*    */         {
/*    */ 
/* 36 */           d1 = d2;
/* 37 */           localObject = localEntityAnimal;
/*    */         }
/*    */       }
/* 40 */     if (localObject == null) {
/* 41 */       return false;
/*    */     }
/* 43 */     if (d1 < 9.0D) {
/* 44 */       return false;
/*    */     }
/* 46 */     this.b = ((EntityAnimal)localObject);
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 52 */     if (this.a.getAge() >= 0) {
/* 53 */       return false;
/*    */     }
/* 55 */     if (!this.b.isAlive()) {
/* 56 */       return false;
/*    */     }
/* 58 */     double d1 = this.a.h(this.b);
/* 59 */     if ((d1 < 9.0D) || (d1 > 256.0D)) {
/* 60 */       return false;
/*    */     }
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 67 */     this.d = 0;
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 72 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 77 */     if (--this.d > 0) {
/* 78 */       return;
/*    */     }
/* 80 */     this.d = 10;
/* 81 */     this.a.getNavigation().a(this.b, this.c);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalFollowParent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */