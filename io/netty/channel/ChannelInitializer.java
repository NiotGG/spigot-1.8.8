/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*    */ @ChannelHandler.Sharable
/*    */ public abstract class ChannelInitializer<C extends Channel>
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/* 52 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected abstract void initChannel(C paramC)
/*    */     throws Exception;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void channelRegistered(ChannelHandlerContext paramChannelHandlerContext)
/*    */     throws Exception
/*    */   {
/* 66 */     ChannelPipeline localChannelPipeline = paramChannelHandlerContext.pipeline();
/* 67 */     int i = 0;
/*    */     try {
/* 69 */       initChannel(paramChannelHandlerContext.channel());
/* 70 */       localChannelPipeline.remove(this);
/* 71 */       paramChannelHandlerContext.fireChannelRegistered();
/* 72 */       i = 1;
/*    */     } catch (Throwable localThrowable) {
/* 74 */       logger.warn("Failed to initialize a channel. Closing: " + paramChannelHandlerContext.channel(), localThrowable);
/*    */     } finally {
/* 76 */       if (localChannelPipeline.context(this) != null) {
/* 77 */         localChannelPipeline.remove(this);
/*    */       }
/* 79 */       if (i == 0) {
/* 80 */         paramChannelHandlerContext.close();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */