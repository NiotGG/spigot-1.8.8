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
/*    */ public abstract class AtomicSafeInitializer<T>
/*    */   implements ConcurrentInitializer<T>
/*    */ {
/* 59 */   private final AtomicReference<AtomicSafeInitializer<T>> factory = new AtomicReference();
/*    */   
/*    */ 
/*    */ 
/* 63 */   private final AtomicReference<T> reference = new AtomicReference();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final T get()
/*    */     throws ConcurrentException
/*    */   {
/*    */     Object localObject;
/*    */     
/*    */ 
/*    */ 
/* 76 */     while ((localObject = this.reference.get()) == null) {
/* 77 */       if (this.factory.compareAndSet(null, this)) {
/* 78 */         this.reference.set(initialize());
/*    */       }
/*    */     }
/*    */     
/* 82 */     return (T)localObject;
/*    */   }
/*    */   
/*    */   protected abstract T initialize()
/*    */     throws ConcurrentException;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\AtomicSafeInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */