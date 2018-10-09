/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.ConnectionPoolDataSource;
/*    */ import javax.sql.PooledConnection;
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
/*    */ public class MysqlConnectionPoolDataSource
/*    */   extends MysqlDataSource
/*    */   implements ConnectionPoolDataSource
/*    */ {
/*    */   public synchronized PooledConnection getPooledConnection()
/*    */     throws SQLException
/*    */   {
/* 59 */     java.sql.Connection connection = getConnection();
/* 60 */     MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((com.mysql.jdbc.Connection)connection);
/*    */     
/*    */ 
/* 63 */     return mysqlPooledConnection;
/*    */   }
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
/*    */   public synchronized PooledConnection getPooledConnection(String s, String s1)
/*    */     throws SQLException
/*    */   {
/* 80 */     java.sql.Connection connection = getConnection(s, s1);
/* 81 */     MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((com.mysql.jdbc.Connection)connection);
/*    */     
/*    */ 
/* 84 */     return mysqlPooledConnection;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlConnectionPoolDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */