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
/*    */ public enum SocksAuthScheme
/*    */ {
/* 20 */   NO_AUTH((byte)0), 
/* 21 */   AUTH_GSSAPI((byte)1), 
/* 22 */   AUTH_PASSWORD((byte)2), 
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksAuthScheme(byte paramByte) {
/* 28 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksAuthScheme fromByte(byte paramByte)
/*    */   {
/* 36 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksAuthScheme valueOf(byte paramByte) {
/* 40 */     for (SocksAuthScheme localSocksAuthScheme : ) {
/* 41 */       if (localSocksAuthScheme.b == paramByte) {
/* 42 */         return localSocksAuthScheme;
/*    */       }
/*    */     }
/* 45 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 49 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthScheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */