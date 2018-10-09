/*     */ package io.netty.channel.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*     */ import com.sun.nio.sctp.SctpStandardSocketOptions.InitMaxStreams;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.NetUtil;
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
/*     */ public class DefaultSctpServerChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SctpServerChannelConfig
/*     */ {
/*     */   private final com.sun.nio.sctp.SctpServerChannel javaChannel;
/*  38 */   private volatile int backlog = NetUtil.SOMAXCONN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultSctpServerChannelConfig(SctpServerChannel paramSctpServerChannel, com.sun.nio.sctp.SctpServerChannel paramSctpServerChannel1)
/*     */   {
/*  45 */     super(paramSctpServerChannel);
/*  46 */     if (paramSctpServerChannel1 == null) {
/*  47 */       throw new NullPointerException("javaChannel");
/*     */     }
/*  49 */     this.javaChannel = paramSctpServerChannel1;
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  54 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_INIT_MAXSTREAMS });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  62 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  63 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  65 */     if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  66 */       return Integer.valueOf(getSendBufferSize());
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
/*  77 */     } else if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  78 */       setSendBufferSize(((Integer)paramT).intValue());
/*  79 */     } else if (paramChannelOption == SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS) {
/*  80 */       setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)paramT);
/*     */     } else {
/*  82 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/*  85 */     return true;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/*     */     try {
/*  91 */       return ((Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF)).intValue();
/*     */     } catch (IOException localIOException) {
/*  93 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 100 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, Integer.valueOf(paramInt));
/*     */     } catch (IOException localIOException) {
/* 102 */       throw new ChannelException(localIOException);
/*     */     }
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/*     */     try {
/* 110 */       return ((Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF)).intValue();
/*     */     } catch (IOException localIOException) {
/* 112 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 119 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, Integer.valueOf(paramInt));
/*     */     } catch (IOException localIOException) {
/* 121 */       throw new ChannelException(localIOException);
/*     */     }
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams()
/*     */   {
/*     */     try {
/* 129 */       return (SctpStandardSocketOptions.InitMaxStreams)this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
/*     */     } catch (IOException localIOException) {
/* 131 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams paramInitMaxStreams)
/*     */   {
/*     */     try {
/* 138 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, paramInitMaxStreams);
/*     */     } catch (IOException localIOException) {
/* 140 */       throw new ChannelException(localIOException);
/*     */     }
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public int getBacklog()
/*     */   {
/* 147 */     return this.backlog;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setBacklog(int paramInt)
/*     */   {
/* 152 */     if (paramInt < 0) {
/* 153 */       throw new IllegalArgumentException("backlog: " + paramInt);
/*     */     }
/* 155 */     this.backlog = paramInt;
/* 156 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 161 */     super.setMaxMessagesPerRead(paramInt);
/* 162 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 167 */     super.setWriteSpinCount(paramInt);
/* 168 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 173 */     super.setConnectTimeoutMillis(paramInt);
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 179 */     super.setAllocator(paramByteBufAllocator);
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 185 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 186 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 191 */     super.setAutoRead(paramBoolean);
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 197 */     super.setAutoClose(paramBoolean);
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 203 */     super.setWriteBufferLowWaterMark(paramInt);
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 209 */     super.setWriteBufferHighWaterMark(paramInt);
/* 210 */     return this;
/*     */   }
/*     */   
/*     */   public SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 215 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 216 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\DefaultSctpServerChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */