/*    */ package com.avaje.ebeaninternal.server.jdbc;
/*    */ 
/*    */ import com.avaje.ebean.config.PstmtDelegate;
/*    */ import com.avaje.ebeaninternal.server.lib.sql.ExtendedPreparedStatement;
/*    */ import java.sql.PreparedStatement;
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
/*    */ public class StandardPstmtDelegate
/*    */   implements PstmtDelegate
/*    */ {
/*    */   public PreparedStatement unwrap(PreparedStatement pstmt)
/*    */   {
/* 40 */     return ((ExtendedPreparedStatement)pstmt).getDelegate();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\jdbc\StandardPstmtDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */