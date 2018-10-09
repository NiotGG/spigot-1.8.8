/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public final class SocksInitRequest
/*    */   extends SocksRequest
/*    */ {
/*    */   private final List<SocksAuthScheme> authSchemes;
/*    */   
/*    */   public SocksInitRequest(List<SocksAuthScheme> paramList)
/*    */   {
/* 33 */     super(SocksRequestType.INIT);
/* 34 */     if (paramList == null) {
/* 35 */       throw new NullPointerException("authSchemes");
/*    */     }
/* 37 */     this.authSchemes = paramList;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<SocksAuthScheme> authSchemes()
/*    */   {
/* 46 */     return Collections.unmodifiableList(this.authSchemes);
/*    */   }
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*    */   {
/* 51 */     paramByteBuf.writeByte(protocolVersion().byteValue());
/* 52 */     paramByteBuf.writeByte(this.authSchemes.size());
/* 53 */     for (SocksAuthScheme localSocksAuthScheme : this.authSchemes) {
/* 54 */       paramByteBuf.writeByte(localSocksAuthScheme.byteValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksInitRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */