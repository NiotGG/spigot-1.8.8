/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.config.ScalarTypeConverter;
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
/*    */ public class ScalarTypeScalaDouble
/*    */   extends ScalarTypeWrapper<Object, Double>
/*    */ {
/*    */   public ScalarTypeScalaDouble()
/*    */   {
/* 29 */     super(Object.class, new ScalarTypeDouble(), new Converter());
/*    */   }
/*    */   
/*    */   static class Converter implements ScalarTypeConverter<Object, Double>
/*    */   {
/*    */     public scala.Double getNullValue() {
/* 35 */       return null;
/*    */     }
/*    */     
/*    */     public Object wrapValue(Double scalarType) {
/* 39 */       return scalarType;
/*    */     }
/*    */     
/*    */     public Double unwrapValue(Object beanType) {
/* 43 */       return Double.valueOf(((scala.Double)beanType).toDouble());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeScalaDouble.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */