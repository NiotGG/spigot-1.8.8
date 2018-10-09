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
/*    */ public class EmailValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 15 */   public static final Validator EMAIL = new EmailValidator();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type) {
/* 18 */     if (!type.equals(String.class)) {
/* 19 */       throw new RuntimeException("Can only apply this annotation to String types, not " + type);
/*    */     }
/*    */     
/* 22 */     return EMAIL;
/*    */   }
/*    */   
/*    */   public static class EmailValidator extends NoAttributesValidator
/*    */   {
/*    */     private final EmailValidation emailValidation;
/*    */     
/*    */     EmailValidator()
/*    */     {
/* 31 */       this.emailValidation = EmailValidation.create(false, false);
/*    */     }
/*    */     
/*    */     public String getKey() {
/* 35 */       return "email";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 39 */       if (value == null) {
/* 40 */         return true;
/*    */       }
/*    */       
/* 43 */       return this.emailValidation.isValid((String)value);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\EmailValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */