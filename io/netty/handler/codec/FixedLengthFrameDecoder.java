/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
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
/*    */ public class FixedLengthFrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   private final int frameLength;
/*    */   
/*    */   public FixedLengthFrameDecoder(int paramInt)
/*    */   {
/* 49 */     if (paramInt <= 0) {
/* 50 */       throw new IllegalArgumentException("frameLength must be a positive integer: " + paramInt);
/*    */     }
/*    */     
/* 53 */     this.frameLength = paramInt;
/*    */   }
/*    */   
/*    */   protected final void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*    */   {
/* 58 */     Object localObject = decode(paramChannelHandlerContext, paramByteBuf);
/* 59 */     if (localObject != null) {
/* 60 */       paramList.add(localObject);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Object decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
/*    */     throws Exception
/*    */   {
/* 74 */     if (paramByteBuf.readableBytes() < this.frameLength) {
/* 75 */       return null;
/*    */     }
/* 77 */     return paramByteBuf.readSlice(this.frameLength).retain();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\FixedLengthFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */