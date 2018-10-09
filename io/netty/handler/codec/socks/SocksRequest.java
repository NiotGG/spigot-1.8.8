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
/*    */ public abstract class SocksRequest
/*    */   extends SocksMessage
/*    */ {
/*    */   private final SocksRequestType requestType;
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
/*    */   protected SocksRequest(SocksRequestType paramSocksRequestType)
/*    */   {
/* 31 */     super(SocksMessageType.REQUEST);
/* 32 */     if (paramSocksRequestType == null) {
/* 33 */       throw new NullPointerException("requestType");
/*    */     }
/* 35 */     this.requestType = paramSocksRequestType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SocksRequestType requestType()
/*    */   {
/* 44 */     return this.requestType;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */