/*    */ package io.netty.handler.codec.socks;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class UnknownSocksRequest
/*    */   extends SocksRequest
/*    */ {
/*    */   public UnknownSocksRequest()
/*    */   {
/* 30 */     super(SocksRequestType.UNKNOWN);
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf paramByteBuf) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\UnknownSocksRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */