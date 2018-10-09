/*     */ package org.bukkit.craftbukkit.v1_8_R3.scheduler;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.IllegalPluginAccessException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.scheduler.BukkitWorker;
/*     */ import org.spigotmc.CustomTimingsHandler;
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
/*     */ public class CraftScheduler
/*     */   implements BukkitScheduler
/*     */ {
/*  48 */   private final AtomicInteger ids = new AtomicInteger(1);
/*     */   
/*     */ 
/*     */ 
/*  52 */   private volatile CraftTask head = new CraftTask();
/*     */   
/*     */ 
/*     */ 
/*  56 */   private final AtomicReference<CraftTask> tail = new AtomicReference(this.head);
/*     */   
/*     */ 
/*     */ 
/*  60 */   private final PriorityQueue<CraftTask> pending = new PriorityQueue(10, 
/*  61 */     new Comparator() {
/*     */     public int compare(CraftTask o1, CraftTask o2) {
/*  63 */       return (int)(o1.getNextRun() - o2.getNextRun());
/*     */     }
/*  60 */   }
/*  61 */     );
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  69 */   private final List<CraftTask> temp = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*  73 */   private final ConcurrentHashMap<Integer, CraftTask> runners = new ConcurrentHashMap();
/*  74 */   private volatile int currentTick = -1;
/*  75 */   private final Executor executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("Craft Scheduler Thread - %1$d").build());
/*  76 */   private CraftAsyncDebugger debugHead = new CraftAsyncDebugger(-1, null, null) { StringBuilder debugTo(StringBuilder string) { return string; } };
/*  77 */   private CraftAsyncDebugger debugTail = this.debugHead;
/*     */   
/*     */ 
/*     */ 
/*  81 */   private static final int RECENT_TICKS = 30;
/*     */   
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task)
/*     */   {
/*  85 */     return scheduleSyncDelayedTask(plugin, task, 0L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTask(Plugin plugin, Runnable runnable) {
/*  89 */     return runTaskLater(plugin, runnable, 0L);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
/*  94 */     return scheduleAsyncDelayedTask(plugin, task, 0L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskAsynchronously(Plugin plugin, Runnable runnable) {
/*  98 */     return runTaskLaterAsynchronously(plugin, runnable, 0L);
/*     */   }
/*     */   
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
/* 102 */     return scheduleSyncRepeatingTask(plugin, task, delay, -1L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskLater(Plugin plugin, Runnable runnable, long delay) {
/* 106 */     return runTaskTimer(plugin, runnable, delay, -1L);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
/* 111 */     return scheduleAsyncRepeatingTask(plugin, task, delay, -1L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long delay) {
/* 115 */     return runTaskTimerAsynchronously(plugin, runnable, delay, -1L);
/*     */   }
/*     */   
/*     */   public int scheduleSyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
/* 119 */     return runTaskTimer(plugin, runnable, delay, period).getTaskId();
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskTimer(Plugin plugin, Runnable runnable, long delay, long period) {
/* 123 */     validate(plugin, runnable);
/* 124 */     if (delay < 0L) {
/* 125 */       delay = 0L;
/*     */     }
/* 127 */     if (period == 0L) {
/* 128 */       period = 1L;
/* 129 */     } else if (period < -1L) {
/* 130 */       period = -1L;
/*     */     }
/* 132 */     return handle(new CraftTask(plugin, runnable, nextId(), period), delay);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
/* 137 */     return runTaskTimerAsynchronously(plugin, runnable, delay, period).getTaskId();
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Runnable runnable, long delay, long period) {
/* 141 */     validate(plugin, runnable);
/* 142 */     if (delay < 0L) {
/* 143 */       delay = 0L;
/*     */     }
/* 145 */     if (period == 0L) {
/* 146 */       period = 1L;
/* 147 */     } else if (period < -1L) {
/* 148 */       period = -1L;
/*     */     }
/* 150 */     return handle(new CraftAsyncTask(this.runners, plugin, runnable, nextId(), period), delay);
/*     */   }
/*     */   
/*     */   public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
/* 154 */     validate(plugin, task);
/* 155 */     CraftFuture<T> future = new CraftFuture(task, plugin, nextId());
/* 156 */     handle(future, 0L);
/* 157 */     return future;
/*     */   }
/*     */   
/*     */   public void cancelTask(final int taskId) {
/* 161 */     if (taskId <= 0) {
/* 162 */       return;
/*     */     }
/* 164 */     CraftTask task = (CraftTask)this.runners.get(Integer.valueOf(taskId));
/* 165 */     if (task != null) {
/* 166 */       task.cancel0();
/*     */     }
/* 168 */     task = new CraftTask(
/* 169 */       new Runnable() {
/*     */         public void run() {
/* 171 */           if (!check(CraftScheduler.this.temp))
/* 172 */             check(CraftScheduler.this.pending);
/*     */         }
/*     */         
/*     */         private boolean check(Iterable<CraftTask> collection) {
/* 176 */           Iterator<CraftTask> tasks = collection.iterator();
/* 177 */           while (tasks.hasNext()) {
/* 178 */             CraftTask task = (CraftTask)tasks.next();
/* 179 */             if (task.getTaskId() == taskId) {
/* 180 */               task.cancel0();
/* 181 */               tasks.remove();
/* 182 */               if (task.isSync()) {
/* 183 */                 CraftScheduler.this.runners.remove(Integer.valueOf(taskId));
/*     */               }
/* 185 */               return true;
/*     */             }
/*     */           }
/* 188 */           return false;
/* 189 */         } });
/* 190 */     handle(task, 0L);
/* 191 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
/* 192 */       if (taskPending == task) {
/* 193 */         return;
/*     */       }
/* 195 */       if (taskPending.getTaskId() == taskId) {
/* 196 */         taskPending.cancel0();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void cancelTasks(final Plugin plugin) {
/* 202 */     Validate.notNull(plugin, "Cannot cancel tasks of null plugin");
/* 203 */     CraftTask task = new CraftTask(
/* 204 */       new Runnable() {
/*     */         public void run() {
/* 206 */           check(CraftScheduler.this.pending);
/* 207 */           check(CraftScheduler.this.temp);
/*     */         }
/*     */         
/* 210 */         void check(Iterable<CraftTask> collection) { Iterator<CraftTask> tasks = collection.iterator();
/* 211 */           while (tasks.hasNext()) {
/* 212 */             CraftTask task = (CraftTask)tasks.next();
/* 213 */             if (task.getOwner().equals(plugin)) {
/* 214 */               task.cancel0();
/* 215 */               tasks.remove();
/* 216 */               if (task.isSync()) {
/* 217 */                 CraftScheduler.this.runners.remove(Integer.valueOf(task.getTaskId()));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 222 */       });
/* 223 */     handle(task, 0L);
/* 224 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
/* 225 */       if (taskPending == task) {
/* 226 */         return;
/*     */       }
/* 228 */       if ((taskPending.getTaskId() != -1) && (taskPending.getOwner().equals(plugin))) {
/* 229 */         taskPending.cancel0();
/*     */       }
/*     */     }
/* 232 */     for (CraftTask runner : this.runners.values()) {
/* 233 */       if (runner.getOwner().equals(plugin)) {
/* 234 */         runner.cancel0();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void cancelAllTasks() {
/* 240 */     CraftTask task = new CraftTask(
/* 241 */       new Runnable() {
/*     */         public void run() {
/* 243 */           Iterator<CraftTask> it = CraftScheduler.this.runners.values().iterator();
/* 244 */           while (it.hasNext()) {
/* 245 */             CraftTask task = (CraftTask)it.next();
/* 246 */             task.cancel0();
/* 247 */             if (task.isSync()) {
/* 248 */               it.remove();
/*     */             }
/*     */           }
/* 251 */           CraftScheduler.this.pending.clear();
/* 252 */           CraftScheduler.this.temp.clear();
/*     */         }
/* 254 */       });
/* 255 */     handle(task, 0L);
/* 256 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
/* 257 */       if (taskPending == task) {
/*     */         break;
/*     */       }
/* 260 */       taskPending.cancel0();
/*     */     }
/* 262 */     for (CraftTask runner : this.runners.values()) {
/* 263 */       runner.cancel0();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean isCurrentlyRunning(int taskId)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 87	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftScheduler:runners	Ljava/util/concurrent/ConcurrentHashMap;
/*     */     //   4: iload_1
/*     */     //   5: invokestatic 206	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   8: invokevirtual 210	java/util/concurrent/ConcurrentHashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   11: checkcast 57	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftTask
/*     */     //   14: astore_2
/*     */     //   15: aload_2
/*     */     //   16: ifnull +10 -> 26
/*     */     //   19: aload_2
/*     */     //   20: invokevirtual 277	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftTask:isSync	()Z
/*     */     //   23: ifeq +5 -> 28
/*     */     //   26: iconst_0
/*     */     //   27: ireturn
/*     */     //   28: aload_2
/*     */     //   29: checkcast 184	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftAsyncTask
/*     */     //   32: astore_3
/*     */     //   33: aload_3
/*     */     //   34: invokevirtual 281	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftAsyncTask:getWorkers	()Ljava/util/LinkedList;
/*     */     //   37: dup
/*     */     //   38: astore 4
/*     */     //   40: monitorenter
/*     */     //   41: aload_3
/*     */     //   42: invokevirtual 281	org/bukkit/craftbukkit/v1_8_R3/scheduler/CraftAsyncTask:getWorkers	()Ljava/util/LinkedList;
/*     */     //   45: invokevirtual 286	java/util/LinkedList:isEmpty	()Z
/*     */     //   48: aload 4
/*     */     //   50: monitorexit
/*     */     //   51: ireturn
/*     */     //   52: aload 4
/*     */     //   54: monitorexit
/*     */     //   55: athrow
/*     */     // Line number table:
/*     */     //   Java source line #268	-> byte code offset #0
/*     */     //   Java source line #269	-> byte code offset #15
/*     */     //   Java source line #270	-> byte code offset #26
/*     */     //   Java source line #272	-> byte code offset #28
/*     */     //   Java source line #273	-> byte code offset #33
/*     */     //   Java source line #274	-> byte code offset #41
/*     */     //   Java source line #273	-> byte code offset #52
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	56	0	this	CraftScheduler
/*     */     //   0	56	1	taskId	int
/*     */     //   14	15	2	task	CraftTask
/*     */     //   32	10	3	asyncTask	CraftAsyncTask
/*     */     //   38	15	4	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   41	51	52	finally
/*     */     //   52	55	52	finally
/*     */   }
/*     */   
/*     */   public boolean isQueued(int taskId)
/*     */   {
/* 279 */     if (taskId <= 0) {
/* 280 */       return false;
/*     */     }
/* 282 */     for (CraftTask task = this.head.getNext(); task != null; task = task.getNext()) {
/* 283 */       if (task.getTaskId() == taskId) {
/* 284 */         return task.getPeriod() >= -1L;
/*     */       }
/*     */     }
/* 287 */     CraftTask task = (CraftTask)this.runners.get(Integer.valueOf(taskId));
/* 288 */     return (task != null) && (task.getPeriod() >= -1L);
/*     */   }
/*     */   
/*     */   public List<BukkitWorker> getActiveWorkers() {
/* 292 */     ArrayList<BukkitWorker> workers = new ArrayList();
/* 293 */     for (CraftTask taskObj : this.runners.values())
/*     */     {
/* 295 */       if (!taskObj.isSync())
/*     */       {
/*     */ 
/* 298 */         CraftAsyncTask task = (CraftAsyncTask)taskObj;
/* 299 */         synchronized (task.getWorkers())
/*     */         {
/* 301 */           workers.addAll(task.getWorkers());
/*     */         }
/*     */       } }
/* 304 */     return workers;
/*     */   }
/*     */   
/*     */   public List<BukkitTask> getPendingTasks() {
/* 308 */     ArrayList<CraftTask> truePending = new ArrayList();
/* 309 */     for (CraftTask task = this.head.getNext(); task != null; task = task.getNext()) {
/* 310 */       if (task.getTaskId() != -1)
/*     */       {
/* 312 */         truePending.add(task);
/*     */       }
/*     */     }
/*     */     
/* 316 */     ArrayList<BukkitTask> pending = new ArrayList();
/* 317 */     for (CraftTask task : this.runners.values()) {
/* 318 */       if (task.getPeriod() >= -1L) {
/* 319 */         pending.add(task);
/*     */       }
/*     */     }
/*     */     
/* 323 */     for (CraftTask task : truePending) {
/* 324 */       if ((task.getPeriod() >= -1L) && (!pending.contains(task))) {
/* 325 */         pending.add(task);
/*     */       }
/*     */     }
/* 328 */     return pending;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void mainThreadHeartbeat(int currentTick)
/*     */   {
/* 335 */     this.currentTick = currentTick;
/* 336 */     List<CraftTask> temp = this.temp;
/* 337 */     parsePending();
/* 338 */     while (isReady(currentTick)) {
/* 339 */       CraftTask task = (CraftTask)this.pending.remove();
/* 340 */       if (task.getPeriod() < -1L) {
/* 341 */         if (task.isSync()) {
/* 342 */           this.runners.remove(Integer.valueOf(task.getTaskId()), task);
/*     */         }
/* 344 */         parsePending();
/*     */       }
/*     */       else {
/* 347 */         if (task.isSync()) {
/*     */           try {
/* 349 */             task.timings.startTiming();
/* 350 */             task.run();
/* 351 */             task.timings.stopTiming();
/*     */           } catch (Throwable throwable) {
/* 353 */             task.getOwner().getLogger().log(
/* 354 */               Level.WARNING, 
/* 355 */               String.format(
/* 356 */               "Task #%s for %s generated an exception", new Object[] {
/* 357 */               Integer.valueOf(task.getTaskId()), 
/* 358 */               task.getOwner().getDescription().getFullName() }), 
/* 359 */               throwable);
/*     */           }
/* 361 */           parsePending();
/*     */         } else {
/* 363 */           this.debugTail = this.debugTail.setNext(new CraftAsyncDebugger(currentTick + RECENT_TICKS, task.getOwner(), task.getTaskClass()));
/* 364 */           this.executor.execute(task);
/*     */         }
/*     */         
/*     */ 
/* 368 */         long period = task.getPeriod();
/* 369 */         if (period > 0L) {
/* 370 */           task.setNextRun(currentTick + period);
/* 371 */           temp.add(task);
/* 372 */         } else if (task.isSync()) {
/* 373 */           this.runners.remove(Integer.valueOf(task.getTaskId()));
/*     */         }
/*     */       } }
/* 376 */     this.pending.addAll(temp);
/* 377 */     temp.clear();
/* 378 */     this.debugHead = this.debugHead.getNextHead(currentTick);
/*     */   }
/*     */   
/*     */   private void addTask(CraftTask task) {
/* 382 */     AtomicReference<CraftTask> tail = this.tail;
/* 383 */     CraftTask tailTask = (CraftTask)tail.get();
/* 384 */     while (!tail.compareAndSet(tailTask, task)) {
/* 385 */       tailTask = (CraftTask)tail.get();
/*     */     }
/* 387 */     tailTask.setNext(task);
/*     */   }
/*     */   
/*     */   private CraftTask handle(CraftTask task, long delay) {
/* 391 */     task.setNextRun(this.currentTick + delay);
/* 392 */     addTask(task);
/* 393 */     return task;
/*     */   }
/*     */   
/*     */   private static void validate(Plugin plugin, Object task) {
/* 397 */     Validate.notNull(plugin, "Plugin cannot be null");
/* 398 */     Validate.notNull(task, "Task cannot be null");
/* 399 */     if (!plugin.isEnabled()) {
/* 400 */       throw new IllegalPluginAccessException("Plugin attempted to register task while disabled");
/*     */     }
/*     */   }
/*     */   
/*     */   private int nextId() {
/* 405 */     return this.ids.incrementAndGet();
/*     */   }
/*     */   
/*     */   private void parsePending() {
/* 409 */     CraftTask head = this.head;
/* 410 */     CraftTask task = head.getNext();
/* 411 */     CraftTask lastTask = head;
/* 412 */     for (; task != null; task = (lastTask = task).getNext()) {
/* 413 */       if (task.getTaskId() == -1) {
/* 414 */         task.run();
/* 415 */       } else if (task.getPeriod() >= -1L) {
/* 416 */         this.pending.add(task);
/* 417 */         this.runners.put(Integer.valueOf(task.getTaskId()), task);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 422 */     for (task = head; task != lastTask; task = head) {
/* 423 */       head = task.getNext();
/* 424 */       task.setNext(null);
/*     */     }
/* 426 */     this.head = lastTask;
/*     */   }
/*     */   
/*     */   private boolean isReady(int currentTick) {
/* 430 */     return (!this.pending.isEmpty()) && (((CraftTask)this.pending.peek()).getNextRun() <= currentTick);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 435 */     int debugTick = this.currentTick;
/* 436 */     StringBuilder string = new StringBuilder("Recent tasks from ").append(debugTick - RECENT_TICKS).append('-').append(debugTick).append('{');
/* 437 */     this.debugHead.debugTo(string);
/* 438 */     return '}';
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task, long delay)
/*     */   {
/* 444 */     return scheduleSyncDelayedTask(plugin, task, delay);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task)
/*     */   {
/* 450 */     return scheduleSyncDelayedTask(plugin, task);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncRepeatingTask(Plugin plugin, BukkitRunnable task, long delay, long period)
/*     */   {
/* 456 */     return scheduleSyncRepeatingTask(plugin, task, delay, period);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTask(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException
/*     */   {
/* 462 */     return runTask(plugin, task);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskAsynchronously(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException
/*     */   {
/* 468 */     return runTaskAsynchronously(plugin, task);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskLater(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException
/*     */   {
/* 474 */     return runTaskLater(plugin, task, delay);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskLaterAsynchronously(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException
/*     */   {
/* 480 */     return runTaskLaterAsynchronously(plugin, task, delay);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskTimer(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException
/*     */   {
/* 486 */     return runTaskTimer(plugin, task, delay, period);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskTimerAsynchronously(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException
/*     */   {
/* 492 */     return runTaskTimerAsynchronously(plugin, task, delay, period);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scheduler\CraftScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */