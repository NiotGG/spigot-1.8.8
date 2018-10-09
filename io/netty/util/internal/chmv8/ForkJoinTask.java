/*      */ package io.netty.util.internal.chmv8;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Field;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.RunnableFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.TimeoutException;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ForkJoinTask<V>
/*      */   implements Future<V>, Serializable
/*      */ {
/*      */   volatile int status;
/*      */   static final int DONE_MASK = -268435456;
/*      */   static final int NORMAL = -268435456;
/*      */   static final int CANCELLED = -1073741824;
/*      */   static final int EXCEPTIONAL = Integer.MIN_VALUE;
/*      */   static final int SIGNAL = 65536;
/*      */   static final int SMASK = 65535;
/*      */   private static final ExceptionNode[] exceptionTable;
/*      */   
/*      */   private int setCompletion(int paramInt)
/*      */   {
/*      */     int i;
/*      */     do
/*      */     {
/*  259 */       if ((i = this.status) < 0)
/*  260 */         return i;
/*  261 */     } while (!U.compareAndSwapInt(this, STATUS, i, i | paramInt));
/*  262 */     if (i >>> 16 != 0)
/*  263 */       synchronized (this) { notifyAll(); }
/*  264 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int doExec()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  278 */     if ((i = this.status) >= 0) {
/*      */       boolean bool;
/*  280 */       try { bool = exec();
/*      */       } catch (Throwable localThrowable) {
/*  282 */         return setExceptionalCompletion(localThrowable);
/*      */       }
/*  284 */       if (bool)
/*  285 */         i = setCompletion(-268435456);
/*      */     }
/*  287 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean trySetSignal()
/*      */   {
/*  298 */     int i = this.status;
/*  299 */     return (i >= 0) && (U.compareAndSwapInt(this, STATUS, i, i | 0x10000));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int externalAwaitDone()
/*      */   {
/*  308 */     ForkJoinPool localForkJoinPool = ForkJoinPool.common;
/*  309 */     int i; if ((i = this.status) >= 0) {
/*  310 */       if (localForkJoinPool != null) {
/*  311 */         if ((this instanceof CountedCompleter)) {
/*  312 */           i = localForkJoinPool.externalHelpComplete((CountedCompleter)this);
/*  313 */         } else if (localForkJoinPool.tryExternalUnpush(this))
/*  314 */           i = doExec();
/*      */       }
/*  316 */       if ((i >= 0) && ((i = this.status) >= 0)) {
/*  317 */         int j = 0;
/*      */         do {
/*  319 */           if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000)) {
/*  320 */             synchronized (this) {
/*  321 */               if (this.status >= 0) {
/*      */                 try {
/*  323 */                   wait();
/*      */                 } catch (InterruptedException localInterruptedException) {
/*  325 */                   j = 1;
/*      */                 }
/*      */                 
/*      */               } else
/*  329 */                 notifyAll();
/*      */             }
/*      */           }
/*  332 */         } while ((i = this.status) >= 0);
/*  333 */         if (j != 0)
/*  334 */           Thread.currentThread().interrupt();
/*      */       }
/*      */     }
/*  337 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int externalInterruptibleAwaitDone()
/*      */     throws InterruptedException
/*      */   {
/*  345 */     ForkJoinPool localForkJoinPool = ForkJoinPool.common;
/*  346 */     if (Thread.interrupted())
/*  347 */       throw new InterruptedException();
/*  348 */     int i; if (((i = this.status) >= 0) && (localForkJoinPool != null)) {
/*  349 */       if ((this instanceof CountedCompleter)) {
/*  350 */         localForkJoinPool.externalHelpComplete((CountedCompleter)this);
/*  351 */       } else if (localForkJoinPool.tryExternalUnpush(this))
/*  352 */         doExec();
/*      */     }
/*  354 */     while ((i = this.status) >= 0) {
/*  355 */       if (U.compareAndSwapInt(this, STATUS, i, i | 0x10000)) {
/*  356 */         synchronized (this) {
/*  357 */           if (this.status >= 0) {
/*  358 */             wait();
/*      */           } else
/*  360 */             notifyAll();
/*      */         }
/*      */       }
/*      */     }
/*  364 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   private int doJoin()
/*      */   {
/*      */     int i;
/*      */     
/*      */     Thread localThread;
/*      */     
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*  377 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? localForkJoinWorkerThread.pool.awaitJoin(localWorkQueue, this) : ((localWorkQueue = (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).workQueue).tryUnpush(this)) && ((i = doExec()) < 0) ? i : (i = this.status) < 0 ? i : externalAwaitDone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int doInvoke()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*  392 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool.awaitJoin(localForkJoinWorkerThread.workQueue, this) : (i = doExec()) < 0 ? i : externalAwaitDone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class ExceptionNode
/*      */     extends WeakReference<ForkJoinTask<?>>
/*      */   {
/*      */     final Throwable ex;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ExceptionNode next;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     final long thrower;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ExceptionNode(ForkJoinTask<?> paramForkJoinTask, Throwable paramThrowable, ExceptionNode paramExceptionNode)
/*      */     {
/*  435 */       super(ForkJoinTask.exceptionTableRefQueue);
/*  436 */       this.ex = paramThrowable;
/*  437 */       this.next = paramExceptionNode;
/*  438 */       this.thrower = Thread.currentThread().getId();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final int recordExceptionalCompletion(Throwable paramThrowable)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*  449 */     if ((i = this.status) >= 0) {
/*  450 */       int j = System.identityHashCode(this);
/*  451 */       ReentrantLock localReentrantLock = exceptionTableLock;
/*  452 */       localReentrantLock.lock();
/*      */       try {
/*  454 */         expungeStaleExceptions();
/*  455 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  456 */         int k = j & arrayOfExceptionNode.length - 1;
/*  457 */         for (ExceptionNode localExceptionNode = arrayOfExceptionNode[k];; localExceptionNode = localExceptionNode.next) {
/*  458 */           if (localExceptionNode == null) {
/*  459 */             arrayOfExceptionNode[k] = new ExceptionNode(this, paramThrowable, arrayOfExceptionNode[k]);
/*      */           }
/*      */           else
/*  462 */             if (localExceptionNode.get() == this)
/*      */               break;
/*      */         }
/*      */       } finally {
/*  466 */         localReentrantLock.unlock();
/*      */       }
/*  468 */       i = setCompletion(Integer.MIN_VALUE);
/*      */     }
/*  470 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int setExceptionalCompletion(Throwable paramThrowable)
/*      */   {
/*  479 */     int i = recordExceptionalCompletion(paramThrowable);
/*  480 */     if ((i & 0xF0000000) == Integer.MIN_VALUE)
/*  481 */       internalPropagateException(paramThrowable);
/*  482 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final void cancelIgnoringExceptions(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/*  498 */     if ((paramForkJoinTask != null) && (paramForkJoinTask.status >= 0)) {
/*      */       try {
/*  500 */         paramForkJoinTask.cancel(false);
/*      */       }
/*      */       catch (Throwable localThrowable) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void clearExceptionalCompletion()
/*      */   {
/*  510 */     int i = System.identityHashCode(this);
/*  511 */     ReentrantLock localReentrantLock = exceptionTableLock;
/*  512 */     localReentrantLock.lock();
/*      */     try {
/*  514 */       ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  515 */       int j = i & arrayOfExceptionNode.length - 1;
/*  516 */       Object localObject1 = arrayOfExceptionNode[j];
/*  517 */       Object localObject2 = null;
/*  518 */       while (localObject1 != null) {
/*  519 */         ExceptionNode localExceptionNode = ((ExceptionNode)localObject1).next;
/*  520 */         if (((ExceptionNode)localObject1).get() == this) {
/*  521 */           if (localObject2 == null) {
/*  522 */             arrayOfExceptionNode[j] = localExceptionNode; break;
/*      */           }
/*  524 */           ((ExceptionNode)localObject2).next = localExceptionNode;
/*  525 */           break;
/*      */         }
/*  527 */         localObject2 = localObject1;
/*  528 */         localObject1 = localExceptionNode;
/*      */       }
/*  530 */       expungeStaleExceptions();
/*  531 */       this.status = 0;
/*      */     } finally {
/*  533 */       localReentrantLock.unlock();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Throwable getThrowableException()
/*      */   {
/*  552 */     if ((this.status & 0xF0000000) != Integer.MIN_VALUE)
/*  553 */       return null;
/*  554 */     int i = System.identityHashCode(this);
/*      */     
/*  556 */     ReentrantLock localReentrantLock = exceptionTableLock;
/*  557 */     localReentrantLock.lock();
/*      */     Object localObject1;
/*  559 */     ExceptionNode localExceptionNode; try { expungeStaleExceptions();
/*  560 */       localObject1 = exceptionTable;
/*  561 */       localExceptionNode = localObject1[(i & localObject1.length - 1)];
/*  562 */       while ((localExceptionNode != null) && (localExceptionNode.get() != this))
/*  563 */         localExceptionNode = localExceptionNode.next;
/*      */     } finally {
/*  565 */       localReentrantLock.unlock();
/*      */     }
/*      */     
/*  568 */     if ((localExceptionNode == null) || ((localObject1 = localExceptionNode.ex) == null)) {
/*  569 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  591 */     return (Throwable)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   private static void expungeStaleExceptions()
/*      */   {
/*      */     Reference localReference;
/*  598 */     while ((localReference = exceptionTableRefQueue.poll()) != null) {
/*  599 */       if ((localReference instanceof ExceptionNode)) {
/*  600 */         ForkJoinTask localForkJoinTask = (ForkJoinTask)((ExceptionNode)localReference).get();
/*  601 */         ExceptionNode[] arrayOfExceptionNode = exceptionTable;
/*  602 */         int i = System.identityHashCode(localForkJoinTask) & arrayOfExceptionNode.length - 1;
/*  603 */         Object localObject1 = arrayOfExceptionNode[i];
/*  604 */         Object localObject2 = null;
/*  605 */         while (localObject1 != null) {
/*  606 */           ExceptionNode localExceptionNode = ((ExceptionNode)localObject1).next;
/*  607 */           if (localObject1 == localReference) {
/*  608 */             if (localObject2 == null) {
/*  609 */               arrayOfExceptionNode[i] = localExceptionNode; break;
/*      */             }
/*  611 */             ((ExceptionNode)localObject2).next = localExceptionNode;
/*  612 */             break;
/*      */           }
/*  614 */           localObject2 = localObject1;
/*  615 */           localObject1 = localExceptionNode;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final void helpExpungeStaleExceptions()
/*      */   {
/*  626 */     ReentrantLock localReentrantLock = exceptionTableLock;
/*  627 */     if (localReentrantLock.tryLock()) {
/*      */       try {
/*  629 */         expungeStaleExceptions();
/*      */       } finally {
/*  631 */         localReentrantLock.unlock();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void rethrow(Throwable paramThrowable)
/*      */   {
/*  640 */     if (paramThrowable != null) {
/*  641 */       uncheckedThrow(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static <T extends Throwable> void uncheckedThrow(Throwable paramThrowable)
/*      */     throws Throwable
/*      */   {
/*  651 */     throw paramThrowable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void reportException(int paramInt)
/*      */   {
/*  658 */     if (paramInt == -1073741824)
/*  659 */       throw new CancellationException();
/*  660 */     if (paramInt == Integer.MIN_VALUE) {
/*  661 */       rethrow(getThrowableException());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final ForkJoinTask<V> fork()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  683 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/*  684 */       ((ForkJoinWorkerThread)localThread).workQueue.push(this);
/*      */     } else
/*  686 */       ForkJoinPool.common.externalPush(this);
/*  687 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V join()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  703 */     if ((i = doJoin() & 0xF0000000) != -268435456)
/*  704 */       reportException(i);
/*  705 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V invoke()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*  718 */     if ((i = doInvoke() & 0xF0000000) != -268435456)
/*  719 */       reportException(i);
/*  720 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void invokeAll(ForkJoinTask<?> paramForkJoinTask1, ForkJoinTask<?> paramForkJoinTask2)
/*      */   {
/*  742 */     paramForkJoinTask2.fork();
/*  743 */     int i; if ((i = paramForkJoinTask1.doInvoke() & 0xF0000000) != -268435456)
/*  744 */       paramForkJoinTask1.reportException(i);
/*  745 */     int j; if ((j = paramForkJoinTask2.doJoin() & 0xF0000000) != -268435456) {
/*  746 */       paramForkJoinTask2.reportException(j);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void invokeAll(ForkJoinTask<?>... paramVarArgs)
/*      */   {
/*  765 */     Object localObject = null;
/*  766 */     int i = paramVarArgs.length - 1;
/*  767 */     ForkJoinTask<?> localForkJoinTask; for (int j = i; j >= 0; j--) {
/*  768 */       localForkJoinTask = paramVarArgs[j];
/*  769 */       if (localForkJoinTask == null) {
/*  770 */         if (localObject == null) {
/*  771 */           localObject = new NullPointerException();
/*      */         }
/*  773 */       } else if (j != 0) {
/*  774 */         localForkJoinTask.fork();
/*  775 */       } else if ((localForkJoinTask.doInvoke() < -268435456) && (localObject == null))
/*  776 */         localObject = localForkJoinTask.getException();
/*      */     }
/*  778 */     for (j = 1; j <= i; j++) {
/*  779 */       localForkJoinTask = paramVarArgs[j];
/*  780 */       if (localForkJoinTask != null) {
/*  781 */         if (localObject != null) {
/*  782 */           localForkJoinTask.cancel(false);
/*  783 */         } else if (localForkJoinTask.doJoin() < -268435456)
/*  784 */           localObject = localForkJoinTask.getException();
/*      */       }
/*      */     }
/*  787 */     if (localObject != null) {
/*  788 */       rethrow((Throwable)localObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> paramCollection)
/*      */   {
/*  809 */     if ((!(paramCollection instanceof RandomAccess)) || (!(paramCollection instanceof List))) {
/*  810 */       invokeAll((ForkJoinTask[])paramCollection.toArray(new ForkJoinTask[paramCollection.size()]));
/*  811 */       return paramCollection;
/*      */     }
/*      */     
/*  814 */     List localList = (List)paramCollection;
/*      */     
/*  816 */     Object localObject = null;
/*  817 */     int i = localList.size() - 1;
/*  818 */     ForkJoinTask localForkJoinTask; for (int j = i; j >= 0; j--) {
/*  819 */       localForkJoinTask = (ForkJoinTask)localList.get(j);
/*  820 */       if (localForkJoinTask == null) {
/*  821 */         if (localObject == null) {
/*  822 */           localObject = new NullPointerException();
/*      */         }
/*  824 */       } else if (j != 0) {
/*  825 */         localForkJoinTask.fork();
/*  826 */       } else if ((localForkJoinTask.doInvoke() < -268435456) && (localObject == null))
/*  827 */         localObject = localForkJoinTask.getException();
/*      */     }
/*  829 */     for (j = 1; j <= i; j++) {
/*  830 */       localForkJoinTask = (ForkJoinTask)localList.get(j);
/*  831 */       if (localForkJoinTask != null) {
/*  832 */         if (localObject != null) {
/*  833 */           localForkJoinTask.cancel(false);
/*  834 */         } else if (localForkJoinTask.doJoin() < -268435456)
/*  835 */           localObject = localForkJoinTask.getException();
/*      */       }
/*      */     }
/*  838 */     if (localObject != null)
/*  839 */       rethrow((Throwable)localObject);
/*  840 */     return paramCollection;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean cancel(boolean paramBoolean)
/*      */   {
/*  871 */     return (setCompletion(-1073741824) & 0xF0000000) == -1073741824;
/*      */   }
/*      */   
/*      */   public final boolean isDone() {
/*  875 */     return this.status < 0;
/*      */   }
/*      */   
/*      */   public final boolean isCancelled() {
/*  879 */     return (this.status & 0xF0000000) == -1073741824;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isCompletedAbnormally()
/*      */   {
/*  888 */     return this.status < -268435456;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean isCompletedNormally()
/*      */   {
/*  899 */     return (this.status & 0xF0000000) == -268435456;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final Throwable getException()
/*      */   {
/*  910 */     int i = this.status & 0xF0000000;
/*  911 */     return i == -1073741824 ? new CancellationException() : i >= -268435456 ? null : getThrowableException();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void completeExceptionally(Throwable paramThrowable)
/*      */   {
/*  931 */     setExceptionalCompletion(((paramThrowable instanceof RuntimeException)) || ((paramThrowable instanceof Error)) ? paramThrowable : new RuntimeException(paramThrowable));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void complete(V paramV)
/*      */   {
/*      */     try
/*      */     {
/*  951 */       setRawResult(paramV);
/*      */     } catch (Throwable localThrowable) {
/*  953 */       setExceptionalCompletion(localThrowable);
/*  954 */       return;
/*      */     }
/*  956 */     setCompletion(-268435456);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyComplete()
/*      */   {
/*  968 */     setCompletion(-268435456);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V get()
/*      */     throws InterruptedException, ExecutionException
/*      */   {
/*  983 */     int i = (Thread.currentThread() instanceof ForkJoinWorkerThread) ? doJoin() : externalInterruptibleAwaitDone();
/*      */     
/*      */ 
/*  986 */     if ((i &= 0xF0000000) == -1073741824)
/*  987 */       throw new CancellationException();
/*  988 */     Throwable localThrowable; if ((i == Integer.MIN_VALUE) && ((localThrowable = getThrowableException()) != null))
/*  989 */       throw new ExecutionException(localThrowable);
/*  990 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final V get(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException, ExecutionException, TimeoutException
/*      */   {
/* 1009 */     if (Thread.interrupted()) {
/* 1010 */       throw new InterruptedException();
/*      */     }
/*      */     
/* 1013 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*      */     int i;
/* 1015 */     if (((i = this.status) >= 0) && (l1 > 0L)) {
/* 1016 */       long l2 = System.nanoTime() + l1;
/* 1017 */       ForkJoinPool localForkJoinPool1 = null;
/* 1018 */       ForkJoinPool.WorkQueue localWorkQueue = null;
/* 1019 */       Thread localThread = Thread.currentThread();
/* 1020 */       if ((localThread instanceof ForkJoinWorkerThread)) {
/* 1021 */         ForkJoinWorkerThread localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread;
/* 1022 */         localForkJoinPool1 = localForkJoinWorkerThread.pool;
/* 1023 */         localWorkQueue = localForkJoinWorkerThread.workQueue;
/* 1024 */         localForkJoinPool1.helpJoinOnce(localWorkQueue, this);
/*      */       } else { ForkJoinPool localForkJoinPool2;
/* 1026 */         if ((localForkJoinPool2 = ForkJoinPool.common) != null)
/* 1027 */           if ((this instanceof CountedCompleter)) {
/* 1028 */             localForkJoinPool2.externalHelpComplete((CountedCompleter)this);
/* 1029 */           } else if (localForkJoinPool2.tryExternalUnpush(this))
/* 1030 */             doExec();
/*      */       }
/* 1032 */       int j = 0;
/* 1033 */       int k = 0;
/*      */       try {
/* 1035 */         while ((i = this.status) >= 0) {
/* 1036 */           if ((localWorkQueue != null) && (localWorkQueue.qlock < 0)) {
/* 1037 */             cancelIgnoringExceptions(this);
/* 1038 */           } else if (j == 0) {
/* 1039 */             if ((localForkJoinPool1 == null) || (localForkJoinPool1.tryCompensate(localForkJoinPool1.ctl)))
/* 1040 */               j = 1;
/*      */           } else {
/*      */             long l3;
/* 1043 */             if (((l3 = TimeUnit.NANOSECONDS.toMillis(l1)) > 0L) && (U.compareAndSwapInt(this, STATUS, i, i | 0x10000)))
/*      */             {
/* 1045 */               synchronized (this) {
/* 1046 */                 if (this.status >= 0) {
/*      */                   try {
/* 1048 */                     wait(l3);
/*      */                   } catch (InterruptedException localInterruptedException) {
/* 1050 */                     if (localForkJoinPool1 == null) {
/* 1051 */                       k = 1;
/*      */                     }
/*      */                   }
/*      */                 } else
/* 1055 */                   notifyAll();
/*      */               }
/*      */             }
/* 1058 */             if (((i = this.status) >= 0) && (k == 0)) if ((l1 = l2 - System.nanoTime()) <= 0L) {
/*      */                 break;
/*      */               }
/*      */           }
/*      */         }
/*      */       } finally {
/* 1064 */         if ((localForkJoinPool1 != null) && (j != 0))
/* 1065 */           localForkJoinPool1.incrementActiveCount();
/*      */       }
/* 1067 */       if (k != 0)
/* 1068 */         throw new InterruptedException();
/*      */     }
/* 1070 */     if ((i &= 0xF0000000) != -268435456)
/*      */     {
/* 1072 */       if (i == -1073741824)
/* 1073 */         throw new CancellationException();
/* 1074 */       if (i != Integer.MIN_VALUE)
/* 1075 */         throw new TimeoutException();
/* 1076 */       Throwable localThrowable; if ((localThrowable = getThrowableException()) != null)
/* 1077 */         throw new ExecutionException(localThrowable);
/*      */     }
/* 1079 */     return (V)getRawResult();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyJoin()
/*      */   {
/* 1089 */     doJoin();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final void quietlyInvoke()
/*      */   {
/* 1098 */     doInvoke();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void helpQuiesce()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/* 1110 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1111 */       ForkJoinWorkerThread localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread;
/* 1112 */       localForkJoinWorkerThread.pool.helpQuiescePool(localForkJoinWorkerThread.workQueue);
/*      */     }
/*      */     else {
/* 1115 */       ForkJoinPool.quiesceCommonPool();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void reinitialize()
/*      */   {
/* 1135 */     if ((this.status & 0xF0000000) == Integer.MIN_VALUE) {
/* 1136 */       clearExceptionalCompletion();
/*      */     } else {
/* 1138 */       this.status = 0;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ForkJoinPool getPool()
/*      */   {
/* 1149 */     Thread localThread = Thread.currentThread();
/* 1150 */     return (localThread instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).pool : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean inForkJoinPool()
/*      */   {
/* 1163 */     return Thread.currentThread() instanceof ForkJoinWorkerThread;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean tryUnfork()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1178 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).workQueue.tryUnpush(this) : ForkJoinPool.common.tryExternalUnpush(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getQueuedTaskCount()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*      */     
/*      */ 
/* 1193 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1194 */       localWorkQueue = ((ForkJoinWorkerThread)localThread).workQueue;
/*      */     } else
/* 1196 */       localWorkQueue = ForkJoinPool.commonSubmitterQueue();
/* 1197 */     return localWorkQueue == null ? 0 : localWorkQueue.queueSize();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getSurplusQueuedTaskCount()
/*      */   {
/* 1214 */     return ForkJoinPool.getSurplusQueuedTaskCount();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> peekNextLocalTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ForkJoinPool.WorkQueue localWorkQueue;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1269 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) {
/* 1270 */       localWorkQueue = ((ForkJoinWorkerThread)localThread).workQueue;
/*      */     } else
/* 1272 */       localWorkQueue = ForkJoinPool.commonSubmitterQueue();
/* 1273 */     return localWorkQueue == null ? null : localWorkQueue.peek();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> pollNextLocalTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1287 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? ((ForkJoinWorkerThread)localThread).workQueue.nextLocalTask() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static ForkJoinTask<?> pollTask()
/*      */   {
/*      */     Thread localThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1307 */     return ((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread) ? (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool.nextTaskFor(localForkJoinWorkerThread.workQueue) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final short getForkJoinTaskTag()
/*      */   {
/* 1321 */     return (short)this.status;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public final short setForkJoinTaskTag(short paramShort)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/* 1333 */     while (!U.compareAndSwapInt(this, STATUS, i = this.status, i & 0xFFFF0000 | paramShort & 0xFFFF)) {}
/*      */     
/* 1335 */     return (short)i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final boolean compareAndSetForkJoinTaskTag(short paramShort1, short paramShort2)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     do
/*      */     {
/* 1355 */       if ((short)(i = this.status) != paramShort1)
/* 1356 */         return false;
/* 1357 */     } while (!U.compareAndSwapInt(this, STATUS, i, i & 0xFFFF0000 | paramShort2 & 0xFFFF));
/*      */     
/* 1359 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   static final class AdaptedRunnable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Runnable runnable;
/*      */     T result;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnable(Runnable paramRunnable, T paramT)
/*      */     {
/* 1373 */       if (paramRunnable == null) throw new NullPointerException();
/* 1374 */       this.runnable = paramRunnable;
/* 1375 */       this.result = paramT; }
/*      */     
/* 1377 */     public final T getRawResult() { return (T)this.result; }
/* 1378 */     public final void setRawResult(T paramT) { this.result = paramT; }
/* 1379 */     public final boolean exec() { this.runnable.run();return true; }
/* 1380 */     public final void run() { invoke(); }
/*      */   }
/*      */   
/*      */   static final class AdaptedRunnableAction
/*      */     extends ForkJoinTask<Void> implements RunnableFuture<Void>
/*      */   {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     AdaptedRunnableAction(Runnable paramRunnable)
/*      */     {
/* 1391 */       if (paramRunnable == null) throw new NullPointerException();
/* 1392 */       this.runnable = paramRunnable; }
/*      */     
/* 1394 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/* 1396 */     public final boolean exec() { this.runnable.run();return true; }
/* 1397 */     public final void run() { invoke(); }
/*      */   }
/*      */   
/*      */   static final class RunnableExecuteAction extends ForkJoinTask<Void>
/*      */   {
/*      */     final Runnable runnable;
/*      */     private static final long serialVersionUID = 5232453952276885070L;
/*      */     
/*      */     RunnableExecuteAction(Runnable paramRunnable)
/*      */     {
/* 1407 */       if (paramRunnable == null) throw new NullPointerException();
/* 1408 */       this.runnable = paramRunnable; }
/*      */     
/* 1410 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/* 1412 */     public final boolean exec() { this.runnable.run();return true; }
/*      */     
/* 1414 */     void internalPropagateException(Throwable paramThrowable) { rethrow(paramThrowable); }
/*      */   }
/*      */   
/*      */   static final class AdaptedCallable<T>
/*      */     extends ForkJoinTask<T>
/*      */     implements RunnableFuture<T>
/*      */   {
/*      */     final Callable<? extends T> callable;
/*      */     T result;
/*      */     private static final long serialVersionUID = 2838392045355241008L;
/*      */     
/*      */     AdaptedCallable(Callable<? extends T> paramCallable)
/*      */     {
/* 1427 */       if (paramCallable == null) throw new NullPointerException();
/* 1428 */       this.callable = paramCallable; }
/*      */     
/* 1430 */     public final T getRawResult() { return (T)this.result; }
/* 1431 */     public final void setRawResult(T paramT) { this.result = paramT; }
/*      */     
/*      */     public final boolean exec() {
/* 1434 */       try { this.result = this.callable.call();
/* 1435 */         return true;
/*      */       } catch (Error localError) {
/* 1437 */         throw localError;
/*      */       } catch (RuntimeException localRuntimeException) {
/* 1439 */         throw localRuntimeException;
/*      */       } catch (Exception localException) {
/* 1441 */         throw new RuntimeException(localException);
/*      */       } }
/*      */     
/* 1444 */     public final void run() { invoke(); }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ForkJoinTask<?> adapt(Runnable paramRunnable)
/*      */   {
/* 1457 */     return new AdaptedRunnableAction(paramRunnable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> ForkJoinTask<T> adapt(Runnable paramRunnable, T paramT)
/*      */   {
/* 1470 */     return new AdaptedRunnable(paramRunnable, paramT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> ForkJoinTask<T> adapt(Callable<? extends T> paramCallable)
/*      */   {
/* 1483 */     return new AdaptedCallable(paramCallable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1498 */     paramObjectOutputStream.defaultWriteObject();
/* 1499 */     paramObjectOutputStream.writeObject(getException());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1507 */     paramObjectInputStream.defaultReadObject();
/* 1508 */     Object localObject = paramObjectInputStream.readObject();
/* 1509 */     if (localObject != null) {
/* 1510 */       setExceptionalCompletion((Throwable)localObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1518 */   private static final ReentrantLock exceptionTableLock = new ReentrantLock();
/* 1519 */   private static final ReferenceQueue<Object> exceptionTableRefQueue = new ReferenceQueue();
/* 1520 */   static { exceptionTable = new ExceptionNode[32];
/*      */     try {
/* 1522 */       U = getUnsafe();
/* 1523 */       Class localClass = ForkJoinTask.class;
/* 1524 */       STATUS = U.objectFieldOffset(localClass.getDeclaredField("status"));
/*      */     }
/*      */     catch (Exception localException) {
/* 1527 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static final int EXCEPTION_MAP_CAPACITY = 32;
/*      */   private static final long serialVersionUID = -7721805057305804111L;
/*      */   private static final Unsafe U;
/*      */   private static final long STATUS;
/*      */   private static Unsafe getUnsafe()
/*      */   {
/*      */     try
/*      */     {
/* 1540 */       return Unsafe.getUnsafe();
/*      */     } catch (SecurityException localSecurityException) {
/*      */       try {
/* 1543 */         (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */         {
/*      */           public Unsafe run() throws Exception {
/* 1546 */             Class localClass = Unsafe.class;
/* 1547 */             for (Field localField : localClass.getDeclaredFields()) {
/* 1548 */               localField.setAccessible(true);
/* 1549 */               Object localObject = localField.get(null);
/* 1550 */               if (localClass.isInstance(localObject))
/* 1551 */                 return (Unsafe)localClass.cast(localObject);
/*      */             }
/* 1553 */             throw new NoSuchFieldError("the Unsafe");
/*      */           }
/*      */         });
/* 1556 */       } catch (PrivilegedActionException localPrivilegedActionException) { throw new RuntimeException("Could not initialize intrinsics", localPrivilegedActionException.getCause());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   void internalPropagateException(Throwable paramThrowable) {}
/*      */   
/*      */   public abstract V getRawResult();
/*      */   
/*      */   protected abstract void setRawResult(V paramV);
/*      */   
/*      */   protected abstract boolean exec();
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\chmv8\ForkJoinTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */