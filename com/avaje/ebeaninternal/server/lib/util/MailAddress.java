/*    */ package com.avaje.ebeaninternal.server.lib.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailAddress
/*    */ {
/*    */   String alias;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   String emailAddress;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MailAddress(String alias, String emailAddress)
/*    */   {
/* 34 */     this.alias = alias;
/* 35 */     this.emailAddress = emailAddress;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getAlias()
/*    */   {
/* 43 */     if (this.alias == null) {
/* 44 */       return "";
/*    */     }
/* 46 */     return this.alias;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getEmailAddress()
/*    */   {
/* 53 */     return this.emailAddress;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuffer sb = new StringBuffer();
/* 58 */     sb.append(getAlias()).append(" ").append("<").append(getEmailAddress()).append(">");
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\MailAddress.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */