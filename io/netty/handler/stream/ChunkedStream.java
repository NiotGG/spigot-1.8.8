/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedStream
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   static final int DEFAULT_CHUNK_SIZE = 8192;
/*     */   private final PushbackInputStream in;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   
/*     */   public ChunkedStream(InputStream paramInputStream)
/*     */   {
/*  46 */     this(paramInputStream, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedStream(InputStream paramInputStream, int paramInt)
/*     */   {
/*  56 */     if (paramInputStream == null) {
/*  57 */       throw new NullPointerException("in");
/*     */     }
/*  59 */     if (paramInt <= 0) {
/*  60 */       throw new IllegalArgumentException("chunkSize: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  65 */     if ((paramInputStream instanceof PushbackInputStream)) {
/*  66 */       this.in = ((PushbackInputStream)paramInputStream);
/*     */     } else {
/*  68 */       this.in = new PushbackInputStream(paramInputStream);
/*     */     }
/*  70 */     this.chunkSize = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long transferredBytes()
/*     */   {
/*  77 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean isEndOfInput() throws Exception
/*     */   {
/*  82 */     int i = this.in.read();
/*  83 */     if (i < 0) {
/*  84 */       return true;
/*     */     }
/*  86 */     this.in.unread(i);
/*  87 */     return false;
/*     */   }
/*     */   
/*     */   public void close()
/*     */     throws Exception
/*     */   {
/*  93 */     this.in.close();
/*     */   }
/*     */   
/*     */   public ByteBuf readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/*  98 */     if (isEndOfInput()) {
/*  99 */       return null;
/*     */     }
/*     */     
/* 102 */     int i = this.in.available();
/*     */     int j;
/* 104 */     if (i <= 0) {
/* 105 */       j = this.chunkSize;
/*     */     } else {
/* 107 */       j = Math.min(this.chunkSize, this.in.available());
/*     */     }
/*     */     
/* 110 */     int k = 1;
/* 111 */     ByteBuf localByteBuf1 = paramChannelHandlerContext.alloc().buffer(j);
/*     */     try
/*     */     {
/* 114 */       this.offset += localByteBuf1.writeBytes(this.in, j);
/* 115 */       k = 0;
/* 116 */       return localByteBuf1;
/*     */     } finally {
/* 118 */       if (k != 0) {
/* 119 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */