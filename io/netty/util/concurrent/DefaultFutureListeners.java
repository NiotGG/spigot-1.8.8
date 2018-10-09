/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ final class DefaultFutureListeners
/*    */ {
/*    */   private GenericFutureListener<? extends Future<?>>[] listeners;
/*    */   private int size;
/*    */   private int progressiveSize;
/*    */   
/*    */   DefaultFutureListeners(GenericFutureListener<? extends Future<?>> paramGenericFutureListener1, GenericFutureListener<? extends Future<?>> paramGenericFutureListener2)
/*    */   {
/* 29 */     this.listeners = new GenericFutureListener[2];
/* 30 */     this.listeners[0] = paramGenericFutureListener1;
/* 31 */     this.listeners[1] = paramGenericFutureListener2;
/* 32 */     this.size = 2;
/* 33 */     if ((paramGenericFutureListener1 instanceof GenericProgressiveFutureListener)) {
/* 34 */       this.progressiveSize += 1;
/*    */     }
/* 36 */     if ((paramGenericFutureListener2 instanceof GenericProgressiveFutureListener)) {
/* 37 */       this.progressiveSize += 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void add(GenericFutureListener<? extends Future<?>> paramGenericFutureListener) {
/* 42 */     GenericFutureListener[] arrayOfGenericFutureListener = this.listeners;
/* 43 */     int i = this.size;
/* 44 */     if (i == arrayOfGenericFutureListener.length) {
/* 45 */       this.listeners = (arrayOfGenericFutureListener = (GenericFutureListener[])Arrays.copyOf(arrayOfGenericFutureListener, i << 1));
/*    */     }
/* 47 */     arrayOfGenericFutureListener[i] = paramGenericFutureListener;
/* 48 */     this.size = (i + 1);
/*    */     
/* 50 */     if ((paramGenericFutureListener instanceof GenericProgressiveFutureListener)) {
/* 51 */       this.progressiveSize += 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void remove(GenericFutureListener<? extends Future<?>> paramGenericFutureListener) {
/* 56 */     GenericFutureListener[] arrayOfGenericFutureListener = this.listeners;
/* 57 */     int i = this.size;
/* 58 */     for (int j = 0; j < i; j++) {
/* 59 */       if (arrayOfGenericFutureListener[j] == paramGenericFutureListener) {
/* 60 */         int k = i - j - 1;
/* 61 */         if (k > 0) {
/* 62 */           System.arraycopy(arrayOfGenericFutureListener, j + 1, arrayOfGenericFutureListener, j, k);
/*    */         }
/* 64 */         arrayOfGenericFutureListener[(--i)] = null;
/* 65 */         this.size = i;
/*    */         
/* 67 */         if ((paramGenericFutureListener instanceof GenericProgressiveFutureListener)) {
/* 68 */           this.progressiveSize -= 1;
/*    */         }
/* 70 */         return;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public GenericFutureListener<? extends Future<?>>[] listeners() {
/* 76 */     return this.listeners;
/*    */   }
/*    */   
/*    */   public int size() {
/* 80 */     return this.size;
/*    */   }
/*    */   
/*    */   public int progressiveSize() {
/* 84 */     return this.progressiveSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultFutureListeners.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */