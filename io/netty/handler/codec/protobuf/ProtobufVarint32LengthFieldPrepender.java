/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.CodedOutputStream;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*    */ public class ProtobufVarint32LengthFieldPrepender
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*    */     throws Exception
/*    */   {
/* 45 */     int i = paramByteBuf1.readableBytes();
/* 46 */     int j = CodedOutputStream.computeRawVarint32Size(i);
/* 47 */     paramByteBuf2.ensureWritable(j + i);
/*    */     
/* 49 */     CodedOutputStream localCodedOutputStream = CodedOutputStream.newInstance(new ByteBufOutputStream(paramByteBuf2), j);
/*    */     
/* 51 */     localCodedOutputStream.writeRawVarint32(i);
/* 52 */     localCodedOutputStream.flush();
/*    */     
/* 54 */     paramByteBuf2.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\protobuf\ProtobufVarint32LengthFieldPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */