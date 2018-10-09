/*    */ package io.netty.channel.nio;
/*    */ 
/*    */ import io.netty.channel.MultithreadEventLoopGroup;
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import java.nio.channels.spi.SelectorProvider;
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
/*    */ 
/*    */ public class NioEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public NioEventLoopGroup()
/*    */   {
/* 36 */     this(0);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public NioEventLoopGroup(int paramInt)
/*    */   {
/* 44 */     this(paramInt, null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public NioEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory)
/*    */   {
/* 52 */     this(paramInt, paramThreadFactory, SelectorProvider.provider());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NioEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory, SelectorProvider paramSelectorProvider)
/*    */   {
/* 61 */     super(paramInt, paramThreadFactory, new Object[] { paramSelectorProvider });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setIoRatio(int paramInt)
/*    */   {
/* 69 */     for (EventExecutor localEventExecutor : children()) {
/* 70 */       ((NioEventLoop)localEventExecutor).setIoRatio(paramInt);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void rebuildSelectors()
/*    */   {
/* 79 */     for (EventExecutor localEventExecutor : children()) {
/* 80 */       ((NioEventLoop)localEventExecutor).rebuildSelector();
/*    */     }
/*    */   }
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*    */     throws Exception
/*    */   {
/* 87 */     return new NioEventLoop(this, paramThreadFactory, (SelectorProvider)paramVarArgs[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\NioEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */