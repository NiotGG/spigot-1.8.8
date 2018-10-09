/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
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
/*     */ public abstract class JdkSslContext
/*     */   extends SslContext
/*     */ {
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
/*     */   static final String PROTOCOL = "TLS";
/*     */   static final String[] PROTOCOLS;
/*     */   static final List<String> DEFAULT_CIPHERS;
/*     */   private final String[] cipherSuites;
/*     */   private final List<String> unmodifiableCipherSuites;
/*     */   
/*     */   static {
/*     */     SSLContext localSSLContext;
/*  45 */     try { localSSLContext = SSLContext.getInstance("TLS");
/*  46 */       localSSLContext.init(null, null, null);
/*     */     } catch (Exception localException) {
/*  48 */       throw new Error("failed to initialize the default SSL context", localException);
/*     */     }
/*     */     
/*  51 */     SSLEngine localSSLEngine = localSSLContext.createSSLEngine();
/*     */     
/*     */ 
/*  54 */     String[] arrayOfString1 = localSSLEngine.getSupportedProtocols();
/*  55 */     ArrayList localArrayList1 = new ArrayList();
/*  56 */     addIfSupported(arrayOfString1, localArrayList1, new String[] { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" });
/*     */     
/*     */ 
/*     */ 
/*  60 */     if (!localArrayList1.isEmpty()) {
/*  61 */       PROTOCOLS = (String[])localArrayList1.toArray(new String[localArrayList1.size()]);
/*     */     } else {
/*  63 */       PROTOCOLS = localSSLEngine.getEnabledProtocols();
/*     */     }
/*     */     
/*     */ 
/*  67 */     String[] arrayOfString2 = localSSLEngine.getSupportedCipherSuites();
/*  68 */     ArrayList localArrayList2 = new ArrayList();
/*  69 */     addIfSupported(arrayOfString2, localArrayList2, new String[] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_RC4_128_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "SSL_RSA_WITH_RC4_128_SHA", "SSL_RSA_WITH_RC4_128_MD5", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA" });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */     if (!localArrayList2.isEmpty()) {
/*  88 */       DEFAULT_CIPHERS = Collections.unmodifiableList(localArrayList2);
/*     */     }
/*     */     else {
/*  91 */       DEFAULT_CIPHERS = Collections.unmodifiableList(Arrays.asList(localSSLEngine.getEnabledCipherSuites()));
/*     */     }
/*     */     
/*  94 */     if (logger.isDebugEnabled()) {
/*  95 */       logger.debug("Default protocols (JDK): {} ", Arrays.asList(PROTOCOLS));
/*  96 */       logger.debug("Default cipher suites (JDK): {}", DEFAULT_CIPHERS);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addIfSupported(String[] paramArrayOfString1, List<String> paramList, String... paramVarArgs) {
/* 101 */     for (String str1 : paramVarArgs) {
/* 102 */       for (String str2 : paramArrayOfString1) {
/* 103 */         if (str1.equals(str2)) {
/* 104 */           paramList.add(str2);
/* 105 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   JdkSslContext(Iterable<String> paramIterable)
/*     */   {
/* 115 */     this.cipherSuites = toCipherSuiteArray(paramIterable);
/* 116 */     this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SSLSessionContext sessionContext()
/*     */   {
/* 128 */     if (isServer()) {
/* 129 */       return context().getServerSessionContext();
/*     */     }
/* 131 */     return context().getClientSessionContext();
/*     */   }
/*     */   
/*     */ 
/*     */   public final List<String> cipherSuites()
/*     */   {
/* 137 */     return this.unmodifiableCipherSuites;
/*     */   }
/*     */   
/*     */   public final long sessionCacheSize()
/*     */   {
/* 142 */     return sessionContext().getSessionCacheSize();
/*     */   }
/*     */   
/*     */   public final long sessionTimeout()
/*     */   {
/* 147 */     return sessionContext().getSessionTimeout();
/*     */   }
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 152 */     SSLEngine localSSLEngine = context().createSSLEngine();
/* 153 */     localSSLEngine.setEnabledCipherSuites(this.cipherSuites);
/* 154 */     localSSLEngine.setEnabledProtocols(PROTOCOLS);
/* 155 */     localSSLEngine.setUseClientMode(isClient());
/* 156 */     return wrapEngine(localSSLEngine);
/*     */   }
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt)
/*     */   {
/* 161 */     SSLEngine localSSLEngine = context().createSSLEngine(paramString, paramInt);
/* 162 */     localSSLEngine.setEnabledCipherSuites(this.cipherSuites);
/* 163 */     localSSLEngine.setEnabledProtocols(PROTOCOLS);
/* 164 */     localSSLEngine.setUseClientMode(isClient());
/* 165 */     return wrapEngine(localSSLEngine);
/*     */   }
/*     */   
/*     */   private SSLEngine wrapEngine(SSLEngine paramSSLEngine) {
/* 169 */     if (nextProtocols().isEmpty()) {
/* 170 */       return paramSSLEngine;
/*     */     }
/* 172 */     return new JettyNpnSslEngine(paramSSLEngine, nextProtocols(), isServer());
/*     */   }
/*     */   
/*     */   private static String[] toCipherSuiteArray(Iterable<String> paramIterable)
/*     */   {
/* 177 */     if (paramIterable == null) {
/* 178 */       return (String[])DEFAULT_CIPHERS.toArray(new String[DEFAULT_CIPHERS.size()]);
/*     */     }
/* 180 */     ArrayList localArrayList = new ArrayList();
/* 181 */     for (String str : paramIterable) {
/* 182 */       if (str == null) {
/*     */         break;
/*     */       }
/* 185 */       localArrayList.add(str);
/*     */     }
/* 187 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */   
/*     */   public abstract SSLContext context();
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\JdkSslContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */