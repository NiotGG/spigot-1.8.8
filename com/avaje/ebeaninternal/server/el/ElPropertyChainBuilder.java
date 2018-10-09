/*    */ package com.avaje.ebeaninternal.server.el;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElPropertyChainBuilder
/*    */ {
/*    */   private final String expression;
/* 39 */   private final List<ElPropertyValue> chain = new ArrayList();
/*    */   
/*    */ 
/*    */   private final boolean embedded;
/*    */   
/*    */   private boolean containsMany;
/*    */   
/*    */ 
/*    */   public ElPropertyChainBuilder(boolean embedded, String expression)
/*    */   {
/* 49 */     this.embedded = embedded;
/* 50 */     this.expression = expression;
/*    */   }
/*    */   
/*    */   public boolean isContainsMany() {
/* 54 */     return this.containsMany;
/*    */   }
/*    */   
/*    */   public void setContainsMany(boolean containsMany) {
/* 58 */     this.containsMany = containsMany;
/*    */   }
/*    */   
/*    */   public String getExpression() {
/* 62 */     return this.expression;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ElPropertyChainBuilder add(ElPropertyValue element)
/*    */   {
/* 69 */     if (element == null) {
/* 70 */       throw new NullPointerException("element null in expression " + this.expression);
/*    */     }
/* 72 */     this.chain.add(element);
/* 73 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ElPropertyChain build()
/*    */   {
/* 80 */     return new ElPropertyChain(this.containsMany, this.embedded, this.expression, (ElPropertyValue[])this.chain.toArray(new ElPropertyValue[this.chain.size()]));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElPropertyChainBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */