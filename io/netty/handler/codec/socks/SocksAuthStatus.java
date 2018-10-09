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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SocksAuthStatus
/*    */ {
/* 20 */   SUCCESS((byte)0), 
/* 21 */   FAILURE((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksAuthStatus(byte paramByte) {
/* 26 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksAuthStatus fromByte(byte paramByte)
/*    */   {
/* 34 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksAuthStatus valueOf(byte paramByte) {
/* 38 */     for (SocksAuthStatus localSocksAuthStatus : ) {
/* 39 */       if (localSocksAuthStatus.b == paramByte) {
/* 40 */         return localSocksAuthStatus;
/*    */       }
/*    */     }
/* 43 */     return FAILURE;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 47 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */