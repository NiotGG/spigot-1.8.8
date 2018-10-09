/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpObjectDecoder;
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
/*    */ public abstract class RtspObjectDecoder
/*    */   extends HttpObjectDecoder
/*    */ {
/*    */   protected RtspObjectDecoder()
/*    */   {
/* 59 */     this(4096, 8192, 8192);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected RtspObjectDecoder(int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 66 */     super(paramInt1, paramInt2, paramInt3 * 2, false);
/*    */   }
/*    */   
/*    */   protected RtspObjectDecoder(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*    */   {
/* 71 */     super(paramInt1, paramInt2, paramInt3 * 2, false, paramBoolean);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected boolean isContentAlwaysEmpty(HttpMessage paramHttpMessage)
/*    */   {
/* 78 */     boolean bool = super.isContentAlwaysEmpty(paramHttpMessage);
/* 79 */     if (bool) {
/* 80 */       return true;
/*    */     }
/* 82 */     if (!paramHttpMessage.headers().contains("Content-Length")) {
/* 83 */       return true;
/*    */     }
/* 85 */     return bool;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\rtsp\RtspObjectDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */