/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import com.jcraft.jzlib.Deflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.compression.CompressionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SpdyHeaderBlockJZlibEncoder
/*     */   extends SpdyHeaderBlockRawEncoder
/*     */ {
/*  28 */   private final Deflater z = new Deflater();
/*     */   
/*     */   private boolean finished;
/*     */   
/*     */   SpdyHeaderBlockJZlibEncoder(SpdyVersion paramSpdyVersion, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  34 */     super(paramSpdyVersion);
/*  35 */     if ((paramInt1 < 0) || (paramInt1 > 9)) {
/*  36 */       throw new IllegalArgumentException("compressionLevel: " + paramInt1 + " (expected: 0-9)");
/*     */     }
/*     */     
/*  39 */     if ((paramInt2 < 9) || (paramInt2 > 15)) {
/*  40 */       throw new IllegalArgumentException("windowBits: " + paramInt2 + " (expected: 9-15)");
/*     */     }
/*     */     
/*  43 */     if ((paramInt3 < 1) || (paramInt3 > 9)) {
/*  44 */       throw new IllegalArgumentException("memLevel: " + paramInt3 + " (expected: 1-9)");
/*     */     }
/*     */     
/*     */ 
/*  48 */     int i = this.z.deflateInit(paramInt1, paramInt2, paramInt3, JZlib.W_ZLIB);
/*     */     
/*  50 */     if (i != 0) {
/*  51 */       throw new CompressionException("failed to initialize an SPDY header block deflater: " + i);
/*     */     }
/*     */     
/*  54 */     i = this.z.deflateSetDictionary(SpdyCodecUtil.SPDY_DICT, SpdyCodecUtil.SPDY_DICT.length);
/*  55 */     if (i != 0) {
/*  56 */       throw new CompressionException("failed to set the SPDY dictionary: " + i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setInput(ByteBuf paramByteBuf)
/*     */   {
/*  63 */     byte[] arrayOfByte = new byte[paramByteBuf.readableBytes()];
/*  64 */     paramByteBuf.readBytes(arrayOfByte);
/*  65 */     this.z.next_in = arrayOfByte;
/*  66 */     this.z.next_in_index = 0;
/*  67 */     this.z.avail_in = arrayOfByte.length;
/*     */   }
/*     */   
/*     */   private void encode(ByteBuf paramByteBuf) {
/*     */     try {
/*  72 */       byte[] arrayOfByte = new byte[(int)Math.ceil(this.z.next_in.length * 1.001D) + 12];
/*  73 */       this.z.next_out = arrayOfByte;
/*  74 */       this.z.next_out_index = 0;
/*  75 */       this.z.avail_out = arrayOfByte.length;
/*     */       
/*  77 */       int i = this.z.deflate(2);
/*  78 */       if (i != 0) {
/*  79 */         throw new CompressionException("compression failure: " + i);
/*     */       }
/*     */       
/*  82 */       if (this.z.next_out_index != 0) {
/*  83 */         paramByteBuf.writeBytes(arrayOfByte, 0, this.z.next_out_index);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*  90 */       this.z.next_in = null;
/*  91 */       this.z.next_out = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public ByteBuf encode(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/*  97 */     if (paramSpdyHeadersFrame == null) {
/*  98 */       throw new IllegalArgumentException("frame");
/*     */     }
/*     */     
/* 101 */     if (this.finished) {
/* 102 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 105 */     ByteBuf localByteBuf1 = super.encode(paramSpdyHeadersFrame);
/* 106 */     if (localByteBuf1.readableBytes() == 0) {
/* 107 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/*     */     
/* 110 */     ByteBuf localByteBuf2 = localByteBuf1.alloc().buffer();
/* 111 */     setInput(localByteBuf1);
/* 112 */     encode(localByteBuf2);
/* 113 */     return localByteBuf2;
/*     */   }
/*     */   
/*     */   public void end()
/*     */   {
/* 118 */     if (this.finished) {
/* 119 */       return;
/*     */     }
/* 121 */     this.finished = true;
/* 122 */     this.z.deflateEnd();
/* 123 */     this.z.next_in = null;
/* 124 */     this.z.next_out = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockJZlibEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */