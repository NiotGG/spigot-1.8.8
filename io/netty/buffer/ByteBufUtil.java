/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteBufUtil
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);
/*     */   
/*  43 */   private static final char[] HEXDUMP_TABLE = new char['Ѐ'];
/*     */   
/*     */   static final ByteBufAllocator DEFAULT_ALLOCATOR;
/*     */   private static final int THREAD_LOCAL_BUFFER_SIZE;
/*     */   
/*     */   static
/*     */   {
/*  50 */     char[] arrayOfChar = "0123456789abcdef".toCharArray();
/*  51 */     for (int i = 0; i < 256; i++) {
/*  52 */       HEXDUMP_TABLE[(i << 1)] = arrayOfChar[(i >>> 4 & 0xF)];
/*  53 */       HEXDUMP_TABLE[((i << 1) + 1)] = arrayOfChar[(i & 0xF)];
/*     */     }
/*     */     
/*  56 */     String str = SystemPropertyUtil.get("io.netty.allocator.type", "unpooled").toLowerCase(Locale.US).trim();
/*     */     Object localObject;
/*  58 */     if ("unpooled".equals(str)) {
/*  59 */       localObject = UnpooledByteBufAllocator.DEFAULT;
/*  60 */       logger.debug("-Dio.netty.allocator.type: {}", str);
/*  61 */     } else if ("pooled".equals(str)) {
/*  62 */       localObject = PooledByteBufAllocator.DEFAULT;
/*  63 */       logger.debug("-Dio.netty.allocator.type: {}", str);
/*     */     } else {
/*  65 */       localObject = UnpooledByteBufAllocator.DEFAULT;
/*  66 */       logger.debug("-Dio.netty.allocator.type: unpooled (unknown: {})", str);
/*     */     }
/*     */     
/*  69 */     DEFAULT_ALLOCATOR = (ByteBufAllocator)localObject;
/*     */     
/*  71 */     THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 65536);
/*  72 */     logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", Integer.valueOf(THREAD_LOCAL_BUFFER_SIZE));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String hexDump(ByteBuf paramByteBuf)
/*     */   {
/*  80 */     return hexDump(paramByteBuf, paramByteBuf.readerIndex(), paramByteBuf.readableBytes());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String hexDump(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/*  88 */     if (paramInt2 < 0) {
/*  89 */       throw new IllegalArgumentException("length: " + paramInt2);
/*     */     }
/*  91 */     if (paramInt2 == 0) {
/*  92 */       return "";
/*     */     }
/*     */     
/*  95 */     int i = paramInt1 + paramInt2;
/*  96 */     char[] arrayOfChar = new char[paramInt2 << 1];
/*     */     
/*  98 */     int j = paramInt1;
/*  99 */     for (int k = 0; 
/* 100 */         j < i; k += 2) {
/* 101 */       System.arraycopy(HEXDUMP_TABLE, paramByteBuf.getUnsignedByte(j) << 1, arrayOfChar, k, 2);j++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 106 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int hashCode(ByteBuf paramByteBuf)
/*     */   {
/* 114 */     int i = paramByteBuf.readableBytes();
/* 115 */     int j = i >>> 2;
/* 116 */     int k = i & 0x3;
/*     */     
/* 118 */     int m = 1;
/* 119 */     int n = paramByteBuf.readerIndex();
/* 120 */     if (paramByteBuf.order() == ByteOrder.BIG_ENDIAN) {
/* 121 */       for (i1 = j; i1 > 0; i1--) {
/* 122 */         m = 31 * m + paramByteBuf.getInt(n);
/* 123 */         n += 4;
/*     */       }
/*     */     } else {
/* 126 */       for (i1 = j; i1 > 0; i1--) {
/* 127 */         m = 31 * m + swapInt(paramByteBuf.getInt(n));
/* 128 */         n += 4;
/*     */       }
/*     */     }
/*     */     
/* 132 */     for (int i1 = k; i1 > 0; i1--) {
/* 133 */       m = 31 * m + paramByteBuf.getByte(n++);
/*     */     }
/*     */     
/* 136 */     if (m == 0) {
/* 137 */       m = 1;
/*     */     }
/*     */     
/* 140 */     return m;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean equals(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*     */   {
/* 149 */     int i = paramByteBuf1.readableBytes();
/* 150 */     if (i != paramByteBuf2.readableBytes()) {
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     int j = i >>> 3;
/* 155 */     int k = i & 0x7;
/*     */     
/* 157 */     int m = paramByteBuf1.readerIndex();
/* 158 */     int n = paramByteBuf2.readerIndex();
/*     */     
/* 160 */     if (paramByteBuf1.order() == paramByteBuf2.order()) {
/* 161 */       for (i1 = j; i1 > 0; i1--) {
/* 162 */         if (paramByteBuf1.getLong(m) != paramByteBuf2.getLong(n)) {
/* 163 */           return false;
/*     */         }
/* 165 */         m += 8;
/* 166 */         n += 8;
/*     */       }
/*     */     } else {
/* 169 */       for (i1 = j; i1 > 0; i1--) {
/* 170 */         if (paramByteBuf1.getLong(m) != swapLong(paramByteBuf2.getLong(n))) {
/* 171 */           return false;
/*     */         }
/* 173 */         m += 8;
/* 174 */         n += 8;
/*     */       }
/*     */     }
/*     */     
/* 178 */     for (int i1 = k; i1 > 0; i1--) {
/* 179 */       if (paramByteBuf1.getByte(m) != paramByteBuf2.getByte(n)) {
/* 180 */         return false;
/*     */       }
/* 182 */       m++;
/* 183 */       n++;
/*     */     }
/*     */     
/* 186 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int compare(ByteBuf paramByteBuf1, ByteBuf paramByteBuf2)
/*     */   {
/* 194 */     int i = paramByteBuf1.readableBytes();
/* 195 */     int j = paramByteBuf2.readableBytes();
/* 196 */     int k = Math.min(i, j);
/* 197 */     int m = k >>> 2;
/* 198 */     int n = k & 0x3;
/*     */     
/* 200 */     int i1 = paramByteBuf1.readerIndex();
/* 201 */     int i2 = paramByteBuf2.readerIndex();
/*     */     long l1;
/* 203 */     long l2; if (paramByteBuf1.order() == paramByteBuf2.order()) {
/* 204 */       for (i3 = m; i3 > 0; i3--) {
/* 205 */         l1 = paramByteBuf1.getUnsignedInt(i1);
/* 206 */         l2 = paramByteBuf2.getUnsignedInt(i2);
/* 207 */         if (l1 > l2) {
/* 208 */           return 1;
/*     */         }
/* 210 */         if (l1 < l2) {
/* 211 */           return -1;
/*     */         }
/* 213 */         i1 += 4;
/* 214 */         i2 += 4;
/*     */       }
/*     */     } else {
/* 217 */       for (i3 = m; i3 > 0; i3--) {
/* 218 */         l1 = paramByteBuf1.getUnsignedInt(i1);
/* 219 */         l2 = swapInt(paramByteBuf2.getInt(i2)) & 0xFFFFFFFF;
/* 220 */         if (l1 > l2) {
/* 221 */           return 1;
/*     */         }
/* 223 */         if (l1 < l2) {
/* 224 */           return -1;
/*     */         }
/* 226 */         i1 += 4;
/* 227 */         i2 += 4;
/*     */       }
/*     */     }
/*     */     
/* 231 */     for (int i3 = n; i3 > 0; i3--) {
/* 232 */       int i4 = paramByteBuf1.getUnsignedByte(i1);
/* 233 */       int i5 = paramByteBuf2.getUnsignedByte(i2);
/* 234 */       if (i4 > i5) {
/* 235 */         return 1;
/*     */       }
/* 237 */       if (i4 < i5) {
/* 238 */         return -1;
/*     */       }
/* 240 */       i1++;
/* 241 */       i2++;
/*     */     }
/*     */     
/* 244 */     return i - j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int indexOf(ByteBuf paramByteBuf, int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 252 */     if (paramInt1 <= paramInt2) {
/* 253 */       return firstIndexOf(paramByteBuf, paramInt1, paramInt2, paramByte);
/*     */     }
/* 255 */     return lastIndexOf(paramByteBuf, paramInt1, paramInt2, paramByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static short swapShort(short paramShort)
/*     */   {
/* 263 */     return Short.reverseBytes(paramShort);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int swapMedium(int paramInt)
/*     */   {
/* 270 */     int i = paramInt << 16 & 0xFF0000 | paramInt & 0xFF00 | paramInt >>> 16 & 0xFF;
/* 271 */     if ((i & 0x800000) != 0) {
/* 272 */       i |= 0xFF000000;
/*     */     }
/* 274 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int swapInt(int paramInt)
/*     */   {
/* 281 */     return Integer.reverseBytes(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static long swapLong(long paramLong)
/*     */   {
/* 288 */     return Long.reverseBytes(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static ByteBuf readBytes(ByteBufAllocator paramByteBufAllocator, ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 295 */     int i = 1;
/* 296 */     ByteBuf localByteBuf1 = paramByteBufAllocator.buffer(paramInt);
/*     */     try {
/* 298 */       paramByteBuf.readBytes(localByteBuf1);
/* 299 */       i = 0;
/* 300 */       return localByteBuf1;
/*     */     } finally {
/* 302 */       if (i != 0) {
/* 303 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static int firstIndexOf(ByteBuf paramByteBuf, int paramInt1, int paramInt2, byte paramByte) {
/* 309 */     paramInt1 = Math.max(paramInt1, 0);
/* 310 */     if ((paramInt1 >= paramInt2) || (paramByteBuf.capacity() == 0)) {
/* 311 */       return -1;
/*     */     }
/*     */     
/* 314 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 315 */       if (paramByteBuf.getByte(i) == paramByte) {
/* 316 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 320 */     return -1;
/*     */   }
/*     */   
/*     */   private static int lastIndexOf(ByteBuf paramByteBuf, int paramInt1, int paramInt2, byte paramByte) {
/* 324 */     paramInt1 = Math.min(paramInt1, paramByteBuf.capacity());
/* 325 */     if ((paramInt1 < 0) || (paramByteBuf.capacity() == 0)) {
/* 326 */       return -1;
/*     */     }
/*     */     
/* 329 */     for (int i = paramInt1 - 1; i >= paramInt2; i--) {
/* 330 */       if (paramByteBuf.getByte(i) == paramByte) {
/* 331 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 335 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf encodeString(ByteBufAllocator paramByteBufAllocator, CharBuffer paramCharBuffer, Charset paramCharset)
/*     */   {
/* 343 */     return encodeString0(paramByteBufAllocator, false, paramCharBuffer, paramCharset);
/*     */   }
/*     */   
/*     */   static ByteBuf encodeString0(ByteBufAllocator paramByteBufAllocator, boolean paramBoolean, CharBuffer paramCharBuffer, Charset paramCharset) {
/* 347 */     CharsetEncoder localCharsetEncoder = CharsetUtil.getEncoder(paramCharset);
/* 348 */     int i = (int)(paramCharBuffer.remaining() * localCharsetEncoder.maxBytesPerChar());
/* 349 */     int j = 1;
/*     */     ByteBuf localByteBuf1;
/* 351 */     if (paramBoolean) {
/* 352 */       localByteBuf1 = paramByteBufAllocator.heapBuffer(i);
/*     */     } else {
/* 354 */       localByteBuf1 = paramByteBufAllocator.buffer(i);
/*     */     }
/*     */     try {
/* 357 */       ByteBuffer localByteBuffer = localByteBuf1.internalNioBuffer(0, i);
/* 358 */       int k = localByteBuffer.position();
/* 359 */       CoderResult localCoderResult = localCharsetEncoder.encode(paramCharBuffer, localByteBuffer, true);
/* 360 */       if (!localCoderResult.isUnderflow()) {
/* 361 */         localCoderResult.throwException();
/*     */       }
/* 363 */       localCoderResult = localCharsetEncoder.flush(localByteBuffer);
/* 364 */       if (!localCoderResult.isUnderflow()) {
/* 365 */         localCoderResult.throwException();
/*     */       }
/* 367 */       localByteBuf1.writerIndex(localByteBuf1.writerIndex() + localByteBuffer.position() - k);
/* 368 */       j = 0;
/* 369 */       return localByteBuf1;
/*     */     } catch (CharacterCodingException localCharacterCodingException) {
/* 371 */       throw new IllegalStateException(localCharacterCodingException);
/*     */     } finally {
/* 373 */       if (j != 0) {
/* 374 */         localByteBuf1.release();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static String decodeString(ByteBuffer paramByteBuffer, Charset paramCharset) {
/* 380 */     CharsetDecoder localCharsetDecoder = CharsetUtil.getDecoder(paramCharset);
/* 381 */     CharBuffer localCharBuffer = CharBuffer.allocate((int)(paramByteBuffer.remaining() * localCharsetDecoder.maxCharsPerByte()));
/*     */     try
/*     */     {
/* 384 */       CoderResult localCoderResult = localCharsetDecoder.decode(paramByteBuffer, localCharBuffer, true);
/* 385 */       if (!localCoderResult.isUnderflow()) {
/* 386 */         localCoderResult.throwException();
/*     */       }
/* 388 */       localCoderResult = localCharsetDecoder.flush(localCharBuffer);
/* 389 */       if (!localCoderResult.isUnderflow()) {
/* 390 */         localCoderResult.throwException();
/*     */       }
/*     */     } catch (CharacterCodingException localCharacterCodingException) {
/* 393 */       throw new IllegalStateException(localCharacterCodingException);
/*     */     }
/* 395 */     return localCharBuffer.flip().toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteBuf threadLocalDirectBuffer()
/*     */   {
/* 404 */     if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
/* 405 */       return null;
/*     */     }
/*     */     
/* 408 */     if (PlatformDependent.hasUnsafe()) {
/* 409 */       return ThreadLocalUnsafeDirectByteBuf.newInstance();
/*     */     }
/* 411 */     return ThreadLocalDirectByteBuf.newInstance();
/*     */   }
/*     */   
/*     */   static final class ThreadLocalUnsafeDirectByteBuf
/*     */     extends UnpooledUnsafeDirectByteBuf
/*     */   {
/* 417 */     private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler()
/*     */     {
/*     */ 
/*     */       protected ByteBufUtil.ThreadLocalUnsafeDirectByteBuf newObject(Recycler.Handle paramAnonymousHandle) {
/* 421 */         return new ByteBufUtil.ThreadLocalUnsafeDirectByteBuf(paramAnonymousHandle, null); }
/*     */     };
/*     */     private final Recycler.Handle handle;
/*     */     
/*     */     static ThreadLocalUnsafeDirectByteBuf newInstance() {
/* 426 */       ThreadLocalUnsafeDirectByteBuf localThreadLocalUnsafeDirectByteBuf = (ThreadLocalUnsafeDirectByteBuf)RECYCLER.get();
/* 427 */       localThreadLocalUnsafeDirectByteBuf.setRefCnt(1);
/* 428 */       return localThreadLocalUnsafeDirectByteBuf;
/*     */     }
/*     */     
/*     */ 
/*     */     private ThreadLocalUnsafeDirectByteBuf(Recycler.Handle paramHandle)
/*     */     {
/* 434 */       super(256, Integer.MAX_VALUE);
/* 435 */       this.handle = paramHandle;
/*     */     }
/*     */     
/*     */     protected void deallocate()
/*     */     {
/* 440 */       if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
/* 441 */         super.deallocate();
/*     */       } else {
/* 443 */         clear();
/* 444 */         RECYCLER.recycle(this, this.handle);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static final class ThreadLocalDirectByteBuf extends UnpooledDirectByteBuf
/*     */   {
/* 451 */     private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler()
/*     */     {
/*     */ 
/* 454 */       protected ByteBufUtil.ThreadLocalDirectByteBuf newObject(Recycler.Handle paramAnonymousHandle) { return new ByteBufUtil.ThreadLocalDirectByteBuf(paramAnonymousHandle, null); }
/*     */     };
/*     */     private final Recycler.Handle handle;
/*     */     
/*     */     static ThreadLocalDirectByteBuf newInstance() {
/* 459 */       ThreadLocalDirectByteBuf localThreadLocalDirectByteBuf = (ThreadLocalDirectByteBuf)RECYCLER.get();
/* 460 */       localThreadLocalDirectByteBuf.setRefCnt(1);
/* 461 */       return localThreadLocalDirectByteBuf;
/*     */     }
/*     */     
/*     */ 
/*     */     private ThreadLocalDirectByteBuf(Recycler.Handle paramHandle)
/*     */     {
/* 467 */       super(256, Integer.MAX_VALUE);
/* 468 */       this.handle = paramHandle;
/*     */     }
/*     */     
/*     */     protected void deallocate()
/*     */     {
/* 473 */       if (capacity() > ByteBufUtil.THREAD_LOCAL_BUFFER_SIZE) {
/* 474 */         super.deallocate();
/*     */       } else {
/* 476 */         clear();
/* 477 */         RECYCLER.recycle(this, this.handle);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ByteBufUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */