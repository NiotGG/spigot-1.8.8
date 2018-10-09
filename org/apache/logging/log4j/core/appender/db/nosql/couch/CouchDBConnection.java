/*    */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
/*    */ import org.lightcouch.CouchDbClient;
/*    */ import org.lightcouch.Response;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CouchDBConnection
/*    */   implements NoSQLConnection<Map<String, Object>, CouchDBObject>
/*    */ {
/*    */   private final CouchDbClient client;
/* 32 */   private boolean closed = false;
/*    */   
/*    */   public CouchDBConnection(CouchDbClient paramCouchDbClient) {
/* 35 */     this.client = paramCouchDbClient;
/*    */   }
/*    */   
/*    */   public CouchDBObject createObject()
/*    */   {
/* 40 */     return new CouchDBObject();
/*    */   }
/*    */   
/*    */   public CouchDBObject[] createList(int paramInt)
/*    */   {
/* 45 */     return new CouchDBObject[paramInt];
/*    */   }
/*    */   
/*    */   public void insertObject(NoSQLObject<Map<String, Object>> paramNoSQLObject)
/*    */   {
/*    */     try {
/* 51 */       Response localResponse = this.client.save(paramNoSQLObject.unwrap());
/* 52 */       if ((localResponse.getError() != null) && (localResponse.getError().length() > 0)) {
/* 53 */         throw new AppenderLoggingException("Failed to write log event to CouchDB due to error: " + localResponse.getError() + ".");
/*    */       }
/*    */     }
/*    */     catch (Exception localException) {
/* 57 */       throw new AppenderLoggingException("Failed to write log event to CouchDB due to error: " + localException.getMessage(), localException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public synchronized void close()
/*    */   {
/* 64 */     this.closed = true;
/* 65 */     this.client.shutdown();
/*    */   }
/*    */   
/*    */   public synchronized boolean isClosed()
/*    */   {
/* 70 */     return this.closed;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */