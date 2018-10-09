/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.SingleThreadEventLoop;
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
/*    */ final class LocalEventLoop
/*    */   extends SingleThreadEventLoop
/*    */ {
/*    */   LocalEventLoop(LocalEventLoopGroup paramLocalEventLoopGroup, ThreadFactory paramThreadFactory)
/*    */   {
/* 25 */     super(paramLocalEventLoopGroup, paramThreadFactory, true);
/*    */   }
/*    */   
/*    */   protected void run()
/*    */   {
/*    */     for (;;) {
/* 31 */       Runnable localRunnable = takeTask();
/* 32 */       if (localRunnable != null) {
/* 33 */         localRunnable.run();
/* 34 */         updateLastExecutionTime();
/*    */       }
/*    */       
/* 37 */       if (confirmShutdown()) {
/*    */         break;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */