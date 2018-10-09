/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PacketSplitter extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 14 */     paramByteBuf.markReaderIndex();
/*    */     
/* 16 */     byte[] arrayOfByte = new byte[3];
/* 17 */     for (int i = 0; i < arrayOfByte.length; i++) {
/* 18 */       if (!paramByteBuf.isReadable()) {
/* 19 */         paramByteBuf.resetReaderIndex();
/* 20 */         return;
/*    */       }
/*    */       
/* 23 */       arrayOfByte[i] = paramByteBuf.readByte();
/* 24 */       if (arrayOfByte[i] >= 0) {
/* 25 */         PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
/*    */         try {
/* 27 */           int j = localPacketDataSerializer.e();
/*    */           
/* 29 */           if (paramByteBuf.readableBytes() < j) {
/* 30 */             paramByteBuf.resetReaderIndex(); return;
/*    */           }
/*    */           
/* 33 */           paramList.add(paramByteBuf.readBytes(j)); return;
/*    */         }
/*    */         finally
/*    */         {
/* 37 */           localPacketDataSerializer.release();
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 42 */     throw new CorruptedFrameException("length wider than 21-bit");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketSplitter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */