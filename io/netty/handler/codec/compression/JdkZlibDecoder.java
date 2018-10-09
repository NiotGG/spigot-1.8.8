/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.List;
/*     */ import java.util.zip.CRC32;
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
/*     */ 
/*     */ 
/*     */ public class JdkZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*     */   private static final int FHCRC = 2;
/*     */   private static final int FEXTRA = 4;
/*     */   private static final int FNAME = 8;
/*     */   private static final int FCOMMENT = 16;
/*     */   private static final int FRESERVED = 224;
/*     */   private Inflater inflater;
/*     */   private final byte[] dictionary;
/*     */   private final CRC32 crc;
/*     */   
/*     */   private static enum GzipState
/*     */   {
/*  44 */     HEADER_START, 
/*  45 */     HEADER_END, 
/*  46 */     FLG_READ, 
/*  47 */     XLEN_READ, 
/*  48 */     SKIP_FNAME, 
/*  49 */     SKIP_COMMENT, 
/*  50 */     PROCESS_FHCRC, 
/*  51 */     FOOTER_START;
/*     */     
/*     */     private GzipState() {} }
/*  54 */   private GzipState gzipState = GzipState.HEADER_START;
/*  55 */   private int flags = -1;
/*  56 */   private int xlen = -1;
/*     */   
/*     */ 
/*     */   private volatile boolean finished;
/*     */   
/*     */   private boolean decideZlibOrNone;
/*     */   
/*     */ 
/*     */   public JdkZlibDecoder()
/*     */   {
/*  66 */     this(ZlibWrapper.ZLIB, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkZlibDecoder(byte[] paramArrayOfByte)
/*     */   {
/*  75 */     this(ZlibWrapper.ZLIB, paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JdkZlibDecoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/*  84 */     this(paramZlibWrapper, null);
/*     */   }
/*     */   
/*     */   private JdkZlibDecoder(ZlibWrapper paramZlibWrapper, byte[] paramArrayOfByte) {
/*  88 */     if (paramZlibWrapper == null) {
/*  89 */       throw new NullPointerException("wrapper");
/*     */     }
/*  91 */     switch (paramZlibWrapper) {
/*     */     case GZIP: 
/*  93 */       this.inflater = new Inflater(true);
/*  94 */       this.crc = new CRC32();
/*  95 */       break;
/*     */     case NONE: 
/*  97 */       this.inflater = new Inflater(true);
/*  98 */       this.crc = null;
/*  99 */       break;
/*     */     case ZLIB: 
/* 101 */       this.inflater = new Inflater();
/* 102 */       this.crc = null;
/* 103 */       break;
/*     */     
/*     */     case ZLIB_OR_NONE: 
/* 106 */       this.decideZlibOrNone = true;
/* 107 */       this.crc = null;
/* 108 */       break;
/*     */     default: 
/* 110 */       throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + paramZlibWrapper);
/*     */     }
/* 112 */     this.dictionary = paramArrayOfByte;
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/* 117 */     return this.finished;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/* 122 */     if (this.finished)
/*     */     {
/* 124 */       paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/* 125 */       return;
/*     */     }
/*     */     
/* 128 */     if (!paramByteBuf.isReadable()) {
/* 129 */       return;
/*     */     }
/*     */     
/* 132 */     if (this.decideZlibOrNone)
/*     */     {
/* 134 */       if (paramByteBuf.readableBytes() < 2) {
/* 135 */         return;
/*     */       }
/*     */       
/* 138 */       boolean bool = !looksLikeZlib(paramByteBuf.getShort(0));
/* 139 */       this.inflater = new Inflater(bool);
/* 140 */       this.decideZlibOrNone = false;
/*     */     }
/*     */     
/* 143 */     if (this.crc != null) {
/* 144 */       switch (this.gzipState) {
/*     */       case FOOTER_START: 
/* 146 */         if (readGZIPFooter(paramByteBuf)) {
/* 147 */           this.finished = true;
/*     */         }
/* 149 */         return;
/*     */       }
/* 151 */       if ((this.gzipState != GzipState.HEADER_END) && 
/* 152 */         (!readGZIPHeader(paramByteBuf))) {
/* 153 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 159 */     int i = paramByteBuf.readableBytes();
/* 160 */     if (paramByteBuf.hasArray()) {
/* 161 */       this.inflater.setInput(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramByteBuf.readerIndex(), paramByteBuf.readableBytes());
/*     */     } else {
/* 163 */       byte[] arrayOfByte1 = new byte[paramByteBuf.readableBytes()];
/* 164 */       paramByteBuf.getBytes(paramByteBuf.readerIndex(), arrayOfByte1);
/* 165 */       this.inflater.setInput(arrayOfByte1);
/*     */     }
/*     */     
/* 168 */     int j = this.inflater.getRemaining() << 1;
/* 169 */     ByteBuf localByteBuf = paramChannelHandlerContext.alloc().heapBuffer(j);
/*     */     try {
/* 171 */       int k = 0;
/* 172 */       byte[] arrayOfByte2 = localByteBuf.array();
/* 173 */       while (!this.inflater.needsInput()) {
/* 174 */         int m = localByteBuf.writerIndex();
/* 175 */         int n = localByteBuf.arrayOffset() + m;
/* 176 */         int i1 = localByteBuf.writableBytes();
/*     */         
/* 178 */         if (i1 == 0)
/*     */         {
/* 180 */           paramList.add(localByteBuf);
/* 181 */           localByteBuf = paramChannelHandlerContext.alloc().heapBuffer(j);
/* 182 */           arrayOfByte2 = localByteBuf.array();
/*     */         }
/*     */         else
/*     */         {
/* 186 */           int i2 = this.inflater.inflate(arrayOfByte2, n, i1);
/* 187 */           if (i2 > 0) {
/* 188 */             localByteBuf.writerIndex(m + i2);
/* 189 */             if (this.crc != null) {
/* 190 */               this.crc.update(arrayOfByte2, n, i2);
/*     */             }
/*     */           }
/* 193 */           else if (this.inflater.needsDictionary()) {
/* 194 */             if (this.dictionary == null) {
/* 195 */               throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
/*     */             }
/*     */             
/* 198 */             this.inflater.setDictionary(this.dictionary);
/*     */           }
/*     */           
/*     */ 
/* 202 */           if (this.inflater.finished()) {
/* 203 */             if (this.crc == null) {
/* 204 */               this.finished = true; break;
/*     */             }
/* 206 */             k = 1;
/*     */             
/* 208 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 212 */       paramByteBuf.skipBytes(i - this.inflater.getRemaining());
/*     */       
/* 214 */       if (k != 0) {
/* 215 */         this.gzipState = GzipState.FOOTER_START;
/* 216 */         if (readGZIPFooter(paramByteBuf)) {
/* 217 */           this.finished = true;
/*     */         }
/*     */       }
/*     */     } catch (DataFormatException localDataFormatException) {
/* 221 */       throw new DecompressionException("decompression failure", localDataFormatException);
/*     */     }
/*     */     finally {
/* 224 */       if (localByteBuf.isReadable()) {
/* 225 */         paramList.add(localByteBuf);
/*     */       } else {
/* 227 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void handlerRemoved0(ChannelHandlerContext paramChannelHandlerContext) throws Exception
/*     */   {
/* 234 */     super.handlerRemoved0(paramChannelHandlerContext);
/* 235 */     if (this.inflater != null)
/* 236 */       this.inflater.end();
/*     */   }
/*     */   
/*     */   private boolean readGZIPHeader(ByteBuf paramByteBuf) {
/*     */     int n;
/* 241 */     switch (this.gzipState) {
/*     */     case HEADER_START: 
/* 243 */       if (paramByteBuf.readableBytes() < 10) {
/* 244 */         return false;
/*     */       }
/*     */       
/* 247 */       int i = paramByteBuf.readByte();
/* 248 */       int j = paramByteBuf.readByte();
/*     */       
/* 250 */       if (i != 31) {
/* 251 */         throw new DecompressionException("Input is not in the GZIP format");
/*     */       }
/* 253 */       this.crc.update(i);
/* 254 */       this.crc.update(j);
/*     */       
/* 256 */       int k = paramByteBuf.readUnsignedByte();
/* 257 */       if (k != 8) {
/* 258 */         throw new DecompressionException("Unsupported compression method " + k + " in the GZIP header");
/*     */       }
/*     */       
/* 261 */       this.crc.update(k);
/*     */       
/* 263 */       this.flags = paramByteBuf.readUnsignedByte();
/* 264 */       this.crc.update(this.flags);
/*     */       
/* 266 */       if ((this.flags & 0xE0) != 0) {
/* 267 */         throw new DecompressionException("Reserved flags are set in the GZIP header");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 272 */       this.crc.update(paramByteBuf.readByte());
/* 273 */       this.crc.update(paramByteBuf.readByte());
/* 274 */       this.crc.update(paramByteBuf.readByte());
/* 275 */       this.crc.update(paramByteBuf.readByte());
/*     */       
/* 277 */       this.crc.update(paramByteBuf.readUnsignedByte());
/* 278 */       this.crc.update(paramByteBuf.readUnsignedByte());
/*     */       
/* 280 */       this.gzipState = GzipState.FLG_READ;
/*     */     case FLG_READ: 
/* 282 */       if ((this.flags & 0x4) != 0) {
/* 283 */         if (paramByteBuf.readableBytes() < 2) {
/* 284 */           return false;
/*     */         }
/* 286 */         int m = paramByteBuf.readUnsignedByte();
/* 287 */         int i1 = paramByteBuf.readUnsignedByte();
/* 288 */         this.crc.update(m);
/* 289 */         this.crc.update(i1);
/*     */         
/* 291 */         this.xlen |= m << 8 | i1;
/*     */       }
/* 293 */       this.gzipState = GzipState.XLEN_READ;
/*     */     case XLEN_READ: 
/* 295 */       if (this.xlen != -1) {
/* 296 */         if (paramByteBuf.readableBytes() < this.xlen) {
/* 297 */           return false;
/*     */         }
/* 299 */         byte[] arrayOfByte = new byte[this.xlen];
/* 300 */         paramByteBuf.readBytes(arrayOfByte);
/* 301 */         this.crc.update(arrayOfByte);
/*     */       }
/* 303 */       this.gzipState = GzipState.SKIP_FNAME;
/*     */     case SKIP_FNAME: 
/* 305 */       if ((this.flags & 0x8) != 0) {
/* 306 */         if (!paramByteBuf.isReadable()) {
/* 307 */           return false;
/*     */         }
/*     */         do {
/* 310 */           n = paramByteBuf.readUnsignedByte();
/* 311 */           this.crc.update(n);
/* 312 */         } while ((n != 0) && 
/*     */         
/*     */ 
/* 315 */           (paramByteBuf.isReadable()));
/*     */       }
/* 317 */       this.gzipState = GzipState.SKIP_COMMENT;
/*     */     case SKIP_COMMENT: 
/* 319 */       if ((this.flags & 0x10) != 0) {
/* 320 */         if (!paramByteBuf.isReadable()) {
/* 321 */           return false;
/*     */         }
/*     */         do {
/* 324 */           n = paramByteBuf.readUnsignedByte();
/* 325 */           this.crc.update(n);
/* 326 */         } while ((n != 0) && 
/*     */         
/*     */ 
/* 329 */           (paramByteBuf.isReadable()));
/*     */       }
/* 331 */       this.gzipState = GzipState.PROCESS_FHCRC;
/*     */     case PROCESS_FHCRC: 
/* 333 */       if ((this.flags & 0x2) != 0) {
/* 334 */         if (paramByteBuf.readableBytes() < 4) {
/* 335 */           return false;
/*     */         }
/* 337 */         verifyCrc(paramByteBuf);
/*     */       }
/* 339 */       this.crc.reset();
/* 340 */       this.gzipState = GzipState.HEADER_END;
/*     */     case HEADER_END: 
/* 342 */       return true;
/*     */     }
/* 344 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   private boolean readGZIPFooter(ByteBuf paramByteBuf)
/*     */   {
/* 349 */     if (paramByteBuf.readableBytes() < 8) {
/* 350 */       return false;
/*     */     }
/*     */     
/* 353 */     verifyCrc(paramByteBuf);
/*     */     
/*     */ 
/* 356 */     int i = 0;
/* 357 */     for (int j = 0; j < 4; j++) {
/* 358 */       i |= paramByteBuf.readUnsignedByte() << j * 8;
/*     */     }
/* 360 */     j = this.inflater.getTotalOut();
/* 361 */     if (i != j) {
/* 362 */       throw new DecompressionException("Number of bytes mismatch. Expected: " + i + ", Got: " + j);
/*     */     }
/*     */     
/* 365 */     return true;
/*     */   }
/*     */   
/*     */   private void verifyCrc(ByteBuf paramByteBuf) {
/* 369 */     long l1 = 0L;
/* 370 */     for (int i = 0; i < 4; i++) {
/* 371 */       l1 |= paramByteBuf.readUnsignedByte() << i * 8;
/*     */     }
/* 373 */     long l2 = this.crc.getValue();
/* 374 */     if (l1 != l2) {
/* 375 */       throw new DecompressionException("CRC value missmatch. Expected: " + l1 + ", Got: " + l2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean looksLikeZlib(short paramShort)
/*     */   {
/* 388 */     return ((paramShort & 0x7800) == 30720) && (paramShort % 31 == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\JdkZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */