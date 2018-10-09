/*    */ package com.avaje.ebean.text.json;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class JsonReadOptions
/*    */ {
/*    */   protected JsonValueAdapter valueAdapter;
/*    */   protected Map<String, JsonReadBeanVisitor<?>> visitorMap;
/*    */   
/*    */   public JsonReadOptions()
/*    */   {
/* 50 */     this.visitorMap = new LinkedHashMap();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonValueAdapter getValueAdapter()
/*    */   {
/* 57 */     return this.valueAdapter;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Map<String, JsonReadBeanVisitor<?>> getVisitorMap()
/*    */   {
/* 64 */     return this.visitorMap;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonReadOptions setValueAdapter(JsonValueAdapter valueAdapter)
/*    */   {
/* 71 */     this.valueAdapter = valueAdapter;
/* 72 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonReadOptions addRootVisitor(JsonReadBeanVisitor<?> visitor)
/*    */   {
/* 79 */     return addVisitor(null, visitor);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public JsonReadOptions addVisitor(String path, JsonReadBeanVisitor<?> visitor)
/*    */   {
/* 86 */     this.visitorMap.put(path, visitor);
/* 87 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonReadOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */