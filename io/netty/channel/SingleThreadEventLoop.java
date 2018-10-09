/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.SingleThreadEventExecutor;
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
/*    */ public abstract class SingleThreadEventLoop
/*    */   extends SingleThreadEventExecutor
/*    */   implements EventLoop
/*    */ {
/*    */   protected SingleThreadEventLoop(EventLoopGroup paramEventLoopGroup, ThreadFactory paramThreadFactory, boolean paramBoolean)
/*    */   {
/* 33 */     super(paramEventLoopGroup, paramThreadFactory, paramBoolean);
/*    */   }
/*    */   
/*    */   public EventLoopGroup parent()
/*    */   {
/* 38 */     return (EventLoopGroup)super.parent();
/*    */   }
/*    */   
/*    */   public EventLoop next()
/*    */   {
/* 43 */     return (EventLoop)super.next();
/*    */   }
/*    */   
/*    */   public ChannelFuture register(Channel paramChannel)
/*    */   {
/* 48 */     return register(paramChannel, new DefaultChannelPromise(paramChannel, this));
/*    */   }
/*    */   
/*    */   public ChannelFuture register(Channel paramChannel, ChannelPromise paramChannelPromise)
/*    */   {
/* 53 */     if (paramChannel == null) {
/* 54 */       throw new NullPointerException("channel");
/*    */     }
/* 56 */     if (paramChannelPromise == null) {
/* 57 */       throw new NullPointerException("promise");
/*    */     }
/*    */     
/* 60 */     paramChannel.unsafe().register(this, paramChannelPromise);
/* 61 */     return paramChannelPromise;
/*    */   }
/*    */   
/*    */   protected boolean wakesUpForTask(Runnable paramRunnable)
/*    */   {
/* 66 */     return !(paramRunnable instanceof NonWakeupRunnable);
/*    */   }
/*    */   
/*    */   static abstract interface NonWakeupRunnable
/*    */     extends Runnable
/*    */   {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\SingleThreadEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */