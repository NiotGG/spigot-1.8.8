/*     */ package io.netty.channel.sctp.nio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.nio.NioEventLoop;
/*     */ import io.netty.channel.sctp.DefaultSctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpChannelConfig;
/*     */ import io.netty.channel.sctp.SctpMessage;
/*     */ import io.netty.channel.sctp.SctpNotificationHandler;
/*     */ import io.netty.channel.sctp.SctpServerChannel;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class NioSctpChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements io.netty.channel.sctp.SctpChannel
/*     */ {
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  65 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSctpChannel.class);
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static com.sun.nio.sctp.SctpChannel newSctpChannel()
/*     */   {
/*     */     try
/*     */     {
/*  75 */       return com.sun.nio.sctp.SctpChannel.open();
/*     */     } catch (IOException localIOException) {
/*  77 */       throw new ChannelException("Failed to open a sctp channel.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioSctpChannel()
/*     */   {
/*  85 */     this(newSctpChannel());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NioSctpChannel(com.sun.nio.sctp.SctpChannel paramSctpChannel)
/*     */   {
/*  92 */     this(null, paramSctpChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NioSctpChannel(Channel paramChannel, com.sun.nio.sctp.SctpChannel paramSctpChannel)
/*     */   {
/* 103 */     super(paramChannel, paramSctpChannel, 1);
/*     */     try {
/* 105 */       paramSctpChannel.configureBlocking(false);
/* 106 */       this.config = new NioSctpChannelConfig(this, paramSctpChannel, null);
/* 107 */       this.notificationHandler = new SctpNotificationHandler(this);
/*     */     } catch (IOException localIOException1) {
/*     */       try {
/* 110 */         paramSctpChannel.close();
/*     */       } catch (IOException localIOException2) {
/* 112 */         if (logger.isWarnEnabled()) {
/* 113 */           logger.warn("Failed to close a partially initialized sctp channel.", localIOException2);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 118 */       throw new ChannelException("Failed to enter non-blocking mode.", localIOException1);
/*     */     }
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 124 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 129 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public SctpServerChannel parent()
/*     */   {
/* 134 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 139 */     return METADATA;
/*     */   }
/*     */   
/*     */   public Association association()
/*     */   {
/*     */     try {
/* 145 */       return javaChannel().association();
/*     */     } catch (IOException localIOException) {}
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses()
/*     */   {
/*     */     try
/*     */     {
/* 154 */       Set localSet = javaChannel().getAllLocalAddresses();
/* 155 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(localSet.size());
/* 156 */       for (SocketAddress localSocketAddress : localSet) {
/* 157 */         localLinkedHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/* 159 */       return localLinkedHashSet;
/*     */     } catch (Throwable localThrowable) {}
/* 161 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */ 
/*     */   public SctpChannelConfig config()
/*     */   {
/* 167 */     return this.config;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses()
/*     */   {
/*     */     try {
/* 173 */       Set localSet = javaChannel().getRemoteAddresses();
/* 174 */       HashSet localHashSet = new HashSet(localSet.size());
/* 175 */       for (SocketAddress localSocketAddress : localSet) {
/* 176 */         localHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/* 178 */       return localHashSet;
/*     */     } catch (Throwable localThrowable) {}
/* 180 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */ 
/*     */   protected com.sun.nio.sctp.SctpChannel javaChannel()
/*     */   {
/* 186 */     return (com.sun.nio.sctp.SctpChannel)super.javaChannel();
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/* 191 */     com.sun.nio.sctp.SctpChannel localSctpChannel = javaChannel();
/* 192 */     return (localSctpChannel.isOpen()) && (association() != null);
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*     */     try {
/* 198 */       Iterator localIterator = javaChannel().getAllLocalAddresses().iterator();
/* 199 */       if (localIterator.hasNext()) {
/* 200 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 205 */     return null;
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/*     */     try {
/* 211 */       Iterator localIterator = javaChannel().getRemoteAddresses().iterator();
/* 212 */       if (localIterator.hasNext()) {
/* 213 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 218 */     return null;
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress) throws Exception
/*     */   {
/* 223 */     javaChannel().bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected boolean doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception
/*     */   {
/* 228 */     if (paramSocketAddress2 != null) {
/* 229 */       javaChannel().bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 232 */     int i = 0;
/*     */     try {
/* 234 */       boolean bool1 = javaChannel().connect(paramSocketAddress1);
/* 235 */       if (!bool1) {
/* 236 */         selectionKey().interestOps(8);
/*     */       }
/* 238 */       i = 1;
/* 239 */       return bool1;
/*     */     } finally {
/* 241 */       if (i == 0) {
/* 242 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doFinishConnect() throws Exception
/*     */   {
/* 249 */     if (!javaChannel().finishConnect()) {
/* 250 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 256 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 261 */     javaChannel().close();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 266 */     com.sun.nio.sctp.SctpChannel localSctpChannel = javaChannel();
/*     */     
/* 268 */     RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 269 */     if (localHandle == null) {
/* 270 */       this.allocHandle = (localHandle = config().getRecvByteBufAllocator().newHandle());
/*     */     }
/* 272 */     ByteBuf localByteBuf = localHandle.allocate(config().getAllocator());
/* 273 */     int i = 1;
/*     */     try {
/* 275 */       ByteBuffer localByteBuffer = localByteBuf.internalNioBuffer(localByteBuf.writerIndex(), localByteBuf.writableBytes());
/* 276 */       j = localByteBuffer.position();
/*     */       
/* 278 */       MessageInfo localMessageInfo = localSctpChannel.receive(localByteBuffer, null, this.notificationHandler);
/* 279 */       int m; int n; if (localMessageInfo == null) {
/* 280 */         return 0;
/*     */       }
/* 282 */       paramList.add(new SctpMessage(localMessageInfo, localByteBuf.writerIndex(localByteBuf.writerIndex() + localByteBuffer.position() - j)));
/* 283 */       i = 0;
/* 284 */       return 1;
/*     */     } catch (Throwable localThrowable) { int j;
/* 286 */       PlatformDependent.throwException(localThrowable);
/* 287 */       int k; return -1;
/*     */     } finally {
/* 289 */       int i1 = localByteBuf.readableBytes();
/* 290 */       localHandle.record(i1);
/* 291 */       if (i != 0) {
/* 292 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 299 */     SctpMessage localSctpMessage = (SctpMessage)paramObject;
/* 300 */     ByteBuf localByteBuf = localSctpMessage.content();
/* 301 */     int i = localByteBuf.readableBytes();
/* 302 */     if (i == 0) {
/* 303 */       return true;
/*     */     }
/*     */     
/* 306 */     ByteBufAllocator localByteBufAllocator = alloc();
/* 307 */     int j = localByteBuf.nioBufferCount() != 1 ? 1 : 0;
/* 308 */     if ((j == 0) && 
/* 309 */       (!localByteBuf.isDirect()) && (localByteBufAllocator.isDirectBufferPooled())) {
/* 310 */       j = 1;
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/* 314 */     if (j == 0) {
/* 315 */       localByteBuffer = localByteBuf.nioBuffer();
/*     */     } else {
/* 317 */       localByteBuf = localByteBufAllocator.directBuffer(i).writeBytes(localByteBuf);
/* 318 */       localByteBuffer = localByteBuf.nioBuffer();
/*     */     }
/* 320 */     MessageInfo localMessageInfo = MessageInfo.createOutgoing(association(), null, localSctpMessage.streamIdentifier());
/* 321 */     localMessageInfo.payloadProtocolID(localSctpMessage.protocolIdentifier());
/* 322 */     localMessageInfo.streamNumber(localSctpMessage.streamIdentifier());
/*     */     
/* 324 */     int k = javaChannel().send(localByteBuffer, localMessageInfo);
/* 325 */     return k > 0;
/*     */   }
/*     */   
/*     */   protected final Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 330 */     if ((paramObject instanceof SctpMessage)) {
/* 331 */       SctpMessage localSctpMessage = (SctpMessage)paramObject;
/* 332 */       ByteBuf localByteBuf = localSctpMessage.content();
/* 333 */       if ((localByteBuf.isDirect()) && (localByteBuf.nioBufferCount() == 1)) {
/* 334 */         return localSctpMessage;
/*     */       }
/*     */       
/* 337 */       return new SctpMessage(localSctpMessage.protocolIdentifier(), localSctpMessage.streamIdentifier(), newDirectBuffer(localSctpMessage, localByteBuf));
/*     */     }
/*     */     
/* 340 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + " (expected: " + StringUtil.simpleClassName(SctpMessage.class));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ChannelFuture bindAddress(InetAddress paramInetAddress)
/*     */   {
/* 347 */     return bindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 352 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 354 */         javaChannel().bindAddress(paramInetAddress);
/* 355 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 357 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 360 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 363 */           NioSctpChannel.this.bindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 367 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress paramInetAddress)
/*     */   {
/* 372 */     return unbindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 377 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 379 */         javaChannel().unbindAddress(paramInetAddress);
/* 380 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 382 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 385 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 388 */           NioSctpChannel.this.unbindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 392 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private final class NioSctpChannelConfig extends DefaultSctpChannelConfig {
/*     */     private NioSctpChannelConfig(NioSctpChannel paramNioSctpChannel, com.sun.nio.sctp.SctpChannel paramSctpChannel) {
/* 397 */       super(paramSctpChannel);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 402 */       NioSctpChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\nio\NioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */