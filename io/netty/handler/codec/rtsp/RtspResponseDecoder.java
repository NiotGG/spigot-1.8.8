/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpResponseStatus;
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
/*    */ public class RtspResponseDecoder
/*    */   extends RtspObjectDecoder
/*    */ {
/* 54 */   private static final HttpResponseStatus UNKNOWN_STATUS = new HttpResponseStatus(999, "Unknown");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public RtspResponseDecoder() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public RtspResponseDecoder(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 69 */     super(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   public RtspResponseDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 74 */     super(paramInt1, paramInt2, paramInt3, paramBoolean);
/*    */   }
/*    */   
/*    */   protected HttpMessage createMessage(String[] paramArrayOfString) throws Exception
/*    */   {
/* 79 */     return new DefaultHttpResponse(RtspVersions.valueOf(paramArrayOfString[0]), new HttpResponseStatus(Integer.parseInt(paramArrayOfString[1]), paramArrayOfString[2]), this.validateHeaders);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected HttpMessage createInvalidMessage()
/*    */   {
/* 86 */     return new DefaultHttpResponse(RtspVersions.RTSP_1_0, UNKNOWN_STATUS, this.validateHeaders);
/*    */   }
/*    */   
/*    */   protected boolean isDecodingRequest()
/*    */   {
/* 91 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\rtsp\RtspResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */