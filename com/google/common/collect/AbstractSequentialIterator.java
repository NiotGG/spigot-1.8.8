/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.NoSuchElementException;
/*    */ import javax.annotation.Nullable;
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
/*    */ @GwtCompatible
/*    */ public abstract class AbstractSequentialIterator<T>
/*    */   extends UnmodifiableIterator<T>
/*    */ {
/*    */   private T nextOrNull;
/*    */   
/*    */   protected AbstractSequentialIterator(@Nullable T firstOrNull)
/*    */   {
/* 53 */     this.nextOrNull = firstOrNull;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected abstract T computeNext(T paramT);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final boolean hasNext()
/*    */   {
/* 66 */     return this.nextOrNull != null;
/*    */   }
/*    */   
/*    */   public final T next()
/*    */   {
/* 71 */     if (!hasNext()) {
/* 72 */       throw new NoSuchElementException();
/*    */     }
/*    */     try {
/* 75 */       return (T)this.nextOrNull;
/*    */     } finally {
/* 77 */       this.nextOrNull = computeNext(this.nextOrNull);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractSequentialIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */