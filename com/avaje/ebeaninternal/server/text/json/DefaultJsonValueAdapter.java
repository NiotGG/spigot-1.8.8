/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.json.JsonValueAdapter;
/*    */ import java.sql.Timestamp;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.TimeZone;
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
/*    */ public class DefaultJsonValueAdapter
/*    */   implements JsonValueAdapter
/*    */ {
/*    */   private final SimpleDateFormat dateTimeProto;
/*    */   
/*    */   public DefaultJsonValueAdapter(String dateTimeFormat)
/*    */   {
/* 34 */     this.dateTimeProto = new SimpleDateFormat(dateTimeFormat);
/* 35 */     this.dateTimeProto.setTimeZone(TimeZone.getTimeZone("UTC"));
/*    */   }
/*    */   
/*    */   public DefaultJsonValueAdapter() {
/* 39 */     this("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
/*    */   }
/*    */   
/*    */   private SimpleDateFormat dtFormat() {
/* 43 */     return (SimpleDateFormat)this.dateTimeProto.clone();
/*    */   }
/*    */   
/*    */   public String jsonFromDate(java.sql.Date date) {
/* 47 */     return "\"" + date.toString() + "\"";
/*    */   }
/*    */   
/*    */   public String jsonFromTimestamp(Timestamp date) {
/* 51 */     return "\"" + dtFormat().format(date) + "\"";
/*    */   }
/*    */   
/*    */   public java.sql.Date jsonToDate(String jsonDate) {
/* 55 */     return java.sql.Date.valueOf(jsonDate);
/*    */   }
/*    */   
/*    */   public Timestamp jsonToTimestamp(String jsonDateTime) {
/*    */     try {
/* 60 */       java.util.Date d = dtFormat().parse(jsonDateTime);
/* 61 */       return new Timestamp(d.getTime());
/*    */     } catch (Exception e) {
/* 63 */       String m = "Error parsing Datetime[" + jsonDateTime + "]";
/* 64 */       throw new RuntimeException(m, e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\DefaultJsonValueAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */