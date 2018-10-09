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
/*    */ public enum SocksProtocolVersion
/*    */ {
/* 20 */   SOCKS4a((byte)4), 
/* 21 */   SOCKS5((byte)5), 
/* 22 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksProtocolVersion(byte paramByte) {
/* 27 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksProtocolVersion fromByte(byte paramByte)
/*    */   {
/* 35 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksProtocolVersion valueOf(byte paramByte) {
/* 39 */     for (SocksProtocolVersion localSocksProtocolVersion : ) {
/* 40 */       if (localSocksProtocolVersion.b == paramByte) {
/* 41 */         return localSocksProtocolVersion;
/*    */       }
/*    */     }
/* 44 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 48 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksProtocolVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */