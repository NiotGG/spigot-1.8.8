/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.sql.SQLException;
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
/*    */ public class ScalarTypeBytesVarbinary
/*    */   extends ScalarTypeBytesBase
/*    */ {
/*    */   public ScalarTypeBytesVarbinary()
/*    */   {
/* 31 */     super(true, -3);
/*    */   }
/*    */   
/*    */   public byte[] read(DataReader dataReader) throws SQLException {
/* 35 */     return dataReader.getBytes();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesVarbinary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */