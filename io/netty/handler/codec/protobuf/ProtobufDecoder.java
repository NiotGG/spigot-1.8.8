/*     */ package io.netty.handler.codec.protobuf;
/*     */ 
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import com.google.protobuf.MessageLite.Builder;
/*     */ import com.google.protobuf.Parser;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*     */ @ChannelHandler.Sharable
/*     */ public class ProtobufDecoder
/*     */   extends MessageToMessageDecoder<ByteBuf>
/*     */ {
/*     */   private static final boolean HAS_PARSER;
/*     */   private final MessageLite prototype;
/*     */   private final ExtensionRegistry extensionRegistry;
/*     */   
/*     */   static
/*     */   {
/*  68 */     boolean bool = false;
/*     */     try
/*     */     {
/*  71 */       MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
/*  72 */       bool = true;
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */     
/*     */ 
/*  77 */     HAS_PARSER = bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProtobufDecoder(MessageLite paramMessageLite)
/*     */   {
/*  87 */     this(paramMessageLite, null);
/*     */   }
/*     */   
/*     */   public ProtobufDecoder(MessageLite paramMessageLite, ExtensionRegistry paramExtensionRegistry) {
/*  91 */     if (paramMessageLite == null) {
/*  92 */       throw new NullPointerException("prototype");
/*     */     }
/*  94 */     this.prototype = paramMessageLite.getDefaultInstanceForType();
/*  95 */     this.extensionRegistry = paramExtensionRegistry;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 102 */     int i = paramByteBuf.readableBytes();
/* 103 */     byte[] arrayOfByte; int j; if (paramByteBuf.hasArray()) {
/* 104 */       arrayOfByte = paramByteBuf.array();
/* 105 */       j = paramByteBuf.arrayOffset() + paramByteBuf.readerIndex();
/*     */     } else {
/* 107 */       arrayOfByte = new byte[i];
/* 108 */       paramByteBuf.getBytes(paramByteBuf.readerIndex(), arrayOfByte, 0, i);
/* 109 */       j = 0;
/*     */     }
/*     */     
/* 112 */     if (this.extensionRegistry == null) {
/* 113 */       if (HAS_PARSER) {
/* 114 */         paramList.add(this.prototype.getParserForType().parseFrom(arrayOfByte, j, i));
/*     */       } else {
/* 116 */         paramList.add(this.prototype.newBuilderForType().mergeFrom(arrayOfByte, j, i).build());
/*     */       }
/*     */     }
/* 119 */     else if (HAS_PARSER) {
/* 120 */       paramList.add(this.prototype.getParserForType().parseFrom(arrayOfByte, j, i, this.extensionRegistry));
/*     */     } else {
/* 122 */       paramList.add(this.prototype.newBuilderForType().mergeFrom(arrayOfByte, j, i, this.extensionRegistry).build());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\protobuf\ProtobufDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */