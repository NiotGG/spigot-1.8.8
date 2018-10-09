/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentUtils
/*     */ {
/*     */   public static ConcurrentException extractCause(ExecutionException paramExecutionException)
/*     */   {
/*  61 */     if ((paramExecutionException == null) || (paramExecutionException.getCause() == null)) {
/*  62 */       return null;
/*     */     }
/*     */     
/*  65 */     throwCause(paramExecutionException);
/*  66 */     return new ConcurrentException(paramExecutionException.getMessage(), paramExecutionException.getCause());
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
/*     */   public static ConcurrentRuntimeException extractCauseUnchecked(ExecutionException paramExecutionException)
/*     */   {
/*  83 */     if ((paramExecutionException == null) || (paramExecutionException.getCause() == null)) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     throwCause(paramExecutionException);
/*  88 */     return new ConcurrentRuntimeException(paramExecutionException.getMessage(), paramExecutionException.getCause());
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
/*     */   public static void handleCause(ExecutionException paramExecutionException)
/*     */     throws ConcurrentException
/*     */   {
/* 106 */     ConcurrentException localConcurrentException = extractCause(paramExecutionException);
/*     */     
/* 108 */     if (localConcurrentException != null) {
/* 109 */       throw localConcurrentException;
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
/*     */   public static void handleCauseUnchecked(ExecutionException paramExecutionException)
/*     */   {
/* 127 */     ConcurrentRuntimeException localConcurrentRuntimeException = extractCauseUnchecked(paramExecutionException);
/*     */     
/* 129 */     if (localConcurrentRuntimeException != null) {
/* 130 */       throw localConcurrentRuntimeException;
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
/*     */   static Throwable checkedException(Throwable paramThrowable)
/*     */   {
/* 144 */     if ((paramThrowable != null) && (!(paramThrowable instanceof RuntimeException)) && (!(paramThrowable instanceof Error)))
/*     */     {
/* 146 */       return paramThrowable;
/*     */     }
/* 148 */     throw new IllegalArgumentException("Not a checked exception: " + paramThrowable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void throwCause(ExecutionException paramExecutionException)
/*     */   {
/* 159 */     if ((paramExecutionException.getCause() instanceof RuntimeException)) {
/* 160 */       throw ((RuntimeException)paramExecutionException.getCause());
/*     */     }
/*     */     
/* 163 */     if ((paramExecutionException.getCause() instanceof Error)) {
/* 164 */       throw ((Error)paramExecutionException.getCause());
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
/*     */   public static <T> T initialize(ConcurrentInitializer<T> paramConcurrentInitializer)
/*     */     throws ConcurrentException
/*     */   {
/* 184 */     return (T)(paramConcurrentInitializer != null ? paramConcurrentInitializer.get() : null);
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
/*     */   public static <T> T initializeUnchecked(ConcurrentInitializer<T> paramConcurrentInitializer)
/*     */   {
/*     */     try
/*     */     {
/* 202 */       return (T)initialize(paramConcurrentInitializer);
/*     */     } catch (ConcurrentException localConcurrentException) {
/* 204 */       throw new ConcurrentRuntimeException(localConcurrentException.getCause());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> V putIfAbsent(ConcurrentMap<K, V> paramConcurrentMap, K paramK, V paramV)
/*     */   {
/* 244 */     if (paramConcurrentMap == null) {
/* 245 */       return null;
/*     */     }
/*     */     
/* 248 */     Object localObject = paramConcurrentMap.putIfAbsent(paramK, paramV);
/* 249 */     return (V)(localObject != null ? localObject : paramV);
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
/*     */   public static <K, V> V createIfAbsent(ConcurrentMap<K, V> paramConcurrentMap, K paramK, ConcurrentInitializer<V> paramConcurrentInitializer)
/*     */     throws ConcurrentException
/*     */   {
/* 274 */     if ((paramConcurrentMap == null) || (paramConcurrentInitializer == null)) {
/* 275 */       return null;
/*     */     }
/*     */     
/* 278 */     Object localObject = paramConcurrentMap.get(paramK);
/* 279 */     if (localObject == null) {
/* 280 */       return (V)putIfAbsent(paramConcurrentMap, paramK, paramConcurrentInitializer.get());
/*     */     }
/* 282 */     return (V)localObject;
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
/*     */   public static <K, V> V createIfAbsentUnchecked(ConcurrentMap<K, V> paramConcurrentMap, K paramK, ConcurrentInitializer<V> paramConcurrentInitializer)
/*     */   {
/*     */     try
/*     */     {
/* 303 */       return (V)createIfAbsent(paramConcurrentMap, paramK, paramConcurrentInitializer);
/*     */     } catch (ConcurrentException localConcurrentException) {
/* 305 */       throw new ConcurrentRuntimeException(localConcurrentException.getCause());
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
/*     */ 
/*     */ 
/*     */   public static <T> Future<T> constantFuture(T paramT)
/*     */   {
/* 326 */     return new ConstantFuture(paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class ConstantFuture<T>
/*     */     implements Future<T>
/*     */   {
/*     */     private final T value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     ConstantFuture(T paramT)
/*     */     {
/* 344 */       this.value = paramT;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isDone()
/*     */     {
/* 354 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public T get()
/*     */     {
/* 362 */       return (T)this.value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public T get(long paramLong, TimeUnit paramTimeUnit)
/*     */     {
/* 371 */       return (T)this.value;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isCancelled()
/*     */     {
/* 380 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean cancel(boolean paramBoolean)
/*     */     {
/* 389 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\ConcurrentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */