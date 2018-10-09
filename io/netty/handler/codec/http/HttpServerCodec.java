/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.channel.CombinedChannelDuplexHandler;
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
/*    */ public final class HttpServerCodec
/*    */   extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
/*    */ {
/*    */   public HttpServerCodec()
/*    */   {
/* 36 */     this(4096, 8192, 8192);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HttpServerCodec(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 43 */     super(new HttpRequestDecoder(paramInt1, paramInt2, paramInt3), new HttpResponseEncoder());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public HttpServerCodec(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 50 */     super(new HttpRequestDecoder(paramInt1, paramInt2, paramInt3, paramBoolean), new HttpResponseEncoder());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpServerCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */