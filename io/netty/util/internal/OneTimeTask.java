/*    */ package io.netty.util.internal;
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
/*    */ public abstract class OneTimeTask
/*    */   extends MpscLinkedQueueNode<Runnable>
/*    */   implements Runnable
/*    */ {
/*    */   public Runnable value()
/*    */   {
/* 30 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\OneTimeTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */