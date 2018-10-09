/*     */ package org.apache.commons.codec.binary;
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
/*     */ public class Base32
/*     */   extends BaseNCodec
/*     */ {
/*     */   private static final int BITS_PER_ENCODED_BYTE = 5;
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
/*     */   private static final int BYTES_PER_ENCODED_BLOCK = 8;
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
/*     */   private static final int BYTES_PER_UNENCODED_BLOCK = 5;
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
/*  60 */   private static final byte[] CHUNK_SEPARATOR = { 13, 10 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */   private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
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
/*  81 */   private static final byte[] ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55 };
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
/*  92 */   private static final byte[] HEX_DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32 };
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
/* 106 */   private static final byte[] HEX_ENCODE_TABLE = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MASK_5BITS = 31;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int decodeSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte[] decodeTable;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int encodeSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte[] encodeTable;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte[] lineSeparator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base32()
/*     */   {
/* 159 */     this(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base32(boolean paramBoolean)
/*     */   {
/* 170 */     this(0, null, paramBoolean);
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
/*     */   public Base32(int paramInt)
/*     */   {
/* 185 */     this(paramInt, CHUNK_SEPARATOR);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base32(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 207 */     this(paramInt, paramArrayOfByte, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Base32(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 232 */     super(5, 8, paramInt, paramArrayOfByte == null ? 0 : paramArrayOfByte.length);
/*     */     
/*     */ 
/* 235 */     if (paramBoolean) {
/* 236 */       this.encodeTable = HEX_ENCODE_TABLE;
/* 237 */       this.decodeTable = HEX_DECODE_TABLE;
/*     */     } else {
/* 239 */       this.encodeTable = ENCODE_TABLE;
/* 240 */       this.decodeTable = DECODE_TABLE;
/*     */     }
/* 242 */     if (paramInt > 0) {
/* 243 */       if (paramArrayOfByte == null) {
/* 244 */         throw new IllegalArgumentException("lineLength " + paramInt + " > 0, but lineSeparator is null");
/*     */       }
/*     */       
/* 247 */       if (containsAlphabetOrPad(paramArrayOfByte)) {
/* 248 */         String str = StringUtils.newStringUtf8(paramArrayOfByte);
/* 249 */         throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + str + "]");
/*     */       }
/* 251 */       this.encodeSize = (8 + paramArrayOfByte.length);
/* 252 */       this.lineSeparator = new byte[paramArrayOfByte.length];
/* 253 */       System.arraycopy(paramArrayOfByte, 0, this.lineSeparator, 0, paramArrayOfByte.length);
/*     */     } else {
/* 255 */       this.encodeSize = 8;
/* 256 */       this.lineSeparator = null;
/*     */     }
/* 258 */     this.decodeSize = (this.encodeSize - 1);
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
/*     */   void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
/*     */   {
/* 287 */     if (paramContext.eof) {
/* 288 */       return;
/*     */     }
/* 290 */     if (paramInt2 < 0) {
/* 291 */       paramContext.eof = true;
/*     */     }
/* 293 */     for (int i = 0; i < paramInt2; i++) {
/* 294 */       int j = paramArrayOfByte[(paramInt1++)];
/* 295 */       if (j == 61)
/*     */       {
/* 297 */         paramContext.eof = true;
/* 298 */         break;
/*     */       }
/* 300 */       byte[] arrayOfByte2 = ensureBufferSize(this.decodeSize, paramContext);
/* 301 */       if ((j >= 0) && (j < this.decodeTable.length)) {
/* 302 */         int k = this.decodeTable[j];
/* 303 */         if (k >= 0) {
/* 304 */           paramContext.modulus = ((paramContext.modulus + 1) % 8);
/*     */           
/* 306 */           paramContext.lbitWorkArea = ((paramContext.lbitWorkArea << 5) + k);
/* 307 */           if (paramContext.modulus == 0) {
/* 308 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 32 & 0xFF));
/* 309 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 24 & 0xFF));
/* 310 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
/* 311 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
/* 312 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */     if ((paramContext.eof) && (paramContext.modulus >= 2)) {
/* 323 */       byte[] arrayOfByte1 = ensureBufferSize(this.decodeSize, paramContext);
/*     */       
/*     */ 
/* 326 */       switch (paramContext.modulus) {
/*     */       case 2: 
/* 328 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 2 & 0xFF));
/* 329 */         break;
/*     */       case 3: 
/* 331 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 7 & 0xFF));
/* 332 */         break;
/*     */       case 4: 
/* 334 */         paramContext.lbitWorkArea >>= 4;
/* 335 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
/* 336 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
/* 337 */         break;
/*     */       case 5: 
/* 339 */         paramContext.lbitWorkArea >>= 1;
/* 340 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
/* 341 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
/* 342 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
/* 343 */         break;
/*     */       case 6: 
/* 345 */         paramContext.lbitWorkArea >>= 6;
/* 346 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
/* 347 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
/* 348 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
/* 349 */         break;
/*     */       case 7: 
/* 351 */         paramContext.lbitWorkArea >>= 3;
/* 352 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 24 & 0xFF));
/* 353 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
/* 354 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
/* 355 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
/* 356 */         break;
/*     */       
/*     */       default: 
/* 359 */         throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
/*     */       }
/*     */       
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
/*     */   {
/* 383 */     if (paramContext.eof) {
/* 384 */       return;
/*     */     }
/*     */     
/*     */ 
/* 388 */     if (paramInt2 < 0) {
/* 389 */       paramContext.eof = true;
/* 390 */       if ((0 == paramContext.modulus) && (this.lineLength == 0)) {
/* 391 */         return;
/*     */       }
/* 393 */       byte[] arrayOfByte1 = ensureBufferSize(this.encodeSize, paramContext);
/* 394 */       int j = paramContext.pos;
/* 395 */       switch (paramContext.modulus) {
/*     */       case 0: 
/*     */         break;
/*     */       case 1: 
/* 399 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 3) & 0x1F)];
/* 400 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea << 2) & 0x1F)];
/* 401 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 402 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 403 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 404 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 405 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 406 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 407 */         break;
/*     */       case 2: 
/* 409 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 11) & 0x1F)];
/* 410 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 6) & 0x1F)];
/* 411 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 1) & 0x1F)];
/* 412 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea << 4) & 0x1F)];
/* 413 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 414 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 415 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 416 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 417 */         break;
/*     */       case 3: 
/* 419 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 19) & 0x1F)];
/* 420 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 14) & 0x1F)];
/* 421 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 9) & 0x1F)];
/* 422 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 4) & 0x1F)];
/* 423 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea << 1) & 0x1F)];
/* 424 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 425 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 426 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 427 */         break;
/*     */       case 4: 
/* 429 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 27) & 0x1F)];
/* 430 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 22) & 0x1F)];
/* 431 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 17) & 0x1F)];
/* 432 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 12) & 0x1F)];
/* 433 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 7) & 0x1F)];
/* 434 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 2) & 0x1F)];
/* 435 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea << 3) & 0x1F)];
/* 436 */         arrayOfByte1[(paramContext.pos++)] = 61;
/* 437 */         break;
/*     */       default: 
/* 439 */         throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
/*     */       }
/* 441 */       paramContext.currentLinePos += paramContext.pos - j;
/*     */       
/* 443 */       if ((this.lineLength > 0) && (paramContext.currentLinePos > 0)) {
/* 444 */         System.arraycopy(this.lineSeparator, 0, arrayOfByte1, paramContext.pos, this.lineSeparator.length);
/* 445 */         paramContext.pos += this.lineSeparator.length;
/*     */       }
/*     */     } else {
/* 448 */       for (int i = 0; i < paramInt2; i++) {
/* 449 */         byte[] arrayOfByte2 = ensureBufferSize(this.encodeSize, paramContext);
/* 450 */         paramContext.modulus = ((paramContext.modulus + 1) % 5);
/* 451 */         int k = paramArrayOfByte[(paramInt1++)];
/* 452 */         if (k < 0) {
/* 453 */           k += 256;
/*     */         }
/* 455 */         paramContext.lbitWorkArea = ((paramContext.lbitWorkArea << 8) + k);
/* 456 */         if (0 == paramContext.modulus) {
/* 457 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 35) & 0x1F)];
/* 458 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 30) & 0x1F)];
/* 459 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 25) & 0x1F)];
/* 460 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 20) & 0x1F)];
/* 461 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 15) & 0x1F)];
/* 462 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 10) & 0x1F)];
/* 463 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 5) & 0x1F)];
/* 464 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[((int)paramContext.lbitWorkArea & 0x1F)];
/* 465 */           paramContext.currentLinePos += 8;
/* 466 */           if ((this.lineLength > 0) && (this.lineLength <= paramContext.currentLinePos)) {
/* 467 */             System.arraycopy(this.lineSeparator, 0, arrayOfByte2, paramContext.pos, this.lineSeparator.length);
/* 468 */             paramContext.pos += this.lineSeparator.length;
/* 469 */             paramContext.currentLinePos = 0;
/*     */           }
/*     */         }
/*     */       }
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
/*     */   public boolean isInAlphabet(byte paramByte)
/*     */   {
/* 485 */     return (paramByte >= 0) && (paramByte < this.decodeTable.length) && (this.decodeTable[paramByte] != -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\Base32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */