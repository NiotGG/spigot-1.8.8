/*     */ package org.apache.logging.log4j.core.selector;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.logging.log4j.core.LoggerContext;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.core.impl.ContextAnchor;
/*     */ import org.apache.logging.log4j.core.impl.ReflectiveCallerClassUtility;
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
/*     */ public class ClassLoaderContextSelector
/*     */   implements ContextSelector
/*     */ {
/*  50 */   private static final AtomicReference<LoggerContext> CONTEXT = new AtomicReference();
/*     */   
/*     */   private static final PrivateSecurityManager SECURITY_MANAGER;
/*     */   
/*  54 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
/*     */   
/*  56 */   private static final ConcurrentMap<String, AtomicReference<WeakReference<LoggerContext>>> CONTEXT_MAP = new ConcurrentHashMap();
/*     */   
/*     */   static
/*     */   {
/*  60 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*  61 */       SECURITY_MANAGER = null;
/*     */     } else {
/*     */       PrivateSecurityManager localPrivateSecurityManager;
/*     */       try {
/*  65 */         localPrivateSecurityManager = new PrivateSecurityManager(null);
/*  66 */         if (localPrivateSecurityManager.getCaller(ClassLoaderContextSelector.class.getName()) == null)
/*     */         {
/*  68 */           localPrivateSecurityManager = null;
/*  69 */           LOGGER.error("Unable to obtain call stack from security manager.");
/*     */         }
/*     */       } catch (Exception localException) {
/*  72 */         localPrivateSecurityManager = null;
/*  73 */         LOGGER.debug("Unable to install security manager", localException);
/*     */       }
/*  75 */       SECURITY_MANAGER = localPrivateSecurityManager;
/*     */     }
/*     */   }
/*     */   
/*     */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
/*     */   {
/*  81 */     return getContext(paramString, paramClassLoader, paramBoolean, null);
/*     */   }
/*     */   
/*     */   public LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI)
/*     */   {
/*     */     Object localObject1;
/*  87 */     if (paramBoolean) {
/*  88 */       localObject1 = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/*  89 */       if (localObject1 != null) {
/*  90 */         return (LoggerContext)localObject1;
/*     */       }
/*  92 */       return getDefault(); }
/*  93 */     if (paramClassLoader != null) {
/*  94 */       return locateContext(paramClassLoader, paramURI);
/*     */     }
/*  96 */     if (ReflectiveCallerClassUtility.isSupported()) {
/*     */       try {
/*  98 */         localObject1 = Class.class;
/*  99 */         int i = 0;
/* 100 */         for (int k = 2; localObject1 != null; k++) {
/* 101 */           localObject1 = ReflectiveCallerClassUtility.getCaller(k);
/* 102 */           if (localObject1 == null) {
/*     */             break;
/*     */           }
/* 105 */           if (((Class)localObject1).getName().equals(paramString)) {
/* 106 */             i = 1;
/*     */           }
/*     */           else {
/* 109 */             if (i != 0)
/*     */               break;
/*     */           }
/*     */         }
/* 113 */         if (localObject1 != null) {
/* 114 */           return locateContext(((Class)localObject1).getClassLoader(), paramURI);
/*     */         }
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */     
/*     */ 
/* 121 */     if (SECURITY_MANAGER != null) {
/* 122 */       localObject2 = SECURITY_MANAGER.getCaller(paramString);
/* 123 */       if (localObject2 != null) {
/* 124 */         ClassLoader localClassLoader = ((Class)localObject2).getClassLoader() != null ? ((Class)localObject2).getClassLoader() : ClassLoader.getSystemClassLoader();
/*     */         
/* 126 */         return locateContext(localClassLoader, paramURI);
/*     */       }
/*     */     }
/*     */     
/* 130 */     Object localObject2 = new Throwable();
/* 131 */     int j = 0;
/* 132 */     String str = null;
/* 133 */     for (StackTraceElement localStackTraceElement : ((Throwable)localObject2).getStackTrace()) {
/* 134 */       if (localStackTraceElement.getClassName().equals(paramString)) {
/* 135 */         j = 1;
/*     */ 
/*     */       }
/* 138 */       else if (j != 0) {
/* 139 */         str = localStackTraceElement.getClassName();
/* 140 */         break;
/*     */       }
/*     */     }
/* 143 */     if (str != null) {
/*     */       try {
/* 145 */         return locateContext(Loader.loadClass(str).getClassLoader(), paramURI);
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException) {}
/*     */     }
/*     */     
/* 150 */     LoggerContext localLoggerContext = (LoggerContext)ContextAnchor.THREAD_CONTEXT.get();
/* 151 */     if (localLoggerContext != null) {
/* 152 */       return localLoggerContext;
/*     */     }
/* 154 */     return getDefault();
/*     */   }
/*     */   
/*     */ 
/*     */   public void removeContext(LoggerContext paramLoggerContext)
/*     */   {
/* 160 */     for (Map.Entry localEntry : CONTEXT_MAP.entrySet()) {
/* 161 */       LoggerContext localLoggerContext = (LoggerContext)((WeakReference)((AtomicReference)localEntry.getValue()).get()).get();
/* 162 */       if (localLoggerContext == paramLoggerContext) {
/* 163 */         CONTEXT_MAP.remove(localEntry.getKey());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<LoggerContext> getLoggerContexts()
/*     */   {
/* 170 */     ArrayList localArrayList = new ArrayList();
/* 171 */     Collection localCollection = CONTEXT_MAP.values();
/* 172 */     for (AtomicReference localAtomicReference : localCollection) {
/* 173 */       LoggerContext localLoggerContext = (LoggerContext)((WeakReference)localAtomicReference.get()).get();
/* 174 */       if (localLoggerContext != null) {
/* 175 */         localArrayList.add(localLoggerContext);
/*     */       }
/*     */     }
/* 178 */     return Collections.unmodifiableList(localArrayList);
/*     */   }
/*     */   
/*     */   private LoggerContext locateContext(ClassLoader paramClassLoader, URI paramURI) {
/* 182 */     String str = paramClassLoader.toString();
/* 183 */     AtomicReference localAtomicReference = (AtomicReference)CONTEXT_MAP.get(str);
/* 184 */     if (localAtomicReference == null) {
/* 185 */       if (paramURI == null) {
/* 186 */         localObject1 = paramClassLoader.getParent();
/* 187 */         while (localObject1 != null)
/*     */         {
/* 189 */           localAtomicReference = (AtomicReference)CONTEXT_MAP.get(localObject1.toString());
/* 190 */           if (localAtomicReference != null) {
/* 191 */             localObject2 = (WeakReference)localAtomicReference.get();
/* 192 */             LoggerContext localLoggerContext = (LoggerContext)((WeakReference)localObject2).get();
/* 193 */             if (localLoggerContext != null) {
/* 194 */               return localLoggerContext;
/*     */             }
/*     */           }
/* 197 */           localObject1 = ((ClassLoader)localObject1).getParent();
/*     */         }
/*     */       }
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
/* 217 */       localObject1 = new LoggerContext(str, null, paramURI);
/* 218 */       localObject2 = new AtomicReference();
/*     */       
/* 220 */       ((AtomicReference)localObject2).set(new WeakReference(localObject1));
/* 221 */       CONTEXT_MAP.putIfAbsent(paramClassLoader.toString(), localObject2);
/* 222 */       localObject1 = (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(str)).get()).get();
/* 223 */       return (LoggerContext)localObject1;
/*     */     }
/* 225 */     Object localObject1 = (WeakReference)localAtomicReference.get();
/* 226 */     Object localObject2 = (LoggerContext)((WeakReference)localObject1).get();
/* 227 */     if (localObject2 != null) {
/* 228 */       if ((((LoggerContext)localObject2).getConfigLocation() == null) && (paramURI != null)) {
/* 229 */         LOGGER.debug("Setting configuration to {}", new Object[] { paramURI });
/* 230 */         ((LoggerContext)localObject2).setConfigLocation(paramURI);
/* 231 */       } else if ((((LoggerContext)localObject2).getConfigLocation() != null) && (paramURI != null) && (!((LoggerContext)localObject2).getConfigLocation().equals(paramURI)))
/*     */       {
/* 233 */         LOGGER.warn("locateContext called with URI {}. Existing LoggerContext has URI {}", new Object[] { paramURI, ((LoggerContext)localObject2).getConfigLocation() });
/*     */       }
/*     */       
/* 236 */       return (LoggerContext)localObject2;
/*     */     }
/* 238 */     localObject2 = new LoggerContext(str, null, paramURI);
/* 239 */     localAtomicReference.compareAndSet(localObject1, new WeakReference(localObject2));
/* 240 */     return (LoggerContext)localObject2;
/*     */   }
/*     */   
/*     */   private LoggerContext getDefault() {
/* 244 */     LoggerContext localLoggerContext = (LoggerContext)CONTEXT.get();
/* 245 */     if (localLoggerContext != null) {
/* 246 */       return localLoggerContext;
/*     */     }
/* 248 */     CONTEXT.compareAndSet(null, new LoggerContext("Default"));
/* 249 */     return (LoggerContext)CONTEXT.get();
/*     */   }
/*     */   
/*     */ 
/*     */   private static class PrivateSecurityManager
/*     */     extends SecurityManager
/*     */   {
/*     */     public Class<?> getCaller(String paramString)
/*     */     {
/* 258 */       Class[] arrayOfClass1 = getClassContext();
/* 259 */       int i = 0;
/* 260 */       for (Class localClass : arrayOfClass1) {
/* 261 */         if (localClass.getName().equals(paramString)) {
/* 262 */           i = 1;
/*     */ 
/*     */         }
/* 265 */         else if (i != 0) {
/* 266 */           return localClass;
/*     */         }
/*     */       }
/* 269 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\selector\ClassLoaderContextSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */