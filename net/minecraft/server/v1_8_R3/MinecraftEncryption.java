/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PublicKey;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.SecretKey;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class MinecraftEncryption
/*     */ {
/*  16 */   private static final Logger a = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static java.security.KeyPair b()
/*     */   {
/*     */     try
/*     */     {
/*  37 */       KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
/*  38 */       localKeyPairGenerator.initialize(1024);
/*     */       
/*  40 */       return localKeyPairGenerator.generateKeyPair();
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  42 */       localNoSuchAlgorithmException.printStackTrace();
/*     */       
/*  44 */       a.error("Key pair generation failed!"); }
/*  45 */     return null;
/*     */   }
/*     */   
/*     */   public static byte[] a(String paramString, PublicKey paramPublicKey, SecretKey paramSecretKey) {
/*     */     try {
/*  50 */       return a("SHA-1", new byte[][] { paramString.getBytes("ISO_8859_1"), paramSecretKey.getEncoded(), paramPublicKey.getEncoded() });
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (java.io.UnsupportedEncodingException localUnsupportedEncodingException)
/*     */     {
/*     */ 
/*  57 */       localUnsupportedEncodingException.printStackTrace();
/*     */     }
/*     */     
/*  60 */     return null;
/*     */   }
/*     */   
/*     */   private static byte[] a(String paramString, byte[]... paramVarArgs) {
/*     */     try {
/*  65 */       MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
/*  66 */       for (byte[] arrayOfByte1 : paramVarArgs) {
/*  67 */         localMessageDigest.update(arrayOfByte1);
/*     */       }
/*  69 */       return localMessageDigest.digest();
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  71 */       localNoSuchAlgorithmException.printStackTrace();
/*     */     }
/*     */     
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   public static PublicKey a(byte[] paramArrayOfByte) {
/*     */     try {
/*  79 */       java.security.spec.X509EncodedKeySpec localX509EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(paramArrayOfByte);
/*  80 */       KeyFactory localKeyFactory = KeyFactory.getInstance("RSA");
/*  81 */       return localKeyFactory.generatePublic(localX509EncodedKeySpec);
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}catch (java.security.spec.InvalidKeySpecException localInvalidKeySpecException) {}
/*     */     
/*  85 */     a.error("Public key reconstitute failed!");
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public static SecretKey a(java.security.PrivateKey paramPrivateKey, byte[] paramArrayOfByte) {
/*  90 */     return new javax.crypto.spec.SecretKeySpec(b(paramPrivateKey, paramArrayOfByte), "AES");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] b(Key paramKey, byte[] paramArrayOfByte)
/*     */   {
/*  98 */     return a(2, paramKey, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   private static byte[] a(int paramInt, Key paramKey, byte[] paramArrayOfByte) {
/*     */     try {
/* 103 */       return a(paramInt, paramKey.getAlgorithm(), paramKey).doFinal(paramArrayOfByte);
/*     */     } catch (javax.crypto.IllegalBlockSizeException localIllegalBlockSizeException) {
/* 105 */       localIllegalBlockSizeException.printStackTrace();
/*     */     } catch (BadPaddingException localBadPaddingException) {
/* 107 */       localBadPaddingException.printStackTrace();
/*     */     }
/* 109 */     a.error("Cipher data failed!");
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   private static Cipher a(int paramInt, String paramString, Key paramKey) {
/*     */     try {
/* 115 */       Cipher localCipher = Cipher.getInstance(paramString);
/* 116 */       localCipher.init(paramInt, paramKey);
/* 117 */       return localCipher;
/*     */     } catch (java.security.InvalidKeyException localInvalidKeyException) {
/* 119 */       localInvalidKeyException.printStackTrace();
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 121 */       localNoSuchAlgorithmException.printStackTrace();
/*     */     } catch (javax.crypto.NoSuchPaddingException localNoSuchPaddingException) {
/* 123 */       localNoSuchPaddingException.printStackTrace();
/*     */     }
/* 125 */     a.error("Cipher creation failed!");
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public static Cipher a(int paramInt, Key paramKey) {
/*     */     try {
/* 131 */       Cipher localCipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 132 */       localCipher.init(paramInt, paramKey, new javax.crypto.spec.IvParameterSpec(paramKey.getEncoded()));
/* 133 */       return localCipher;
/*     */     } catch (java.security.GeneralSecurityException localGeneralSecurityException) {
/* 135 */       throw new RuntimeException(localGeneralSecurityException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MinecraftEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */