/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.security.MessageDigest;
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
/*    */ public final class NameUtil
/*    */ {
/*    */   private static final int MASK = 255;
/*    */   
/*    */   public static String getSubName(String paramString)
/*    */   {
/* 32 */     if (paramString.isEmpty()) {
/* 33 */       return null;
/*    */     }
/* 35 */     int i = paramString.lastIndexOf('.');
/* 36 */     return i > 0 ? paramString.substring(0, i) : "";
/*    */   }
/*    */   
/*    */   public static String md5(String paramString) {
/*    */     try {
/* 41 */       MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
/* 42 */       localMessageDigest.update(paramString.getBytes());
/* 43 */       byte[] arrayOfByte1 = localMessageDigest.digest();
/* 44 */       StringBuilder localStringBuilder = new StringBuilder();
/* 45 */       for (int k : arrayOfByte1) {
/* 46 */         String str = Integer.toHexString(0xFF & k);
/* 47 */         if (str.length() == 1) {
/* 48 */           localStringBuilder.append('0');
/*    */         }
/* 50 */         localStringBuilder.append(str);
/*    */       }
/* 52 */       return localStringBuilder.toString();
/*    */     } catch (Exception localException) {}
/* 54 */     return paramString;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\NameUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */