/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageEncoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class WebSocket08FrameEncoder
/*     */   extends MessageToMessageEncoder<WebSocketFrame>
/*     */   implements WebSocketFrameEncoder
/*     */ {
/*  74 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
/*     */   
/*     */   private static final byte OPCODE_CONT = 0;
/*     */   
/*     */   private static final byte OPCODE_TEXT = 1;
/*     */   
/*     */   private static final byte OPCODE_BINARY = 2;
/*     */   
/*     */   private static final byte OPCODE_CLOSE = 8;
/*     */   
/*     */   private static final byte OPCODE_PING = 9;
/*     */   
/*     */   private static final byte OPCODE_PONG = 10;
/*     */   
/*     */   private final boolean maskPayload;
/*     */   
/*     */ 
/*     */   public WebSocket08FrameEncoder(boolean paramBoolean)
/*     */   {
/*  93 */     this.maskPayload = paramBoolean;
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList) throws Exception
/*     */   {
/*  98 */     ByteBuf localByteBuf1 = paramWebSocketFrame.content();
/*     */     
/*     */     int i;
/*     */     
/* 102 */     if ((paramWebSocketFrame instanceof TextWebSocketFrame)) {
/* 103 */       i = 1;
/* 104 */     } else if ((paramWebSocketFrame instanceof PingWebSocketFrame)) {
/* 105 */       i = 9;
/* 106 */     } else if ((paramWebSocketFrame instanceof PongWebSocketFrame)) {
/* 107 */       i = 10;
/* 108 */     } else if ((paramWebSocketFrame instanceof CloseWebSocketFrame)) {
/* 109 */       i = 8;
/* 110 */     } else if ((paramWebSocketFrame instanceof BinaryWebSocketFrame)) {
/* 111 */       i = 2;
/* 112 */     } else if ((paramWebSocketFrame instanceof ContinuationWebSocketFrame)) {
/* 113 */       i = 0;
/*     */     } else {
/* 115 */       throw new UnsupportedOperationException("Cannot encode frame of type: " + paramWebSocketFrame.getClass().getName());
/*     */     }
/*     */     
/* 118 */     int j = localByteBuf1.readableBytes();
/*     */     
/* 120 */     if (logger.isDebugEnabled()) {
/* 121 */       logger.debug("Encoding WebSocket Frame opCode=" + i + " length=" + j);
/*     */     }
/*     */     
/* 124 */     int k = 0;
/* 125 */     if (paramWebSocketFrame.isFinalFragment()) {
/* 126 */       k |= 0x80;
/*     */     }
/* 128 */     k |= paramWebSocketFrame.rsv() % 8 << 4;
/* 129 */     k |= i % 128;
/*     */     
/* 131 */     if ((i == 9) && (j > 125)) {
/* 132 */       throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + j);
/*     */     }
/*     */     
/*     */ 
/* 136 */     int m = 1;
/* 137 */     ByteBuf localByteBuf2 = null;
/*     */     try {
/* 139 */       int n = this.maskPayload ? 4 : 0;
/* 140 */       int i1; int i2; if (j <= 125) {
/* 141 */         i1 = 2 + n;
/* 142 */         if (this.maskPayload) {
/* 143 */           i1 += j;
/*     */         }
/* 145 */         localByteBuf2 = paramChannelHandlerContext.alloc().buffer(i1);
/* 146 */         localByteBuf2.writeByte(k);
/* 147 */         i2 = (byte)(this.maskPayload ? 0x80 | (byte)j : (byte)j);
/* 148 */         localByteBuf2.writeByte(i2);
/* 149 */       } else if (j <= 65535) {
/* 150 */         i1 = 4 + n;
/* 151 */         if (this.maskPayload) {
/* 152 */           i1 += j;
/*     */         }
/* 154 */         localByteBuf2 = paramChannelHandlerContext.alloc().buffer(i1);
/* 155 */         localByteBuf2.writeByte(k);
/* 156 */         localByteBuf2.writeByte(this.maskPayload ? 254 : 126);
/* 157 */         localByteBuf2.writeByte(j >>> 8 & 0xFF);
/* 158 */         localByteBuf2.writeByte(j & 0xFF);
/*     */       } else {
/* 160 */         i1 = 10 + n;
/* 161 */         if (this.maskPayload) {
/* 162 */           i1 += j;
/*     */         }
/* 164 */         localByteBuf2 = paramChannelHandlerContext.alloc().buffer(i1);
/* 165 */         localByteBuf2.writeByte(k);
/* 166 */         localByteBuf2.writeByte(this.maskPayload ? 255 : 127);
/* 167 */         localByteBuf2.writeLong(j);
/*     */       }
/*     */       
/*     */ 
/* 171 */       if (this.maskPayload) {
/* 172 */         i1 = (int)(Math.random() * 2.147483647E9D);
/* 173 */         byte[] arrayOfByte = ByteBuffer.allocate(4).putInt(i1).array();
/* 174 */         localByteBuf2.writeBytes(arrayOfByte);
/*     */         
/* 176 */         i2 = 0;
/* 177 */         for (int i3 = localByteBuf1.readerIndex(); i3 < localByteBuf1.writerIndex(); i3++) {
/* 178 */           int i4 = localByteBuf1.getByte(i3);
/* 179 */           localByteBuf2.writeByte(i4 ^ arrayOfByte[(i2++ % 4)]);
/*     */         }
/* 181 */         paramList.add(localByteBuf2);
/*     */       }
/* 183 */       else if (localByteBuf2.writableBytes() >= localByteBuf1.readableBytes())
/*     */       {
/* 185 */         localByteBuf2.writeBytes(localByteBuf1);
/* 186 */         paramList.add(localByteBuf2);
/*     */       } else {
/* 188 */         paramList.add(localByteBuf2);
/* 189 */         paramList.add(localByteBuf1.retain());
/*     */       }
/*     */       
/* 192 */       m = 0;
/*     */     } finally {
/* 194 */       if ((m != 0) && (localByteBuf2 != null)) {
/* 195 */         localByteBuf2.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocket08FrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */