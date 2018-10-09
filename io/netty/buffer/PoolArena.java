/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
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
/*     */ abstract class PoolArena<T>
/*     */ {
/*     */   static final int numTinySubpagePools = 32;
/*     */   final PooledByteBufAllocator parent;
/*     */   private final int maxOrder;
/*     */   final int pageSize;
/*     */   final int pageShifts;
/*     */   final int chunkSize;
/*     */   final int subpageOverflowMask;
/*     */   final int numSmallSubpagePools;
/*     */   private final PoolSubpage<T>[] tinySubpagePools;
/*     */   private final PoolSubpage<T>[] smallSubpagePools;
/*     */   private final PoolChunkList<T> q050;
/*     */   private final PoolChunkList<T> q025;
/*     */   private final PoolChunkList<T> q000;
/*     */   private final PoolChunkList<T> qInit;
/*     */   private final PoolChunkList<T> q075;
/*     */   private final PoolChunkList<T> q100;
/*     */   
/*     */   protected PoolArena(PooledByteBufAllocator paramPooledByteBufAllocator, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  50 */     this.parent = paramPooledByteBufAllocator;
/*  51 */     this.pageSize = paramInt1;
/*  52 */     this.maxOrder = paramInt2;
/*  53 */     this.pageShifts = paramInt3;
/*  54 */     this.chunkSize = paramInt4;
/*  55 */     this.subpageOverflowMask = (paramInt1 - 1 ^ 0xFFFFFFFF);
/*  56 */     this.tinySubpagePools = newSubpagePoolArray(32);
/*  57 */     for (int i = 0; i < this.tinySubpagePools.length; i++) {
/*  58 */       this.tinySubpagePools[i] = newSubpagePoolHead(paramInt1);
/*     */     }
/*     */     
/*  61 */     this.numSmallSubpagePools = (paramInt3 - 9);
/*  62 */     this.smallSubpagePools = newSubpagePoolArray(this.numSmallSubpagePools);
/*  63 */     for (i = 0; i < this.smallSubpagePools.length; i++) {
/*  64 */       this.smallSubpagePools[i] = newSubpagePoolHead(paramInt1);
/*     */     }
/*     */     
/*  67 */     this.q100 = new PoolChunkList(this, null, 100, Integer.MAX_VALUE);
/*  68 */     this.q075 = new PoolChunkList(this, this.q100, 75, 100);
/*  69 */     this.q050 = new PoolChunkList(this, this.q075, 50, 100);
/*  70 */     this.q025 = new PoolChunkList(this, this.q050, 25, 75);
/*  71 */     this.q000 = new PoolChunkList(this, this.q025, 1, 50);
/*  72 */     this.qInit = new PoolChunkList(this, this.q000, Integer.MIN_VALUE, 25);
/*     */     
/*  74 */     this.q100.prevList = this.q075;
/*  75 */     this.q075.prevList = this.q050;
/*  76 */     this.q050.prevList = this.q025;
/*  77 */     this.q025.prevList = this.q000;
/*  78 */     this.q000.prevList = null;
/*  79 */     this.qInit.prevList = this.qInit;
/*     */   }
/*     */   
/*     */   private PoolSubpage<T> newSubpagePoolHead(int paramInt) {
/*  83 */     PoolSubpage localPoolSubpage = new PoolSubpage(paramInt);
/*  84 */     localPoolSubpage.prev = localPoolSubpage;
/*  85 */     localPoolSubpage.next = localPoolSubpage;
/*  86 */     return localPoolSubpage;
/*     */   }
/*     */   
/*     */   private PoolSubpage<T>[] newSubpagePoolArray(int paramInt)
/*     */   {
/*  91 */     return new PoolSubpage[paramInt];
/*     */   }
/*     */   
/*     */   abstract boolean isDirect();
/*     */   
/*     */   PooledByteBuf<T> allocate(PoolThreadCache paramPoolThreadCache, int paramInt1, int paramInt2) {
/*  97 */     PooledByteBuf localPooledByteBuf = newByteBuf(paramInt2);
/*  98 */     allocate(paramPoolThreadCache, localPooledByteBuf, paramInt1);
/*  99 */     return localPooledByteBuf;
/*     */   }
/*     */   
/*     */   static int tinyIdx(int paramInt) {
/* 103 */     return paramInt >>> 4;
/*     */   }
/*     */   
/*     */   static int smallIdx(int paramInt) {
/* 107 */     int i = 0;
/* 108 */     int j = paramInt >>> 10;
/* 109 */     while (j != 0) {
/* 110 */       j >>>= 1;
/* 111 */       i++;
/*     */     }
/* 113 */     return i;
/*     */   }
/*     */   
/*     */   boolean isTinyOrSmall(int paramInt)
/*     */   {
/* 118 */     return (paramInt & this.subpageOverflowMask) == 0;
/*     */   }
/*     */   
/*     */   static boolean isTiny(int paramInt)
/*     */   {
/* 123 */     return (paramInt & 0xFE00) == 0;
/*     */   }
/*     */   
/*     */   private void allocate(PoolThreadCache paramPoolThreadCache, PooledByteBuf<T> paramPooledByteBuf, int paramInt) {
/* 127 */     int i = normalizeCapacity(paramInt);
/* 128 */     if (isTinyOrSmall(i)) {
/*     */       int j;
/*     */       PoolSubpage[] arrayOfPoolSubpage;
/* 131 */       if (isTiny(i)) {
/* 132 */         if (paramPoolThreadCache.allocateTiny(this, paramPooledByteBuf, paramInt, i))
/*     */         {
/* 134 */           return;
/*     */         }
/* 136 */         j = tinyIdx(i);
/* 137 */         arrayOfPoolSubpage = this.tinySubpagePools;
/*     */       } else {
/* 139 */         if (paramPoolThreadCache.allocateSmall(this, paramPooledByteBuf, paramInt, i))
/*     */         {
/* 141 */           return;
/*     */         }
/* 143 */         j = smallIdx(i);
/* 144 */         arrayOfPoolSubpage = this.smallSubpagePools;
/*     */       }
/*     */       
/* 147 */       synchronized (this) {
/* 148 */         PoolSubpage localPoolSubpage1 = arrayOfPoolSubpage[j];
/* 149 */         PoolSubpage localPoolSubpage2 = localPoolSubpage1.next;
/* 150 */         if (localPoolSubpage2 != localPoolSubpage1) {
/* 151 */           assert ((localPoolSubpage2.doNotDestroy) && (localPoolSubpage2.elemSize == i));
/* 152 */           long l = localPoolSubpage2.allocate();
/* 153 */           assert (l >= 0L);
/* 154 */           localPoolSubpage2.chunk.initBufWithSubpage(paramPooledByteBuf, l, paramInt);
/* 155 */           return;
/*     */         }
/*     */       }
/* 158 */     } else if (i <= this.chunkSize) {
/* 159 */       if (!paramPoolThreadCache.allocateNormal(this, paramPooledByteBuf, paramInt, i)) {}
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 165 */       allocateHuge(paramPooledByteBuf, paramInt);
/* 166 */       return;
/*     */     }
/* 168 */     allocateNormal(paramPooledByteBuf, paramInt, i);
/*     */   }
/*     */   
/*     */   private synchronized void allocateNormal(PooledByteBuf<T> paramPooledByteBuf, int paramInt1, int paramInt2) {
/* 172 */     if ((this.q050.allocate(paramPooledByteBuf, paramInt1, paramInt2)) || (this.q025.allocate(paramPooledByteBuf, paramInt1, paramInt2)) || (this.q000.allocate(paramPooledByteBuf, paramInt1, paramInt2)) || (this.qInit.allocate(paramPooledByteBuf, paramInt1, paramInt2)) || (this.q075.allocate(paramPooledByteBuf, paramInt1, paramInt2)) || (this.q100.allocate(paramPooledByteBuf, paramInt1, paramInt2)))
/*     */     {
/*     */ 
/* 175 */       return;
/*     */     }
/*     */     
/*     */ 
/* 179 */     PoolChunk localPoolChunk = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
/* 180 */     long l = localPoolChunk.allocate(paramInt2);
/* 181 */     assert (l > 0L);
/* 182 */     localPoolChunk.initBuf(paramPooledByteBuf, l, paramInt1);
/* 183 */     this.qInit.add(localPoolChunk);
/*     */   }
/*     */   
/*     */   private void allocateHuge(PooledByteBuf<T> paramPooledByteBuf, int paramInt) {
/* 187 */     paramPooledByteBuf.initUnpooled(newUnpooledChunk(paramInt), paramInt);
/*     */   }
/*     */   
/*     */   void free(PoolChunk<T> paramPoolChunk, long paramLong, int paramInt) {
/* 191 */     if (paramPoolChunk.unpooled) {
/* 192 */       destroyChunk(paramPoolChunk);
/*     */     } else {
/* 194 */       PoolThreadCache localPoolThreadCache = (PoolThreadCache)this.parent.threadCache.get();
/* 195 */       if (localPoolThreadCache.add(this, paramPoolChunk, paramLong, paramInt))
/*     */       {
/* 197 */         return;
/*     */       }
/* 199 */       synchronized (this) {
/* 200 */         paramPoolChunk.parent.free(paramPoolChunk, paramLong);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   PoolSubpage<T> findSubpagePoolHead(int paramInt) {
/*     */     int i;
/*     */     PoolSubpage[] arrayOfPoolSubpage;
/* 208 */     if (isTiny(paramInt)) {
/* 209 */       i = paramInt >>> 4;
/* 210 */       arrayOfPoolSubpage = this.tinySubpagePools;
/*     */     } else {
/* 212 */       i = 0;
/* 213 */       paramInt >>>= 10;
/* 214 */       while (paramInt != 0) {
/* 215 */         paramInt >>>= 1;
/* 216 */         i++;
/*     */       }
/* 218 */       arrayOfPoolSubpage = this.smallSubpagePools;
/*     */     }
/*     */     
/* 221 */     return arrayOfPoolSubpage[i];
/*     */   }
/*     */   
/*     */   int normalizeCapacity(int paramInt) {
/* 225 */     if (paramInt < 0) {
/* 226 */       throw new IllegalArgumentException("capacity: " + paramInt + " (expected: 0+)");
/*     */     }
/* 228 */     if (paramInt >= this.chunkSize) {
/* 229 */       return paramInt;
/*     */     }
/*     */     
/* 232 */     if (!isTiny(paramInt))
/*     */     {
/*     */ 
/* 235 */       int i = paramInt;
/* 236 */       i--;
/* 237 */       i |= i >>> 1;
/* 238 */       i |= i >>> 2;
/* 239 */       i |= i >>> 4;
/* 240 */       i |= i >>> 8;
/* 241 */       i |= i >>> 16;
/* 242 */       i++;
/*     */       
/* 244 */       if (i < 0) {
/* 245 */         i >>>= 1;
/*     */       }
/*     */       
/* 248 */       return i;
/*     */     }
/*     */     
/*     */ 
/* 252 */     if ((paramInt & 0xF) == 0) {
/* 253 */       return paramInt;
/*     */     }
/*     */     
/* 256 */     return (paramInt & 0xFFFFFFF0) + 16;
/*     */   }
/*     */   
/*     */   void reallocate(PooledByteBuf<T> paramPooledByteBuf, int paramInt, boolean paramBoolean) {
/* 260 */     if ((paramInt < 0) || (paramInt > paramPooledByteBuf.maxCapacity())) {
/* 261 */       throw new IllegalArgumentException("newCapacity: " + paramInt);
/*     */     }
/*     */     
/* 264 */     int i = paramPooledByteBuf.length;
/* 265 */     if (i == paramInt) {
/* 266 */       return;
/*     */     }
/*     */     
/* 269 */     PoolChunk localPoolChunk = paramPooledByteBuf.chunk;
/* 270 */     long l = paramPooledByteBuf.handle;
/* 271 */     Object localObject = paramPooledByteBuf.memory;
/* 272 */     int j = paramPooledByteBuf.offset;
/* 273 */     int k = paramPooledByteBuf.maxLength;
/* 274 */     int m = paramPooledByteBuf.readerIndex();
/* 275 */     int n = paramPooledByteBuf.writerIndex();
/*     */     
/* 277 */     allocate((PoolThreadCache)this.parent.threadCache.get(), paramPooledByteBuf, paramInt);
/* 278 */     if (paramInt > i) {
/* 279 */       memoryCopy(localObject, j, paramPooledByteBuf.memory, paramPooledByteBuf.offset, i);
/*     */ 
/*     */     }
/* 282 */     else if (paramInt < i) {
/* 283 */       if (m < paramInt) {
/* 284 */         if (n > paramInt) {
/* 285 */           n = paramInt;
/*     */         }
/* 287 */         memoryCopy(localObject, j + m, paramPooledByteBuf.memory, paramPooledByteBuf.offset + m, n - m);
/*     */       }
/*     */       else
/*     */       {
/* 291 */         m = n = paramInt;
/*     */       }
/*     */     }
/*     */     
/* 295 */     paramPooledByteBuf.setIndex(m, n);
/*     */     
/* 297 */     if (paramBoolean)
/* 298 */       free(localPoolChunk, l, k); }
/*     */   
/*     */   protected abstract PoolChunk<T> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   protected abstract PoolChunk<T> newUnpooledChunk(int paramInt);
/*     */   
/*     */   protected abstract PooledByteBuf<T> newByteBuf(int paramInt);
/*     */   
/*     */   protected abstract void memoryCopy(T paramT1, int paramInt1, T paramT2, int paramInt2, int paramInt3);
/*     */   
/*     */   protected abstract void destroyChunk(PoolChunk<T> paramPoolChunk);
/* 309 */   public synchronized String toString() { StringBuilder localStringBuilder = new StringBuilder();
/* 310 */     localStringBuilder.append("Chunk(s) at 0~25%:");
/* 311 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 312 */     localStringBuilder.append(this.qInit);
/* 313 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 314 */     localStringBuilder.append("Chunk(s) at 0~50%:");
/* 315 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 316 */     localStringBuilder.append(this.q000);
/* 317 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 318 */     localStringBuilder.append("Chunk(s) at 25~75%:");
/* 319 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 320 */     localStringBuilder.append(this.q025);
/* 321 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 322 */     localStringBuilder.append("Chunk(s) at 50~100%:");
/* 323 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 324 */     localStringBuilder.append(this.q050);
/* 325 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 326 */     localStringBuilder.append("Chunk(s) at 75~100%:");
/* 327 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 328 */     localStringBuilder.append(this.q075);
/* 329 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 330 */     localStringBuilder.append("Chunk(s) at 100%:");
/* 331 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 332 */     localStringBuilder.append(this.q100);
/* 333 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 334 */     localStringBuilder.append("tiny subpages:");
/* 335 */     PoolSubpage localPoolSubpage1; PoolSubpage localPoolSubpage2; for (int i = 1; i < this.tinySubpagePools.length; i++) {
/* 336 */       localPoolSubpage1 = this.tinySubpagePools[i];
/* 337 */       if (localPoolSubpage1.next != localPoolSubpage1)
/*     */       {
/*     */ 
/*     */ 
/* 341 */         localStringBuilder.append(StringUtil.NEWLINE);
/* 342 */         localStringBuilder.append(i);
/* 343 */         localStringBuilder.append(": ");
/* 344 */         localPoolSubpage2 = localPoolSubpage1.next;
/*     */         for (;;) {
/* 346 */           localStringBuilder.append(localPoolSubpage2);
/* 347 */           localPoolSubpage2 = localPoolSubpage2.next;
/* 348 */           if (localPoolSubpage2 == localPoolSubpage1)
/*     */             break;
/*     */         }
/*     */       }
/*     */     }
/* 353 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 354 */     localStringBuilder.append("small subpages:");
/* 355 */     for (i = 1; i < this.smallSubpagePools.length; i++) {
/* 356 */       localPoolSubpage1 = this.smallSubpagePools[i];
/* 357 */       if (localPoolSubpage1.next != localPoolSubpage1)
/*     */       {
/*     */ 
/*     */ 
/* 361 */         localStringBuilder.append(StringUtil.NEWLINE);
/* 362 */         localStringBuilder.append(i);
/* 363 */         localStringBuilder.append(": ");
/* 364 */         localPoolSubpage2 = localPoolSubpage1.next;
/*     */         for (;;) {
/* 366 */           localStringBuilder.append(localPoolSubpage2);
/* 367 */           localPoolSubpage2 = localPoolSubpage2.next;
/* 368 */           if (localPoolSubpage2 == localPoolSubpage1)
/*     */             break;
/*     */         }
/*     */       }
/*     */     }
/* 373 */     localStringBuilder.append(StringUtil.NEWLINE);
/*     */     
/* 375 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   static final class HeapArena extends PoolArena<byte[]>
/*     */   {
/*     */     HeapArena(PooledByteBufAllocator paramPooledByteBufAllocator, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 381 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/*     */     boolean isDirect()
/*     */     {
/* 386 */       return false;
/*     */     }
/*     */     
/*     */     protected PoolChunk<byte[]> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 391 */       return new PoolChunk(this, new byte[paramInt4], paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/*     */     protected PoolChunk<byte[]> newUnpooledChunk(int paramInt)
/*     */     {
/* 396 */       return new PoolChunk(this, new byte[paramInt], paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void destroyChunk(PoolChunk<byte[]> paramPoolChunk) {}
/*     */     
/*     */ 
/*     */     protected PooledByteBuf<byte[]> newByteBuf(int paramInt)
/*     */     {
/* 406 */       return PooledHeapByteBuf.newInstance(paramInt);
/*     */     }
/*     */     
/*     */     protected void memoryCopy(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3)
/*     */     {
/* 411 */       if (paramInt3 == 0) {
/* 412 */         return;
/*     */       }
/*     */       
/* 415 */       System.arraycopy(paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2, paramInt3);
/*     */     }
/*     */   }
/*     */   
/*     */   static final class DirectArena extends PoolArena<ByteBuffer>
/*     */   {
/* 421 */     private static final boolean HAS_UNSAFE = ;
/*     */     
/*     */     DirectArena(PooledByteBufAllocator paramPooledByteBufAllocator, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 424 */       super(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/*     */     boolean isDirect()
/*     */     {
/* 429 */       return true;
/*     */     }
/*     */     
/*     */     protected PoolChunk<ByteBuffer> newChunk(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     {
/* 434 */       return new PoolChunk(this, ByteBuffer.allocateDirect(paramInt4), paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     
/*     */ 
/*     */     protected PoolChunk<ByteBuffer> newUnpooledChunk(int paramInt)
/*     */     {
/* 440 */       return new PoolChunk(this, ByteBuffer.allocateDirect(paramInt), paramInt);
/*     */     }
/*     */     
/*     */     protected void destroyChunk(PoolChunk<ByteBuffer> paramPoolChunk)
/*     */     {
/* 445 */       PlatformDependent.freeDirectBuffer((ByteBuffer)paramPoolChunk.memory);
/*     */     }
/*     */     
/*     */     protected PooledByteBuf<ByteBuffer> newByteBuf(int paramInt)
/*     */     {
/* 450 */       if (HAS_UNSAFE) {
/* 451 */         return PooledUnsafeDirectByteBuf.newInstance(paramInt);
/*     */       }
/* 453 */       return PooledDirectByteBuf.newInstance(paramInt);
/*     */     }
/*     */     
/*     */ 
/*     */     protected void memoryCopy(ByteBuffer paramByteBuffer1, int paramInt1, ByteBuffer paramByteBuffer2, int paramInt2, int paramInt3)
/*     */     {
/* 459 */       if (paramInt3 == 0) {
/* 460 */         return;
/*     */       }
/*     */       
/* 463 */       if (HAS_UNSAFE) {
/* 464 */         PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(paramByteBuffer1) + paramInt1, PlatformDependent.directBufferAddress(paramByteBuffer2) + paramInt2, paramInt3);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 469 */         paramByteBuffer1 = paramByteBuffer1.duplicate();
/* 470 */         paramByteBuffer2 = paramByteBuffer2.duplicate();
/* 471 */         paramByteBuffer1.position(paramInt1).limit(paramInt1 + paramInt3);
/* 472 */         paramByteBuffer2.position(paramInt2);
/* 473 */         paramByteBuffer2.put(paramByteBuffer1);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PoolArena.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */