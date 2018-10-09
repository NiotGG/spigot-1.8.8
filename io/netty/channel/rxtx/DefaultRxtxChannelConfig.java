/*     */ package io.netty.channel.rxtx;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
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
/*     */ final class DefaultRxtxChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements RxtxChannelConfig
/*     */ {
/*  33 */   private volatile int baudrate = 115200;
/*     */   private volatile boolean dtr;
/*     */   private volatile boolean rts;
/*  36 */   private volatile RxtxChannelConfig.Stopbits stopbits = RxtxChannelConfig.Stopbits.STOPBITS_1;
/*  37 */   private volatile RxtxChannelConfig.Databits databits = RxtxChannelConfig.Databits.DATABITS_8;
/*  38 */   private volatile RxtxChannelConfig.Paritybit paritybit = RxtxChannelConfig.Paritybit.NONE;
/*     */   private volatile int waitTime;
/*  40 */   private volatile int readTimeout = 1000;
/*     */   
/*     */   DefaultRxtxChannelConfig(RxtxChannel paramRxtxChannel) {
/*  43 */     super(paramRxtxChannel);
/*     */   }
/*     */   
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  48 */     return getOptions(super.getOptions(), new ChannelOption[] { RxtxChannelOption.BAUD_RATE, RxtxChannelOption.DTR, RxtxChannelOption.RTS, RxtxChannelOption.STOP_BITS, RxtxChannelOption.DATA_BITS, RxtxChannelOption.PARITY_BIT, RxtxChannelOption.WAIT_TIME });
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  54 */     if (paramChannelOption == RxtxChannelOption.BAUD_RATE) {
/*  55 */       return Integer.valueOf(getBaudrate());
/*     */     }
/*  57 */     if (paramChannelOption == RxtxChannelOption.DTR) {
/*  58 */       return Boolean.valueOf(isDtr());
/*     */     }
/*  60 */     if (paramChannelOption == RxtxChannelOption.RTS) {
/*  61 */       return Boolean.valueOf(isRts());
/*     */     }
/*  63 */     if (paramChannelOption == RxtxChannelOption.STOP_BITS) {
/*  64 */       return getStopbits();
/*     */     }
/*  66 */     if (paramChannelOption == RxtxChannelOption.DATA_BITS) {
/*  67 */       return getDatabits();
/*     */     }
/*  69 */     if (paramChannelOption == RxtxChannelOption.PARITY_BIT) {
/*  70 */       return getParitybit();
/*     */     }
/*  72 */     if (paramChannelOption == RxtxChannelOption.WAIT_TIME) {
/*  73 */       return Integer.valueOf(getWaitTimeMillis());
/*     */     }
/*  75 */     if (paramChannelOption == RxtxChannelOption.READ_TIMEOUT) {
/*  76 */       return Integer.valueOf(getReadTimeout());
/*     */     }
/*  78 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  83 */     validate(paramChannelOption, paramT);
/*     */     
/*  85 */     if (paramChannelOption == RxtxChannelOption.BAUD_RATE) {
/*  86 */       setBaudrate(((Integer)paramT).intValue());
/*  87 */     } else if (paramChannelOption == RxtxChannelOption.DTR) {
/*  88 */       setDtr(((Boolean)paramT).booleanValue());
/*  89 */     } else if (paramChannelOption == RxtxChannelOption.RTS) {
/*  90 */       setRts(((Boolean)paramT).booleanValue());
/*  91 */     } else if (paramChannelOption == RxtxChannelOption.STOP_BITS) {
/*  92 */       setStopbits((RxtxChannelConfig.Stopbits)paramT);
/*  93 */     } else if (paramChannelOption == RxtxChannelOption.DATA_BITS) {
/*  94 */       setDatabits((RxtxChannelConfig.Databits)paramT);
/*  95 */     } else if (paramChannelOption == RxtxChannelOption.PARITY_BIT) {
/*  96 */       setParitybit((RxtxChannelConfig.Paritybit)paramT);
/*  97 */     } else if (paramChannelOption == RxtxChannelOption.WAIT_TIME) {
/*  98 */       setWaitTimeMillis(((Integer)paramT).intValue());
/*  99 */     } else if (paramChannelOption == RxtxChannelOption.READ_TIMEOUT) {
/* 100 */       setReadTimeout(((Integer)paramT).intValue());
/*     */     } else {
/* 102 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setBaudrate(int paramInt)
/*     */   {
/* 109 */     this.baudrate = paramInt;
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setStopbits(RxtxChannelConfig.Stopbits paramStopbits)
/*     */   {
/* 115 */     this.stopbits = paramStopbits;
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setDatabits(RxtxChannelConfig.Databits paramDatabits)
/*     */   {
/* 121 */     this.databits = paramDatabits;
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setParitybit(RxtxChannelConfig.Paritybit paramParitybit)
/*     */   {
/* 127 */     this.paritybit = paramParitybit;
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public int getBaudrate()
/*     */   {
/* 133 */     return this.baudrate;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig.Stopbits getStopbits()
/*     */   {
/* 138 */     return this.stopbits;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig.Databits getDatabits()
/*     */   {
/* 143 */     return this.databits;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig.Paritybit getParitybit()
/*     */   {
/* 148 */     return this.paritybit;
/*     */   }
/*     */   
/*     */   public boolean isDtr()
/*     */   {
/* 153 */     return this.dtr;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setDtr(boolean paramBoolean)
/*     */   {
/* 158 */     this.dtr = paramBoolean;
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isRts()
/*     */   {
/* 164 */     return this.rts;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setRts(boolean paramBoolean)
/*     */   {
/* 169 */     this.rts = paramBoolean;
/* 170 */     return this;
/*     */   }
/*     */   
/*     */   public int getWaitTimeMillis()
/*     */   {
/* 175 */     return this.waitTime;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setWaitTimeMillis(int paramInt)
/*     */   {
/* 180 */     if (paramInt < 0) {
/* 181 */       throw new IllegalArgumentException("Wait time must be >= 0");
/*     */     }
/* 183 */     this.waitTime = paramInt;
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setReadTimeout(int paramInt)
/*     */   {
/* 189 */     if (paramInt < 0) {
/* 190 */       throw new IllegalArgumentException("readTime must be >= 0");
/*     */     }
/* 192 */     this.readTimeout = paramInt;
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public int getReadTimeout()
/*     */   {
/* 198 */     return this.readTimeout;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 203 */     super.setConnectTimeoutMillis(paramInt);
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 209 */     super.setMaxMessagesPerRead(paramInt);
/* 210 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 215 */     super.setWriteSpinCount(paramInt);
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 221 */     super.setAllocator(paramByteBufAllocator);
/* 222 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 227 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 228 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 233 */     super.setAutoRead(paramBoolean);
/* 234 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 239 */     super.setAutoClose(paramBoolean);
/* 240 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 245 */     super.setWriteBufferHighWaterMark(paramInt);
/* 246 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 251 */     super.setWriteBufferLowWaterMark(paramInt);
/* 252 */     return this;
/*     */   }
/*     */   
/*     */   public RxtxChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 257 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 258 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\rxtx\DefaultRxtxChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */