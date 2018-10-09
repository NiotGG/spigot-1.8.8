/*    */ package io.netty.handler.ssl.util;
/*    */ 
/*    */ import io.netty.util.internal.EmptyArrays;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.security.KeyStore;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.ManagerFactoryParameters;
/*    */ import javax.net.ssl.TrustManager;
/*    */ import javax.net.ssl.TrustManagerFactory;
/*    */ import javax.net.ssl.X509TrustManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InsecureTrustManagerFactory
/*    */   extends SimpleTrustManagerFactory
/*    */ {
/* 40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(InsecureTrustManagerFactory.class);
/*    */   
/* 42 */   public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();
/*    */   
/* 44 */   private static final TrustManager tm = new X509TrustManager()
/*    */   {
/*    */     public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) {
/* 47 */       InsecureTrustManagerFactory.logger.debug("Accepting a client certificate: " + paramAnonymousArrayOfX509Certificate[0].getSubjectDN());
/*    */     }
/*    */     
/*    */     public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
/*    */     {
/* 52 */       InsecureTrustManagerFactory.logger.debug("Accepting a server certificate: " + paramAnonymousArrayOfX509Certificate[0].getSubjectDN());
/*    */     }
/*    */     
/*    */     public X509Certificate[] getAcceptedIssuers()
/*    */     {
/* 57 */       return EmptyArrays.EMPTY_X509_CERTIFICATES;
/*    */     }
/*    */   };
/*    */   
/*    */   protected void engineInit(KeyStore paramKeyStore)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   protected void engineInit(ManagerFactoryParameters paramManagerFactoryParameters)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   protected TrustManager[] engineGetTrustManagers()
/*    */   {
/* 71 */     return new TrustManager[] { tm };
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\util\InsecureTrustManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */