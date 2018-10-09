/*     */ package io.netty.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PoolChunk<T>
/*     */ {
/*     */   final PoolArena<T> arena;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final T memory;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final boolean unpooled;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte[] memoryMap;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte[] depthMap;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final PoolSubpage<T>[] subpages;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int subpageOverflowMask;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int pageSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int pageShifts;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int maxOrder;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int chunkSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int log2ChunkSize;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int maxSubpageAllocs;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final byte unusable;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int freeBytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PoolChunkList<T> parent;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PoolChunk<T> prev;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PoolChunk<T> next;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PoolChunk(PoolArena<T> paramPoolArena, T paramT, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 134 */     this.unpooled = false;
/* 135 */     this.arena = paramPoolArena;
/* 136 */     this.memory = paramT;
/* 137 */     this.pageSize = paramInt1;
/* 138 */     this.pageShifts = paramInt3;
/* 139 */     this.maxOrder = paramInt2;
/* 140 */     this.chunkSize = paramInt4;
/* 141 */     this.unusable = ((byte)(paramInt2 + 1));
/* 142 */     this.log2ChunkSize = log2(paramInt4);
/* 143 */     this.subpageOverflowMask = (paramInt1 - 1 ^ 0xFFFFFFFF);
/* 144 */     this.freeBytes = paramInt4;
/*     */     
/* 146 */     assert (paramInt2 < 30) : ("maxOrder should be < 30, but is: " + paramInt2);
/* 147 */     this.maxSubpageAllocs = (1 << paramInt2);
/*     */     
/*     */ 
/* 150 */     this.memoryMap = new byte[this.maxSubpageAllocs << 1];
/* 151 */     this.depthMap = new byte[this.memoryMap.length];
/* 152 */     int i = 1;
/* 153 */     for (int j = 0; j <= paramInt2; j++) {
/* 154 */       int k = 1 << j;
/* 155 */       for (int m = 0; m < k; m++)
/*     */       {
/* 157 */         this.memoryMap[i] = ((byte)j);
/* 158 */         this.depthMap[i] = ((byte)j);
/* 159 */         i++;
/*     */       }
/*     */     }
/*     */     
/* 163 */     this.subpages = newSubpageArray(this.maxSubpageAllocs);
/*     */   }
/*     */   
/*     */   PoolChunk(PoolArena<T> paramPoolArena, T paramT, int paramInt)
/*     */   {
/* 168 */     this.unpooled = true;
/* 169 */     this.arena = paramPoolArena;
/* 170 */     this.memory = paramT;
/* 171 */     this.memoryMap = null;
/* 172 */     this.depthMap = null;
/* 173 */     this.subpages = null;
/* 174 */     this.subpageOverflowMask = 0;
/* 175 */     this.pageSize = 0;
/* 176 */     this.pageShifts = 0;
/* 177 */     this.maxOrder = 0;
/* 178 */     this.unusable = ((byte)(this.maxOrder + 1));
/* 179 */     this.chunkSize = paramInt;
/* 180 */     this.log2ChunkSize = log2(this.chunkSize);
/* 181 */     this.maxSubpageAllocs = 0;
/*     */   }
/*     */   
/*     */   private PoolSubpage<T>[] newSubpageArray(int paramInt)
/*     */   {
/* 186 */     return new PoolSubpage[paramInt];
/*     */   }
/*     */   
/*     */   int usage() {
/* 190 */     int i = this.freeBytes;
/* 191 */     if (i == 0) {
/* 192 */       return 100;
/*     */     }
/*     */     
/* 195 */     int j = (int)(i * 100L / this.chunkSize);
/* 196 */     if (j == 0) {
/* 197 */       return 99;
/*     */     }
/* 199 */     return 100 - j;
/*     */   }
/*     */   
/*     */   long allocate(int paramInt) {
/* 203 */     if ((paramInt & this.subpageOverflowMask) != 0) {
/* 204 */       return allocateRun(paramInt);
/*     */     }
/* 206 */     return allocateSubpage(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateParentsAlloc(int paramInt)
/*     */   {
/* 219 */     while (paramInt > 1) {
/* 220 */       int i = paramInt >>> 1;
/* 221 */       int j = value(paramInt);
/* 222 */       byte b1 = value(paramInt ^ 0x1);
/* 223 */       byte b2 = j < b1 ? j : b1;
/* 224 */       setValue(i, b2);
/* 225 */       paramInt = i;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void updateParentsFree(int paramInt)
/*     */   {
/* 237 */     int i = depth(paramInt) + 1;
/* 238 */     while (paramInt > 1) {
/* 239 */       int j = paramInt >>> 1;
/* 240 */       int k = value(paramInt);
/* 241 */       int m = value(paramInt ^ 0x1);
/* 242 */       i--;
/*     */       
/* 244 */       if ((k == i) && (m == i)) {
/* 245 */         setValue(j, (byte)(i - 1));
/*     */       } else {
/* 247 */         int n = k < m ? k : m;
/* 248 */         setValue(j, n);
/*     */       }
/*     */       
/* 251 */       paramInt = j;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int allocateNode(int paramInt)
/*     */   {
/* 263 */     int i = 1;
/* 264 */     int j = -(1 << paramInt);
/* 265 */     int k = value(i);
/* 266 */     if (k > paramInt) {
/* 267 */       return -1;
/*     */     }
/* 269 */     while ((k < paramInt) || ((i & j) == 0)) {
/* 270 */       i <<= 1;
/* 271 */       k = value(i);
/* 272 */       if (k > paramInt) {
/* 273 */         i ^= 0x1;
/* 274 */         k = value(i);
/*     */       }
/*     */     }
/* 277 */     int m = value(i);
/* 278 */     if ((!$assertionsDisabled) && ((m != paramInt) || ((i & j) != 1 << paramInt))) { throw new AssertionError(String.format("val = %d, id & initial = %d, d = %d", new Object[] { Byte.valueOf(m), Integer.valueOf(i & j), Integer.valueOf(paramInt) }));
/*     */     }
/* 280 */     setValue(i, this.unusable);
/* 281 */     updateParentsAlloc(i);
/* 282 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long allocateRun(int paramInt)
/*     */   {
/* 292 */     int i = this.maxOrder - (log2(paramInt) - this.pageShifts);
/* 293 */     int j = allocateNode(i);
/* 294 */     if (j < 0) {
/* 295 */       return j;
/*     */     }
/* 297 */     this.freeBytes -= runLength(j);
/* 298 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long allocateSubpage(int paramInt)
/*     */   {
/* 309 */     int i = this.maxOrder;
/* 310 */     int j = allocateNode(i);
/* 311 */     if (j < 0) {
/* 312 */       return j;
/*     */     }
/*     */     
/* 315 */     PoolSubpage[] arrayOfPoolSubpage = this.subpages;
/* 316 */     int k = this.pageSize;
/*     */     
/* 318 */     this.freeBytes -= k;
/*     */     
/* 320 */     int m = subpageIdx(j);
/* 321 */     PoolSubpage localPoolSubpage = arrayOfPoolSubpage[m];
/* 322 */     if (localPoolSubpage == null) {
/* 323 */       localPoolSubpage = new PoolSubpage(this, j, runOffset(j), k, paramInt);
/* 324 */       arrayOfPoolSubpage[m] = localPoolSubpage;
/*     */     } else {
/* 326 */       localPoolSubpage.init(paramInt);
/*     */     }
/* 328 */     return localPoolSubpage.allocate();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void free(long paramLong)
/*     */   {
/* 340 */     int i = (int)paramLong;
/* 341 */     int j = (int)(paramLong >>> 32);
/*     */     
/* 343 */     if (j != 0) {
/* 344 */       PoolSubpage localPoolSubpage = this.subpages[subpageIdx(i)];
/* 345 */       assert ((localPoolSubpage != null) && (localPoolSubpage.doNotDestroy));
/* 346 */       if (localPoolSubpage.free(j & 0x3FFFFFFF)) {
/* 347 */         return;
/*     */       }
/*     */     }
/* 350 */     this.freeBytes += runLength(i);
/* 351 */     setValue(i, depth(i));
/* 352 */     updateParentsFree(i);
/*     */   }
/*     */   
/*     */   void initBuf(PooledByteBuf<T> paramPooledByteBuf, long paramLong, int paramInt) {
/* 356 */     int i = (int)paramLong;
/* 357 */     int j = (int)(paramLong >>> 32);
/* 358 */     if (j == 0) {
/* 359 */       int k = value(i);
/* 360 */       assert (k == this.unusable) : String.valueOf(k);
/* 361 */       paramPooledByteBuf.init(this, paramLong, runOffset(i), paramInt, runLength(i));
/*     */     } else {
/* 363 */       initBufWithSubpage(paramPooledByteBuf, paramLong, j, paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   void initBufWithSubpage(PooledByteBuf<T> paramPooledByteBuf, long paramLong, int paramInt) {
/* 368 */     initBufWithSubpage(paramPooledByteBuf, paramLong, (int)(paramLong >>> 32), paramInt);
/*     */   }
/*     */   
/*     */   private void initBufWithSubpage(PooledByteBuf<T> paramPooledByteBuf, long paramLong, int paramInt1, int paramInt2) {
/* 372 */     assert (paramInt1 != 0);
/*     */     
/* 374 */     int i = (int)paramLong;
/*     */     
/* 376 */     PoolSubpage localPoolSubpage = this.subpages[subpageIdx(i)];
/* 377 */     assert (localPoolSubpage.doNotDestroy);
/* 378 */     assert (paramInt2 <= localPoolSubpage.elemSize);
/*     */     
/* 380 */     paramPooledByteBuf.init(this, paramLong, runOffset(i) + (paramInt1 & 0x3FFFFFFF) * localPoolSubpage.elemSize, paramInt2, localPoolSubpage.elemSize);
/*     */   }
/*     */   
/*     */ 
/*     */   private byte value(int paramInt)
/*     */   {
/* 386 */     return this.memoryMap[paramInt];
/*     */   }
/*     */   
/*     */   private void setValue(int paramInt, byte paramByte) {
/* 390 */     this.memoryMap[paramInt] = paramByte;
/*     */   }
/*     */   
/*     */   private byte depth(int paramInt) {
/* 394 */     return this.depthMap[paramInt];
/*     */   }
/*     */   
/*     */   private static int log2(int paramInt)
/*     */   {
/* 399 */     return 31 - Integer.numberOfLeadingZeros(paramInt);
/*     */   }
/*     */   
/*     */   private int runLength(int paramInt)
/*     */   {
/* 404 */     return 1 << this.log2ChunkSize - depth(paramInt);
/*     */   }
/*     */   
/*     */   private int runOffset(int paramInt)
/*     */   {
/* 409 */     int i = paramInt ^ 1 << depth(paramInt);
/* 410 */     return i * runLength(paramInt);
/*     */   }
/*     */   
/*     */   private int subpageIdx(int paramInt) {
/* 414 */     return paramInt ^ this.maxSubpageAllocs;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 419 */     StringBuilder localStringBuilder = new StringBuilder();
/* 420 */     localStringBuilder.append("Chunk(");
/* 421 */     localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
/* 422 */     localStringBuilder.append(": ");
/* 423 */     localStringBuilder.append(usage());
/* 424 */     localStringBuilder.append("%, ");
/* 425 */     localStringBuilder.append(this.chunkSize - this.freeBytes);
/* 426 */     localStringBuilder.append('/');
/* 427 */     localStringBuilder.append(this.chunkSize);
/* 428 */     localStringBuilder.append(')');
/* 429 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PoolChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */