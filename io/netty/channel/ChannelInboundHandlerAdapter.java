/*     */ package io.netty.channel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChannelInboundHandlerAdapter
/*     */   extends ChannelHandlerAdapter
/*     */   implements ChannelInboundHandler
/*     */ {
/*     */   public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  42 */     paramChannelHandlerContext.fireChannelRegistered();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelUnregistered(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  53 */     paramChannelHandlerContext.fireChannelUnregistered();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelActive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  64 */     paramChannelHandlerContext.fireChannelActive();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  75 */     paramChannelHandlerContext.fireChannelInactive();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     throws Exception
/*     */   {
/*  86 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  97 */     paramChannelHandlerContext.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void userEventTriggered(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     throws Exception
/*     */   {
/* 108 */     paramChannelHandlerContext.fireUserEventTriggered(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void channelWritabilityChanged(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 119 */     paramChannelHandlerContext.fireChannelWritabilityChanged();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable)
/*     */     throws Exception
/*     */   {
/* 131 */     paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelInboundHandlerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */