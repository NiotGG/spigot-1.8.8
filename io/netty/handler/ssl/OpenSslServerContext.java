/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLException;
/*     */ import org.apache.tomcat.jni.Pool;
/*     */ import org.apache.tomcat.jni.SSL;
/*     */ import org.apache.tomcat.jni.SSLContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OpenSslServerContext
/*     */   extends SslContext
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSslServerContext.class);
/*     */   private static final List<String> DEFAULT_CIPHERS;
/*     */   private final long aprPool;
/*     */   
/*  41 */   static { ArrayList localArrayList = new ArrayList();
/*     */     
/*  43 */     Collections.addAll(localArrayList, new String[] { "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-RC4-SHA", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "RC4-SHA", "RC4-MD5", "AES128-SHA", "AES256-SHA", "DES-CBC3-SHA" });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */     DEFAULT_CIPHERS = Collections.unmodifiableList(localArrayList);
/*     */     
/*  57 */     if (logger.isDebugEnabled()) {
/*  58 */       logger.debug("Default cipher suite (OpenSSL): " + localArrayList);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  64 */   private final List<String> ciphers = new ArrayList();
/*  65 */   private final List<String> unmodifiableCiphers = Collections.unmodifiableList(this.ciphers);
/*     */   
/*     */   private final long sessionCacheSize;
/*     */   
/*     */   private final long sessionTimeout;
/*     */   
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   private final long ctx;
/*     */   
/*     */   private final OpenSslSessionStats stats;
/*     */   
/*     */ 
/*     */   public OpenSslServerContext(File paramFile1, File paramFile2)
/*     */     throws SSLException
/*     */   {
/*  81 */     this(paramFile1, paramFile2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OpenSslServerContext(File paramFile1, File paramFile2, String paramString)
/*     */     throws SSLException
/*     */   {
/*  93 */     this(paramFile1, paramFile2, paramString, null, null, 0L, 0L);
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
/*     */   public OpenSslServerContext(File paramFile1, File paramFile2, String paramString, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 117 */     OpenSsl.ensureAvailability();
/*     */     
/* 119 */     if (paramFile1 == null) {
/* 120 */       throw new NullPointerException("certChainFile");
/*     */     }
/* 122 */     if (!paramFile1.isFile()) {
/* 123 */       throw new IllegalArgumentException("certChainFile is not a file: " + paramFile1);
/*     */     }
/* 125 */     if (paramFile2 == null) {
/* 126 */       throw new NullPointerException("keyPath");
/*     */     }
/* 128 */     if (!paramFile2.isFile()) {
/* 129 */       throw new IllegalArgumentException("keyPath is not a file: " + paramFile2);
/*     */     }
/* 131 */     if (paramIterable1 == null) {
/* 132 */       paramIterable1 = DEFAULT_CIPHERS;
/*     */     }
/*     */     
/* 135 */     if (paramString == null) {
/* 136 */       paramString = "";
/*     */     }
/* 138 */     if (paramIterable2 == null) {
/* 139 */       paramIterable2 = Collections.emptyList();
/*     */     }
/*     */     
/* 142 */     for (Object localObject1 = paramIterable1.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (String)((Iterator)localObject1).next();
/* 143 */       if (localObject2 == null) {
/*     */         break;
/*     */       }
/* 146 */       this.ciphers.add(localObject2);
/*     */     }
/*     */     
/* 149 */     localObject1 = new ArrayList();
/* 150 */     for (Object localObject2 = paramIterable2.iterator(); ((Iterator)localObject2).hasNext();) { String str1 = (String)((Iterator)localObject2).next();
/* 151 */       if (str1 == null) {
/*     */         break;
/*     */       }
/* 154 */       ((List)localObject1).add(str1);
/*     */     }
/* 156 */     this.nextProtocols = Collections.unmodifiableList((List)localObject1);
/*     */     
/*     */ 
/* 159 */     this.aprPool = Pool.create(0L);
/*     */     
/*     */ 
/* 162 */     int i = 0;
/*     */     try {
/* 164 */       synchronized (OpenSslServerContext.class) {
/*     */         try {
/* 166 */           this.ctx = SSLContext.make(this.aprPool, 6, 1);
/*     */         } catch (Exception localException1) {
/* 168 */           throw new SSLException("failed to create an SSL_CTX", localException1);
/*     */         }
/*     */         
/* 171 */         SSLContext.setOptions(this.ctx, 4095);
/* 172 */         SSLContext.setOptions(this.ctx, 16777216);
/* 173 */         SSLContext.setOptions(this.ctx, 4194304);
/* 174 */         SSLContext.setOptions(this.ctx, 524288);
/* 175 */         SSLContext.setOptions(this.ctx, 1048576);
/* 176 */         SSLContext.setOptions(this.ctx, 65536);
/*     */         Iterator localIterator;
/*     */         String str2;
/*     */         try
/*     */         {
/* 181 */           StringBuilder localStringBuilder = new StringBuilder();
/* 182 */           for (localIterator = this.ciphers.iterator(); localIterator.hasNext();) { str2 = (String)localIterator.next();
/* 183 */             localStringBuilder.append(str2);
/* 184 */             localStringBuilder.append(':');
/*     */           }
/* 186 */           localStringBuilder.setLength(localStringBuilder.length() - 1);
/*     */           
/* 188 */           SSLContext.setCipherSuite(this.ctx, localStringBuilder.toString());
/*     */         } catch (SSLException localSSLException1) {
/* 190 */           throw localSSLException1;
/*     */         } catch (Exception localException2) {
/* 192 */           throw new SSLException("failed to set cipher suite: " + this.ciphers, localException2);
/*     */         }
/*     */         
/*     */ 
/* 196 */         SSLContext.setVerify(this.ctx, 0, 10);
/*     */         
/*     */         try
/*     */         {
/* 200 */           if (!SSLContext.setCertificate(this.ctx, paramFile1.getPath(), paramFile2.getPath(), paramString, 0))
/*     */           {
/* 202 */             throw new SSLException("failed to set certificate: " + paramFile1 + " and " + paramFile2 + " (" + SSL.getLastError() + ')');
/*     */           }
/*     */         }
/*     */         catch (SSLException localSSLException2) {
/* 206 */           throw localSSLException2;
/*     */         } catch (Exception localException3) {
/* 208 */           throw new SSLException("failed to set certificate: " + paramFile1 + " and " + paramFile2, localException3);
/*     */         }
/*     */         
/*     */         Object localObject3;
/* 212 */         if (!SSLContext.setCertificateChainFile(this.ctx, paramFile1.getPath(), true)) {
/* 213 */           localObject3 = SSL.getLastError();
/* 214 */           if (!((String)localObject3).startsWith("error:00000000:")) {
/* 215 */             throw new SSLException("failed to set certificate chain: " + paramFile1 + " (" + SSL.getLastError() + ')');
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 221 */         if (!((List)localObject1).isEmpty())
/*     */         {
/* 223 */           localObject3 = new StringBuilder();
/* 224 */           for (localIterator = ((List)localObject1).iterator(); localIterator.hasNext();) { str2 = (String)localIterator.next();
/* 225 */             ((StringBuilder)localObject3).append(str2);
/* 226 */             ((StringBuilder)localObject3).append(',');
/*     */           }
/* 228 */           ((StringBuilder)localObject3).setLength(((StringBuilder)localObject3).length() - 1);
/*     */           
/* 230 */           SSLContext.setNextProtos(this.ctx, ((StringBuilder)localObject3).toString());
/*     */         }
/*     */         
/*     */ 
/* 234 */         if (paramLong1 > 0L) {
/* 235 */           this.sessionCacheSize = paramLong1;
/* 236 */           SSLContext.setSessionCacheSize(this.ctx, paramLong1);
/*     */         }
/*     */         else {
/* 239 */           this.sessionCacheSize = (paramLong1 = SSLContext.setSessionCacheSize(this.ctx, 20480L));
/*     */           
/* 241 */           SSLContext.setSessionCacheSize(this.ctx, paramLong1);
/*     */         }
/*     */         
/*     */ 
/* 245 */         if (paramLong2 > 0L) {
/* 246 */           this.sessionTimeout = paramLong2;
/* 247 */           SSLContext.setSessionCacheTimeout(this.ctx, paramLong2);
/*     */         }
/*     */         else {
/* 250 */           this.sessionTimeout = (paramLong2 = SSLContext.setSessionCacheTimeout(this.ctx, 300L));
/*     */           
/* 252 */           SSLContext.setSessionCacheTimeout(this.ctx, paramLong2);
/*     */         }
/*     */       }
/* 255 */       i = 1;
/*     */     } finally {
/* 257 */       if (i == 0) {
/* 258 */         destroyPools();
/*     */       }
/*     */     }
/*     */     
/* 262 */     this.stats = new OpenSslSessionStats(this.ctx);
/*     */   }
/*     */   
/*     */   public boolean isClient()
/*     */   {
/* 267 */     return false;
/*     */   }
/*     */   
/*     */   public List<String> cipherSuites()
/*     */   {
/* 272 */     return this.unmodifiableCiphers;
/*     */   }
/*     */   
/*     */   public long sessionCacheSize()
/*     */   {
/* 277 */     return this.sessionCacheSize;
/*     */   }
/*     */   
/*     */   public long sessionTimeout()
/*     */   {
/* 282 */     return this.sessionTimeout;
/*     */   }
/*     */   
/*     */   public List<String> nextProtocols()
/*     */   {
/* 287 */     return this.nextProtocols;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long context()
/*     */   {
/* 294 */     return this.ctx;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public OpenSslSessionStats stats()
/*     */   {
/* 301 */     return this.stats;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 309 */     if (this.nextProtocols.isEmpty()) {
/* 310 */       return new OpenSslEngine(this.ctx, paramByteBufAllocator, null);
/*     */     }
/* 312 */     return new OpenSslEngine(this.ctx, paramByteBufAllocator, (String)this.nextProtocols.get(this.nextProtocols.size() - 1));
/*     */   }
/*     */   
/*     */ 
/*     */   public SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt)
/*     */   {
/* 318 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTicketKeys(byte[] paramArrayOfByte)
/*     */   {
/* 325 */     if (paramArrayOfByte == null) {
/* 326 */       throw new NullPointerException("keys");
/*     */     }
/* 328 */     SSLContext.setSessionTicketKeys(this.ctx, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/* 334 */     super.finalize();
/* 335 */     synchronized (OpenSslServerContext.class) {
/* 336 */       if (this.ctx != 0L) {
/* 337 */         SSLContext.free(this.ctx);
/*     */       }
/*     */     }
/*     */     
/* 341 */     destroyPools();
/*     */   }
/*     */   
/*     */   private void destroyPools() {
/* 345 */     if (this.aprPool != 0L) {
/* 346 */       Pool.destroy(this.aprPool);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\OpenSslServerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */