/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
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
/*     */ public class SpdyHeaderBlockRawDecoder
/*     */   extends SpdyHeaderBlockDecoder
/*     */ {
/*     */   private static final int LENGTH_FIELD_SIZE = 4;
/*     */   private final int maxHeaderSize;
/*     */   private State state;
/*     */   private ByteBuf cumulation;
/*     */   private int headerSize;
/*     */   private int numHeaders;
/*     */   private int length;
/*     */   private String name;
/*     */   
/*     */   private static enum State
/*     */   {
/*  38 */     READ_NUM_HEADERS, 
/*  39 */     READ_NAME_LENGTH, 
/*  40 */     READ_NAME, 
/*  41 */     SKIP_NAME, 
/*  42 */     READ_VALUE_LENGTH, 
/*  43 */     READ_VALUE, 
/*  44 */     SKIP_VALUE, 
/*  45 */     END_HEADER_BLOCK, 
/*  46 */     ERROR;
/*     */     
/*     */     private State() {} }
/*     */   
/*  50 */   public SpdyHeaderBlockRawDecoder(SpdyVersion paramSpdyVersion, int paramInt) { if (paramSpdyVersion == null) {
/*  51 */       throw new NullPointerException("spdyVersion");
/*     */     }
/*  53 */     this.maxHeaderSize = paramInt;
/*  54 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */   
/*     */   private static int readLengthField(ByteBuf paramByteBuf) {
/*  58 */     int i = SpdyCodecUtil.getSignedInt(paramByteBuf, paramByteBuf.readerIndex());
/*  59 */     paramByteBuf.skipBytes(4);
/*  60 */     return i;
/*     */   }
/*     */   
/*     */   void decode(ByteBuf paramByteBuf, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/*  65 */     if (paramByteBuf == null) {
/*  66 */       throw new NullPointerException("headerBlock");
/*     */     }
/*  68 */     if (paramSpdyHeadersFrame == null) {
/*  69 */       throw new NullPointerException("frame");
/*     */     }
/*     */     
/*  72 */     if (this.cumulation == null) {
/*  73 */       decodeHeaderBlock(paramByteBuf, paramSpdyHeadersFrame);
/*  74 */       if (paramByteBuf.isReadable()) {
/*  75 */         this.cumulation = paramByteBuf.alloc().buffer(paramByteBuf.readableBytes());
/*  76 */         this.cumulation.writeBytes(paramByteBuf);
/*     */       }
/*     */     } else {
/*  79 */       this.cumulation.writeBytes(paramByteBuf);
/*  80 */       decodeHeaderBlock(this.cumulation, paramSpdyHeadersFrame);
/*  81 */       if (this.cumulation.isReadable()) {
/*  82 */         this.cumulation.discardReadBytes();
/*     */       } else {
/*  84 */         releaseBuffer();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void decodeHeaderBlock(ByteBuf paramByteBuf, SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/*  91 */     while (paramByteBuf.isReadable()) { int i;
/*  92 */       switch (this.state) {
/*     */       case READ_NUM_HEADERS: 
/*  94 */         if (paramByteBuf.readableBytes() < 4) {
/*  95 */           return;
/*     */         }
/*     */         
/*  98 */         this.numHeaders = readLengthField(paramByteBuf);
/*     */         
/* 100 */         if (this.numHeaders < 0) {
/* 101 */           this.state = State.ERROR;
/* 102 */           paramSpdyHeadersFrame.setInvalid();
/* 103 */         } else if (this.numHeaders == 0) {
/* 104 */           this.state = State.END_HEADER_BLOCK;
/*     */         } else {
/* 106 */           this.state = State.READ_NAME_LENGTH;
/*     */         }
/* 108 */         break;
/*     */       
/*     */       case READ_NAME_LENGTH: 
/* 111 */         if (paramByteBuf.readableBytes() < 4) {
/* 112 */           return;
/*     */         }
/*     */         
/* 115 */         this.length = readLengthField(paramByteBuf);
/*     */         
/*     */ 
/* 118 */         if (this.length <= 0) {
/* 119 */           this.state = State.ERROR;
/* 120 */           paramSpdyHeadersFrame.setInvalid();
/* 121 */         } else if ((this.length > this.maxHeaderSize) || (this.headerSize > this.maxHeaderSize - this.length)) {
/* 122 */           this.headerSize = (this.maxHeaderSize + 1);
/* 123 */           this.state = State.SKIP_NAME;
/* 124 */           paramSpdyHeadersFrame.setTruncated();
/*     */         } else {
/* 126 */           this.headerSize += this.length;
/* 127 */           this.state = State.READ_NAME;
/*     */         }
/* 129 */         break;
/*     */       
/*     */       case READ_NAME: 
/* 132 */         if (paramByteBuf.readableBytes() < this.length) {
/* 133 */           return;
/*     */         }
/*     */         
/* 136 */         byte[] arrayOfByte1 = new byte[this.length];
/* 137 */         paramByteBuf.readBytes(arrayOfByte1);
/* 138 */         this.name = new String(arrayOfByte1, "UTF-8");
/*     */         
/*     */ 
/* 141 */         if (paramSpdyHeadersFrame.headers().contains(this.name)) {
/* 142 */           this.state = State.ERROR;
/* 143 */           paramSpdyHeadersFrame.setInvalid();
/*     */         } else {
/* 145 */           this.state = State.READ_VALUE_LENGTH;
/*     */         }
/* 147 */         break;
/*     */       
/*     */       case SKIP_NAME: 
/* 150 */         i = Math.min(paramByteBuf.readableBytes(), this.length);
/* 151 */         paramByteBuf.skipBytes(i);
/* 152 */         this.length -= i;
/*     */         
/* 154 */         if (this.length == 0) {
/* 155 */           this.state = State.READ_VALUE_LENGTH;
/*     */         }
/*     */         
/*     */         break;
/*     */       case READ_VALUE_LENGTH: 
/* 160 */         if (paramByteBuf.readableBytes() < 4) {
/* 161 */           return;
/*     */         }
/*     */         
/* 164 */         this.length = readLengthField(paramByteBuf);
/*     */         
/*     */ 
/* 167 */         if (this.length < 0) {
/* 168 */           this.state = State.ERROR;
/* 169 */           paramSpdyHeadersFrame.setInvalid();
/* 170 */         } else if (this.length == 0) {
/* 171 */           if (!paramSpdyHeadersFrame.isTruncated())
/*     */           {
/* 173 */             paramSpdyHeadersFrame.headers().add(this.name, "");
/*     */           }
/*     */           
/* 176 */           this.name = null;
/* 177 */           if (--this.numHeaders == 0) {
/* 178 */             this.state = State.END_HEADER_BLOCK;
/*     */           } else {
/* 180 */             this.state = State.READ_NAME_LENGTH;
/*     */           }
/*     */         }
/* 183 */         else if ((this.length > this.maxHeaderSize) || (this.headerSize > this.maxHeaderSize - this.length)) {
/* 184 */           this.headerSize = (this.maxHeaderSize + 1);
/* 185 */           this.name = null;
/* 186 */           this.state = State.SKIP_VALUE;
/* 187 */           paramSpdyHeadersFrame.setTruncated();
/*     */         } else {
/* 189 */           this.headerSize += this.length;
/* 190 */           this.state = State.READ_VALUE;
/*     */         }
/* 192 */         break;
/*     */       
/*     */       case READ_VALUE: 
/* 195 */         if (paramByteBuf.readableBytes() < this.length) {
/* 196 */           return;
/*     */         }
/*     */         
/* 199 */         byte[] arrayOfByte2 = new byte[this.length];
/* 200 */         paramByteBuf.readBytes(arrayOfByte2);
/*     */         
/*     */ 
/* 203 */         int j = 0;
/* 204 */         int k = 0;
/*     */         
/*     */ 
/* 207 */         if (arrayOfByte2[0] == 0) {
/* 208 */           this.state = State.ERROR;
/* 209 */           paramSpdyHeadersFrame.setInvalid();
/*     */         }
/*     */         else
/*     */         {
/* 213 */           while (j < this.length) {
/* 214 */             while ((j < arrayOfByte2.length) && (arrayOfByte2[j] != 0)) {
/* 215 */               j++;
/*     */             }
/* 217 */             if (j < arrayOfByte2.length)
/*     */             {
/* 219 */               if ((j + 1 == arrayOfByte2.length) || (arrayOfByte2[(j + 1)] == 0))
/*     */               {
/*     */ 
/*     */ 
/* 223 */                 this.state = State.ERROR;
/* 224 */                 paramSpdyHeadersFrame.setInvalid();
/* 225 */                 break;
/*     */               }
/*     */             }
/* 228 */             String str = new String(arrayOfByte2, k, j - k, "UTF-8");
/*     */             try
/*     */             {
/* 231 */               paramSpdyHeadersFrame.headers().add(this.name, str);
/*     */             }
/*     */             catch (IllegalArgumentException localIllegalArgumentException) {
/* 234 */               this.state = State.ERROR;
/* 235 */               paramSpdyHeadersFrame.setInvalid();
/* 236 */               break;
/*     */             }
/* 238 */             j++;
/* 239 */             k = j;
/*     */           }
/*     */           
/* 242 */           this.name = null;
/*     */           
/*     */ 
/* 245 */           if (this.state != State.ERROR)
/*     */           {
/*     */ 
/*     */ 
/* 249 */             if (--this.numHeaders == 0) {
/* 250 */               this.state = State.END_HEADER_BLOCK;
/*     */             } else
/* 252 */               this.state = State.READ_NAME_LENGTH; }
/*     */         }
/* 254 */         break;
/*     */       
/*     */       case SKIP_VALUE: 
/* 257 */         i = Math.min(paramByteBuf.readableBytes(), this.length);
/* 258 */         paramByteBuf.skipBytes(i);
/* 259 */         this.length -= i;
/*     */         
/* 261 */         if (this.length == 0) {
/* 262 */           if (--this.numHeaders == 0) {
/* 263 */             this.state = State.END_HEADER_BLOCK;
/*     */           } else {
/* 265 */             this.state = State.READ_NAME_LENGTH;
/*     */           }
/*     */         }
/*     */         
/*     */         break;
/*     */       case END_HEADER_BLOCK: 
/* 271 */         this.state = State.ERROR;
/* 272 */         paramSpdyHeadersFrame.setInvalid();
/* 273 */         break;
/*     */       
/*     */       case ERROR: 
/* 276 */         paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/* 277 */         return;
/*     */       
/*     */       default: 
/* 280 */         throw new Error("Shouldn't reach here.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void endHeaderBlock(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*     */   {
/* 287 */     if (this.state != State.END_HEADER_BLOCK) {
/* 288 */       paramSpdyHeadersFrame.setInvalid();
/*     */     }
/*     */     
/* 291 */     releaseBuffer();
/*     */     
/*     */ 
/* 294 */     this.headerSize = 0;
/* 295 */     this.name = null;
/* 296 */     this.state = State.READ_NUM_HEADERS;
/*     */   }
/*     */   
/*     */   void end()
/*     */   {
/* 301 */     releaseBuffer();
/*     */   }
/*     */   
/*     */   private void releaseBuffer() {
/* 305 */     if (this.cumulation != null) {
/* 306 */       this.cumulation.release();
/* 307 */       this.cumulation = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockRawDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */