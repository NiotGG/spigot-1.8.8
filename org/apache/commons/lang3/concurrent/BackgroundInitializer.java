/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BackgroundInitializer<T>
/*     */   implements ConcurrentInitializer<T>
/*     */ {
/*     */   private ExecutorService externalExecutor;
/*     */   private ExecutorService executor;
/*     */   private Future<T> future;
/*     */   
/*     */   protected BackgroundInitializer()
/*     */   {
/* 102 */     this(null);
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
/*     */   protected BackgroundInitializer(ExecutorService paramExecutorService)
/*     */   {
/* 116 */     setExternalExecutor(paramExecutorService);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized ExecutorService getExternalExecutor()
/*     */   {
/* 125 */     return this.externalExecutor;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized boolean isStarted()
/*     */   {
/* 136 */     return this.future != null;
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
/*     */   public final synchronized void setExternalExecutor(ExecutorService paramExecutorService)
/*     */   {
/* 155 */     if (isStarted()) {
/* 156 */       throw new IllegalStateException("Cannot set ExecutorService after start()!");
/*     */     }
/*     */     
/*     */ 
/* 160 */     this.externalExecutor = paramExecutorService;
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
/*     */   public synchronized boolean start()
/*     */   {
/* 175 */     if (!isStarted())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 180 */       this.executor = getExternalExecutor();
/* 181 */       ExecutorService localExecutorService; if (this.executor == null) {
/* 182 */         this.executor = (localExecutorService = createExecutor());
/*     */       } else {
/* 184 */         localExecutorService = null;
/*     */       }
/*     */       
/* 187 */       this.future = this.executor.submit(createTask(localExecutorService));
/*     */       
/* 189 */       return true;
/*     */     }
/*     */     
/* 192 */     return false;
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
/*     */   public T get()
/*     */     throws ConcurrentException
/*     */   {
/*     */     try
/*     */     {
/* 212 */       return (T)getFuture().get();
/*     */     } catch (ExecutionException localExecutionException) {
/* 214 */       ConcurrentUtils.handleCause(localExecutionException);
/* 215 */       return null;
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 218 */       Thread.currentThread().interrupt();
/* 219 */       throw new ConcurrentException(localInterruptedException);
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
/*     */   public synchronized Future<T> getFuture()
/*     */   {
/* 232 */     if (this.future == null) {
/* 233 */       throw new IllegalStateException("start() must be called first!");
/*     */     }
/*     */     
/* 236 */     return this.future;
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
/*     */   protected final synchronized ExecutorService getActiveExecutor()
/*     */   {
/* 249 */     return this.executor;
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
/* 264 */     return 1;
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
/*     */   protected abstract T initialize()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Callable<T> createTask(ExecutorService paramExecutorService)
/*     */   {
/* 291 */     return new InitializationTask(paramExecutorService);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ExecutorService createExecutor()
/*     */   {
/* 301 */     return Executors.newFixedThreadPool(getTaskCount());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class InitializationTask
/*     */     implements Callable<T>
/*     */   {
/*     */     private final ExecutorService execFinally;
/*     */     
/*     */ 
/*     */ 
/*     */     public InitializationTask(ExecutorService paramExecutorService)
/*     */     {
/* 315 */       this.execFinally = paramExecutorService;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public T call()
/*     */       throws Exception
/*     */     {
/*     */       try
/*     */       {
/* 327 */         return (T)BackgroundInitializer.this.initialize();
/*     */       } finally {
/* 329 */         if (this.execFinally != null) {
/* 330 */           this.execFinally.shutdown();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\BackgroundInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */