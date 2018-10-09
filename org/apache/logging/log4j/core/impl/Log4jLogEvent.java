/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Property;
/*     */ import org.apache.logging.log4j.message.LoggerNameAwareMessage;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.TimestampMessage;
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
/*     */ public class Log4jLogEvent
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = -1351367343806656055L;
/*     */   private static final String NOT_AVAIL = "?";
/*     */   private final String fqcnOfLogger;
/*     */   private final Marker marker;
/*     */   private final Level level;
/*     */   private final String name;
/*     */   private final Message message;
/*     */   private final long timestamp;
/*     */   private final ThrowableProxy throwable;
/*     */   private final Map<String, String> mdc;
/*     */   private final ThreadContext.ContextStack ndc;
/*  52 */   private String threadName = null;
/*     */   private StackTraceElement location;
/*     */   private boolean includeLocation;
/*  55 */   private boolean endOfBatch = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public Log4jLogEvent(long paramLong)
/*     */   {
/*  61 */     this("", null, "", null, null, (ThrowableProxy)null, null, null, null, null, paramLong);
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
/*     */   public Log4jLogEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*     */   {
/*  75 */     this(paramString1, paramMarker, paramString2, paramLevel, paramMessage, null, paramThrowable);
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
/*     */   public Log4jLogEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, List<Property> paramList, Throwable paramThrowable)
/*     */   {
/*  90 */     this(paramString1, paramMarker, paramString2, paramLevel, paramMessage, paramThrowable, createMap(paramList), ThreadContext.getDepth() == 0 ? null : ThreadContext.cloneStack(), null, null, System.currentTimeMillis());
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
/*     */   public Log4jLogEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, Throwable paramThrowable, Map<String, String> paramMap, ThreadContext.ContextStack paramContextStack, String paramString3, StackTraceElement paramStackTraceElement, long paramLong)
/*     */   {
/* 114 */     this(paramString1, paramMarker, paramString2, paramLevel, paramMessage, paramThrowable == null ? null : new ThrowableProxy(paramThrowable), paramMap, paramContextStack, paramString3, paramStackTraceElement, paramLong);
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
/*     */   public static Log4jLogEvent createEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, ThrowableProxy paramThrowableProxy, Map<String, String> paramMap, ThreadContext.ContextStack paramContextStack, String paramString3, StackTraceElement paramStackTraceElement, long paramLong)
/*     */   {
/* 137 */     return new Log4jLogEvent(paramString1, paramMarker, paramString2, paramLevel, paramMessage, paramThrowableProxy, paramMap, paramContextStack, paramString3, paramStackTraceElement, paramLong);
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
/*     */   private Log4jLogEvent(String paramString1, Marker paramMarker, String paramString2, Level paramLevel, Message paramMessage, ThrowableProxy paramThrowableProxy, Map<String, String> paramMap, ThreadContext.ContextStack paramContextStack, String paramString3, StackTraceElement paramStackTraceElement, long paramLong)
/*     */   {
/* 158 */     this.name = paramString1;
/* 159 */     this.marker = paramMarker;
/* 160 */     this.fqcnOfLogger = paramString2;
/* 161 */     this.level = paramLevel;
/* 162 */     this.message = paramMessage;
/* 163 */     this.throwable = paramThrowableProxy;
/* 164 */     this.mdc = paramMap;
/* 165 */     this.ndc = paramContextStack;
/* 166 */     this.timestamp = ((paramMessage instanceof TimestampMessage) ? ((TimestampMessage)paramMessage).getTimestamp() : paramLong);
/* 167 */     this.threadName = paramString3;
/* 168 */     this.location = paramStackTraceElement;
/* 169 */     if ((paramMessage != null) && ((paramMessage instanceof LoggerNameAwareMessage))) {
/* 170 */       ((LoggerNameAwareMessage)paramMessage).setLoggerName(this.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private static Map<String, String> createMap(List<Property> paramList) {
/* 175 */     Map localMap = ThreadContext.getImmutableContext();
/* 176 */     if ((localMap == null) && ((paramList == null) || (paramList.size() == 0))) {
/* 177 */       return null;
/*     */     }
/* 179 */     if ((paramList == null) || (paramList.size() == 0)) {
/* 180 */       return localMap;
/*     */     }
/* 182 */     HashMap localHashMap = new HashMap(localMap);
/*     */     
/* 184 */     for (Property localProperty : paramList) {
/* 185 */       if (!localHashMap.containsKey(localProperty.getName())) {
/* 186 */         localHashMap.put(localProperty.getName(), localProperty.getValue());
/*     */       }
/*     */     }
/* 189 */     return Collections.unmodifiableMap(localHashMap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getLevel()
/*     */   {
/* 198 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLoggerName()
/*     */   {
/* 207 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Message getMessage()
/*     */   {
/* 216 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getThreadName()
/*     */   {
/* 225 */     if (this.threadName == null) {
/* 226 */       this.threadName = Thread.currentThread().getName();
/*     */     }
/* 228 */     return this.threadName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getMillis()
/*     */   {
/* 237 */     return this.timestamp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrown()
/*     */   {
/* 246 */     return this.throwable == null ? null : this.throwable.getThrowable();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThrowableProxy getThrownProxy()
/*     */   {
/* 254 */     return this.throwable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Marker getMarker()
/*     */   {
/* 264 */     return this.marker;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFQCN()
/*     */   {
/* 273 */     return this.fqcnOfLogger;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContextMap()
/*     */   {
/* 282 */     return this.mdc == null ? ThreadContext.EMPTY_MAP : this.mdc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThreadContext.ContextStack getContextStack()
/*     */   {
/* 291 */     return this.ndc == null ? ThreadContext.EMPTY_STACK : this.ndc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StackTraceElement getSource()
/*     */   {
/* 301 */     if (this.location != null) {
/* 302 */       return this.location;
/*     */     }
/* 304 */     if ((this.fqcnOfLogger == null) || (!this.includeLocation)) {
/* 305 */       return null;
/*     */     }
/* 307 */     this.location = calcLocation(this.fqcnOfLogger);
/* 308 */     return this.location;
/*     */   }
/*     */   
/*     */   public static StackTraceElement calcLocation(String paramString) {
/* 312 */     if (paramString == null) {
/* 313 */       return null;
/*     */     }
/* 315 */     StackTraceElement[] arrayOfStackTraceElement1 = Thread.currentThread().getStackTrace();
/* 316 */     int i = 0;
/* 317 */     for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1) {
/* 318 */       String str = localStackTraceElement.getClassName();
/* 319 */       if (i != 0) {
/* 320 */         if (!paramString.equals(str))
/*     */         {
/*     */ 
/* 323 */           return localStackTraceElement;
/*     */         }
/*     */       }
/* 326 */       else if (paramString.equals(str))
/* 327 */         i = 1; else {
/* 328 */         if ("?".equals(str))
/*     */           break;
/*     */       }
/*     */     }
/* 332 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isIncludeLocation()
/*     */   {
/* 337 */     return this.includeLocation;
/*     */   }
/*     */   
/*     */   public void setIncludeLocation(boolean paramBoolean)
/*     */   {
/* 342 */     this.includeLocation = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isEndOfBatch()
/*     */   {
/* 347 */     return this.endOfBatch;
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean paramBoolean)
/*     */   {
/* 352 */     this.endOfBatch = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object writeReplace()
/*     */   {
/* 360 */     return new LogEventProxy(this, this.includeLocation);
/*     */   }
/*     */   
/*     */   public static Serializable serialize(Log4jLogEvent paramLog4jLogEvent, boolean paramBoolean)
/*     */   {
/* 365 */     return new LogEventProxy(paramLog4jLogEvent, paramBoolean);
/*     */   }
/*     */   
/*     */   public static Log4jLogEvent deserialize(Serializable paramSerializable) {
/* 369 */     if (paramSerializable == null) {
/* 370 */       throw new NullPointerException("Event cannot be null");
/*     */     }
/* 372 */     if ((paramSerializable instanceof LogEventProxy)) {
/* 373 */       LogEventProxy localLogEventProxy = (LogEventProxy)paramSerializable;
/* 374 */       Log4jLogEvent localLog4jLogEvent = new Log4jLogEvent(localLogEventProxy.name, localLogEventProxy.marker, localLogEventProxy.fqcnOfLogger, localLogEventProxy.level, localLogEventProxy.message, localLogEventProxy.throwable, localLogEventProxy.mdc, localLogEventProxy.ndc, localLogEventProxy.threadName, localLogEventProxy.location, localLogEventProxy.timestamp);
/*     */       
/*     */ 
/*     */ 
/* 378 */       localLog4jLogEvent.setEndOfBatch(localLogEventProxy.isEndOfBatch);
/* 379 */       localLog4jLogEvent.setIncludeLocation(localLogEventProxy.isLocationRequired);
/* 380 */       return localLog4jLogEvent;
/*     */     }
/* 382 */     throw new IllegalArgumentException("Event is not a serialized LogEvent: " + paramSerializable.toString());
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 386 */     throw new InvalidObjectException("Proxy required");
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 391 */     StringBuilder localStringBuilder = new StringBuilder();
/* 392 */     String str = this.name.isEmpty() ? "root" : this.name;
/* 393 */     localStringBuilder.append("Logger=").append(str);
/* 394 */     localStringBuilder.append(" Level=").append(this.level.name());
/* 395 */     localStringBuilder.append(" Message=").append(this.message.getFormattedMessage());
/* 396 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   private static class LogEventProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7139032940312647146L;
/*     */     private final String fqcnOfLogger;
/*     */     private final Marker marker;
/*     */     private final Level level;
/*     */     private final String name;
/*     */     private final Message message;
/*     */     private final long timestamp;
/*     */     private final ThrowableProxy throwable;
/*     */     private final Map<String, String> mdc;
/*     */     private final ThreadContext.ContextStack ndc;
/*     */     private final String threadName;
/*     */     private final StackTraceElement location;
/*     */     private final boolean isLocationRequired;
/*     */     private final boolean isEndOfBatch;
/*     */     
/*     */     public LogEventProxy(Log4jLogEvent paramLog4jLogEvent, boolean paramBoolean)
/*     */     {
/* 420 */       this.fqcnOfLogger = paramLog4jLogEvent.fqcnOfLogger;
/* 421 */       this.marker = paramLog4jLogEvent.marker;
/* 422 */       this.level = paramLog4jLogEvent.level;
/* 423 */       this.name = paramLog4jLogEvent.name;
/* 424 */       this.message = paramLog4jLogEvent.message;
/* 425 */       this.timestamp = paramLog4jLogEvent.timestamp;
/* 426 */       this.throwable = paramLog4jLogEvent.throwable;
/* 427 */       this.mdc = paramLog4jLogEvent.mdc;
/* 428 */       this.ndc = paramLog4jLogEvent.ndc;
/* 429 */       this.location = (paramBoolean ? paramLog4jLogEvent.getSource() : null);
/* 430 */       this.threadName = paramLog4jLogEvent.getThreadName();
/* 431 */       this.isLocationRequired = paramBoolean;
/* 432 */       this.isEndOfBatch = paramLog4jLogEvent.endOfBatch;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Object readResolve()
/*     */     {
/* 440 */       Log4jLogEvent localLog4jLogEvent = new Log4jLogEvent(this.name, this.marker, this.fqcnOfLogger, this.level, this.message, this.throwable, this.mdc, this.ndc, this.threadName, this.location, this.timestamp, null);
/*     */       
/*     */ 
/* 443 */       localLog4jLogEvent.setEndOfBatch(this.isEndOfBatch);
/* 444 */       localLog4jLogEvent.setIncludeLocation(this.isLocationRequired);
/* 445 */       return localLog4jLogEvent;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\Log4jLogEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */