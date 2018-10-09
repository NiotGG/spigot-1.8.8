/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavigationGuardian
/*    */   extends NavigationAbstract
/*    */ {
/*    */   public NavigationGuardian(EntityInsentient paramEntityInsentient, World paramWorld)
/*    */   {
/* 12 */     super(paramEntityInsentient, paramWorld);
/*    */   }
/*    */   
/*    */   protected Pathfinder a()
/*    */   {
/* 17 */     return new Pathfinder(new PathfinderWater());
/*    */   }
/*    */   
/*    */   protected boolean b()
/*    */   {
/* 22 */     return o();
/*    */   }
/*    */   
/*    */   protected Vec3D c()
/*    */   {
/* 27 */     return new Vec3D(this.b.locX, this.b.locY + this.b.length * 0.5D, this.b.locZ);
/*    */   }
/*    */   
/*    */   protected void l()
/*    */   {
/* 32 */     Vec3D localVec3D1 = c();
/*    */     
/*    */ 
/* 35 */     float f = this.b.width * this.b.width;
/* 36 */     int i = 6;
/* 37 */     if (localVec3D1.distanceSquared(this.d.a(this.b, this.d.e())) < f) {
/* 38 */       this.d.a();
/*    */     }
/*    */     
/* 41 */     for (int j = Math.min(this.d.e() + i, this.d.d() - 1); j > this.d.e(); j--) {
/* 42 */       Vec3D localVec3D2 = this.d.a(this.b, j);
/* 43 */       if (localVec3D2.distanceSquared(localVec3D1) <= 36.0D)
/*    */       {
/*    */ 
/* 46 */         if (a(localVec3D1, localVec3D2, 0, 0, 0)) {
/* 47 */           this.d.c(j);
/* 48 */           break;
/*    */         }
/*    */       }
/*    */     }
/* 52 */     a(localVec3D1);
/*    */   }
/*    */   
/*    */   protected void d()
/*    */   {
/* 57 */     super.d();
/*    */   }
/*    */   
/*    */   protected boolean a(Vec3D paramVec3D1, Vec3D paramVec3D2, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 62 */     MovingObjectPosition localMovingObjectPosition = this.c.rayTrace(paramVec3D1, new Vec3D(paramVec3D2.a, paramVec3D2.b + this.b.length * 0.5D, paramVec3D2.c), false, true, false);
/* 63 */     return (localMovingObjectPosition == null) || (localMovingObjectPosition.type == MovingObjectPosition.EnumMovingObjectType.MISS);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NavigationGuardian.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */