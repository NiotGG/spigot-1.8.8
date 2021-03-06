/*    */ package com.avaje.ebean.config;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PropertyMap
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private LinkedHashMap<String, String> map = new LinkedHashMap();
/*    */   
/*    */   public String toString() {
/* 20 */     return this.map.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void evaluateProperties()
/*    */   {
/* 29 */     for (Map.Entry<String, String> e : entrySet()) {
/* 30 */       String key = (String)e.getKey();
/* 31 */       String val = (String)e.getValue();
/* 32 */       String eval = eval(val);
/* 33 */       if ((eval != null) && (!eval.equals(val))) {
/* 34 */         put(key, eval);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public synchronized String eval(String val) {
/* 40 */     return PropertyExpression.eval(val, this);
/*    */   }
/*    */   
/*    */   public synchronized boolean getBoolean(String key, boolean defaultValue) {
/* 44 */     String value = get(key);
/* 45 */     if (value == null) {
/* 46 */       return defaultValue;
/*    */     }
/* 48 */     return Boolean.parseBoolean(value);
/*    */   }
/*    */   
/*    */   public synchronized int getInt(String key, int defaultValue)
/*    */   {
/* 53 */     String value = get(key);
/* 54 */     if (value == null) {
/* 55 */       return defaultValue;
/*    */     }
/* 57 */     return Integer.parseInt(value);
/*    */   }
/*    */   
/*    */   public synchronized String get(String key, String defaultValue)
/*    */   {
/* 62 */     String value = (String)this.map.get(key.toLowerCase());
/* 63 */     return value == null ? defaultValue : value;
/*    */   }
/*    */   
/*    */   public synchronized String get(String key) {
/* 67 */     return (String)this.map.get(key.toLowerCase());
/*    */   }
/*    */   
/*    */   synchronized void putAll(Map<String, String> keyValueMap) {
/* 71 */     Iterator<Map.Entry<String, String>> it = keyValueMap.entrySet().iterator();
/* 72 */     while (it.hasNext()) {
/* 73 */       Map.Entry<String, String> entry = (Map.Entry)it.next();
/* 74 */       put((String)entry.getKey(), (String)entry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   synchronized String putEval(String key, String value) {
/* 79 */     value = PropertyExpression.eval(value, this);
/* 80 */     return (String)this.map.put(key.toLowerCase(), value);
/*    */   }
/*    */   
/*    */   synchronized String put(String key, String value) {
/* 84 */     return (String)this.map.put(key.toLowerCase(), value);
/*    */   }
/*    */   
/*    */   synchronized String remove(String key) {
/* 88 */     return (String)this.map.remove(key.toLowerCase());
/*    */   }
/*    */   
/*    */   synchronized Set<Map.Entry<String, String>> entrySet() {
/* 92 */     return this.map.entrySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\PropertyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */