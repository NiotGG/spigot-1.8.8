/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
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
/*     */ class SpdyHeaderBlockZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*     */   private final Deflater compressor;
/*     */   private boolean finished;
/*     */   
/*     */   SpdyHeaderBlockZlibEncoder(SpdyVersion paramSpdyVersion, int paramInt)
/*     */   {
/*  32 */     super(paramSpdyVersion);
/*  33 */     if ((paramInt < 0) || (paramInt > 9)) {
/*  34 */       throw new IllegalArgumentException("compressionLevel: " + paramInt + " (expected: 0-9)");
/*     */     }
/*     */     
/*  37 */     this.compressor = new Deflater(paramInt);
/*  38 */     this.compressor.setDictionary(SpdyCodecUtil.SPDY_DICT);
/*     */   }
/*     */   
/*     */   private int setInput(ByteBuf paramByteBuf) {
/*  42 */     int i = paramByteBuf.readableBytes();
/*     */     
/*  44 */     if (paramByteBuf.hasArray()) {
/*  45 */       this.compressor.setInput(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramByteBuf.readerIndex(), i);
/*     */     } else {
/*  47 */       byte[] arrayOfByte = new byte[i];
/*  48 */       paramByteBuf.getBytes(paramByteBuf.readerIndex(), arrayOfByte);
/*  49 */       this.compressor.setInput(arrayOfByte, 0, arrayOfByte.length);
/*     */     }
/*     */     
/*  52 */     return i;
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf paramByteBuf) {
/*  56 */     while (compressInto(paramByteBuf))
/*     */     {
/*  58 */       paramByteBuf.ensureWritable(paramByteBuf.capacity() << 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean compressInto(ByteBuf paramByteBuf) {
/*  63 */     byte[] arrayOfByte = paramByteBuf.array();
/*  64 */     int i = paramByteBuf.arrayOffset() + paramByteBuf.writerIndex();
/*  65 */     int j = paramByteBuf.writableBytes();
/*  66 */     int k = this.compressor.deflate(arrayOfByte, i, j, 2);
/*  67 */     paramByteBuf.writerIndex(paramByteBuf.writerIndex() + k);
/*  68 */     return k == j;
/*     */   }
/*     */   
/*     */   public ByteBuf encode(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/*  73 */     if (paramSpdyHeadersFrame == null) {
/*  74 */       throw new IllegalArgumentException("frame");
/*     */     }
/*     */     
/*  77 */     if (this.finished) {
/*  78 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/*  81 */     ByteBuf localByteBuf1 = super.encode(paramSpdyHeadersFrame);
/*  82 */     if (localByteBuf1.readableBytes() == 0) {
/*  83 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/*  86 */     ByteBuf localByteBuf2 = localByteBuf1.alloc().heapBuffer(localByteBuf1.readableBytes());
/*  87 */     int i = setInput(localByteBuf1);
/*  88 */     encode(localByteBuf2);
/*  89 */     localByteBuf1.skipBytes(i);
/*     */     
/*  91 */     return localByteBuf2;
/*     */   }
/*     */   
/*     */   public void end()
/*     */   {
/*  96 */     if (this.finished) {
/*  97 */       return;
/*     */     }
/*  99 */     this.finished = true;
/* 100 */     this.compressor.end();
/* 101 */     super.end();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */