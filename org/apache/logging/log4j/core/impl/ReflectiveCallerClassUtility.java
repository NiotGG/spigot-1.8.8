/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.helpers.Loader;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReflectiveCallerClassUtility
/*     */ {
/*  59 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private static final boolean GET_CALLER_CLASS_SUPPORTED;
/*     */   
/*     */   private static final Method GET_CALLER_CLASS_METHOD;
/*     */   static final int JAVA_7U25_COMPENSATION_OFFSET;
/*     */   
/*     */   static
/*     */   {
/*  68 */     Object localObject1 = null;
/*  69 */     int i = 0;
/*     */     try
/*     */     {
/*  72 */       ClassLoader localClassLoader = Loader.getClassLoader();
/*     */       
/*  74 */       Class localClass = localClassLoader.loadClass("sun.reflect.Reflection");
/*  75 */       Method[] arrayOfMethod = localClass.getMethods();
/*  76 */       for (Object localObject3 : arrayOfMethod) {
/*  77 */         int m = ((Method)localObject3).getModifiers();
/*  78 */         Class[] arrayOfClass = ((Method)localObject3).getParameterTypes();
/*  79 */         if ((((Method)localObject3).getName().equals("getCallerClass")) && (Modifier.isStatic(m)) && (arrayOfClass.length == 1) && (arrayOfClass[0] == Integer.TYPE))
/*     */         {
/*  81 */           localObject1 = localObject3;
/*  82 */           break;
/*     */         }
/*     */       }
/*     */       
/*  86 */       if (localObject1 == null) {
/*  87 */         LOGGER.info("sun.reflect.Reflection#getCallerClass does not exist.");
/*     */       } else {
/*  89 */         ??? = ((Method)localObject1).invoke(null, new Object[] { Integer.valueOf(0) });
/*  90 */         if ((??? == null) || (??? != localClass)) {
/*  91 */           localObject1 = null;
/*  92 */           LOGGER.warn("sun.reflect.Reflection#getCallerClass returned unexpected value of [{}] and is unusable. Will fall back to another option.", new Object[] { ??? });
/*     */         }
/*     */         else {
/*  95 */           ??? = ((Method)localObject1).invoke(null, new Object[] { Integer.valueOf(1) });
/*  96 */           if (??? == localClass) {
/*  97 */             i = 1;
/*  98 */             LOGGER.warn("sun.reflect.Reflection#getCallerClass is broken in Java 7u25. You should upgrade to 7u40. Using alternate stack offset to compensate.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {
/* 104 */       LOGGER.info("sun.reflect.Reflection is not installed.");
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 106 */       LOGGER.info("sun.reflect.Reflection#getCallerClass is not accessible.");
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 108 */       LOGGER.info("sun.reflect.Reflection#getCallerClass is not supported.");
/*     */     }
/*     */     
/* 111 */     if (localObject1 == null) {
/* 112 */       GET_CALLER_CLASS_SUPPORTED = false;
/* 113 */       GET_CALLER_CLASS_METHOD = null;
/* 114 */       JAVA_7U25_COMPENSATION_OFFSET = -1;
/*     */     } else {
/* 116 */       GET_CALLER_CLASS_SUPPORTED = true;
/* 117 */       GET_CALLER_CLASS_METHOD = (Method)localObject1;
/* 118 */       JAVA_7U25_COMPENSATION_OFFSET = i;
/*     */     }
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
/*     */   public static boolean isSupported()
/*     */   {
/* 133 */     return GET_CALLER_CLASS_SUPPORTED;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<?> getCaller(int paramInt)
/*     */   {
/* 145 */     if (!GET_CALLER_CLASS_SUPPORTED) {
/* 146 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 150 */       return (Class)GET_CALLER_CLASS_METHOD.invoke(null, new Object[] { Integer.valueOf(paramInt + 1 + JAVA_7U25_COMPENSATION_OFFSET) });
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 152 */       LOGGER.warn("Should not have failed to call getCallerClass.");
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 154 */       LOGGER.warn("Should not have failed to call getCallerClass.");
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ReflectiveCallerClassUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */