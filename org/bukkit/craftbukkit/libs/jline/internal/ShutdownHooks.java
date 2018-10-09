/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class ShutdownHooks
/*     */ {
/*     */   public static final String JLINE_SHUTDOWNHOOK = "org.bukkit.craftbukkit.libs.jline.shutdownhook";
/*  26 */   private static final boolean enabled = Configuration.getBoolean("org.bukkit.craftbukkit.libs.jline.shutdownhook", true);
/*     */   
/*  28 */   private static final List<Task> tasks = new ArrayList();
/*     */   private static Thread hook;
/*     */   
/*     */   public static synchronized <T extends Task> T add(T task)
/*     */   {
/*  33 */     Preconditions.checkNotNull(task);
/*     */     
/*     */ 
/*  36 */     if (!enabled) {
/*  37 */       Log.debug(new Object[] { "Shutdown-hook is disabled; not installing: ", task });
/*  38 */       return task;
/*     */     }
/*     */     
/*     */ 
/*  42 */     if (hook == null) {
/*  43 */       hook = addHook(new Thread("JLine Shutdown Hook")
/*     */       {
/*     */         public void run() {}
/*     */       });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  53 */     Log.debug(new Object[] { "Adding shutdown-hook task: ", task });
/*  54 */     tasks.add(task);
/*     */     
/*  56 */     return task;
/*     */   }
/*     */   
/*     */   private static synchronized void runTasks() {
/*  60 */     Log.debug(new Object[] { "Running all shutdown-hook tasks" });
/*     */     
/*     */ 
/*  63 */     for (Task task : (Task[])tasks.toArray(new Task[tasks.size()])) {
/*  64 */       Log.debug(new Object[] { "Running task: ", task });
/*     */       try {
/*  66 */         task.run();
/*     */       }
/*     */       catch (Throwable e) {
/*  69 */         Log.warn(new Object[] { "Task failed", e });
/*     */       }
/*     */     }
/*     */     
/*  73 */     tasks.clear();
/*     */   }
/*     */   
/*     */   private static Thread addHook(Thread thread) {
/*  77 */     Log.debug(new Object[] { "Registering shutdown-hook: ", thread });
/*     */     try {
/*  79 */       Runtime.getRuntime().addShutdownHook(thread);
/*     */     }
/*     */     catch (AbstractMethodError e)
/*     */     {
/*  83 */       Log.debug(new Object[] { "Failed to register shutdown-hook", e });
/*     */     }
/*  85 */     return thread;
/*     */   }
/*     */   
/*     */   public static synchronized void remove(Task task) {
/*  89 */     Preconditions.checkNotNull(task);
/*     */     
/*     */ 
/*  92 */     if ((!enabled) || (hook == null)) {
/*  93 */       return;
/*     */     }
/*     */     
/*     */ 
/*  97 */     tasks.remove(task);
/*     */     
/*     */ 
/* 100 */     if (tasks.isEmpty()) {
/* 101 */       removeHook(hook);
/* 102 */       hook = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static void removeHook(Thread thread) {
/* 107 */     Log.debug(new Object[] { "Removing shutdown-hook: ", thread });
/*     */     try
/*     */     {
/* 110 */       Runtime.getRuntime().removeShutdownHook(thread);
/*     */     }
/*     */     catch (AbstractMethodError e)
/*     */     {
/* 114 */       Log.debug(new Object[] { "Failed to remove shutdown-hook", e });
/*     */     }
/*     */     catch (IllegalStateException e) {}
/*     */   }
/*     */   
/*     */   public static abstract interface Task
/*     */   {
/*     */     public abstract void run()
/*     */       throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\ShutdownHooks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */