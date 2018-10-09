/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LengthFieldBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final ByteOrder byteOrder;
/*     */   private final int maxFrameLength;
/*     */   private final int lengthFieldOffset;
/*     */   private final int lengthFieldLength;
/*     */   private final int lengthFieldEndOffset;
/*     */   private final int lengthAdjustment;
/*     */   private final int initialBytesToStrip;
/*     */   private final boolean failFast;
/*     */   private boolean discardingTooLongFrame;
/*     */   private long tooLongFrameLength;
/*     */   private long bytesToDiscard;
/*     */   
/*     */   public LengthFieldBasedFrameDecoder(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 213 */     this(paramInt1, paramInt2, paramInt3, 0, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LengthFieldBasedFrameDecoder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 236 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, true);
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
/*     */   public LengthFieldBasedFrameDecoder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
/*     */   {
/* 268 */     this(ByteOrder.BIG_ENDIAN, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean);
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
/*     */   public LengthFieldBasedFrameDecoder(ByteOrder paramByteOrder, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
/*     */   {
/* 301 */     if (paramByteOrder == null) {
/* 302 */       throw new NullPointerException("byteOrder");
/*     */     }
/*     */     
/* 305 */     if (paramInt1 <= 0) {
/* 306 */       throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + paramInt1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 311 */     if (paramInt2 < 0) {
/* 312 */       throw new IllegalArgumentException("lengthFieldOffset must be a non-negative integer: " + paramInt2);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 317 */     if (paramInt5 < 0) {
/* 318 */       throw new IllegalArgumentException("initialBytesToStrip must be a non-negative integer: " + paramInt5);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 323 */     if (paramInt2 > paramInt1 - paramInt3) {
/* 324 */       throw new IllegalArgumentException("maxFrameLength (" + paramInt1 + ") " + "must be equal to or greater than " + "lengthFieldOffset (" + paramInt2 + ") + " + "lengthFieldLength (" + paramInt3 + ").");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 331 */     this.byteOrder = paramByteOrder;
/* 332 */     this.maxFrameLength = paramInt1;
/* 333 */     this.lengthFieldOffset = paramInt2;
/* 334 */     this.lengthFieldLength = paramInt3;
/* 335 */     this.lengthAdjustment = paramInt4;
/* 336 */     this.lengthFieldEndOffset = (paramInt2 + paramInt3);
/* 337 */     this.initialBytesToStrip = paramInt5;
/* 338 */     this.failFast = paramBoolean;
/*     */   }
/*     */   
/*     */   protected final void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/* 343 */     Object localObject = decode(paramChannelHandlerContext, paramByteBuf);
/* 344 */     if (localObject != null) {
/* 345 */       paramList.add(localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
/*     */     throws Exception
/*     */   {
/* 358 */     if (this.discardingTooLongFrame) {
/* 359 */       long l1 = this.bytesToDiscard;
/* 360 */       int i = (int)Math.min(l1, paramByteBuf.readableBytes());
/* 361 */       paramByteBuf.skipBytes(i);
/* 362 */       l1 -= i;
/* 363 */       this.bytesToDiscard = l1;
/*     */       
/* 365 */       failIfNecessary(false);
/*     */     }
/*     */     
/* 368 */     if (paramByteBuf.readableBytes() < this.lengthFieldEndOffset) {
/* 369 */       return null;
/*     */     }
/*     */     
/* 372 */     int j = paramByteBuf.readerIndex() + this.lengthFieldOffset;
/* 373 */     long l2 = getUnadjustedFrameLength(paramByteBuf, j, this.lengthFieldLength, this.byteOrder);
/*     */     
/* 375 */     if (l2 < 0L) {
/* 376 */       paramByteBuf.skipBytes(this.lengthFieldEndOffset);
/* 377 */       throw new CorruptedFrameException("negative pre-adjustment length field: " + l2);
/*     */     }
/*     */     
/*     */ 
/* 381 */     l2 += this.lengthAdjustment + this.lengthFieldEndOffset;
/*     */     
/* 383 */     if (l2 < this.lengthFieldEndOffset) {
/* 384 */       paramByteBuf.skipBytes(this.lengthFieldEndOffset);
/* 385 */       throw new CorruptedFrameException("Adjusted frame length (" + l2 + ") is less " + "than lengthFieldEndOffset: " + this.lengthFieldEndOffset);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 390 */     if (l2 > this.maxFrameLength) {
/* 391 */       long l3 = l2 - paramByteBuf.readableBytes();
/* 392 */       this.tooLongFrameLength = l2;
/*     */       
/* 394 */       if (l3 < 0L)
/*     */       {
/* 396 */         paramByteBuf.skipBytes((int)l2);
/*     */       }
/*     */       else {
/* 399 */         this.discardingTooLongFrame = true;
/* 400 */         this.bytesToDiscard = l3;
/* 401 */         paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/*     */       }
/* 403 */       failIfNecessary(true);
/* 404 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 408 */     int k = (int)l2;
/* 409 */     if (paramByteBuf.readableBytes() < k) {
/* 410 */       return null;
/*     */     }
/*     */     
/* 413 */     if (this.initialBytesToStrip > k) {
/* 414 */       paramByteBuf.skipBytes(k);
/* 415 */       throw new CorruptedFrameException("Adjusted frame length (" + l2 + ") is less " + "than initialBytesToStrip: " + this.initialBytesToStrip);
/*     */     }
/*     */     
/*     */ 
/* 419 */     paramByteBuf.skipBytes(this.initialBytesToStrip);
/*     */     
/*     */ 
/* 422 */     int m = paramByteBuf.readerIndex();
/* 423 */     int n = k - this.initialBytesToStrip;
/* 424 */     ByteBuf localByteBuf = extractFrame(paramChannelHandlerContext, paramByteBuf, m, n);
/* 425 */     paramByteBuf.readerIndex(m + n);
/* 426 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected long getUnadjustedFrameLength(ByteBuf paramByteBuf, int paramInt1, int paramInt2, ByteOrder paramByteOrder)
/*     */   {
/* 438 */     paramByteBuf = paramByteBuf.order(paramByteOrder);
/*     */     long l;
/* 440 */     switch (paramInt2) {
/*     */     case 1: 
/* 442 */       l = paramByteBuf.getUnsignedByte(paramInt1);
/* 443 */       break;
/*     */     case 2: 
/* 445 */       l = paramByteBuf.getUnsignedShort(paramInt1);
/* 446 */       break;
/*     */     case 3: 
/* 448 */       l = paramByteBuf.getUnsignedMedium(paramInt1);
/* 449 */       break;
/*     */     case 4: 
/* 451 */       l = paramByteBuf.getUnsignedInt(paramInt1);
/* 452 */       break;
/*     */     case 8: 
/* 454 */       l = paramByteBuf.getLong(paramInt1);
/* 455 */       break;
/*     */     case 5: case 6: case 7: default: 
/* 457 */       throw new DecoderException("unsupported lengthFieldLength: " + this.lengthFieldLength + " (expected: 1, 2, 3, 4, or 8)");
/*     */     }
/*     */     
/* 460 */     return l;
/*     */   }
/*     */   
/*     */   private void failIfNecessary(boolean paramBoolean) {
/* 464 */     if (this.bytesToDiscard == 0L)
/*     */     {
/*     */ 
/* 467 */       long l = this.tooLongFrameLength;
/* 468 */       this.tooLongFrameLength = 0L;
/* 469 */       this.discardingTooLongFrame = false;
/* 470 */       if ((!this.failFast) || ((this.failFast) && (paramBoolean)))
/*     */       {
/* 472 */         fail(l);
/*     */       }
/*     */       
/*     */     }
/* 476 */     else if ((this.failFast) && (paramBoolean)) {
/* 477 */       fail(this.tooLongFrameLength);
/*     */     }
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
/*     */ 
/*     */   protected ByteBuf extractFrame(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 494 */     ByteBuf localByteBuf = paramChannelHandlerContext.alloc().buffer(paramInt2);
/* 495 */     localByteBuf.writeBytes(paramByteBuf, paramInt1, paramInt2);
/* 496 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   private void fail(long paramLong) {
/* 500 */     if (paramLong > 0L) {
/* 501 */       throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + ": " + paramLong + " - discarded");
/*     */     }
/*     */     
/*     */ 
/* 505 */     throw new TooLongFrameException("Adjusted frame length exceeds " + this.maxFrameLength + " - discarding");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\LengthFieldBasedFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */