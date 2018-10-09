/*     */ package org.apache.commons.io.monitor;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileAlterationMonitor
/*     */   implements Runnable
/*     */ {
/*     */   private final long interval;
/*  34 */   private final List<FileAlterationObserver> observers = new CopyOnWriteArrayList();
/*  35 */   private Thread thread = null;
/*     */   private ThreadFactory threadFactory;
/*  37 */   private volatile boolean running = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public FileAlterationMonitor()
/*     */   {
/*  43 */     this(10000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationMonitor(long paramLong)
/*     */   {
/*  53 */     this.interval = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileAlterationMonitor(long paramLong, FileAlterationObserver... paramVarArgs)
/*     */   {
/*  64 */     this(paramLong);
/*  65 */     if (paramVarArgs != null) {
/*  66 */       for (FileAlterationObserver localFileAlterationObserver : paramVarArgs) {
/*  67 */         addObserver(localFileAlterationObserver);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getInterval()
/*     */   {
/*  78 */     return this.interval;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setThreadFactory(ThreadFactory paramThreadFactory)
/*     */   {
/*  87 */     this.threadFactory = paramThreadFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addObserver(FileAlterationObserver paramFileAlterationObserver)
/*     */   {
/*  96 */     if (paramFileAlterationObserver != null) {
/*  97 */       this.observers.add(paramFileAlterationObserver);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeObserver(FileAlterationObserver paramFileAlterationObserver)
/*     */   {
/* 107 */     while ((paramFileAlterationObserver != null) && 
/* 108 */       (this.observers.remove(paramFileAlterationObserver))) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterable<FileAlterationObserver> getObservers()
/*     */   {
/* 120 */     return this.observers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void start()
/*     */     throws Exception
/*     */   {
/* 129 */     if (this.running) {
/* 130 */       throw new IllegalStateException("Monitor is already running");
/*     */     }
/* 132 */     for (FileAlterationObserver localFileAlterationObserver : this.observers) {
/* 133 */       localFileAlterationObserver.initialize();
/*     */     }
/* 135 */     this.running = true;
/* 136 */     if (this.threadFactory != null) {
/* 137 */       this.thread = this.threadFactory.newThread(this);
/*     */     } else {
/* 139 */       this.thread = new Thread(this);
/*     */     }
/* 141 */     this.thread.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void stop()
/*     */     throws Exception
/*     */   {
/* 150 */     stop(this.interval);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void stop(long paramLong)
/*     */     throws Exception
/*     */   {
/* 162 */     if (!this.running) {
/* 163 */       throw new IllegalStateException("Monitor is not running");
/*     */     }
/* 165 */     this.running = false;
/*     */     try {
/* 167 */       this.thread.join(paramLong);
/*     */     } catch (InterruptedException localInterruptedException) {
/* 169 */       Thread.currentThread().interrupt();
/*     */     }
/* 171 */     for (FileAlterationObserver localFileAlterationObserver : this.observers) {
/* 172 */       localFileAlterationObserver.destroy();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 180 */     while (this.running) {
/* 181 */       for (FileAlterationObserver localFileAlterationObserver : this.observers) {
/* 182 */         localFileAlterationObserver.checkAndNotify();
/*     */       }
/* 184 */       if (!this.running) {
/*     */         break;
/*     */       }
/*     */       try {
/* 188 */         Thread.sleep(this.interval);
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\monitor\FileAlterationMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */