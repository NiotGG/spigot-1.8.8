/*     */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*     */ 
/*     */ import com.mongodb.BasicDBObject;
/*     */ import com.mongodb.DB;
/*     */ import com.mongodb.DBCollection;
/*     */ import com.mongodb.DBObject;
/*     */ import com.mongodb.DBTCPConnector;
/*     */ import com.mongodb.Mongo;
/*     */ import com.mongodb.MongoException;
/*     */ import com.mongodb.WriteConcern;
/*     */ import com.mongodb.WriteResult;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLConnection;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.bson.BSON;
/*     */ import org.bson.Transformer;
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
/*     */ public final class MongoDBConnection
/*     */   implements NoSQLConnection<BasicDBObject, MongoDBObject>
/*     */ {
/*  41 */   private static final Logger LOGGER = ;
/*     */   private final DBCollection collection;
/*     */   
/*  44 */   static { BSON.addDecodingHook(Level.class, new Transformer()
/*     */     {
/*     */       public Object transform(Object paramAnonymousObject) {
/*  47 */         if ((paramAnonymousObject instanceof Level)) {
/*  48 */           return ((Level)paramAnonymousObject).name();
/*     */         }
/*  50 */         return paramAnonymousObject;
/*     */       }
/*     */     }); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MongoDBConnection(DB paramDB, WriteConcern paramWriteConcern, String paramString)
/*     */   {
/*  60 */     this.mongo = paramDB.getMongo();
/*  61 */     this.collection = paramDB.getCollection(paramString);
/*  62 */     this.writeConcern = paramWriteConcern;
/*     */   }
/*     */   
/*     */   public MongoDBObject createObject()
/*     */   {
/*  67 */     return new MongoDBObject();
/*     */   }
/*     */   
/*     */   public MongoDBObject[] createList(int paramInt)
/*     */   {
/*  72 */     return new MongoDBObject[paramInt];
/*     */   }
/*     */   
/*     */   public void insertObject(NoSQLObject<BasicDBObject> paramNoSQLObject)
/*     */   {
/*     */     try {
/*  78 */       WriteResult localWriteResult = this.collection.insert((DBObject)paramNoSQLObject.unwrap(), this.writeConcern);
/*  79 */       if ((localWriteResult.getError() != null) && (localWriteResult.getError().length() > 0)) {
/*  80 */         throw new AppenderLoggingException("Failed to write log event to MongoDB due to error: " + localWriteResult.getError() + ".");
/*     */       }
/*     */     }
/*     */     catch (MongoException localMongoException) {
/*  84 */       throw new AppenderLoggingException("Failed to write log event to MongoDB due to error: " + localMongoException.getMessage(), localMongoException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void close()
/*     */   {
/*  91 */     this.mongo.close();
/*     */   }
/*     */   
/*     */   public boolean isClosed()
/*     */   {
/*  96 */     return !this.mongo.getConnector().isOpen();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Mongo mongo;
/*     */   
/*     */ 
/*     */ 
/*     */   private final WriteConcern writeConcern;
/*     */   
/*     */ 
/*     */   static void authenticate(DB paramDB, String paramString1, String paramString2)
/*     */   {
/*     */     try
/*     */     {
/* 113 */       if (!paramDB.authenticate(paramString1, paramString2.toCharArray())) {
/* 114 */         LOGGER.error("Failed to authenticate against MongoDB server. Unknown error.");
/*     */       }
/*     */     } catch (MongoException localMongoException) {
/* 117 */       LOGGER.error("Failed to authenticate against MongoDB: " + localMongoException.getMessage(), localMongoException);
/*     */     } catch (IllegalStateException localIllegalStateException) {
/* 119 */       LOGGER.error("Factory-supplied MongoDB database connection already authenticated with differentcredentials but lost connection.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */