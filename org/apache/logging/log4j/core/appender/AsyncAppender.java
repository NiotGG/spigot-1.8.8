/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.AppenderRef;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationException;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAliases;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
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
/*     */ @Plugin(name="Async", category="Core", elementType="appender", printObject=true)
/*     */ public final class AsyncAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_QUEUE_SIZE = 128;
/*     */   private static final String SHUTDOWN = "Shutdown";
/*     */   private final BlockingQueue<Serializable> queue;
/*     */   private final boolean blocking;
/*     */   private final Configuration config;
/*     */   private final AppenderRef[] appenderRefs;
/*     */   private final String errorRef;
/*     */   private final boolean includeLocation;
/*     */   private AppenderControl errorAppender;
/*     */   private AsyncThread thread;
/*  63 */   private static final AtomicLong threadSequence = new AtomicLong(1L);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private AsyncAppender(String paramString1, Filter paramFilter, AppenderRef[] paramArrayOfAppenderRef, String paramString2, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Configuration paramConfiguration, boolean paramBoolean3)
/*     */   {
/*  70 */     super(paramString1, paramFilter, null, paramBoolean2);
/*  71 */     this.queue = new ArrayBlockingQueue(paramInt);
/*  72 */     this.blocking = paramBoolean1;
/*  73 */     this.config = paramConfiguration;
/*  74 */     this.appenderRefs = paramArrayOfAppenderRef;
/*  75 */     this.errorRef = paramString2;
/*  76 */     this.includeLocation = paramBoolean3;
/*     */   }
/*     */   
/*     */   public void start()
/*     */   {
/*  81 */     Map localMap = this.config.getAppenders();
/*  82 */     ArrayList localArrayList = new ArrayList();
/*  83 */     for (AppenderRef localAppenderRef : this.appenderRefs) {
/*  84 */       if (localMap.containsKey(localAppenderRef.getRef())) {
/*  85 */         localArrayList.add(new AppenderControl((Appender)localMap.get(localAppenderRef.getRef()), localAppenderRef.getLevel(), localAppenderRef.getFilter()));
/*     */       }
/*     */       else {
/*  88 */         LOGGER.error("No appender named {} was configured", new Object[] { localAppenderRef });
/*     */       }
/*     */     }
/*  91 */     if (this.errorRef != null) {
/*  92 */       if (localMap.containsKey(this.errorRef)) {
/*  93 */         this.errorAppender = new AppenderControl((Appender)localMap.get(this.errorRef), null, null);
/*     */       } else {
/*  95 */         LOGGER.error("Unable to set up error Appender. No appender named {} was configured", new Object[] { this.errorRef });
/*     */       }
/*     */     }
/*  98 */     if (localArrayList.size() > 0) {
/*  99 */       this.thread = new AsyncThread(localArrayList, this.queue);
/* 100 */       this.thread.setName("AsyncAppender-" + getName());
/* 101 */     } else if (this.errorRef == null) {
/* 102 */       throw new ConfigurationException("No appenders are available for AsyncAppender " + getName());
/*     */     }
/*     */     
/* 105 */     this.thread.start();
/* 106 */     super.start();
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/* 111 */     super.stop();
/* 112 */     this.thread.shutdown();
/*     */     try {
/* 114 */       this.thread.join();
/*     */     } catch (InterruptedException localInterruptedException) {
/* 116 */       LOGGER.warn("Interrupted while stopping AsyncAppender {}", new Object[] { getName() });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/* 127 */     if (!isStarted()) {
/* 128 */       throw new IllegalStateException("AsyncAppender " + getName() + " is not active");
/*     */     }
/* 130 */     if ((paramLogEvent instanceof Log4jLogEvent)) {
/* 131 */       boolean bool = false;
/* 132 */       if (this.blocking)
/*     */       {
/*     */         try {
/* 135 */           this.queue.put(Log4jLogEvent.serialize((Log4jLogEvent)paramLogEvent, this.includeLocation));
/* 136 */           bool = true;
/*     */         } catch (InterruptedException localInterruptedException) {
/* 138 */           LOGGER.warn("Interrupted while waiting for a free slot in the AsyncAppender LogEvent-queue {}", new Object[] { getName() });
/*     */         }
/*     */       }
/*     */       else {
/* 142 */         bool = this.queue.offer(Log4jLogEvent.serialize((Log4jLogEvent)paramLogEvent, this.includeLocation));
/* 143 */         if (!bool) {
/* 144 */           error("Appender " + getName() + " is unable to write primary appenders. queue is full");
/*     */         }
/*     */       }
/* 147 */       if ((!bool) && (this.errorAppender != null)) {
/* 148 */         this.errorAppender.callAppender(paramLogEvent);
/*     */       }
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
/*     */   @PluginFactory
/*     */   public static AsyncAppender createAppender(@PluginElement("AppenderRef") AppenderRef[] paramArrayOfAppenderRef, @PluginAttribute("errorRef") @PluginAliases({"error-ref"}) String paramString1, @PluginAttribute("blocking") String paramString2, @PluginAttribute("bufferSize") String paramString3, @PluginAttribute("name") String paramString4, @PluginAttribute("includeLocation") String paramString5, @PluginElement("Filter") Filter paramFilter, @PluginConfiguration Configuration paramConfiguration, @PluginAttribute("ignoreExceptions") String paramString6)
/*     */   {
/* 177 */     if (paramString4 == null) {
/* 178 */       LOGGER.error("No name provided for AsyncAppender");
/* 179 */       return null;
/*     */     }
/* 181 */     if (paramArrayOfAppenderRef == null) {
/* 182 */       LOGGER.error("No appender references provided to AsyncAppender {}", new Object[] { paramString4 });
/*     */     }
/*     */     
/* 185 */     boolean bool1 = Booleans.parseBoolean(paramString2, true);
/* 186 */     int i = AbstractAppender.parseInt(paramString3, 128);
/* 187 */     boolean bool2 = Boolean.parseBoolean(paramString5);
/* 188 */     boolean bool3 = Booleans.parseBoolean(paramString6, true);
/*     */     
/* 190 */     return new AsyncAppender(paramString4, paramFilter, paramArrayOfAppenderRef, paramString1, i, bool1, bool3, paramConfiguration, bool2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class AsyncThread
/*     */     extends Thread
/*     */   {
/* 199 */     private volatile boolean shutdown = false;
/*     */     private final List<AppenderControl> appenders;
/*     */     private final BlockingQueue<Serializable> queue;
/*     */     
/*     */     public AsyncThread(BlockingQueue<Serializable> paramBlockingQueue) {
/* 204 */       this.appenders = paramBlockingQueue;
/* 205 */       BlockingQueue localBlockingQueue; this.queue = localBlockingQueue;
/* 206 */       setDaemon(true);
/* 207 */       setName("AsyncAppenderThread" + AsyncAppender.threadSequence.getAndIncrement());
/*     */     }
/*     */     
/*     */     public void run() { Serializable localSerializable;
/*     */       Log4jLogEvent localLog4jLogEvent;
/* 212 */       while (!this.shutdown)
/*     */       {
/*     */         try {
/* 215 */           localSerializable = (Serializable)this.queue.take();
/* 216 */           if ((localSerializable != null) && ((localSerializable instanceof String)) && ("Shutdown".equals(localSerializable.toString()))) {
/* 217 */             this.shutdown = true;
/* 218 */             continue;
/*     */           }
/*     */         }
/*     */         catch (InterruptedException localInterruptedException2) {}
/* 222 */         continue;
/*     */         
/* 224 */         localLog4jLogEvent = Log4jLogEvent.deserialize(localSerializable);
/* 225 */         localLog4jLogEvent.setEndOfBatch(this.queue.isEmpty());
/* 226 */         int i = 0;
/* 227 */         for (AppenderControl localAppenderControl2 : this.appenders) {
/*     */           try {
/* 229 */             localAppenderControl2.callAppender(localLog4jLogEvent);
/* 230 */             i = 1;
/*     */           }
/*     */           catch (Exception localException2) {}
/*     */         }
/*     */         
/* 235 */         if ((i == 0) && (AsyncAppender.this.errorAppender != null)) {
/*     */           try {
/* 237 */             AsyncAppender.this.errorAppender.callAppender(localLog4jLogEvent);
/*     */           }
/*     */           catch (Exception localException1) {}
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 244 */       while (!this.queue.isEmpty()) {
/*     */         try {
/* 246 */           localSerializable = (Serializable)this.queue.take();
/* 247 */           if ((localSerializable instanceof Log4jLogEvent)) {
/* 248 */             localLog4jLogEvent = Log4jLogEvent.deserialize(localSerializable);
/* 249 */             localLog4jLogEvent.setEndOfBatch(this.queue.isEmpty());
/* 250 */             for (AppenderControl localAppenderControl1 : this.appenders) {
/* 251 */               localAppenderControl1.callAppender(localLog4jLogEvent);
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (InterruptedException localInterruptedException1) {}
/*     */       }
/*     */     }
/*     */     
/*     */     public void shutdown()
/*     */     {
/* 261 */       this.shutdown = true;
/* 262 */       if (this.queue.isEmpty()) {
/* 263 */         this.queue.offer("Shutdown");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AsyncAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */