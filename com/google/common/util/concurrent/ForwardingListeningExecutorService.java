/*    */ package com.google.common.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.Callable;
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
/*    */ public abstract class ForwardingListeningExecutorService
/*    */   extends ForwardingExecutorService
/*    */   implements ListeningExecutorService
/*    */ {
/*    */   protected abstract ListeningExecutorService delegate();
/*    */   
/*    */   public <T> ListenableFuture<T> submit(Callable<T> task)
/*    */   {
/* 40 */     return delegate().submit(task);
/*    */   }
/*    */   
/*    */   public ListenableFuture<?> submit(Runnable task)
/*    */   {
/* 45 */     return delegate().submit(task);
/*    */   }
/*    */   
/*    */   public <T> ListenableFuture<T> submit(Runnable task, T result)
/*    */   {
/* 50 */     return delegate().submit(task, result);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\ForwardingListeningExecutorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */