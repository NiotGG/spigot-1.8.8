/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import org.apache.commons.lang.Validate;
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
/*     */ public final class AsynchronousExecutor<P, T, C, E extends Throwable>
/*     */ {
/*     */   public static abstract interface CallBackProvider<P, T, C, E extends Throwable>
/*     */     extends ThreadFactory
/*     */   {
/*     */     public abstract T callStage1(P paramP)
/*     */       throws Throwable;
/*     */     
/*     */     public abstract void callStage2(P paramP, T paramT)
/*     */       throws Throwable;
/*     */     
/*     */     public abstract void callStage3(P paramP, T paramT, C paramC)
/*     */       throws Throwable;
/*     */   }
/*     */   
/*  60 */   static final AtomicIntegerFieldUpdater STATE_FIELD = AtomicIntegerFieldUpdater.newUpdater(Task.class, "state");
/*     */   final CallBackProvider<P, T, C, E> provider;
/*     */   
/*     */   private static boolean set(Task $this, int expected, int value) {
/*  64 */     return STATE_FIELD.compareAndSet($this, expected, value); }
/*     */   
/*     */   class Task
/*     */     implements Runnable
/*     */   {
/*     */     static final int PENDING = 0;
/*     */     static final int STAGE_1_ASYNC = 1;
/*     */     static final int STAGE_1_SYNC = 2;
/*     */     static final int STAGE_1_COMPLETE = 3;
/*     */     static final int FINISHED = 4;
/*  74 */     volatile int state = 0;
/*     */     final P parameter;
/*     */     T object;
/*  77 */     final List<C> callbacks = new LinkedList();
/*  78 */     E t = null;
/*     */     
/*     */     Task() {
/*  81 */       this.parameter = parameter;
/*     */     }
/*     */     
/*     */     public void run() {
/*  85 */       if (initAsync()) {
/*  86 */         AsynchronousExecutor.this.finished.add(this);
/*     */       }
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     boolean initAsync()
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: iconst_0
/*     */       //   2: iconst_1
/*     */       //   3: invokestatic 77	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor:access$0	(Lorg/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task;II)Z
/*     */       //   6: ifeq +97 -> 103
/*     */       //   9: iconst_1
/*     */       //   10: istore_1
/*     */       //   11: aload_0
/*     */       //   12: invokevirtual 80	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task:init	()V
/*     */       //   15: goto +46 -> 61
/*     */       //   18: astore_2
/*     */       //   19: aload_0
/*     */       //   20: iconst_1
/*     */       //   21: iconst_3
/*     */       //   22: invokestatic 77	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor:access$0	(Lorg/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task;II)Z
/*     */       //   25: ifne +34 -> 59
/*     */       //   28: aload_0
/*     */       //   29: dup
/*     */       //   30: astore_3
/*     */       //   31: monitorenter
/*     */       //   32: aload_0
/*     */       //   33: getfield 47	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task:state	I
/*     */       //   36: iconst_2
/*     */       //   37: if_icmpeq +7 -> 44
/*     */       //   40: aload_0
/*     */       //   41: invokevirtual 85	java/lang/Object:notifyAll	()V
/*     */       //   44: aload_0
/*     */       //   45: iconst_3
/*     */       //   46: putfield 47	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task:state	I
/*     */       //   49: aload_3
/*     */       //   50: monitorexit
/*     */       //   51: goto +6 -> 57
/*     */       //   54: aload_3
/*     */       //   55: monitorexit
/*     */       //   56: athrow
/*     */       //   57: iconst_0
/*     */       //   58: istore_1
/*     */       //   59: aload_2
/*     */       //   60: athrow
/*     */       //   61: aload_0
/*     */       //   62: iconst_1
/*     */       //   63: iconst_3
/*     */       //   64: invokestatic 77	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor:access$0	(Lorg/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task;II)Z
/*     */       //   67: ifne +34 -> 101
/*     */       //   70: aload_0
/*     */       //   71: dup
/*     */       //   72: astore_3
/*     */       //   73: monitorenter
/*     */       //   74: aload_0
/*     */       //   75: getfield 47	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task:state	I
/*     */       //   78: iconst_2
/*     */       //   79: if_icmpeq +7 -> 86
/*     */       //   82: aload_0
/*     */       //   83: invokevirtual 85	java/lang/Object:notifyAll	()V
/*     */       //   86: aload_0
/*     */       //   87: iconst_3
/*     */       //   88: putfield 47	org/bukkit/craftbukkit/v1_8_R3/util/AsynchronousExecutor$Task:state	I
/*     */       //   91: aload_3
/*     */       //   92: monitorexit
/*     */       //   93: goto +6 -> 99
/*     */       //   96: aload_3
/*     */       //   97: monitorexit
/*     */       //   98: athrow
/*     */       //   99: iconst_0
/*     */       //   100: istore_1
/*     */       //   101: iload_1
/*     */       //   102: ireturn
/*     */       //   103: iconst_0
/*     */       //   104: ireturn
/*     */       // Line number table:
/*     */       //   Java source line #91	-> byte code offset #0
/*     */       //   Java source line #92	-> byte code offset #9
/*     */       //   Java source line #95	-> byte code offset #11
/*     */       //   Java source line #96	-> byte code offset #15
/*     */       //   Java source line #97	-> byte code offset #19
/*     */       //   Java source line #101	-> byte code offset #28
/*     */       //   Java source line #102	-> byte code offset #32
/*     */       //   Java source line #104	-> byte code offset #40
/*     */       //   Java source line #108	-> byte code offset #44
/*     */       //   Java source line #101	-> byte code offset #49
/*     */       //   Java source line #111	-> byte code offset #57
/*     */       //   Java source line #113	-> byte code offset #59
/*     */       //   Java source line #97	-> byte code offset #61
/*     */       //   Java source line #101	-> byte code offset #70
/*     */       //   Java source line #102	-> byte code offset #74
/*     */       //   Java source line #104	-> byte code offset #82
/*     */       //   Java source line #108	-> byte code offset #86
/*     */       //   Java source line #101	-> byte code offset #91
/*     */       //   Java source line #111	-> byte code offset #99
/*     */       //   Java source line #115	-> byte code offset #101
/*     */       //   Java source line #117	-> byte code offset #103
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	105	0	this	Task
/*     */       //   10	92	1	ret	boolean
/*     */       //   18	42	2	localObject1	Object
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   11	18	18	finally
/*     */       //   32	51	54	finally
/*     */       //   54	56	54	finally
/*     */       //   74	93	96	finally
/*     */       //   96	98	96	finally
/*     */     }
/*     */     
/*     */     void initSync()
/*     */     {
/* 122 */       if (AsynchronousExecutor.set(this, 0, 3))
/*     */       {
/* 124 */         init();
/* 125 */       } else if (AsynchronousExecutor.set(this, 1, 2))
/*     */       {
/* 127 */         synchronized (this) {
/* 128 */           if (AsynchronousExecutor.set(this, 2, 0))
/*     */           {
/* 130 */             while (this.state != 3) {
/*     */               try {
/* 132 */                 wait();
/*     */               } catch (InterruptedException e) {
/* 134 */                 Thread.currentThread().interrupt();
/* 135 */                 throw new RuntimeException("Unable to handle interruption on " + this.parameter, e);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     void init()
/*     */     {
/*     */       try
/*     */       {
/* 150 */         this.object = AsynchronousExecutor.this.provider.callStage1(this.parameter);
/*     */       } catch (Throwable t) {
/* 152 */         this.t = t;
/*     */       }
/*     */     }
/*     */     
/*     */     T get() throws Throwable
/*     */     {
/* 158 */       initSync();
/* 159 */       if (this.callbacks.isEmpty())
/*     */       {
/*     */ 
/* 162 */         this.callbacks.add(this);
/*     */       }
/* 164 */       finish();
/* 165 */       return (T)this.object;
/*     */     }
/*     */     
/*     */     void finish() throws Throwable {
/* 169 */       switch (this.state) {
/*     */       case 0: 
/*     */       case 1: 
/*     */       case 2: 
/*     */       default: 
/* 174 */         throw new IllegalStateException("Attempting to finish unprepared(" + this.state + ") task(" + this.parameter + ")");
/*     */       case 3: 
/*     */         try {
/* 177 */           if (this.t != null) {
/* 178 */             throw this.t;
/*     */           }
/* 180 */           if (this.callbacks.isEmpty()) {
/* 181 */             return;
/*     */           }
/*     */           
/* 184 */           AsynchronousExecutor.CallBackProvider<P, T, C, E> provider = AsynchronousExecutor.this.provider;
/* 185 */           P parameter = this.parameter;
/* 186 */           T object = this.object;
/*     */           
/* 188 */           provider.callStage2(parameter, object);
/* 189 */           for (C callback : this.callbacks) {
/* 190 */             if (callback != this)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/* 195 */               provider.callStage3(parameter, object, callback); }
/*     */           }
/*     */         } finally {
/* 198 */           AsynchronousExecutor.this.tasks.remove(this.parameter);
/* 199 */           this.state = 4;
/*     */         }
/* 198 */         AsynchronousExecutor.this.tasks.remove(this.parameter);
/* 199 */         this.state = 4;
/*     */       }
/*     */       
/*     */     }
/*     */     
/*     */     boolean drop()
/*     */     {
/* 206 */       if (AsynchronousExecutor.set(this, 0, 4))
/*     */       {
/* 208 */         AsynchronousExecutor.this.tasks.remove(this.parameter);
/* 209 */         return true;
/*     */       }
/*     */       
/* 212 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 218 */   final Queue<AsynchronousExecutor<P, T, C, E>.Task> finished = new ConcurrentLinkedQueue();
/* 219 */   final Map<P, AsynchronousExecutor<P, T, C, E>.Task> tasks = new HashMap();
/*     */   
/*     */ 
/*     */   final ThreadPoolExecutor pool;
/*     */   
/*     */ 
/*     */   public AsynchronousExecutor(CallBackProvider<P, T, C, E> provider, int coreSize)
/*     */   {
/* 227 */     Validate.notNull(provider, "Provider cannot be null");
/* 228 */     this.provider = provider;
/*     */     
/*     */ 
/* 231 */     this.pool = new ThreadPoolExecutor(coreSize, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), provider);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(P parameter, C callback)
/*     */   {
/* 240 */     AsynchronousExecutor<P, T, C, E>.Task task = (Task)this.tasks.get(parameter);
/* 241 */     if (task == null) {
/* 242 */       this.tasks.put(parameter, task = new Task(parameter));
/* 243 */       this.pool.execute(task);
/*     */     }
/* 245 */     task.callbacks.add(callback);
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
/*     */   public boolean drop(P parameter, C callback)
/*     */     throws IllegalStateException
/*     */   {
/* 264 */     AsynchronousExecutor<P, T, C, E>.Task task = (Task)this.tasks.get(parameter);
/* 265 */     if (task == null) {
/* 266 */       return true;
/*     */     }
/* 268 */     if (!task.callbacks.remove(callback)) {
/* 269 */       throw new IllegalStateException("Unknown " + callback + " for " + parameter);
/*     */     }
/* 271 */     if (task.callbacks.isEmpty()) {
/* 272 */       return task.drop();
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T get(P parameter)
/*     */     throws Throwable, IllegalStateException
/*     */   {
/* 284 */     AsynchronousExecutor<P, T, C, E>.Task task = (Task)this.tasks.get(parameter);
/* 285 */     if (task == null) {
/* 286 */       throw new IllegalStateException("Unknown " + parameter);
/*     */     }
/* 288 */     return (T)task.get();
/*     */   }
/*     */   
/*     */ 
/*     */   public T getSkipQueue(P parameter)
/*     */     throws Throwable
/*     */   {
/* 295 */     return (T)skipQueue(parameter);
/*     */   }
/*     */   
/*     */ 
/*     */   public T getSkipQueue(P parameter, C callback)
/*     */     throws Throwable
/*     */   {
/* 302 */     T object = skipQueue(parameter);
/* 303 */     this.provider.callStage3(parameter, object, callback);
/* 304 */     return object;
/*     */   }
/*     */   
/*     */ 
/*     */   public T getSkipQueue(P parameter, C... callbacks)
/*     */     throws Throwable
/*     */   {
/* 311 */     CallBackProvider<P, T, C, E> provider = this.provider;
/* 312 */     T object = skipQueue(parameter);
/* 313 */     Object[] arrayOfObject; int i = (arrayOfObject = callbacks).length; for (int j = 0; j < i; j++) { C callback = arrayOfObject[j];
/* 314 */       provider.callStage3(parameter, object, callback);
/*     */     }
/* 316 */     return object;
/*     */   }
/*     */   
/*     */ 
/*     */   public T getSkipQueue(P parameter, Iterable<C> callbacks)
/*     */     throws Throwable
/*     */   {
/* 323 */     CallBackProvider<P, T, C, E> provider = this.provider;
/* 324 */     T object = skipQueue(parameter);
/* 325 */     for (C callback : callbacks) {
/* 326 */       provider.callStage3(parameter, object, callback);
/*     */     }
/* 328 */     return object;
/*     */   }
/*     */   
/*     */   private T skipQueue(P parameter) throws Throwable {
/* 332 */     AsynchronousExecutor<P, T, C, E>.Task task = (Task)this.tasks.get(parameter);
/* 333 */     if (task != null) {
/* 334 */       return (T)task.get();
/*     */     }
/* 336 */     T object = this.provider.callStage1(parameter);
/* 337 */     this.provider.callStage2(parameter, object);
/* 338 */     return object;
/*     */   }
/*     */   
/*     */ 
/*     */   public void finishActive()
/*     */     throws Throwable
/*     */   {
/* 345 */     Queue<AsynchronousExecutor<P, T, C, E>.Task> finished = this.finished;
/* 346 */     while (!finished.isEmpty()) {
/* 347 */       ((Task)finished.poll()).finish();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setActiveThreads(int coreSize) {
/* 352 */     this.pool.setCorePoolSize(coreSize);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\AsynchronousExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */