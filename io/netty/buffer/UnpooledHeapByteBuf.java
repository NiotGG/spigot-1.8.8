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
/*     */ public class UnpooledHeapByteBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final ByteBufAllocator alloc;
/*     */   private byte[] array;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   protected UnpooledHeapByteBuf(ByteBufAllocator paramByteBufAllocator, int paramInt1, int paramInt2)
/*     */   {
/*  45 */     this(paramByteBufAllocator, new byte[paramInt1], 0, 0, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected UnpooledHeapByteBuf(ByteBufAllocator paramByteBufAllocator, byte[] paramArrayOfByte, int paramInt)
/*     */   {
/*  55 */     this(paramByteBufAllocator, paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   private UnpooledHeapByteBuf(ByteBufAllocator paramByteBufAllocator, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  61 */     super(paramInt3);
/*     */     
/*  63 */     if (paramByteBufAllocator == null) {
/*  64 */       throw new NullPointerException("alloc");
/*     */     }
/*  66 */     if (paramArrayOfByte == null) {
/*  67 */       throw new NullPointerException("initialArray");
/*     */     }
/*  69 */     if (paramArrayOfByte.length > paramInt3) {
/*  70 */       throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", new Object[] { Integer.valueOf(paramArrayOfByte.length), Integer.valueOf(paramInt3) }));
/*     */     }
/*     */     
/*     */ 
/*  74 */     this.alloc = paramByteBufAllocator;
/*  75 */     setArray(paramArrayOfByte);
/*  76 */     setIndex(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   private void setArray(byte[] paramArrayOfByte) {
/*  80 */     this.array = paramArrayOfByte;
/*  81 */     this.tmpNioBuf = null;
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/*  86 */     return this.alloc;
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/*  91 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 101 */     ensureAccessible();
/* 102 */     return this.array.length;
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 107 */     ensureAccessible();
/* 108 */     if ((paramInt < 0) || (paramInt > maxCapacity())) {
/* 109 */       throw new IllegalArgumentException("newCapacity: " + paramInt);
/*     */     }
/*     */     
/* 112 */     int i = this.array.length;
/* 113 */     byte[] arrayOfByte; if (paramInt > i) {
/* 114 */       arrayOfByte = new byte[paramInt];
/* 115 */       System.arraycopy(this.array, 0, arrayOfByte, 0, this.array.length);
/* 116 */       setArray(arrayOfByte);
/* 117 */     } else if (paramInt < i) {
/* 118 */       arrayOfByte = new byte[paramInt];
/* 119 */       int j = readerIndex();
/* 120 */       if (j < paramInt) {
/* 121 */         int k = writerIndex();
/* 122 */         if (k > paramInt) {
/* 123 */           writerIndex(k = paramInt);
/*     */         }
/* 125 */         System.arraycopy(this.array, j, arrayOfByte, j, k - j);
/*     */       } else {
/* 127 */         setIndex(paramInt, paramInt);
/*     */       }
/* 129 */       setArray(arrayOfByte);
/*     */     }
/* 131 */     return this;
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 141 */     ensureAccessible();
/* 142 */     return this.array;
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 147 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 157 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 162 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 163 */     if (paramByteBuf.hasMemoryAddress()) {
/* 164 */       PlatformDependent.copyMemory(this.array, paramInt1, paramByteBuf.memoryAddress() + paramInt2, paramInt3);
/* 165 */     } else if (paramByteBuf.hasArray()) {
/* 166 */       getBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/* 168 */       paramByteBuf.setBytes(paramInt2, this.array, paramInt1, paramInt3);
/*     */     }
/* 170 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 175 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 176 */     System.arraycopy(this.array, paramInt1, paramArrayOfByte, paramInt2, paramInt3);
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 182 */     ensureAccessible();
/* 183 */     paramByteBuffer.put(this.array, paramInt, Math.min(capacity() - paramInt, paramByteBuffer.remaining()));
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 189 */     ensureAccessible();
/* 190 */     paramOutputStream.write(this.array, paramInt1, paramInt2);
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 196 */     ensureAccessible();
/* 197 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 201 */     ensureAccessible();
/*     */     ByteBuffer localByteBuffer;
/* 203 */     if (paramBoolean) {
/* 204 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 206 */       localByteBuffer = ByteBuffer.wrap(this.array);
/*     */     }
/* 208 */     return paramGatheringByteChannel.write((ByteBuffer)localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2));
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 213 */     checkReadableBytes(paramInt);
/* 214 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 215 */     this.readerIndex += i;
/* 216 */     return i;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 221 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 222 */     if (paramByteBuf.hasMemoryAddress()) {
/* 223 */       PlatformDependent.copyMemory(paramByteBuf.memoryAddress() + paramInt2, this.array, paramInt1, paramInt3);
/* 224 */     } else if (paramByteBuf.hasArray()) {
/* 225 */       setBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/* 227 */       paramByteBuf.getBytes(paramInt2, this.array, paramInt1, paramInt3);
/*     */     }
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 234 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 235 */     System.arraycopy(paramArrayOfByte, paramInt2, this.array, paramInt1, paramInt3);
/* 236 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 241 */     ensureAccessible();
/* 242 */     paramByteBuffer.get(this.array, paramInt, paramByteBuffer.remaining());
/* 243 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 248 */     ensureAccessible();
/* 249 */     return paramInputStream.read(this.array, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 254 */     ensureAccessible();
/*     */     try {
/* 256 */       return paramScatteringByteChannel.read((ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2));
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 258 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public int nioBufferCount()
/*     */   {
/* 264 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 269 */     ensureAccessible();
/* 270 */     return ByteBuffer.wrap(this.array, paramInt1, paramInt2).slice();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 275 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 280 */     checkIndex(paramInt1, paramInt2);
/* 281 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/* 286 */     ensureAccessible();
/* 287 */     return _getByte(paramInt);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/* 292 */     return this.array[paramInt];
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/* 297 */     ensureAccessible();
/* 298 */     return _getShort(paramInt);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/* 303 */     return (short)(this.array[paramInt] << 8 | this.array[(paramInt + 1)] & 0xFF);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/* 308 */     ensureAccessible();
/* 309 */     return _getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/* 314 */     return (this.array[paramInt] & 0xFF) << 16 | (this.array[(paramInt + 1)] & 0xFF) << 8 | this.array[(paramInt + 2)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getInt(int paramInt)
/*     */   {
/* 321 */     ensureAccessible();
/* 322 */     return _getInt(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 327 */     return (this.array[paramInt] & 0xFF) << 24 | (this.array[(paramInt + 1)] & 0xFF) << 16 | (this.array[(paramInt + 2)] & 0xFF) << 8 | this.array[(paramInt + 3)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getLong(int paramInt)
/*     */   {
/* 335 */     ensureAccessible();
/* 336 */     return _getLong(paramInt);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 341 */     return (this.array[paramInt] & 0xFF) << 56 | (this.array[(paramInt + 1)] & 0xFF) << 48 | (this.array[(paramInt + 2)] & 0xFF) << 40 | (this.array[(paramInt + 3)] & 0xFF) << 32 | (this.array[(paramInt + 4)] & 0xFF) << 24 | (this.array[(paramInt + 5)] & 0xFF) << 16 | (this.array[(paramInt + 6)] & 0xFF) << 8 | this.array[(paramInt + 7)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteBuf setByte(int paramInt1, int paramInt2)
/*     */   {
/* 353 */     ensureAccessible();
/* 354 */     _setByte(paramInt1, paramInt2);
/* 355 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 360 */     this.array[paramInt1] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int paramInt1, int paramInt2)
/*     */   {
/* 365 */     ensureAccessible();
/* 366 */     _setShort(paramInt1, paramInt2);
/* 367 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 372 */     this.array[paramInt1] = ((byte)(paramInt2 >>> 8));
/* 373 */     this.array[(paramInt1 + 1)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 378 */     ensureAccessible();
/* 379 */     _setMedium(paramInt1, paramInt2);
/* 380 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 385 */     this.array[paramInt1] = ((byte)(paramInt2 >>> 16));
/* 386 */     this.array[(paramInt1 + 1)] = ((byte)(paramInt2 >>> 8));
/* 387 */     this.array[(paramInt1 + 2)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int paramInt1, int paramInt2)
/*     */   {
/* 392 */     ensureAccessible();
/* 393 */     _setInt(paramInt1, paramInt2);
/* 394 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 399 */     this.array[paramInt1] = ((byte)(paramInt2 >>> 24));
/* 400 */     this.array[(paramInt1 + 1)] = ((byte)(paramInt2 >>> 16));
/* 401 */     this.array[(paramInt1 + 2)] = ((byte)(paramInt2 >>> 8));
/* 402 */     this.array[(paramInt1 + 3)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int paramInt, long paramLong)
/*     */   {
/* 407 */     ensureAccessible();
/* 408 */     _setLong(paramInt, paramLong);
/* 409 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 414 */     this.array[paramInt] = ((byte)(int)(paramLong >>> 56));
/* 415 */     this.array[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/* 416 */     this.array[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/* 417 */     this.array[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/* 418 */     this.array[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/* 419 */     this.array[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/* 420 */     this.array[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/* 421 */     this.array[(paramInt + 7)] = ((byte)(int)paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 426 */     checkIndex(paramInt1, paramInt2);
/* 427 */     byte[] arrayOfByte = new byte[paramInt2];
/* 428 */     System.arraycopy(this.array, paramInt1, arrayOfByte, 0, paramInt2);
/* 429 */     return new UnpooledHeapByteBuf(alloc(), arrayOfByte, maxCapacity());
/*     */   }
/*     */   
/*     */   private ByteBuffer internalNioBuffer() {
/* 433 */     ByteBuffer localByteBuffer = this.tmpNioBuf;
/* 434 */     if (localByteBuffer == null) {
/* 435 */       this.tmpNioBuf = (localByteBuffer = ByteBuffer.wrap(this.array));
/*     */     }
/* 437 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   protected void deallocate()
/*     */   {
/* 442 */     this.array = null;
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/* 447 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\UnpooledHeapByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */