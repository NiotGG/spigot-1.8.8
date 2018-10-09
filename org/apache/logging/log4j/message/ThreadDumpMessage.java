/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class ThreadDumpMessage
/*     */   implements Message
/*     */ {
/*     */   private static final long serialVersionUID = -1103400781608841088L;
/*     */   
/*     */   static
/*     */   {
/*  45 */     Method[] arrayOfMethod1 = ThreadInfo.class.getMethods();
/*  46 */     int i = 1;
/*  47 */     for (Method localMethod : arrayOfMethod1)
/*  48 */       if (localMethod.getName().equals("getLockInfo")) {
/*  49 */         i = 0;
/*  50 */         break;
/*     */       } }
/*     */   
/*  53 */   private static final ThreadInfoFactory FACTORY = i != 0 ? new BasicThreadInfoFactory(null) : new ExtendedThreadInfoFactory(null);
/*     */   
/*     */   private volatile Map<ThreadInformation, StackTraceElement[]> threads;
/*     */   private final String title;
/*     */   private String formattedMessage;
/*     */   
/*     */   public ThreadDumpMessage(String paramString)
/*     */   {
/*  61 */     this.title = (paramString == null ? "" : paramString);
/*  62 */     this.threads = FACTORY.createThreadInfo();
/*     */   }
/*     */   
/*     */   private ThreadDumpMessage(String paramString1, String paramString2) {
/*  66 */     this.formattedMessage = paramString1;
/*  67 */     this.title = (paramString2 == null ? "" : paramString2);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  72 */     StringBuilder localStringBuilder = new StringBuilder("ThreadDumpMessage[");
/*  73 */     if (this.title.length() > 0) {
/*  74 */       localStringBuilder.append("Title=\"").append(this.title).append("\"");
/*     */     }
/*  76 */     localStringBuilder.append("]");
/*  77 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  86 */     if (this.formattedMessage != null) {
/*  87 */       return this.formattedMessage;
/*     */     }
/*  89 */     StringBuilder localStringBuilder = new StringBuilder(this.title);
/*  90 */     if (this.title.length() > 0) {
/*  91 */       localStringBuilder.append("\n");
/*     */     }
/*  93 */     for (Map.Entry localEntry : this.threads.entrySet()) {
/*  94 */       ThreadInformation localThreadInformation = (ThreadInformation)localEntry.getKey();
/*  95 */       localThreadInformation.printThreadInfo(localStringBuilder);
/*  96 */       localThreadInformation.printStack(localStringBuilder, (StackTraceElement[])localEntry.getValue());
/*  97 */       localStringBuilder.append("\n");
/*     */     }
/*  99 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 108 */     return this.title == null ? "" : this.title;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/* 118 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object writeReplace()
/*     */   {
/* 126 */     return new ThreadDumpMessageProxy(this);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException
/*     */   {
/* 131 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */ 
/*     */   private static class ThreadDumpMessageProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -3476620450287648269L;
/*     */     private final String formattedMsg;
/*     */     private final String title;
/*     */     
/*     */     public ThreadDumpMessageProxy(ThreadDumpMessage paramThreadDumpMessage)
/*     */     {
/* 144 */       this.formattedMsg = paramThreadDumpMessage.getFormattedMessage();
/* 145 */       this.title = paramThreadDumpMessage.title;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Object readResolve()
/*     */     {
/* 153 */       return new ThreadDumpMessage(this.formattedMsg, this.title, null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static abstract interface ThreadInfoFactory
/*     */   {
/*     */     public abstract Map<ThreadInformation, StackTraceElement[]> createThreadInfo();
/*     */   }
/*     */   
/*     */ 
/*     */   private static class BasicThreadInfoFactory
/*     */     implements ThreadDumpMessage.ThreadInfoFactory
/*     */   {
/*     */     public Map<ThreadInformation, StackTraceElement[]> createThreadInfo()
/*     */     {
/* 170 */       Map localMap = Thread.getAllStackTraces();
/* 171 */       HashMap localHashMap = new HashMap(localMap.size());
/*     */       
/* 173 */       for (Map.Entry localEntry : localMap.entrySet()) {
/* 174 */         localHashMap.put(new BasicThreadInformation((Thread)localEntry.getKey()), localEntry.getValue());
/*     */       }
/* 176 */       return localHashMap;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class ExtendedThreadInfoFactory
/*     */     implements ThreadDumpMessage.ThreadInfoFactory
/*     */   {
/*     */     public Map<ThreadInformation, StackTraceElement[]> createThreadInfo()
/*     */     {
/* 186 */       ThreadMXBean localThreadMXBean = ManagementFactory.getThreadMXBean();
/* 187 */       ThreadInfo[] arrayOfThreadInfo1 = localThreadMXBean.dumpAllThreads(true, true);
/*     */       
/* 189 */       HashMap localHashMap = new HashMap(arrayOfThreadInfo1.length);
/*     */       
/* 191 */       for (ThreadInfo localThreadInfo : arrayOfThreadInfo1) {
/* 192 */         localHashMap.put(new ExtendedThreadInformation(localThreadInfo), localThreadInfo.getStackTrace());
/*     */       }
/* 194 */       return localHashMap;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/* 205 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ThreadDumpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */