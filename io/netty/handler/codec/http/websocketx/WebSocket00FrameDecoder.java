/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
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
/*     */ public class WebSocket00FrameDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */   implements WebSocketFrameDecoder
/*     */ {
/*     */   static final int DEFAULT_MAX_FRAME_SIZE = 16384;
/*     */   private final long maxFrameSize;
/*     */   private boolean receivedClosingHandshake;
/*     */   
/*     */   public WebSocket00FrameDecoder()
/*     */   {
/*  39 */     this(16384);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public WebSocket00FrameDecoder(int paramInt)
/*     */   {
/*  50 */     this.maxFrameSize = paramInt;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/*  56 */     if (this.receivedClosingHandshake) {
/*  57 */       paramByteBuf.skipBytes(actualReadableBytes());
/*  58 */       return;
/*     */     }
/*     */     
/*     */ 
/*  62 */     int i = paramByteBuf.readByte();
/*     */     WebSocketFrame localWebSocketFrame;
/*  64 */     if ((i & 0x80) == 128)
/*     */     {
/*  66 */       localWebSocketFrame = decodeBinaryFrame(paramChannelHandlerContext, i, paramByteBuf);
/*     */     }
/*     */     else {
/*  69 */       localWebSocketFrame = decodeTextFrame(paramChannelHandlerContext, paramByteBuf);
/*     */     }
/*     */     
/*  72 */     if (localWebSocketFrame != null) {
/*  73 */       paramList.add(localWebSocketFrame);
/*     */     }
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeBinaryFrame(ChannelHandlerContext paramChannelHandlerContext, byte paramByte, ByteBuf paramByteBuf) {
/*  78 */     long l = 0L;
/*  79 */     int i = 0;
/*     */     int j;
/*     */     do {
/*  82 */       j = paramByteBuf.readByte();
/*  83 */       l <<= 7;
/*  84 */       l |= j & 0x7F;
/*  85 */       if (l > this.maxFrameSize) {
/*  86 */         throw new TooLongFrameException();
/*     */       }
/*  88 */       i++;
/*  89 */       if (i > 8)
/*     */       {
/*  91 */         throw new TooLongFrameException();
/*     */       }
/*  93 */     } while ((j & 0x80) == 128);
/*     */     
/*  95 */     if ((paramByte == -1) && (l == 0L)) {
/*  96 */       this.receivedClosingHandshake = true;
/*  97 */       return new CloseWebSocketFrame();
/*     */     }
/*  99 */     ByteBuf localByteBuf = paramChannelHandlerContext.alloc().buffer((int)l);
/* 100 */     paramByteBuf.readBytes(localByteBuf);
/* 101 */     return new BinaryWebSocketFrame(localByteBuf);
/*     */   }
/*     */   
/*     */   private WebSocketFrame decodeTextFrame(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
/* 105 */     int i = paramByteBuf.readerIndex();
/* 106 */     int j = actualReadableBytes();
/* 107 */     int k = paramByteBuf.indexOf(i, i + j, (byte)-1);
/* 108 */     if (k == -1)
/*     */     {
/* 110 */       if (j > this.maxFrameSize)
/*     */       {
/* 112 */         throw new TooLongFrameException();
/*     */       }
/*     */       
/* 115 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 119 */     int m = k - i;
/* 120 */     if (m > this.maxFrameSize) {
/* 121 */       throw new TooLongFrameException();
/*     */     }
/*     */     
/* 124 */     ByteBuf localByteBuf = paramChannelHandlerContext.alloc().buffer(m);
/* 125 */     paramByteBuf.readBytes(localByteBuf);
/* 126 */     paramByteBuf.skipBytes(1);
/*     */     
/* 128 */     int n = localByteBuf.indexOf(localByteBuf.readerIndex(), localByteBuf.writerIndex(), (byte)-1);
/* 129 */     if (n >= 0) {
/* 130 */       throw new IllegalArgumentException("a text frame should not contain 0xFF.");
/*     */     }
/*     */     
/* 133 */     return new TextWebSocketFrame(localByteBuf);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocket00FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */