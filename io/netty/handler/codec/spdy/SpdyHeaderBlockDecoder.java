/*    */ package io.netty.handler.codec.spdy;
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
/*    */ abstract class SpdyHeaderBlockDecoder
/*    */ {
/*    */   static SpdyHeaderBlockDecoder newInstance(SpdyVersion paramSpdyVersion, int paramInt)
/*    */   {
/* 23 */     return new SpdyHeaderBlockZlibDecoder(paramSpdyVersion, paramInt);
/*    */   }
/*    */   
/*    */   abstract void decode(ByteBuf paramByteBuf, SpdyHeadersFrame paramSpdyHeadersFrame)
/*    */     throws Exception;
/*    */   
/*    */   abstract void endHeaderBlock(SpdyHeadersFrame paramSpdyHeadersFrame)
/*    */     throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */