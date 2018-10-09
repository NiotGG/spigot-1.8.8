/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public abstract class MultithreadEventExecutorGroup
/*     */   extends AbstractEventExecutorGroup
/*     */ {
/*     */   private final EventExecutor[] children;
/*  33 */   private final AtomicInteger childIndex = new AtomicInteger();
/*  34 */   private final AtomicInteger terminatedChildren = new AtomicInteger();
/*  35 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*     */   
/*     */ 
/*     */ 
/*     */   private final EventExecutorChooser chooser;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected MultithreadEventExecutorGroup(int paramInt, ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*     */   {
/*  46 */     if (paramInt <= 0) {
/*  47 */       throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[] { Integer.valueOf(paramInt) }));
/*     */     }
/*     */     
/*  50 */     if (paramThreadFactory == null) {
/*  51 */       paramThreadFactory = newDefaultThreadFactory();
/*     */     }
/*     */     
/*  54 */     this.children = new SingleThreadEventExecutor[paramInt];
/*  55 */     if (isPowerOfTwo(this.children.length)) {
/*  56 */       this.chooser = new PowerOfTwoEventExecutorChooser(null);
/*     */     } else {
/*  58 */       this.chooser = new GenericEventExecutorChooser(null);
/*     */     }
/*     */     
/*  61 */     for (int i = 0; i < paramInt; i++) {
/*  62 */       int j = 0;
/*     */       try {
/*  64 */         this.children[i] = newChild(paramThreadFactory, paramVarArgs);
/*  65 */         j = 1;
/*     */       } catch (Exception localException) { int k;
/*     */         EventExecutor localEventExecutor1;
/*  68 */         throw new IllegalStateException("failed to create a child event loop", localException);
/*     */       } finally {
/*  70 */         if (j == 0) {
/*  71 */           for (int i1 = 0; i1 < i; i1++) {
/*  72 */             this.children[i1].shutdownGracefully();
/*     */           }
/*     */           
/*  75 */           for (i1 = 0; i1 < i; i1++) {
/*  76 */             EventExecutor localEventExecutor3 = this.children[i1];
/*     */             try {
/*  78 */               while (!localEventExecutor3.isTerminated()) {
/*  79 */                 localEventExecutor3.awaitTermination(2147483647L, TimeUnit.SECONDS);
/*     */               }
/*     */             } catch (InterruptedException localInterruptedException2) {
/*  82 */               Thread.currentThread().interrupt();
/*  83 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  90 */     FutureListener local1 = new FutureListener()
/*     */     {
/*     */       public void operationComplete(Future<Object> paramAnonymousFuture) throws Exception {
/*  93 */         if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
/*  94 */           MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
/*     */         }
/*     */       }
/*     */     };
/*     */     
/*  99 */     for (EventExecutor localEventExecutor2 : this.children) {
/* 100 */       localEventExecutor2.terminationFuture().addListener(local1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected ThreadFactory newDefaultThreadFactory() {
/* 105 */     return new DefaultThreadFactory(getClass());
/*     */   }
/*     */   
/*     */   public EventExecutor next()
/*     */   {
/* 110 */     return this.chooser.next();
/*     */   }
/*     */   
/*     */   public Iterator<EventExecutor> iterator()
/*     */   {
/* 115 */     return children().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int executorCount()
/*     */   {
/* 123 */     return this.children.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Set<EventExecutor> children()
/*     */   {
/* 130 */     Set localSet = Collections.newSetFromMap(new LinkedHashMap());
/* 131 */     Collections.addAll(localSet, this.children);
/* 132 */     return localSet;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */   public Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
/*     */   {
/* 145 */     for (EventExecutor localEventExecutor : this.children) {
/* 146 */       localEventExecutor.shutdownGracefully(paramLong1, paramLong2, paramTimeUnit);
/*     */     }
/* 148 */     return terminationFuture();
/*     */   }
/*     */   
/*     */   public Future<?> terminationFuture()
/*     */   {
/* 153 */     return this.terminationFuture;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown()
/*     */   {
/* 159 */     for (EventExecutor localEventExecutor : this.children) {
/* 160 */       localEventExecutor.shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isShuttingDown()
/*     */   {
/* 166 */     for (EventExecutor localEventExecutor : this.children) {
/* 167 */       if (!localEventExecutor.isShuttingDown()) {
/* 168 */         return false;
/*     */       }
/*     */     }
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isShutdown()
/*     */   {
/* 176 */     for (EventExecutor localEventExecutor : this.children) {
/* 177 */       if (!localEventExecutor.isShutdown()) {
/* 178 */         return false;
/*     */       }
/*     */     }
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isTerminated()
/*     */   {
/* 186 */     for (EventExecutor localEventExecutor : this.children) {
/* 187 */       if (!localEventExecutor.isTerminated()) {
/* 188 */         return false;
/*     */       }
/*     */     }
/* 191 */     return true;
/*     */   }
/*     */   
/*     */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws InterruptedException
/*     */   {
/* 197 */     long l1 = System.nanoTime() + paramTimeUnit.toNanos(paramLong);
/* 198 */     for (EventExecutor localEventExecutor : this.children) {
/*     */       for (;;) {
/* 200 */         long l2 = l1 - System.nanoTime();
/* 201 */         if (l2 <= 0L) {
/*     */           break label84;
/*     */         }
/* 204 */         if (localEventExecutor.awaitTermination(l2, TimeUnit.NANOSECONDS))
/*     */           break;
/*     */       }
/*     */     }
/*     */     label84:
/* 209 */     return isTerminated();
/*     */   }
/*     */   
/*     */   private static boolean isPowerOfTwo(int paramInt) {
/* 213 */     return (paramInt & -paramInt) == paramInt;
/*     */   }
/*     */   
/*     */   private static abstract interface EventExecutorChooser {
/*     */     public abstract EventExecutor next();
/*     */   }
/*     */   
/*     */   private final class PowerOfTwoEventExecutorChooser implements MultithreadEventExecutorGroup.EventExecutorChooser {
/*     */     private PowerOfTwoEventExecutorChooser() {}
/*     */     
/* 223 */     public EventExecutor next() { return MultithreadEventExecutorGroup.this.children[(MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() & MultithreadEventExecutorGroup.this.children.length - 1)]; }
/*     */   }
/*     */   
/*     */   private final class GenericEventExecutorChooser implements MultithreadEventExecutorGroup.EventExecutorChooser {
/*     */     private GenericEventExecutorChooser() {}
/*     */     
/*     */     public EventExecutor next() {
/* 230 */       return MultithreadEventExecutorGroup.this.children[Math.abs(MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() % MultithreadEventExecutorGroup.this.children.length)];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\MultithreadEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */