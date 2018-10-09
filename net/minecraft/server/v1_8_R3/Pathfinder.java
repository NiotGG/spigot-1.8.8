/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pathfinder
/*     */ {
/*  10 */   private Path a = new Path();
/*     */   
/*  12 */   private PathPoint[] b = new PathPoint[32];
/*     */   private PathfinderAbstract c;
/*     */   
/*     */   public Pathfinder(PathfinderAbstract paramPathfinderAbstract)
/*     */   {
/*  17 */     this.c = paramPathfinderAbstract;
/*     */   }
/*     */   
/*     */   public PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity1, Entity paramEntity2, float paramFloat)
/*     */   {
/*  22 */     return a(paramIBlockAccess, paramEntity1, paramEntity2.locX, paramEntity2.getBoundingBox().b, paramEntity2.locZ, paramFloat);
/*     */   }
/*     */   
/*     */   public PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity, BlockPosition paramBlockPosition, float paramFloat)
/*     */   {
/*  27 */     return a(paramIBlockAccess, paramEntity, paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, paramFloat);
/*     */   }
/*     */   
/*     */   private PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat)
/*     */   {
/*  32 */     this.a.a();
/*  33 */     this.c.a(paramIBlockAccess, paramEntity);
/*  34 */     PathPoint localPathPoint1 = this.c.a(paramEntity);
/*  35 */     PathPoint localPathPoint2 = this.c.a(paramEntity, paramDouble1, paramDouble2, paramDouble3);
/*     */     
/*  37 */     PathEntity localPathEntity = a(paramEntity, localPathPoint1, localPathPoint2, paramFloat);
/*     */     
/*  39 */     this.c.a();
/*  40 */     return localPathEntity;
/*     */   }
/*     */   
/*     */ 
/*     */   private PathEntity a(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, float paramFloat)
/*     */   {
/*  46 */     paramPathPoint1.e = 0.0F;
/*  47 */     paramPathPoint1.f = paramPathPoint1.b(paramPathPoint2);
/*  48 */     paramPathPoint1.g = paramPathPoint1.f;
/*     */     
/*  50 */     this.a.a();
/*  51 */     this.a.a(paramPathPoint1);
/*     */     
/*  53 */     Object localObject = paramPathPoint1;
/*     */     
/*  55 */     while (!this.a.e()) {
/*  56 */       PathPoint localPathPoint1 = this.a.c();
/*     */       
/*  58 */       if (localPathPoint1.equals(paramPathPoint2)) {
/*  59 */         return a(paramPathPoint1, paramPathPoint2);
/*     */       }
/*     */       
/*  62 */       if (localPathPoint1.b(paramPathPoint2) < ((PathPoint)localObject).b(paramPathPoint2)) {
/*  63 */         localObject = localPathPoint1;
/*     */       }
/*  65 */       localPathPoint1.i = true;
/*     */       
/*  67 */       int i = this.c.a(this.b, paramEntity, localPathPoint1, paramPathPoint2, paramFloat);
/*  68 */       for (int j = 0; j < i; j++) {
/*  69 */         PathPoint localPathPoint2 = this.b[j];
/*     */         
/*  71 */         float f = localPathPoint1.e + localPathPoint1.b(localPathPoint2);
/*  72 */         if ((f < paramFloat * 2.0F) && ((!localPathPoint2.a()) || (f < localPathPoint2.e))) {
/*  73 */           localPathPoint2.h = localPathPoint1;
/*  74 */           localPathPoint2.e = f;
/*  75 */           localPathPoint2.f = localPathPoint2.b(paramPathPoint2);
/*  76 */           if (localPathPoint2.a()) {
/*  77 */             this.a.a(localPathPoint2, localPathPoint2.e + localPathPoint2.f);
/*     */           } else {
/*  79 */             localPathPoint2.g = (localPathPoint2.e + localPathPoint2.f);
/*  80 */             this.a.a(localPathPoint2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  86 */     if (localObject == paramPathPoint1) {
/*  87 */       return null;
/*     */     }
/*  89 */     return a(paramPathPoint1, (PathPoint)localObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private PathEntity a(PathPoint paramPathPoint1, PathPoint paramPathPoint2)
/*     */   {
/* 102 */     int i = 1;
/* 103 */     PathPoint localPathPoint = paramPathPoint2;
/* 104 */     while (localPathPoint.h != null) {
/* 105 */       i++;
/* 106 */       localPathPoint = localPathPoint.h;
/*     */     }
/*     */     
/* 109 */     PathPoint[] arrayOfPathPoint = new PathPoint[i];
/* 110 */     localPathPoint = paramPathPoint2;
/* 111 */     arrayOfPathPoint[(--i)] = localPathPoint;
/* 112 */     while (localPathPoint.h != null) {
/* 113 */       localPathPoint = localPathPoint.h;
/* 114 */       arrayOfPathPoint[(--i)] = localPathPoint;
/*     */     }
/* 116 */     return new PathEntity(arrayOfPathPoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Pathfinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */