/*     */ package org.apache.commons.codec.digest;
/*     */ 
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class Md5Crypt
/*     */ {
/*     */   static final String APR1_PREFIX = "$apr1$";
/*     */   private static final int BLOCKSIZE = 16;
/*     */   static final String MD5_PREFIX = "$1$";
/*     */   private static final int ROUNDS = 1000;
/*     */   
/*     */   public static String apr1Crypt(byte[] paramArrayOfByte)
/*     */   {
/*  72 */     return apr1Crypt(paramArrayOfByte, "$apr1$" + B64.getRandomSalt(8));
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
/*     */   public static String apr1Crypt(byte[] paramArrayOfByte, String paramString)
/*     */   {
/*  89 */     if ((paramString != null) && (!paramString.startsWith("$apr1$"))) {
/*  90 */       paramString = "$apr1$" + paramString;
/*     */     }
/*  92 */     return md5Crypt(paramArrayOfByte, paramString, "$apr1$");
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
/*     */   public static String apr1Crypt(String paramString)
/*     */   {
/* 105 */     return apr1Crypt(paramString.getBytes(Charsets.UTF_8));
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
/*     */   public static String apr1Crypt(String paramString1, String paramString2)
/*     */   {
/* 126 */     return apr1Crypt(paramString1.getBytes(Charsets.UTF_8), paramString2);
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
/*     */   public static String md5Crypt(byte[] paramArrayOfByte)
/*     */   {
/* 141 */     return md5Crypt(paramArrayOfByte, "$1$" + B64.getRandomSalt(8));
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
/*     */   public static String md5Crypt(byte[] paramArrayOfByte, String paramString)
/*     */   {
/* 161 */     return md5Crypt(paramArrayOfByte, paramString, "$1$");
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
/*     */   public static String md5Crypt(byte[] paramArrayOfByte, String paramString1, String paramString2)
/*     */   {
/* 180 */     int i = paramArrayOfByte.length;
/*     */     
/*     */     String str;
/*     */     
/* 184 */     if (paramString1 == null) {
/* 185 */       str = B64.getRandomSalt(8);
/*     */     } else {
/* 187 */       localObject1 = Pattern.compile("^" + paramString2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*");
/* 188 */       localObject2 = ((Pattern)localObject1).matcher(paramString1);
/* 189 */       if ((localObject2 == null) || (!((Matcher)localObject2).find())) {
/* 190 */         throw new IllegalArgumentException("Invalid salt value: " + paramString1);
/*     */       }
/* 192 */       str = ((Matcher)localObject2).group(1);
/*     */     }
/* 194 */     Object localObject1 = str.getBytes(Charsets.UTF_8);
/*     */     
/* 196 */     Object localObject2 = DigestUtils.getMd5Digest();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 201 */     ((MessageDigest)localObject2).update(paramArrayOfByte);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 206 */     ((MessageDigest)localObject2).update(paramString2.getBytes(Charsets.UTF_8));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 211 */     ((MessageDigest)localObject2).update((byte[])localObject1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 216 */     MessageDigest localMessageDigest = DigestUtils.getMd5Digest();
/* 217 */     localMessageDigest.update(paramArrayOfByte);
/* 218 */     localMessageDigest.update((byte[])localObject1);
/* 219 */     localMessageDigest.update(paramArrayOfByte);
/* 220 */     byte[] arrayOfByte = localMessageDigest.digest();
/* 221 */     int j = i;
/* 222 */     while (j > 0) {
/* 223 */       ((MessageDigest)localObject2).update(arrayOfByte, 0, j > 16 ? 16 : j);
/* 224 */       j -= 16;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 230 */     Arrays.fill(arrayOfByte, (byte)0);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 235 */     j = i;
/* 236 */     int k = 0;
/* 237 */     while (j > 0) {
/* 238 */       if ((j & 0x1) == 1) {
/* 239 */         ((MessageDigest)localObject2).update(arrayOfByte[0]);
/*     */       } else {
/* 241 */         ((MessageDigest)localObject2).update(paramArrayOfByte[0]);
/*     */       }
/* 243 */       j >>= 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 249 */     StringBuilder localStringBuilder = new StringBuilder(paramString2 + str + "$");
/* 250 */     arrayOfByte = ((MessageDigest)localObject2).digest();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 256 */     for (int m = 0; m < 1000; m++) {
/* 257 */       localMessageDigest = DigestUtils.getMd5Digest();
/* 258 */       if ((m & 0x1) != 0) {
/* 259 */         localMessageDigest.update(paramArrayOfByte);
/*     */       } else {
/* 261 */         localMessageDigest.update(arrayOfByte, 0, 16);
/*     */       }
/*     */       
/* 264 */       if (m % 3 != 0) {
/* 265 */         localMessageDigest.update((byte[])localObject1);
/*     */       }
/*     */       
/* 268 */       if (m % 7 != 0) {
/* 269 */         localMessageDigest.update(paramArrayOfByte);
/*     */       }
/*     */       
/* 272 */       if ((m & 0x1) != 0) {
/* 273 */         localMessageDigest.update(arrayOfByte, 0, 16);
/*     */       } else {
/* 275 */         localMessageDigest.update(paramArrayOfByte);
/*     */       }
/* 277 */       arrayOfByte = localMessageDigest.digest();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 283 */     B64.b64from24bit(arrayOfByte[0], arrayOfByte[6], arrayOfByte[12], 4, localStringBuilder);
/* 284 */     B64.b64from24bit(arrayOfByte[1], arrayOfByte[7], arrayOfByte[13], 4, localStringBuilder);
/* 285 */     B64.b64from24bit(arrayOfByte[2], arrayOfByte[8], arrayOfByte[14], 4, localStringBuilder);
/* 286 */     B64.b64from24bit(arrayOfByte[3], arrayOfByte[9], arrayOfByte[15], 4, localStringBuilder);
/* 287 */     B64.b64from24bit(arrayOfByte[4], arrayOfByte[10], arrayOfByte[5], 4, localStringBuilder);
/* 288 */     B64.b64from24bit((byte)0, (byte)0, arrayOfByte[11], 2, localStringBuilder);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 294 */     ((MessageDigest)localObject2).reset();
/* 295 */     localMessageDigest.reset();
/* 296 */     Arrays.fill(paramArrayOfByte, (byte)0);
/* 297 */     Arrays.fill((byte[])localObject1, (byte)0);
/* 298 */     Arrays.fill(arrayOfByte, (byte)0);
/*     */     
/* 300 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\digest\Md5Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */