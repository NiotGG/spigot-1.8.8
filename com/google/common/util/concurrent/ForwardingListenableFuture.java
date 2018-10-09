/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.concurrent.Executor;
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
/*    */ public abstract class ForwardingListenableFuture<V>
/*    */   extends ForwardingFuture<V>
/*    */   implements ListenableFuture<V>
/*    */ {
/*    */   protected abstract ListenableFuture<V> delegate();
/*    */   
/*    */   public void addListener(Runnable listener, Executor exec)
/*    */   {
/* 47 */     delegate().addListener(listener, exec);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static abstract class SimpleForwardingListenableFuture<V>
/*    */     extends ForwardingListenableFuture<V>
/*    */   {
/*    */     private final ListenableFuture<V> delegate;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     protected SimpleForwardingListenableFuture(ListenableFuture<V> delegate)
/*    */     {
/* 66 */       this.delegate = ((ListenableFuture)Preconditions.checkNotNull(delegate));
/*    */     }
/*    */     
/*    */     protected final ListenableFuture<V> delegate()
/*    */     {
/* 71 */       return this.delegate;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\ForwardingListenableFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */