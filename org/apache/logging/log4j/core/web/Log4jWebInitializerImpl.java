/*     */ package org.apache.logging.log4j.core.web;
/*     */ 
/*     */ import java.net.URI;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.UnavailableException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.LoggerContext.Status;
/*     */ import org.apache.logging.log4j.core.config.Configurator;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ import org.apache.logging.log4j.core.impl.Log4jContextFactory;
/*     */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.core.selector.ContextSelector;
/*     */ import org.apache.logging.log4j.core.selector.NamedContextSelector;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
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
/*     */ final class Log4jWebInitializerImpl
/*     */   implements Log4jWebInitializer
/*     */ {
/*  38 */   private static final Object MUTEX = new Object();
/*     */   
/*     */   static {
/*     */     try {
/*  42 */       Class.forName("org.apache.logging.log4j.core.web.JNDIContextFilter");
/*  43 */       throw new IllegalStateException("You are using Log4j 2 in a web application with the old, extinct log4j-web artifact. This is not supported and could cause serious runtime problems. Pleaseremove the log4j-web JAR file from your application.");
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */   private final StrSubstitutor substitutor = new StrSubstitutor(new Interpolator());
/*     */   
/*     */   private final ServletContext servletContext;
/*     */   
/*     */   private String name;
/*     */   private NamedContextSelector selector;
/*     */   private LoggerContext loggerContext;
/*  58 */   private boolean initialized = false;
/*  59 */   private boolean deinitialized = false;
/*     */   
/*     */   private Log4jWebInitializerImpl(ServletContext paramServletContext) {
/*  62 */     this.servletContext = paramServletContext;
/*     */   }
/*     */   
/*     */   public synchronized void initialize() throws UnavailableException
/*     */   {
/*  67 */     if (this.deinitialized) {
/*  68 */       throw new IllegalStateException("Cannot initialize Log4jWebInitializer after it was destroyed.");
/*     */     }
/*     */     
/*     */ 
/*  72 */     if (!this.initialized) {
/*  73 */       this.initialized = true;
/*     */       
/*  75 */       this.name = this.substitutor.replace(this.servletContext.getInitParameter("log4jContextName"));
/*  76 */       String str = this.substitutor.replace(this.servletContext.getInitParameter("log4jConfiguration"));
/*  77 */       boolean bool = "true".equals(this.servletContext.getInitParameter("isLog4jContextSelectorNamed"));
/*     */       
/*  79 */       if (bool) {
/*  80 */         initializeJndi(str);
/*     */       } else {
/*  82 */         initializeNonJndi(str);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void initializeJndi(String paramString) throws UnavailableException {
/*  88 */     URI localURI = null;
/*  89 */     if (paramString != null) {
/*     */       try {
/*  91 */         localURI = new URI(paramString);
/*     */       } catch (Exception localException) {
/*  93 */         this.servletContext.log("Unable to convert configuration location [" + paramString + "] to a URI!", localException);
/*     */       }
/*     */     }
/*     */     
/*  97 */     if (this.name == null) {
/*  98 */       throw new UnavailableException("A log4jContextName context parameter is required");
/*     */     }
/*     */     
/*     */ 
/* 102 */     LoggerContextFactory localLoggerContextFactory = LogManager.getFactory();
/* 103 */     LoggerContext localLoggerContext; if ((localLoggerContextFactory instanceof Log4jContextFactory)) {
/* 104 */       ContextSelector localContextSelector = ((Log4jContextFactory)localLoggerContextFactory).getSelector();
/* 105 */       if ((localContextSelector instanceof NamedContextSelector)) {
/* 106 */         this.selector = ((NamedContextSelector)localContextSelector);
/* 107 */         localLoggerContext = this.selector.locateContext(this.name, this.servletContext, localURI);
/* 108 */         ContextAnchor.THREAD_CONTEXT.set(localLoggerContext);
/* 109 */         if (localLoggerContext.getStatus() == LoggerContext.Status.INITIALIZED) {
/* 110 */           localLoggerContext.start();
/*     */         }
/* 112 */         ContextAnchor.THREAD_CONTEXT.remove();
/*     */       } else {
/* 114 */         this.servletContext.log("Potential problem: Selector is not an instance of NamedContextSelector.");
/* 115 */         return;
/*     */       }
/*     */     } else {
/* 118 */       this.servletContext.log("Potential problem: Factory is not an instance of Log4jContextFactory.");
/* 119 */       return;
/*     */     }
/* 121 */     this.loggerContext = localLoggerContext;
/* 122 */     this.servletContext.log("Created logger context for [" + this.name + "] using [" + localLoggerContext.getClass().getClassLoader() + "].");
/*     */   }
/*     */   
/*     */   private void initializeNonJndi(String paramString)
/*     */   {
/* 127 */     if (this.name == null) {
/* 128 */       this.name = this.servletContext.getServletContextName();
/*     */     }
/*     */     
/* 131 */     if ((this.name == null) && (paramString == null)) {
/* 132 */       this.servletContext.log("No Log4j context configuration provided. This is very unusual.");
/* 133 */       return;
/*     */     }
/*     */     
/* 136 */     this.loggerContext = Configurator.initialize(this.name, getClassLoader(), paramString, this.servletContext);
/*     */   }
/*     */   
/*     */   public synchronized void deinitialize()
/*     */   {
/* 141 */     if (!this.initialized) {
/* 142 */       throw new IllegalStateException("Cannot deinitialize Log4jWebInitializer because it has not initialized.");
/*     */     }
/*     */     
/*     */ 
/* 146 */     if (!this.deinitialized) {
/* 147 */       this.deinitialized = true;
/*     */       
/* 149 */       if (this.loggerContext != null) {
/* 150 */         this.servletContext.log("Removing LoggerContext for [" + this.name + "].");
/* 151 */         if (this.selector != null) {
/* 152 */           this.selector.removeContext(this.name);
/*     */         }
/* 154 */         this.loggerContext.stop();
/* 155 */         this.loggerContext.setExternalContext(null);
/* 156 */         this.loggerContext = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLoggerContext()
/*     */   {
/* 163 */     if (this.loggerContext != null) {
/* 164 */       ContextAnchor.THREAD_CONTEXT.set(this.loggerContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearLoggerContext()
/*     */   {
/* 170 */     ContextAnchor.THREAD_CONTEXT.remove();
/*     */   }
/*     */   
/*     */ 
/*     */   private ClassLoader getClassLoader()
/*     */   {
/*     */     try
/*     */     {
/* 178 */       return this.servletContext.getClassLoader();
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/* 181 */     return Log4jWebInitializerImpl.class.getClassLoader();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Log4jWebInitializer getLog4jWebInitializer(ServletContext paramServletContext)
/*     */   {
/* 193 */     synchronized (MUTEX) {
/* 194 */       Object localObject1 = (Log4jWebInitializer)paramServletContext.getAttribute(INITIALIZER_ATTRIBUTE);
/* 195 */       if (localObject1 == null) {
/* 196 */         localObject1 = new Log4jWebInitializerImpl(paramServletContext);
/* 197 */         paramServletContext.setAttribute(INITIALIZER_ATTRIBUTE, localObject1);
/*     */       }
/* 199 */       return (Log4jWebInitializer)localObject1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jWebInitializerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */