/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
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
/*     */ final class PooledUnsafeDirectByteBuf
/*     */   extends PooledByteBuf<ByteBuffer>
/*     */ {
/*  33 */   private static final boolean NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
/*     */   
/*  35 */   private static final Recycler<PooledUnsafeDirectByteBuf> RECYCLER = new Recycler()
/*     */   {
/*     */ 
/*  38 */     protected PooledUnsafeDirectByteBuf newObject(Recycler.Handle paramAnonymousHandle) { return new PooledUnsafeDirectByteBuf(paramAnonymousHandle, 0, null); }
/*     */   };
/*     */   private long memoryAddress;
/*     */   
/*     */   static PooledUnsafeDirectByteBuf newInstance(int paramInt) {
/*  43 */     PooledUnsafeDirectByteBuf localPooledUnsafeDirectByteBuf = (PooledUnsafeDirectByteBuf)RECYCLER.get();
/*  44 */     localPooledUnsafeDirectByteBuf.setRefCnt(1);
/*  45 */     localPooledUnsafeDirectByteBuf.maxCapacity(paramInt);
/*  46 */     return localPooledUnsafeDirectByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */   private PooledUnsafeDirectByteBuf(Recycler.Handle paramHandle, int paramInt)
/*     */   {
/*  52 */     super(paramHandle, paramInt);
/*     */   }
/*     */   
/*     */   void init(PoolChunk<ByteBuffer> paramPoolChunk, long paramLong, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  57 */     super.init(paramPoolChunk, paramLong, paramInt1, paramInt2, paramInt3);
/*  58 */     initMemoryAddress();
/*     */   }
/*     */   
/*     */   void initUnpooled(PoolChunk<ByteBuffer> paramPoolChunk, int paramInt)
/*     */   {
/*  63 */     super.initUnpooled(paramPoolChunk, paramInt);
/*  64 */     initMemoryAddress();
/*     */   }
/*     */   
/*     */   private void initMemoryAddress() {
/*  68 */     this.memoryAddress = (PlatformDependent.directBufferAddress((ByteBuffer)this.memory) + this.offset);
/*     */   }
/*     */   
/*     */   protected ByteBuffer newInternalNioBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/*  73 */     return paramByteBuffer.duplicate();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  78 */     return true;
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/*  83 */     return PlatformDependent.getByte(addr(paramInt));
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/*  88 */     short s = PlatformDependent.getShort(addr(paramInt));
/*  89 */     return NATIVE_ORDER ? s : Short.reverseBytes(s);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/*  94 */     long l = addr(paramInt);
/*  95 */     return (PlatformDependent.getByte(l) & 0xFF) << 16 | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | PlatformDependent.getByte(l + 2L) & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int _getInt(int paramInt)
/*     */   {
/* 102 */     int i = PlatformDependent.getInt(addr(paramInt));
/* 103 */     return NATIVE_ORDER ? i : Integer.reverseBytes(i);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/* 108 */     long l = PlatformDependent.getLong(addr(paramInt));
/* 109 */     return NATIVE_ORDER ? l : Long.reverseBytes(l);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 114 */     checkIndex(paramInt1, paramInt3);
/* 115 */     if (paramByteBuf == null) {
/* 116 */       throw new NullPointerException("dst");
/*     */     }
/* 118 */     if ((paramInt2 < 0) || (paramInt2 > paramByteBuf.capacity() - paramInt3)) {
/* 119 */       throw new IndexOutOfBoundsException("dstIndex: " + paramInt2);
/*     */     }
/*     */     
/* 122 */     if (paramInt3 != 0) {
/* 123 */       if (paramByteBuf.hasMemoryAddress()) {
/* 124 */         PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.memoryAddress() + paramInt2, paramInt3);
/* 125 */       } else if (paramByteBuf.hasArray()) {
/* 126 */         PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */       } else {
/* 128 */         paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */       }
/*     */     }
/* 131 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 136 */     checkIndex(paramInt1, paramInt3);
/* 137 */     if (paramArrayOfByte == null) {
/* 138 */       throw new NullPointerException("dst");
/*     */     }
/* 140 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt3)) {
/* 141 */       throw new IndexOutOfBoundsException("dstIndex: " + paramInt2);
/*     */     }
/* 143 */     if (paramInt3 != 0) {
/* 144 */       PlatformDependent.copyMemory(addr(paramInt1), paramArrayOfByte, paramInt2, paramInt3);
/*     */     }
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 151 */     getBytes(paramInt, paramByteBuffer, false);
/* 152 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt, ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 156 */     checkIndex(paramInt);
/* 157 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/*     */     ByteBuffer localByteBuffer;
/* 159 */     if (paramBoolean) {
/* 160 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 162 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 164 */     paramInt = idx(paramInt);
/* 165 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 166 */     paramByteBuffer.put(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 171 */     int i = paramByteBuffer.remaining();
/* 172 */     checkReadableBytes(i);
/* 173 */     getBytes(this.readerIndex, paramByteBuffer, true);
/* 174 */     this.readerIndex += i;
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 180 */     checkIndex(paramInt1, paramInt2);
/* 181 */     if (paramInt2 != 0) {
/* 182 */       byte[] arrayOfByte = new byte[paramInt2];
/* 183 */       PlatformDependent.copyMemory(addr(paramInt1), arrayOfByte, 0, paramInt2);
/* 184 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/* 186 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 191 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 195 */     checkIndex(paramInt1, paramInt2);
/* 196 */     if (paramInt2 == 0) {
/* 197 */       return 0;
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/* 201 */     if (paramBoolean) {
/* 202 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 204 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 206 */     paramInt1 = idx(paramInt1);
/* 207 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/* 208 */     return paramGatheringByteChannel.write(localByteBuffer);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt)
/*     */     throws IOException
/*     */   {
/* 214 */     checkReadableBytes(paramInt);
/* 215 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 216 */     this.readerIndex += i;
/* 217 */     return i;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 222 */     PlatformDependent.putByte(addr(paramInt1), (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 227 */     PlatformDependent.putShort(addr(paramInt1), NATIVE_ORDER ? (short)paramInt2 : Short.reverseBytes((short)paramInt2));
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 232 */     long l = addr(paramInt1);
/* 233 */     PlatformDependent.putByte(l, (byte)(paramInt2 >>> 16));
/* 234 */     PlatformDependent.putByte(l + 1L, (byte)(paramInt2 >>> 8));
/* 235 */     PlatformDependent.putByte(l + 2L, (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 240 */     PlatformDependent.putInt(addr(paramInt1), NATIVE_ORDER ? paramInt2 : Integer.reverseBytes(paramInt2));
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 245 */     PlatformDependent.putLong(addr(paramInt), NATIVE_ORDER ? paramLong : Long.reverseBytes(paramLong));
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 250 */     checkIndex(paramInt1, paramInt3);
/* 251 */     if (paramByteBuf == null) {
/* 252 */       throw new NullPointerException("src");
/*     */     }
/* 254 */     if ((paramInt2 < 0) || (paramInt2 > paramByteBuf.capacity() - paramInt3)) {
/* 255 */       throw new IndexOutOfBoundsException("srcIndex: " + paramInt2);
/*     */     }
/*     */     
/* 258 */     if (paramInt3 != 0) {
/* 259 */       if (paramByteBuf.hasMemoryAddress()) {
/* 260 */         PlatformDependent.copyMemory(paramByteBuf.memoryAddress() + paramInt2, addr(paramInt1), paramInt3);
/* 261 */       } else if (paramByteBuf.hasArray()) {
/* 262 */         PlatformDependent.copyMemory(paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, addr(paramInt1), paramInt3);
/*     */       } else {
/* 264 */         paramByteBuf.getBytes(paramInt2, this, paramInt1, paramInt3);
/*     */       }
/*     */     }
/* 267 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 272 */     checkIndex(paramInt1, paramInt3);
/* 273 */     if (paramInt3 != 0) {
/* 274 */       PlatformDependent.copyMemory(paramArrayOfByte, paramInt2, addr(paramInt1), paramInt3);
/*     */     }
/* 276 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 281 */     checkIndex(paramInt, paramByteBuffer.remaining());
/* 282 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 283 */     if (paramByteBuffer == localByteBuffer) {
/* 284 */       paramByteBuffer = paramByteBuffer.duplicate();
/*     */     }
/*     */     
/* 287 */     paramInt = idx(paramInt);
/* 288 */     localByteBuffer.clear().position(paramInt).limit(paramInt + paramByteBuffer.remaining());
/* 289 */     localByteBuffer.put(paramByteBuffer);
/* 290 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 295 */     checkIndex(paramInt1, paramInt2);
/* 296 */     byte[] arrayOfByte = new byte[paramInt2];
/* 297 */     int i = paramInputStream.read(arrayOfByte);
/* 298 */     if (i > 0) {
/* 299 */       PlatformDependent.copyMemory(arrayOfByte, 0, addr(paramInt1), i);
/*     */     }
/* 301 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 306 */     checkIndex(paramInt1, paramInt2);
/* 307 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 308 */     paramInt1 = idx(paramInt1);
/* 309 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     try {
/* 311 */       return paramScatteringByteChannel.read(localByteBuffer);
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 313 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 319 */     checkIndex(paramInt1, paramInt2);
/* 320 */     ByteBuf localByteBuf = alloc().directBuffer(paramInt2, maxCapacity());
/* 321 */     if (paramInt2 != 0) {
/* 322 */       if (localByteBuf.hasMemoryAddress()) {
/* 323 */         PlatformDependent.copyMemory(addr(paramInt1), localByteBuf.memoryAddress(), paramInt2);
/* 324 */         localByteBuf.setIndex(0, paramInt2);
/*     */       } else {
/* 326 */         localByteBuf.writeBytes(this, paramInt1, paramInt2);
/*     */       }
/*     */     }
/* 329 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 334 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 339 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 344 */     checkIndex(paramInt1, paramInt2);
/* 345 */     paramInt1 = idx(paramInt1);
/* 346 */     return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(paramInt1).limit(paramInt1 + paramInt2)).slice();
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 351 */     checkIndex(paramInt1, paramInt2);
/* 352 */     paramInt1 = idx(paramInt1);
/* 353 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 358 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 363 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 368 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 373 */     return true;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 378 */     return this.memoryAddress;
/*     */   }
/*     */   
/*     */   private long addr(int paramInt) {
/* 382 */     return this.memoryAddress + paramInt;
/*     */   }
/*     */   
/*     */   protected Recycler<?> recycler()
/*     */   {
/* 387 */     return RECYCLER;
/*     */   }
/*     */   
/*     */   protected SwappedByteBuf newSwappedByteBuf()
/*     */   {
/* 392 */     return new UnsafeDirectSwappedByteBuf(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PooledUnsafeDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */