/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import java.io.File;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdkSslClientContext
/*     */   extends JdkSslContext
/*     */ {
/*     */   private final SSLContext ctx;
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   public JdkSslClientContext()
/*     */     throws SSLException
/*     */   {
/*  48 */     this(null, null, null, null, 0L, 0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkSslClientContext(File paramFile)
/*     */     throws SSLException
/*     */   {
/*  58 */     this(paramFile, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkSslClientContext(TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/*  69 */     this(null, paramTrustManagerFactory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkSslClientContext(File paramFile, TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/*  82 */     this(paramFile, paramTrustManagerFactory, null, null, 0L, 0L);
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
/*     */   public JdkSslClientContext(File paramFile, TrustManagerFactory paramTrustManagerFactory, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 107 */     super(paramIterable1);
/*     */     Object localObject1;
/* 109 */     Object localObject2; Object localObject3; if ((paramIterable2 != null) && (paramIterable2.iterator().hasNext())) {
/* 110 */       if (!JettyNpnSslEngine.isAvailable()) {
/* 111 */         throw new SSLException("NPN/ALPN unsupported: " + paramIterable2);
/*     */       }
/*     */       
/* 114 */       localObject1 = new ArrayList();
/* 115 */       for (localObject2 = paramIterable2.iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (String)((Iterator)localObject2).next();
/* 116 */         if (localObject3 == null) {
/*     */           break;
/*     */         }
/* 119 */         ((List)localObject1).add(localObject3);
/*     */       }
/* 121 */       this.nextProtocols = Collections.unmodifiableList((List)localObject1);
/*     */     } else {
/* 123 */       this.nextProtocols = Collections.emptyList();
/*     */     }
/*     */     try
/*     */     {
/* 127 */       if (paramFile == null) {
/* 128 */         this.ctx = SSLContext.getInstance("TLS");
/* 129 */         if (paramTrustManagerFactory == null) {
/* 130 */           this.ctx.init(null, null, null);
/*     */         } else {
/* 132 */           paramTrustManagerFactory.init((KeyStore)null);
/* 133 */           this.ctx.init(null, paramTrustManagerFactory.getTrustManagers(), null);
/*     */         }
/*     */       } else {
/* 136 */         localObject1 = KeyStore.getInstance("JKS");
/* 137 */         ((KeyStore)localObject1).load(null, null);
/* 138 */         localObject2 = CertificateFactory.getInstance("X.509");
/*     */         
/* 140 */         localObject3 = PemReader.readCertificates(paramFile);
/*     */         try {
/* 142 */           for (localByteBuf : localObject3) {
/* 143 */             X509Certificate localX509Certificate = (X509Certificate)((CertificateFactory)localObject2).generateCertificate(new ByteBufInputStream(localByteBuf));
/* 144 */             X500Principal localX500Principal = localX509Certificate.getSubjectX500Principal();
/* 145 */             ((KeyStore)localObject1).setCertificateEntry(localX500Principal.getName("RFC2253"), localX509Certificate);
/*     */           }
/*     */         } finally { ByteBuf localByteBuf;
/* 148 */           for (Object localObject7 : localObject3) {
/* 149 */             ((ByteBuf)localObject7).release();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 154 */         if (paramTrustManagerFactory == null) {
/* 155 */           paramTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */         }
/* 157 */         paramTrustManagerFactory.init((KeyStore)localObject1);
/*     */         
/*     */ 
/* 160 */         this.ctx = SSLContext.getInstance("TLS");
/* 161 */         this.ctx.init(null, paramTrustManagerFactory.getTrustManagers(), null);
/*     */       }
/*     */       
/* 164 */       localObject1 = this.ctx.getClientSessionContext();
/* 165 */       if (paramLong1 > 0L) {
/* 166 */         ((SSLSessionContext)localObject1).setSessionCacheSize((int)Math.min(paramLong1, 2147483647L));
/*     */       }
/* 168 */       if (paramLong2 > 0L) {
/* 169 */         ((SSLSessionContext)localObject1).setSessionTimeout((int)Math.min(paramLong2, 2147483647L));
/*     */       }
/*     */     } catch (Exception localException) {
/* 172 */       throw new SSLException("failed to initialize the server-side SSL context", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isClient()
/*     */   {
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> nextProtocols()
/*     */   {
/* 183 */     return this.nextProtocols;
/*     */   }
/*     */   
/*     */   public SSLContext context()
/*     */   {
/* 188 */     return this.ctx;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\JdkSslClientContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */