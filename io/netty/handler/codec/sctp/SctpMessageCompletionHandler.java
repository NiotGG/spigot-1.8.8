/*    */ package io.netty.handler.codec.sctp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.sctp.SctpMessage;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class SctpMessageCompletionHandler
/*    */   extends MessageToMessageDecoder<SctpMessage>
/*    */ {
/* 36 */   private final Map<Integer, ByteBuf> fragments = new HashMap();
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, SctpMessage paramSctpMessage, List<Object> paramList) throws Exception
/*    */   {
/* 40 */     ByteBuf localByteBuf1 = paramSctpMessage.content();
/* 41 */     int i = paramSctpMessage.protocolIdentifier();
/* 42 */     int j = paramSctpMessage.streamIdentifier();
/* 43 */     boolean bool = paramSctpMessage.isComplete();
/*    */     
/*    */     ByteBuf localByteBuf2;
/* 46 */     if (this.fragments.containsKey(Integer.valueOf(j))) {
/* 47 */       localByteBuf2 = (ByteBuf)this.fragments.remove(Integer.valueOf(j));
/*    */     } else {
/* 49 */       localByteBuf2 = Unpooled.EMPTY_BUFFER;
/*    */     }
/*    */     
/* 52 */     if ((bool) && (!localByteBuf2.isReadable()))
/*    */     {
/* 54 */       paramList.add(paramSctpMessage);
/* 55 */     } else if ((!bool) && (localByteBuf2.isReadable()))
/*    */     {
/* 57 */       this.fragments.put(Integer.valueOf(j), Unpooled.wrappedBuffer(new ByteBuf[] { localByteBuf2, localByteBuf1 }));
/* 58 */     } else if ((bool) && (localByteBuf2.isReadable()))
/*    */     {
/* 60 */       this.fragments.remove(Integer.valueOf(j));
/* 61 */       SctpMessage localSctpMessage = new SctpMessage(i, j, Unpooled.wrappedBuffer(new ByteBuf[] { localByteBuf2, localByteBuf1 }));
/*    */       
/*    */ 
/*    */ 
/* 65 */       paramList.add(localSctpMessage);
/*    */     }
/*    */     else {
/* 68 */       this.fragments.put(Integer.valueOf(j), localByteBuf1);
/*    */     }
/* 70 */     localByteBuf1.retain();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\sctp\SctpMessageCompletionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */