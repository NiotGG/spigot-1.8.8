/*     */ package org.apache.logging.log4j.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.spi.Provider;
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
/*     */ 
/*     */ public final class ProviderUtil
/*     */ {
/*     */   private static final String PROVIDER_RESOURCE = "META-INF/log4j-provider.properties";
/*     */   private static final String API_VERSION = "Log4jAPIVersion";
/*  39 */   private static final String[] COMPATIBLE_API_VERSIONS = { "2.0.0" };
/*     */   
/*     */ 
/*     */ 
/*  43 */   private static final Logger LOGGER = StatusLogger.getLogger();
/*     */   
/*  45 */   private static final List<Provider> PROVIDERS = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  51 */     ClassLoader localClassLoader = findClassLoader();
/*  52 */     Enumeration localEnumeration = null;
/*     */     try {
/*  54 */       localEnumeration = localClassLoader.getResources("META-INF/log4j-provider.properties");
/*     */     } catch (IOException localIOException1) {
/*  56 */       LOGGER.fatal("Unable to locate META-INF/log4j-provider.properties", localIOException1);
/*     */     }
/*     */     
/*  59 */     if (localEnumeration != null) {
/*  60 */       while (localEnumeration.hasMoreElements()) {
/*  61 */         URL localURL = (URL)localEnumeration.nextElement();
/*     */         try
/*     */         {
/*  64 */           Properties localProperties = PropertiesUtil.loadClose(localURL.openStream(), localURL);
/*  65 */           if (validVersion(localProperties.getProperty("Log4jAPIVersion")))
/*     */           {
/*     */ 
/*  68 */             PROVIDERS.add(new Provider(localProperties, localURL)); }
/*     */         } catch (IOException localIOException2) {
/*  70 */           LOGGER.error("Unable to open " + localURL.toString(), localIOException2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static Iterator<Provider> getProviders() {
/*  77 */     return PROVIDERS.iterator();
/*     */   }
/*     */   
/*     */   public static boolean hasProviders() {
/*  81 */     return PROVIDERS.size() > 0;
/*     */   }
/*     */   
/*     */   public static ClassLoader findClassLoader() {
/*     */     ClassLoader localClassLoader;
/*  86 */     if (System.getSecurityManager() == null) {
/*  87 */       localClassLoader = Thread.currentThread().getContextClassLoader();
/*     */     } else {
/*  89 */       localClassLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public ClassLoader run()
/*     */         {
/*  93 */           return Thread.currentThread().getContextClassLoader();
/*     */         }
/*     */       });
/*     */     }
/*     */     
/*  98 */     if (localClassLoader == null) {
/*  99 */       localClassLoader = ProviderUtil.class.getClassLoader();
/*     */     }
/*     */     
/* 102 */     return localClassLoader;
/*     */   }
/*     */   
/*     */   private static boolean validVersion(String paramString) {
/* 106 */     for (String str : COMPATIBLE_API_VERSIONS) {
/* 107 */       if (paramString.startsWith(str)) {
/* 108 */         return true;
/*     */       }
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\util\ProviderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */