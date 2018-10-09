/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ abstract class SpdyHeaderBlockEncoder
/*    */ {
/*    */   static SpdyHeaderBlockEncoder newInstance(SpdyVersion paramSpdyVersion, int paramInt1, int paramInt2, int paramInt3)
/*    */   {
/* 26 */     if (PlatformDependent.javaVersion() >= 7) {
/* 27 */       return new SpdyHeaderBlockZlibEncoder(paramSpdyVersion, paramInt1);
/*    */     }
/*    */     
/* 30 */     return new SpdyHeaderBlockJZlibEncoder(paramSpdyVersion, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   
/*    */   abstract ByteBuf encode(SpdyHeadersFrame paramSpdyHeadersFrame)
/*    */     throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */