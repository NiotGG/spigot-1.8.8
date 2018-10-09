/*    */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*    */ 
/*    */ import com.mongodb.BasicDBList;
/*    */ import com.mongodb.BasicDBObject;
/*    */ import java.util.Collections;
/*    */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;
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
/*    */ public final class MongoDBObject
/*    */   implements NoSQLObject<BasicDBObject>
/*    */ {
/*    */   private final BasicDBObject mongoObject;
/*    */   
/*    */   public MongoDBObject()
/*    */   {
/* 33 */     this.mongoObject = new BasicDBObject();
/*    */   }
/*    */   
/*    */   public void set(String paramString, Object paramObject)
/*    */   {
/* 38 */     this.mongoObject.append(paramString, paramObject);
/*    */   }
/*    */   
/*    */   public void set(String paramString, NoSQLObject<BasicDBObject> paramNoSQLObject)
/*    */   {
/* 43 */     this.mongoObject.append(paramString, paramNoSQLObject.unwrap());
/*    */   }
/*    */   
/*    */   public void set(String paramString, Object[] paramArrayOfObject)
/*    */   {
/* 48 */     BasicDBList localBasicDBList = new BasicDBList();
/* 49 */     Collections.addAll(localBasicDBList, paramArrayOfObject);
/* 50 */     this.mongoObject.append(paramString, localBasicDBList);
/*    */   }
/*    */   
/*    */   public void set(String paramString, NoSQLObject<BasicDBObject>[] paramArrayOfNoSQLObject)
/*    */   {
/* 55 */     BasicDBList localBasicDBList = new BasicDBList();
/* 56 */     for (NoSQLObject<BasicDBObject> localNoSQLObject : paramArrayOfNoSQLObject) {
/* 57 */       localBasicDBList.add(localNoSQLObject.unwrap());
/*    */     }
/* 59 */     this.mongoObject.append(paramString, localBasicDBList);
/*    */   }
/*    */   
/*    */   public BasicDBObject unwrap()
/*    */   {
/* 64 */     return this.mongoObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */