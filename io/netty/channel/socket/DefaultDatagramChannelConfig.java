/*     */ package io.netty.channel.socket;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.FixedRecvByteBufAllocator;
/*     */ import io.netty.channel.MessageSizeEstimator;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
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
/*     */ public class DefaultDatagramChannelConfig
/*     */   extends DefaultChannelConfig
/*     */   implements DatagramChannelConfig
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultDatagramChannelConfig.class);
/*     */   
/*  46 */   private static final RecvByteBufAllocator DEFAULT_RCVBUF_ALLOCATOR = new FixedRecvByteBufAllocator(2048);
/*     */   
/*     */   private final DatagramSocket javaSocket;
/*     */   
/*     */   private volatile boolean activeOnOpen;
/*     */   
/*     */ 
/*     */   public DefaultDatagramChannelConfig(DatagramChannel paramDatagramChannel, DatagramSocket paramDatagramSocket)
/*     */   {
/*  55 */     super(paramDatagramChannel);
/*  56 */     if (paramDatagramSocket == null) {
/*  57 */       throw new NullPointerException("javaSocket");
/*     */     }
/*  59 */     this.javaSocket = paramDatagramSocket;
/*  60 */     setRecvByteBufAllocator(DEFAULT_RCVBUF_ALLOCATOR);
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<ChannelOption<?>, Object> getOptions()
/*     */   {
/*  66 */     return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_BROADCAST, ChannelOption.SO_RCVBUF, ChannelOption.SO_SNDBUF, ChannelOption.SO_REUSEADDR, ChannelOption.IP_MULTICAST_LOOP_DISABLED, ChannelOption.IP_MULTICAST_ADDR, ChannelOption.IP_MULTICAST_IF, ChannelOption.IP_MULTICAST_TTL, ChannelOption.IP_TOS, ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T getOption(ChannelOption<T> paramChannelOption)
/*     */   {
/*  75 */     if (paramChannelOption == ChannelOption.SO_BROADCAST) {
/*  76 */       return Boolean.valueOf(isBroadcast());
/*     */     }
/*  78 */     if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/*  79 */       return Integer.valueOf(getReceiveBufferSize());
/*     */     }
/*  81 */     if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/*  82 */       return Integer.valueOf(getSendBufferSize());
/*     */     }
/*  84 */     if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/*  85 */       return Boolean.valueOf(isReuseAddress());
/*     */     }
/*  87 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/*  88 */       return Boolean.valueOf(isLoopbackModeDisabled());
/*     */     }
/*  90 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_ADDR) {
/*  91 */       return getInterface();
/*     */     }
/*  93 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_IF) {
/*  94 */       return getNetworkInterface();
/*     */     }
/*  96 */     if (paramChannelOption == ChannelOption.IP_MULTICAST_TTL) {
/*  97 */       return Integer.valueOf(getTimeToLive());
/*     */     }
/*  99 */     if (paramChannelOption == ChannelOption.IP_TOS) {
/* 100 */       return Integer.valueOf(getTrafficClass());
/*     */     }
/* 102 */     if (paramChannelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 103 */       return Boolean.valueOf(this.activeOnOpen);
/*     */     }
/* 105 */     return (T)super.getOption(paramChannelOption);
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> boolean setOption(ChannelOption<T> paramChannelOption, T paramT)
/*     */   {
/* 111 */     validate(paramChannelOption, paramT);
/*     */     
/* 113 */     if (paramChannelOption == ChannelOption.SO_BROADCAST) {
/* 114 */       setBroadcast(((Boolean)paramT).booleanValue());
/* 115 */     } else if (paramChannelOption == ChannelOption.SO_RCVBUF) {
/* 116 */       setReceiveBufferSize(((Integer)paramT).intValue());
/* 117 */     } else if (paramChannelOption == ChannelOption.SO_SNDBUF) {
/* 118 */       setSendBufferSize(((Integer)paramT).intValue());
/* 119 */     } else if (paramChannelOption == ChannelOption.SO_REUSEADDR) {
/* 120 */       setReuseAddress(((Boolean)paramT).booleanValue());
/* 121 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_LOOP_DISABLED) {
/* 122 */       setLoopbackModeDisabled(((Boolean)paramT).booleanValue());
/* 123 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_ADDR) {
/* 124 */       setInterface((InetAddress)paramT);
/* 125 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_IF) {
/* 126 */       setNetworkInterface((NetworkInterface)paramT);
/* 127 */     } else if (paramChannelOption == ChannelOption.IP_MULTICAST_TTL) {
/* 128 */       setTimeToLive(((Integer)paramT).intValue());
/* 129 */     } else if (paramChannelOption == ChannelOption.IP_TOS) {
/* 130 */       setTrafficClass(((Integer)paramT).intValue());
/* 131 */     } else if (paramChannelOption == ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) {
/* 132 */       setActiveOnOpen(((Boolean)paramT).booleanValue());
/*     */     } else {
/* 134 */       return super.setOption(paramChannelOption, paramT);
/*     */     }
/*     */     
/* 137 */     return true;
/*     */   }
/*     */   
/*     */   private void setActiveOnOpen(boolean paramBoolean) {
/* 141 */     if (this.channel.isRegistered()) {
/* 142 */       throw new IllegalStateException("Can only changed before channel was registered");
/*     */     }
/* 144 */     this.activeOnOpen = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isBroadcast() {
/*     */     try {
/* 149 */       return this.javaSocket.getBroadcast();
/*     */     } catch (SocketException localSocketException) {
/* 151 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setBroadcast(boolean paramBoolean)
/*     */   {
/*     */     try
/*     */     {
/* 159 */       if ((paramBoolean) && (!PlatformDependent.isWindows()) && (!PlatformDependent.isRoot()) && (!this.javaSocket.getLocalAddress().isAnyLocalAddress()))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 164 */         logger.warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; setting the SO_BROADCAST flag anyway as requested on the socket which is bound to " + this.javaSocket.getLocalSocketAddress() + '.');
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */       this.javaSocket.setBroadcast(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 173 */       throw new ChannelException(localSocketException);
/*     */     }
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public InetAddress getInterface()
/*     */   {
/* 180 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 182 */         return ((MulticastSocket)this.javaSocket).getInterface();
/*     */       } catch (SocketException localSocketException) {
/* 184 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     }
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   public DatagramChannelConfig setInterface(InetAddress paramInetAddress)
/*     */   {
/* 193 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 195 */         ((MulticastSocket)this.javaSocket).setInterface(paramInetAddress);
/*     */       } catch (SocketException localSocketException) {
/* 197 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     } else {
/* 200 */       throw new UnsupportedOperationException();
/*     */     }
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isLoopbackModeDisabled()
/*     */   {
/* 207 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 209 */         return ((MulticastSocket)this.javaSocket).getLoopbackMode();
/*     */       } catch (SocketException localSocketException) {
/* 211 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     }
/* 214 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean paramBoolean)
/*     */   {
/* 220 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 222 */         ((MulticastSocket)this.javaSocket).setLoopbackMode(paramBoolean);
/*     */       } catch (SocketException localSocketException) {
/* 224 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     } else {
/* 227 */       throw new UnsupportedOperationException();
/*     */     }
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   public NetworkInterface getNetworkInterface()
/*     */   {
/* 234 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 236 */         return ((MulticastSocket)this.javaSocket).getNetworkInterface();
/*     */       } catch (SocketException localSocketException) {
/* 238 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     }
/* 241 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   public DatagramChannelConfig setNetworkInterface(NetworkInterface paramNetworkInterface)
/*     */   {
/* 247 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 249 */         ((MulticastSocket)this.javaSocket).setNetworkInterface(paramNetworkInterface);
/*     */       } catch (SocketException localSocketException) {
/* 251 */         throw new ChannelException(localSocketException);
/*     */       }
/*     */     } else {
/* 254 */       throw new UnsupportedOperationException();
/*     */     }
/* 256 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isReuseAddress()
/*     */   {
/*     */     try {
/* 262 */       return this.javaSocket.getReuseAddress();
/*     */     } catch (SocketException localSocketException) {
/* 264 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setReuseAddress(boolean paramBoolean)
/*     */   {
/*     */     try {
/* 271 */       this.javaSocket.setReuseAddress(paramBoolean);
/*     */     } catch (SocketException localSocketException) {
/* 273 */       throw new ChannelException(localSocketException);
/*     */     }
/* 275 */     return this;
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize()
/*     */   {
/*     */     try {
/* 281 */       return this.javaSocket.getReceiveBufferSize();
/*     */     } catch (SocketException localSocketException) {
/* 283 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setReceiveBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 290 */       this.javaSocket.setReceiveBufferSize(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 292 */       throw new ChannelException(localSocketException);
/*     */     }
/* 294 */     return this;
/*     */   }
/*     */   
/*     */   public int getSendBufferSize()
/*     */   {
/*     */     try {
/* 300 */       return this.javaSocket.getSendBufferSize();
/*     */     } catch (SocketException localSocketException) {
/* 302 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setSendBufferSize(int paramInt)
/*     */   {
/*     */     try {
/* 309 */       this.javaSocket.setSendBufferSize(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 311 */       throw new ChannelException(localSocketException);
/*     */     }
/* 313 */     return this;
/*     */   }
/*     */   
/*     */   public int getTimeToLive()
/*     */   {
/* 318 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 320 */         return ((MulticastSocket)this.javaSocket).getTimeToLive();
/*     */       } catch (IOException localIOException) {
/* 322 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 325 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   public DatagramChannelConfig setTimeToLive(int paramInt)
/*     */   {
/* 331 */     if ((this.javaSocket instanceof MulticastSocket)) {
/*     */       try {
/* 333 */         ((MulticastSocket)this.javaSocket).setTimeToLive(paramInt);
/*     */       } catch (IOException localIOException) {
/* 335 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     } else {
/* 338 */       throw new UnsupportedOperationException();
/*     */     }
/* 340 */     return this;
/*     */   }
/*     */   
/*     */   public int getTrafficClass()
/*     */   {
/*     */     try {
/* 346 */       return this.javaSocket.getTrafficClass();
/*     */     } catch (SocketException localSocketException) {
/* 348 */       throw new ChannelException(localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setTrafficClass(int paramInt)
/*     */   {
/*     */     try {
/* 355 */       this.javaSocket.setTrafficClass(paramInt);
/*     */     } catch (SocketException localSocketException) {
/* 357 */       throw new ChannelException(localSocketException);
/*     */     }
/* 359 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setWriteSpinCount(int paramInt)
/*     */   {
/* 364 */     super.setWriteSpinCount(paramInt);
/* 365 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setConnectTimeoutMillis(int paramInt)
/*     */   {
/* 370 */     super.setConnectTimeoutMillis(paramInt);
/* 371 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setMaxMessagesPerRead(int paramInt)
/*     */   {
/* 376 */     super.setMaxMessagesPerRead(paramInt);
/* 377 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/* 382 */     super.setAllocator(paramByteBufAllocator);
/* 383 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator)
/*     */   {
/* 388 */     super.setRecvByteBufAllocator(paramRecvByteBufAllocator);
/* 389 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 394 */     super.setAutoRead(paramBoolean);
/* 395 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setAutoClose(boolean paramBoolean)
/*     */   {
/* 400 */     super.setAutoClose(paramBoolean);
/* 401 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setWriteBufferHighWaterMark(int paramInt)
/*     */   {
/* 406 */     super.setWriteBufferHighWaterMark(paramInt);
/* 407 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setWriteBufferLowWaterMark(int paramInt)
/*     */   {
/* 412 */     super.setWriteBufferLowWaterMark(paramInt);
/* 413 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator)
/*     */   {
/* 418 */     super.setMessageSizeEstimator(paramMessageSizeEstimator);
/* 419 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\DefaultDatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */