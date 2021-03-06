/*     */ package com.avaje.ebean.validation.factory;
/*     */ 
/*     */ import com.avaje.ebean.validation.Range;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RangeValidatorFactory
/*     */   implements ValidatorFactory
/*     */ {
/*  24 */   private static final Map<String, Validator> cache = new HashMap();
/*     */   
/*     */   public Validator create(Annotation annotation, Class<?> type) {
/*  27 */     Range range = (Range)annotation;
/*  28 */     return create(range.min(), range.max(), type);
/*     */   }
/*     */   
/*     */   public synchronized Validator create(long min, long max, Class<?> type)
/*     */   {
/*  33 */     String key = type + ":" + min + ":" + max;
/*  34 */     Validator validator = (Validator)cache.get(key);
/*  35 */     if (validator != null) {
/*  36 */       return validator;
/*     */     }
/*  38 */     if (type.equals(String.class)) {
/*  39 */       validator = new StringValidator(min, max, null);
/*     */     }
/*  41 */     else if (useDouble(type)) {
/*  42 */       validator = new DoubleValidator(min, max, null);
/*     */     }
/*  44 */     else if (useLong(type)) {
/*  45 */       validator = new LongValidator(min, max, null);
/*     */     }
/*     */     else {
/*  48 */       String msg = "@Range annotation not assignable to type " + type;
/*  49 */       throw new RuntimeException(msg);
/*     */     }
/*     */     
/*  52 */     cache.put(key, validator);
/*  53 */     return validator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean useLong(Class<?> type)
/*     */   {
/*  60 */     if ((type.equals(Integer.TYPE)) || (type.equals(Long.TYPE)) || (type.equals(Short.TYPE))) {
/*  61 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  65 */     if (Number.class.isAssignableFrom(type)) {
/*  66 */       return true;
/*     */     }
/*  68 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static boolean useDouble(Class<?> type)
/*     */   {
/*  75 */     if ((type.equals(Float.TYPE)) || (type.equals(Double.TYPE))) {
/*  76 */       return true;
/*     */     }
/*  78 */     if (type.equals(BigDecimal.class)) {
/*  79 */       return true;
/*     */     }
/*  81 */     if (Double.class.isAssignableFrom(type)) {
/*  82 */       return true;
/*     */     }
/*  84 */     if (Float.class.isAssignableFrom(type)) {
/*  85 */       return true;
/*     */     }
/*  87 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private static class DoubleValidator
/*     */     implements Validator
/*     */   {
/*     */     final long min;
/*     */     final long max;
/*     */     final String key;
/*     */     final Object[] attributes;
/*     */     
/*     */     private DoubleValidator(long min, long max)
/*     */     {
/* 101 */       this.min = min;
/* 102 */       this.max = max;
/* 103 */       this.key = determineKey(min, max);
/* 104 */       this.attributes = determineAttributes(min, max);
/*     */     }
/*     */     
/*     */     private String determineKey(long min, long max) {
/* 108 */       if ((min > Long.MIN_VALUE) && (max < Long.MAX_VALUE))
/* 109 */         return "range.minmax";
/* 110 */       if (min > Long.MIN_VALUE) {
/* 111 */         return "range.min";
/*     */       }
/* 113 */       return "range.max";
/*     */     }
/*     */     
/*     */     private Object[] determineAttributes(long min, long max)
/*     */     {
/* 118 */       if ((min > Long.MIN_VALUE) && (max < Long.MAX_VALUE))
/* 119 */         return new Object[] { Long.valueOf(min), Long.valueOf(max) };
/* 120 */       if (min > Long.MIN_VALUE) {
/* 121 */         return new Object[] { Long.valueOf(min) };
/*     */       }
/* 123 */       return new Object[] { Long.valueOf(max) };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public Object[] getAttributes()
/*     */     {
/* 131 */       return this.attributes;
/*     */     }
/*     */     
/*     */     public String getKey() {
/* 135 */       return this.key;
/*     */     }
/*     */     
/*     */     public boolean isValid(Object value) {
/* 139 */       if (value == null) {
/* 140 */         return true;
/*     */       }
/* 142 */       Number n = (Number)value;
/* 143 */       double dv = n.doubleValue();
/* 144 */       return (dv >= this.min) && (dv <= this.max);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 148 */       return getClass().getName() + "key:" + this.key + " min:" + this.min + " max:" + this.max;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class LongValidator
/*     */     extends RangeValidatorFactory.DoubleValidator
/*     */   {
/*     */     private LongValidator(long min, long max)
/*     */     {
/* 158 */       super(max, null);
/*     */     }
/*     */     
/*     */     public boolean isValid(Object value)
/*     */     {
/* 163 */       if (value == null) {
/* 164 */         return true;
/*     */       }
/* 166 */       Number n = (Number)value;
/* 167 */       long lv = n.longValue();
/* 168 */       return (lv >= this.min) && (lv <= this.max);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class StringValidator
/*     */     extends RangeValidatorFactory.DoubleValidator
/*     */   {
/*     */     private StringValidator(long min, long max)
/*     */     {
/* 181 */       super(max, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean isValid(Object value)
/*     */     {
/* 188 */       if (value == null) {
/* 189 */         return true;
/*     */       }
/*     */       
/* 192 */       BigDecimal bd = new BigDecimal((String)value);
/* 193 */       double dv = bd.doubleValue();
/* 194 */       return (dv >= this.min) && (dv <= this.max);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\validation\factory\RangeValidatorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */