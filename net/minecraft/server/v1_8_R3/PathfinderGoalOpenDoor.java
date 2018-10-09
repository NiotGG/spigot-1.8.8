/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class PathfinderGoalOpenDoor extends PathfinderGoalDoorInteract
/*    */ {
/*    */   boolean g;
/*    */   int h;
/*    */   
/*    */   public PathfinderGoalOpenDoor(EntityInsentient paramEntityInsentient, boolean paramBoolean)
/*    */   {
/* 10 */     super(paramEntityInsentient);
/* 11 */     this.a = paramEntityInsentient;
/* 12 */     this.g = paramBoolean;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 17 */     return (this.g) && (this.h > 0) && (super.b());
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 22 */     this.h = 20;
/* 23 */     this.c.setDoor(this.a.world, this.b, true);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 28 */     if (this.g) {
/* 29 */       this.c.setDoor(this.a.world, this.b, false);
/*    */     }
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 35 */     this.h -= 1;
/* 36 */     super.e();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalOpenDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */