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
/*    */ 
/*    */ public class PostgresDbEncrypt
/*    */   extends AbstractDbEncrypt
/*    */ {
/*    */   public PostgresDbEncrypt()
/*    */   {
/* 32 */     this.varcharEncryptFunction = new PgVarcharFunction(null);
/* 33 */     this.dateEncryptFunction = new PgDateFunction(null);
/*    */   }
/*    */   
/*    */   private static class PgVarcharFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 39 */       return "pgp_sym_decrypt(" + columnWithTableAlias + ",?)";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 43 */       return "pgp_sym_encrypt(?,?)";
/*    */     }
/*    */   }
/*    */   
/*    */   private static class PgDateFunction implements DbEncryptFunction
/*    */   {
/*    */     public String getDecryptSql(String columnWithTableAlias) {
/* 50 */       return "to_date(pgp_sym_decrypt(" + columnWithTableAlias + ",?),'YYYYMMDD')";
/*    */     }
/*    */     
/*    */     public String getEncryptBindSql() {
/* 54 */       return "pgp_sym_encrypt(to_char(?::date,'YYYYMMDD'),?)";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\PostgresDbEncrypt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */