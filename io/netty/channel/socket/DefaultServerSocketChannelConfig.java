/*     */ package io.netty.channel.socket;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.NetUtil;
/*     */ import java.net.ServerSocket;
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
/*     */ public class DefaultServerSocketChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements ServerSocketChannelConfig
/*     */ {
/*     */   protected final ServerSocket javaSocket;
/*  39 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   
/*     */ 
/*     */ 
/*     */   public DefaultServerSocketChannelConfig(ServerSocketChannel paramServerSocketChannel, ServerSocket paramServerSocket)
/*     */   {
/*  45 */     super(paramServerSocketChannel);
/*  46 */     if (paramServerSocket == null) {
/*  47 */       throw new NullPointerException("javaSocket");
/*     */     }
/*  49 */     this.javaSocket = paramServerSocket;
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  54 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG });
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  60 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  61 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  63 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  64 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  66 */     if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  67 */       return Integer.valueOf(getBacklog());
/*     */     }
/*     */     
/*  70 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  75 */     validate(paramChannelOption, paramT);
/*     */     
/*  77 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  78 */       setReceiveBufferSize(((Integer)paramT).intValue());
/*  79 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  80 */       setReuseAddress(((Boolean)paramT).booleanValue());
/*  81 */     } else if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  82 */       setBacklog(((Integer)paramT).intValue());
/*     */     } else {
/*  84 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/*     */     try {
/*  93 */       return this.javaSocket.getReuseAddress();
/*     */     } catch (SocketException localSocketException) {
/*  95 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 102 */       this.javaSocket.setReuseAddress(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 104 */       throw new ChannelException(localSocketException);
/*     */     }
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/*     */     try {
/* 112 */       return this.javaSocket.getReceiveBufferSize();
/*     */     } catch (SocketException localSocketException) {
/* 114 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 121 */       this.javaSocket.setReceiveBufferSize(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 123 */       throw new ChannelException(localSocketException);
/*     */     }
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 130 */     this.javaSocket.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
/* 131 */     return this;
/*     */   }
/*     */   
/*     */   public int getBacklog()
/*     */   {
/* 136 */     return this.backlog;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setBacklog(int paramInt)
/*     */   {
/* 141 */     if (paramInt < 0) {
/* 142 */       throw new IllegalArgumentException("backlog: " + paramInt);
/*     */     }
/* 144 */     this.backlog = paramInt;
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 150 */     super.setConnectTimeoutMillis(paramInt);
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 156 */     super.setMaxMessagesPerRead(paramInt);
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 162 */     super.setWriteSpinCount(paramInt);
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 168 */     super.setAllocator(paramByteBufAllocator);
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 174 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 180 */     super.setAutoRead(paramBoolean);
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 186 */     super.setWriteBufferHighWaterMark(paramInt);
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 192 */     super.setWriteBufferLowWaterMark(paramInt);
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 198 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 199 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\DefaultServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */