/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.CorruptedFrameException;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public class WebSocket08FrameDecoder
/*     */   extends ReplayingDecoder<State>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*  75 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   private static final byte OPCODE_PING = 9;
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   private int fragmentedFramesCount;
/*     */   private final long maxFramePayloadLength;
/*     */   private boolean frameFinalFlag;
/*     */   private int frameRsv;
/*     */   private int frameOpcode;
/*     */   private long framePayloadLength;
/*     */   private ByteBuf framePayload;
/*     */   private int framePayloadBytesRead;
/*     */   private byte[] maskingKey;
/*     */   private ByteBuf payloadBuffer;
/*     */   private final boolean allowExtensions;
/*     */   private final boolean maskedPayload;
/*     */   private boolean receivedClosingHandshake;
/*     */   private Utf8Validator utf8Validator;
/*     */   
/*     */   static enum State
/*     */   {
/* 100 */     FRAME_START,  MASKING_KEY,  PAYLOAD,  CORRUPT;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private State() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocket08FrameDecoder(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
/*     */   {
/* 116 */     super(State.FRAME_START);
/* 117 */     this.maskedPayload = paramBoolean1;
/* 118 */     this.allowExtensions = paramBoolean2;
/* 119 */     this.maxFramePayloadLength = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 126 */     if (this.receivedClosingHandshake) {
/* 127 */       paramByteBuf.skipBytes(actualReadableBytes());
/* 128 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 132 */       switch ((State)state()) {
/*     */       case FRAME_START: 
/* 134 */         this.framePayloadBytesRead = 0;
/* 135 */         this.framePayloadLength = -1L;
/* 136 */         this.framePayload = null;
/* 137 */         this.payloadBuffer = null;
/*     */         
/*     */ 
/* 140 */         int i = paramByteBuf.readByte();
/* 141 */         this.frameFinalFlag = ((i & 0x80) != 0);
/* 142 */         this.frameRsv = ((i & 0x70) >> 4);
/* 143 */         this.frameOpcode = (i & 0xF);
/*     */         
/* 145 */         if (logger.isDebugEnabled()) {
/* 146 */           logger.debug("Decoding WebSocket Frame opCode={}", Integer.valueOf(this.frameOpcode));
/*     */         }
/*     */         
/*     */ 
/* 150 */         i = paramByteBuf.readByte();
/* 151 */         int j = (i & 0x80) != 0 ? 1 : 0;
/* 152 */         int k = i & 0x7F;
/*     */         
/* 154 */         if ((this.frameRsv != 0) && (!this.allowExtensions)) {
/* 155 */           protocolViolation(paramChannelHandlerContext, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
/* 156 */           return;
/*     */         }
/*     */         
/* 159 */         if ((this.maskedPayload) && (j == 0)) {
/* 160 */           protocolViolation(paramChannelHandlerContext, "unmasked client to server frame");
/* 161 */           return;
/*     */         }
/* 163 */         if (this.frameOpcode > 7)
/*     */         {
/*     */ 
/* 166 */           if (!this.frameFinalFlag) {
/* 167 */             protocolViolation(paramChannelHandlerContext, "fragmented control frame");
/* 168 */             return;
/*     */           }
/*     */           
/*     */ 
/* 172 */           if (k > 125) {
/* 173 */             protocolViolation(paramChannelHandlerContext, "control frame with payload length > 125 octets");
/* 174 */             return;
/*     */           }
/*     */           
/*     */ 
/* 178 */           if ((this.frameOpcode != 8) && (this.frameOpcode != 9) && (this.frameOpcode != 10))
/*     */           {
/* 180 */             protocolViolation(paramChannelHandlerContext, "control frame using reserved opcode " + this.frameOpcode);
/* 181 */             return;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 187 */           if ((this.frameOpcode == 8) && (k == 1)) {
/* 188 */             protocolViolation(paramChannelHandlerContext, "received close control frame with payload len 1");
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 193 */           if ((this.frameOpcode != 0) && (this.frameOpcode != 1) && (this.frameOpcode != 2))
/*     */           {
/* 195 */             protocolViolation(paramChannelHandlerContext, "data frame using reserved opcode " + this.frameOpcode);
/* 196 */             return;
/*     */           }
/*     */           
/*     */ 
/* 200 */           if ((this.fragmentedFramesCount == 0) && (this.frameOpcode == 0)) {
/* 201 */             protocolViolation(paramChannelHandlerContext, "received continuation data frame outside fragmented message");
/* 202 */             return;
/*     */           }
/*     */           
/*     */ 
/* 206 */           if ((this.fragmentedFramesCount != 0) && (this.frameOpcode != 0) && (this.frameOpcode != 9)) {
/* 207 */             protocolViolation(paramChannelHandlerContext, "received non-continuation data frame while inside fragmented message");
/*     */             
/* 209 */             return;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 214 */         if (k == 126) {
/* 215 */           this.framePayloadLength = paramByteBuf.readUnsignedShort();
/* 216 */           if (this.framePayloadLength < 126L) {
/* 217 */             protocolViolation(paramChannelHandlerContext, "invalid data frame length (not using minimal length encoding)");
/*     */           }
/*     */         }
/* 220 */         else if (k == 127) {
/* 221 */           this.framePayloadLength = paramByteBuf.readLong();
/*     */           
/*     */ 
/*     */ 
/* 225 */           if (this.framePayloadLength < 65536L) {
/* 226 */             protocolViolation(paramChannelHandlerContext, "invalid data frame length (not using minimal length encoding)");
/*     */           }
/*     */         }
/*     */         else {
/* 230 */           this.framePayloadLength = k;
/*     */         }
/*     */         
/* 233 */         if (this.framePayloadLength > this.maxFramePayloadLength) {
/* 234 */           protocolViolation(paramChannelHandlerContext, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
/* 235 */           return;
/*     */         }
/*     */         
/* 238 */         if (logger.isDebugEnabled()) {
/* 239 */           logger.debug("Decoding WebSocket Frame length={}", Long.valueOf(this.framePayloadLength));
/*     */         }
/*     */         
/* 242 */         checkpoint(State.MASKING_KEY);
/*     */       case MASKING_KEY: 
/* 244 */         if (this.maskedPayload) {
/* 245 */           if (this.maskingKey == null) {
/* 246 */             this.maskingKey = new byte[4];
/*     */           }
/* 248 */           paramByteBuf.readBytes(this.maskingKey);
/*     */         }
/* 250 */         checkpoint(State.PAYLOAD);
/*     */       
/*     */ 
/*     */       case PAYLOAD: 
/* 254 */         int m = actualReadableBytes();
/*     */         
/* 256 */         long l = this.framePayloadBytesRead + m;
/*     */         
/*     */ 
/*     */ 
/* 260 */         if (l == this.framePayloadLength)
/*     */         {
/* 262 */           this.payloadBuffer = paramChannelHandlerContext.alloc().buffer(m);
/* 263 */           this.payloadBuffer.writeBytes(paramByteBuf, m);
/* 264 */         } else { if (l < this.framePayloadLength)
/*     */           {
/*     */ 
/*     */ 
/* 268 */             if (this.framePayload == null) {
/* 269 */               this.framePayload = paramChannelHandlerContext.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */             }
/* 271 */             this.framePayload.writeBytes(paramByteBuf, m);
/* 272 */             this.framePayloadBytesRead += m;
/*     */             
/*     */ 
/* 275 */             return; }
/* 276 */           if (l > this.framePayloadLength)
/*     */           {
/*     */ 
/* 279 */             if (this.framePayload == null) {
/* 280 */               this.framePayload = paramChannelHandlerContext.alloc().buffer(toFrameLength(this.framePayloadLength));
/*     */             }
/* 282 */             this.framePayload.writeBytes(paramByteBuf, toFrameLength(this.framePayloadLength - this.framePayloadBytesRead));
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 287 */         checkpoint(State.FRAME_START);
/*     */         
/*     */ 
/* 290 */         if (this.framePayload == null) {
/* 291 */           this.framePayload = this.payloadBuffer;
/* 292 */           this.payloadBuffer = null;
/* 293 */         } else if (this.payloadBuffer != null) {
/* 294 */           this.framePayload.writeBytes(this.payloadBuffer);
/* 295 */           this.payloadBuffer.release();
/* 296 */           this.payloadBuffer = null;
/*     */         }
/*     */         
/*     */ 
/* 300 */         if (this.maskedPayload) {
/* 301 */           unmask(this.framePayload);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 306 */         if (this.frameOpcode == 9) {
/* 307 */           paramList.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 308 */           this.framePayload = null;
/* 309 */           return;
/*     */         }
/* 311 */         if (this.frameOpcode == 10) {
/* 312 */           paramList.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 313 */           this.framePayload = null;
/* 314 */           return;
/*     */         }
/* 316 */         if (this.frameOpcode == 8) {
/* 317 */           checkCloseFrameBody(paramChannelHandlerContext, this.framePayload);
/* 318 */           this.receivedClosingHandshake = true;
/* 319 */           paramList.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 320 */           this.framePayload = null;
/* 321 */           return;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 326 */         if (this.frameFinalFlag)
/*     */         {
/*     */ 
/* 329 */           if (this.frameOpcode != 9) {
/* 330 */             this.fragmentedFramesCount = 0;
/*     */             
/*     */ 
/* 333 */             if ((this.frameOpcode == 1) || ((this.utf8Validator != null) && (this.utf8Validator.isChecking())))
/*     */             {
/*     */ 
/* 336 */               checkUTF8String(paramChannelHandlerContext, this.framePayload);
/*     */               
/*     */ 
/*     */ 
/* 340 */               this.utf8Validator.finish();
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 346 */           if (this.fragmentedFramesCount == 0)
/*     */           {
/* 348 */             if (this.frameOpcode == 1) {
/* 349 */               checkUTF8String(paramChannelHandlerContext, this.framePayload);
/*     */             }
/*     */             
/*     */           }
/* 353 */           else if ((this.utf8Validator != null) && (this.utf8Validator.isChecking())) {
/* 354 */             checkUTF8String(paramChannelHandlerContext, this.framePayload);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 359 */           this.fragmentedFramesCount += 1;
/*     */         }
/*     */         
/*     */ 
/* 363 */         if (this.frameOpcode == 1) {
/* 364 */           paramList.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 365 */           this.framePayload = null;
/* 366 */           return; }
/* 367 */         if (this.frameOpcode == 2) {
/* 368 */           paramList.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 369 */           this.framePayload = null;
/* 370 */           return; }
/* 371 */         if (this.frameOpcode == 0) {
/* 372 */           paramList.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, this.framePayload));
/* 373 */           this.framePayload = null;
/* 374 */           return;
/*     */         }
/* 376 */         throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       case CORRUPT: 
/* 382 */         paramByteBuf.readByte();
/* 383 */         return;
/*     */       }
/* 385 */       throw new Error("Shouldn't reach here.");
/*     */     }
/*     */     catch (Exception localException) {
/* 388 */       if (this.payloadBuffer != null) {
/* 389 */         if (this.payloadBuffer.refCnt() > 0) {
/* 390 */           this.payloadBuffer.release();
/*     */         }
/* 392 */         this.payloadBuffer = null;
/*     */       }
/* 394 */       if (this.framePayload != null) {
/* 395 */         if (this.framePayload.refCnt() > 0) {
/* 396 */           this.framePayload.release();
/*     */         }
/* 398 */         this.framePayload = null;
/*     */       }
/* 400 */       throw localException;
/*     */     }
/*     */   }
/*     */   
/*     */   private void unmask(ByteBuf paramByteBuf) {
/* 405 */     for (int i = paramByteBuf.readerIndex(); i < paramByteBuf.writerIndex(); i++) {
/* 406 */       paramByteBuf.setByte(i, paramByteBuf.getByte(i) ^ this.maskingKey[(i % 4)]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext paramChannelHandlerContext, String paramString) {
/* 411 */     protocolViolation(paramChannelHandlerContext, new CorruptedFrameException(paramString));
/*     */   }
/*     */   
/*     */   private void protocolViolation(ChannelHandlerContext paramChannelHandlerContext, CorruptedFrameException paramCorruptedFrameException) {
/* 415 */     checkpoint(State.CORRUPT);
/* 416 */     if (paramChannelHandlerContext.channel().isActive()) {
/* 417 */       paramChannelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
/*     */     }
/* 419 */     throw paramCorruptedFrameException;
/*     */   }
/*     */   
/*     */   private static int toFrameLength(long paramLong) {
/* 423 */     if (paramLong > 2147483647L) {
/* 424 */       throw new TooLongFrameException("Length:" + paramLong);
/*     */     }
/* 426 */     return (int)paramLong;
/*     */   }
/*     */   
/*     */   private void checkUTF8String(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
/*     */   {
/*     */     try {
/* 432 */       if (this.utf8Validator == null) {
/* 433 */         this.utf8Validator = new Utf8Validator();
/*     */       }
/* 435 */       this.utf8Validator.check(paramByteBuf);
/*     */     } catch (CorruptedFrameException localCorruptedFrameException) {
/* 437 */       protocolViolation(paramChannelHandlerContext, localCorruptedFrameException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void checkCloseFrameBody(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
/*     */   {
/* 444 */     if ((paramByteBuf == null) || (!paramByteBuf.isReadable())) {
/* 445 */       return;
/*     */     }
/* 447 */     if (paramByteBuf.readableBytes() == 1) {
/* 448 */       protocolViolation(paramChannelHandlerContext, "Invalid close frame body");
/*     */     }
/*     */     
/*     */ 
/* 452 */     int i = paramByteBuf.readerIndex();
/* 453 */     paramByteBuf.readerIndex(0);
/*     */     
/*     */ 
/* 456 */     int j = paramByteBuf.readShort();
/* 457 */     if (((j >= 0) && (j <= 999)) || ((j >= 1004) && (j <= 1006)) || ((j >= 1012) && (j <= 2999)))
/*     */     {
/* 459 */       protocolViolation(paramChannelHandlerContext, "Invalid close frame getStatus code: " + j);
/*     */     }
/*     */     
/*     */ 
/* 463 */     if (paramByteBuf.isReadable()) {
/*     */       try {
/* 465 */         new Utf8Validator().check(paramByteBuf);
/*     */       } catch (CorruptedFrameException localCorruptedFrameException) {
/* 467 */         protocolViolation(paramChannelHandlerContext, localCorruptedFrameException);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 472 */     paramByteBuf.readerIndex(i);
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 477 */     super.channelInactive(paramChannelHandlerContext);
/*     */     
/*     */ 
/*     */ 
/* 481 */     if (this.framePayload != null) {
/* 482 */       this.framePayload.release();
/*     */     }
/* 484 */     if (this.payloadBuffer != null) {
/* 485 */       this.payloadBuffer.release();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */