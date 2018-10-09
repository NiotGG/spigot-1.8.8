/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.handler.codec.http.FullHttpRequest;
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import io.netty.handler.codec.http.HttpMethod;
/*    */ import io.netty.handler.codec.http.HttpRequest;
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
/*    */ 
/*    */ public class RtspRequestEncoder
/*    */   extends RtspObjectEncoder<HttpRequest>
/*    */ {
/* 32 */   private static final byte[] CRLF = { 13, 10 };
/*    */   
/*    */   public boolean acceptOutboundMessage(Object paramObject) throws Exception
/*    */   {
/* 36 */     return paramObject instanceof FullHttpRequest;
/*    */   }
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf paramByteBuf, HttpRequest paramHttpRequest)
/*    */     throws Exception
/*    */   {
/* 42 */     HttpHeaders.encodeAscii(paramHttpRequest.getMethod().toString(), paramByteBuf);
/* 43 */     paramByteBuf.writeByte(32);
/* 44 */     paramByteBuf.writeBytes(paramHttpRequest.getUri().getBytes(CharsetUtil.UTF_8));
/* 45 */     paramByteBuf.writeByte(32);
/* 46 */     encodeAscii(paramHttpRequest.getProtocolVersion().toString(), paramByteBuf);
/* 47 */     paramByteBuf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\rtsp\RtspRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */