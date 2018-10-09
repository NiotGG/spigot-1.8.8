/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.entity.EntityBreakDoorEvent;
/*    */ 
/*    */ public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract { private int g;
/*  6 */   private int h = -1;
/*    */   
/*    */   public PathfinderGoalBreakDoor(EntityInsentient entityinsentient) {
/*  9 */     super(entityinsentient);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 13 */     if (!super.a())
/* 14 */       return false;
/* 15 */     if (!this.a.world.getGameRules().getBoolean("mobGriefing")) {
/* 16 */       return false;
/*    */     }
/*    */     
/*    */ 
/* 20 */     return !BlockDoor.f(this.a.world, this.b);
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 25 */     super.c();
/* 26 */     this.g = 0;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 30 */     double d0 = this.a.b(this.b);
/*    */     
/*    */ 
/* 33 */     if (this.g <= 240)
/*    */     {
/*    */ 
/* 36 */       if ((!BlockDoor.f(this.a.world, this.b)) && (d0 < 4.0D)) {
/* 37 */         boolean flag = true;
/* 38 */         return flag;
/*    */       }
/*    */     }
/*    */     
/* 42 */     boolean flag = false;
/* 43 */     return flag;
/*    */   }
/*    */   
/*    */   public void d() {
/* 47 */     super.d();
/* 48 */     this.a.world.c(this.a.getId(), this.b, -1);
/*    */   }
/*    */   
/*    */   public void e() {
/* 52 */     super.e();
/* 53 */     if (this.a.bc().nextInt(20) == 0) {
/* 54 */       this.a.world.triggerEffect(1010, this.b, 0);
/*    */     }
/*    */     
/* 57 */     this.g += 1;
/* 58 */     int i = (int)(this.g / 240.0F * 10.0F);
/*    */     
/* 60 */     if (i != this.h) {
/* 61 */       this.a.world.c(this.a.getId(), this.b, i);
/* 62 */       this.h = i;
/*    */     }
/*    */     
/* 65 */     if ((this.g == 240) && (this.a.world.getDifficulty() == EnumDifficulty.HARD))
/*    */     {
/* 67 */       if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityBreakDoorEvent(this.a, this.b.getX(), this.b.getY(), this.b.getZ()).isCancelled()) {
/* 68 */         c();
/* 69 */         return;
/*    */       }
/*    */       
/* 72 */       this.a.world.setAir(this.b);
/* 73 */       this.a.world.triggerEffect(1012, this.b, 0);
/* 74 */       this.a.world.triggerEffect(2001, this.b, Block.getId(this.c));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalBreakDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */