/*     */ package io.netty.channel.udt;
/*     */ 
/*     */ import com.barchart.udt.nio.ChannelUDT;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import java.io.IOException;
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
/*     */ public class DefaultUdtServerChannelConfig
/*     */   extends DefaultUdtChannelConfig
/*     */   implements UdtServerChannelConfig
/*     */ {
/*  35 */   private volatile int backlog = 64;
/*     */   
/*     */   public DefaultUdtServerChannelConfig(UdtChannel paramUdtChannel, ChannelUDT paramChannelUDT, boolean paramBoolean) throws IOException
/*     */   {
/*  39 */     super(paramUdtChannel, paramChannelUDT, paramBoolean);
/*  40 */     if (paramBoolean) {
/*  41 */       apply(paramChannelUDT);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void apply(ChannelUDT paramChannelUDT)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */   public int getBacklog()
/*     */   {
/*  52 */     return this.backlog;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  58 */     if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  59 */       return Integer.valueOf(getBacklog());
/*     */     }
/*  61 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  66 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_BACKLOG });
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setBacklog(int paramInt)
/*     */   {
/*  71 */     this.backlog = paramInt;
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  77 */     validate(paramChannelOption, paramT);
/*  78 */     if (paramChannelOption == ChannelOption.SO_BACKLOG) {
/*  79 */       setBacklog(((Integer)paramT).intValue());
/*     */     } else {
/*  81 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*  83 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtServerChannelConfig setProtocolReceiveBufferSize(int paramInt)
/*     */   {
/*  89 */     super.setProtocolReceiveBufferSize(paramInt);
/*  90 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtServerChannelConfig setProtocolSendBufferSize(int paramInt)
/*     */   {
/*  96 */     super.setProtocolSendBufferSize(paramInt);
/*  97 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtServerChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 103 */     super.setReceiveBufferSize(paramInt);
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 109 */     super.setReuseAddress(paramBoolean);
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/* 115 */     super.setSendBufferSize(paramInt);
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setSoLinger(int paramInt)
/*     */   {
/* 121 */     super.setSoLinger(paramInt);
/* 122 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtServerChannelConfig setSystemReceiveBufferSize(int paramInt)
/*     */   {
/* 128 */     super.setSystemReceiveBufferSize(paramInt);
/* 129 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtServerChannelConfig setSystemSendBufferSize(int paramInt)
/*     */   {
/* 135 */     super.setSystemSendBufferSize(paramInt);
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 141 */     super.setConnectTimeoutMillis(paramInt);
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 147 */     super.setMaxMessagesPerRead(paramInt);
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 153 */     super.setWriteSpinCount(paramInt);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 159 */     super.setAllocator(paramByteBufAllocator);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 165 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 171 */     super.setAutoRead(paramBoolean);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 177 */     super.setAutoClose(paramBoolean);
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 183 */     super.setWriteBufferLowWaterMark(paramInt);
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 189 */     super.setWriteBufferHighWaterMark(paramInt);
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 195 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 196 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\DefaultUdtServerChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */