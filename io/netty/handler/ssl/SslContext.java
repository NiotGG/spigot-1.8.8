/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SslContext
/*     */ {
/*     */   public static SslProvider defaultServerProvider()
/*     */   {
/*  61 */     if (OpenSsl.isAvailable()) {
/*  62 */       return SslProvider.OPENSSL;
/*     */     }
/*  64 */     return SslProvider.JDK;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SslProvider defaultClientProvider()
/*     */   {
/*  74 */     return SslProvider.JDK;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SslContext newServerContext(File paramFile1, File paramFile2)
/*     */     throws SSLException
/*     */   {
/*  85 */     return newServerContext(null, paramFile1, paramFile2, null, null, null, 0L, 0L);
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
/*     */   public static SslContext newServerContext(File paramFile1, File paramFile2, String paramString)
/*     */     throws SSLException
/*     */   {
/*  99 */     return newServerContext(null, paramFile1, paramFile2, paramString, null, null, 0L, 0L);
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
/*     */   public static SslContext newServerContext(File paramFile1, File paramFile2, String paramString, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 123 */     return newServerContext(null, paramFile1, paramFile2, paramString, paramIterable1, paramIterable2, paramLong1, paramLong2);
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
/*     */   public static SslContext newServerContext(SslProvider paramSslProvider, File paramFile1, File paramFile2)
/*     */     throws SSLException
/*     */   {
/* 139 */     return newServerContext(paramSslProvider, paramFile1, paramFile2, null, null, null, 0L, 0L);
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
/*     */   public static SslContext newServerContext(SslProvider paramSslProvider, File paramFile1, File paramFile2, String paramString)
/*     */     throws SSLException
/*     */   {
/* 155 */     return newServerContext(paramSslProvider, paramFile1, paramFile2, paramString, null, null, 0L, 0L);
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
/*     */   public static SslContext newServerContext(SslProvider paramSslProvider, File paramFile1, File paramFile2, String paramString, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 183 */     if (paramSslProvider == null) {
/* 184 */       paramSslProvider = OpenSsl.isAvailable() ? SslProvider.OPENSSL : SslProvider.JDK;
/*     */     }
/*     */     
/* 187 */     switch (paramSslProvider) {
/*     */     case JDK: 
/* 189 */       return new JdkSslServerContext(paramFile1, paramFile2, paramString, paramIterable1, paramIterable2, paramLong1, paramLong2);
/*     */     
/*     */ 
/*     */     case OPENSSL: 
/* 193 */       return new OpenSslServerContext(paramFile1, paramFile2, paramString, paramIterable1, paramIterable2, paramLong1, paramLong2);
/*     */     }
/*     */     
/*     */     
/* 197 */     throw new Error(paramSslProvider.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SslContext newClientContext()
/*     */     throws SSLException
/*     */   {
/* 207 */     return newClientContext(null, null, null, null, null, 0L, 0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SslContext newClientContext(File paramFile)
/*     */     throws SSLException
/*     */   {
/* 218 */     return newClientContext(null, paramFile, null, null, null, 0L, 0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SslContext newClientContext(TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/* 231 */     return newClientContext(null, null, paramTrustManagerFactory, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(File paramFile, TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/* 247 */     return newClientContext(null, paramFile, paramTrustManagerFactory, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(File paramFile, TrustManagerFactory paramTrustManagerFactory, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 273 */     return newClientContext(null, paramFile, paramTrustManagerFactory, paramIterable1, paramIterable2, paramLong1, paramLong2);
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
/*     */   public static SslContext newClientContext(SslProvider paramSslProvider)
/*     */     throws SSLException
/*     */   {
/* 287 */     return newClientContext(paramSslProvider, null, null, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(SslProvider paramSslProvider, File paramFile)
/*     */     throws SSLException
/*     */   {
/* 301 */     return newClientContext(paramSslProvider, paramFile, null, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(SslProvider paramSslProvider, TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/* 317 */     return newClientContext(paramSslProvider, null, paramTrustManagerFactory, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(SslProvider paramSslProvider, File paramFile, TrustManagerFactory paramTrustManagerFactory)
/*     */     throws SSLException
/*     */   {
/* 335 */     return newClientContext(paramSslProvider, paramFile, paramTrustManagerFactory, null, null, 0L, 0L);
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
/*     */   public static SslContext newClientContext(SslProvider paramSslProvider, File paramFile, TrustManagerFactory paramTrustManagerFactory, Iterable<String> paramIterable1, Iterable<String> paramIterable2, long paramLong1, long paramLong2)
/*     */     throws SSLException
/*     */   {
/* 365 */     if ((paramSslProvider != null) && (paramSslProvider != SslProvider.JDK)) {
/* 366 */       throw new SSLException("client context unsupported for: " + paramSslProvider);
/*     */     }
/*     */     
/* 369 */     return new JdkSslClientContext(paramFile, paramTrustManagerFactory, paramIterable1, paramIterable2, paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isServer()
/*     */   {
/* 380 */     return !isClient();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract boolean isClient();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<String> cipherSuites();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract long sessionCacheSize();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract long sessionTimeout();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract List<String> nextProtocols();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract SSLEngine newEngine(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SslHandler newHandler(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 434 */     return newHandler(newEngine(paramByteBufAllocator));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SslHandler newHandler(ByteBufAllocator paramByteBufAllocator, String paramString, int paramInt)
/*     */   {
/* 446 */     return newHandler(newEngine(paramByteBufAllocator, paramString, paramInt));
/*     */   }
/*     */   
/*     */   private static SslHandler newHandler(SSLEngine paramSSLEngine) {
/* 450 */     return new SslHandler(paramSSLEngine);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\SslContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */