/*    */ package com.avaje.ebean.validation.factory;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
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
/*    */ public final class PatternValidatorFactory
/*    */   implements ValidatorFactory
/*    */ {
/* 24 */   private static final Map<String, Validator> cache = new HashMap();
/*    */   
/*    */   public Validator create(Annotation annotation, Class<?> type)
/*    */   {
/* 28 */     if (!type.equals(String.class)) {
/* 29 */       String msg = "You can only specify @Pattern on String types";
/* 30 */       throw new RuntimeException(msg);
/*    */     }
/* 32 */     com.avaje.ebean.validation.Pattern pattern = (com.avaje.ebean.validation.Pattern)annotation;
/* 33 */     return create(pattern.regex(), pattern.flags());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static synchronized Validator create(String regex, int flags)
/*    */   {
/* 41 */     regex = regex.trim();
/* 42 */     if (regex.length() == 0) {
/* 43 */       throw new RuntimeException("Missing regex attribute on @Pattern");
/*    */     }
/* 45 */     String key = regex;
/* 46 */     Validator validator = (Validator)cache.get(key);
/* 47 */     if (validator == null) {
/* 48 */       validator = new PatternValidator(regex, flags, null);
/* 49 */       cache.put(key, validator);
/*    */     }
/* 51 */     return validator;
/*    */   }
/*    */   
/*    */ 
/*    */   private static final class PatternValidator
/*    */     implements Validator
/*    */   {
/*    */     private final java.util.regex.Pattern pattern;
/*    */     
/*    */     private final Object[] attributes;
/*    */     
/*    */     private PatternValidator(String regex, int flags)
/*    */     {
/* 64 */       this.pattern = java.util.regex.Pattern.compile(regex, flags);
/* 65 */       this.attributes = new Object[] { regex, Integer.valueOf(flags) };
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     public Object[] getAttributes()
/*    */     {
/* 72 */       return this.attributes;
/*    */     }
/*    */     
/*    */     public String getKey() {
/* 76 */       return "pattern";
/*    */     }
/*    */     
/*    */     public boolean isValid(Object value) {
/* 80 */       if (value == null) {
/* 81 */         return true;
/*    */       }
/* 83 */       String string = (String)value;
/* 84 */       Matcher m = this.pattern.matcher(string);
/* 85 */       return m.matches();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\PatternValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */