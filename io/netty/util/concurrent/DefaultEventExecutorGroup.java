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
/*    */ 
/*    */ 
/*    */ public class DefaultEventExecutorGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */ {
/*    */   public DefaultEventExecutorGroup(int paramInt)
/*    */   {
/* 30 */     this(paramInt, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DefaultEventExecutorGroup(int paramInt, ThreadFactory paramThreadFactory)
/*    */   {
/* 40 */     super(paramInt, paramThreadFactory, new Object[0]);
/*    */   }
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*    */     throws Exception
/*    */   {
/* 46 */     return new DefaultEventExecutor(this, paramThreadFactory);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */