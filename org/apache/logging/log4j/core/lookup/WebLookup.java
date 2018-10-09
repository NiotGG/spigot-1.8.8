/*     */ package org.apache.logging.log4j.core.lookup;
/*     */ 
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
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
/*     */ @Plugin(name="web", category="Lookup")
/*     */ public class WebLookup
/*     */   implements StrLookup
/*     */ {
/*     */   private static final String ATTR_PREFIX = "attr.";
/*     */   private static final String INIT_PARAM_PREFIX = "initParam.";
/*     */   
/*     */   protected ServletContext getServletContext()
/*     */   {
/*  36 */     LoggerContext localLoggerContext = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/*  37 */     if (localLoggerContext == null) {
/*  38 */       localLoggerContext = (LoggerContext)LogManager.getContext(false);
/*     */     }
/*  40 */     if (localLoggerContext != null) {
/*  41 */       Object localObject = localLoggerContext.getExternalContext();
/*  42 */       return (localObject != null) && ((localObject instanceof ServletContext)) ? (ServletContext)localObject : null;
/*     */     }
/*  44 */     return null;
/*     */   }
/*     */   
/*     */   public String lookup(String paramString)
/*     */   {
/*  49 */     ServletContext localServletContext = getServletContext();
/*  50 */     if (localServletContext == null)
/*  51 */       return null;
/*     */     String str;
/*     */     Object localObject;
/*  54 */     if (paramString.startsWith("attr.")) {
/*  55 */       str = paramString.substring("attr.".length());
/*  56 */       localObject = localServletContext.getAttribute(str);
/*  57 */       return localObject == null ? null : localObject.toString();
/*     */     }
/*     */     
/*  60 */     if (paramString.startsWith("initParam.")) {
/*  61 */       str = paramString.substring("initParam.".length());
/*  62 */       return localServletContext.getInitParameter(str);
/*     */     }
/*     */     
/*  65 */     if ("rootDir".equals(paramString)) {
/*  66 */       str = localServletContext.getRealPath("/");
/*  67 */       if (str == null) {
/*  68 */         localObject = "failed to resolve web:rootDir -- servlet container unable to translate virtual path  to real path (probably not deployed as exploded";
/*     */         
/*     */ 
/*  71 */         throw new RuntimeException((String)localObject);
/*     */       }
/*     */       
/*  74 */       return str;
/*     */     }
/*     */     
/*  77 */     if ("contextPath".equals(paramString)) {
/*  78 */       return localServletContext.getContextPath();
/*     */     }
/*     */     
/*  81 */     if ("servletContextName".equals(paramString)) {
/*  82 */       return localServletContext.getServletContextName();
/*     */     }
/*     */     
/*  85 */     if ("serverInfo".equals(paramString)) {
/*  86 */       return localServletContext.getServerInfo();
/*     */     }
/*     */     
/*  89 */     if ("effectiveMajorVersion".equals(paramString)) {
/*  90 */       return String.valueOf(localServletContext.getEffectiveMajorVersion());
/*     */     }
/*     */     
/*  93 */     if ("effectiveMinorVersion".equals(paramString)) {
/*  94 */       return String.valueOf(localServletContext.getEffectiveMinorVersion());
/*     */     }
/*     */     
/*  97 */     if ("majorVersion".equals(paramString)) {
/*  98 */       return String.valueOf(localServletContext.getMajorVersion());
/*     */     }
/*     */     
/* 101 */     if ("minorVersion".equals(paramString)) {
/* 102 */       return String.valueOf(localServletContext.getMinorVersion());
/*     */     }
/*     */     
/* 105 */     if (localServletContext.getAttribute(paramString) != null) {
/* 106 */       return localServletContext.getAttribute(paramString).toString();
/*     */     }
/*     */     
/* 109 */     if (localServletContext.getInitParameter(paramString) != null) {
/* 110 */       return localServletContext.getInitParameter(paramString);
/*     */     }
/*     */     
/* 113 */     localServletContext.log(getClass().getName() + " unable to resolve key '" + paramString + "'");
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public String lookup(LogEvent paramLogEvent, String paramString)
/*     */   {
/* 119 */     return lookup(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\WebLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */