/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.DefaultServerSocketChannelConfig;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
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
/*     */ public class DefaultOioServerSocketChannelConfig
/*     */   extends DefaultServerSocketChannelConfig
/*     */   implements OioServerSocketChannelConfig
/*     */ {
/*     */   @Deprecated
/*     */   public DefaultOioServerSocketChannelConfig(ServerSocketChannel paramServerSocketChannel, ServerSocket paramServerSocket)
/*     */   {
/*  40 */     super(paramServerSocketChannel, paramServerSocket);
/*     */   }
/*     */   
/*     */   DefaultOioServerSocketChannelConfig(OioServerSocketChannel paramOioServerSocketChannel, ServerSocket paramServerSocket) {
/*  44 */     super(paramOioServerSocketChannel, paramServerSocket);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  49 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_TIMEOUT });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  56 */     if (paramChannelOption == ChannelOption.SO_TIMEOUT) {
/*  57 */       return Integer.valueOf(getSoTimeout());
/*     */     }
/*  59 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  64 */     validate(paramChannelOption, paramT);
/*     */     
/*  66 */     if (paramChannelOption == ChannelOption.SO_TIMEOUT) {
/*  67 */       setSoTimeout(((Integer)paramT).intValue());
/*     */     } else {
/*  69 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*  71 */     return true;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setSoTimeout(int paramInt)
/*     */   {
/*     */     try {
/*  77 */       this.javaSocket.setSoTimeout(paramInt);
/*     */     } catch (IOException localIOException) {
/*  79 */       throw new ChannelException(localIOException);
/*     */     }
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public int getSoTimeout()
/*     */   {
/*     */     try {
/*  87 */       return this.javaSocket.getSoTimeout();
/*     */     } catch (IOException localIOException) {
/*  89 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setBacklog(int paramInt)
/*     */   {
/*  95 */     super.setBacklog(paramInt);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 101 */     super.setReuseAddress(paramBoolean);
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 107 */     super.setReceiveBufferSize(paramInt);
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 113 */     super.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 119 */     super.setConnectTimeoutMillis(paramInt);
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 125 */     super.setMaxMessagesPerRead(paramInt);
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 131 */     super.setWriteSpinCount(paramInt);
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 137 */     super.setAllocator(paramByteBufAllocator);
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 143 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 144 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 149 */     super.setAutoRead(paramBoolean);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 155 */     if ((this.channel instanceof OioServerSocketChannel)) {
/* 156 */       ((OioServerSocketChannel)this.channel).setReadPending(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 162 */     super.setAutoClose(paramBoolean);
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 168 */     super.setWriteBufferHighWaterMark(paramInt);
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 174 */     super.setWriteBufferLowWaterMark(paramInt);
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 180 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 181 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\oio\DefaultOioServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */