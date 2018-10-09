/*    */ package io.netty.util.concurrent;
/*    */ 
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
/*    */ public final class ImmediateExecutor
/*    */   implements Executor
/*    */ {
/* 24 */   public static final ImmediateExecutor INSTANCE = new ImmediateExecutor();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void execute(Runnable paramRunnable)
/*    */   {
/* 32 */     if (paramRunnable == null) {
/* 33 */       throw new NullPointerException("command");
/*    */     }
/* 35 */     paramRunnable.run();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\ImmediateExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */