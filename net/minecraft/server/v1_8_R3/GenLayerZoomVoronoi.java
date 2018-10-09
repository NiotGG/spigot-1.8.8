/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GenLayerZoomVoronoi
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerZoomVoronoi(long paramLong, GenLayer paramGenLayer)
/*    */   {
/* 10 */     super(paramLong);
/* 11 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 16 */     paramInt1 -= 2;
/* 17 */     paramInt2 -= 2;
/* 18 */     int i = paramInt1 >> 2;
/* 19 */     int j = paramInt2 >> 2;
/* 20 */     int k = (paramInt3 >> 2) + 2;
/* 21 */     int m = (paramInt4 >> 2) + 2;
/* 22 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 24 */     int n = k - 1 << 2;
/* 25 */     int i1 = m - 1 << 2;
/*    */     
/* 27 */     int[] arrayOfInt2 = IntCache.a(n * i1);
/* 28 */     for (int i2 = 0; i2 < m - 1; i2++) {
/* 29 */       i3 = 0;
/* 30 */       int i4 = arrayOfInt1[(i3 + 0 + (i2 + 0) * k)];
/* 31 */       int i5 = arrayOfInt1[(i3 + 0 + (i2 + 1) * k)];
/* 32 */       for (; i3 < k - 1; i3++) {
/* 33 */         double d1 = 3.6D;
/* 34 */         a(i3 + i << 2, i2 + j << 2);
/* 35 */         double d2 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/* 36 */         double d3 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/*    */         
/* 38 */         a(i3 + i + 1 << 2, i2 + j << 2);
/* 39 */         double d4 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 40 */         double d5 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/*    */         
/* 42 */         a(i3 + i << 2, i2 + j + 1 << 2);
/* 43 */         double d6 = (a(1024) / 1024.0D - 0.5D) * 3.6D;
/* 44 */         double d7 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/*    */         
/* 46 */         a(i3 + i + 1 << 2, i2 + j + 1 << 2);
/* 47 */         double d8 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 48 */         double d9 = (a(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/*    */         
/* 50 */         int i6 = arrayOfInt1[(i3 + 1 + (i2 + 0) * k)] & 0xFF;
/* 51 */         int i7 = arrayOfInt1[(i3 + 1 + (i2 + 1) * k)] & 0xFF;
/*    */         
/* 53 */         for (int i8 = 0; i8 < 4; i8++) {
/* 54 */           int i9 = ((i2 << 2) + i8) * n + (i3 << 2);
/* 55 */           for (int i10 = 0; i10 < 4; i10++) {
/* 56 */             double d10 = (i8 - d3) * (i8 - d3) + (i10 - d2) * (i10 - d2);
/* 57 */             double d11 = (i8 - d5) * (i8 - d5) + (i10 - d4) * (i10 - d4);
/* 58 */             double d12 = (i8 - d7) * (i8 - d7) + (i10 - d6) * (i10 - d6);
/* 59 */             double d13 = (i8 - d9) * (i8 - d9) + (i10 - d8) * (i10 - d8);
/*    */             
/* 61 */             if ((d10 < d11) && (d10 < d12) && (d10 < d13)) {
/* 62 */               arrayOfInt2[(i9++)] = i4;
/* 63 */             } else if ((d11 < d10) && (d11 < d12) && (d11 < d13)) {
/* 64 */               arrayOfInt2[(i9++)] = i6;
/* 65 */             } else if ((d12 < d10) && (d12 < d11) && (d12 < d13)) {
/* 66 */               arrayOfInt2[(i9++)] = i5;
/*    */             } else {
/* 68 */               arrayOfInt2[(i9++)] = i7;
/*    */             }
/*    */           }
/*    */         }
/*    */         
/* 73 */         i4 = i6;
/* 74 */         i5 = i7;
/*    */       }
/*    */     }
/*    */     
/* 78 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 79 */     for (int i3 = 0; i3 < paramInt4; i3++) {
/* 80 */       System.arraycopy(arrayOfInt2, (i3 + (paramInt2 & 0x3)) * n + (paramInt1 & 0x3), arrayOfInt3, i3 * paramInt3, paramInt3);
/*    */     }
/* 82 */     return arrayOfInt3;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerZoomVoronoi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */