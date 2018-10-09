/*     */ package org.apache.logging.log4j.core.appender.db.nosql;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext.ContextStack;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager.AbstractFactoryData;
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
/*     */ public final class NoSQLDatabaseManager<W>
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  34 */   private static final NoSQLDatabaseManagerFactory FACTORY = new NoSQLDatabaseManagerFactory(null);
/*     */   
/*     */   private final NoSQLProvider<NoSQLConnection<W, ? extends NoSQLObject<W>>> provider;
/*     */   
/*     */   private NoSQLConnection<W, ? extends NoSQLObject<W>> connection;
/*     */   
/*     */   private NoSQLDatabaseManager(String paramString, int paramInt, NoSQLProvider<NoSQLConnection<W, ? extends NoSQLObject<W>>> paramNoSQLProvider)
/*     */   {
/*  42 */     super(paramString, paramInt);
/*  43 */     this.provider = paramNoSQLProvider;
/*     */   }
/*     */   
/*     */   protected void connectInternal()
/*     */   {
/*  48 */     this.connection = this.provider.getConnection();
/*     */   }
/*     */   
/*     */   protected void disconnectInternal()
/*     */   {
/*  53 */     if ((this.connection != null) && (!this.connection.isClosed())) {
/*  54 */       this.connection.close();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeInternal(LogEvent paramLogEvent)
/*     */   {
/*  60 */     if ((!isConnected()) || (this.connection == null) || (this.connection.isClosed())) {
/*  61 */       throw new AppenderLoggingException("Cannot write logging event; NoSQL manager not connected to the database.");
/*     */     }
/*     */     
/*     */ 
/*  65 */     NoSQLObject localNoSQLObject = this.connection.createObject();
/*  66 */     localNoSQLObject.set("level", paramLogEvent.getLevel());
/*  67 */     localNoSQLObject.set("loggerName", paramLogEvent.getLoggerName());
/*  68 */     localNoSQLObject.set("message", paramLogEvent.getMessage() == null ? null : paramLogEvent.getMessage().getFormattedMessage());
/*     */     
/*  70 */     StackTraceElement localStackTraceElement = paramLogEvent.getSource();
/*  71 */     if (localStackTraceElement == null) {
/*  72 */       localNoSQLObject.set("source", (Object)null);
/*     */     } else {
/*  74 */       localNoSQLObject.set("source", convertStackTraceElement(localStackTraceElement));
/*     */     }
/*     */     
/*  77 */     Marker localMarker = paramLogEvent.getMarker();
/*  78 */     if (localMarker == null) {
/*  79 */       localNoSQLObject.set("marker", (Object)null);
/*     */     } else {
/*  81 */       localObject1 = this.connection.createObject();
/*  82 */       localObject2 = localObject1;
/*  83 */       ((NoSQLObject)localObject2).set("name", localMarker.getName());
/*  84 */       while (localMarker.getParent() != null) {
/*  85 */         localMarker = localMarker.getParent();
/*  86 */         localObject3 = this.connection.createObject();
/*  87 */         ((NoSQLObject)localObject3).set("name", localMarker.getName());
/*  88 */         ((NoSQLObject)localObject2).set("parent", (NoSQLObject)localObject3);
/*  89 */         localObject2 = localObject3;
/*     */       }
/*  91 */       localNoSQLObject.set("marker", (NoSQLObject)localObject1);
/*     */     }
/*     */     
/*  94 */     localNoSQLObject.set("threadName", paramLogEvent.getThreadName());
/*  95 */     localNoSQLObject.set("millis", Long.valueOf(paramLogEvent.getMillis()));
/*  96 */     localNoSQLObject.set("date", new Date(paramLogEvent.getMillis()));
/*     */     
/*     */ 
/*  99 */     Object localObject1 = paramLogEvent.getThrown();
/* 100 */     Object localObject4; if (localObject1 == null) {
/* 101 */       localNoSQLObject.set("thrown", (Object)null);
/*     */     } else {
/* 103 */       localObject2 = this.connection.createObject();
/* 104 */       localObject3 = localObject2;
/* 105 */       ((NoSQLObject)localObject3).set("type", localObject1.getClass().getName());
/* 106 */       ((NoSQLObject)localObject3).set("message", ((Throwable)localObject1).getMessage());
/* 107 */       ((NoSQLObject)localObject3).set("stackTrace", convertStackTrace(((Throwable)localObject1).getStackTrace()));
/* 108 */       while (((Throwable)localObject1).getCause() != null) {
/* 109 */         localObject1 = ((Throwable)localObject1).getCause();
/* 110 */         localObject4 = this.connection.createObject();
/* 111 */         ((NoSQLObject)localObject4).set("type", localObject1.getClass().getName());
/* 112 */         ((NoSQLObject)localObject4).set("message", ((Throwable)localObject1).getMessage());
/* 113 */         ((NoSQLObject)localObject4).set("stackTrace", convertStackTrace(((Throwable)localObject1).getStackTrace()));
/* 114 */         ((NoSQLObject)localObject3).set("cause", (NoSQLObject)localObject4);
/* 115 */         localObject3 = localObject4;
/*     */       }
/*     */       
/* 118 */       localNoSQLObject.set("thrown", (NoSQLObject)localObject2);
/*     */     }
/*     */     
/* 121 */     Object localObject2 = paramLogEvent.getContextMap();
/* 122 */     if (localObject2 == null) {
/* 123 */       localNoSQLObject.set("contextMap", (Object)null);
/*     */     } else {
/* 125 */       localObject3 = this.connection.createObject();
/* 126 */       for (localObject4 = ((Map)localObject2).entrySet().iterator(); ((Iterator)localObject4).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject4).next();
/* 127 */         ((NoSQLObject)localObject3).set((String)localEntry.getKey(), localEntry.getValue());
/*     */       }
/* 129 */       localNoSQLObject.set("contextMap", (NoSQLObject)localObject3);
/*     */     }
/*     */     
/* 132 */     Object localObject3 = paramLogEvent.getContextStack();
/* 133 */     if (localObject3 == null) {
/* 134 */       localNoSQLObject.set("contextStack", (Object)null);
/*     */     } else {
/* 136 */       localNoSQLObject.set("contextStack", ((ThreadContext.ContextStack)localObject3).asList().toArray());
/*     */     }
/*     */     
/* 139 */     this.connection.insertObject(localNoSQLObject);
/*     */   }
/*     */   
/*     */   private NoSQLObject<W>[] convertStackTrace(StackTraceElement[] paramArrayOfStackTraceElement) {
/* 143 */     NoSQLObject[] arrayOfNoSQLObject = this.connection.createList(paramArrayOfStackTraceElement.length);
/* 144 */     for (int i = 0; i < paramArrayOfStackTraceElement.length; i++) {
/* 145 */       arrayOfNoSQLObject[i] = convertStackTraceElement(paramArrayOfStackTraceElement[i]);
/*     */     }
/* 147 */     return arrayOfNoSQLObject;
/*     */   }
/*     */   
/*     */   private NoSQLObject<W> convertStackTraceElement(StackTraceElement paramStackTraceElement) {
/* 151 */     NoSQLObject localNoSQLObject = this.connection.createObject();
/* 152 */     localNoSQLObject.set("className", paramStackTraceElement.getClassName());
/* 153 */     localNoSQLObject.set("methodName", paramStackTraceElement.getMethodName());
/* 154 */     localNoSQLObject.set("fileName", paramStackTraceElement.getFileName());
/* 155 */     localNoSQLObject.set("lineNumber", Integer.valueOf(paramStackTraceElement.getLineNumber()));
/* 156 */     return localNoSQLObject;
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
/*     */   public static NoSQLDatabaseManager<?> getNoSQLDatabaseManager(String paramString, int paramInt, NoSQLProvider<?> paramNoSQLProvider)
/*     */   {
/* 169 */     return (NoSQLDatabaseManager)AbstractDatabaseManager.getManager(paramString, new FactoryData(paramInt, paramNoSQLProvider), FACTORY);
/*     */   }
/*     */   
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final NoSQLProvider<?> provider;
/*     */     
/*     */     protected FactoryData(int paramInt, NoSQLProvider<?> paramNoSQLProvider)
/*     */     {
/* 179 */       super();
/* 180 */       this.provider = paramNoSQLProvider;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class NoSQLDatabaseManagerFactory
/*     */     implements ManagerFactory<NoSQLDatabaseManager<?>, NoSQLDatabaseManager.FactoryData>
/*     */   {
/*     */     public NoSQLDatabaseManager<?> createManager(String paramString, NoSQLDatabaseManager.FactoryData paramFactoryData)
/*     */     {
/* 192 */       return new NoSQLDatabaseManager(paramString, paramFactoryData.getBufferSize(), NoSQLDatabaseManager.FactoryData.access$100(paramFactoryData), null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */