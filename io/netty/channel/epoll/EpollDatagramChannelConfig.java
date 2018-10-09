/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
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
/*     */ public final class EpollDatagramChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*  31 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
/*     */   private final EpollDatagramChannel datagramChannel;
/*     */   private boolean activeOnOpen;
/*     */   
/*     */   EpollDatagramChannelConfig(EpollDatagramChannel paramEpollDatagramChannel) {
/*  36 */     super(paramEpollDatagramChannel);
/*  37 */     this.datagramChannel = paramEpollDatagramChannel;
/*  38 */     setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  44 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, EpollChannelOption.SO_REUSEPORT });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  56 */     if (paramChannelOption == ChannelOption.SO_BROADCAST) {
/*  57 */       return Boolean.valueOf(isBroadcast());
/*     */     }
/*  59 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  60 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  62 */     if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  63 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/*  65 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  66 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  68 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  69 */       return Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  71 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_ADDR) {
/*  72 */       return getInterface();
/*     */     }
/*  74 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_IF) {
/*  75 */       return getNetworkInterface();
/*     */     }
/*  77 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_TTL) {
/*  78 */       return Integer.valueOf(getTimeToLive());
/*     */     }
/*  80 */     if (paramChannelOption == ChannelOption.IP_TOS) {
/*  81 */       return Integer.valueOf(getTrafficClass());
/*     */     }
/*  83 */     if (paramChannelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/*  84 */       return Boolean.valueOf(this.activeOnOpen);
/*     */     }
/*  86 */     if (paramChannelOption == EpollChannelOption.SO_REUSEPORT) {
/*  87 */       return Boolean.valueOf(isReusePort());
/*     */     }
/*  89 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/*  95 */     validate(paramChannelOption, paramT);
/*     */     
/*  97 */     if (paramChannelOption == ChannelOption.SO_BROADCAST) {
/*  98 */       setBroadcast(((Boolean)paramT).booleanValue());
/*  99 */     } else if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/* 100 */       setReceiveBufferSize(((Integer)paramT).intValue());
/* 101 */     } else if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/* 102 */       setSendBufferSize(((Integer)paramT).intValue());
/* 103 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/* 104 */       setReuseAddress(((Boolean)paramT).booleanValue());
/* 105 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 106 */       setLoopbackModeDisabled(((Boolean)paramT).booleanValue());
/* 107 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_ADDR) {
/* 108 */       setInterface((InetAddress)paramT);
/* 109 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_IF) {
/* 110 */       setNetworkInterface((NetworkInterface)paramT);
/* 111 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_TTL) {
/* 112 */       setTimeToLive(((Integer)paramT).intValue());
/* 113 */     } else if (paramChannelOption == ChannelOption.IP_TOS) {
/* 114 */       setTrafficClass(((Integer)paramT).intValue());
/* 115 */     } else if (paramChannelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 116 */       setActiveOnOpen(((Boolean)paramT).booleanValue());
/* 117 */     } else if (paramChannelOption == EpollChannelOption.SO_REUSEPORT) {
/* 118 */       setReusePort(((Boolean)paramT).booleanValue());
/*     */     } else {
/* 120 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/* 123 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean paramBoolean) {
/* 127 */     if (this.channel.isRegistered()) {
/* 128 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 130 */     this.activeOnOpen = paramBoolean;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 135 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 141 */     super.setWriteBufferLowWaterMark(paramInt);
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 147 */     super.setWriteBufferHighWaterMark(paramInt);
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 153 */     super.setAutoClose(paramBoolean);
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 159 */     super.setAutoRead(paramBoolean);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 165 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 171 */     super.setWriteSpinCount(paramInt);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 177 */     super.setAllocator(paramByteBufAllocator);
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 183 */     super.setConnectTimeoutMillis(paramInt);
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 189 */     super.setMaxMessagesPerRead(paramInt);
/* 190 */     return this;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/* 195 */     return Native.getSendBufferSize(this.datagramChannel.fd);
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/* 200 */     Native.setSendBufferSize(this.datagramChannel.fd, paramInt);
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/* 206 */     return Native.getReceiveBufferSize(this.datagramChannel.fd);
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/* 211 */     Native.setReceiveBufferSize(this.datagramChannel.fd, paramInt);
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public int getTrafficClass()
/*     */   {
/* 217 */     return Native.getTrafficClass(this.datagramChannel.fd);
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setTrafficClass(int paramInt)
/*     */   {
/* 222 */     Native.setTrafficClass(this.datagramChannel.fd, paramInt);
/* 223 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/* 228 */     return Native.isReuseAddress(this.datagramChannel.fd) == 1;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/* 233 */     Native.setReuseAddress(this.datagramChannel.fd, paramBoolean ? 1 : 0);
/* 234 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isBroadcast()
/*     */   {
/* 239 */     return Native.isBroadcast(this.datagramChannel.fd) == 1;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setBroadcast(boolean paramBoolean)
/*     */   {
/* 244 */     Native.setBroadcast(this.datagramChannel.fd, paramBoolean ? 1 : 0);
/* 245 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isLoopbackModeDisabled()
/*     */   {
/* 250 */     return false;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean paramBoolean)
/*     */   {
/* 255 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */   
/*     */   public int getTimeToLive()
/*     */   {
/* 260 */     return -1;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setTimeToLive(int paramInt)
/*     */   {
/* 265 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */   
/*     */   public InetAddress getInterface()
/*     */   {
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setInterface(InetAddress paramInetAddress)
/*     */   {
/* 275 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */   
/*     */   public NetworkInterface getNetworkInterface()
/*     */   {
/* 280 */     return null;
/*     */   }
/*     */   
/*     */   public EpollDatagramChannelConfig setNetworkInterface(NetworkInterface paramNetworkInterface)
/*     */   {
/* 285 */     throw new UnsupportedOperationException("Multicast not supported");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isReusePort()
/*     */   {
/* 292 */     return Native.isReusePort(this.datagramChannel.fd) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EpollDatagramChannelConfig setReusePort(boolean paramBoolean)
/*     */   {
/* 303 */     Native.setReusePort(this.datagramChannel.fd, paramBoolean ? 1 : 0);
/* 304 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 309 */     this.datagramChannel.clearEpollIn();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollDatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */