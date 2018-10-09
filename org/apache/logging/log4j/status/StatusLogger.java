/*     */ package org.apache.logging.log4j.status;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.simple.SimpleLogger;
/*     */ import org.apache.logging.log4j.spi.AbstractLogger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public final class StatusLogger
/*     */   extends AbstractLogger
/*     */ {
/*     */   public static final String MAX_STATUS_ENTRIES = "log4j2.status.entries";
/*     */   private static final String NOT_AVAIL = "?";
/*  48 */   private static final PropertiesUtil PROPS = new PropertiesUtil("log4j2.StatusLogger.properties");
/*     */   
/*  50 */   private static final int MAX_ENTRIES = PROPS.getIntegerProperty("log4j2.status.entries", 200);
/*     */   
/*  52 */   private static final String DEFAULT_STATUS_LEVEL = PROPS.getStringProperty("log4j2.StatusLogger.level");
/*     */   
/*     */ 
/*     */ 
/*  56 */   private static final StatusLogger STATUS_LOGGER = new StatusLogger();
/*     */   
/*     */   private final SimpleLogger logger;
/*     */   
/*  60 */   private final CopyOnWriteArrayList<StatusListener> listeners = new CopyOnWriteArrayList();
/*  61 */   private final ReentrantReadWriteLock listenersLock = new ReentrantReadWriteLock();
/*     */   
/*  63 */   private final Queue<StatusData> messages = new BoundedQueue(MAX_ENTRIES);
/*  64 */   private final ReentrantLock msgLock = new ReentrantLock();
/*     */   private int listenersLevel;
/*     */   
/*     */   private StatusLogger()
/*     */   {
/*  69 */     this.logger = new SimpleLogger("StatusLogger", Level.ERROR, false, true, false, false, "", null, PROPS, System.err);
/*     */     
/*  71 */     this.listenersLevel = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StatusLogger getLogger()
/*     */   {
/*  79 */     return STATUS_LOGGER;
/*     */   }
/*     */   
/*     */   public Level getLevel() {
/*  83 */     return this.logger.getLevel();
/*     */   }
/*     */   
/*     */   public void setLevel(Level paramLevel) {
/*  87 */     this.logger.setLevel(paramLevel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerListener(StatusListener paramStatusListener)
/*     */   {
/*  95 */     this.listenersLock.writeLock().lock();
/*     */     try {
/*  97 */       this.listeners.add(paramStatusListener);
/*  98 */       Level localLevel = paramStatusListener.getStatusLevel();
/*  99 */       if (this.listenersLevel < localLevel.intLevel()) {
/* 100 */         this.listenersLevel = localLevel.intLevel();
/*     */       }
/*     */     } finally {
/* 103 */       this.listenersLock.writeLock().unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeListener(StatusListener paramStatusListener)
/*     */   {
/* 112 */     this.listenersLock.writeLock().lock();
/*     */     try {
/* 114 */       this.listeners.remove(paramStatusListener);
/* 115 */       int i = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
/* 116 */       for (StatusListener localStatusListener : this.listeners) {
/* 117 */         int j = localStatusListener.getStatusLevel().intLevel();
/* 118 */         if (i < j) {
/* 119 */           i = j;
/*     */         }
/*     */       }
/* 122 */       this.listenersLevel = i;
/*     */     } finally {
/* 124 */       this.listenersLock.writeLock().unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<StatusListener> getListeners()
/*     */   {
/* 133 */     return this.listeners.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 140 */     this.listeners.clear();
/* 141 */     clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<StatusData> getStatusData()
/*     */   {
/* 149 */     this.msgLock.lock();
/*     */     try {
/* 151 */       return new ArrayList(this.messages);
/*     */     } finally {
/* 153 */       this.msgLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 161 */     this.msgLock.lock();
/*     */     try {
/* 163 */       this.messages.clear();
/*     */     } finally {
/* 165 */       this.msgLock.unlock();
/*     */     }
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
/*     */   public void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 180 */     StackTraceElement localStackTraceElement = null;
/* 181 */     if (paramString != null) {
/* 182 */       localStackTraceElement = getStackTraceElement(paramString, Thread.currentThread().getStackTrace());
/*     */     }
/* 184 */     StatusData localStatusData = new StatusData(localStackTraceElement, paramLevel, paramMessage, paramThrowable);
/* 185 */     this.msgLock.lock();
/*     */     try {
/* 187 */       this.messages.add(localStatusData);
/*     */     } finally {
/* 189 */       this.msgLock.unlock();
/*     */     }
/* 191 */     if (this.listeners.size() > 0) {
/* 192 */       for (StatusListener localStatusListener : this.listeners) {
/* 193 */         if (localStatusData.getLevel().isAtLeastAsSpecificAs(localStatusListener.getStatusLevel())) {
/* 194 */           localStatusListener.log(localStatusData);
/*     */         }
/*     */       }
/*     */     } else {
/* 198 */       this.logger.log(paramMarker, paramString, paramLevel, paramMessage, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private StackTraceElement getStackTraceElement(String paramString, StackTraceElement[] paramArrayOfStackTraceElement) {
/* 203 */     if (paramString == null) {
/* 204 */       return null;
/*     */     }
/* 206 */     int i = 0;
/* 207 */     for (StackTraceElement localStackTraceElement : paramArrayOfStackTraceElement) {
/* 208 */       if (i != 0) {
/* 209 */         return localStackTraceElement;
/*     */       }
/* 211 */       String str = localStackTraceElement.getClassName();
/* 212 */       if (paramString.equals(str))
/* 213 */         i = 1; else {
/* 214 */         if ("?".equals(str))
/*     */           break;
/*     */       }
/*     */     }
/* 218 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString)
/*     */   {
/* 223 */     return isEnabled(paramLevel, paramMarker);
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable)
/*     */   {
/* 228 */     return isEnabled(paramLevel, paramMarker);
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/* 233 */     return isEnabled(paramLevel, paramMarker);
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 238 */     return isEnabled(paramLevel, paramMarker);
/*     */   }
/*     */   
/*     */   protected boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 243 */     return isEnabled(paramLevel, paramMarker);
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Level paramLevel, Marker paramMarker)
/*     */   {
/* 248 */     if (this.listeners.size() > 0) {
/* 249 */       return this.listenersLevel >= paramLevel.intLevel();
/*     */     }
/* 251 */     switch (paramLevel) {
/*     */     case FATAL: 
/* 253 */       return this.logger.isFatalEnabled(paramMarker);
/*     */     case TRACE: 
/* 255 */       return this.logger.isTraceEnabled(paramMarker);
/*     */     case DEBUG: 
/* 257 */       return this.logger.isDebugEnabled(paramMarker);
/*     */     case INFO: 
/* 259 */       return this.logger.isInfoEnabled(paramMarker);
/*     */     case WARN: 
/* 261 */       return this.logger.isWarnEnabled(paramMarker);
/*     */     case ERROR: 
/* 263 */       return this.logger.isErrorEnabled(paramMarker);
/*     */     }
/* 265 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class BoundedQueue<E>
/*     */     extends ConcurrentLinkedQueue<E>
/*     */   {
/*     */     private static final long serialVersionUID = -3945953719763255337L;
/*     */     
/*     */     private final int size;
/*     */     
/*     */ 
/*     */     public BoundedQueue(int paramInt)
/*     */     {
/* 280 */       this.size = paramInt;
/*     */     }
/*     */     
/*     */     public boolean add(E paramE)
/*     */     {
/* 285 */       while (StatusLogger.this.messages.size() > this.size) {
/* 286 */         StatusLogger.this.messages.poll();
/*     */       }
/* 288 */       return super.add(paramE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */