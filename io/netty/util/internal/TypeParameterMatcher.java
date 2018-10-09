/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TypeParameterMatcher
/*     */ {
/*  29 */   private static final TypeParameterMatcher NOOP = new NoOpTypeParameterMatcher();
/*  30 */   private static final Object TEST_OBJECT = new Object();
/*     */   
/*     */   public static TypeParameterMatcher get(Class<?> paramClass) {
/*  33 */     Map localMap = InternalThreadLocalMap.get().typeParameterMatcherGetCache();
/*     */     
/*     */ 
/*  36 */     Object localObject = (TypeParameterMatcher)localMap.get(paramClass);
/*  37 */     if (localObject == null) {
/*  38 */       if (paramClass == Object.class) {
/*  39 */         localObject = NOOP;
/*  40 */       } else if (PlatformDependent.hasJavassist()) {
/*     */         try {
/*  42 */           localObject = JavassistTypeParameterMatcherGenerator.generate(paramClass);
/*  43 */           ((TypeParameterMatcher)localObject).match(TEST_OBJECT);
/*     */         }
/*     */         catch (IllegalAccessError localIllegalAccessError) {
/*  46 */           localObject = null;
/*     */         }
/*     */         catch (Exception localException) {
/*  49 */           localObject = null;
/*     */         }
/*     */       }
/*     */       
/*  53 */       if (localObject == null) {
/*  54 */         localObject = new ReflectiveMatcher(paramClass);
/*     */       }
/*     */       
/*  57 */       localMap.put(paramClass, localObject);
/*     */     }
/*     */     
/*  60 */     return (TypeParameterMatcher)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public static TypeParameterMatcher find(Object paramObject, Class<?> paramClass, String paramString)
/*     */   {
/*  66 */     Map localMap = InternalThreadLocalMap.get().typeParameterMatcherFindCache();
/*     */     
/*  68 */     Class localClass = paramObject.getClass();
/*     */     
/*  70 */     Object localObject = (Map)localMap.get(localClass);
/*  71 */     if (localObject == null) {
/*  72 */       localObject = new HashMap();
/*  73 */       localMap.put(localClass, localObject);
/*     */     }
/*     */     
/*  76 */     TypeParameterMatcher localTypeParameterMatcher = (TypeParameterMatcher)((Map)localObject).get(paramString);
/*  77 */     if (localTypeParameterMatcher == null) {
/*  78 */       localTypeParameterMatcher = get(find0(paramObject, paramClass, paramString));
/*  79 */       ((Map)localObject).put(paramString, localTypeParameterMatcher);
/*     */     }
/*     */     
/*  82 */     return localTypeParameterMatcher;
/*     */   }
/*     */   
/*     */ 
/*     */   private static Class<?> find0(Object paramObject, Class<?> paramClass, String paramString)
/*     */   {
/*  88 */     Class localClass1 = paramObject.getClass();
/*  89 */     Class localClass2 = localClass1;
/*     */     do {
/*  91 */       while (localClass2.getSuperclass() == paramClass) {
/*  92 */         int i = -1;
/*  93 */         TypeVariable[] arrayOfTypeVariable = localClass2.getSuperclass().getTypeParameters();
/*  94 */         for (int j = 0; j < arrayOfTypeVariable.length; j++) {
/*  95 */           if (paramString.equals(arrayOfTypeVariable[j].getName())) {
/*  96 */             i = j;
/*  97 */             break;
/*     */           }
/*     */         }
/*     */         
/* 101 */         if (i < 0) {
/* 102 */           throw new IllegalStateException("unknown type parameter '" + paramString + "': " + paramClass);
/*     */         }
/*     */         
/*     */ 
/* 106 */         Type localType1 = localClass2.getGenericSuperclass();
/* 107 */         if (!(localType1 instanceof ParameterizedType)) {
/* 108 */           return Object.class;
/*     */         }
/*     */         
/* 111 */         Type[] arrayOfType = ((ParameterizedType)localType1).getActualTypeArguments();
/*     */         
/* 113 */         Type localType2 = arrayOfType[i];
/* 114 */         if ((localType2 instanceof ParameterizedType)) {
/* 115 */           localType2 = ((ParameterizedType)localType2).getRawType();
/*     */         }
/* 117 */         if ((localType2 instanceof Class))
/* 118 */           return (Class)localType2;
/*     */         Object localObject;
/* 120 */         if ((localType2 instanceof GenericArrayType)) {
/* 121 */           localObject = ((GenericArrayType)localType2).getGenericComponentType();
/* 122 */           if ((localObject instanceof ParameterizedType)) {
/* 123 */             localObject = ((ParameterizedType)localObject).getRawType();
/*     */           }
/* 125 */           if ((localObject instanceof Class)) {
/* 126 */             return Array.newInstance((Class)localObject, 0).getClass();
/*     */           }
/*     */         }
/* 129 */         if ((localType2 instanceof TypeVariable))
/*     */         {
/* 131 */           localObject = (TypeVariable)localType2;
/* 132 */           localClass2 = localClass1;
/* 133 */           if (!(((TypeVariable)localObject).getGenericDeclaration() instanceof Class)) {
/* 134 */             return Object.class;
/*     */           }
/*     */           
/* 137 */           paramClass = (Class)((TypeVariable)localObject).getGenericDeclaration();
/* 138 */           paramString = ((TypeVariable)localObject).getName();
/* 139 */           if (!paramClass.isAssignableFrom(localClass1))
/*     */           {
/*     */ 
/* 142 */             return Object.class;
/*     */           }
/*     */         }
/*     */         else {
/* 146 */           return fail(localClass1, paramString);
/*     */         } }
/* 148 */       localClass2 = localClass2.getSuperclass();
/* 149 */     } while (localClass2 != null);
/* 150 */     return fail(localClass1, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */   private static Class<?> fail(Class<?> paramClass, String paramString)
/*     */   {
/* 156 */     throw new IllegalStateException("cannot determine the type of the type parameter '" + paramString + "': " + paramClass);
/*     */   }
/*     */   
/*     */   public abstract boolean match(Object paramObject);
/*     */   
/*     */   private static final class ReflectiveMatcher extends TypeParameterMatcher
/*     */   {
/*     */     private final Class<?> type;
/*     */     
/*     */     ReflectiveMatcher(Class<?> paramClass) {
/* 166 */       this.type = paramClass;
/*     */     }
/*     */     
/*     */     public boolean match(Object paramObject)
/*     */     {
/* 171 */       return this.type.isInstance(paramObject);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\TypeParameterMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */