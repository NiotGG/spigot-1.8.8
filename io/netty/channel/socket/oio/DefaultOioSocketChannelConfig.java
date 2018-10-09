/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketChannel;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
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
/*     */ public class DefaultOioSocketChannelConfig
/*     */   extends DefaultSocketChannelConfig
/*     */   implements OioSocketChannelConfig
/*     */ {
/*     */   @Deprecated
/*     */   public DefaultOioSocketChannelConfig(SocketChannel paramSocketChannel, Socket paramSocket)
/*     */   {
/*  38 */     super(paramSocketChannel, paramSocket);
/*     */   }
/*     */   
/*     */   DefaultOioSocketChannelConfig(OioSocketChannel paramOioSocketChannel, Socket paramSocket) {
/*  42 */     super(paramOioSocketChannel, paramSocket);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  47 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_TIMEOUT });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  54 */     if (paramChannelOption == ChannelOption.SO_TIMEOUT) {
/*  55 */       return Integer.valueOf(getSoTimeout());
/*     */     }
/*  57 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  62 */     validate(paramChannelOption, paramT);
/*     */     
/*  64 */     if (paramChannelOption == ChannelOption.SO_TIMEOUT) {
/*  65 */       setSoTimeout(((Integer)paramT).intValue());
/*     */     } else {
/*  67 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setSoTimeout(int paramInt)
/*     */   {
/*     */     try {
/*  75 */       this.javaSocket.setSoTimeout(paramInt);
/*     */     } catch (IOException localIOException) {
/*  77 */       throw new ChannelException(localIOException);
/*     */     }
/*  79 */     return this;
/*     */   }
/*     */   
/*     */   public int getSoTimeout()
/*     */   {
/*     */     try {
/*  85 */       return this.javaSocket.getSoTimeout();
/*     */     } catch (IOException localIOException) {
/*  87 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setTcpNoDelay(boolean paramBoolean)
/*     */   {
/*  93 */     super.setTcpNoDelay(paramBoolean);
/*  94 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setSoLinger(int paramInt)
/*     */   {
/*  99 */     super.setSoLinger(paramInt);
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/* 105 */     super.setSendBufferSize(paramInt);
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 111 */     super.setReceiveBufferSize(paramInt);
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setKeepAlive(boolean paramBoolean)
/*     */   {
/* 117 */     super.setKeepAlive(paramBoolean);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setTrafficClass(int paramInt)
/*     */   {
/* 123 */     super.setTrafficClass(paramInt);
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 129 */     super.setReuseAddress(paramBoolean);
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 135 */     super.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setAllowHalfClosure(boolean paramBoolean)
/*     */   {
/* 141 */     super.setAllowHalfClosure(paramBoolean);
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 147 */     super.setConnectTimeoutMillis(paramInt);
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 153 */     super.setMaxMessagesPerRead(paramInt);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 159 */     super.setWriteSpinCount(paramInt);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 165 */     super.setAllocator(paramByteBufAllocator);
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 171 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 177 */     super.setAutoRead(paramBoolean);
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 183 */     if ((this.channel instanceof OioSocketChannel)) {
/* 184 */       ((OioSocketChannel)this.channel).setReadPending(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 190 */     super.setAutoClose(paramBoolean);
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 196 */     super.setWriteBufferHighWaterMark(paramInt);
/* 197 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 202 */     super.setWriteBufferLowWaterMark(paramInt);
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   public OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 208 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 209 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\oio\DefaultOioSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */