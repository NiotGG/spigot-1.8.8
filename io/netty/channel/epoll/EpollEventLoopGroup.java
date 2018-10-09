/*    */ package io.netty.channel.epoll;
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
/*    */ 
/*    */ 
/*    */ public final class EpollEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public EpollEventLoopGroup()
/*    */   {
/* 34 */     this(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EpollEventLoopGroup(int paramInt)
/*    */   {
/* 41 */     this(paramInt, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public EpollEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory)
/*    */   {
/* 48 */     this(paramInt, paramThreadFactory, 128);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public EpollEventLoopGroup(int paramInt1, ThreadFactory paramThreadFactory, int paramInt2)
/*    */   {
/* 56 */     super(paramInt1, paramThreadFactory, new Object[] { Integer.valueOf(paramInt2) });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setIoRatio(int paramInt)
/*    */   {
/* 64 */     for (EventExecutor localEventExecutor : children()) {
/* 65 */       ((EpollEventLoop)localEventExecutor).setIoRatio(paramInt);
/*    */     }
/*    */   }
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs) throws Exception
/*    */   {
/* 71 */     return new EpollEventLoop(this, paramThreadFactory, ((Integer)paramVarArgs[0]).intValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */