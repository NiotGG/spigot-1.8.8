/*     */ package io.netty.channel.sctp.oio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.RecvByteBufAllocator.Handle;
/*     */ import io.netty.channel.oio.AbstractOioMessageChannel;
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
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ public class OioSctpChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements io.netty.channel.sctp.SctpChannel
/*     */ {
/*  64 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
/*     */   
/*     */ 
/*  67 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*  68 */   private static final String EXPECTED_TYPE = " (expected: " + StringUtil.simpleClassName(SctpMessage.class) + ')';
/*     */   
/*     */   private final com.sun.nio.sctp.SctpChannel ch;
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   private final Selector readSelector;
/*     */   private final Selector writeSelector;
/*     */   private final Selector connectSelector;
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static com.sun.nio.sctp.SctpChannel openChannel()
/*     */   {
/*     */     try
/*     */     {
/*  83 */       return com.sun.nio.sctp.SctpChannel.open();
/*     */     } catch (IOException localIOException) {
/*  85 */       throw new ChannelException("Failed to open a sctp channel.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public OioSctpChannel()
/*     */   {
/*  93 */     this(openChannel());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioSctpChannel(com.sun.nio.sctp.SctpChannel paramSctpChannel)
/*     */   {
/* 102 */     this(null, paramSctpChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OioSctpChannel(Channel paramChannel, com.sun.nio.sctp.SctpChannel paramSctpChannel)
/*     */   {
/* 113 */     super(paramChannel);
/* 114 */     this.ch = paramSctpChannel;
/* 115 */     int i = 0;
/*     */     try {
/* 117 */       paramSctpChannel.configureBlocking(false);
/* 118 */       this.readSelector = Selector.open();
/* 119 */       this.writeSelector = Selector.open();
/* 120 */       this.connectSelector = Selector.open();
/*     */       
/* 122 */       paramSctpChannel.register(this.readSelector, 1);
/* 123 */       paramSctpChannel.register(this.writeSelector, 4);
/* 124 */       paramSctpChannel.register(this.connectSelector, 8);
/*     */       
/* 126 */       this.config = new OioSctpChannelConfig(this, paramSctpChannel, null);
/* 127 */       this.notificationHandler = new SctpNotificationHandler(this);
/* 128 */       i = 1; return;
/*     */     } catch (Exception localException) {
/* 130 */       throw new ChannelException("failed to initialize a sctp channel", localException);
/*     */     } finally {
/* 132 */       if (i == 0) {
/*     */         try {
/* 134 */           paramSctpChannel.close();
/*     */         } catch (IOException localIOException2) {
/* 136 */           logger.warn("Failed to close a sctp channel.", localIOException2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress()
/*     */   {
/* 144 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress remoteAddress()
/*     */   {
/* 149 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */   
/*     */   public SctpServerChannel parent()
/*     */   {
/* 154 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */   
/*     */   public ChannelMetadata metadata()
/*     */   {
/* 159 */     return METADATA;
/*     */   }
/*     */   
/*     */   public SctpChannelConfig config()
/*     */   {
/* 164 */     return this.config;
/*     */   }
/*     */   
/*     */   public boolean isOpen()
/*     */   {
/* 169 */     return this.ch.isOpen();
/*     */   }
/*     */   
/*     */   protected int doReadMessages(List<Object> paramList) throws Exception
/*     */   {
/* 174 */     if (!this.readSelector.isOpen()) {
/* 175 */       return 0;
/*     */     }
/*     */     
/* 178 */     int i = 0;
/*     */     
/* 180 */     int j = this.readSelector.select(1000L);
/* 181 */     int k = j > 0 ? 1 : 0;
/*     */     
/* 183 */     if (k == 0) {
/* 184 */       return i;
/*     */     }
/*     */     
/* 187 */     Set localSet = this.readSelector.selectedKeys();
/*     */     try {
/* 189 */       for (SelectionKey localSelectionKey : localSet) {
/* 190 */         RecvByteBufAllocator.Handle localHandle = this.allocHandle;
/* 191 */         if (localHandle == null) {
/* 192 */           this.allocHandle = (localHandle = config().getRecvByteBufAllocator().newHandle());
/*     */         }
/* 194 */         ByteBuf localByteBuf = localHandle.allocate(config().getAllocator());
/* 195 */         int m = 1;
/*     */         try
/*     */         {
/* 198 */           ByteBuffer localByteBuffer = localByteBuf.nioBuffer(localByteBuf.writerIndex(), localByteBuf.writableBytes());
/* 199 */           MessageInfo localMessageInfo = this.ch.receive(localByteBuffer, null, this.notificationHandler);
/* 200 */           if (localMessageInfo == null) {
/* 201 */             int i2 = i;
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 211 */             int i3 = localByteBuf.readableBytes();
/* 212 */             localHandle.record(i3);
/* 213 */             if (m != 0) {
/* 214 */               localByteBuf.release();
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 219 */             return i2;
/*     */           }
/* 204 */           localByteBuffer.flip();
/* 205 */           paramList.add(new SctpMessage(localMessageInfo, localByteBuf.writerIndex(localByteBuf.writerIndex() + localByteBuffer.remaining())));
/* 206 */           m = 0;
/* 207 */           i++;
/*     */         } catch (Throwable localThrowable) { int n;
/* 209 */           PlatformDependent.throwException(localThrowable);
/*     */         } finally { int i1;
/* 211 */           int i4 = localByteBuf.readableBytes();
/* 212 */           localHandle.record(i4);
/* 213 */           if (m != 0) {
/* 214 */             localByteBuf.release();
/*     */           }
/*     */         }
/*     */       }
/*     */     } finally {
/* 219 */       localSet.clear();
/*     */     }
/* 221 */     return i;
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception
/*     */   {
/* 226 */     if (!this.writeSelector.isOpen()) {
/* 227 */       return;
/*     */     }
/* 229 */     int i = paramChannelOutboundBuffer.size();
/* 230 */     int j = this.writeSelector.select(1000L);
/* 231 */     if (j > 0) {
/* 232 */       Set localSet = this.writeSelector.selectedKeys();
/* 233 */       if (localSet.isEmpty()) {
/* 234 */         return;
/*     */       }
/* 236 */       Iterator localIterator = localSet.iterator();
/* 237 */       int k = 0;
/*     */       for (;;) {
/* 239 */         if (k == i)
/*     */         {
/* 241 */           return;
/*     */         }
/* 243 */         localIterator.next();
/* 244 */         localIterator.remove();
/*     */         
/* 246 */         SctpMessage localSctpMessage = (SctpMessage)paramChannelOutboundBuffer.current();
/* 247 */         if (localSctpMessage == null) {
/* 248 */           return;
/*     */         }
/*     */         
/* 251 */         ByteBuf localByteBuf = localSctpMessage.content();
/* 252 */         int m = localByteBuf.readableBytes();
/*     */         
/*     */         ByteBuffer localByteBuffer;
/* 255 */         if (localByteBuf.nioBufferCount() != -1) {
/* 256 */           localByteBuffer = localByteBuf.nioBuffer();
/*     */         } else {
/* 258 */           localByteBuffer = ByteBuffer.allocate(m);
/* 259 */           localByteBuf.getBytes(localByteBuf.readerIndex(), localByteBuffer);
/* 260 */           localByteBuffer.flip();
/*     */         }
/*     */         
/* 263 */         MessageInfo localMessageInfo = MessageInfo.createOutgoing(association(), null, localSctpMessage.streamIdentifier());
/* 264 */         localMessageInfo.payloadProtocolID(localSctpMessage.protocolIdentifier());
/* 265 */         localMessageInfo.streamNumber(localSctpMessage.streamIdentifier());
/*     */         
/* 267 */         this.ch.send(localByteBuffer, localMessageInfo);
/* 268 */         k++;
/* 269 */         paramChannelOutboundBuffer.remove();
/*     */         
/* 271 */         if (!localIterator.hasNext()) {
/* 272 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Object filterOutboundMessage(Object paramObject) throws Exception
/*     */   {
/* 280 */     if ((paramObject instanceof SctpMessage)) {
/* 281 */       return paramObject;
/*     */     }
/*     */     
/* 284 */     throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(paramObject) + EXPECTED_TYPE);
/*     */   }
/*     */   
/*     */   public Association association()
/*     */   {
/*     */     try
/*     */     {
/* 291 */       return this.ch.association();
/*     */     } catch (IOException localIOException) {}
/* 293 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 299 */     return (isOpen()) && (association() != null);
/*     */   }
/*     */   
/*     */   protected SocketAddress localAddress0()
/*     */   {
/*     */     try {
/* 305 */       Iterator localIterator = this.ch.getAllLocalAddresses().iterator();
/* 306 */       if (localIterator.hasNext()) {
/* 307 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 312 */     return null;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses()
/*     */   {
/*     */     try {
/* 318 */       Set localSet = this.ch.getAllLocalAddresses();
/* 319 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(localSet.size());
/* 320 */       for (SocketAddress localSocketAddress : localSet) {
/* 321 */         localLinkedHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/* 323 */       return localLinkedHashSet;
/*     */     } catch (Throwable localThrowable) {}
/* 325 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0()
/*     */   {
/*     */     try
/*     */     {
/* 332 */       Iterator localIterator = this.ch.getRemoteAddresses().iterator();
/* 333 */       if (localIterator.hasNext()) {
/* 334 */         return (SocketAddress)localIterator.next();
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/* 339 */     return null;
/*     */   }
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses()
/*     */   {
/*     */     try {
/* 345 */       Set localSet = this.ch.getRemoteAddresses();
/* 346 */       LinkedHashSet localLinkedHashSet = new LinkedHashSet(localSet.size());
/* 347 */       for (SocketAddress localSocketAddress : localSet) {
/* 348 */         localLinkedHashSet.add((InetSocketAddress)localSocketAddress);
/*     */       }
/* 350 */       return localLinkedHashSet;
/*     */     } catch (Throwable localThrowable) {}
/* 352 */     return Collections.emptySet();
/*     */   }
/*     */   
/*     */   protected void doBind(SocketAddress paramSocketAddress)
/*     */     throws Exception
/*     */   {
/* 358 */     this.ch.bind(paramSocketAddress);
/*     */   }
/*     */   
/*     */   protected void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception
/*     */   {
/* 364 */     if (paramSocketAddress2 != null) {
/* 365 */       this.ch.bind(paramSocketAddress2);
/*     */     }
/*     */     
/* 368 */     boolean bool = false;
/*     */     try {
/* 370 */       this.ch.connect(paramSocketAddress1);
/* 371 */       int i = 0;
/* 372 */       while (i == 0) {
/* 373 */         if (this.connectSelector.select(1000L) >= 0) {
/* 374 */           Set localSet = this.connectSelector.selectedKeys();
/* 375 */           for (SelectionKey localSelectionKey : localSet) {
/* 376 */             if (localSelectionKey.isConnectable()) {
/* 377 */               localSet.clear();
/* 378 */               i = 1;
/* 379 */               break;
/*     */             }
/*     */           }
/* 382 */           localSet.clear();
/*     */         }
/*     */       }
/* 385 */       bool = this.ch.finishConnect();
/*     */     } finally {
/* 387 */       if (!bool) {
/* 388 */         doClose();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doDisconnect() throws Exception
/*     */   {
/* 395 */     doClose();
/*     */   }
/*     */   
/*     */   protected void doClose() throws Exception
/*     */   {
/* 400 */     closeSelector("read", this.readSelector);
/* 401 */     closeSelector("write", this.writeSelector);
/* 402 */     closeSelector("connect", this.connectSelector);
/* 403 */     this.ch.close();
/*     */   }
/*     */   
/*     */   private static void closeSelector(String paramString, Selector paramSelector) {
/*     */     try {
/* 408 */       paramSelector.close();
/*     */     } catch (IOException localIOException) {
/* 410 */       logger.warn("Failed to close a " + paramString + " selector.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress paramInetAddress)
/*     */   {
/* 416 */     return bindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 421 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 423 */         this.ch.bindAddress(paramInetAddress);
/* 424 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 426 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 429 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 432 */           OioSctpChannel.this.bindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 436 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress paramInetAddress)
/*     */   {
/* 441 */     return unbindAddress(paramInetAddress, newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress paramInetAddress, final ChannelPromise paramChannelPromise)
/*     */   {
/* 446 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 448 */         this.ch.unbindAddress(paramInetAddress);
/* 449 */         paramChannelPromise.setSuccess();
/*     */       } catch (Throwable localThrowable) {
/* 451 */         paramChannelPromise.setFailure(localThrowable);
/*     */       }
/*     */     } else {
/* 454 */       eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 457 */           OioSctpChannel.this.unbindAddress(paramInetAddress, paramChannelPromise);
/*     */         }
/*     */       });
/*     */     }
/* 461 */     return paramChannelPromise;
/*     */   }
/*     */   
/*     */   private final class OioSctpChannelConfig extends DefaultSctpChannelConfig {
/*     */     private OioSctpChannelConfig(OioSctpChannel paramOioSctpChannel, com.sun.nio.sctp.SctpChannel paramSctpChannel) {
/* 466 */       super(paramSctpChannel);
/*     */     }
/*     */     
/*     */     protected void autoReadCleared()
/*     */     {
/* 471 */       OioSctpChannel.this.setReadPending(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\oio\OioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */