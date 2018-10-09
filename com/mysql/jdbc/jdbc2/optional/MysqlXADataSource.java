/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import com.mysql.jdbc.ConnectionImpl;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.XAConnection;
/*    */ import javax.sql.XADataSource;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MysqlXADataSource
/*    */   extends MysqlDataSource
/*    */   implements XADataSource
/*    */ {
/*    */   public XAConnection getXAConnection()
/*    */     throws SQLException
/*    */   {
/* 48 */     java.sql.Connection conn = getConnection();
/*    */     
/* 50 */     return wrapConnection(conn);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public XAConnection getXAConnection(String u, String p)
/*    */     throws SQLException
/*    */   {
/* 59 */     java.sql.Connection conn = getConnection(u, p);
/*    */     
/* 61 */     return wrapConnection(conn);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private XAConnection wrapConnection(java.sql.Connection conn)
/*    */     throws SQLException
/*    */   {
/* 69 */     if ((getPinGlobalTxToPhysicalConnection()) || (((com.mysql.jdbc.Connection)conn).getPinGlobalTxToPhysicalConnection()))
/*    */     {
/* 71 */       return SuspendableXAConnection.getInstance((ConnectionImpl)conn);
/*    */     }
/*    */     
/* 74 */     return MysqlXAConnection.getInstance((ConnectionImpl)conn, getLogXaCommands());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlXADataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */