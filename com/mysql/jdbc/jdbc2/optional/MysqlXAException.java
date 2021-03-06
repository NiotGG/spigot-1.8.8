/*    */ package com.mysql.jdbc.jdbc2.optional;
/*    */ 
/*    */ import javax.transaction.xa.XAException;
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
/*    */ class MysqlXAException
/*    */   extends XAException
/*    */ {
/*    */   private static final long serialVersionUID = -9075817535836563004L;
/*    */   private String message;
/*    */   private String xidAsString;
/*    */   
/*    */   public MysqlXAException(int errorCode, String message, String xidAsString)
/*    */   {
/* 40 */     super(errorCode);
/* 41 */     this.message = message;
/* 42 */     this.xidAsString = xidAsString;
/*    */   }
/*    */   
/*    */ 
/*    */   public MysqlXAException(String message, String xidAsString)
/*    */   {
/* 48 */     this.message = message;
/* 49 */     this.xidAsString = xidAsString;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 53 */     String superMessage = super.getMessage();
/* 54 */     StringBuffer returnedMessage = new StringBuffer();
/*    */     
/* 56 */     if (superMessage != null) {
/* 57 */       returnedMessage.append(superMessage);
/* 58 */       returnedMessage.append(":");
/*    */     }
/*    */     
/* 61 */     if (this.message != null) {
/* 62 */       returnedMessage.append(this.message);
/*    */     }
/*    */     
/* 65 */     return returnedMessage.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\jdbc2\optional\MysqlXAException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */