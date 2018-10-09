/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufOutputStream;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
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
/*    */ public class ObjectEncoder
/*    */   extends MessageToByteEncoder<Serializable>
/*    */ {
/* 38 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, Serializable paramSerializable, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 42 */     int i = paramByteBuf.writerIndex();
/*    */     
/* 44 */     ByteBufOutputStream localByteBufOutputStream = new ByteBufOutputStream(paramByteBuf);
/* 45 */     localByteBufOutputStream.write(LENGTH_PLACEHOLDER);
/* 46 */     CompactObjectOutputStream localCompactObjectOutputStream = new CompactObjectOutputStream(localByteBufOutputStream);
/* 47 */     localCompactObjectOutputStream.writeObject(paramSerializable);
/* 48 */     localCompactObjectOutputStream.flush();
/* 49 */     localCompactObjectOutputStream.close();
/*    */     
/* 51 */     int j = paramByteBuf.writerIndex();
/*    */     
/* 53 */     paramByteBuf.setInt(i, j - i - 4);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ObjectEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */