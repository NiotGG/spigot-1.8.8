/*     */ package io.netty.handler.stream;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
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
/*     */ public class ChunkedFile
/*     */   implements ChunkedInput<ByteBuf>
/*     */ {
/*     */   private final RandomAccessFile file;
/*     */   private final long startOffset;
/*     */   private final long endOffset;
/*     */   private final int chunkSize;
/*     */   private long offset;
/*     */   
/*     */   public ChunkedFile(File paramFile)
/*     */     throws IOException
/*     */   {
/*  45 */     this(paramFile, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedFile(File paramFile, int paramInt)
/*     */     throws IOException
/*     */   {
/*  55 */     this(new RandomAccessFile(paramFile, "r"), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   public ChunkedFile(RandomAccessFile paramRandomAccessFile)
/*     */     throws IOException
/*     */   {
/*  62 */     this(paramRandomAccessFile, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedFile(RandomAccessFile paramRandomAccessFile, int paramInt)
/*     */     throws IOException
/*     */   {
/*  72 */     this(paramRandomAccessFile, 0L, paramRandomAccessFile.length(), paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChunkedFile(RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, int paramInt)
/*     */     throws IOException
/*     */   {
/*  84 */     if (paramRandomAccessFile == null) {
/*  85 */       throw new NullPointerException("file");
/*     */     }
/*  87 */     if (paramLong1 < 0L) {
/*  88 */       throw new IllegalArgumentException("offset: " + paramLong1 + " (expected: 0 or greater)");
/*     */     }
/*     */     
/*  91 */     if (paramLong2 < 0L) {
/*  92 */       throw new IllegalArgumentException("length: " + paramLong2 + " (expected: 0 or greater)");
/*     */     }
/*     */     
/*  95 */     if (paramInt <= 0) {
/*  96 */       throw new IllegalArgumentException("chunkSize: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 101 */     this.file = paramRandomAccessFile;
/* 102 */     this.offset = (this.startOffset = paramLong1);
/* 103 */     this.endOffset = (paramLong1 + paramLong2);
/* 104 */     this.chunkSize = paramInt;
/*     */     
/* 106 */     paramRandomAccessFile.seek(paramLong1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long startOffset()
/*     */   {
/* 113 */     return this.startOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long endOffset()
/*     */   {
/* 120 */     return this.endOffset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long currentOffset()
/*     */   {
/* 127 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean isEndOfInput() throws Exception
/*     */   {
/* 132 */     return (this.offset >= this.endOffset) || (!this.file.getChannel().isOpen());
/*     */   }
/*     */   
/*     */   public void close() throws Exception
/*     */   {
/* 137 */     this.file.close();
/*     */   }
/*     */   
/*     */   public ByteBuf readChunk(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 142 */     long l = this.offset;
/* 143 */     if (l >= this.endOffset) {
/* 144 */       return null;
/*     */     }
/*     */     
/* 147 */     int i = (int)Math.min(this.chunkSize, this.endOffset - l);
/*     */     
/*     */ 
/* 150 */     ByteBuf localByteBuf1 = paramChannelHandlerContext.alloc().heapBuffer(i);
/* 151 */     int j = 1;
/*     */     try {
/* 153 */       this.file.readFully(localByteBuf1.array(), localByteBuf1.arrayOffset(), i);
/* 154 */       localByteBuf1.writerIndex(i);
/* 155 */       this.offset = (l + i);
/* 156 */       j = 0;
/* 157 */       return localByteBuf1;
/*     */     } finally {
/* 159 */       if (j != 0) {
/* 160 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\stream\ChunkedFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */