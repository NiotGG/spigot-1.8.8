/*    */ package com.avaje.ebeaninternal.server.deploy.generatedproperty;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class CounterFactory
/*    */ {
/* 38 */   final GeneratedCounterInteger integerCounter = new GeneratedCounterInteger();
/*    */   
/* 40 */   final GeneratedCounterLong longCounter = new GeneratedCounterLong();
/*    */   
/*    */   public void setCounter(DeployBeanProperty property)
/*    */   {
/* 44 */     property.setGeneratedProperty(createCounter(property));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private GeneratedProperty createCounter(DeployBeanProperty property)
/*    */   {
/* 52 */     Class<?> propType = property.getPropertyType();
/* 53 */     if ((propType.equals(Integer.class)) || (propType.equals(Integer.TYPE))) {
/* 54 */       return this.integerCounter;
/*    */     }
/* 56 */     if ((propType.equals(Long.class)) || (propType.equals(Long.TYPE))) {
/* 57 */       return this.longCounter;
/*    */     }
/*    */     
/* 60 */     int type = getType(propType);
/* 61 */     return new GeneratedCounter(type);
/*    */   }
/*    */   
/*    */   private int getType(Class<?> propType) {
/* 65 */     if ((propType.equals(Short.class)) || (propType.equals(Short.TYPE))) {
/* 66 */       return -6;
/*    */     }
/* 68 */     if (propType.equals(BigDecimal.class)) {
/* 69 */       return 3;
/*    */     }
/* 71 */     if ((propType.equals(Double.class)) || (propType.equals(Double.TYPE))) {
/* 72 */       return 8;
/*    */     }
/* 74 */     if ((propType.equals(Float.class)) || (propType.equals(Float.TYPE))) {
/* 75 */       return 7;
/*    */     }
/* 77 */     if (propType.equals(BigInteger.class)) {
/* 78 */       return -5;
/*    */     }
/* 80 */     String msg = "Can not support Counter for type " + propType.getName();
/* 81 */     throw new PersistenceException(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\CounterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */