/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.EventLoopException;
/*     */ import io.netty.channel.SingleThreadEventLoop;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.AbstractSelector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public final class NioEventLoop
/*     */   extends SingleThreadEventLoop
/*     */ {
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
/*     */   
/*     */   private static final int CLEANUP_INTERVAL = 256;
/*     */   
/*  57 */   private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
/*     */   
/*     */   private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
/*     */   
/*     */   private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
/*     */   
/*     */   Selector selector;
/*     */   private SelectedSelectionKeySet selectedKeys;
/*     */   private final SelectorProvider provider;
/*     */   
/*     */   static
/*     */   {
/*  69 */     String str1 = "sun.nio.ch.bugLevel";
/*     */     try {
/*  71 */       String str2 = SystemPropertyUtil.get(str1);
/*  72 */       if (str2 == null) {
/*  73 */         System.setProperty(str1, "");
/*     */       }
/*     */     } catch (SecurityException localSecurityException) {
/*  76 */       if (logger.isDebugEnabled()) {
/*  77 */         logger.debug("Unable to get/set System Property: {}", str1, localSecurityException);
/*     */       }
/*     */     }
/*     */     
/*  81 */     int i = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
/*  82 */     if (i < 3) {
/*  83 */       i = 0;
/*     */     }
/*     */     
/*  86 */     SELECTOR_AUTO_REBUILD_THRESHOLD = i;
/*     */     
/*  88 */     if (logger.isDebugEnabled()) {
/*  89 */       logger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
/*  90 */       logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
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
/*     */ 
/*     */ 
/*     */ 
/* 108 */   private final AtomicBoolean wakenUp = new AtomicBoolean();
/*     */   
/* 110 */   private volatile int ioRatio = 50;
/*     */   private int cancelledKeys;
/*     */   private boolean needsToSelectAgain;
/*     */   
/*     */   NioEventLoop(NioEventLoopGroup paramNioEventLoopGroup, ThreadFactory paramThreadFactory, SelectorProvider paramSelectorProvider) {
/* 115 */     super(paramNioEventLoopGroup, paramThreadFactory, false);
/* 116 */     if (paramSelectorProvider == null) {
/* 117 */       throw new NullPointerException("selectorProvider");
/*     */     }
/* 119 */     this.provider = paramSelectorProvider;
/* 120 */     this.selector = openSelector();
/*     */   }
/*     */   
/*     */   private Selector openSelector() {
/*     */     AbstractSelector localAbstractSelector;
/*     */     try {
/* 126 */       localAbstractSelector = this.provider.openSelector();
/*     */     } catch (IOException localIOException) {
/* 128 */       throw new ChannelException("failed to open a new selector", localIOException);
/*     */     }
/*     */     
/* 131 */     if (DISABLE_KEYSET_OPTIMIZATION) {
/* 132 */       return localAbstractSelector;
/*     */     }
/*     */     try
/*     */     {
/* 136 */       SelectedSelectionKeySet localSelectedSelectionKeySet = new SelectedSelectionKeySet();
/*     */       
/* 138 */       Class localClass = Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
/*     */       
/*     */ 
/*     */ 
/* 142 */       if (!localClass.isAssignableFrom(localAbstractSelector.getClass())) {
/* 143 */         return localAbstractSelector;
/*     */       }
/*     */       
/* 146 */       Field localField1 = localClass.getDeclaredField("selectedKeys");
/* 147 */       Field localField2 = localClass.getDeclaredField("publicSelectedKeys");
/*     */       
/* 149 */       localField1.setAccessible(true);
/* 150 */       localField2.setAccessible(true);
/*     */       
/* 152 */       localField1.set(localAbstractSelector, localSelectedSelectionKeySet);
/* 153 */       localField2.set(localAbstractSelector, localSelectedSelectionKeySet);
/*     */       
/* 155 */       this.selectedKeys = localSelectedSelectionKeySet;
/* 156 */       logger.trace("Instrumented an optimized java.util.Set into: {}", localAbstractSelector);
/*     */     } catch (Throwable localThrowable) {
/* 158 */       this.selectedKeys = null;
/* 159 */       logger.trace("Failed to instrument an optimized java.util.Set into: {}", localAbstractSelector, localThrowable);
/*     */     }
/*     */     
/* 162 */     return localAbstractSelector;
/*     */   }
/*     */   
/*     */ 
/*     */   protected Queue<Runnable> newTaskQueue()
/*     */   {
/* 168 */     return PlatformDependent.newMpscQueue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void register(SelectableChannel paramSelectableChannel, int paramInt, NioTask<?> paramNioTask)
/*     */   {
/* 177 */     if (paramSelectableChannel == null) {
/* 178 */       throw new NullPointerException("ch");
/*     */     }
/* 180 */     if (paramInt == 0) {
/* 181 */       throw new IllegalArgumentException("interestOps must be non-zero.");
/*     */     }
/* 183 */     if ((paramInt & (paramSelectableChannel.validOps() ^ 0xFFFFFFFF)) != 0) {
/* 184 */       throw new IllegalArgumentException("invalid interestOps: " + paramInt + "(validOps: " + paramSelectableChannel.validOps() + ')');
/*     */     }
/*     */     
/* 187 */     if (paramNioTask == null) {
/* 188 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 191 */     if (isShutdown()) {
/* 192 */       throw new IllegalStateException("event loop shut down");
/*     */     }
/*     */     try
/*     */     {
/* 196 */       paramSelectableChannel.register(this.selector, paramInt, paramNioTask);
/*     */     } catch (Exception localException) {
/* 198 */       throw new EventLoopException("failed to register a channel", localException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getIoRatio()
/*     */   {
/* 206 */     return this.ioRatio;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIoRatio(int paramInt)
/*     */   {
/* 214 */     if ((paramInt <= 0) || (paramInt > 100)) {
/* 215 */       throw new IllegalArgumentException("ioRatio: " + paramInt + " (expected: 0 < ioRatio <= 100)");
/*     */     }
/* 217 */     this.ioRatio = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void rebuildSelector()
/*     */   {
/* 225 */     if (!inEventLoop()) {
/* 226 */       execute(new Runnable()
/*     */       {
/*     */         public void run() {
/* 229 */           NioEventLoop.this.rebuildSelector();
/*     */         }
/* 231 */       });
/* 232 */       return;
/*     */     }
/*     */     
/* 235 */     Selector localSelector1 = this.selector;
/*     */     
/*     */ 
/* 238 */     if (localSelector1 == null) {
/*     */       return;
/*     */     }
/*     */     Selector localSelector2;
/*     */     try {
/* 243 */       localSelector2 = openSelector();
/*     */     } catch (Exception localException1) {
/* 245 */       logger.warn("Failed to create a new Selector.", localException1);
/* 246 */       return;
/*     */     }
/*     */     
/*     */ 
/* 250 */     int i = 0;
/*     */     for (;;) {
/*     */       try {
/* 253 */         Iterator localIterator = localSelector1.keys().iterator(); if (localIterator.hasNext()) { SelectionKey localSelectionKey = (SelectionKey)localIterator.next();
/* 254 */           Object localObject1 = localSelectionKey.attachment();
/*     */           try {
/* 256 */             if ((localSelectionKey.isValid()) && (localSelectionKey.channel().keyFor(localSelector2) != null)) {
/*     */               continue;
/*     */             }
/*     */             
/* 260 */             int j = localSelectionKey.interestOps();
/* 261 */             localSelectionKey.cancel();
/* 262 */             localObject2 = localSelectionKey.channel().register(localSelector2, j, localObject1);
/* 263 */             if ((localObject1 instanceof AbstractNioChannel))
/*     */             {
/* 265 */               ((AbstractNioChannel)localObject1).selectionKey = ((SelectionKey)localObject2);
/*     */             }
/* 267 */             i++;
/*     */           } catch (Exception localException2) { Object localObject2;
/* 269 */             logger.warn("Failed to re-register a Channel to the new Selector.", localException2);
/* 270 */             if ((localObject1 instanceof AbstractNioChannel)) {
/* 271 */               localObject2 = (AbstractNioChannel)localObject1;
/* 272 */               ((AbstractNioChannel)localObject2).unsafe().close(((AbstractNioChannel)localObject2).unsafe().voidPromise());
/*     */             }
/*     */             else {
/* 275 */               localObject2 = (NioTask)localObject1;
/* 276 */               invokeChannelUnregistered((NioTask)localObject2, localSelectionKey, localException2);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (ConcurrentModificationException localConcurrentModificationException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 288 */     this.selector = localSelector2;
/*     */     
/*     */     try
/*     */     {
/* 292 */       localSelector1.close();
/*     */     } catch (Throwable localThrowable) {
/* 294 */       if (logger.isWarnEnabled()) {
/* 295 */         logger.warn("Failed to close the old Selector.", localThrowable);
/*     */       }
/*     */     }
/*     */     
/* 299 */     logger.info("Migrated " + i + " channel(s) to the new Selector.");
/*     */   }
/*     */   
/*     */   protected void run()
/*     */   {
/*     */     for (;;) {
/* 305 */       boolean bool = this.wakenUp.getAndSet(false);
/*     */       try {
/* 307 */         if (hasTasks()) {
/* 308 */           selectNow();
/*     */         } else {
/* 310 */           select(bool);
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
/* 340 */           if (this.wakenUp.get()) {
/* 341 */             this.selector.wakeup();
/*     */           }
/*     */         }
/*     */         
/* 345 */         this.cancelledKeys = 0;
/* 346 */         this.needsToSelectAgain = false;
/* 347 */         int i = this.ioRatio;
/* 348 */         if (i == 100) {
/* 349 */           processSelectedKeys();
/* 350 */           runAllTasks();
/*     */         } else {
/* 352 */           long l1 = System.nanoTime();
/*     */           
/* 354 */           processSelectedKeys();
/*     */           
/* 356 */           long l2 = System.nanoTime() - l1;
/* 357 */           runAllTasks(l2 * (100 - i) / i);
/*     */         }
/*     */         
/* 360 */         if (isShuttingDown()) {
/* 361 */           closeAll();
/* 362 */           if (confirmShutdown()) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 367 */         logger.warn("Unexpected exception in the selector loop.", localThrowable);
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 372 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void processSelectedKeys()
/*     */   {
/* 381 */     if (this.selectedKeys != null) {
/* 382 */       processSelectedKeysOptimized(this.selectedKeys.flip());
/*     */     } else {
/* 384 */       processSelectedKeysPlain(this.selector.selectedKeys());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void cleanup()
/*     */   {
/*     */     try {
/* 391 */       this.selector.close();
/*     */     } catch (IOException localIOException) {
/* 393 */       logger.warn("Failed to close a selector.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   void cancel(SelectionKey paramSelectionKey) {
/* 398 */     paramSelectionKey.cancel();
/* 399 */     this.cancelledKeys += 1;
/* 400 */     if (this.cancelledKeys >= 256) {
/* 401 */       this.cancelledKeys = 0;
/* 402 */       this.needsToSelectAgain = true;
/*     */     }
/*     */   }
/*     */   
/*     */   protected Runnable pollTask()
/*     */   {
/* 408 */     Runnable localRunnable = super.pollTask();
/* 409 */     if (this.needsToSelectAgain) {
/* 410 */       selectAgain();
/*     */     }
/* 412 */     return localRunnable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void processSelectedKeysPlain(Set<SelectionKey> paramSet)
/*     */   {
/* 419 */     if (paramSet.isEmpty()) {
/* 420 */       return;
/*     */     }
/*     */     
/* 423 */     Iterator localIterator = paramSet.iterator();
/*     */     for (;;) {
/* 425 */       SelectionKey localSelectionKey = (SelectionKey)localIterator.next();
/* 426 */       Object localObject = localSelectionKey.attachment();
/* 427 */       localIterator.remove();
/*     */       
/* 429 */       if ((localObject instanceof AbstractNioChannel)) {
/* 430 */         processSelectedKey(localSelectionKey, (AbstractNioChannel)localObject);
/*     */       }
/*     */       else {
/* 433 */         NioTask localNioTask = (NioTask)localObject;
/* 434 */         processSelectedKey(localSelectionKey, localNioTask);
/*     */       }
/*     */       
/* 437 */       if (!localIterator.hasNext()) {
/*     */         break;
/*     */       }
/*     */       
/* 441 */       if (this.needsToSelectAgain) {
/* 442 */         selectAgain();
/* 443 */         paramSet = this.selector.selectedKeys();
/*     */         
/*     */ 
/* 446 */         if (paramSet.isEmpty()) {
/*     */           break;
/*     */         }
/* 449 */         localIterator = paramSet.iterator();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void processSelectedKeysOptimized(SelectionKey[] paramArrayOfSelectionKey)
/*     */   {
/* 456 */     for (int i = 0;; i++) {
/* 457 */       SelectionKey localSelectionKey = paramArrayOfSelectionKey[i];
/* 458 */       if (localSelectionKey == null) {
/*     */         break;
/*     */       }
/*     */       
/*     */ 
/* 463 */       paramArrayOfSelectionKey[i] = null;
/*     */       
/* 465 */       Object localObject = localSelectionKey.attachment();
/*     */       
/* 467 */       if ((localObject instanceof AbstractNioChannel)) {
/* 468 */         processSelectedKey(localSelectionKey, (AbstractNioChannel)localObject);
/*     */       }
/*     */       else {
/* 471 */         NioTask localNioTask = (NioTask)localObject;
/* 472 */         processSelectedKey(localSelectionKey, localNioTask);
/*     */       }
/*     */       
/* 475 */       if (this.needsToSelectAgain)
/*     */       {
/*     */ 
/*     */ 
/* 479 */         while (paramArrayOfSelectionKey[i] != null)
/*     */         {
/*     */ 
/* 482 */           paramArrayOfSelectionKey[i] = null;
/* 483 */           i++;
/*     */         }
/*     */         
/* 486 */         selectAgain();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 492 */         paramArrayOfSelectionKey = this.selectedKeys.flip();
/* 493 */         i = -1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey paramSelectionKey, AbstractNioChannel paramAbstractNioChannel) {
/* 499 */     AbstractNioChannel.NioUnsafe localNioUnsafe = paramAbstractNioChannel.unsafe();
/* 500 */     if (!paramSelectionKey.isValid())
/*     */     {
/* 502 */       localNioUnsafe.close(localNioUnsafe.voidPromise());
/* 503 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 507 */       int i = paramSelectionKey.readyOps();
/*     */       
/*     */ 
/* 510 */       if (((i & 0x11) != 0) || (i == 0)) {
/* 511 */         localNioUnsafe.read();
/* 512 */         if (!paramAbstractNioChannel.isOpen())
/*     */         {
/* 514 */           return;
/*     */         }
/*     */       }
/* 517 */       if ((i & 0x4) != 0)
/*     */       {
/* 519 */         paramAbstractNioChannel.unsafe().forceFlush();
/*     */       }
/* 521 */       if ((i & 0x8) != 0)
/*     */       {
/*     */ 
/* 524 */         int j = paramSelectionKey.interestOps();
/* 525 */         j &= 0xFFFFFFF7;
/* 526 */         paramSelectionKey.interestOps(j);
/*     */         
/* 528 */         localNioUnsafe.finishConnect();
/*     */       }
/*     */     } catch (CancelledKeyException localCancelledKeyException) {
/* 531 */       localNioUnsafe.close(localNioUnsafe.voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey paramSelectionKey, NioTask<SelectableChannel> paramNioTask) {
/* 536 */     int i = 0;
/*     */     try {
/* 538 */       paramNioTask.channelReady(paramSelectionKey.channel(), paramSelectionKey);
/* 539 */       i = 1;
/*     */     } catch (Exception localException) {
/* 541 */       paramSelectionKey.cancel();
/* 542 */       invokeChannelUnregistered(paramNioTask, paramSelectionKey, localException);
/* 543 */       i = 2;
/*     */     } finally {
/* 545 */       switch (i) {
/*     */       case 0: 
/* 547 */         paramSelectionKey.cancel();
/* 548 */         invokeChannelUnregistered(paramNioTask, paramSelectionKey, null);
/* 549 */         break;
/*     */       case 1: 
/* 551 */         if (!paramSelectionKey.isValid()) {
/* 552 */           invokeChannelUnregistered(paramNioTask, paramSelectionKey, null);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeAll() {
/* 560 */     selectAgain();
/* 561 */     Set localSet = this.selector.keys();
/* 562 */     ArrayList localArrayList = new ArrayList(localSet.size());
/* 563 */     for (Iterator localIterator = localSet.iterator(); localIterator.hasNext();) { localObject1 = (SelectionKey)localIterator.next();
/* 564 */       Object localObject2 = ((SelectionKey)localObject1).attachment();
/* 565 */       if ((localObject2 instanceof AbstractNioChannel)) {
/* 566 */         localArrayList.add((AbstractNioChannel)localObject2);
/*     */       } else {
/* 568 */         ((SelectionKey)localObject1).cancel();
/*     */         
/* 570 */         NioTask localNioTask = (NioTask)localObject2;
/* 571 */         invokeChannelUnregistered(localNioTask, (SelectionKey)localObject1, null);
/*     */       }
/*     */     }
/*     */     Object localObject1;
/* 575 */     for (localIterator = localArrayList.iterator(); localIterator.hasNext();) { localObject1 = (AbstractNioChannel)localIterator.next();
/* 576 */       ((AbstractNioChannel)localObject1).unsafe().close(((AbstractNioChannel)localObject1).unsafe().voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void invokeChannelUnregistered(NioTask<SelectableChannel> paramNioTask, SelectionKey paramSelectionKey, Throwable paramThrowable) {
/*     */     try {
/* 582 */       paramNioTask.channelUnregistered(paramSelectionKey.channel(), paramThrowable);
/*     */     } catch (Exception localException) {
/* 584 */       logger.warn("Unexpected exception while running NioTask.channelUnregistered()", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void wakeup(boolean paramBoolean)
/*     */   {
/* 590 */     if ((!paramBoolean) && (this.wakenUp.compareAndSet(false, true))) {
/* 591 */       this.selector.wakeup();
/*     */     }
/*     */   }
/*     */   
/*     */   void selectNow() throws IOException {
/*     */     try {
/* 597 */       this.selector.selectNow();
/*     */     }
/*     */     finally {
/* 600 */       if (this.wakenUp.get()) {
/* 601 */         this.selector.wakeup();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void select(boolean paramBoolean) throws IOException {
/* 607 */     Selector localSelector = this.selector;
/*     */     try {
/* 609 */       int i = 0;
/* 610 */       long l1 = System.nanoTime();
/* 611 */       long l2 = l1 + delayNanos(l1);
/*     */       for (;;) {
/* 613 */         long l3 = (l2 - l1 + 500000L) / 1000000L;
/* 614 */         if (l3 <= 0L) {
/* 615 */           if (i != 0) break;
/* 616 */           localSelector.selectNow();
/* 617 */           i = 1; break;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 622 */         int j = localSelector.select(l3);
/* 623 */         i++;
/*     */         
/* 625 */         if ((j != 0) || (paramBoolean) || (this.wakenUp.get()) || (hasTasks()) || (hasScheduledTasks())) {
/*     */           break;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 632 */         if (Thread.interrupted())
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 638 */           if (logger.isDebugEnabled()) {
/* 639 */             logger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
/*     */           }
/*     */           
/*     */ 
/* 643 */           i = 1;
/* 644 */           break;
/*     */         }
/*     */         
/* 647 */         long l4 = System.nanoTime();
/* 648 */         if (l4 - TimeUnit.MILLISECONDS.toNanos(l3) >= l1)
/*     */         {
/* 650 */           i = 1;
/* 651 */         } else if ((SELECTOR_AUTO_REBUILD_THRESHOLD > 0) && (i >= SELECTOR_AUTO_REBUILD_THRESHOLD))
/*     */         {
/*     */ 
/*     */ 
/* 655 */           logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", Integer.valueOf(i));
/*     */           
/*     */ 
/*     */ 
/* 659 */           rebuildSelector();
/* 660 */           localSelector = this.selector;
/*     */           
/*     */ 
/* 663 */           localSelector.selectNow();
/* 664 */           i = 1;
/* 665 */           break;
/*     */         }
/*     */         
/* 668 */         l1 = l4;
/*     */       }
/*     */       
/* 671 */       if ((i > 3) && 
/* 672 */         (logger.isDebugEnabled())) {
/* 673 */         logger.debug("Selector.select() returned prematurely {} times in a row.", Integer.valueOf(i - 1));
/*     */       }
/*     */     }
/*     */     catch (CancelledKeyException localCancelledKeyException) {
/* 677 */       if (logger.isDebugEnabled()) {
/* 678 */         logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector - JDK bug?", localCancelledKeyException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void selectAgain()
/*     */   {
/* 685 */     this.needsToSelectAgain = false;
/*     */     try {
/* 687 */       this.selector.selectNow();
/*     */     } catch (Throwable localThrowable) {
/* 689 */       logger.warn("Failed to update SelectionKeys.", localThrowable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\nio\NioEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */