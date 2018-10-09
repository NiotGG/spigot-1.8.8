/*     */ package org.apache.commons.lang3;
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
/*     */ 
/*     */ public class ClassPathUtils
/*     */ {
/*     */   public static String toFullyQualifiedName(Class<?> paramClass, String paramString)
/*     */   {
/*  59 */     Validate.notNull(paramClass, "Parameter '%s' must not be null!", new Object[] { "context" });
/*  60 */     Validate.notNull(paramString, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/*  61 */     return toFullyQualifiedName(paramClass.getPackage(), paramString);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toFullyQualifiedName(Package paramPackage, String paramString)
/*     */   {
/*  81 */     Validate.notNull(paramPackage, "Parameter '%s' must not be null!", new Object[] { "context" });
/*  82 */     Validate.notNull(paramString, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/*  83 */     StringBuilder localStringBuilder = new StringBuilder();
/*  84 */     localStringBuilder.append(paramPackage.getName());
/*  85 */     localStringBuilder.append(".");
/*  86 */     localStringBuilder.append(paramString);
/*  87 */     return localStringBuilder.toString();
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toFullyQualifiedPath(Class<?> paramClass, String paramString)
/*     */   {
/* 107 */     Validate.notNull(paramClass, "Parameter '%s' must not be null!", new Object[] { "context" });
/* 108 */     Validate.notNull(paramString, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/* 109 */     return toFullyQualifiedPath(paramClass.getPackage(), paramString);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toFullyQualifiedPath(Package paramPackage, String paramString)
/*     */   {
/* 130 */     Validate.notNull(paramPackage, "Parameter '%s' must not be null!", new Object[] { "context" });
/* 131 */     Validate.notNull(paramString, "Parameter '%s' must not be null!", new Object[] { "resourceName" });
/* 132 */     StringBuilder localStringBuilder = new StringBuilder();
/* 133 */     localStringBuilder.append(paramPackage.getName().replace('.', '/'));
/* 134 */     localStringBuilder.append("/");
/* 135 */     localStringBuilder.append(paramString);
/* 136 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\ClassPathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */