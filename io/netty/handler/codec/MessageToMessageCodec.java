/*     */ package io.netty.handler.codec;
/*     */ 
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
/*     */ public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  57 */   private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder()
/*     */   {
/*     */     public boolean acceptOutboundMessage(Object paramAnonymousObject) throws Exception
/*     */     {
/*  61 */       return MessageToMessageCodec.this.acceptOutboundMessage(paramAnonymousObject);
/*     */     }
/*     */     
/*     */     protected void encode(ChannelHandlerContext paramAnonymousChannelHandlerContext, Object paramAnonymousObject, List<Object> paramAnonymousList)
/*     */       throws Exception
/*     */     {
/*  67 */       MessageToMessageCodec.this.encode(paramAnonymousChannelHandlerContext, paramAnonymousObject, paramAnonymousList);
/*     */     }
/*     */   };
/*     */   
/*  71 */   private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder()
/*     */   {
/*     */     public boolean acceptInboundMessage(Object paramAnonymousObject) throws Exception
/*     */     {
/*  75 */       return MessageToMessageCodec.this.acceptInboundMessage(paramAnonymousObject);
/*     */     }
/*     */     
/*     */     protected void decode(ChannelHandlerContext paramAnonymousChannelHandlerContext, Object paramAnonymousObject, List<Object> paramAnonymousList)
/*     */       throws Exception
/*     */     {
/*  81 */       MessageToMessageCodec.this.decode(paramAnonymousChannelHandlerContext, paramAnonymousObject, paramAnonymousList);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   private final TypeParameterMatcher inboundMsgMatcher;
/*     */   
/*     */   private final TypeParameterMatcher outboundMsgMatcher;
/*     */   
/*     */ 
/*     */   protected MessageToMessageCodec()
/*     */   {
/*  93 */     this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
/*  94 */     this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MessageToMessageCodec(Class<? extends INBOUND_IN> paramClass, Class<? extends OUTBOUND_IN> paramClass1)
/*     */   {
/* 105 */     this.inboundMsgMatcher = TypeParameterMatcher.get(paramClass);
/* 106 */     this.outboundMsgMatcher = TypeParameterMatcher.get(paramClass1);
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/* 111 */     this.decoder.channelRead(paramChannelHandlerContext, paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 116 */     this.encoder.write(paramChannelHandlerContext, paramObject, paramChannelPromise);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean acceptInboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 125 */     return this.inboundMsgMatcher.match(paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean acceptOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/* 134 */     return this.outboundMsgMatcher.match(paramObject);
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, OUTBOUND_IN paramOUTBOUND_IN, List<Object> paramList)
/*     */     throws Exception;
/*     */   
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, INBOUND_IN paramINBOUND_IN, List<Object> paramList)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\MessageToMessageCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */