/*     */ package org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.reflect.MethodUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventUtils
/*     */ {
/*     */   public static <L> void addEventListener(Object paramObject, Class<L> paramClass, L paramL)
/*     */   {
/*     */     try
/*     */     {
/*  50 */       MethodUtils.invokeMethod(paramObject, "add" + paramClass.getSimpleName(), new Object[] { paramL });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/*  52 */       throw new IllegalArgumentException("Class " + paramObject.getClass().getName() + " does not have a public add" + paramClass.getSimpleName() + " method which takes a parameter of type " + paramClass.getName() + ".");
/*     */     }
/*     */     catch (IllegalAccessException localIllegalAccessException)
/*     */     {
/*  56 */       throw new IllegalArgumentException("Class " + paramObject.getClass().getName() + " does not have an accessible add" + paramClass.getSimpleName() + " method which takes a parameter of type " + paramClass.getName() + ".");
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException)
/*     */     {
/*  60 */       throw new RuntimeException("Unable to add listener.", localInvocationTargetException.getCause());
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
/*     */ 
/*     */ 
/*     */   public static <L> void bindEventsToMethod(Object paramObject1, String paramString, Object paramObject2, Class<L> paramClass, String... paramVarArgs)
/*     */   {
/*  77 */     Object localObject = paramClass.cast(Proxy.newProxyInstance(paramObject1.getClass().getClassLoader(), new Class[] { paramClass }, new EventBindingInvocationHandler(paramObject1, paramString, paramVarArgs)));
/*     */     
/*  79 */     addEventListener(paramObject2, paramClass, localObject);
/*     */   }
/*     */   
/*     */ 
/*     */   private static class EventBindingInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     private final Object target;
/*     */     
/*     */     private final String methodName;
/*     */     
/*     */     private final Set<String> eventTypes;
/*     */     
/*     */ 
/*     */     EventBindingInvocationHandler(Object paramObject, String paramString, String[] paramArrayOfString)
/*     */     {
/*  95 */       this.target = paramObject;
/*  96 */       this.methodName = paramString;
/*  97 */       this.eventTypes = new HashSet(Arrays.asList(paramArrayOfString));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
/*     */       throws Throwable
/*     */     {
/* 111 */       if ((this.eventTypes.isEmpty()) || (this.eventTypes.contains(paramMethod.getName()))) {
/* 112 */         if (hasMatchingParametersMethod(paramMethod)) {
/* 113 */           return MethodUtils.invokeMethod(this.target, this.methodName, paramArrayOfObject);
/*     */         }
/* 115 */         return MethodUtils.invokeMethod(this.target, this.methodName, new Object[0]);
/*     */       }
/*     */       
/* 118 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private boolean hasMatchingParametersMethod(Method paramMethod)
/*     */     {
/* 128 */       return MethodUtils.getAccessibleMethod(this.target.getClass(), this.methodName, paramMethod.getParameterTypes()) != null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\event\EventUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */