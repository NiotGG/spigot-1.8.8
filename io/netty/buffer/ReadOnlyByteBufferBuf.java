/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.ReadOnlyBufferException;
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
/*     */ class ReadOnlyByteBufferBuf
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   protected final ByteBuffer buffer;
/*     */   private final ByteBufAllocator allocator;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   ReadOnlyByteBufferBuf(ByteBufAllocator paramByteBufAllocator, ByteBuffer paramByteBuffer)
/*     */   {
/*  40 */     super(paramByteBuffer.remaining());
/*  41 */     if (!paramByteBuffer.isReadOnly()) {
/*  42 */       throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName(paramByteBuffer));
/*     */     }
/*     */     
/*  45 */     this.allocator = paramByteBufAllocator;
/*  46 */     this.buffer = paramByteBuffer.slice().order(ByteOrder.BIG_ENDIAN);
/*  47 */     writerIndex(this.buffer.limit());
/*     */   }
/*     */   
/*     */ 
/*     */   protected void deallocate() {}
/*     */   
/*     */   public byte getByte(int paramInt)
/*     */   {
/*  55 */     ensureAccessible();
/*  56 */     return _getByte(paramInt);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/*  61 */     return this.buffer.get(paramInt);
/*     */   }
/*     */   
/*     */   public short getShort(int paramInt)
/*     */   {
/*  66 */     ensureAccessible();
/*  67 */     return _getShort(paramInt);
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/*  72 */     return this.buffer.getShort(paramInt);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int paramInt)
/*     */   {
/*  77 */     ensureAccessible();
/*  78 */     return _getUnsignedMedium(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/*  83 */     return (getByte(paramInt) & 0xFF) << 16 | (getByte(paramInt + 1) & 0xFF) << 8 | getByte(paramInt + 2) & 0xFF;
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt)
/*     */   {
/*  88 */     ensureAccessible();
/*  89 */     return _getInt(paramInt);
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/*  94 */     return this.buffer.getInt(paramInt);
/*     */   }
/*     */   
/*     */   public long getLong(int paramInt)
/*     */   {
/*  99 */     ensureAccessible();
/* 100 */     return _getLong(paramInt);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 105 */     return this.buffer.getLong(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 110 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 111 */     if (paramByteBuf.hasArray()) {
/* 112 */       getBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/* 113 */     } else if (paramByteBuf.nioBufferCount() > 0) {
/* 114 */       for (ByteBuffer localByteBuffer : paramByteBuf.nioBuffers(paramInt2, paramInt3)) {
/* 115 */         int k = localByteBuffer.remaining();
/* 116 */         getBytes(paramInt1, localByteBuffer);
/* 117 */         paramInt1 += k;
/*     */       }
/*     */     } else {
/* 120 */       paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 127 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/*     */     
/* 129 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt3)) {
/* 130 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramArrayOfByte.length) }));
/*     */     }
/*     */     
/*     */ 
/* 134 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 135 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt3);
/* 136 */     localByteBuffer.get(paramArrayOfByte, paramInt2, paramInt3);
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 142 */     checkIndex(paramInt);
/* 143 */     if (paramByteBuffer == null) {
/* 144 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 147 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/* 148 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 149 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 150 */     paramByteBuffer.put(localByteBuffer);
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 156 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 161 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 166 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 171 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 176 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 181 */     return maxCapacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int paramInt)
/*     */   {
/* 186 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc()
/*     */   {
/* 191 */     return this.allocator;
/*     */   }
/*     */   
/*     */   public ByteOrder order()
/*     */   {
/* 196 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap()
/*     */   {
/* 201 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/* 206 */     return this.buffer.isDirect();
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 211 */     ensureAccessible();
/* 212 */     if (paramInt2 == 0) {
/* 213 */       return this;
/*     */     }
/*     */     
/* 216 */     if (this.buffer.hasArray()) {
/* 217 */       paramOutputStream.write(this.buffer.array(), paramInt1 + this.buffer.arrayOffset(), paramInt2);
/*     */     } else {
/* 219 */       byte[] arrayOfByte = new byte[paramInt2];
/* 220 */       ByteBuffer localByteBuffer = internalNioBuffer();
/* 221 */       localByteBuffer.clear().position(paramInt1);
/* 222 */       localByteBuffer.get(arrayOfByte);
/* 223 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/* 225 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 230 */     ensureAccessible();
/* 231 */     if (paramInt2 == 0) {
/* 232 */       return 0;
/*     */     }
/*     */     
/* 235 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 236 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/* 237 */     return paramGatheringByteChannel.write(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 242 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 247 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 252 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 257 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 262 */     throw new ReadOnlyBufferException();
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 266 */     ByteBuffer localByteBuffer = this.tmpNioBuf;
/* 267 */     if (localByteBuffer == null) {
/* 268 */       this.tmpNioBuf = (localByteBuffer = this.buffer.duplicate());
/*     */     }
/* 270 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 275 */     ensureAccessible();
/*     */     ByteBuffer localByteBuffer1;
/*     */     try {
/* 278 */       localByteBuffer1 = (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {
/* 280 */       throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (paramInt1 + paramInt2));
/*     */     }
/*     */     
/* 283 */     ByteBuffer localByteBuffer2 = ByteBuffer.allocateDirect(paramInt2);
/* 284 */     localByteBuffer2.put(localByteBuffer1);
/* 285 */     localByteBuffer2.order(order());
/* 286 */     localByteBuffer2.clear();
/* 287 */     return new UnpooledDirectByteBuf(alloc(), localByteBuffer2, maxCapacity());
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 292 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 297 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 302 */     return (ByteBuffer)this.buffer.duplicate().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 307 */     ensureAccessible();
/* 308 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 313 */     return this.buffer.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 318 */     return this.buffer.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 323 */     return this.buffer.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 328 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 333 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ReadOnlyByteBufferBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */