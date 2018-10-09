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
/*     */ public class DelimiterBasedFrameDecoder
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final ByteBuf[] delimiters;
/*     */   private final int maxFrameLength;
/*     */   private final boolean stripDelimiter;
/*     */   private final boolean failFast;
/*     */   private boolean discardingTooLongFrame;
/*     */   private int tooLongFrameLength;
/*     */   private final LineBasedFrameDecoder lineBasedDecoder;
/*     */   
/*     */   public DelimiterBasedFrameDecoder(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/*  78 */     this(paramInt, true, paramByteBuf);
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
/*     */   public DelimiterBasedFrameDecoder(int paramInt, boolean paramBoolean, ByteBuf paramByteBuf)
/*     */   {
/*  93 */     this(paramInt, paramBoolean, true, paramByteBuf);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DelimiterBasedFrameDecoder(int paramInt, boolean paramBoolean1, boolean paramBoolean2, ByteBuf paramByteBuf)
/*     */   {
/* 116 */     this(paramInt, paramBoolean1, paramBoolean2, new ByteBuf[] { paramByteBuf.slice(paramByteBuf.readerIndex(), paramByteBuf.readableBytes()) });
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
/*     */   public DelimiterBasedFrameDecoder(int paramInt, ByteBuf... paramVarArgs)
/*     */   {
/* 129 */     this(paramInt, true, paramVarArgs);
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
/*     */   public DelimiterBasedFrameDecoder(int paramInt, boolean paramBoolean, ByteBuf... paramVarArgs)
/*     */   {
/* 144 */     this(paramInt, paramBoolean, true, paramVarArgs);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public DelimiterBasedFrameDecoder(int paramInt, boolean paramBoolean1, boolean paramBoolean2, ByteBuf... paramVarArgs)
/*     */   {
/* 166 */     validateMaxFrameLength(paramInt);
/* 167 */     if (paramVarArgs == null) {
/* 168 */       throw new NullPointerException("delimiters");
/*     */     }
/* 170 */     if (paramVarArgs.length == 0) {
/* 171 */       throw new IllegalArgumentException("empty delimiters");
/*     */     }
/*     */     
/* 174 */     if ((isLineBased(paramVarArgs)) && (!isSubclass())) {
/* 175 */       this.lineBasedDecoder = new LineBasedFrameDecoder(paramInt, paramBoolean1, paramBoolean2);
/* 176 */       this.delimiters = null;
/*     */     } else {
/* 178 */       this.delimiters = new ByteBuf[paramVarArgs.length];
/* 179 */       for (int i = 0; i < paramVarArgs.length; i++) {
/* 180 */         ByteBuf localByteBuf = paramVarArgs[i];
/* 181 */         validateDelimiter(localByteBuf);
/* 182 */         this.delimiters[i] = localByteBuf.slice(localByteBuf.readerIndex(), localByteBuf.readableBytes());
/*     */       }
/* 184 */       this.lineBasedDecoder = null;
/*     */     }
/* 186 */     this.maxFrameLength = paramInt;
/* 187 */     this.stripDelimiter = paramBoolean1;
/* 188 */     this.failFast = paramBoolean2;
/*     */   }
/*     */   
/*     */   private static boolean isLineBased(ByteBuf[] paramArrayOfByteBuf)
/*     */   {
/* 193 */     if (paramArrayOfByteBuf.length != 2) {
/* 194 */       return false;
/*     */     }
/* 196 */     ByteBuf localByteBuf1 = paramArrayOfByteBuf[0];
/* 197 */     ByteBuf localByteBuf2 = paramArrayOfByteBuf[1];
/* 198 */     if (localByteBuf1.capacity() < localByteBuf2.capacity()) {
/* 199 */       localByteBuf1 = paramArrayOfByteBuf[1];
/* 200 */       localByteBuf2 = paramArrayOfByteBuf[0];
/*     */     }
/* 202 */     return (localByteBuf1.capacity() == 2) && (localByteBuf2.capacity() == 1) && (localByteBuf1.getByte(0) == 13) && (localByteBuf1.getByte(1) == 10) && (localByteBuf2.getByte(0) == 10);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isSubclass()
/*     */   {
/* 211 */     return getClass() != DelimiterBasedFrameDecoder.class;
/*     */   }
/*     */   
/*     */   protected final void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/* 216 */     Object localObject = decode(paramChannelHandlerContext, paramByteBuf);
/* 217 */     if (localObject != null) {
/* 218 */       paramList.add(localObject);
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
/* 231 */     if (this.lineBasedDecoder != null) {
/* 232 */       return this.lineBasedDecoder.decode(paramChannelHandlerContext, paramByteBuf);
/*     */     }
/*     */     
/* 235 */     int i = Integer.MAX_VALUE;
/* 236 */     Object localObject = null;
/* 237 */     for (ByteBuf localByteBuf2 : this.delimiters) {
/* 238 */       int n = indexOf(paramByteBuf, localByteBuf2);
/* 239 */       if ((n >= 0) && (n < i)) {
/* 240 */         i = n;
/* 241 */         localObject = localByteBuf2;
/*     */       }
/*     */     }
/*     */     
/* 245 */     if (localObject != null) {
/* 246 */       int j = ((ByteBuf)localObject).capacity();
/*     */       
/*     */ 
/* 249 */       if (this.discardingTooLongFrame)
/*     */       {
/*     */ 
/* 252 */         this.discardingTooLongFrame = false;
/* 253 */         paramByteBuf.skipBytes(i + j);
/*     */         
/* 255 */         ??? = this.tooLongFrameLength;
/* 256 */         this.tooLongFrameLength = 0;
/* 257 */         if (!this.failFast) {
/* 258 */           fail(???);
/*     */         }
/* 260 */         return null;
/*     */       }
/*     */       
/* 263 */       if (i > this.maxFrameLength)
/*     */       {
/* 265 */         paramByteBuf.skipBytes(i + j);
/* 266 */         fail(i);
/* 267 */         return null;
/*     */       }
/*     */       ByteBuf localByteBuf1;
/* 270 */       if (this.stripDelimiter) {
/* 271 */         localByteBuf1 = paramByteBuf.readSlice(i);
/* 272 */         paramByteBuf.skipBytes(j);
/*     */       } else {
/* 274 */         localByteBuf1 = paramByteBuf.readSlice(i + j);
/*     */       }
/*     */       
/* 277 */       return localByteBuf1.retain();
/*     */     }
/* 279 */     if (!this.discardingTooLongFrame) {
/* 280 */       if (paramByteBuf.readableBytes() > this.maxFrameLength)
/*     */       {
/* 282 */         this.tooLongFrameLength = paramByteBuf.readableBytes();
/* 283 */         paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/* 284 */         this.discardingTooLongFrame = true;
/* 285 */         if (this.failFast) {
/* 286 */           fail(this.tooLongFrameLength);
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 291 */       this.tooLongFrameLength += paramByteBuf.readableBytes();
/* 292 */       paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   private void fail(long paramLong)
/*     */   {
/* 299 */     if (paramLong > 0L) {
/* 300 */       throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + paramLong + " - discarded");
/*     */     }
/*     */     
/*     */ 
/* 304 */     throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int indexOf(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*     */   {
/* 316 */     for (int i = paramByteBuf1.readerIndex(); i < paramByteBuf1.writerIndex(); i++) {
/* 317 */       int j = i;
/*     */       
/* 319 */       for (int k = 0; k < paramByteBuf2.capacity(); k++) {
/* 320 */         if (paramByteBuf1.getByte(j) != paramByteBuf2.getByte(k)) {
/*     */           break;
/*     */         }
/* 323 */         j++;
/* 324 */         if ((j == paramByteBuf1.writerIndex()) && (k != paramByteBuf2.capacity() - 1))
/*     */         {
/* 326 */           return -1;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 331 */       if (k == paramByteBuf2.capacity())
/*     */       {
/* 333 */         return i - paramByteBuf1.readerIndex();
/*     */       }
/*     */     }
/* 336 */     return -1;
/*     */   }
/*     */   
/*     */   private static void validateDelimiter(ByteBuf paramByteBuf) {
/* 340 */     if (paramByteBuf == null) {
/* 341 */       throw new NullPointerException("delimiter");
/*     */     }
/* 343 */     if (!paramByteBuf.isReadable()) {
/* 344 */       throw new IllegalArgumentException("empty delimiter");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validateMaxFrameLength(int paramInt) {
/* 349 */     if (paramInt <= 0) {
/* 350 */       throw new IllegalArgumentException("maxFrameLength must be a positive integer: " + paramInt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\DelimiterBasedFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */