/*    */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public final class CouchDBObject
/*    */   implements NoSQLObject<Map<String, Object>>
/*    */ {
/*    */   private final Map<String, Object> map;
/*    */   
/*    */   public CouchDBObject()
/*    */   {
/* 33 */     this.map = new HashMap();
/*    */   }
/*    */   
/*    */   public void set(String paramString, Object paramObject)
/*    */   {
/* 38 */     this.map.put(paramString, paramObject);
/*    */   }
/*    */   
/*    */   public void set(String paramString, NoSQLObject<Map<String, Object>> paramNoSQLObject)
/*    */   {
/* 43 */     this.map.put(paramString, paramNoSQLObject.unwrap());
/*    */   }
/*    */   
/*    */   public void set(String paramString, Object[] paramArrayOfObject)
/*    */   {
/* 48 */     this.map.put(paramString, Arrays.asList(paramArrayOfObject));
/*    */   }
/*    */   
/*    */   public void set(String paramString, NoSQLObject<Map<String, Object>>[] paramArrayOfNoSQLObject)
/*    */   {
/* 53 */     ArrayList localArrayList = new ArrayList();
/* 54 */     for (NoSQLObject<Map<String, Object>> localNoSQLObject : paramArrayOfNoSQLObject) {
/* 55 */       localArrayList.add(localNoSQLObject.unwrap());
/*    */     }
/* 57 */     this.map.put(paramString, localArrayList);
/*    */   }
/*    */   
/*    */   public Map<String, Object> unwrap()
/*    */   {
/* 62 */     return this.map;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */