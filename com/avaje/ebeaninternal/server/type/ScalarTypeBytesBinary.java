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
/*    */ public class ScalarTypeBytesBinary
/*    */   extends ScalarTypeBytesBase
/*    */ {
/*    */   public ScalarTypeBytesBinary()
/*    */   {
/* 31 */     super(true, -2);
/*    */   }
/*    */   
/*    */   public byte[] read(DataReader dataReader) throws SQLException {
/* 35 */     return dataReader.getBytes();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBinary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */