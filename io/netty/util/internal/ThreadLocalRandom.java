/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ThreadLocalRandom
/*     */   extends Random
/*     */ {
/*  63 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
/*     */   
/*  65 */   private static final AtomicLong seedUniquifier = new AtomicLong();
/*     */   private static volatile long initialSeedUniquifier;
/*     */   private static final long multiplier = 25214903917L;
/*     */   private static final long addend = 11L;
/*     */   
/*  70 */   public static void setInitialSeedUniquifier(long paramLong) { initialSeedUniquifier = paramLong; }
/*     */   
/*     */ 
/*     */   public static synchronized long getInitialSeedUniquifier()
/*     */   {
/*  75 */     long l1 = initialSeedUniquifier;
/*  76 */     if (l1 == 0L)
/*     */     {
/*  78 */       initialSeedUniquifier = l1 = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  83 */     if (l1 == 0L)
/*     */     {
/*     */ 
/*  86 */       final LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
/*  87 */       Thread local1 = new Thread("initialSeedUniquifierGenerator")
/*     */       {
/*     */         public void run() {
/*  90 */           SecureRandom localSecureRandom = new SecureRandom();
/*  91 */           localLinkedBlockingQueue.add(localSecureRandom.generateSeed(8));
/*     */         }
/*  93 */       };
/*  94 */       local1.setDaemon(true);
/*  95 */       local1.start();
/*  96 */       local1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*     */       {
/*     */         public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable) {
/*  99 */           ThreadLocalRandom.logger.debug("An exception has been raised by {}", paramAnonymousThread.getName(), paramAnonymousThrowable);
/*     */         }
/*     */         
/*     */ 
/* 103 */       });
/* 104 */       long l2 = 3L;
/* 105 */       long l3 = System.nanoTime() + TimeUnit.SECONDS.toNanos(3L);
/* 106 */       int i = 0;
/*     */       for (;;) {
/* 108 */         long l4 = l3 - System.nanoTime();
/* 109 */         if (l4 <= 0L) {
/* 110 */           local1.interrupt();
/* 111 */           logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entrophy?", Long.valueOf(3L));
/*     */           
/*     */ 
/*     */ 
/* 115 */           break;
/*     */         }
/*     */         try
/*     */         {
/* 119 */           byte[] arrayOfByte = (byte[])localLinkedBlockingQueue.poll(l4, TimeUnit.NANOSECONDS);
/* 120 */           if (arrayOfByte != null) {
/* 121 */             l1 = (arrayOfByte[0] & 0xFF) << 56 | (arrayOfByte[1] & 0xFF) << 48 | (arrayOfByte[2] & 0xFF) << 40 | (arrayOfByte[3] & 0xFF) << 32 | (arrayOfByte[4] & 0xFF) << 24 | (arrayOfByte[5] & 0xFF) << 16 | (arrayOfByte[6] & 0xFF) << 8 | arrayOfByte[7] & 0xFF;
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 130 */             break;
/*     */           }
/*     */         } catch (InterruptedException localInterruptedException) {
/* 133 */           i = 1;
/* 134 */           logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
/* 135 */           break;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 140 */       l1 ^= 0x3255ECDC33BAE119;
/* 141 */       l1 ^= Long.reverse(System.nanoTime());
/*     */       
/* 143 */       initialSeedUniquifier = l1;
/*     */       
/* 145 */       if (i != 0)
/*     */       {
/* 147 */         Thread.currentThread().interrupt();
/*     */         
/*     */ 
/*     */ 
/* 151 */         local1.interrupt();
/*     */       }
/*     */     }
/*     */     
/* 155 */     return l1;
/*     */   }
/*     */   
/*     */   private static long newSeed() {
/* 159 */     long l1 = System.nanoTime();
/*     */     for (;;) {
/* 161 */       long l2 = seedUniquifier.get();
/* 162 */       long l3 = l2 != 0L ? l2 : getInitialSeedUniquifier();
/*     */       
/*     */ 
/* 165 */       long l4 = l3 * 181783497276652981L;
/*     */       
/* 167 */       if (seedUniquifier.compareAndSet(l2, l4)) {
/* 168 */         if ((l2 == 0L) && (logger.isDebugEnabled())) {
/* 169 */           logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", new Object[] { Long.valueOf(l3), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - l1)) }));
/*     */         }
/*     */         
/*     */ 
/* 173 */         return l4 ^ System.nanoTime();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final long mask = 281474976710655L;
/*     */   
/*     */   private long rnd;
/*     */   
/*     */   boolean initialized;
/*     */   
/*     */   private long pad0;
/*     */   
/*     */   private long pad1;
/*     */   
/*     */   private long pad2;
/*     */   
/*     */   private long pad3;
/*     */   
/*     */   private long pad4;
/*     */   
/*     */   private long pad5;
/*     */   
/*     */   private long pad6;
/*     */   
/*     */   private long pad7;
/*     */   
/*     */   private static final long serialVersionUID = -5851777807851030925L;
/*     */   
/*     */   ThreadLocalRandom()
/*     */   {
/* 205 */     super(newSeed());
/* 206 */     this.initialized = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ThreadLocalRandom current()
/*     */   {
/* 215 */     return InternalThreadLocalMap.get().random();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSeed(long paramLong)
/*     */   {
/* 225 */     if (this.initialized) {
/* 226 */       throw new UnsupportedOperationException();
/*     */     }
/* 228 */     this.rnd = ((paramLong ^ 0x5DEECE66D) & 0xFFFFFFFFFFFF);
/*     */   }
/*     */   
/*     */   protected int next(int paramInt) {
/* 232 */     this.rnd = (this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFF);
/* 233 */     return (int)(this.rnd >>> 48 - paramInt);
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
/*     */   public int nextInt(int paramInt1, int paramInt2)
/*     */   {
/* 247 */     if (paramInt1 >= paramInt2) {
/* 248 */       throw new IllegalArgumentException();
/*     */     }
/* 250 */     return nextInt(paramInt2 - paramInt1) + paramInt1;
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
/*     */   public long nextLong(long paramLong)
/*     */   {
/* 263 */     if (paramLong <= 0L) {
/* 264 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 272 */     long l1 = 0L;
/* 273 */     while (paramLong >= 2147483647L) {
/* 274 */       int i = next(2);
/* 275 */       long l2 = paramLong >>> 1;
/* 276 */       long l3 = (i & 0x2) == 0 ? l2 : paramLong - l2;
/* 277 */       if ((i & 0x1) == 0) {
/* 278 */         l1 += paramLong - l3;
/*     */       }
/* 280 */       paramLong = l3;
/*     */     }
/* 282 */     return l1 + nextInt((int)paramLong);
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
/*     */   public long nextLong(long paramLong1, long paramLong2)
/*     */   {
/* 296 */     if (paramLong1 >= paramLong2) {
/* 297 */       throw new IllegalArgumentException();
/*     */     }
/* 299 */     return nextLong(paramLong2 - paramLong1) + paramLong1;
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
/*     */   public double nextDouble(double paramDouble)
/*     */   {
/* 312 */     if (paramDouble <= 0.0D) {
/* 313 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/* 315 */     return nextDouble() * paramDouble;
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
/*     */   public double nextDouble(double paramDouble1, double paramDouble2)
/*     */   {
/* 329 */     if (paramDouble1 >= paramDouble2) {
/* 330 */       throw new IllegalArgumentException();
/*     */     }
/* 332 */     return nextDouble() * (paramDouble2 - paramDouble1) + paramDouble1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\ThreadLocalRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */