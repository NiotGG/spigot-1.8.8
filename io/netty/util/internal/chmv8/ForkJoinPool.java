/*      */ package io.netty.util.internal.chmv8;
/*      */ 
/*      */ import io.netty.util.internal.ThreadLocalRandom;
/*      */ import java.lang.reflect.Field;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.AbstractExecutorService;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.RejectedExecutionException;
/*      */ import java.util.concurrent.RunnableFuture;
/*      */ import java.util.concurrent.TimeUnit;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ForkJoinPool
/*      */   extends AbstractExecutorService
/*      */ {
/*      */   static final ThreadLocal<Submitter> submitters;
/*      */   public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
/*      */   private static final RuntimePermission modifyThreadPermission;
/*      */   static final ForkJoinPool common;
/*      */   static final int commonParallelism;
/*      */   private static int poolNumberSequence;
/*      */   private static final long IDLE_TIMEOUT = 2000000000L;
/*      */   private static final long FAST_IDLE_TIMEOUT = 200000000L;
/*      */   private static final long TIMEOUT_SLOP = 2000000L;
/*      */   private static final int MAX_HELP = 64;
/*      */   private static final int SEED_INCREMENT = 1640531527;
/*      */   private static final int AC_SHIFT = 48;
/*      */   private static final int TC_SHIFT = 32;
/*      */   private static final int ST_SHIFT = 31;
/*      */   private static final int EC_SHIFT = 16;
/*      */   private static final int SMASK = 65535;
/*      */   private static final int MAX_CAP = 32767;
/*      */   private static final int EVENMASK = 65534;
/*      */   private static final int SQMASK = 126;
/*      */   private static final int SHORT_SIGN = 32768;
/*      */   private static final int INT_SIGN = Integer.MIN_VALUE;
/*      */   private static final long STOP_BIT = 2147483648L;
/*      */   private static final long AC_MASK = -281474976710656L;
/*      */   private static final long TC_MASK = 281470681743360L;
/*      */   private static final long TC_UNIT = 4294967296L;
/*      */   private static final long AC_UNIT = 281474976710656L;
/*      */   private static final int UAC_SHIFT = 16;
/*      */   private static final int UTC_SHIFT = 0;
/*      */   private static final int UAC_MASK = -65536;
/*      */   private static final int UTC_MASK = 65535;
/*      */   private static final int UAC_UNIT = 65536;
/*      */   private static final int UTC_UNIT = 1;
/*      */   private static final int E_MASK = Integer.MAX_VALUE;
/*      */   private static final int E_SEQ = 65536;
/*      */   private static final int SHUTDOWN = Integer.MIN_VALUE;
/*      */   private static final int PL_LOCK = 2;
/*      */   private static final int PL_SIGNAL = 1;
/*      */   private static final int PL_SPINS = 256;
/*      */   static final int LIFO_QUEUE = 0;
/*      */   static final int FIFO_QUEUE = 1;
/*      */   static final int SHARED_QUEUE = -1;
/*      */   volatile long pad00;
/*      */   volatile long pad01;
/*      */   volatile long pad02;
/*      */   volatile long pad03;
/*      */   volatile long pad04;
/*      */   volatile long pad05;
/*      */   volatile long pad06;
/*      */   volatile long stealCount;
/*      */   volatile long ctl;
/*      */   volatile int plock;
/*      */   volatile int indexSeed;
/*      */   final short parallelism;
/*      */   final short mode;
/*      */   WorkQueue[] workQueues;
/*      */   final ForkJoinWorkerThreadFactory factory;
/*      */   final Thread.UncaughtExceptionHandler ueh;
/*      */   final String workerNamePrefix;
/*      */   volatile Object pad10;
/*      */   volatile Object pad11;
/*      */   volatile Object pad12;
/*      */   volatile Object pad13;
/*      */   volatile Object pad14;
/*      */   volatile Object pad15;
/*      */   volatile Object pad16;
/*      */   volatile Object pad17;
/*      */   volatile Object pad18;
/*      */   volatile Object pad19;
/*      */   volatile Object pad1a;
/*      */   volatile Object pad1b;
/*      */   private static final Unsafe U;
/*      */   private static final long CTL;
/*      */   private static final long PARKBLOCKER;
/*      */   private static final int ABASE;
/*      */   private static final int ASHIFT;
/*      */   private static final long STEALCOUNT;
/*      */   private static final long PLOCK;
/*      */   private static final long INDEXSEED;
/*      */   private static final long QBASE;
/*      */   private static final long QLOCK;
/*      */   
/*      */   private static void checkPermission()
/*      */   {
/*  534 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  535 */     if (localSecurityManager != null) {
/*  536 */       localSecurityManager.checkPermission(modifyThreadPermission);
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
/*      */   public static abstract interface ForkJoinWorkerThreadFactory
/*      */   {
/*      */     public abstract ForkJoinWorkerThread newThread(ForkJoinPool paramForkJoinPool);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class DefaultForkJoinWorkerThreadFactory
/*      */     implements ForkJoinPool.ForkJoinWorkerThreadFactory
/*      */   {
/*      */     public final ForkJoinWorkerThread newThread(ForkJoinPool paramForkJoinPool)
/*      */     {
/*  565 */       return new ForkJoinWorkerThread(paramForkJoinPool);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class EmptyTask
/*      */     extends ForkJoinTask<Void>
/*      */   {
/*      */     private static final long serialVersionUID = -7721805057305804111L;
/*      */     
/*      */ 
/*  577 */     EmptyTask() { this.status = -268435456; }
/*  578 */     public final Void getRawResult() { return null; }
/*      */     public final void setRawResult(Void paramVoid) {}
/*  580 */     public final boolean exec() { return true; }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class WorkQueue
/*      */   {
/*      */     static final int INITIAL_QUEUE_CAPACITY = 8192;
/*      */     
/*      */     static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
/*      */     
/*      */     volatile long pad00;
/*      */     
/*      */     volatile long pad01;
/*      */     
/*      */     volatile long pad02;
/*      */     
/*      */     volatile long pad03;
/*      */     
/*      */     volatile long pad04;
/*      */     
/*      */     volatile long pad05;
/*      */     
/*      */     volatile long pad06;
/*      */     
/*      */     volatile int eventCount;
/*      */     
/*      */     int nextWait;
/*      */     
/*      */     int nsteals;
/*      */     
/*      */     int hint;
/*      */     
/*      */     short poolIndex;
/*      */     
/*      */     final short mode;
/*      */     
/*      */     volatile int qlock;
/*      */     
/*      */     volatile int base;
/*      */     
/*      */     int top;
/*      */     
/*      */     ForkJoinTask<?>[] array;
/*      */     
/*      */     final ForkJoinPool pool;
/*      */     
/*      */     final ForkJoinWorkerThread owner;
/*      */     
/*      */     volatile Thread parker;
/*      */     
/*      */     volatile ForkJoinTask<?> currentJoin;
/*      */     
/*      */     ForkJoinTask<?> currentSteal;
/*      */     
/*      */     volatile Object pad10;
/*      */     
/*      */     volatile Object pad11;
/*      */     
/*      */     volatile Object pad12;
/*      */     
/*      */     volatile Object pad13;
/*      */     
/*      */     volatile Object pad14;
/*      */     
/*      */     volatile Object pad15;
/*      */     
/*      */     volatile Object pad16;
/*      */     
/*      */     volatile Object pad17;
/*      */     
/*      */     volatile Object pad18;
/*      */     
/*      */     volatile Object pad19;
/*      */     
/*      */     volatile Object pad1a;
/*      */     
/*      */     volatile Object pad1b;
/*      */     
/*      */     volatile Object pad1c;
/*      */     
/*      */     volatile Object pad1d;
/*      */     
/*      */     private static final Unsafe U;
/*      */     
/*      */     private static final long QBASE;
/*      */     
/*      */     private static final long QLOCK;
/*      */     
/*      */     private static final int ABASE;
/*      */     
/*      */     private static final int ASHIFT;
/*      */     
/*      */ 
/*      */     WorkQueue(ForkJoinPool paramForkJoinPool, ForkJoinWorkerThread paramForkJoinWorkerThread, int paramInt1, int paramInt2)
/*      */     {
/*  676 */       this.pool = paramForkJoinPool;
/*  677 */       this.owner = paramForkJoinWorkerThread;
/*  678 */       this.mode = ((short)paramInt1);
/*  679 */       this.hint = paramInt2;
/*      */       
/*  681 */       this.base = (this.top = 'က');
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final int queueSize()
/*      */     {
/*  688 */       int i = this.base - this.top;
/*  689 */       return i >= 0 ? 0 : -i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final boolean isEmpty()
/*      */     {
/*      */       int i;
/*      */       
/*      */ 
/*  699 */       int j = this.base - (i = this.top);
/*  700 */       ForkJoinTask[] arrayOfForkJoinTask; int k; return (j >= 0) || ((j == -1) && (((arrayOfForkJoinTask = this.array) == null) || ((k = arrayOfForkJoinTask.length - 1) < 0) || (U.getObject(arrayOfForkJoinTask, ((k & i - 1) << ASHIFT) + ABASE) == null)));
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
/*      */     final void push(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*  717 */       int i = this.top;
/*  718 */       ForkJoinTask[] arrayOfForkJoinTask; if ((arrayOfForkJoinTask = this.array) != null) {
/*  719 */         int j = arrayOfForkJoinTask.length - 1;
/*  720 */         U.putOrderedObject(arrayOfForkJoinTask, ((j & i) << ASHIFT) + ABASE, paramForkJoinTask);
/*  721 */         int k; if ((k = (this.top = i + 1) - this.base) <= 2) { ForkJoinPool localForkJoinPool;
/*  722 */           (localForkJoinPool = this.pool).signalWork(localForkJoinPool.workQueues, this);
/*  723 */         } else if (k >= j) {
/*  724 */           growArray();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?>[] growArray()
/*      */     {
/*  734 */       ForkJoinTask[] arrayOfForkJoinTask1 = this.array;
/*  735 */       int i = arrayOfForkJoinTask1 != null ? arrayOfForkJoinTask1.length << 1 : 8192;
/*  736 */       if (i > 67108864) {
/*  737 */         throw new RejectedExecutionException("Queue capacity exceeded");
/*      */       }
/*  739 */       ForkJoinTask[] arrayOfForkJoinTask2 = this.array = new ForkJoinTask[i];
/*  740 */       int j; int k; int m; if ((arrayOfForkJoinTask1 != null) && ((j = arrayOfForkJoinTask1.length - 1) >= 0) && ((k = this.top) - (m = this.base) > 0))
/*      */       {
/*  742 */         int n = i - 1;
/*      */         do
/*      */         {
/*  745 */           int i1 = ((m & j) << ASHIFT) + ABASE;
/*  746 */           int i2 = ((m & n) << ASHIFT) + ABASE;
/*  747 */           ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask1, i1);
/*  748 */           if ((localForkJoinTask != null) && (U.compareAndSwapObject(arrayOfForkJoinTask1, i1, localForkJoinTask, null)))
/*      */           {
/*  750 */             U.putObjectVolatile(arrayOfForkJoinTask2, i2, localForkJoinTask); }
/*  751 */           m++; } while (m != k);
/*      */       }
/*  753 */       return arrayOfForkJoinTask2;
/*      */     }
/*      */     
/*      */ 
/*      */     final ForkJoinTask<?> pop()
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*  762 */       if (((arrayOfForkJoinTask = this.array) != null) && ((i = arrayOfForkJoinTask.length - 1) >= 0)) { int j;
/*  763 */         while ((j = this.top - 1) - this.base >= 0) {
/*  764 */           long l = ((i & j) << ASHIFT) + ABASE;
/*  765 */           ForkJoinTask localForkJoinTask; if ((localForkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l)) == null)
/*      */             break;
/*  767 */           if (U.compareAndSwapObject(arrayOfForkJoinTask, l, localForkJoinTask, null)) {
/*  768 */             this.top = j;
/*  769 */             return localForkJoinTask;
/*      */           }
/*      */         }
/*      */       }
/*  773 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> pollAt(int paramInt)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */ 
/*  783 */       if ((arrayOfForkJoinTask = this.array) != null) {
/*  784 */         int i = ((arrayOfForkJoinTask.length - 1 & paramInt) << ASHIFT) + ABASE;
/*  785 */         ForkJoinTask localForkJoinTask; if (((localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, i)) != null) && (this.base == paramInt) && (U.compareAndSwapObject(arrayOfForkJoinTask, i, localForkJoinTask, null)))
/*      */         {
/*  787 */           U.putOrderedInt(this, QBASE, paramInt + 1);
/*  788 */           return localForkJoinTask;
/*      */         }
/*      */       }
/*  791 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */     final ForkJoinTask<?> poll()
/*      */     {
/*      */       int i;
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*  799 */       while (((i = this.base) - this.top < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/*  800 */         int j = ((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE;
/*  801 */         ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, j);
/*  802 */         if (localForkJoinTask != null) {
/*  803 */           if (U.compareAndSwapObject(arrayOfForkJoinTask, j, localForkJoinTask, null)) {
/*  804 */             U.putOrderedInt(this, QBASE, i + 1);
/*  805 */             return localForkJoinTask;
/*      */           }
/*      */         }
/*  808 */         else if (this.base == i) {
/*  809 */           if (i + 1 == this.top)
/*      */             break;
/*  811 */           Thread.yield();
/*      */         }
/*      */       }
/*  814 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> nextLocalTask()
/*      */     {
/*  821 */       return this.mode == 0 ? pop() : poll();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final ForkJoinTask<?> peek()
/*      */     {
/*  828 */       ForkJoinTask[] arrayOfForkJoinTask = this.array;
/*  829 */       int i; if ((arrayOfForkJoinTask == null) || ((i = arrayOfForkJoinTask.length - 1) < 0))
/*  830 */         return null;
/*  831 */       int j = this.mode == 0 ? this.top - 1 : this.base;
/*  832 */       int k = ((j & i) << ASHIFT) + ABASE;
/*  833 */       return (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, k);
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean tryUnpush(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*  842 */       if (((arrayOfForkJoinTask = this.array) != null) && ((i = this.top) != this.base) && (U.compareAndSwapObject(arrayOfForkJoinTask, ((arrayOfForkJoinTask.length - 1 & --i) << ASHIFT) + ABASE, paramForkJoinTask, null)))
/*      */       {
/*      */ 
/*  845 */         this.top = i;
/*  846 */         return true;
/*      */       }
/*  848 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final void cancelAll()
/*      */     {
/*  855 */       ForkJoinTask.cancelIgnoringExceptions(this.currentJoin);
/*  856 */       ForkJoinTask.cancelIgnoringExceptions(this.currentSteal);
/*  857 */       ForkJoinTask localForkJoinTask; while ((localForkJoinTask = poll()) != null) {
/*  858 */         ForkJoinTask.cancelIgnoringExceptions(localForkJoinTask);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final void pollAndExecAll()
/*      */     {
/*      */       ForkJoinTask localForkJoinTask;
/*      */       
/*  867 */       while ((localForkJoinTask = poll()) != null) {
/*  868 */         localForkJoinTask.doExec();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     final void runTask(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*  876 */       if ((this.currentSteal = paramForkJoinTask) != null) {
/*  877 */         paramForkJoinTask.doExec();
/*  878 */         ForkJoinTask[] arrayOfForkJoinTask = this.array;
/*  879 */         int i = this.mode;
/*  880 */         this.nsteals += 1;
/*  881 */         this.currentSteal = null;
/*  882 */         if (i != 0) {
/*  883 */           pollAndExecAll();
/*  884 */         } else if (arrayOfForkJoinTask != null) {
/*  885 */           int j = arrayOfForkJoinTask.length - 1;
/*  886 */           int k; while ((k = this.top - 1) - this.base >= 0) {
/*  887 */             long l = ((j & k) << ASHIFT) + ABASE;
/*  888 */             ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l);
/*  889 */             if (localForkJoinTask == null)
/*      */               break;
/*  891 */             if (U.compareAndSwapObject(arrayOfForkJoinTask, l, localForkJoinTask, null)) {
/*  892 */               this.top = k;
/*  893 */               localForkJoinTask.doExec();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean tryRemoveAndExec(ForkJoinTask<?> paramForkJoinTask)
/*      */     {
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*      */       
/*      */       int i;
/*      */       int j;
/*      */       int k;
/*      */       int m;
/*      */       boolean bool2;
/*  910 */       if ((paramForkJoinTask != null) && ((arrayOfForkJoinTask = this.array) != null) && ((i = arrayOfForkJoinTask.length - 1) >= 0) && ((m = (j = this.top) - (k = this.base)) > 0))
/*      */       {
/*  912 */         boolean bool1 = false;int n = 1;
/*  913 */         bool2 = true;
/*      */         for (;;) {
/*  915 */           j--;long l = ((j & i) << ASHIFT) + ABASE;
/*  916 */           ForkJoinTask localForkJoinTask = (ForkJoinTask)U.getObject(arrayOfForkJoinTask, l);
/*  917 */           if (localForkJoinTask == null)
/*      */             break;
/*  919 */           if (localForkJoinTask == paramForkJoinTask) {
/*  920 */             if (j + 1 == this.top) {
/*  921 */               if (!U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, null))
/*      */                 break;
/*  923 */               this.top = j;
/*  924 */               bool1 = true; break;
/*      */             }
/*  926 */             if (this.base != k) break;
/*  927 */             bool1 = U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, new ForkJoinPool.EmptyTask()); break;
/*      */           }
/*      */           
/*      */ 
/*  931 */           if (localForkJoinTask.status >= 0) {
/*  932 */             n = 0;
/*  933 */           } else if (j + 1 == this.top) {
/*  934 */             if (!U.compareAndSwapObject(arrayOfForkJoinTask, l, localForkJoinTask, null)) break;
/*  935 */             this.top = j; break;
/*      */           }
/*      */           
/*  938 */           m--; if (m == 0) {
/*  939 */             if ((n != 0) || (this.base != k)) break;
/*  940 */             bool2 = false; break;
/*      */           }
/*      */         }
/*      */         
/*  944 */         if (bool1) {
/*  945 */           paramForkJoinTask.doExec();
/*      */         }
/*      */       } else {
/*  948 */         bool2 = false; }
/*  949 */       return bool2;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean pollAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*  958 */       if (((i = this.base) - this.top < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/*  959 */         long l = ((arrayOfForkJoinTask.length - 1 & i) << ASHIFT) + ABASE;
/*  960 */         Object localObject; if ((localObject = U.getObjectVolatile(arrayOfForkJoinTask, l)) == null)
/*  961 */           return true;
/*  962 */         if ((localObject instanceof CountedCompleter)) {
/*  963 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*  964 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/*  965 */               if ((this.base == i) && (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null)))
/*      */               {
/*  967 */                 U.putOrderedInt(this, QBASE, i + 1);
/*  968 */                 localCountedCompleter1.doExec();
/*      */               }
/*  970 */               return true;
/*      */             }
/*  972 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/*  977 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean externalPopAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/*  986 */       if ((this.base - (i = this.top) < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/*  987 */         long l = ((arrayOfForkJoinTask.length - 1 & i - 1) << ASHIFT) + ABASE;
/*  988 */         Object localObject; if (((localObject = U.getObject(arrayOfForkJoinTask, l)) instanceof CountedCompleter)) {
/*  989 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/*  990 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/*  991 */               if (U.compareAndSwapInt(this, QLOCK, 0, 1))
/*  992 */                 if ((this.top == i) && (this.array == arrayOfForkJoinTask) && (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null)))
/*      */                 {
/*  994 */                   this.top = (i - 1);
/*  995 */                   this.qlock = 0;
/*  996 */                   localCountedCompleter1.doExec();
/*      */                 }
/*      */                 else {
/*  999 */                   this.qlock = 0;
/*      */                 }
/* 1001 */               return true;
/*      */             }
/* 1003 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/* 1008 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean internalPopAndExecCC(CountedCompleter<?> paramCountedCompleter)
/*      */     {
/*      */       int i;
/*      */       ForkJoinTask[] arrayOfForkJoinTask;
/* 1016 */       if ((this.base - (i = this.top) < 0) && ((arrayOfForkJoinTask = this.array) != null)) {
/* 1017 */         long l = ((arrayOfForkJoinTask.length - 1 & i - 1) << ASHIFT) + ABASE;
/* 1018 */         Object localObject; if (((localObject = U.getObject(arrayOfForkJoinTask, l)) instanceof CountedCompleter)) {
/* 1019 */           CountedCompleter localCountedCompleter1 = (CountedCompleter)localObject;CountedCompleter localCountedCompleter2 = localCountedCompleter1;
/* 1020 */           for (;;) { if (localCountedCompleter2 == paramCountedCompleter) {
/* 1021 */               if (U.compareAndSwapObject(arrayOfForkJoinTask, l, localCountedCompleter1, null)) {
/* 1022 */                 this.top = (i - 1);
/* 1023 */                 localCountedCompleter1.doExec();
/*      */               }
/* 1025 */               return true;
/*      */             }
/* 1027 */             if ((localCountedCompleter2 = localCountedCompleter2.completer) == null)
/*      */               break;
/*      */           }
/*      */         }
/*      */       }
/* 1032 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     final boolean isApparentlyUnblocked()
/*      */     {
/*      */       ForkJoinWorkerThread localForkJoinWorkerThread;
/*      */       Thread.State localState;
/* 1040 */       return (this.eventCount >= 0) && ((localForkJoinWorkerThread = this.owner) != null) && ((localState = localForkJoinWorkerThread.getState()) != Thread.State.BLOCKED) && (localState != Thread.State.WAITING) && (localState != Thread.State.TIMED_WAITING);
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
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/* 1055 */         U = ForkJoinPool.access$000();
/* 1056 */         Class localClass1 = WorkQueue.class;
/* 1057 */         Class localClass2 = ForkJoinTask[].class;
/* 1058 */         QBASE = U.objectFieldOffset(localClass1.getDeclaredField("base"));
/*      */         
/* 1060 */         QLOCK = U.objectFieldOffset(localClass1.getDeclaredField("qlock"));
/*      */         
/* 1062 */         ABASE = U.arrayBaseOffset(localClass2);
/* 1063 */         int i = U.arrayIndexScale(localClass2);
/* 1064 */         if ((i & i - 1) != 0)
/* 1065 */           throw new Error("data type scale not a power of two");
/* 1066 */         ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/*      */       } catch (Exception localException) {
/* 1068 */         throw new Error(localException);
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final synchronized int nextPoolId()
/*      */   {
/* 1123 */     return ++poolNumberSequence;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int acquirePlock()
/*      */   {
/* 1275 */     int i = 256;
/*      */     for (;;) { int j;
/* 1277 */       int k; if ((((j = this.plock) & 0x2) == 0) && (U.compareAndSwapInt(this, PLOCK, j, k = j + 2)))
/*      */       {
/* 1279 */         return k; }
/* 1280 */       if (i >= 0) {
/* 1281 */         if (ThreadLocalRandom.current().nextInt() >= 0) {
/* 1282 */           i--;
/*      */         }
/* 1284 */       } else if (U.compareAndSwapInt(this, PLOCK, j, j | 0x1)) {
/* 1285 */         synchronized (this) {
/* 1286 */           if ((this.plock & 0x1) != 0) {
/*      */             try {
/* 1288 */               wait();
/*      */             } catch (InterruptedException localInterruptedException) {
/*      */               try {
/* 1291 */                 Thread.currentThread().interrupt();
/*      */ 
/*      */               }
/*      */               catch (SecurityException localSecurityException) {}
/*      */             }
/*      */           } else {
/* 1297 */             notifyAll();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void releasePlock(int paramInt)
/*      */   {
/* 1308 */     this.plock = paramInt;
/* 1309 */     synchronized (this) { notifyAll();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void tryAddWorker()
/*      */   {
/*      */     long l1;
/*      */     int i;
/*      */     int j;
/* 1319 */     while (((i = (int)((l1 = this.ctl) >>> 32)) < 0) && ((i & 0x8000) != 0) && ((j = (int)l1) >= 0)) {
/* 1320 */       long l2 = (i + 1 & 0xFFFF | i + 65536 & 0xFFFF0000) << 32 | j;
/*      */       
/* 1322 */       if (U.compareAndSwapLong(this, CTL, l1, l2))
/*      */       {
/* 1324 */         Object localObject = null;
/* 1325 */         ForkJoinWorkerThread localForkJoinWorkerThread = null;
/*      */         try { ForkJoinWorkerThreadFactory localForkJoinWorkerThreadFactory;
/* 1327 */           if (((localForkJoinWorkerThreadFactory = this.factory) != null) && ((localForkJoinWorkerThread = localForkJoinWorkerThreadFactory.newThread(this)) != null))
/*      */           {
/* 1329 */             localForkJoinWorkerThread.start();
/* 1330 */             break;
/*      */           }
/*      */         } catch (Throwable localThrowable) {
/* 1333 */           localObject = localThrowable;
/*      */         }
/* 1335 */         deregisterWorker(localForkJoinWorkerThread, (Throwable)localObject);
/* 1336 */         break;
/*      */       }
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
/*      */   final WorkQueue registerWorker(ForkJoinWorkerThread paramForkJoinWorkerThread)
/*      */   {
/* 1355 */     paramForkJoinWorkerThread.setDaemon(true);
/* 1356 */     Thread.UncaughtExceptionHandler localUncaughtExceptionHandler; if ((localUncaughtExceptionHandler = this.ueh) != null)
/* 1357 */       paramForkJoinWorkerThread.setUncaughtExceptionHandler(localUncaughtExceptionHandler);
/*      */     int i;
/* 1359 */     do { i += 1640531527; } while ((!U.compareAndSwapInt(this, INDEXSEED, i = this.indexSeed, i)) || (i == 0));
/*      */     
/* 1361 */     WorkQueue localWorkQueue = new WorkQueue(this, paramForkJoinWorkerThread, this.mode, i);
/* 1362 */     int j; if ((((j = this.plock) & 0x2) != 0) || (!U.compareAndSwapInt(this, PLOCK, , j)))
/*      */     {
/* 1364 */       j = acquirePlock(); }
/* 1365 */     int k = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/*      */     try { WorkQueue[] arrayOfWorkQueue;
/* 1367 */       if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 1368 */         int m = arrayOfWorkQueue.length;int n = m - 1;
/* 1369 */         int i1 = i << 1 | 0x1;
/* 1370 */         if (arrayOfWorkQueue[(i1 &= n)] != null) {
/* 1371 */           int i2 = 0;
/* 1372 */           int i3 = m <= 4 ? 2 : (m >>> 1 & 0xFFFE) + 2;
/* 1373 */           while (arrayOfWorkQueue[(i1 = i1 + i3 & n)] != null) {
/* 1374 */             i2++; if (i2 >= m) {
/* 1375 */               this.workQueues = (arrayOfWorkQueue = (WorkQueue[])Arrays.copyOf(arrayOfWorkQueue, m <<= 1));
/* 1376 */               n = m - 1;
/* 1377 */               i2 = 0;
/*      */             }
/*      */           }
/*      */         }
/* 1381 */         localWorkQueue.poolIndex = ((short)i1);
/* 1382 */         localWorkQueue.eventCount = i1;
/* 1383 */         arrayOfWorkQueue[i1] = localWorkQueue;
/*      */       }
/*      */     } finally {
/* 1386 */       if (!U.compareAndSwapInt(this, PLOCK, j, k))
/* 1387 */         releasePlock(k);
/*      */     }
/* 1389 */     paramForkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(localWorkQueue.poolIndex >>> 1)));
/* 1390 */     return localWorkQueue;
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
/*      */   final void deregisterWorker(ForkJoinWorkerThread paramForkJoinWorkerThread, Throwable paramThrowable)
/*      */   {
/* 1403 */     WorkQueue localWorkQueue = null;
/* 1404 */     if ((paramForkJoinWorkerThread != null) && ((localWorkQueue = paramForkJoinWorkerThread.workQueue) != null))
/*      */     {
/* 1406 */       localWorkQueue.qlock = -1;
/* 1407 */       long l1; while (!U.compareAndSwapLong(this, STEALCOUNT, l1 = this.stealCount, l1 + localWorkQueue.nsteals)) {}
/*      */       
/*      */       int i;
/* 1410 */       if ((((i = this.plock) & 0x2) != 0) || (!U.compareAndSwapInt(this, PLOCK, , i)))
/*      */       {
/* 1412 */         i = acquirePlock(); }
/* 1413 */       int j = i & 0x80000000 | i + 2 & 0x7FFFFFFF;
/*      */       try {
/* 1415 */         int k = localWorkQueue.poolIndex;
/* 1416 */         WorkQueue[] arrayOfWorkQueue1 = this.workQueues;
/* 1417 */         if ((arrayOfWorkQueue1 != null) && (k >= 0) && (k < arrayOfWorkQueue1.length) && (arrayOfWorkQueue1[k] == localWorkQueue))
/* 1418 */           arrayOfWorkQueue1[k] = null;
/*      */       } finally {
/* 1420 */         if (!U.compareAndSwapInt(this, PLOCK, i, j)) {
/* 1421 */           releasePlock(j);
/*      */         }
/*      */       }
/*      */     }
/*      */     long l2;
/* 1426 */     while (!U.compareAndSwapLong(this, CTL, l2 = this.ctl, l2 - 281474976710656L & 0xFFFF000000000000 | l2 - 4294967296L & 0xFFFF00000000 | l2 & 0xFFFFFFFF)) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1431 */     if ((!tryTerminate(false, false)) && (localWorkQueue != null) && (localWorkQueue.array != null)) {
/* 1432 */       localWorkQueue.cancelAll();
/*      */       int m;
/* 1434 */       int i1; while (((m = (int)((l2 = this.ctl) >>> 32)) < 0) && ((i1 = (int)l2) >= 0)) {
/* 1435 */         if (i1 > 0) { WorkQueue[] arrayOfWorkQueue2;
/* 1436 */           int n; Object localObject1; if (((arrayOfWorkQueue2 = this.workQueues) != null) && ((n = i1 & 0xFFFF) < arrayOfWorkQueue2.length) && ((localObject1 = arrayOfWorkQueue2[n]) != null))
/*      */           {
/*      */ 
/*      */ 
/* 1440 */             long l3 = ((WorkQueue)localObject1).nextWait & 0x7FFFFFFF | m + 65536 << 32;
/*      */             
/* 1442 */             if (((WorkQueue)localObject1).eventCount == (i1 | 0x80000000))
/*      */             {
/* 1444 */               if (U.compareAndSwapLong(this, CTL, l2, l3)) {
/* 1445 */                 ((WorkQueue)localObject1).eventCount = (i1 + 65536 & 0x7FFFFFFF);
/* 1446 */                 Thread localThread; if ((localThread = ((WorkQueue)localObject1).parker) == null) break;
/* 1447 */                 U.unpark(localThread);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1452 */         else if ((short)m < 0) {
/* 1453 */           tryAddWorker();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1458 */     if (paramThrowable == null) {
/* 1459 */       ForkJoinTask.helpExpungeStaleExceptions();
/*      */     } else {
/* 1461 */       ForkJoinTask.rethrow(paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class Submitter
/*      */   {
/*      */     int seed;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Submitter(int paramInt)
/*      */     {
/* 1483 */       this.seed = paramInt;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void externalPush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 1495 */     Submitter localSubmitter = (Submitter)submitters.get();
/*      */     
/* 1497 */     int i = this.plock;
/* 1498 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 1499 */     int j; int k; WorkQueue localWorkQueue; if ((localSubmitter != null) && (i > 0) && (arrayOfWorkQueue != null) && ((j = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue = arrayOfWorkQueue[(j & (k = localSubmitter.seed) & 0x7E)]) != null) && (k != 0) && (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1))) { ForkJoinTask[] arrayOfForkJoinTask;
/*      */       int m;
/*      */       int n;
/* 1502 */       int i1; if (((arrayOfForkJoinTask = localWorkQueue.array) != null) && ((m = arrayOfForkJoinTask.length - 1) > (i1 = (n = localWorkQueue.top) - localWorkQueue.base)))
/*      */       {
/* 1504 */         int i2 = ((m & n) << ASHIFT) + ABASE;
/* 1505 */         U.putOrderedObject(arrayOfForkJoinTask, i2, paramForkJoinTask);
/* 1506 */         localWorkQueue.top = (n + 1);
/* 1507 */         localWorkQueue.qlock = 0;
/* 1508 */         if (i1 <= 1)
/* 1509 */           signalWork(arrayOfWorkQueue, localWorkQueue);
/* 1510 */         return;
/*      */       }
/* 1512 */       localWorkQueue.qlock = 0;
/*      */     }
/* 1514 */     fullExternalPush(paramForkJoinTask);
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
/*      */   private void fullExternalPush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 1535 */     int i = 0;
/* 1536 */     Submitter localSubmitter = (Submitter)submitters.get();
/*      */     for (;;) {
/* 1538 */       if (localSubmitter == null) {
/* 1539 */         i += 1640531527; if ((U.compareAndSwapInt(this, INDEXSEED, i = this.indexSeed, i)) && (i != 0))
/*      */         {
/* 1541 */           submitters.set(localSubmitter = new Submitter(i));
/*      */         }
/* 1543 */       } else if (i == 0) {
/* 1544 */         i = localSubmitter.seed;
/* 1545 */         i ^= i << 13;
/* 1546 */         i ^= i >>> 17;
/* 1547 */         localSubmitter.seed = (i ^= i << 5); }
/*      */       int j;
/* 1549 */       if ((j = this.plock) < 0)
/* 1550 */         throw new RejectedExecutionException();
/* 1551 */       WorkQueue[] arrayOfWorkQueue1; int k; int i1; int i3; if ((j == 0) || ((arrayOfWorkQueue1 = this.workQueues) == null) || ((k = arrayOfWorkQueue1.length - 1) < 0))
/*      */       {
/* 1553 */         int m = this.parallelism;
/* 1554 */         i1 = m > 1 ? m - 1 : 1;
/* 1555 */         i1 |= i1 >>> 1;i1 |= i1 >>> 2;i1 |= i1 >>> 4;
/* 1556 */         i1 |= i1 >>> 8;i1 |= i1 >>> 16;i1 = i1 + 1 << 1;
/* 1557 */         WorkQueue[] arrayOfWorkQueue2 = ((arrayOfWorkQueue1 = this.workQueues) == null) || (arrayOfWorkQueue1.length == 0) ? new WorkQueue[i1] : null;
/*      */         
/* 1559 */         if ((((j = this.plock) & 0x2) != 0) || (!U.compareAndSwapInt(this, PLOCK, , j)))
/*      */         {
/* 1561 */           j = acquirePlock(); }
/* 1562 */         if ((((arrayOfWorkQueue1 = this.workQueues) == null) || (arrayOfWorkQueue1.length == 0)) && (arrayOfWorkQueue2 != null))
/* 1563 */           this.workQueues = arrayOfWorkQueue2;
/* 1564 */         i3 = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/* 1565 */         if (!U.compareAndSwapInt(this, PLOCK, j, i3))
/* 1566 */           releasePlock(i3); } else { int i4;
/*      */         WorkQueue localWorkQueue;
/* 1568 */         if ((localWorkQueue = arrayOfWorkQueue1[(i4 = i & k & 0x7E)]) != null) {
/* 1569 */           if ((localWorkQueue.qlock == 0) && (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1))) {
/* 1570 */             ForkJoinTask[] arrayOfForkJoinTask = localWorkQueue.array;
/* 1571 */             i1 = localWorkQueue.top;
/* 1572 */             int i2 = 0;
/*      */             try {
/* 1574 */               if (((arrayOfForkJoinTask != null) && (arrayOfForkJoinTask.length > i1 + 1 - localWorkQueue.base)) || ((arrayOfForkJoinTask = localWorkQueue.growArray()) != null))
/*      */               {
/* 1576 */                 i3 = ((arrayOfForkJoinTask.length - 1 & i1) << ASHIFT) + ABASE;
/* 1577 */                 U.putOrderedObject(arrayOfForkJoinTask, i3, paramForkJoinTask);
/* 1578 */                 localWorkQueue.top = (i1 + 1);
/* 1579 */                 i2 = 1;
/*      */               }
/*      */             } finally {
/* 1582 */               localWorkQueue.qlock = 0;
/*      */             }
/* 1584 */             if (i2 != 0) {
/* 1585 */               signalWork(arrayOfWorkQueue1, localWorkQueue);
/* 1586 */               return;
/*      */             }
/*      */           }
/* 1589 */           i = 0;
/*      */         }
/* 1591 */         else if (((j = this.plock) & 0x2) == 0) {
/* 1592 */           localWorkQueue = new WorkQueue(this, null, -1, i);
/* 1593 */           localWorkQueue.poolIndex = ((short)i4);
/* 1594 */           if ((((j = this.plock) & 0x2) != 0) || (!U.compareAndSwapInt(this, PLOCK, , j)))
/*      */           {
/* 1596 */             j = acquirePlock(); }
/* 1597 */           if (((arrayOfWorkQueue1 = this.workQueues) != null) && (i4 < arrayOfWorkQueue1.length) && (arrayOfWorkQueue1[i4] == null))
/* 1598 */             arrayOfWorkQueue1[i4] = localWorkQueue;
/* 1599 */           int n = j & 0x80000000 | j + 2 & 0x7FFFFFFF;
/* 1600 */           if (!U.compareAndSwapInt(this, PLOCK, j, n)) {
/* 1601 */             releasePlock(n);
/*      */           }
/*      */         } else {
/* 1604 */           i = 0;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final void incrementActiveCount()
/*      */   {
/*      */     long l;
/*      */     
/* 1615 */     while (!U.compareAndSwapLong(this, CTL, l = this.ctl, l & 0xFFFFFFFFFFFF | (l & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final void signalWork(WorkQueue[] paramArrayOfWorkQueue, WorkQueue paramWorkQueue)
/*      */   {
/*      */     long l1;
/*      */     
/*      */ 
/*      */     int i;
/*      */     
/*      */ 
/* 1629 */     while ((i = (int)((l1 = this.ctl) >>> 32)) < 0) {
/*      */       int j;
/* 1631 */       if ((j = (int)l1) <= 0) {
/* 1632 */         if ((short)i < 0)
/* 1633 */           tryAddWorker();
/*      */       } else { int k;
/*      */         WorkQueue localWorkQueue;
/* 1636 */         if ((paramArrayOfWorkQueue != null) && (paramArrayOfWorkQueue.length > (k = j & 0xFFFF)) && ((localWorkQueue = paramArrayOfWorkQueue[k]) != null))
/*      */         {
/*      */ 
/* 1639 */           long l2 = localWorkQueue.nextWait & 0x7FFFFFFF | i + 65536 << 32;
/*      */           
/* 1641 */           int m = j + 65536 & 0x7FFFFFFF;
/* 1642 */           if ((localWorkQueue.eventCount == (j | 0x80000000)) && (U.compareAndSwapLong(this, CTL, l1, l2)))
/*      */           {
/* 1644 */             localWorkQueue.eventCount = m;
/* 1645 */             Thread localThread; if ((localThread = localWorkQueue.parker) != null) {
/* 1646 */               U.unpark(localThread);
/*      */             }
/*      */           } else {
/* 1649 */             if ((paramWorkQueue != null) && (paramWorkQueue.base >= paramWorkQueue.top)) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   final void runWorker(WorkQueue paramWorkQueue)
/*      */   {
/* 1660 */     paramWorkQueue.growArray();
/* 1661 */     for (int i = paramWorkQueue.hint; scan(paramWorkQueue, i) == 0; 
/* 1662 */         i ^= i << 5) { i ^= i << 13;i ^= i >>> 17;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int scan(WorkQueue paramWorkQueue, int paramInt)
/*      */   {
/* 1690 */     long l1 = this.ctl;
/* 1691 */     WorkQueue[] arrayOfWorkQueue; int i; if (((arrayOfWorkQueue = this.workQueues) != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && (paramWorkQueue != null)) {
/* 1692 */       int j = i + i + 1;int k = paramWorkQueue.eventCount;
/*      */       for (;;) { WorkQueue localWorkQueue;
/* 1694 */         int m; ForkJoinTask[] arrayOfForkJoinTask; long l2; if (((localWorkQueue = arrayOfWorkQueue[(paramInt - j & i)]) != null) && ((m = localWorkQueue.base) - localWorkQueue.top < 0) && ((arrayOfForkJoinTask = localWorkQueue.array) != null))
/*      */         {
/* 1696 */           l2 = ((arrayOfForkJoinTask.length - 1 & m) << ASHIFT) + ABASE;
/* 1697 */           ForkJoinTask localForkJoinTask; if ((localForkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, l2)) == null)
/*      */             break;
/* 1699 */           if (k < 0) {
/* 1700 */             helpRelease(l1, arrayOfWorkQueue, paramWorkQueue, localWorkQueue, m); break; }
/* 1701 */           if ((localWorkQueue.base != m) || (!U.compareAndSwapObject(arrayOfForkJoinTask, l2, localForkJoinTask, null)))
/*      */             break;
/* 1703 */           U.putOrderedInt(localWorkQueue, QBASE, m + 1);
/* 1704 */           if (m + 1 - localWorkQueue.top < 0)
/* 1705 */             signalWork(arrayOfWorkQueue, localWorkQueue);
/* 1706 */           paramWorkQueue.runTask(localForkJoinTask); break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1711 */         j--; if (j < 0) { int n;
/* 1712 */           if ((k | (n = (int)l1)) < 0)
/* 1713 */             return awaitWork(paramWorkQueue, l1, k);
/* 1714 */           if (this.ctl != l1) break;
/* 1715 */           l2 = k | l1 - 281474976710656L & 0xFFFFFFFF00000000;
/* 1716 */           paramWorkQueue.nextWait = n;
/* 1717 */           paramWorkQueue.eventCount = (k | 0x80000000);
/* 1718 */           if (!U.compareAndSwapLong(this, CTL, l1, l2))
/* 1719 */             paramWorkQueue.eventCount = k;
/* 1720 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1725 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int awaitWork(WorkQueue paramWorkQueue, long paramLong, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1744 */     if (((i = paramWorkQueue.qlock) >= 0) && (paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong) && (!Thread.interrupted()))
/*      */     {
/* 1746 */       int j = (int)paramLong;
/* 1747 */       int k = (int)(paramLong >>> 32);
/* 1748 */       int m = (k >> 16) + this.parallelism;
/*      */       
/* 1750 */       if ((j < 0) || ((m <= 0) && (tryTerminate(false, false)))) {
/* 1751 */         i = paramWorkQueue.qlock = -1; } else { int n;
/* 1752 */         long l1; if ((n = paramWorkQueue.nsteals) != 0)
/*      */         {
/* 1754 */           paramWorkQueue.nsteals = 0;
/* 1755 */           while (!U.compareAndSwapLong(this, STEALCOUNT, l1 = this.stealCount, l1 + n)) {}
/*      */         }
/*      */         else
/*      */         {
/* 1759 */           l1 = (m > 0) || (paramInt != (j | 0x80000000)) ? 0L : paramWorkQueue.nextWait & 0x7FFFFFFF | k + 65536 << 32;
/*      */           long l2;
/*      */           long l3;
/* 1762 */           if (l1 != 0L) {
/* 1763 */             int i1 = -(short)(int)(paramLong >>> 32);
/* 1764 */             l2 = i1 < 0 ? 200000000L : (i1 + 1) * 2000000000L;
/*      */             
/* 1766 */             l3 = System.nanoTime() + l2 - 2000000L;
/*      */           }
/*      */           else {
/* 1769 */             l2 = l3 = 0L; }
/* 1770 */           if ((paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong)) {
/* 1771 */             Thread localThread = Thread.currentThread();
/* 1772 */             U.putObject(localThread, PARKBLOCKER, this);
/* 1773 */             paramWorkQueue.parker = localThread;
/* 1774 */             if ((paramWorkQueue.eventCount == paramInt) && (this.ctl == paramLong))
/* 1775 */               U.park(false, l2);
/* 1776 */             paramWorkQueue.parker = null;
/* 1777 */             U.putObject(localThread, PARKBLOCKER, null);
/* 1778 */             if ((l2 != 0L) && (this.ctl == paramLong) && (l3 - System.nanoTime() <= 0L) && (U.compareAndSwapLong(this, CTL, paramLong, l1)))
/*      */             {
/*      */ 
/* 1781 */               i = paramWorkQueue.qlock = -1; }
/*      */           }
/*      */         }
/*      */       } }
/* 1785 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   private final void helpRelease(long paramLong, WorkQueue[] paramArrayOfWorkQueue, WorkQueue paramWorkQueue1, WorkQueue paramWorkQueue2, int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/*      */     int j;
/*      */     
/*      */     WorkQueue localWorkQueue;
/*      */     
/* 1797 */     if ((paramWorkQueue1 != null) && (paramWorkQueue1.eventCount < 0) && ((i = (int)paramLong) > 0) && (paramArrayOfWorkQueue != null) && (paramArrayOfWorkQueue.length > (j = i & 0xFFFF)) && ((localWorkQueue = paramArrayOfWorkQueue[j]) != null) && (this.ctl == paramLong))
/*      */     {
/*      */ 
/* 1800 */       long l = localWorkQueue.nextWait & 0x7FFFFFFF | (int)(paramLong >>> 32) + 65536 << 32;
/*      */       
/* 1802 */       int k = i + 65536 & 0x7FFFFFFF;
/* 1803 */       if ((paramWorkQueue2 != null) && (paramWorkQueue2.base == paramInt) && (paramWorkQueue1.eventCount < 0) && (localWorkQueue.eventCount == (i | 0x80000000)) && (U.compareAndSwapLong(this, CTL, paramLong, l)))
/*      */       {
/*      */ 
/* 1806 */         localWorkQueue.eventCount = k;
/* 1807 */         Thread localThread; if ((localThread = localWorkQueue.parker) != null) {
/* 1808 */           U.unpark(localThread);
/*      */         }
/*      */       }
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
/*      */ 
/*      */   private int tryHelpStealer(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 1832 */     int i = 0;int j = 0;
/* 1833 */     if ((paramForkJoinTask != null) && (paramWorkQueue != null) && (paramWorkQueue.base - paramWorkQueue.top >= 0)) {
/*      */       break label107;
/*      */       label25:
/* 1836 */       Object localObject1 = paramForkJoinTask;
/* 1837 */       Object localObject2 = paramWorkQueue;
/*      */       label107:
/* 1839 */       label471: label472: label474: for (;;) { int k; if ((k = paramForkJoinTask.status) < 0) {
/* 1840 */           i = k;
/* 1841 */           return i; }
/*      */         WorkQueue[] arrayOfWorkQueue;
/* 1843 */         int m; if (((arrayOfWorkQueue = this.workQueues) == null) || ((m = arrayOfWorkQueue.length - 1) <= 0)) return i;
/*      */         int n;
/* 1845 */         WorkQueue localWorkQueue; if (((localWorkQueue = arrayOfWorkQueue[(n = (((WorkQueue)localObject2).hint | 0x1) & m)]) == null) || (localWorkQueue.currentSteal != localObject1))
/*      */         {
/* 1847 */           int i1 = n;
/* 1848 */           if ((((n = n + 2 & m) & 0xF) == 1) && ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1))) {
/*      */             break label25;
/*      */           }
/* 1851 */           if (((localWorkQueue = arrayOfWorkQueue[n]) != null) && (localWorkQueue.currentSteal == localObject1))
/*      */           {
/* 1853 */             ((WorkQueue)localObject2).hint = n;
/*      */           }
/*      */           else {
/* 1856 */             if (n != i1) break;
/* 1857 */             return i;
/*      */           }
/*      */         }
/*      */         for (;;)
/*      */         {
/* 1862 */           if (((ForkJoinTask)localObject1).status < 0) break label472;
/*      */           int i2;
/* 1864 */           ForkJoinTask[] arrayOfForkJoinTask; if (((i2 = localWorkQueue.base) - localWorkQueue.top < 0) && ((arrayOfForkJoinTask = localWorkQueue.array) != null)) {
/* 1865 */             int i3 = ((arrayOfForkJoinTask.length - 1 & i2) << ASHIFT) + ABASE;
/* 1866 */             ForkJoinTask localForkJoinTask2 = (ForkJoinTask)U.getObjectVolatile(arrayOfForkJoinTask, i3);
/*      */             
/* 1868 */             if ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1) || (localWorkQueue.currentSteal != localObject1)) {
/*      */               break;
/*      */             }
/* 1871 */             i = 1;
/* 1872 */             if (localWorkQueue.base == i2) {
/* 1873 */               if (localForkJoinTask2 == null)
/*      */                 return i;
/* 1875 */               if (U.compareAndSwapObject(arrayOfForkJoinTask, i3, localForkJoinTask2, null)) {
/* 1876 */                 U.putOrderedInt(localWorkQueue, QBASE, i2 + 1);
/* 1877 */                 ForkJoinTask localForkJoinTask3 = paramWorkQueue.currentSteal;
/* 1878 */                 int i4 = paramWorkQueue.top;
/*      */                 do {
/* 1880 */                   paramWorkQueue.currentSteal = localForkJoinTask2;
/* 1881 */                   localForkJoinTask2.doExec();
/*      */                 }
/* 1883 */                 while ((paramForkJoinTask.status >= 0) && (paramWorkQueue.top != i4) && ((localForkJoinTask2 = paramWorkQueue.pop()) != null));
/*      */                 
/* 1885 */                 paramWorkQueue.currentSteal = localForkJoinTask3;
/* 1886 */                 return i;
/*      */               }
/*      */             }
/*      */             break label471;
/*      */           }
/* 1891 */           ForkJoinTask localForkJoinTask1 = localWorkQueue.currentJoin;
/* 1892 */           if ((((ForkJoinTask)localObject1).status < 0) || (((WorkQueue)localObject2).currentJoin != localObject1) || (localWorkQueue.currentSteal != localObject1)) {
/*      */             break;
/*      */           }
/* 1895 */           if (localForkJoinTask1 == null) return i; j++; if (j == 64) {
/*      */             return i;
/*      */           }
/* 1898 */           localObject1 = localForkJoinTask1;
/* 1899 */           localObject2 = localWorkQueue;
/*      */           
/*      */           break label474;
/*      */         }
/*      */         
/*      */         break label25;
/*      */       }
/*      */     }
/* 1907 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int helpComplete(WorkQueue paramWorkQueue, CountedCompleter<?> paramCountedCompleter)
/*      */   {
/* 1918 */     int i = 0;
/* 1919 */     WorkQueue[] arrayOfWorkQueue; int j; if (((arrayOfWorkQueue = this.workQueues) != null) && ((j = arrayOfWorkQueue.length - 1) >= 0) && (paramWorkQueue != null) && (paramCountedCompleter != null))
/*      */     {
/* 1921 */       int k = paramWorkQueue.poolIndex;
/* 1922 */       int m = j + j + 1;
/* 1923 */       long l = 0L;
/* 1924 */       int n = m;
/* 1926 */       for (; 
/* 1926 */           (i = paramCountedCompleter.status) >= 0; k += 2)
/*      */       {
/*      */ 
/*      */ 
/* 1928 */         if (paramWorkQueue.internalPopAndExecCC(paramCountedCompleter)) {
/* 1929 */           n = m;
/* 1930 */         } else { if ((i = paramCountedCompleter.status) < 0) break;
/*      */           WorkQueue localWorkQueue;
/* 1932 */           if (((localWorkQueue = arrayOfWorkQueue[(k & j)]) != null) && (localWorkQueue.pollAndExecCC(paramCountedCompleter))) {
/* 1933 */             n = m;
/* 1934 */           } else { n--; if (n < 0) {
/* 1935 */               if (l == (l = this.ctl))
/*      */                 break;
/* 1937 */               n = m;
/*      */             }
/*      */           }
/*      */         } } }
/* 1941 */     return i;
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
/*      */   final boolean tryCompensate(long paramLong)
/*      */   {
/* 1954 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 1955 */     int i = this.parallelism;int j = (int)paramLong;
/* 1956 */     int k; if ((arrayOfWorkQueue != null) && ((k = arrayOfWorkQueue.length - 1) >= 0) && (j >= 0) && (this.ctl == paramLong)) {
/* 1957 */       WorkQueue localWorkQueue = arrayOfWorkQueue[(j & k)];
/* 1958 */       if ((j != 0) && (localWorkQueue != null))
/*      */       {
/* 1960 */         long l1 = localWorkQueue.nextWait & 0x7FFFFFFF | paramLong & 0xFFFFFFFF00000000;
/*      */         
/* 1962 */         int m = j + 65536 & 0x7FFFFFFF;
/* 1963 */         if ((localWorkQueue.eventCount == (j | 0x80000000)) && (U.compareAndSwapLong(this, CTL, paramLong, l1)))
/*      */         {
/* 1965 */           localWorkQueue.eventCount = m;
/* 1966 */           Thread localThread; if ((localThread = localWorkQueue.parker) != null)
/* 1967 */             U.unpark(localThread);
/* 1968 */           return true;
/*      */         } } else { int n;
/*      */         long l2;
/* 1971 */         if (((n = (short)(int)(paramLong >>> 32)) >= 0) && ((int)(paramLong >> 48) + i > 1))
/*      */         {
/* 1973 */           l2 = paramLong - 281474976710656L & 0xFFFF000000000000 | paramLong & 0xFFFFFFFFFFFF;
/* 1974 */           if (U.compareAndSwapLong(this, CTL, paramLong, l2)) {
/* 1975 */             return true;
/*      */           }
/* 1977 */         } else if (n + i < 32767) {
/* 1978 */           l2 = paramLong + 4294967296L & 0xFFFF00000000 | paramLong & 0xFFFF0000FFFFFFFF;
/* 1979 */           if (U.compareAndSwapLong(this, CTL, paramLong, l2))
/*      */           {
/* 1981 */             Object localObject = null;
/* 1982 */             ForkJoinWorkerThread localForkJoinWorkerThread = null;
/*      */             try { ForkJoinWorkerThreadFactory localForkJoinWorkerThreadFactory;
/* 1984 */               if (((localForkJoinWorkerThreadFactory = this.factory) != null) && ((localForkJoinWorkerThread = localForkJoinWorkerThreadFactory.newThread(this)) != null))
/*      */               {
/* 1986 */                 localForkJoinWorkerThread.start();
/* 1987 */                 return true;
/*      */               }
/*      */             } catch (Throwable localThrowable) {
/* 1990 */               localObject = localThrowable;
/*      */             }
/* 1992 */             deregisterWorker(localForkJoinWorkerThread, (Throwable)localObject);
/*      */           }
/*      */         }
/*      */       } }
/* 1996 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int awaitJoin(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2007 */     int i = 0;
/* 2008 */     if ((paramForkJoinTask != null) && ((i = paramForkJoinTask.status) >= 0) && (paramWorkQueue != null)) {
/* 2009 */       ForkJoinTask localForkJoinTask = paramWorkQueue.currentJoin;
/* 2010 */       paramWorkQueue.currentJoin = paramForkJoinTask;
/* 2011 */       while ((paramWorkQueue.tryRemoveAndExec(paramForkJoinTask)) && ((i = paramForkJoinTask.status) >= 0)) {}
/*      */       
/* 2013 */       if ((i >= 0) && ((paramForkJoinTask instanceof CountedCompleter)))
/* 2014 */         i = helpComplete(paramWorkQueue, (CountedCompleter)paramForkJoinTask);
/* 2015 */       long l1 = 0L;
/* 2016 */       while ((i >= 0) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2017 */         if (((i = tryHelpStealer(paramWorkQueue, paramForkJoinTask)) == 0) && ((i = paramForkJoinTask.status) >= 0))
/*      */         {
/* 2019 */           if (!tryCompensate(l1)) {
/* 2020 */             l1 = this.ctl;
/*      */           } else {
/* 2022 */             if ((paramForkJoinTask.trySetSignal()) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2023 */               synchronized (paramForkJoinTask) {
/* 2024 */                 if (paramForkJoinTask.status >= 0) {
/*      */                   try {
/* 2026 */                     paramForkJoinTask.wait();
/*      */ 
/*      */                   }
/*      */                   catch (InterruptedException localInterruptedException) {}
/*      */                 } else
/* 2031 */                   paramForkJoinTask.notifyAll();
/*      */               }
/*      */             }
/*      */             long l2;
/* 2035 */             while (!U.compareAndSwapLong(this, CTL, l2 = this.ctl, l2 & 0xFFFFFFFFFFFF | (l2 & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 2042 */       paramWorkQueue.currentJoin = localForkJoinTask;
/*      */     }
/* 2044 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void helpJoinOnce(WorkQueue paramWorkQueue, ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */ 
/* 2057 */     if ((paramWorkQueue != null) && (paramForkJoinTask != null) && ((i = paramForkJoinTask.status) >= 0)) {
/* 2058 */       ForkJoinTask localForkJoinTask = paramWorkQueue.currentJoin;
/* 2059 */       paramWorkQueue.currentJoin = paramForkJoinTask;
/* 2060 */       while ((paramWorkQueue.tryRemoveAndExec(paramForkJoinTask)) && ((i = paramForkJoinTask.status) >= 0)) {}
/*      */       
/* 2062 */       if (i >= 0) {
/* 2063 */         if ((paramForkJoinTask instanceof CountedCompleter))
/* 2064 */           helpComplete(paramWorkQueue, (CountedCompleter)paramForkJoinTask);
/* 2065 */         while ((paramForkJoinTask.status >= 0) && (tryHelpStealer(paramWorkQueue, paramForkJoinTask) > 0)) {}
/*      */       }
/*      */       
/* 2068 */       paramWorkQueue.currentJoin = localForkJoinTask;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private WorkQueue findNonEmptyStealQueue()
/*      */   {
/* 2078 */     int i = ThreadLocalRandom.current().nextInt();
/*      */     for (;;) {
/* 2080 */       int j = this.plock;
/* 2081 */       WorkQueue[] arrayOfWorkQueue; int k; if (((arrayOfWorkQueue = this.workQueues) != null) && ((k = arrayOfWorkQueue.length - 1) >= 0)) {
/* 2082 */         for (int m = k + 1 << 2; m >= 0; m--) { WorkQueue localWorkQueue;
/* 2083 */           if (((localWorkQueue = arrayOfWorkQueue[((i - m << 1 | 0x1) & k)]) != null) && (localWorkQueue.base - localWorkQueue.top < 0))
/*      */           {
/* 2085 */             return localWorkQueue; }
/*      */         }
/*      */       }
/* 2088 */       if (this.plock == j) {
/* 2089 */         return null;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final void helpQuiescePool(WorkQueue paramWorkQueue)
/*      */   {
/* 2100 */     ForkJoinTask localForkJoinTask1 = paramWorkQueue.currentSteal;
/* 2101 */     int i = 1;
/*      */     for (;;) { ForkJoinTask localForkJoinTask2;
/* 2103 */       if ((localForkJoinTask2 = paramWorkQueue.nextLocalTask()) != null) {
/* 2104 */         localForkJoinTask2.doExec(); } else { WorkQueue localWorkQueue;
/* 2105 */         long l1; if ((localWorkQueue = findNonEmptyStealQueue()) != null) {
/* 2106 */           if (i == 0) {
/* 2107 */             i = 1;
/* 2108 */             while (!U.compareAndSwapLong(this, CTL, l1 = this.ctl, l1 & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) + 281474976710656L)) {}
/*      */           }
/*      */           
/*      */           int j;
/*      */           
/* 2113 */           if (((j = localWorkQueue.base) - localWorkQueue.top < 0) && ((localForkJoinTask2 = localWorkQueue.pollAt(j)) != null)) {
/* 2114 */             (paramWorkQueue.currentSteal = localForkJoinTask2).doExec();
/* 2115 */             paramWorkQueue.currentSteal = localForkJoinTask1;
/*      */           }
/*      */         }
/* 2118 */         else if (i != 0) {
/* 2119 */           long l2 = (l1 = this.ctl) & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) - 281474976710656L;
/* 2120 */           if ((int)(l2 >> 48) + this.parallelism == 0)
/*      */             break;
/* 2122 */           if (U.compareAndSwapLong(this, CTL, l1, l2))
/* 2123 */             i = 0;
/*      */         } else {
/* 2125 */           if (((int)((l1 = this.ctl) >> 48) + this.parallelism <= 0) && (U.compareAndSwapLong(this, CTL, l1, l1 & 0xFFFFFFFFFFFF | (l1 & 0xFFFF000000000000) + 281474976710656L))) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   final ForkJoinTask<?> nextTaskFor(WorkQueue paramWorkQueue)
/*      */   {
/*      */     for (;;)
/*      */     {
/*      */       ForkJoinTask localForkJoinTask;
/*      */       
/* 2141 */       if ((localForkJoinTask = paramWorkQueue.nextLocalTask()) != null)
/* 2142 */         return localForkJoinTask;
/* 2143 */       WorkQueue localWorkQueue; if ((localWorkQueue = findNonEmptyStealQueue()) == null)
/* 2144 */         return null;
/* 2145 */       int i; if (((i = localWorkQueue.base) - localWorkQueue.top < 0) && ((localForkJoinTask = localWorkQueue.pollAt(i)) != null)) {
/* 2146 */         return localForkJoinTask;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int getSurplusQueuedTaskCount()
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2198 */     if (((localThread = Thread.currentThread()) instanceof ForkJoinWorkerThread)) { ForkJoinWorkerThread localForkJoinWorkerThread;
/* 2199 */       ForkJoinPool localForkJoinPool; int i = (localForkJoinPool = (localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool).parallelism;
/* 2200 */       WorkQueue localWorkQueue; int j = (localWorkQueue = localForkJoinWorkerThread.workQueue).top - localWorkQueue.base;
/* 2201 */       int k = (int)(localForkJoinPool.ctl >> 48) + i;
/* 2202 */       return j - (k > i >>>= 1 ? 4 : k > i >>>= 1 ? 2 : k > i >>>= 1 ? 1 : k > i >>>= 1 ? 0 : 8);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2208 */     return 0;
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
/*      */   private boolean tryTerminate(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 2229 */     if (this == common)
/* 2230 */       return false;
/* 2231 */     int i; if ((i = this.plock) >= 0) {
/* 2232 */       if (!paramBoolean2)
/* 2233 */         return false;
/* 2234 */       if (((i & 0x2) != 0) || (!U.compareAndSwapInt(this, PLOCK, , i)))
/*      */       {
/* 2236 */         i = acquirePlock(); }
/* 2237 */       int j = i + 2 & 0x7FFFFFFF | 0x80000000;
/* 2238 */       if (!U.compareAndSwapInt(this, PLOCK, i, j))
/* 2239 */         releasePlock(j);
/*      */     }
/*      */     for (;;) { long l1;
/* 2242 */       if (((l1 = this.ctl) & 0x80000000) != 0L) {
/* 2243 */         if ((short)(int)(l1 >>> 32) + this.parallelism <= 0) {
/* 2244 */           synchronized (this) {
/* 2245 */             notifyAll();
/*      */           }
/*      */         }
/* 2248 */         return true; }
/*      */       Object localObject2;
/* 2250 */       if (!paramBoolean1)
/*      */       {
/* 2252 */         if ((int)(l1 >> 48) + this.parallelism > 0)
/* 2253 */           return false;
/* 2254 */         if ((??? = this.workQueues) != null) {
/* 2255 */           for (int m = 0; m < ???.length; m++) {
/* 2256 */             if (((localObject2 = ???[m]) != null) && ((!((WorkQueue)localObject2).isEmpty()) || (((m & 0x1) != 0) && (((WorkQueue)localObject2).eventCount >= 0))))
/*      */             {
/*      */ 
/* 2259 */               signalWork((WorkQueue[])???, (WorkQueue)localObject2);
/* 2260 */               return false;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2265 */       if (U.compareAndSwapLong(this, CTL, l1, l1 | 0x80000000)) {
/* 2266 */         for (int k = 0; k < 3; k++)
/*      */         {
/* 2268 */           if ((localObject2 = this.workQueues) != null) {
/* 2269 */             int n = localObject2.length;
/* 2270 */             Object localObject3; for (int i1 = 0; i1 < n; i1++) {
/* 2271 */               if ((localObject3 = localObject2[i1]) != null) {
/* 2272 */                 ((WorkQueue)localObject3).qlock = -1;
/* 2273 */                 if (k > 0) {
/* 2274 */                   ((WorkQueue)localObject3).cancelAll();
/* 2275 */                   ForkJoinWorkerThread localForkJoinWorkerThread; if ((k > 1) && ((localForkJoinWorkerThread = ((WorkQueue)localObject3).owner) != null)) {
/* 2276 */                     if (!localForkJoinWorkerThread.isInterrupted()) {
/*      */                       try {
/* 2278 */                         localForkJoinWorkerThread.interrupt();
/*      */                       }
/*      */                       catch (Throwable localThrowable) {}
/*      */                     }
/* 2282 */                     U.unpark(localForkJoinWorkerThread);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*      */             long l2;
/*      */             int i2;
/* 2290 */             while (((i2 = (int)(l2 = this.ctl) & 0x7FFFFFFF) != 0) && ((i1 = i2 & 0xFFFF) < n) && (i1 >= 0) && ((localObject3 = localObject2[i1]) != null))
/*      */             {
/* 2292 */               long l3 = ((WorkQueue)localObject3).nextWait & 0x7FFFFFFF | l2 + 281474976710656L & 0xFFFF000000000000 | l2 & 0xFFFF80000000;
/*      */               
/*      */ 
/* 2295 */               if ((((WorkQueue)localObject3).eventCount == (i2 | 0x80000000)) && (U.compareAndSwapLong(this, CTL, l2, l3)))
/*      */               {
/* 2297 */                 ((WorkQueue)localObject3).eventCount = (i2 + 65536 & 0x7FFFFFFF);
/* 2298 */                 ((WorkQueue)localObject3).qlock = -1;
/* 2299 */                 Thread localThread; if ((localThread = ((WorkQueue)localObject3).parker) != null) {
/* 2300 */                   U.unpark(localThread);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static WorkQueue commonSubmitterQueue()
/*      */   {
/*      */     Submitter localSubmitter;
/*      */     ForkJoinPool localForkJoinPool;
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     int i;
/* 2317 */     return ((localSubmitter = (Submitter)submitters.get()) != null) && ((localForkJoinPool = common) != null) && ((arrayOfWorkQueue = localForkJoinPool.workQueues) != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) ? arrayOfWorkQueue[(i & localSubmitter.seed & 0x7E)] : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final boolean tryExternalUnpush(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2329 */     Submitter localSubmitter = (Submitter)submitters.get();
/* 2330 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 2331 */     boolean bool = false;
/* 2332 */     int i; WorkQueue localWorkQueue; int j; ForkJoinTask[] arrayOfForkJoinTask; if ((localSubmitter != null) && (arrayOfWorkQueue != null) && ((i = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue = arrayOfWorkQueue[(localSubmitter.seed & i & 0x7E)]) != null) && (localWorkQueue.base != (j = localWorkQueue.top)) && ((arrayOfForkJoinTask = localWorkQueue.array) != null))
/*      */     {
/*      */ 
/*      */ 
/* 2336 */       long l = ((arrayOfForkJoinTask.length - 1 & j - 1) << ASHIFT) + ABASE;
/* 2337 */       if ((U.getObject(arrayOfForkJoinTask, l) == paramForkJoinTask) && (U.compareAndSwapInt(localWorkQueue, QLOCK, 0, 1)))
/*      */       {
/* 2339 */         if ((localWorkQueue.top == j) && (localWorkQueue.array == arrayOfForkJoinTask) && (U.compareAndSwapObject(arrayOfForkJoinTask, l, paramForkJoinTask, null)))
/*      */         {
/* 2341 */           localWorkQueue.top = (j - 1);
/* 2342 */           bool = true;
/*      */         }
/* 2344 */         localWorkQueue.qlock = 0;
/*      */       }
/*      */     }
/* 2347 */     return bool;
/*      */   }
/*      */   
/*      */   final int externalHelpComplete(CountedCompleter<?> paramCountedCompleter)
/*      */   {
/* 2352 */     Submitter localSubmitter = (Submitter)submitters.get();
/* 2353 */     WorkQueue[] arrayOfWorkQueue = this.workQueues;
/* 2354 */     int i = 0;
/* 2355 */     int j; int k; WorkQueue localWorkQueue1; if ((localSubmitter != null) && (arrayOfWorkQueue != null) && ((j = arrayOfWorkQueue.length - 1) >= 0) && ((localWorkQueue1 = arrayOfWorkQueue[((k = localSubmitter.seed) & j & 0x7E)]) != null) && (paramCountedCompleter != null))
/*      */     {
/* 2357 */       int m = j + j + 1;
/* 2358 */       long l = 0L;
/* 2359 */       k |= 0x1;
/* 2360 */       int n = m;
/* 2362 */       for (; 
/* 2362 */           (i = paramCountedCompleter.status) >= 0; k += 2)
/*      */       {
/*      */ 
/*      */ 
/* 2364 */         if (localWorkQueue1.externalPopAndExecCC(paramCountedCompleter)) {
/* 2365 */           n = m;
/* 2366 */         } else { if ((i = paramCountedCompleter.status) < 0) break;
/*      */           WorkQueue localWorkQueue2;
/* 2368 */           if (((localWorkQueue2 = arrayOfWorkQueue[(k & j)]) != null) && (localWorkQueue2.pollAndExecCC(paramCountedCompleter))) {
/* 2369 */             n = m;
/* 2370 */           } else { n--; if (n < 0) {
/* 2371 */               if (l == (l = this.ctl))
/*      */                 break;
/* 2373 */               n = m;
/*      */             }
/*      */           }
/*      */         } } }
/* 2377 */     return i;
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
/*      */   public ForkJoinPool()
/*      */   {
/* 2396 */     this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt)
/*      */   {
/* 2415 */     this(paramInt, defaultForkJoinWorkerThreadFactory, null, false);
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
/*      */   public ForkJoinPool(int paramInt, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, boolean paramBoolean)
/*      */   {
/* 2446 */     this(checkParallelism(paramInt), checkFactory(paramForkJoinWorkerThreadFactory), paramUncaughtExceptionHandler, paramBoolean ? 1 : 0, "ForkJoinPool-" + nextPoolId() + "-worker-");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2451 */     checkPermission();
/*      */   }
/*      */   
/*      */   private static int checkParallelism(int paramInt) {
/* 2455 */     if ((paramInt <= 0) || (paramInt > 32767))
/* 2456 */       throw new IllegalArgumentException();
/* 2457 */     return paramInt;
/*      */   }
/*      */   
/*      */   private static ForkJoinWorkerThreadFactory checkFactory(ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory)
/*      */   {
/* 2462 */     if (paramForkJoinWorkerThreadFactory == null)
/* 2463 */       throw new NullPointerException();
/* 2464 */     return paramForkJoinWorkerThreadFactory;
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
/*      */   private ForkJoinPool(int paramInt1, ForkJoinWorkerThreadFactory paramForkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler, int paramInt2, String paramString)
/*      */   {
/* 2477 */     this.workerNamePrefix = paramString;
/* 2478 */     this.factory = paramForkJoinWorkerThreadFactory;
/* 2479 */     this.ueh = paramUncaughtExceptionHandler;
/* 2480 */     this.mode = ((short)paramInt2);
/* 2481 */     this.parallelism = ((short)paramInt1);
/* 2482 */     long l = -paramInt1;
/* 2483 */     this.ctl = (l << 48 & 0xFFFF000000000000 | l << 32 & 0xFFFF00000000);
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
/*      */   public static ForkJoinPool commonPool()
/*      */   {
/* 2501 */     return common;
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
/*      */   public <T> T invoke(ForkJoinTask<T> paramForkJoinTask)
/*      */   {
/* 2523 */     if (paramForkJoinTask == null)
/* 2524 */       throw new NullPointerException();
/* 2525 */     externalPush(paramForkJoinTask);
/* 2526 */     return (T)paramForkJoinTask.join();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void execute(ForkJoinTask<?> paramForkJoinTask)
/*      */   {
/* 2538 */     if (paramForkJoinTask == null)
/* 2539 */       throw new NullPointerException();
/* 2540 */     externalPush(paramForkJoinTask);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void execute(Runnable paramRunnable)
/*      */   {
/* 2551 */     if (paramRunnable == null)
/* 2552 */       throw new NullPointerException();
/*      */     Object localObject;
/* 2554 */     if ((paramRunnable instanceof ForkJoinTask)) {
/* 2555 */       localObject = (ForkJoinTask)paramRunnable;
/*      */     } else
/* 2557 */       localObject = new ForkJoinTask.RunnableExecuteAction(paramRunnable);
/* 2558 */     externalPush((ForkJoinTask)localObject);
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
/*      */   public <T> ForkJoinTask<T> submit(ForkJoinTask<T> paramForkJoinTask)
/*      */   {
/* 2571 */     if (paramForkJoinTask == null)
/* 2572 */       throw new NullPointerException();
/* 2573 */     externalPush(paramForkJoinTask);
/* 2574 */     return paramForkJoinTask;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> ForkJoinTask<T> submit(Callable<T> paramCallable)
/*      */   {
/* 2583 */     ForkJoinTask.AdaptedCallable localAdaptedCallable = new ForkJoinTask.AdaptedCallable(paramCallable);
/* 2584 */     externalPush(localAdaptedCallable);
/* 2585 */     return localAdaptedCallable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> ForkJoinTask<T> submit(Runnable paramRunnable, T paramT)
/*      */   {
/* 2594 */     ForkJoinTask.AdaptedRunnable localAdaptedRunnable = new ForkJoinTask.AdaptedRunnable(paramRunnable, paramT);
/* 2595 */     externalPush(localAdaptedRunnable);
/* 2596 */     return localAdaptedRunnable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ForkJoinTask<?> submit(Runnable paramRunnable)
/*      */   {
/* 2605 */     if (paramRunnable == null)
/* 2606 */       throw new NullPointerException();
/*      */     Object localObject;
/* 2608 */     if ((paramRunnable instanceof ForkJoinTask)) {
/* 2609 */       localObject = (ForkJoinTask)paramRunnable;
/*      */     } else
/* 2611 */       localObject = new ForkJoinTask.AdaptedRunnableAction(paramRunnable);
/* 2612 */     externalPush((ForkJoinTask)localObject);
/* 2613 */     return (ForkJoinTask<?>)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> paramCollection)
/*      */   {
/* 2624 */     ArrayList localArrayList1 = new ArrayList(paramCollection.size());
/*      */     
/* 2626 */     int i = 0;
/*      */     try {
/* 2628 */       for (Callable localCallable : paramCollection) {
/* 2629 */         ForkJoinTask.AdaptedCallable localAdaptedCallable = new ForkJoinTask.AdaptedCallable(localCallable);
/* 2630 */         localArrayList1.add(localAdaptedCallable);
/* 2631 */         externalPush(localAdaptedCallable);
/*      */       }
/* 2633 */       int j = 0; for (int k = localArrayList1.size(); j < k; j++)
/* 2634 */         ((ForkJoinTask)localArrayList1.get(j)).quietlyJoin();
/* 2635 */       i = 1;
/* 2636 */       int m; return localArrayList1;
/*      */     } finally {
/* 2638 */       if (i == 0) {
/* 2639 */         int n = 0; for (int i1 = localArrayList1.size(); n < i1; n++) {
/* 2640 */           ((Future)localArrayList1.get(n)).cancel(false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public ForkJoinWorkerThreadFactory getFactory()
/*      */   {
/* 2650 */     return this.factory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler()
/*      */   {
/* 2660 */     return this.ueh;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int getParallelism()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/* 2670 */     return (i = this.parallelism) > 0 ? i : 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int getCommonPoolParallelism()
/*      */   {
/* 2680 */     return commonParallelism;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getPoolSize()
/*      */   {
/* 2692 */     return this.parallelism + (short)(int)(this.ctl >>> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getAsyncMode()
/*      */   {
/* 2702 */     return this.mode == 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRunningThreadCount()
/*      */   {
/* 2714 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2716 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2717 */       for (int j = 1; j < arrayOfWorkQueue.length; j += 2) { WorkQueue localWorkQueue;
/* 2718 */         if (((localWorkQueue = arrayOfWorkQueue[j]) != null) && (localWorkQueue.isApparentlyUnblocked()))
/* 2719 */           i++;
/*      */       }
/*      */     }
/* 2722 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getActiveThreadCount()
/*      */   {
/* 2733 */     int i = this.parallelism + (int)(this.ctl >> 48);
/* 2734 */     return i <= 0 ? 0 : i;
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
/*      */   public boolean isQuiescent()
/*      */   {
/* 2749 */     return this.parallelism + (int)(this.ctl >> 48) <= 0;
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
/*      */   public long getStealCount()
/*      */   {
/* 2764 */     long l = this.stealCount;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2766 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2767 */       for (int i = 1; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2768 */         if ((localWorkQueue = arrayOfWorkQueue[i]) != null)
/* 2769 */           l += localWorkQueue.nsteals;
/*      */       }
/*      */     }
/* 2772 */     return l;
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
/*      */   public long getQueuedTaskCount()
/*      */   {
/* 2786 */     long l = 0L;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2788 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2789 */       for (int i = 1; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2790 */         if ((localWorkQueue = arrayOfWorkQueue[i]) != null)
/* 2791 */           l += localWorkQueue.queueSize();
/*      */       }
/*      */     }
/* 2794 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getQueuedSubmissionCount()
/*      */   {
/* 2805 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2807 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2808 */       for (int j = 0; j < arrayOfWorkQueue.length; j += 2) { WorkQueue localWorkQueue;
/* 2809 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null)
/* 2810 */           i += localWorkQueue.queueSize();
/*      */       }
/*      */     }
/* 2813 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasQueuedSubmissions()
/*      */   {
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     
/*      */ 
/* 2824 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2825 */       for (int i = 0; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2826 */         if (((localWorkQueue = arrayOfWorkQueue[i]) != null) && (!localWorkQueue.isEmpty()))
/* 2827 */           return true;
/*      */       }
/*      */     }
/* 2830 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ForkJoinTask<?> pollSubmission()
/*      */   {
/*      */     WorkQueue[] arrayOfWorkQueue;
/*      */     
/*      */ 
/*      */ 
/* 2842 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2843 */       for (int i = 0; i < arrayOfWorkQueue.length; i += 2) { WorkQueue localWorkQueue;
/* 2844 */         ForkJoinTask localForkJoinTask; if (((localWorkQueue = arrayOfWorkQueue[i]) != null) && ((localForkJoinTask = localWorkQueue.poll()) != null))
/* 2845 */           return localForkJoinTask;
/*      */       }
/*      */     }
/* 2848 */     return null;
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
/*      */   protected int drainTasksTo(Collection<? super ForkJoinTask<?>> paramCollection)
/*      */   {
/* 2869 */     int i = 0;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2871 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2872 */       for (int j = 0; j < arrayOfWorkQueue.length; j++) { WorkQueue localWorkQueue;
/* 2873 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null) { ForkJoinTask localForkJoinTask;
/* 2874 */           while ((localForkJoinTask = localWorkQueue.poll()) != null) {
/* 2875 */             paramCollection.add(localForkJoinTask);
/* 2876 */             i++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2881 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 2893 */     long l1 = 0L;long l2 = 0L;int i = 0;
/* 2894 */     long l3 = this.stealCount;
/* 2895 */     long l4 = this.ctl;
/*      */     WorkQueue[] arrayOfWorkQueue;
/* 2897 */     if ((arrayOfWorkQueue = this.workQueues) != null) {
/* 2898 */       for (j = 0; j < arrayOfWorkQueue.length; j++) { WorkQueue localWorkQueue;
/* 2899 */         if ((localWorkQueue = arrayOfWorkQueue[j]) != null) {
/* 2900 */           k = localWorkQueue.queueSize();
/* 2901 */           if ((j & 0x1) == 0) {
/* 2902 */             l2 += k;
/*      */           } else {
/* 2904 */             l1 += k;
/* 2905 */             l3 += localWorkQueue.nsteals;
/* 2906 */             if (localWorkQueue.isApparentlyUnblocked())
/* 2907 */               i++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 2912 */     int j = this.parallelism;
/* 2913 */     int k = j + (short)(int)(l4 >>> 32);
/* 2914 */     int m = j + (int)(l4 >> 48);
/* 2915 */     if (m < 0)
/* 2916 */       m = 0;
/*      */     String str;
/* 2918 */     if ((l4 & 0x80000000) != 0L) {
/* 2919 */       str = k == 0 ? "Terminated" : "Terminating";
/*      */     } else
/* 2921 */       str = this.plock < 0 ? "Shutting down" : "Running";
/* 2922 */     return super.toString() + "[" + str + ", parallelism = " + j + ", size = " + k + ", active = " + m + ", running = " + i + ", steals = " + l3 + ", tasks = " + l1 + ", submissions = " + l2 + "]";
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
/*      */   public void shutdown()
/*      */   {
/* 2949 */     checkPermission();
/* 2950 */     tryTerminate(false, true);
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
/*      */   public List<Runnable> shutdownNow()
/*      */   {
/* 2972 */     checkPermission();
/* 2973 */     tryTerminate(true, true);
/* 2974 */     return Collections.emptyList();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTerminated()
/*      */   {
/* 2983 */     long l = this.ctl;
/* 2984 */     return ((l & 0x80000000) != 0L) && ((short)(int)(l >>> 32) + this.parallelism <= 0);
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
/*      */   public boolean isTerminating()
/*      */   {
/* 3002 */     long l = this.ctl;
/* 3003 */     return ((l & 0x80000000) != 0L) && ((short)(int)(l >>> 32) + this.parallelism > 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isShutdown()
/*      */   {
/* 3013 */     return this.plock < 0;
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
/*      */   public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
/*      */     throws InterruptedException
/*      */   {
/* 3032 */     if (Thread.interrupted())
/* 3033 */       throw new InterruptedException();
/* 3034 */     if (this == common) {
/* 3035 */       awaitQuiescence(paramLong, paramTimeUnit);
/* 3036 */       return false;
/*      */     }
/* 3038 */     long l1 = paramTimeUnit.toNanos(paramLong);
/* 3039 */     if (isTerminated())
/* 3040 */       return true;
/* 3041 */     if (l1 <= 0L)
/* 3042 */       return false;
/* 3043 */     long l2 = System.nanoTime() + l1;
/* 3044 */     synchronized (this)
/*      */     {
/* 3046 */       if (isTerminated())
/* 3047 */         return true;
/* 3048 */       if (l1 <= 0L)
/* 3049 */         return false;
/* 3050 */       long l3 = TimeUnit.NANOSECONDS.toMillis(l1);
/* 3051 */       wait(l3 > 0L ? l3 : 1L);
/* 3052 */       l1 = l2 - System.nanoTime();
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
/*      */   public boolean awaitQuiescence(long paramLong, TimeUnit paramTimeUnit)
/*      */   {
/* 3069 */     long l1 = paramTimeUnit.toNanos(paramLong);
/*      */     
/* 3071 */     Thread localThread = Thread.currentThread();
/* 3072 */     ForkJoinWorkerThread localForkJoinWorkerThread; if (((localThread instanceof ForkJoinWorkerThread)) && ((localForkJoinWorkerThread = (ForkJoinWorkerThread)localThread).pool == this))
/*      */     {
/* 3074 */       helpQuiescePool(localForkJoinWorkerThread.workQueue);
/* 3075 */       return true;
/*      */     }
/* 3077 */     long l2 = System.nanoTime();
/*      */     
/* 3079 */     int i = 0;
/* 3080 */     int j = 1;
/* 3081 */     WorkQueue[] arrayOfWorkQueue; int k; while ((!isQuiescent()) && ((arrayOfWorkQueue = this.workQueues) != null) && ((k = arrayOfWorkQueue.length - 1) >= 0))
/*      */     {
/* 3083 */       if (j == 0) {
/* 3084 */         if (System.nanoTime() - l2 > l1)
/* 3085 */           return false;
/* 3086 */         Thread.yield();
/*      */       }
/* 3088 */       j = 0;
/* 3089 */       for (int m = k + 1 << 2; m >= 0; m--) { WorkQueue localWorkQueue;
/*      */         int n;
/* 3091 */         if (((localWorkQueue = arrayOfWorkQueue[(i++ & k)]) != null) && ((n = localWorkQueue.base) - localWorkQueue.top < 0)) {
/* 3092 */           j = 1;
/* 3093 */           ForkJoinTask localForkJoinTask; if ((localForkJoinTask = localWorkQueue.pollAt(n)) == null) break;
/* 3094 */           localForkJoinTask.doExec(); break;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 3099 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void quiesceCommonPool()
/*      */   {
/* 3107 */     common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void managedBlock(ManagedBlocker paramManagedBlocker)
/*      */     throws InterruptedException
/*      */   {
/* 3206 */     Thread localThread = Thread.currentThread();
/* 3207 */     if ((localThread instanceof ForkJoinWorkerThread)) {
/* 3208 */       ForkJoinPool localForkJoinPool = ((ForkJoinWorkerThread)localThread).pool;
/* 3209 */       while (!paramManagedBlocker.isReleasable()) {
/* 3210 */         if (localForkJoinPool.tryCompensate(localForkJoinPool.ctl)) {
/*      */           try {
/* 3212 */             do { if (paramManagedBlocker.isReleasable()) break; } while (!paramManagedBlocker.block());
/*      */           }
/*      */           finally {
/* 3215 */             localForkJoinPool.incrementActiveCount();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3222 */     while ((!paramManagedBlocker.isReleasable()) && (!paramManagedBlocker.block())) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
/*      */   {
/* 3232 */     return new ForkJoinTask.AdaptedRunnable(paramRunnable, paramT);
/*      */   }
/*      */   
/*      */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable) {
/* 3236 */     return new ForkJoinTask.AdaptedCallable(paramCallable);
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
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 3254 */       U = getUnsafe();
/* 3255 */       Class localClass1 = ForkJoinPool.class;
/* 3256 */       CTL = U.objectFieldOffset(localClass1.getDeclaredField("ctl"));
/*      */       
/* 3258 */       STEALCOUNT = U.objectFieldOffset(localClass1.getDeclaredField("stealCount"));
/*      */       
/* 3260 */       PLOCK = U.objectFieldOffset(localClass1.getDeclaredField("plock"));
/*      */       
/* 3262 */       INDEXSEED = U.objectFieldOffset(localClass1.getDeclaredField("indexSeed"));
/*      */       
/* 3264 */       Class localClass2 = Thread.class;
/* 3265 */       PARKBLOCKER = U.objectFieldOffset(localClass2.getDeclaredField("parkBlocker"));
/*      */       
/* 3267 */       Class localClass3 = WorkQueue.class;
/* 3268 */       QBASE = U.objectFieldOffset(localClass3.getDeclaredField("base"));
/*      */       
/* 3270 */       QLOCK = U.objectFieldOffset(localClass3.getDeclaredField("qlock"));
/*      */       
/* 3272 */       Class localClass4 = ForkJoinTask[].class;
/* 3273 */       ABASE = U.arrayBaseOffset(localClass4);
/* 3274 */       int j = U.arrayIndexScale(localClass4);
/* 3275 */       if ((j & j - 1) != 0)
/* 3276 */         throw new Error("data type scale not a power of two");
/* 3277 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(j);
/*      */     } catch (Exception localException) {
/* 3279 */       throw new Error(localException);
/*      */     }
/*      */     
/* 3282 */     submitters = new ThreadLocal();
/* 3283 */     defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
/*      */     
/* 3285 */     modifyThreadPermission = new RuntimePermission("modifyThread");
/*      */     
/* 3287 */     common = (ForkJoinPool)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/* 3289 */       public ForkJoinPool run() { return ForkJoinPool.access$100(); } });
/* 3290 */     int i = common.parallelism;
/* 3291 */     commonParallelism = i > 0 ? i : 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ForkJoinPool makeCommonPool()
/*      */   {
/* 3299 */     int i = -1;
/* 3300 */     ForkJoinWorkerThreadFactory localForkJoinWorkerThreadFactory = defaultForkJoinWorkerThreadFactory;
/*      */     
/* 3302 */     Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = null;
/*      */     try {
/* 3304 */       String str1 = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
/*      */       
/* 3306 */       String str2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
/*      */       
/* 3308 */       String str3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
/*      */       
/* 3310 */       if (str1 != null)
/* 3311 */         i = Integer.parseInt(str1);
/* 3312 */       if (str2 != null) {
/* 3313 */         localForkJoinWorkerThreadFactory = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(str2).newInstance();
/*      */       }
/* 3315 */       if (str3 != null) {
/* 3316 */         localUncaughtExceptionHandler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(str3).newInstance();
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/* 3321 */     if ((i < 0) && ((i = Runtime.getRuntime().availableProcessors() - 1) < 0))
/*      */     {
/* 3323 */       i = 0; }
/* 3324 */     if (i > 32767)
/* 3325 */       i = 32767;
/* 3326 */     return new ForkJoinPool(i, localForkJoinWorkerThreadFactory, localUncaughtExceptionHandler, 0, "ForkJoinPool.commonPool-worker-");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Unsafe getUnsafe()
/*      */   {
/*      */     try
/*      */     {
/* 3339 */       return Unsafe.getUnsafe();
/*      */     } catch (SecurityException localSecurityException) {
/*      */       try {
/* 3342 */         (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */         {
/*      */           public Unsafe run() throws Exception {
/* 3345 */             Class localClass = Unsafe.class;
/* 3346 */             for (Field localField : localClass.getDeclaredFields()) {
/* 3347 */               localField.setAccessible(true);
/* 3348 */               Object localObject = localField.get(null);
/* 3349 */               if (localClass.isInstance(localObject))
/* 3350 */                 return (Unsafe)localClass.cast(localObject);
/*      */             }
/* 3352 */             throw new NoSuchFieldError("the Unsafe");
/*      */           }
/*      */         });
/* 3355 */       } catch (PrivilegedActionException localPrivilegedActionException) { throw new RuntimeException("Could not initialize intrinsics", localPrivilegedActionException.getCause());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract interface ManagedBlocker
/*      */   {
/*      */     public abstract boolean block()
/*      */       throws InterruptedException;
/*      */     
/*      */     public abstract boolean isReleasable();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\chmv8\ForkJoinPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */