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
/*     */ final class PooledHeapByteBuf
/*     */   extends PooledByteBuf<byte[]>
/*     */ {
/*  30 */   private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler()
/*     */   {
/*     */     protected PooledHeapByteBuf newObject(Recycler.Handle paramAnonymousHandle) {
/*  33 */       return new PooledHeapByteBuf(paramAnonymousHandle, 0, null);
/*     */     }
/*     */   };
/*     */   
/*     */   static PooledHeapByteBuf newInstance(int paramInt) {
/*  38 */     PooledHeapByteBuf localPooledHeapByteBuf = (PooledHeapByteBuf)RECYCLER.get();
/*  39 */     localPooledHeapByteBuf.setRefCnt(1);
/*  40 */     localPooledHeapByteBuf.maxCapacity(paramInt);
/*  41 */     return localPooledHeapByteBuf;
/*     */   }
/*     */   
/*     */   private PooledHeapByteBuf(Recycler.Handle paramHandle, int paramInt) {
/*  45 */     super(paramHandle, paramInt);
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/*  55 */     return ((byte[])this.memory)[idx(paramInt)];
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/*  60 */     paramInt = idx(paramInt);
/*  61 */     return (short)(((byte[])this.memory)[paramInt] << 8 | ((byte[])this.memory)[(paramInt + 1)] & 0xFF);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/*  66 */     paramInt = idx(paramInt);
/*  67 */     return (((byte[])this.memory)[paramInt] & 0xFF) << 16 | (((byte[])this.memory)[(paramInt + 1)] & 0xFF) << 8 | ((byte[])this.memory)[(paramInt + 2)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int _getInt(int paramInt)
/*     */   {
/*  74 */     paramInt = idx(paramInt);
/*  75 */     return (((byte[])this.memory)[paramInt] & 0xFF) << 24 | (((byte[])this.memory)[(paramInt + 1)] & 0xFF) << 16 | (((byte[])this.memory)[(paramInt + 2)] & 0xFF) << 8 | ((byte[])this.memory)[(paramInt + 3)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected long _getLong(int paramInt)
/*     */   {
/*  83 */     paramInt = idx(paramInt);
/*  84 */     return (((byte[])this.memory)[paramInt] & 0xFF) << 56 | (((byte[])this.memory)[(paramInt + 1)] & 0xFF) << 48 | (((byte[])this.memory)[(paramInt + 2)] & 0xFF) << 40 | (((byte[])this.memory)[(paramInt + 3)] & 0xFF) << 32 | (((byte[])this.memory)[(paramInt + 4)] & 0xFF) << 24 | (((byte[])this.memory)[(paramInt + 5)] & 0xFF) << 16 | (((byte[])this.memory)[(paramInt + 6)] & 0xFF) << 8 | ((byte[])this.memory)[(paramInt + 7)] & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/*  96 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/*  97 */     if (paramByteBuf.hasMemoryAddress()) {
/*  98 */       PlatformDependent.copyMemory((byte[])this.memory, idx(paramInt1), paramByteBuf.memoryAddress() + paramInt2, paramInt3);
/*  99 */     } else if (paramByteBuf.hasArray()) {
/* 100 */       getBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/* 102 */       paramByteBuf.setBytes(paramInt2, (byte[])this.memory, idx(paramInt1), paramInt3);
/*     */     }
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 109 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 110 */     System.arraycopy(this.memory, idx(paramInt1), paramArrayOfByte, paramInt2, paramInt3);
/* 111 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 116 */     checkIndex(paramInt);
/* 117 */     paramByteBuffer.put((byte[])this.memory, idx(paramInt), Math.min(capacity() - paramInt, paramByteBuffer.remaining()));
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 123 */     checkIndex(paramInt1, paramInt2);
/* 124 */     paramOutputStream.write((byte[])this.memory, idx(paramInt1), paramInt2);
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 130 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 134 */     checkIndex(paramInt1, paramInt2);
/* 135 */     paramInt1 = idx(paramInt1);
/*     */     ByteBuffer localByteBuffer;
/* 137 */     if (paramBoolean) {
/* 138 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 140 */       localByteBuffer = ByteBuffer.wrap((byte[])this.memory);
/*     */     }
/* 142 */     return paramGatheringByteChannel.write((ByteBuffer)localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2));
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 147 */     checkReadableBytes(paramInt);
/* 148 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 149 */     this.readerIndex += i;
/* 150 */     return i;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 155 */     ((byte[])this.memory)[idx(paramInt1)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 160 */     paramInt1 = idx(paramInt1);
/* 161 */     ((byte[])this.memory)[paramInt1] = ((byte)(paramInt2 >>> 8));
/* 162 */     ((byte[])this.memory)[(paramInt1 + 1)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 167 */     paramInt1 = idx(paramInt1);
/* 168 */     ((byte[])this.memory)[paramInt1] = ((byte)(paramInt2 >>> 16));
/* 169 */     ((byte[])this.memory)[(paramInt1 + 1)] = ((byte)(paramInt2 >>> 8));
/* 170 */     ((byte[])this.memory)[(paramInt1 + 2)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 175 */     paramInt1 = idx(paramInt1);
/* 176 */     ((byte[])this.memory)[paramInt1] = ((byte)(paramInt2 >>> 24));
/* 177 */     ((byte[])this.memory)[(paramInt1 + 1)] = ((byte)(paramInt2 >>> 16));
/* 178 */     ((byte[])this.memory)[(paramInt1 + 2)] = ((byte)(paramInt2 >>> 8));
/* 179 */     ((byte[])this.memory)[(paramInt1 + 3)] = ((byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 184 */     paramInt = idx(paramInt);
/* 185 */     ((byte[])this.memory)[paramInt] = ((byte)(int)(paramLong >>> 56));
/* 186 */     ((byte[])this.memory)[(paramInt + 1)] = ((byte)(int)(paramLong >>> 48));
/* 187 */     ((byte[])this.memory)[(paramInt + 2)] = ((byte)(int)(paramLong >>> 40));
/* 188 */     ((byte[])this.memory)[(paramInt + 3)] = ((byte)(int)(paramLong >>> 32));
/* 189 */     ((byte[])this.memory)[(paramInt + 4)] = ((byte)(int)(paramLong >>> 24));
/* 190 */     ((byte[])this.memory)[(paramInt + 5)] = ((byte)(int)(paramLong >>> 16));
/* 191 */     ((byte[])this.memory)[(paramInt + 6)] = ((byte)(int)(paramLong >>> 8));
/* 192 */     ((byte[])this.memory)[(paramInt + 7)] = ((byte)(int)paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 197 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 198 */     if (paramByteBuf.hasMemoryAddress()) {
/* 199 */       PlatformDependent.copyMemory(paramByteBuf.memoryAddress() + paramInt2, (byte[])this.memory, idx(paramInt1), paramInt3);
/* 200 */     } else if (paramByteBuf.hasArray()) {
/* 201 */       setBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/* 203 */       paramByteBuf.getBytes(paramInt2, (byte[])this.memory, idx(paramInt1), paramInt3);
/*     */     }
/* 205 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 210 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 211 */     System.arraycopy(paramArrayOfByte, paramInt2, this.memory, idx(paramInt1), paramInt3);
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 217 */     int i = paramByteBuffer.remaining();
/* 218 */     checkIndex(paramInt, i);
/* 219 */     paramByteBuffer.get((byte[])this.memory, idx(paramInt), i);
/* 220 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 225 */     checkIndex(paramInt1, paramInt2);
/* 226 */     return paramInputStream.read((byte[])this.memory, idx(paramInt1), paramInt2);
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 231 */     checkIndex(paramInt1, paramInt2);
/* 232 */     paramInt1 = idx(paramInt1);
/*     */     try {
/* 234 */       return paramScatteringByteChannel.read((ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2));
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 236 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 242 */     checkIndex(paramInt1, paramInt2);
/* 243 */     ByteBuf localByteBuf = alloc().heapBuffer(paramInt2, maxCapacity());
/* 244 */     localByteBuf.writeBytes((byte[])this.memory, idx(paramInt1), paramInt2);
/* 245 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 250 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 255 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 260 */     checkIndex(paramInt1, paramInt2);
/* 261 */     paramInt1 = idx(paramInt1);
/* 262 */     ByteBuffer localByteBuffer = ByteBuffer.wrap((byte[])this.memory, paramInt1, paramInt2);
/* 263 */     return localByteBuffer.slice();
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 268 */     checkIndex(paramInt1, paramInt2);
/* 269 */     paramInt1 = idx(paramInt1);
/* 270 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 275 */     return true;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 280 */     return (byte[])this.memory;
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 285 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 290 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 295 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected ByteBuffer newInternalNioBuffer(byte[] paramArrayOfByte)
/*     */   {
/* 300 */     return ByteBuffer.wrap(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   protected Recycler<?> recycler()
/*     */   {
/* 305 */     return RECYCLER;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PooledHeapByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */