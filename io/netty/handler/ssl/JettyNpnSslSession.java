/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class JettyNpnSslSession
/*     */   implements SSLSession
/*     */ {
/*     */   private final SSLEngine engine;
/*     */   private volatile String applicationProtocol;
/*     */   
/*     */   JettyNpnSslSession(SSLEngine paramSSLEngine)
/*     */   {
/*  33 */     this.engine = paramSSLEngine;
/*     */   }
/*     */   
/*     */   void setApplicationProtocol(String paramString) {
/*  37 */     if (paramString != null) {
/*  38 */       paramString = paramString.replace(':', '_');
/*     */     }
/*  40 */     this.applicationProtocol = paramString;
/*     */   }
/*     */   
/*     */   public String getProtocol()
/*     */   {
/*  45 */     String str1 = unwrap().getProtocol();
/*  46 */     String str2 = this.applicationProtocol;
/*     */     
/*  48 */     if (str2 == null) {
/*  49 */       if (str1 != null) {
/*  50 */         return str1.replace(':', '_');
/*     */       }
/*  52 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  56 */     StringBuilder localStringBuilder = new StringBuilder(32);
/*  57 */     if (str1 != null) {
/*  58 */       localStringBuilder.append(str1.replace(':', '_'));
/*  59 */       localStringBuilder.append(':');
/*     */     } else {
/*  61 */       localStringBuilder.append("null:");
/*     */     }
/*  63 */     localStringBuilder.append(str2);
/*  64 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private SSLSession unwrap() {
/*  68 */     return this.engine.getSession();
/*     */   }
/*     */   
/*     */   public byte[] getId()
/*     */   {
/*  73 */     return unwrap().getId();
/*     */   }
/*     */   
/*     */   public SSLSessionContext getSessionContext()
/*     */   {
/*  78 */     return unwrap().getSessionContext();
/*     */   }
/*     */   
/*     */   public long getCreationTime()
/*     */   {
/*  83 */     return unwrap().getCreationTime();
/*     */   }
/*     */   
/*     */   public long getLastAccessedTime()
/*     */   {
/*  88 */     return unwrap().getLastAccessedTime();
/*     */   }
/*     */   
/*     */   public void invalidate()
/*     */   {
/*  93 */     unwrap().invalidate();
/*     */   }
/*     */   
/*     */   public boolean isValid()
/*     */   {
/*  98 */     return unwrap().isValid();
/*     */   }
/*     */   
/*     */   public void putValue(String paramString, Object paramObject)
/*     */   {
/* 103 */     unwrap().putValue(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public Object getValue(String paramString)
/*     */   {
/* 108 */     return unwrap().getValue(paramString);
/*     */   }
/*     */   
/*     */   public void removeValue(String paramString)
/*     */   {
/* 113 */     unwrap().removeValue(paramString);
/*     */   }
/*     */   
/*     */   public String[] getValueNames()
/*     */   {
/* 118 */     return unwrap().getValueNames();
/*     */   }
/*     */   
/*     */   public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException
/*     */   {
/* 123 */     return unwrap().getPeerCertificates();
/*     */   }
/*     */   
/*     */   public Certificate[] getLocalCertificates()
/*     */   {
/* 128 */     return unwrap().getLocalCertificates();
/*     */   }
/*     */   
/*     */   public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException
/*     */   {
/* 133 */     return unwrap().getPeerCertificateChain();
/*     */   }
/*     */   
/*     */   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException
/*     */   {
/* 138 */     return unwrap().getPeerPrincipal();
/*     */   }
/*     */   
/*     */   public Principal getLocalPrincipal()
/*     */   {
/* 143 */     return unwrap().getLocalPrincipal();
/*     */   }
/*     */   
/*     */   public String getCipherSuite()
/*     */   {
/* 148 */     return unwrap().getCipherSuite();
/*     */   }
/*     */   
/*     */   public String getPeerHost()
/*     */   {
/* 153 */     return unwrap().getPeerHost();
/*     */   }
/*     */   
/*     */   public int getPeerPort()
/*     */   {
/* 158 */     return unwrap().getPeerPort();
/*     */   }
/*     */   
/*     */   public int getPacketBufferSize()
/*     */   {
/* 163 */     return unwrap().getPacketBufferSize();
/*     */   }
/*     */   
/*     */   public int getApplicationBufferSize()
/*     */   {
/* 168 */     return unwrap().getApplicationBufferSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\JettyNpnSslSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */