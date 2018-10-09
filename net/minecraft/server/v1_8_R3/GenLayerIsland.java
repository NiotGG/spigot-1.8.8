/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class GenLayerIsland extends GenLayer {
/*    */   public GenLayerIsland(long paramLong, GenLayer paramGenLayer) {
/*  5 */     super(paramLong);
/*  6 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 11 */     int i = paramInt1 - 1;
/* 12 */     int j = paramInt2 - 1;
/* 13 */     int k = paramInt3 + 2;
/* 14 */     int m = paramInt4 + 2;
/* 15 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 17 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/* 18 */     for (int n = 0; n < paramInt4; n++) {
/* 19 */       for (int i1 = 0; i1 < paramInt3; i1++) {
/* 20 */         int i2 = arrayOfInt1[(i1 + 0 + (n + 0) * k)];
/* 21 */         int i3 = arrayOfInt1[(i1 + 2 + (n + 0) * k)];
/* 22 */         int i4 = arrayOfInt1[(i1 + 0 + (n + 2) * k)];
/* 23 */         int i5 = arrayOfInt1[(i1 + 2 + (n + 2) * k)];
/* 24 */         int i6 = arrayOfInt1[(i1 + 1 + (n + 1) * k)];
/* 25 */         a(i1 + paramInt1, n + paramInt2);
/* 26 */         if ((i6 == 0) && ((i2 != 0) || (i3 != 0) || (i4 != 0) || (i5 != 0))) {
/* 27 */           int i7 = 1;
/* 28 */           int i8 = 1;
/* 29 */           if ((i2 != 0) && (a(i7++) == 0)) {
/* 30 */             i8 = i2;
/*    */           }
/* 32 */           if ((i3 != 0) && (a(i7++) == 0)) {
/* 33 */             i8 = i3;
/*    */           }
/* 35 */           if ((i4 != 0) && (a(i7++) == 0)) {
/* 36 */             i8 = i4;
/*    */           }
/* 38 */           if ((i5 != 0) && (a(i7++) == 0)) {
/* 39 */             i8 = i5;
/*    */           }
/* 41 */           if (a(3) == 0) {
/* 42 */             arrayOfInt2[(i1 + n * paramInt3)] = i8;
/*    */           }
/* 44 */           else if (i8 == 4) {
/* 45 */             arrayOfInt2[(i1 + n * paramInt3)] = 4;
/*    */           } else {
/* 47 */             arrayOfInt2[(i1 + n * paramInt3)] = 0;
/*    */           }
/*    */         }
/* 50 */         else if ((i6 > 0) && ((i2 == 0) || (i3 == 0) || (i4 == 0) || (i5 == 0))) {
/* 51 */           if (a(5) == 0) {
/* 52 */             if (i6 == 4) {
/* 53 */               arrayOfInt2[(i1 + n * paramInt3)] = 4;
/*    */             } else {
/* 55 */               arrayOfInt2[(i1 + n * paramInt3)] = 0;
/*    */             }
/*    */           } else {
/* 58 */             arrayOfInt2[(i1 + n * paramInt3)] = i6;
/*    */           }
/*    */         } else {
/* 61 */           arrayOfInt2[(i1 + n * paramInt3)] = i6;
/*    */         }
/*    */       }
/*    */     }
/* 65 */     return arrayOfInt2;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerIsland.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */