/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
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
/*    */ 
/*    */ @ChannelHandler.Sharable
/*    */ public class SocksMessageEncoder
/*    */   extends MessageToByteEncoder<SocksMessage>
/*    */ {
/*    */   private static final String name = "SOCKS_MESSAGE_ENCODER";
/*    */   
/*    */   @Deprecated
/*    */   public static String getName()
/*    */   {
/* 38 */     return "SOCKS_MESSAGE_ENCODER";
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, SocksMessage paramSocksMessage, ByteBuf paramByteBuf)
/*    */     throws Exception
/*    */   {
/* 44 */     paramSocksMessage.encodeAsByteBuf(paramByteBuf);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksMessageEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */