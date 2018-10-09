/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufUtil;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelException;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.channel.ChannelOutboundHandler;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.PendingWriteQueue;
/*      */ import io.netty.handler.codec.ByteToMessageDecoder;
/*      */ import io.netty.util.concurrent.DefaultPromise;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.io.IOException;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLEngineResult.HandshakeStatus;
/*      */ import javax.net.ssl.SSLEngineResult.Status;
/*      */ import javax.net.ssl.SSLException;
/*      */ import javax.net.ssl.SSLSession;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SslHandler
/*      */   extends ByteToMessageDecoder
/*      */   implements ChannelOutboundHandler
/*      */ {
/*      */   private static final InternalLogger logger;
/*      */   private static final Pattern IGNORABLE_CLASS_IN_STACK;
/*      */   private static final Pattern IGNORABLE_ERROR_MESSAGE;
/*      */   private static final SSLException SSLENGINE_CLOSED;
/*      */   private static final SSLException HANDSHAKE_TIMED_OUT;
/*      */   private static final ClosedChannelException CHANNEL_CLOSED;
/*      */   private volatile ChannelHandlerContext ctx;
/*      */   private final SSLEngine engine;
/*      */   private final int maxPacketBufferSize;
/*      */   private final Executor delegatedTaskExecutor;
/*      */   private final boolean wantsDirectBuffer;
/*      */   private final boolean wantsLargeOutboundNetworkBuffer;
/*      */   private boolean wantsInboundHeapBuffer;
/*      */   private final boolean startTls;
/*      */   private boolean sentFirstMessage;
/*      */   private boolean flushedBeforeHandshakeDone;
/*      */   private PendingWriteQueue pendingUnencryptedWrites;
/*      */   
/*      */   static
/*      */   {
/*  159 */     logger = InternalLoggerFactory.getInstance(SslHandler.class);
/*      */     
/*      */ 
/*  162 */     IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
/*      */     
/*  164 */     IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
/*      */     
/*      */ 
/*  167 */     SSLENGINE_CLOSED = new SSLException("SSLEngine closed already");
/*  168 */     HANDSHAKE_TIMED_OUT = new SSLException("handshake timed out");
/*  169 */     CHANNEL_CLOSED = new ClosedChannelException();
/*      */     
/*      */ 
/*  172 */     SSLENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  173 */     HANDSHAKE_TIMED_OUT.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  174 */     CHANNEL_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  210 */   private final LazyChannelPromise handshakePromise = new LazyChannelPromise(null);
/*  211 */   private final LazyChannelPromise sslCloseFuture = new LazyChannelPromise(null);
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean needsFlush;
/*      */   
/*      */ 
/*      */   private int packetLength;
/*      */   
/*      */ 
/*  221 */   private volatile long handshakeTimeoutMillis = 10000L;
/*  222 */   private volatile long closeNotifyTimeoutMillis = 3000L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SslHandler(SSLEngine paramSSLEngine)
/*      */   {
/*  230 */     this(paramSSLEngine, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public SslHandler(SSLEngine paramSSLEngine, boolean paramBoolean)
/*      */   {
/*  242 */     this(paramSSLEngine, paramBoolean, ImmediateExecutor.INSTANCE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public SslHandler(SSLEngine paramSSLEngine, Executor paramExecutor)
/*      */   {
/*  250 */     this(paramSSLEngine, false, paramExecutor);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public SslHandler(SSLEngine paramSSLEngine, boolean paramBoolean, Executor paramExecutor)
/*      */   {
/*  258 */     if (paramSSLEngine == null) {
/*  259 */       throw new NullPointerException("engine");
/*      */     }
/*  261 */     if (paramExecutor == null) {
/*  262 */       throw new NullPointerException("delegatedTaskExecutor");
/*      */     }
/*  264 */     this.engine = paramSSLEngine;
/*  265 */     this.delegatedTaskExecutor = paramExecutor;
/*  266 */     this.startTls = paramBoolean;
/*  267 */     this.maxPacketBufferSize = paramSSLEngine.getSession().getPacketBufferSize();
/*      */     
/*  269 */     this.wantsDirectBuffer = (paramSSLEngine instanceof OpenSslEngine);
/*  270 */     this.wantsLargeOutboundNetworkBuffer = (!(paramSSLEngine instanceof OpenSslEngine));
/*      */   }
/*      */   
/*      */   public long getHandshakeTimeoutMillis() {
/*  274 */     return this.handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeout(long paramLong, TimeUnit paramTimeUnit) {
/*  278 */     if (paramTimeUnit == null) {
/*  279 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  282 */     setHandshakeTimeoutMillis(paramTimeUnit.toMillis(paramLong));
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeoutMillis(long paramLong) {
/*  286 */     if (paramLong < 0L) {
/*  287 */       throw new IllegalArgumentException("handshakeTimeoutMillis: " + paramLong + " (expected: >= 0)");
/*      */     }
/*      */     
/*  290 */     this.handshakeTimeoutMillis = paramLong;
/*      */   }
/*      */   
/*      */   public long getCloseNotifyTimeoutMillis() {
/*  294 */     return this.closeNotifyTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeout(long paramLong, TimeUnit paramTimeUnit) {
/*  298 */     if (paramTimeUnit == null) {
/*  299 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  302 */     setCloseNotifyTimeoutMillis(paramTimeUnit.toMillis(paramLong));
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeoutMillis(long paramLong) {
/*  306 */     if (paramLong < 0L) {
/*  307 */       throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + paramLong + " (expected: >= 0)");
/*      */     }
/*      */     
/*  310 */     this.closeNotifyTimeoutMillis = paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public SSLEngine engine()
/*      */   {
/*  317 */     return this.engine;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Future<Channel> handshakeFuture()
/*      */   {
/*  324 */     return this.handshakePromise;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ChannelFuture close()
/*      */   {
/*  332 */     return close(this.ctx.newPromise());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ChannelFuture close(final ChannelPromise paramChannelPromise)
/*      */   {
/*  339 */     final ChannelHandlerContext localChannelHandlerContext = this.ctx;
/*  340 */     localChannelHandlerContext.executor().execute(new Runnable()
/*      */     {
/*      */       public void run() {
/*  343 */         SslHandler.this.engine.closeOutbound();
/*      */         try {
/*  345 */           SslHandler.this.write(localChannelHandlerContext, Unpooled.EMPTY_BUFFER, paramChannelPromise);
/*  346 */           SslHandler.this.flush(localChannelHandlerContext);
/*      */         } catch (Exception localException) {
/*  348 */           if (!paramChannelPromise.tryFailure(localException)) {
/*  349 */             SslHandler.logger.warn("flush() raised a masked exception.", localException);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*  354 */     });
/*  355 */     return paramChannelPromise;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Future<Channel> sslCloseFuture()
/*      */   {
/*  367 */     return this.sslCloseFuture;
/*      */   }
/*      */   
/*      */   public void handlerRemoved0(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */   {
/*  372 */     if (!this.pendingUnencryptedWrites.isEmpty())
/*      */     {
/*  374 */       this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
/*      */     }
/*      */   }
/*      */   
/*      */   public void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise) throws Exception
/*      */   {
/*  380 */     paramChannelHandlerContext.bind(paramSocketAddress, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*      */     throws Exception
/*      */   {
/*  386 */     paramChannelHandlerContext.connect(paramSocketAddress1, paramSocketAddress2, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*      */   {
/*  391 */     paramChannelHandlerContext.deregister(paramChannelPromise);
/*      */   }
/*      */   
/*      */   public void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*      */     throws Exception
/*      */   {
/*  397 */     closeOutboundAndChannel(paramChannelHandlerContext, paramChannelPromise, true);
/*      */   }
/*      */   
/*      */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*      */     throws Exception
/*      */   {
/*  403 */     closeOutboundAndChannel(paramChannelHandlerContext, paramChannelPromise, false);
/*      */   }
/*      */   
/*      */   public void read(ChannelHandlerContext paramChannelHandlerContext)
/*      */   {
/*  408 */     paramChannelHandlerContext.read();
/*      */   }
/*      */   
/*      */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*      */   {
/*  413 */     this.pendingUnencryptedWrites.add(paramObject, paramChannelPromise);
/*      */   }
/*      */   
/*      */ 
/*      */   public void flush(ChannelHandlerContext paramChannelHandlerContext)
/*      */     throws Exception
/*      */   {
/*  420 */     if ((this.startTls) && (!this.sentFirstMessage)) {
/*  421 */       this.sentFirstMessage = true;
/*  422 */       this.pendingUnencryptedWrites.removeAndWriteAll();
/*  423 */       paramChannelHandlerContext.flush();
/*  424 */       return;
/*      */     }
/*  426 */     if (this.pendingUnencryptedWrites.isEmpty()) {
/*  427 */       this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, paramChannelHandlerContext.voidPromise());
/*      */     }
/*  429 */     if (!this.handshakePromise.isDone()) {
/*  430 */       this.flushedBeforeHandshakeDone = true;
/*      */     }
/*  432 */     wrap(paramChannelHandlerContext, false);
/*  433 */     paramChannelHandlerContext.flush();
/*      */   }
/*      */   
/*      */   private void wrap(ChannelHandlerContext paramChannelHandlerContext, boolean paramBoolean) throws SSLException {
/*  437 */     ByteBuf localByteBuf1 = null;
/*  438 */     ChannelPromise localChannelPromise = null;
/*      */     try {
/*      */       for (;;) {
/*  441 */         Object localObject1 = this.pendingUnencryptedWrites.current();
/*  442 */         if (localObject1 == null) {
/*      */           break;
/*      */         }
/*      */         
/*  446 */         if (!(localObject1 instanceof ByteBuf)) {
/*  447 */           this.pendingUnencryptedWrites.removeAndWrite();
/*      */         }
/*      */         else
/*      */         {
/*  451 */           ByteBuf localByteBuf2 = (ByteBuf)localObject1;
/*  452 */           if (localByteBuf1 == null) {
/*  453 */             localByteBuf1 = allocateOutNetBuf(paramChannelHandlerContext, localByteBuf2.readableBytes());
/*      */           }
/*      */           
/*  456 */           SSLEngineResult localSSLEngineResult = wrap(this.engine, localByteBuf2, localByteBuf1);
/*      */           
/*  458 */           if (!localByteBuf2.isReadable()) {
/*  459 */             localChannelPromise = this.pendingUnencryptedWrites.remove();
/*      */           } else {
/*  461 */             localChannelPromise = null;
/*      */           }
/*      */           
/*  464 */           if (localSSLEngineResult.getStatus() == SSLEngineResult.Status.CLOSED)
/*      */           {
/*      */ 
/*  467 */             this.pendingUnencryptedWrites.removeAndFailAll(SSLENGINE_CLOSED); return;
/*      */           }
/*      */           
/*  470 */           switch (localSSLEngineResult.getHandshakeStatus()) {
/*      */           case NEED_TASK: 
/*  472 */             runDelegatedTasks();
/*  473 */             break;
/*      */           case FINISHED: 
/*  475 */             setHandshakeSuccess();
/*      */           
/*      */           case NOT_HANDSHAKING: 
/*  478 */             setHandshakeSuccessIfStillHandshaking();
/*      */           
/*      */           case NEED_WRAP: 
/*  481 */             finishWrap(paramChannelHandlerContext, localByteBuf1, localChannelPromise, paramBoolean);
/*  482 */             localChannelPromise = null;
/*  483 */             localByteBuf1 = null;
/*  484 */             break;
/*      */           case NEED_UNWRAP: 
/*      */             return;
/*      */           default: 
/*  488 */             throw new IllegalStateException("Unknown handshake status: " + localSSLEngineResult.getHandshakeStatus());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (SSLException localSSLException) {
/*  494 */       setHandshakeFailure(localSSLException);
/*  495 */       throw localSSLException;
/*      */     } finally {
/*  497 */       finishWrap(paramChannelHandlerContext, localByteBuf1, localChannelPromise, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */   private void finishWrap(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, ChannelPromise paramChannelPromise, boolean paramBoolean) {
/*  502 */     if (paramByteBuf == null) {
/*  503 */       paramByteBuf = Unpooled.EMPTY_BUFFER;
/*  504 */     } else if (!paramByteBuf.isReadable()) {
/*  505 */       paramByteBuf.release();
/*  506 */       paramByteBuf = Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*  509 */     if (paramChannelPromise != null) {
/*  510 */       paramChannelHandlerContext.write(paramByteBuf, paramChannelPromise);
/*      */     } else {
/*  512 */       paramChannelHandlerContext.write(paramByteBuf);
/*      */     }
/*      */     
/*  515 */     if (paramBoolean) {
/*  516 */       this.needsFlush = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void wrapNonAppData(ChannelHandlerContext paramChannelHandlerContext, boolean paramBoolean) throws SSLException {
/*  521 */     ByteBuf localByteBuf = null;
/*      */     try {
/*      */       for (;;) {
/*  524 */         if (localByteBuf == null) {
/*  525 */           localByteBuf = allocateOutNetBuf(paramChannelHandlerContext, 0);
/*      */         }
/*  527 */         SSLEngineResult localSSLEngineResult = wrap(this.engine, Unpooled.EMPTY_BUFFER, localByteBuf);
/*      */         
/*  529 */         if (localSSLEngineResult.bytesProduced() > 0) {
/*  530 */           paramChannelHandlerContext.write(localByteBuf);
/*  531 */           if (paramBoolean) {
/*  532 */             this.needsFlush = true;
/*      */           }
/*  534 */           localByteBuf = null;
/*      */         }
/*      */         
/*  537 */         switch (localSSLEngineResult.getHandshakeStatus()) {
/*      */         case FINISHED: 
/*  539 */           setHandshakeSuccess();
/*  540 */           break;
/*      */         case NEED_TASK: 
/*  542 */           runDelegatedTasks();
/*  543 */           break;
/*      */         case NEED_UNWRAP: 
/*  545 */           if (!paramBoolean) {
/*  546 */             unwrapNonAppData(paramChannelHandlerContext);
/*      */           }
/*      */           break;
/*      */         case NEED_WRAP: 
/*      */           break;
/*      */         case NOT_HANDSHAKING: 
/*  552 */           setHandshakeSuccessIfStillHandshaking();
/*      */           
/*      */ 
/*  555 */           if (!paramBoolean) {
/*  556 */             unwrapNonAppData(paramChannelHandlerContext);
/*      */           }
/*      */           break;
/*      */         default: 
/*  560 */           throw new IllegalStateException("Unknown handshake status: " + localSSLEngineResult.getHandshakeStatus());
/*      */         }
/*      */         
/*  563 */         if (localSSLEngineResult.bytesProduced() == 0) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     } catch (SSLException localSSLException) {
/*  568 */       setHandshakeFailure(localSSLException);
/*  569 */       throw localSSLException;
/*      */     } finally {
/*  571 */       if (localByteBuf != null) {
/*  572 */         localByteBuf.release();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private SSLEngineResult wrap(SSLEngine paramSSLEngine, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws SSLException {
/*  578 */     Object localObject = paramByteBuf1.nioBuffer();
/*  579 */     ByteBuffer localByteBuffer; if (!((ByteBuffer)localObject).isDirect()) {
/*  580 */       localByteBuffer = ByteBuffer.allocateDirect(((ByteBuffer)localObject).remaining());
/*  581 */       localByteBuffer.put((ByteBuffer)localObject).flip();
/*  582 */       localObject = localByteBuffer;
/*      */     }
/*      */     for (;;)
/*      */     {
/*  586 */       localByteBuffer = paramByteBuf2.nioBuffer(paramByteBuf2.writerIndex(), paramByteBuf2.writableBytes());
/*  587 */       SSLEngineResult localSSLEngineResult = paramSSLEngine.wrap((ByteBuffer)localObject, localByteBuffer);
/*  588 */       paramByteBuf1.skipBytes(localSSLEngineResult.bytesConsumed());
/*  589 */       paramByteBuf2.writerIndex(paramByteBuf2.writerIndex() + localSSLEngineResult.bytesProduced());
/*      */       
/*  591 */       switch (localSSLEngineResult.getStatus()) {
/*      */       case BUFFER_OVERFLOW: 
/*  593 */         paramByteBuf2.ensureWritable(this.maxPacketBufferSize);
/*  594 */         break;
/*      */       default: 
/*  596 */         return localSSLEngineResult;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext)
/*      */     throws Exception
/*      */   {
/*  605 */     setHandshakeFailure(CHANNEL_CLOSED);
/*  606 */     super.channelInactive(paramChannelHandlerContext);
/*      */   }
/*      */   
/*      */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*      */   {
/*  611 */     if (ignoreException(paramThrowable))
/*      */     {
/*      */ 
/*  614 */       if (logger.isDebugEnabled()) {
/*  615 */         logger.debug("Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", paramThrowable);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  622 */       if (paramChannelHandlerContext.channel().isActive()) {
/*  623 */         paramChannelHandlerContext.close();
/*      */       }
/*      */     } else {
/*  626 */       paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean ignoreException(Throwable paramThrowable)
/*      */   {
/*  640 */     if ((!(paramThrowable instanceof SSLException)) && ((paramThrowable instanceof IOException)) && (this.sslCloseFuture.isDone())) {
/*  641 */       String str1 = String.valueOf(paramThrowable.getMessage()).toLowerCase();
/*      */       
/*      */ 
/*      */ 
/*  645 */       if (IGNORABLE_ERROR_MESSAGE.matcher(str1).matches()) {
/*  646 */         return true;
/*      */       }
/*      */       
/*      */ 
/*  650 */       StackTraceElement[] arrayOfStackTraceElement1 = paramThrowable.getStackTrace();
/*  651 */       for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
/*  652 */         String str2 = localStackTraceElement.getClassName();
/*  653 */         String str3 = localStackTraceElement.getMethodName();
/*      */         
/*      */ 
/*  656 */         if (!str2.startsWith("io.netty."))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  661 */           if ("read".equals(str3))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  667 */             if (IGNORABLE_CLASS_IN_STACK.matcher(str2).matches()) {
/*  668 */               return true;
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */             try
/*      */             {
/*  675 */               Class localClass = PlatformDependent.getClassLoader(getClass()).loadClass(str2);
/*      */               
/*  677 */               if ((SocketChannel.class.isAssignableFrom(localClass)) || (DatagramChannel.class.isAssignableFrom(localClass)))
/*      */               {
/*  679 */                 return true;
/*      */               }
/*      */               
/*      */ 
/*  683 */               if ((PlatformDependent.javaVersion() >= 7) && ("com.sun.nio.sctp.SctpChannel".equals(localClass.getSuperclass().getName())))
/*      */               {
/*  685 */                 return true;
/*      */               }
/*      */             }
/*      */             catch (ClassNotFoundException localClassNotFoundException) {}
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  693 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEncrypted(ByteBuf paramByteBuf)
/*      */   {
/*  709 */     if (paramByteBuf.readableBytes() < 5) {
/*  710 */       throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
/*      */     }
/*  712 */     return getEncryptedPacketLength(paramByteBuf, paramByteBuf.readerIndex()) != -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getEncryptedPacketLength(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/*  729 */     int i = 0;
/*      */     
/*      */     int j;
/*      */     
/*  733 */     switch (paramByteBuf.getUnsignedByte(paramInt)) {
/*      */     case 20: 
/*      */     case 21: 
/*      */     case 22: 
/*      */     case 23: 
/*  738 */       j = 1;
/*  739 */       break;
/*      */     
/*      */     default: 
/*  742 */       j = 0;
/*      */     }
/*      */     int k;
/*  745 */     if (j != 0)
/*      */     {
/*  747 */       k = paramByteBuf.getUnsignedByte(paramInt + 1);
/*  748 */       if (k == 3)
/*      */       {
/*  750 */         i = paramByteBuf.getUnsignedShort(paramInt + 3) + 5;
/*  751 */         if (i <= 5)
/*      */         {
/*  753 */           j = 0;
/*      */         }
/*      */       }
/*      */       else {
/*  757 */         j = 0;
/*      */       }
/*      */     }
/*      */     
/*  761 */     if (j == 0)
/*      */     {
/*  763 */       k = 1;
/*  764 */       int m = (paramByteBuf.getUnsignedByte(paramInt) & 0x80) != 0 ? 2 : 3;
/*  765 */       int n = paramByteBuf.getUnsignedByte(paramInt + m + 1);
/*  766 */       if ((n == 2) || (n == 3))
/*      */       {
/*  768 */         if (m == 2) {
/*  769 */           i = (paramByteBuf.getShort(paramInt) & 0x7FFF) + 2;
/*      */         } else {
/*  771 */           i = (paramByteBuf.getShort(paramInt) & 0x3FFF) + 3;
/*      */         }
/*  773 */         if (i <= m) {
/*  774 */           k = 0;
/*      */         }
/*      */       } else {
/*  777 */         k = 0;
/*      */       }
/*      */       
/*  780 */       if (k == 0) {
/*  781 */         return -1;
/*      */       }
/*      */     }
/*  784 */     return i;
/*      */   }
/*      */   
/*      */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*      */     throws SSLException
/*      */   {
/*  790 */     int i = paramByteBuf.readerIndex();
/*  791 */     int j = paramByteBuf.writerIndex();
/*  792 */     int k = i;
/*  793 */     int m = 0;
/*      */     
/*      */ 
/*  796 */     if (this.packetLength > 0) {
/*  797 */       if (j - i < this.packetLength) {
/*  798 */         return;
/*      */       }
/*  800 */       k += this.packetLength;
/*  801 */       m = this.packetLength;
/*  802 */       this.packetLength = 0;
/*      */     }
/*      */     
/*      */ 
/*  806 */     int n = 0;
/*      */     
/*  808 */     while (m < 18713) {
/*  809 */       int i1 = j - k;
/*  810 */       if (i1 < 5) {
/*      */         break;
/*      */       }
/*      */       
/*  814 */       int i2 = getEncryptedPacketLength(paramByteBuf, k);
/*  815 */       if (i2 == -1) {
/*  816 */         n = 1;
/*  817 */         break;
/*      */       }
/*      */       
/*  820 */       assert (i2 > 0);
/*      */       
/*  822 */       if (i2 > i1)
/*      */       {
/*  824 */         this.packetLength = i2;
/*  825 */         break;
/*      */       }
/*      */       
/*  828 */       int i3 = m + i2;
/*  829 */       if (i3 > 18713) {
/*      */         break;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  836 */       k += i2;
/*  837 */       m = i3;
/*      */     }
/*      */     Object localObject;
/*  840 */     if (m > 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  852 */       paramByteBuf.skipBytes(m);
/*  853 */       localObject = paramByteBuf.nioBuffer(i, m);
/*  854 */       unwrap(paramChannelHandlerContext, (ByteBuffer)localObject, m);
/*  855 */       assert ((!((ByteBuffer)localObject).hasRemaining()) || (this.engine.isInboundDone()));
/*      */     }
/*      */     
/*  858 */     if (n != 0)
/*      */     {
/*  860 */       localObject = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(paramByteBuf));
/*      */       
/*  862 */       paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/*  863 */       paramChannelHandlerContext.fireExceptionCaught((Throwable)localObject);
/*  864 */       setHandshakeFailure((Throwable)localObject);
/*      */     }
/*      */   }
/*      */   
/*      */   public void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */   {
/*  870 */     if (this.needsFlush) {
/*  871 */       this.needsFlush = false;
/*  872 */       paramChannelHandlerContext.flush();
/*      */     }
/*  874 */     super.channelReadComplete(paramChannelHandlerContext);
/*      */   }
/*      */   
/*      */ 
/*      */   private void unwrapNonAppData(ChannelHandlerContext paramChannelHandlerContext)
/*      */     throws SSLException
/*      */   {
/*  881 */     unwrap(paramChannelHandlerContext, Unpooled.EMPTY_BUFFER.nioBuffer(), 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void unwrap(ChannelHandlerContext paramChannelHandlerContext, ByteBuffer paramByteBuffer, int paramInt)
/*      */     throws SSLException
/*      */   {
/*  893 */     int i = paramByteBuffer.position();
/*  894 */     ByteBuf localByteBuf1; ByteBuffer localByteBuffer; if ((this.wantsInboundHeapBuffer) && (paramByteBuffer.isDirect())) {
/*  895 */       localByteBuf1 = paramChannelHandlerContext.alloc().heapBuffer(paramByteBuffer.limit() - i);
/*  896 */       localByteBuf1.writeBytes(paramByteBuffer);
/*  897 */       localByteBuffer = paramByteBuffer;
/*  898 */       paramByteBuffer = localByteBuf1.nioBuffer();
/*      */     } else {
/*  900 */       localByteBuffer = null;
/*  901 */       localByteBuf1 = null;
/*      */     }
/*      */     
/*  904 */     int j = 0;
/*  905 */     ByteBuf localByteBuf2 = allocate(paramChannelHandlerContext, paramInt);
/*      */     try {
/*      */       for (;;) {
/*  908 */         SSLEngineResult localSSLEngineResult = unwrap(this.engine, paramByteBuffer, localByteBuf2);
/*  909 */         SSLEngineResult.Status localStatus = localSSLEngineResult.getStatus();
/*  910 */         SSLEngineResult.HandshakeStatus localHandshakeStatus = localSSLEngineResult.getHandshakeStatus();
/*  911 */         int k = localSSLEngineResult.bytesProduced();
/*  912 */         int m = localSSLEngineResult.bytesConsumed();
/*      */         
/*  914 */         if (localStatus == SSLEngineResult.Status.CLOSED)
/*      */         {
/*  916 */           this.sslCloseFuture.trySuccess(paramChannelHandlerContext.channel());
/*      */         }
/*      */         else
/*      */         {
/*  920 */           switch (localHandshakeStatus) {
/*      */           case NEED_UNWRAP: 
/*      */             break;
/*      */           case NEED_WRAP: 
/*  924 */             wrapNonAppData(paramChannelHandlerContext, true);
/*  925 */             break;
/*      */           case NEED_TASK: 
/*  927 */             runDelegatedTasks();
/*  928 */             break;
/*      */           case FINISHED: 
/*  930 */             setHandshakeSuccess();
/*  931 */             j = 1;
/*  932 */             break;
/*      */           case NOT_HANDSHAKING: 
/*  934 */             if (setHandshakeSuccessIfStillHandshaking()) {
/*  935 */               j = 1;
/*      */ 
/*      */             }
/*  938 */             else if (this.flushedBeforeHandshakeDone)
/*      */             {
/*      */ 
/*      */ 
/*  942 */               this.flushedBeforeHandshakeDone = false;
/*  943 */               j = 1;
/*      */             }
/*      */             
/*      */             break;
/*      */           default: 
/*  948 */             throw new IllegalStateException("Unknown handshake status: " + localHandshakeStatus);
/*      */             
/*      */ 
/*  951 */             if ((localStatus == SSLEngineResult.Status.BUFFER_UNDERFLOW) || ((m == 0) && (k == 0)))
/*      */               break label297;
/*      */           } }
/*      */       }
/*      */       label297:
/*  956 */       if (j != 0) {
/*  957 */         wrap(paramChannelHandlerContext, true);
/*      */       }
/*      */     } catch (SSLException localSSLException) {
/*  960 */       setHandshakeFailure(localSSLException);
/*  961 */       throw localSSLException;
/*      */     }
/*      */     finally
/*      */     {
/*  965 */       if (localByteBuf1 != null) {
/*  966 */         localByteBuffer.position(i + paramByteBuffer.position());
/*  967 */         localByteBuf1.release();
/*      */       }
/*      */       
/*  970 */       if (localByteBuf2.isReadable()) {
/*  971 */         paramChannelHandlerContext.fireChannelRead(localByteBuf2);
/*      */       } else {
/*  973 */         localByteBuf2.release();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static SSLEngineResult unwrap(SSLEngine paramSSLEngine, ByteBuffer paramByteBuffer, ByteBuf paramByteBuf) throws SSLException {
/*  979 */     int i = 0;
/*      */     for (;;) {
/*  981 */       ByteBuffer localByteBuffer = paramByteBuf.nioBuffer(paramByteBuf.writerIndex(), paramByteBuf.writableBytes());
/*  982 */       SSLEngineResult localSSLEngineResult = paramSSLEngine.unwrap(paramByteBuffer, localByteBuffer);
/*  983 */       paramByteBuf.writerIndex(paramByteBuf.writerIndex() + localSSLEngineResult.bytesProduced());
/*  984 */       switch (localSSLEngineResult.getStatus()) {
/*      */       case BUFFER_OVERFLOW: 
/*  986 */         int j = paramSSLEngine.getSession().getApplicationBufferSize();
/*  987 */         switch (i++) {
/*      */         case 0: 
/*  989 */           paramByteBuf.ensureWritable(Math.min(j, paramByteBuffer.remaining()));
/*  990 */           break;
/*      */         default: 
/*  992 */           paramByteBuf.ensureWritable(j);
/*      */         }
/*  994 */         break;
/*      */       default: 
/*  996 */         return localSSLEngineResult;
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void runDelegatedTasks()
/*      */   {
/* 1008 */     if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
/*      */       for (;;) {
/* 1010 */         localObject1 = this.engine.getDelegatedTask();
/* 1011 */         if (localObject1 == null) {
/*      */           break;
/*      */         }
/*      */         
/* 1015 */         ((Runnable)localObject1).run();
/*      */       }
/*      */     }
/* 1018 */     final Object localObject1 = new ArrayList(2);
/*      */     for (;;) {
/* 1020 */       localObject2 = this.engine.getDelegatedTask();
/* 1021 */       if (localObject2 == null) {
/*      */         break;
/*      */       }
/*      */       
/* 1025 */       ((List)localObject1).add(localObject2);
/*      */     }
/*      */     
/* 1028 */     if (((List)localObject1).isEmpty()) {
/* 1029 */       return;
/*      */     }
/*      */     
/* 1032 */     final Object localObject2 = new CountDownLatch(1);
/* 1033 */     this.delegatedTaskExecutor.execute(new Runnable()
/*      */     {
/*      */       public void run() {
/*      */         try {
/* 1037 */           for (Runnable localRunnable : localObject1) {
/* 1038 */             localRunnable.run();
/*      */           }
/*      */         } catch (Exception localException) {
/* 1041 */           SslHandler.this.ctx.fireExceptionCaught(localException);
/*      */         } finally {
/* 1043 */           localObject2.countDown();
/*      */         }
/*      */         
/*      */       }
/* 1047 */     });
/* 1048 */     int i = 0;
/* 1049 */     while (((CountDownLatch)localObject2).getCount() != 0L) {
/*      */       try {
/* 1051 */         ((CountDownLatch)localObject2).await();
/*      */       }
/*      */       catch (InterruptedException localInterruptedException) {
/* 1054 */         i = 1;
/*      */       }
/*      */     }
/*      */     
/* 1058 */     if (i != 0) {
/* 1059 */       Thread.currentThread().interrupt();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean setHandshakeSuccessIfStillHandshaking()
/*      */   {
/* 1072 */     if (!this.handshakePromise.isDone()) {
/* 1073 */       setHandshakeSuccess();
/* 1074 */       return true;
/*      */     }
/* 1076 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setHandshakeSuccess()
/*      */   {
/* 1084 */     String str = String.valueOf(this.engine.getSession().getCipherSuite());
/* 1085 */     if ((!this.wantsDirectBuffer) && ((str.contains("_GCM_")) || (str.contains("-GCM-")))) {
/* 1086 */       this.wantsInboundHeapBuffer = true;
/*      */     }
/*      */     
/* 1089 */     if (this.handshakePromise.trySuccess(this.ctx.channel())) {
/* 1090 */       if (logger.isDebugEnabled()) {
/* 1091 */         logger.debug(this.ctx.channel() + " HANDSHAKEN: " + this.engine.getSession().getCipherSuite());
/*      */       }
/* 1093 */       this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setHandshakeFailure(Throwable paramThrowable)
/*      */   {
/* 1103 */     this.engine.closeOutbound();
/*      */     try
/*      */     {
/* 1106 */       this.engine.closeInbound();
/*      */ 
/*      */     }
/*      */     catch (SSLException localSSLException)
/*      */     {
/*      */ 
/* 1112 */       String str = localSSLException.getMessage();
/* 1113 */       if ((str == null) || (!str.contains("possible truncation attack"))) {
/* 1114 */         logger.debug("SSLEngine.closeInbound() raised an exception.", localSSLException);
/*      */       }
/*      */     }
/* 1117 */     notifyHandshakeFailure(paramThrowable);
/* 1118 */     this.pendingUnencryptedWrites.removeAndFailAll(paramThrowable);
/*      */   }
/*      */   
/*      */   private void notifyHandshakeFailure(Throwable paramThrowable) {
/* 1122 */     if (this.handshakePromise.tryFailure(paramThrowable)) {
/* 1123 */       this.ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(paramThrowable));
/* 1124 */       this.ctx.close();
/*      */     }
/*      */   }
/*      */   
/*      */   private void closeOutboundAndChannel(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise, boolean paramBoolean) throws Exception
/*      */   {
/* 1130 */     if (!paramChannelHandlerContext.channel().isActive()) {
/* 1131 */       if (paramBoolean) {
/* 1132 */         paramChannelHandlerContext.disconnect(paramChannelPromise);
/*      */       } else {
/* 1134 */         paramChannelHandlerContext.close(paramChannelPromise);
/*      */       }
/* 1136 */       return;
/*      */     }
/*      */     
/* 1139 */     this.engine.closeOutbound();
/*      */     
/* 1141 */     ChannelPromise localChannelPromise = paramChannelHandlerContext.newPromise();
/* 1142 */     write(paramChannelHandlerContext, Unpooled.EMPTY_BUFFER, localChannelPromise);
/* 1143 */     flush(paramChannelHandlerContext);
/* 1144 */     safeClose(paramChannelHandlerContext, localChannelPromise, paramChannelPromise);
/*      */   }
/*      */   
/*      */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*      */   {
/* 1149 */     this.ctx = paramChannelHandlerContext;
/* 1150 */     this.pendingUnencryptedWrites = new PendingWriteQueue(paramChannelHandlerContext);
/*      */     
/* 1152 */     if ((paramChannelHandlerContext.channel().isActive()) && (this.engine.getUseClientMode()))
/*      */     {
/*      */ 
/* 1155 */       handshake();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private Future<Channel> handshake()
/*      */   {
/*      */     final io.netty.util.concurrent.ScheduledFuture localScheduledFuture;
/*      */     
/* 1164 */     if (this.handshakeTimeoutMillis > 0L) {
/* 1165 */       localScheduledFuture = this.ctx.executor().schedule(new Runnable()
/*      */       {
/*      */         public void run() {
/* 1168 */           if (SslHandler.this.handshakePromise.isDone()) {
/* 1169 */             return;
/*      */           }
/* 1171 */           SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT); } }, this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     }
/*      */     else
/*      */     {
/* 1175 */       localScheduledFuture = null;
/*      */     }
/*      */     
/* 1178 */     this.handshakePromise.addListener(new GenericFutureListener()
/*      */     {
/*      */       public void operationComplete(Future<Channel> paramAnonymousFuture) throws Exception {
/* 1181 */         if (localScheduledFuture != null) {
/* 1182 */           localScheduledFuture.cancel(false);
/*      */         }
/*      */       }
/*      */     });
/*      */     try {
/* 1187 */       this.engine.beginHandshake();
/* 1188 */       wrapNonAppData(this.ctx, false);
/* 1189 */       this.ctx.flush();
/*      */     } catch (Exception localException) {
/* 1191 */       notifyHandshakeFailure(localException);
/*      */     }
/* 1193 */     return this.handshakePromise;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void channelActive(final ChannelHandlerContext paramChannelHandlerContext)
/*      */     throws Exception
/*      */   {
/* 1201 */     if ((!this.startTls) && (this.engine.getUseClientMode()))
/*      */     {
/*      */ 
/* 1204 */       handshake().addListener(new GenericFutureListener()
/*      */       {
/*      */         public void operationComplete(Future<Channel> paramAnonymousFuture) throws Exception {
/* 1207 */           if (!paramAnonymousFuture.isSuccess()) {
/* 1208 */             SslHandler.logger.debug("Failed to complete handshake", paramAnonymousFuture.cause());
/* 1209 */             paramChannelHandlerContext.close();
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/* 1214 */     paramChannelHandlerContext.fireChannelActive();
/*      */   }
/*      */   
/*      */ 
/*      */   private void safeClose(final ChannelHandlerContext paramChannelHandlerContext, ChannelFuture paramChannelFuture, final ChannelPromise paramChannelPromise)
/*      */   {
/* 1220 */     if (!paramChannelHandlerContext.channel().isActive()) {
/* 1221 */       paramChannelHandlerContext.close(paramChannelPromise); return;
/*      */     }
/*      */     
/*      */     final io.netty.util.concurrent.ScheduledFuture localScheduledFuture;
/*      */     
/* 1226 */     if (this.closeNotifyTimeoutMillis > 0L)
/*      */     {
/* 1228 */       localScheduledFuture = paramChannelHandlerContext.executor().schedule(new Runnable()
/*      */       {
/*      */         public void run() {
/* 1231 */           SslHandler.logger.warn(paramChannelHandlerContext.channel() + " last write attempt timed out." + " Force-closing the connection.");
/*      */           
/*      */ 
/* 1234 */           paramChannelHandlerContext.close(paramChannelPromise); } }, this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     }
/*      */     else
/*      */     {
/* 1238 */       localScheduledFuture = null;
/*      */     }
/*      */     
/*      */ 
/* 1242 */     paramChannelFuture.addListener(new ChannelFutureListener()
/*      */     {
/*      */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception
/*      */       {
/* 1246 */         if (localScheduledFuture != null) {
/* 1247 */           localScheduledFuture.cancel(false);
/*      */         }
/*      */         
/*      */ 
/* 1251 */         paramChannelHandlerContext.close(paramChannelPromise);
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private ByteBuf allocate(ChannelHandlerContext paramChannelHandlerContext, int paramInt)
/*      */   {
/* 1261 */     ByteBufAllocator localByteBufAllocator = paramChannelHandlerContext.alloc();
/* 1262 */     if (this.wantsDirectBuffer) {
/* 1263 */       return localByteBufAllocator.directBuffer(paramInt);
/*      */     }
/* 1265 */     return localByteBufAllocator.buffer(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ByteBuf allocateOutNetBuf(ChannelHandlerContext paramChannelHandlerContext, int paramInt)
/*      */   {
/* 1274 */     if (this.wantsLargeOutboundNetworkBuffer) {
/* 1275 */       return allocate(paramChannelHandlerContext, this.maxPacketBufferSize);
/*      */     }
/* 1277 */     return allocate(paramChannelHandlerContext, Math.min(paramInt + 2329, this.maxPacketBufferSize));
/*      */   }
/*      */   
/*      */   private final class LazyChannelPromise
/*      */     extends DefaultPromise<Channel>
/*      */   {
/*      */     private LazyChannelPromise() {}
/*      */     
/*      */     protected EventExecutor executor()
/*      */     {
/* 1287 */       if (SslHandler.this.ctx == null) {
/* 1288 */         throw new IllegalStateException();
/*      */       }
/* 1290 */       return SslHandler.this.ctx.executor();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\SslHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */