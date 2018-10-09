/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageCodec;
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.util.ReferenceCountUtil;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Queue;
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
/*    */ public class SpdyHttpResponseStreamIdHandler
/*    */   extends MessageToMessageCodec<Object, HttpMessage>
/*    */ {
/* 34 */   private static final Integer NO_ID = Integer.valueOf(-1);
/* 35 */   private final Queue<Integer> ids = new LinkedList();
/*    */   
/*    */   public boolean acceptInboundMessage(Object paramObject) throws Exception
/*    */   {
/* 39 */     return ((paramObject instanceof HttpMessage)) || ((paramObject instanceof SpdyRstStreamFrame));
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, HttpMessage paramHttpMessage, List<Object> paramList) throws Exception
/*    */   {
/* 44 */     Integer localInteger = (Integer)this.ids.poll();
/* 45 */     if ((localInteger != null) && (localInteger.intValue() != NO_ID.intValue()) && (!paramHttpMessage.headers().contains("X-SPDY-Stream-ID"))) {
/* 46 */       SpdyHttpHeaders.setStreamId(paramHttpMessage, localInteger.intValue());
/*    */     }
/*    */     
/* 49 */     paramList.add(ReferenceCountUtil.retain(paramHttpMessage));
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, List<Object> paramList) throws Exception
/*    */   {
/* 54 */     if ((paramObject instanceof HttpMessage)) {
/* 55 */       boolean bool = ((HttpMessage)paramObject).headers().contains("X-SPDY-Stream-ID");
/* 56 */       if (!bool) {
/* 57 */         this.ids.add(NO_ID);
/*    */       } else {
/* 59 */         this.ids.add(Integer.valueOf(SpdyHttpHeaders.getStreamId((HttpMessage)paramObject)));
/*    */       }
/* 61 */     } else if ((paramObject instanceof SpdyRstStreamFrame)) {
/* 62 */       this.ids.remove(Integer.valueOf(((SpdyRstStreamFrame)paramObject).streamId()));
/*    */     }
/*    */     
/* 65 */     paramList.add(ReferenceCountUtil.retain(paramObject));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHttpResponseStreamIdHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */