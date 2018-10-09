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
/*    */ public enum SocksAddressType
/*    */ {
/* 20 */   IPv4((byte)1), 
/* 21 */   DOMAIN((byte)3), 
/* 22 */   IPv6((byte)4), 
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksAddressType(byte paramByte) {
/* 28 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksAddressType fromByte(byte paramByte)
/*    */   {
/* 36 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksAddressType valueOf(byte paramByte) {
/* 40 */     for (SocksAddressType localSocksAddressType : ) {
/* 41 */       if (localSocksAddressType.b == paramByte) {
/* 42 */         return localSocksAddressType;
/*    */       }
/*    */     }
/* 45 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 49 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAddressType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */