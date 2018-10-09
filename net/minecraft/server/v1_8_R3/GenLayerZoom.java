/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class GenLayerZoom
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerZoom(long paramLong, GenLayer paramGenLayer)
/*    */   {
/*  9 */     super(paramLong);
/* 10 */     this.a = paramGenLayer;
/*    */   }
/*    */   
/*    */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 15 */     int i = paramInt1 >> 1;
/* 16 */     int j = paramInt2 >> 1;
/* 17 */     int k = (paramInt3 >> 1) + 2;
/* 18 */     int m = (paramInt4 >> 1) + 2;
/* 19 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*    */     
/* 21 */     int n = k - 1 << 1;
/* 22 */     int i1 = m - 1 << 1;
/*    */     
/* 24 */     int[] arrayOfInt2 = IntCache.a(n * i1);
/*    */     
/* 26 */     for (int i2 = 0; i2 < m - 1; i2++) {
/* 27 */       i3 = (i2 << 1) * n;
/*    */       
/* 29 */       int i4 = 0;
/* 30 */       int i5 = arrayOfInt1[(i4 + 0 + (i2 + 0) * k)];
/* 31 */       int i6 = arrayOfInt1[(i4 + 0 + (i2 + 1) * k)];
/* 32 */       for (; i4 < k - 1; i4++) {
/* 33 */         a(i4 + i << 1, i2 + j << 1);
/*    */         
/* 35 */         int i7 = arrayOfInt1[(i4 + 1 + (i2 + 0) * k)];
/* 36 */         int i8 = arrayOfInt1[(i4 + 1 + (i2 + 1) * k)];
/*    */         
/* 38 */         arrayOfInt2[i3] = i5;
/* 39 */         arrayOfInt2[(i3++ + n)] = a(new int[] { i5, i6 });
/* 40 */         arrayOfInt2[i3] = a(new int[] { i5, i7 });
/* 41 */         arrayOfInt2[(i3++ + n)] = b(i5, i7, i6, i8);
/*    */         
/* 43 */         i5 = i7;
/* 44 */         i6 = i8;
/*    */       }
/*    */     }
/*    */     
/* 48 */     int[] arrayOfInt3 = IntCache.a(paramInt3 * paramInt4);
/* 49 */     for (int i3 = 0; i3 < paramInt4; i3++) {
/* 50 */       System.arraycopy(arrayOfInt2, (i3 + (paramInt2 & 0x1)) * n + (paramInt1 & 0x1), arrayOfInt3, i3 * paramInt3, paramInt3);
/*    */     }
/* 52 */     return arrayOfInt3;
/*    */   }
/*    */   
/*    */   public static GenLayer b(long paramLong, GenLayer paramGenLayer, int paramInt) {
/* 56 */     Object localObject = paramGenLayer;
/* 57 */     for (int i = 0; i < paramInt; i++) {
/* 58 */       localObject = new GenLayerZoom(paramLong + i, (GenLayer)localObject);
/*    */     }
/* 60 */     return (GenLayer)localObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GenLayerZoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */