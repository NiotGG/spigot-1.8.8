/*     */ package io.netty.handler.traffic;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrafficCounter
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  44 */   private final AtomicLong currentWrittenBytes = new AtomicLong();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  49 */   private final AtomicLong currentReadBytes = new AtomicLong();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  54 */   private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  59 */   private final AtomicLong cumulativeReadBytes = new AtomicLong();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastCumulativeTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastWriteThroughput;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastReadThroughput;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  79 */   private final AtomicLong lastTime = new AtomicLong();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastWrittenBytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastReadBytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastNonNullWrittenBytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastNonNullWrittenTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastNonNullReadTime;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private long lastNonNullReadBytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 114 */   final AtomicLong checkInterval = new AtomicLong(1000L);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   final String name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final AbstractTrafficShapingHandler trafficShapingHandler;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final ScheduledExecutorService executor;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Runnable monitor;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private volatile ScheduledFuture<?> scheduledFuture;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 144 */   final AtomicBoolean monitorActive = new AtomicBoolean();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class TrafficMonitoringTask
/*     */     implements Runnable
/*     */   {
/*     */     private final AbstractTrafficShapingHandler trafficShapingHandler1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private final TrafficCounter counter;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected TrafficMonitoringTask(AbstractTrafficShapingHandler paramAbstractTrafficShapingHandler, TrafficCounter paramTrafficCounter)
/*     */     {
/* 168 */       this.trafficShapingHandler1 = paramAbstractTrafficShapingHandler;
/* 169 */       this.counter = paramTrafficCounter;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 174 */       if (!this.counter.monitorActive.get()) {
/* 175 */         return;
/*     */       }
/* 177 */       long l = System.currentTimeMillis();
/* 178 */       this.counter.resetAccounting(l);
/* 179 */       if (this.trafficShapingHandler1 != null) {
/* 180 */         this.trafficShapingHandler1.doAccounting(this.counter);
/*     */       }
/* 182 */       this.counter.scheduledFuture = this.counter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void start()
/*     */   {
/* 191 */     if (this.monitorActive.get()) {
/* 192 */       return;
/*     */     }
/* 194 */     this.lastTime.set(System.currentTimeMillis());
/* 195 */     if (this.checkInterval.get() > 0L) {
/* 196 */       this.monitorActive.set(true);
/* 197 */       this.monitor = new TrafficMonitoringTask(this.trafficShapingHandler, this);
/* 198 */       this.scheduledFuture = this.executor.schedule(this.monitor, this.checkInterval.get(), TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 206 */     if (!this.monitorActive.get()) {
/* 207 */       return;
/*     */     }
/* 209 */     this.monitorActive.set(false);
/* 210 */     resetAccounting(System.currentTimeMillis());
/* 211 */     if (this.trafficShapingHandler != null) {
/* 212 */       this.trafficShapingHandler.doAccounting(this);
/*     */     }
/* 214 */     if (this.scheduledFuture != null) {
/* 215 */       this.scheduledFuture.cancel(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   synchronized void resetAccounting(long paramLong)
/*     */   {
/* 226 */     long l = paramLong - this.lastTime.getAndSet(paramLong);
/* 227 */     if (l == 0L)
/*     */     {
/* 229 */       return;
/*     */     }
/* 231 */     if ((logger.isDebugEnabled()) && (l > 2L * checkInterval())) {
/* 232 */       logger.debug("Acct schedule not ok: " + l + " > 2*" + checkInterval() + " from " + this.name);
/*     */     }
/* 234 */     this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
/* 235 */     this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
/* 236 */     this.lastReadThroughput = (this.lastReadBytes / l * 1000L);
/*     */     
/* 238 */     this.lastWriteThroughput = (this.lastWrittenBytes / l * 1000L);
/*     */     
/* 240 */     if (this.lastWrittenBytes > 0L) {
/* 241 */       this.lastNonNullWrittenBytes = this.lastWrittenBytes;
/* 242 */       this.lastNonNullWrittenTime = paramLong;
/*     */     }
/* 244 */     if (this.lastReadBytes > 0L) {
/* 245 */       this.lastNonNullReadBytes = this.lastReadBytes;
/* 246 */       this.lastNonNullReadTime = paramLong;
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
/*     */   public TrafficCounter(AbstractTrafficShapingHandler paramAbstractTrafficShapingHandler, ScheduledExecutorService paramScheduledExecutorService, String paramString, long paramLong)
/*     */   {
/* 265 */     this.trafficShapingHandler = paramAbstractTrafficShapingHandler;
/* 266 */     this.executor = paramScheduledExecutorService;
/* 267 */     this.name = paramString;
/* 268 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 269 */     configure(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void configure(long paramLong)
/*     */   {
/* 279 */     long l = paramLong / 10L * 10L;
/* 280 */     if (this.checkInterval.get() != l) {
/* 281 */       this.checkInterval.set(l);
/* 282 */       if (l <= 0L) {
/* 283 */         stop();
/*     */         
/* 285 */         this.lastTime.set(System.currentTimeMillis());
/*     */       }
/*     */       else {
/* 288 */         start();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void bytesRecvFlowControl(long paramLong)
/*     */   {
/* 300 */     this.currentReadBytes.addAndGet(paramLong);
/* 301 */     this.cumulativeReadBytes.addAndGet(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void bytesWriteFlowControl(long paramLong)
/*     */   {
/* 311 */     this.currentWrittenBytes.addAndGet(paramLong);
/* 312 */     this.cumulativeWrittenBytes.addAndGet(paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long checkInterval()
/*     */   {
/* 321 */     return this.checkInterval.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long lastReadThroughput()
/*     */   {
/* 329 */     return this.lastReadThroughput;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long lastWriteThroughput()
/*     */   {
/* 337 */     return this.lastWriteThroughput;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long lastReadBytes()
/*     */   {
/* 345 */     return this.lastReadBytes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long lastWrittenBytes()
/*     */   {
/* 353 */     return this.lastWrittenBytes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long currentReadBytes()
/*     */   {
/* 361 */     return this.currentReadBytes.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long currentWrittenBytes()
/*     */   {
/* 369 */     return this.currentWrittenBytes.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long lastTime()
/*     */   {
/* 376 */     return this.lastTime.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long cumulativeWrittenBytes()
/*     */   {
/* 383 */     return this.cumulativeWrittenBytes.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long cumulativeReadBytes()
/*     */   {
/* 390 */     return this.cumulativeReadBytes.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long lastCumulativeTime()
/*     */   {
/* 398 */     return this.lastCumulativeTime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetCumulativeTime()
/*     */   {
/* 405 */     this.lastCumulativeTime = System.currentTimeMillis();
/* 406 */     this.cumulativeReadBytes.set(0L);
/* 407 */     this.cumulativeWrittenBytes.set(0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String name()
/*     */   {
/* 414 */     return this.name;
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
/*     */   public synchronized long readTimeToWait(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 430 */     long l1 = System.currentTimeMillis();
/* 431 */     bytesRecvFlowControl(paramLong1);
/* 432 */     if (paramLong2 == 0L) {
/* 433 */       return 0L;
/*     */     }
/* 435 */     long l2 = this.currentReadBytes.get();
/* 436 */     long l3 = l1 - this.lastTime.get();
/*     */     long l4;
/* 438 */     if ((l3 > 10L) && (l2 > 0L)) {
/* 439 */       l4 = (l2 * 1000L / paramLong2 - l3) / 10L * 10L;
/* 440 */       if (l4 > 10L) {
/* 441 */         if (logger.isDebugEnabled()) {
/* 442 */           logger.debug("Time: " + l4 + ":" + l2 + ":" + l3);
/*     */         }
/* 444 */         return l4 > paramLong3 ? paramLong3 : l4;
/*     */       }
/* 446 */       return 0L;
/*     */     }
/*     */     long l5;
/* 449 */     if ((this.lastNonNullReadBytes > 0L) && (this.lastNonNullReadTime + 10L < l1)) {
/* 450 */       l4 = l2 + this.lastNonNullReadBytes;
/* 451 */       l5 = l1 - this.lastNonNullReadTime;
/* 452 */       long l6 = (l4 * 1000L / paramLong2 - l5) / 10L * 10L;
/* 453 */       if (l6 > 10L) {
/* 454 */         if (logger.isDebugEnabled()) {
/* 455 */           logger.debug("Time: " + l6 + ":" + l4 + ":" + l5);
/*     */         }
/* 457 */         return l6 > paramLong3 ? paramLong3 : l6;
/*     */       }
/*     */     }
/*     */     else {
/* 461 */       l2 += this.lastReadBytes;
/* 462 */       l4 = 10L;
/* 463 */       l5 = (l2 * 1000L / paramLong2 - l4) / 10L * 10L;
/* 464 */       if (l5 > 10L) {
/* 465 */         if (logger.isDebugEnabled()) {
/* 466 */           logger.debug("Time: " + l5 + ":" + l2 + ":" + l4);
/*     */         }
/* 468 */         return l5 > paramLong3 ? paramLong3 : l5;
/*     */       }
/*     */     }
/* 471 */     return 0L;
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
/*     */   public synchronized long writeTimeToWait(long paramLong1, long paramLong2, long paramLong3)
/*     */   {
/* 487 */     bytesWriteFlowControl(paramLong1);
/* 488 */     if (paramLong2 == 0L) {
/* 489 */       return 0L;
/*     */     }
/* 491 */     long l1 = this.currentWrittenBytes.get();
/* 492 */     long l2 = System.currentTimeMillis();
/* 493 */     long l3 = l2 - this.lastTime.get();
/* 494 */     long l4; if ((l3 > 10L) && (l1 > 0L)) {
/* 495 */       l4 = (l1 * 1000L / paramLong2 - l3) / 10L * 10L;
/* 496 */       if (l4 > 10L) {
/* 497 */         if (logger.isDebugEnabled()) {
/* 498 */           logger.debug("Time: " + l4 + ":" + l1 + ":" + l3);
/*     */         }
/* 500 */         return l4 > paramLong3 ? paramLong3 : l4;
/*     */       }
/* 502 */       return 0L; }
/*     */     long l5;
/* 504 */     if ((this.lastNonNullWrittenBytes > 0L) && (this.lastNonNullWrittenTime + 10L < l2)) {
/* 505 */       l4 = l1 + this.lastNonNullWrittenBytes;
/* 506 */       l5 = l2 - this.lastNonNullWrittenTime;
/* 507 */       long l6 = (l4 * 1000L / paramLong2 - l5) / 10L * 10L;
/* 508 */       if (l6 > 10L) {
/* 509 */         if (logger.isDebugEnabled()) {
/* 510 */           logger.debug("Time: " + l6 + ":" + l4 + ":" + l5);
/*     */         }
/* 512 */         return l6 > paramLong3 ? paramLong3 : l6;
/*     */       }
/*     */     } else {
/* 515 */       l1 += this.lastWrittenBytes;
/* 516 */       l4 = 10L + Math.abs(l3);
/* 517 */       l5 = (l1 * 1000L / paramLong2 - l4) / 10L * 10L;
/* 518 */       if (l5 > 10L) {
/* 519 */         if (logger.isDebugEnabled()) {
/* 520 */           logger.debug("Time: " + l5 + ":" + l1 + ":" + l4);
/*     */         }
/* 522 */         return l5 > paramLong3 ? paramLong3 : l5;
/*     */       }
/*     */     }
/* 525 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 533 */     return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10) + " KB/s, Write: " + (this.lastWriteThroughput >> 10) + " KB/s Current Read: " + (this.currentReadBytes.get() >> 10) + " KB Current Write: " + (this.currentWrittenBytes.get() >> 10) + " KB";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\traffic\TrafficCounter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */