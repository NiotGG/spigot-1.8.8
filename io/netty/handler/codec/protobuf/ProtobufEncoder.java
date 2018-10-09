/*    */ package io.netty.handler.codec.protobuf;
/*    */ 
/*    */ import com.google.protobuf.MessageLite;
/*    */ import com.google.protobuf.MessageLite.Builder;
/*    */ import com.google.protobuf.MessageLiteOrBuilder;
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
/*    */ public class ProtobufEncoder
/*    */   extends MessageToMessageEncoder<MessageLiteOrBuilder>
/*    */ {
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, MessageLiteOrBuilder paramMessageLiteOrBuilder, List<Object> paramList)
/*    */     throws Exception
/*    */   {
/* 65 */     if ((paramMessageLiteOrBuilder instanceof MessageLite)) {
/* 66 */       paramList.add(Unpooled.wrappedBuffer(((MessageLite)paramMessageLiteOrBuilder).toByteArray()));
/* 67 */       return;
/*    */     }
/* 69 */     if ((paramMessageLiteOrBuilder instanceof MessageLite.Builder)) {
/* 70 */       paramList.add(Unpooled.wrappedBuffer(((MessageLite.Builder)paramMessageLiteOrBuilder).build().toByteArray()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\protobuf\ProtobufEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */