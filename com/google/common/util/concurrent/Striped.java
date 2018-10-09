/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.MapMaker;
/*     */ import com.google.common.math.IntMath;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public abstract class Striped<L>
/*     */ {
/*     */   private static final int LARGE_LAZY_CUTOFF = 1024;
/*     */   
/*     */   public abstract L get(Object paramObject);
/*     */   
/*     */   public abstract L getAt(int paramInt);
/*     */   
/*     */   abstract int indexFor(Object paramObject);
/*     */   
/*     */   public abstract int size();
/*     */   
/*     */   public Iterable<L> bulkGet(Iterable<?> keys)
/*     */   {
/* 147 */     Object[] array = Iterables.toArray(keys, Object.class);
/* 148 */     if (array.length == 0) {
/* 149 */       return ImmutableList.of();
/*     */     }
/* 151 */     int[] stripes = new int[array.length];
/* 152 */     for (int i = 0; i < array.length; i++) {
/* 153 */       stripes[i] = indexFor(array[i]);
/*     */     }
/* 155 */     Arrays.sort(stripes);
/*     */     
/* 157 */     int previousStripe = stripes[0];
/* 158 */     array[0] = getAt(previousStripe);
/* 159 */     for (int i = 1; i < array.length; i++) {
/* 160 */       int currentStripe = stripes[i];
/* 161 */       if (currentStripe == previousStripe) {
/* 162 */         array[i] = array[(i - 1)];
/*     */       } else {
/* 164 */         array[i] = getAt(currentStripe);
/* 165 */         previousStripe = currentStripe;
/*     */       }
/*     */     }
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
/* 186 */     List<L> asList = Arrays.asList(array);
/* 187 */     return Collections.unmodifiableList(asList);
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
/*     */   public static Striped<Lock> lock(int stripes)
/*     */   {
/* 200 */     new CompactStriped(stripes, new Supplier()
/*     */     {
/* 202 */       public Lock get() { return new Striped.PaddedLock(); } }, null);
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
/*     */   public static Striped<Lock> lazyWeakLock(int stripes)
/*     */   {
/* 215 */     lazy(stripes, new Supplier() {
/*     */       public Lock get() {
/* 217 */         return new ReentrantLock(false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private static <L> Striped<L> lazy(int stripes, Supplier<L> supplier) {
/* 223 */     return stripes < 1024 ? new SmallLazyStriped(stripes, supplier) : new LargeLazyStriped(stripes, supplier);
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
/*     */ 
/*     */   public static Striped<Semaphore> semaphore(int stripes, int permits)
/*     */   {
/* 237 */     new CompactStriped(stripes, new Supplier()
/*     */     {
/* 239 */       public Semaphore get() { return new Striped.PaddedSemaphore(this.val$permits); } }, null);
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
/*     */ 
/*     */   public static Striped<Semaphore> lazyWeakSemaphore(int stripes, int permits)
/*     */   {
/* 253 */     lazy(stripes, new Supplier() {
/*     */       public Semaphore get() {
/* 255 */         return new Semaphore(this.val$permits, false);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Striped<ReadWriteLock> readWriteLock(int stripes)
/*     */   {
/* 268 */     return new CompactStriped(stripes, READ_WRITE_LOCK_SUPPLIER, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int stripes)
/*     */   {
/* 279 */     return lazy(stripes, READ_WRITE_LOCK_SUPPLIER);
/*     */   }
/*     */   
/*     */ 
/* 283 */   private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new Supplier()
/*     */   {
/*     */ 
/* 286 */     public ReadWriteLock get() { return new ReentrantReadWriteLock(); }
/*     */   };
/*     */   private static final int ALL_SET = -1;
/*     */   
/*     */   private static abstract class PowerOfTwoStriped<L> extends Striped<L> {
/*     */     final int mask;
/*     */     
/*     */     PowerOfTwoStriped(int stripes) {
/* 294 */       super();
/* 295 */       Preconditions.checkArgument(stripes > 0, "Stripes must be positive");
/* 296 */       this.mask = (stripes > 1073741824 ? -1 : Striped.ceilToPowerOfTwo(stripes) - 1);
/*     */     }
/*     */     
/*     */     final int indexFor(Object key) {
/* 300 */       int hash = Striped.smear(key.hashCode());
/* 301 */       return hash & this.mask;
/*     */     }
/*     */     
/*     */     public final L get(Object key) {
/* 305 */       return (L)getAt(indexFor(key));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class CompactStriped<L>
/*     */     extends Striped.PowerOfTwoStriped<L>
/*     */   {
/*     */     private final Object[] array;
/*     */     
/*     */ 
/*     */     private CompactStriped(int stripes, Supplier<L> supplier)
/*     */     {
/* 318 */       super();
/* 319 */       Preconditions.checkArgument(stripes <= 1073741824, "Stripes must be <= 2^30)");
/*     */       
/* 321 */       this.array = new Object[this.mask + 1];
/* 322 */       for (int i = 0; i < this.array.length; i++) {
/* 323 */         this.array[i] = supplier.get();
/*     */       }
/*     */     }
/*     */     
/*     */     public L getAt(int index)
/*     */     {
/* 329 */       return (L)this.array[index];
/*     */     }
/*     */     
/*     */     public int size() {
/* 333 */       return this.array.length;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @VisibleForTesting
/*     */   static class SmallLazyStriped<L>
/*     */     extends Striped.PowerOfTwoStriped<L>
/*     */   {
/*     */     final AtomicReferenceArray<ArrayReference<? extends L>> locks;
/*     */     
/*     */     final Supplier<L> supplier;
/*     */     final int size;
/* 346 */     final ReferenceQueue<L> queue = new ReferenceQueue();
/*     */     
/*     */     SmallLazyStriped(int stripes, Supplier<L> supplier) {
/* 349 */       super();
/* 350 */       this.size = (this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1);
/* 351 */       this.locks = new AtomicReferenceArray(this.size);
/* 352 */       this.supplier = supplier;
/*     */     }
/*     */     
/*     */     public L getAt(int index) {
/* 356 */       if (this.size != Integer.MAX_VALUE) {
/* 357 */         Preconditions.checkElementIndex(index, size());
/*     */       }
/* 359 */       ArrayReference<? extends L> existingRef = (ArrayReference)this.locks.get(index);
/* 360 */       L existing = existingRef == null ? null : existingRef.get();
/* 361 */       if (existing != null) {
/* 362 */         return existing;
/*     */       }
/* 364 */       L created = this.supplier.get();
/* 365 */       ArrayReference<L> newRef = new ArrayReference(created, index, this.queue);
/* 366 */       while (!this.locks.compareAndSet(index, existingRef, newRef))
/*     */       {
/* 368 */         existingRef = (ArrayReference)this.locks.get(index);
/* 369 */         existing = existingRef == null ? null : existingRef.get();
/* 370 */         if (existing != null) {
/* 371 */           return existing;
/*     */         }
/*     */       }
/* 374 */       drainQueue();
/* 375 */       return created;
/*     */     }
/*     */     
/*     */ 
/*     */     private void drainQueue()
/*     */     {
/*     */       Reference<? extends L> ref;
/*     */       
/* 383 */       while ((ref = this.queue.poll()) != null)
/*     */       {
/* 385 */         ArrayReference<? extends L> arrayRef = (ArrayReference)ref;
/*     */         
/*     */ 
/* 388 */         this.locks.compareAndSet(arrayRef.index, arrayRef, null);
/*     */       }
/*     */     }
/*     */     
/*     */     public int size() {
/* 393 */       return this.size;
/*     */     }
/*     */     
/*     */     private static final class ArrayReference<L> extends WeakReference<L> {
/*     */       final int index;
/*     */       
/*     */       ArrayReference(L referent, int index, ReferenceQueue<L> queue) {
/* 400 */         super(queue);
/* 401 */         this.index = index;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @VisibleForTesting
/*     */   static class LargeLazyStriped<L>
/*     */     extends Striped.PowerOfTwoStriped<L>
/*     */   {
/*     */     final ConcurrentMap<Integer, L> locks;
/*     */     final Supplier<L> supplier;
/*     */     final int size;
/*     */     
/*     */     LargeLazyStriped(int stripes, Supplier<L> supplier)
/*     */     {
/* 417 */       super();
/* 418 */       this.size = (this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1);
/* 419 */       this.supplier = supplier;
/* 420 */       this.locks = new MapMaker().weakValues().makeMap();
/*     */     }
/*     */     
/*     */     public L getAt(int index) {
/* 424 */       if (this.size != Integer.MAX_VALUE) {
/* 425 */         Preconditions.checkElementIndex(index, size());
/*     */       }
/* 427 */       L existing = this.locks.get(Integer.valueOf(index));
/* 428 */       if (existing != null) {
/* 429 */         return existing;
/*     */       }
/* 431 */       L created = this.supplier.get();
/* 432 */       existing = this.locks.putIfAbsent(Integer.valueOf(index), created);
/* 433 */       return (L)Objects.firstNonNull(existing, created);
/*     */     }
/*     */     
/*     */     public int size() {
/* 437 */       return this.size;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int ceilToPowerOfTwo(int x)
/*     */   {
/* 447 */     return 1 << IntMath.log2(x, RoundingMode.CEILING);
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
/*     */   private static int smear(int hashCode)
/*     */   {
/* 460 */     hashCode ^= hashCode >>> 20 ^ hashCode >>> 12;
/* 461 */     return hashCode ^ hashCode >>> 7 ^ hashCode >>> 4;
/*     */   }
/*     */   
/*     */ 
/*     */   private static class PaddedLock
/*     */     extends ReentrantLock
/*     */   {
/*     */     long q1;
/*     */     long q2;
/*     */     long q3;
/*     */     
/*     */     PaddedLock()
/*     */     {
/* 474 */       super();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class PaddedSemaphore extends Semaphore {
/*     */     long q1;
/*     */     long q2;
/*     */     long q3;
/*     */     
/*     */     PaddedSemaphore(int permits) {
/* 484 */       super(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\util\concurrent\Striped.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */