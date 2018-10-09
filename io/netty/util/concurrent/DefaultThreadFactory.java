/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.ThreadFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultThreadFactory
/*     */   implements ThreadFactory
/*     */ {
/*  30 */   private static final AtomicInteger poolId = new AtomicInteger();
/*     */   
/*  32 */   private final AtomicInteger nextId = new AtomicInteger();
/*     */   private final String prefix;
/*     */   private final boolean daemon;
/*     */   private final int priority;
/*     */   
/*     */   public DefaultThreadFactory(Class<?> paramClass) {
/*  38 */     this(paramClass, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String paramString) {
/*  42 */     this(paramString, false, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> paramClass, boolean paramBoolean) {
/*  46 */     this(paramClass, paramBoolean, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String paramString, boolean paramBoolean) {
/*  50 */     this(paramString, paramBoolean, 5);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> paramClass, int paramInt) {
/*  54 */     this(paramClass, false, paramInt);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(String paramString, int paramInt) {
/*  58 */     this(paramString, false, paramInt);
/*     */   }
/*     */   
/*     */   public DefaultThreadFactory(Class<?> paramClass, boolean paramBoolean, int paramInt) {
/*  62 */     this(toPoolName(paramClass), paramBoolean, paramInt);
/*     */   }
/*     */   
/*     */   private static String toPoolName(Class<?> paramClass) {
/*  66 */     if (paramClass == null) {
/*  67 */       throw new NullPointerException("poolType");
/*     */     }
/*     */     
/*  70 */     String str = StringUtil.simpleClassName(paramClass);
/*  71 */     switch (str.length()) {
/*     */     case 0: 
/*  73 */       return "unknown";
/*     */     case 1: 
/*  75 */       return str.toLowerCase(Locale.US);
/*     */     }
/*  77 */     if ((Character.isUpperCase(str.charAt(0))) && (Character.isLowerCase(str.charAt(1)))) {
/*  78 */       return Character.toLowerCase(str.charAt(0)) + str.substring(1);
/*     */     }
/*  80 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */   public DefaultThreadFactory(String paramString, boolean paramBoolean, int paramInt)
/*     */   {
/*  86 */     if (paramString == null) {
/*  87 */       throw new NullPointerException("poolName");
/*     */     }
/*  89 */     if ((paramInt < 1) || (paramInt > 10)) {
/*  90 */       throw new IllegalArgumentException("priority: " + paramInt + " (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
/*     */     }
/*     */     
/*     */ 
/*  94 */     this.prefix = (paramString + '-' + poolId.incrementAndGet() + '-');
/*  95 */     this.daemon = paramBoolean;
/*  96 */     this.priority = paramInt;
/*     */   }
/*     */   
/*     */   public Thread newThread(Runnable paramRunnable)
/*     */   {
/* 101 */     Thread localThread = newThread(new DefaultRunnableDecorator(paramRunnable), this.prefix + this.nextId.incrementAndGet());
/*     */     try {
/* 103 */       if (localThread.isDaemon()) {
/* 104 */         if (!this.daemon) {
/* 105 */           localThread.setDaemon(false);
/*     */         }
/*     */       }
/* 108 */       else if (this.daemon) {
/* 109 */         localThread.setDaemon(true);
/*     */       }
/*     */       
/*     */ 
/* 113 */       if (localThread.getPriority() != this.priority) {
/* 114 */         localThread.setPriority(this.priority);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/* 119 */     return localThread;
/*     */   }
/*     */   
/*     */   protected Thread newThread(Runnable paramRunnable, String paramString) {
/* 123 */     return new FastThreadLocalThread(paramRunnable, paramString);
/*     */   }
/*     */   
/*     */   private static final class DefaultRunnableDecorator implements Runnable
/*     */   {
/*     */     private final Runnable r;
/*     */     
/*     */     DefaultRunnableDecorator(Runnable paramRunnable) {
/* 131 */       this.r = paramRunnable;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       try {
/* 137 */         this.r.run();
/*     */       } finally {
/* 139 */         FastThreadLocal.removeAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\DefaultThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */