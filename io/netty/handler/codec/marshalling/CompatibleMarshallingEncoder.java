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
/*    */ 
/*    */ 
/*    */ 
/*    */ @ChannelHandler.Sharable
/*    */ public class CompatibleMarshallingEncoder
/*    */   extends MessageToByteEncoder<Object>
/*    */ {
/*    */   private final MarshallerProvider provider;
/*    */   
/*    */   public CompatibleMarshallingEncoder(MarshallerProvider paramMarshallerProvider)
/*    */   {
/* 47 */     this.provider = paramMarshallerProvider;
/*    */   }
/*    */   
/*    */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ByteBuf paramByteBuf) throws Exception
/*    */   {
/* 52 */     Marshaller localMarshaller = this.provider.getMarshaller(paramChannelHandlerContext);
/* 53 */     localMarshaller.start(new ChannelBufferByteOutput(paramByteBuf));
/* 54 */     localMarshaller.writeObject(paramObject);
/* 55 */     localMarshaller.finish();
/* 56 */     localMarshaller.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\CompatibleMarshallingEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */