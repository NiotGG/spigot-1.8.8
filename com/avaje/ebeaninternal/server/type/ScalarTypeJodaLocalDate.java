/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import org.joda.time.DateMidnight;
/*    */ import org.joda.time.LocalDate;
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
/*    */ public class ScalarTypeJodaLocalDate
/*    */   extends ScalarTypeBaseDate<LocalDate>
/*    */ {
/*    */   public ScalarTypeJodaLocalDate()
/*    */   {
/* 35 */     super(LocalDate.class, false, 91);
/*    */   }
/*    */   
/*    */   public LocalDate convertFromDate(java.sql.Date ts)
/*    */   {
/* 40 */     return new LocalDate(ts.getTime());
/*    */   }
/*    */   
/*    */   public java.sql.Date convertToDate(LocalDate t)
/*    */   {
/* 45 */     return new java.sql.Date(t.toDateMidnight().getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if ((value instanceof LocalDate)) {
/* 50 */       return new java.sql.Date(((LocalDate)value).toDateMidnight().getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public LocalDate toBeanType(Object value) {
/* 56 */     if ((value instanceof java.util.Date)) {
/* 57 */       return new LocalDate(((java.util.Date)value).getTime());
/*    */     }
/* 59 */     return (LocalDate)value;
/*    */   }
/*    */   
/*    */   public LocalDate parseDateTime(long systemTimeMillis) {
/* 63 */     return new LocalDate(systemTimeMillis);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */