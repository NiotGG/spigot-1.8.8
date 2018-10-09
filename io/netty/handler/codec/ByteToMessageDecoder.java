/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ public abstract class ByteToMessageDecoder
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   ByteBuf cumulation;
/*     */   private boolean singleDecode;
/*     */   private boolean decodeWasNull;
/*     */   private boolean first;
/*     */   
/*     */   protected ByteToMessageDecoder()
/*     */   {
/*  55 */     if (isSharable()) {
/*  56 */       throw new IllegalStateException("@Sharable annotation is not allowed");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSingleDecode(boolean paramBoolean)
/*     */   {
/*  67 */     this.singleDecode = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSingleDecode()
/*     */   {
/*  77 */     return this.singleDecode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int actualReadableBytes()
/*     */   {
/*  87 */     return internalBuffer().readableBytes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ByteBuf internalBuffer()
/*     */   {
/*  96 */     if (this.cumulation != null) {
/*  97 */       return this.cumulation;
/*     */     }
/*  99 */     return Unpooled.EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */   public final void handlerRemoved(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {
/* 105 */     ByteBuf localByteBuf1 = internalBuffer();
/* 106 */     int i = localByteBuf1.readableBytes();
/* 107 */     if (localByteBuf1.isReadable()) {
/* 108 */       ByteBuf localByteBuf2 = localByteBuf1.readBytes(i);
/* 109 */       localByteBuf1.release();
/* 110 */       paramChannelHandlerContext.fireChannelRead(localByteBuf2);
/*     */     } else {
/* 112 */       localByteBuf1.release();
/*     */     }
/* 114 */     this.cumulation = null;
/* 115 */     paramChannelHandlerContext.fireChannelReadComplete();
/* 116 */     handlerRemoved0(paramChannelHandlerContext);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void handlerRemoved0(ChannelHandlerContext paramChannelHandlerContext)
/*     */     throws Exception
/*     */   {}
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject)
/*     */     throws Exception
/*     */   {
/* 127 */     if ((paramObject instanceof ByteBuf)) {
/* 128 */       RecyclableArrayList localRecyclableArrayList = RecyclableArrayList.newInstance();
/*     */       try {
/* 130 */         ByteBuf localByteBuf = (ByteBuf)paramObject;
/* 131 */         this.first = (this.cumulation == null);
/* 132 */         if (this.first) {
/* 133 */           this.cumulation = localByteBuf;
/*     */         } else {
/* 135 */           if ((this.cumulation.writerIndex() > this.cumulation.maxCapacity() - localByteBuf.readableBytes()) || (this.cumulation.refCnt() > 1))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 144 */             expandCumulation(paramChannelHandlerContext, localByteBuf.readableBytes());
/*     */           }
/* 146 */           this.cumulation.writeBytes(localByteBuf);
/* 147 */           localByteBuf.release();
/*     */         }
/* 149 */         callDecode(paramChannelHandlerContext, this.cumulation, localRecyclableArrayList); } catch (DecoderException localDecoderException) { int i;
/*     */         int j;
/* 151 */         throw localDecoderException;
/*     */       } catch (Throwable localThrowable) {
/* 153 */         throw new DecoderException(localThrowable);
/*     */       } finally {
/* 155 */         if ((this.cumulation != null) && (!this.cumulation.isReadable())) {
/* 156 */           this.cumulation.release();
/* 157 */           this.cumulation = null;
/*     */         }
/* 159 */         int k = localRecyclableArrayList.size();
/* 160 */         this.decodeWasNull = (k == 0);
/*     */         
/* 162 */         for (int m = 0; m < k; m++) {
/* 163 */           paramChannelHandlerContext.fireChannelRead(localRecyclableArrayList.get(m));
/*     */         }
/* 165 */         localRecyclableArrayList.recycle();
/*     */       }
/*     */     } else {
/* 168 */       paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   private void expandCumulation(ChannelHandlerContext paramChannelHandlerContext, int paramInt) {
/* 173 */     ByteBuf localByteBuf = this.cumulation;
/* 174 */     this.cumulation = paramChannelHandlerContext.alloc().buffer(localByteBuf.readableBytes() + paramInt);
/* 175 */     this.cumulation.writeBytes(localByteBuf);
/* 176 */     localByteBuf.release();
/*     */   }
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 181 */     if ((this.cumulation != null) && (!this.first) && (this.cumulation.refCnt() == 1))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 189 */       this.cumulation.discardSomeReadBytes();
/*     */     }
/* 191 */     if (this.decodeWasNull) {
/* 192 */       this.decodeWasNull = false;
/* 193 */       if (!paramChannelHandlerContext.channel().config().isAutoRead()) {
/* 194 */         paramChannelHandlerContext.read();
/*     */       }
/*     */     }
/* 197 */     paramChannelHandlerContext.fireChannelReadComplete();
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 202 */     RecyclableArrayList localRecyclableArrayList = RecyclableArrayList.newInstance();
/*     */     try {
/* 204 */       if (this.cumulation != null) {
/* 205 */         callDecode(paramChannelHandlerContext, this.cumulation, localRecyclableArrayList);
/* 206 */         decodeLast(paramChannelHandlerContext, this.cumulation, localRecyclableArrayList);
/*     */       } else {
/* 208 */         decodeLast(paramChannelHandlerContext, Unpooled.EMPTY_BUFFER, localRecyclableArrayList);
/*     */       } } catch (DecoderException localDecoderException) { int i;
/*     */       int j;
/* 211 */       throw localDecoderException;
/*     */     } catch (Exception localException) {
/* 213 */       throw new DecoderException(localException);
/*     */     } finally {
/*     */       try {
/* 216 */         if (this.cumulation != null) {
/* 217 */           this.cumulation.release();
/* 218 */           this.cumulation = null;
/*     */         }
/* 220 */         int k = localRecyclableArrayList.size();
/* 221 */         for (int m = 0; m < k; m++) {
/* 222 */           paramChannelHandlerContext.fireChannelRead(localRecyclableArrayList.get(m));
/*     */         }
/* 224 */         if (k > 0)
/*     */         {
/* 226 */           paramChannelHandlerContext.fireChannelReadComplete();
/*     */         }
/* 228 */         paramChannelHandlerContext.fireChannelInactive();
/*     */       }
/*     */       finally {
/* 231 */         localRecyclableArrayList.recycle();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void callDecode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */   {
/*     */     try
/*     */     {
/* 246 */       while (paramByteBuf.isReadable()) {
/* 247 */         int i = paramList.size();
/* 248 */         int j = paramByteBuf.readableBytes();
/* 249 */         decode(paramChannelHandlerContext, paramByteBuf, paramList);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 255 */         if (paramChannelHandlerContext.isRemoved()) {
/*     */           break;
/*     */         }
/*     */         
/* 259 */         if (i == paramList.size()) {
/* 260 */           if (j == paramByteBuf.readableBytes()) {
/*     */             break;
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 267 */           if (j == paramByteBuf.readableBytes()) {
/* 268 */             throw new DecoderException(StringUtil.simpleClassName(getClass()) + ".decode() did not read anything but decoded a message.");
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 273 */           if (isSingleDecode())
/*     */             break;
/*     */         }
/*     */       }
/*     */     } catch (DecoderException localDecoderException) {
/* 278 */       throw localDecoderException;
/*     */     } catch (Throwable localThrowable) {
/* 280 */       throw new DecoderException(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void decodeLast(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList)
/*     */     throws Exception
/*     */   {
/* 304 */     decode(paramChannelHandlerContext, paramByteBuf, paramList);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\ByteToMessageDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */