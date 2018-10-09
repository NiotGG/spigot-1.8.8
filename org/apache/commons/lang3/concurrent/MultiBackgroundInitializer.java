/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
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
/*     */ public class MultiBackgroundInitializer
/*     */   extends BackgroundInitializer<MultiBackgroundInitializerResults>
/*     */ {
/* 101 */   private final Map<String, BackgroundInitializer<?>> childInitializers = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MultiBackgroundInitializer() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MultiBackgroundInitializer(ExecutorService paramExecutorService)
/*     */   {
/* 119 */     super(paramExecutorService);
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
/*     */   public void addInitializer(String paramString, BackgroundInitializer<?> paramBackgroundInitializer)
/*     */   {
/* 135 */     if (paramString == null) {
/* 136 */       throw new IllegalArgumentException("Name of child initializer must not be null!");
/*     */     }
/*     */     
/* 139 */     if (paramBackgroundInitializer == null) {
/* 140 */       throw new IllegalArgumentException("Child initializer must not be null!");
/*     */     }
/*     */     
/*     */ 
/* 144 */     synchronized (this) {
/* 145 */       if (isStarted()) {
/* 146 */         throw new IllegalStateException("addInitializer() must not be called after start()!");
/*     */       }
/*     */       
/* 149 */       this.childInitializers.put(paramString, paramBackgroundInitializer);
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
/*     */   protected int getTaskCount()
/*     */   {
/* 165 */     int i = 1;
/*     */     
/* 167 */     for (BackgroundInitializer localBackgroundInitializer : this.childInitializers.values()) {
/* 168 */       i += localBackgroundInitializer.getTaskCount();
/*     */     }
/*     */     
/* 171 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MultiBackgroundInitializerResults initialize()
/*     */     throws Exception
/*     */   {
/*     */     HashMap localHashMap;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 187 */     synchronized (this)
/*     */     {
/* 189 */       localHashMap = new HashMap(this.childInitializers);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 194 */     ??? = getActiveExecutor();
/* 195 */     for (Object localObject2 = localHashMap.values().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (BackgroundInitializer)((Iterator)localObject2).next();
/* 196 */       if (((BackgroundInitializer)localObject3).getExternalExecutor() == null)
/*     */       {
/* 198 */         ((BackgroundInitializer)localObject3).setExternalExecutor((ExecutorService)???);
/*     */       }
/* 200 */       ((BackgroundInitializer)localObject3).start();
/*     */     }
/*     */     
/*     */ 
/* 204 */     localObject2 = new HashMap();
/* 205 */     Object localObject3 = new HashMap();
/* 206 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/*     */       try {
/* 208 */         ((Map)localObject2).put(localEntry.getKey(), ((BackgroundInitializer)localEntry.getValue()).get());
/*     */       } catch (ConcurrentException localConcurrentException) {
/* 210 */         ((Map)localObject3).put(localEntry.getKey(), localConcurrentException);
/*     */       }
/*     */     }
/*     */     
/* 214 */     return new MultiBackgroundInitializerResults(localHashMap, (Map)localObject2, (Map)localObject3, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class MultiBackgroundInitializerResults
/*     */   {
/*     */     private final Map<String, BackgroundInitializer<?>> initializers;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final Map<String, Object> resultObjects;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final Map<String, ConcurrentException> exceptions;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private MultiBackgroundInitializerResults(Map<String, BackgroundInitializer<?>> paramMap, Map<String, Object> paramMap1, Map<String, ConcurrentException> paramMap2)
/*     */     {
/* 250 */       this.initializers = paramMap;
/* 251 */       this.resultObjects = paramMap1;
/* 252 */       this.exceptions = paramMap2;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BackgroundInitializer<?> getInitializer(String paramString)
/*     */     {
/* 264 */       return checkName(paramString);
/*     */     }
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
/*     */     public Object getResultObject(String paramString)
/*     */     {
/* 280 */       checkName(paramString);
/* 281 */       return this.resultObjects.get(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isException(String paramString)
/*     */     {
/* 293 */       checkName(paramString);
/* 294 */       return this.exceptions.containsKey(paramString);
/*     */     }
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
/*     */     public ConcurrentException getException(String paramString)
/*     */     {
/* 308 */       checkName(paramString);
/* 309 */       return (ConcurrentException)this.exceptions.get(paramString);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Set<String> initializerNames()
/*     */     {
/* 320 */       return Collections.unmodifiableSet(this.initializers.keySet());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isSuccessful()
/*     */     {
/* 330 */       return this.exceptions.isEmpty();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private BackgroundInitializer<?> checkName(String paramString)
/*     */     {
/* 343 */       BackgroundInitializer localBackgroundInitializer = (BackgroundInitializer)this.initializers.get(paramString);
/* 344 */       if (localBackgroundInitializer == null) {
/* 345 */         throw new NoSuchElementException("No child initializer with name " + paramString);
/*     */       }
/*     */       
/*     */ 
/* 349 */       return localBackgroundInitializer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\MultiBackgroundInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */