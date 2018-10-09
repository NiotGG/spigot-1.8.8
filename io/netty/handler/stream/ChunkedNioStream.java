/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedNioStream
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   private final ReadableByteChannel in;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   private final ByteBuffer byteBuffer;
/*     */   
/*     */   public ChunkedNioStream(ReadableByteChannel paramReadableByteChannel)
/*     */   {
/*  45 */     this(paramReadableByteChannel, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedNioStream(ReadableByteChannel paramReadableByteChannel, int paramInt)
/*     */   {
/*  55 */     if (paramReadableByteChannel == null) {
/*  56 */       throw new NullPointerException("in");
/*     */     }
/*  58 */     if (paramInt <= 0) {
/*  59 */       throw new IllegalArgumentException("chunkSize: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*  62 */     this.in = paramReadableByteChannel;
/*  63 */     this.offset = 0L;
/*  64 */     this.chunkSize = paramInt;
/*  65 */     this.byteBuffer = ByteBuffer.allocate(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long transferredBytes()
/*     */   {
/*  72 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean isEndOfInput() throws Exception
/*     */   {
/*  77 */     if (this.byteBuffer.position() > 0)
/*     */     {
/*  79 */       return false;
/*     */     }
/*  81 */     if (this.in.isOpen())
/*     */     {
/*  83 */       int i = this.in.read(this.byteBuffer);
/*  84 */       if (i < 0) {
/*  85 */         return true;
/*     */       }
/*  87 */       this.offset += i;
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public void close() throws Exception
/*     */   {
/*  96 */     this.in.close();
/*     */   }
/*     */   
/*     */   public ByteBuf readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 101 */     if (isEndOfInput()) {
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     int i = this.byteBuffer.position();
/*     */     for (;;) {
/* 107 */       j = this.in.read(this.byteBuffer);
/* 108 */       if (j < 0) {
/*     */         break;
/*     */       }
/* 111 */       i += j;
/* 112 */       this.offset += j;
/* 113 */       if (i == this.chunkSize) {
/*     */         break;
/*     */       }
/*     */     }
/* 117 */     this.byteBuffer.flip();
/* 118 */     int j = 1;
/* 119 */     ByteBuf localByteBuf1 = paramChannelHandlerContext.alloc().buffer(this.byteBuffer.remaining());
/*     */     try {
/* 121 */       localByteBuf1.writeBytes(this.byteBuffer);
/* 122 */       this.byteBuffer.clear();
/* 123 */       j = 0;
/* 124 */       return localByteBuf1;
/*     */     } finally {
/* 126 */       if (j != 0) {
/* 127 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedNioStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */