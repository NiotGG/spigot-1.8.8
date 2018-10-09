/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpResponseDecoder
/*     */   extends HttpObjectDecoder
/*     */ {
/*  86 */   private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResponseDecoder() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResponseDecoder(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 101 */     super(paramInt1, paramInt2, paramInt3, true);
/*     */   }
/*     */   
/*     */   public HttpResponseDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/* 106 */     super(paramInt1, paramInt2, paramInt3, true, paramBoolean);
/*     */   }
/*     */   
/*     */   protected HttpMessage createMessage(String[] paramArrayOfString)
/*     */   {
/* 111 */     return new DefaultHttpResponse(HttpVersion.valueOf(paramArrayOfString[0]), new HttpResponseStatus(Integer.parseInt(paramArrayOfString[1]), paramArrayOfString[2]), this.validateHeaders);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected HttpMessage createInvalidMessage()
/*     */   {
/* 118 */     return new DefaultHttpResponse(HttpVersion.HTTP_1_0, UNKNOWN_STATUS, this.validateHeaders);
/*     */   }
/*     */   
/*     */   protected boolean isDecodingRequest()
/*     */   {
/* 123 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */