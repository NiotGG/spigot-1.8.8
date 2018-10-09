/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderWater
/*    */   extends PathfinderAbstract
/*    */ {
/*    */   public void a(IBlockAccess paramIBlockAccess, Entity paramEntity)
/*    */   {
/* 20 */     super.a(paramIBlockAccess, paramEntity);
/*    */   }
/*    */   
/*    */   public void a()
/*    */   {
/* 25 */     super.a();
/*    */   }
/*    */   
/*    */   public PathPoint a(Entity paramEntity)
/*    */   {
/* 30 */     return a(MathHelper.floor(paramEntity.getBoundingBox().a), MathHelper.floor(paramEntity.getBoundingBox().b + 0.5D), MathHelper.floor(paramEntity.getBoundingBox().c));
/*    */   }
/*    */   
/*    */   public PathPoint a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3)
/*    */   {
/* 35 */     return a(MathHelper.floor(paramDouble1 - paramEntity.width / 2.0F), MathHelper.floor(paramDouble2 + 0.5D), MathHelper.floor(paramDouble3 - paramEntity.width / 2.0F));
/*    */   }
/*    */   
/*    */   public int a(PathPoint[] paramArrayOfPathPoint, Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, float paramFloat)
/*    */   {
/* 40 */     int i = 0;
/*    */     
/* 42 */     for (EnumDirection localEnumDirection : EnumDirection.values()) {
/* 43 */       PathPoint localPathPoint = a(paramEntity, paramPathPoint1.a + localEnumDirection.getAdjacentX(), paramPathPoint1.b + localEnumDirection.getAdjacentY(), paramPathPoint1.c + localEnumDirection.getAdjacentZ());
/* 44 */       if ((localPathPoint != null) && (!localPathPoint.i) && (localPathPoint.a(paramPathPoint2) < paramFloat)) {
/* 45 */         paramArrayOfPathPoint[(i++)] = localPathPoint;
/*    */       }
/*    */     }
/*    */     
/* 49 */     return i;
/*    */   }
/*    */   
/*    */   private PathPoint a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3) {
/* 53 */     int i = b(paramEntity, paramInt1, paramInt2, paramInt3);
/* 54 */     if (i == -1) {
/* 55 */       return a(paramInt1, paramInt2, paramInt3);
/*    */     }
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   private int b(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3) {
/* 61 */     BlockPosition.MutableBlockPosition localMutableBlockPosition = new BlockPosition.MutableBlockPosition();
/* 62 */     for (int i = paramInt1; i < paramInt1 + this.c; i++) {
/* 63 */       for (int j = paramInt2; j < paramInt2 + this.d; j++) {
/* 64 */         for (int k = paramInt3; k < paramInt3 + this.e; k++) {
/* 65 */           Block localBlock = this.a.getType(localMutableBlockPosition.c(i, j, k)).getBlock();
/* 66 */           if (localBlock.getMaterial() != Material.WATER) {
/* 67 */             return 0;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 72 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */