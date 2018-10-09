/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateException;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="trustStore", category="Core", printObject=true)
/*     */ public class TrustStoreConfiguration
/*     */   extends StoreConfiguration
/*     */ {
/*     */   private KeyStore trustStore;
/*     */   private String trustStoreType;
/*     */   
/*     */   public TrustStoreConfiguration(String paramString1, String paramString2)
/*     */   {
/*  39 */     super(paramString1, paramString2);
/*  40 */     this.trustStoreType = "JKS";
/*  41 */     this.trustStore = null;
/*     */   }
/*     */   
/*     */   protected void load() throws StoreConfigurationException
/*     */   {
/*  46 */     KeyStore localKeyStore = null;
/*  47 */     FileInputStream localFileInputStream = null;
/*     */     
/*  49 */     LOGGER.debug("Loading truststore from file with params(location={})", new Object[] { getLocation() });
/*     */     try {
/*  51 */       if (getLocation() == null) {
/*  52 */         throw new IOException("The location is null");
/*     */       }
/*  54 */       localKeyStore = KeyStore.getInstance(this.trustStoreType);
/*  55 */       localFileInputStream = new FileInputStream(getLocation());
/*  56 */       localKeyStore.load(localFileInputStream, getPasswordAsCharArray());
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
/*     */       try
/*     */       {
/*  75 */         if (localFileInputStream != null) {
/*  76 */           localFileInputStream.close();
/*     */         }
/*     */       }
/*     */       catch (Exception localException1) {
/*  80 */         LOGGER.warn("Error closing {}", new Object[] { getLocation(), localException1 });
/*     */       }
/*     */       
/*  83 */       this.trustStore = localKeyStore;
/*     */     }
/*     */     catch (CertificateException localCertificateException)
/*     */     {
/*  59 */       LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type {}", new Object[] { this.trustStoreType });
/*  60 */       throw new StoreConfigurationException(localCertificateException);
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  62 */       LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found");
/*  63 */       throw new StoreConfigurationException(localNoSuchAlgorithmException);
/*     */     } catch (KeyStoreException localKeyStoreException) {
/*  65 */       LOGGER.error(localKeyStoreException);
/*  66 */       throw new StoreConfigurationException(localKeyStoreException);
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/*  68 */       LOGGER.error("The keystore file({}) is not found", new Object[] { getLocation() });
/*  69 */       throw new StoreConfigurationException(localFileNotFoundException);
/*     */     } catch (IOException localIOException) {
/*  71 */       LOGGER.error("Something is wrong with the format of the truststore or the given password: {}", new Object[] { localIOException.getMessage() });
/*  72 */       throw new StoreConfigurationException(localIOException);
/*     */     } finally {
/*     */       try {
/*  75 */         if (localFileInputStream != null) {
/*  76 */           localFileInputStream.close();
/*     */         }
/*     */       }
/*     */       catch (Exception localException2) {
/*  80 */         LOGGER.warn("Error closing {}", new Object[] { getLocation(), localException2 });
/*     */       }
/*     */     }
/*     */     
/*  84 */     LOGGER.debug("Truststore successfully loaded with params(location={})", new Object[] { getLocation() });
/*     */   }
/*     */   
/*     */   public KeyStore getTrustStore() throws StoreConfigurationException {
/*  88 */     if (this.trustStore == null) {
/*  89 */       load();
/*     */     }
/*  91 */     return this.trustStore;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static TrustStoreConfiguration createTrustStoreConfiguration(@PluginAttribute("location") String paramString1, @PluginAttribute("password") String paramString2)
/*     */   {
/* 103 */     return new TrustStoreConfiguration(paramString1, paramString2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\TrustStoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */