/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.concurrent.DefaultThreadFactory;
/*     */ import io.netty.util.internal.MpscLinkedQueueNode;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ThreadDeathWatcher
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
/*  43 */   private static final ThreadFactory threadFactory = new DefaultThreadFactory(ThreadDeathWatcher.class, true, 1);
/*     */   
/*     */ 
/*  46 */   private static final Queue<Entry> pendingEntries = PlatformDependent.newMpscQueue();
/*  47 */   private static final Watcher watcher = new Watcher(null);
/*  48 */   private static final AtomicBoolean started = new AtomicBoolean();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static volatile Thread watcherThread;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void watch(Thread paramThread, Runnable paramRunnable)
/*     */   {
/*  60 */     if (paramThread == null) {
/*  61 */       throw new NullPointerException("thread");
/*     */     }
/*  63 */     if (paramRunnable == null) {
/*  64 */       throw new NullPointerException("task");
/*     */     }
/*  66 */     if (!paramThread.isAlive()) {
/*  67 */       throw new IllegalArgumentException("thread must be alive.");
/*     */     }
/*     */     
/*  70 */     schedule(paramThread, paramRunnable, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void unwatch(Thread paramThread, Runnable paramRunnable)
/*     */   {
/*  77 */     if (paramThread == null) {
/*  78 */       throw new NullPointerException("thread");
/*     */     }
/*  80 */     if (paramRunnable == null) {
/*  81 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/*  84 */     schedule(paramThread, paramRunnable, false);
/*     */   }
/*     */   
/*     */   private static void schedule(Thread paramThread, Runnable paramRunnable, boolean paramBoolean) {
/*  88 */     pendingEntries.add(new Entry(paramThread, paramRunnable, paramBoolean));
/*     */     
/*  90 */     if (started.compareAndSet(false, true)) {
/*  91 */       Thread localThread = threadFactory.newThread(watcher);
/*  92 */       localThread.start();
/*  93 */       watcherThread = localThread;
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
/*     */   public static boolean awaitInactivity(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 107 */     if (paramTimeUnit == null) {
/* 108 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 111 */     Thread localThread = watcherThread;
/* 112 */     if (localThread != null) {
/* 113 */       localThread.join(paramTimeUnit.toMillis(paramLong));
/* 114 */       return !localThread.isAlive();
/*     */     }
/* 116 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static final class Watcher
/*     */     implements Runnable
/*     */   {
/* 124 */     private final List<ThreadDeathWatcher.Entry> watchees = new ArrayList();
/*     */     
/*     */     public void run()
/*     */     {
/*     */       for (;;) {
/* 129 */         fetchWatchees();
/* 130 */         notifyWatchees();
/*     */         
/*     */ 
/* 133 */         fetchWatchees();
/* 134 */         notifyWatchees();
/*     */         try
/*     */         {
/* 137 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */         
/*     */ 
/* 142 */         if ((this.watchees.isEmpty()) && (ThreadDeathWatcher.pendingEntries.isEmpty()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 147 */           boolean bool = ThreadDeathWatcher.started.compareAndSet(true, false);
/* 148 */           assert (bool);
/*     */           
/*     */ 
/* 151 */           if (ThreadDeathWatcher.pendingEntries.isEmpty()) {
/*     */             break;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */           if (!ThreadDeathWatcher.started.compareAndSet(false, true)) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private void fetchWatchees()
/*     */     {
/*     */       for (;;)
/*     */       {
/* 175 */         ThreadDeathWatcher.Entry localEntry = (ThreadDeathWatcher.Entry)ThreadDeathWatcher.pendingEntries.poll();
/* 176 */         if (localEntry == null) {
/*     */           break;
/*     */         }
/*     */         
/* 180 */         if (localEntry.isWatch) {
/* 181 */           this.watchees.add(localEntry);
/*     */         } else {
/* 183 */           this.watchees.remove(localEntry);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private void notifyWatchees() {
/* 189 */       List localList = this.watchees;
/* 190 */       for (int i = 0; i < localList.size();) {
/* 191 */         ThreadDeathWatcher.Entry localEntry = (ThreadDeathWatcher.Entry)localList.get(i);
/* 192 */         if (!localEntry.thread.isAlive()) {
/* 193 */           localList.remove(i);
/*     */           try {
/* 195 */             localEntry.task.run();
/*     */           } catch (Throwable localThrowable) {
/* 197 */             ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", localThrowable);
/*     */           }
/*     */         } else {
/* 200 */           i++;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class Entry extends MpscLinkedQueueNode<Entry> {
/*     */     final Thread thread;
/*     */     final Runnable task;
/*     */     final boolean isWatch;
/*     */     
/*     */     Entry(Thread paramThread, Runnable paramRunnable, boolean paramBoolean) {
/* 212 */       this.thread = paramThread;
/* 213 */       this.task = paramRunnable;
/* 214 */       this.isWatch = paramBoolean;
/*     */     }
/*     */     
/*     */     public Entry value()
/*     */     {
/* 219 */       return this;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 224 */       return this.thread.hashCode() ^ this.task.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 229 */       if (paramObject == this) {
/* 230 */         return true;
/*     */       }
/*     */       
/* 233 */       if (!(paramObject instanceof Entry)) {
/* 234 */         return false;
/*     */       }
/*     */       
/* 237 */       Entry localEntry = (Entry)paramObject;
/* 238 */       return (this.thread == localEntry.thread) && (this.task == localEntry.task);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\ThreadDeathWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */