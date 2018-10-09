/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.DefaultHttpRequest;
/*    */ import io.netty.handler.codec.http.HttpMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RtspRequestDecoder
/*    */   extends RtspObjectDecoder
/*    */ {
/*    */   public RtspRequestDecoder() {}
/*    */   
/*    */   public RtspRequestDecoder(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 65 */     super(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   public RtspRequestDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 70 */     super(paramInt1, paramInt2, paramInt3, paramBoolean);
/*    */   }
/*    */   
/*    */   protected HttpMessage createMessage(String[] paramArrayOfString) throws Exception
/*    */   {
/* 75 */     return new DefaultHttpRequest(RtspVersions.valueOf(paramArrayOfString[2]), RtspMethods.valueOf(paramArrayOfString[0]), paramArrayOfString[1], this.validateHeaders);
/*    */   }
/*    */   
/*    */ 
/*    */   protected HttpMessage createInvalidMessage()
/*    */   {
/* 81 */     return new DefaultHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", this.validateHeaders);
/*    */   }
/*    */   
/*    */   protected boolean isDecodingRequest()
/*    */   {
/* 86 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\rtsp\RtspRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */