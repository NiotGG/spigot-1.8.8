/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.mutable.MutableObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassUtils
/*      */ {
/*      */   public static final char PACKAGE_SEPARATOR_CHAR = '.';
/*      */   
/*      */   public static enum Interfaces
/*      */   {
/*   53 */     INCLUDE,  EXCLUDE;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private Interfaces() {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   64 */   public static final String PACKAGE_SEPARATOR = String.valueOf('.');
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   74 */   public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   79 */   private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap();
/*      */   
/*   81 */   static { primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
/*   82 */     primitiveWrapperMap.put(Byte.TYPE, Byte.class);
/*   83 */     primitiveWrapperMap.put(Character.TYPE, Character.class);
/*   84 */     primitiveWrapperMap.put(Short.TYPE, Short.class);
/*   85 */     primitiveWrapperMap.put(Integer.TYPE, Integer.class);
/*   86 */     primitiveWrapperMap.put(Long.TYPE, Long.class);
/*   87 */     primitiveWrapperMap.put(Double.TYPE, Double.class);
/*   88 */     primitiveWrapperMap.put(Float.TYPE, Float.class);
/*   89 */     primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */     wrapperPrimitiveMap = new HashMap();
/*      */     
/*   97 */     for (Object localObject1 = primitiveWrapperMap.keySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Class)((Iterator)localObject1).next();
/*   98 */       localObject3 = (Class)primitiveWrapperMap.get(localObject2);
/*   99 */       if (!localObject2.equals(localObject3)) {
/*  100 */         wrapperPrimitiveMap.put(localObject3, localObject2);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  119 */     localObject1 = new HashMap();
/*  120 */     ((Map)localObject1).put("int", "I");
/*  121 */     ((Map)localObject1).put("boolean", "Z");
/*  122 */     ((Map)localObject1).put("float", "F");
/*  123 */     ((Map)localObject1).put("long", "J");
/*  124 */     ((Map)localObject1).put("short", "S");
/*  125 */     ((Map)localObject1).put("byte", "B");
/*  126 */     ((Map)localObject1).put("double", "D");
/*  127 */     ((Map)localObject1).put("char", "C");
/*  128 */     ((Map)localObject1).put("void", "V");
/*  129 */     Object localObject2 = new HashMap();
/*  130 */     for (Object localObject3 = ((Map)localObject1).entrySet().iterator(); ((Iterator)localObject3).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject3).next();
/*  131 */       ((Map)localObject2).put(localEntry.getValue(), localEntry.getKey());
/*      */     }
/*  133 */     abbreviationMap = Collections.unmodifiableMap((Map)localObject1);
/*  134 */     reverseAbbreviationMap = Collections.unmodifiableMap((Map)localObject2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortClassName(Object paramObject, String paramString)
/*      */   {
/*  159 */     if (paramObject == null) {
/*  160 */       return paramString;
/*      */     }
/*  162 */     return getShortClassName(paramObject.getClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortClassName(Class<?> paramClass)
/*      */   {
/*  176 */     if (paramClass == null) {
/*  177 */       return "";
/*      */     }
/*  179 */     return getShortClassName(paramClass.getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortClassName(String paramString)
/*      */   {
/*  195 */     if (StringUtils.isEmpty(paramString)) {
/*  196 */       return "";
/*      */     }
/*      */     
/*  199 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/*      */ 
/*  202 */     if (paramString.startsWith("[")) {
/*  203 */       while (paramString.charAt(0) == '[') {
/*  204 */         paramString = paramString.substring(1);
/*  205 */         localStringBuilder.append("[]");
/*      */       }
/*      */       
/*  208 */       if ((paramString.charAt(0) == 'L') && (paramString.charAt(paramString.length() - 1) == ';')) {
/*  209 */         paramString = paramString.substring(1, paramString.length() - 1);
/*      */       }
/*      */       
/*  212 */       if (reverseAbbreviationMap.containsKey(paramString)) {
/*  213 */         paramString = (String)reverseAbbreviationMap.get(paramString);
/*      */       }
/*      */     }
/*      */     
/*  217 */     int i = paramString.lastIndexOf('.');
/*  218 */     int j = paramString.indexOf('$', i == -1 ? 0 : i + 1);
/*      */     
/*  220 */     String str = paramString.substring(i + 1);
/*  221 */     if (j != -1) {
/*  222 */       str = str.replace('$', '.');
/*      */     }
/*  224 */     return str + localStringBuilder;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getSimpleName(Class<?> paramClass)
/*      */   {
/*  236 */     if (paramClass == null) {
/*  237 */       return "";
/*      */     }
/*  239 */     return paramClass.getSimpleName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getSimpleName(Object paramObject, String paramString)
/*      */   {
/*  252 */     if (paramObject == null) {
/*  253 */       return paramString;
/*      */     }
/*  255 */     return getSimpleName(paramObject.getClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageName(Object paramObject, String paramString)
/*      */   {
/*  268 */     if (paramObject == null) {
/*  269 */       return paramString;
/*      */     }
/*  271 */     return getPackageName(paramObject.getClass());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageName(Class<?> paramClass)
/*      */   {
/*  281 */     if (paramClass == null) {
/*  282 */       return "";
/*      */     }
/*  284 */     return getPackageName(paramClass.getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageName(String paramString)
/*      */   {
/*  297 */     if (StringUtils.isEmpty(paramString)) {
/*  298 */       return "";
/*      */     }
/*      */     
/*      */ 
/*  302 */     while (paramString.charAt(0) == '[') {
/*  303 */       paramString = paramString.substring(1);
/*      */     }
/*      */     
/*  306 */     if ((paramString.charAt(0) == 'L') && (paramString.charAt(paramString.length() - 1) == ';')) {
/*  307 */       paramString = paramString.substring(1);
/*      */     }
/*      */     
/*  310 */     int i = paramString.lastIndexOf('.');
/*  311 */     if (i == -1) {
/*  312 */       return "";
/*      */     }
/*  314 */     return paramString.substring(0, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Class<?>> getAllSuperclasses(Class<?> paramClass)
/*      */   {
/*  327 */     if (paramClass == null) {
/*  328 */       return null;
/*      */     }
/*  330 */     ArrayList localArrayList = new ArrayList();
/*  331 */     Class localClass = paramClass.getSuperclass();
/*  332 */     while (localClass != null) {
/*  333 */       localArrayList.add(localClass);
/*  334 */       localClass = localClass.getSuperclass();
/*      */     }
/*  336 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Class<?>> getAllInterfaces(Class<?> paramClass)
/*      */   {
/*  353 */     if (paramClass == null) {
/*  354 */       return null;
/*      */     }
/*      */     
/*  357 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/*  358 */     getAllInterfaces(paramClass, localLinkedHashSet);
/*      */     
/*  360 */     return new ArrayList(localLinkedHashSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void getAllInterfaces(Class<?> paramClass, HashSet<Class<?>> paramHashSet)
/*      */   {
/*  370 */     while (paramClass != null) {
/*  371 */       Class[] arrayOfClass1 = paramClass.getInterfaces();
/*      */       
/*  373 */       for (Class localClass : arrayOfClass1) {
/*  374 */         if (paramHashSet.add(localClass)) {
/*  375 */           getAllInterfaces(localClass, paramHashSet);
/*      */         }
/*      */       }
/*      */       
/*  379 */       paramClass = paramClass.getSuperclass();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<Class<?>> convertClassNamesToClasses(List<String> paramList)
/*      */   {
/*  398 */     if (paramList == null) {
/*  399 */       return null;
/*      */     }
/*  401 */     ArrayList localArrayList = new ArrayList(paramList.size());
/*  402 */     for (String str : paramList) {
/*      */       try {
/*  404 */         localArrayList.add(Class.forName(str));
/*      */       } catch (Exception localException) {
/*  406 */         localArrayList.add(null);
/*      */       }
/*      */     }
/*  409 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<String> convertClassesToClassNames(List<Class<?>> paramList)
/*      */   {
/*  425 */     if (paramList == null) {
/*  426 */       return null;
/*      */     }
/*  428 */     ArrayList localArrayList = new ArrayList(paramList.size());
/*  429 */     for (Class localClass : paramList) {
/*  430 */       if (localClass == null) {
/*  431 */         localArrayList.add(null);
/*      */       } else {
/*  433 */         localArrayList.add(localClass.getName());
/*      */       }
/*      */     }
/*  436 */     return localArrayList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Map<String, String> abbreviationMap;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final Map<String, String> reverseAbbreviationMap;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isAssignable(Class<?>[] paramArrayOfClass1, Class<?>... paramVarArgs)
/*      */   {
/*  478 */     return isAssignable(paramArrayOfClass1, paramVarArgs, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isAssignable(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, boolean paramBoolean)
/*      */   {
/*  514 */     if (!ArrayUtils.isSameLength(paramArrayOfClass1, paramArrayOfClass2)) {
/*  515 */       return false;
/*      */     }
/*  517 */     if (paramArrayOfClass1 == null) {
/*  518 */       paramArrayOfClass1 = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  520 */     if (paramArrayOfClass2 == null) {
/*  521 */       paramArrayOfClass2 = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  523 */     for (int i = 0; i < paramArrayOfClass1.length; i++) {
/*  524 */       if (!isAssignable(paramArrayOfClass1[i], paramArrayOfClass2[i], paramBoolean)) {
/*  525 */         return false;
/*      */       }
/*      */     }
/*  528 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isPrimitiveOrWrapper(Class<?> paramClass)
/*      */   {
/*  542 */     if (paramClass == null) {
/*  543 */       return false;
/*      */     }
/*  545 */     return (paramClass.isPrimitive()) || (isPrimitiveWrapper(paramClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isPrimitiveWrapper(Class<?> paramClass)
/*      */   {
/*  559 */     return wrapperPrimitiveMap.containsKey(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/*  594 */     return isAssignable(paramClass1, paramClass2, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isAssignable(Class<?> paramClass1, Class<?> paramClass2, boolean paramBoolean)
/*      */   {
/*  625 */     if (paramClass2 == null) {
/*  626 */       return false;
/*      */     }
/*      */     
/*  629 */     if (paramClass1 == null) {
/*  630 */       return !paramClass2.isPrimitive();
/*      */     }
/*      */     
/*  633 */     if (paramBoolean) {
/*  634 */       if ((paramClass1.isPrimitive()) && (!paramClass2.isPrimitive())) {
/*  635 */         paramClass1 = primitiveToWrapper(paramClass1);
/*  636 */         if (paramClass1 == null) {
/*  637 */           return false;
/*      */         }
/*      */       }
/*  640 */       if ((paramClass2.isPrimitive()) && (!paramClass1.isPrimitive())) {
/*  641 */         paramClass1 = wrapperToPrimitive(paramClass1);
/*  642 */         if (paramClass1 == null) {
/*  643 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*  647 */     if (paramClass1.equals(paramClass2)) {
/*  648 */       return true;
/*      */     }
/*  650 */     if (paramClass1.isPrimitive()) {
/*  651 */       if (!paramClass2.isPrimitive()) {
/*  652 */         return false;
/*      */       }
/*  654 */       if (Integer.TYPE.equals(paramClass1)) {
/*  655 */         return (Long.TYPE.equals(paramClass2)) || (Float.TYPE.equals(paramClass2)) || (Double.TYPE.equals(paramClass2));
/*      */       }
/*      */       
/*      */ 
/*  659 */       if (Long.TYPE.equals(paramClass1)) {
/*  660 */         return (Float.TYPE.equals(paramClass2)) || (Double.TYPE.equals(paramClass2));
/*      */       }
/*      */       
/*  663 */       if (Boolean.TYPE.equals(paramClass1)) {
/*  664 */         return false;
/*      */       }
/*  666 */       if (Double.TYPE.equals(paramClass1)) {
/*  667 */         return false;
/*      */       }
/*  669 */       if (Float.TYPE.equals(paramClass1)) {
/*  670 */         return Double.TYPE.equals(paramClass2);
/*      */       }
/*  672 */       if (Character.TYPE.equals(paramClass1)) {
/*  673 */         return (Integer.TYPE.equals(paramClass2)) || (Long.TYPE.equals(paramClass2)) || (Float.TYPE.equals(paramClass2)) || (Double.TYPE.equals(paramClass2));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  678 */       if (Short.TYPE.equals(paramClass1)) {
/*  679 */         return (Integer.TYPE.equals(paramClass2)) || (Long.TYPE.equals(paramClass2)) || (Float.TYPE.equals(paramClass2)) || (Double.TYPE.equals(paramClass2));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  684 */       if (Byte.TYPE.equals(paramClass1)) {
/*  685 */         return (Short.TYPE.equals(paramClass2)) || (Integer.TYPE.equals(paramClass2)) || (Long.TYPE.equals(paramClass2)) || (Float.TYPE.equals(paramClass2)) || (Double.TYPE.equals(paramClass2));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  692 */       return false;
/*      */     }
/*  694 */     return paramClass2.isAssignableFrom(paramClass1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> primitiveToWrapper(Class<?> paramClass)
/*      */   {
/*  710 */     Object localObject = paramClass;
/*  711 */     if ((paramClass != null) && (paramClass.isPrimitive())) {
/*  712 */       localObject = (Class)primitiveWrapperMap.get(paramClass);
/*      */     }
/*  714 */     return (Class<?>)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?>[] primitivesToWrappers(Class<?>... paramVarArgs)
/*      */   {
/*  728 */     if (paramVarArgs == null) {
/*  729 */       return null;
/*      */     }
/*      */     
/*  732 */     if (paramVarArgs.length == 0) {
/*  733 */       return paramVarArgs;
/*      */     }
/*      */     
/*  736 */     Class[] arrayOfClass = new Class[paramVarArgs.length];
/*  737 */     for (int i = 0; i < paramVarArgs.length; i++) {
/*  738 */       arrayOfClass[i] = primitiveToWrapper(paramVarArgs[i]);
/*      */     }
/*  740 */     return arrayOfClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> wrapperToPrimitive(Class<?> paramClass)
/*      */   {
/*  760 */     return (Class)wrapperPrimitiveMap.get(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?>[] wrappersToPrimitives(Class<?>... paramVarArgs)
/*      */   {
/*  778 */     if (paramVarArgs == null) {
/*  779 */       return null;
/*      */     }
/*      */     
/*  782 */     if (paramVarArgs.length == 0) {
/*  783 */       return paramVarArgs;
/*      */     }
/*      */     
/*  786 */     Class[] arrayOfClass = new Class[paramVarArgs.length];
/*  787 */     for (int i = 0; i < paramVarArgs.length; i++) {
/*  788 */       arrayOfClass[i] = wrapperToPrimitive(paramVarArgs[i]);
/*      */     }
/*  790 */     return arrayOfClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isInnerClass(Class<?> paramClass)
/*      */   {
/*  803 */     return (paramClass != null) && (paramClass.getEnclosingClass() != null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> getClass(ClassLoader paramClassLoader, String paramString, boolean paramBoolean)
/*      */     throws ClassNotFoundException
/*      */   {
/*      */     try
/*      */     {
/*      */       Class localClass;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  824 */       if (abbreviationMap.containsKey(paramString)) {
/*  825 */         String str = "[" + (String)abbreviationMap.get(paramString);
/*  826 */         localClass = Class.forName(str, paramBoolean, paramClassLoader).getComponentType();
/*      */       }
/*  828 */       return Class.forName(toCanonicalName(paramString), paramBoolean, paramClassLoader);
/*      */ 
/*      */     }
/*      */     catch (ClassNotFoundException localClassNotFoundException1)
/*      */     {
/*  833 */       int i = paramString.lastIndexOf('.');
/*      */       
/*  835 */       if (i != -1) {
/*      */         try {
/*  837 */           return getClass(paramClassLoader, paramString.substring(0, i) + '$' + paramString.substring(i + 1), paramBoolean);
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException2) {}
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  845 */       throw localClassNotFoundException1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> getClass(ClassLoader paramClassLoader, String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  862 */     return getClass(paramClassLoader, paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> getClass(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  877 */     return getClass(paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?> getClass(String paramString, boolean paramBoolean)
/*      */     throws ClassNotFoundException
/*      */   {
/*  892 */     ClassLoader localClassLoader1 = Thread.currentThread().getContextClassLoader();
/*  893 */     ClassLoader localClassLoader2 = localClassLoader1 == null ? ClassUtils.class.getClassLoader() : localClassLoader1;
/*  894 */     return getClass(localClassLoader2, paramString, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Method getPublicMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
/*      */     throws SecurityException, NoSuchMethodException
/*      */   {
/*  924 */     Method localMethod1 = paramClass.getMethod(paramString, paramVarArgs);
/*  925 */     if (Modifier.isPublic(localMethod1.getDeclaringClass().getModifiers())) {
/*  926 */       return localMethod1;
/*      */     }
/*      */     
/*  929 */     ArrayList localArrayList = new ArrayList();
/*  930 */     localArrayList.addAll(getAllInterfaces(paramClass));
/*  931 */     localArrayList.addAll(getAllSuperclasses(paramClass));
/*      */     
/*  933 */     for (Class localClass : localArrayList) {
/*  934 */       if (Modifier.isPublic(localClass.getModifiers()))
/*      */       {
/*      */         Method localMethod2;
/*      */         try
/*      */         {
/*  939 */           localMethod2 = localClass.getMethod(paramString, paramVarArgs);
/*      */         } catch (NoSuchMethodException localNoSuchMethodException) {}
/*  941 */         continue;
/*      */         
/*  943 */         if (Modifier.isPublic(localMethod2.getDeclaringClass().getModifiers())) {
/*  944 */           return localMethod2;
/*      */         }
/*      */       }
/*      */     }
/*  948 */     throw new NoSuchMethodException("Can't find a public method for " + paramString + " " + ArrayUtils.toString(paramVarArgs));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String toCanonicalName(String paramString)
/*      */   {
/*  960 */     paramString = StringUtils.deleteWhitespace(paramString);
/*  961 */     if (paramString == null)
/*  962 */       throw new NullPointerException("className must not be null.");
/*  963 */     if (paramString.endsWith("[]")) {
/*  964 */       StringBuilder localStringBuilder = new StringBuilder();
/*  965 */       while (paramString.endsWith("[]")) {
/*  966 */         paramString = paramString.substring(0, paramString.length() - 2);
/*  967 */         localStringBuilder.append("[");
/*      */       }
/*  969 */       String str = (String)abbreviationMap.get(paramString);
/*  970 */       if (str != null) {
/*  971 */         localStringBuilder.append(str);
/*      */       } else {
/*  973 */         localStringBuilder.append("L").append(paramString).append(";");
/*      */       }
/*  975 */       paramString = localStringBuilder.toString();
/*      */     }
/*  977 */     return paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Class<?>[] toClass(Object... paramVarArgs)
/*      */   {
/*  991 */     if (paramVarArgs == null)
/*  992 */       return null;
/*  993 */     if (paramVarArgs.length == 0) {
/*  994 */       return ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  996 */     Class[] arrayOfClass = new Class[paramVarArgs.length];
/*  997 */     for (int i = 0; i < paramVarArgs.length; i++) {
/*  998 */       arrayOfClass[i] = (paramVarArgs[i] == null ? null : paramVarArgs[i].getClass());
/*      */     }
/* 1000 */     return arrayOfClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortCanonicalName(Object paramObject, String paramString)
/*      */   {
/* 1014 */     if (paramObject == null) {
/* 1015 */       return paramString;
/*      */     }
/* 1017 */     return getShortCanonicalName(paramObject.getClass().getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortCanonicalName(Class<?> paramClass)
/*      */   {
/* 1028 */     if (paramClass == null) {
/* 1029 */       return "";
/*      */     }
/* 1031 */     return getShortCanonicalName(paramClass.getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getShortCanonicalName(String paramString)
/*      */   {
/* 1044 */     return getShortClassName(getCanonicalName(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageCanonicalName(Object paramObject, String paramString)
/*      */   {
/* 1058 */     if (paramObject == null) {
/* 1059 */       return paramString;
/*      */     }
/* 1061 */     return getPackageCanonicalName(paramObject.getClass().getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageCanonicalName(Class<?> paramClass)
/*      */   {
/* 1072 */     if (paramClass == null) {
/* 1073 */       return "";
/*      */     }
/* 1075 */     return getPackageCanonicalName(paramClass.getName());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getPackageCanonicalName(String paramString)
/*      */   {
/* 1089 */     return getPackageName(getCanonicalName(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String getCanonicalName(String paramString)
/*      */   {
/* 1109 */     paramString = StringUtils.deleteWhitespace(paramString);
/* 1110 */     if (paramString == null) {
/* 1111 */       return null;
/*      */     }
/* 1113 */     int i = 0;
/* 1114 */     while (paramString.startsWith("[")) {
/* 1115 */       i++;
/* 1116 */       paramString = paramString.substring(1);
/*      */     }
/* 1118 */     if (i < 1) {
/* 1119 */       return paramString;
/*      */     }
/* 1121 */     if (paramString.startsWith("L")) {
/* 1122 */       paramString = paramString.substring(1, paramString.endsWith(";") ? paramString.length() - 1 : paramString.length());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/* 1128 */     else if (paramString.length() > 0) {
/* 1129 */       paramString = (String)reverseAbbreviationMap.get(paramString.substring(0, 1));
/*      */     }
/*      */     
/* 1132 */     StringBuilder localStringBuilder = new StringBuilder(paramString);
/* 1133 */     for (int j = 0; j < i; j++) {
/* 1134 */       localStringBuilder.append("[]");
/*      */     }
/* 1136 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterable<Class<?>> hierarchy(Class<?> paramClass)
/*      */   {
/* 1150 */     return hierarchy(paramClass, Interfaces.EXCLUDE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterable<Class<?>> hierarchy(Class<?> paramClass, Interfaces paramInterfaces)
/*      */   {
/* 1162 */     Iterable local1 = new Iterable()
/*      */     {
/*      */       public Iterator<Class<?>> iterator()
/*      */       {
/* 1166 */         final MutableObject localMutableObject = new MutableObject(this.val$type);
/* 1167 */         new Iterator()
/*      */         {
/*      */           public boolean hasNext()
/*      */           {
/* 1171 */             return localMutableObject.getValue() != null;
/*      */           }
/*      */           
/*      */           public Class<?> next()
/*      */           {
/* 1176 */             Class localClass = (Class)localMutableObject.getValue();
/* 1177 */             localMutableObject.setValue(localClass.getSuperclass());
/* 1178 */             return localClass;
/*      */           }
/*      */           
/*      */           public void remove()
/*      */           {
/* 1183 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */       }
/*      */     };
/*      */     
/*      */ 
/* 1190 */     if (paramInterfaces != Interfaces.INCLUDE) {
/* 1191 */       return local1;
/*      */     }
/* 1193 */     new Iterable()
/*      */     {
/*      */       public Iterator<Class<?>> iterator()
/*      */       {
/* 1197 */         final HashSet localHashSet = new HashSet();
/* 1198 */         final Iterator localIterator = this.val$classes.iterator();
/*      */         
/* 1200 */         new Iterator() {
/* 1201 */           Iterator<Class<?>> interfaces = Collections.emptySet().iterator();
/*      */           
/*      */           public boolean hasNext()
/*      */           {
/* 1205 */             return (this.interfaces.hasNext()) || (localIterator.hasNext());
/*      */           }
/*      */           
/*      */           public Class<?> next()
/*      */           {
/* 1210 */             if (this.interfaces.hasNext()) {
/* 1211 */               localClass = (Class)this.interfaces.next();
/* 1212 */               localHashSet.add(localClass);
/* 1213 */               return localClass;
/*      */             }
/* 1215 */             Class localClass = (Class)localIterator.next();
/* 1216 */             LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/* 1217 */             walkInterfaces(localLinkedHashSet, localClass);
/* 1218 */             this.interfaces = localLinkedHashSet.iterator();
/* 1219 */             return localClass;
/*      */           }
/*      */           
/*      */           private void walkInterfaces(Set<Class<?>> paramAnonymous2Set, Class<?> paramAnonymous2Class) {
/* 1223 */             for (Class localClass : paramAnonymous2Class.getInterfaces()) {
/* 1224 */               if (!localHashSet.contains(localClass)) {
/* 1225 */                 paramAnonymous2Set.add(localClass);
/*      */               }
/* 1227 */               walkInterfaces(paramAnonymous2Set, localClass);
/*      */             }
/*      */           }
/*      */           
/*      */           public void remove()
/*      */           {
/* 1233 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */       }
/*      */     };
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\ClassUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */