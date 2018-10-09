/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import java.net.InetSocketAddress;
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
/*     */ public final class EpollServerSocketChannel
/*     */   extends AbstractEpollChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*     */   private final EpollServerSocketChannelConfig config;
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   public EpollServerSocketChannel()
/*     */   {
/*  37 */     super(Native.socketStreamFd(), 4);
/*  38 */     this.config = new EpollServerSocketChannelConfig(this);
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/*  43 */     return paramEventLoop instanceof EpollEventLoop;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/*  48 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  49 */     checkResolvable(localInetSocketAddress);
/*  50 */     Native.bind(this.fd, localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/*  51 */     this.local = Native.localAddress(this.fd);
/*  52 */     Native.listen(this.fd, this.config.getBacklog());
/*  53 */     this.active = true;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig config()
/*     */   {
/*  58 */     return this.config;
/*     */   }
/*     */   
/*     */   protected InetSocketAddress localAddress0()
/*     */   {
/*  63 */     return this.local;
/*     */   }
/*     */   
/*     */   protected InetSocketAddress remoteAddress0()
/*     */   {
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe()
/*     */   {
/*  73 */     return new EpollServerSocketUnsafe();
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*  78 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  83 */   protected Object filterOutboundMessage(Object paramObject) throws Exception { throw new UnsupportedOperationException(); }
/*     */   
/*     */   final class EpollServerSocketUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
/*  86 */     EpollServerSocketUnsafe() { super(); }
/*     */     
/*     */ 
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/*  91 */       paramChannelPromise.setFailure(new UnsupportedOperationException());
/*     */     }
/*     */     
/*     */     void epollInReady()
/*     */     {
/*  96 */       assert (EpollServerSocketChannel.this.eventLoop().inEventLoop());
/*  97 */       ChannelPipeline localChannelPipeline = EpollServerSocketChannel.this.pipeline();
/*  98 */       Object localObject1 = null;
/*     */       try {
/*     */         try {
/*     */           for (;;) {
/* 102 */             int i = Native.accept(EpollServerSocketChannel.this.fd);
/* 103 */             if (i == -1) {
/*     */               break;
/*     */             }
/*     */             try
/*     */             {
/* 108 */               this.readPending = false;
/* 109 */               localChannelPipeline.fireChannelRead(new EpollSocketChannel(EpollServerSocketChannel.this, i));
/*     */             }
/*     */             catch (Throwable localThrowable2) {
/* 112 */               localChannelPipeline.fireChannelReadComplete();
/* 113 */               localChannelPipeline.fireExceptionCaught(localThrowable2);
/*     */             }
/*     */           }
/*     */         } catch (Throwable localThrowable1) {
/* 117 */           localObject1 = localThrowable1;
/*     */         }
/* 119 */         localChannelPipeline.fireChannelReadComplete();
/*     */         
/* 121 */         if (localObject1 != null) {
/* 122 */           localChannelPipeline.fireExceptionCaught((Throwable)localObject1);
/*     */ 
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/*     */ 
/* 131 */         if ((!EpollServerSocketChannel.this.config.isAutoRead()) && (!this.readPending)) {
/* 132 */           clearEpollIn0();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollServerSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */