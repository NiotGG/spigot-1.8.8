/*     */ package io.netty.channel.socket.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DefaultDatagramChannelConfig;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ public class OioDatagramChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  60 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
/*     */   
/*  62 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  63 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(io.netty.channel.socket.DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */ 
/*     */   private final MulticastSocket socket;
/*     */   
/*     */ 
/*     */   private final DatagramChannelConfig config;
/*     */   
/*     */ 
/*  72 */   private final java.net.DatagramPacket tmpPacket = new java.net.DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static MulticastSocket newSocket()
/*     */   {
/*     */     try {
/*  78 */       return new MulticastSocket(null);
/*     */     } catch (Exception localException) {
/*  80 */       throw new ChannelException("failed to create a new socket", localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public OioDatagramChannel()
/*     */   {
/*  88 */     this(newSocket());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioDatagramChannel(MulticastSocket paramMulticastSocket)
/*     */   {
/*  97 */     super(null);
/*     */     
/*  99 */     int i = 0;
/*     */     try {
/* 101 */       paramMulticastSocket.setSoTimeout(1000);
/* 102 */       paramMulticastSocket.setBroadcast(false);
/* 103 */       i = 1;
/*     */     } catch (SocketException localSocketException) {
/* 105 */       throw new ChannelException("Failed to configure the datagram socket timeout.", localSocketException);
/*     */     }
/*     */     finally {
/* 108 */       if (i == 0) {
/* 109 */         paramMulticastSocket.close();
/*     */       }
/*     */     }
/*     */     
/* 113 */     this.socket = paramMulticastSocket;
/* 114 */     this.config = new DefaultDatagramChannelConfig(this, paramMulticastSocket);
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 119 */     return METADATA;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig config()
/*     */   {
/* 124 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 129 */     return !this.socket.isClosed();
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 135 */     return (isOpen()) && (((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue()) && (isRegistered())) || (this.socket.isBound()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/* 142 */     return this.socket.isConnected();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 147 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 152 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 157 */     this.socket.bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 162 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 167 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   protected void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 173 */     if (paramSocketAddress2 != null) {
/* 174 */       this.socket.bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 177 */     int i = 0;
/*     */     try {
/* 179 */       this.socket.connect(paramSocketAddress1);
/* 180 */       i = 1; return;
/*     */     } finally {
/* 182 */       if (i == 0) {
/*     */         try {
/* 184 */           this.socket.close();
/*     */         } catch (Throwable localThrowable2) {
/* 186 */           logger.warn("Failed to close a socket.", localThrowable2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 194 */     this.socket.disconnect();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 199 */     this.socket.close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 204 */     DatagramChannelConfig localDatagramChannelConfig = config();
/* 205 */     RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 206 */     if (localHandle == null) {
/* 207 */       this.allocHandle = (localHandle = localDatagramChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */     }
/*     */     
/* 210 */     ByteBuf localByteBuf = localDatagramChannelConfig.getAllocator().heapBuffer(localHandle.guess());
/* 211 */     int i = 1;
/*     */     try {
/* 213 */       this.tmpPacket.setData(localByteBuf.array(), localByteBuf.arrayOffset(), localByteBuf.capacity());
/* 214 */       this.socket.receive(this.tmpPacket);
/*     */       
/* 216 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)this.tmpPacket.getSocketAddress();
/*     */       
/* 218 */       j = this.tmpPacket.getLength();
/* 219 */       localHandle.record(j);
/* 220 */       paramList.add(new io.netty.channel.socket.DatagramPacket(localByteBuf.writerIndex(j), localAddress(), localInetSocketAddress));
/* 221 */       i = 0;
/* 222 */       return 1;
/*     */     }
/*     */     catch (SocketTimeoutException localSocketTimeoutException) {
/* 225 */       return 0;
/*     */     } catch (SocketException localSocketException) {
/* 227 */       if (!localSocketException.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
/* 228 */         throw localSocketException;
/*     */       }
/* 230 */       return -1;
/*     */     } catch (Throwable localThrowable) { int j;
/* 232 */       PlatformDependent.throwException(localThrowable);
/* 233 */       return -1;
/*     */     } finally {
/* 235 */       if (i != 0) {
/* 236 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 244 */       Object localObject = paramChannelOutboundBuffer.current();
/* 245 */       if (localObject == null) {
/*     */         break;
/*     */       }
/*     */       
/*     */       SocketAddress localSocketAddress;
/*     */       ByteBuf localByteBuf;
/* 251 */       if ((localObject instanceof AddressedEnvelope))
/*     */       {
/* 253 */         AddressedEnvelope localAddressedEnvelope = (AddressedEnvelope)localObject;
/* 254 */         localSocketAddress = localAddressedEnvelope.recipient();
/* 255 */         localByteBuf = (ByteBuf)localAddressedEnvelope.content();
/*     */       } else {
/* 257 */         localByteBuf = (ByteBuf)localObject;
/* 258 */         localSocketAddress = null;
/*     */       }
/*     */       
/* 261 */       int i = localByteBuf.readableBytes();
/* 262 */       if (localSocketAddress != null) {
/* 263 */         this.tmpPacket.setSocketAddress(localSocketAddress);
/*     */       }
/* 265 */       if (localByteBuf.hasArray()) {
/* 266 */         this.tmpPacket.setData(localByteBuf.array(), localByteBuf.arrayOffset() + localByteBuf.readerIndex(), i);
/*     */       } else {
/* 268 */         byte[] arrayOfByte = new byte[i];
/* 269 */         localByteBuf.getBytes(localByteBuf.readerIndex(), arrayOfByte);
/* 270 */         this.tmpPacket.setData(arrayOfByte);
/*     */       }
/*     */       try {
/* 273 */         this.socket.send(this.tmpPacket);
/* 274 */         paramChannelOutboundBuffer.remove();
/*     */ 
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/* 279 */         paramChannelOutboundBuffer.remove(localIOException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject)
/*     */   {
/* 286 */     if (((paramObject instanceof io.netty.channel.socket.DatagramPacket)) || ((paramObject instanceof ByteBuf))) {
/* 287 */       return paramObject;
/*     */     }
/*     */     
/* 290 */     if ((paramObject instanceof AddressedEnvelope))
/*     */     {
/* 292 */       AddressedEnvelope localAddressedEnvelope = (AddressedEnvelope)paramObject;
/* 293 */       if ((localAddressedEnvelope.content() instanceof ByteBuf)) {
/* 294 */         return paramObject;
/*     */       }
/*     */     }
/*     */     
/* 298 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress)
/*     */   {
/* 304 */     return joinGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/* 309 */     ensureBound();
/*     */     try {
/* 311 */       this.socket.joinGroup(paramInetAddress);
/* 312 */       paramChannelPromise.setSuccess();
/*     */     } catch (IOException localIOException) {
/* 314 */       paramChannelPromise.setFailure(localIOException);
/*     */     }
/* 316 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 321 */     return joinGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/* 328 */     ensureBound();
/*     */     try {
/* 330 */       this.socket.joinGroup(paramInetSocketAddress, paramNetworkInterface);
/* 331 */       paramChannelPromise.setSuccess();
/*     */     } catch (IOException localIOException) {
/* 333 */       paramChannelPromise.setFailure(localIOException);
/*     */     }
/* 335 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 341 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 348 */     paramChannelPromise.setFailure(new UnsupportedOperationException());
/* 349 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private void ensureBound() {
/* 353 */     if (!isActive()) {
/* 354 */       throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress)
/*     */   {
/* 362 */     return leaveGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try {
/* 368 */       this.socket.leaveGroup(paramInetAddress);
/* 369 */       paramChannelPromise.setSuccess();
/*     */     } catch (IOException localIOException) {
/* 371 */       paramChannelPromise.setFailure(localIOException);
/*     */     }
/* 373 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 379 */     return leaveGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try
/*     */     {
/* 387 */       this.socket.leaveGroup(paramInetSocketAddress, paramNetworkInterface);
/* 388 */       paramChannelPromise.setSuccess();
/*     */     } catch (IOException localIOException) {
/* 390 */       paramChannelPromise.setFailure(localIOException);
/*     */     }
/* 392 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 398 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 405 */     paramChannelPromise.setFailure(new UnsupportedOperationException());
/* 406 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 412 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 419 */     paramChannelPromise.setFailure(new UnsupportedOperationException());
/* 420 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2)
/*     */   {
/* 426 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 432 */     paramChannelPromise.setFailure(new UnsupportedOperationException());
/* 433 */     return paramChannelPromise;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\oio\OioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */