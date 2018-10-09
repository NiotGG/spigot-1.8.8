/*    */ package io.netty.handler.codec.sctp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.sctp.SctpMessage;
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
/*    */ public class SctpOutboundByteStreamHandler
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final int streamIdentifier;
/*    */   private final int protocolIdentifier;
/*    */   
/*    */   public SctpOutboundByteStreamHandler(int paramInt1, int paramInt2)
/*    */   {
/* 39 */     this.streamIdentifier = paramInt1;
/* 40 */     this.protocolIdentifier = paramInt2;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 45 */     paramList.add(new SctpMessage(this.streamIdentifier, this.protocolIdentifier, paramByteBuf.retain()));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\sctp\SctpOutboundByteStreamHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */