/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import io.netty.handler.codec.ReplayingDecoder;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class SocksAuthRequestDecoder
/*    */   extends ReplayingDecoder<State>
/*    */ {
/*    */   private static final String name = "SOCKS_AUTH_REQUEST_DECODER";
/*    */   private SocksSubnegotiationVersion version;
/*    */   private int fieldLength;
/*    */   private String username;
/*    */   private String password;
/*    */   
/*    */   @Deprecated
/*    */   public static String getName()
/*    */   {
/* 38 */     return "SOCKS_AUTH_REQUEST_DECODER";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 45 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*    */   
/*    */   public SocksAuthRequestDecoder() {
/* 48 */     super(State.CHECK_PROTOCOL_VERSION);
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 53 */     switch ((State)state()) {
/*    */     case CHECK_PROTOCOL_VERSION: 
/* 55 */       this.version = SocksSubnegotiationVersion.valueOf(paramByteBuf.readByte());
/* 56 */       if (this.version == SocksSubnegotiationVersion.AUTH_PASSWORD)
/*    */       {
/*    */ 
/* 59 */         checkpoint(State.READ_USERNAME); }
/*    */       break;
/*    */     case READ_USERNAME: 
/* 62 */       this.fieldLength = paramByteBuf.readByte();
/* 63 */       this.username = paramByteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 64 */       checkpoint(State.READ_PASSWORD);
/*    */     
/*    */     case READ_PASSWORD: 
/* 67 */       this.fieldLength = paramByteBuf.readByte();
/* 68 */       this.password = paramByteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/* 69 */       this.msg = new SocksAuthRequest(this.username, this.password);
/*    */     }
/*    */     
/* 72 */     paramChannelHandlerContext.pipeline().remove(this);
/* 73 */     paramList.add(this.msg);
/*    */   }
/*    */   
/*    */   static enum State {
/* 77 */     CHECK_PROTOCOL_VERSION, 
/* 78 */     READ_USERNAME, 
/* 79 */     READ_PASSWORD;
/*    */     
/*    */     private State() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksAuthRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */