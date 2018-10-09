/*     */ package io.netty.handler.codec.marshalling;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.handler.codec.TooLongFrameException;
/*     */ import java.util.List;
/*     */ import org.jboss.marshalling.ByteInput;
/*     */ import org.jboss.marshalling.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompatibleMarshallingDecoder
/*     */   extends ReplayingDecoder<Void>
/*     */ {
/*     */   protected final UnmarshallerProvider provider;
/*     */   protected final int maxObjectSize;
/*     */   private boolean discardingTooLongFrame;
/*     */   
/*     */   public CompatibleMarshallingDecoder(UnmarshallerProvider paramUnmarshallerProvider, int paramInt)
/*     */   {
/*  53 */     this.provider = paramUnmarshallerProvider;
/*  54 */     this.maxObjectSize = paramInt;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  59 */     if (this.discardingTooLongFrame) {
/*  60 */       paramByteBuf.skipBytes(actualReadableBytes());
/*  61 */       checkpoint();
/*  62 */       return;
/*     */     }
/*     */     
/*  65 */     Unmarshaller localUnmarshaller = this.provider.getUnmarshaller(paramChannelHandlerContext);
/*  66 */     Object localObject1 = new ChannelBufferByteInput(paramByteBuf);
/*  67 */     if (this.maxObjectSize != Integer.MAX_VALUE) {
/*  68 */       localObject1 = new LimitingByteInput((ByteInput)localObject1, this.maxObjectSize);
/*     */     }
/*     */     try {
/*  71 */       localUnmarshaller.start((ByteInput)localObject1);
/*  72 */       Object localObject2 = localUnmarshaller.readObject();
/*  73 */       localUnmarshaller.finish();
/*  74 */       paramList.add(localObject2);
/*     */     } catch (LimitingByteInput.TooBigObjectException localTooBigObjectException) {
/*  76 */       this.discardingTooLongFrame = true;
/*  77 */       throw new TooLongFrameException();
/*     */     }
/*     */     finally
/*     */     {
/*  81 */       localUnmarshaller.close();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void decodeLast(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  87 */     switch (paramByteBuf.readableBytes()) {
/*     */     case 0: 
/*  89 */       return;
/*     */     
/*     */     case 1: 
/*  92 */       if (paramByteBuf.getByte(paramByteBuf.readerIndex()) == 121) {
/*  93 */         paramByteBuf.skipBytes(1); return;
/*     */       }
/*     */       break;
/*     */     }
/*     */     
/*  98 */     decode(paramChannelHandlerContext, paramByteBuf, paramList);
/*     */   }
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception
/*     */   {
/* 103 */     if ((paramThrowable instanceof TooLongFrameException)) {
/* 104 */       paramChannelHandlerContext.close();
/*     */     } else {
/* 106 */       super.exceptionCaught(paramChannelHandlerContext, paramThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\CompatibleMarshallingDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */