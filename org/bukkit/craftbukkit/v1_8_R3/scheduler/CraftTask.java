/*     */ package org.bukkit.craftbukkit.v1_8_R3.scheduler;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ public class CraftTask implements BukkitTask, Runnable
/*     */ {
/*  12 */   private volatile CraftTask next = null;
/*     */   
/*     */   private volatile long period;
/*     */   
/*     */   private long nextRun;
/*     */   
/*     */   private final Runnable task;
/*     */   
/*     */   private final Plugin plugin;
/*     */   
/*     */   private final int id;
/*     */   
/*     */   final CustomTimingsHandler timings;
/*     */   
/*     */ 
/*     */   CraftTask()
/*     */   {
/*  29 */     this(null, null, -1, -1L);
/*     */   }
/*     */   
/*     */   CraftTask(Runnable task) {
/*  33 */     this(null, task, -1, -1L);
/*     */   }
/*     */   
/*     */ 
/*  37 */   public String timingName = null;
/*     */   
/*  39 */   CraftTask(String timingName) { this(timingName, null, null, -1, -1L); }
/*     */   
/*     */ 
/*  42 */   CraftTask(String timingName, Runnable task) { this(timingName, null, task, -1, -1L); }
/*     */   
/*     */   CraftTask(String timingName, Plugin plugin, Runnable task, int id, long period) {
/*  45 */     this.plugin = plugin;
/*  46 */     this.task = task;
/*  47 */     this.id = id;
/*  48 */     this.period = period;
/*  49 */     this.timingName = ((timingName == null) && (task == null) ? "Unknown" : timingName);
/*  50 */     this.timings = (isSync() ? SpigotTimings.getPluginTaskTimings(this, period) : null);
/*     */   }
/*     */   
/*     */   CraftTask(Plugin plugin, Runnable task, int id, long period) {
/*  54 */     this(null, plugin, task, id, period);
/*     */   }
/*     */   
/*     */   public final int getTaskId()
/*     */   {
/*  59 */     return this.id;
/*     */   }
/*     */   
/*     */   public final Plugin getOwner() {
/*  63 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public boolean isSync() {
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public void run() {
/*  71 */     this.task.run();
/*     */   }
/*     */   
/*     */   long getPeriod() {
/*  75 */     return this.period;
/*     */   }
/*     */   
/*     */   void setPeriod(long period) {
/*  79 */     this.period = period;
/*     */   }
/*     */   
/*     */   long getNextRun() {
/*  83 */     return this.nextRun;
/*     */   }
/*     */   
/*     */   void setNextRun(long nextRun) {
/*  87 */     this.nextRun = nextRun;
/*     */   }
/*     */   
/*     */   CraftTask getNext() {
/*  91 */     return this.next;
/*     */   }
/*     */   
/*     */   void setNext(CraftTask next) {
/*  95 */     this.next = next;
/*     */   }
/*     */   
/*     */   Class<? extends Runnable> getTaskClass() {
/*  99 */     return this.task.getClass();
/*     */   }
/*     */   
/*     */   public void cancel() {
/* 103 */     Bukkit.getScheduler().cancelTask(this.id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean cancel0()
/*     */   {
/* 112 */     setPeriod(-2L);
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public String getTaskName()
/*     */   {
/* 118 */     if (this.timingName != null) {
/* 119 */       return this.timingName;
/*     */     }
/* 121 */     return this.task.getClass().getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scheduler\CraftTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */