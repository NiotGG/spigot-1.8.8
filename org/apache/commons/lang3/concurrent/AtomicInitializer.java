/*    */ package org.apache.commons.lang3.concurrent;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AtomicInitializer<T>
/*    */   implements ConcurrentInitializer<T>
/*    */ {
/* 69 */   private final AtomicReference<T> reference = new AtomicReference();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public T get()
/*    */     throws ConcurrentException
/*    */   {
/* 82 */     Object localObject = this.reference.get();
/*    */     
/* 84 */     if (localObject == null) {
/* 85 */       localObject = initialize();
/* 86 */       if (!this.reference.compareAndSet(null, localObject))
/*    */       {
/* 88 */         localObject = this.reference.get();
/*    */       }
/*    */     }
/*    */     
/* 92 */     return (T)localObject;
/*    */   }
/*    */   
/*    */   protected abstract T initialize()
/*    */     throws ConcurrentException;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\AtomicInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */