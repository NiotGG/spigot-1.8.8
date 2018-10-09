/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.persistence.Inheritance;
/*     */ import javax.persistence.InheritanceType;
/*     */ import javax.persistence.MappedSuperclass;
/*     */ import javax.persistence.Transient;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @MappedSuperclass
/*     */ @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
/*     */ public abstract class AbstractLogEventWrapperEntity
/*     */   implements LogEvent
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final LogEvent wrappedEvent;
/*     */   
/*     */   protected AbstractLogEventWrapperEntity()
/*     */   {
/*  81 */     this(new NullLogEvent(null));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractLogEventWrapperEntity(LogEvent paramLogEvent)
/*     */   {
/*  91 */     if (paramLogEvent == null) {
/*  92 */       throw new IllegalArgumentException("The wrapped event cannot be null.");
/*     */     }
/*  94 */     this.wrappedEvent = paramLogEvent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transient
/*     */   protected final LogEvent getWrappedEvent()
/*     */   {
/* 105 */     return this.wrappedEvent;
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
/*     */   public void setLevel(Level paramLevel) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLoggerName(String paramString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSource(StackTraceElement paramStackTraceElement) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMessage(Message paramMessage) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMarker(Marker paramMarker) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThreadName(String paramString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMillis(long paramLong) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setThrown(Throwable paramThrowable) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContextMap(Map<String, String> paramMap) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setContextStack(ThreadContext.ContextStack paramContextStack) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFQCN(String paramString) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transient
/*     */   public final boolean isIncludeLocation()
/*     */   {
/* 227 */     return getWrappedEvent().isIncludeLocation();
/*     */   }
/*     */   
/*     */   public final void setIncludeLocation(boolean paramBoolean)
/*     */   {
/* 232 */     getWrappedEvent().setIncludeLocation(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Transient
/*     */   public final boolean isEndOfBatch()
/*     */   {
/* 244 */     return getWrappedEvent().isEndOfBatch();
/*     */   }
/*     */   
/*     */   public final void setEndOfBatch(boolean paramBoolean)
/*     */   {
/* 249 */     getWrappedEvent().setEndOfBatch(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */   private static class NullLogEvent
/*     */     implements LogEvent
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */ 
/*     */     public Level getLevel()
/*     */     {
/* 261 */       return null;
/*     */     }
/*     */     
/*     */     public String getLoggerName()
/*     */     {
/* 266 */       return null;
/*     */     }
/*     */     
/*     */     public StackTraceElement getSource()
/*     */     {
/* 271 */       return null;
/*     */     }
/*     */     
/*     */     public Message getMessage()
/*     */     {
/* 276 */       return null;
/*     */     }
/*     */     
/*     */     public Marker getMarker()
/*     */     {
/* 281 */       return null;
/*     */     }
/*     */     
/*     */     public String getThreadName()
/*     */     {
/* 286 */       return null;
/*     */     }
/*     */     
/*     */     public long getMillis()
/*     */     {
/* 291 */       return 0L;
/*     */     }
/*     */     
/*     */     public Throwable getThrown()
/*     */     {
/* 296 */       return null;
/*     */     }
/*     */     
/*     */     public Map<String, String> getContextMap()
/*     */     {
/* 301 */       return null;
/*     */     }
/*     */     
/*     */     public ThreadContext.ContextStack getContextStack()
/*     */     {
/* 306 */       return null;
/*     */     }
/*     */     
/*     */     public String getFQCN()
/*     */     {
/* 311 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isIncludeLocation()
/*     */     {
/* 316 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void setIncludeLocation(boolean paramBoolean) {}
/*     */     
/*     */ 
/*     */     public boolean isEndOfBatch()
/*     */     {
/* 326 */       return false;
/*     */     }
/*     */     
/*     */     public void setEndOfBatch(boolean paramBoolean) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\AbstractLogEventWrapperEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */