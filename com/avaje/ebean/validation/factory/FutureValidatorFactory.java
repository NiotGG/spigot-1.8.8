/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class FutureValidatorFactory implements ValidatorFactory
/*    */ {
/*    */   Validator DATE;
/*    */   Validator CALENDAR;
/*    */   
/*    */   public FutureValidatorFactory()
/*    */   {
/* 12 */     this.DATE = new DateValidator(null);
/*    */     
/* 14 */     this.CALENDAR = new CalendarValidator(null);
/*    */   }
/*    */   
/* 17 */   public Validator create(java.lang.annotation.Annotation annotation, Class<?> type) { if (Date.class.isAssignableFrom(type)) {
/* 18 */       return this.DATE;
/*    */     }
/* 20 */     if (java.util.Calendar.class.isAssignableFrom(type)) {
/* 21 */       return this.CALENDAR;
/*    */     }
/* 23 */     String msg = "Can not use @Future on type " + type;
/* 24 */     throw new RuntimeException(msg);
/*    */   }
/*    */   
/*    */   private static class DateValidator extends NoAttributesValidator
/*    */   {
/*    */     public String getKey() {
/* 30 */       return "future";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 34 */       if (value == null) {
/* 35 */         return true;
/*    */       }
/*    */       
/* 38 */       Date date = (Date)value;
/* 39 */       return date.after(new Date());
/*    */     }
/*    */   }
/*    */   
/*    */   private static class CalendarValidator extends NoAttributesValidator
/*    */   {
/*    */     public String getKey() {
/* 46 */       return "future";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 50 */       if (value == null) {
/* 51 */         return true;
/*    */       }
/*    */       
/* 54 */       java.util.Calendar cal = (java.util.Calendar)value;
/* 55 */       return cal.after(java.util.Calendar.getInstance());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\FutureValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */