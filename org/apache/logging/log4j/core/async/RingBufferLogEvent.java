/*     */ package org.apache.logging.log4j.core.async;
/*     */ 
/*     */ import com.lmax.disruptor.EventFactory;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.SimpleMessage;
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
/*     */ public class RingBufferLogEvent
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = 8462119088943934758L;
/*     */   
/*     */   private static class Factory
/*     */     implements EventFactory<RingBufferLogEvent>
/*     */   {
/*     */     public RingBufferLogEvent newInstance()
/*     */     {
/*  47 */       return new RingBufferLogEvent();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  52 */   public static final Factory FACTORY = new Factory(null);
/*     */   
/*     */   private AsyncLogger asyncLogger;
/*     */   
/*     */   private String loggerName;
/*     */   
/*     */   private Marker marker;
/*     */   
/*     */   private String fqcn;
/*     */   
/*     */   private Level level;
/*     */   private Message message;
/*     */   private Throwable thrown;
/*     */   private Map<String, String> contextMap;
/*     */   private ThreadContext.ContextStack contextStack;
/*     */   private String threadName;
/*     */   private StackTraceElement location;
/*     */   private long currentTimeMillis;
/*     */   private boolean endOfBatch;
/*     */   private boolean includeLocation;
/*     */   
/*     */   public void setValues(AsyncLogger paramAsyncLogger, String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, Throwable paramThrowable, Map<String, String> paramMap, ThreadContext.ContextStack paramContextStack, String paramString3, StackTraceElement paramStackTraceElement, long paramLong)
/*     */   {
/*  75 */     this.asyncLogger = paramAsyncLogger;
/*  76 */     this.loggerName = paramString1;
/*  77 */     this.marker = paramMarker;
/*  78 */     this.fqcn = paramString2;
/*  79 */     this.level = paramLevel;
/*  80 */     this.message = paramMessage;
/*  81 */     this.thrown = paramThrowable;
/*  82 */     this.contextMap = paramMap;
/*  83 */     this.contextStack = paramContextStack;
/*  84 */     this.threadName = paramString3;
/*  85 */     this.location = paramStackTraceElement;
/*  86 */     this.currentTimeMillis = paramLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execute(boolean paramBoolean)
/*     */   {
/*  97 */     this.endOfBatch = paramBoolean;
/*  98 */     this.asyncLogger.actualAsyncLog(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEndOfBatch()
/*     */   {
/* 110 */     return this.endOfBatch;
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean paramBoolean)
/*     */   {
/* 115 */     this.endOfBatch = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isIncludeLocation()
/*     */   {
/* 120 */     return this.includeLocation;
/*     */   }
/*     */   
/*     */   public void setIncludeLocation(boolean paramBoolean)
/*     */   {
/* 125 */     this.includeLocation = paramBoolean;
/*     */   }
/*     */   
/*     */   public String getLoggerName()
/*     */   {
/* 130 */     return this.loggerName;
/*     */   }
/*     */   
/*     */   public Marker getMarker()
/*     */   {
/* 135 */     return this.marker;
/*     */   }
/*     */   
/*     */   public String getFQCN()
/*     */   {
/* 140 */     return this.fqcn;
/*     */   }
/*     */   
/*     */   public Level getLevel()
/*     */   {
/* 145 */     return this.level;
/*     */   }
/*     */   
/*     */   public Message getMessage()
/*     */   {
/* 150 */     if (this.message == null) {
/* 151 */       this.message = new SimpleMessage("");
/*     */     }
/* 153 */     return this.message;
/*     */   }
/*     */   
/*     */   public Throwable getThrown()
/*     */   {
/* 158 */     return this.thrown;
/*     */   }
/*     */   
/*     */   public Map<String, String> getContextMap()
/*     */   {
/* 163 */     return this.contextMap;
/*     */   }
/*     */   
/*     */   public ThreadContext.ContextStack getContextStack()
/*     */   {
/* 168 */     return this.contextStack;
/*     */   }
/*     */   
/*     */   public String getThreadName()
/*     */   {
/* 173 */     return this.threadName;
/*     */   }
/*     */   
/*     */   public StackTraceElement getSource()
/*     */   {
/* 178 */     return this.location;
/*     */   }
/*     */   
/*     */   public long getMillis()
/*     */   {
/* 183 */     return this.currentTimeMillis;
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
/*     */   public void mergePropertiesIntoContextMap(Map<Property, Boolean> paramMap, StrSubstitutor paramStrSubstitutor)
/*     */   {
/* 197 */     if (paramMap == null) {
/* 198 */       return;
/*     */     }
/*     */     
/* 201 */     HashMap localHashMap = this.contextMap == null ? new HashMap() : new HashMap(this.contextMap);
/*     */     
/*     */ 
/* 204 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 205 */       Property localProperty = (Property)localEntry.getKey();
/* 206 */       if (!localHashMap.containsKey(localProperty.getName()))
/*     */       {
/*     */ 
/* 209 */         String str = ((Boolean)localEntry.getValue()).booleanValue() ? paramStrSubstitutor.replace(localProperty.getValue()) : localProperty.getValue();
/*     */         
/* 211 */         localHashMap.put(localProperty.getName(), str);
/*     */       } }
/* 213 */     this.contextMap = localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 221 */     setValues(null, null, null, null, null, null, null, null, null, null, null, 0L);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\async\RingBufferLogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */