/*    */ package io.netty.handler.codec.spdy;
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
/*    */ public enum SpdyVersion
/*    */ {
/* 19 */   SPDY_3_1(3, 1);
/*    */   
/*    */   private final int version;
/*    */   private final int minorVersion;
/*    */   
/*    */   private SpdyVersion(int paramInt1, int paramInt2) {
/* 25 */     this.version = paramInt1;
/* 26 */     this.minorVersion = paramInt2;
/*    */   }
/*    */   
/*    */   int getVersion() {
/* 30 */     return this.version;
/*    */   }
/*    */   
/*    */   int getMinorVersion() {
/* 34 */     return this.minorVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */