/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tailer
/*     */   implements Runnable
/*     */ {
/*     */   private static final int DEFAULT_DELAY_MILLIS = 1000;
/*     */   private static final String RAF_MODE = "r";
/*     */   private static final int DEFAULT_BUFSIZE = 4096;
/*     */   private final byte[] inbuf;
/*     */   private final File file;
/*     */   private final long delayMillis;
/*     */   private final boolean end;
/*     */   private final TailerListener listener;
/*     */   private final boolean reOpen;
/* 148 */   private volatile boolean run = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener)
/*     */   {
/* 156 */     this(paramFile, paramTailerListener, 1000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener, long paramLong)
/*     */   {
/* 166 */     this(paramFile, paramTailerListener, paramLong, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean)
/*     */   {
/* 177 */     this(paramFile, paramTailerListener, paramLong, paramBoolean, 4096);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 189 */     this(paramFile, paramTailerListener, paramLong, paramBoolean1, paramBoolean2, 4096);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean, int paramInt)
/*     */   {
/* 201 */     this(paramFile, paramTailerListener, paramLong, paramBoolean, false, paramInt);
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
/*     */   public Tailer(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
/*     */   {
/* 214 */     this.file = paramFile;
/* 215 */     this.delayMillis = paramLong;
/* 216 */     this.end = paramBoolean1;
/*     */     
/* 218 */     this.inbuf = new byte[paramInt];
/*     */     
/*     */ 
/* 221 */     this.listener = paramTailerListener;
/* 222 */     paramTailerListener.init(this);
/* 223 */     this.reOpen = paramBoolean2;
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
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean, int paramInt)
/*     */   {
/* 237 */     Tailer localTailer = new Tailer(paramFile, paramTailerListener, paramLong, paramBoolean, paramInt);
/* 238 */     Thread localThread = new Thread(localTailer);
/* 239 */     localThread.setDaemon(true);
/* 240 */     localThread.start();
/* 241 */     return localTailer;
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
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
/*     */   {
/* 257 */     Tailer localTailer = new Tailer(paramFile, paramTailerListener, paramLong, paramBoolean1, paramBoolean2, paramInt);
/* 258 */     Thread localThread = new Thread(localTailer);
/* 259 */     localThread.setDaemon(true);
/* 260 */     localThread.start();
/* 261 */     return localTailer;
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
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean)
/*     */   {
/* 274 */     return create(paramFile, paramTailerListener, paramLong, paramBoolean, 4096);
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
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 288 */     return create(paramFile, paramTailerListener, paramLong, paramBoolean1, paramBoolean2, 4096);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener, long paramLong)
/*     */   {
/* 300 */     return create(paramFile, paramTailerListener, paramLong, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Tailer create(File paramFile, TailerListener paramTailerListener)
/*     */   {
/* 312 */     return create(paramFile, paramTailerListener, 1000L, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public File getFile()
/*     */   {
/* 321 */     return this.file;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getDelay()
/*     */   {
/* 330 */     return this.delayMillis;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 337 */     RandomAccessFile localRandomAccessFile1 = null;
/*     */     try {
/* 339 */       long l1 = 0L;
/* 340 */       long l2 = 0L;
/*     */       
/* 342 */       while ((this.run) && (localRandomAccessFile1 == null)) {
/*     */         try {
/* 344 */           localRandomAccessFile1 = new RandomAccessFile(this.file, "r");
/*     */         } catch (FileNotFoundException localFileNotFoundException1) {
/* 346 */           this.listener.fileNotFound();
/*     */         }
/*     */         
/* 349 */         if (localRandomAccessFile1 == null) {
/*     */           try {
/* 351 */             Thread.sleep(this.delayMillis);
/*     */           }
/*     */           catch (InterruptedException localInterruptedException1) {}
/*     */         }
/*     */         else {
/* 356 */           l2 = this.end ? this.file.length() : 0L;
/* 357 */           l1 = System.currentTimeMillis();
/* 358 */           localRandomAccessFile1.seek(l2);
/*     */         }
/*     */       }
/*     */       
/* 362 */       while (this.run)
/*     */       {
/* 364 */         boolean bool = FileUtils.isFileNewer(this.file, l1);
/*     */         
/*     */ 
/* 367 */         long l3 = this.file.length();
/*     */         
/* 369 */         if (l3 < l2)
/*     */         {
/*     */ 
/* 372 */           this.listener.fileRotated();
/*     */           
/*     */ 
/*     */           try
/*     */           {
/* 377 */             localRandomAccessFile2 = localRandomAccessFile1;
/* 378 */             localRandomAccessFile1 = new RandomAccessFile(this.file, "r");
/* 379 */             l2 = 0L;
/*     */           }
/*     */           catch (FileNotFoundException localFileNotFoundException2)
/*     */           {
/*     */             RandomAccessFile localRandomAccessFile2;
/* 384 */             this.listener.fileNotFound();
/*     */           }
/*     */           
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 392 */           if (l3 > l2)
/*     */           {
/*     */ 
/* 395 */             l2 = readLines(localRandomAccessFile1);
/* 396 */             l1 = System.currentTimeMillis();
/*     */           }
/* 398 */           else if (bool)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 404 */             l2 = 0L;
/* 405 */             localRandomAccessFile1.seek(l2);
/*     */             
/*     */ 
/* 408 */             l2 = readLines(localRandomAccessFile1);
/* 409 */             l1 = System.currentTimeMillis();
/*     */           }
/*     */           
/* 412 */           if (this.reOpen) {
/* 413 */             IOUtils.closeQuietly(localRandomAccessFile1);
/*     */           }
/*     */           try {
/* 416 */             Thread.sleep(this.delayMillis);
/*     */           }
/*     */           catch (InterruptedException localInterruptedException2) {}
/* 419 */           if ((this.run) && (this.reOpen)) {
/* 420 */             localRandomAccessFile1 = new RandomAccessFile(this.file, "r");
/* 421 */             localRandomAccessFile1.seek(l2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 427 */       this.listener.handle(localException);
/*     */     }
/*     */     finally {
/* 430 */       IOUtils.closeQuietly(localRandomAccessFile1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 438 */     this.run = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long readLines(RandomAccessFile paramRandomAccessFile)
/*     */     throws IOException
/*     */   {
/* 449 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 451 */     long l1 = paramRandomAccessFile.getFilePointer();
/* 452 */     long l2 = l1;
/*     */     
/*     */ 
/* 455 */     int i = 0;
/* 456 */     int j; while ((this.run) && ((j = paramRandomAccessFile.read(this.inbuf)) != -1)) {
/* 457 */       for (int k = 0; k < j; k++) {
/* 458 */         int m = this.inbuf[k];
/* 459 */         switch (m) {
/*     */         case 10: 
/* 461 */           i = 0;
/* 462 */           this.listener.handle(localStringBuilder.toString());
/* 463 */           localStringBuilder.setLength(0);
/* 464 */           l2 = l1 + k + 1L;
/* 465 */           break;
/*     */         case 13: 
/* 467 */           if (i != 0) {
/* 468 */             localStringBuilder.append('\r');
/*     */           }
/* 470 */           i = 1;
/* 471 */           break;
/*     */         default: 
/* 473 */           if (i != 0) {
/* 474 */             i = 0;
/* 475 */             this.listener.handle(localStringBuilder.toString());
/* 476 */             localStringBuilder.setLength(0);
/* 477 */             l2 = l1 + k + 1L;
/*     */           }
/* 479 */           localStringBuilder.append((char)m);
/*     */         }
/*     */         
/*     */       }
/* 483 */       l1 = paramRandomAccessFile.getFilePointer();
/*     */     }
/*     */     
/* 486 */     paramRandomAccessFile.seek(l2);
/* 487 */     return l2;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\Tailer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */