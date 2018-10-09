/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLEngineResult.HandshakeStatus;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import org.eclipse.jetty.npn.NextProtoNego;
/*     */ import org.eclipse.jetty.npn.NextProtoNego.ClientProvider;
/*     */ import org.eclipse.jetty.npn.NextProtoNego.ServerProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class JettyNpnSslEngine
/*     */   extends SSLEngine
/*     */ {
/*     */   private static boolean available;
/*     */   private final SSLEngine engine;
/*     */   private final JettyNpnSslSession session;
/*     */   
/*     */   static boolean isAvailable()
/*     */   {
/*  37 */     updateAvailability();
/*  38 */     return available;
/*     */   }
/*     */   
/*     */   private static void updateAvailability() {
/*  42 */     if (available) {
/*  43 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  47 */       ClassLoader localClassLoader = ClassLoader.getSystemClassLoader().getParent();
/*  48 */       if (localClassLoader == null)
/*     */       {
/*     */ 
/*  51 */         localClassLoader = ClassLoader.getSystemClassLoader();
/*     */       }
/*  53 */       Class.forName("sun.security.ssl.NextProtoNegoExtension", true, localClassLoader);
/*  54 */       available = true;
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   JettyNpnSslEngine(SSLEngine paramSSLEngine, final List<String> paramList, boolean paramBoolean)
/*     */   {
/*  64 */     assert (!paramList.isEmpty());
/*     */     
/*  66 */     this.engine = paramSSLEngine;
/*  67 */     this.session = new JettyNpnSslSession(paramSSLEngine);
/*     */     
/*  69 */     if (paramBoolean) {
/*  70 */       NextProtoNego.put(paramSSLEngine, new NextProtoNego.ServerProvider()
/*     */       {
/*     */         public void unsupported() {
/*  73 */           JettyNpnSslEngine.this.getSession().setApplicationProtocol((String)paramList.get(paramList.size() - 1));
/*     */         }
/*     */         
/*     */         public List<String> protocols()
/*     */         {
/*  78 */           return paramList;
/*     */         }
/*     */         
/*     */         public void protocolSelected(String paramAnonymousString)
/*     */         {
/*  83 */           JettyNpnSslEngine.this.getSession().setApplicationProtocol(paramAnonymousString);
/*     */         }
/*     */       });
/*     */     } else {
/*  87 */       final String[] arrayOfString = (String[])paramList.toArray(new String[paramList.size()]);
/*  88 */       final String str = arrayOfString[(arrayOfString.length - 1)];
/*     */       
/*  90 */       NextProtoNego.put(paramSSLEngine, new NextProtoNego.ClientProvider()
/*     */       {
/*     */         public boolean supports() {
/*  93 */           return true;
/*     */         }
/*     */         
/*     */         public void unsupported()
/*     */         {
/*  98 */           JettyNpnSslEngine.this.session.setApplicationProtocol(null);
/*     */         }
/*     */         
/*     */         public String selectProtocol(List<String> paramAnonymousList)
/*     */         {
/* 103 */           for (String str : arrayOfString) {
/* 104 */             if (paramAnonymousList.contains(str)) {
/* 105 */               return str;
/*     */             }
/*     */           }
/* 108 */           return str;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   public JettyNpnSslSession getSession()
/*     */   {
/* 116 */     return this.session;
/*     */   }
/*     */   
/*     */   public void closeInbound() throws SSLException
/*     */   {
/* 121 */     NextProtoNego.remove(this.engine);
/* 122 */     this.engine.closeInbound();
/*     */   }
/*     */   
/*     */   public void closeOutbound()
/*     */   {
/* 127 */     NextProtoNego.remove(this.engine);
/* 128 */     this.engine.closeOutbound();
/*     */   }
/*     */   
/*     */   public String getPeerHost()
/*     */   {
/* 133 */     return this.engine.getPeerHost();
/*     */   }
/*     */   
/*     */   public int getPeerPort()
/*     */   {
/* 138 */     return this.engine.getPeerPort();
/*     */   }
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws SSLException
/*     */   {
/* 143 */     return this.engine.wrap(paramByteBuffer1, paramByteBuffer2);
/*     */   }
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] paramArrayOfByteBuffer, ByteBuffer paramByteBuffer) throws SSLException
/*     */   {
/* 148 */     return this.engine.wrap(paramArrayOfByteBuffer, paramByteBuffer);
/*     */   }
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer) throws SSLException
/*     */   {
/* 153 */     return this.engine.wrap(paramArrayOfByteBuffer, paramInt1, paramInt2, paramByteBuffer);
/*     */   }
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws SSLException
/*     */   {
/* 158 */     return this.engine.unwrap(paramByteBuffer1, paramByteBuffer2);
/*     */   }
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer) throws SSLException
/*     */   {
/* 163 */     return this.engine.unwrap(paramByteBuffer, paramArrayOfByteBuffer);
/*     */   }
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) throws SSLException
/*     */   {
/* 168 */     return this.engine.unwrap(paramByteBuffer, paramArrayOfByteBuffer, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public Runnable getDelegatedTask()
/*     */   {
/* 173 */     return this.engine.getDelegatedTask();
/*     */   }
/*     */   
/*     */   public boolean isInboundDone()
/*     */   {
/* 178 */     return this.engine.isInboundDone();
/*     */   }
/*     */   
/*     */   public boolean isOutboundDone()
/*     */   {
/* 183 */     return this.engine.isOutboundDone();
/*     */   }
/*     */   
/*     */   public String[] getSupportedCipherSuites()
/*     */   {
/* 188 */     return this.engine.getSupportedCipherSuites();
/*     */   }
/*     */   
/*     */   public String[] getEnabledCipherSuites()
/*     */   {
/* 193 */     return this.engine.getEnabledCipherSuites();
/*     */   }
/*     */   
/*     */   public void setEnabledCipherSuites(String[] paramArrayOfString)
/*     */   {
/* 198 */     this.engine.setEnabledCipherSuites(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public String[] getSupportedProtocols()
/*     */   {
/* 203 */     return this.engine.getSupportedProtocols();
/*     */   }
/*     */   
/*     */   public String[] getEnabledProtocols()
/*     */   {
/* 208 */     return this.engine.getEnabledProtocols();
/*     */   }
/*     */   
/*     */   public void setEnabledProtocols(String[] paramArrayOfString)
/*     */   {
/* 213 */     this.engine.setEnabledProtocols(paramArrayOfString);
/*     */   }
/*     */   
/*     */   public SSLSession getHandshakeSession()
/*     */   {
/* 218 */     return this.engine.getHandshakeSession();
/*     */   }
/*     */   
/*     */   public void beginHandshake() throws SSLException
/*     */   {
/* 223 */     this.engine.beginHandshake();
/*     */   }
/*     */   
/*     */   public SSLEngineResult.HandshakeStatus getHandshakeStatus()
/*     */   {
/* 228 */     return this.engine.getHandshakeStatus();
/*     */   }
/*     */   
/*     */   public void setUseClientMode(boolean paramBoolean)
/*     */   {
/* 233 */     this.engine.setUseClientMode(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getUseClientMode()
/*     */   {
/* 238 */     return this.engine.getUseClientMode();
/*     */   }
/*     */   
/*     */   public void setNeedClientAuth(boolean paramBoolean)
/*     */   {
/* 243 */     this.engine.setNeedClientAuth(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getNeedClientAuth()
/*     */   {
/* 248 */     return this.engine.getNeedClientAuth();
/*     */   }
/*     */   
/*     */   public void setWantClientAuth(boolean paramBoolean)
/*     */   {
/* 253 */     this.engine.setWantClientAuth(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getWantClientAuth()
/*     */   {
/* 258 */     return this.engine.getWantClientAuth();
/*     */   }
/*     */   
/*     */   public void setEnableSessionCreation(boolean paramBoolean)
/*     */   {
/* 263 */     this.engine.setEnableSessionCreation(paramBoolean);
/*     */   }
/*     */   
/*     */   public boolean getEnableSessionCreation()
/*     */   {
/* 268 */     return this.engine.getEnableSessionCreation();
/*     */   }
/*     */   
/*     */   public SSLParameters getSSLParameters()
/*     */   {
/* 273 */     return this.engine.getSSLParameters();
/*     */   }
/*     */   
/*     */   public void setSSLParameters(SSLParameters paramSSLParameters)
/*     */   {
/* 278 */     this.engine.setSSLParameters(paramSSLParameters);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\JettyNpnSslEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */