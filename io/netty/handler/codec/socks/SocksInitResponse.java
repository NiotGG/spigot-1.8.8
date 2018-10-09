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
/*    */ public final class SocksInitResponse
/*    */   extends SocksResponse
/*    */ {
/*    */   private final SocksAuthScheme authScheme;
/*    */   
/*    */   public SocksInitResponse(SocksAuthScheme paramSocksAuthScheme)
/*    */   {
/* 30 */     super(SocksResponseType.INIT);
/* 31 */     if (paramSocksAuthScheme == null) {
/* 32 */       throw new NullPointerException("authScheme");
/*    */     }
/* 34 */     this.authScheme = paramSocksAuthScheme;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SocksAuthScheme authScheme()
/*    */   {
/* 43 */     return this.authScheme;
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*    */   {
/* 48 */     paramByteBuf.writeByte(protocolVersion().byteValue());
/* 49 */     paramByteBuf.writeByte(this.authScheme.byteValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksInitResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */