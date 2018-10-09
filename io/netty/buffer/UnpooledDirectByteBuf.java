/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnpooledDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ByteBufAllocator alloc;
/*     */   private ByteBuffer buffer;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   private int capacity;
/*     */   private boolean doNotFree;
/*     */   
/*     */   protected UnpooledDirectByteBuf(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2)
/*     */   {
/*  50 */     super(paramInt2);
/*  51 */     if (paramByteBufAllocator == null) {
/*  52 */       throw new NullPointerException("alloc");
/*     */     }
/*  54 */     if (paramInt1 < 0) {
/*  55 */       throw new IllegalArgumentException("initialCapacity: " + paramInt1);
/*     */     }
/*  57 */     if (paramInt2 < 0) {
/*  58 */       throw new IllegalArgumentException("maxCapacity: " + paramInt2);
/*     */     }
/*  60 */     if (paramInt1 > paramInt2) {
/*  61 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
/*     */     }
/*     */     
/*     */ 
/*  65 */     this.alloc = paramByteBufAllocator;
/*  66 */     setByteBuffer(ByteBuffer.allocateDirect(paramInt1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected UnpooledDirectByteBuf(ByteBufAllocator paramByteBufAllocator, ByteBuffer paramByteBuffer, int paramInt)
/*     */   {
/*  75 */     super(paramInt);
/*  76 */     if (paramByteBufAllocator == null) {
/*  77 */       throw new NullPointerException("alloc");
/*     */     }
/*  79 */     if (paramByteBuffer == null) {
/*  80 */       throw new NullPointerException("initialBuffer");
/*     */     }
/*  82 */     if (!paramByteBuffer.isDirect()) {
/*  83 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/*  85 */     if (paramByteBuffer.isReadOnly()) {
/*  86 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/*  89 */     int i = paramByteBuffer.remaining();
/*  90 */     if (i > paramInt) {
/*  91 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt) }));
/*     */     }
/*     */     
/*     */ 
/*  95 */     this.alloc = paramByteBufAllocator;
/*  96 */     this.doNotFree = true;
/*  97 */     setByteBuffer(paramByteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
/*  98 */     writerIndex(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ByteBuffer allocateDirect(int paramInt)
/*     */   {
/* 105 */     return ByteBuffer.allocateDirect(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void freeDirect(ByteBuffer paramByteBuffer)
/*     */   {
/* 112 */     PlatformDependent.freeDirectBuffer(paramByteBuffer);
/*     */   }
/*     */   
/*     */   private void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 116 */     ByteBuffer localByteBuffer = this.buffer;
/* 117 */     if (localByteBuffer != null) {
/* 118 */       if (this.doNotFree) {
/* 119 */         this.doNotFree = false;
/*     */       } else {
/* 121 */         freeDirect(localByteBuffer);
/*     */       }
/*     */     }
/*     */     
/* 125 */     this.buffer = paramByteBuffer;
/* 126 */     this.tmpNioBuf = null;
/* 127 */     this.capacity = paramByteBuffer.remaining();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 137 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 142 */     ensureAccessible();
/* 143 */     if ((paramInt < 0) || (paramInt > maxCapacity())) {
/* 144 */       throw new IllegalArgumentException("newCapacity: " + paramInt);
/*     */     }
/*     */     
/* 147 */     int i = readerIndex();
/* 148 */     int j = writerIndex();
/*     */     
/* 150 */     int k = this.capacity;
/* 151 */     ByteBuffer localByteBuffer1; ByteBuffer localByteBuffer2; if (paramInt > k) {
/* 152 */       localByteBuffer1 = this.buffer;
/* 153 */       localByteBuffer2 = allocateDirect(paramInt);
/* 154 */       localByteBuffer1.position(0).limit(localByteBuffer1.capacity());
/* 155 */       localByteBuffer2.position(0).limit(localByteBuffer1.capacity());
/* 156 */       localByteBuffer2.put(localByteBuffer1);
/* 157 */       localByteBuffer2.clear();
/* 158 */       setByteBuffer(localByteBuffer2);
/* 159 */     } else if (paramInt < k) {
/* 160 */       localByteBuffer1 = this.buffer;
/* 161 */       localByteBuffer2 = allocateDirect(paramInt);
/* 162 */       if (i < paramInt) {
/* 163 */         if (j > paramInt) {
/* 164 */           writerIndex(j = paramInt);
/*     */         }
/* 166 */         localByteBuffer1.position(i).limit(j);
/* 167 */         localByteBuffer2.position(i).limit(j);
/* 168 */         localByteBuffer2.put(localByteBuffer1);
/* 169 */         localByteBuffer2.clear();
/*     */       } else {
/* 171 */         setIndex(paramInt, paramInt);
/*     */       }
/* 173 */       setByteBuffer(localByteBuffer2);
/*     */     }
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/* 180 */     return this.alloc;
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/* 185 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 195 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 200 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 210 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 215 */     ensureAccessible();
/* 216 */     return _getByte(paramInt);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 221 */     return this.buffer.get(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 226 */     ensureAccessible();
/* 227 */     return _getShort(paramInt);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 232 */     return this.buffer.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 237 */     ensureAccessible();
/* 238 */     return _getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 243 */     return (getByte(paramInt) & 0xFF) << 16 | (getByte(paramInt + 1) & 0xFF) << 8 | getByte(paramInt + 2) & 0xFF;
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 248 */     ensureAccessible();
/* 249 */     return _getInt(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 254 */     return this.buffer.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 259 */     ensureAccessible();
/* 260 */     return _getLong(paramInt);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 265 */     return this.buffer.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 270 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 271 */     if (paramByteBuf.hasArray()) {
/* 272 */       getBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/* 273 */     } else if (paramByteBuf.nioBufferCount() > 0) {
/* 274 */       for (ByteBuffer localByteBuffer : paramByteBuf.nioBuffers(paramInt2, paramInt3)) {
/* 275 */         int k = localByteBuffer.remaining();
/* 276 */         getBytes(paramInt1, localByteBuffer);
/* 277 */         paramInt1 += k;
/*     */       }
/*     */     } else {
/* 280 */       paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/* 282 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 287 */     getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3, false);
/* 288 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 292 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/*     */     
/* 294 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt3)) {
/* 295 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramArrayOfByte.length) }));
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/*     */     
/* 300 */     if (paramBoolean) {
/* 301 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 303 */       localByteBuffer = this.buffer.duplicate();
/*     */     }
/* 305 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt3);
/* 306 */     localByteBuffer.get(paramArrayOfByte, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 311 */     checkReadableBytes(paramInt2);
/* 312 */     getBytes(this.readerIndex, paramArrayOfByte, paramInt1, paramInt2, true);
/* 313 */     this.readerIndex += paramInt2;
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 319 */     getBytes(paramInt, paramByteBuffer, false);
/* 320 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt, ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 324 */     checkIndex(paramInt);
/* 325 */     if (paramByteBuffer == null) {
/* 326 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 329 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/*     */     ByteBuffer localByteBuffer;
/* 331 */     if (paramBoolean) {
/* 332 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 334 */       localByteBuffer = this.buffer.duplicate();
/*     */     }
/* 336 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 337 */     paramByteBuffer.put(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 342 */     int i = paramByteBuffer.remaining();
/* 343 */     checkReadableBytes(i);
/* 344 */     getBytes(this.readerIndex, paramByteBuffer, true);
/* 345 */     this.readerIndex += i;
/* 346 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 351 */     ensureAccessible();
/* 352 */     _setByte(paramInt1, paramInt2);
/* 353 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 358 */     this.buffer.put(paramInt1, (byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 363 */     ensureAccessible();
/* 364 */     _setShort(paramInt1, paramInt2);
/* 365 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 370 */     this.buffer.putShort(paramInt1, (short)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 375 */     ensureAccessible();
/* 376 */     _setMedium(paramInt1, paramInt2);
/* 377 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 382 */     setByte(paramInt1, (byte)(paramInt2 >>> 16));
/* 383 */     setByte(paramInt1 + 1, (byte)(paramInt2 >>> 8));
/* 384 */     setByte(paramInt1 + 2, (byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 389 */     ensureAccessible();
/* 390 */     _setInt(paramInt1, paramInt2);
/* 391 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 396 */     this.buffer.putInt(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 401 */     ensureAccessible();
/* 402 */     _setLong(paramInt, paramLong);
/* 403 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 408 */     this.buffer.putLong(paramInt, paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 413 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 414 */     if (paramByteBuf.nioBufferCount() > 0) {
/* 415 */       for (ByteBuffer localByteBuffer : paramByteBuf.nioBuffers(paramInt2, paramInt3)) {
/* 416 */         int k = localByteBuffer.remaining();
/* 417 */         setBytes(paramInt1, localByteBuffer);
/* 418 */         paramInt1 += k;
/*     */       }
/*     */     } else {
/* 421 */       paramByteBuf.getBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/* 423 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 428 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 429 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 430 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt3);
/* 431 */     localByteBuffer.put(paramArrayOfByte, paramInt2, paramInt3);
/* 432 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 437 */     ensureAccessible();
/* 438 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 439 */     if (paramByteBuffer == localByteBuffer) {
/* 440 */       paramByteBuffer = paramByteBuffer.duplicate();
/*     */     }
/*     */     
/* 443 */     localByteBuffer.clear().position(paramInt).limit(paramInt + paramByteBuffer.remaining());
/* 444 */     localByteBuffer.put(paramByteBuffer);
/* 445 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 450 */     getBytes(paramInt1, paramOutputStream, paramInt2, false);
/* 451 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2, boolean paramBoolean) throws IOException {
/* 455 */     ensureAccessible();
/* 456 */     if (paramInt2 == 0) {
/* 457 */       return;
/*     */     }
/*     */     
/* 460 */     if (this.buffer.hasArray()) {
/* 461 */       paramOutputStream.write(this.buffer.array(), paramInt1 + this.buffer.arrayOffset(), paramInt2);
/*     */     } else {
/* 463 */       byte[] arrayOfByte = new byte[paramInt2];
/*     */       ByteBuffer localByteBuffer;
/* 465 */       if (paramBoolean) {
/* 466 */         localByteBuffer = internalNioBuffer();
/*     */       } else {
/* 468 */         localByteBuffer = this.buffer.duplicate();
/*     */       }
/* 470 */       localByteBuffer.clear().position(paramInt1);
/* 471 */       localByteBuffer.get(arrayOfByte);
/* 472 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*     */   {
/* 478 */     checkReadableBytes(paramInt);
/* 479 */     getBytes(this.readerIndex, paramOutputStream, paramInt, true);
/* 480 */     this.readerIndex += paramInt;
/* 481 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 486 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 490 */     ensureAccessible();
/* 491 */     if (paramInt2 == 0) {
/* 492 */       return 0;
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/* 496 */     if (paramBoolean) {
/* 497 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 499 */       localByteBuffer = this.buffer.duplicate();
/*     */     }
/* 501 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/* 502 */     return paramGatheringByteChannel.write(localByteBuffer);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 507 */     checkReadableBytes(paramInt);
/* 508 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 509 */     this.readerIndex += i;
/* 510 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 515 */     ensureAccessible();
/* 516 */     if (this.buffer.hasArray()) {
/* 517 */       return paramInputStream.read(this.buffer.array(), this.buffer.arrayOffset() + paramInt1, paramInt2);
/*     */     }
/* 519 */     byte[] arrayOfByte = new byte[paramInt2];
/* 520 */     int i = paramInputStream.read(arrayOfByte);
/* 521 */     if (i <= 0) {
/* 522 */       return i;
/*     */     }
/* 524 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 525 */     localByteBuffer.clear().position(paramInt1);
/* 526 */     localByteBuffer.put(arrayOfByte, 0, i);
/* 527 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 533 */     ensureAccessible();
/* 534 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 535 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     try {
/* 537 */       return paramScatteringByteChannel.read(this.tmpNioBuf);
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 539 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public int nioBufferCount()
/*     */   {
/* 545 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 550 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 555 */     ensureAccessible();
/*     */     ByteBuffer localByteBuffer;
/*     */     try {
/* 558 */       localByteBuffer = (ByteBuffer)this.buffer.duplicate().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 560 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (paramInt1 + paramInt2));
/*     */     }
/*     */     
/* 563 */     return alloc().directBuffer(paramInt2, maxCapacity()).writeBytes(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 568 */     checkIndex(paramInt1, paramInt2);
/* 569 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 573 */     ByteBuffer localByteBuffer = this.tmpNioBuf;
/* 574 */     if (localByteBuffer == null) {
/* 575 */       this.tmpNioBuf = (localByteBuffer = this.buffer.duplicate());
/*     */     }
/* 577 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 582 */     checkIndex(paramInt1, paramInt2);
/* 583 */     return ((ByteBuffer)this.buffer.duplicate().position(paramInt1).limit(paramInt1 + paramInt2)).slice();
/*     */   }
/*     */   
/*     */   protected void deallocate()
/*     */   {
/* 588 */     ByteBuffer localByteBuffer = this.buffer;
/* 589 */     if (localByteBuffer == null) {
/* 590 */       return;
/*     */     }
/*     */     
/* 593 */     this.buffer = null;
/*     */     
/* 595 */     if (!this.doNotFree) {
/* 596 */       freeDirect(localByteBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/* 602 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnpooledDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */