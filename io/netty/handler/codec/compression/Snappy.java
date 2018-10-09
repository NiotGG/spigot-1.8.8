/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
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
/*     */ class Snappy
/*     */ {
/*     */   private static final int MAX_HT_SIZE = 16384;
/*     */   private static final int MIN_COMPRESSIBLE_BYTES = 15;
/*     */   private static final int PREAMBLE_NOT_FULL = -1;
/*     */   private static final int NOT_ENOUGH_INPUT = -1;
/*     */   private static final int LITERAL = 0;
/*     */   private static final int COPY_1_BYTE_OFFSET = 1;
/*     */   private static final int COPY_2_BYTE_OFFSET = 2;
/*     */   private static final int COPY_4_BYTE_OFFSET = 3;
/*     */   private State state;
/*     */   private byte tag;
/*     */   private int written;
/*     */   
/*     */   Snappy()
/*     */   {
/*  42 */     this.state = State.READY;
/*     */   }
/*     */   
/*     */   private static enum State
/*     */   {
/*  47 */     READY, 
/*  48 */     READING_PREAMBLE, 
/*  49 */     READING_TAG, 
/*  50 */     READING_LITERAL, 
/*  51 */     READING_COPY;
/*     */     
/*     */     private State() {} }
/*     */   
/*  55 */   public void reset() { this.state = State.READY;
/*  56 */     this.tag = 0;
/*  57 */     this.written = 0;
/*     */   }
/*     */   
/*     */   public void encode(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/*  62 */     for (int i = 0;; i++) {
/*  63 */       j = paramInt >>> i * 7;
/*  64 */       if ((j & 0xFFFFFF80) != 0) {
/*  65 */         paramByteBuf2.writeByte(j & 0x7F | 0x80);
/*     */       } else {
/*  67 */         paramByteBuf2.writeByte(j);
/*  68 */         break;
/*     */       }
/*     */     }
/*     */     
/*  72 */     i = paramByteBuf1.readerIndex();
/*  73 */     int j = i;
/*     */     
/*  75 */     short[] arrayOfShort = getHashTable(paramInt);
/*  76 */     int k = 32 - (int)Math.floor(Math.log(arrayOfShort.length) / Math.log(2.0D));
/*     */     
/*  78 */     int m = i;
/*     */     
/*  80 */     if (paramInt - i >= 15) {
/*  81 */       int n = hash(paramByteBuf1, ++i, k);
/*     */       for (;;) {
/*  83 */         int i1 = 32;
/*     */         
/*     */ 
/*  86 */         int i2 = i;
/*     */         int i3;
/*  88 */         int i4; int i5; do { i = i2;
/*  89 */           i3 = n;
/*  90 */           i4 = i1++ >> 5;
/*  91 */           i2 = i + i4;
/*     */           
/*     */ 
/*  94 */           if (i2 > paramInt - 4) {
/*     */             break;
/*     */           }
/*     */           
/*  98 */           n = hash(paramByteBuf1, i2, k);
/*     */           
/* 100 */           i5 = j + arrayOfShort[i3];
/*     */           
/* 102 */           arrayOfShort[i3] = ((short)(i - j));
/*     */         }
/* 104 */         while (paramByteBuf1.getInt(i) != paramByteBuf1.getInt(i5));
/*     */         
/* 106 */         encodeLiteral(paramByteBuf1, paramByteBuf2, i - m);
/*     */         
/*     */         do
/*     */         {
/* 110 */           i4 = i;
/* 111 */           int i6 = 4 + findMatchingLength(paramByteBuf1, i5 + 4, i + 4, paramInt);
/* 112 */           i += i6;
/* 113 */           int i7 = i4 - i5;
/* 114 */           encodeCopy(paramByteBuf2, i7, i6);
/* 115 */           paramByteBuf1.readerIndex(paramByteBuf1.readerIndex() + i6);
/* 116 */           i3 = i - 1;
/* 117 */           m = i;
/* 118 */           if (i >= paramInt - 4) {
/*     */             break;
/*     */           }
/*     */           
/* 122 */           int i8 = hash(paramByteBuf1, i3, k);
/* 123 */           arrayOfShort[i8] = ((short)(i - j - 1));
/* 124 */           int i9 = hash(paramByteBuf1, i3 + 1, k);
/* 125 */           i5 = j + arrayOfShort[i9];
/* 126 */           arrayOfShort[i9] = ((short)(i - j));
/*     */         }
/* 128 */         while (paramByteBuf1.getInt(i3 + 1) == paramByteBuf1.getInt(i5));
/*     */         
/* 130 */         n = hash(paramByteBuf1, i3 + 2, k);
/* 131 */         i++;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 136 */     if (m < paramInt) {
/* 137 */       encodeLiteral(paramByteBuf1, paramByteBuf2, paramInt - m);
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
/*     */ 
/*     */ 
/*     */   private static int hash(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 152 */     return paramByteBuf.getInt(paramInt1) + 506832829 >>> paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static short[] getHashTable(int paramInt)
/*     */   {
/* 162 */     int i = 256;
/* 163 */     while ((i < 16384) && (i < paramInt)) {
/* 164 */       i <<= 1;
/*     */     }
/*     */     
/*     */     short[] arrayOfShort;
/* 168 */     if (i <= 256) {
/* 169 */       arrayOfShort = new short['Ā'];
/*     */     } else {
/* 171 */       arrayOfShort = new short['䀀'];
/*     */     }
/*     */     
/* 174 */     return arrayOfShort;
/*     */   }
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
/*     */   private static int findMatchingLength(ByteBuf paramByteBuf, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 189 */     int i = 0;
/*     */     
/* 191 */     while ((paramInt2 <= paramInt3 - 4) && (paramByteBuf.getInt(paramInt2) == paramByteBuf.getInt(paramInt1 + i)))
/*     */     {
/* 193 */       paramInt2 += 4;
/* 194 */       i += 4;
/*     */     }
/*     */     
/* 197 */     while ((paramInt2 < paramInt3) && (paramByteBuf.getByte(paramInt1 + i) == paramByteBuf.getByte(paramInt2))) {
/* 198 */       paramInt2++;
/* 199 */       i++;
/*     */     }
/*     */     
/* 202 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int bitsToEncode(int paramInt)
/*     */   {
/* 214 */     int i = Integer.highestOneBit(paramInt);
/* 215 */     int j = 0;
/* 216 */     while (i >>= 1 != 0) {
/* 217 */       j++;
/*     */     }
/*     */     
/* 220 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void encodeLiteral(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/* 233 */     if (paramInt < 61) {
/* 234 */       paramByteBuf2.writeByte(paramInt - 1 << 2);
/*     */     } else {
/* 236 */       int i = bitsToEncode(paramInt - 1);
/* 237 */       int j = 1 + i / 8;
/* 238 */       paramByteBuf2.writeByte(59 + j << 2);
/* 239 */       for (int k = 0; k < j; k++) {
/* 240 */         paramByteBuf2.writeByte(paramInt - 1 >> k * 8 & 0xFF);
/*     */       }
/*     */     }
/*     */     
/* 244 */     paramByteBuf2.writeBytes(paramByteBuf1, paramInt);
/*     */   }
/*     */   
/*     */   private static void encodeCopyWithOffset(ByteBuf paramByteBuf, int paramInt1, int paramInt2) {
/* 248 */     if ((paramInt2 < 12) && (paramInt1 < 2048)) {
/* 249 */       paramByteBuf.writeByte(0x1 | paramInt2 - 4 << 2 | paramInt1 >> 8 << 5);
/* 250 */       paramByteBuf.writeByte(paramInt1 & 0xFF);
/*     */     } else {
/* 252 */       paramByteBuf.writeByte(0x2 | paramInt2 - 1 << 2);
/* 253 */       paramByteBuf.writeByte(paramInt1 & 0xFF);
/* 254 */       paramByteBuf.writeByte(paramInt1 >> 8 & 0xFF);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void encodeCopy(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 266 */     while (paramInt2 >= 68) {
/* 267 */       encodeCopyWithOffset(paramByteBuf, paramInt1, 64);
/* 268 */       paramInt2 -= 64;
/*     */     }
/*     */     
/* 271 */     if (paramInt2 > 64) {
/* 272 */       encodeCopyWithOffset(paramByteBuf, paramInt1, 60);
/* 273 */       paramInt2 -= 60;
/*     */     }
/*     */     
/* 276 */     encodeCopyWithOffset(paramByteBuf, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void decode(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) {
/* 280 */     while (paramByteBuf1.isReadable()) {
/* 281 */       switch (this.state) {
/*     */       case READY: 
/* 283 */         this.state = State.READING_PREAMBLE;
/*     */       case READING_PREAMBLE: 
/* 285 */         int i = readPreamble(paramByteBuf1);
/* 286 */         if (i == -1)
/*     */         {
/* 288 */           return;
/*     */         }
/* 290 */         if (i == 0)
/*     */         {
/* 292 */           this.state = State.READY;
/* 293 */           return;
/*     */         }
/* 295 */         paramByteBuf2.ensureWritable(i);
/* 296 */         this.state = State.READING_TAG;
/*     */       case READING_TAG: 
/* 298 */         if (!paramByteBuf1.isReadable()) {
/* 299 */           return;
/*     */         }
/* 301 */         this.tag = paramByteBuf1.readByte();
/* 302 */         switch (this.tag & 0x3) {
/*     */         case 0: 
/* 304 */           this.state = State.READING_LITERAL;
/* 305 */           break;
/*     */         case 1: 
/*     */         case 2: 
/*     */         case 3: 
/* 309 */           this.state = State.READING_COPY;
/*     */         }
/*     */         
/* 312 */         break;
/*     */       case READING_LITERAL: 
/* 314 */         int j = decodeLiteral(this.tag, paramByteBuf1, paramByteBuf2);
/* 315 */         if (j != -1) {
/* 316 */           this.state = State.READING_TAG;
/* 317 */           this.written += j;
/*     */         }
/*     */         else {
/*     */           return;
/*     */         }
/*     */         break;
/*     */       case READING_COPY: 
/*     */         int k;
/* 325 */         switch (this.tag & 0x3) {
/*     */         case 1: 
/* 327 */           k = decodeCopyWith1ByteOffset(this.tag, paramByteBuf1, paramByteBuf2, this.written);
/* 328 */           if (k != -1) {
/* 329 */             this.state = State.READING_TAG;
/* 330 */             this.written += k;
/*     */           }
/*     */           else {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case 2: 
/* 337 */           k = decodeCopyWith2ByteOffset(this.tag, paramByteBuf1, paramByteBuf2, this.written);
/* 338 */           if (k != -1) {
/* 339 */             this.state = State.READING_TAG;
/* 340 */             this.written += k;
/*     */           }
/*     */           else {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case 3: 
/* 347 */           k = decodeCopyWith4ByteOffset(this.tag, paramByteBuf1, paramByteBuf2, this.written);
/* 348 */           if (k != -1) {
/* 349 */             this.state = State.READING_TAG;
/* 350 */             this.written += k;
/*     */           }
/*     */           else
/*     */           {
/*     */             return;
/*     */           }
/*     */           
/*     */ 
/*     */           break;
/*     */         }
/*     */         
/*     */         
/*     */         break;
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static int readPreamble(ByteBuf paramByteBuf)
/*     */   {
/* 371 */     int i = 0;
/* 372 */     int j = 0;
/* 373 */     while (paramByteBuf.isReadable()) {
/* 374 */       int k = paramByteBuf.readUnsignedByte();
/* 375 */       i |= (k & 0x7F) << j++ * 7;
/* 376 */       if ((k & 0x80) == 0) {
/* 377 */         return i;
/*     */       }
/*     */       
/* 380 */       if (j >= 4) {
/* 381 */         throw new DecompressionException("Preamble is greater than 4 bytes");
/*     */       }
/*     */     }
/*     */     
/* 385 */     return 0;
/*     */   }
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
/*     */   private static int decodeLiteral(byte paramByte, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*     */   {
/* 400 */     paramByteBuf1.markReaderIndex();
/*     */     int i;
/* 402 */     switch (paramByte >> 2 & 0x3F) {
/*     */     case 60: 
/* 404 */       if (!paramByteBuf1.isReadable()) {
/* 405 */         return -1;
/*     */       }
/* 407 */       i = paramByteBuf1.readUnsignedByte();
/* 408 */       break;
/*     */     case 61: 
/* 410 */       if (paramByteBuf1.readableBytes() < 2) {
/* 411 */         return -1;
/*     */       }
/* 413 */       i = ByteBufUtil.swapShort(paramByteBuf1.readShort());
/* 414 */       break;
/*     */     case 62: 
/* 416 */       if (paramByteBuf1.readableBytes() < 3) {
/* 417 */         return -1;
/*     */       }
/* 419 */       i = ByteBufUtil.swapMedium(paramByteBuf1.readUnsignedMedium());
/* 420 */       break;
/*     */     case 64: 
/* 422 */       if (paramByteBuf1.readableBytes() < 4) {
/* 423 */         return -1;
/*     */       }
/* 425 */       i = ByteBufUtil.swapInt(paramByteBuf1.readInt());
/* 426 */       break;
/*     */     case 63: default: 
/* 428 */       i = paramByte >> 2 & 0x3F;
/*     */     }
/* 430 */     i++;
/*     */     
/* 432 */     if (paramByteBuf1.readableBytes() < i) {
/* 433 */       paramByteBuf1.resetReaderIndex();
/* 434 */       return -1;
/*     */     }
/*     */     
/* 437 */     paramByteBuf2.writeBytes(paramByteBuf1, i);
/* 438 */     return i;
/*     */   }
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
/*     */   private static int decodeCopyWith1ByteOffset(byte paramByte, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/* 455 */     if (!paramByteBuf1.isReadable()) {
/* 456 */       return -1;
/*     */     }
/*     */     
/* 459 */     int i = paramByteBuf2.writerIndex();
/* 460 */     int j = 4 + ((paramByte & 0x1C) >> 2);
/* 461 */     int k = (paramByte & 0xE0) << 8 >> 5 | paramByteBuf1.readUnsignedByte();
/*     */     
/* 463 */     validateOffset(k, paramInt);
/*     */     
/* 465 */     paramByteBuf2.markReaderIndex();
/* 466 */     if (k < j) {
/* 467 */       for (int m = j / k; 
/* 468 */           m > 0; m--) {
/* 469 */         paramByteBuf2.readerIndex(i - k);
/* 470 */         paramByteBuf2.readBytes(paramByteBuf2, k);
/*     */       }
/* 472 */       if (j % k != 0) {
/* 473 */         paramByteBuf2.readerIndex(i - k);
/* 474 */         paramByteBuf2.readBytes(paramByteBuf2, j % k);
/*     */       }
/*     */     } else {
/* 477 */       paramByteBuf2.readerIndex(i - k);
/* 478 */       paramByteBuf2.readBytes(paramByteBuf2, j);
/*     */     }
/* 480 */     paramByteBuf2.resetReaderIndex();
/*     */     
/* 482 */     return j;
/*     */   }
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
/*     */   private static int decodeCopyWith2ByteOffset(byte paramByte, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/* 499 */     if (paramByteBuf1.readableBytes() < 2) {
/* 500 */       return -1;
/*     */     }
/*     */     
/* 503 */     int i = paramByteBuf2.writerIndex();
/* 504 */     int j = 1 + (paramByte >> 2 & 0x3F);
/* 505 */     int k = ByteBufUtil.swapShort(paramByteBuf1.readShort());
/*     */     
/* 507 */     validateOffset(k, paramInt);
/*     */     
/* 509 */     paramByteBuf2.markReaderIndex();
/* 510 */     if (k < j) {
/* 511 */       for (int m = j / k; 
/* 512 */           m > 0; m--) {
/* 513 */         paramByteBuf2.readerIndex(i - k);
/* 514 */         paramByteBuf2.readBytes(paramByteBuf2, k);
/*     */       }
/* 516 */       if (j % k != 0) {
/* 517 */         paramByteBuf2.readerIndex(i - k);
/* 518 */         paramByteBuf2.readBytes(paramByteBuf2, j % k);
/*     */       }
/*     */     } else {
/* 521 */       paramByteBuf2.readerIndex(i - k);
/* 522 */       paramByteBuf2.readBytes(paramByteBuf2, j);
/*     */     }
/* 524 */     paramByteBuf2.resetReaderIndex();
/*     */     
/* 526 */     return j;
/*     */   }
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
/*     */   private static int decodeCopyWith4ByteOffset(byte paramByte, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2, int paramInt)
/*     */   {
/* 543 */     if (paramByteBuf1.readableBytes() < 4) {
/* 544 */       return -1;
/*     */     }
/*     */     
/* 547 */     int i = paramByteBuf2.writerIndex();
/* 548 */     int j = 1 + (paramByte >> 2 & 0x3F);
/* 549 */     int k = ByteBufUtil.swapInt(paramByteBuf1.readInt());
/*     */     
/* 551 */     validateOffset(k, paramInt);
/*     */     
/* 553 */     paramByteBuf2.markReaderIndex();
/* 554 */     if (k < j) {
/* 555 */       for (int m = j / k; 
/* 556 */           m > 0; m--) {
/* 557 */         paramByteBuf2.readerIndex(i - k);
/* 558 */         paramByteBuf2.readBytes(paramByteBuf2, k);
/*     */       }
/* 560 */       if (j % k != 0) {
/* 561 */         paramByteBuf2.readerIndex(i - k);
/* 562 */         paramByteBuf2.readBytes(paramByteBuf2, j % k);
/*     */       }
/*     */     } else {
/* 565 */       paramByteBuf2.readerIndex(i - k);
/* 566 */       paramByteBuf2.readBytes(paramByteBuf2, j);
/*     */     }
/* 568 */     paramByteBuf2.resetReaderIndex();
/*     */     
/* 570 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void validateOffset(int paramInt1, int paramInt2)
/*     */   {
/* 583 */     if (paramInt1 > 32767) {
/* 584 */       throw new DecompressionException("Offset exceeds maximum permissible value");
/*     */     }
/*     */     
/* 587 */     if (paramInt1 <= 0) {
/* 588 */       throw new DecompressionException("Offset is less than minimum permissible value");
/*     */     }
/*     */     
/* 591 */     if (paramInt1 > paramInt2) {
/* 592 */       throw new DecompressionException("Offset exceeds size of chunk");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int calculateChecksum(ByteBuf paramByteBuf)
/*     */   {
/* 603 */     return calculateChecksum(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int calculateChecksum(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 613 */     Crc32c localCrc32c = new Crc32c();
/*     */     try {
/* 615 */       if (paramByteBuf.hasArray()) {
/* 616 */         localCrc32c.update(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt1, paramInt2);
/*     */       } else {
/* 618 */         byte[] arrayOfByte = new byte[paramInt2];
/* 619 */         paramByteBuf.getBytes(paramInt1, arrayOfByte);
/* 620 */         localCrc32c.update(arrayOfByte, 0, paramInt2);
/*     */       }
/*     */       
/* 623 */       return maskChecksum((int)localCrc32c.getValue());
/*     */     } finally {
/* 625 */       localCrc32c.reset();
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
/*     */ 
/*     */   static void validateChecksum(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 639 */     validateChecksum(paramInt, paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void validateChecksum(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 652 */     int i = calculateChecksum(paramByteBuf, paramInt2, paramInt3);
/* 653 */     if (i != paramInt1) {
/* 654 */       throw new DecompressionException("mismatching checksum: " + Integer.toHexString(i) + " (expected: " + Integer.toHexString(paramInt1) + ')');
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int maskChecksum(int paramInt)
/*     */   {
/* 672 */     return (paramInt >> 15 | paramInt << 17) + -1568478504;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\Snappy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */