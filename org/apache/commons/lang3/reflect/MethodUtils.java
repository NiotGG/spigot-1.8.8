/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.ClassUtils.Interfaces;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ public class MethodUtils
/*     */ {
/*     */   public static Object invokeMethod(Object paramObject, String paramString, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/*  95 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/*  96 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/*  97 */     return invokeMethod(paramObject, paramString, paramVarArgs, arrayOfClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object invokeMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 123 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 124 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 125 */     Method localMethod = getMatchingAccessibleMethod(paramObject.getClass(), paramString, paramArrayOfClass);
/*     */     
/* 127 */     if (localMethod == null) {
/* 128 */       throw new NoSuchMethodException("No such accessible method: " + paramString + "() on object: " + paramObject.getClass().getName());
/*     */     }
/*     */     
/*     */ 
/* 132 */     return localMethod.invoke(paramObject, paramArrayOfObject);
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
/*     */ 
/*     */ 
/*     */   public static Object invokeExactMethod(Object paramObject, String paramString, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 156 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/* 157 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/* 158 */     return invokeExactMethod(paramObject, paramString, paramVarArgs, arrayOfClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object invokeExactMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 184 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 185 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 186 */     Method localMethod = getAccessibleMethod(paramObject.getClass(), paramString, paramArrayOfClass);
/*     */     
/* 188 */     if (localMethod == null) {
/* 189 */       throw new NoSuchMethodException("No such accessible method: " + paramString + "() on object: " + paramObject.getClass().getName());
/*     */     }
/*     */     
/*     */ 
/* 193 */     return localMethod.invoke(paramObject, paramArrayOfObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 219 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 220 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 221 */     Method localMethod = getAccessibleMethod(paramClass, paramString, paramArrayOfClass);
/* 222 */     if (localMethod == null) {
/* 223 */       throw new NoSuchMethodException("No such accessible method: " + paramString + "() on class: " + paramClass.getName());
/*     */     }
/*     */     
/* 226 */     return localMethod.invoke(null, paramArrayOfObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 256 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/* 257 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/* 258 */     return invokeStaticMethod(paramClass, paramString, paramVarArgs, arrayOfClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 287 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 288 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 289 */     Method localMethod = getMatchingAccessibleMethod(paramClass, paramString, paramArrayOfClass);
/*     */     
/* 291 */     if (localMethod == null) {
/* 292 */       throw new NoSuchMethodException("No such accessible method: " + paramString + "() on class: " + paramClass.getName());
/*     */     }
/*     */     
/* 295 */     return localMethod.invoke(null, paramArrayOfObject);
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
/*     */ 
/*     */ 
/*     */   public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 319 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/* 320 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/* 321 */     return invokeExactStaticMethod(paramClass, paramString, paramVarArgs, arrayOfClass);
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
/*     */   public static Method getAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
/*     */   {
/*     */     try
/*     */     {
/* 339 */       return getAccessibleMethod(paramClass.getMethod(paramString, paramVarArgs));
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {}
/* 342 */     return null;
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
/*     */   public static Method getAccessibleMethod(Method paramMethod)
/*     */   {
/* 355 */     if (!MemberUtils.isAccessible(paramMethod)) {
/* 356 */       return null;
/*     */     }
/*     */     
/* 359 */     Class localClass = paramMethod.getDeclaringClass();
/* 360 */     if (Modifier.isPublic(localClass.getModifiers())) {
/* 361 */       return paramMethod;
/*     */     }
/* 363 */     String str = paramMethod.getName();
/* 364 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/*     */     
/*     */ 
/* 367 */     paramMethod = getAccessibleMethodFromInterfaceNest(localClass, str, arrayOfClass);
/*     */     
/*     */ 
/*     */ 
/* 371 */     if (paramMethod == null) {
/* 372 */       paramMethod = getAccessibleMethodFromSuperclass(localClass, str, arrayOfClass);
/*     */     }
/*     */     
/* 375 */     return paramMethod;
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
/*     */   private static Method getAccessibleMethodFromSuperclass(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
/*     */   {
/* 390 */     Class localClass = paramClass.getSuperclass();
/* 391 */     while (localClass != null) {
/* 392 */       if (Modifier.isPublic(localClass.getModifiers())) {
/*     */         try {
/* 394 */           return localClass.getMethod(paramString, paramVarArgs);
/*     */         } catch (NoSuchMethodException localNoSuchMethodException) {
/* 396 */           return null;
/*     */         }
/*     */       }
/* 399 */       localClass = localClass.getSuperclass();
/*     */     }
/* 401 */     return null;
/*     */   }
/*     */   
/*     */   private static Method getAccessibleMethodFromInterfaceNest(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
/*     */   {
/* 422 */     for (; 
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
/* 422 */         paramClass != null; paramClass = paramClass.getSuperclass())
/*     */     {
/*     */ 
/* 425 */       Class[] arrayOfClass = paramClass.getInterfaces();
/* 426 */       for (int i = 0; i < arrayOfClass.length; i++)
/*     */       {
/* 428 */         if (Modifier.isPublic(arrayOfClass[i].getModifiers()))
/*     */         {
/*     */           try
/*     */           {
/*     */ 
/* 433 */             return arrayOfClass[i].getDeclaredMethod(paramString, paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           catch (NoSuchMethodException localNoSuchMethodException)
/*     */           {
/*     */ 
/*     */ 
/* 442 */             Method localMethod = getAccessibleMethodFromInterfaceNest(arrayOfClass[i], paramString, paramVarArgs);
/*     */             
/* 444 */             if (localMethod != null)
/* 445 */               return localMethod;
/*     */           } }
/*     */       }
/*     */     }
/* 449 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Method getMatchingAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
/*     */   {
/*     */     try
/*     */     {
/* 477 */       Method localMethod1 = paramClass.getMethod(paramString, paramVarArgs);
/* 478 */       MemberUtils.setAccessibleWorkaround(localMethod1);
/* 479 */       return localMethod1;
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException)
/*     */     {
/* 483 */       Object localObject = null;
/* 484 */       Method[] arrayOfMethod1 = paramClass.getMethods();
/* 485 */       for (Method localMethod2 : arrayOfMethod1)
/*     */       {
/* 487 */         if ((localMethod2.getName().equals(paramString)) && (ClassUtils.isAssignable(paramVarArgs, localMethod2.getParameterTypes(), true)))
/*     */         {
/* 489 */           Method localMethod3 = getAccessibleMethod(localMethod2);
/* 490 */           if ((localMethod3 != null) && ((localObject == null) || (MemberUtils.compareParameterTypes(localMethod3.getParameterTypes(), ((Method)localObject).getParameterTypes(), paramVarArgs) < 0)))
/*     */           {
/*     */ 
/*     */ 
/* 494 */             localObject = localMethod3;
/*     */           }
/*     */         }
/*     */       }
/* 498 */       if (localObject != null) {
/* 499 */         MemberUtils.setAccessibleWorkaround((AccessibleObject)localObject);
/*     */       }
/* 501 */       return (Method)localObject;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Set<Method> getOverrideHierarchy(Method paramMethod, ClassUtils.Interfaces paramInterfaces)
/*     */   {
/* 513 */     Validate.notNull(paramMethod);
/* 514 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 515 */     localLinkedHashSet.add(paramMethod);
/*     */     
/* 517 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/*     */     
/* 519 */     Class localClass1 = paramMethod.getDeclaringClass();
/*     */     
/* 521 */     Iterator localIterator = ClassUtils.hierarchy(localClass1, paramInterfaces).iterator();
/*     */     
/* 523 */     localIterator.next();
/* 524 */     while (localIterator.hasNext()) {
/* 525 */       Class localClass2 = (Class)localIterator.next();
/* 526 */       Method localMethod = getMatchingAccessibleMethod(localClass2, paramMethod.getName(), arrayOfClass);
/* 527 */       if (localMethod != null)
/*     */       {
/*     */ 
/* 530 */         if (Arrays.equals(localMethod.getParameterTypes(), arrayOfClass))
/*     */         {
/* 532 */           localLinkedHashSet.add(localMethod);
/*     */         }
/*     */         else
/*     */         {
/* 536 */           Map localMap = TypeUtils.getTypeArguments(localClass1, localMethod.getDeclaringClass());
/* 537 */           for (int i = 0;; i++) { if (i >= arrayOfClass.length) break label189;
/* 538 */             Type localType1 = TypeUtils.unrollVariables(localMap, paramMethod.getGenericParameterTypes()[i]);
/* 539 */             Type localType2 = TypeUtils.unrollVariables(localMap, localMethod.getGenericParameterTypes()[i]);
/* 540 */             if (!TypeUtils.equals(localType1, localType2))
/*     */               break;
/*     */           }
/*     */           label189:
/* 544 */           localLinkedHashSet.add(localMethod);
/*     */         } } }
/* 546 */     return localLinkedHashSet;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\MethodUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */