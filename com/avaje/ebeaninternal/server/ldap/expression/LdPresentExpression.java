/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*    */ 
/*    */ class LdPresentExpression extends LdAbstractExpression
/*    */ {
/*    */   private static final long serialVersionUID = -4221300142054382003L;
/*    */   
/*    */   public LdPresentExpression(String propertyName)
/*    */   {
/* 11 */     super(propertyName);
/*    */   }
/*    */   
/*    */   public String getPropertyName() {
/* 15 */     return this.propertyName;
/*    */   }
/*    */   
/*    */ 
/*    */   public void addBindValues(SpiExpressionRequest request) {}
/*    */   
/*    */ 
/*    */   public void addSql(SpiExpressionRequest request)
/*    */   {
/* 24 */     String parsed = request.parseDeploy(this.propertyName);
/* 25 */     request.append("(").append(parsed).append("=*").append(")");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public int queryAutoFetchHash()
/*    */   {
/* 32 */     int hc = LdPresentExpression.class.getName().hashCode();
/* 33 */     hc = hc * 31 + this.propertyName.hashCode();
/* 34 */     return hc;
/*    */   }
/*    */   
/*    */   public int queryPlanHash(com.avaje.ebean.event.BeanQueryRequest<?> request) {
/* 38 */     return queryAutoFetchHash();
/*    */   }
/*    */   
/*    */   public int queryBindHash() {
/* 42 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdPresentExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */