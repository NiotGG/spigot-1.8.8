/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public final class CancelledPacketHandleException extends RuntimeException {
/*  4 */   public static final CancelledPacketHandleException INSTANCE = new CancelledPacketHandleException();
/*    */   
/*    */   private CancelledPacketHandleException() {
/*  7 */     setStackTrace(new StackTraceElement[0]);
/*    */   }
/*    */   
/*    */   public synchronized Throwable fillInStackTrace()
/*    */   {
/* 12 */     setStackTrace(new StackTraceElement[0]);
/* 13 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CancelledPacketHandleException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */