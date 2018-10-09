/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyStore;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Security;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.EncryptedPrivateKeyInfo;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.PBEKeySpec;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdkSslServerContext
/*     */   extends JdkSslContext
/*     */ {
/*     */   private final SSLContext ctx;
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   public JdkSslServerContext(File paramFile1, File paramFile2)
/*     */     throws SSLException
/*     */   {
/*  64 */     this(paramFile1, paramFile2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkSslServerContext(File paramFile1, File paramFile2, String paramString)
/*     */     throws SSLException
/*     */   {
/*  76 */     this(paramFile1, paramFile2, paramString, null, null, 0L, 0L);
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
/*     */   public JdkSslServerContext(File paramFile1, File paramFile2, String paramString, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 100 */     super(paramIterable1);
/*     */     
/* 102 */     if (paramFile1 == null) {
/* 103 */       throw new NullPointerException("certChainFile");
/*     */     }
/* 105 */     if (paramFile2 == null) {
/* 106 */       throw new NullPointerException("keyFile");
/*     */     }
/*     */     
/* 109 */     if (paramString == null)
/* 110 */       paramString = "";
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 113 */     if ((paramIterable2 != null) && (paramIterable2.iterator().hasNext())) {
/* 114 */       if (!JettyNpnSslEngine.isAvailable()) {
/* 115 */         throw new SSLException("NPN/ALPN unsupported: " + paramIterable2);
/*     */       }
/*     */       
/* 118 */       localObject1 = new ArrayList();
/* 119 */       for (localObject2 = paramIterable2.iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (String)((Iterator)localObject2).next();
/* 120 */         if (localObject3 == null) {
/*     */           break;
/*     */         }
/* 123 */         ((List)localObject1).add(localObject3);
/*     */       }
/*     */       
/* 126 */       this.nextProtocols = Collections.unmodifiableList((List)localObject1);
/*     */     } else {
/* 128 */       this.nextProtocols = Collections.emptyList();
/*     */     }
/*     */     
/* 131 */     Object localObject1 = Security.getProperty("ssl.KeyManagerFactory.algorithm");
/* 132 */     if (localObject1 == null) {
/* 133 */       localObject1 = "SunX509";
/*     */     }
/*     */     try
/*     */     {
/* 137 */       localObject2 = KeyStore.getInstance("JKS");
/* 138 */       ((KeyStore)localObject2).load(null, null);
/* 139 */       localObject3 = CertificateFactory.getInstance("X.509");
/* 140 */       KeyFactory localKeyFactory1 = KeyFactory.getInstance("RSA");
/* 141 */       KeyFactory localKeyFactory2 = KeyFactory.getInstance("DSA");
/*     */       
/* 143 */       ByteBuf localByteBuf1 = PemReader.readPrivateKey(paramFile2);
/* 144 */       byte[] arrayOfByte = new byte[localByteBuf1.readableBytes()];
/* 145 */       localByteBuf1.readBytes(arrayOfByte).release();
/*     */       
/* 147 */       char[] arrayOfChar = paramString.toCharArray();
/* 148 */       PKCS8EncodedKeySpec localPKCS8EncodedKeySpec = generateKeySpec(arrayOfChar, arrayOfByte);
/*     */       PrivateKey localPrivateKey;
/*     */       try
/*     */       {
/* 152 */         localPrivateKey = localKeyFactory1.generatePrivate(localPKCS8EncodedKeySpec);
/*     */       } catch (InvalidKeySpecException localInvalidKeySpecException) {
/* 154 */         localPrivateKey = localKeyFactory2.generatePrivate(localPKCS8EncodedKeySpec);
/*     */       }
/*     */       
/* 157 */       ArrayList localArrayList = new ArrayList();
/* 158 */       ByteBuf[] arrayOfByteBuf1 = PemReader.readCertificates(paramFile1);
/*     */       try {
/* 160 */         for (localByteBuf2 : arrayOfByteBuf1)
/* 161 */           localArrayList.add(((CertificateFactory)localObject3).generateCertificate(new ByteBufInputStream(localByteBuf2)));
/*     */       } finally {
/*     */         ByteBuf localByteBuf2;
/* 164 */         for (ByteBuf localByteBuf3 : arrayOfByteBuf1) {
/* 165 */           localByteBuf3.release();
/*     */         }
/*     */       }
/*     */       
/* 169 */       ((KeyStore)localObject2).setKeyEntry("key", localPrivateKey, arrayOfChar, (Certificate[])localArrayList.toArray(new Certificate[localArrayList.size()]));
/*     */       
/*     */ 
/* 172 */       ??? = KeyManagerFactory.getInstance((String)localObject1);
/* 173 */       ((KeyManagerFactory)???).init((KeyStore)localObject2, arrayOfChar);
/*     */       
/*     */ 
/* 176 */       this.ctx = SSLContext.getInstance("TLS");
/* 177 */       this.ctx.init(((KeyManagerFactory)???).getKeyManagers(), null, null);
/*     */       
/* 179 */       SSLSessionContext localSSLSessionContext = this.ctx.getServerSessionContext();
/* 180 */       if (paramLong1 > 0L) {
/* 181 */         localSSLSessionContext.setSessionCacheSize((int)Math.min(paramLong1, 2147483647L));
/*     */       }
/* 183 */       if (paramLong2 > 0L) {
/* 184 */         localSSLSessionContext.setSessionTimeout((int)Math.min(paramLong2, 2147483647L));
/*     */       }
/*     */     } catch (Exception localException) {
/* 187 */       throw new SSLException("failed to initialize the server-side SSL context", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isClient()
/*     */   {
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public List<String> nextProtocols()
/*     */   {
/* 198 */     return this.nextProtocols;
/*     */   }
/*     */   
/*     */   public SSLContext context()
/*     */   {
/* 203 */     return this.ctx;
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
/*     */   private static PKCS8EncodedKeySpec generateKeySpec(char[] paramArrayOfChar, byte[] paramArrayOfByte)
/*     */     throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException
/*     */   {
/* 226 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
/* 227 */       return new PKCS8EncodedKeySpec(paramArrayOfByte);
/*     */     }
/*     */     
/* 230 */     EncryptedPrivateKeyInfo localEncryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(paramArrayOfByte);
/* 231 */     SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(localEncryptedPrivateKeyInfo.getAlgName());
/* 232 */     PBEKeySpec localPBEKeySpec = new PBEKeySpec(paramArrayOfChar);
/* 233 */     SecretKey localSecretKey = localSecretKeyFactory.generateSecret(localPBEKeySpec);
/*     */     
/* 235 */     Cipher localCipher = Cipher.getInstance(localEncryptedPrivateKeyInfo.getAlgName());
/* 236 */     localCipher.init(2, localSecretKey, localEncryptedPrivateKeyInfo.getAlgParameters());
/*     */     
/* 238 */     return localEncryptedPrivateKeyInfo.getKeySpec(localCipher);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\JdkSslServerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */