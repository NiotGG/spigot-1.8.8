/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.Unpooled;
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
/*     */ public class SpdyFrameDecoder
/*     */ {
/*     */   private final int spdyVersion;
/*     */   private final int maxChunkSize;
/*     */   private final SpdyFrameDecoderDelegate delegate;
/*     */   private State state;
/*     */   private byte flags;
/*     */   private int length;
/*     */   private int streamId;
/*     */   private int numSettings;
/*     */   
/*     */   private static enum State
/*     */   {
/*  64 */     READ_COMMON_HEADER, 
/*  65 */     READ_DATA_FRAME, 
/*  66 */     READ_SYN_STREAM_FRAME, 
/*  67 */     READ_SYN_REPLY_FRAME, 
/*  68 */     READ_RST_STREAM_FRAME, 
/*  69 */     READ_SETTINGS_FRAME, 
/*  70 */     READ_SETTING, 
/*  71 */     READ_PING_FRAME, 
/*  72 */     READ_GOAWAY_FRAME, 
/*  73 */     READ_HEADERS_FRAME, 
/*  74 */     READ_WINDOW_UPDATE_FRAME, 
/*  75 */     READ_HEADER_BLOCK, 
/*  76 */     DISCARD_FRAME, 
/*  77 */     FRAME_ERROR;
/*     */     
/*     */ 
/*     */     private State() {}
/*     */   }
/*     */   
/*     */   public SpdyFrameDecoder(SpdyVersion paramSpdyVersion, SpdyFrameDecoderDelegate paramSpdyFrameDecoderDelegate)
/*     */   {
/*  85 */     this(paramSpdyVersion, paramSpdyFrameDecoderDelegate, 8192);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SpdyFrameDecoder(SpdyVersion paramSpdyVersion, SpdyFrameDecoderDelegate paramSpdyFrameDecoderDelegate, int paramInt)
/*     */   {
/*  92 */     if (paramSpdyVersion == null) {
/*  93 */       throw new NullPointerException("spdyVersion");
/*     */     }
/*  95 */     if (paramSpdyFrameDecoderDelegate == null) {
/*  96 */       throw new NullPointerException("delegate");
/*     */     }
/*  98 */     if (paramInt <= 0) {
/*  99 */       throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + paramInt);
/*     */     }
/*     */     
/* 102 */     this.spdyVersion = paramSpdyVersion.getVersion();
/* 103 */     this.delegate = paramSpdyFrameDecoderDelegate;
/* 104 */     this.maxChunkSize = paramInt;
/* 105 */     this.state = State.READ_COMMON_HEADER;
/*     */   }
/*     */   
/*     */   public void decode(ByteBuf paramByteBuf)
/*     */   {
/*     */     for (;;) {
/*     */       boolean bool1;
/*     */       int i5;
/* 113 */       switch (this.state) {
/*     */       case READ_COMMON_HEADER: 
/* 115 */         if (paramByteBuf.readableBytes() < 8) {
/* 116 */           return;
/*     */         }
/*     */         
/* 119 */         int i = paramByteBuf.readerIndex();
/* 120 */         int j = i + 4;
/* 121 */         int k = i + 5;
/* 122 */         paramByteBuf.skipBytes(8);
/*     */         
/* 124 */         int m = (paramByteBuf.getByte(i) & 0x80) != 0 ? 1 : 0;
/*     */         
/*     */         int n;
/*     */         int i1;
/* 128 */         if (m != 0)
/*     */         {
/* 130 */           n = SpdyCodecUtil.getUnsignedShort(paramByteBuf, i) & 0x7FFF;
/* 131 */           i1 = SpdyCodecUtil.getUnsignedShort(paramByteBuf, i + 2);
/* 132 */           this.streamId = 0;
/*     */         }
/*     */         else {
/* 135 */           n = this.spdyVersion;
/* 136 */           i1 = 0;
/* 137 */           this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, i);
/*     */         }
/*     */         
/* 140 */         this.flags = paramByteBuf.getByte(j);
/* 141 */         this.length = SpdyCodecUtil.getUnsignedMedium(paramByteBuf, k);
/*     */         
/*     */ 
/* 144 */         if (n != this.spdyVersion) {
/* 145 */           this.state = State.FRAME_ERROR;
/* 146 */           this.delegate.readFrameError("Invalid SPDY Version");
/* 147 */         } else if (!isValidFrameHeader(this.streamId, i1, this.flags, this.length)) {
/* 148 */           this.state = State.FRAME_ERROR;
/* 149 */           this.delegate.readFrameError("Invalid Frame Error");
/*     */         } else {
/* 151 */           this.state = getNextState(i1, this.length);
/*     */         }
/* 153 */         break;
/*     */       
/*     */       case READ_DATA_FRAME: 
/* 156 */         if (this.length == 0) {
/* 157 */           this.state = State.READ_COMMON_HEADER;
/* 158 */           this.delegate.readDataFrame(this.streamId, hasFlag(this.flags, (byte)1), Unpooled.buffer(0));
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 163 */           int i2 = Math.min(this.maxChunkSize, this.length);
/*     */           
/*     */ 
/* 166 */           if (paramByteBuf.readableBytes() < i2) {
/* 167 */             return;
/*     */           }
/*     */           
/* 170 */           ByteBuf localByteBuf1 = paramByteBuf.alloc().buffer(i2);
/* 171 */           localByteBuf1.writeBytes(paramByteBuf, i2);
/* 172 */           this.length -= i2;
/*     */           
/* 174 */           if (this.length == 0) {
/* 175 */             this.state = State.READ_COMMON_HEADER;
/*     */           }
/*     */           
/* 178 */           bool1 = (this.length == 0) && (hasFlag(this.flags, (byte)1));
/*     */           
/* 180 */           this.delegate.readDataFrame(this.streamId, bool1, localByteBuf1); }
/* 181 */         break;
/*     */       
/*     */       case READ_SYN_STREAM_FRAME: 
/* 184 */         if (paramByteBuf.readableBytes() < 10) {
/* 185 */           return;
/*     */         }
/*     */         
/* 188 */         int i3 = paramByteBuf.readerIndex();
/* 189 */         this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, i3);
/* 190 */         int i4 = SpdyCodecUtil.getUnsignedInt(paramByteBuf, i3 + 4);
/* 191 */         byte b1 = (byte)(paramByteBuf.getByte(i3 + 8) >> 5 & 0x7);
/* 192 */         bool1 = hasFlag(this.flags, (byte)1);
/* 193 */         boolean bool2 = hasFlag(this.flags, (byte)2);
/* 194 */         paramByteBuf.skipBytes(10);
/* 195 */         this.length -= 10;
/*     */         
/* 197 */         if (this.streamId == 0) {
/* 198 */           this.state = State.FRAME_ERROR;
/* 199 */           this.delegate.readFrameError("Invalid SYN_STREAM Frame");
/*     */         } else {
/* 201 */           this.state = State.READ_HEADER_BLOCK;
/* 202 */           this.delegate.readSynStreamFrame(this.streamId, i4, b1, bool1, bool2);
/*     */         }
/* 204 */         break;
/*     */       
/*     */       case READ_SYN_REPLY_FRAME: 
/* 207 */         if (paramByteBuf.readableBytes() < 4) {
/* 208 */           return;
/*     */         }
/*     */         
/* 211 */         this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 212 */         bool1 = hasFlag(this.flags, (byte)1);
/*     */         
/* 214 */         paramByteBuf.skipBytes(4);
/* 215 */         this.length -= 4;
/*     */         
/* 217 */         if (this.streamId == 0) {
/* 218 */           this.state = State.FRAME_ERROR;
/* 219 */           this.delegate.readFrameError("Invalid SYN_REPLY Frame");
/*     */         } else {
/* 221 */           this.state = State.READ_HEADER_BLOCK;
/* 222 */           this.delegate.readSynReplyFrame(this.streamId, bool1);
/*     */         }
/* 224 */         break;
/*     */       
/*     */       case READ_RST_STREAM_FRAME: 
/* 227 */         if (paramByteBuf.readableBytes() < 8) {
/* 228 */           return;
/*     */         }
/*     */         
/* 231 */         this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 232 */         i5 = SpdyCodecUtil.getSignedInt(paramByteBuf, paramByteBuf.readerIndex() + 4);
/* 233 */         paramByteBuf.skipBytes(8);
/*     */         
/* 235 */         if ((this.streamId == 0) || (i5 == 0)) {
/* 236 */           this.state = State.FRAME_ERROR;
/* 237 */           this.delegate.readFrameError("Invalid RST_STREAM Frame");
/*     */         } else {
/* 239 */           this.state = State.READ_COMMON_HEADER;
/* 240 */           this.delegate.readRstStreamFrame(this.streamId, i5);
/*     */         }
/* 242 */         break;
/*     */       
/*     */       case READ_SETTINGS_FRAME: 
/* 245 */         if (paramByteBuf.readableBytes() < 4) {
/* 246 */           return;
/*     */         }
/*     */         
/* 249 */         boolean bool3 = hasFlag(this.flags, (byte)1);
/*     */         
/* 251 */         this.numSettings = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 252 */         paramByteBuf.skipBytes(4);
/* 253 */         this.length -= 4;
/*     */         
/*     */ 
/* 256 */         if (((this.length & 0x7) != 0) || (this.length >> 3 != this.numSettings)) {
/* 257 */           this.state = State.FRAME_ERROR;
/* 258 */           this.delegate.readFrameError("Invalid SETTINGS Frame");
/*     */         } else {
/* 260 */           this.state = State.READ_SETTING;
/* 261 */           this.delegate.readSettingsFrame(bool3);
/*     */         }
/* 263 */         break;
/*     */       
/*     */       case READ_SETTING: 
/* 266 */         if (this.numSettings == 0) {
/* 267 */           this.state = State.READ_COMMON_HEADER;
/* 268 */           this.delegate.readSettingsEnd();
/*     */         }
/*     */         else
/*     */         {
/* 272 */           if (paramByteBuf.readableBytes() < 8) {
/* 273 */             return;
/*     */           }
/*     */           
/* 276 */           byte b2 = paramByteBuf.getByte(paramByteBuf.readerIndex());
/* 277 */           int i6 = SpdyCodecUtil.getUnsignedMedium(paramByteBuf, paramByteBuf.readerIndex() + 1);
/* 278 */           int i7 = SpdyCodecUtil.getSignedInt(paramByteBuf, paramByteBuf.readerIndex() + 4);
/* 279 */           boolean bool4 = hasFlag(b2, (byte)1);
/* 280 */           boolean bool5 = hasFlag(b2, (byte)2);
/* 281 */           paramByteBuf.skipBytes(8);
/*     */           
/* 283 */           this.numSettings -= 1;
/*     */           
/* 285 */           this.delegate.readSetting(i6, i7, bool4, bool5); }
/* 286 */         break;
/*     */       
/*     */       case READ_PING_FRAME: 
/* 289 */         if (paramByteBuf.readableBytes() < 4) {
/* 290 */           return;
/*     */         }
/*     */         
/* 293 */         int i8 = SpdyCodecUtil.getSignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 294 */         paramByteBuf.skipBytes(4);
/*     */         
/* 296 */         this.state = State.READ_COMMON_HEADER;
/* 297 */         this.delegate.readPingFrame(i8);
/* 298 */         break;
/*     */       
/*     */       case READ_GOAWAY_FRAME: 
/* 301 */         if (paramByteBuf.readableBytes() < 8) {
/* 302 */           return;
/*     */         }
/*     */         
/* 305 */         int i9 = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 306 */         i5 = SpdyCodecUtil.getSignedInt(paramByteBuf, paramByteBuf.readerIndex() + 4);
/* 307 */         paramByteBuf.skipBytes(8);
/*     */         
/* 309 */         this.state = State.READ_COMMON_HEADER;
/* 310 */         this.delegate.readGoAwayFrame(i9, i5);
/* 311 */         break;
/*     */       
/*     */       case READ_HEADERS_FRAME: 
/* 314 */         if (paramByteBuf.readableBytes() < 4) {
/* 315 */           return;
/*     */         }
/*     */         
/* 318 */         this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 319 */         bool1 = hasFlag(this.flags, (byte)1);
/*     */         
/* 321 */         paramByteBuf.skipBytes(4);
/* 322 */         this.length -= 4;
/*     */         
/* 324 */         if (this.streamId == 0) {
/* 325 */           this.state = State.FRAME_ERROR;
/* 326 */           this.delegate.readFrameError("Invalid HEADERS Frame");
/*     */         } else {
/* 328 */           this.state = State.READ_HEADER_BLOCK;
/* 329 */           this.delegate.readHeadersFrame(this.streamId, bool1);
/*     */         }
/* 331 */         break;
/*     */       
/*     */       case READ_WINDOW_UPDATE_FRAME: 
/* 334 */         if (paramByteBuf.readableBytes() < 8) {
/* 335 */           return;
/*     */         }
/*     */         
/* 338 */         this.streamId = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex());
/* 339 */         int i10 = SpdyCodecUtil.getUnsignedInt(paramByteBuf, paramByteBuf.readerIndex() + 4);
/* 340 */         paramByteBuf.skipBytes(8);
/*     */         
/* 342 */         if (i10 == 0) {
/* 343 */           this.state = State.FRAME_ERROR;
/* 344 */           this.delegate.readFrameError("Invalid WINDOW_UPDATE Frame");
/*     */         } else {
/* 346 */           this.state = State.READ_COMMON_HEADER;
/* 347 */           this.delegate.readWindowUpdateFrame(this.streamId, i10);
/*     */         }
/* 349 */         break;
/*     */       
/*     */       case READ_HEADER_BLOCK: 
/* 352 */         if (this.length == 0) {
/* 353 */           this.state = State.READ_COMMON_HEADER;
/* 354 */           this.delegate.readHeaderBlockEnd();
/*     */         }
/*     */         else
/*     */         {
/* 358 */           if (!paramByteBuf.isReadable()) {
/* 359 */             return;
/*     */           }
/*     */           
/* 362 */           int i11 = Math.min(paramByteBuf.readableBytes(), this.length);
/* 363 */           ByteBuf localByteBuf2 = paramByteBuf.alloc().buffer(i11);
/* 364 */           localByteBuf2.writeBytes(paramByteBuf, i11);
/* 365 */           this.length -= i11;
/*     */           
/* 367 */           this.delegate.readHeaderBlock(localByteBuf2); }
/* 368 */         break;
/*     */       
/*     */       case DISCARD_FRAME: 
/* 371 */         int i12 = Math.min(paramByteBuf.readableBytes(), this.length);
/* 372 */         paramByteBuf.skipBytes(i12);
/* 373 */         this.length -= i12;
/* 374 */         if (this.length == 0) {
/* 375 */           this.state = State.READ_COMMON_HEADER;
/*     */         }
/*     */         else
/* 378 */           return;
/*     */         break;
/*     */       case FRAME_ERROR: 
/* 381 */         paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/* 382 */         return;
/*     */       
/*     */       default: 
/* 385 */         throw new Error("Shouldn't reach here.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean hasFlag(byte paramByte1, byte paramByte2) {
/* 391 */     return (paramByte1 & paramByte2) != 0;
/*     */   }
/*     */   
/*     */   private static State getNextState(int paramInt1, int paramInt2) {
/* 395 */     switch (paramInt1) {
/*     */     case 0: 
/* 397 */       return State.READ_DATA_FRAME;
/*     */     
/*     */     case 1: 
/* 400 */       return State.READ_SYN_STREAM_FRAME;
/*     */     
/*     */     case 2: 
/* 403 */       return State.READ_SYN_REPLY_FRAME;
/*     */     
/*     */     case 3: 
/* 406 */       return State.READ_RST_STREAM_FRAME;
/*     */     
/*     */     case 4: 
/* 409 */       return State.READ_SETTINGS_FRAME;
/*     */     
/*     */     case 6: 
/* 412 */       return State.READ_PING_FRAME;
/*     */     
/*     */     case 7: 
/* 415 */       return State.READ_GOAWAY_FRAME;
/*     */     
/*     */     case 8: 
/* 418 */       return State.READ_HEADERS_FRAME;
/*     */     
/*     */     case 9: 
/* 421 */       return State.READ_WINDOW_UPDATE_FRAME;
/*     */     }
/*     */     
/* 424 */     if (paramInt2 != 0) {
/* 425 */       return State.DISCARD_FRAME;
/*     */     }
/* 427 */     return State.READ_COMMON_HEADER;
/*     */   }
/*     */   
/*     */ 
/*     */   private static boolean isValidFrameHeader(int paramInt1, int paramInt2, byte paramByte, int paramInt3)
/*     */   {
/* 433 */     switch (paramInt2) {
/*     */     case 0: 
/* 435 */       return paramInt1 != 0;
/*     */     
/*     */     case 1: 
/* 438 */       return paramInt3 >= 10;
/*     */     
/*     */     case 2: 
/* 441 */       return paramInt3 >= 4;
/*     */     
/*     */     case 3: 
/* 444 */       return (paramByte == 0) && (paramInt3 == 8);
/*     */     
/*     */     case 4: 
/* 447 */       return paramInt3 >= 4;
/*     */     
/*     */     case 6: 
/* 450 */       return paramInt3 == 4;
/*     */     
/*     */     case 7: 
/* 453 */       return paramInt3 == 8;
/*     */     
/*     */     case 8: 
/* 456 */       return paramInt3 >= 4;
/*     */     
/*     */     case 9: 
/* 459 */       return paramInt3 == 8;
/*     */     }
/*     */     
/* 462 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyFrameDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */