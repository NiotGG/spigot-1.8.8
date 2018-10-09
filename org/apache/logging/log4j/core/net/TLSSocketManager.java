/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.net.ssl.SSLConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TLSSocketManager
/*     */   extends TCPSocketManager
/*     */ {
/*     */   public static final int DEFAULT_PORT = 6514;
/*  38 */   private static final TLSSocketManagerFactory FACTORY = new TLSSocketManagerFactory(null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private SSLConfiguration sslConfig;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TLSSocketManager(String paramString1, OutputStream paramOutputStream, Socket paramSocket, SSLConfiguration paramSSLConfiguration, InetAddress paramInetAddress, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean, Layout paramLayout)
/*     */   {
/*  56 */     super(paramString1, paramOutputStream, paramSocket, paramInetAddress, paramString2, paramInt1, paramInt2, paramBoolean, paramLayout);
/*  57 */     this.sslConfig = paramSSLConfiguration;
/*     */   }
/*     */   
/*     */   private static class TLSFactoryData
/*     */   {
/*     */     protected SSLConfiguration sslConfig;
/*     */     private final String host;
/*     */     private final int port;
/*     */     private final int delay;
/*     */     private final boolean immediateFail;
/*     */     private final Layout layout;
/*     */     
/*     */     public TLSFactoryData(SSLConfiguration paramSSLConfiguration, String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout paramLayout) {
/*  70 */       this.host = paramString;
/*  71 */       this.port = paramInt1;
/*  72 */       this.delay = paramInt2;
/*  73 */       this.immediateFail = paramBoolean;
/*  74 */       this.layout = paramLayout;
/*  75 */       this.sslConfig = paramSSLConfiguration;
/*     */     }
/*     */   }
/*     */   
/*     */   public static TLSSocketManager getSocketManager(SSLConfiguration paramSSLConfiguration, String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout paramLayout)
/*     */   {
/*  81 */     if (Strings.isEmpty(paramString)) {
/*  82 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/*  84 */     if (paramInt1 <= 0) {
/*  85 */       paramInt1 = 6514;
/*     */     }
/*  87 */     if (paramInt2 == 0) {
/*  88 */       paramInt2 = 30000;
/*     */     }
/*  90 */     return (TLSSocketManager)getManager("TLS:" + paramString + ":" + paramInt1, new TLSFactoryData(paramSSLConfiguration, paramString, paramInt1, paramInt2, paramBoolean, paramLayout), FACTORY);
/*     */   }
/*     */   
/*     */   protected Socket createSocket(String paramString, int paramInt)
/*     */     throws IOException
/*     */   {
/*  96 */     SSLSocketFactory localSSLSocketFactory = createSSLSocketFactory(this.sslConfig);
/*  97 */     return localSSLSocketFactory.createSocket(paramString, paramInt);
/*     */   }
/*     */   
/*     */   private static SSLSocketFactory createSSLSocketFactory(SSLConfiguration paramSSLConfiguration)
/*     */   {
/*     */     SSLSocketFactory localSSLSocketFactory;
/* 103 */     if (paramSSLConfiguration != null) {
/* 104 */       localSSLSocketFactory = paramSSLConfiguration.getSSLSocketFactory();
/*     */     } else {
/* 106 */       localSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*     */     }
/* 108 */     return localSSLSocketFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class TLSSocketManagerFactory
/*     */     implements ManagerFactory<TLSSocketManager, TLSSocketManager.TLSFactoryData>
/*     */   {
/*     */     public TLSSocketManager createManager(String paramString, TLSSocketManager.TLSFactoryData paramTLSFactoryData)
/*     */     {
/* 119 */       InetAddress localInetAddress = null;
/* 120 */       Object localObject = null;
/* 121 */       Socket localSocket = null;
/*     */       try
/*     */       {
/* 124 */         localInetAddress = resolveAddress(TLSSocketManager.TLSFactoryData.access$100(paramTLSFactoryData));
/* 125 */         localSocket = createSocket(paramTLSFactoryData);
/* 126 */         localObject = localSocket.getOutputStream();
/* 127 */         checkDelay(TLSSocketManager.TLSFactoryData.access$200(paramTLSFactoryData), (OutputStream)localObject);
/*     */       }
/*     */       catch (IOException localIOException) {
/* 130 */         TLSSocketManager.LOGGER.error("TLSSocketManager (" + paramString + ") " + localIOException);
/* 131 */         localObject = new ByteArrayOutputStream();
/*     */       }
/*     */       catch (TLSSocketManagerFactoryException localTLSSocketManagerFactoryException) {
/* 134 */         return null;
/*     */       }
/* 136 */       return createManager(paramString, (OutputStream)localObject, localSocket, paramTLSFactoryData.sslConfig, localInetAddress, TLSSocketManager.TLSFactoryData.access$100(paramTLSFactoryData), TLSSocketManager.TLSFactoryData.access$400(paramTLSFactoryData), TLSSocketManager.TLSFactoryData.access$200(paramTLSFactoryData), TLSSocketManager.TLSFactoryData.access$500(paramTLSFactoryData), TLSSocketManager.TLSFactoryData.access$600(paramTLSFactoryData));
/*     */     }
/*     */     
/*     */     private InetAddress resolveAddress(String paramString) throws TLSSocketManager.TLSSocketManagerFactory.TLSSocketManagerFactoryException
/*     */     {
/*     */       InetAddress localInetAddress;
/*     */       try {
/* 143 */         localInetAddress = InetAddress.getByName(paramString);
/*     */       } catch (UnknownHostException localUnknownHostException) {
/* 145 */         TLSSocketManager.LOGGER.error("Could not find address of " + paramString, localUnknownHostException);
/* 146 */         throw new TLSSocketManagerFactoryException(null);
/*     */       }
/*     */       
/* 149 */       return localInetAddress;
/*     */     }
/*     */     
/*     */     private void checkDelay(int paramInt, OutputStream paramOutputStream) throws TLSSocketManager.TLSSocketManagerFactory.TLSSocketManagerFactoryException {
/* 153 */       if ((paramInt == 0) && (paramOutputStream == null)) {
/* 154 */         throw new TLSSocketManagerFactoryException(null);
/*     */       }
/*     */     }
/*     */     
/*     */     private Socket createSocket(TLSSocketManager.TLSFactoryData paramTLSFactoryData)
/*     */       throws IOException
/*     */     {
/* 161 */       SSLSocketFactory localSSLSocketFactory = TLSSocketManager.createSSLSocketFactory(paramTLSFactoryData.sslConfig);
/* 162 */       SSLSocket localSSLSocket = (SSLSocket)localSSLSocketFactory.createSocket(TLSSocketManager.TLSFactoryData.access$100(paramTLSFactoryData), TLSSocketManager.TLSFactoryData.access$400(paramTLSFactoryData));
/* 163 */       return localSSLSocket;
/*     */     }
/*     */     
/*     */     private TLSSocketManager createManager(String paramString1, OutputStream paramOutputStream, Socket paramSocket, SSLConfiguration paramSSLConfiguration, InetAddress paramInetAddress, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean, Layout paramLayout) {
/* 167 */       return new TLSSocketManager(paramString1, paramOutputStream, paramSocket, paramSSLConfiguration, paramInetAddress, paramString2, paramInt1, paramInt2, paramBoolean, paramLayout);
/*     */     }
/*     */     
/*     */     private class TLSSocketManagerFactoryException
/*     */       extends Exception
/*     */     {
/*     */       private TLSSocketManagerFactoryException() {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\TLSSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */