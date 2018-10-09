/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class HttpResponseEncoder
/*    */   extends HttpObjectEncoder<HttpResponse>
/*    */ {
/* 27 */   private static final byte[] CRLF = { 13, 10 };
/*    */   
/*    */   public boolean acceptOutboundMessage(Object paramObject) throws Exception
/*    */   {
/* 31 */     return (super.acceptOutboundMessage(paramObject)) && (!(paramObject instanceof HttpRequest));
/*    */   }
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf paramByteBuf, HttpResponse paramHttpResponse) throws Exception
/*    */   {
/* 36 */     paramHttpResponse.getProtocolVersion().encode(paramByteBuf);
/* 37 */     paramByteBuf.writeByte(32);
/* 38 */     paramHttpResponse.getStatus().encode(paramByteBuf);
/* 39 */     paramByteBuf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpResponseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */