/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
/*    */ import org.jboss.marshalling.Unmarshaller;
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
/*    */ public class MarshallingDecoder
/*    */   extends LengthFieldBasedFrameDecoder
/*    */ {
/*    */   private final UnmarshallerProvider provider;
/*    */   
/*    */   public MarshallingDecoder(UnmarshallerProvider paramUnmarshallerProvider)
/*    */   {
/* 45 */     this(paramUnmarshallerProvider, 1048576);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MarshallingDecoder(UnmarshallerProvider paramUnmarshallerProvider, int paramInt)
/*    */   {
/* 57 */     super(paramInt, 0, 4, 0, 4);
/* 58 */     this.provider = paramUnmarshallerProvider;
/*    */   }
/*    */   
/*    */   protected Object decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 63 */     ByteBuf localByteBuf = (ByteBuf)super.decode(paramChannelHandlerContext, paramByteBuf);
/* 64 */     if (localByteBuf == null) {
/* 65 */       return null;
/*    */     }
/*    */     
/* 68 */     Unmarshaller localUnmarshaller = this.provider.getUnmarshaller(paramChannelHandlerContext);
/* 69 */     ChannelBufferByteInput localChannelBufferByteInput = new ChannelBufferByteInput(localByteBuf);
/*    */     try
/*    */     {
/* 72 */       localUnmarshaller.start(localChannelBufferByteInput);
/* 73 */       Object localObject1 = localUnmarshaller.readObject();
/* 74 */       localUnmarshaller.finish();
/* 75 */       return localObject1;
/*    */     }
/*    */     finally
/*    */     {
/* 79 */       localUnmarshaller.close();
/*    */     }
/*    */   }
/*    */   
/*    */   protected ByteBuf extractFrame(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*    */   {
/* 85 */     return paramByteBuf.slice(paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\MarshallingDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */