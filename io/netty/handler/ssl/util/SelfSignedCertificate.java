/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SelfSignedCertificate
/*     */ {
/*  57 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
/*     */   
/*     */ 
/*  60 */   static final Date NOT_BEFORE = new Date(System.currentTimeMillis() - 31536000000L);
/*     */   
/*  62 */   static final Date NOT_AFTER = new Date(253402300799000L);
/*     */   
/*     */   private final File certificate;
/*     */   
/*     */   private final File privateKey;
/*     */   
/*     */   public SelfSignedCertificate()
/*     */     throws CertificateException
/*     */   {
/*  71 */     this("example.com");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SelfSignedCertificate(String paramString)
/*     */     throws CertificateException
/*     */   {
/*  82 */     this(paramString, ThreadLocalInsecureRandom.current(), 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SelfSignedCertificate(String paramString, SecureRandom paramSecureRandom, int paramInt)
/*     */     throws CertificateException
/*     */   {
/*     */     KeyPair localKeyPair;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  96 */       KeyPairGenerator localKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
/*  97 */       localKeyPairGenerator.initialize(paramInt, paramSecureRandom);
/*  98 */       localKeyPair = localKeyPairGenerator.generateKeyPair();
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 101 */       throw new Error(localNoSuchAlgorithmException);
/*     */     }
/*     */     
/*     */     String[] arrayOfString;
/*     */     try
/*     */     {
/* 107 */       arrayOfString = OpenJdkSelfSignedCertGenerator.generate(paramString, localKeyPair, paramSecureRandom);
/*     */     } catch (Throwable localThrowable1) {
/* 109 */       logger.debug("Failed to generate a self-signed X.509 certificate using sun.security.x509:", localThrowable1);
/*     */       try
/*     */       {
/* 112 */         arrayOfString = BouncyCastleSelfSignedCertGenerator.generate(paramString, localKeyPair, paramSecureRandom);
/*     */       } catch (Throwable localThrowable2) {
/* 114 */         logger.debug("Failed to generate a self-signed X.509 certificate using Bouncy Castle:", localThrowable2);
/* 115 */         throw new CertificateException("No provider succeeded to generate a self-signed certificate. See debug log for the root cause.");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 121 */     this.certificate = new File(arrayOfString[0]);
/* 122 */     this.privateKey = new File(arrayOfString[1]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public File certificate()
/*     */   {
/* 129 */     return this.certificate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public File privateKey()
/*     */   {
/* 136 */     return this.privateKey;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void delete()
/*     */   {
/* 143 */     safeDelete(this.certificate);
/* 144 */     safeDelete(this.privateKey);
/*     */   }
/*     */   
/*     */ 
/*     */   static String[] newSelfSignedCertificate(String paramString, PrivateKey paramPrivateKey, X509Certificate paramX509Certificate)
/*     */     throws IOException, CertificateEncodingException
/*     */   {
/* 151 */     String str1 = "-----BEGIN PRIVATE KEY-----\n" + Base64.encode(Unpooled.wrappedBuffer(paramPrivateKey.getEncoded()), true).toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
/*     */     
/*     */ 
/*     */ 
/* 155 */     File localFile1 = File.createTempFile("keyutil_" + paramString + '_', ".key");
/* 156 */     localFile1.deleteOnExit();
/*     */     
/* 158 */     FileOutputStream localFileOutputStream1 = new FileOutputStream(localFile1);
/*     */     try {
/* 160 */       localFileOutputStream1.write(str1.getBytes(CharsetUtil.US_ASCII));
/* 161 */       localFileOutputStream1.close();
/* 162 */       localFileOutputStream1 = null;
/*     */     } finally {
/* 164 */       if (localFileOutputStream1 != null) {
/* 165 */         safeClose(localFile1, localFileOutputStream1);
/* 166 */         safeDelete(localFile1);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 171 */     String str2 = "-----BEGIN CERTIFICATE-----\n" + Base64.encode(Unpooled.wrappedBuffer(paramX509Certificate.getEncoded()), true).toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
/*     */     
/*     */ 
/*     */ 
/* 175 */     File localFile2 = File.createTempFile("keyutil_" + paramString + '_', ".crt");
/* 176 */     localFile2.deleteOnExit();
/*     */     
/* 178 */     FileOutputStream localFileOutputStream2 = new FileOutputStream(localFile2);
/*     */     try {
/* 180 */       localFileOutputStream2.write(str2.getBytes(CharsetUtil.US_ASCII));
/* 181 */       localFileOutputStream2.close();
/* 182 */       localFileOutputStream2 = null;
/*     */     } finally {
/* 184 */       if (localFileOutputStream2 != null) {
/* 185 */         safeClose(localFile2, localFileOutputStream2);
/* 186 */         safeDelete(localFile2);
/* 187 */         safeDelete(localFile1);
/*     */       }
/*     */     }
/*     */     
/* 191 */     return new String[] { localFile2.getPath(), localFile1.getPath() };
/*     */   }
/*     */   
/*     */   private static void safeDelete(File paramFile) {
/* 195 */     if (!paramFile.delete()) {
/* 196 */       logger.warn("Failed to delete a file: " + paramFile);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeClose(File paramFile, OutputStream paramOutputStream) {
/*     */     try {
/* 202 */       paramOutputStream.close();
/*     */     } catch (IOException localIOException) {
/* 204 */       logger.warn("Failed to close a file: " + paramFile, localIOException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\util\SelfSignedCertificate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */