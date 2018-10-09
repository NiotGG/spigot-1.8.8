/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class PathfinderGoalMakeLove extends PathfinderGoal {
/*    */   private EntityVillager b;
/*    */   private EntityVillager c;
/*    */   private World d;
/*    */   private int e;
/*    */   Village a;
/*    */   
/* 12 */   public PathfinderGoalMakeLove(EntityVillager entityvillager) { this.b = entityvillager;
/* 13 */     this.d = entityvillager.world;
/* 14 */     a(3);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 18 */     if (this.b.getAge() != 0)
/* 19 */       return false;
/* 20 */     if (this.b.bc().nextInt(500) != 0) {
/* 21 */       return false;
/*    */     }
/* 23 */     this.a = this.d.ae().getClosestVillage(new BlockPosition(this.b), 0);
/* 24 */     if (this.a == null)
/* 25 */       return false;
/* 26 */     if ((f()) && (this.b.n(true))) {
/* 27 */       Entity entity = this.d.a(EntityVillager.class, this.b.getBoundingBox().grow(8.0D, 3.0D, 8.0D), this.b);
/*    */       
/* 29 */       if (entity == null) {
/* 30 */         return false;
/*    */       }
/* 32 */       this.c = ((EntityVillager)entity);
/* 33 */       return (this.c.getAge() == 0) && (this.c.n(true));
/*    */     }
/*    */     
/* 36 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   public void c()
/*    */   {
/* 42 */     this.e = 300;
/* 43 */     this.b.l(true);
/*    */   }
/*    */   
/*    */   public void d() {
/* 47 */     this.a = null;
/* 48 */     this.c = null;
/* 49 */     this.b.l(false);
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 53 */     return (this.e >= 0) && (f()) && (this.b.getAge() == 0) && (this.b.n(false));
/*    */   }
/*    */   
/*    */   public void e() {
/* 57 */     this.e -= 1;
/* 58 */     this.b.getControllerLook().a(this.c, 10.0F, 30.0F);
/* 59 */     if (this.b.h(this.c) > 2.25D) {
/* 60 */       this.b.getNavigation().a(this.c, 0.25D);
/* 61 */     } else if ((this.e == 0) && (this.c.cm())) {
/* 62 */       g();
/*    */     }
/*    */     
/* 65 */     if (this.b.bc().nextInt(35) == 0) {
/* 66 */       this.d.broadcastEntityEffect(this.b, (byte)12);
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean f()
/*    */   {
/* 72 */     if (!this.a.i()) {
/* 73 */       return false;
/*    */     }
/* 75 */     int i = (int)(this.a.c() * 0.35D);
/*    */     
/* 77 */     return this.a.e() < i;
/*    */   }
/*    */   
/*    */   private void g()
/*    */   {
/* 82 */     EntityVillager entityvillager = this.b.b(this.c);
/*    */     
/* 84 */     this.c.setAgeRaw(6000);
/* 85 */     this.b.setAgeRaw(6000);
/* 86 */     this.c.o(false);
/* 87 */     this.b.o(false);
/* 88 */     entityvillager.setAgeRaw(41536);
/* 89 */     entityvillager.setPositionRotation(this.b.locX, this.b.locY, this.b.locZ, 0.0F, 0.0F);
/* 90 */     this.d.addEntity(entityvillager, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING);
/* 91 */     this.d.broadcastEntityEffect(entityvillager, (byte)12);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMakeLove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */