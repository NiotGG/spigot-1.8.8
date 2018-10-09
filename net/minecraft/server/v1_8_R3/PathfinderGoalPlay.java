/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalPlay
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   private EntityLiving b;
/*    */   private double c;
/*    */   private int d;
/*    */   
/*    */   public PathfinderGoalPlay(EntityVillager paramEntityVillager, double paramDouble)
/*    */   {
/* 18 */     this.a = paramEntityVillager;
/* 19 */     this.c = paramDouble;
/* 20 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 25 */     if (this.a.getAge() >= 0) {
/* 26 */       return false;
/*    */     }
/* 28 */     if (this.a.bc().nextInt(400) != 0) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     List localList = this.a.world.a(EntityVillager.class, this.a.getBoundingBox().grow(6.0D, 3.0D, 6.0D));
/* 33 */     double d1 = Double.MAX_VALUE;
/* 34 */     for (Object localObject = localList.iterator(); ((Iterator)localObject).hasNext();) { EntityVillager localEntityVillager = (EntityVillager)((Iterator)localObject).next();
/* 35 */       if ((localEntityVillager != this.a) && 
/*    */       
/*    */ 
/* 38 */         (!localEntityVillager.cn()) && 
/*    */         
/*    */ 
/* 41 */         (localEntityVillager.getAge() < 0))
/*    */       {
/*    */ 
/* 44 */         double d2 = localEntityVillager.h(this.a);
/* 45 */         if (d2 <= d1)
/*    */         {
/*    */ 
/* 48 */           d1 = d2;
/* 49 */           this.b = localEntityVillager;
/*    */         }
/*    */       } }
/* 52 */     if (this.b == null) {
/* 53 */       localObject = RandomPositionGenerator.a(this.a, 16, 3);
/* 54 */       if (localObject == null) {
/* 55 */         return false;
/*    */       }
/*    */     }
/* 58 */     return true;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 63 */     return this.d > 0;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 68 */     if (this.b != null) {
/* 69 */       this.a.m(true);
/*    */     }
/* 71 */     this.d = 1000;
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 76 */     this.a.m(false);
/* 77 */     this.b = null;
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 82 */     this.d -= 1;
/* 83 */     if (this.b != null) {
/* 84 */       if (this.a.h(this.b) > 4.0D) {
/* 85 */         this.a.getNavigation().a(this.b, this.c);
/*    */       }
/*    */     }
/* 88 */     else if (this.a.getNavigation().m()) {
/* 89 */       Vec3D localVec3D = RandomPositionGenerator.a(this.a, 16, 3);
/* 90 */       if (localVec3D == null) {
/* 91 */         return;
/*    */       }
/* 93 */       this.a.getNavigation().a(localVec3D.a, localVec3D.b, localVec3D.c, this.c);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalPlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */