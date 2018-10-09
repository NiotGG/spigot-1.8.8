/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ final class ReadOnlyUnsafeDirectByteBuf
/*     */   extends ReadOnlyByteBufferBuf
/*     */ {
/*  30 */   private static final boolean NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
/*     */   private final long memoryAddress;
/*     */   
/*     */   ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator paramByteBufAllocator, ByteBuffer paramByteBuffer) {
/*  34 */     super(paramByteBufAllocator, paramByteBuffer);
/*  35 */     this.memoryAddress = PlatformDependent.directBufferAddress(paramByteBuffer);
/*     */   }
/*     */   
/*     */   protected byte _getByte(int paramInt)
/*     */   {
/*  40 */     return PlatformDependent.getByte(addr(paramInt));
/*     */   }
/*     */   
/*     */   protected short _getShort(int paramInt)
/*     */   {
/*  45 */     short s = PlatformDependent.getShort(addr(paramInt));
/*  46 */     return NATIVE_ORDER ? s : Short.reverseBytes(s);
/*     */   }
/*     */   
/*     */   protected int _getUnsignedMedium(int paramInt)
/*     */   {
/*  51 */     long l = addr(paramInt);
/*  52 */     return (PlatformDependent.getByte(l) & 0xFF) << 16 | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | PlatformDependent.getByte(l + 2L) & 0xFF;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int _getInt(int paramInt)
/*     */   {
/*  59 */     int i = PlatformDependent.getInt(addr(paramInt));
/*  60 */     return NATIVE_ORDER ? i : Integer.reverseBytes(i);
/*     */   }
/*     */   
/*     */   protected long _getLong(int paramInt)
/*     */   {
/*  65 */     long l = PlatformDependent.getLong(addr(paramInt));
/*  66 */     return NATIVE_ORDER ? l : Long.reverseBytes(l);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*     */   {
/*  71 */     checkIndex(paramInt1, paramInt3);
/*  72 */     if (paramByteBuf == null) {
/*  73 */       throw new NullPointerException("dst");
/*     */     }
/*  75 */     if ((paramInt2 < 0) || (paramInt2 > paramByteBuf.capacity() - paramInt3)) {
/*  76 */       throw new IndexOutOfBoundsException("dstIndex: " + paramInt2);
/*     */     }
/*     */     
/*  79 */     if (paramByteBuf.hasMemoryAddress()) {
/*  80 */       PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.memoryAddress() + paramInt2, paramInt3);
/*  81 */     } else if (paramByteBuf.hasArray()) {
/*  82 */       PlatformDependent.copyMemory(addr(paramInt1), paramByteBuf.array(), paramByteBuf.arrayOffset() + paramInt2, paramInt3);
/*     */     } else {
/*  84 */       paramByteBuf.setBytes(paramInt2, this, paramInt1, paramInt3);
/*     */     }
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */   {
/*  91 */     checkIndex(paramInt1, paramInt3);
/*  92 */     if (paramArrayOfByte == null) {
/*  93 */       throw new NullPointerException("dst");
/*     */     }
/*  95 */     if ((paramInt2 < 0) || (paramInt2 > paramArrayOfByte.length - paramInt3)) {
/*  96 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramArrayOfByte.length) }));
/*     */     }
/*     */     
/*     */ 
/* 100 */     if (paramInt3 != 0) {
/* 101 */       PlatformDependent.copyMemory(addr(paramInt1), paramArrayOfByte, paramInt2, paramInt3);
/*     */     }
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*     */   {
/* 108 */     checkIndex(paramInt);
/* 109 */     if (paramByteBuffer == null) {
/* 110 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 113 */     int i = Math.min(capacity() - paramInt, paramByteBuffer.remaining());
/* 114 */     ByteBuffer localByteBuffer = internalNioBuffer();
/* 115 */     localByteBuffer.clear().position(paramInt).limit(paramInt + i);
/* 116 */     paramByteBuffer.put(localByteBuffer);
/* 117 */     return this;
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int paramInt1, int paramInt2)
/*     */   {
/* 122 */     checkIndex(paramInt1, paramInt2);
/* 123 */     ByteBuf localByteBuf = alloc().directBuffer(paramInt2, maxCapacity());
/* 124 */     if (paramInt2 != 0) {
/* 125 */       if (localByteBuf.hasMemoryAddress()) {
/* 126 */         PlatformDependent.copyMemory(addr(paramInt1), localByteBuf.memoryAddress(), paramInt2);
/* 127 */         localByteBuf.setIndex(0, paramInt2);
/*     */       } else {
/* 129 */         localByteBuf.writeBytes(this, paramInt1, paramInt2);
/*     */       }
/*     */     }
/* 132 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   private long addr(int paramInt) {
/* 136 */     return this.memoryAddress + paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ReadOnlyUnsafeDirectByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */