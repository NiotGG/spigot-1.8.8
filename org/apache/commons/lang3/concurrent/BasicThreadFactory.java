/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.lang3.builder.Builder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicThreadFactory
/*     */   implements ThreadFactory
/*     */ {
/*     */   private final AtomicLong threadCounter;
/*     */   private final ThreadFactory wrappedFactory;
/*     */   private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
/*     */   private final String namingPattern;
/*     */   private final Integer priority;
/*     */   private final Boolean daemonFlag;
/*     */   
/*     */   private BasicThreadFactory(Builder paramBuilder)
/*     */   {
/* 116 */     if (paramBuilder.wrappedFactory == null) {
/* 117 */       this.wrappedFactory = Executors.defaultThreadFactory();
/*     */     } else {
/* 119 */       this.wrappedFactory = paramBuilder.wrappedFactory;
/*     */     }
/*     */     
/* 122 */     this.namingPattern = paramBuilder.namingPattern;
/* 123 */     this.priority = paramBuilder.priority;
/* 124 */     this.daemonFlag = paramBuilder.daemonFlag;
/* 125 */     this.uncaughtExceptionHandler = paramBuilder.exceptionHandler;
/*     */     
/* 127 */     this.threadCounter = new AtomicLong();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ThreadFactory getWrappedFactory()
/*     */   {
/* 139 */     return this.wrappedFactory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getNamingPattern()
/*     */   {
/* 149 */     return this.namingPattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Boolean getDaemonFlag()
/*     */   {
/* 161 */     return this.daemonFlag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Integer getPriority()
/*     */   {
/* 171 */     return this.priority;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler()
/*     */   {
/* 181 */     return this.uncaughtExceptionHandler;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getThreadCount()
/*     */   {
/* 192 */     return this.threadCounter.get();
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
/*     */   public Thread newThread(Runnable paramRunnable)
/*     */   {
/* 205 */     Thread localThread = getWrappedFactory().newThread(paramRunnable);
/* 206 */     initializeThread(localThread);
/*     */     
/* 208 */     return localThread;
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
/*     */   private void initializeThread(Thread paramThread)
/*     */   {
/* 221 */     if (getNamingPattern() != null) {
/* 222 */       Long localLong = Long.valueOf(this.threadCounter.incrementAndGet());
/* 223 */       paramThread.setName(String.format(getNamingPattern(), new Object[] { localLong }));
/*     */     }
/*     */     
/* 226 */     if (getUncaughtExceptionHandler() != null) {
/* 227 */       paramThread.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
/*     */     }
/*     */     
/* 230 */     if (getPriority() != null) {
/* 231 */       paramThread.setPriority(getPriority().intValue());
/*     */     }
/*     */     
/* 234 */     if (getDaemonFlag() != null) {
/* 235 */       paramThread.setDaemon(getDaemonFlag().booleanValue());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Builder
/*     */     implements Builder<BasicThreadFactory>
/*     */   {
/*     */     private ThreadFactory wrappedFactory;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Thread.UncaughtExceptionHandler exceptionHandler;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private String namingPattern;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Integer priority;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Boolean daemonFlag;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder wrappedFactory(ThreadFactory paramThreadFactory)
/*     */     {
/* 283 */       if (paramThreadFactory == null) {
/* 284 */         throw new NullPointerException("Wrapped ThreadFactory must not be null!");
/*     */       }
/*     */       
/*     */ 
/* 288 */       this.wrappedFactory = paramThreadFactory;
/* 289 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder namingPattern(String paramString)
/*     */     {
/* 301 */       if (paramString == null) {
/* 302 */         throw new NullPointerException("Naming pattern must not be null!");
/*     */       }
/*     */       
/*     */ 
/* 306 */       this.namingPattern = paramString;
/* 307 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder daemon(boolean paramBoolean)
/*     */     {
/* 319 */       this.daemonFlag = Boolean.valueOf(paramBoolean);
/* 320 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder priority(int paramInt)
/*     */     {
/* 331 */       this.priority = Integer.valueOf(paramInt);
/* 332 */       return this;
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
/*     */     public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
/*     */     {
/* 346 */       if (paramUncaughtExceptionHandler == null) {
/* 347 */         throw new NullPointerException("Uncaught exception handler must not be null!");
/*     */       }
/*     */       
/*     */ 
/* 351 */       this.exceptionHandler = paramUncaughtExceptionHandler;
/* 352 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void reset()
/*     */     {
/* 362 */       this.wrappedFactory = null;
/* 363 */       this.exceptionHandler = null;
/* 364 */       this.namingPattern = null;
/* 365 */       this.priority = null;
/* 366 */       this.daemonFlag = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public BasicThreadFactory build()
/*     */     {
/* 378 */       BasicThreadFactory localBasicThreadFactory = new BasicThreadFactory(this, null);
/* 379 */       reset();
/* 380 */       return localBasicThreadFactory;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\concurrent\BasicThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */