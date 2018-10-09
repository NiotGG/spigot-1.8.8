/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotNullValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator NOT_NULL = new NotNullValidator();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 18 */     return NOT_NULL;
/*    */   }
/*    */   
/*    */   public static class NotNullValidator extends NoAttributesValidator
/*    */   {
/*    */     public String getKey() {
/* 24 */       return "notnull";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 28 */       return value != null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\NotNullValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */