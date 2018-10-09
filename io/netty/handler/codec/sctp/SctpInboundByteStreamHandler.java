/*    */ package io.netty.handler.codec.sctp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.sctp.SctpMessage;
/*    */ import io.netty.handler.codec.CodecException;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ public class SctpInboundByteStreamHandler
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/*    */   private final int protocolIdentifier;
/*    */   private final int streamIdentifier;
/*    */   
/*    */   public SctpInboundByteStreamHandler(int paramInt1, int paramInt2)
/*    */   {
/* 40 */     this.protocolIdentifier = paramInt1;
/* 41 */     this.streamIdentifier = paramInt2;
/*    */   }
/*    */   
/*    */   public final boolean acceptInboundMessage(Object paramObject) throws Exception
/*    */   {
/* 46 */     if (super.acceptInboundMessage(paramObject)) {
/* 47 */       return acceptInboundMessage((SctpMessage)paramObject);
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean acceptInboundMessage(SctpMessage paramSctpMessage) {
/* 53 */     return (paramSctpMessage.protocolIdentifier() == this.protocolIdentifier) && (paramSctpMessage.streamIdentifier() == this.streamIdentifier);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, SctpMessage paramSctpMessage, List<Object> paramList) throws Exception
/*    */   {
/* 58 */     if (!paramSctpMessage.isComplete()) {
/* 59 */       throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", new Object[] { SctpMessageCompletionHandler.class.getSimpleName() }));
/*    */     }
/*    */     
/* 62 */     paramList.add(paramSctpMessage.content().retain());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\sctp\SctpInboundByteStreamHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */