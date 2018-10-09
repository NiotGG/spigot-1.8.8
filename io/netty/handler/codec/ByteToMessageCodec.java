/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
/*     */ import java.util.List;
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
/*     */ public abstract class ByteToMessageCodec<I>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private final TypeParameterMatcher outboundMsgMatcher;
/*     */   private final MessageToByteEncoder<I> encoder;
/*  39 */   private final ByteToMessageDecoder decoder = new ByteToMessageDecoder()
/*     */   {
/*     */     public void decode(ChannelHandlerContext paramAnonymousChannelHandlerContext, ByteBuf paramAnonymousByteBuf, List<Object> paramAnonymousList) throws Exception {
/*  42 */       ByteToMessageCodec.this.decode(paramAnonymousChannelHandlerContext, paramAnonymousByteBuf, paramAnonymousList);
/*     */     }
/*     */     
/*     */     protected void decodeLast(ChannelHandlerContext paramAnonymousChannelHandlerContext, ByteBuf paramAnonymousByteBuf, List<Object> paramAnonymousList) throws Exception
/*     */     {
/*  47 */       ByteToMessageCodec.this.decodeLast(paramAnonymousChannelHandlerContext, paramAnonymousByteBuf, paramAnonymousList);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   protected ByteToMessageCodec()
/*     */   {
/*  55 */     this(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ByteToMessageCodec(Class<? extends I> paramClass)
/*     */   {
/*  62 */     this(paramClass, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ByteToMessageCodec(boolean paramBoolean)
/*     */   {
/*  73 */     this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
/*  74 */     this.encoder = new Encoder(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ByteToMessageCodec(Class<? extends I> paramClass, boolean paramBoolean)
/*     */   {
/*  86 */     checkForSharableAnnotation();
/*  87 */     this.outboundMsgMatcher = TypeParameterMatcher.get(paramClass);
/*  88 */     this.encoder = new Encoder(paramBoolean);
/*     */   }
/*     */   
/*     */   private void checkForSharableAnnotation() {
/*  92 */     if (isSharable()) {
/*  93 */       throw new IllegalStateException("@Sharable annotation is not allowed");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean acceptOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 103 */     return this.outboundMsgMatcher.match(paramObject);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 108 */     this.decoder.channelRead(paramChannelHandlerContext, paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 113 */     this.encoder.write(paramChannelHandlerContext, paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */   protected void decodeLast(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 130 */     decode(paramChannelHandlerContext, paramByteBuf, paramList);
/*     */   }
/*     */   
/*     */   private final class Encoder extends MessageToByteEncoder<I> {
/*     */     Encoder(boolean paramBoolean) {
/* 135 */       super();
/*     */     }
/*     */     
/*     */     public boolean acceptOutboundMessage(Object paramObject) throws Exception
/*     */     {
/* 140 */       return ByteToMessageCodec.this.acceptOutboundMessage(paramObject);
/*     */     }
/*     */     
/*     */     protected void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, ByteBuf paramByteBuf) throws Exception
/*     */     {
/* 145 */       ByteToMessageCodec.this.encode(paramChannelHandlerContext, paramI, paramByteBuf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\ByteToMessageCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */