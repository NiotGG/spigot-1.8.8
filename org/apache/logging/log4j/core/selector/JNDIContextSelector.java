/*     */ package org.apache.logging.log4j.core.selector;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JNDIContextSelector
/*     */   implements NamedContextSelector
/*     */ {
/*  90 */   private static final LoggerContext CONTEXT = new LoggerContext("Default");
/*     */   
/*  92 */   private static final ConcurrentMap<String, LoggerContext> CONTEXT_MAP = new ConcurrentHashMap();
/*     */   
/*     */ 
/*  95 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*     */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/*  99 */     return getContext(paramString, paramClassLoader, paramBoolean, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*     */   {
/* 106 */     LoggerContext localLoggerContext = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/* 107 */     if (localLoggerContext != null) {
/* 108 */       return localLoggerContext;
/*     */     }
/*     */     
/* 111 */     String str = null;
/*     */     try
/*     */     {
/* 114 */       InitialContext localInitialContext = new InitialContext();
/* 115 */       str = (String)lookup(localInitialContext, "java:comp/env/log4j/context-name");
/*     */     } catch (NamingException localNamingException) {
/* 117 */       LOGGER.error("Unable to lookup java:comp/env/log4j/context-name", localNamingException);
/*     */     }
/*     */     
/* 120 */     return str == null ? CONTEXT : locateContext(str, null, paramURI);
/*     */   }
/*     */   
/*     */   public LoggerContext locateContext(String paramString, Object paramObject, URI paramURI)
/*     */   {
/* 125 */     if (paramString == null) {
/* 126 */       LOGGER.error("A context name is required to locate a LoggerContext");
/* 127 */       return null;
/*     */     }
/* 129 */     if (!CONTEXT_MAP.containsKey(paramString)) {
/* 130 */       LoggerContext localLoggerContext = new LoggerContext(paramString, paramObject, paramURI);
/* 131 */       CONTEXT_MAP.putIfAbsent(paramString, localLoggerContext);
/*     */     }
/* 133 */     return (LoggerContext)CONTEXT_MAP.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeContext(LoggerContext paramLoggerContext)
/*     */   {
/* 139 */     for (Map.Entry localEntry : CONTEXT_MAP.entrySet()) {
/* 140 */       if (((LoggerContext)localEntry.getValue()).equals(paramLoggerContext)) {
/* 141 */         CONTEXT_MAP.remove(localEntry.getKey());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public LoggerContext removeContext(String paramString)
/*     */   {
/* 148 */     return (LoggerContext)CONTEXT_MAP.remove(paramString);
/*     */   }
/*     */   
/*     */   public List<LoggerContext> getLoggerContexts()
/*     */   {
/* 153 */     ArrayList localArrayList = new ArrayList(CONTEXT_MAP.values());
/* 154 */     return Collections.unmodifiableList(localArrayList);
/*     */   }
/*     */   
/*     */   protected static Object lookup(Context paramContext, String paramString) throws NamingException
/*     */   {
/* 159 */     if (paramContext == null) {
/* 160 */       return null;
/*     */     }
/*     */     try {
/* 163 */       return paramContext.lookup(paramString);
/*     */     } catch (NameNotFoundException localNameNotFoundException) {
/* 165 */       LOGGER.error("Could not find name [" + paramString + "].");
/* 166 */       throw localNameNotFoundException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\JNDIContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */