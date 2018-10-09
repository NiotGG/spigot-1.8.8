/*     */ package io.netty.channel.rxtx;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ 
/*     */ public abstract interface RxtxChannelConfig extends io.netty.channel.ChannelConfig { public abstract RxtxChannelConfig setBaudrate(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setStopbits(Stopbits paramStopbits);
/*     */   
/*     */   public abstract RxtxChannelConfig setDatabits(Databits paramDatabits);
/*     */   
/*     */   public abstract RxtxChannelConfig setParitybit(Paritybit paramParitybit);
/*     */   
/*     */   public abstract int getBaudrate();
/*     */   
/*     */   public abstract Stopbits getStopbits();
/*     */   
/*     */   public abstract Databits getDatabits();
/*     */   
/*     */   public abstract Paritybit getParitybit();
/*     */   
/*     */   public abstract boolean isDtr();
/*     */   
/*     */   public abstract RxtxChannelConfig setDtr(boolean paramBoolean);
/*     */   
/*     */   public abstract boolean isRts();
/*     */   
/*     */   public abstract RxtxChannelConfig setRts(boolean paramBoolean);
/*     */   
/*     */   public abstract int getWaitTimeMillis();
/*     */   
/*     */   public abstract RxtxChannelConfig setWaitTimeMillis(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setReadTimeout(int paramInt);
/*     */   
/*     */   public abstract int getReadTimeout();
/*     */   
/*     */   public abstract RxtxChannelConfig setConnectTimeoutMillis(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setMaxMessagesPerRead(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setWriteSpinCount(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
/*     */   
/*     */   public abstract RxtxChannelConfig setRecvByteBufAllocator(io.netty.channel.RecvByteBufAllocator paramRecvByteBufAllocator);
/*     */   
/*     */   public abstract RxtxChannelConfig setAutoRead(boolean paramBoolean);
/*     */   
/*     */   public abstract RxtxChannelConfig setAutoClose(boolean paramBoolean);
/*     */   
/*     */   public abstract RxtxChannelConfig setWriteBufferHighWaterMark(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setWriteBufferLowWaterMark(int paramInt);
/*     */   
/*     */   public abstract RxtxChannelConfig setMessageSizeEstimator(io.netty.channel.MessageSizeEstimator paramMessageSizeEstimator);
/*     */   
/*  57 */   public static enum Stopbits { STOPBITS_1(1), 
/*     */     
/*     */ 
/*     */ 
/*  61 */     STOPBITS_2(2), 
/*     */     
/*     */ 
/*     */ 
/*  65 */     STOPBITS_1_5(3);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     private Stopbits(int paramInt) {
/*  70 */       this.value = paramInt;
/*     */     }
/*     */     
/*     */     public int value() {
/*  74 */       return this.value;
/*     */     }
/*     */     
/*     */     public static Stopbits valueOf(int paramInt) {
/*  78 */       for (Stopbits localStopbits : ) {
/*  79 */         if (localStopbits.value == paramInt) {
/*  80 */           return localStopbits;
/*     */         }
/*     */       }
/*  83 */       throw new IllegalArgumentException("unknown " + Stopbits.class.getSimpleName() + " value: " + paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum Databits
/*     */   {
/*  91 */     DATABITS_5(5), 
/*     */     
/*     */ 
/*     */ 
/*  95 */     DATABITS_6(6), 
/*     */     
/*     */ 
/*     */ 
/*  99 */     DATABITS_7(7), 
/*     */     
/*     */ 
/*     */ 
/* 103 */     DATABITS_8(8);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     private Databits(int paramInt) {
/* 108 */       this.value = paramInt;
/*     */     }
/*     */     
/*     */     public int value() {
/* 112 */       return this.value;
/*     */     }
/*     */     
/*     */     public static Databits valueOf(int paramInt) {
/* 116 */       for (Databits localDatabits : ) {
/* 117 */         if (localDatabits.value == paramInt) {
/* 118 */           return localDatabits;
/*     */         }
/*     */       }
/* 121 */       throw new IllegalArgumentException("unknown " + Databits.class.getSimpleName() + " value: " + paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum Paritybit
/*     */   {
/* 129 */     NONE(0), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 134 */     ODD(1), 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 139 */     EVEN(2), 
/*     */     
/*     */ 
/*     */ 
/* 143 */     MARK(3), 
/*     */     
/*     */ 
/*     */ 
/* 147 */     SPACE(4);
/*     */     
/*     */     private final int value;
/*     */     
/*     */     private Paritybit(int paramInt) {
/* 152 */       this.value = paramInt;
/*     */     }
/*     */     
/*     */     public int value() {
/* 156 */       return this.value;
/*     */     }
/*     */     
/*     */     public static Paritybit valueOf(int paramInt) {
/* 160 */       for (Paritybit localParitybit : ) {
/* 161 */         if (localParitybit.value == paramInt) {
/* 162 */           return localParitybit;
/*     */         }
/*     */       }
/* 165 */       throw new IllegalArgumentException("unknown " + Paritybit.class.getSimpleName() + " value: " + paramInt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\rxtx\RxtxChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */