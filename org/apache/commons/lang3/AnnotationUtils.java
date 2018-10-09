/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*     */ import org.apache.commons.lang3.builder.ToStringStyle;
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
/*     */ public class AnnotationUtils
/*     */ {
/*  51 */   private static final ToStringStyle TO_STRING_STYLE = new ToStringStyle()
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
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
/*     */     protected String getShortClassName(Class<?> paramAnonymousClass)
/*     */     {
/*  73 */       Object localObject = null;
/*  74 */       for (Class localClass1 : ClassUtils.getAllInterfaces(paramAnonymousClass)) {
/*  75 */         if (Annotation.class.isAssignableFrom(localClass1))
/*     */         {
/*     */ 
/*  78 */           Class localClass2 = localClass1;
/*  79 */           localObject = localClass2;
/*  80 */           break;
/*     */         }
/*     */       }
/*  83 */       return new StringBuilder(localObject == null ? "" : ((Class)localObject).getName()).insert(0, '@').toString();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void appendDetail(StringBuffer paramAnonymousStringBuffer, String paramAnonymousString, Object paramAnonymousObject)
/*     */     {
/*  92 */       if ((paramAnonymousObject instanceof Annotation)) {
/*  93 */         paramAnonymousObject = AnnotationUtils.toString((Annotation)paramAnonymousObject);
/*     */       }
/*  95 */       super.appendDetail(paramAnonymousStringBuffer, paramAnonymousString, paramAnonymousObject);
/*     */     }
/*     */   };
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
/*     */   public static boolean equals(Annotation paramAnnotation1, Annotation paramAnnotation2)
/*     */   {
/* 123 */     if (paramAnnotation1 == paramAnnotation2) {
/* 124 */       return true;
/*     */     }
/* 126 */     if ((paramAnnotation1 == null) || (paramAnnotation2 == null)) {
/* 127 */       return false;
/*     */     }
/* 129 */     Class localClass1 = paramAnnotation1.annotationType();
/* 130 */     Class localClass2 = paramAnnotation2.annotationType();
/* 131 */     Validate.notNull(localClass1, "Annotation %s with null annotationType()", new Object[] { paramAnnotation1 });
/* 132 */     Validate.notNull(localClass2, "Annotation %s with null annotationType()", new Object[] { paramAnnotation2 });
/* 133 */     if (!localClass1.equals(localClass2)) {
/* 134 */       return false;
/*     */     }
/*     */     try {
/* 137 */       for (Method localMethod : localClass1.getDeclaredMethods()) {
/* 138 */         if ((localMethod.getParameterTypes().length == 0) && (isValidAnnotationMemberType(localMethod.getReturnType())))
/*     */         {
/* 140 */           Object localObject1 = localMethod.invoke(paramAnnotation1, new Object[0]);
/* 141 */           Object localObject2 = localMethod.invoke(paramAnnotation2, new Object[0]);
/* 142 */           if (!memberEquals(localMethod.getReturnType(), localObject1, localObject2)) {
/* 143 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 148 */       return false;
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 150 */       return false;
/*     */     }
/* 152 */     return true;
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
/*     */   public static int hashCode(Annotation paramAnnotation)
/*     */   {
/* 168 */     int i = 0;
/* 169 */     Class localClass = paramAnnotation.annotationType();
/* 170 */     for (Method localMethod : localClass.getDeclaredMethods()) {
/*     */       try {
/* 172 */         Object localObject = localMethod.invoke(paramAnnotation, new Object[0]);
/* 173 */         if (localObject == null) {
/* 174 */           throw new IllegalStateException(String.format("Annotation method %s returned null", new Object[] { localMethod }));
/*     */         }
/*     */         
/* 177 */         i += hashMember(localMethod.getName(), localObject);
/*     */       } catch (RuntimeException localRuntimeException) {
/* 179 */         throw localRuntimeException;
/*     */       } catch (Exception localException) {
/* 181 */         throw new RuntimeException(localException);
/*     */       }
/*     */     }
/* 184 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toString(Annotation paramAnnotation)
/*     */   {
/* 196 */     ToStringBuilder localToStringBuilder = new ToStringBuilder(paramAnnotation, TO_STRING_STYLE);
/* 197 */     for (Method localMethod : paramAnnotation.annotationType().getDeclaredMethods()) {
/* 198 */       if (localMethod.getParameterTypes().length <= 0)
/*     */       {
/*     */         try
/*     */         {
/* 202 */           localToStringBuilder.append(localMethod.getName(), localMethod.invoke(paramAnnotation, new Object[0]));
/*     */         } catch (RuntimeException localRuntimeException) {
/* 204 */           throw localRuntimeException;
/*     */         } catch (Exception localException) {
/* 206 */           throw new RuntimeException(localException);
/*     */         } }
/*     */     }
/* 209 */     return localToStringBuilder.build();
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
/*     */   public static boolean isValidAnnotationMemberType(Class<?> paramClass)
/*     */   {
/* 224 */     if (paramClass == null) {
/* 225 */       return false;
/*     */     }
/* 227 */     if (paramClass.isArray()) {
/* 228 */       paramClass = paramClass.getComponentType();
/*     */     }
/* 230 */     return (paramClass.isPrimitive()) || (paramClass.isEnum()) || (paramClass.isAnnotation()) || (String.class.equals(paramClass)) || (Class.class.equals(paramClass));
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
/*     */   private static int hashMember(String paramString, Object paramObject)
/*     */   {
/* 243 */     int i = paramString.hashCode() * 127;
/* 244 */     if (paramObject.getClass().isArray()) {
/* 245 */       return i ^ arrayMemberHash(paramObject.getClass().getComponentType(), paramObject);
/*     */     }
/* 247 */     if ((paramObject instanceof Annotation)) {
/* 248 */       return i ^ hashCode((Annotation)paramObject);
/*     */     }
/* 250 */     return i ^ paramObject.hashCode();
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
/*     */   private static boolean memberEquals(Class<?> paramClass, Object paramObject1, Object paramObject2)
/*     */   {
/* 264 */     if (paramObject1 == paramObject2) {
/* 265 */       return true;
/*     */     }
/* 267 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 268 */       return false;
/*     */     }
/* 270 */     if (paramClass.isArray()) {
/* 271 */       return arrayMemberEquals(paramClass.getComponentType(), paramObject1, paramObject2);
/*     */     }
/* 273 */     if (paramClass.isAnnotation()) {
/* 274 */       return equals((Annotation)paramObject1, (Annotation)paramObject2);
/*     */     }
/* 276 */     return paramObject1.equals(paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean arrayMemberEquals(Class<?> paramClass, Object paramObject1, Object paramObject2)
/*     */   {
/* 288 */     if (paramClass.isAnnotation()) {
/* 289 */       return annotationArrayMemberEquals((Annotation[])paramObject1, (Annotation[])paramObject2);
/*     */     }
/* 291 */     if (paramClass.equals(Byte.TYPE)) {
/* 292 */       return Arrays.equals((byte[])paramObject1, (byte[])paramObject2);
/*     */     }
/* 294 */     if (paramClass.equals(Short.TYPE)) {
/* 295 */       return Arrays.equals((short[])paramObject1, (short[])paramObject2);
/*     */     }
/* 297 */     if (paramClass.equals(Integer.TYPE)) {
/* 298 */       return Arrays.equals((int[])paramObject1, (int[])paramObject2);
/*     */     }
/* 300 */     if (paramClass.equals(Character.TYPE)) {
/* 301 */       return Arrays.equals((char[])paramObject1, (char[])paramObject2);
/*     */     }
/* 303 */     if (paramClass.equals(Long.TYPE)) {
/* 304 */       return Arrays.equals((long[])paramObject1, (long[])paramObject2);
/*     */     }
/* 306 */     if (paramClass.equals(Float.TYPE)) {
/* 307 */       return Arrays.equals((float[])paramObject1, (float[])paramObject2);
/*     */     }
/* 309 */     if (paramClass.equals(Double.TYPE)) {
/* 310 */       return Arrays.equals((double[])paramObject1, (double[])paramObject2);
/*     */     }
/* 312 */     if (paramClass.equals(Boolean.TYPE)) {
/* 313 */       return Arrays.equals((boolean[])paramObject1, (boolean[])paramObject2);
/*     */     }
/* 315 */     return Arrays.equals((Object[])paramObject1, (Object[])paramObject2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean annotationArrayMemberEquals(Annotation[] paramArrayOfAnnotation1, Annotation[] paramArrayOfAnnotation2)
/*     */   {
/* 326 */     if (paramArrayOfAnnotation1.length != paramArrayOfAnnotation2.length) {
/* 327 */       return false;
/*     */     }
/* 329 */     for (int i = 0; i < paramArrayOfAnnotation1.length; i++) {
/* 330 */       if (!equals(paramArrayOfAnnotation1[i], paramArrayOfAnnotation2[i])) {
/* 331 */         return false;
/*     */       }
/*     */     }
/* 334 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int arrayMemberHash(Class<?> paramClass, Object paramObject)
/*     */   {
/* 345 */     if (paramClass.equals(Byte.TYPE)) {
/* 346 */       return Arrays.hashCode((byte[])paramObject);
/*     */     }
/* 348 */     if (paramClass.equals(Short.TYPE)) {
/* 349 */       return Arrays.hashCode((short[])paramObject);
/*     */     }
/* 351 */     if (paramClass.equals(Integer.TYPE)) {
/* 352 */       return Arrays.hashCode((int[])paramObject);
/*     */     }
/* 354 */     if (paramClass.equals(Character.TYPE)) {
/* 355 */       return Arrays.hashCode((char[])paramObject);
/*     */     }
/* 357 */     if (paramClass.equals(Long.TYPE)) {
/* 358 */       return Arrays.hashCode((long[])paramObject);
/*     */     }
/* 360 */     if (paramClass.equals(Float.TYPE)) {
/* 361 */       return Arrays.hashCode((float[])paramObject);
/*     */     }
/* 363 */     if (paramClass.equals(Double.TYPE)) {
/* 364 */       return Arrays.hashCode((double[])paramObject);
/*     */     }
/* 366 */     if (paramClass.equals(Boolean.TYPE)) {
/* 367 */       return Arrays.hashCode((boolean[])paramObject);
/*     */     }
/* 369 */     return Arrays.hashCode((Object[])paramObject);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\AnnotationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */