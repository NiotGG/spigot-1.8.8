/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.lang.management.LockInfo;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
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
/*     */ class ExtendedThreadInformation
/*     */   implements ThreadInformation
/*     */ {
/*     */   private final ThreadInfo info;
/*     */   
/*     */   public ExtendedThreadInformation(ThreadInfo paramThreadInfo)
/*     */   {
/*  33 */     this.info = paramThreadInfo;
/*     */   }
/*     */   
/*     */   public void printThreadInfo(StringBuilder paramStringBuilder)
/*     */   {
/*  38 */     paramStringBuilder.append("\"").append(this.info.getThreadName()).append("\"");
/*  39 */     paramStringBuilder.append(" Id=").append(this.info.getThreadId()).append(" ");
/*  40 */     formatState(paramStringBuilder, this.info);
/*  41 */     if (this.info.isSuspended()) {
/*  42 */       paramStringBuilder.append(" (suspended)");
/*     */     }
/*  44 */     if (this.info.isInNative()) {
/*  45 */       paramStringBuilder.append(" (in native)");
/*     */     }
/*  47 */     paramStringBuilder.append('\n');
/*     */   }
/*     */   
/*     */   public void printStack(StringBuilder paramStringBuilder, StackTraceElement[] paramArrayOfStackTraceElement)
/*     */   {
/*  52 */     int i = 0;
/*  53 */     Object localObject4; for (Object localObject3 : paramArrayOfStackTraceElement) {
/*  54 */       paramStringBuilder.append("\tat ").append(((StackTraceElement)localObject3).toString());
/*  55 */       paramStringBuilder.append('\n');
/*  56 */       if ((i == 0) && (this.info.getLockInfo() != null)) {
/*  57 */         localObject4 = this.info.getThreadState();
/*  58 */         switch (localObject4) {
/*     */         case BLOCKED: 
/*  60 */           paramStringBuilder.append("\t-  blocked on ");
/*  61 */           formatLock(paramStringBuilder, this.info.getLockInfo());
/*  62 */           paramStringBuilder.append('\n');
/*  63 */           break;
/*     */         case WAITING: 
/*  65 */           paramStringBuilder.append("\t-  waiting on ");
/*  66 */           formatLock(paramStringBuilder, this.info.getLockInfo());
/*  67 */           paramStringBuilder.append('\n');
/*  68 */           break;
/*     */         case TIMED_WAITING: 
/*  70 */           paramStringBuilder.append("\t-  waiting on ");
/*  71 */           formatLock(paramStringBuilder, this.info.getLockInfo());
/*  72 */           paramStringBuilder.append('\n');
/*  73 */           break;
/*     */         }
/*     */         
/*     */       }
/*     */       
/*  78 */       for (LockInfo localLockInfo : this.info.getLockedMonitors()) {
/*  79 */         if (localLockInfo.getLockedStackDepth() == i) {
/*  80 */           paramStringBuilder.append("\t-  locked ");
/*  81 */           formatLock(paramStringBuilder, localLockInfo);
/*  82 */           paramStringBuilder.append('\n');
/*     */         }
/*     */       }
/*  85 */       i++;
/*     */     }
/*     */     
/*  88 */     ??? = this.info.getLockedSynchronizers();
/*  89 */     if (???.length > 0) {
/*  90 */       paramStringBuilder.append("\n\tNumber of locked synchronizers = ").append(???.length).append('\n');
/*  91 */       for (localObject4 : ???) {
/*  92 */         paramStringBuilder.append("\t- ");
/*  93 */         formatLock(paramStringBuilder, (LockInfo)localObject4);
/*  94 */         paramStringBuilder.append('\n');
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void formatLock(StringBuilder paramStringBuilder, LockInfo paramLockInfo) {
/* 100 */     paramStringBuilder.append("<").append(paramLockInfo.getIdentityHashCode()).append("> (a ");
/* 101 */     paramStringBuilder.append(paramLockInfo.getClassName()).append(")");
/*     */   }
/*     */   
/*     */   private void formatState(StringBuilder paramStringBuilder, ThreadInfo paramThreadInfo) {
/* 105 */     Thread.State localState = paramThreadInfo.getThreadState();
/* 106 */     paramStringBuilder.append(localState);
/* 107 */     StackTraceElement localStackTraceElement; String str1; String str2; switch (localState) {
/*     */     case BLOCKED: 
/* 109 */       paramStringBuilder.append(" (on object monitor owned by \"");
/* 110 */       paramStringBuilder.append(paramThreadInfo.getLockOwnerName()).append("\" Id=").append(paramThreadInfo.getLockOwnerId()).append(")");
/* 111 */       break;
/*     */     
/*     */     case WAITING: 
/* 114 */       localStackTraceElement = paramThreadInfo.getStackTrace()[0];
/* 115 */       str1 = localStackTraceElement.getClassName();
/* 116 */       str2 = localStackTraceElement.getMethodName();
/* 117 */       if ((str1.equals("java.lang.Object")) && (str2.equals("wait"))) {
/* 118 */         paramStringBuilder.append(" (on object monitor");
/* 119 */         if (paramThreadInfo.getLockOwnerName() != null) {
/* 120 */           paramStringBuilder.append(" owned by \"");
/* 121 */           paramStringBuilder.append(paramThreadInfo.getLockOwnerName()).append("\" Id=").append(paramThreadInfo.getLockOwnerId());
/*     */         }
/* 123 */         paramStringBuilder.append(")");
/* 124 */       } else if ((str1.equals("java.lang.Thread")) && (str2.equals("join"))) {
/* 125 */         paramStringBuilder.append(" (on completion of thread ").append(paramThreadInfo.getLockOwnerId()).append(")");
/*     */       } else {
/* 127 */         paramStringBuilder.append(" (parking for lock");
/* 128 */         if (paramThreadInfo.getLockOwnerName() != null) {
/* 129 */           paramStringBuilder.append(" owned by \"");
/* 130 */           paramStringBuilder.append(paramThreadInfo.getLockOwnerName()).append("\" Id=").append(paramThreadInfo.getLockOwnerId());
/*     */         }
/* 132 */         paramStringBuilder.append(")");
/*     */       }
/* 134 */       break;
/*     */     
/*     */     case TIMED_WAITING: 
/* 137 */       localStackTraceElement = paramThreadInfo.getStackTrace()[0];
/* 138 */       str1 = localStackTraceElement.getClassName();
/* 139 */       str2 = localStackTraceElement.getMethodName();
/* 140 */       if ((str1.equals("java.lang.Object")) && (str2.equals("wait"))) {
/* 141 */         paramStringBuilder.append(" (on object monitor");
/* 142 */         if (paramThreadInfo.getLockOwnerName() != null) {
/* 143 */           paramStringBuilder.append(" owned by \"");
/* 144 */           paramStringBuilder.append(paramThreadInfo.getLockOwnerName()).append("\" Id=").append(paramThreadInfo.getLockOwnerId());
/*     */         }
/* 146 */         paramStringBuilder.append(")");
/* 147 */       } else if ((str1.equals("java.lang.Thread")) && (str2.equals("sleep"))) {
/* 148 */         paramStringBuilder.append(" (sleeping)");
/* 149 */       } else if ((str1.equals("java.lang.Thread")) && (str2.equals("join"))) {
/* 150 */         paramStringBuilder.append(" (on completion of thread ").append(paramThreadInfo.getLockOwnerId()).append(")");
/*     */       } else {
/* 152 */         paramStringBuilder.append(" (parking for lock");
/* 153 */         if (paramThreadInfo.getLockOwnerName() != null) {
/* 154 */           paramStringBuilder.append(" owned by \"");
/* 155 */           paramStringBuilder.append(paramThreadInfo.getLockOwnerName()).append("\" Id=").append(paramThreadInfo.getLockOwnerId());
/*     */         }
/* 157 */         paramStringBuilder.append(")");
/*     */       }
/* 159 */       break;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ExtendedThreadInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */