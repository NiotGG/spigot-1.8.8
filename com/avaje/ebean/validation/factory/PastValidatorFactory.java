/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class PastValidatorFactory implements ValidatorFactory
/*    */ {
/*    */   Validator DATE;
/*    */   Validator CALENDAR;
/*    */   
/*    */   public PastValidatorFactory()
/*    */   {
/* 12 */     this.DATE = new DateValidator(null);
/* 13 */     this.CALENDAR = new CalendarValidator(null);
/*    */   }
/*    */   
/* 16 */   public Validator create(java.lang.annotation.Annotation annotation, Class<?> type) { if (Date.class.isAssignableFrom(type)) {
/* 17 */       return this.DATE;
/*    */     }
/* 19 */     if (java.util.Calendar.class.isAssignableFrom(type)) {
/* 20 */       return this.CALENDAR;
/*    */     }
/* 22 */     String msg = "Can not use @Past on type " + type;
/* 23 */     throw new RuntimeException(msg);
/*    */   }
/*    */   
/*    */   private static class DateValidator extends NoAttributesValidator
/*    */   {
/*    */     public String getKey() {
/* 29 */       return "past";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 33 */       if (value == null) {
/* 34 */         return true;
/*    */       }
/*    */       
/* 37 */       Date date = (Date)value;
/* 38 */       return date.before(new Date());
/*    */     }
/*    */   }
/*    */   
/*    */   private static class CalendarValidator extends NoAttributesValidator
/*    */   {
/*    */     public String getKey() {
/* 45 */       return "past";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 49 */       if (value == null) {
/* 50 */         return true;
/*    */       }
/*    */       
/* 53 */       java.util.Calendar cal = (java.util.Calendar)value;
/* 54 */       return cal.before(java.util.Calendar.getInstance());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\PastValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */