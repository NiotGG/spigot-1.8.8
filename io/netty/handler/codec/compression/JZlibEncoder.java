/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ChannelPromiseNotifier;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*     */   private final int wrapperOverhead;
/*  38 */   private final Deflater z = new Deflater();
/*     */   
/*     */ 
/*     */   private volatile boolean finished;
/*     */   
/*     */ 
/*     */   private volatile ChannelHandlerContext ctx;
/*     */   
/*     */ 
/*     */ 
/*     */   public JZlibEncoder()
/*     */   {
/*  50 */     this(6);
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
/*     */   public JZlibEncoder(int paramInt)
/*     */   {
/*  66 */     this(ZlibWrapper.ZLIB, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JZlibEncoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/*  77 */     this(paramZlibWrapper, 6);
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
/*     */   public JZlibEncoder(ZlibWrapper paramZlibWrapper, int paramInt)
/*     */   {
/*  93 */     this(paramZlibWrapper, paramInt, 15, 8);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JZlibEncoder(ZlibWrapper paramZlibWrapper, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 120 */     if ((paramInt1 < 0) || (paramInt1 > 9)) {
/* 121 */       throw new IllegalArgumentException("compressionLevel: " + paramInt1 + " (expected: 0-9)");
/*     */     }
/*     */     
/*     */ 
/* 125 */     if ((paramInt2 < 9) || (paramInt2 > 15)) {
/* 126 */       throw new IllegalArgumentException("windowBits: " + paramInt2 + " (expected: 9-15)");
/*     */     }
/*     */     
/* 129 */     if ((paramInt3 < 1) || (paramInt3 > 9)) {
/* 130 */       throw new IllegalArgumentException("memLevel: " + paramInt3 + " (expected: 1-9)");
/*     */     }
/*     */     
/* 133 */     if (paramZlibWrapper == null) {
/* 134 */       throw new NullPointerException("wrapper");
/*     */     }
/* 136 */     if (paramZlibWrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 137 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 142 */     int i = this.z.init(paramInt1, paramInt2, paramInt3, ZlibUtil.convertWrapperType(paramZlibWrapper));
/*     */     
/*     */ 
/* 145 */     if (i != 0) {
/* 146 */       ZlibUtil.fail(this.z, "initialization failure", i);
/*     */     }
/*     */     
/* 149 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(paramZlibWrapper);
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
/*     */   public JZlibEncoder(byte[] paramArrayOfByte)
/*     */   {
/* 164 */     this(6, paramArrayOfByte);
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
/*     */   public JZlibEncoder(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 183 */     this(paramInt, 15, 8, paramArrayOfByte);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JZlibEncoder(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
/*     */   {
/* 212 */     if ((paramInt1 < 0) || (paramInt1 > 9)) {
/* 213 */       throw new IllegalArgumentException("compressionLevel: " + paramInt1 + " (expected: 0-9)");
/*     */     }
/* 215 */     if ((paramInt2 < 9) || (paramInt2 > 15)) {
/* 216 */       throw new IllegalArgumentException("windowBits: " + paramInt2 + " (expected: 9-15)");
/*     */     }
/*     */     
/* 219 */     if ((paramInt3 < 1) || (paramInt3 > 9)) {
/* 220 */       throw new IllegalArgumentException("memLevel: " + paramInt3 + " (expected: 1-9)");
/*     */     }
/*     */     
/* 223 */     if (paramArrayOfByte == null) {
/* 224 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 227 */     int i = this.z.deflateInit(paramInt1, paramInt2, paramInt3, JZlib.W_ZLIB);
/*     */     
/*     */ 
/* 230 */     if (i != 0) {
/* 231 */       ZlibUtil.fail(this.z, "initialization failure", i);
/*     */     } else {
/* 233 */       i = this.z.deflateSetDictionary(paramArrayOfByte, paramArrayOfByte.length);
/* 234 */       if (i != 0) {
/* 235 */         ZlibUtil.fail(this.z, "failed to set the dictionary", i);
/*     */       }
/*     */     }
/*     */     
/* 239 */     this.wrapperOverhead = ZlibUtil.wrapperOverhead(ZlibWrapper.ZLIB);
/*     */   }
/*     */   
/*     */   public ChannelFuture close()
/*     */   {
/* 244 */     return close(ctx().channel().newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise paramChannelPromise)
/*     */   {
/* 249 */     ChannelHandlerContext localChannelHandlerContext = ctx();
/* 250 */     EventExecutor localEventExecutor = localChannelHandlerContext.executor();
/* 251 */     if (localEventExecutor.inEventLoop()) {
/* 252 */       return finishEncode(localChannelHandlerContext, paramChannelPromise);
/*     */     }
/* 254 */     final ChannelPromise localChannelPromise = localChannelHandlerContext.newPromise();
/* 255 */     localEventExecutor.execute(new Runnable()
/*     */     {
/*     */       public void run() {
/* 258 */         ChannelFuture localChannelFuture = JZlibEncoder.this.finishEncode(JZlibEncoder.access$000(JZlibEncoder.this), localChannelPromise);
/* 259 */         localChannelFuture.addListener(new ChannelPromiseNotifier(new ChannelPromise[] { paramChannelPromise }));
/*     */       }
/* 261 */     });
/* 262 */     return localChannelPromise;
/*     */   }
/*     */   
/*     */   private ChannelHandlerContext ctx()
/*     */   {
/* 267 */     ChannelHandlerContext localChannelHandlerContext = this.ctx;
/* 268 */     if (localChannelHandlerContext == null) {
/* 269 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 271 */     return localChannelHandlerContext;
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/* 276 */     return this.finished;
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*     */   {
/* 281 */     if (this.finished) {
/* 282 */       return;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 287 */       int i = paramByteBuf1.readableBytes();
/* 288 */       boolean bool = paramByteBuf1.hasArray();
/* 289 */       this.z.avail_in = i;
/* 290 */       if (bool) {
/* 291 */         this.z.next_in = paramByteBuf1.array();
/* 292 */         this.z.next_in_index = (paramByteBuf1.arrayOffset() + paramByteBuf1.readerIndex());
/*     */       } else {
/* 294 */         byte[] arrayOfByte = new byte[i];
/* 295 */         paramByteBuf1.getBytes(paramByteBuf1.readerIndex(), arrayOfByte);
/* 296 */         this.z.next_in = arrayOfByte;
/* 297 */         this.z.next_in_index = 0;
/*     */       }
/* 299 */       int j = this.z.next_in_index;
/*     */       
/*     */ 
/* 302 */       int k = (int)Math.ceil(i * 1.001D) + 12 + this.wrapperOverhead;
/* 303 */       paramByteBuf2.ensureWritable(k);
/* 304 */       this.z.avail_out = k;
/* 305 */       this.z.next_out = paramByteBuf2.array();
/* 306 */       this.z.next_out_index = (paramByteBuf2.arrayOffset() + paramByteBuf2.writerIndex());
/* 307 */       int m = this.z.next_out_index;
/*     */       
/*     */       int n;
/*     */       try
/*     */       {
/* 312 */         n = this.z.deflate(2);
/*     */       } finally {
/* 314 */         paramByteBuf1.skipBytes(this.z.next_in_index - j);
/*     */       }
/*     */       
/* 317 */       if (n != 0) {
/* 318 */         ZlibUtil.fail(this.z, "compression failure", n);
/*     */       }
/*     */       
/* 321 */       int i1 = this.z.next_out_index - m;
/* 322 */       if (i1 > 0) {
/* 323 */         paramByteBuf2.writerIndex(paramByteBuf2.writerIndex() + i1);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/* 330 */       this.z.next_in = null;
/* 331 */       this.z.next_out = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void close(final ChannelHandlerContext paramChannelHandlerContext, final ChannelPromise paramChannelPromise)
/*     */   {
/* 339 */     ChannelFuture localChannelFuture = finishEncode(paramChannelHandlerContext, paramChannelHandlerContext.newPromise());
/* 340 */     localChannelFuture.addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 343 */         paramChannelHandlerContext.close(paramChannelPromise);
/*     */       }
/*     */     });
/*     */     
/* 347 */     if (!localChannelFuture.isDone())
/*     */     {
/* 349 */       paramChannelHandlerContext.executor().schedule(new Runnable()
/*     */       {
/*     */ 
/* 352 */         public void run() { paramChannelHandlerContext.close(paramChannelPromise); } }, 10L, TimeUnit.SECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */   {
/* 359 */     if (this.finished) {
/* 360 */       paramChannelPromise.setSuccess();
/* 361 */       return paramChannelPromise;
/*     */     }
/* 363 */     this.finished = true;
/*     */     
/*     */     ByteBuf localByteBuf;
/*     */     try
/*     */     {
/* 368 */       this.z.next_in = EmptyArrays.EMPTY_BYTES;
/* 369 */       this.z.next_in_index = 0;
/* 370 */       this.z.avail_in = 0;
/*     */       
/*     */ 
/* 373 */       byte[] arrayOfByte = new byte[32];
/* 374 */       this.z.next_out = arrayOfByte;
/* 375 */       this.z.next_out_index = 0;
/* 376 */       this.z.avail_out = arrayOfByte.length;
/*     */       
/*     */ 
/* 379 */       int i = this.z.deflate(4);
/* 380 */       if ((i != 0) && (i != 1)) {
/* 381 */         paramChannelPromise.setFailure(ZlibUtil.deflaterException(this.z, "compression failure", i));
/* 382 */         return paramChannelPromise; }
/* 383 */       if (this.z.next_out_index != 0) {
/* 384 */         localByteBuf = Unpooled.wrappedBuffer(arrayOfByte, 0, this.z.next_out_index);
/*     */       } else {
/* 386 */         localByteBuf = Unpooled.EMPTY_BUFFER;
/*     */       }
/*     */     } finally {
/* 389 */       this.z.deflateEnd();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 395 */       this.z.next_in = null;
/* 396 */       this.z.next_out = null;
/*     */     }
/* 398 */     return paramChannelHandlerContext.writeAndFlush(localByteBuf, paramChannelPromise);
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 403 */     this.ctx = paramChannelHandlerContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\JZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */