/*    */ package com.avaje.ebeaninternal.server.query;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
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
/*    */ public abstract class BaseFuture<T>
/*    */   implements Future<T>
/*    */ {
/*    */   private final FutureTask<T> futureTask;
/*    */   
/*    */   public BaseFuture(FutureTask<T> futureTask)
/*    */   {
/* 40 */     this.futureTask = futureTask;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 44 */     return this.futureTask.cancel(mayInterruptIfRunning);
/*    */   }
/*    */   
/*    */   public T get() throws InterruptedException, ExecutionException {
/* 48 */     return (T)this.futureTask.get();
/*    */   }
/*    */   
/*    */   public T get(long timeout, TimeUnit unit)
/*    */     throws InterruptedException, ExecutionException, TimeoutException
/*    */   {
/* 54 */     return (T)this.futureTask.get(timeout, unit);
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 58 */     return this.futureTask.isCancelled();
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 62 */     return this.futureTask.isDone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\BaseFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */