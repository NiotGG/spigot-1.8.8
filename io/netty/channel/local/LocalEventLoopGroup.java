/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.MultithreadEventLoopGroup;
/*    */ import io.netty.util.concurrent.EventExecutor;
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
/*    */ public class LocalEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public LocalEventLoopGroup()
/*    */   {
/* 32 */     this(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LocalEventLoopGroup(int paramInt)
/*    */   {
/* 41 */     this(paramInt, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LocalEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory)
/*    */   {
/* 51 */     super(paramInt, paramThreadFactory, new Object[0]);
/*    */   }
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*    */     throws Exception
/*    */   {
/* 57 */     return new LocalEventLoop(this, paramThreadFactory);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\local\LocalEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */