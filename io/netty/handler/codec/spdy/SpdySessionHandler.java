/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class SpdySessionHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  35 */   private static final SpdyProtocolException PROTOCOL_EXCEPTION = new SpdyProtocolException();
/*  36 */   private static final SpdyProtocolException STREAM_CLOSED = new SpdyProtocolException("Stream closed");
/*     */   private static final int DEFAULT_WINDOW_SIZE = 65536;
/*     */   
/*  39 */   static { PROTOCOL_EXCEPTION.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  40 */     STREAM_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*     */ 
/*  44 */   private int initialSendWindowSize = 65536;
/*  45 */   private int initialReceiveWindowSize = 65536;
/*  46 */   private volatile int initialSessionReceiveWindowSize = 65536;
/*     */   
/*  48 */   private final SpdySession spdySession = new SpdySession(this.initialSendWindowSize, this.initialReceiveWindowSize);
/*     */   
/*     */   private int lastGoodStreamId;
/*     */   private static final int DEFAULT_MAX_CONCURRENT_STREAMS = Integer.MAX_VALUE;
/*  52 */   private int remoteConcurrentStreams = Integer.MAX_VALUE;
/*  53 */   private int localConcurrentStreams = Integer.MAX_VALUE;
/*     */   
/*  55 */   private final AtomicInteger pings = new AtomicInteger();
/*     */   
/*     */ 
/*     */   private boolean sentGoAwayFrame;
/*     */   
/*     */ 
/*     */   private boolean receivedGoAwayFrame;
/*     */   
/*     */ 
/*     */   private ChannelFutureListener closeSessionFutureListener;
/*     */   
/*     */ 
/*     */   private final boolean server;
/*     */   
/*     */ 
/*     */   private final int minorVersion;
/*     */   
/*     */ 
/*     */   public SpdySessionHandler(SpdyVersion paramSpdyVersion, boolean paramBoolean)
/*     */   {
/*  75 */     if (paramSpdyVersion == null) {
/*  76 */       throw new NullPointerException("version");
/*     */     }
/*  78 */     this.server = paramBoolean;
/*  79 */     this.minorVersion = paramSpdyVersion.getMinorVersion();
/*     */   }
/*     */   
/*     */   public void setSessionReceiveWindowSize(int paramInt) {
/*  83 */     if (paramInt < 0) {
/*  84 */       throw new IllegalArgumentException("sessionReceiveWindowSize");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  92 */     this.initialSessionReceiveWindowSize = paramInt; }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception { Object localObject1;
/*     */     int i;
/*     */     int j;
/*  97 */     if ((paramObject instanceof SpdyDataFrame))
/*     */     {
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
/* 121 */       localObject1 = (SpdyDataFrame)paramObject;
/* 122 */       i = ((SpdyDataFrame)localObject1).streamId();
/*     */       
/* 124 */       j = -1 * ((SpdyDataFrame)localObject1).content().readableBytes();
/* 125 */       int m = this.spdySession.updateReceiveWindowSize(0, j);
/*     */       
/*     */ 
/*     */ 
/* 129 */       if (m < 0) {
/* 130 */         issueSessionError(paramChannelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR); return;
/*     */       }
/*     */       
/*     */       Object localObject2;
/*     */       
/* 135 */       if (m <= this.initialSessionReceiveWindowSize / 2) {
/* 136 */         i1 = this.initialSessionReceiveWindowSize - m;
/* 137 */         this.spdySession.updateReceiveWindowSize(0, i1);
/* 138 */         localObject2 = new DefaultSpdyWindowUpdateFrame(0, i1);
/*     */         
/* 140 */         paramChannelHandlerContext.writeAndFlush(localObject2);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 145 */       if (!this.spdySession.isActiveStream(i)) {
/* 146 */         ((SpdyDataFrame)localObject1).release();
/* 147 */         if (i <= this.lastGoodStreamId) {
/* 148 */           issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.PROTOCOL_ERROR);
/* 149 */         } else if (!this.sentGoAwayFrame) {
/* 150 */           issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.INVALID_STREAM);
/*     */         }
/* 152 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 157 */       if (this.spdySession.isRemoteSideClosed(i)) {
/* 158 */         ((SpdyDataFrame)localObject1).release();
/* 159 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.STREAM_ALREADY_CLOSED);
/* 160 */         return;
/*     */       }
/*     */       
/*     */ 
/* 164 */       if ((!isRemoteInitiatedId(i)) && (!this.spdySession.hasReceivedReply(i))) {
/* 165 */         ((SpdyDataFrame)localObject1).release();
/* 166 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.PROTOCOL_ERROR);
/* 167 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */       int i1 = this.spdySession.updateReceiveWindowSize(i, j);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 184 */       if (i1 < this.spdySession.getReceiveWindowSizeLowerBound(i)) {
/* 185 */         ((SpdyDataFrame)localObject1).release();
/* 186 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/* 187 */         return;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 192 */       if (i1 < 0) {
/* 193 */         while (((SpdyDataFrame)localObject1).content().readableBytes() > this.initialReceiveWindowSize) {
/* 194 */           localObject2 = new DefaultSpdyDataFrame(i, ((SpdyDataFrame)localObject1).content().readSlice(this.initialReceiveWindowSize).retain());
/*     */           
/* 196 */           paramChannelHandlerContext.writeAndFlush(localObject2);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 201 */       if ((i1 <= this.initialReceiveWindowSize / 2) && (!((SpdyDataFrame)localObject1).isLast())) {
/* 202 */         int i2 = this.initialReceiveWindowSize - i1;
/* 203 */         this.spdySession.updateReceiveWindowSize(i, i2);
/* 204 */         DefaultSpdyWindowUpdateFrame localDefaultSpdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(i, i2);
/*     */         
/* 206 */         paramChannelHandlerContext.writeAndFlush(localDefaultSpdyWindowUpdateFrame);
/*     */       }
/*     */       
/*     */ 
/* 210 */       if (((SpdyDataFrame)localObject1).isLast()) {
/* 211 */         halfCloseStream(i, true, paramChannelHandlerContext.newSucceededFuture());
/*     */       }
/*     */     }
/* 214 */     else if ((paramObject instanceof SpdySynStreamFrame))
/*     */     {
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
/* 230 */       localObject1 = (SpdySynStreamFrame)paramObject;
/* 231 */       i = ((SpdySynStreamFrame)localObject1).streamId();
/*     */       
/*     */ 
/* 234 */       if ((((SpdySynStreamFrame)localObject1).isInvalid()) || (!isRemoteInitiatedId(i)) || (this.spdySession.isActiveStream(i)))
/*     */       {
/*     */ 
/* 237 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.PROTOCOL_ERROR);
/* 238 */         return;
/*     */       }
/*     */       
/*     */ 
/* 242 */       if (i <= this.lastGoodStreamId) {
/* 243 */         issueSessionError(paramChannelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
/* 244 */         return;
/*     */       }
/*     */       
/*     */ 
/* 248 */       j = ((SpdySynStreamFrame)localObject1).priority();
/* 249 */       boolean bool1 = ((SpdySynStreamFrame)localObject1).isLast();
/* 250 */       boolean bool2 = ((SpdySynStreamFrame)localObject1).isUnidirectional();
/* 251 */       if (!acceptStream(i, j, bool1, bool2)) {
/* 252 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.REFUSED_STREAM);
/* 253 */         return;
/*     */       }
/*     */     }
/* 256 */     else if ((paramObject instanceof SpdySynReplyFrame))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 265 */       localObject1 = (SpdySynReplyFrame)paramObject;
/* 266 */       i = ((SpdySynReplyFrame)localObject1).streamId();
/*     */       
/*     */ 
/* 269 */       if ((((SpdySynReplyFrame)localObject1).isInvalid()) || (isRemoteInitiatedId(i)) || (this.spdySession.isRemoteSideClosed(i)))
/*     */       {
/*     */ 
/* 272 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.INVALID_STREAM);
/* 273 */         return;
/*     */       }
/*     */       
/*     */ 
/* 277 */       if (this.spdySession.hasReceivedReply(i)) {
/* 278 */         issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.STREAM_IN_USE);
/* 279 */         return;
/*     */       }
/*     */       
/* 282 */       this.spdySession.receivedReply(i);
/*     */       
/*     */ 
/* 285 */       if (((SpdySynReplyFrame)localObject1).isLast()) {
/* 286 */         halfCloseStream(i, true, paramChannelHandlerContext.newSucceededFuture());
/*     */       }
/*     */     }
/* 289 */     else if ((paramObject instanceof SpdyRstStreamFrame))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 300 */       localObject1 = (SpdyRstStreamFrame)paramObject;
/* 301 */       removeStream(((SpdyRstStreamFrame)localObject1).streamId(), paramChannelHandlerContext.newSucceededFuture());
/*     */     } else { int k;
/* 303 */       if ((paramObject instanceof SpdySettingsFrame))
/*     */       {
/* 305 */         localObject1 = (SpdySettingsFrame)paramObject;
/*     */         
/* 307 */         i = ((SpdySettingsFrame)localObject1).getValue(0);
/* 308 */         if ((i >= 0) && (i != this.minorVersion))
/*     */         {
/* 310 */           issueSessionError(paramChannelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
/* 311 */           return;
/*     */         }
/*     */         
/* 314 */         k = ((SpdySettingsFrame)localObject1).getValue(4);
/*     */         
/* 316 */         if (k >= 0) {
/* 317 */           this.remoteConcurrentStreams = k;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 323 */         if (((SpdySettingsFrame)localObject1).isPersisted(7)) {
/* 324 */           ((SpdySettingsFrame)localObject1).removeValue(7);
/*     */         }
/* 326 */         ((SpdySettingsFrame)localObject1).setPersistValue(7, false);
/*     */         
/* 328 */         int n = ((SpdySettingsFrame)localObject1).getValue(7);
/*     */         
/* 330 */         if (n >= 0) {
/* 331 */           updateInitialSendWindowSize(n);
/*     */         }
/*     */       }
/* 334 */       else if ((paramObject instanceof SpdyPingFrame))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 345 */         localObject1 = (SpdyPingFrame)paramObject;
/*     */         
/* 347 */         if (isRemoteInitiatedId(((SpdyPingFrame)localObject1).id())) {
/* 348 */           paramChannelHandlerContext.writeAndFlush(localObject1);
/* 349 */           return;
/*     */         }
/*     */         
/*     */ 
/* 353 */         if (this.pings.get() == 0) {
/* 354 */           return;
/*     */         }
/* 356 */         this.pings.getAndDecrement();
/*     */       }
/* 358 */       else if ((paramObject instanceof SpdyGoAwayFrame))
/*     */       {
/* 360 */         this.receivedGoAwayFrame = true;
/*     */       }
/* 362 */       else if ((paramObject instanceof SpdyHeadersFrame))
/*     */       {
/* 364 */         localObject1 = (SpdyHeadersFrame)paramObject;
/* 365 */         i = ((SpdyHeadersFrame)localObject1).streamId();
/*     */         
/*     */ 
/* 368 */         if (((SpdyHeadersFrame)localObject1).isInvalid()) {
/* 369 */           issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.PROTOCOL_ERROR);
/* 370 */           return;
/*     */         }
/*     */         
/* 373 */         if (this.spdySession.isRemoteSideClosed(i)) {
/* 374 */           issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.INVALID_STREAM);
/* 375 */           return;
/*     */         }
/*     */         
/*     */ 
/* 379 */         if (((SpdyHeadersFrame)localObject1).isLast()) {
/* 380 */           halfCloseStream(i, true, paramChannelHandlerContext.newSucceededFuture());
/*     */         }
/*     */       }
/* 383 */       else if ((paramObject instanceof SpdyWindowUpdateFrame))
/*     */       {
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
/* 395 */         localObject1 = (SpdyWindowUpdateFrame)paramObject;
/* 396 */         i = ((SpdyWindowUpdateFrame)localObject1).streamId();
/* 397 */         k = ((SpdyWindowUpdateFrame)localObject1).deltaWindowSize();
/*     */         
/*     */ 
/* 400 */         if ((i != 0) && (this.spdySession.isLocalSideClosed(i))) {
/* 401 */           return;
/*     */         }
/*     */         
/*     */ 
/* 405 */         if (this.spdySession.getSendWindowSize(i) > Integer.MAX_VALUE - k) {
/* 406 */           if (i == 0) {
/* 407 */             issueSessionError(paramChannelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
/*     */           } else {
/* 409 */             issueStreamError(paramChannelHandlerContext, i, SpdyStreamStatus.FLOW_CONTROL_ERROR);
/*     */           }
/* 411 */           return;
/*     */         }
/*     */         
/* 414 */         updateSendWindowSize(paramChannelHandlerContext, i, k);
/*     */       }
/*     */     }
/* 417 */     paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 422 */     for (Integer localInteger : this.spdySession.activeStreams().keySet()) {
/* 423 */       removeStream(localInteger.intValue(), paramChannelHandlerContext.newSucceededFuture());
/*     */     }
/* 425 */     paramChannelHandlerContext.fireChannelInactive();
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */   {
/* 430 */     if ((paramThrowable instanceof SpdyProtocolException)) {
/* 431 */       issueSessionError(paramChannelHandlerContext, SpdySessionStatus.PROTOCOL_ERROR);
/*     */     }
/*     */     
/* 434 */     paramChannelHandlerContext.fireExceptionCaught(paramThrowable);
/*     */   }
/*     */   
/*     */   public void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 439 */     sendGoAwayFrame(paramChannelHandlerContext, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 444 */     if (((paramObject instanceof SpdyDataFrame)) || ((paramObject instanceof SpdySynStreamFrame)) || ((paramObject instanceof SpdySynReplyFrame)) || ((paramObject instanceof SpdyRstStreamFrame)) || ((paramObject instanceof SpdySettingsFrame)) || ((paramObject instanceof SpdyPingFrame)) || ((paramObject instanceof SpdyGoAwayFrame)) || ((paramObject instanceof SpdyHeadersFrame)) || ((paramObject instanceof SpdyWindowUpdateFrame)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 454 */       handleOutboundMessage(paramChannelHandlerContext, paramObject, paramChannelPromise);
/*     */     } else
/* 456 */       paramChannelHandlerContext.write(paramObject, paramChannelPromise); }
/*     */   
/*     */   private void handleOutboundMessage(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception { Object localObject1;
/*     */     int i;
/*     */     int j;
/* 461 */     if ((paramObject instanceof SpdyDataFrame))
/*     */     {
/* 463 */       localObject1 = (SpdyDataFrame)paramObject;
/* 464 */       i = ((SpdyDataFrame)localObject1).streamId();
/*     */       
/*     */ 
/* 467 */       if (this.spdySession.isLocalSideClosed(i)) {
/* 468 */         ((SpdyDataFrame)localObject1).release();
/* 469 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 470 */         return;
/*     */       }
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
/* 486 */       j = ((SpdyDataFrame)localObject1).content().readableBytes();
/* 487 */       int m = this.spdySession.getSendWindowSize(i);
/* 488 */       int i1 = this.spdySession.getSendWindowSize(0);
/* 489 */       m = Math.min(m, i1);
/*     */       
/* 491 */       if (m <= 0)
/*     */       {
/* 493 */         this.spdySession.putPendingWrite(i, new SpdySession.PendingWrite((SpdyDataFrame)localObject1, paramChannelPromise));
/* 494 */         return; }
/* 495 */       if (m < j)
/*     */       {
/* 497 */         this.spdySession.updateSendWindowSize(i, -1 * m);
/* 498 */         this.spdySession.updateSendWindowSize(0, -1 * m);
/*     */         
/*     */ 
/* 501 */         localObject2 = new DefaultSpdyDataFrame(i, ((SpdyDataFrame)localObject1).content().readSlice(m).retain());
/*     */         
/*     */ 
/*     */ 
/* 505 */         this.spdySession.putPendingWrite(i, new SpdySession.PendingWrite((SpdyDataFrame)localObject1, paramChannelPromise));
/*     */         
/*     */ 
/*     */ 
/* 509 */         final ChannelHandlerContext localChannelHandlerContext = paramChannelHandlerContext;
/* 510 */         paramChannelHandlerContext.write(localObject2).addListener(new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 513 */             if (!paramAnonymousChannelFuture.isSuccess()) {
/* 514 */               SpdySessionHandler.this.issueSessionError(localChannelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
/*     */             }
/*     */           }
/* 517 */         });
/* 518 */         return;
/*     */       }
/*     */       
/* 521 */       this.spdySession.updateSendWindowSize(i, -1 * j);
/* 522 */       this.spdySession.updateSendWindowSize(0, -1 * j);
/*     */       
/*     */ 
/*     */ 
/* 526 */       final Object localObject2 = paramChannelHandlerContext;
/* 527 */       paramChannelPromise.addListener(new ChannelFutureListener()
/*     */       {
/*     */         public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 530 */           if (!paramAnonymousChannelFuture.isSuccess()) {
/* 531 */             SpdySessionHandler.this.issueSessionError(localObject2, SpdySessionStatus.INTERNAL_ERROR);
/*     */           }
/*     */         }
/*     */       });
/*     */       
/*     */ 
/*     */ 
/* 538 */       if (((SpdyDataFrame)localObject1).isLast()) {
/* 539 */         halfCloseStream(i, false, paramChannelPromise);
/*     */       }
/*     */     }
/* 542 */     else if ((paramObject instanceof SpdySynStreamFrame))
/*     */     {
/* 544 */       localObject1 = (SpdySynStreamFrame)paramObject;
/* 545 */       i = ((SpdySynStreamFrame)localObject1).streamId();
/*     */       
/* 547 */       if (isRemoteInitiatedId(i)) {
/* 548 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 549 */         return;
/*     */       }
/*     */       
/* 552 */       j = ((SpdySynStreamFrame)localObject1).priority();
/* 553 */       boolean bool1 = ((SpdySynStreamFrame)localObject1).isUnidirectional();
/* 554 */       boolean bool2 = ((SpdySynStreamFrame)localObject1).isLast();
/* 555 */       if (!acceptStream(i, j, bool1, bool2)) {
/* 556 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 557 */         return;
/*     */       }
/*     */     }
/* 560 */     else if ((paramObject instanceof SpdySynReplyFrame))
/*     */     {
/* 562 */       localObject1 = (SpdySynReplyFrame)paramObject;
/* 563 */       i = ((SpdySynReplyFrame)localObject1).streamId();
/*     */       
/*     */ 
/* 566 */       if ((!isRemoteInitiatedId(i)) || (this.spdySession.isLocalSideClosed(i))) {
/* 567 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 568 */         return;
/*     */       }
/*     */       
/*     */ 
/* 572 */       if (((SpdySynReplyFrame)localObject1).isLast()) {
/* 573 */         halfCloseStream(i, false, paramChannelPromise);
/*     */       }
/*     */     }
/* 576 */     else if ((paramObject instanceof SpdyRstStreamFrame))
/*     */     {
/* 578 */       localObject1 = (SpdyRstStreamFrame)paramObject;
/* 579 */       removeStream(((SpdyRstStreamFrame)localObject1).streamId(), paramChannelPromise);
/*     */     }
/* 581 */     else if ((paramObject instanceof SpdySettingsFrame))
/*     */     {
/* 583 */       localObject1 = (SpdySettingsFrame)paramObject;
/*     */       
/* 585 */       i = ((SpdySettingsFrame)localObject1).getValue(0);
/* 586 */       if ((i >= 0) && (i != this.minorVersion))
/*     */       {
/* 588 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 589 */         return;
/*     */       }
/*     */       
/* 592 */       int k = ((SpdySettingsFrame)localObject1).getValue(4);
/*     */       
/* 594 */       if (k >= 0) {
/* 595 */         this.localConcurrentStreams = k;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 601 */       if (((SpdySettingsFrame)localObject1).isPersisted(7)) {
/* 602 */         ((SpdySettingsFrame)localObject1).removeValue(7);
/*     */       }
/* 604 */       ((SpdySettingsFrame)localObject1).setPersistValue(7, false);
/*     */       
/* 606 */       int n = ((SpdySettingsFrame)localObject1).getValue(7);
/*     */       
/* 608 */       if (n >= 0) {
/* 609 */         updateInitialReceiveWindowSize(n);
/*     */       }
/*     */     }
/* 612 */     else if ((paramObject instanceof SpdyPingFrame))
/*     */     {
/* 614 */       localObject1 = (SpdyPingFrame)paramObject;
/* 615 */       if (isRemoteInitiatedId(((SpdyPingFrame)localObject1).id())) {
/* 616 */         paramChannelHandlerContext.fireExceptionCaught(new IllegalArgumentException("invalid PING ID: " + ((SpdyPingFrame)localObject1).id()));
/*     */         
/* 618 */         return;
/*     */       }
/* 620 */       this.pings.getAndIncrement();
/*     */     } else {
/* 622 */       if ((paramObject instanceof SpdyGoAwayFrame))
/*     */       {
/*     */ 
/*     */ 
/* 626 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 627 */         return;
/*     */       }
/* 629 */       if ((paramObject instanceof SpdyHeadersFrame))
/*     */       {
/* 631 */         localObject1 = (SpdyHeadersFrame)paramObject;
/* 632 */         i = ((SpdyHeadersFrame)localObject1).streamId();
/*     */         
/*     */ 
/* 635 */         if (this.spdySession.isLocalSideClosed(i)) {
/* 636 */           paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 637 */           return;
/*     */         }
/*     */         
/*     */ 
/* 641 */         if (((SpdyHeadersFrame)localObject1).isLast()) {
/* 642 */           halfCloseStream(i, false, paramChannelPromise);
/*     */         }
/*     */       }
/* 645 */       else if ((paramObject instanceof SpdyWindowUpdateFrame))
/*     */       {
/*     */ 
/* 648 */         paramChannelPromise.setFailure(PROTOCOL_EXCEPTION);
/* 649 */         return;
/*     */       }
/*     */     }
/* 652 */     paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */   }
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
/*     */   private void issueSessionError(ChannelHandlerContext paramChannelHandlerContext, SpdySessionStatus paramSpdySessionStatus)
/*     */   {
/* 667 */     sendGoAwayFrame(paramChannelHandlerContext, paramSpdySessionStatus).addListener(new ClosingChannelFutureListener(paramChannelHandlerContext, paramChannelHandlerContext.newPromise()));
/*     */   }
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
/*     */   private void issueStreamError(ChannelHandlerContext paramChannelHandlerContext, int paramInt, SpdyStreamStatus paramSpdyStreamStatus)
/*     */   {
/* 682 */     int i = !this.spdySession.isRemoteSideClosed(paramInt) ? 1 : 0;
/* 683 */     ChannelPromise localChannelPromise = paramChannelHandlerContext.newPromise();
/* 684 */     removeStream(paramInt, localChannelPromise);
/*     */     
/* 686 */     DefaultSpdyRstStreamFrame localDefaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(paramInt, paramSpdyStreamStatus);
/* 687 */     paramChannelHandlerContext.writeAndFlush(localDefaultSpdyRstStreamFrame, localChannelPromise);
/* 688 */     if (i != 0) {
/* 689 */       paramChannelHandlerContext.fireChannelRead(localDefaultSpdyRstStreamFrame);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isRemoteInitiatedId(int paramInt)
/*     */   {
/* 698 */     boolean bool = SpdyCodecUtil.isServerId(paramInt);
/* 699 */     return ((this.server) && (!bool)) || ((!this.server) && (bool));
/*     */   }
/*     */   
/*     */   private synchronized void updateInitialSendWindowSize(int paramInt)
/*     */   {
/* 704 */     int i = paramInt - this.initialSendWindowSize;
/* 705 */     this.initialSendWindowSize = paramInt;
/* 706 */     this.spdySession.updateAllSendWindowSizes(i);
/*     */   }
/*     */   
/*     */   private synchronized void updateInitialReceiveWindowSize(int paramInt)
/*     */   {
/* 711 */     int i = paramInt - this.initialReceiveWindowSize;
/* 712 */     this.initialReceiveWindowSize = paramInt;
/* 713 */     this.spdySession.updateAllReceiveWindowSizes(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private synchronized boolean acceptStream(int paramInt, byte paramByte, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 720 */     if ((this.receivedGoAwayFrame) || (this.sentGoAwayFrame)) {
/* 721 */       return false;
/*     */     }
/*     */     
/* 724 */     boolean bool = isRemoteInitiatedId(paramInt);
/* 725 */     int i = bool ? this.localConcurrentStreams : this.remoteConcurrentStreams;
/* 726 */     if (this.spdySession.numActiveStreams(bool) >= i) {
/* 727 */       return false;
/*     */     }
/* 729 */     this.spdySession.acceptStream(paramInt, paramByte, paramBoolean1, paramBoolean2, this.initialSendWindowSize, this.initialReceiveWindowSize, bool);
/*     */     
/*     */ 
/* 732 */     if (bool) {
/* 733 */       this.lastGoodStreamId = paramInt;
/*     */     }
/* 735 */     return true;
/*     */   }
/*     */   
/*     */   private void halfCloseStream(int paramInt, boolean paramBoolean, ChannelFuture paramChannelFuture) {
/* 739 */     if (paramBoolean) {
/* 740 */       this.spdySession.closeRemoteSide(paramInt, isRemoteInitiatedId(paramInt));
/*     */     } else {
/* 742 */       this.spdySession.closeLocalSide(paramInt, isRemoteInitiatedId(paramInt));
/*     */     }
/* 744 */     if ((this.closeSessionFutureListener != null) && (this.spdySession.noActiveStreams())) {
/* 745 */       paramChannelFuture.addListener(this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeStream(int paramInt, ChannelFuture paramChannelFuture) {
/* 750 */     this.spdySession.removeStream(paramInt, STREAM_CLOSED, isRemoteInitiatedId(paramInt));
/*     */     
/* 752 */     if ((this.closeSessionFutureListener != null) && (this.spdySession.noActiveStreams())) {
/* 753 */       paramChannelFuture.addListener(this.closeSessionFutureListener);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateSendWindowSize(final ChannelHandlerContext paramChannelHandlerContext, int paramInt1, int paramInt2) {
/* 758 */     this.spdySession.updateSendWindowSize(paramInt1, paramInt2);
/*     */     
/*     */     for (;;)
/*     */     {
/* 762 */       SpdySession.PendingWrite localPendingWrite = this.spdySession.getPendingWrite(paramInt1);
/* 763 */       if (localPendingWrite == null) {
/* 764 */         return;
/*     */       }
/*     */       
/* 767 */       SpdyDataFrame localSpdyDataFrame = localPendingWrite.spdyDataFrame;
/* 768 */       int i = localSpdyDataFrame.content().readableBytes();
/* 769 */       int j = localSpdyDataFrame.streamId();
/* 770 */       int k = this.spdySession.getSendWindowSize(j);
/* 771 */       int m = this.spdySession.getSendWindowSize(0);
/* 772 */       k = Math.min(k, m);
/*     */       
/* 774 */       if (k <= 0)
/* 775 */         return;
/* 776 */       if (k < i)
/*     */       {
/* 778 */         this.spdySession.updateSendWindowSize(j, -1 * k);
/* 779 */         this.spdySession.updateSendWindowSize(0, -1 * k);
/*     */         
/*     */ 
/* 782 */         DefaultSpdyDataFrame localDefaultSpdyDataFrame = new DefaultSpdyDataFrame(j, localSpdyDataFrame.content().readSlice(k).retain());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 787 */         paramChannelHandlerContext.writeAndFlush(localDefaultSpdyDataFrame).addListener(new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 790 */             if (!paramAnonymousChannelFuture.isSuccess()) {
/* 791 */               SpdySessionHandler.this.issueSessionError(paramChannelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */       else {
/* 797 */         this.spdySession.removePendingWrite(j);
/* 798 */         this.spdySession.updateSendWindowSize(j, -1 * i);
/* 799 */         this.spdySession.updateSendWindowSize(0, -1 * i);
/*     */         
/*     */ 
/* 802 */         if (localSpdyDataFrame.isLast()) {
/* 803 */           halfCloseStream(j, false, localPendingWrite.promise);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 808 */         paramChannelHandlerContext.writeAndFlush(localSpdyDataFrame, localPendingWrite.promise).addListener(new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 811 */             if (!paramAnonymousChannelFuture.isSuccess()) {
/* 812 */               SpdySessionHandler.this.issueSessionError(paramChannelHandlerContext, SpdySessionStatus.INTERNAL_ERROR);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendGoAwayFrame(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */   {
/* 822 */     if (!paramChannelHandlerContext.channel().isActive()) {
/* 823 */       paramChannelHandlerContext.close(paramChannelPromise);
/* 824 */       return;
/*     */     }
/*     */     
/* 827 */     ChannelFuture localChannelFuture = sendGoAwayFrame(paramChannelHandlerContext, SpdySessionStatus.OK);
/* 828 */     if (this.spdySession.noActiveStreams()) {
/* 829 */       localChannelFuture.addListener(new ClosingChannelFutureListener(paramChannelHandlerContext, paramChannelPromise));
/*     */     } else {
/* 831 */       this.closeSessionFutureListener = new ClosingChannelFutureListener(paramChannelHandlerContext, paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private synchronized ChannelFuture sendGoAwayFrame(ChannelHandlerContext paramChannelHandlerContext, SpdySessionStatus paramSpdySessionStatus)
/*     */   {
/* 838 */     if (!this.sentGoAwayFrame) {
/* 839 */       this.sentGoAwayFrame = true;
/* 840 */       DefaultSpdyGoAwayFrame localDefaultSpdyGoAwayFrame = new DefaultSpdyGoAwayFrame(this.lastGoodStreamId, paramSpdySessionStatus);
/* 841 */       return paramChannelHandlerContext.writeAndFlush(localDefaultSpdyGoAwayFrame);
/*     */     }
/* 843 */     return paramChannelHandlerContext.newSucceededFuture();
/*     */   }
/*     */   
/*     */   private static final class ClosingChannelFutureListener implements ChannelFutureListener
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     private final ChannelPromise promise;
/*     */     
/*     */     ClosingChannelFutureListener(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) {
/* 852 */       this.ctx = paramChannelHandlerContext;
/* 853 */       this.promise = paramChannelPromise;
/*     */     }
/*     */     
/*     */     public void operationComplete(ChannelFuture paramChannelFuture) throws Exception
/*     */     {
/* 858 */       this.ctx.close(this.promise);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdySessionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */