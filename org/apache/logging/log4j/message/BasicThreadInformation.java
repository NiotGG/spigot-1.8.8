/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ 
/*     */ 
/*     */ class BasicThreadInformation
/*     */   implements ThreadInformation
/*     */ {
/*     */   private static final int HASH_SHIFT = 32;
/*     */   
/*     */ 
/*     */   private static final int HASH_MULTIPLIER = 31;
/*     */   
/*     */ 
/*     */   private final long id;
/*     */   
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */   private final String longName;
/*     */   
/*     */ 
/*     */   private final Thread.State state;
/*     */   
/*     */ 
/*     */   private final int priority;
/*     */   
/*     */ 
/*     */   private final boolean isAlive;
/*     */   
/*     */ 
/*     */   private final boolean isDaemon;
/*     */   
/*     */   private final String threadGroupName;
/*     */   
/*     */ 
/*     */   public BasicThreadInformation(Thread paramThread)
/*     */   {
/*  39 */     this.id = paramThread.getId();
/*  40 */     this.name = paramThread.getName();
/*  41 */     this.longName = paramThread.toString();
/*  42 */     this.state = paramThread.getState();
/*  43 */     this.priority = paramThread.getPriority();
/*  44 */     this.isAlive = paramThread.isAlive();
/*  45 */     this.isDaemon = paramThread.isDaemon();
/*  46 */     ThreadGroup localThreadGroup = paramThread.getThreadGroup();
/*  47 */     this.threadGroupName = (localThreadGroup == null ? null : localThreadGroup.getName());
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  52 */     if (this == paramObject) {
/*  53 */       return true;
/*     */     }
/*  55 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     BasicThreadInformation localBasicThreadInformation = (BasicThreadInformation)paramObject;
/*     */     
/*  61 */     if (this.id != localBasicThreadInformation.id) {
/*  62 */       return false;
/*     */     }
/*  64 */     if (this.name != null ? !this.name.equals(localBasicThreadInformation.name) : localBasicThreadInformation.name != null) {
/*  65 */       return false;
/*     */     }
/*     */     
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  73 */     int i = (int)(this.id ^ this.id >>> 32);
/*  74 */     i = 31 * i + (this.name != null ? this.name.hashCode() : 0);
/*  75 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printThreadInfo(StringBuilder paramStringBuilder)
/*     */   {
/*  84 */     paramStringBuilder.append("\"").append(this.name).append("\" ");
/*  85 */     if (this.isDaemon) {
/*  86 */       paramStringBuilder.append("daemon ");
/*     */     }
/*  88 */     paramStringBuilder.append("prio=").append(this.priority).append(" tid=").append(this.id).append(" ");
/*  89 */     if (this.threadGroupName != null) {
/*  90 */       paramStringBuilder.append("group=\"").append(this.threadGroupName).append("\"");
/*     */     }
/*  92 */     paramStringBuilder.append("\n");
/*  93 */     paramStringBuilder.append("\tThread state: ").append(this.state.name()).append("\n");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void printStack(StringBuilder paramStringBuilder, StackTraceElement[] paramArrayOfStackTraceElement)
/*     */   {
/* 103 */     for (StackTraceElement localStackTraceElement : paramArrayOfStackTraceElement) {
/* 104 */       paramStringBuilder.append("\tat ").append(localStackTraceElement).append("\n");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\BasicThreadInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */