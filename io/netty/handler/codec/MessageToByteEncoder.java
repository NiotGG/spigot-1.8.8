/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.TypeParameterMatcher;
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
/*     */ public abstract class MessageToByteEncoder<I>
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean preferDirect;
/*     */   
/*     */   protected MessageToByteEncoder()
/*     */   {
/*  55 */     this(true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected MessageToByteEncoder(Class<? extends I> paramClass)
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
/*     */   protected MessageToByteEncoder(boolean paramBoolean)
/*     */   {
/*  73 */     this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
/*  74 */     this.preferDirect = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MessageToByteEncoder(Class<? extends I> paramClass, boolean paramBoolean)
/*     */   {
/*  86 */     this.matcher = TypeParameterMatcher.get(paramClass);
/*  87 */     this.preferDirect = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean acceptOutboundMessage(Object paramObject)
/*     */     throws Exception
/*     */   {
/*  95 */     return this.matcher.match(paramObject);
/*     */   }
/*     */   
/*     */   public void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 100 */     ByteBuf localByteBuf = null;
/*     */     try {
/* 102 */       if (acceptOutboundMessage(paramObject))
/*     */       {
/* 104 */         Object localObject1 = paramObject;
/* 105 */         localByteBuf = allocateBuffer(paramChannelHandlerContext, localObject1, this.preferDirect);
/*     */         try {
/* 107 */           encode(paramChannelHandlerContext, localObject1, localByteBuf);
/*     */         } finally {
/* 109 */           ReferenceCountUtil.release(localObject1);
/*     */         }
/*     */         
/* 112 */         if (localByteBuf.isReadable()) {
/* 113 */           paramChannelHandlerContext.write(localByteBuf, paramChannelPromise);
/*     */         } else {
/* 115 */           localByteBuf.release();
/* 116 */           paramChannelHandlerContext.write(Unpooled.EMPTY_BUFFER, paramChannelPromise);
/*     */         }
/* 118 */         localByteBuf = null;
/*     */       } else {
/* 120 */         paramChannelHandlerContext.write(paramObject, paramChannelPromise);
/*     */       }
/*     */     } catch (EncoderException localEncoderException) {
/* 123 */       throw localEncoderException;
/*     */     } catch (Throwable localThrowable) {
/* 125 */       throw new EncoderException(localThrowable);
/*     */     } finally {
/* 127 */       if (localByteBuf != null) {
/* 128 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ByteBuf allocateBuffer(ChannelHandlerContext paramChannelHandlerContext, I paramI, boolean paramBoolean)
/*     */     throws Exception
/*     */   {
/* 139 */     if (paramBoolean) {
/* 140 */       return paramChannelHandlerContext.alloc().ioBuffer();
/*     */     }
/* 142 */     return paramChannelHandlerContext.alloc().heapBuffer();
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, ByteBuf paramByteBuf)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\MessageToByteEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */