/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.Inflater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SpdyHeaderBlockZlibDecoder
/*     */   extends SpdyHeaderBlockRawDecoder
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_CAPACITY = 4096;
/*  29 */   private static final SpdyProtocolException INVALID_HEADER_BLOCK = new SpdyProtocolException("Invalid Header Block");
/*     */   
/*     */ 
/*  32 */   private final Inflater decompressor = new Inflater();
/*     */   private ByteBuf decompressed;
/*     */   
/*     */   SpdyHeaderBlockZlibDecoder(SpdyVersion paramSpdyVersion, int paramInt)
/*     */   {
/*  37 */     super(paramSpdyVersion, paramInt);
/*     */   }
/*     */   
/*     */   void decode(ByteBuf paramByteBuf, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/*  42 */     int i = setInput(paramByteBuf);
/*     */     int j;
/*     */     do
/*     */     {
/*  46 */       j = decompress(paramByteBuf.alloc(), paramSpdyHeadersFrame);
/*  47 */     } while (j > 0);
/*     */     
/*     */ 
/*     */ 
/*  51 */     if (this.decompressor.getRemaining() != 0)
/*     */     {
/*  53 */       throw INVALID_HEADER_BLOCK;
/*     */     }
/*     */     
/*  56 */     paramByteBuf.skipBytes(i);
/*     */   }
/*     */   
/*     */   private int setInput(ByteBuf paramByteBuf) {
/*  60 */     int i = paramByteBuf.readableBytes();
/*     */     
/*  62 */     if (paramByteBuf.hasArray()) {
/*  63 */       this.decompressor.setInput(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramByteBuf.readerIndex(), i);
/*     */     } else {
/*  65 */       byte[] arrayOfByte = new byte[i];
/*  66 */       paramByteBuf.getBytes(paramByteBuf.readerIndex(), arrayOfByte);
/*  67 */       this.decompressor.setInput(arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/*     */     
/*  70 */     return i;
/*     */   }
/*     */   
/*     */   private int decompress(ByteBufAllocator paramByteBufAllocator, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception {
/*  74 */     ensureBuffer(paramByteBufAllocator);
/*  75 */     byte[] arrayOfByte = this.decompressed.array();
/*  76 */     int i = this.decompressed.arrayOffset() + this.decompressed.writerIndex();
/*     */     try {
/*  78 */       int j = this.decompressor.inflate(arrayOfByte, i, this.decompressed.writableBytes());
/*  79 */       if ((j == 0) && (this.decompressor.needsDictionary())) {
/*     */         try {
/*  81 */           this.decompressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*     */         } catch (IllegalArgumentException localIllegalArgumentException) {
/*  83 */           throw INVALID_HEADER_BLOCK;
/*     */         }
/*  85 */         j = this.decompressor.inflate(arrayOfByte, i, this.decompressed.writableBytes());
/*     */       }
/*  87 */       if (paramSpdyHeadersFrame != null) {
/*  88 */         this.decompressed.writerIndex(this.decompressed.writerIndex() + j);
/*  89 */         decodeHeaderBlock(this.decompressed, paramSpdyHeadersFrame);
/*  90 */         this.decompressed.discardReadBytes();
/*     */       }
/*     */       
/*  93 */       return j;
/*     */     } catch (DataFormatException localDataFormatException) {
/*  95 */       throw new SpdyProtocolException("Received invalid header block", localDataFormatException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensureBuffer(ByteBufAllocator paramByteBufAllocator) {
/* 100 */     if (this.decompressed == null) {
/* 101 */       this.decompressed = paramByteBufAllocator.heapBuffer(4096);
/*     */     }
/* 103 */     this.decompressed.ensureWritable(1);
/*     */   }
/*     */   
/*     */   void endHeaderBlock(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/* 108 */     super.endHeaderBlock(paramSpdyHeadersFrame);
/* 109 */     releaseBuffer();
/*     */   }
/*     */   
/*     */   public void end()
/*     */   {
/* 114 */     super.end();
/* 115 */     releaseBuffer();
/* 116 */     this.decompressor.end();
/*     */   }
/*     */   
/*     */   private void releaseBuffer() {
/* 120 */     if (this.decompressed != null) {
/* 121 */       this.decompressed.release();
/* 122 */       this.decompressed = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */