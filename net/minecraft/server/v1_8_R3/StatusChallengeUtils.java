/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ 
/*    */ public class StatusChallengeUtils
/*    */ {
/*  7 */   public static final char[] a = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*    */   {
/* 16 */     int i = paramInt2 - 1;
/* 17 */     int j = paramInt1 > i ? i : paramInt1;
/* 18 */     while ((0 != paramArrayOfByte[j]) && (j < i)) {
/* 19 */       j++;
/*    */     }
/*    */     
/* 22 */     return new String(paramArrayOfByte, paramInt1, j - paramInt1, Charsets.UTF_8);
/*    */   }
/*    */   
/*    */   public static int b(byte[] paramArrayOfByte, int paramInt) {
/* 26 */     return b(paramArrayOfByte, paramInt, paramArrayOfByte.length);
/*    */   }
/*    */   
/*    */   public static int b(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 30 */     if (0 > paramInt2 - paramInt1 - 4)
/*    */     {
/*    */ 
/* 33 */       return 0;
/*    */     }
/* 35 */     return paramArrayOfByte[(paramInt1 + 3)] << 24 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 8 | paramArrayOfByte[paramInt1] & 0xFF;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static int c(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*    */   {
/* 43 */     if (0 > paramInt2 - paramInt1 - 4)
/*    */     {
/*    */ 
/* 46 */       return 0;
/*    */     }
/* 48 */     return paramArrayOfByte[paramInt1] << 24 | (paramArrayOfByte[(paramInt1 + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt1 + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt1 + 3)] & 0xFF;
/*    */   }
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
/*    */   public static String a(byte paramByte)
/*    */   {
/* 62 */     return "" + a[((paramByte & 0xF0) >>> 4)] + a[(paramByte & 0xF)];
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StatusChallengeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */