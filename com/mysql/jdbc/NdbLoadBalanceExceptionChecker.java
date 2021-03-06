/*    */ package com.mysql.jdbc;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class NdbLoadBalanceExceptionChecker extends StandardLoadBalanceExceptionChecker
/*    */ {
/*    */   public boolean shouldExceptionTriggerFailover(SQLException ex)
/*    */   {
/*  9 */     return (super.shouldExceptionTriggerFailover(ex)) || (checkNdbException(ex));
/*    */   }
/*    */   
/*    */   private boolean checkNdbException(SQLException ex)
/*    */   {
/* 14 */     return (ex.getMessage().startsWith("Lock wait timeout exceeded")) || ((ex.getMessage().startsWith("Got temporary error")) && (ex.getMessage().endsWith("from NDB")));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\NdbLoadBalanceExceptionChecker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */