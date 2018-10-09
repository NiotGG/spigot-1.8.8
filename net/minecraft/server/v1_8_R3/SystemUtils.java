/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ public class SystemUtils
/*    */ {
/*    */   public static <V> V a(FutureTask<V> paramFutureTask, Logger paramLogger)
/*    */   {
/*    */     try
/*    */     {
/* 44 */       paramFutureTask.run();
/* 45 */       return (V)paramFutureTask.get();
/*    */     } catch (ExecutionException localExecutionException) {
/* 47 */       paramLogger.fatal("Error executing task", localExecutionException);
/*    */     } catch (InterruptedException localInterruptedException) {
/* 49 */       paramLogger.fatal("Error executing task", localInterruptedException);
/*    */     }
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SystemUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */