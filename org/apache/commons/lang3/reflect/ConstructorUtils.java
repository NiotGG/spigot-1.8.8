/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
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
/*     */ public class ConstructorUtils
/*     */ {
/*     */   public static <T> T invokeConstructor(Class<T> paramClass, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*     */   {
/*  83 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/*  84 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/*  85 */     return (T)invokeConstructor(paramClass, paramVarArgs, arrayOfClass);
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
/*     */   public static <T> T invokeConstructor(Class<T> paramClass, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*     */   {
/* 111 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 112 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 113 */     Constructor localConstructor = getMatchingAccessibleConstructor(paramClass, paramArrayOfClass);
/* 114 */     if (localConstructor == null) {
/* 115 */       throw new NoSuchMethodException("No such accessible constructor on object: " + paramClass.getName());
/*     */     }
/*     */     
/* 118 */     return (T)localConstructor.newInstance(paramArrayOfObject);
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
/*     */   public static <T> T invokeExactConstructor(Class<T> paramClass, Object... paramVarArgs)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*     */   {
/* 143 */     paramVarArgs = ArrayUtils.nullToEmpty(paramVarArgs);
/* 144 */     Class[] arrayOfClass = ClassUtils.toClass(paramVarArgs);
/* 145 */     return (T)invokeExactConstructor(paramClass, paramVarArgs, arrayOfClass);
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
/*     */   public static <T> T invokeExactConstructor(Class<T> paramClass, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
/*     */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*     */   {
/* 171 */     paramArrayOfObject = ArrayUtils.nullToEmpty(paramArrayOfObject);
/* 172 */     paramArrayOfClass = ArrayUtils.nullToEmpty(paramArrayOfClass);
/* 173 */     Constructor localConstructor = getAccessibleConstructor(paramClass, paramArrayOfClass);
/* 174 */     if (localConstructor == null) {
/* 175 */       throw new NoSuchMethodException("No such accessible constructor on object: " + paramClass.getName());
/*     */     }
/*     */     
/* 178 */     return (T)localConstructor.newInstance(paramArrayOfObject);
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
/*     */   public static <T> Constructor<T> getAccessibleConstructor(Class<T> paramClass, Class<?>... paramVarArgs)
/*     */   {
/* 198 */     Validate.notNull(paramClass, "class cannot be null", new Object[0]);
/*     */     try {
/* 200 */       return getAccessibleConstructor(paramClass.getConstructor(paramVarArgs));
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {}
/* 202 */     return null;
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
/*     */   public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> paramConstructor)
/*     */   {
/* 218 */     Validate.notNull(paramConstructor, "constructor cannot be null", new Object[0]);
/* 219 */     return (MemberUtils.isAccessible(paramConstructor)) && (isAccessible(paramConstructor.getDeclaringClass())) ? paramConstructor : null;
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
/*     */   public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> paramClass, Class<?>... paramVarArgs)
/*     */   {
/* 243 */     Validate.notNull(paramClass, "class cannot be null", new Object[0]);
/*     */     
/*     */     try
/*     */     {
/* 247 */       Constructor localConstructor1 = paramClass.getConstructor(paramVarArgs);
/* 248 */       MemberUtils.setAccessibleWorkaround(localConstructor1);
/* 249 */       return localConstructor1;
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {
/* 252 */       Object localObject = null;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 257 */       Constructor[] arrayOfConstructor1 = paramClass.getConstructors();
/*     */       
/*     */ 
/* 260 */       for (Constructor localConstructor2 : arrayOfConstructor1)
/*     */       {
/* 262 */         if (ClassUtils.isAssignable(paramVarArgs, localConstructor2.getParameterTypes(), true))
/*     */         {
/* 264 */           localConstructor2 = getAccessibleConstructor(localConstructor2);
/* 265 */           if (localConstructor2 != null) {
/* 266 */             MemberUtils.setAccessibleWorkaround(localConstructor2);
/* 267 */             if ((localObject == null) || (MemberUtils.compareParameterTypes(localConstructor2.getParameterTypes(), ((Constructor)localObject).getParameterTypes(), paramVarArgs) < 0))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 273 */               Constructor localConstructor3 = localConstructor2;
/* 274 */               localObject = localConstructor3;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 279 */       return (Constructor<T>)localObject;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isAccessible(Class<?> paramClass)
/*     */   {
/* 290 */     Object localObject = paramClass;
/* 291 */     while (localObject != null) {
/* 292 */       if (!Modifier.isPublic(((Class)localObject).getModifiers())) {
/* 293 */         return false;
/*     */       }
/* 295 */       localObject = ((Class)localObject).getEnclosingClass();
/*     */     }
/* 297 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\ConstructorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */