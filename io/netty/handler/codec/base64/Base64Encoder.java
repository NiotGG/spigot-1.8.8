/*    */ package io.netty.handler.codec.base64;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
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
/*    */ @ChannelHandler.Sharable
/*    */ public class Base64Encoder
/*    */   extends MessageToMessageEncoder<ByteBuf>
/*    */ {
/*    */   private final boolean breakLines;
/*    */   private final Base64Dialect dialect;
/*    */   
/*    */   public Base64Encoder()
/*    */   {
/* 49 */     this(true);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean paramBoolean) {
/* 53 */     this(paramBoolean, Base64Dialect.STANDARD);
/*    */   }
/*    */   
/*    */   public Base64Encoder(boolean paramBoolean, Base64Dialect paramBase64Dialect) {
/* 57 */     if (paramBase64Dialect == null) {
/* 58 */       throw new NullPointerException("dialect");
/*    */     }
/*    */     
/* 61 */     this.breakLines = paramBoolean;
/* 62 */     this.dialect = paramBase64Dialect;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 67 */     paramList.add(Base64.encode(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes(), this.breakLines, this.dialect));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\base64\Base64Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */