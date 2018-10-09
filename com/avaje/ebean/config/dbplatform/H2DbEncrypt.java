/*    */ package com.avaje.ebean.config.dbplatform;
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
/*    */ public class H2DbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   public H2DbEncrypt()
/*    */   {
/* 31 */     this.varcharEncryptFunction = new H2VarcharFunction(null);
/* 32 */     this.dateEncryptFunction = new H2DateFunction(null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isBindEncryptDataFirst()
/*    */   {
/* 39 */     return false;
/*    */   }
/*    */   
/*    */   private static class H2VarcharFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias)
/*    */     {
/* 46 */       return "TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + ")))";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 50 */       return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(?))";
/*    */     }
/*    */   }
/*    */   
/*    */   private static class H2DateFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias)
/*    */     {
/* 58 */       return "PARSEDATETIME(TRIM(CHAR(0) FROM UTF8TOSTRING(DECRYPT('AES', STRINGTOUTF8(?), " + columnWithTableAlias + "))),'yyyyMMdd')";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 62 */       return "ENCRYPT('AES', STRINGTOUTF8(?), STRINGTOUTF8(FORMATDATETIME(?,'yyyyMMdd')))";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\H2DbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */