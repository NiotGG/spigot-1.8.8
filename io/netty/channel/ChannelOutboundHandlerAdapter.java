/*     */ package io.netty.channel;
/*     */ 
/*     */ import java.net.SocketAddress;
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
/*     */ public class ChannelOutboundHandlerAdapter
/*     */   extends ChannelHandlerAdapter
/*     */   implements ChannelOutboundHandler
/*     */ {
/*     */   public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/*  35 */     paramChannelHandlerContext.bind(paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/*  47 */     paramChannelHandlerContext.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/*  59 */     paramChannelHandlerContext.disconnect(paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/*  71 */     paramChannelHandlerContext.close(paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/*  82 */     paramChannelHandlerContext.deregister(paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void read(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/*  93 */     paramChannelHandlerContext.read();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 104 */     paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 115 */     paramChannelHandlerContext.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelOutboundHandlerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */