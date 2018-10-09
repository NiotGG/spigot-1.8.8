/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import org.jboss.marshalling.Marshaller;
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
/*    */ public class MarshallingEncoder
/*    */   extends MessageToByteEncoder<Object>
/*    */ {
/* 40 */   private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
/*    */   
/*    */ 
/*    */   private final MarshallerProvider provider;
/*    */   
/*    */ 
/*    */ 
/*    */   public MarshallingEncoder(MarshallerProvider paramMarshallerProvider)
/*    */   {
/* 49 */     this.provider = paramMarshallerProvider;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 54 */     Marshaller localMarshaller = this.provider.getMarshaller(paramChannelHandlerContext);
/* 55 */     int i = paramByteBuf.writerIndex();
/* 56 */     paramByteBuf.writeBytes(LENGTH_PLACEHOLDER);
/* 57 */     ChannelBufferByteOutput localChannelBufferByteOutput = new ChannelBufferByteOutput(paramByteBuf);
/* 58 */     localMarshaller.start(localChannelBufferByteOutput);
/* 59 */     localMarshaller.writeObject(paramObject);
/* 60 */     localMarshaller.finish();
/* 61 */     localMarshaller.close();
/*    */     
/* 63 */     paramByteBuf.setInt(i, paramByteBuf.writerIndex() - i - 4);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\MarshallingEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */