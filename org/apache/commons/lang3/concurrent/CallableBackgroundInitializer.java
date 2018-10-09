/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
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
/*     */ public class CallableBackgroundInitializer<T>
/*     */   extends BackgroundInitializer<T>
/*     */ {
/*     */   private final Callable<T> callable;
/*     */   
/*     */   public CallableBackgroundInitializer(Callable<T> paramCallable)
/*     */   {
/*  80 */     checkCallable(paramCallable);
/*  81 */     this.callable = paramCallable;
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
/*     */   public CallableBackgroundInitializer(Callable<T> paramCallable, ExecutorService paramExecutorService)
/*     */   {
/*  96 */     super(paramExecutorService);
/*  97 */     checkCallable(paramCallable);
/*  98 */     this.callable = paramCallable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected T initialize()
/*     */     throws Exception
/*     */   {
/* 111 */     return (T)this.callable.call();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void checkCallable(Callable<T> paramCallable)
/*     */   {
/* 122 */     if (paramCallable == null) {
/* 123 */       throw new IllegalArgumentException("Callable must not be null!");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\CallableBackgroundInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */