/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LazyInitializer<T>
/*     */   implements ConcurrentInitializer<T>
/*     */ {
/*     */   private volatile T object;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  97 */     Object localObject1 = this.object;
/*     */     
/*  99 */     if (localObject1 == null) {
/* 100 */       synchronized (this) {
/* 101 */         localObject1 = this.object;
/* 102 */         if (localObject1 == null) {
/* 103 */           this.object = (localObject1 = initialize());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 108 */     return (T)localObject1;
/*     */   }
/*     */   
/*     */   protected abstract T initialize()
/*     */     throws ConcurrentException;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\LazyInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */