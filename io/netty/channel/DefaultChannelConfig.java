/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class DefaultChannelConfig
/*     */   implements ChannelConfig
/*     */ {
/*  33 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = AdaptiveRecvByteBufAllocator.DEFAULT;
/*  34 */   private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
/*     */   
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
/*     */   
/*     */   protected final Channel channel;
/*     */   
/*  40 */   private volatile ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
/*  41 */   private volatile RecvByteBufAllocator rcvBufAllocator = DEFAULT_RCVBUF_ALLOCATOR;
/*  42 */   private volatile MessageSizeEstimator msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
/*     */   
/*  44 */   private volatile int connectTimeoutMillis = 30000;
/*     */   private volatile int maxMessagesPerRead;
/*  46 */   private volatile int writeSpinCount = 16;
/*  47 */   private volatile boolean autoRead = true;
/*  48 */   private volatile boolean autoClose = true;
/*  49 */   private volatile int writeBufferHighWaterMark = 65536;
/*  50 */   private volatile int writeBufferLowWaterMark = 32768;
/*     */   
/*     */   public DefaultChannelConfig(Channel paramChannel) {
/*  53 */     if (paramChannel == null) {
/*  54 */       throw new NullPointerException("channel");
/*     */     }
/*  56 */     this.channel = paramChannel;
/*     */     
/*  58 */     if (((paramChannel instanceof ServerChannel)) || ((paramChannel instanceof AbstractNioByteChannel)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  63 */       this.maxMessagesPerRead = 16;
/*     */     } else {
/*  65 */       this.maxMessagesPerRead = 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  72 */     return getOptions(null, new ChannelOption[] { ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> paramMap, ChannelOption<?>... paramVarArgs)
/*     */   {
/*  81 */     if (paramMap == null) {
/*  82 */       paramMap = new IdentityHashMap();
/*     */     }
/*  84 */     for (ChannelOption<?> localChannelOption : paramVarArgs) {
/*  85 */       paramMap.put(localChannelOption, getOption(localChannelOption));
/*     */     }
/*  87 */     return paramMap;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean setOptions(Map<ChannelOption<?>, ?> paramMap)
/*     */   {
/*  93 */     if (paramMap == null) {
/*  94 */       throw new NullPointerException("options");
/*     */     }
/*     */     
/*  97 */     boolean bool = true;
/*  98 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  99 */       if (!setOption((ChannelOption)localEntry.getKey(), localEntry.getValue())) {
/* 100 */         bool = false;
/*     */       }
/*     */     }
/*     */     
/* 104 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/* 110 */     if (paramChannelOption == null) {
/* 111 */       throw new NullPointerException("option");
/*     */     }
/*     */     
/* 114 */     if (paramChannelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 115 */       return Integer.valueOf(getConnectTimeoutMillis());
/*     */     }
/* 117 */     if (paramChannelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 118 */       return Integer.valueOf(getMaxMessagesPerRead());
/*     */     }
/* 120 */     if (paramChannelOption == ChannelOption.WRITE_SPIN_COUNT) {
/* 121 */       return Integer.valueOf(getWriteSpinCount());
/*     */     }
/* 123 */     if (paramChannelOption == ChannelOption.ALLOCATOR) {
/* 124 */       return getAllocator();
/*     */     }
/* 126 */     if (paramChannelOption == ChannelOption.RCVBUF_ALLOCATOR) {
/* 127 */       return getRecvByteBufAllocator();
/*     */     }
/* 129 */     if (paramChannelOption == ChannelOption.AUTO_READ) {
/* 130 */       return Boolean.valueOf(isAutoRead());
/*     */     }
/* 132 */     if (paramChannelOption == ChannelOption.AUTO_CLOSE) {
/* 133 */       return Boolean.valueOf(isAutoClose());
/*     */     }
/* 135 */     if (paramChannelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 136 */       return Integer.valueOf(getWriteBufferHighWaterMark());
/*     */     }
/* 138 */     if (paramChannelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 139 */       return Integer.valueOf(getWriteBufferLowWaterMark());
/*     */     }
/* 141 */     if (paramChannelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 142 */       return getMessageSizeEstimator();
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 150 */     validate(paramChannelOption, paramT);
/*     */     
/* 152 */     if (paramChannelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
/* 153 */       setConnectTimeoutMillis(((Integer)paramT).intValue());
/* 154 */     } else if (paramChannelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
/* 155 */       setMaxMessagesPerRead(((Integer)paramT).intValue());
/* 156 */     } else if (paramChannelOption == ChannelOption.WRITE_SPIN_COUNT) {
/* 157 */       setWriteSpinCount(((Integer)paramT).intValue());
/* 158 */     } else if (paramChannelOption == ChannelOption.ALLOCATOR) {
/* 159 */       setAllocator((ByteBufAllocator)paramT);
/* 160 */     } else if (paramChannelOption == ChannelOption.RCVBUF_ALLOCATOR) {
/* 161 */       setRecvByteBufAllocator((RecvByteBufAllocator)paramT);
/* 162 */     } else if (paramChannelOption == ChannelOption.AUTO_READ) {
/* 163 */       setAutoRead(((Boolean)paramT).booleanValue());
/* 164 */     } else if (paramChannelOption == ChannelOption.AUTO_CLOSE) {
/* 165 */       setAutoClose(((Boolean)paramT).booleanValue());
/* 166 */     } else if (paramChannelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
/* 167 */       setWriteBufferHighWaterMark(((Integer)paramT).intValue());
/* 168 */     } else if (paramChannelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
/* 169 */       setWriteBufferLowWaterMark(((Integer)paramT).intValue());
/* 170 */     } else if (paramChannelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
/* 171 */       setMessageSizeEstimator((MessageSizeEstimator)paramT);
/*     */     } else {
/* 173 */       return false;
/*     */     }
/*     */     
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   protected <T> void validate(ChannelOption<T> paramChannelOption, T paramT) {
/* 180 */     if (paramChannelOption == null) {
/* 181 */       throw new NullPointerException("option");
/*     */     }
/* 183 */     paramChannelOption.validate(paramT);
/*     */   }
/*     */   
/*     */   public int getConnectTimeoutMillis()
/*     */   {
/* 188 */     return this.connectTimeoutMillis;
/*     */   }
/*     */   
/*     */   public ChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 193 */     if (paramInt < 0) {
/* 194 */       throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", new Object[] { Integer.valueOf(paramInt) }));
/*     */     }
/*     */     
/* 197 */     this.connectTimeoutMillis = paramInt;
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public int getMaxMessagesPerRead()
/*     */   {
/* 203 */     return this.maxMessagesPerRead;
/*     */   }
/*     */   
/*     */   public ChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 208 */     if (paramInt <= 0) {
/* 209 */       throw new IllegalArgumentException("maxMessagesPerRead: " + paramInt + " (expected: > 0)");
/*     */     }
/* 211 */     this.maxMessagesPerRead = paramInt;
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public int getWriteSpinCount()
/*     */   {
/* 217 */     return this.writeSpinCount;
/*     */   }
/*     */   
/*     */   public ChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 222 */     if (paramInt <= 0) {
/* 223 */       throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
/*     */     }
/*     */     
/* 226 */     this.writeSpinCount = paramInt;
/* 227 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator getAllocator()
/*     */   {
/* 232 */     return this.allocator;
/*     */   }
/*     */   
/*     */   public ChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 237 */     if (paramByteBufAllocator == null) {
/* 238 */       throw new NullPointerException("allocator");
/*     */     }
/* 240 */     this.allocator = paramByteBufAllocator;
/* 241 */     return this;
/*     */   }
/*     */   
/*     */   public RecvByteBufAllocator getRecvByteBufAllocator()
/*     */   {
/* 246 */     return this.rcvBufAllocator;
/*     */   }
/*     */   
/*     */   public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 251 */     if (paramRecvByteBufAllocator == null) {
/* 252 */       throw new NullPointerException("allocator");
/*     */     }
/* 254 */     this.rcvBufAllocator = paramRecvByteBufAllocator;
/* 255 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isAutoRead()
/*     */   {
/* 260 */     return this.autoRead;
/*     */   }
/*     */   
/*     */   public ChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 265 */     boolean bool = this.autoRead;
/* 266 */     this.autoRead = paramBoolean;
/* 267 */     if ((paramBoolean) && (!bool)) {
/* 268 */       this.channel.read();
/* 269 */     } else if ((!paramBoolean) && (bool)) {
/* 270 */       autoReadCleared();
/*     */     }
/* 272 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void autoReadCleared() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isAutoClose()
/*     */   {
/* 283 */     return this.autoClose;
/*     */   }
/*     */   
/*     */   public ChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 288 */     this.autoClose = paramBoolean;
/* 289 */     return this;
/*     */   }
/*     */   
/*     */   public int getWriteBufferHighWaterMark()
/*     */   {
/* 294 */     return this.writeBufferHighWaterMark;
/*     */   }
/*     */   
/*     */   public ChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 299 */     if (paramInt < getWriteBufferLowWaterMark()) {
/* 300 */       throw new IllegalArgumentException("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (" + getWriteBufferLowWaterMark() + "): " + paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 305 */     if (paramInt < 0) {
/* 306 */       throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
/*     */     }
/*     */     
/* 309 */     this.writeBufferHighWaterMark = paramInt;
/* 310 */     return this;
/*     */   }
/*     */   
/*     */   public int getWriteBufferLowWaterMark()
/*     */   {
/* 315 */     return this.writeBufferLowWaterMark;
/*     */   }
/*     */   
/*     */   public ChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 320 */     if (paramInt > getWriteBufferHighWaterMark()) {
/* 321 */       throw new IllegalArgumentException("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (" + getWriteBufferHighWaterMark() + "): " + paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 326 */     if (paramInt < 0) {
/* 327 */       throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
/*     */     }
/*     */     
/* 330 */     this.writeBufferLowWaterMark = paramInt;
/* 331 */     return this;
/*     */   }
/*     */   
/*     */   public MessageSizeEstimator getMessageSizeEstimator()
/*     */   {
/* 336 */     return this.msgSizeEstimator;
/*     */   }
/*     */   
/*     */   public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 341 */     if (paramMessageSizeEstimator == null) {
/* 342 */       throw new NullPointerException("estimator");
/*     */     }
/* 344 */     this.msgSizeEstimator = paramMessageSizeEstimator;
/* 345 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */