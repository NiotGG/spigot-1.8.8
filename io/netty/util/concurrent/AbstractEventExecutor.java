/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.AbstractExecutorService;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractEventExecutor
/*     */   extends AbstractExecutorService
/*     */   implements EventExecutor
/*     */ {
/*     */   public EventExecutor next()
/*     */   {
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   public boolean inEventLoop()
/*     */   {
/*  39 */     return inEventLoop(Thread.currentThread());
/*     */   }
/*     */   
/*     */   public Iterator<EventExecutor> iterator()
/*     */   {
/*  44 */     return new EventExecutorIterator(null);
/*     */   }
/*     */   
/*     */   public Future<?> shutdownGracefully()
/*     */   {
/*  49 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
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
/*  65 */     shutdown();
/*  66 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public <V> Promise<V> newPromise()
/*     */   {
/*  71 */     return new DefaultPromise(this);
/*     */   }
/*     */   
/*     */   public <V> ProgressivePromise<V> newProgressivePromise()
/*     */   {
/*  76 */     return new DefaultProgressivePromise(this);
/*     */   }
/*     */   
/*     */   public <V> Future<V> newSucceededFuture(V paramV)
/*     */   {
/*  81 */     return new SucceededFuture(this, paramV);
/*     */   }
/*     */   
/*     */   public <V> Future<V> newFailedFuture(Throwable paramThrowable)
/*     */   {
/*  86 */     return new FailedFuture(this, paramThrowable);
/*     */   }
/*     */   
/*     */   public Future<?> submit(Runnable paramRunnable)
/*     */   {
/*  91 */     return (Future)super.submit(paramRunnable);
/*     */   }
/*     */   
/*     */   public <T> Future<T> submit(Runnable paramRunnable, T paramT)
/*     */   {
/*  96 */     return (Future)super.submit(paramRunnable, paramT);
/*     */   }
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> paramCallable)
/*     */   {
/* 101 */     return (Future)super.submit(paramCallable);
/*     */   }
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
/*     */   {
/* 106 */     return new PromiseTask(this, paramRunnable, paramT);
/*     */   }
/*     */   
/*     */   protected final <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
/*     */   {
/* 111 */     return new PromiseTask(this, paramCallable);
/*     */   }
/*     */   
/*     */ 
/*     */   public ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 122 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 132 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class EventExecutorIterator implements Iterator<EventExecutor> {
/*     */     private boolean nextCalled;
/*     */     
/*     */     private EventExecutorIterator() {}
/*     */     
/* 140 */     public boolean hasNext() { return !this.nextCalled; }
/*     */     
/*     */ 
/*     */     public EventExecutor next()
/*     */     {
/* 145 */       if (!hasNext()) {
/* 146 */         throw new NoSuchElementException();
/*     */       }
/* 148 */       this.nextCalled = true;
/* 149 */       return AbstractEventExecutor.this;
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 154 */       throw new UnsupportedOperationException("read-only");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\AbstractEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */