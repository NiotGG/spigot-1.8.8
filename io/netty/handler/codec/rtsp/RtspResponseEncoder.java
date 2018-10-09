/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.handler.codec.http.FullHttpResponse;
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import io.netty.handler.codec.http.HttpResponse;
/*    */ import io.netty.handler.codec.http.HttpResponseStatus;
/*    */ import io.netty.handler.codec.http.HttpVersion;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class RtspResponseEncoder
/*    */   extends RtspObjectEncoder<HttpResponse>
/*    */ {
/* 31 */   private static final byte[] CRLF = { 13, 10 };
/*    */   
/*    */   public boolean acceptOutboundMessage(Object paramObject) throws Exception
/*    */   {
/* 35 */     return paramObject instanceof FullHttpResponse;
/*    */   }
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf paramByteBuf, HttpResponse paramHttpResponse)
/*    */     throws Exception
/*    */   {
/* 41 */     HttpHeaders.encodeAscii(paramHttpResponse.getProtocolVersion().toString(), paramByteBuf);
/* 42 */     paramByteBuf.writeByte(32);
/* 43 */     paramByteBuf.writeBytes(String.valueOf(paramHttpResponse.getStatus().code()).getBytes(CharsetUtil.US_ASCII));
/* 44 */     paramByteBuf.writeByte(32);
/* 45 */     encodeAscii(String.valueOf(paramHttpResponse.getStatus().reasonPhrase()), paramByteBuf);
/* 46 */     paramByteBuf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\rtsp\RtspResponseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */