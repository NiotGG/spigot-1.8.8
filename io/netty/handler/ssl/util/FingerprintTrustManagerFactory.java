/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.security.KeyStore;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.net.ssl.ManagerFactoryParameters;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FingerprintTrustManagerFactory
/*     */   extends SimpleTrustManagerFactory
/*     */ {
/*  66 */   private static final Pattern FINGERPRINT_PATTERN = Pattern.compile("^[0-9a-fA-F:]+$");
/*  67 */   private static final Pattern FINGERPRINT_STRIP_PATTERN = Pattern.compile(":");
/*     */   
/*     */   private static final int SHA1_BYTE_LEN = 20;
/*     */   private static final int SHA1_HEX_LEN = 40;
/*  71 */   private static final FastThreadLocal<MessageDigest> tlmd = new FastThreadLocal()
/*     */   {
/*     */     protected MessageDigest initialValue() {
/*     */       try {
/*  75 */         return MessageDigest.getInstance("SHA1");
/*     */       }
/*     */       catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  78 */         throw new Error(localNoSuchAlgorithmException);
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*  83 */   private final TrustManager tm = new X509TrustManager()
/*     */   {
/*     */     public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) throws CertificateException
/*     */     {
/*  87 */       checkTrusted("client", paramAnonymousArrayOfX509Certificate);
/*     */     }
/*     */     
/*     */     public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) throws CertificateException
/*     */     {
/*  92 */       checkTrusted("server", paramAnonymousArrayOfX509Certificate);
/*     */     }
/*     */     
/*     */     private void checkTrusted(String paramAnonymousString, X509Certificate[] paramAnonymousArrayOfX509Certificate) throws CertificateException {
/*  96 */       X509Certificate localX509Certificate = paramAnonymousArrayOfX509Certificate[0];
/*  97 */       byte[] arrayOfByte1 = fingerprint(localX509Certificate);
/*  98 */       int i = 0;
/*  99 */       for (byte[] arrayOfByte2 : FingerprintTrustManagerFactory.this.fingerprints) {
/* 100 */         if (Arrays.equals(arrayOfByte1, arrayOfByte2)) {
/* 101 */           i = 1;
/* 102 */           break;
/*     */         }
/*     */       }
/*     */       
/* 106 */       if (i == 0) {
/* 107 */         throw new CertificateException(paramAnonymousString + " certificate with unknown fingerprint: " + localX509Certificate.getSubjectDN());
/*     */       }
/*     */     }
/*     */     
/*     */     private byte[] fingerprint(X509Certificate paramAnonymousX509Certificate) throws CertificateEncodingException
/*     */     {
/* 113 */       MessageDigest localMessageDigest = (MessageDigest)FingerprintTrustManagerFactory.tlmd.get();
/* 114 */       localMessageDigest.reset();
/* 115 */       return localMessageDigest.digest(paramAnonymousX509Certificate.getEncoded());
/*     */     }
/*     */     
/*     */     public X509Certificate[] getAcceptedIssuers()
/*     */     {
/* 120 */       return EmptyArrays.EMPTY_X509_CERTIFICATES;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   private final byte[][] fingerprints;
/*     */   
/*     */ 
/*     */ 
/*     */   public FingerprintTrustManagerFactory(Iterable<String> paramIterable)
/*     */   {
/* 132 */     this(toFingerprintArray(paramIterable));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FingerprintTrustManagerFactory(String... paramVarArgs)
/*     */   {
/* 141 */     this(toFingerprintArray(Arrays.asList(paramVarArgs)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FingerprintTrustManagerFactory(byte[]... paramVarArgs)
/*     */   {
/* 150 */     if (paramVarArgs == null) {
/* 151 */       throw new NullPointerException("fingerprints");
/*     */     }
/*     */     
/* 154 */     ArrayList localArrayList = new ArrayList();
/* 155 */     for (byte[] arrayOfByte1 : paramVarArgs) {
/* 156 */       if (arrayOfByte1 == null) {
/*     */         break;
/*     */       }
/* 159 */       if (arrayOfByte1.length != 20) {
/* 160 */         throw new IllegalArgumentException("malformed fingerprint: " + ByteBufUtil.hexDump(Unpooled.wrappedBuffer(arrayOfByte1)) + " (expected: SHA1)");
/*     */       }
/*     */       
/* 163 */       localArrayList.add(arrayOfByte1.clone());
/*     */     }
/*     */     
/* 166 */     this.fingerprints = ((byte[][])localArrayList.toArray(new byte[localArrayList.size()][]));
/*     */   }
/*     */   
/*     */   private static byte[][] toFingerprintArray(Iterable<String> paramIterable) {
/* 170 */     if (paramIterable == null) {
/* 171 */       throw new NullPointerException("fingerprints");
/*     */     }
/*     */     
/* 174 */     ArrayList localArrayList = new ArrayList();
/* 175 */     for (String str : paramIterable) {
/* 176 */       if (str == null) {
/*     */         break;
/*     */       }
/*     */       
/* 180 */       if (!FINGERPRINT_PATTERN.matcher(str).matches()) {
/* 181 */         throw new IllegalArgumentException("malformed fingerprint: " + str);
/*     */       }
/* 183 */       str = FINGERPRINT_STRIP_PATTERN.matcher(str).replaceAll("");
/* 184 */       if (str.length() != 40) {
/* 185 */         throw new IllegalArgumentException("malformed fingerprint: " + str + " (expected: SHA1)");
/*     */       }
/*     */       
/* 188 */       byte[] arrayOfByte = new byte[20];
/* 189 */       for (int i = 0; i < arrayOfByte.length; i++) {
/* 190 */         int j = i << 1;
/* 191 */         arrayOfByte[i] = ((byte)Integer.parseInt(str.substring(j, j + 2), 16));
/*     */       }
/*     */     }
/*     */     
/* 195 */     return (byte[][])localArrayList.toArray(new byte[localArrayList.size()][]);
/*     */   }
/*     */   
/*     */   protected void engineInit(KeyStore paramKeyStore) throws Exception
/*     */   {}
/*     */   
/*     */   protected void engineInit(ManagerFactoryParameters paramManagerFactoryParameters) throws Exception
/*     */   {}
/*     */   
/*     */   protected TrustManager[] engineGetTrustManagers()
/*     */   {
/* 206 */     return new TrustManager[] { this.tm };
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\util\FingerprintTrustManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */