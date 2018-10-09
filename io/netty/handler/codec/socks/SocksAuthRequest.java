/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
/*    */ import java.nio.charset.CharsetEncoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SocksAuthRequest
/*    */   extends SocksRequest
/*    */ {
/* 30 */   private static final CharsetEncoder asciiEncoder = CharsetUtil.getEncoder(CharsetUtil.US_ASCII);
/* 31 */   private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
/*    */   private final String username;
/*    */   private final String password;
/*    */   
/*    */   public SocksAuthRequest(String paramString1, String paramString2) {
/* 36 */     super(SocksRequestType.AUTH);
/* 37 */     if (paramString1 == null) {
/* 38 */       throw new NullPointerException("username");
/*    */     }
/* 40 */     if (paramString2 == null) {
/* 41 */       throw new NullPointerException("username");
/*    */     }
/* 43 */     if ((!asciiEncoder.canEncode(paramString1)) || (!asciiEncoder.canEncode(paramString2))) {
/* 44 */       throw new IllegalArgumentException(" username: " + paramString1 + " or password: " + paramString2 + " values should be in pure ascii");
/*    */     }
/*    */     
/* 47 */     if (paramString1.length() > 255) {
/* 48 */       throw new IllegalArgumentException(paramString1 + " exceeds 255 char limit");
/*    */     }
/* 50 */     if (paramString2.length() > 255) {
/* 51 */       throw new IllegalArgumentException(paramString2 + " exceeds 255 char limit");
/*    */     }
/* 53 */     this.username = paramString1;
/* 54 */     this.password = paramString2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String username()
/*    */   {
/* 63 */     return this.username;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String password()
/*    */   {
/* 72 */     return this.password;
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*    */   {
/* 77 */     paramByteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
/* 78 */     paramByteBuf.writeByte(this.username.length());
/* 79 */     paramByteBuf.writeBytes(this.username.getBytes(CharsetUtil.US_ASCII));
/* 80 */     paramByteBuf.writeByte(this.password.length());
/* 81 */     paramByteBuf.writeBytes(this.password.getBytes(CharsetUtil.US_ASCII));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */