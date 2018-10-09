/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.message.ParameterizedMessage;
/*     */ import org.apache.logging.log4j.spi.DefaultThreadContextMap;
/*     */ import org.apache.logging.log4j.spi.DefaultThreadContextStack;
/*     */ import org.apache.logging.log4j.spi.LoggerContextFactory;
/*     */ import org.apache.logging.log4j.spi.MutableThreadContextStack;
/*     */ import org.apache.logging.log4j.spi.Provider;
/*     */ import org.apache.logging.log4j.spi.ThreadContextMap;
/*     */ import org.apache.logging.log4j.spi.ThreadContextStack;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
/*     */ import org.apache.logging.log4j.util.ProviderUtil;
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
/*     */ public final class ThreadContext
/*     */ {
/*  52 */   public static final Map<String, String> EMPTY_MAP = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  57 */   public static final ThreadContextStack EMPTY_STACK = new MutableThreadContextStack(new ArrayList());
/*     */   
/*     */   private static final String DISABLE_MAP = "disableThreadContextMap";
/*     */   
/*     */   private static final String DISABLE_STACK = "disableThreadContextStack";
/*     */   private static final String DISABLE_ALL = "disableThreadContext";
/*     */   private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
/*     */   private static boolean all;
/*     */   private static boolean useMap;
/*     */   private static boolean useStack;
/*     */   private static ThreadContextMap contextMap;
/*     */   private static ThreadContextStack contextStack;
/*  69 */   private static final Logger LOGGER = StatusLogger.getLogger();
/*     */   
/*     */   static {
/*  72 */     PropertiesUtil localPropertiesUtil = PropertiesUtil.getProperties();
/*  73 */     all = localPropertiesUtil.getBooleanProperty("disableThreadContext");
/*  74 */     useStack = (!localPropertiesUtil.getBooleanProperty("disableThreadContextStack")) && (!all);
/*  75 */     contextStack = new DefaultThreadContextStack(useStack);
/*     */     
/*  77 */     useMap = (!localPropertiesUtil.getBooleanProperty("disableThreadContextMap")) && (!all);
/*  78 */     String str1 = localPropertiesUtil.getStringProperty("log4j2.threadContextMap");
/*  79 */     ClassLoader localClassLoader = ProviderUtil.findClassLoader();
/*  80 */     if (str1 != null) {
/*     */       try {
/*  82 */         Class localClass1 = localClassLoader.loadClass(str1);
/*  83 */         if (ThreadContextMap.class.isAssignableFrom(localClass1)) {
/*  84 */           contextMap = (ThreadContextMap)localClass1.newInstance();
/*     */         }
/*     */       } catch (ClassNotFoundException localClassNotFoundException1) {
/*  87 */         LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { str1 });
/*     */       } catch (Exception localException1) {
/*  89 */         LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { str1, localException1 });
/*     */       }
/*     */     }
/*  92 */     if ((contextMap == null) && (ProviderUtil.hasProviders())) {
/*  93 */       LoggerContextFactory localLoggerContextFactory = LogManager.getFactory();
/*  94 */       Iterator localIterator = ProviderUtil.getProviders();
/*  95 */       while (localIterator.hasNext()) {
/*  96 */         Provider localProvider = (Provider)localIterator.next();
/*  97 */         str1 = localProvider.getThreadContextMap();
/*  98 */         String str2 = localProvider.getClassName();
/*  99 */         if ((str1 != null) && (localLoggerContextFactory.getClass().getName().equals(str2))) {
/*     */           try {
/* 101 */             Class localClass2 = localClassLoader.loadClass(str1);
/* 102 */             if (ThreadContextMap.class.isAssignableFrom(localClass2)) {
/* 103 */               contextMap = (ThreadContextMap)localClass2.newInstance();
/* 104 */               break;
/*     */             }
/*     */           } catch (ClassNotFoundException localClassNotFoundException2) {
/* 107 */             LOGGER.error("Unable to locate configured LoggerContextFactory {}", new Object[] { str1 });
/* 108 */             contextMap = new DefaultThreadContextMap(useMap);
/*     */           } catch (Exception localException2) {
/* 110 */             LOGGER.error("Unable to create configured LoggerContextFactory {}", new Object[] { str1, localException2 });
/* 111 */             contextMap = new DefaultThreadContextMap(useMap);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 116 */     if (contextMap == null) {
/* 117 */       contextMap = new DefaultThreadContextMap(useMap);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void put(String paramString1, String paramString2)
/*     */   {
/* 136 */     contextMap.put(paramString1, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String get(String paramString)
/*     */   {
/* 147 */     return contextMap.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void remove(String paramString)
/*     */   {
/* 155 */     contextMap.remove(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void clear()
/*     */   {
/* 162 */     contextMap.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean containsKey(String paramString)
/*     */   {
/* 171 */     return contextMap.containsKey(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Map<String, String> getContext()
/*     */   {
/* 179 */     return contextMap.getCopy();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Map<String, String> getImmutableContext()
/*     */   {
/* 187 */     Map localMap = contextMap.getImmutableMapOrNull();
/* 188 */     return localMap == null ? EMPTY_MAP : localMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isEmpty()
/*     */   {
/* 196 */     return contextMap.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void clearStack()
/*     */   {
/* 203 */     contextStack.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ContextStack cloneStack()
/*     */   {
/* 211 */     return contextStack.copy();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ContextStack getImmutableStack()
/*     */   {
/* 219 */     return contextStack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void setStack(Collection<String> paramCollection)
/*     */   {
/* 227 */     if ((paramCollection.size() == 0) || (!useStack)) {
/* 228 */       return;
/*     */     }
/* 230 */     contextStack.clear();
/* 231 */     contextStack.addAll(paramCollection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getDepth()
/*     */   {
/* 241 */     return contextStack.getDepth();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String pop()
/*     */   {
/* 253 */     return contextStack.pop();
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
/*     */   public static String peek()
/*     */   {
/* 266 */     return contextStack.peek();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void push(String paramString)
/*     */   {
/* 278 */     contextStack.push(paramString);
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
/*     */   public static void push(String paramString, Object... paramVarArgs)
/*     */   {
/* 292 */     contextStack.push(ParameterizedMessage.format(paramString, paramVarArgs));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeStack()
/*     */   {
/* 314 */     contextStack.clear();
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
/*     */   public static void trim(int paramInt)
/*     */   {
/* 347 */     contextStack.trim(paramInt);
/*     */   }
/*     */   
/*     */   public static abstract interface ContextStack
/*     */     extends Serializable
/*     */   {
/*     */     public abstract void clear();
/*     */     
/*     */     public abstract String pop();
/*     */     
/*     */     public abstract String peek();
/*     */     
/*     */     public abstract void push(String paramString);
/*     */     
/*     */     public abstract int getDepth();
/*     */     
/*     */     public abstract List<String> asList();
/*     */     
/*     */     public abstract void trim(int paramInt);
/*     */     
/*     */     public abstract ContextStack copy();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\ThreadContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */