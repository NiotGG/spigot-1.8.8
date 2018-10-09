/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ public class LineBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maxLength;
/*     */   private final boolean failFast;
/*     */   private final boolean stripDelimiter;
/*     */   private boolean discarding;
/*     */   private int discardedBytes;
/*     */   
/*     */   public LineBasedFrameDecoder(int paramInt)
/*     */   {
/*  48 */     this(paramInt, true, false);
/*     */   }
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
/*     */   public LineBasedFrameDecoder(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  67 */     this.maxLength = paramInt;
/*  68 */     this.failFast = paramBoolean2;
/*  69 */     this.stripDelimiter = paramBoolean1;
/*     */   }
/*     */   
/*     */   protected final void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  74 */     Object localObject = decode(paramChannelHandlerContext, paramByteBuf);
/*  75 */     if (localObject != null) {
/*  76 */       paramList.add(localObject);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf)
/*     */     throws Exception
/*     */   {
/*  89 */     int i = findEndOfLine(paramByteBuf);
/*  90 */     int j; int m; if (!this.discarding) {
/*  91 */       if (i >= 0)
/*     */       {
/*  93 */         j = i - paramByteBuf.readerIndex();
/*  94 */         int k = paramByteBuf.getByte(i) == 13 ? 2 : 1;
/*     */         
/*  96 */         if (j > this.maxLength) {
/*  97 */           paramByteBuf.readerIndex(i + k);
/*  98 */           fail(paramChannelHandlerContext, j);
/*  99 */           return null;
/*     */         }
/*     */         ByteBuf localByteBuf;
/* 102 */         if (this.stripDelimiter) {
/* 103 */           localByteBuf = paramByteBuf.readSlice(j);
/* 104 */           paramByteBuf.skipBytes(k);
/*     */         } else {
/* 106 */           localByteBuf = paramByteBuf.readSlice(j + k);
/*     */         }
/*     */         
/* 109 */         return localByteBuf.retain();
/*     */       }
/* 111 */       m = paramByteBuf.readableBytes();
/* 112 */       if (m > this.maxLength) {
/* 113 */         this.discardedBytes = m;
/* 114 */         paramByteBuf.readerIndex(paramByteBuf.writerIndex());
/* 115 */         this.discarding = true;
/* 116 */         if (this.failFast) {
/* 117 */           fail(paramChannelHandlerContext, "over " + this.discardedBytes);
/*     */         }
/*     */       }
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     if (i >= 0) {
/* 124 */       m = this.discardedBytes + i - paramByteBuf.readerIndex();
/* 125 */       j = paramByteBuf.getByte(i) == 13 ? 2 : 1;
/* 126 */       paramByteBuf.readerIndex(i + j);
/* 127 */       this.discardedBytes = 0;
/* 128 */       this.discarding = false;
/* 129 */       if (!this.failFast) {
/* 130 */         fail(paramChannelHandlerContext, m);
/*     */       }
/*     */     } else {
/* 133 */       this.discardedBytes = paramByteBuf.readableBytes();
/* 134 */       paramByteBuf.readerIndex(paramByteBuf.writerIndex());
/*     */     }
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   private void fail(ChannelHandlerContext paramChannelHandlerContext, int paramInt)
/*     */   {
/* 141 */     fail(paramChannelHandlerContext, String.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   private void fail(ChannelHandlerContext paramChannelHandlerContext, String paramString) {
/* 145 */     paramChannelHandlerContext.fireExceptionCaught(new TooLongFrameException("frame length (" + paramString + ") exceeds the allowed maximum (" + this.maxLength + ')'));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int findEndOfLine(ByteBuf paramByteBuf)
/*     */   {
/* 155 */     int i = paramByteBuf.writerIndex();
/* 156 */     for (int j = paramByteBuf.readerIndex(); j < i; j++) {
/* 157 */       int k = paramByteBuf.getByte(j);
/* 158 */       if (k == 10)
/* 159 */         return j;
/* 160 */       if ((k == 13) && (j < i - 1) && (paramByteBuf.getByte(j + 1) == 10)) {
/* 161 */         return j;
/*     */       }
/*     */     }
/* 164 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\LineBasedFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */