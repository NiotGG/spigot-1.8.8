/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufAllocator;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ChannelHandler.Sharable
/*    */ public class WebSocket00FrameEncoder
/*    */   extends MessageToMessageEncoder<WebSocketFrame>
/*    */   implements WebSocketFrameEncoder
/*    */ {
/* 34 */   private static final ByteBuf _0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0));
/*    */   
/* 36 */   private static final ByteBuf _0XFF = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1));
/*    */   
/* 38 */   private static final ByteBuf _0XFF_0X00 = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0));
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, WebSocketFrame paramWebSocketFrame, List<Object> paramList) throws Exception
/*    */   {
/*    */     ByteBuf localByteBuf1;
/* 43 */     if ((paramWebSocketFrame instanceof TextWebSocketFrame))
/*    */     {
/* 45 */       localByteBuf1 = paramWebSocketFrame.content();
/*    */       
/* 47 */       paramList.add(_0X00.duplicate());
/* 48 */       paramList.add(localByteBuf1.retain());
/* 49 */       paramList.add(_0XFF.duplicate());
/* 50 */     } else if ((paramWebSocketFrame instanceof CloseWebSocketFrame))
/*    */     {
/*    */ 
/* 53 */       paramList.add(_0XFF_0X00.duplicate());
/*    */     }
/*    */     else {
/* 56 */       localByteBuf1 = paramWebSocketFrame.content();
/* 57 */       int i = localByteBuf1.readableBytes();
/*    */       
/* 59 */       ByteBuf localByteBuf2 = paramChannelHandlerContext.alloc().buffer(5);
/* 60 */       int j = 1;
/*    */       try
/*    */       {
/* 63 */         localByteBuf2.writeByte(-128);
/*    */         
/*    */ 
/* 66 */         int k = i >>> 28 & 0x7F;
/* 67 */         int m = i >>> 14 & 0x7F;
/* 68 */         int n = i >>> 7 & 0x7F;
/* 69 */         int i1 = i & 0x7F;
/* 70 */         if (k == 0) {
/* 71 */           if (m == 0) {
/* 72 */             if (n == 0) {
/* 73 */               localByteBuf2.writeByte(i1);
/*    */             } else {
/* 75 */               localByteBuf2.writeByte(n | 0x80);
/* 76 */               localByteBuf2.writeByte(i1);
/*    */             }
/*    */           } else {
/* 79 */             localByteBuf2.writeByte(m | 0x80);
/* 80 */             localByteBuf2.writeByte(n | 0x80);
/* 81 */             localByteBuf2.writeByte(i1);
/*    */           }
/*    */         } else {
/* 84 */           localByteBuf2.writeByte(k | 0x80);
/* 85 */           localByteBuf2.writeByte(m | 0x80);
/* 86 */           localByteBuf2.writeByte(n | 0x80);
/* 87 */           localByteBuf2.writeByte(i1);
/*    */         }
/*    */         
/*    */ 
/* 91 */         paramList.add(localByteBuf2);
/* 92 */         paramList.add(localByteBuf1.retain());
/* 93 */         j = 0;
/*    */       } finally {
/* 95 */         if (j != 0) {
/* 96 */           localByteBuf2.release();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocket00FrameEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */