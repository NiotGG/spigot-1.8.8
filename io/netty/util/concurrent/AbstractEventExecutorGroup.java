/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractEventExecutorGroup
/*     */   implements EventExecutorGroup
/*     */ {
/*     */   public Future<?> submit(Runnable paramRunnable)
/*     */   {
/*  34 */     return next().submit(paramRunnable);
/*     */   }
/*     */   
/*     */   public <T> Future<T> submit(Runnable paramRunnable, T paramT)
/*     */   {
/*  39 */     return next().submit(paramRunnable, paramT);
/*     */   }
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> paramCallable)
/*     */   {
/*  44 */     return next().submit(paramCallable);
/*     */   }
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  49 */     return next().schedule(paramRunnable, paramLong, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/*  54 */     return next().schedule(paramCallable, paramLong, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/*  59 */     return next().scheduleAtFixedRate(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/*  64 */     return next().scheduleWithFixedDelay(paramRunnable, paramLong1, paramLong2, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully()
/*     */   {
/*  69 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public abstract void shutdown();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public List<Runnable> shutdownNow()
/*     */   {
/*  85 */     shutdown();
/*  86 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection)
/*     */     throws InterruptedException
/*     */   {
/*  92 */     return next().invokeAll(paramCollection);
/*     */   }
/*     */   
/*     */   public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/*  98 */     return next().invokeAll(paramCollection, paramLong, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection) throws InterruptedException, ExecutionException
/*     */   {
/* 103 */     return (T)next().invokeAny(paramCollection);
/*     */   }
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> paramCollection, long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException, ExecutionException, TimeoutException
/*     */   {
/* 109 */     return (T)next().invokeAny(paramCollection, paramLong, paramTimeUnit);
/*     */   }
/*     */   
/*     */   public void execute(Runnable paramRunnable)
/*     */   {
/* 114 */     next().execute(paramRunnable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\AbstractEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */