/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
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
/*    */ public class SocksAuthResponseDecoder
/*    */   extends ReplayingDecoder<State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private SocksAuthStatus authStatus;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName()
/*    */   {
/* 37 */     return "SOCKS_AUTH_RESPONSE_DECODER";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 42 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksAuthResponseDecoder() {
/* 45 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*    */     throws Exception
/*    */   {
/* 51 */     switch ((State)state()) {
/*    */     case CHECK_PROTOCOL_VERSION: 
/* 53 */       this.version = SocksSubnegotiationVersion.valueOf(paramByteBuf.readByte());
/* 54 */       if (this.version == SocksSubnegotiationVersion.AUTH_PASSWORD)
/*    */       {
/*    */ 
/* 57 */         checkpoint(State.READ_AUTH_RESPONSE); }
/*    */       break;
/*    */     case READ_AUTH_RESPONSE: 
/* 60 */       this.authStatus = SocksAuthStatus.valueOf(paramByteBuf.readByte());
/* 61 */       this.msg = new SocksAuthResponse(this.authStatus);
/*    */     }
/*    */     
/* 64 */     paramChannelHandlerContext.pipeline().remove(this);
/* 65 */     paramList.add(this.msg);
/*    */   }
/*    */   
/*    */   static enum State {
/* 69 */     CHECK_PROTOCOL_VERSION, 
/* 70 */     READ_AUTH_RESPONSE;
/*    */     
/*    */     private State() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */