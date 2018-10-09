/*     */ package io.netty.channel.socket;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SocketChannelConfig
/*     */ {
/*     */   protected final Socket javaSocket;
/*     */   private volatile boolean allowHalfClosure;
/*     */   
/*     */   public DefaultSocketChannelConfig(SocketChannel paramSocketChannel, Socket paramSocket)
/*     */   {
/*  45 */     super(paramSocketChannel);
/*  46 */     if (paramSocket == null) {
/*  47 */       throw new NullPointerException("javaSocket");
/*     */     }
/*  49 */     this.javaSocket = paramSocket;
/*     */     
/*     */ 
/*  52 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*     */       try {
/*  54 */         setTcpNoDelay(true);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  63 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.TCP_NODELAY, ChannelOption.SO_KEEPALIVE, ChannelOption.SO_REUSEADDR, ChannelOption.SO_LINGER, ChannelOption.IP_TOS, ChannelOption.ALLOW_HALF_CLOSURE });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  72 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  73 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  75 */     if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  76 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/*  78 */     if (paramChannelOption == ChannelOption.TCP_NODELAY) {
/*  79 */       return Boolean.valueOf(isTcpNoDelay());
/*     */     }
/*  81 */     if (paramChannelOption == ChannelOption.SO_KEEPALIVE) {
/*  82 */       return Boolean.valueOf(isKeepAlive());
/*     */     }
/*  84 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  85 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  87 */     if (paramChannelOption == ChannelOption.SO_LINGER) {
/*  88 */       return Integer.valueOf(getSoLinger());
/*     */     }
/*  90 */     if (paramChannelOption == ChannelOption.IP_TOS) {
/*  91 */       return Integer.valueOf(getTrafficClass());
/*     */     }
/*  93 */     if (paramChannelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
/*  94 */       return Boolean.valueOf(isAllowHalfClosure());
/*     */     }
/*     */     
/*  97 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 102 */     validate(paramChannelOption, paramT);
/*     */     
/* 104 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/* 105 */       setReceiveBufferSize(((Integer)paramT).intValue());
/* 106 */     } else if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/* 107 */       setSendBufferSize(((Integer)paramT).intValue());
/* 108 */     } else if (paramChannelOption == ChannelOption.TCP_NODELAY) {
/* 109 */       setTcpNoDelay(((Boolean)paramT).booleanValue());
/* 110 */     } else if (paramChannelOption == ChannelOption.SO_KEEPALIVE) {
/* 111 */       setKeepAlive(((Boolean)paramT).booleanValue());
/* 112 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/* 113 */       setReuseAddress(((Boolean)paramT).booleanValue());
/* 114 */     } else if (paramChannelOption == ChannelOption.SO_LINGER) {
/* 115 */       setSoLinger(((Integer)paramT).intValue());
/* 116 */     } else if (paramChannelOption == ChannelOption.IP_TOS) {
/* 117 */       setTrafficClass(((Integer)paramT).intValue());
/* 118 */     } else if (paramChannelOption == ChannelOption.ALLOW_HALF_CLOSURE) {
/* 119 */       setAllowHalfClosure(((Boolean)paramT).booleanValue());
/*     */     } else {
/* 121 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/*     */     try {
/* 130 */       return this.javaSocket.getReceiveBufferSize();
/*     */     } catch (SocketException localSocketException) {
/* 132 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/*     */     try {
/* 139 */       return this.javaSocket.getSendBufferSize();
/*     */     } catch (SocketException localSocketException) {
/* 141 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSoLinger()
/*     */   {
/*     */     try {
/* 148 */       return this.javaSocket.getSoLinger();
/*     */     } catch (SocketException localSocketException) {
/* 150 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getTrafficClass()
/*     */   {
/*     */     try {
/* 157 */       return this.javaSocket.getTrafficClass();
/*     */     } catch (SocketException localSocketException) {
/* 159 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isKeepAlive()
/*     */   {
/*     */     try {
/* 166 */       return this.javaSocket.getKeepAlive();
/*     */     } catch (SocketException localSocketException) {
/* 168 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/*     */     try {
/* 175 */       return this.javaSocket.getReuseAddress();
/*     */     } catch (SocketException localSocketException) {
/* 177 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isTcpNoDelay()
/*     */   {
/*     */     try {
/* 184 */       return this.javaSocket.getTcpNoDelay();
/*     */     } catch (SocketException localSocketException) {
/* 186 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setKeepAlive(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 193 */       this.javaSocket.setKeepAlive(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 195 */       throw new ChannelException(localSocketException);
/*     */     }
/* 197 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public SocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 203 */     this.javaSocket.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 210 */       this.javaSocket.setReceiveBufferSize(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 212 */       throw new ChannelException(localSocketException);
/*     */     }
/* 214 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 220 */       this.javaSocket.setReuseAddress(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 222 */       throw new ChannelException(localSocketException);
/*     */     }
/* 224 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 230 */       this.javaSocket.setSendBufferSize(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 232 */       throw new ChannelException(localSocketException);
/*     */     }
/* 234 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setSoLinger(int paramInt)
/*     */   {
/*     */     try {
/* 240 */       if (paramInt < 0) {
/* 241 */         this.javaSocket.setSoLinger(false, 0);
/*     */       } else {
/* 243 */         this.javaSocket.setSoLinger(true, paramInt);
/*     */       }
/*     */     } catch (SocketException localSocketException) {
/* 246 */       throw new ChannelException(localSocketException);
/*     */     }
/* 248 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setTcpNoDelay(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 254 */       this.javaSocket.setTcpNoDelay(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 256 */       throw new ChannelException(localSocketException);
/*     */     }
/* 258 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setTrafficClass(int paramInt)
/*     */   {
/*     */     try {
/* 264 */       this.javaSocket.setTrafficClass(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 266 */       throw new ChannelException(localSocketException);
/*     */     }
/* 268 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAllowHalfClosure()
/*     */   {
/* 273 */     return this.allowHalfClosure;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setAllowHalfClosure(boolean paramBoolean)
/*     */   {
/* 278 */     this.allowHalfClosure = paramBoolean;
/* 279 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 284 */     super.setConnectTimeoutMillis(paramInt);
/* 285 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 290 */     super.setMaxMessagesPerRead(paramInt);
/* 291 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 296 */     super.setWriteSpinCount(paramInt);
/* 297 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 302 */     super.setAllocator(paramByteBufAllocator);
/* 303 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 308 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 309 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 314 */     super.setAutoRead(paramBoolean);
/* 315 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 320 */     super.setAutoClose(paramBoolean);
/* 321 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 326 */     super.setWriteBufferHighWaterMark(paramInt);
/* 327 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 332 */     super.setWriteBufferLowWaterMark(paramInt);
/* 333 */     return this;
/*     */   }
/*     */   
/*     */   public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 338 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 339 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\DefaultSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */