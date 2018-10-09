/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.security.KeyManagementException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableKeyException;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLServerSocketFactory;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name="ssl", category="Core", printObject=true)
/*     */ public class SSLConfiguration
/*     */ {
/*  32 */   private static final StatusLogger LOGGER = ;
/*     */   private KeyStoreConfiguration keyStoreConfig;
/*     */   private TrustStoreConfiguration trustStoreConfig;
/*     */   private SSLContext sslContext;
/*     */   
/*     */   private SSLConfiguration(KeyStoreConfiguration paramKeyStoreConfiguration, TrustStoreConfiguration paramTrustStoreConfiguration) {
/*  38 */     this.keyStoreConfig = paramKeyStoreConfiguration;
/*  39 */     this.trustStoreConfig = paramTrustStoreConfiguration;
/*  40 */     this.sslContext = null;
/*     */   }
/*     */   
/*     */   public SSLSocketFactory getSSLSocketFactory() {
/*  44 */     if (this.sslContext == null) {
/*  45 */       this.sslContext = createSSLContext();
/*     */     }
/*  47 */     return this.sslContext.getSocketFactory();
/*     */   }
/*     */   
/*     */   public SSLServerSocketFactory getSSLServerSocketFactory() {
/*  51 */     if (this.sslContext == null) {
/*  52 */       this.sslContext = createSSLContext();
/*     */     }
/*  54 */     return this.sslContext.getServerSocketFactory();
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContext() {
/*  58 */     SSLContext localSSLContext = null;
/*     */     try
/*     */     {
/*  61 */       localSSLContext = createSSLContextBasedOnConfiguration();
/*  62 */       LOGGER.debug("Creating SSLContext with the given parameters");
/*     */     }
/*     */     catch (TrustStoreConfigurationException localTrustStoreConfigurationException) {
/*  65 */       localSSLContext = createSSLContextWithTrustStoreFailure();
/*     */     }
/*     */     catch (KeyStoreConfigurationException localKeyStoreConfigurationException) {
/*  68 */       localSSLContext = createSSLContextWithKeyStoreFailure();
/*     */     }
/*  70 */     return localSSLContext;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithTrustStoreFailure()
/*     */   {
/*     */     SSLContext localSSLContext;
/*     */     try {
/*  77 */       localSSLContext = createSSLContextWithDefaultTrustManagerFactory();
/*  78 */       LOGGER.debug("Creating SSLContext with default truststore");
/*     */     }
/*     */     catch (KeyStoreConfigurationException localKeyStoreConfigurationException) {
/*  81 */       localSSLContext = createDefaultSSLContext();
/*  82 */       LOGGER.debug("Creating SSLContext with default configuration");
/*     */     }
/*  84 */     return localSSLContext;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithKeyStoreFailure()
/*     */   {
/*     */     SSLContext localSSLContext;
/*     */     try {
/*  91 */       localSSLContext = createSSLContextWithDefaultKeyManagerFactory();
/*  92 */       LOGGER.debug("Creating SSLContext with default keystore");
/*     */     }
/*     */     catch (TrustStoreConfigurationException localTrustStoreConfigurationException) {
/*  95 */       localSSLContext = createDefaultSSLContext();
/*  96 */       LOGGER.debug("Creating SSLContext with default configuration");
/*     */     }
/*  98 */     return localSSLContext;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
/* 102 */     return createSSLContext(false, false);
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
/*     */     try {
/* 107 */       return createSSLContext(true, false);
/*     */     } catch (KeyStoreConfigurationException localKeyStoreConfigurationException) {
/* 109 */       LOGGER.debug("Exception occured while using default keystore. This should be a BUG"); }
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException
/*     */   {
/*     */     try {
/* 116 */       return createSSLContext(false, true);
/*     */     }
/*     */     catch (TrustStoreConfigurationException localTrustStoreConfigurationException) {
/* 119 */       LOGGER.debug("Exception occured while using default truststore. This should be a BUG"); }
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   private SSLContext createDefaultSSLContext()
/*     */   {
/*     */     try {
/* 126 */       return SSLContext.getDefault();
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 128 */       LOGGER.error("Failed to create an SSLContext with default configuration"); }
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   private SSLContext createSSLContext(boolean paramBoolean1, boolean paramBoolean2) throws KeyStoreConfigurationException, TrustStoreConfigurationException
/*     */   {
/*     */     try
/*     */     {
/* 136 */       KeyManager[] arrayOfKeyManager = null;
/* 137 */       TrustManager[] arrayOfTrustManager = null;
/*     */       
/* 139 */       SSLContext localSSLContext = SSLContext.getInstance("SSL");
/* 140 */       Object localObject; if (!paramBoolean1) {
/* 141 */         localObject = loadKeyManagerFactory();
/* 142 */         arrayOfKeyManager = ((KeyManagerFactory)localObject).getKeyManagers();
/*     */       }
/* 144 */       if (!paramBoolean2) {
/* 145 */         localObject = loadTrustManagerFactory();
/* 146 */         arrayOfTrustManager = ((TrustManagerFactory)localObject).getTrustManagers();
/*     */       }
/*     */       
/* 149 */       localSSLContext.init(arrayOfKeyManager, arrayOfTrustManager, null);
/* 150 */       return localSSLContext;
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 153 */       LOGGER.error("No Provider supports a TrustManagerFactorySpi implementation for the specified protocol");
/* 154 */       throw new TrustStoreConfigurationException(localNoSuchAlgorithmException);
/*     */     }
/*     */     catch (KeyManagementException localKeyManagementException) {
/* 157 */       LOGGER.error("Failed to initialize the SSLContext");
/* 158 */       throw new KeyStoreConfigurationException(localKeyManagementException);
/*     */     }
/*     */   }
/*     */   
/*     */   private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
/* 163 */     KeyStore localKeyStore = null;
/* 164 */     TrustManagerFactory localTrustManagerFactory = null;
/*     */     
/* 166 */     if (this.trustStoreConfig == null) {
/* 167 */       throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
/*     */     }
/*     */     try {
/* 170 */       localKeyStore = this.trustStoreConfig.getTrustStore();
/* 171 */       localTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/* 172 */       localTrustManagerFactory.init(localKeyStore);
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 175 */       LOGGER.error("The specified algorithm is not available from the specified provider");
/* 176 */       throw new TrustStoreConfigurationException(localNoSuchAlgorithmException);
/*     */     } catch (KeyStoreException localKeyStoreException) {
/* 178 */       LOGGER.error("Failed to initialize the TrustManagerFactory");
/* 179 */       throw new TrustStoreConfigurationException(localKeyStoreException);
/*     */     } catch (StoreConfigurationException localStoreConfigurationException) {
/* 181 */       throw new TrustStoreConfigurationException(localStoreConfigurationException);
/*     */     }
/*     */     
/* 184 */     return localTrustManagerFactory;
/*     */   }
/*     */   
/*     */   private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
/* 188 */     KeyStore localKeyStore = null;
/* 189 */     KeyManagerFactory localKeyManagerFactory = null;
/*     */     
/* 191 */     if (this.keyStoreConfig == null) {
/* 192 */       throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
/*     */     }
/*     */     try {
/* 195 */       localKeyStore = this.keyStoreConfig.getKeyStore();
/* 196 */       localKeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
/* 197 */       localKeyManagerFactory.init(localKeyStore, this.keyStoreConfig.getPasswordAsCharArray());
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/* 200 */       LOGGER.error("The specified algorithm is not available from the specified provider");
/* 201 */       throw new KeyStoreConfigurationException(localNoSuchAlgorithmException);
/*     */     } catch (KeyStoreException localKeyStoreException) {
/* 203 */       LOGGER.error("Failed to initialize the TrustManagerFactory");
/* 204 */       throw new KeyStoreConfigurationException(localKeyStoreException);
/*     */     } catch (StoreConfigurationException localStoreConfigurationException) {
/* 206 */       throw new KeyStoreConfigurationException(localStoreConfigurationException);
/*     */     } catch (UnrecoverableKeyException localUnrecoverableKeyException) {
/* 208 */       LOGGER.error("The key cannot be recovered (e.g. the given password is wrong)");
/* 209 */       throw new KeyStoreConfigurationException(localUnrecoverableKeyException);
/*     */     }
/*     */     
/* 212 */     return localKeyManagerFactory;
/*     */   }
/*     */   
/*     */   public boolean equals(SSLConfiguration paramSSLConfiguration) {
/* 216 */     if (paramSSLConfiguration == null) {
/* 217 */       return false;
/*     */     }
/* 219 */     boolean bool1 = false;
/* 220 */     boolean bool2 = false;
/*     */     
/* 222 */     if (this.keyStoreConfig != null) {
/* 223 */       bool1 = this.keyStoreConfig.equals(paramSSLConfiguration.keyStoreConfig);
/*     */     } else {
/* 225 */       bool1 = this.keyStoreConfig == paramSSLConfiguration.keyStoreConfig;
/*     */     }
/* 227 */     if (this.trustStoreConfig != null) {
/* 228 */       bool2 = this.trustStoreConfig.equals(paramSSLConfiguration.trustStoreConfig);
/*     */     } else {
/* 230 */       bool2 = this.trustStoreConfig == paramSSLConfiguration.trustStoreConfig;
/*     */     }
/* 232 */     return (bool1) && (bool2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static SSLConfiguration createSSLConfiguration(@PluginElement("keyStore") KeyStoreConfiguration paramKeyStoreConfiguration, @PluginElement("trustStore") TrustStoreConfiguration paramTrustStoreConfiguration)
/*     */   {
/* 245 */     return new SSLConfiguration(paramKeyStoreConfiguration, paramTrustStoreConfiguration);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\SSLConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */