/*    */ package com.avaje.ebean.config;
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
/*    */ public class MatchingNamingConvention
/*    */   extends AbstractNamingConvention
/*    */ {
/*    */   public MatchingNamingConvention() {}
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
/*    */   public MatchingNamingConvention(String sequenceFormat)
/*    */   {
/* 49 */     super(sequenceFormat);
/*    */   }
/*    */   
/*    */   public String getColumnFromProperty(Class<?> beanClass, String propertyName)
/*    */   {
/* 54 */     return propertyName;
/*    */   }
/*    */   
/*    */ 
/*    */   public TableName getTableNameByConvention(Class<?> beanClass)
/*    */   {
/* 60 */     return new TableName(getCatalog(), getSchema(), beanClass.getSimpleName());
/*    */   }
/*    */   
/*    */   public String getPropertyFromColumn(Class<?> beanClass, String dbColumnName)
/*    */   {
/* 65 */     return dbColumnName;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\MatchingNamingConvention.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */