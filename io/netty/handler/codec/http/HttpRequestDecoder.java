/*    */ package io.netty.handler.codec.http;
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
/*    */ public class HttpRequestDecoder
/*    */   extends HttpObjectDecoder
/*    */ {
/*    */   public HttpRequestDecoder() {}
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
/*    */   public HttpRequestDecoder(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 70 */     super(paramInt1, paramInt2, paramInt3, true);
/*    */   }
/*    */   
/*    */   public HttpRequestDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 75 */     super(paramInt1, paramInt2, paramInt3, true, paramBoolean);
/*    */   }
/*    */   
/*    */   protected HttpMessage createMessage(String[] paramArrayOfString) throws Exception
/*    */   {
/* 80 */     return new DefaultHttpRequest(HttpVersion.valueOf(paramArrayOfString[2]), HttpMethod.valueOf(paramArrayOfString[0]), paramArrayOfString[1], this.validateHeaders);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected HttpMessage createInvalidMessage()
/*    */   {
/* 87 */     return new DefaultHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, "/bad-request", this.validateHeaders);
/*    */   }
/*    */   
/*    */   protected boolean isDecodingRequest()
/*    */   {
/* 92 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */