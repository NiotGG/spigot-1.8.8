/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPrepender
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*    */     throws Exception
/*    */   {
/* 15 */     int i = paramByteBuf1.readableBytes();
/* 16 */     int j = PacketDataSerializer.a(i);
/*    */     
/* 18 */     if (j > 3) {
/* 19 */       throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
/*    */     }
/*    */     
/* 22 */     PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(paramByteBuf2);
/*    */     
/* 24 */     localPacketDataSerializer.ensureWritable(j + i);
/*    */     
/* 26 */     localPacketDataSerializer.b(i);
/* 27 */     localPacketDataSerializer.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */