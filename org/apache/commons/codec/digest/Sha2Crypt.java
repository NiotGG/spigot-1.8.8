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
/*     */ public class Sha2Crypt
/*     */ {
/*     */   private static final int ROUNDS_DEFAULT = 5000;
/*     */   private static final int ROUNDS_MAX = 999999999;
/*     */   private static final int ROUNDS_MIN = 1000;
/*     */   private static final String ROUNDS_PREFIX = "rounds=";
/*     */   private static final int SHA256_BLOCKSIZE = 32;
/*     */   static final String SHA256_PREFIX = "$5$";
/*     */   private static final int SHA512_BLOCKSIZE = 64;
/*     */   static final String SHA512_PREFIX = "$6$";
/*  68 */   private static final Pattern SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
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
/*     */   public static String sha256Crypt(byte[] paramArrayOfByte)
/*     */   {
/*  83 */     return sha256Crypt(paramArrayOfByte, null);
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
/*     */   public static String sha256Crypt(byte[] paramArrayOfByte, String paramString)
/*     */   {
/* 102 */     if (paramString == null) {
/* 103 */       paramString = "$5$" + B64.getRandomSalt(8);
/*     */     }
/* 105 */     return sha2Crypt(paramArrayOfByte, paramString, "$5$", 32, "SHA-256");
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
/*     */   private static String sha2Crypt(byte[] paramArrayOfByte, String paramString1, String paramString2, int paramInt, String paramString3)
/*     */   {
/* 136 */     int i = paramArrayOfByte.length;
/*     */     
/*     */ 
/* 139 */     int j = 5000;
/* 140 */     int k = 0;
/* 141 */     if (paramString1 == null) {
/* 142 */       throw new IllegalArgumentException("Salt must not be null");
/*     */     }
/*     */     
/* 145 */     Matcher localMatcher = SALT_PATTERN.matcher(paramString1);
/* 146 */     if ((localMatcher == null) || (!localMatcher.find())) {
/* 147 */       throw new IllegalArgumentException("Invalid salt value: " + paramString1);
/*     */     }
/* 149 */     if (localMatcher.group(3) != null) {
/* 150 */       j = Integer.parseInt(localMatcher.group(3));
/* 151 */       j = Math.max(1000, Math.min(999999999, j));
/* 152 */       k = 1;
/*     */     }
/* 154 */     String str = localMatcher.group(4);
/* 155 */     byte[] arrayOfByte1 = str.getBytes(Charsets.UTF_8);
/* 156 */     int m = arrayOfByte1.length;
/*     */     
/*     */ 
/*     */ 
/* 160 */     MessageDigest localMessageDigest1 = DigestUtils.getDigest(paramString3);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 166 */     localMessageDigest1.update(paramArrayOfByte);
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
/* 181 */     localMessageDigest1.update(arrayOfByte1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 188 */     MessageDigest localMessageDigest2 = DigestUtils.getDigest(paramString3);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */     localMessageDigest2.update(paramArrayOfByte);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 200 */     localMessageDigest2.update(arrayOfByte1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 206 */     localMessageDigest2.update(paramArrayOfByte);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 212 */     byte[] arrayOfByte2 = localMessageDigest2.digest();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 222 */     int n = paramArrayOfByte.length;
/* 223 */     while (n > paramInt) {
/* 224 */       localMessageDigest1.update(arrayOfByte2, 0, paramInt);
/* 225 */       n -= paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 230 */     localMessageDigest1.update(arrayOfByte2, 0, n);
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
/* 246 */     n = paramArrayOfByte.length;
/* 247 */     while (n > 0) {
/* 248 */       if ((n & 0x1) != 0) {
/* 249 */         localMessageDigest1.update(arrayOfByte2, 0, paramInt);
/*     */       } else {
/* 251 */         localMessageDigest1.update(paramArrayOfByte);
/*     */       }
/* 253 */       n >>= 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 260 */     arrayOfByte2 = localMessageDigest1.digest();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 266 */     localMessageDigest2 = DigestUtils.getDigest(paramString3);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 275 */     for (int i1 = 1; i1 <= i; i1++) {
/* 276 */       localMessageDigest2.update(paramArrayOfByte);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 283 */     byte[] arrayOfByte3 = localMessageDigest2.digest();
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
/* 295 */     byte[] arrayOfByte4 = new byte[i];
/* 296 */     int i2 = 0;
/* 297 */     while (i2 < i - paramInt) {
/* 298 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte4, i2, paramInt);
/* 299 */       i2 += paramInt;
/*     */     }
/* 301 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte4, i2, i - i2);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 307 */     localMessageDigest2 = DigestUtils.getDigest(paramString3);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 316 */     for (int i3 = 1; i3 <= 16 + (arrayOfByte2[0] & 0xFF); i3++) {
/* 317 */       localMessageDigest2.update(arrayOfByte1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 324 */     arrayOfByte3 = localMessageDigest2.digest();
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
/* 337 */     byte[] arrayOfByte5 = new byte[m];
/* 338 */     i2 = 0;
/* 339 */     while (i2 < m - paramInt) {
/* 340 */       System.arraycopy(arrayOfByte3, 0, arrayOfByte5, i2, paramInt);
/* 341 */       i2 += paramInt;
/*     */     }
/* 343 */     System.arraycopy(arrayOfByte3, 0, arrayOfByte5, i2, m - i2);
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
/* 356 */     for (int i4 = 0; i4 <= j - 1; i4++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 361 */       localMessageDigest1 = DigestUtils.getDigest(paramString3);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 368 */       if ((i4 & 0x1) != 0) {
/* 369 */         localMessageDigest1.update(arrayOfByte4, 0, i);
/*     */       } else {
/* 371 */         localMessageDigest1.update(arrayOfByte2, 0, paramInt);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 378 */       if (i4 % 3 != 0) {
/* 379 */         localMessageDigest1.update(arrayOfByte5, 0, m);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 386 */       if (i4 % 7 != 0) {
/* 387 */         localMessageDigest1.update(arrayOfByte4, 0, i);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 395 */       if ((i4 & 0x1) != 0) {
/* 396 */         localMessageDigest1.update(arrayOfByte2, 0, paramInt);
/*     */       } else {
/* 398 */         localMessageDigest1.update(arrayOfByte4, 0, i);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 405 */       arrayOfByte2 = localMessageDigest1.digest();
/*     */     }
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
/* 423 */     StringBuilder localStringBuilder = new StringBuilder(paramString2);
/* 424 */     if (k != 0) {
/* 425 */       localStringBuilder.append("rounds=");
/* 426 */       localStringBuilder.append(j);
/* 427 */       localStringBuilder.append("$");
/*     */     }
/* 429 */     localStringBuilder.append(str);
/* 430 */     localStringBuilder.append("$");
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
/* 456 */     if (paramInt == 32) {
/* 457 */       B64.b64from24bit(arrayOfByte2[0], arrayOfByte2[10], arrayOfByte2[20], 4, localStringBuilder);
/* 458 */       B64.b64from24bit(arrayOfByte2[21], arrayOfByte2[1], arrayOfByte2[11], 4, localStringBuilder);
/* 459 */       B64.b64from24bit(arrayOfByte2[12], arrayOfByte2[22], arrayOfByte2[2], 4, localStringBuilder);
/* 460 */       B64.b64from24bit(arrayOfByte2[3], arrayOfByte2[13], arrayOfByte2[23], 4, localStringBuilder);
/* 461 */       B64.b64from24bit(arrayOfByte2[24], arrayOfByte2[4], arrayOfByte2[14], 4, localStringBuilder);
/* 462 */       B64.b64from24bit(arrayOfByte2[15], arrayOfByte2[25], arrayOfByte2[5], 4, localStringBuilder);
/* 463 */       B64.b64from24bit(arrayOfByte2[6], arrayOfByte2[16], arrayOfByte2[26], 4, localStringBuilder);
/* 464 */       B64.b64from24bit(arrayOfByte2[27], arrayOfByte2[7], arrayOfByte2[17], 4, localStringBuilder);
/* 465 */       B64.b64from24bit(arrayOfByte2[18], arrayOfByte2[28], arrayOfByte2[8], 4, localStringBuilder);
/* 466 */       B64.b64from24bit(arrayOfByte2[9], arrayOfByte2[19], arrayOfByte2[29], 4, localStringBuilder);
/* 467 */       B64.b64from24bit((byte)0, arrayOfByte2[31], arrayOfByte2[30], 3, localStringBuilder);
/*     */     } else {
/* 469 */       B64.b64from24bit(arrayOfByte2[0], arrayOfByte2[21], arrayOfByte2[42], 4, localStringBuilder);
/* 470 */       B64.b64from24bit(arrayOfByte2[22], arrayOfByte2[43], arrayOfByte2[1], 4, localStringBuilder);
/* 471 */       B64.b64from24bit(arrayOfByte2[44], arrayOfByte2[2], arrayOfByte2[23], 4, localStringBuilder);
/* 472 */       B64.b64from24bit(arrayOfByte2[3], arrayOfByte2[24], arrayOfByte2[45], 4, localStringBuilder);
/* 473 */       B64.b64from24bit(arrayOfByte2[25], arrayOfByte2[46], arrayOfByte2[4], 4, localStringBuilder);
/* 474 */       B64.b64from24bit(arrayOfByte2[47], arrayOfByte2[5], arrayOfByte2[26], 4, localStringBuilder);
/* 475 */       B64.b64from24bit(arrayOfByte2[6], arrayOfByte2[27], arrayOfByte2[48], 4, localStringBuilder);
/* 476 */       B64.b64from24bit(arrayOfByte2[28], arrayOfByte2[49], arrayOfByte2[7], 4, localStringBuilder);
/* 477 */       B64.b64from24bit(arrayOfByte2[50], arrayOfByte2[8], arrayOfByte2[29], 4, localStringBuilder);
/* 478 */       B64.b64from24bit(arrayOfByte2[9], arrayOfByte2[30], arrayOfByte2[51], 4, localStringBuilder);
/* 479 */       B64.b64from24bit(arrayOfByte2[31], arrayOfByte2[52], arrayOfByte2[10], 4, localStringBuilder);
/* 480 */       B64.b64from24bit(arrayOfByte2[53], arrayOfByte2[11], arrayOfByte2[32], 4, localStringBuilder);
/* 481 */       B64.b64from24bit(arrayOfByte2[12], arrayOfByte2[33], arrayOfByte2[54], 4, localStringBuilder);
/* 482 */       B64.b64from24bit(arrayOfByte2[34], arrayOfByte2[55], arrayOfByte2[13], 4, localStringBuilder);
/* 483 */       B64.b64from24bit(arrayOfByte2[56], arrayOfByte2[14], arrayOfByte2[35], 4, localStringBuilder);
/* 484 */       B64.b64from24bit(arrayOfByte2[15], arrayOfByte2[36], arrayOfByte2[57], 4, localStringBuilder);
/* 485 */       B64.b64from24bit(arrayOfByte2[37], arrayOfByte2[58], arrayOfByte2[16], 4, localStringBuilder);
/* 486 */       B64.b64from24bit(arrayOfByte2[59], arrayOfByte2[17], arrayOfByte2[38], 4, localStringBuilder);
/* 487 */       B64.b64from24bit(arrayOfByte2[18], arrayOfByte2[39], arrayOfByte2[60], 4, localStringBuilder);
/* 488 */       B64.b64from24bit(arrayOfByte2[40], arrayOfByte2[61], arrayOfByte2[19], 4, localStringBuilder);
/* 489 */       B64.b64from24bit(arrayOfByte2[62], arrayOfByte2[20], arrayOfByte2[41], 4, localStringBuilder);
/* 490 */       B64.b64from24bit((byte)0, (byte)0, arrayOfByte2[63], 2, localStringBuilder);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 498 */     Arrays.fill(arrayOfByte3, (byte)0);
/* 499 */     Arrays.fill(arrayOfByte4, (byte)0);
/* 500 */     Arrays.fill(arrayOfByte5, (byte)0);
/* 501 */     localMessageDigest1.reset();
/* 502 */     localMessageDigest2.reset();
/* 503 */     Arrays.fill(paramArrayOfByte, (byte)0);
/* 504 */     Arrays.fill(arrayOfByte1, (byte)0);
/*     */     
/* 506 */     return localStringBuilder.toString();
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
/*     */   public static String sha512Crypt(byte[] paramArrayOfByte)
/*     */   {
/* 521 */     return sha512Crypt(paramArrayOfByte, null);
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
/*     */   public static String sha512Crypt(byte[] paramArrayOfByte, String paramString)
/*     */   {
/* 540 */     if (paramString == null) {
/* 541 */       paramString = "$6$" + B64.getRandomSalt(8);
/*     */     }
/* 543 */     return sha2Crypt(paramArrayOfByte, paramString, "$6$", 64, "SHA-512");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\digest\Sha2Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */