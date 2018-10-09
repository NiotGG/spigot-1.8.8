/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Unpooled
/*     */ {
/*  79 */   private static final ByteBufAllocator ALLOC = UnpooledByteBufAllocator.DEFAULT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  84 */   public static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  89 */   public static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  94 */   public static final ByteBuf EMPTY_BUFFER = ALLOC.buffer(0, 0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf buffer()
/*     */   {
/* 101 */     return ALLOC.heapBuffer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf directBuffer()
/*     */   {
/* 109 */     return ALLOC.directBuffer();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf buffer(int paramInt)
/*     */   {
/* 118 */     return ALLOC.heapBuffer(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf directBuffer(int paramInt)
/*     */   {
/* 127 */     return ALLOC.directBuffer(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf buffer(int paramInt1, int paramInt2)
/*     */   {
/* 137 */     return ALLOC.heapBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf directBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 147 */     return ALLOC.directBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(byte[] paramArrayOfByte)
/*     */   {
/* 156 */     if (paramArrayOfByte.length == 0) {
/* 157 */       return EMPTY_BUFFER;
/*     */     }
/* 159 */     return new UnpooledHeapByteBuf(ALLOC, paramArrayOfByte, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 168 */     if (paramInt2 == 0) {
/* 169 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 172 */     if ((paramInt1 == 0) && (paramInt2 == paramArrayOfByte.length)) {
/* 173 */       return wrappedBuffer(paramArrayOfByte);
/*     */     }
/*     */     
/* 176 */     return wrappedBuffer(paramArrayOfByte).slice(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/* 185 */     if (!paramByteBuffer.hasRemaining()) {
/* 186 */       return EMPTY_BUFFER;
/*     */     }
/* 188 */     if (paramByteBuffer.hasArray()) {
/* 189 */       return wrappedBuffer(paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramByteBuffer.remaining()).order(paramByteBuffer.order());
/*     */     }
/*     */     
/*     */ 
/* 193 */     if (PlatformDependent.hasUnsafe()) {
/* 194 */       if (paramByteBuffer.isReadOnly()) {
/* 195 */         if (paramByteBuffer.isDirect()) {
/* 196 */           return new ReadOnlyUnsafeDirectByteBuf(ALLOC, paramByteBuffer);
/*     */         }
/* 198 */         return new ReadOnlyByteBufferBuf(ALLOC, paramByteBuffer);
/*     */       }
/*     */       
/* 201 */       return new UnpooledUnsafeDirectByteBuf(ALLOC, paramByteBuffer, paramByteBuffer.remaining());
/*     */     }
/*     */     
/* 204 */     if (paramByteBuffer.isReadOnly()) {
/* 205 */       return new ReadOnlyByteBufferBuf(ALLOC, paramByteBuffer);
/*     */     }
/* 207 */     return new UnpooledDirectByteBuf(ALLOC, paramByteBuffer, paramByteBuffer.remaining());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 218 */     if (paramByteBuf.isReadable()) {
/* 219 */       return paramByteBuf.slice();
/*     */     }
/* 221 */     return EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(byte[]... paramVarArgs)
/*     */   {
/* 231 */     return wrappedBuffer(16, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(ByteBuf... paramVarArgs)
/*     */   {
/* 240 */     return wrappedBuffer(16, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(ByteBuffer... paramVarArgs)
/*     */   {
/* 249 */     return wrappedBuffer(16, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(int paramInt, byte[]... paramVarArgs)
/*     */   {
/* 258 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/*     */       break;
/*     */     case 1: 
/* 262 */       if (paramVarArgs[0].length != 0) {
/* 263 */         return wrappedBuffer(paramVarArgs[0]);
/*     */       }
/*     */       
/*     */       break;
/*     */     default: 
/* 268 */       ArrayList localArrayList = new ArrayList(paramVarArgs.length);
/* 269 */       for (byte[] arrayOfByte1 : paramVarArgs) {
/* 270 */         if (arrayOfByte1 == null) {
/*     */           break;
/*     */         }
/* 273 */         if (arrayOfByte1.length > 0) {
/* 274 */           localArrayList.add(wrappedBuffer(arrayOfByte1));
/*     */         }
/*     */       }
/*     */       
/* 278 */       if (!localArrayList.isEmpty()) {
/* 279 */         return new CompositeByteBuf(ALLOC, false, paramInt, localArrayList);
/*     */       }
/*     */       break;
/*     */     }
/* 283 */     return EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(int paramInt, ByteBuf... paramVarArgs)
/*     */   {
/* 292 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/*     */       break;
/*     */     case 1: 
/* 296 */       if (paramVarArgs[0].isReadable()) {
/* 297 */         return wrappedBuffer(paramVarArgs[0].order(BIG_ENDIAN));
/*     */       }
/*     */       break;
/*     */     default: 
/* 301 */       for (ByteBuf localByteBuf : paramVarArgs) {
/* 302 */         if (localByteBuf.isReadable()) {
/* 303 */           return new CompositeByteBuf(ALLOC, false, paramInt, paramVarArgs);
/*     */         }
/*     */       }
/*     */     }
/* 307 */     return EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf wrappedBuffer(int paramInt, ByteBuffer... paramVarArgs)
/*     */   {
/* 316 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/*     */       break;
/*     */     case 1: 
/* 320 */       if (paramVarArgs[0].hasRemaining()) {
/* 321 */         return wrappedBuffer(paramVarArgs[0].order(BIG_ENDIAN));
/*     */       }
/*     */       
/*     */       break;
/*     */     default: 
/* 326 */       ArrayList localArrayList = new ArrayList(paramVarArgs.length);
/* 327 */       for (ByteBuffer localByteBuffer : paramVarArgs) {
/* 328 */         if (localByteBuffer == null) {
/*     */           break;
/*     */         }
/* 331 */         if (localByteBuffer.remaining() > 0) {
/* 332 */           localArrayList.add(wrappedBuffer(localByteBuffer.order(BIG_ENDIAN)));
/*     */         }
/*     */       }
/*     */       
/* 336 */       if (!localArrayList.isEmpty()) {
/* 337 */         return new CompositeByteBuf(ALLOC, false, paramInt, localArrayList);
/*     */       }
/*     */       break;
/*     */     }
/* 341 */     return EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CompositeByteBuf compositeBuffer()
/*     */   {
/* 348 */     return compositeBuffer(16);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CompositeByteBuf compositeBuffer(int paramInt)
/*     */   {
/* 355 */     return new CompositeByteBuf(ALLOC, false, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(byte[] paramArrayOfByte)
/*     */   {
/* 364 */     if (paramArrayOfByte.length == 0) {
/* 365 */       return EMPTY_BUFFER;
/*     */     }
/* 367 */     return wrappedBuffer((byte[])paramArrayOfByte.clone());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 377 */     if (paramInt2 == 0) {
/* 378 */       return EMPTY_BUFFER;
/*     */     }
/* 380 */     byte[] arrayOfByte = new byte[paramInt2];
/* 381 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
/* 382 */     return wrappedBuffer(arrayOfByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/* 392 */     int i = paramByteBuffer.remaining();
/* 393 */     if (i == 0) {
/* 394 */       return EMPTY_BUFFER;
/*     */     }
/* 396 */     byte[] arrayOfByte = new byte[i];
/* 397 */     int j = paramByteBuffer.position();
/*     */     try {
/* 399 */       paramByteBuffer.get(arrayOfByte);
/*     */     } finally {
/* 401 */       paramByteBuffer.position(j);
/*     */     }
/* 403 */     return wrappedBuffer(arrayOfByte).order(paramByteBuffer.order());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 413 */     int i = paramByteBuf.readableBytes();
/* 414 */     if (i > 0) {
/* 415 */       ByteBuf localByteBuf = buffer(i);
/* 416 */       localByteBuf.writeBytes(paramByteBuf, paramByteBuf.readerIndex(), i);
/* 417 */       return localByteBuf;
/*     */     }
/* 419 */     return EMPTY_BUFFER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(byte[]... paramVarArgs)
/*     */   {
/* 430 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/* 432 */       return EMPTY_BUFFER;
/*     */     case 1: 
/* 434 */       if (paramVarArgs[0].length == 0) {
/* 435 */         return EMPTY_BUFFER;
/*     */       }
/* 437 */       return copiedBuffer(paramVarArgs[0]);
/*     */     }
/*     */     
/*     */     
/*     */ 
/* 442 */     int i = 0;
/* 443 */     byte[] arrayOfByte; for (arrayOfByte : paramVarArgs) {
/* 444 */       if (Integer.MAX_VALUE - i < arrayOfByte.length) {
/* 445 */         throw new IllegalArgumentException("The total length of the specified arrays is too big.");
/*     */       }
/*     */       
/* 448 */       i += arrayOfByte.length;
/*     */     }
/*     */     
/* 451 */     if (i == 0) {
/* 452 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 455 */     ??? = new byte[i];
/* 456 */     ??? = 0; for (??? = 0; ??? < paramVarArgs.length; ???++) {
/* 457 */       arrayOfByte = paramVarArgs[???];
/* 458 */       System.arraycopy(arrayOfByte, 0, ???, ???, arrayOfByte.length);
/* 459 */       ??? += arrayOfByte.length;
/*     */     }
/*     */     
/* 462 */     return wrappedBuffer((byte[])???);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuf... paramVarArgs)
/*     */   {
/* 476 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/* 478 */       return EMPTY_BUFFER;
/*     */     case 1: 
/* 480 */       return copiedBuffer(paramVarArgs[0]);
/*     */     }
/*     */     
/*     */     
/* 484 */     ByteOrder localByteOrder = null;
/* 485 */     int i = 0;
/* 486 */     ByteBuf localByteBuf; int m; for (localByteBuf : paramVarArgs) {
/* 487 */       m = localByteBuf.readableBytes();
/* 488 */       if (m > 0)
/*     */       {
/*     */ 
/* 491 */         if (Integer.MAX_VALUE - i < m) {
/* 492 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 495 */         i += m;
/* 496 */         if (localByteOrder != null) {
/* 497 */           if (!localByteOrder.equals(localByteBuf.order())) {
/* 498 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 501 */           localByteOrder = localByteBuf.order();
/*     */         }
/*     */       }
/*     */     }
/* 505 */     if (i == 0) {
/* 506 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 509 */     ??? = new byte[i];
/* 510 */     ??? = 0; for (??? = 0; ??? < paramVarArgs.length; ???++) {
/* 511 */       localByteBuf = paramVarArgs[???];
/* 512 */       m = localByteBuf.readableBytes();
/* 513 */       localByteBuf.getBytes(localByteBuf.readerIndex(), (byte[])???, ???, m);
/* 514 */       ??? += m;
/*     */     }
/*     */     
/* 517 */     return wrappedBuffer((byte[])???).order(localByteOrder);
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
/*     */   public static ByteBuf copiedBuffer(ByteBuffer... paramVarArgs)
/*     */   {
/* 531 */     switch (paramVarArgs.length) {
/*     */     case 0: 
/* 533 */       return EMPTY_BUFFER;
/*     */     case 1: 
/* 535 */       return copiedBuffer(paramVarArgs[0]);
/*     */     }
/*     */     
/*     */     
/* 539 */     ByteOrder localByteOrder = null;
/* 540 */     int i = 0;
/* 541 */     ByteBuffer localByteBuffer; int m; for (localByteBuffer : paramVarArgs) {
/* 542 */       m = localByteBuffer.remaining();
/* 543 */       if (m > 0)
/*     */       {
/*     */ 
/* 546 */         if (Integer.MAX_VALUE - i < m) {
/* 547 */           throw new IllegalArgumentException("The total length of the specified buffers is too big.");
/*     */         }
/*     */         
/* 550 */         i += m;
/* 551 */         if (localByteOrder != null) {
/* 552 */           if (!localByteOrder.equals(localByteBuffer.order())) {
/* 553 */             throw new IllegalArgumentException("inconsistent byte order");
/*     */           }
/*     */         } else {
/* 556 */           localByteOrder = localByteBuffer.order();
/*     */         }
/*     */       }
/*     */     }
/* 560 */     if (i == 0) {
/* 561 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 564 */     ??? = new byte[i];
/* 565 */     ??? = 0; for (??? = 0; ??? < paramVarArgs.length; ???++) {
/* 566 */       localByteBuffer = paramVarArgs[???];
/* 567 */       m = localByteBuffer.remaining();
/* 568 */       int n = localByteBuffer.position();
/* 569 */       localByteBuffer.get((byte[])???, ???, m);
/* 570 */       localByteBuffer.position(n);
/* 571 */       ??? += m;
/*     */     }
/*     */     
/* 574 */     return wrappedBuffer((byte[])???).order(localByteOrder);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(CharSequence paramCharSequence, Charset paramCharset)
/*     */   {
/* 584 */     if (paramCharSequence == null) {
/* 585 */       throw new NullPointerException("string");
/*     */     }
/*     */     
/* 588 */     if ((paramCharSequence instanceof CharBuffer)) {
/* 589 */       return copiedBuffer((CharBuffer)paramCharSequence, paramCharset);
/*     */     }
/*     */     
/* 592 */     return copiedBuffer(CharBuffer.wrap(paramCharSequence), paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(CharSequence paramCharSequence, int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 603 */     if (paramCharSequence == null) {
/* 604 */       throw new NullPointerException("string");
/*     */     }
/* 606 */     if (paramInt2 == 0) {
/* 607 */       return EMPTY_BUFFER;
/*     */     }
/*     */     
/* 610 */     if ((paramCharSequence instanceof CharBuffer)) {
/* 611 */       CharBuffer localCharBuffer = (CharBuffer)paramCharSequence;
/* 612 */       if (localCharBuffer.hasArray()) {
/* 613 */         return copiedBuffer(localCharBuffer.array(), localCharBuffer.arrayOffset() + localCharBuffer.position() + paramInt1, paramInt2, paramCharset);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 619 */       localCharBuffer = localCharBuffer.slice();
/* 620 */       localCharBuffer.limit(paramInt2);
/* 621 */       localCharBuffer.position(paramInt1);
/* 622 */       return copiedBuffer(localCharBuffer, paramCharset);
/*     */     }
/*     */     
/* 625 */     return copiedBuffer(CharBuffer.wrap(paramCharSequence, paramInt1, paramInt1 + paramInt2), paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(char[] paramArrayOfChar, Charset paramCharset)
/*     */   {
/* 635 */     if (paramArrayOfChar == null) {
/* 636 */       throw new NullPointerException("array");
/*     */     }
/* 638 */     return copiedBuffer(paramArrayOfChar, 0, paramArrayOfChar.length, paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf copiedBuffer(char[] paramArrayOfChar, int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 648 */     if (paramArrayOfChar == null) {
/* 649 */       throw new NullPointerException("array");
/*     */     }
/* 651 */     if (paramInt2 == 0) {
/* 652 */       return EMPTY_BUFFER;
/*     */     }
/* 654 */     return copiedBuffer(CharBuffer.wrap(paramArrayOfChar, paramInt1, paramInt2), paramCharset);
/*     */   }
/*     */   
/*     */   private static ByteBuf copiedBuffer(CharBuffer paramCharBuffer, Charset paramCharset) {
/* 658 */     return ByteBufUtil.encodeString0(ALLOC, true, paramCharBuffer, paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf unmodifiableBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 668 */     ByteOrder localByteOrder = paramByteBuf.order();
/* 669 */     if (localByteOrder == BIG_ENDIAN) {
/* 670 */       return new ReadOnlyByteBuf(paramByteBuf);
/*     */     }
/*     */     
/* 673 */     return new ReadOnlyByteBuf(paramByteBuf.order(BIG_ENDIAN)).order(LITTLE_ENDIAN);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyInt(int paramInt)
/*     */   {
/* 680 */     ByteBuf localByteBuf = buffer(4);
/* 681 */     localByteBuf.writeInt(paramInt);
/* 682 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyInt(int... paramVarArgs)
/*     */   {
/* 689 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 690 */       return EMPTY_BUFFER;
/*     */     }
/* 692 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 4);
/* 693 */     for (int k : paramVarArgs) {
/* 694 */       localByteBuf.writeInt(k);
/*     */     }
/* 696 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyShort(int paramInt)
/*     */   {
/* 703 */     ByteBuf localByteBuf = buffer(2);
/* 704 */     localByteBuf.writeShort(paramInt);
/* 705 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyShort(short... paramVarArgs)
/*     */   {
/* 712 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 713 */       return EMPTY_BUFFER;
/*     */     }
/* 715 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 2);
/* 716 */     for (int k : paramVarArgs) {
/* 717 */       localByteBuf.writeShort(k);
/*     */     }
/* 719 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyShort(int... paramVarArgs)
/*     */   {
/* 726 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 727 */       return EMPTY_BUFFER;
/*     */     }
/* 729 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 2);
/* 730 */     for (int k : paramVarArgs) {
/* 731 */       localByteBuf.writeShort(k);
/*     */     }
/* 733 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyMedium(int paramInt)
/*     */   {
/* 740 */     ByteBuf localByteBuf = buffer(3);
/* 741 */     localByteBuf.writeMedium(paramInt);
/* 742 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyMedium(int... paramVarArgs)
/*     */   {
/* 749 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 750 */       return EMPTY_BUFFER;
/*     */     }
/* 752 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 3);
/* 753 */     for (int k : paramVarArgs) {
/* 754 */       localByteBuf.writeMedium(k);
/*     */     }
/* 756 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyLong(long paramLong)
/*     */   {
/* 763 */     ByteBuf localByteBuf = buffer(8);
/* 764 */     localByteBuf.writeLong(paramLong);
/* 765 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyLong(long... paramVarArgs)
/*     */   {
/* 772 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 773 */       return EMPTY_BUFFER;
/*     */     }
/* 775 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 8);
/* 776 */     for (long l : paramVarArgs) {
/* 777 */       localByteBuf.writeLong(l);
/*     */     }
/* 779 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyBoolean(boolean paramBoolean)
/*     */   {
/* 786 */     ByteBuf localByteBuf = buffer(1);
/* 787 */     localByteBuf.writeBoolean(paramBoolean);
/* 788 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyBoolean(boolean... paramVarArgs)
/*     */   {
/* 795 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 796 */       return EMPTY_BUFFER;
/*     */     }
/* 798 */     ByteBuf localByteBuf = buffer(paramVarArgs.length);
/* 799 */     for (int k : paramVarArgs) {
/* 800 */       localByteBuf.writeBoolean(k);
/*     */     }
/* 802 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyFloat(float paramFloat)
/*     */   {
/* 809 */     ByteBuf localByteBuf = buffer(4);
/* 810 */     localByteBuf.writeFloat(paramFloat);
/* 811 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyFloat(float... paramVarArgs)
/*     */   {
/* 818 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 819 */       return EMPTY_BUFFER;
/*     */     }
/* 821 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 4);
/* 822 */     for (float f : paramVarArgs) {
/* 823 */       localByteBuf.writeFloat(f);
/*     */     }
/* 825 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyDouble(double paramDouble)
/*     */   {
/* 832 */     ByteBuf localByteBuf = buffer(8);
/* 833 */     localByteBuf.writeDouble(paramDouble);
/* 834 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf copyDouble(double... paramVarArgs)
/*     */   {
/* 841 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 842 */       return EMPTY_BUFFER;
/*     */     }
/* 844 */     ByteBuf localByteBuf = buffer(paramVarArgs.length * 8);
/* 845 */     for (double d : paramVarArgs) {
/* 846 */       localByteBuf.writeDouble(d);
/*     */     }
/* 848 */     return localByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf unreleasableBuffer(ByteBuf paramByteBuf)
/*     */   {
/* 855 */     return new UnreleasableByteBuf(paramByteBuf);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\Unpooled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */