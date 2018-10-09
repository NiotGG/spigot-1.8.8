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
/*    */ public enum SocksSubnegotiationVersion
/*    */ {
/* 20 */   AUTH_PASSWORD((byte)1), 
/* 21 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksSubnegotiationVersion(byte paramByte) {
/* 26 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksSubnegotiationVersion fromByte(byte paramByte)
/*    */   {
/* 34 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksSubnegotiationVersion valueOf(byte paramByte) {
/* 38 */     for (SocksSubnegotiationVersion localSocksSubnegotiationVersion : ) {
/* 39 */       if (localSocksSubnegotiationVersion.b == paramByte) {
/* 40 */         return localSocksSubnegotiationVersion;
/*    */       }
/*    */     }
/* 43 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 47 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksSubnegotiationVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */