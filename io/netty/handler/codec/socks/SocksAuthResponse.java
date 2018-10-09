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
/*    */ public final class SocksAuthResponse
/*    */   extends SocksResponse
/*    */ {
/* 27 */   private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
/*    */   private final SocksAuthStatus authStatus;
/*    */   
/*    */   public SocksAuthResponse(SocksAuthStatus paramSocksAuthStatus) {
/* 31 */     super(SocksResponseType.AUTH);
/* 32 */     if (paramSocksAuthStatus == null) {
/* 33 */       throw new NullPointerException("authStatus");
/*    */     }
/* 35 */     this.authStatus = paramSocksAuthStatus;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SocksAuthStatus authStatus()
/*    */   {
/* 44 */     return this.authStatus;
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*    */   {
/* 49 */     paramByteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
/* 50 */     paramByteBuf.writeByte(this.authStatus.byteValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */