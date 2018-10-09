/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileCleaningTracker
/*     */ {
/*     */   ReferenceQueue<Object> q;
/*     */   final Collection<Tracker> trackers;
/*     */   final List<String> deleteFailures;
/*     */   volatile boolean exitWhenFinished;
/*     */   Thread reaper;
/*     */   
/*     */   public FileCleaningTracker()
/*     */   {
/*  48 */     this.q = new ReferenceQueue();
/*     */     
/*     */ 
/*     */ 
/*  52 */     this.trackers = Collections.synchronizedSet(new HashSet());
/*     */     
/*     */ 
/*     */ 
/*  56 */     this.deleteFailures = Collections.synchronizedList(new ArrayList());
/*     */     
/*     */ 
/*     */ 
/*  60 */     this.exitWhenFinished = false;
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
/*     */   public void track(File paramFile, Object paramObject)
/*     */   {
/*  77 */     track(paramFile, paramObject, (FileDeleteStrategy)null);
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
/*     */   public void track(File paramFile, Object paramObject, FileDeleteStrategy paramFileDeleteStrategy)
/*     */   {
/*  91 */     if (paramFile == null) {
/*  92 */       throw new NullPointerException("The file must not be null");
/*     */     }
/*  94 */     addTracker(paramFile.getPath(), paramObject, paramFileDeleteStrategy);
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
/*     */   public void track(String paramString, Object paramObject)
/*     */   {
/* 107 */     track(paramString, paramObject, (FileDeleteStrategy)null);
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
/*     */   public void track(String paramString, Object paramObject, FileDeleteStrategy paramFileDeleteStrategy)
/*     */   {
/* 121 */     if (paramString == null) {
/* 122 */       throw new NullPointerException("The path must not be null");
/*     */     }
/* 124 */     addTracker(paramString, paramObject, paramFileDeleteStrategy);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void addTracker(String paramString, Object paramObject, FileDeleteStrategy paramFileDeleteStrategy)
/*     */   {
/* 136 */     if (this.exitWhenFinished) {
/* 137 */       throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
/*     */     }
/* 139 */     if (this.reaper == null) {
/* 140 */       this.reaper = new Reaper();
/* 141 */       this.reaper.start();
/*     */     }
/* 143 */     this.trackers.add(new Tracker(paramString, paramFileDeleteStrategy, paramObject, this.q));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getTrackCount()
/*     */   {
/* 154 */     return this.trackers.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> getDeleteFailures()
/*     */   {
/* 164 */     return this.deleteFailures;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void exitWhenFinished()
/*     */   {
/* 190 */     this.exitWhenFinished = true;
/* 191 */     if (this.reaper != null) {
/* 192 */       synchronized (this.reaper) {
/* 193 */         this.reaper.interrupt();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final class Reaper
/*     */     extends Thread
/*     */   {
/*     */     Reaper()
/*     */     {
/* 205 */       super();
/* 206 */       setPriority(10);
/* 207 */       setDaemon(true);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void run()
/*     */     {
/* 217 */       while ((!FileCleaningTracker.this.exitWhenFinished) || (FileCleaningTracker.this.trackers.size() > 0)) {
/*     */         try
/*     */         {
/* 220 */           FileCleaningTracker.Tracker localTracker = (FileCleaningTracker.Tracker)FileCleaningTracker.this.q.remove();
/* 221 */           FileCleaningTracker.this.trackers.remove(localTracker);
/* 222 */           if (!localTracker.delete()) {
/* 223 */             FileCleaningTracker.this.deleteFailures.add(localTracker.getPath());
/*     */           }
/* 225 */           localTracker.clear();
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
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
/*     */   private static final class Tracker
/*     */     extends PhantomReference<Object>
/*     */   {
/*     */     private final String path;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final FileDeleteStrategy deleteStrategy;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Tracker(String paramString, FileDeleteStrategy paramFileDeleteStrategy, Object paramObject, ReferenceQueue<? super Object> paramReferenceQueue)
/*     */     {
/* 257 */       super(paramReferenceQueue);
/* 258 */       this.path = paramString;
/* 259 */       this.deleteStrategy = (paramFileDeleteStrategy == null ? FileDeleteStrategy.NORMAL : paramFileDeleteStrategy);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getPath()
/*     */     {
/* 268 */       return this.path;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean delete()
/*     */     {
/* 278 */       return this.deleteStrategy.deleteQuietly(new File(this.path));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\FileCleaningTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */