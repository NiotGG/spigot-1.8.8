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
/*    */ public class SocksInitResponseDecoder
/*    */   extends ReplayingDecoder<State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_RESPONSE_DECODER";
/*    */   private SocksProtocolVersion version;
/*    */   private SocksAuthScheme authScheme;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName()
/*    */   {
/* 37 */     return "SOCKS_INIT_RESPONSE_DECODER";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 43 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*    */   
/*    */   public SocksInitResponseDecoder() {
/* 46 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 51 */     switch ((State)state()) {
/*    */     case CHECK_PROTOCOL_VERSION: 
/* 53 */       this.version = SocksProtocolVersion.valueOf(paramByteBuf.readByte());
/* 54 */       if (this.version == SocksProtocolVersion.SOCKS5)
/*    */       {
/*    */ 
/* 57 */         checkpoint(State.READ_PREFFERED_AUTH_TYPE); }
/*    */       break;
/*    */     case READ_PREFFERED_AUTH_TYPE: 
/* 60 */       this.authScheme = SocksAuthScheme.valueOf(paramByteBuf.readByte());
/* 61 */       this.msg = new SocksInitResponse(this.authScheme);
/*    */     }
/*    */     
/*    */     
/* 65 */     paramChannelHandlerContext.pipeline().remove(this);
/* 66 */     paramList.add(this.msg);
/*    */   }
/*    */   
/*    */   static enum State {
/* 70 */     CHECK_PROTOCOL_VERSION, 
/* 71 */     READ_PREFFERED_AUTH_TYPE;
/*    */     
/*    */     private State() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksInitResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */