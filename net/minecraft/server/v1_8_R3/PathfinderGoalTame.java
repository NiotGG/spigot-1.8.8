/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalTame extends PathfinderGoal {
/*    */   private EntityHorse entity;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/* 12 */   public PathfinderGoalTame(EntityHorse entityhorse, double d0) { this.entity = entityhorse;
/* 13 */     this.b = d0;
/* 14 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 18 */     if ((!this.entity.isTame()) && (this.entity.passenger != null)) {
/* 19 */       Vec3D vec3d = RandomPositionGenerator.a(this.entity, 5, 4);
/*    */       
/* 21 */       if (vec3d == null) {
/* 22 */         return false;
/*    */       }
/* 24 */       this.c = vec3d.a;
/* 25 */       this.d = vec3d.b;
/* 26 */       this.e = vec3d.c;
/* 27 */       return true;
/*    */     }
/*    */     
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 35 */     this.entity.getNavigation().a(this.c, this.d, this.e, this.b);
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 39 */     return (!this.entity.getNavigation().m()) && (this.entity.passenger != null);
/*    */   }
/*    */   
/*    */   public void e() {
/* 43 */     if (this.entity.bc().nextInt(50) == 0) {
/* 44 */       if ((this.entity.passenger instanceof EntityHuman)) {
/* 45 */         int i = this.entity.getTemper();
/* 46 */         int j = this.entity.getMaxDomestication();
/*    */         
/*    */ 
/* 49 */         if ((j > 0) && (this.entity.bc().nextInt(j) < i) && (!org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityTameEvent(this.entity, (EntityHuman)this.entity.passenger).isCancelled()) && ((this.entity.passenger instanceof EntityHuman))) {
/* 50 */           this.entity.h((EntityHuman)this.entity.passenger);
/* 51 */           this.entity.world.broadcastEntityEffect(this.entity, (byte)7);
/* 52 */           return;
/*    */         }
/*    */         
/* 55 */         this.entity.u(5);
/*    */       }
/*    */       
/*    */ 
/* 59 */       if (this.entity.passenger != null) {
/* 60 */         this.entity.passenger.mount(null);
/*    */         
/* 62 */         if (this.entity.passenger != null) {
/* 63 */           return;
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 68 */       this.entity.cW();
/* 69 */       this.entity.world.broadcastEntityEffect(this.entity, (byte)6);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */