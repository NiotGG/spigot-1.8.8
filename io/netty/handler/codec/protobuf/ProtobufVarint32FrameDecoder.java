/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.CodedInputStream;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
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
/*    */ public class ProtobufVarint32FrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*    */     throws Exception
/*    */   {
/* 49 */     paramByteBuf.markReaderIndex();
/* 50 */     byte[] arrayOfByte = new byte[5];
/* 51 */     for (int i = 0; i < arrayOfByte.length; i++) {
/* 52 */       if (!paramByteBuf.isReadable()) {
/* 53 */         paramByteBuf.resetReaderIndex();
/* 54 */         return;
/*    */       }
/*    */       
/* 57 */       arrayOfByte[i] = paramByteBuf.readByte();
/* 58 */       if (arrayOfByte[i] >= 0) {
/* 59 */         int j = CodedInputStream.newInstance(arrayOfByte, 0, i + 1).readRawVarint32();
/* 60 */         if (j < 0) {
/* 61 */           throw new CorruptedFrameException("negative length: " + j);
/*    */         }
/*    */         
/* 64 */         if (paramByteBuf.readableBytes() < j) {
/* 65 */           paramByteBuf.resetReaderIndex();
/* 66 */           return;
/*    */         }
/* 68 */         paramList.add(paramByteBuf.readBytes(j));
/* 69 */         return;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/* 75 */     throw new CorruptedFrameException("length wider than 32-bit");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\protobuf\ProtobufVarint32FrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */