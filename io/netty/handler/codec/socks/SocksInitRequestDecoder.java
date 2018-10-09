/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ public class SocksInitRequestDecoder
/*    */   extends ReplayingDecoder<State>
/*    */ {
/*    */   private static final String name = "SOCKS_INIT_REQUEST_DECODER";
/*    */   
/*    */   @Deprecated
/*    */   public static String getName()
/*    */   {
/* 38 */     return "SOCKS_INIT_REQUEST_DECODER";
/*    */   }
/*    */   
/* 41 */   private final List<SocksAuthScheme> authSchemes = new ArrayList();
/*    */   private SocksProtocolVersion version;
/*    */   private byte authSchemeNum;
/* 44 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksInitRequestDecoder() {
/* 47 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 52 */     switch ((State)state()) {
/*    */     case CHECK_PROTOCOL_VERSION: 
/* 54 */       this.version = SocksProtocolVersion.valueOf(paramByteBuf.readByte());
/* 55 */       if (this.version == SocksProtocolVersion.SOCKS5)
/*    */       {
/*    */ 
/* 58 */         checkpoint(State.READ_AUTH_SCHEMES); }
/*    */       break;
/*    */     case READ_AUTH_SCHEMES: 
/* 61 */       this.authSchemes.clear();
/* 62 */       this.authSchemeNum = paramByteBuf.readByte();
/* 63 */       for (int i = 0; i < this.authSchemeNum; i++) {
/* 64 */         this.authSchemes.add(SocksAuthScheme.valueOf(paramByteBuf.readByte()));
/*    */       }
/* 66 */       this.msg = new SocksInitRequest(this.authSchemes);
/*    */     }
/*    */     
/*    */     
/* 70 */     paramChannelHandlerContext.pipeline().remove(this);
/* 71 */     paramList.add(this.msg);
/*    */   }
/*    */   
/*    */   static enum State {
/* 75 */     CHECK_PROTOCOL_VERSION, 
/* 76 */     READ_AUTH_SCHEMES;
/*    */     
/*    */     private State() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksInitRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */