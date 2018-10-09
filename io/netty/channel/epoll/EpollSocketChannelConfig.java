/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.SocketChannelConfig;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public final class EpollSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SocketChannelConfig
/*     */ {
/*     */   private final EpollSocketChannel channel;
/*     */   private volatile boolean allowHalfClosure;
/*     */   
/*     */   EpollSocketChannelConfig(EpollSocketChannel paramEpollSocketChannel)
/*     */   {
/*  39 */     super(paramEpollSocketChannel);
/*     */     
/*  41 */     this.channel = paramEpollSocketChannel;
/*  42 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*  43 */       setTcpNoDelay(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  49 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE, EpollChannelOption.TCP_CORK, EpollChannelOption.TCP_KEEPCNT, EpollChannelOption.TCP_KEEPIDLE, EpollChannelOption.TCP_KEEPINTVL });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  59 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  60 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  62 */     if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  63 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/*  65 */     if (paramChannelOption == ChannelOption.TCP_NODELAY) {
/*  66 */       return Boolean.valueOf(isTcpNoDelay());
/*     */     }
/*  68 */     if (paramChannelOption == ChannelOption.SO_KEEPALIVE) {
/*  69 */       return Boolean.valueOf(isKeepAlive());
/*     */     }
/*  71 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  72 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  74 */     if (paramChannelOption == ChannelOption.SO_LINGER) {
/*  75 */       return Integer.valueOf(getSoLinger());
/*     */     }
/*  77 */     if (paramChannelOption == ChannelOption.IP_TOS) {
/*  78 */       return Integer.valueOf(getTrafficClass());
/*     */     }
/*  80 */     if (paramChannelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  81 */       return Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*  83 */     if (paramChannelOption == EpollChannelOption.TCP_CORK) {
/*  84 */       return Boolean.valueOf(isTcpCork());
/*     */     }
/*  86 */     if (paramChannelOption == EpollChannelOption.TCP_KEEPIDLE) {
/*  87 */       return Integer.valueOf(getTcpKeepIdle());
/*     */     }
/*  89 */     if (paramChannelOption == EpollChannelOption.TCP_KEEPINTVL) {
/*  90 */       return Integer.valueOf(getTcpKeepIntvl());
/*     */     }
/*  92 */     if (paramChannelOption == EpollChannelOption.TCP_KEEPCNT) {
/*  93 */       return Integer.valueOf(getTcpKeepCnt());
/*     */     }
/*  95 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 100 */     validate(paramChannelOption, paramT);
/*     */     
/* 102 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/* 103 */       setReceiveBufferSize(((Integer)paramT).intValue());
/* 104 */     } else if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/* 105 */       setSendBufferSize(((Integer)paramT).intValue());
/* 106 */     } else if (paramChannelOption == ChannelOption.TCP_NODELAY) {
/* 107 */       setTcpNoDelay(((Boolean)paramT).booleanValue());
/* 108 */     } else if (paramChannelOption == ChannelOption.SO_KEEPALIVE) {
/* 109 */       setKeepAlive(((Boolean)paramT).booleanValue());
/* 110 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/* 111 */       setReuseAddress(((Boolean)paramT).booleanValue());
/* 112 */     } else if (paramChannelOption == ChannelOption.SO_LINGER) {
/* 113 */       setSoLinger(((Integer)paramT).intValue());
/* 114 */     } else if (paramChannelOption == ChannelOption.IP_TOS) {
/* 115 */       setTrafficClass(((Integer)paramT).intValue());
/* 116 */     } else if (paramChannelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
/* 117 */       setAllowHalfClosure(((Boolean)paramT).booleanValue());
/* 118 */     } else if (paramChannelOption == EpollChannelOption.TCP_CORK) {
/* 119 */       setTcpCork(((Boolean)paramT).booleanValue());
/* 120 */     } else if (paramChannelOption == EpollChannelOption.TCP_KEEPIDLE) {
/* 121 */       setTcpKeepIdle(((Integer)paramT).intValue());
/* 122 */     } else if (paramChannelOption == EpollChannelOption.TCP_KEEPCNT) {
/* 123 */       setTcpKeepCntl(((Integer)paramT).intValue());
/* 124 */     } else if (paramChannelOption == EpollChannelOption.TCP_KEEPINTVL) {
/* 125 */       setTcpKeepIntvl(((Integer)paramT).intValue());
/*     */     } else {
/* 127 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/* 135 */     return Native.getReceiveBufferSize(this.channel.fd);
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/* 140 */     return Native.getSendBufferSize(this.channel.fd);
/*     */   }
/*     */   
/*     */   public int getSoLinger()
/*     */   {
/* 145 */     return Native.getSoLinger(this.channel.fd);
/*     */   }
/*     */   
/*     */   public int getTrafficClass()
/*     */   {
/* 150 */     return Native.getTrafficClass(this.channel.fd);
/*     */   }
/*     */   
/*     */   public boolean isKeepAlive()
/*     */   {
/* 155 */     return Native.isKeepAlive(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/* 160 */     return Native.isReuseAddress(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */   public boolean isTcpNoDelay()
/*     */   {
/* 165 */     return Native.isTcpNoDelay(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isTcpCork()
/*     */   {
/* 172 */     return Native.isTcpCork(this.channel.fd) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTcpKeepIdle()
/*     */   {
/* 179 */     return Native.getTcpKeepIdle(this.channel.fd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTcpKeepIntvl()
/*     */   {
/* 186 */     return Native.getTcpKeepIntvl(this.channel.fd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getTcpKeepCnt()
/*     */   {
/* 193 */     return Native.getTcpKeepCnt(this.channel.fd);
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setKeepAlive(boolean paramBoolean)
/*     */   {
/* 198 */     Native.setKeepAlive(this.channel.fd, paramBoolean ? 1 : 0);
/* 199 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public EpollSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 205 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 210 */     Native.setReceiveBufferSize(this.channel.fd, paramInt);
/* 211 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 216 */     Native.setReuseAddress(this.channel.fd, paramBoolean ? 1 : 0);
/* 217 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/* 222 */     Native.setSendBufferSize(this.channel.fd, paramInt);
/* 223 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setSoLinger(int paramInt)
/*     */   {
/* 228 */     Native.setSoLinger(this.channel.fd, paramInt);
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setTcpNoDelay(boolean paramBoolean)
/*     */   {
/* 234 */     Native.setTcpNoDelay(this.channel.fd, paramBoolean ? 1 : 0);
/* 235 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EpollSocketChannelConfig setTcpCork(boolean paramBoolean)
/*     */   {
/* 242 */     Native.setTcpCork(this.channel.fd, paramBoolean ? 1 : 0);
/* 243 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setTrafficClass(int paramInt)
/*     */   {
/* 248 */     Native.setTrafficClass(this.channel.fd, paramInt);
/* 249 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EpollSocketChannelConfig setTcpKeepIdle(int paramInt)
/*     */   {
/* 256 */     Native.setTcpKeepIdle(this.channel.fd, paramInt);
/* 257 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EpollSocketChannelConfig setTcpKeepIntvl(int paramInt)
/*     */   {
/* 264 */     Native.setTcpKeepIntvl(this.channel.fd, paramInt);
/* 265 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EpollSocketChannelConfig setTcpKeepCntl(int paramInt)
/*     */   {
/* 272 */     Native.setTcpKeepCnt(this.channel.fd, paramInt);
/* 273 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAllowHalfClosure()
/*     */   {
/* 278 */     return this.allowHalfClosure;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setAllowHalfClosure(boolean paramBoolean)
/*     */   {
/* 283 */     this.allowHalfClosure = paramBoolean;
/* 284 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 289 */     super.setConnectTimeoutMillis(paramInt);
/* 290 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 295 */     super.setMaxMessagesPerRead(paramInt);
/* 296 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 301 */     super.setWriteSpinCount(paramInt);
/* 302 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 307 */     super.setAllocator(paramByteBufAllocator);
/* 308 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 313 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 319 */     super.setAutoRead(paramBoolean);
/* 320 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 325 */     super.setAutoClose(paramBoolean);
/* 326 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 331 */     super.setWriteBufferHighWaterMark(paramInt);
/* 332 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 337 */     super.setWriteBufferLowWaterMark(paramInt);
/* 338 */     return this;
/*     */   }
/*     */   
/*     */   public EpollSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 343 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 344 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 349 */     this.channel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */