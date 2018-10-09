/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.ChannelPromiseNotifier;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.zip.CRC32;
/*     */ import java.util.zip.Deflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JdkZlibEncoder
/*     */   extends ZlibEncoder
/*     */ {
/*     */   private final ZlibWrapper wrapper;
/*     */   private final Deflater deflater;
/*     */   private volatile boolean finished;
/*     */   private volatile ChannelHandlerContext ctx;
/*  43 */   private final CRC32 crc = new CRC32();
/*  44 */   private static final byte[] gzipHeader = { 31, -117, 8, 0, 0, 0, 0, 0, 0, 0 };
/*  45 */   private boolean writeHeader = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkZlibEncoder()
/*     */   {
/*  54 */     this(6);
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
/*     */   public JdkZlibEncoder(int paramInt)
/*     */   {
/*  69 */     this(ZlibWrapper.ZLIB, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkZlibEncoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/*  79 */     this(paramZlibWrapper, 6);
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
/*     */   public JdkZlibEncoder(ZlibWrapper paramZlibWrapper, int paramInt)
/*     */   {
/*  94 */     if ((paramInt < 0) || (paramInt > 9)) {
/*  95 */       throw new IllegalArgumentException("compressionLevel: " + paramInt + " (expected: 0-9)");
/*     */     }
/*     */     
/*  98 */     if (paramZlibWrapper == null) {
/*  99 */       throw new NullPointerException("wrapper");
/*     */     }
/* 101 */     if (paramZlibWrapper == ZlibWrapper.ZLIB_OR_NONE) {
/* 102 */       throw new IllegalArgumentException("wrapper '" + ZlibWrapper.ZLIB_OR_NONE + "' is not " + "allowed for compression.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 107 */     this.wrapper = paramZlibWrapper;
/* 108 */     this.deflater = new Deflater(paramInt, paramZlibWrapper != ZlibWrapper.ZLIB);
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
/*     */   public JdkZlibEncoder(byte[] paramArrayOfByte)
/*     */   {
/* 122 */     this(6, paramArrayOfByte);
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
/*     */   public JdkZlibEncoder(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 140 */     if ((paramInt < 0) || (paramInt > 9)) {
/* 141 */       throw new IllegalArgumentException("compressionLevel: " + paramInt + " (expected: 0-9)");
/*     */     }
/*     */     
/* 144 */     if (paramArrayOfByte == null) {
/* 145 */       throw new NullPointerException("dictionary");
/*     */     }
/*     */     
/* 148 */     this.wrapper = ZlibWrapper.ZLIB;
/* 149 */     this.deflater = new Deflater(paramInt);
/* 150 */     this.deflater.setDictionary(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public ChannelFuture close()
/*     */   {
/* 155 */     return close(ctx().newPromise());
/*     */   }
/*     */   
/*     */   public ChannelFuture close(final ChannelPromise paramChannelPromise)
/*     */   {
/* 160 */     ChannelHandlerContext localChannelHandlerContext = ctx();
/* 161 */     EventExecutor localEventExecutor = localChannelHandlerContext.executor();
/* 162 */     if (localEventExecutor.inEventLoop()) {
/* 163 */       return finishEncode(localChannelHandlerContext, paramChannelPromise);
/*     */     }
/* 165 */     final ChannelPromise localChannelPromise = localChannelHandlerContext.newPromise();
/* 166 */     localEventExecutor.execute(new Runnable()
/*     */     {
/*     */       public void run() {
/* 169 */         ChannelFuture localChannelFuture = JdkZlibEncoder.this.finishEncode(JdkZlibEncoder.access$000(JdkZlibEncoder.this), localChannelPromise);
/* 170 */         localChannelFuture.addListener(new ChannelPromiseNotifier(new ChannelPromise[] { paramChannelPromise }));
/*     */       }
/* 172 */     });
/* 173 */     return localChannelPromise;
/*     */   }
/*     */   
/*     */   private ChannelHandlerContext ctx()
/*     */   {
/* 178 */     ChannelHandlerContext localChannelHandlerContext = this.ctx;
/* 179 */     if (localChannelHandlerContext == null) {
/* 180 */       throw new IllegalStateException("not added to a pipeline");
/*     */     }
/* 182 */     return localChannelHandlerContext;
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/* 187 */     return this.finished;
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*     */   {
/* 192 */     if (this.finished) {
/* 193 */       paramByteBuf2.writeBytes(paramByteBuf1);
/* 194 */       return;
/*     */     }
/*     */     
/* 197 */     int i = paramByteBuf1.readableBytes();
/*     */     byte[] arrayOfByte;
/*     */     int j;
/* 200 */     if (paramByteBuf1.hasArray())
/*     */     {
/* 202 */       arrayOfByte = paramByteBuf1.array();
/* 203 */       j = paramByteBuf1.arrayOffset() + paramByteBuf1.readerIndex();
/*     */       
/* 205 */       paramByteBuf1.skipBytes(i);
/*     */     } else {
/* 207 */       arrayOfByte = new byte[i];
/* 208 */       paramByteBuf1.readBytes(arrayOfByte);
/* 209 */       j = 0;
/*     */     }
/*     */     
/* 212 */     if (this.writeHeader) {
/* 213 */       this.writeHeader = false;
/* 214 */       if (this.wrapper == ZlibWrapper.GZIP) {
/* 215 */         paramByteBuf2.writeBytes(gzipHeader);
/*     */       }
/*     */     }
/*     */     
/* 219 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 220 */       this.crc.update(arrayOfByte, j, i);
/*     */     }
/*     */     
/* 223 */     this.deflater.setInput(arrayOfByte, j, i);
/* 224 */     while (!this.deflater.needsInput()) {
/* 225 */       deflate(paramByteBuf2);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final ByteBuf allocateBuffer(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, boolean paramBoolean)
/*     */     throws Exception
/*     */   {
/* 232 */     int i = (int)Math.ceil(paramByteBuf.readableBytes() * 1.001D) + 12;
/* 233 */     if (this.writeHeader) {
/* 234 */       switch (this.wrapper) {
/*     */       case GZIP: 
/* 236 */         i += gzipHeader.length;
/* 237 */         break;
/*     */       case ZLIB: 
/* 239 */         i += 2;
/*     */       }
/*     */       
/*     */     }
/* 243 */     return paramChannelHandlerContext.alloc().heapBuffer(i);
/*     */   }
/*     */   
/*     */   public void close(final ChannelHandlerContext paramChannelHandlerContext, final ChannelPromise paramChannelPromise) throws Exception
/*     */   {
/* 248 */     ChannelFuture localChannelFuture = finishEncode(paramChannelHandlerContext, paramChannelHandlerContext.newPromise());
/* 249 */     localChannelFuture.addListener(new ChannelFutureListener()
/*     */     {
/*     */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 252 */         paramChannelHandlerContext.close(paramChannelPromise);
/*     */       }
/*     */     });
/*     */     
/* 256 */     if (!localChannelFuture.isDone())
/*     */     {
/* 258 */       paramChannelHandlerContext.executor().schedule(new Runnable()
/*     */       {
/*     */ 
/* 261 */         public void run() { paramChannelHandlerContext.close(paramChannelPromise); } }, 10L, TimeUnit.SECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private ChannelFuture finishEncode(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise)
/*     */   {
/* 268 */     if (this.finished) {
/* 269 */       paramChannelPromise.setSuccess();
/* 270 */       return paramChannelPromise;
/*     */     }
/*     */     
/* 273 */     this.finished = true;
/* 274 */     ByteBuf localByteBuf = paramChannelHandlerContext.alloc().heapBuffer();
/* 275 */     if ((this.writeHeader) && (this.wrapper == ZlibWrapper.GZIP))
/*     */     {
/* 277 */       this.writeHeader = false;
/* 278 */       localByteBuf.writeBytes(gzipHeader);
/*     */     }
/*     */     
/* 281 */     this.deflater.finish();
/*     */     
/* 283 */     while (!this.deflater.finished()) {
/* 284 */       deflate(localByteBuf);
/* 285 */       if (!localByteBuf.isWritable())
/*     */       {
/* 287 */         paramChannelHandlerContext.write(localByteBuf);
/* 288 */         localByteBuf = paramChannelHandlerContext.alloc().heapBuffer();
/*     */       }
/*     */     }
/* 291 */     if (this.wrapper == ZlibWrapper.GZIP) {
/* 292 */       int i = (int)this.crc.getValue();
/* 293 */       int j = this.deflater.getTotalIn();
/* 294 */       localByteBuf.writeByte(i);
/* 295 */       localByteBuf.writeByte(i >>> 8);
/* 296 */       localByteBuf.writeByte(i >>> 16);
/* 297 */       localByteBuf.writeByte(i >>> 24);
/* 298 */       localByteBuf.writeByte(j);
/* 299 */       localByteBuf.writeByte(j >>> 8);
/* 300 */       localByteBuf.writeByte(j >>> 16);
/* 301 */       localByteBuf.writeByte(j >>> 24);
/*     */     }
/* 303 */     this.deflater.end();
/* 304 */     return paramChannelHandlerContext.writeAndFlush(localByteBuf, paramChannelPromise);
/*     */   }
/*     */   
/*     */   private void deflate(ByteBuf paramByteBuf) {
/*     */     int j;
/*     */     do {
/* 310 */       int i = paramByteBuf.writerIndex();
/* 311 */       j = this.deflater.deflate(paramByteBuf.array(), paramByteBuf.arrayOffset() + i, paramByteBuf.writableBytes(), 2);
/*     */       
/* 313 */       paramByteBuf.writerIndex(i + j);
/* 314 */     } while (j > 0);
/*     */   }
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 319 */     this.ctx = paramChannelHandlerContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\JdkZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */