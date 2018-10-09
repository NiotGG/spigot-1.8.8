/*     */ package org.apache.logging.log4j.core.appender.db;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractManager;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
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
/*     */ public abstract class AbstractDatabaseManager
/*     */   extends AbstractManager
/*     */ {
/*     */   private final ArrayList<LogEvent> buffer;
/*     */   private final int bufferSize;
/*  32 */   private boolean connected = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractDatabaseManager(String paramString, int paramInt)
/*     */   {
/*  42 */     super(paramString);
/*  43 */     this.bufferSize = paramInt;
/*  44 */     this.buffer = new ArrayList(paramInt + 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void connectInternal()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void connect()
/*     */   {
/*  58 */     if (!isConnected()) {
/*     */       try {
/*  60 */         connectInternal();
/*  61 */         this.connected = true;
/*     */       } catch (Exception localException) {
/*  63 */         LOGGER.error("Could not connect to database using logging manager [{}].", new Object[] { getName(), localException });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void disconnectInternal()
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void disconnect()
/*     */   {
/*  81 */     flush();
/*  82 */     if (isConnected()) {
/*     */       try {
/*  84 */         disconnectInternal();
/*     */       } catch (Exception localException) {
/*  86 */         LOGGER.warn("Error while disconnecting from database using logging manager [{}].", new Object[] { getName(), localException });
/*     */       } finally {
/*  88 */         this.connected = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isConnected()
/*     */   {
/* 100 */     return this.connected;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void writeInternal(LogEvent paramLogEvent);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void flush()
/*     */   {
/* 116 */     if ((isConnected()) && (this.buffer.size() > 0)) {
/* 117 */       for (LogEvent localLogEvent : this.buffer) {
/* 118 */         writeInternal(localLogEvent);
/*     */       }
/* 120 */       this.buffer.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final synchronized void write(LogEvent paramLogEvent)
/*     */   {
/* 130 */     if (this.bufferSize > 0) {
/* 131 */       this.buffer.add(paramLogEvent);
/* 132 */       if ((this.buffer.size() >= this.bufferSize) || (paramLogEvent.isEndOfBatch())) {
/* 133 */         flush();
/*     */       }
/*     */     } else {
/* 136 */       writeInternal(paramLogEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void releaseSub()
/*     */   {
/* 142 */     disconnect();
/*     */   }
/*     */   
/*     */   public final String toString()
/*     */   {
/* 147 */     return getName();
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
/*     */   protected static <M extends AbstractDatabaseManager, T extends AbstractFactoryData> M getManager(String paramString, T paramT, ManagerFactory<M, T> paramManagerFactory)
/*     */   {
/* 165 */     return (AbstractDatabaseManager)AbstractManager.getManager(paramString, paramManagerFactory, paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static abstract class AbstractFactoryData
/*     */   {
/*     */     private final int bufferSize;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     protected AbstractFactoryData(int paramInt)
/*     */     {
/* 181 */       this.bufferSize = paramInt;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getBufferSize()
/*     */     {
/* 190 */       return this.bufferSize;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\AbstractDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */