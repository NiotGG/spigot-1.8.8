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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private I inboundHandler;
/*     */   private O outboundHandler;
/*     */   
/*     */   protected CombinedChannelDuplexHandler() {}
/*     */   
/*     */   public CombinedChannelDuplexHandler(I paramI, O paramO)
/*     */   {
/*  41 */     init(paramI, paramO);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void init(I paramI, O paramO)
/*     */   {
/*  53 */     validate(paramI, paramO);
/*  54 */     this.inboundHandler = paramI;
/*  55 */     this.outboundHandler = paramO;
/*     */   }
/*     */   
/*     */   private void validate(I paramI, O paramO) {
/*  59 */     if (this.inboundHandler != null) {
/*  60 */       throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  65 */     if (paramI == null) {
/*  66 */       throw new NullPointerException("inboundHandler");
/*     */     }
/*  68 */     if (paramO == null) {
/*  69 */       throw new NullPointerException("outboundHandler");
/*     */     }
/*  71 */     if ((paramI instanceof ChannelOutboundHandler)) {
/*  72 */       throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */     
/*     */ 
/*  76 */     if ((paramO instanceof ChannelInboundHandler)) {
/*  77 */       throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected final I inboundHandler()
/*     */   {
/*  84 */     return this.inboundHandler;
/*     */   }
/*     */   
/*     */   protected final O outboundHandler() {
/*  88 */     return this.outboundHandler;
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/*  93 */     if (this.inboundHandler == null) {
/*  94 */       throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 100 */       this.inboundHandler.handlerAdded(paramChannelHandlerContext);
/*     */     } finally {
/* 102 */       this.outboundHandler.handlerAdded(paramChannelHandlerContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/*     */     try {
/* 109 */       this.inboundHandler.handlerRemoved(paramChannelHandlerContext);
/*     */     } finally {
/* 111 */       this.outboundHandler.handlerRemoved(paramChannelHandlerContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 117 */     this.inboundHandler.channelRegistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 122 */     this.inboundHandler.channelUnregistered(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelActive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 127 */     this.inboundHandler.channelActive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 132 */     this.inboundHandler.channelInactive(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */   {
/* 137 */     this.inboundHandler.exceptionCaught(paramChannelHandlerContext, paramThrowable);
/*     */   }
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 142 */     this.inboundHandler.userEventTriggered(paramChannelHandlerContext, paramObject);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 147 */     this.inboundHandler.channelRead(paramChannelHandlerContext, paramObject);
/*     */   }
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 152 */     this.inboundHandler.channelReadComplete(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */ 
/*     */   public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 159 */     this.outboundHandler.bind(paramChannelHandlerContext, paramSocketAddress, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     throws Exception
/*     */   {
/* 167 */     this.outboundHandler.connect(paramChannelHandlerContext, paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 172 */     this.outboundHandler.disconnect(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 177 */     this.outboundHandler.close(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 182 */     this.outboundHandler.deregister(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void read(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 187 */     this.outboundHandler.read(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 192 */     this.outboundHandler.write(paramChannelHandlerContext, paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 197 */     this.outboundHandler.flush(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 202 */     this.inboundHandler.channelWritabilityChanged(paramChannelHandlerContext);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\CombinedChannelDuplexHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */