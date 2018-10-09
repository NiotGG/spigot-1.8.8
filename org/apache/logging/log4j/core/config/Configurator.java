/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
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
/*     */ 
/*     */ 
/*     */ public final class Configurator
/*     */ {
/*  32 */   protected static final StatusLogger LOGGER = ;
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
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(String paramString1, ClassLoader paramClassLoader, String paramString2)
/*     */   {
/*  46 */     return initialize(paramString1, paramClassLoader, paramString2, null);
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
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(String paramString1, ClassLoader paramClassLoader, String paramString2, Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/*  62 */       URI localURI = paramString2 == null ? null : new URI(paramString2);
/*  63 */       return initialize(paramString1, paramClassLoader, localURI, paramObject);
/*     */     } catch (URISyntaxException localURISyntaxException) {
/*  65 */       localURISyntaxException.printStackTrace();
/*     */     }
/*  67 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(String paramString1, String paramString2)
/*     */   {
/*  77 */     return initialize(paramString1, null, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(String paramString, ClassLoader paramClassLoader, URI paramURI)
/*     */   {
/*  88 */     return initialize(paramString, paramClassLoader, paramURI, null);
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
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(String paramString, ClassLoader paramClassLoader, URI paramURI, Object paramObject)
/*     */   {
/*     */     try
/*     */     {
/* 103 */       org.apache.logging.log4j.spi.LoggerContext localLoggerContext = LogManager.getContext(paramClassLoader, false, paramURI);
/* 104 */       if ((localLoggerContext instanceof org.apache.logging.log4j.core.LoggerContext)) {
/* 105 */         org.apache.logging.log4j.core.LoggerContext localLoggerContext1 = (org.apache.logging.log4j.core.LoggerContext)localLoggerContext;
/* 106 */         ContextAnchor.THREAD_CONTEXT.set(localLoggerContext1);
/* 107 */         if (paramObject != null) {
/* 108 */           localLoggerContext1.setExternalContext(paramObject);
/*     */         }
/* 110 */         Configuration localConfiguration = ConfigurationFactory.getInstance().getConfiguration(paramString, paramURI);
/* 111 */         localLoggerContext1.start(localConfiguration);
/* 112 */         ContextAnchor.THREAD_CONTEXT.remove();
/* 113 */         return localLoggerContext1;
/*     */       }
/* 115 */       LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", new Object[] { localLoggerContext.getClass().getName(), org.apache.logging.log4j.core.LoggerContext.class.getName() });
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/* 119 */       localException.printStackTrace();
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static org.apache.logging.log4j.core.LoggerContext initialize(ClassLoader paramClassLoader, ConfigurationFactory.ConfigurationSource paramConfigurationSource)
/*     */   {
/*     */     try
/*     */     {
/* 134 */       URI localURI = null;
/*     */       try {
/* 136 */         localURI = paramConfigurationSource.getLocation() == null ? null : new URI(paramConfigurationSource.getLocation());
/*     */       }
/*     */       catch (Exception localException2) {}
/*     */       
/* 140 */       org.apache.logging.log4j.spi.LoggerContext localLoggerContext = LogManager.getContext(paramClassLoader, false, localURI);
/* 141 */       if ((localLoggerContext instanceof org.apache.logging.log4j.core.LoggerContext)) {
/* 142 */         org.apache.logging.log4j.core.LoggerContext localLoggerContext1 = (org.apache.logging.log4j.core.LoggerContext)localLoggerContext;
/* 143 */         ContextAnchor.THREAD_CONTEXT.set(localLoggerContext1);
/* 144 */         Configuration localConfiguration = ConfigurationFactory.getInstance().getConfiguration(paramConfigurationSource);
/* 145 */         localLoggerContext1.start(localConfiguration);
/* 146 */         ContextAnchor.THREAD_CONTEXT.remove();
/* 147 */         return localLoggerContext1;
/*     */       }
/* 149 */       LOGGER.error("LogManager returned an instance of {} which does not implement {}. Unable to initialize Log4j", new Object[] { localLoggerContext.getClass().getName(), org.apache.logging.log4j.core.LoggerContext.class.getName() });
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/* 153 */       localException1.printStackTrace();
/*     */     }
/* 155 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void shutdown(org.apache.logging.log4j.core.LoggerContext paramLoggerContext)
/*     */   {
/* 163 */     if (paramLoggerContext != null) {
/* 164 */       paramLoggerContext.stop();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\Configurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */