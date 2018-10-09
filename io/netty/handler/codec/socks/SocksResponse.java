/*    */ package io.netty.handler.codec.socks;
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
/*    */ public abstract class SocksResponse
/*    */   extends SocksMessage
/*    */ {
/*    */   private final SocksResponseType responseType;
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
/*    */   protected SocksResponse(SocksResponseType paramSocksResponseType)
/*    */   {
/* 31 */     super(SocksMessageType.RESPONSE);
/* 32 */     if (paramSocksResponseType == null) {
/* 33 */       throw new NullPointerException("responseType");
/*    */     }
/* 35 */     this.responseType = paramSocksResponseType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SocksResponseType responseType()
/*    */   {
/* 44 */     return this.responseType;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */