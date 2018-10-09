/*     */ package io.netty.channel.udt;
/*     */ 
/*     */ import com.barchart.udt.OptionUDT;
/*     */ import com.barchart.udt.SocketUDT;
/*     */ import com.barchart.udt.nio.ChannelUDT;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
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
/*     */ 
/*     */ public class DefaultUdtChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements UdtChannelConfig
/*     */ {
/*     */   private static final int K = 1024;
/*     */   private static final int M = 1048576;
/*  41 */   private volatile int protocolReceiveBuferSize = 10485760;
/*  42 */   private volatile int protocolSendBuferSize = 10485760;
/*     */   
/*  44 */   private volatile int systemReceiveBufferSize = 1048576;
/*  45 */   private volatile int systemSendBuferSize = 1048576;
/*     */   
/*  47 */   private volatile int allocatorReceiveBufferSize = 131072;
/*  48 */   private volatile int allocatorSendBufferSize = 131072;
/*     */   
/*     */   private volatile int soLinger;
/*     */   
/*  52 */   private volatile boolean reuseAddress = true;
/*     */   
/*     */   public DefaultUdtChannelConfig(UdtChannel paramUdtChannel, ChannelUDT paramChannelUDT, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  57 */     super(paramUdtChannel);
/*  58 */     if (paramBoolean) {
/*  59 */       apply(paramChannelUDT);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void apply(ChannelUDT paramChannelUDT) throws IOException {
/*  64 */     SocketUDT localSocketUDT = paramChannelUDT.socketUDT();
/*  65 */     localSocketUDT.setReuseAddress(isReuseAddress());
/*  66 */     localSocketUDT.setSendBufferSize(getSendBufferSize());
/*  67 */     if (getSoLinger() <= 0) {
/*  68 */       localSocketUDT.setSoLinger(false, 0);
/*     */     } else {
/*  70 */       localSocketUDT.setSoLinger(true, getSoLinger());
/*     */     }
/*  72 */     localSocketUDT.setOption(OptionUDT.Protocol_Receive_Buffer_Size, Integer.valueOf(getProtocolReceiveBufferSize()));
/*     */     
/*  74 */     localSocketUDT.setOption(OptionUDT.Protocol_Send_Buffer_Size, Integer.valueOf(getProtocolSendBufferSize()));
/*     */     
/*  76 */     localSocketUDT.setOption(OptionUDT.System_Receive_Buffer_Size, Integer.valueOf(getSystemReceiveBufferSize()));
/*     */     
/*  78 */     localSocketUDT.setOption(OptionUDT.System_Send_Buffer_Size, Integer.valueOf(getSystemSendBufferSize()));
/*     */   }
/*     */   
/*     */ 
/*     */   public int getProtocolReceiveBufferSize()
/*     */   {
/*  84 */     return this.protocolReceiveBuferSize;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  90 */     if (paramChannelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
/*  91 */       return Integer.valueOf(getProtocolReceiveBufferSize());
/*     */     }
/*  93 */     if (paramChannelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
/*  94 */       return Integer.valueOf(getProtocolSendBufferSize());
/*     */     }
/*  96 */     if (paramChannelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
/*  97 */       return Integer.valueOf(getSystemReceiveBufferSize());
/*     */     }
/*  99 */     if (paramChannelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
/* 100 */       return Integer.valueOf(getSystemSendBufferSize());
/*     */     }
/* 102 */     if (paramChannelOption == UdtChannelOption.SO_RCVBUF) {
/* 103 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/* 105 */     if (paramChannelOption == UdtChannelOption.SO_SNDBUF) {
/* 106 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/* 108 */     if (paramChannelOption == UdtChannelOption.SO_REUSEADDR) {
/* 109 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/* 111 */     if (paramChannelOption == UdtChannelOption.SO_LINGER) {
/* 112 */       return Integer.valueOf(getSoLinger());
/*     */     }
/* 114 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/* 119 */     return getOptions(super.getOptions(), new ChannelOption[] { UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE, UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE, UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE, UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE, UdtChannelOption.SO_RCVBUF, UdtChannelOption.SO_SNDBUF, UdtChannelOption.SO_REUSEADDR, UdtChannelOption.SO_LINGER });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getReceiveBufferSize()
/*     */   {
/* 127 */     return this.allocatorReceiveBufferSize;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/* 132 */     return this.allocatorSendBufferSize;
/*     */   }
/*     */   
/*     */   public int getSoLinger()
/*     */   {
/* 137 */     return this.soLinger;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/* 142 */     return this.reuseAddress;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setProtocolReceiveBufferSize(int paramInt)
/*     */   {
/* 147 */     this.protocolReceiveBuferSize = paramInt;
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 153 */     validate(paramChannelOption, paramT);
/* 154 */     if (paramChannelOption == UdtChannelOption.PROTOCOL_RECEIVE_BUFFER_SIZE) {
/* 155 */       setProtocolReceiveBufferSize(((Integer)paramT).intValue());
/* 156 */     } else if (paramChannelOption == UdtChannelOption.PROTOCOL_SEND_BUFFER_SIZE) {
/* 157 */       setProtocolSendBufferSize(((Integer)paramT).intValue());
/* 158 */     } else if (paramChannelOption == UdtChannelOption.SYSTEM_RECEIVE_BUFFER_SIZE) {
/* 159 */       setSystemReceiveBufferSize(((Integer)paramT).intValue());
/* 160 */     } else if (paramChannelOption == UdtChannelOption.SYSTEM_SEND_BUFFER_SIZE) {
/* 161 */       setSystemSendBufferSize(((Integer)paramT).intValue());
/* 162 */     } else if (paramChannelOption == UdtChannelOption.SO_RCVBUF) {
/* 163 */       setReceiveBufferSize(((Integer)paramT).intValue());
/* 164 */     } else if (paramChannelOption == UdtChannelOption.SO_SNDBUF) {
/* 165 */       setSendBufferSize(((Integer)paramT).intValue());
/* 166 */     } else if (paramChannelOption == UdtChannelOption.SO_REUSEADDR) {
/* 167 */       setReuseAddress(((Boolean)paramT).booleanValue());
/* 168 */     } else if (paramChannelOption == UdtChannelOption.SO_LINGER) {
/* 169 */       setSoLinger(((Integer)paramT).intValue());
/*     */     } else {
/* 171 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 178 */     this.allocatorReceiveBufferSize = paramInt;
/* 179 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 184 */     this.reuseAddress = paramBoolean;
/* 185 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/* 190 */     this.allocatorSendBufferSize = paramInt;
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setSoLinger(int paramInt)
/*     */   {
/* 196 */     this.soLinger = paramInt;
/* 197 */     return this;
/*     */   }
/*     */   
/*     */   public int getSystemReceiveBufferSize()
/*     */   {
/* 202 */     return this.systemReceiveBufferSize;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtChannelConfig setSystemSendBufferSize(int paramInt)
/*     */   {
/* 208 */     this.systemReceiveBufferSize = paramInt;
/* 209 */     return this;
/*     */   }
/*     */   
/*     */   public int getProtocolSendBufferSize()
/*     */   {
/* 214 */     return this.protocolSendBuferSize;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtChannelConfig setProtocolSendBufferSize(int paramInt)
/*     */   {
/* 220 */     this.protocolSendBuferSize = paramInt;
/* 221 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public UdtChannelConfig setSystemReceiveBufferSize(int paramInt)
/*     */   {
/* 227 */     this.systemSendBuferSize = paramInt;
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   public int getSystemSendBufferSize()
/*     */   {
/* 233 */     return this.systemSendBuferSize;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 238 */     super.setConnectTimeoutMillis(paramInt);
/* 239 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 244 */     super.setMaxMessagesPerRead(paramInt);
/* 245 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 250 */     super.setWriteSpinCount(paramInt);
/* 251 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 256 */     super.setAllocator(paramByteBufAllocator);
/* 257 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 262 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 263 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 268 */     super.setAutoRead(paramBoolean);
/* 269 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 274 */     super.setAutoClose(paramBoolean);
/* 275 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 280 */     super.setWriteBufferLowWaterMark(paramInt);
/* 281 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 286 */     super.setWriteBufferHighWaterMark(paramInt);
/* 287 */     return this;
/*     */   }
/*     */   
/*     */   public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 292 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 293 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\udt\DefaultUdtChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */