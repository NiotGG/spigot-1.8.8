/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import org.joda.time.DateMidnight;
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
/*    */ public class ScalarTypeJodaDateMidnight
/*    */   extends ScalarTypeBaseDate<DateMidnight>
/*    */ {
/*    */   public ScalarTypeJodaDateMidnight()
/*    */   {
/* 38 */     super(DateMidnight.class, false, 91);
/*    */   }
/*    */   
/*    */   public DateMidnight convertFromDate(java.sql.Date ts)
/*    */   {
/* 43 */     return new DateMidnight(ts.getTime());
/*    */   }
/*    */   
/*    */   public java.sql.Date convertToDate(DateMidnight t)
/*    */   {
/* 48 */     return new java.sql.Date(t.getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 52 */     if ((value instanceof DateMidnight)) {
/* 53 */       return new java.sql.Date(((DateMidnight)value).getMillis());
/*    */     }
/* 55 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public DateMidnight toBeanType(Object value) {
/* 59 */     if ((value instanceof java.util.Date)) {
/* 60 */       return new DateMidnight(((java.util.Date)value).getTime());
/*    */     }
/* 62 */     return (DateMidnight)value;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaDateMidnight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */