/*    */ package com.mojang.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import java.util.concurrent.locks.Lock;
/*    */ import java.util.concurrent.locks.ReadWriteLock;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*    */ 
/*    */ @Plugin(name="Queue", category="Core", elementType="appender", printObject=true)
/*    */ public class QueueLogAppender extends AbstractAppender
/*    */ {
/*    */   private static final int MAX_CAPACITY = 250;
/* 24 */   private static final Map<String, BlockingQueue<String>> QUEUES = new HashMap();
/* 25 */   private static final ReadWriteLock QUEUE_LOCK = new java.util.concurrent.locks.ReentrantReadWriteLock();
/*    */   private final BlockingQueue<String> queue;
/*    */   
/*    */   public QueueLogAppender(String paramString, Filter paramFilter, Layout<? extends Serializable> paramLayout, boolean paramBoolean, BlockingQueue<String> paramBlockingQueue)
/*    */   {
/* 30 */     super(paramString, paramFilter, paramLayout, paramBoolean);
/* 31 */     this.queue = paramBlockingQueue;
/*    */   }
/*    */   
/*    */   public void append(LogEvent paramLogEvent)
/*    */   {
/* 36 */     if (this.queue.size() >= 250) {
/* 37 */       this.queue.clear();
/*    */     }
/* 39 */     this.queue.add(getLayout().toSerializable(paramLogEvent).toString());
/*    */   }
/*    */   
/*    */   @org.apache.logging.log4j.core.config.plugins.PluginFactory
/*    */   public static QueueLogAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("target") String paramString3) {
/* 44 */     boolean bool = Boolean.parseBoolean(paramString2);
/*    */     
/* 46 */     if (paramString1 == null) {
/* 47 */       LOGGER.error("No name provided for QueueLogAppender");
/* 48 */       return null;
/*    */     }
/*    */     
/* 51 */     if (paramString3 == null) {
/* 52 */       paramString3 = paramString1;
/*    */     }
/*    */     
/* 55 */     QUEUE_LOCK.writeLock().lock();
/* 56 */     Object localObject = (BlockingQueue)QUEUES.get(paramString3);
/* 57 */     if (localObject == null) {
/* 58 */       localObject = new LinkedBlockingQueue();
/* 59 */       QUEUES.put(paramString3, localObject);
/*    */     }
/* 61 */     QUEUE_LOCK.writeLock().unlock();
/*    */     
/* 63 */     if (paramLayout == null) {
/* 64 */       paramLayout = PatternLayout.createLayout(null, null, null, null, null);
/*    */     }
/*    */     
/* 67 */     return new QueueLogAppender(paramString1, paramFilter, paramLayout, bool, (BlockingQueue)localObject);
/*    */   }
/*    */   
/*    */   public static String getNextLogEvent(String paramString) {
/* 71 */     QUEUE_LOCK.readLock().lock();
/* 72 */     BlockingQueue localBlockingQueue = (BlockingQueue)QUEUES.get(paramString);
/* 73 */     QUEUE_LOCK.readLock().unlock();
/*    */     
/* 75 */     if (localBlockingQueue != null) {
/*    */       try {
/* 77 */         return (String)localBlockingQueue.take();
/*    */       }
/*    */       catch (InterruptedException localInterruptedException) {}
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\util\QueueLogAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */