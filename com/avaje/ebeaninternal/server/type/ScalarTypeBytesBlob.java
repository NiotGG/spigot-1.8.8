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
/*    */ public class ScalarTypeBytesBlob
/*    */   extends ScalarTypeBytesBase
/*    */ {
/*    */   public ScalarTypeBytesBlob()
/*    */   {
/* 31 */     super(true, 2004);
/*    */   }
/*    */   
/*    */   public byte[] read(DataReader dataReader) throws SQLException
/*    */   {
/* 36 */     return dataReader.getBlobBytes();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeBytesBlob.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */