/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.util.UUID;
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
/*    */ public class ScalarTypeUUID
/*    */   extends ScalarTypeBaseVarchar<UUID>
/*    */ {
/*    */   public ScalarTypeUUID()
/*    */   {
/* 32 */     super(UUID.class);
/*    */   }
/*    */   
/*    */   public int getLength()
/*    */   {
/* 37 */     return 40;
/*    */   }
/*    */   
/*    */   public UUID convertFromDbString(String dbValue)
/*    */   {
/* 42 */     return UUID.fromString(dbValue);
/*    */   }
/*    */   
/*    */   public String convertToDbString(UUID beanValue)
/*    */   {
/* 47 */     return formatValue(beanValue);
/*    */   }
/*    */   
/*    */   public UUID toBeanType(Object value) {
/* 51 */     return BasicTypeConverter.toUUID(value);
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 55 */     return BasicTypeConverter.convert(value, this.jdbcType);
/*    */   }
/*    */   
/*    */   public String formatValue(UUID v) {
/* 59 */     return v.toString();
/*    */   }
/*    */   
/*    */   public UUID parse(String value) {
/* 63 */     return UUID.fromString(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeUUID.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */