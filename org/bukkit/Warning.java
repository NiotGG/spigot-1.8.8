/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.ImmutableMap.Builder;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Target({java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
/*    */ @Retention(RetentionPolicy.RUNTIME)
/*    */ public @interface Warning
/*    */ {
/*    */   boolean value() default false;
/*    */   
/*    */   String reason() default "";
/*    */   
/*    */   public static enum WarningState
/*    */   {
/* 26 */     ON, 
/*    */     
/*    */ 
/*    */ 
/* 30 */     OFF, 
/*    */     
/*    */ 
/*    */ 
/* 34 */     DEFAULT;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 40 */     private static final Map<String, WarningState> values = ImmutableMap.builder()
/* 41 */       .put("off", OFF)
/* 42 */       .put("false", OFF)
/* 43 */       .put("f", OFF)
/* 44 */       .put("no", OFF)
/* 45 */       .put("n", OFF)
/* 46 */       .put("on", ON)
/* 47 */       .put("true", ON)
/* 48 */       .put("t", ON)
/* 49 */       .put("yes", ON)
/* 50 */       .put("y", ON)
/* 51 */       .put("", DEFAULT)
/* 52 */       .put("d", DEFAULT)
/* 53 */       .put("default", DEFAULT)
/* 54 */       .build();
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
/*    */     public boolean printFor(Warning warning)
/*    */     {
/* 69 */       if (this == DEFAULT) {
/* 70 */         return (warning == null) || (warning.value());
/*    */       }
/* 72 */       return this == ON;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     public static WarningState value(String value)
/*    */     {
/* 84 */       if (value == null) {
/* 85 */         return DEFAULT;
/*    */       }
/* 87 */       WarningState state = (WarningState)values.get(value.toLowerCase());
/* 88 */       if (state == null) {
/* 89 */         return DEFAULT;
/*    */       }
/* 91 */       return state;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Warning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */