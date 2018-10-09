/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeUtilDate
/*     */ {
/*     */   public static class TimestampType
/*     */     extends ScalarTypeBaseDateTime<java.util.Date>
/*     */   {
/*     */     public TimestampType()
/*     */     {
/*  37 */       super(false, 93);
/*     */     }
/*     */     
/*     */     public java.util.Date read(DataReader dataReader) throws SQLException {
/*  41 */       Timestamp timestamp = dataReader.getTimestamp();
/*  42 */       if (timestamp == null) {
/*  43 */         return null;
/*     */       }
/*  45 */       return new java.util.Date(timestamp.getTime());
/*     */     }
/*     */     
/*     */     public void bind(DataBind b, java.util.Date value)
/*     */       throws SQLException
/*     */     {
/*  51 */       if (value == null) {
/*  52 */         b.setNull(93);
/*     */       }
/*     */       else {
/*  55 */         Timestamp timestamp = new Timestamp(value.getTime());
/*  56 */         b.setTimestamp(timestamp);
/*     */       }
/*     */     }
/*     */     
/*     */     public Object toJdbcType(Object value) {
/*  61 */       return BasicTypeConverter.toTimestamp(value);
/*     */     }
/*     */     
/*     */     public java.util.Date toBeanType(Object value) {
/*  65 */       return BasicTypeConverter.toUtilDate(value);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public java.util.Date convertFromTimestamp(Timestamp ts)
/*     */     {
/*  72 */       return new java.util.Date(ts.getTime());
/*     */     }
/*     */     
/*     */     public Timestamp convertToTimestamp(java.util.Date t)
/*     */     {
/*  77 */       return new Timestamp(t.getTime());
/*     */     }
/*     */     
/*     */     public java.util.Date parseDateTime(long systemTimeMillis) {
/*  81 */       return new java.util.Date(systemTimeMillis);
/*     */     }
/*     */     
/*     */     public Object luceneFromIndexValue(Object value) {
/*  85 */       Long l = (Long)value;
/*  86 */       return new java.util.Date(l.longValue());
/*     */     }
/*     */     
/*     */     public Object luceneToIndexValue(Object value) {
/*  90 */       return Long.valueOf(((java.util.Date)value).getTime());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DateType extends ScalarTypeBaseDate<java.util.Date>
/*     */   {
/*     */     public DateType() {
/*  97 */       super(false, 91);
/*     */     }
/*     */     
/*     */     public java.util.Date convertFromDate(java.sql.Date ts)
/*     */     {
/* 102 */       return new java.util.Date(ts.getTime());
/*     */     }
/*     */     
/*     */     public java.sql.Date convertToDate(java.util.Date t)
/*     */     {
/* 107 */       return new java.sql.Date(t.getTime());
/*     */     }
/*     */     
/*     */     public Object toJdbcType(Object value) {
/* 111 */       return BasicTypeConverter.toDate(value);
/*     */     }
/*     */     
/*     */     public java.util.Date toBeanType(Object value) {
/* 115 */       return BasicTypeConverter.toUtilDate(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeUtilDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */