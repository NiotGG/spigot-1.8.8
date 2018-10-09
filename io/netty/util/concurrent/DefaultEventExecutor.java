/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ final class DefaultEventExecutor
/*    */   extends SingleThreadEventExecutor
/*    */ {
/*    */   DefaultEventExecutor(DefaultEventExecutorGroup paramDefaultEventExecutorGroup, ThreadFactory paramThreadFactory)
/*    */   {
/* 28 */     super(paramDefaultEventExecutorGroup, paramThreadFactory, true);
/*    */   }
/*    */   
/*    */   protected void run()
/*    */   {
/*    */     for (;;) {
/* 34 */       Runnable localRunnable = takeTask();
/* 35 */       if (localRunnable != null) {
/* 36 */         localRunnable.run();
/* 37 */         updateLastExecutionTime();
/*    */       }
/*    */       
/* 40 */       if (confirmShutdown()) {
/*    */         break;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */