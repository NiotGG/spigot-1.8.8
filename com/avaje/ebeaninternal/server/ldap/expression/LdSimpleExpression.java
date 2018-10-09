/*     */ package com.avaje.ebeaninternal.server.ldap.expression;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiExpressionRequest;
/*     */ 
/*     */ class LdSimpleExpression extends LdAbstractExpression
/*     */ {
/*     */   private static final long serialVersionUID = 4091359751840929075L;
/*     */   private final Op type;
/*     */   private final Object value;
/*     */   
/*     */   static enum Op
/*     */   {
/*  13 */     EQ, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  18 */     NOT_EQ, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  23 */     LT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  28 */     LT_EQ, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  33 */     GT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  38 */     GT_EQ;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private Op() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public LdSimpleExpression(String propertyName, Op type, Object value)
/*     */   {
/*  50 */     super(propertyName);
/*  51 */     this.type = type;
/*  52 */     this.value = value;
/*     */   }
/*     */   
/*     */   public String getPropertyName() {
/*  56 */     return this.propertyName;
/*     */   }
/*     */   
/*     */   public void addBindValues(SpiExpressionRequest request)
/*     */   {
/*  61 */     com.avaje.ebeaninternal.server.el.ElPropertyValue prop = getElProp(request);
/*  62 */     if (prop != null) {
/*  63 */       if (prop.isAssocId()) {
/*  64 */         Object[] ids = prop.getAssocOneIdValues(this.value);
/*  65 */         if (ids != null) {
/*  66 */           for (int i = 0; i < ids.length; i++) {
/*  67 */             request.addBindValue(ids[i]);
/*     */           }
/*     */         }
/*  70 */         return;
/*     */       }
/*  72 */       com.avaje.ebeaninternal.server.type.ScalarType<?> scalarType = prop.getBeanProperty().getScalarType();
/*  73 */       Object v = scalarType.toJdbcType(this.value);
/*  74 */       request.addBindValue(v);
/*     */     } else {
/*  76 */       request.addBindValue(this.value);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSql(SpiExpressionRequest request) {
/*  81 */     com.avaje.ebeaninternal.server.el.ElPropertyValue prop = getElProp(request);
/*  82 */     if ((prop != null) && 
/*  83 */       (prop.isAssocId())) {
/*  84 */       String rawExpr = prop.getAssocOneIdExpr(this.propertyName, this.type.toString());
/*  85 */       String parsed = request.parseDeploy(rawExpr);
/*  86 */       request.append(parsed);
/*  87 */       return;
/*     */     }
/*     */     
/*  90 */     String parsed = request.parseDeploy(this.propertyName);
/*     */     
/*  92 */     request.append("(").append(parsed).append("").append(this.type.toString()).append(nextParam(request)).append(")");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int queryAutoFetchHash()
/*     */   {
/*  99 */     int hc = LdSimpleExpression.class.getName().hashCode();
/* 100 */     hc = hc * 31 + this.propertyName.hashCode();
/* 101 */     hc = hc * 31 + this.type.name().hashCode();
/* 102 */     return hc;
/*     */   }
/*     */   
/*     */   public int queryPlanHash(com.avaje.ebean.event.BeanQueryRequest<?> request) {
/* 106 */     return queryAutoFetchHash();
/*     */   }
/*     */   
/*     */   public int queryBindHash() {
/* 110 */     return this.value.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdSimpleExpression.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */