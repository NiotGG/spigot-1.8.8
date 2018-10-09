/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkedNioFile
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   private final FileChannel in;
/*     */   private final long startOffset;
/*     */   private final long endOffset;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   
/*     */   public ChunkedNioFile(File paramFile)
/*     */     throws IOException
/*     */   {
/*  47 */     this(new FileInputStream(paramFile).getChannel());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedNioFile(File paramFile, int paramInt)
/*     */     throws IOException
/*     */   {
/*  57 */     this(new FileInputStream(paramFile).getChannel(), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChunkedNioFile(FileChannel paramFileChannel)
/*     */     throws IOException
/*     */   {
/*  64 */     this(paramFileChannel, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedNioFile(FileChannel paramFileChannel, int paramInt)
/*     */     throws IOException
/*     */   {
/*  74 */     this(paramFileChannel, 0L, paramFileChannel.size(), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedNioFile(FileChannel paramFileChannel, long paramLong1, long paramLong2, int paramInt)
/*     */     throws IOException
/*     */   {
/*  87 */     if (paramFileChannel == null) {
/*  88 */       throw new NullPointerException("in");
/*     */     }
/*  90 */     if (paramLong1 < 0L) {
/*  91 */       throw new IllegalArgumentException("offset: " + paramLong1 + " (expected: 0 or greater)");
/*     */     }
/*     */     
/*  94 */     if (paramLong2 < 0L) {
/*  95 */       throw new IllegalArgumentException("length: " + paramLong2 + " (expected: 0 or greater)");
/*     */     }
/*     */     
/*  98 */     if (paramInt <= 0) {
/*  99 */       throw new IllegalArgumentException("chunkSize: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 104 */     if (paramLong1 != 0L) {
/* 105 */       paramFileChannel.position(paramLong1);
/*     */     }
/* 107 */     this.in = paramFileChannel;
/* 108 */     this.chunkSize = paramInt;
/* 109 */     this.offset = (this.startOffset = paramLong1);
/* 110 */     this.endOffset = (paramLong1 + paramLong2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long startOffset()
/*     */   {
/* 117 */     return this.startOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long endOffset()
/*     */   {
/* 124 */     return this.endOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long currentOffset()
/*     */   {
/* 131 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean isEndOfInput() throws Exception
/*     */   {
/* 136 */     return (this.offset >= this.endOffset) || (!this.in.isOpen());
/*     */   }
/*     */   
/*     */   public void close() throws Exception
/*     */   {
/* 141 */     this.in.close();
/*     */   }
/*     */   
/*     */   public ByteBuf readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 146 */     long l = this.offset;
/* 147 */     if (l >= this.endOffset) {
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     int i = (int)Math.min(this.chunkSize, this.endOffset - l);
/* 152 */     ByteBuf localByteBuf1 = paramChannelHandlerContext.alloc().buffer(i);
/* 153 */     int j = 1;
/*     */     try {
/* 155 */       int k = 0;
/*     */       for (;;) {
/* 157 */         int m = localByteBuf1.writeBytes(this.in, i - k);
/* 158 */         if (m < 0) {
/*     */           break;
/*     */         }
/* 161 */         k += m;
/* 162 */         if (k == i) {
/*     */           break;
/*     */         }
/*     */       }
/* 166 */       this.offset += k;
/* 167 */       j = 0;
/* 168 */       return localByteBuf1;
/*     */     } finally {
/* 170 */       if (j != 0) {
/* 171 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedNioFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */