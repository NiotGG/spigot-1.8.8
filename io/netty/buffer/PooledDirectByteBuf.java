/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
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
/*     */ 
/*     */ 
/*     */ final class PooledDirectByteBuf
/*     */   extends PooledByteBuf<ByteBuffer>
/*     */ {
/*  31 */   private static final Recycler<PooledDirectByteBuf> RECYCLER = new Recycler()
/*     */   {
/*     */     protected PooledDirectByteBuf newObject(Recycler.Handle paramAnonymousHandle) {
/*  34 */       return new PooledDirectByteBuf(paramAnonymousHandle, 0, null);
/*     */     }
/*     */   };
/*     */   
/*     */   static PooledDirectByteBuf newInstance(int paramInt) {
/*  39 */     PooledDirectByteBuf localPooledDirectByteBuf = (PooledDirectByteBuf)RECYCLER.get();
/*  40 */     localPooledDirectByteBuf.setRefCnt(1);
/*  41 */     localPooledDirectByteBuf.maxCapacity(paramInt);
/*  42 */     return localPooledDirectByteBuf;
/*     */   }
/*     */   
/*     */   private PooledDirectByteBuf(Recycler.Handle paramHandle, int paramInt) {
/*  46 */     super(paramHandle, paramInt);
/*     */   }
/*     */   
/*     */   protected ByteBuffer newInternalNioBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/*  51 */     return paramByteBuffer.duplicate();
/*     */   }
/*     */   
/*     */   public boolean isDirect()
/*     */   {
/*  56 */     return true;
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/*  61 */     return ((ByteBuffer)this.memory).get(idx(paramInt));
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/*  66 */     return ((ByteBuffer)this.memory).getShort(idx(paramInt));
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/*  71 */     paramInt = idx(paramInt);
/*  72 */     return (((ByteBuffer)this.memory).get(paramInt) & 0xFF) << 16 | (((ByteBuffer)this.memory).get(paramInt + 1) & 0xFF) << 8 | ((ByteBuffer)this.memory).get(paramInt + 2) & 0xFF;
/*     */   }
/*     */   
/*     */   protected int _getInt(int paramInt)
/*     */   {
/*  77 */     return ((ByteBuffer)this.memory).getInt(idx(paramInt));
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/*  82 */     return ((ByteBuffer)this.memory).getLong(idx(paramInt));
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/*  87 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/*  88 */     if (paramByteBuf.hasArray()) {
/*  89 */       getBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*  90 */     } else if (paramByteBuf.nioBufferCount() > 0) {
/*  91 */       for (ByteBuffer localByteBuffer : paramByteBuf.nioBuffers(paramInt2, paramInt3)) {
/*  92 */         int k = localByteBuffer.remaining();
/*  93 */         getBytes(paramInt1, localByteBuffer);
/*  94 */         paramInt1 += k;
/*     */       }
/*     */     } else {
/*  97 */       paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 104 */     getBytes(paramInt1, paramArrayOfByte, paramInt2, paramInt3, false);
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 109 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/*     */     ByteBuffer localByteBuffer;
/* 111 */     if (paramBoolean) {
/* 112 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 114 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 116 */     paramInt1 = idx(paramInt1);
/* 117 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt3);
/* 118 */     localByteBuffer.get(paramArrayOfByte, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 123 */     checkReadableBytes(paramInt2);
/* 124 */     getBytes(this.readerIndex, paramArrayOfByte, paramInt1, paramInt2, true);
/* 125 */     this.readerIndex += paramInt2;
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 131 */     getBytes(paramInt, paramByteBuffer, false);
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt, ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 136 */     checkIndex(paramInt);
/* 137 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/*     */     ByteBuffer localByteBuffer;
/* 139 */     if (paramBoolean) {
/* 140 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 142 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 144 */     paramInt = idx(paramInt);
/* 145 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 146 */     paramByteBuffer.put(localByteBuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer paramByteBuffer)
/*     */   {
/* 151 */     int i = paramByteBuffer.remaining();
/* 152 */     checkReadableBytes(i);
/* 153 */     getBytes(this.readerIndex, paramByteBuffer, true);
/* 154 */     this.readerIndex += i;
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2) throws IOException
/*     */   {
/* 160 */     getBytes(paramInt1, paramOutputStream, paramInt2, false);
/* 161 */     return this;
/*     */   }
/*     */   
/*     */   private void getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2, boolean paramBoolean) throws IOException {
/* 165 */     checkIndex(paramInt1, paramInt2);
/* 166 */     if (paramInt2 == 0) {
/* 167 */       return;
/*     */     }
/*     */     
/* 170 */     byte[] arrayOfByte = new byte[paramInt2];
/*     */     ByteBuffer localByteBuffer;
/* 172 */     if (paramBoolean) {
/* 173 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 175 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 177 */     localByteBuffer.clear().position(idx(paramInt1));
/* 178 */     localByteBuffer.get(arrayOfByte);
/* 179 */     paramOutputStream.write(arrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*     */   {
/* 184 */     checkReadableBytes(paramInt);
/* 185 */     getBytes(this.readerIndex, paramOutputStream, paramInt, true);
/* 186 */     this.readerIndex += paramInt;
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 192 */     return getBytes(paramInt1, paramGatheringByteChannel, paramInt2, false);
/*     */   }
/*     */   
/*     */   private int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2, boolean paramBoolean) throws IOException {
/* 196 */     checkIndex(paramInt1, paramInt2);
/* 197 */     if (paramInt2 == 0) {
/* 198 */       return 0;
/*     */     }
/*     */     
/*     */     ByteBuffer localByteBuffer;
/* 202 */     if (paramBoolean) {
/* 203 */       localByteBuffer = internalNioBuffer();
/*     */     } else {
/* 205 */       localByteBuffer = ((ByteBuffer)this.memory).duplicate();
/*     */     }
/* 207 */     paramInt1 = idx(paramInt1);
/* 208 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/* 209 */     return paramGatheringByteChannel.write(localByteBuffer);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel paramGatheringByteChannel, int paramInt) throws IOException
/*     */   {
/* 214 */     checkReadableBytes(paramInt);
/* 215 */     int i = getBytes(this.readerIndex, paramGatheringByteChannel, paramInt, true);
/* 216 */     this.readerIndex += i;
/* 217 */     return i;
/*     */   }
/*     */   
/*     */   protected void _setByte(int paramInt1, int paramInt2)
/*     */   {
/* 222 */     ((ByteBuffer)this.memory).put(idx(paramInt1), (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setShort(int paramInt1, int paramInt2)
/*     */   {
/* 227 */     ((ByteBuffer)this.memory).putShort(idx(paramInt1), (short)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setMedium(int paramInt1, int paramInt2)
/*     */   {
/* 232 */     paramInt1 = idx(paramInt1);
/* 233 */     ((ByteBuffer)this.memory).put(paramInt1, (byte)(paramInt2 >>> 16));
/* 234 */     ((ByteBuffer)this.memory).put(paramInt1 + 1, (byte)(paramInt2 >>> 8));
/* 235 */     ((ByteBuffer)this.memory).put(paramInt1 + 2, (byte)paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setInt(int paramInt1, int paramInt2)
/*     */   {
/* 240 */     ((ByteBuffer)this.memory).putInt(idx(paramInt1), paramInt2);
/*     */   }
/*     */   
/*     */   protected void _setLong(int paramInt, long paramLong)
/*     */   {
/* 245 */     ((ByteBuffer)this.memory).putLong(idx(paramInt), paramLong);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/* 250 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/* 251 */     if (paramByteBuf.hasArray()) {
/* 252 */       setBytes(paramInt1, paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/* 253 */     } else if (paramByteBuf.nioBufferCount() > 0) {
/* 254 */       for (ByteBuffer localByteBuffer : paramByteBuf.nioBuffers(paramInt2, paramInt3)) {
/* 255 */         int k = localByteBuffer.remaining();
/* 256 */         setBytes(paramInt1, localByteBuffer);
/* 257 */         paramInt1 += k;
/*     */       }
/*     */     } else {
/* 260 */       paramByteBuf.getBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/* 262 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/* 267 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/* 268 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 269 */     paramInt1 = idx(paramInt1);
/* 270 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt3);
/* 271 */     localByteBuffer.put(paramArrayOfByte, paramInt2, paramInt3);
/* 272 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 277 */     checkIndex(paramInt, paramByteBuffer.remaining());
/* 278 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 279 */     if (paramByteBuffer == localByteBuffer) {
/* 280 */       paramByteBuffer = paramByteBuffer.duplicate();
/*     */     }
/*     */     
/* 283 */     paramInt = idx(paramInt);
/* 284 */     localByteBuffer.clear().position(paramInt).limit(paramInt + paramByteBuffer.remaining());
/* 285 */     localByteBuffer.put(paramByteBuffer);
/* 286 */     return this;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*     */   {
/* 291 */     checkIndex(paramInt1, paramInt2);
/* 292 */     byte[] arrayOfByte = new byte[paramInt2];
/* 293 */     int i = paramInputStream.read(arrayOfByte);
/* 294 */     if (i <= 0) {
/* 295 */       return i;
/*     */     }
/* 297 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 298 */     localByteBuffer.clear().position(idx(paramInt1));
/* 299 */     localByteBuffer.put(arrayOfByte, 0, i);
/* 300 */     return i;
/*     */   }
/*     */   
/*     */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*     */   {
/* 305 */     checkIndex(paramInt1, paramInt2);
/* 306 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 307 */     paramInt1 = idx(paramInt1);
/* 308 */     localByteBuffer.clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */     try {
/* 310 */       return paramScatteringByteChannel.read(localByteBuffer);
/*     */     } catch (ClosedChannelException localClosedChannelException) {}
/* 312 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 318 */     checkIndex(paramInt1, paramInt2);
/* 319 */     ByteBuf localByteBuf = alloc().directBuffer(paramInt2, maxCapacity());
/* 320 */     localByteBuf.writeBytes(this, paramInt1, paramInt2);
/* 321 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public int nioBufferCount()
/*     */   {
/* 326 */     return 1;
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 331 */     checkIndex(paramInt1, paramInt2);
/* 332 */     paramInt1 = idx(paramInt1);
/* 333 */     return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(paramInt1).limit(paramInt1 + paramInt2)).slice();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*     */   {
/* 338 */     return new ByteBuffer[] { nioBuffer(paramInt1, paramInt2) };
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 343 */     checkIndex(paramInt1, paramInt2);
/* 344 */     paramInt1 = idx(paramInt1);
/* 345 */     return (ByteBuffer)internalNioBuffer().clear().position(paramInt1).limit(paramInt1 + paramInt2);
/*     */   }
/*     */   
/*     */   public boolean hasArray()
/*     */   {
/* 350 */     return false;
/*     */   }
/*     */   
/*     */   public byte[] array()
/*     */   {
/* 355 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public int arrayOffset()
/*     */   {
/* 360 */     throw new UnsupportedOperationException("direct buffer");
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress()
/*     */   {
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   public long memoryAddress()
/*     */   {
/* 370 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected Recycler<?> recycler()
/*     */   {
/* 375 */     return RECYCLER;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PooledDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */