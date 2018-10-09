/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class PooledByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */ {
/*  30 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
/*     */   
/*     */   private static final int DEFAULT_NUM_HEAP_ARENA;
/*     */   private static final int DEFAULT_NUM_DIRECT_ARENA;
/*     */   private static final int DEFAULT_PAGE_SIZE;
/*     */   private static final int DEFAULT_MAX_ORDER;
/*     */   private static final int DEFAULT_TINY_CACHE_SIZE;
/*     */   private static final int DEFAULT_SMALL_CACHE_SIZE;
/*     */   private static final int DEFAULT_NORMAL_CACHE_SIZE;
/*     */   private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
/*     */   private static final int DEFAULT_CACHE_TRIM_INTERVAL;
/*     */   private static final int MIN_PAGE_SIZE = 4096;
/*     */   private static final int MAX_CHUNK_SIZE = 1073741824;
/*     */   
/*     */   static
/*     */   {
/*  46 */     int i = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
/*  47 */     Object localObject1 = null;
/*     */     try {
/*  49 */       validateAndCalculatePageShifts(i);
/*     */     } catch (Throwable localThrowable1) {
/*  51 */       localObject1 = localThrowable1;
/*  52 */       i = 8192;
/*     */     }
/*  54 */     DEFAULT_PAGE_SIZE = i;
/*     */     
/*  56 */     int j = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
/*  57 */     Object localObject2 = null;
/*     */     try {
/*  59 */       validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, j);
/*     */     } catch (Throwable localThrowable2) {
/*  61 */       localObject2 = localThrowable2;
/*  62 */       j = 11;
/*     */     }
/*  64 */     DEFAULT_MAX_ORDER = j;
/*     */     
/*     */ 
/*     */ 
/*  68 */     Runtime localRuntime = Runtime.getRuntime();
/*  69 */     int k = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
/*  70 */     DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min(localRuntime.availableProcessors(), Runtime.getRuntime().maxMemory() / k / 2L / 3L)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  76 */     DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min(localRuntime.availableProcessors(), PlatformDependent.maxDirectMemory() / k / 2L / 3L)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  84 */     DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
/*  85 */     DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
/*  86 */     DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
/*     */     
/*     */ 
/*     */ 
/*  90 */     DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
/*     */     
/*     */ 
/*     */ 
/*  94 */     DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
/*     */     
/*     */ 
/*  97 */     if (logger.isDebugEnabled()) {
/*  98 */       logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
/*  99 */       logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
/* 100 */       if (localObject1 == null) {
/* 101 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
/*     */       } else {
/* 103 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), localObject1);
/*     */       }
/* 105 */       if (localObject2 == null) {
/* 106 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
/*     */       } else {
/* 108 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), localObject2);
/*     */       }
/* 110 */       logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
/* 111 */       logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", Integer.valueOf(DEFAULT_TINY_CACHE_SIZE));
/* 112 */       logger.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
/* 113 */       logger.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
/* 114 */       logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
/* 115 */       logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
/*     */     }
/*     */   }
/*     */   
/* 119 */   public static final PooledByteBufAllocator DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
/*     */   
/*     */   private final PoolArena<byte[]>[] heapArenas;
/*     */   
/*     */   private final PoolArena<ByteBuffer>[] directArenas;
/*     */   private final int tinyCacheSize;
/*     */   private final int smallCacheSize;
/*     */   private final int normalCacheSize;
/*     */   final PoolThreadLocalCache threadCache;
/*     */   
/*     */   public PooledByteBufAllocator()
/*     */   {
/* 131 */     this(false);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(boolean paramBoolean) {
/* 135 */     this(paramBoolean, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 139 */     this(false, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 143 */     this(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
/*     */   }
/*     */   
/*     */ 
/*     */   public PooledByteBufAllocator(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*     */   {
/* 149 */     super(paramBoolean);
/* 150 */     this.threadCache = new PoolThreadLocalCache();
/* 151 */     this.tinyCacheSize = paramInt5;
/* 152 */     this.smallCacheSize = paramInt6;
/* 153 */     this.normalCacheSize = paramInt7;
/* 154 */     int i = validateAndCalculateChunkSize(paramInt3, paramInt4);
/*     */     
/* 156 */     if (paramInt1 < 0) {
/* 157 */       throw new IllegalArgumentException("nHeapArena: " + paramInt1 + " (expected: >= 0)");
/*     */     }
/* 159 */     if (paramInt2 < 0) {
/* 160 */       throw new IllegalArgumentException("nDirectArea: " + paramInt2 + " (expected: >= 0)");
/*     */     }
/*     */     
/* 163 */     int j = validateAndCalculatePageShifts(paramInt3);
/*     */     int k;
/* 165 */     if (paramInt1 > 0) {
/* 166 */       this.heapArenas = newArenaArray(paramInt1);
/* 167 */       for (k = 0; k < this.heapArenas.length; k++) {
/* 168 */         this.heapArenas[k] = new PoolArena.HeapArena(this, paramInt3, paramInt4, j, i);
/*     */       }
/*     */     } else {
/* 171 */       this.heapArenas = null;
/*     */     }
/*     */     
/* 174 */     if (paramInt2 > 0) {
/* 175 */       this.directArenas = newArenaArray(paramInt2);
/* 176 */       for (k = 0; k < this.directArenas.length; k++) {
/* 177 */         this.directArenas[k] = new PoolArena.DirectArena(this, paramInt3, paramInt4, j, i);
/*     */       }
/*     */     } else {
/* 180 */       this.directArenas = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PooledByteBufAllocator(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong)
/*     */   {
/* 189 */     this(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */   }
/*     */   
/*     */ 
/*     */   private static <T> PoolArena<T>[] newArenaArray(int paramInt)
/*     */   {
/* 195 */     return new PoolArena[paramInt];
/*     */   }
/*     */   
/*     */   private static int validateAndCalculatePageShifts(int paramInt) {
/* 199 */     if (paramInt < 4096) {
/* 200 */       throw new IllegalArgumentException("pageSize: " + paramInt + " (expected: " + 4096 + "+)");
/*     */     }
/*     */     
/* 203 */     if ((paramInt & paramInt - 1) != 0) {
/* 204 */       throw new IllegalArgumentException("pageSize: " + paramInt + " (expected: power of 2)");
/*     */     }
/*     */     
/*     */ 
/* 208 */     return 31 - Integer.numberOfLeadingZeros(paramInt);
/*     */   }
/*     */   
/*     */   private static int validateAndCalculateChunkSize(int paramInt1, int paramInt2) {
/* 212 */     if (paramInt2 > 14) {
/* 213 */       throw new IllegalArgumentException("maxOrder: " + paramInt2 + " (expected: 0-14)");
/*     */     }
/*     */     
/*     */ 
/* 217 */     int i = paramInt1;
/* 218 */     for (int j = paramInt2; j > 0; j--) {
/* 219 */       if (i > 536870912) {
/* 220 */         throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(1073741824) }));
/*     */       }
/*     */       
/* 223 */       i <<= 1;
/*     */     }
/* 225 */     return i;
/*     */   }
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 230 */     PoolThreadCache localPoolThreadCache = (PoolThreadCache)this.threadCache.get();
/* 231 */     PoolArena localPoolArena = localPoolThreadCache.heapArena;
/*     */     
/*     */     Object localObject;
/* 234 */     if (localPoolArena != null) {
/* 235 */       localObject = localPoolArena.allocate(localPoolThreadCache, paramInt1, paramInt2);
/*     */     } else {
/* 237 */       localObject = new UnpooledHeapByteBuf(this, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 240 */     return toLeakAwareBuffer((ByteBuf)localObject);
/*     */   }
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 245 */     PoolThreadCache localPoolThreadCache = (PoolThreadCache)this.threadCache.get();
/* 246 */     PoolArena localPoolArena = localPoolThreadCache.directArena;
/*     */     
/*     */     Object localObject;
/* 249 */     if (localPoolArena != null) {
/* 250 */       localObject = localPoolArena.allocate(localPoolThreadCache, paramInt1, paramInt2);
/*     */     }
/* 252 */     else if (PlatformDependent.hasUnsafe()) {
/* 253 */       localObject = new UnpooledUnsafeDirectByteBuf(this, paramInt1, paramInt2);
/*     */     } else {
/* 255 */       localObject = new UnpooledDirectByteBuf(this, paramInt1, paramInt2);
/*     */     }
/*     */     
/*     */ 
/* 259 */     return toLeakAwareBuffer((ByteBuf)localObject);
/*     */   }
/*     */   
/*     */   public boolean isDirectBufferPooled()
/*     */   {
/* 264 */     return this.directArenas != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public boolean hasThreadLocalCache()
/*     */   {
/* 273 */     return this.threadCache.isSet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void freeThreadLocalCache()
/*     */   {
/* 281 */     this.threadCache.remove();
/*     */   }
/*     */   
/*     */   final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
/* 285 */     private final AtomicInteger index = new AtomicInteger();
/*     */     
/*     */     PoolThreadLocalCache() {}
/*     */     
/* 289 */     protected PoolThreadCache initialValue() { int i = this.index.getAndIncrement();
/*     */       
/*     */       PoolArena localPoolArena1;
/*     */       
/* 293 */       if (PooledByteBufAllocator.this.heapArenas != null) {
/* 294 */         localPoolArena1 = PooledByteBufAllocator.this.heapArenas[Math.abs(i % PooledByteBufAllocator.this.heapArenas.length)];
/*     */       } else {
/* 296 */         localPoolArena1 = null;
/*     */       }
/*     */       PoolArena localPoolArena2;
/* 299 */       if (PooledByteBufAllocator.this.directArenas != null) {
/* 300 */         localPoolArena2 = PooledByteBufAllocator.this.directArenas[Math.abs(i % PooledByteBufAllocator.this.directArenas.length)];
/*     */       } else {
/* 302 */         localPoolArena2 = null;
/*     */       }
/*     */       
/* 305 */       return new PoolThreadCache(localPoolArena1, localPoolArena2, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     protected void onRemoval(PoolThreadCache paramPoolThreadCache)
/*     */     {
/* 312 */       paramPoolThreadCache.free();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PooledByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */