/*     */ package org.apache.commons.codec.digest;
/*     */ 
/*     */ import org.apache.commons.codec.Charsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crypt
/*     */ {
/*     */   public static String crypt(byte[] paramArrayOfByte)
/*     */   {
/*  46 */     return crypt(paramArrayOfByte, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String crypt(byte[] paramArrayOfByte, String paramString)
/*     */   {
/*  66 */     if (paramString == null)
/*  67 */       return Sha2Crypt.sha512Crypt(paramArrayOfByte);
/*  68 */     if (paramString.startsWith("$6$"))
/*  69 */       return Sha2Crypt.sha512Crypt(paramArrayOfByte, paramString);
/*  70 */     if (paramString.startsWith("$5$"))
/*  71 */       return Sha2Crypt.sha256Crypt(paramArrayOfByte, paramString);
/*  72 */     if (paramString.startsWith("$1$")) {
/*  73 */       return Md5Crypt.md5Crypt(paramArrayOfByte, paramString);
/*     */     }
/*  75 */     return UnixCrypt.crypt(paramArrayOfByte, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String crypt(String paramString)
/*     */   {
/*  92 */     return crypt(paramString, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String crypt(String paramString1, String paramString2)
/*     */   {
/* 149 */     return crypt(paramString1.getBytes(Charsets.UTF_8), paramString2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\digest\Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */