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
/*    */ public class MySqlDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   public MySqlDbEncrypt()
/*    */   {
/* 31 */     this.varcharEncryptFunction = new MyVarcharFunction(null);
/* 32 */     this.dateEncryptFunction = new MyDateFunction(null);
/*    */   }
/*    */   
/*    */   private static class MyVarcharFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 38 */       return "CONVERT(AES_DECRYPT(" + columnWithTableAlias + ",?) USING UTF8)";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 42 */       return "AES_ENCRYPT(?,?)";
/*    */     }
/*    */   }
/*    */   
/*    */   private static class MyDateFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 49 */       return "STR_TO_DATE(AES_DECRYPT(" + columnWithTableAlias + ",?),'%Y%d%m')";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 53 */       return "AES_ENCRYPT(DATE_FORMAT(?,'%Y%d%m'),?)";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\MySqlDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */