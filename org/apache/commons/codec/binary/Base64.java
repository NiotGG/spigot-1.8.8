/*     */ package org.apache.commons.codec.binary;
/*     */ 
/*     */ import java.math.BigInteger;
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
/*     */ public class Base64
/*     */   extends BaseNCodec
/*     */ {
/*     */   private static final int BITS_PER_ENCODED_BYTE = 6;
/*     */   private static final int BYTES_PER_UNENCODED_BLOCK = 3;
/*     */   private static final int BYTES_PER_ENCODED_BLOCK = 4;
/*  71 */   static final byte[] CHUNK_SEPARATOR = { 13, 10 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  80 */   private static final byte[] STANDARD_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
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
/*  93 */   private static final byte[] URL_SAFE_ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
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
/* 112 */   private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final int MASK_6BITS = 63;
/*     */   
/*     */ 
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
/*     */ 
/*     */ 
/* 140 */   private final byte[] decodeTable = DECODE_TABLE;
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
/*     */   private final int decodeSize;
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
/*     */   public Base64()
/*     */   {
/* 170 */     this(0);
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
/*     */   public Base64(boolean paramBoolean)
/*     */   {
/* 189 */     this(76, CHUNK_SEPARATOR, paramBoolean);
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
/*     */   public Base64(int paramInt)
/*     */   {
/* 212 */     this(paramInt, CHUNK_SEPARATOR);
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
/*     */   public Base64(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 239 */     this(paramInt, paramArrayOfByte, false);
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
/*     */ 
/*     */ 
/*     */   public Base64(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 270 */     super(3, 4, paramInt, paramArrayOfByte == null ? 0 : paramArrayOfByte.length);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 275 */     if (paramArrayOfByte != null) {
/* 276 */       if (containsAlphabetOrPad(paramArrayOfByte)) {
/* 277 */         String str = StringUtils.newStringUtf8(paramArrayOfByte);
/* 278 */         throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + str + "]");
/*     */       }
/* 280 */       if (paramInt > 0) {
/* 281 */         this.encodeSize = (4 + paramArrayOfByte.length);
/* 282 */         this.lineSeparator = new byte[paramArrayOfByte.length];
/* 283 */         System.arraycopy(paramArrayOfByte, 0, this.lineSeparator, 0, paramArrayOfByte.length);
/*     */       } else {
/* 285 */         this.encodeSize = 4;
/* 286 */         this.lineSeparator = null;
/*     */       }
/*     */     } else {
/* 289 */       this.encodeSize = 4;
/* 290 */       this.lineSeparator = null;
/*     */     }
/* 292 */     this.decodeSize = (this.encodeSize - 1);
/* 293 */     this.encodeTable = (paramBoolean ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUrlSafe()
/*     */   {
/* 303 */     return this.encodeTable == URL_SAFE_ENCODE_TABLE;
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
/*     */   void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
/*     */   {
/* 329 */     if (paramContext.eof) {
/* 330 */       return;
/*     */     }
/*     */     
/*     */ 
/* 334 */     if (paramInt2 < 0) {
/* 335 */       paramContext.eof = true;
/* 336 */       if ((0 == paramContext.modulus) && (this.lineLength == 0)) {
/* 337 */         return;
/*     */       }
/* 339 */       byte[] arrayOfByte1 = ensureBufferSize(this.encodeSize, paramContext);
/* 340 */       int j = paramContext.pos;
/* 341 */       switch (paramContext.modulus)
/*     */       {
/*     */       case 0: 
/*     */         break;
/*     */       case 1: 
/* 346 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 2 & 0x3F)];
/*     */         
/* 348 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea << 4 & 0x3F)];
/*     */         
/* 350 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/* 351 */           arrayOfByte1[(paramContext.pos++)] = 61;
/* 352 */           arrayOfByte1[(paramContext.pos++)] = 61;
/*     */         }
/*     */         
/*     */         break;
/*     */       case 2: 
/* 357 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 10 & 0x3F)];
/* 358 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 4 & 0x3F)];
/* 359 */         arrayOfByte1[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea << 2 & 0x3F)];
/*     */         
/* 361 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/* 362 */           arrayOfByte1[(paramContext.pos++)] = 61;
/*     */         }
/*     */         break;
/*     */       default: 
/* 366 */         throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
/*     */       }
/* 368 */       paramContext.currentLinePos += paramContext.pos - j;
/*     */       
/* 370 */       if ((this.lineLength > 0) && (paramContext.currentLinePos > 0)) {
/* 371 */         System.arraycopy(this.lineSeparator, 0, arrayOfByte1, paramContext.pos, this.lineSeparator.length);
/* 372 */         paramContext.pos += this.lineSeparator.length;
/*     */       }
/*     */     } else {
/* 375 */       for (int i = 0; i < paramInt2; i++) {
/* 376 */         byte[] arrayOfByte2 = ensureBufferSize(this.encodeSize, paramContext);
/* 377 */         paramContext.modulus = ((paramContext.modulus + 1) % 3);
/* 378 */         int k = paramArrayOfByte[(paramInt1++)];
/* 379 */         if (k < 0) {
/* 380 */           k += 256;
/*     */         }
/* 382 */         paramContext.ibitWorkArea = ((paramContext.ibitWorkArea << 8) + k);
/* 383 */         if (0 == paramContext.modulus) {
/* 384 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 18 & 0x3F)];
/* 385 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 12 & 0x3F)];
/* 386 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea >> 6 & 0x3F)];
/* 387 */           arrayOfByte2[(paramContext.pos++)] = this.encodeTable[(paramContext.ibitWorkArea & 0x3F)];
/* 388 */           paramContext.currentLinePos += 4;
/* 389 */           if ((this.lineLength > 0) && (this.lineLength <= paramContext.currentLinePos)) {
/* 390 */             System.arraycopy(this.lineSeparator, 0, arrayOfByte2, paramContext.pos, this.lineSeparator.length);
/* 391 */             paramContext.pos += this.lineSeparator.length;
/* 392 */             paramContext.currentLinePos = 0;
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
/* 426 */     if (paramContext.eof) {
/* 427 */       return;
/*     */     }
/* 429 */     if (paramInt2 < 0) {
/* 430 */       paramContext.eof = true;
/*     */     }
/* 432 */     for (int i = 0; i < paramInt2; i++) {
/* 433 */       byte[] arrayOfByte2 = ensureBufferSize(this.decodeSize, paramContext);
/* 434 */       int j = paramArrayOfByte[(paramInt1++)];
/* 435 */       if (j == 61)
/*     */       {
/* 437 */         paramContext.eof = true;
/* 438 */         break;
/*     */       }
/* 440 */       if ((j >= 0) && (j < DECODE_TABLE.length)) {
/* 441 */         int k = DECODE_TABLE[j];
/* 442 */         if (k >= 0) {
/* 443 */           paramContext.modulus = ((paramContext.modulus + 1) % 4);
/* 444 */           paramContext.ibitWorkArea = ((paramContext.ibitWorkArea << 6) + k);
/* 445 */           if (paramContext.modulus == 0) {
/* 446 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea >> 16 & 0xFF));
/* 447 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea >> 8 & 0xFF));
/* 448 */             arrayOfByte2[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea & 0xFF));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 458 */     if ((paramContext.eof) && (paramContext.modulus != 0)) {
/* 459 */       byte[] arrayOfByte1 = ensureBufferSize(this.decodeSize, paramContext);
/*     */       
/*     */ 
/*     */ 
/* 463 */       switch (paramContext.modulus)
/*     */       {
/*     */       case 1: 
/*     */         break;
/*     */       
/*     */       case 2: 
/* 469 */         paramContext.ibitWorkArea >>= 4;
/* 470 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea & 0xFF));
/* 471 */         break;
/*     */       case 3: 
/* 473 */         paramContext.ibitWorkArea >>= 2;
/* 474 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea >> 8 & 0xFF));
/* 475 */         arrayOfByte1[(paramContext.pos++)] = ((byte)(paramContext.ibitWorkArea & 0xFF));
/* 476 */         break;
/*     */       default: 
/* 478 */         throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
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
/*     */   @Deprecated
/*     */   public static boolean isArrayByteBase64(byte[] paramArrayOfByte)
/*     */   {
/* 495 */     return isBase64(paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isBase64(byte paramByte)
/*     */   {
/* 507 */     return (paramByte == 61) || ((paramByte >= 0) && (paramByte < DECODE_TABLE.length) && (DECODE_TABLE[paramByte] != -1));
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
/*     */   public static boolean isBase64(String paramString)
/*     */   {
/* 521 */     return isBase64(StringUtils.getBytesUtf8(paramString));
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
/*     */   public static boolean isBase64(byte[] paramArrayOfByte)
/*     */   {
/* 535 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 536 */       if ((!isBase64(paramArrayOfByte[i])) && (!isWhiteSpace(paramArrayOfByte[i]))) {
/* 537 */         return false;
/*     */       }
/*     */     }
/* 540 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] encodeBase64(byte[] paramArrayOfByte)
/*     */   {
/* 551 */     return encodeBase64(paramArrayOfByte, false);
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
/*     */   public static String encodeBase64String(byte[] paramArrayOfByte)
/*     */   {
/* 566 */     return StringUtils.newStringUtf8(encodeBase64(paramArrayOfByte, false));
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
/*     */   public static byte[] encodeBase64URLSafe(byte[] paramArrayOfByte)
/*     */   {
/* 579 */     return encodeBase64(paramArrayOfByte, false, true);
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
/*     */   public static String encodeBase64URLSafeString(byte[] paramArrayOfByte)
/*     */   {
/* 592 */     return StringUtils.newStringUtf8(encodeBase64(paramArrayOfByte, false, true));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] encodeBase64Chunked(byte[] paramArrayOfByte)
/*     */   {
/* 603 */     return encodeBase64(paramArrayOfByte, true);
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
/*     */   public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean)
/*     */   {
/* 618 */     return encodeBase64(paramArrayOfByte, paramBoolean, false);
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
/*     */   public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 637 */     return encodeBase64(paramArrayOfByte, paramBoolean1, paramBoolean2, Integer.MAX_VALUE);
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
/*     */   public static byte[] encodeBase64(byte[] paramArrayOfByte, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
/*     */   {
/* 659 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/* 660 */       return paramArrayOfByte;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 665 */     Base64 localBase64 = paramBoolean1 ? new Base64(paramBoolean2) : new Base64(0, CHUNK_SEPARATOR, paramBoolean2);
/* 666 */     long l = localBase64.getEncodedLength(paramArrayOfByte);
/* 667 */     if (l > paramInt) {
/* 668 */       throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l + ") than the specified maximum size of " + paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 674 */     return localBase64.encode(paramArrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] decodeBase64(String paramString)
/*     */   {
/* 686 */     return new Base64().decode(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] decodeBase64(byte[] paramArrayOfByte)
/*     */   {
/* 697 */     return new Base64().decode(paramArrayOfByte);
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
/*     */   public static BigInteger decodeInteger(byte[] paramArrayOfByte)
/*     */   {
/* 712 */     return new BigInteger(1, decodeBase64(paramArrayOfByte));
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
/*     */   public static byte[] encodeInteger(BigInteger paramBigInteger)
/*     */   {
/* 726 */     if (paramBigInteger == null) {
/* 727 */       throw new NullPointerException("encodeInteger called with null parameter");
/*     */     }
/* 729 */     return encodeBase64(toIntegerBytes(paramBigInteger), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static byte[] toIntegerBytes(BigInteger paramBigInteger)
/*     */   {
/* 740 */     int i = paramBigInteger.bitLength();
/*     */     
/* 742 */     i = i + 7 >> 3 << 3;
/* 743 */     byte[] arrayOfByte1 = paramBigInteger.toByteArray();
/*     */     
/* 745 */     if ((paramBigInteger.bitLength() % 8 != 0) && (paramBigInteger.bitLength() / 8 + 1 == i / 8)) {
/* 746 */       return arrayOfByte1;
/*     */     }
/*     */     
/* 749 */     int j = 0;
/* 750 */     int k = arrayOfByte1.length;
/*     */     
/*     */ 
/* 753 */     if (paramBigInteger.bitLength() % 8 == 0) {
/* 754 */       j = 1;
/* 755 */       k--;
/*     */     }
/* 757 */     int m = i / 8 - k;
/* 758 */     byte[] arrayOfByte2 = new byte[i / 8];
/* 759 */     System.arraycopy(arrayOfByte1, j, arrayOfByte2, m, k);
/* 760 */     return arrayOfByte2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isInAlphabet(byte paramByte)
/*     */   {
/* 772 */     return (paramByte >= 0) && (paramByte < this.decodeTable.length) && (this.decodeTable[paramByte] != -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\binary\Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */