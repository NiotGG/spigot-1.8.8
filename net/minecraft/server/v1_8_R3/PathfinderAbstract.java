/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PathfinderAbstract
/*    */ {
/*    */   protected IBlockAccess a;
/*    */   
/*    */ 
/* 11 */   protected IntHashMap<PathPoint> b = new IntHashMap();
/*    */   
/*    */   protected int c;
/*    */   
/*    */   protected int d;
/*    */   
/*    */   protected int e;
/*    */   
/*    */   public void a(IBlockAccess paramIBlockAccess, Entity paramEntity)
/*    */   {
/* 21 */     this.a = paramIBlockAccess;
/* 22 */     this.b.c();
/*    */     
/* 24 */     this.c = MathHelper.d(paramEntity.width + 1.0F);
/* 25 */     this.d = MathHelper.d(paramEntity.length + 1.0F);
/* 26 */     this.e = MathHelper.d(paramEntity.width + 1.0F);
/*    */   }
/*    */   
/*    */   public void a() {}
/*    */   
/*    */   protected PathPoint a(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 33 */     int i = PathPoint.a(paramInt1, paramInt2, paramInt3);
/* 34 */     PathPoint localPathPoint = (PathPoint)this.b.get(i);
/*    */     
/* 36 */     if (localPathPoint == null) {
/* 37 */       localPathPoint = new PathPoint(paramInt1, paramInt2, paramInt3);
/* 38 */       this.b.a(i, localPathPoint);
/*    */     }
/*    */     
/* 41 */     return localPathPoint;
/*    */   }
/*    */   
/*    */   public abstract PathPoint a(Entity paramEntity);
/*    */   
/*    */   public abstract PathPoint a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   public abstract int a(PathPoint[] paramArrayOfPathPoint, Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, float paramFloat);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */