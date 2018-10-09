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
/*    */ public enum SocksCmdStatus
/*    */ {
/* 20 */   SUCCESS((byte)0), 
/* 21 */   FAILURE((byte)1), 
/* 22 */   FORBIDDEN((byte)2), 
/* 23 */   NETWORK_UNREACHABLE((byte)3), 
/* 24 */   HOST_UNREACHABLE((byte)4), 
/* 25 */   REFUSED((byte)5), 
/* 26 */   TTL_EXPIRED((byte)6), 
/* 27 */   COMMAND_NOT_SUPPORTED((byte)7), 
/* 28 */   ADDRESS_NOT_SUPPORTED((byte)8), 
/* 29 */   UNASSIGNED((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   private SocksCmdStatus(byte paramByte) {
/* 34 */     this.b = paramByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SocksCmdStatus fromByte(byte paramByte)
/*    */   {
/* 42 */     return valueOf(paramByte);
/*    */   }
/*    */   
/*    */   public static SocksCmdStatus valueOf(byte paramByte) {
/* 46 */     for (SocksCmdStatus localSocksCmdStatus : ) {
/* 47 */       if (localSocksCmdStatus.b == paramByte) {
/* 48 */         return localSocksCmdStatus;
/*    */       }
/*    */     }
/* 51 */     return UNASSIGNED;
/*    */   }
/*    */   
/*    */   public byte byteValue() {
/* 55 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */