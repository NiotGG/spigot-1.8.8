/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ThreadDeathWatcher;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PoolThreadCache
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
/*     */   
/*     */   final PoolArena<byte[]> heapArena;
/*     */   
/*     */   final PoolArena<ByteBuffer> directArena;
/*     */   
/*     */   private final MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;
/*     */   
/*     */   private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
/*     */   
/*     */   private final MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
/*     */   
/*     */   private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
/*     */   
/*     */   private final MemoryRegionCache<byte[]>[] normalHeapCaches;
/*     */   private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
/*     */   private final int numShiftsNormalDirect;
/*     */   private final int numShiftsNormalHeap;
/*     */   private final int freeSweepAllocationThreshold;
/*     */   private int allocations;
/*  54 */   private final Thread thread = Thread.currentThread();
/*  55 */   private final Runnable freeTask = new Runnable()
/*     */   {
/*     */     public void run() {
/*  58 */       PoolThreadCache.this.free0();
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   PoolThreadCache(PoolArena<byte[]> paramPoolArena, PoolArena<ByteBuffer> paramPoolArena1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/*  68 */     if (paramInt4 < 0) {
/*  69 */       throw new IllegalArgumentException("maxCachedBufferCapacity: " + paramInt4 + " (expected: >= 0)");
/*     */     }
/*     */     
/*  72 */     if (paramInt5 < 1) {
/*  73 */       throw new IllegalArgumentException("freeSweepAllocationThreshold: " + paramInt4 + " (expected: > 0)");
/*     */     }
/*     */     
/*  76 */     this.freeSweepAllocationThreshold = paramInt5;
/*  77 */     this.heapArena = paramPoolArena;
/*  78 */     this.directArena = paramPoolArena1;
/*  79 */     if (paramPoolArena1 != null) {
/*  80 */       this.tinySubPageDirectCaches = createSubPageCaches(paramInt1, 32);
/*  81 */       this.smallSubPageDirectCaches = createSubPageCaches(paramInt2, paramPoolArena1.numSmallSubpagePools);
/*     */       
/*  83 */       this.numShiftsNormalDirect = log2(paramPoolArena1.pageSize);
/*  84 */       this.normalDirectCaches = createNormalCaches(paramInt3, paramInt4, paramPoolArena1);
/*     */     }
/*     */     else
/*     */     {
/*  88 */       this.tinySubPageDirectCaches = null;
/*  89 */       this.smallSubPageDirectCaches = null;
/*  90 */       this.normalDirectCaches = null;
/*  91 */       this.numShiftsNormalDirect = -1;
/*     */     }
/*  93 */     if (paramPoolArena != null)
/*     */     {
/*  95 */       this.tinySubPageHeapCaches = createSubPageCaches(paramInt1, 32);
/*  96 */       this.smallSubPageHeapCaches = createSubPageCaches(paramInt2, paramPoolArena.numSmallSubpagePools);
/*     */       
/*  98 */       this.numShiftsNormalHeap = log2(paramPoolArena.pageSize);
/*  99 */       this.normalHeapCaches = createNormalCaches(paramInt3, paramInt4, paramPoolArena);
/*     */     }
/*     */     else
/*     */     {
/* 103 */       this.tinySubPageHeapCaches = null;
/* 104 */       this.smallSubPageHeapCaches = null;
/* 105 */       this.normalHeapCaches = null;
/* 106 */       this.numShiftsNormalHeap = -1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 111 */     ThreadDeathWatcher.watch(this.thread, this.freeTask);
/*     */   }
/*     */   
/*     */   private static <T> SubPageMemoryRegionCache<T>[] createSubPageCaches(int paramInt1, int paramInt2) {
/* 115 */     if (paramInt1 > 0)
/*     */     {
/* 117 */       SubPageMemoryRegionCache[] arrayOfSubPageMemoryRegionCache = new SubPageMemoryRegionCache[paramInt2];
/* 118 */       for (int i = 0; i < arrayOfSubPageMemoryRegionCache.length; i++)
/*     */       {
/* 120 */         arrayOfSubPageMemoryRegionCache[i] = new SubPageMemoryRegionCache(paramInt1);
/*     */       }
/* 122 */       return arrayOfSubPageMemoryRegionCache;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private static <T> NormalMemoryRegionCache<T>[] createNormalCaches(int paramInt1, int paramInt2, PoolArena<T> paramPoolArena)
/*     */   {
/* 130 */     if (paramInt1 > 0) {
/* 131 */       int i = Math.min(paramPoolArena.chunkSize, paramInt2);
/* 132 */       int j = Math.max(1, i / paramPoolArena.pageSize);
/*     */       
/*     */ 
/* 135 */       NormalMemoryRegionCache[] arrayOfNormalMemoryRegionCache = new NormalMemoryRegionCache[j];
/* 136 */       for (int k = 0; k < arrayOfNormalMemoryRegionCache.length; k++) {
/* 137 */         arrayOfNormalMemoryRegionCache[k] = new NormalMemoryRegionCache(paramInt1);
/*     */       }
/* 139 */       return arrayOfNormalMemoryRegionCache;
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   private static int log2(int paramInt)
/*     */   {
/* 146 */     int i = 0;
/* 147 */     while (paramInt > 1) {
/* 148 */       paramInt >>= 1;
/* 149 */       i++;
/*     */     }
/* 151 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean allocateTiny(PoolArena<?> paramPoolArena, PooledByteBuf<?> paramPooledByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 158 */     return allocate(cacheForTiny(paramPoolArena, paramInt2), paramPooledByteBuf, paramInt1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean allocateSmall(PoolArena<?> paramPoolArena, PooledByteBuf<?> paramPooledByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 165 */     return allocate(cacheForSmall(paramPoolArena, paramInt2), paramPooledByteBuf, paramInt1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean allocateNormal(PoolArena<?> paramPoolArena, PooledByteBuf<?> paramPooledByteBuf, int paramInt1, int paramInt2)
/*     */   {
/* 172 */     return allocate(cacheForNormal(paramPoolArena, paramInt2), paramPooledByteBuf, paramInt1);
/*     */   }
/*     */   
/*     */   private boolean allocate(MemoryRegionCache<?> paramMemoryRegionCache, PooledByteBuf paramPooledByteBuf, int paramInt)
/*     */   {
/* 177 */     if (paramMemoryRegionCache == null)
/*     */     {
/* 179 */       return false;
/*     */     }
/* 181 */     boolean bool = paramMemoryRegionCache.allocate(paramPooledByteBuf, paramInt);
/* 182 */     if (++this.allocations >= this.freeSweepAllocationThreshold) {
/* 183 */       this.allocations = 0;
/* 184 */       trim();
/*     */     }
/* 186 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   boolean add(PoolArena<?> paramPoolArena, PoolChunk paramPoolChunk, long paramLong, int paramInt)
/*     */   {
/*     */     MemoryRegionCache localMemoryRegionCache;
/*     */     
/*     */ 
/* 196 */     if (paramPoolArena.isTinyOrSmall(paramInt)) {
/* 197 */       if (PoolArena.isTiny(paramInt)) {
/* 198 */         localMemoryRegionCache = cacheForTiny(paramPoolArena, paramInt);
/*     */       } else {
/* 200 */         localMemoryRegionCache = cacheForSmall(paramPoolArena, paramInt);
/*     */       }
/*     */     } else {
/* 203 */       localMemoryRegionCache = cacheForNormal(paramPoolArena, paramInt);
/*     */     }
/* 205 */     if (localMemoryRegionCache == null) {
/* 206 */       return false;
/*     */     }
/* 208 */     return localMemoryRegionCache.add(paramPoolChunk, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void free()
/*     */   {
/* 215 */     ThreadDeathWatcher.unwatch(this.thread, this.freeTask);
/* 216 */     free0();
/*     */   }
/*     */   
/*     */   private void free0() {
/* 220 */     int i = free(this.tinySubPageDirectCaches) + free(this.smallSubPageDirectCaches) + free(this.normalDirectCaches) + free(this.tinySubPageHeapCaches) + free(this.smallSubPageHeapCaches) + free(this.normalHeapCaches);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */     if ((i > 0) && (logger.isDebugEnabled())) {
/* 228 */       logger.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(i), this.thread.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   private static int free(MemoryRegionCache<?>[] paramArrayOfMemoryRegionCache) {
/* 233 */     if (paramArrayOfMemoryRegionCache == null) {
/* 234 */       return 0;
/*     */     }
/*     */     
/* 237 */     int i = 0;
/* 238 */     for (MemoryRegionCache<?> localMemoryRegionCache : paramArrayOfMemoryRegionCache) {
/* 239 */       i += free(localMemoryRegionCache);
/*     */     }
/* 241 */     return i;
/*     */   }
/*     */   
/*     */   private static int free(MemoryRegionCache<?> paramMemoryRegionCache) {
/* 245 */     if (paramMemoryRegionCache == null) {
/* 246 */       return 0;
/*     */     }
/* 248 */     return paramMemoryRegionCache.free();
/*     */   }
/*     */   
/*     */   void trim() {
/* 252 */     trim(this.tinySubPageDirectCaches);
/* 253 */     trim(this.smallSubPageDirectCaches);
/* 254 */     trim(this.normalDirectCaches);
/* 255 */     trim(this.tinySubPageHeapCaches);
/* 256 */     trim(this.smallSubPageHeapCaches);
/* 257 */     trim(this.normalHeapCaches);
/*     */   }
/*     */   
/*     */   private static void trim(MemoryRegionCache<?>[] paramArrayOfMemoryRegionCache) {
/* 261 */     if (paramArrayOfMemoryRegionCache == null) {
/* 262 */       return;
/*     */     }
/* 264 */     for (MemoryRegionCache<?> localMemoryRegionCache : paramArrayOfMemoryRegionCache) {
/* 265 */       trim(localMemoryRegionCache);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void trim(MemoryRegionCache<?> paramMemoryRegionCache) {
/* 270 */     if (paramMemoryRegionCache == null) {
/* 271 */       return;
/*     */     }
/* 273 */     paramMemoryRegionCache.trim();
/*     */   }
/*     */   
/*     */   private MemoryRegionCache<?> cacheForTiny(PoolArena<?> paramPoolArena, int paramInt) {
/* 277 */     int i = PoolArena.tinyIdx(paramInt);
/* 278 */     if (paramPoolArena.isDirect()) {
/* 279 */       return cache(this.tinySubPageDirectCaches, i);
/*     */     }
/* 281 */     return cache(this.tinySubPageHeapCaches, i);
/*     */   }
/*     */   
/*     */   private MemoryRegionCache<?> cacheForSmall(PoolArena<?> paramPoolArena, int paramInt) {
/* 285 */     int i = PoolArena.smallIdx(paramInt);
/* 286 */     if (paramPoolArena.isDirect()) {
/* 287 */       return cache(this.smallSubPageDirectCaches, i);
/*     */     }
/* 289 */     return cache(this.smallSubPageHeapCaches, i);
/*     */   }
/*     */   
/*     */   private MemoryRegionCache<?> cacheForNormal(PoolArena<?> paramPoolArena, int paramInt) {
/* 293 */     if (paramPoolArena.isDirect()) {
/* 294 */       i = log2(paramInt >> this.numShiftsNormalDirect);
/* 295 */       return cache(this.normalDirectCaches, i);
/*     */     }
/* 297 */     int i = log2(paramInt >> this.numShiftsNormalHeap);
/* 298 */     return cache(this.normalHeapCaches, i);
/*     */   }
/*     */   
/*     */   private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] paramArrayOfMemoryRegionCache, int paramInt) {
/* 302 */     if ((paramArrayOfMemoryRegionCache == null) || (paramInt > paramArrayOfMemoryRegionCache.length - 1)) {
/* 303 */       return null;
/*     */     }
/* 305 */     return paramArrayOfMemoryRegionCache[paramInt];
/*     */   }
/*     */   
/*     */   private static final class SubPageMemoryRegionCache<T>
/*     */     extends PoolThreadCache.MemoryRegionCache<T>
/*     */   {
/*     */     SubPageMemoryRegionCache(int paramInt)
/*     */     {
/* 313 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     protected void initBuf(PoolChunk<T> paramPoolChunk, long paramLong, PooledByteBuf<T> paramPooledByteBuf, int paramInt)
/*     */     {
/* 319 */       paramPoolChunk.initBufWithSubpage(paramPooledByteBuf, paramLong, paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class NormalMemoryRegionCache<T>
/*     */     extends PoolThreadCache.MemoryRegionCache<T>
/*     */   {
/*     */     NormalMemoryRegionCache(int paramInt)
/*     */     {
/* 328 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */     protected void initBuf(PoolChunk<T> paramPoolChunk, long paramLong, PooledByteBuf<T> paramPooledByteBuf, int paramInt)
/*     */     {
/* 334 */       paramPoolChunk.initBuf(paramPooledByteBuf, paramLong, paramInt);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static abstract class MemoryRegionCache<T>
/*     */   {
/*     */     private final Entry<T>[] entries;
/*     */     
/*     */     private final int maxUnusedCached;
/*     */     private int head;
/*     */     private int tail;
/*     */     private int maxEntriesInUse;
/*     */     private int entriesInUse;
/*     */     
/*     */     MemoryRegionCache(int paramInt)
/*     */     {
/* 351 */       this.entries = new Entry[powerOfTwo(paramInt)];
/* 352 */       for (int i = 0; i < this.entries.length; i++) {
/* 353 */         this.entries[i] = new Entry(null);
/*     */       }
/* 355 */       this.maxUnusedCached = (paramInt / 2);
/*     */     }
/*     */     
/*     */     private static int powerOfTwo(int paramInt) {
/* 359 */       if (paramInt <= 2) {
/* 360 */         return 2;
/*     */       }
/* 362 */       paramInt--;
/* 363 */       paramInt |= paramInt >> 1;
/* 364 */       paramInt |= paramInt >> 2;
/* 365 */       paramInt |= paramInt >> 4;
/* 366 */       paramInt |= paramInt >> 8;
/* 367 */       paramInt |= paramInt >> 16;
/* 368 */       paramInt++;
/* 369 */       return paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected abstract void initBuf(PoolChunk<T> paramPoolChunk, long paramLong, PooledByteBuf<T> paramPooledByteBuf, int paramInt);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean add(PoolChunk<T> paramPoolChunk, long paramLong)
/*     */     {
/* 382 */       Entry localEntry = this.entries[this.tail];
/* 383 */       if (localEntry.chunk != null)
/*     */       {
/* 385 */         return false;
/*     */       }
/* 387 */       this.entriesInUse -= 1;
/*     */       
/* 389 */       localEntry.chunk = paramPoolChunk;
/* 390 */       localEntry.handle = paramLong;
/* 391 */       this.tail = nextIdx(this.tail);
/* 392 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean allocate(PooledByteBuf<T> paramPooledByteBuf, int paramInt)
/*     */     {
/* 399 */       Entry localEntry = this.entries[this.head];
/* 400 */       if (localEntry.chunk == null) {
/* 401 */         return false;
/*     */       }
/*     */       
/* 404 */       this.entriesInUse += 1;
/* 405 */       if (this.maxEntriesInUse < this.entriesInUse) {
/* 406 */         this.maxEntriesInUse = this.entriesInUse;
/*     */       }
/* 408 */       initBuf(localEntry.chunk, localEntry.handle, paramPooledByteBuf, paramInt);
/*     */       
/* 410 */       localEntry.chunk = null;
/* 411 */       this.head = nextIdx(this.head);
/* 412 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public int free()
/*     */     {
/* 419 */       int i = 0;
/* 420 */       this.entriesInUse = 0;
/* 421 */       this.maxEntriesInUse = 0;
/* 422 */       for (int j = this.head;; j = nextIdx(j)) {
/* 423 */         if (freeEntry(this.entries[j])) {
/* 424 */           i++;
/*     */         }
/*     */         else {
/* 427 */           return i;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private void trim()
/*     */     {
/* 436 */       int i = size() - this.maxEntriesInUse;
/* 437 */       this.entriesInUse = 0;
/* 438 */       this.maxEntriesInUse = 0;
/*     */       
/* 440 */       if (i <= this.maxUnusedCached) {
/* 441 */         return;
/*     */       }
/*     */       
/* 444 */       int j = this.head;
/* 445 */       for (; i > 0; i--) {
/* 446 */         if (!freeEntry(this.entries[j]))
/*     */         {
/* 448 */           return;
/*     */         }
/* 450 */         j = nextIdx(j);
/*     */       }
/*     */     }
/*     */     
/*     */     private static boolean freeEntry(Entry paramEntry)
/*     */     {
/* 456 */       PoolChunk localPoolChunk = paramEntry.chunk;
/* 457 */       if (localPoolChunk == null) {
/* 458 */         return false;
/*     */       }
/*     */       
/* 461 */       synchronized (localPoolChunk.arena) {
/* 462 */         localPoolChunk.parent.free(localPoolChunk, paramEntry.handle);
/*     */       }
/* 464 */       paramEntry.chunk = null;
/* 465 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private int size()
/*     */     {
/* 472 */       return this.tail - this.head & this.entries.length - 1;
/*     */     }
/*     */     
/*     */     private int nextIdx(int paramInt)
/*     */     {
/* 477 */       return paramInt + 1 & this.entries.length - 1;
/*     */     }
/*     */     
/*     */     private static final class Entry<T>
/*     */     {
/*     */       PoolChunk<T> chunk;
/*     */       long handle;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PoolThreadCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */