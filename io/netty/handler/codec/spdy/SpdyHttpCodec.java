/*    */ package io.netty.handler.codec.spdy;
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
/*    */ public final class SpdyHttpCodec
/*    */   extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder>
/*    */ {
/*    */   public SpdyHttpCodec(SpdyVersion paramSpdyVersion, int paramInt)
/*    */   {
/* 29 */     super(new SpdyHttpDecoder(paramSpdyVersion, paramInt), new SpdyHttpEncoder(paramSpdyVersion));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public SpdyHttpCodec(SpdyVersion paramSpdyVersion, int paramInt, boolean paramBoolean)
/*    */   {
/* 36 */     super(new SpdyHttpDecoder(paramSpdyVersion, paramInt, paramBoolean), new SpdyHttpEncoder(paramSpdyVersion));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHttpCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */