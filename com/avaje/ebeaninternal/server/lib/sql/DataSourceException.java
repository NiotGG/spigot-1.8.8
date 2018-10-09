/*    */ package com.avaje.ebeaninternal.server.lib.sql;
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
/*    */ public class DataSourceException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539844L;
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
/*    */   public DataSourceException(Exception cause)
/*    */   {
/* 30 */     super(cause);
/*    */   }
/*    */   
/*    */   public DataSourceException(String s, Exception cause) {
/* 34 */     super(s, cause);
/*    */   }
/*    */   
/*    */   public DataSourceException(String s) {
/* 38 */     super(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */