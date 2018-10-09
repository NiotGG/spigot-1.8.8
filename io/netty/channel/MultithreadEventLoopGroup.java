/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.DefaultThreadFactory;
/*    */ import io.netty.util.concurrent.MultithreadEventExecutorGroup;
/*    */ import io.netty.util.internal.SystemPropertyUtil;
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*    */ public abstract class MultithreadEventLoopGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */   implements EventLoopGroup
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 37 */   private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
/*    */   
/*    */   static {
/* 40 */     if (logger.isDebugEnabled()) {
/* 41 */       logger.debug("-Dio.netty.eventLoopThreads: {}", Integer.valueOf(DEFAULT_EVENT_LOOP_THREADS));
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected MultithreadEventLoopGroup(int paramInt, ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*    */   {
/* 49 */     super(paramInt == 0 ? DEFAULT_EVENT_LOOP_THREADS : paramInt, paramThreadFactory, paramVarArgs);
/*    */   }
/*    */   
/*    */   protected ThreadFactory newDefaultThreadFactory()
/*    */   {
/* 54 */     return new DefaultThreadFactory(getClass(), 10);
/*    */   }
/*    */   
/*    */   public EventLoop next()
/*    */   {
/* 59 */     return (EventLoop)super.next();
/*    */   }
/*    */   
/*    */   public ChannelFuture register(Channel paramChannel)
/*    */   {
/* 64 */     return next().register(paramChannel);
/*    */   }
/*    */   
/*    */   public ChannelFuture register(Channel paramChannel, ChannelPromise paramChannelPromise)
/*    */   {
/* 69 */     return next().register(paramChannel, paramChannelPromise);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\MultithreadEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */