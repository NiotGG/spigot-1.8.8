/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.NotYetConnectedException;
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
/*     */ public final class EpollDatagramChannel
/*     */   extends AbstractEpollChannel
/*     */   implements DatagramChannel
/*     */ {
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  48 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */   private volatile InetSocketAddress local;
/*     */   
/*     */   private volatile InetSocketAddress remote;
/*     */   
/*     */   private volatile boolean connected;
/*     */   
/*     */   private final EpollDatagramChannelConfig config;
/*     */   
/*     */ 
/*     */   public EpollDatagramChannel()
/*     */   {
/*  61 */     super(Native.socketDgramFd(), 1);
/*  62 */     this.config = new EpollDatagramChannelConfig(this);
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/*  67 */     return METADATA;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/*  73 */     return (this.fd != -1) && (((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue()) && (isRegistered())) || (this.active));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/*  80 */     return this.connected;
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress)
/*     */   {
/*  85 */     return joinGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try {
/*  91 */       return joinGroup(paramInetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, paramChannelPromise);
/*     */ 
/*     */     }
/*     */     catch (SocketException localSocketException)
/*     */     {
/*  96 */       paramChannelPromise.setFailure(localSocketException);
/*     */     }
/*  98 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 104 */     return joinGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/* 111 */     return joinGroup(paramInetSocketAddress.getAddress(), paramNetworkInterface, null, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 117 */     return joinGroup(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 125 */     if (paramInetAddress1 == null) {
/* 126 */       throw new NullPointerException("multicastAddress");
/*     */     }
/*     */     
/* 129 */     if (paramNetworkInterface == null) {
/* 130 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 133 */     paramChannelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 134 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress)
/*     */   {
/* 139 */     return leaveGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try {
/* 145 */       return leaveGroup(paramInetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, paramChannelPromise);
/*     */     }
/*     */     catch (SocketException localSocketException) {
/* 148 */       paramChannelPromise.setFailure(localSocketException);
/*     */     }
/* 150 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 156 */     return leaveGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/* 163 */     return leaveGroup(paramInetSocketAddress.getAddress(), paramNetworkInterface, null, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 169 */     return leaveGroup(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 176 */     if (paramInetAddress1 == null) {
/* 177 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 179 */     if (paramNetworkInterface == null) {
/* 180 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 183 */     paramChannelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/*     */     
/* 185 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 192 */     return block(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/* 199 */     if (paramInetAddress1 == null) {
/* 200 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 202 */     if (paramInetAddress2 == null) {
/* 203 */       throw new NullPointerException("sourceToBlock");
/*     */     }
/*     */     
/* 206 */     if (paramNetworkInterface == null) {
/* 207 */       throw new NullPointerException("networkInterface");
/*     */     }
/* 209 */     paramChannelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
/* 210 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2)
/*     */   {
/* 215 */     return block(paramInetAddress1, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try
/*     */     {
/* 222 */       return block(paramInetAddress1, NetworkInterface.getByInetAddress(localAddress().getAddress()), paramInetAddress2, paramChannelPromise);
/*     */ 
/*     */     }
/*     */     catch (Throwable localThrowable)
/*     */     {
/* 227 */       paramChannelPromise.setFailure(localThrowable);
/*     */     }
/* 229 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe()
/*     */   {
/* 234 */     return new EpollDatagramChannelUnsafe();
/*     */   }
/*     */   
/*     */   protected InetSocketAddress localAddress0()
/*     */   {
/* 239 */     return this.local;
/*     */   }
/*     */   
/*     */   protected InetSocketAddress remoteAddress0()
/*     */   {
/* 244 */     return this.remote;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 249 */     InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 250 */     checkResolvable(localInetSocketAddress);
/* 251 */     Native.bind(this.fd, localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/* 252 */     this.local = Native.localAddress(this.fd);
/* 253 */     this.active = true;
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     for (;;) {
/* 259 */       Object localObject = paramChannelOutboundBuffer.current();
/* 260 */       if (localObject == null)
/*     */       {
/* 262 */         clearEpollOut();
/* 263 */         break;
/*     */       }
/*     */       try
/*     */       {
/* 267 */         int i = 0;
/* 268 */         for (int j = config().getWriteSpinCount() - 1; j >= 0; j--) {
/* 269 */           if (doWriteMessage(localObject)) {
/* 270 */             i = 1;
/* 271 */             break;
/*     */           }
/*     */         }
/*     */         
/* 275 */         if (i != 0) {
/* 276 */           paramChannelOutboundBuffer.remove();
/*     */         }
/*     */         else {
/* 279 */           setEpollOut();
/* 280 */           break;
/*     */         }
/*     */         
/*     */       }
/*     */       catch (IOException localIOException)
/*     */       {
/* 286 */         paramChannelOutboundBuffer.remove(localIOException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean doWriteMessage(Object paramObject) throws IOException {
/*     */     ByteBuf localByteBuf;
/*     */     InetSocketAddress localInetSocketAddress;
/* 294 */     if ((paramObject instanceof AddressedEnvelope))
/*     */     {
/* 296 */       AddressedEnvelope localAddressedEnvelope = (AddressedEnvelope)paramObject;
/*     */       
/* 298 */       localByteBuf = (ByteBuf)localAddressedEnvelope.content();
/* 299 */       localInetSocketAddress = (InetSocketAddress)localAddressedEnvelope.recipient();
/*     */     } else {
/* 301 */       localByteBuf = (ByteBuf)paramObject;
/* 302 */       localInetSocketAddress = null;
/*     */     }
/*     */     
/* 305 */     int i = localByteBuf.readableBytes();
/* 306 */     if (i == 0) {
/* 307 */       return true;
/*     */     }
/*     */     
/* 310 */     if (localInetSocketAddress == null) {
/* 311 */       localInetSocketAddress = this.remote;
/* 312 */       if (localInetSocketAddress == null) {
/* 313 */         throw new NotYetConnectedException();
/*     */       }
/*     */     }
/*     */     
/*     */     int j;
/* 318 */     if (localByteBuf.hasMemoryAddress()) {
/* 319 */       long l = localByteBuf.memoryAddress();
/* 320 */       j = Native.sendToAddress(this.fd, l, localByteBuf.readerIndex(), localByteBuf.writerIndex(), localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/*     */     }
/*     */     else {
/* 323 */       ByteBuffer localByteBuffer = localByteBuf.internalNioBuffer(localByteBuf.readerIndex(), localByteBuf.readableBytes());
/* 324 */       j = Native.sendTo(this.fd, localByteBuffer, localByteBuffer.position(), localByteBuffer.limit(), localInetSocketAddress.getAddress(), localInetSocketAddress.getPort());
/*     */     }
/*     */     
/*     */ 
/* 328 */     return j > 0;
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject) { Object localObject;
/*     */     ByteBuf localByteBuf;
/* 333 */     if ((paramObject instanceof DatagramPacket)) {
/* 334 */       localObject = (DatagramPacket)paramObject;
/* 335 */       localByteBuf = (ByteBuf)((DatagramPacket)localObject).content();
/* 336 */       if (localByteBuf.hasMemoryAddress()) {
/* 337 */         return paramObject;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 342 */       return new DatagramPacket(newDirectBuffer(localObject, localByteBuf), (InetSocketAddress)((DatagramPacket)localObject).recipient());
/*     */     }
/*     */     
/* 345 */     if ((paramObject instanceof ByteBuf)) {
/* 346 */       localObject = (ByteBuf)paramObject;
/* 347 */       if (((ByteBuf)localObject).hasMemoryAddress()) {
/* 348 */         return paramObject;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 353 */       return newDirectBuffer((ByteBuf)localObject);
/*     */     }
/*     */     
/* 356 */     if ((paramObject instanceof AddressedEnvelope))
/*     */     {
/* 358 */       localObject = (AddressedEnvelope)paramObject;
/* 359 */       if (((((AddressedEnvelope)localObject).content() instanceof ByteBuf)) && ((((AddressedEnvelope)localObject).recipient() == null) || ((((AddressedEnvelope)localObject).recipient() instanceof InetSocketAddress))))
/*     */       {
/*     */ 
/* 362 */         localByteBuf = (ByteBuf)((AddressedEnvelope)localObject).content();
/* 363 */         if (localByteBuf.hasMemoryAddress()) {
/* 364 */           return localObject;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 369 */         return new DefaultAddressedEnvelope(newDirectBuffer(localObject, localByteBuf), (InetSocketAddress)((AddressedEnvelope)localObject).recipient());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 374 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */ 
/*     */   public EpollDatagramChannelConfig config()
/*     */   {
/* 380 */     return this.config;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 385 */   protected void doDisconnect() throws Exception { this.connected = false; }
/*     */   
/*     */   final class EpollDatagramChannelUnsafe extends AbstractEpollChannel.AbstractEpollUnsafe {
/* 388 */     EpollDatagramChannelUnsafe() { super(); }
/*     */     
/*     */ 
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/* 393 */       int i = 0;
/*     */       try {
/*     */         try {
/* 396 */           InetSocketAddress localInetSocketAddress1 = (InetSocketAddress)paramSocketAddress1;
/* 397 */           if (paramSocketAddress2 != null) {
/* 398 */             InetSocketAddress localInetSocketAddress2 = (InetSocketAddress)paramSocketAddress2;
/* 399 */             EpollDatagramChannel.this.doBind(localInetSocketAddress2);
/*     */           }
/*     */           
/* 402 */           AbstractEpollChannel.checkResolvable(localInetSocketAddress1);
/* 403 */           EpollDatagramChannel.this.remote = localInetSocketAddress1;
/* 404 */           EpollDatagramChannel.this.local = Native.localAddress(EpollDatagramChannel.this.fd);
/* 405 */           i = 1;
/*     */         } finally {
/* 407 */           if (i == 0) {
/* 408 */             EpollDatagramChannel.this.doClose();
/*     */           } else {
/* 410 */             paramChannelPromise.setSuccess();
/* 411 */             EpollDatagramChannel.this.connected = true;
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 415 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     }
/*     */     
/*     */     void epollInReady()
/*     */     {
/* 421 */       EpollDatagramChannelConfig localEpollDatagramChannelConfig = EpollDatagramChannel.this.config();
/* 422 */       RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 423 */       if (localHandle == null) {
/* 424 */         this.allocHandle = (localHandle = localEpollDatagramChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */       }
/*     */       
/* 427 */       assert (EpollDatagramChannel.this.eventLoop().inEventLoop());
/* 428 */       ChannelPipeline localChannelPipeline = EpollDatagramChannel.this.pipeline();
/*     */       try {
/*     */         for (;;) {
/* 431 */           ByteBuf localByteBuf = null;
/*     */           try {
/* 433 */             localByteBuf = localHandle.allocate(localEpollDatagramChannelConfig.getAllocator());
/* 434 */             int i = localByteBuf.writerIndex();
/*     */             EpollDatagramChannel.DatagramSocketAddress localDatagramSocketAddress;
/* 436 */             if (localByteBuf.hasMemoryAddress())
/*     */             {
/* 438 */               localDatagramSocketAddress = Native.recvFromAddress(EpollDatagramChannel.this.fd, localByteBuf.memoryAddress(), i, localByteBuf.capacity());
/*     */             }
/*     */             else {
/* 441 */               ByteBuffer localByteBuffer = localByteBuf.internalNioBuffer(i, localByteBuf.writableBytes());
/* 442 */               localDatagramSocketAddress = Native.recvFrom(EpollDatagramChannel.this.fd, localByteBuffer, localByteBuffer.position(), localByteBuffer.limit());
/*     */             }
/*     */             
/*     */ 
/* 446 */             if (localDatagramSocketAddress == null)
/*     */             {
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
/* 462 */               if (localByteBuf == null) break;
/* 463 */               localByteBuf.release(); break;
/*     */             }
/* 450 */             int j = localDatagramSocketAddress.receivedAmount;
/* 451 */             localByteBuf.writerIndex(localByteBuf.writerIndex() + j);
/* 452 */             localHandle.record(j);
/* 453 */             this.readPending = false;
/* 454 */             localChannelPipeline.fireChannelRead(new DatagramPacket(localByteBuf, (InetSocketAddress)localAddress(), localDatagramSocketAddress));
/*     */             
/* 456 */             localByteBuf = null;
/*     */           }
/*     */           catch (Throwable localThrowable) {
/* 459 */             localChannelPipeline.fireChannelReadComplete();
/* 460 */             localChannelPipeline.fireExceptionCaught(localThrowable);
/*     */           } finally {
/* 462 */             if (localByteBuf != null) {
/* 463 */               localByteBuf.release();
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */         
/*     */ 
/*     */       }
/*     */       finally
/*     */       {
/* 474 */         if ((!EpollDatagramChannel.this.config().isAutoRead()) && (!this.readPending)) {
/* 475 */           EpollDatagramChannel.this.clearEpollIn();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */   }
/*     */   
/*     */ 
/*     */   static final class DatagramSocketAddress
/*     */     extends InetSocketAddress
/*     */   {
/*     */     private static final long serialVersionUID = 1348596211215015739L;
/*     */     final int receivedAmount;
/*     */     
/*     */     DatagramSocketAddress(String paramString, int paramInt1, int paramInt2)
/*     */     {
/* 493 */       super(paramInt1);
/* 494 */       this.receivedAmount = paramInt2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\EpollDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */