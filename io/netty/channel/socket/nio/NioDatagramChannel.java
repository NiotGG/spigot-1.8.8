/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.AddressedEnvelope;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultAddressedEnvelope;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DatagramPacket;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public final class NioDatagramChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements io.netty.channel.socket.DatagramChannel
/*     */ {
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*  64 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*  65 */   private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
/*     */   
/*     */ 
/*     */ 
/*     */   private final DatagramChannelConfig config;
/*     */   
/*     */ 
/*     */ 
/*     */   private Map<InetAddress, List<MembershipKey>> memberships;
/*     */   
/*     */ 
/*     */ 
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */ 
/*     */ 
/*     */   private static java.nio.channels.DatagramChannel newSocket(SelectorProvider paramSelectorProvider)
/*     */   {
/*     */     try
/*     */     {
/*  85 */       return paramSelectorProvider.openDatagramChannel();
/*     */     } catch (IOException localIOException) {
/*  87 */       throw new ChannelException("Failed to open a socket.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static java.nio.channels.DatagramChannel newSocket(SelectorProvider paramSelectorProvider, InternetProtocolFamily paramInternetProtocolFamily) {
/*  92 */     if (paramInternetProtocolFamily == null) {
/*  93 */       return newSocket(paramSelectorProvider);
/*     */     }
/*     */     
/*  96 */     checkJavaVersion();
/*     */     try
/*     */     {
/*  99 */       return paramSelectorProvider.openDatagramChannel(ProtocolFamilyConverter.convert(paramInternetProtocolFamily));
/*     */     } catch (IOException localIOException) {
/* 101 */       throw new ChannelException("Failed to open a socket.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkJavaVersion() {
/* 106 */     if (PlatformDependent.javaVersion() < 7) {
/* 107 */       throw new UnsupportedOperationException("Only supported on java 7+.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioDatagramChannel()
/*     */   {
/* 115 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioDatagramChannel(SelectorProvider paramSelectorProvider)
/*     */   {
/* 123 */     this(newSocket(paramSelectorProvider));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioDatagramChannel(InternetProtocolFamily paramInternetProtocolFamily)
/*     */   {
/* 131 */     this(newSocket(DEFAULT_SELECTOR_PROVIDER, paramInternetProtocolFamily));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioDatagramChannel(SelectorProvider paramSelectorProvider, InternetProtocolFamily paramInternetProtocolFamily)
/*     */   {
/* 140 */     this(newSocket(paramSelectorProvider, paramInternetProtocolFamily));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioDatagramChannel(java.nio.channels.DatagramChannel paramDatagramChannel)
/*     */   {
/* 147 */     super(null, paramDatagramChannel, 1);
/* 148 */     this.config = new NioDatagramChannelConfig(this, paramDatagramChannel);
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 153 */     return METADATA;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig config()
/*     */   {
/* 158 */     return this.config;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 164 */     java.nio.channels.DatagramChannel localDatagramChannel = javaChannel();
/* 165 */     return (localDatagramChannel.isOpen()) && (((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue()) && (isRegistered())) || (localDatagramChannel.socket().isBound()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/* 172 */     return javaChannel().isConnected();
/*     */   }
/*     */   
/*     */   protected java.nio.channels.DatagramChannel javaChannel()
/*     */   {
/* 177 */     return (java.nio.channels.DatagramChannel)super.javaChannel();
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/* 182 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/* 187 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 192 */     javaChannel().socket().bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 198 */     if (paramSocketAddress2 != null) {
/* 199 */       javaChannel().socket().bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 202 */     int i = 0;
/*     */     try {
/* 204 */       javaChannel().connect(paramSocketAddress1);
/* 205 */       i = 1;
/* 206 */       return true;
/*     */     } finally {
/* 208 */       if (i == 0) {
/* 209 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 216 */     throw new Error();
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 221 */     javaChannel().disconnect();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 226 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 231 */     java.nio.channels.DatagramChannel localDatagramChannel = javaChannel();
/* 232 */     DatagramChannelConfig localDatagramChannelConfig = config();
/* 233 */     RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 234 */     if (localHandle == null) {
/* 235 */       this.allocHandle = (localHandle = localDatagramChannelConfig.getRecvByteBufAllocator().newHandle());
/*     */     }
/* 237 */     ByteBuf localByteBuf = localHandle.allocate(localDatagramChannelConfig.getAllocator());
/* 238 */     int i = 1;
/*     */     try {
/* 240 */       ByteBuffer localByteBuffer = localByteBuf.internalNioBuffer(localByteBuf.writerIndex(), localByteBuf.writableBytes());
/* 241 */       j = localByteBuffer.position();
/* 242 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)localDatagramChannel.receive(localByteBuffer);
/* 243 */       if (localInetSocketAddress == null) {
/* 244 */         return 0;
/*     */       }
/*     */       
/* 247 */       int k = localByteBuffer.position() - j;
/* 248 */       localByteBuf.writerIndex(localByteBuf.writerIndex() + k);
/* 249 */       localHandle.record(k);
/*     */       
/* 251 */       paramList.add(new DatagramPacket(localByteBuf, localAddress(), localInetSocketAddress));
/* 252 */       i = 0;
/* 253 */       return 1;
/*     */     } catch (Throwable localThrowable) { int j;
/* 255 */       PlatformDependent.throwException(localThrowable);
/* 256 */       return -1;
/*     */     } finally {
/* 258 */       if (i != 0) {
/* 259 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/*     */     SocketAddress localSocketAddress;
/*     */     ByteBuf localByteBuf;
/* 268 */     if ((paramObject instanceof AddressedEnvelope))
/*     */     {
/* 270 */       AddressedEnvelope localAddressedEnvelope = (AddressedEnvelope)paramObject;
/* 271 */       localSocketAddress = localAddressedEnvelope.recipient();
/* 272 */       localByteBuf = (ByteBuf)localAddressedEnvelope.content();
/*     */     } else {
/* 274 */       localByteBuf = (ByteBuf)paramObject;
/* 275 */       localSocketAddress = null;
/*     */     }
/*     */     
/* 278 */     int i = localByteBuf.readableBytes();
/* 279 */     if (i == 0) {
/* 280 */       return true;
/*     */     }
/*     */     
/* 283 */     ByteBuffer localByteBuffer = localByteBuf.internalNioBuffer(localByteBuf.readerIndex(), i);
/*     */     int j;
/* 285 */     if (localSocketAddress != null) {
/* 286 */       j = javaChannel().send(localByteBuffer, localSocketAddress);
/*     */     } else {
/* 288 */       j = javaChannel().write(localByteBuffer);
/*     */     }
/* 290 */     return j > 0;
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject) { Object localObject;
/*     */     ByteBuf localByteBuf;
/* 295 */     if ((paramObject instanceof DatagramPacket)) {
/* 296 */       localObject = (DatagramPacket)paramObject;
/* 297 */       localByteBuf = (ByteBuf)((DatagramPacket)localObject).content();
/* 298 */       if (isSingleDirectBuffer(localByteBuf)) {
/* 299 */         return localObject;
/*     */       }
/* 301 */       return new DatagramPacket(newDirectBuffer((ReferenceCounted)localObject, localByteBuf), (InetSocketAddress)((DatagramPacket)localObject).recipient());
/*     */     }
/*     */     
/* 304 */     if ((paramObject instanceof ByteBuf)) {
/* 305 */       localObject = (ByteBuf)paramObject;
/* 306 */       if (isSingleDirectBuffer((ByteBuf)localObject)) {
/* 307 */         return localObject;
/*     */       }
/* 309 */       return newDirectBuffer((ByteBuf)localObject);
/*     */     }
/*     */     
/* 312 */     if ((paramObject instanceof AddressedEnvelope))
/*     */     {
/* 314 */       localObject = (AddressedEnvelope)paramObject;
/* 315 */       if ((((AddressedEnvelope)localObject).content() instanceof ByteBuf)) {
/* 316 */         localByteBuf = (ByteBuf)((AddressedEnvelope)localObject).content();
/* 317 */         if (isSingleDirectBuffer(localByteBuf)) {
/* 318 */           return localObject;
/*     */         }
/* 320 */         return new DefaultAddressedEnvelope(newDirectBuffer((ReferenceCounted)localObject, localByteBuf), ((AddressedEnvelope)localObject).recipient());
/*     */       }
/*     */     }
/*     */     
/* 324 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPES);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isSingleDirectBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 333 */     return (paramByteBuf.isDirect()) && (paramByteBuf.nioBufferCount() == 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean continueOnWriteError()
/*     */   {
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 346 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 351 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress)
/*     */   {
/* 356 */     return joinGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try {
/* 362 */       return joinGroup(paramInetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, paramChannelPromise);
/*     */ 
/*     */     }
/*     */     catch (SocketException localSocketException)
/*     */     {
/* 367 */       paramChannelPromise.setFailure(localSocketException);
/*     */     }
/* 369 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 375 */     return joinGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/* 382 */     return joinGroup(paramInetSocketAddress.getAddress(), paramNetworkInterface, null, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 388 */     return joinGroup(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture joinGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     
/*     */     
/*     */ 
/* 398 */     if (paramInetAddress1 == null) {
/* 399 */       throw new NullPointerException("multicastAddress");
/*     */     }
/*     */     
/* 402 */     if (paramNetworkInterface == null) {
/* 403 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     try
/*     */     {
/*     */       MembershipKey localMembershipKey;
/* 408 */       if (paramInetAddress2 == null) {
/* 409 */         localMembershipKey = javaChannel().join(paramInetAddress1, paramNetworkInterface);
/*     */       } else {
/* 411 */         localMembershipKey = javaChannel().join(paramInetAddress1, paramNetworkInterface, paramInetAddress2);
/*     */       }
/*     */       
/* 414 */       synchronized (this) {
/* 415 */         Object localObject1 = null;
/* 416 */         if (this.memberships == null) {
/* 417 */           this.memberships = new HashMap();
/*     */         } else {
/* 419 */           localObject1 = (List)this.memberships.get(paramInetAddress1);
/*     */         }
/* 421 */         if (localObject1 == null) {
/* 422 */           localObject1 = new ArrayList();
/* 423 */           this.memberships.put(paramInetAddress1, localObject1);
/*     */         }
/* 425 */         ((List)localObject1).add(localMembershipKey);
/*     */       }
/*     */       
/* 428 */       paramChannelPromise.setSuccess();
/*     */     } catch (Throwable localThrowable) {
/* 430 */       paramChannelPromise.setFailure(localThrowable);
/*     */     }
/*     */     
/* 433 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress)
/*     */   {
/* 438 */     return leaveGroup(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try {
/* 444 */       return leaveGroup(paramInetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, paramChannelPromise);
/*     */     }
/*     */     catch (SocketException localSocketException) {
/* 447 */       paramChannelPromise.setFailure(localSocketException);
/*     */     }
/* 449 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface)
/*     */   {
/* 455 */     return leaveGroup(paramInetSocketAddress, paramNetworkInterface, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetSocketAddress paramInetSocketAddress, NetworkInterface paramNetworkInterface, ChannelPromise paramChannelPromise)
/*     */   {
/* 462 */     return leaveGroup(paramInetSocketAddress.getAddress(), paramNetworkInterface, null, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 468 */     return leaveGroup(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture leaveGroup(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     
/*     */     
/* 477 */     if (paramInetAddress1 == null) {
/* 478 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 480 */     if (paramNetworkInterface == null) {
/* 481 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 484 */     synchronized (this) {
/* 485 */       if (this.memberships != null) {
/* 486 */         List localList = (List)this.memberships.get(paramInetAddress1);
/* 487 */         if (localList != null) {
/* 488 */           Iterator localIterator = localList.iterator();
/*     */           
/* 490 */           while (localIterator.hasNext()) {
/* 491 */             MembershipKey localMembershipKey = (MembershipKey)localIterator.next();
/* 492 */             if ((paramNetworkInterface.equals(localMembershipKey.networkInterface())) && (
/* 493 */               ((paramInetAddress2 == null) && (localMembershipKey.sourceAddress() == null)) || ((paramInetAddress2 != null) && (paramInetAddress2.equals(localMembershipKey.sourceAddress())))))
/*     */             {
/* 495 */               localMembershipKey.drop();
/* 496 */               localIterator.remove();
/*     */             }
/*     */           }
/*     */           
/* 500 */           if (localList.isEmpty()) {
/* 501 */             this.memberships.remove(paramInetAddress1);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 507 */     paramChannelPromise.setSuccess();
/* 508 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2)
/*     */   {
/* 518 */     return block(paramInetAddress1, paramNetworkInterface, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, NetworkInterface paramNetworkInterface, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     
/*     */     
/*     */ 
/*     */ 
/* 530 */     if (paramInetAddress1 == null) {
/* 531 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 533 */     if (paramInetAddress2 == null) {
/* 534 */       throw new NullPointerException("sourceToBlock");
/*     */     }
/*     */     
/* 537 */     if (paramNetworkInterface == null) {
/* 538 */       throw new NullPointerException("networkInterface");
/*     */     }
/* 540 */     synchronized (this) {
/* 541 */       if (this.memberships != null) {
/* 542 */         List localList = (List)this.memberships.get(paramInetAddress1);
/* 543 */         for (MembershipKey localMembershipKey : localList) {
/* 544 */           if (paramNetworkInterface.equals(localMembershipKey.networkInterface())) {
/*     */             try {
/* 546 */               localMembershipKey.block(paramInetAddress2);
/*     */             } catch (IOException localIOException) {
/* 548 */               paramChannelPromise.setFailure(localIOException);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 554 */     paramChannelPromise.setSuccess();
/* 555 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2)
/*     */   {
/* 564 */     return block(paramInetAddress1, paramInetAddress2, newPromise());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChannelFuture block(InetAddress paramInetAddress1, InetAddress paramInetAddress2, ChannelPromise paramChannelPromise)
/*     */   {
/*     */     try
/*     */     {
/* 575 */       return block(paramInetAddress1, NetworkInterface.getByInetAddress(localAddress().getAddress()), paramInetAddress2, paramChannelPromise);
/*     */ 
/*     */     }
/*     */     catch (SocketException localSocketException)
/*     */     {
/* 580 */       paramChannelPromise.setFailure(localSocketException);
/*     */     }
/* 582 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean paramBoolean)
/*     */   {
/* 587 */     super.setReadPending(paramBoolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\nio\NioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */