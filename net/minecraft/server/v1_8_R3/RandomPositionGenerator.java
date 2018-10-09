/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomPositionGenerator
/*    */ {
/* 12 */   private static Vec3D a = new Vec3D(0.0D, 0.0D, 0.0D);
/*    */   
/*    */   public static Vec3D a(EntityCreature paramEntityCreature, int paramInt1, int paramInt2) {
/* 15 */     return c(paramEntityCreature, paramInt1, paramInt2, null);
/*    */   }
/*    */   
/*    */   public static Vec3D a(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
/* 19 */     a = paramVec3D.a(paramEntityCreature.locX, paramEntityCreature.locY, paramEntityCreature.locZ);
/* 20 */     return c(paramEntityCreature, paramInt1, paramInt2, a);
/*    */   }
/*    */   
/*    */   public static Vec3D b(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
/* 24 */     a = new Vec3D(paramEntityCreature.locX, paramEntityCreature.locY, paramEntityCreature.locZ).d(paramVec3D);
/* 25 */     return c(paramEntityCreature, paramInt1, paramInt2, a);
/*    */   }
/*    */   
/*    */   private static Vec3D c(EntityCreature paramEntityCreature, int paramInt1, int paramInt2, Vec3D paramVec3D) {
/* 29 */     Random localRandom = paramEntityCreature.bc();
/* 30 */     int i = 0;
/* 31 */     int j = 0;int k = 0;int m = 0;
/* 32 */     float f1 = -99999.0F;
/*    */     
/*    */     int n;
/* 35 */     if (paramEntityCreature.ck()) {
/* 36 */       double d1 = paramEntityCreature.ch().c(MathHelper.floor(paramEntityCreature.locX), MathHelper.floor(paramEntityCreature.locY), MathHelper.floor(paramEntityCreature.locZ)) + 4.0D;
/* 37 */       double d2 = paramEntityCreature.ci() + paramInt1;
/* 38 */       n = d1 < d2 * d2 ? 1 : 0;
/*    */     } else {
/* 40 */       n = 0;
/*    */     }
/*    */     
/*    */ 
/* 44 */     for (int i1 = 0; i1 < 10; i1++) {
/* 45 */       int i2 = localRandom.nextInt(2 * paramInt1 + 1) - paramInt1;
/* 46 */       int i3 = localRandom.nextInt(2 * paramInt2 + 1) - paramInt2;
/* 47 */       int i4 = localRandom.nextInt(2 * paramInt1 + 1) - paramInt1;
/*    */       
/* 49 */       if ((paramVec3D == null) || (i2 * paramVec3D.a + i4 * paramVec3D.c >= 0.0D))
/*    */       {
/*    */ 
/*    */ 
/* 53 */         if ((paramEntityCreature.ck()) && (paramInt1 > 1)) {
/* 54 */           localBlockPosition = paramEntityCreature.ch();
/* 55 */           if (paramEntityCreature.locX > localBlockPosition.getX()) {
/* 56 */             i2 -= localRandom.nextInt(paramInt1 / 2);
/*    */           } else {
/* 58 */             i2 += localRandom.nextInt(paramInt1 / 2);
/*    */           }
/* 60 */           if (paramEntityCreature.locZ > localBlockPosition.getZ()) {
/* 61 */             i4 -= localRandom.nextInt(paramInt1 / 2);
/*    */           } else {
/* 63 */             i4 += localRandom.nextInt(paramInt1 / 2);
/*    */           }
/*    */         }
/*    */         
/* 67 */         i2 += MathHelper.floor(paramEntityCreature.locX);
/* 68 */         i3 += MathHelper.floor(paramEntityCreature.locY);
/* 69 */         i4 += MathHelper.floor(paramEntityCreature.locZ);
/*    */         
/* 71 */         BlockPosition localBlockPosition = new BlockPosition(i2, i3, i4);
/*    */         
/* 73 */         if ((n == 0) || (paramEntityCreature.e(localBlockPosition)))
/*    */         {
/*    */ 
/* 76 */           float f2 = paramEntityCreature.a(localBlockPosition);
/* 77 */           if (f2 > f1) {
/* 78 */             f1 = f2;
/* 79 */             j = i2;
/* 80 */             k = i3;
/* 81 */             m = i4;
/* 82 */             i = 1;
/*    */           }
/*    */         } } }
/* 85 */     if (i != 0) {
/* 86 */       return new Vec3D(j, k, m);
/*    */     }
/*    */     
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RandomPositionGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */