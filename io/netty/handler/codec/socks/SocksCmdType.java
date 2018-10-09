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
/*    */ public enum SocksCmdType
/*    */ {
/* 20 */   CONNECT((byte)1), 
/* 21 */   BIND((byte)2), 
/* 22 */   UDP((byte)3), 
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksCmdType(byte paramByte) {
/* 28 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksCmdType fromByte(byte paramByte)
/*    */   {
/* 36 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksCmdType valueOf(byte paramByte) {
/* 40 */     for (SocksCmdType localSocksCmdType : ) {
/* 41 */       if (localSocksCmdType.b == paramByte) {
/* 42 */         return localSocksCmdType;
/*    */       }
/*    */     }
/* 45 */     return UNKNOWN;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 49 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */