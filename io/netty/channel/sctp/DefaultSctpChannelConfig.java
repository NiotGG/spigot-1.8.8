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
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public class DefaultSctpChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements SctpChannelConfig
/*     */ {
/*     */   private final com.sun.nio.sctp.SctpChannel javaChannel;
/*     */   
/*     */   public DefaultSctpChannelConfig(SctpChannel paramSctpChannel, com.sun.nio.sctp.SctpChannel paramSctpChannel1)
/*     */   {
/*  42 */     super(paramSctpChannel);
/*  43 */     if (paramSctpChannel1 == null) {
/*  44 */       throw new NullPointerException("javaChannel");
/*     */     }
/*  46 */     this.javaChannel = paramSctpChannel1;
/*     */     
/*     */ 
/*  49 */     if (PlatformDependent.canEnableTcpNoDelayByDefault()) {
/*     */       try {
/*  51 */         setSctpNoDelay(true);
/*     */       }
/*     */       catch (Exception localException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  60 */     return getOptions(super.getOptions(), new ChannelOption[] { SctpChannelOption.SO_RCVBUF, SctpChannelOption.SO_SNDBUF, SctpChannelOption.SCTP_NODELAY, SctpChannelOption.SCTP_INIT_MAXSTREAMS });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  68 */     if (paramChannelOption == SctpChannelOption.SO_RCVBUF) {
/*  69 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  71 */     if (paramChannelOption == SctpChannelOption.SO_SNDBUF) {
/*  72 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/*  74 */     if (paramChannelOption == SctpChannelOption.SCTP_NODELAY) {
/*  75 */       return Boolean.valueOf(isSctpNoDelay());
/*     */     }
/*  77 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  82 */     validate(paramChannelOption, paramT);
/*     */     
/*  84 */     if (paramChannelOption == SctpChannelOption.SO_RCVBUF) {
/*  85 */       setReceiveBufferSize(((Integer)paramT).intValue());
/*  86 */     } else if (paramChannelOption == SctpChannelOption.SO_SNDBUF) {
/*  87 */       setSendBufferSize(((Integer)paramT).intValue());
/*  88 */     } else if (paramChannelOption == SctpChannelOption.SCTP_NODELAY) {
/*  89 */       setSctpNoDelay(((Boolean)paramT).booleanValue());
/*  90 */     } else if (paramChannelOption == SctpChannelOption.SCTP_INIT_MAXSTREAMS) {
/*  91 */       setInitMaxStreams((SctpStandardSocketOptions.InitMaxStreams)paramT);
/*     */     } else {
/*  93 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isSctpNoDelay()
/*     */   {
/*     */     try {
/* 102 */       return ((Boolean)this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_NODELAY)).booleanValue();
/*     */     } catch (IOException localIOException) {
/* 104 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setSctpNoDelay(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 111 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, Boolean.valueOf(paramBoolean));
/*     */     } catch (IOException localIOException) {
/* 113 */       throw new ChannelException(localIOException);
/*     */     }
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/*     */     try {
/* 121 */       return ((Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_SNDBUF)).intValue();
/*     */     } catch (IOException localIOException) {
/* 123 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 130 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, Integer.valueOf(paramInt));
/*     */     } catch (IOException localIOException) {
/* 132 */       throw new ChannelException(localIOException);
/*     */     }
/* 134 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/*     */     try {
/* 140 */       return ((Integer)this.javaChannel.getOption(SctpStandardSocketOptions.SO_RCVBUF)).intValue();
/*     */     } catch (IOException localIOException) {
/* 142 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 149 */       this.javaChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, Integer.valueOf(paramInt));
/*     */     } catch (IOException localIOException) {
/* 151 */       throw new ChannelException(localIOException);
/*     */     }
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   public SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams()
/*     */   {
/*     */     try {
/* 159 */       return (SctpStandardSocketOptions.InitMaxStreams)this.javaChannel.getOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS);
/*     */     } catch (IOException localIOException) {
/* 161 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams paramInitMaxStreams)
/*     */   {
/*     */     try {
/* 168 */       this.javaChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, paramInitMaxStreams);
/*     */     } catch (IOException localIOException) {
/* 170 */       throw new ChannelException(localIOException);
/*     */     }
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 177 */     super.setConnectTimeoutMillis(paramInt);
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 183 */     super.setMaxMessagesPerRead(paramInt);
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 189 */     super.setWriteSpinCount(paramInt);
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 195 */     super.setAllocator(paramByteBufAllocator);
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 201 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 207 */     super.setAutoRead(paramBoolean);
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 213 */     super.setAutoClose(paramBoolean);
/* 214 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 219 */     super.setWriteBufferHighWaterMark(paramInt);
/* 220 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 225 */     super.setWriteBufferLowWaterMark(paramInt);
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 231 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 232 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\DefaultSctpChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */