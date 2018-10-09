/*     */ package org.apache.commons.codec.digest;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import org.apache.commons.codec.binary.Hex;
/*     */ import org.apache.commons.codec.binary.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigestUtils
/*     */ {
/*     */   private static final int STREAM_BUFFER_LENGTH = 1024;
/*     */   
/*     */   private static byte[] digest(MessageDigest paramMessageDigest, InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/*  50 */     return updateDigest(paramMessageDigest, paramInputStream).digest();
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
/*     */   public static MessageDigest getDigest(String paramString)
/*     */   {
/*     */     try
/*     */     {
/*  68 */       return MessageDigest.getInstance(paramString);
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  70 */       throw new IllegalArgumentException(localNoSuchAlgorithmException);
/*     */     }
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
/*     */   public static MessageDigest getMd2Digest()
/*     */   {
/*  85 */     return getDigest("MD2");
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
/*     */   public static MessageDigest getMd5Digest()
/*     */   {
/*  98 */     return getDigest("MD5");
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
/*     */   public static MessageDigest getSha1Digest()
/*     */   {
/* 112 */     return getDigest("SHA-1");
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
/*     */   public static MessageDigest getSha256Digest()
/*     */   {
/* 128 */     return getDigest("SHA-256");
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
/*     */   public static MessageDigest getSha384Digest()
/*     */   {
/* 144 */     return getDigest("SHA-384");
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
/*     */   public static MessageDigest getSha512Digest()
/*     */   {
/* 160 */     return getDigest("SHA-512");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static MessageDigest getShaDigest()
/*     */   {
/* 173 */     return getSha1Digest();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md2(byte[] paramArrayOfByte)
/*     */   {
/* 185 */     return getMd2Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] md2(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 199 */     return digest(getMd2Digest(), paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md2(String paramString)
/*     */   {
/* 211 */     return md2(StringUtils.getBytesUtf8(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md2Hex(byte[] paramArrayOfByte)
/*     */   {
/* 223 */     return Hex.encodeHexString(md2(paramArrayOfByte));
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
/*     */   public static String md2Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 237 */     return Hex.encodeHexString(md2(paramInputStream));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md2Hex(String paramString)
/*     */   {
/* 249 */     return Hex.encodeHexString(md2(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md5(byte[] paramArrayOfByte)
/*     */   {
/* 260 */     return getMd5Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] md5(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 274 */     return digest(getMd5Digest(), paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md5(String paramString)
/*     */   {
/* 285 */     return md5(StringUtils.getBytesUtf8(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Hex(byte[] paramArrayOfByte)
/*     */   {
/* 296 */     return Hex.encodeHexString(md5(paramArrayOfByte));
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
/*     */   public static String md5Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 310 */     return Hex.encodeHexString(md5(paramInputStream));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Hex(String paramString)
/*     */   {
/* 321 */     return Hex.encodeHexString(md5(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static byte[] sha(byte[] paramArrayOfByte)
/*     */   {
/* 334 */     return sha1(paramArrayOfByte);
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
/*     */   @Deprecated
/*     */   public static byte[] sha(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 350 */     return sha1(paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static byte[] sha(String paramString)
/*     */   {
/* 363 */     return sha1(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] sha1(byte[] paramArrayOfByte)
/*     */   {
/* 375 */     return getSha1Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] sha1(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 389 */     return digest(getSha1Digest(), paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] sha1(String paramString)
/*     */   {
/* 400 */     return sha1(StringUtils.getBytesUtf8(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String sha1Hex(byte[] paramArrayOfByte)
/*     */   {
/* 412 */     return Hex.encodeHexString(sha1(paramArrayOfByte));
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
/*     */   public static String sha1Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 426 */     return Hex.encodeHexString(sha1(paramInputStream));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String sha1Hex(String paramString)
/*     */   {
/* 438 */     return Hex.encodeHexString(sha1(paramString));
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
/*     */   public static byte[] sha256(byte[] paramArrayOfByte)
/*     */   {
/* 453 */     return getSha256Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] sha256(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 470 */     return digest(getSha256Digest(), paramInputStream);
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
/*     */   public static byte[] sha256(String paramString)
/*     */   {
/* 485 */     return sha256(StringUtils.getBytesUtf8(paramString));
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
/*     */   public static String sha256Hex(byte[] paramArrayOfByte)
/*     */   {
/* 500 */     return Hex.encodeHexString(sha256(paramArrayOfByte));
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
/*     */   public static String sha256Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 517 */     return Hex.encodeHexString(sha256(paramInputStream));
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
/*     */   public static String sha256Hex(String paramString)
/*     */   {
/* 532 */     return Hex.encodeHexString(sha256(paramString));
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
/*     */   public static byte[] sha384(byte[] paramArrayOfByte)
/*     */   {
/* 547 */     return getSha384Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] sha384(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 564 */     return digest(getSha384Digest(), paramInputStream);
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
/*     */   public static byte[] sha384(String paramString)
/*     */   {
/* 579 */     return sha384(StringUtils.getBytesUtf8(paramString));
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
/*     */   public static String sha384Hex(byte[] paramArrayOfByte)
/*     */   {
/* 594 */     return Hex.encodeHexString(sha384(paramArrayOfByte));
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
/*     */   public static String sha384Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 611 */     return Hex.encodeHexString(sha384(paramInputStream));
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
/*     */   public static String sha384Hex(String paramString)
/*     */   {
/* 626 */     return Hex.encodeHexString(sha384(paramString));
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
/*     */   public static byte[] sha512(byte[] paramArrayOfByte)
/*     */   {
/* 641 */     return getSha512Digest().digest(paramArrayOfByte);
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
/*     */   public static byte[] sha512(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 658 */     return digest(getSha512Digest(), paramInputStream);
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
/*     */   public static byte[] sha512(String paramString)
/*     */   {
/* 673 */     return sha512(StringUtils.getBytesUtf8(paramString));
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
/*     */   public static String sha512Hex(byte[] paramArrayOfByte)
/*     */   {
/* 688 */     return Hex.encodeHexString(sha512(paramArrayOfByte));
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
/*     */   public static String sha512Hex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 705 */     return Hex.encodeHexString(sha512(paramInputStream));
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
/*     */   public static String sha512Hex(String paramString)
/*     */   {
/* 720 */     return Hex.encodeHexString(sha512(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static String shaHex(byte[] paramArrayOfByte)
/*     */   {
/* 733 */     return sha1Hex(paramArrayOfByte);
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
/*     */   @Deprecated
/*     */   public static String shaHex(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 749 */     return sha1Hex(paramInputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static String shaHex(String paramString)
/*     */   {
/* 762 */     return sha1Hex(paramString);
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
/*     */   public static MessageDigest updateDigest(MessageDigest paramMessageDigest, byte[] paramArrayOfByte)
/*     */   {
/* 776 */     paramMessageDigest.update(paramArrayOfByte);
/* 777 */     return paramMessageDigest;
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
/*     */   public static MessageDigest updateDigest(MessageDigest paramMessageDigest, InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 793 */     byte[] arrayOfByte = new byte['Ѐ'];
/* 794 */     int i = paramInputStream.read(arrayOfByte, 0, 1024);
/*     */     
/* 796 */     while (i > -1) {
/* 797 */       paramMessageDigest.update(arrayOfByte, 0, i);
/* 798 */       i = paramInputStream.read(arrayOfByte, 0, 1024);
/*     */     }
/*     */     
/* 801 */     return paramMessageDigest;
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
/*     */   public static MessageDigest updateDigest(MessageDigest paramMessageDigest, String paramString)
/*     */   {
/* 816 */     paramMessageDigest.update(StringUtils.getBytesUtf8(paramString));
/* 817 */     return paramMessageDigest;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\digest\DigestUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */