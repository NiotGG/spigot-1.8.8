/*     */ package org.apache.logging.log4j.core.net.ssl;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="keyStore", category="Core", printObject=true)
/*     */ public class KeyStoreConfiguration
/*     */   extends StoreConfiguration
/*     */ {
/*     */   private KeyStore keyStore;
/*     */   private String keyStoreType;
/*     */   
/*     */   public KeyStoreConfiguration(String paramString1, String paramString2)
/*     */   {
/*  42 */     super(paramString1, paramString2);
/*  43 */     this.keyStoreType = "JKS";
/*  44 */     this.keyStore = null;
/*     */   }
/*     */   
/*     */   protected void load() throws StoreConfigurationException
/*     */   {
/*  49 */     FileInputStream localFileInputStream = null;
/*     */     
/*  51 */     LOGGER.debug("Loading keystore from file with params(location={})", new Object[] { getLocation() });
/*     */     try {
/*  53 */       if (getLocation() == null) {
/*  54 */         throw new IOException("The location is null");
/*     */       }
/*  56 */       localFileInputStream = new FileInputStream(getLocation());
/*  57 */       KeyStore localKeyStore = KeyStore.getInstance(this.keyStoreType);
/*  58 */       localKeyStore.load(localFileInputStream, getPasswordAsCharArray());
/*  59 */       this.keyStore = localKeyStore;
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
/*     */       try
/*     */       {
/*  79 */         if (localFileInputStream != null) {
/*  80 */           localFileInputStream.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException1) {}
/*     */     }
/*     */     catch (CertificateException localCertificateException)
/*     */     {
/*  62 */       LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type {}", new Object[] { this.keyStoreType });
/*  63 */       throw new StoreConfigurationException(localCertificateException);
/*     */     } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  65 */       LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found");
/*  66 */       throw new StoreConfigurationException(localNoSuchAlgorithmException);
/*     */     } catch (KeyStoreException localKeyStoreException) {
/*  68 */       LOGGER.error(localKeyStoreException);
/*  69 */       throw new StoreConfigurationException(localKeyStoreException);
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/*  71 */       LOGGER.error("The keystore file({}) is not found", new Object[] { getLocation() });
/*  72 */       throw new StoreConfigurationException(localFileNotFoundException);
/*     */     } catch (IOException localIOException2) {
/*  74 */       LOGGER.error("Something is wrong with the format of the keystore or the given password");
/*  75 */       throw new StoreConfigurationException(localIOException2);
/*     */     }
/*     */     finally {
/*     */       try {
/*  79 */         if (localFileInputStream != null) {
/*  80 */           localFileInputStream.close();
/*     */         }
/*     */       } catch (IOException localIOException3) {}
/*     */     }
/*  84 */     tmp223_220[0] = getLocation();LOGGER.debug("Keystore successfully loaded with params(location={})", tmp223_220);
/*     */   }
/*     */   
/*     */   public KeyStore getKeyStore() throws StoreConfigurationException {
/*  88 */     if (this.keyStore == null) {
/*  89 */       load();
/*     */     }
/*  91 */     return this.keyStore;
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
/*     */   public static KeyStoreConfiguration createKeyStoreConfiguration(@PluginAttribute("location") String paramString1, @PluginAttribute("password") String paramString2)
/*     */   {
/* 104 */     return new KeyStoreConfiguration(paramString1, paramString2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\ssl\KeyStoreConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */