/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.util.zip.Deflater;
/*    */ 
/*    */ public class PacketCompressor extends MessageToByteEncoder<ByteBuf>
/*    */ {
/* 10 */   private final byte[] a = new byte[' '];
/*    */   private final Deflater b;
/*    */   private int c;
/*    */   
/*    */   public PacketCompressor(int paramInt) {
/* 15 */     this.c = paramInt;
/* 16 */     this.b = new Deflater();
/*    */   }
/*    */   
/*    */   protected void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*    */   {
/* 21 */     int i = paramByteBuf1.readableBytes();
/* 22 */     PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(paramByteBuf2);
/*    */     
/* 24 */     if (i < this.c) {
/* 25 */       localPacketDataSerializer.b(0);
/* 26 */       localPacketDataSerializer.writeBytes(paramByteBuf1);
/*    */     } else {
/* 28 */       byte[] arrayOfByte = new byte[i];
/* 29 */       paramByteBuf1.readBytes(arrayOfByte);
/*    */       
/* 31 */       localPacketDataSerializer.b(arrayOfByte.length);
/*    */       
/* 33 */       this.b.setInput(arrayOfByte, 0, i);
/* 34 */       this.b.finish();
/* 35 */       while (!this.b.finished()) {
/* 36 */         int j = this.b.deflate(this.a);
/* 37 */         localPacketDataSerializer.writeBytes(this.a, 0, j);
/*    */       }
/* 39 */       this.b.reset();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void a(int paramInt)
/*    */   {
/* 48 */     this.c = paramInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketCompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */