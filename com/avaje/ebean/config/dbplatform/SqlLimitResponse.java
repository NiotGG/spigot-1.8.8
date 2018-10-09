/*    */ package com.avaje.ebean.config.dbplatform;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SqlLimitResponse
/*    */ {
/*    */   final String sql;
/*    */   
/*    */ 
/*    */   final boolean includesRowNumberColumn;
/*    */   
/*    */ 
/*    */ 
/*    */   public SqlLimitResponse(String sql, boolean includesRowNumberColumn)
/*    */   {
/* 16 */     this.sql = sql;
/* 17 */     this.includesRowNumberColumn = includesRowNumberColumn;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getSql()
/*    */   {
/* 24 */     return this.sql;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isIncludesRowNumberColumn()
/*    */   {
/* 31 */     return this.includesRowNumberColumn;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlLimitResponse.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */