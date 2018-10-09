/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
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
/*    */ public abstract class AbstractFuture<V>
/*    */   implements Future<V>
/*    */ {
/*    */   public V get()
/*    */     throws InterruptedException, ExecutionException
/*    */   {
/* 31 */     await();
/*    */     
/* 33 */     Throwable localThrowable = cause();
/* 34 */     if (localThrowable == null) {
/* 35 */       return (V)getNow();
/*    */     }
/* 37 */     throw new ExecutionException(localThrowable);
/*    */   }
/*    */   
/*    */   public V get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException
/*    */   {
/* 42 */     if (await(paramLong, paramTimeUnit)) {
/* 43 */       Throwable localThrowable = cause();
/* 44 */       if (localThrowable == null) {
/* 45 */         return (V)getNow();
/*    */       }
/* 47 */       throw new ExecutionException(localThrowable);
/*    */     }
/* 49 */     throw new TimeoutException();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\AbstractFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */