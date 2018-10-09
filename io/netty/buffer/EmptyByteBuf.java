/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EmptyByteBuf
/*     */   extends ByteBuf
/*     */ {
/*  37 */   private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocateDirect(0);
/*     */   private static final long EMPTY_BYTE_BUFFER_ADDRESS;
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*  41 */   static { long l = 0L;
/*     */     try {
/*  43 */       if (PlatformDependent.hasUnsafe()) {
/*  44 */         l = PlatformDependent.directBufferAddress(EMPTY_BYTE_BUFFER);
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable) {}
/*     */     
/*  49 */     EMPTY_BYTE_BUFFER_ADDRESS = l;
/*     */   }
/*     */   
/*     */ 
/*     */   private final ByteOrder order;
/*     */   private final String str;
/*     */   private EmptyByteBuf swapped;
/*     */   public EmptyByteBuf(ByteBufAllocator paramByteBufAllocator)
/*     */   {
/*  58 */     this(paramByteBufAllocator, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */   
/*     */   private EmptyByteBuf(ByteBufAllocator paramByteBufAllocator, ByteOrder paramByteOrder) {
/*  62 */     if (paramByteBufAllocator == null) {
/*  63 */       throw new NullPointerException("alloc");
/*     */     }
/*     */     
/*  66 */     this.alloc = paramByteBufAllocator;
/*  67 */     this.order = paramByteOrder;
/*  68 */     this.str = (StringUtil.simpleClassName(this) + (paramByteOrder == ByteOrder.BIG_ENDIAN ? "BE" : "LE"));
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/*  73 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/*  78 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  83 */     return this.alloc;
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  88 */     return this.order;
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  98 */     return true;
/*     */   }
/*     */   
/*     */   public int maxCapacity()
/*     */   {
/* 103 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder paramByteOrder)
/*     */   {
/* 108 */     if (paramByteOrder == null) {
/* 109 */       throw new NullPointerException("endianness");
/*     */     }
/* 111 */     if (paramByteOrder == order()) {
/* 112 */       return this;
/*     */     }
/*     */     
/* 115 */     EmptyByteBuf localEmptyByteBuf = this.swapped;
/* 116 */     if (localEmptyByteBuf != null) {
/* 117 */       return localEmptyByteBuf;
/*     */     }
/*     */     
/* 120 */     this.swapped = (localEmptyByteBuf = new EmptyByteBuf(alloc(), paramByteOrder));
/* 121 */     return localEmptyByteBuf;
/*     */   }
/*     */   
/*     */   public int readerIndex()
/*     */   {
/* 126 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int paramInt)
/*     */   {
/* 131 */     return checkIndex(paramInt);
/*     */   }
/*     */   
/*     */   public int writerIndex()
/*     */   {
/* 136 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int paramInt)
/*     */   {
/* 141 */     return checkIndex(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int paramInt1, int paramInt2)
/*     */   {
/* 146 */     checkIndex(paramInt1);
/* 147 */     checkIndex(paramInt2);
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public int readableBytes()
/*     */   {
/* 153 */     return 0;
/*     */   }
/*     */   
/*     */   public int writableBytes()
/*     */   {
/* 158 */     return 0;
/*     */   }
/*     */   
/*     */   public int maxWritableBytes()
/*     */   {
/* 163 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isReadable()
/*     */   {
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isWritable()
/*     */   {
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public ByteBuf clear()
/*     */   {
/* 178 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex()
/*     */   {
/* 183 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex()
/*     */   {
/* 188 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex()
/*     */   {
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex()
/*     */   {
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes()
/*     */   {
/* 203 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes()
/*     */   {
/* 208 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int paramInt)
/*     */   {
/* 213 */     if (paramInt < 0) {
/* 214 */       throw new IllegalArgumentException("minWritableBytes: " + paramInt + " (expected: >= 0)");
/*     */     }
/* 216 */     if (paramInt != 0) {
/* 217 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 219 */     return this;
/*     */   }
/*     */   
/*     */   public int ensureWritable(int paramInt, boolean paramBoolean)
/*     */   {
/* 224 */     if (paramInt < 0) {
/* 225 */       throw new IllegalArgumentException("minWritableBytes: " + paramInt + " (expected: >= 0)");
/*     */     }
/*     */     
/* 228 */     if (paramInt == 0) {
/* 229 */       return 0;
/*     */     }
/*     */     
/* 232 */     return 1;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int paramInt)
/*     */   {
/* 237 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 242 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int paramInt)
/*     */   {
/* 247 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 252 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int paramInt)
/*     */   {
/* 257 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int getMedium(int paramInt)
/*     */   {
/* 262 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 267 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/* 272 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int paramInt)
/*     */   {
/* 277 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/* 282 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public char getChar(int paramInt)
/*     */   {
/* 287 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public float getFloat(int paramInt)
/*     */   {
/* 292 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public double getDouble(int paramInt)
/*     */   {
/* 297 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 302 */     return checkIndex(paramInt, paramByteBuf.writableBytes());
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 307 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 312 */     return checkIndex(paramInt1, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 317 */     return checkIndex(paramInt, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 322 */     return checkIndex(paramInt1, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 327 */     return checkIndex(paramInt, paramByteBuffer.remaining());
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2)
/*     */   {
/* 332 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2)
/*     */   {
/* 337 */     checkIndex(paramInt1, paramInt2);
/* 338 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*     */   {
/* 343 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 348 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 353 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 358 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 363 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 368 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int paramInt1, int paramInt2)
/*     */   {
/* 373 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int paramInt, float paramFloat)
/*     */   {
/* 378 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int paramInt, double paramDouble)
/*     */   {
/* 383 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*     */   {
/* 388 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*     */   {
/* 393 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 398 */     return checkIndex(paramInt1, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/* 403 */     return checkIndex(paramInt, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 408 */     return checkIndex(paramInt1, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 413 */     return checkIndex(paramInt, paramByteBuffer.remaining());
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2)
/*     */   {
/* 418 */     checkIndex(paramInt1, paramInt2);
/* 419 */     return 0;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2)
/*     */   {
/* 424 */     checkIndex(paramInt1, paramInt2);
/* 425 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int paramInt1, int paramInt2)
/*     */   {
/* 430 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public boolean readBoolean()
/*     */   {
/* 435 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public byte readByte()
/*     */   {
/* 440 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte()
/*     */   {
/* 445 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public short readShort()
/*     */   {
/* 450 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort()
/*     */   {
/* 455 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int readMedium()
/*     */   {
/* 460 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium()
/*     */   {
/* 465 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public int readInt()
/*     */   {
/* 470 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt()
/*     */   {
/* 475 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public long readLong()
/*     */   {
/* 480 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public char readChar()
/*     */   {
/* 485 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public float readFloat()
/*     */   {
/* 490 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public double readDouble()
/*     */   {
/* 495 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int paramInt)
/*     */   {
/* 500 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int paramInt)
/*     */   {
/* 505 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf)
/*     */   {
/* 510 */     return checkLength(paramByteBuf.writableBytes());
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 515 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 520 */     return checkLength(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte)
/*     */   {
/* 525 */     return checkLength(paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 530 */     return checkLength(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 535 */     return checkLength(paramByteBuffer.remaining());
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt)
/*     */   {
/* 540 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt)
/*     */   {
/* 545 */     checkLength(paramInt);
/* 546 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int paramInt)
/*     */   {
/* 551 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean paramBoolean)
/*     */   {
/* 556 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int paramInt)
/*     */   {
/* 561 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int paramInt)
/*     */   {
/* 566 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int paramInt)
/*     */   {
/* 571 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int paramInt)
/*     */   {
/* 576 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long paramLong)
/*     */   {
/* 581 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int paramInt)
/*     */   {
/* 586 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float paramFloat)
/*     */   {
/* 591 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double paramDouble)
/*     */   {
/* 596 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf)
/*     */   {
/* 601 */     throw new IndexOutOfBoundsException();
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*     */   {
/* 606 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 611 */     return checkLength(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte)
/*     */   {
/* 616 */     return checkLength(paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 621 */     return checkLength(paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 626 */     return checkLength(paramByteBuffer.remaining());
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream paramInputStream, int paramInt)
/*     */   {
/* 631 */     checkLength(paramInt);
/* 632 */     return 0;
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel paramScatteringByteChannel, int paramInt)
/*     */   {
/* 637 */     checkLength(paramInt);
/* 638 */     return 0;
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int paramInt)
/*     */   {
/* 643 */     return checkLength(paramInt);
/*     */   }
/*     */   
/*     */   public int indexOf(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 648 */     checkIndex(paramInt1);
/* 649 */     checkIndex(paramInt2);
/* 650 */     return -1;
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte paramByte)
/*     */   {
/* 655 */     return -1;
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt, byte paramByte)
/*     */   {
/* 660 */     checkLength(paramInt);
/* 661 */     return -1;
/*     */   }
/*     */   
/*     */   public int bytesBefore(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/* 666 */     checkIndex(paramInt1, paramInt2);
/* 667 */     return -1;
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 672 */     return -1;
/*     */   }
/*     */   
/*     */   public int forEachByte(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 677 */     checkIndex(paramInt1, paramInt2);
/* 678 */     return -1;
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 683 */     return -1;
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int paramInt1, int paramInt2, ByteBufProcessor paramByteBufProcessor)
/*     */   {
/* 688 */     checkIndex(paramInt1, paramInt2);
/* 689 */     return -1;
/*     */   }
/*     */   
/*     */   public ByteBuf copy()
/*     */   {
/* 694 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 699 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf slice()
/*     */   {
/* 704 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int paramInt1, int paramInt2)
/*     */   {
/* 709 */     return checkIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate()
/*     */   {
/* 714 */     return this;
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 719 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer()
/*     */   {
/* 724 */     return EMPTY_BYTE_BUFFER;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 729 */     checkIndex(paramInt1, paramInt2);
/* 730 */     return nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers()
/*     */   {
/* 735 */     return new ByteBuffer[] { EMPTY_BYTE_BUFFER };
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 740 */     checkIndex(paramInt1, paramInt2);
/* 741 */     return nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 746 */     return EMPTY_BYTE_BUFFER;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 751 */     return true;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 756 */     return EmptyArrays.EMPTY_BYTES;
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 761 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 766 */     return EMPTY_BYTE_BUFFER_ADDRESS != 0L;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 771 */     if (hasMemoryAddress()) {
/* 772 */       return EMPTY_BYTE_BUFFER_ADDRESS;
/*     */     }
/* 774 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString(Charset paramCharset)
/*     */   {
/* 780 */     return "";
/*     */   }
/*     */   
/*     */   public String toString(int paramInt1, int paramInt2, Charset paramCharset)
/*     */   {
/* 785 */     checkIndex(paramInt1, paramInt2);
/* 786 */     return toString(paramCharset);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 791 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 796 */     return ((paramObject instanceof ByteBuf)) && (!((ByteBuf)paramObject).isReadable());
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf paramByteBuf)
/*     */   {
/* 801 */     return paramByteBuf.isReadable() ? -1 : 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 806 */     return this.str;
/*     */   }
/*     */   
/*     */   public boolean isReadable(int paramInt)
/*     */   {
/* 811 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isWritable(int paramInt)
/*     */   {
/* 816 */     return false;
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/* 821 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuf retain()
/*     */   {
/* 826 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int paramInt)
/*     */   {
/* 831 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 836 */     return false;
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 841 */     return false;
/*     */   }
/*     */   
/*     */   private ByteBuf checkIndex(int paramInt) {
/* 845 */     if (paramInt != 0) {
/* 846 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 848 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf checkIndex(int paramInt1, int paramInt2) {
/* 852 */     if (paramInt2 < 0) {
/* 853 */       throw new IllegalArgumentException("length: " + paramInt2);
/*     */     }
/* 855 */     if ((paramInt1 != 0) || (paramInt2 != 0)) {
/* 856 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 858 */     return this;
/*     */   }
/*     */   
/*     */   private ByteBuf checkLength(int paramInt) {
/* 862 */     if (paramInt < 0) {
/* 863 */       throw new IllegalArgumentException("length: " + paramInt + " (expected: >= 0)");
/*     */     }
/* 865 */     if (paramInt != 0) {
/* 866 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 868 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\EmptyByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */