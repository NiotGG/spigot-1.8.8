/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.util.NetUtil;
/*     */ import java.util.Map;
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
/*     */ public final class EpollServerSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*     */   private final EpollServerSocketChannel channel;
/*  36 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   
/*     */   EpollServerSocketChannelConfig(EpollServerSocketChannel paramEpollServerSocketChannel) {
/*  39 */     super(paramEpollServerSocketChannel);
/*  40 */     this.channel = paramEpollServerSocketChannel;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  45 */     setReuseAddress(true);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  50 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG, EpollChannelOption.SO_REUSEPORT });
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  56 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  57 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  59 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  60 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  62 */     if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  63 */       return Integer.valueOf(getBacklog());
/*     */     }
/*  65 */     if (paramChannelOption == EpollChannelOption.SO_REUSEPORT) {
/*  66 */       return Boolean.valueOf(isReusePort());
/*     */     }
/*  68 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  73 */     validate(paramChannelOption, paramT);
/*     */     
/*  75 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  76 */       setReceiveBufferSize(((Integer)paramT).intValue());
/*  77 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  78 */       setReuseAddress(((Boolean)paramT).booleanValue());
/*  79 */     } else if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  80 */       setBacklog(((Integer)paramT).intValue());
/*  81 */     } else if (paramChannelOption == EpollChannelOption.SO_REUSEPORT) {
/*  82 */       setReusePort(((Boolean)paramT).booleanValue());
/*     */     } else {
/*  84 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/*  92 */     return Native.isReuseAddress(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/*  97 */     Native.setReuseAddress(this.channel.fd, paramBoolean ? 1 : 0);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/* 103 */     return Native.getReceiveBufferSize(this.channel.fd);
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 108 */     Native.setReceiveBufferSize(this.channel.fd, paramInt);
/*     */     
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public int getBacklog()
/*     */   {
/* 120 */     return this.backlog;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setBacklog(int paramInt)
/*     */   {
/* 125 */     if (paramInt < 0) {
/* 126 */       throw new IllegalArgumentException("backlog: " + paramInt);
/*     */     }
/* 128 */     this.backlog = paramInt;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 134 */     super.setConnectTimeoutMillis(paramInt);
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 140 */     super.setMaxMessagesPerRead(paramInt);
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 146 */     super.setWriteSpinCount(paramInt);
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 152 */     super.setAllocator(paramByteBufAllocator);
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 158 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 164 */     super.setAutoRead(paramBoolean);
/* 165 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 170 */     super.setWriteBufferHighWaterMark(paramInt);
/* 171 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 176 */     super.setWriteBufferLowWaterMark(paramInt);
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public EpollServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 182 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 183 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isReusePort()
/*     */   {
/* 190 */     return Native.isReusePort(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EpollServerSocketChannelConfig setReusePort(boolean paramBoolean)
/*     */   {
/* 201 */     Native.setReusePort(this.channel.fd, paramBoolean ? 1 : 0);
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 207 */     this.channel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */