/*    */ package io.netty.handler.codec.sctp;
/*    */ 
/*    */ import io.netty.channel.sctp.SctpMessage;
/*    */ import io.netty.handler.codec.CodecException;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ public abstract class SctpMessageToMessageDecoder
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/*    */   public boolean acceptInboundMessage(Object paramObject)
/*    */     throws Exception
/*    */   {
/* 27 */     if ((paramObject instanceof SctpMessage)) {
/* 28 */       SctpMessage localSctpMessage = (SctpMessage)paramObject;
/* 29 */       if (localSctpMessage.isComplete()) {
/* 30 */         return true;
/*    */       }
/*    */       
/* 33 */       throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", new Object[] { SctpMessageCompletionHandler.class.getSimpleName() }));
/*    */     }
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\sctp\SctpMessageToMessageDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */