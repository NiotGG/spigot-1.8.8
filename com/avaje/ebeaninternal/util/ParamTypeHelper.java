/*     */ package com.avaje.ebeaninternal.util;
/*     */ 
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ParamTypeHelper
/*     */ {
/*     */   public static enum ManyType
/*     */   {
/*  12 */     LIST,  SET,  MAP,  NONE;
/*     */     
/*     */     private ManyType() {}
/*     */   }
/*     */   
/*     */   public static class TypeInfo {
/*     */     private final ParamTypeHelper.ManyType manyType;
/*     */     private final Class<?> beanType;
/*     */     
/*  21 */     private TypeInfo(ParamTypeHelper.ManyType manyType, Class<?> beanType) { this.manyType = manyType;
/*  22 */       this.beanType = beanType;
/*     */     }
/*     */     
/*     */     public boolean isManyType() {
/*  26 */       return !ParamTypeHelper.ManyType.NONE.equals(this.manyType);
/*     */     }
/*     */     
/*     */     public ParamTypeHelper.ManyType getManyType() {
/*  30 */       return this.manyType;
/*     */     }
/*     */     
/*     */     public Class<?> getBeanType() {
/*  34 */       return this.beanType;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  38 */       if (isManyType()) {
/*  39 */         return this.manyType + " " + this.beanType;
/*     */       }
/*  41 */       return this.beanType.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static TypeInfo getTypeInfo(Type genericType)
/*     */   {
/*  49 */     if ((genericType instanceof ParameterizedType)) {
/*  50 */       return getParamTypeInfo((ParameterizedType)genericType);
/*     */     }
/*     */     
/*  53 */     Class<?> entityType = getBeanType(genericType);
/*  54 */     if (entityType != null) {
/*  55 */       return new TypeInfo(ManyType.NONE, entityType, null);
/*     */     }
/*  57 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static TypeInfo getParamTypeInfo(ParameterizedType paramType)
/*     */   {
/*  65 */     Type rawType = paramType.getRawType();
/*     */     
/*  67 */     ManyType manyType = getManyType(rawType);
/*  68 */     if (ManyType.NONE.equals(manyType)) {
/*  69 */       return null;
/*     */     }
/*     */     
/*  72 */     Type[] typeArguments = paramType.getActualTypeArguments();
/*     */     
/*  74 */     if (typeArguments.length == 1) {
/*  75 */       Type argType = typeArguments[0];
/*  76 */       Class<?> beanType = getBeanType(argType);
/*  77 */       if (beanType != null) {
/*  78 */         return new TypeInfo(manyType, beanType, null);
/*     */       }
/*     */     }
/*     */     
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   private static Class<?> getBeanType(Type argType) {
/*  86 */     if ((argType instanceof Class)) {
/*  87 */       return (Class)argType;
/*     */     }
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   private static ManyType getManyType(Type rawType) {
/*  93 */     if (java.util.List.class.equals(rawType)) {
/*  94 */       return ManyType.LIST;
/*     */     }
/*  96 */     if (Set.class.equals(rawType)) {
/*  97 */       return ManyType.SET;
/*     */     }
/*  99 */     if (Map.class.equals(rawType)) {
/* 100 */       return ManyType.MAP;
/*     */     }
/* 102 */     return ManyType.NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\util\ParamTypeHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */