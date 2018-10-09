/*    */ package io.netty.handler.codec.base64;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*    */ public class Base64Decoder
/*    */   extends MessageToMessageDecoder<ByteBuf>
/*    */ {
/*    */   private final Base64Dialect dialect;
/*    */   
/*    */   public Base64Decoder()
/*    */   {
/* 52 */     this(Base64Dialect.STANDARD);
/*    */   }
/*    */   
/*    */   public Base64Decoder(Base64Dialect paramBase64Dialect) {
/* 56 */     if (paramBase64Dialect == null) {
/* 57 */       throw new NullPointerException("dialect");
/*    */     }
/* 59 */     this.dialect = paramBase64Dialect;
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 64 */     paramList.add(Base64.decode(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes(), this.dialect));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\base64\Base64Decoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */