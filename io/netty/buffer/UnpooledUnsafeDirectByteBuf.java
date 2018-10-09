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
/*     */ public class UnpooledUnsafeDirectByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*  36 */   private static final boolean NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*     */   private long memoryAddress;
/*     */   
/*     */   private ByteBuffer buffer;
/*     */   
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   private int capacity;
/*     */   
/*     */   private boolean doNotFree;
/*     */   
/*     */ 
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2)
/*     */   {
/*  53 */     super(paramInt2);
/*  54 */     if (paramByteBufAllocator == null) {
/*  55 */       throw new NullPointerException("alloc");
/*     */     }
/*  57 */     if (paramInt1 < 0) {
/*  58 */       throw new IllegalArgumentException("initialCapacity: " + paramInt1);
/*     */     }
/*  60 */     if (paramInt2 < 0) {
/*  61 */       throw new IllegalArgumentException("maxCapacity: " + paramInt2);
/*     */     }
/*  63 */     if (paramInt1 > paramInt2) {
/*  64 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
/*     */     }
/*     */     
/*     */ 
/*  68 */     this.alloc = paramByteBufAllocator;
/*  69 */     setByteBuffer(allocateDirect(paramInt1));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator paramByteBufAllocator, ByteBuffer paramByteBuffer, int paramInt)
/*     */   {
/*  78 */     super(paramInt);
/*  79 */     if (paramByteBufAllocator == null) {
/*  80 */       throw new NullPointerException("alloc");
/*     */     }
/*  82 */     if (paramByteBuffer == null) {
/*  83 */       throw new NullPointerException("initialBuffer");
/*     */     }
/*  85 */     if (!paramByteBuffer.isDirect()) {
/*  86 */       throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
/*     */     }
/*  88 */     if (paramByteBuffer.isReadOnly()) {
/*  89 */       throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
/*     */     }
/*     */     
/*  92 */     int i = paramByteBuffer.remaining();
/*  93 */     if (i > paramInt) {
/*  94 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt) }));
/*     */     }
/*     */     
/*     */ 
/*  98 */     this.alloc = paramByteBufAllocator;
/*  99 */     this.doNotFree = true;
/* 100 */     setByteBuffer(paramByteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
/* 101 */     writerIndex(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ByteBuffer allocateDirect(int paramInt)
/*     */   {
/* 108 */     return ByteBuffer.allocateDirect(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void freeDirect(ByteBuffer paramByteBuffer)
/*     */   {
/* 115 */     PlatformDependent.freeDirectBuffer(paramByteBuffer);
/*     */   }
/*     */   
/*     */   private void setByteBuffer(ByteBuffer paramByteBuffer) {
/* 119 */     ByteBuffer localByteBuffer = this.buffer;
/* 120 */     if (localByteBuffer != null) {
/* 121 */       if (this.doNotFree) {
/* 122 */         this.doNotFree = false;
/*     */       } else {
/* 124 */         freeDirect(localByteBuffer);
/*     */       }
/*     */     }
/*     */     
/* 128 */     this.buffer = paramByteBuffer;
/* 129 */     this.memoryAddress = PlatformDependent.directBufferAddress(paramByteBuffer);
/* 130 */     this.tmpNioBuf = null;
/* 131 */     this.capacity = paramByteBuffer.remaining();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 141 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 146 */     ensureAccessible();
/* 147 */     if ((paramInt < 0) || (paramInt > maxCapacity())) {
/* 148 */       throw new IllegalArgumentException("newCapacity: " + paramInt);
/*     */     }
/*     */     
/* 151 */     int i = readerIndex();
/* 152 */     int j = writerIndex();
/*     */     
/* 154 */     int k = this.capacity;
/* 155 */     ByteBuffer localByteBuffer1; ByteBuffer localByteBuffer2; if (paramInt > k) {
/* 156 */       localByteBuffer1 = this.buffer;
/* 157 */       localByteBuffer2 = allocateDirect(paramInt);
/* 158 */       localByteBuffer1.position(0).limit(localByteBuffer1.capacity());
/* 159 */       localByteBuffer2.position(0).limit(localByteBuffer1.capacity());
/* 160 */       localByteBuffer2.put(localByteBuffer1);
/* 161 */       localByteBuffer2.clear();
/* 162 */       setByteBuffer(localByteBuffer2);
/* 163 */     } else if (paramInt < k) {
/* 164 */       localByteBuffer1 = this.buffer;
/* 165 */       localByteBuffer2 = allocateDirect(paramInt);
/* 166 */       if (i < paramInt) {
/* 167 */         if (j > paramInt) {
/* 168 */           writerIndex(j = paramInt);
/*     */         }
/* 170 */         localByteBuffer1.position(i).limit(j);
/* 171 */         localByteBuffer2.position(i).limit(j);
/* 172 */         localByteBuffer2.put(localByteBuffer1);
/* 173 */         localByteBuffer2.clear();
/*     */       } else {
/* 175 */         setIndex(paramInt, paramInt);
/*     */       }
/* 177 */       setByteBuffer(localByteBuffer2);
/*     */     }
/* 179 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/* 184 */     return this.alloc;
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/* 189 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 194 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 199 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 204 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 209 */     return true;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 214 */     return this.memoryAddress;
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 219 */     return PlatformDependent.getByte(addr(paramInt));
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 224 */     short s = PlatformDependent.getShort(addr(paramInt));
/* 225 */     return NATIVE_ORDER ? s : Short.reverseBytes(s);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 230 */     long l = addr(paramInt);
/* 231 */     return (PlatformDependent.getByte(l) & 0xFF) << 16 | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | PlatformDependent.getByte(l + 2L) & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 238 */     int i = PlatformDependent.getInt(addr(paramInt));
/* 239 */     return NATIVE_ORDER ? i : Integer.reverseBytes(i);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 244 */     long l = PlatformDependent.getLong(addr(paramInt));
/* 245 */     return NATIVE_ORDER ? l : Long.reverseBytes(l);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 250 */     checkIndex(paramInt1, paramInt3);
/* 251 */     if (paramByteBuf == null) {
/* 252 */       throw new NullPointerException("dst");
/*     */     }
/* 254 */     if ((paramInt2 < 0) || (paramInt2 > paramByteBuf.capacity() - paramInt3)) {
/* 255 */       throw new IndexOutOfBoundsException("dstIndex: " + paramInt2);
/*     */     }
/*     */     
/* 258 */     if (paramByteBuf.hasMemoryAddress()) {
/* 259 */       PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.memoryAddress() + paramInt2, paramInt3);
/* 260 */     } else if (paramByteBuf.hasArray()) {
/* 261 */       PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/* 263 */       paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/* 265 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 270 */     checkIndex(paramInt1, paramInt3);
/* 271 */     if (paramArrayOfByte == null) {
/* 272 */       throw new NullPointerException("dst");
/*     */     }
/* 274 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt3)) {
/* 275 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramArrayOfByte.length) }));
/*     */     }
/*     */     
/*     */ 
/* 279 */     if (paramInt3 != 0) {
/* 280 */       PlatformDependent.copyMemory(addr(paramInt1), paramArrayOfByte, paramInt2, paramInt3);
/*     */     }
/* 282 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 287 */     getBytes(paramInt, paramByteBuffer, false);
/* 288 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt, ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 292 */     checkIndex(paramInt);
/* 293 */     if (paramByteBuffer == null) {
/* 294 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 297 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/*     */     ByteBuffer localByteBuffer;
/* 299 */     if (paramBoolean) {
/* 300 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 302 */       localByteBuffer = this.buffer.duplicate();
/*     */     }
/* 304 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 305 */     paramByteBuffer.put(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 310 */     int i = paramByteBuffer.remaining();
/* 311 */     checkReadableBytes(i);
/* 312 */     getBytes(this.readerIndex, paramByteBuffer, true);
/* 313 */     this.readerIndex += i;
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 319 */     PlatformDependent.putByte(addr(paramInt1), (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 324 */     PlatformDependent.putShort(addr(paramInt1), NATIVE_ORDER ? (short)paramInt2 : Short.reverseBytes((short)paramInt2));
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 329 */     long l = addr(paramInt1);
/* 330 */     PlatformDependent.putByte(l, (byte)(paramInt2 >>> 16));
/* 331 */     PlatformDependent.putByte(l + 1L, (byte)(paramInt2 >>> 8));
/* 332 */     PlatformDependent.putByte(l + 2L, (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 337 */     PlatformDependent.putInt(addr(paramInt1), NATIVE_ORDER ? paramInt2 : Integer.reverseBytes(paramInt2));
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 342 */     PlatformDependent.putLong(addr(paramInt), NATIVE_ORDER ? paramLong : Long.reverseBytes(paramLong));
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 347 */     checkIndex(paramInt1, paramInt3);
/* 348 */     if (paramByteBuf == null) {
/* 349 */       throw new NullPointerException("src");
/*     */     }
/* 351 */     if ((paramInt2 < 0) || (paramInt2 > paramByteBuf.capacity() - paramInt3)) {
/* 352 */       throw new IndexOutOfBoundsException("srcIndex: " + paramInt2);
/*     */     }
/*     */     
/* 355 */     if (paramInt3 != 0) {
/* 356 */       if (paramByteBuf.hasMemoryAddress()) {
/* 357 */         PlatformDependent.copyMemory(paramByteBuf.memoryAddress() + paramInt2, addr(paramInt1), paramInt3);
/* 358 */       } else if (paramByteBuf.hasArray()) {
/* 359 */         PlatformDependent.copyMemory(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, addr(paramInt1), paramInt3);
/*     */       } else {
/* 361 */         paramByteBuf.getBytes(paramInt2, this, paramInt1, paramInt3);
/*     */       }
/*     */     }
/* 364 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 369 */     checkIndex(paramInt1, paramInt3);
/* 370 */     if (paramInt3 != 0) {
/* 371 */       PlatformDependent.copyMemory(paramArrayOfByte, paramInt2, addr(paramInt1), paramInt3);
/*     */     }
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 378 */     ensureAccessible();
/* 379 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 380 */     if (paramByteBuffer == localByteBuffer) {
/* 381 */       paramByteBuffer = paramByteBuffer.duplicate();
/*     */     }
/*     */     
/* 384 */     localByteBuffer.clear().position(paramInt).limit(paramInt + paramByteBuffer.remaining());
/* 385 */     localByteBuffer.put(paramByteBuffer);
/* 386 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 391 */     ensureAccessible();
/* 392 */     if (paramInt2 != 0) {
/* 393 */       byte[] arrayOfByte = new byte[paramInt2];
/* 394 */       PlatformDependent.copyMemory(addr(paramInt1), arrayOfByte, 0, paramInt2);
/* 395 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/* 397 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 402 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 406 */     ensureAccessible();
/* 407 */     if (paramInt2 == 0) {
/* 408 */       return 0;
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/* 412 */     if (paramBoolean) {
/* 413 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 415 */       localByteBuffer = this.buffer.duplicate();
/*     */     }
/* 417 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/* 418 */     return paramGatheringByteChannel.write(localByteBuffer);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 423 */     checkReadableBytes(paramInt);
/* 424 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 425 */     this.readerIndex += i;
/* 426 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 431 */     checkIndex(paramInt1, paramInt2);
/* 432 */     byte[] arrayOfByte = new byte[paramInt2];
/* 433 */     int i = paramInputStream.read(arrayOfByte);
/* 434 */     if (i > 0) {
/* 435 */       PlatformDependent.copyMemory(arrayOfByte, 0, addr(paramInt1), i);
/*     */     }
/* 437 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 442 */     ensureAccessible();
/* 443 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 444 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     try {
/* 446 */       return paramScatteringByteChannel.read(localByteBuffer);
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 448 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public int nioBufferCount()
/*     */   {
/* 454 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 459 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 464 */     checkIndex(paramInt1, paramInt2);
/* 465 */     ByteBuf localByteBuf = alloc().directBuffer(paramInt2, maxCapacity());
/* 466 */     if (paramInt2 != 0) {
/* 467 */       if (localByteBuf.hasMemoryAddress()) {
/* 468 */         PlatformDependent.copyMemory(addr(paramInt1), localByteBuf.memoryAddress(), paramInt2);
/* 469 */         localByteBuf.setIndex(0, paramInt2);
/*     */       } else {
/* 471 */         localByteBuf.writeBytes(this, paramInt1, paramInt2);
/*     */       }
/*     */     }
/* 474 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 479 */     checkIndex(paramInt1, paramInt2);
/* 480 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 484 */     ByteBuffer localByteBuffer = this.tmpNioBuf;
/* 485 */     if (localByteBuffer == null) {
/* 486 */       this.tmpNioBuf = (localByteBuffer = this.buffer.duplicate());
/*     */     }
/* 488 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 493 */     checkIndex(paramInt1, paramInt2);
/* 494 */     return ((ByteBuffer)this.buffer.duplicate().position(paramInt1).limit(paramInt1 + paramInt2)).slice();
/*     */   }
/*     */   
/*     */   protected void deallocate()
/*     */   {
/* 499 */     ByteBuffer localByteBuffer = this.buffer;
/* 500 */     if (localByteBuffer == null) {
/* 501 */       return;
/*     */     }
/*     */     
/* 504 */     this.buffer = null;
/*     */     
/* 506 */     if (!this.doNotFree) {
/* 507 */       freeDirect(localByteBuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/* 513 */     return null;
/*     */   }
/*     */   
/*     */   long addr(int paramInt) {
/* 517 */     return this.memoryAddress + paramInt;
/*     */   }
/*     */   
/*     */   protected SwappedByteBuf newSwappedByteBuf()
/*     */   {
/* 522 */     return new UnsafeDirectSwappedByteBuf(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnpooledUnsafeDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */