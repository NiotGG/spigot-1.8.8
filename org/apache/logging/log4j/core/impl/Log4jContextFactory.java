/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.net.URI;
/*     */ import org.apache.logging.log4j.core.LoggerContext.Status;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.jmx.Server;
/*     */ import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public class Log4jContextFactory
/*     */   implements LoggerContextFactory
/*     */ {
/*  36 */   private static final StatusLogger LOGGER = ;
/*     */   
/*     */ 
/*     */   private ContextSelector selector;
/*     */   
/*     */ 
/*     */   public Log4jContextFactory()
/*     */   {
/*  44 */     String str = PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector");
/*  45 */     if (str != null) {
/*     */       try {
/*  47 */         Class localClass = Loader.loadClass(str);
/*  48 */         if ((localClass != null) && (ContextSelector.class.isAssignableFrom(localClass))) {
/*  49 */           this.selector = ((ContextSelector)localClass.newInstance());
/*     */         }
/*     */       } catch (Exception localException1) {
/*  52 */         LOGGER.error("Unable to create context " + str, localException1);
/*     */       }
/*     */     }
/*  55 */     if (this.selector == null) {
/*  56 */       this.selector = new ClassLoaderContextSelector();
/*     */     }
/*     */     try {
/*  59 */       Server.registerMBeans(this.selector);
/*     */     } catch (Exception localException2) {
/*  61 */       LOGGER.error("Could not start JMX", localException2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ContextSelector getSelector()
/*     */   {
/*  70 */     return this.selector;
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
/*     */   public org.apache.logging.log4j.core.LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/*  83 */     org.apache.logging.log4j.core.LoggerContext localLoggerContext = this.selector.getContext(paramString, paramClassLoader, paramBoolean);
/*  84 */     if (localLoggerContext.getStatus() == LoggerContext.Status.INITIALIZED) {
/*  85 */       localLoggerContext.start();
/*     */     }
/*  87 */     return localLoggerContext;
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
/*     */   public org.apache.logging.log4j.core.LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*     */   {
/* 102 */     org.apache.logging.log4j.core.LoggerContext localLoggerContext = this.selector.getContext(paramString, paramClassLoader, paramBoolean, paramURI);
/* 103 */     if (localLoggerContext.getStatus() == LoggerContext.Status.INITIALIZED) {
/* 104 */       localLoggerContext.start();
/*     */     }
/* 106 */     return localLoggerContext;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeContext(org.apache.logging.log4j.spi.LoggerContext paramLoggerContext)
/*     */   {
/* 117 */     if ((paramLoggerContext instanceof org.apache.logging.log4j.core.LoggerContext)) {
/* 118 */       this.selector.removeContext((org.apache.logging.log4j.core.LoggerContext)paramLoggerContext);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\Log4jContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */