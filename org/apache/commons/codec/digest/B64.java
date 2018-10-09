/*    */ package org.apache.commons.codec.digest;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class B64
/*    */ {
/*    */   static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
/*    */   
/*    */   static void b64from24bit(byte paramByte1, byte paramByte2, byte paramByte3, int paramInt, StringBuilder paramStringBuilder)
/*    */   {
/* 57 */     int i = paramByte1 << 16 & 0xFFFFFF | paramByte2 << 8 & 0xFFFF | paramByte3 & 0xFF;
/*    */     
/* 59 */     int j = paramInt;
/* 60 */     while (j-- > 0) {
/* 61 */       paramStringBuilder.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(i & 0x3F));
/* 62 */       i >>= 6;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static String getRandomSalt(int paramInt)
/*    */   {
/* 73 */     StringBuilder localStringBuilder = new StringBuilder();
/* 74 */     for (int i = 1; i <= paramInt; i++) {
/* 75 */       localStringBuilder.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(new Random().nextInt("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
/*    */     }
/* 77 */     return localStringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\digest\B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */