/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
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
/*     */ public final class ChannelOutboundBuffer
/*     */ {
/*     */   private static final InternalLogger logger;
/*     */   private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS;
/*     */   private final Channel channel;
/*     */   private Entry flushedEntry;
/*     */   private Entry unflushedEntry;
/*     */   private Entry tailEntry;
/*     */   private int flushed;
/*     */   private int nioBufferCount;
/*     */   private long nioBufferSize;
/*     */   private boolean inFail;
/*     */   private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
/*     */   private volatile long totalPendingSize;
/*     */   private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> WRITABLE_UPDATER;
/*  78 */   private volatile int writable = 1;
/*     */   
/*     */   static
/*     */   {
/*  44 */     logger = InternalLoggerFactory.getInstance(ChannelOutboundBuffer.class);
/*     */     
/*  46 */     NIO_BUFFERS = new FastThreadLocal()
/*     */     {
/*     */       protected ByteBuffer[] initialValue() throws Exception {
/*  49 */         return new ByteBuffer['Ѐ'];
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
/*     */       }
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
/*  81 */     };
/*  82 */     AtomicIntegerFieldUpdater localAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(ChannelOutboundBuffer.class, "writable");
/*     */     
/*  84 */     if (localAtomicIntegerFieldUpdater == null) {
/*  85 */       localAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "writable");
/*     */     }
/*  87 */     WRITABLE_UPDATER = localAtomicIntegerFieldUpdater;
/*     */     
/*  89 */     AtomicLongFieldUpdater localAtomicLongFieldUpdater = PlatformDependent.newAtomicLongFieldUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */     
/*  91 */     if (localAtomicLongFieldUpdater == null) {
/*  92 */       localAtomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(ChannelOutboundBuffer.class, "totalPendingSize");
/*     */     }
/*  94 */     TOTAL_PENDING_SIZE_UPDATER = localAtomicLongFieldUpdater;
/*     */   }
/*     */   
/*     */   ChannelOutboundBuffer(AbstractChannel paramAbstractChannel) {
/*  98 */     this.channel = paramAbstractChannel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addMessage(Object paramObject, int paramInt, ChannelPromise paramChannelPromise)
/*     */   {
/* 106 */     Entry localEntry1 = Entry.newInstance(paramObject, paramInt, total(paramObject), paramChannelPromise);
/* 107 */     if (this.tailEntry == null) {
/* 108 */       this.flushedEntry = null;
/* 109 */       this.tailEntry = localEntry1;
/*     */     } else {
/* 111 */       Entry localEntry2 = this.tailEntry;
/* 112 */       localEntry2.next = localEntry1;
/* 113 */       this.tailEntry = localEntry1;
/*     */     }
/* 115 */     if (this.unflushedEntry == null) {
/* 116 */       this.unflushedEntry = localEntry1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 121 */     incrementPendingOutboundBytes(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFlush()
/*     */   {
/* 133 */     Entry localEntry = this.unflushedEntry;
/* 134 */     if (localEntry != null) {
/* 135 */       if (this.flushedEntry == null)
/*     */       {
/* 137 */         this.flushedEntry = localEntry;
/*     */       }
/*     */       do {
/* 140 */         this.flushed += 1;
/* 141 */         if (!localEntry.promise.setUncancellable())
/*     */         {
/* 143 */           int i = localEntry.cancel();
/* 144 */           decrementPendingOutboundBytes(i);
/*     */         }
/* 146 */         localEntry = localEntry.next;
/* 147 */       } while (localEntry != null);
/*     */       
/*     */ 
/* 150 */       this.unflushedEntry = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void incrementPendingOutboundBytes(long paramLong)
/*     */   {
/* 159 */     if (paramLong == 0L) {
/* 160 */       return;
/*     */     }
/*     */     
/* 163 */     long l = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, paramLong);
/* 164 */     if ((l > this.channel.config().getWriteBufferHighWaterMark()) && 
/* 165 */       (WRITABLE_UPDATER.compareAndSet(this, 1, 0))) {
/* 166 */       this.channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void decrementPendingOutboundBytes(long paramLong)
/*     */   {
/* 176 */     if (paramLong == 0L) {
/* 177 */       return;
/*     */     }
/*     */     
/* 180 */     long l = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -paramLong);
/* 181 */     if (((l == 0L) || (l < this.channel.config().getWriteBufferLowWaterMark())) && 
/* 182 */       (WRITABLE_UPDATER.compareAndSet(this, 0, 1))) {
/* 183 */       this.channel.pipeline().fireChannelWritabilityChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   private static long total(Object paramObject)
/*     */   {
/* 189 */     if ((paramObject instanceof ByteBuf)) {
/* 190 */       return ((ByteBuf)paramObject).readableBytes();
/*     */     }
/* 192 */     if ((paramObject instanceof FileRegion)) {
/* 193 */       return ((FileRegion)paramObject).count();
/*     */     }
/* 195 */     if ((paramObject instanceof ByteBufHolder)) {
/* 196 */       return ((ByteBufHolder)paramObject).content().readableBytes();
/*     */     }
/* 198 */     return -1L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object current()
/*     */   {
/* 205 */     Entry localEntry = this.flushedEntry;
/* 206 */     if (localEntry == null) {
/* 207 */       return null;
/*     */     }
/*     */     
/* 210 */     return localEntry.msg;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void progress(long paramLong)
/*     */   {
/* 217 */     Entry localEntry = this.flushedEntry;
/* 218 */     assert (localEntry != null);
/* 219 */     ChannelPromise localChannelPromise = localEntry.promise;
/* 220 */     if ((localChannelPromise instanceof ChannelProgressivePromise)) {
/* 221 */       long l = localEntry.progress + paramLong;
/* 222 */       localEntry.progress = l;
/* 223 */       ((ChannelProgressivePromise)localChannelPromise).tryProgress(l, localEntry.total);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove()
/*     */   {
/* 233 */     Entry localEntry = this.flushedEntry;
/* 234 */     if (localEntry == null) {
/* 235 */       return false;
/*     */     }
/* 237 */     Object localObject = localEntry.msg;
/*     */     
/* 239 */     ChannelPromise localChannelPromise = localEntry.promise;
/* 240 */     int i = localEntry.pendingSize;
/*     */     
/* 242 */     removeEntry(localEntry);
/*     */     
/* 244 */     if (!localEntry.cancelled)
/*     */     {
/* 246 */       ReferenceCountUtil.safeRelease(localObject);
/* 247 */       safeSuccess(localChannelPromise);
/* 248 */       decrementPendingOutboundBytes(i);
/*     */     }
/*     */     
/*     */ 
/* 252 */     localEntry.recycle();
/*     */     
/* 254 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(Throwable paramThrowable)
/*     */   {
/* 263 */     Entry localEntry = this.flushedEntry;
/* 264 */     if (localEntry == null) {
/* 265 */       return false;
/*     */     }
/* 267 */     Object localObject = localEntry.msg;
/*     */     
/* 269 */     ChannelPromise localChannelPromise = localEntry.promise;
/* 270 */     int i = localEntry.pendingSize;
/*     */     
/* 272 */     removeEntry(localEntry);
/*     */     
/* 274 */     if (!localEntry.cancelled)
/*     */     {
/* 276 */       ReferenceCountUtil.safeRelease(localObject);
/*     */       
/* 278 */       safeFail(localChannelPromise, paramThrowable);
/* 279 */       decrementPendingOutboundBytes(i);
/*     */     }
/*     */     
/*     */ 
/* 283 */     localEntry.recycle();
/*     */     
/* 285 */     return true;
/*     */   }
/*     */   
/*     */   private void removeEntry(Entry paramEntry) {
/* 289 */     if (--this.flushed == 0)
/*     */     {
/* 291 */       this.flushedEntry = null;
/* 292 */       if (paramEntry == this.tailEntry) {
/* 293 */         this.tailEntry = null;
/* 294 */         this.unflushedEntry = null;
/*     */       }
/*     */     } else {
/* 297 */       this.flushedEntry = paramEntry.next;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeBytes(long paramLong)
/*     */   {
/*     */     for (;;)
/*     */     {
/* 307 */       Object localObject = current();
/* 308 */       if (!(localObject instanceof ByteBuf)) {
/* 309 */         if (($assertionsDisabled) || (paramLong == 0L)) break; throw new AssertionError();
/*     */       }
/*     */       
/*     */ 
/* 313 */       ByteBuf localByteBuf = (ByteBuf)localObject;
/* 314 */       int i = localByteBuf.readerIndex();
/* 315 */       int j = localByteBuf.writerIndex() - i;
/*     */       
/* 317 */       if (j <= paramLong) {
/* 318 */         if (paramLong != 0L) {
/* 319 */           progress(j);
/* 320 */           paramLong -= j;
/*     */         }
/* 322 */         remove();
/*     */       } else {
/* 324 */         if (paramLong == 0L) break;
/* 325 */         localByteBuf.readerIndex(i + (int)paramLong);
/* 326 */         progress(paramLong); break;
/*     */       }
/*     */     }
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
/*     */ 
/*     */ 
/*     */   public ByteBuffer[] nioBuffers()
/*     */   {
/* 344 */     long l = 0L;
/* 345 */     int i = 0;
/* 346 */     InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.get();
/* 347 */     ByteBuffer[] arrayOfByteBuffer = (ByteBuffer[])NIO_BUFFERS.get(localInternalThreadLocalMap);
/* 348 */     Entry localEntry = this.flushedEntry;
/* 349 */     while ((isFlushedEntry(localEntry)) && ((localEntry.msg instanceof ByteBuf))) {
/* 350 */       if (!localEntry.cancelled) {
/* 351 */         ByteBuf localByteBuf = (ByteBuf)localEntry.msg;
/* 352 */         int j = localByteBuf.readerIndex();
/* 353 */         int k = localByteBuf.writerIndex() - j;
/*     */         
/* 355 */         if (k > 0) {
/* 356 */           l += k;
/* 357 */           int m = localEntry.count;
/* 358 */           if (m == -1)
/*     */           {
/* 360 */             localEntry.count = (m = localByteBuf.nioBufferCount());
/*     */           }
/* 362 */           int n = i + m;
/* 363 */           if (n > arrayOfByteBuffer.length) {
/* 364 */             arrayOfByteBuffer = expandNioBufferArray(arrayOfByteBuffer, n, i);
/* 365 */             NIO_BUFFERS.set(localInternalThreadLocalMap, arrayOfByteBuffer); }
/*     */           Object localObject;
/* 367 */           if (m == 1) {
/* 368 */             localObject = localEntry.buf;
/* 369 */             if (localObject == null)
/*     */             {
/*     */ 
/* 372 */               localEntry.buf = (localObject = localByteBuf.internalNioBuffer(j, k));
/*     */             }
/* 374 */             arrayOfByteBuffer[(i++)] = localObject;
/*     */           } else {
/* 376 */             localObject = localEntry.bufs;
/* 377 */             if (localObject == null)
/*     */             {
/*     */ 
/* 380 */               localEntry.bufs = (localObject = localByteBuf.nioBuffers());
/*     */             }
/* 382 */             i = fillBufferArray((ByteBuffer[])localObject, arrayOfByteBuffer, i);
/*     */           }
/*     */         }
/*     */       }
/* 386 */       localEntry = localEntry.next;
/*     */     }
/* 388 */     this.nioBufferCount = i;
/* 389 */     this.nioBufferSize = l;
/*     */     
/* 391 */     return arrayOfByteBuffer;
/*     */   }
/*     */   
/*     */   private static int fillBufferArray(ByteBuffer[] paramArrayOfByteBuffer1, ByteBuffer[] paramArrayOfByteBuffer2, int paramInt) {
/* 395 */     for (ByteBuffer localByteBuffer : paramArrayOfByteBuffer1) {
/* 396 */       if (localByteBuffer == null) {
/*     */         break;
/*     */       }
/* 399 */       paramArrayOfByteBuffer2[(paramInt++)] = localByteBuffer;
/*     */     }
/* 401 */     return paramInt;
/*     */   }
/*     */   
/*     */   private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2) {
/* 405 */     int i = paramArrayOfByteBuffer.length;
/*     */     
/*     */     do
/*     */     {
/* 409 */       i <<= 1;
/*     */       
/* 411 */       if (i < 0) {
/* 412 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 415 */     } while (paramInt1 > i);
/*     */     
/* 417 */     ByteBuffer[] arrayOfByteBuffer = new ByteBuffer[i];
/* 418 */     System.arraycopy(paramArrayOfByteBuffer, 0, arrayOfByteBuffer, 0, paramInt2);
/*     */     
/* 420 */     return arrayOfByteBuffer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int nioBufferCount()
/*     */   {
/* 429 */     return this.nioBufferCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long nioBufferSize()
/*     */   {
/* 438 */     return this.nioBufferSize;
/*     */   }
/*     */   
/*     */   boolean isWritable() {
/* 442 */     return this.writable != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int size()
/*     */   {
/* 449 */     return this.flushed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 457 */     return this.flushed == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void failFlushed(Throwable paramThrowable)
/*     */   {
/* 466 */     if (this.inFail) {
/* 467 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 471 */       this.inFail = true;
/*     */       for (;;) {
/* 473 */         if (!remove(paramThrowable)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     } finally {
/* 478 */       this.inFail = false;
/*     */     }
/*     */   }
/*     */   
/*     */   void close(final ClosedChannelException paramClosedChannelException) {
/* 483 */     if (this.inFail) {
/* 484 */       this.channel.eventLoop().execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 487 */           ChannelOutboundBuffer.this.close(paramClosedChannelException);
/*     */         }
/* 489 */       });
/* 490 */       return;
/*     */     }
/*     */     
/* 493 */     this.inFail = true;
/*     */     
/* 495 */     if (this.channel.isOpen()) {
/* 496 */       throw new IllegalStateException("close() must be invoked after the channel is closed.");
/*     */     }
/*     */     
/* 499 */     if (!isEmpty()) {
/* 500 */       throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 505 */       Entry localEntry = this.unflushedEntry;
/* 506 */       while (localEntry != null)
/*     */       {
/* 508 */         int i = localEntry.pendingSize;
/* 509 */         TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -i);
/*     */         
/* 511 */         if (!localEntry.cancelled) {
/* 512 */           ReferenceCountUtil.safeRelease(localEntry.msg);
/* 513 */           safeFail(localEntry.promise, paramClosedChannelException);
/*     */         }
/* 515 */         localEntry = localEntry.recycleAndGetNext();
/*     */       }
/*     */     } finally {
/* 518 */       this.inFail = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeSuccess(ChannelPromise paramChannelPromise) {
/* 523 */     if ((!(paramChannelPromise instanceof VoidChannelPromise)) && (!paramChannelPromise.trySuccess())) {
/* 524 */       logger.warn("Failed to mark a promise as success because it is done already: {}", paramChannelPromise);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeFail(ChannelPromise paramChannelPromise, Throwable paramThrowable) {
/* 529 */     if ((!(paramChannelPromise instanceof VoidChannelPromise)) && (!paramChannelPromise.tryFailure(paramThrowable))) {
/* 530 */       logger.warn("Failed to mark a promise as failure because it's done already: {}", paramChannelPromise, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long totalPendingWriteBytes()
/*     */   {
/* 540 */     return this.totalPendingSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void forEachFlushedMessage(MessageProcessor paramMessageProcessor)
/*     */     throws Exception
/*     */   {
/* 549 */     if (paramMessageProcessor == null) {
/* 550 */       throw new NullPointerException("processor");
/*     */     }
/*     */     
/* 553 */     Entry localEntry = this.flushedEntry;
/* 554 */     if (localEntry == null) {
/*     */       return;
/*     */     }
/*     */     do
/*     */     {
/* 559 */       if ((!localEntry.cancelled) && 
/* 560 */         (!paramMessageProcessor.processMessage(localEntry.msg))) {
/* 561 */         return;
/*     */       }
/*     */       
/* 564 */       localEntry = localEntry.next;
/* 565 */     } while (isFlushedEntry(localEntry));
/*     */   }
/*     */   
/*     */   private boolean isFlushedEntry(Entry paramEntry) {
/* 569 */     return (paramEntry != null) && (paramEntry != this.unflushedEntry);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void recycle() {}
/*     */   
/*     */ 
/*     */ 
/*     */   static final class Entry
/*     */   {
/* 581 */     private static final Recycler<Entry> RECYCLER = new Recycler()
/*     */     {
/*     */       protected ChannelOutboundBuffer.Entry newObject(Recycler.Handle paramAnonymousHandle) {
/* 584 */         return new ChannelOutboundBuffer.Entry(paramAnonymousHandle, null);
/*     */       }
/*     */     };
/*     */     
/*     */     private final Recycler.Handle handle;
/*     */     Entry next;
/*     */     Object msg;
/*     */     ByteBuffer[] bufs;
/*     */     ByteBuffer buf;
/*     */     ChannelPromise promise;
/*     */     long progress;
/*     */     long total;
/*     */     int pendingSize;
/* 597 */     int count = -1;
/*     */     boolean cancelled;
/*     */     
/*     */     private Entry(Recycler.Handle paramHandle) {
/* 601 */       this.handle = paramHandle;
/*     */     }
/*     */     
/*     */     static Entry newInstance(Object paramObject, int paramInt, long paramLong, ChannelPromise paramChannelPromise) {
/* 605 */       Entry localEntry = (Entry)RECYCLER.get();
/* 606 */       localEntry.msg = paramObject;
/* 607 */       localEntry.pendingSize = paramInt;
/* 608 */       localEntry.total = paramLong;
/* 609 */       localEntry.promise = paramChannelPromise;
/* 610 */       return localEntry;
/*     */     }
/*     */     
/*     */     int cancel() {
/* 614 */       if (!this.cancelled) {
/* 615 */         this.cancelled = true;
/* 616 */         int i = this.pendingSize;
/*     */         
/*     */ 
/* 619 */         ReferenceCountUtil.safeRelease(this.msg);
/* 620 */         this.msg = Unpooled.EMPTY_BUFFER;
/*     */         
/* 622 */         this.pendingSize = 0;
/* 623 */         this.total = 0L;
/* 624 */         this.progress = 0L;
/* 625 */         this.bufs = null;
/* 626 */         this.buf = null;
/* 627 */         return i;
/*     */       }
/* 629 */       return 0;
/*     */     }
/*     */     
/*     */     void recycle() {
/* 633 */       this.next = null;
/* 634 */       this.bufs = null;
/* 635 */       this.buf = null;
/* 636 */       this.msg = null;
/* 637 */       this.promise = null;
/* 638 */       this.progress = 0L;
/* 639 */       this.total = 0L;
/* 640 */       this.pendingSize = 0;
/* 641 */       this.count = -1;
/* 642 */       this.cancelled = false;
/* 643 */       RECYCLER.recycle(this, this.handle);
/*     */     }
/*     */     
/*     */     Entry recycleAndGetNext() {
/* 647 */       Entry localEntry = this.next;
/* 648 */       recycle();
/* 649 */       return localEntry;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract interface MessageProcessor
/*     */   {
/*     */     public abstract boolean processMessage(Object paramObject)
/*     */       throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelOutboundBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */