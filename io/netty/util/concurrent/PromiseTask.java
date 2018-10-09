/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PromiseTask<V>
/*     */   extends DefaultPromise<V>
/*     */   implements RunnableFuture<V>
/*     */ {
/*     */   protected final Callable<V> task;
/*     */   
/*     */   static <T> Callable<T> toCallable(Runnable paramRunnable, T paramT)
/*     */   {
/*  24 */     return new RunnableAdapter(paramRunnable, paramT);
/*     */   }
/*     */   
/*     */   private static final class RunnableAdapter<T> implements Callable<T> {
/*     */     final Runnable task;
/*     */     final T result;
/*     */     
/*     */     RunnableAdapter(Runnable paramRunnable, T paramT) {
/*  32 */       this.task = paramRunnable;
/*  33 */       this.result = paramT;
/*     */     }
/*     */     
/*     */     public T call()
/*     */     {
/*  38 */       this.task.run();
/*  39 */       return (T)this.result;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/*  44 */       return "Callable(task: " + this.task + ", result: " + this.result + ')';
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   PromiseTask(EventExecutor paramEventExecutor, Runnable paramRunnable, V paramV)
/*     */   {
/*  51 */     this(paramEventExecutor, toCallable(paramRunnable, paramV));
/*     */   }
/*     */   
/*     */   PromiseTask(EventExecutor paramEventExecutor, Callable<V> paramCallable) {
/*  55 */     super(paramEventExecutor);
/*  56 */     this.task = paramCallable;
/*     */   }
/*     */   
/*     */   public final int hashCode()
/*     */   {
/*  61 */     return System.identityHashCode(this);
/*     */   }
/*     */   
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/*  66 */     return this == paramObject;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/*  72 */       if (setUncancellableInternal()) {
/*  73 */         Object localObject = this.task.call();
/*  74 */         setSuccessInternal(localObject);
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/*  77 */       setFailureInternal(localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final Promise<V> setFailure(Throwable paramThrowable)
/*     */   {
/*  83 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final Promise<V> setFailureInternal(Throwable paramThrowable) {
/*  87 */     super.setFailure(paramThrowable);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public final boolean tryFailure(Throwable paramThrowable)
/*     */   {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   protected final boolean tryFailureInternal(Throwable paramThrowable) {
/*  97 */     return super.tryFailure(paramThrowable);
/*     */   }
/*     */   
/*     */   public final Promise<V> setSuccess(V paramV)
/*     */   {
/* 102 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final Promise<V> setSuccessInternal(V paramV) {
/* 106 */     super.setSuccess(paramV);
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public final boolean trySuccess(V paramV)
/*     */   {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   protected final boolean trySuccessInternal(V paramV) {
/* 116 */     return super.trySuccess(paramV);
/*     */   }
/*     */   
/*     */   public final boolean setUncancellable()
/*     */   {
/* 121 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   protected final boolean setUncancellableInternal() {
/* 125 */     return super.setUncancellable();
/*     */   }
/*     */   
/*     */   protected StringBuilder toStringBuilder()
/*     */   {
/* 130 */     StringBuilder localStringBuilder = super.toStringBuilder();
/* 131 */     localStringBuilder.setCharAt(localStringBuilder.length() - 1, ',');
/* 132 */     localStringBuilder.append(" task: ");
/* 133 */     localStringBuilder.append(this.task);
/* 134 */     localStringBuilder.append(')');
/* 135 */     return localStringBuilder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\PromiseTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */