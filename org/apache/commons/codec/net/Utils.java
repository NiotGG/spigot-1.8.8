/*    */ package org.apache.commons.codec.net;
/*    */ 
/*    */ import org.apache.commons.codec.DecoderException;
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
/*    */ class Utils
/*    */ {
/*    */   static int digit16(byte paramByte)
/*    */     throws DecoderException
/*    */   {
/* 43 */     int i = Character.digit((char)paramByte, 16);
/* 44 */     if (i == -1) {
/* 45 */       throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + paramByte);
/*    */     }
/* 47 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\net\Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */