/*      */ package org.apache.commons.lang3.reflect;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.GenericArrayType;
/*      */ import java.lang.reflect.GenericDeclaration;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.lang.reflect.WildcardType;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.commons.lang3.builder.Builder;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TypeUtils
/*      */ {
/*      */   public static class WildcardTypeBuilder
/*      */     implements Builder<WildcardType>
/*      */   {
/*      */     private Type[] upperBounds;
/*      */     private Type[] lowerBounds;
/*      */     
/*      */     public WildcardTypeBuilder withUpperBounds(Type... paramVarArgs)
/*      */     {
/*   69 */       this.upperBounds = paramVarArgs;
/*   70 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public WildcardTypeBuilder withLowerBounds(Type... paramVarArgs)
/*      */     {
/*   79 */       this.lowerBounds = paramVarArgs;
/*   80 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public WildcardType build()
/*      */     {
/*   88 */       return new TypeUtils.WildcardTypeImpl(this.upperBounds, this.lowerBounds, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final class GenericArrayTypeImpl
/*      */     implements GenericArrayType
/*      */   {
/*      */     private final Type componentType;
/*      */     
/*      */ 
/*      */ 
/*      */     private GenericArrayTypeImpl(Type paramType)
/*      */     {
/*  104 */       this.componentType = paramType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type getGenericComponentType()
/*      */     {
/*  112 */       return this.componentType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  120 */       return TypeUtils.toString(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*  128 */       return (paramObject == this) || (((paramObject instanceof GenericArrayType)) && (TypeUtils.equals(this, (GenericArrayType)paramObject)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  136 */       int i = 1072;
/*  137 */       i |= this.componentType.hashCode();
/*  138 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class ParameterizedTypeImpl
/*      */     implements ParameterizedType
/*      */   {
/*      */     private final Class<?> raw;
/*      */     
/*      */ 
/*      */     private final Type useOwner;
/*      */     
/*      */ 
/*      */     private final Type[] typeArguments;
/*      */     
/*      */ 
/*      */     private ParameterizedTypeImpl(Class<?> paramClass, Type paramType, Type[] paramArrayOfType)
/*      */     {
/*  158 */       this.raw = paramClass;
/*  159 */       this.useOwner = paramType;
/*  160 */       this.typeArguments = paramArrayOfType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type getRawType()
/*      */     {
/*  168 */       return this.raw;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type getOwnerType()
/*      */     {
/*  176 */       return this.useOwner;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type[] getActualTypeArguments()
/*      */     {
/*  184 */       return (Type[])this.typeArguments.clone();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  192 */       return TypeUtils.toString(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*  200 */       return (paramObject == this) || (((paramObject instanceof ParameterizedType)) && (TypeUtils.equals(this, (ParameterizedType)paramObject)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  209 */       int i = 1136;
/*  210 */       i |= this.raw.hashCode();
/*  211 */       i <<= 4;
/*  212 */       i |= ObjectUtils.hashCode(this.useOwner);
/*  213 */       i <<= 8;
/*  214 */       i |= Arrays.hashCode(this.typeArguments);
/*  215 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static final class WildcardTypeImpl
/*      */     implements WildcardType
/*      */   {
/*  224 */     private static final Type[] EMPTY_BOUNDS = new Type[0];
/*      */     
/*      */ 
/*      */     private final Type[] upperBounds;
/*      */     
/*      */ 
/*      */     private final Type[] lowerBounds;
/*      */     
/*      */ 
/*      */     private WildcardTypeImpl(Type[] paramArrayOfType1, Type[] paramArrayOfType2)
/*      */     {
/*  235 */       this.upperBounds = ((Type[])ObjectUtils.defaultIfNull(paramArrayOfType1, EMPTY_BOUNDS));
/*  236 */       this.lowerBounds = ((Type[])ObjectUtils.defaultIfNull(paramArrayOfType2, EMPTY_BOUNDS));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type[] getUpperBounds()
/*      */     {
/*  244 */       return (Type[])this.upperBounds.clone();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public Type[] getLowerBounds()
/*      */     {
/*  252 */       return (Type[])this.lowerBounds.clone();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/*  260 */       return TypeUtils.toString(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean equals(Object paramObject)
/*      */     {
/*  268 */       return (paramObject == this) || (((paramObject instanceof WildcardType)) && (TypeUtils.equals(this, (WildcardType)paramObject)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  276 */       int i = 18688;
/*  277 */       i |= Arrays.hashCode(this.upperBounds);
/*  278 */       i <<= 8;
/*  279 */       i |= Arrays.hashCode(this.lowerBounds);
/*  280 */       return i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  288 */   public static final WildcardType WILDCARD_ALL = wildcardType().withUpperBounds(new Type[] { Object.class }).build();
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
/*      */   public static boolean isAssignable(Type paramType1, Type paramType2)
/*      */   {
/*  312 */     return isAssignable(paramType1, paramType2, null);
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
/*      */   private static boolean isAssignable(Type paramType1, Type paramType2, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  326 */     if ((paramType2 == null) || ((paramType2 instanceof Class))) {
/*  327 */       return isAssignable(paramType1, (Class)paramType2);
/*      */     }
/*      */     
/*  330 */     if ((paramType2 instanceof ParameterizedType)) {
/*  331 */       return isAssignable(paramType1, (ParameterizedType)paramType2, paramMap);
/*      */     }
/*      */     
/*  334 */     if ((paramType2 instanceof GenericArrayType)) {
/*  335 */       return isAssignable(paramType1, (GenericArrayType)paramType2, paramMap);
/*      */     }
/*      */     
/*  338 */     if ((paramType2 instanceof WildcardType)) {
/*  339 */       return isAssignable(paramType1, (WildcardType)paramType2, paramMap);
/*      */     }
/*      */     
/*  342 */     if ((paramType2 instanceof TypeVariable)) {
/*  343 */       return isAssignable(paramType1, (TypeVariable)paramType2, paramMap);
/*      */     }
/*      */     
/*  346 */     throw new IllegalStateException("found an unhandled type: " + paramType2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean isAssignable(Type paramType, Class<?> paramClass)
/*      */   {
/*  358 */     if (paramType == null)
/*      */     {
/*  360 */       return (paramClass == null) || (!paramClass.isPrimitive());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  365 */     if (paramClass == null) {
/*  366 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  370 */     if (paramClass.equals(paramType)) {
/*  371 */       return true;
/*      */     }
/*      */     
/*  374 */     if ((paramType instanceof Class))
/*      */     {
/*  376 */       return ClassUtils.isAssignable((Class)paramType, paramClass);
/*      */     }
/*      */     
/*  379 */     if ((paramType instanceof ParameterizedType))
/*      */     {
/*  381 */       return isAssignable(getRawType((ParameterizedType)paramType), paramClass);
/*      */     }
/*      */     
/*      */ 
/*  385 */     if ((paramType instanceof TypeVariable))
/*      */     {
/*      */ 
/*  388 */       for (Type localType : ((TypeVariable)paramType).getBounds()) {
/*  389 */         if (isAssignable(localType, paramClass)) {
/*  390 */           return true;
/*      */         }
/*      */       }
/*      */       
/*  394 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  399 */     if ((paramType instanceof GenericArrayType)) {
/*  400 */       return (paramClass.equals(Object.class)) || ((paramClass.isArray()) && (isAssignable(((GenericArrayType)paramType).getGenericComponentType(), paramClass.getComponentType())));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  408 */     if ((paramType instanceof WildcardType)) {
/*  409 */       return false;
/*      */     }
/*      */     
/*  412 */     throw new IllegalStateException("found an unhandled type: " + paramType);
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
/*      */   private static boolean isAssignable(Type paramType, ParameterizedType paramParameterizedType, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  426 */     if (paramType == null) {
/*  427 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  432 */     if (paramParameterizedType == null) {
/*  433 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  437 */     if (paramParameterizedType.equals(paramType)) {
/*  438 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  442 */     Class localClass = getRawType(paramParameterizedType);
/*      */     
/*      */ 
/*  445 */     Map localMap1 = getTypeArguments(paramType, localClass, null);
/*      */     
/*      */ 
/*  448 */     if (localMap1 == null) {
/*  449 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  455 */     if (localMap1.isEmpty()) {
/*  456 */       return true;
/*      */     }
/*      */     
/*      */ 
/*  460 */     Map localMap2 = getTypeArguments(paramParameterizedType, localClass, paramMap);
/*      */     
/*      */ 
/*      */ 
/*  464 */     for (TypeVariable localTypeVariable : localMap2.keySet()) {
/*  465 */       Type localType1 = unrollVariableAssignments(localTypeVariable, localMap2);
/*  466 */       Type localType2 = unrollVariableAssignments(localTypeVariable, localMap1);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  471 */       if ((localType2 != null) && (!localType1.equals(localType2)) && ((!(localType1 instanceof WildcardType)) || (!isAssignable(localType2, localType1, paramMap))))
/*      */       {
/*      */ 
/*      */ 
/*  475 */         return false;
/*      */       }
/*      */     }
/*  478 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Type unrollVariableAssignments(TypeVariable<?> paramTypeVariable, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*      */     Type localType;
/*      */     
/*      */ 
/*      */ 
/*      */     for (;;)
/*      */     {
/*  492 */       localType = (Type)paramMap.get(paramTypeVariable);
/*  493 */       if ((!(localType instanceof TypeVariable)) || (localType.equals(paramTypeVariable))) break;
/*  494 */       paramTypeVariable = (TypeVariable)localType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  499 */     return localType;
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
/*      */   private static boolean isAssignable(Type paramType, GenericArrayType paramGenericArrayType, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  514 */     if (paramType == null) {
/*  515 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  520 */     if (paramGenericArrayType == null) {
/*  521 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  525 */     if (paramGenericArrayType.equals(paramType)) {
/*  526 */       return true;
/*      */     }
/*      */     
/*  529 */     Type localType1 = paramGenericArrayType.getGenericComponentType();
/*      */     Object localObject;
/*  531 */     if ((paramType instanceof Class)) {
/*  532 */       localObject = (Class)paramType;
/*      */       
/*      */ 
/*  535 */       return (((Class)localObject).isArray()) && (isAssignable(((Class)localObject).getComponentType(), localType1, paramMap));
/*      */     }
/*      */     
/*      */ 
/*  539 */     if ((paramType instanceof GenericArrayType))
/*      */     {
/*  541 */       return isAssignable(((GenericArrayType)paramType).getGenericComponentType(), localType1, paramMap);
/*      */     }
/*      */     
/*      */     Type localType2;
/*  545 */     if ((paramType instanceof WildcardType))
/*      */     {
/*  547 */       for (localType2 : getImplicitUpperBounds((WildcardType)paramType)) {
/*  548 */         if (isAssignable(localType2, paramGenericArrayType)) {
/*  549 */           return true;
/*      */         }
/*      */       }
/*      */       
/*  553 */       return false;
/*      */     }
/*      */     
/*  556 */     if ((paramType instanceof TypeVariable))
/*      */     {
/*      */ 
/*  559 */       for (localType2 : getImplicitBounds((TypeVariable)paramType)) {
/*  560 */         if (isAssignable(localType2, paramGenericArrayType)) {
/*  561 */           return true;
/*      */         }
/*      */       }
/*      */       
/*  565 */       return false;
/*      */     }
/*      */     
/*  568 */     if ((paramType instanceof ParameterizedType))
/*      */     {
/*      */ 
/*      */ 
/*  572 */       return false;
/*      */     }
/*      */     
/*  575 */     throw new IllegalStateException("found an unhandled type: " + paramType);
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
/*      */   private static boolean isAssignable(Type paramType, WildcardType paramWildcardType, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  590 */     if (paramType == null) {
/*  591 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  596 */     if (paramWildcardType == null) {
/*  597 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  601 */     if (paramWildcardType.equals(paramType)) {
/*  602 */       return true;
/*      */     }
/*      */     
/*  605 */     Type[] arrayOfType1 = getImplicitUpperBounds(paramWildcardType);
/*  606 */     Type[] arrayOfType2 = getImplicitLowerBounds(paramWildcardType);
/*      */     Object localObject;
/*  608 */     if ((paramType instanceof WildcardType)) {
/*  609 */       localObject = (WildcardType)paramType;
/*  610 */       Type[] arrayOfType3 = getImplicitUpperBounds((WildcardType)localObject);
/*  611 */       Type[] arrayOfType4 = getImplicitLowerBounds((WildcardType)localObject);
/*      */       Type localType1;
/*  613 */       Type localType2; for (localType1 : arrayOfType1)
/*      */       {
/*      */ 
/*  616 */         localType1 = substituteTypeVariables(localType1, paramMap);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  621 */         for (localType2 : arrayOfType3) {
/*  622 */           if (!isAssignable(localType2, localType1, paramMap)) {
/*  623 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  628 */       for (localType1 : arrayOfType2)
/*      */       {
/*      */ 
/*  631 */         localType1 = substituteTypeVariables(localType1, paramMap);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  636 */         for (localType2 : arrayOfType4) {
/*  637 */           if (!isAssignable(localType1, localType2, paramMap)) {
/*  638 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*  642 */       return true;
/*      */     }
/*      */     
/*  645 */     for (??? : arrayOfType1)
/*      */     {
/*      */ 
/*  648 */       if (!isAssignable(paramType, substituteTypeVariables(???, paramMap), paramMap))
/*      */       {
/*  650 */         return false;
/*      */       }
/*      */     }
/*      */     
/*  654 */     for (??? : arrayOfType2)
/*      */     {
/*      */ 
/*  657 */       if (!isAssignable(substituteTypeVariables(???, paramMap), paramType, paramMap))
/*      */       {
/*  659 */         return false;
/*      */       }
/*      */     }
/*  662 */     return true;
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
/*      */   private static boolean isAssignable(Type paramType, TypeVariable<?> paramTypeVariable, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  677 */     if (paramType == null) {
/*  678 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  683 */     if (paramTypeVariable == null) {
/*  684 */       return false;
/*      */     }
/*      */     
/*      */ 
/*  688 */     if (paramTypeVariable.equals(paramType)) {
/*  689 */       return true;
/*      */     }
/*      */     
/*  692 */     if ((paramType instanceof TypeVariable))
/*      */     {
/*      */ 
/*      */ 
/*  696 */       Type[] arrayOfType1 = getImplicitBounds((TypeVariable)paramType);
/*      */       
/*  698 */       for (Type localType : arrayOfType1) {
/*  699 */         if (isAssignable(localType, paramTypeVariable, paramMap)) {
/*  700 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  705 */     if (((paramType instanceof Class)) || ((paramType instanceof ParameterizedType)) || ((paramType instanceof GenericArrayType)) || ((paramType instanceof WildcardType)))
/*      */     {
/*  707 */       return false;
/*      */     }
/*      */     
/*  710 */     throw new IllegalStateException("found an unhandled type: " + paramType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Type substituteTypeVariables(Type paramType, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  722 */     if (((paramType instanceof TypeVariable)) && (paramMap != null)) {
/*  723 */       Type localType = (Type)paramMap.get(paramType);
/*      */       
/*  725 */       if (localType == null) {
/*  726 */         throw new IllegalArgumentException("missing assignment type for type variable " + paramType);
/*      */       }
/*      */       
/*  729 */       return localType;
/*      */     }
/*  731 */     return paramType;
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
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType paramParameterizedType)
/*      */   {
/*  748 */     return getTypeArguments(paramParameterizedType, getRawType(paramParameterizedType), null);
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
/*      */   public static Map<TypeVariable<?>, Type> getTypeArguments(Type paramType, Class<?> paramClass)
/*      */   {
/*  784 */     return getTypeArguments(paramType, paramClass, null);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Type paramType, Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  797 */     if ((paramType instanceof Class)) {
/*  798 */       return getTypeArguments((Class)paramType, paramClass, paramMap);
/*      */     }
/*      */     
/*  801 */     if ((paramType instanceof ParameterizedType)) {
/*  802 */       return getTypeArguments((ParameterizedType)paramType, paramClass, paramMap);
/*      */     }
/*      */     
/*  805 */     if ((paramType instanceof GenericArrayType)) {
/*  806 */       return getTypeArguments(((GenericArrayType)paramType).getGenericComponentType(), paramClass.isArray() ? paramClass.getComponentType() : paramClass, paramMap);
/*      */     }
/*      */     
/*      */ 
/*      */     Type localType;
/*      */     
/*  812 */     if ((paramType instanceof WildcardType)) {
/*  813 */       for (localType : getImplicitUpperBounds((WildcardType)paramType))
/*      */       {
/*  815 */         if (isAssignable(localType, paramClass)) {
/*  816 */           return getTypeArguments(localType, paramClass, paramMap);
/*      */         }
/*      */       }
/*      */       
/*  820 */       return null;
/*      */     }
/*      */     
/*  823 */     if ((paramType instanceof TypeVariable)) {
/*  824 */       for (localType : getImplicitBounds((TypeVariable)paramType))
/*      */       {
/*  826 */         if (isAssignable(localType, paramClass)) {
/*  827 */           return getTypeArguments(localType, paramClass, paramMap);
/*      */         }
/*      */       }
/*      */       
/*  831 */       return null;
/*      */     }
/*  833 */     throw new IllegalStateException("found an unhandled type: " + paramType);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType paramParameterizedType, Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  847 */     Class localClass = getRawType(paramParameterizedType);
/*      */     
/*      */ 
/*  850 */     if (!isAssignable(localClass, paramClass)) {
/*  851 */       return null;
/*      */     }
/*      */     
/*  854 */     Type localType = paramParameterizedType.getOwnerType();
/*      */     
/*      */     Object localObject2;
/*  857 */     if ((localType instanceof ParameterizedType))
/*      */     {
/*  859 */       localObject1 = (ParameterizedType)localType;
/*  860 */       localObject2 = getTypeArguments((ParameterizedType)localObject1, getRawType((ParameterizedType)localObject1), paramMap);
/*      */     }
/*      */     else
/*      */     {
/*  864 */       localObject2 = paramMap == null ? new HashMap() : new HashMap(paramMap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  869 */     Object localObject1 = paramParameterizedType.getActualTypeArguments();
/*      */     
/*  871 */     TypeVariable[] arrayOfTypeVariable = localClass.getTypeParameters();
/*      */     
/*      */ 
/*  874 */     for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/*  875 */       Object localObject3 = localObject1[i];
/*  876 */       ((Map)localObject2).put(arrayOfTypeVariable[i], ((Map)localObject2).containsKey(localObject3) ? (Type)((Map)localObject2).get(localObject3) : localObject3);
/*      */     }
/*      */     
/*      */ 
/*  880 */     if (paramClass.equals(localClass))
/*      */     {
/*  882 */       return (Map<TypeVariable<?>, Type>)localObject2;
/*      */     }
/*      */     
/*      */ 
/*  886 */     return getTypeArguments(getClosestParentType(localClass, paramClass), paramClass, (Map)localObject2);
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
/*      */   private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> paramClass1, Class<?> paramClass2, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/*  900 */     if (!isAssignable(paramClass1, paramClass2)) {
/*  901 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  905 */     if (paramClass1.isPrimitive())
/*      */     {
/*  907 */       if (paramClass2.isPrimitive())
/*      */       {
/*      */ 
/*  910 */         return new HashMap();
/*      */       }
/*      */       
/*      */ 
/*  914 */       paramClass1 = ClassUtils.primitiveToWrapper(paramClass1);
/*      */     }
/*      */     
/*      */ 
/*  918 */     HashMap localHashMap = paramMap == null ? new HashMap() : new HashMap(paramMap);
/*      */     
/*      */ 
/*      */ 
/*  922 */     if (paramClass2.equals(paramClass1)) {
/*  923 */       return localHashMap;
/*      */     }
/*      */     
/*      */ 
/*  927 */     return getTypeArguments(getClosestParentType(paramClass1, paramClass2), paramClass2, localHashMap);
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
/*      */   public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> paramClass, ParameterizedType paramParameterizedType)
/*      */   {
/*  959 */     Validate.notNull(paramClass, "cls is null", new Object[0]);
/*  960 */     Validate.notNull(paramParameterizedType, "superType is null", new Object[0]);
/*      */     
/*  962 */     Class localClass1 = getRawType(paramParameterizedType);
/*      */     
/*      */ 
/*  965 */     if (!isAssignable(paramClass, localClass1)) {
/*  966 */       return null;
/*      */     }
/*      */     
/*  969 */     if (paramClass.equals(localClass1)) {
/*  970 */       return getTypeArguments(paramParameterizedType, localClass1, null);
/*      */     }
/*      */     
/*      */ 
/*  974 */     Type localType = getClosestParentType(paramClass, localClass1);
/*      */     
/*      */ 
/*  977 */     if ((localType instanceof Class)) {
/*  978 */       return determineTypeArguments((Class)localType, paramParameterizedType);
/*      */     }
/*      */     
/*  981 */     ParameterizedType localParameterizedType = (ParameterizedType)localType;
/*  982 */     Class localClass2 = getRawType(localParameterizedType);
/*      */     
/*      */ 
/*  985 */     Map localMap = determineTypeArguments(localClass2, paramParameterizedType);
/*      */     
/*  987 */     mapTypeVariablesToArguments(paramClass, localParameterizedType, localMap);
/*      */     
/*  989 */     return localMap;
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
/*      */   private static <T> void mapTypeVariablesToArguments(Class<T> paramClass, ParameterizedType paramParameterizedType, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/* 1003 */     Type localType1 = paramParameterizedType.getOwnerType();
/*      */     
/* 1005 */     if ((localType1 instanceof ParameterizedType))
/*      */     {
/* 1007 */       mapTypeVariablesToArguments(paramClass, (ParameterizedType)localType1, paramMap);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1014 */     Type[] arrayOfType = paramParameterizedType.getActualTypeArguments();
/*      */     
/*      */ 
/*      */ 
/* 1018 */     TypeVariable[] arrayOfTypeVariable = getRawType(paramParameterizedType).getTypeParameters();
/*      */     
/*      */ 
/* 1021 */     List localList = Arrays.asList(paramClass.getTypeParameters());
/*      */     
/*      */ 
/* 1024 */     for (int i = 0; i < arrayOfType.length; i++) {
/* 1025 */       TypeVariable localTypeVariable = arrayOfTypeVariable[i];
/* 1026 */       Type localType2 = arrayOfType[i];
/*      */       
/*      */ 
/* 1029 */       if ((localList.contains(localType2)) && (paramMap.containsKey(localTypeVariable)))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1034 */         paramMap.put((TypeVariable)localType2, paramMap.get(localTypeVariable));
/*      */       }
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
/*      */   private static Type getClosestParentType(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/* 1049 */     if (paramClass2.isInterface())
/*      */     {
/* 1051 */       Type[] arrayOfType1 = paramClass1.getGenericInterfaces();
/*      */       
/* 1053 */       Object localObject = null;
/*      */       
/*      */ 
/* 1056 */       for (Type localType : arrayOfType1) {
/* 1057 */         Class localClass = null;
/*      */         
/* 1059 */         if ((localType instanceof ParameterizedType)) {
/* 1060 */           localClass = getRawType((ParameterizedType)localType);
/* 1061 */         } else if ((localType instanceof Class)) {
/* 1062 */           localClass = (Class)localType;
/*      */         } else {
/* 1064 */           throw new IllegalStateException("Unexpected generic interface type found: " + localType);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1070 */         if ((isAssignable(localClass, paramClass2)) && (isAssignable((Type)localObject, localClass)))
/*      */         {
/* 1072 */           localObject = localType;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1077 */       if (localObject != null) {
/* 1078 */         return (Type)localObject;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1084 */     return paramClass1.getGenericSuperclass();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isInstance(Object paramObject, Type paramType)
/*      */   {
/* 1096 */     if (paramType == null) {
/* 1097 */       return false;
/*      */     }
/*      */     
/* 1100 */     return paramObject == null ? false : (!(paramType instanceof Class)) || (!((Class)paramType).isPrimitive()) ? true : isAssignable(paramObject.getClass(), paramType, null);
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
/*      */   public static Type[] normalizeUpperBounds(Type[] paramArrayOfType)
/*      */   {
/* 1126 */     Validate.notNull(paramArrayOfType, "null value specified for bounds array", new Object[0]);
/*      */     
/* 1128 */     if (paramArrayOfType.length < 2) {
/* 1129 */       return paramArrayOfType;
/*      */     }
/*      */     
/* 1132 */     HashSet localHashSet = new HashSet(paramArrayOfType.length);
/*      */     
/* 1134 */     for (Type localType1 : paramArrayOfType) {
/* 1135 */       int k = 0;
/*      */       
/* 1137 */       for (Type localType2 : paramArrayOfType) {
/* 1138 */         if ((localType1 != localType2) && (isAssignable(localType2, localType1, null))) {
/* 1139 */           k = 1;
/* 1140 */           break;
/*      */         }
/*      */       }
/*      */       
/* 1144 */       if (k == 0) {
/* 1145 */         localHashSet.add(localType1);
/*      */       }
/*      */     }
/*      */     
/* 1149 */     return (Type[])localHashSet.toArray(new Type[localHashSet.size()]);
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
/*      */   public static Type[] getImplicitBounds(TypeVariable<?> paramTypeVariable)
/*      */   {
/* 1162 */     Validate.notNull(paramTypeVariable, "typeVariable is null", new Object[0]);
/* 1163 */     Type[] arrayOfType = paramTypeVariable.getBounds();
/*      */     
/* 1165 */     return arrayOfType.length == 0 ? new Type[] { Object.class } : normalizeUpperBounds(arrayOfType);
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
/*      */   public static Type[] getImplicitUpperBounds(WildcardType paramWildcardType)
/*      */   {
/* 1179 */     Validate.notNull(paramWildcardType, "wildcardType is null", new Object[0]);
/* 1180 */     Type[] arrayOfType = paramWildcardType.getUpperBounds();
/*      */     
/* 1182 */     return arrayOfType.length == 0 ? new Type[] { Object.class } : normalizeUpperBounds(arrayOfType);
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
/*      */   public static Type[] getImplicitLowerBounds(WildcardType paramWildcardType)
/*      */   {
/* 1195 */     Validate.notNull(paramWildcardType, "wildcardType is null", new Object[0]);
/* 1196 */     Type[] arrayOfType = paramWildcardType.getLowerBounds();
/*      */     
/* 1198 */     return arrayOfType.length == 0 ? new Type[] { null } : arrayOfType;
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
/*      */   public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/* 1215 */     Validate.notNull(paramMap, "typeVarAssigns is null", new Object[0]);
/*      */     
/*      */ 
/* 1218 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 1219 */       TypeVariable localTypeVariable = (TypeVariable)localEntry.getKey();
/* 1220 */       Type localType1 = (Type)localEntry.getValue();
/*      */       
/* 1222 */       for (Type localType2 : getImplicitBounds(localTypeVariable)) {
/* 1223 */         if (!isAssignable(localType1, substituteTypeVariables(localType2, paramMap), paramMap))
/*      */         {
/* 1225 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1229 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Class<?> getRawType(ParameterizedType paramParameterizedType)
/*      */   {
/* 1240 */     Type localType = paramParameterizedType.getRawType();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1247 */     if (!(localType instanceof Class)) {
/* 1248 */       throw new IllegalStateException("Wait... What!? Type of rawType: " + localType);
/*      */     }
/*      */     
/* 1251 */     return (Class)localType;
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
/*      */   public static Class<?> getRawType(Type paramType1, Type paramType2)
/*      */   {
/* 1267 */     if ((paramType1 instanceof Class))
/*      */     {
/* 1269 */       return (Class)paramType1;
/*      */     }
/*      */     
/* 1272 */     if ((paramType1 instanceof ParameterizedType))
/*      */     {
/* 1274 */       return getRawType((ParameterizedType)paramType1);
/*      */     }
/*      */     Object localObject;
/* 1277 */     if ((paramType1 instanceof TypeVariable)) {
/* 1278 */       if (paramType2 == null) {
/* 1279 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1283 */       localObject = ((TypeVariable)paramType1).getGenericDeclaration();
/*      */       
/*      */ 
/*      */ 
/* 1287 */       if (!(localObject instanceof Class)) {
/* 1288 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1293 */       Map localMap = getTypeArguments(paramType2, (Class)localObject);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1298 */       if (localMap == null) {
/* 1299 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1303 */       Type localType = (Type)localMap.get(paramType1);
/*      */       
/* 1305 */       if (localType == null) {
/* 1306 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1310 */       return getRawType(localType, paramType2);
/*      */     }
/*      */     
/* 1313 */     if ((paramType1 instanceof GenericArrayType))
/*      */     {
/* 1315 */       localObject = getRawType(((GenericArrayType)paramType1).getGenericComponentType(), paramType2);
/*      */       
/*      */ 
/*      */ 
/* 1319 */       return Array.newInstance((Class)localObject, 0).getClass();
/*      */     }
/*      */     
/*      */ 
/* 1323 */     if ((paramType1 instanceof WildcardType)) {
/* 1324 */       return null;
/*      */     }
/*      */     
/* 1327 */     throw new IllegalArgumentException("unknown type: " + paramType1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isArrayType(Type paramType)
/*      */   {
/* 1336 */     return ((paramType instanceof GenericArrayType)) || (((paramType instanceof Class)) && (((Class)paramType).isArray()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Type getArrayComponentType(Type paramType)
/*      */   {
/* 1345 */     if ((paramType instanceof Class)) {
/* 1346 */       Class localClass = (Class)paramType;
/* 1347 */       return localClass.isArray() ? localClass.getComponentType() : null;
/*      */     }
/* 1349 */     if ((paramType instanceof GenericArrayType)) {
/* 1350 */       return ((GenericArrayType)paramType).getGenericComponentType();
/*      */     }
/* 1352 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Type unrollVariables(Map<TypeVariable<?>, Type> paramMap, Type paramType)
/*      */   {
/* 1364 */     if (paramMap == null) {
/* 1365 */       paramMap = Collections.emptyMap();
/*      */     }
/* 1367 */     if (containsTypeVariables(paramType)) {
/* 1368 */       if ((paramType instanceof TypeVariable))
/* 1369 */         return unrollVariables(paramMap, (Type)paramMap.get(paramType));
/*      */       Object localObject1;
/* 1371 */       if ((paramType instanceof ParameterizedType)) {
/* 1372 */         localObject1 = (ParameterizedType)paramType;
/*      */         Object localObject2;
/* 1374 */         if (((ParameterizedType)localObject1).getOwnerType() == null) {
/* 1375 */           localObject2 = paramMap;
/*      */         } else {
/* 1377 */           localObject2 = new HashMap(paramMap);
/* 1378 */           ((Map)localObject2).putAll(getTypeArguments((ParameterizedType)localObject1));
/*      */         }
/* 1380 */         Type[] arrayOfType = ((ParameterizedType)localObject1).getActualTypeArguments();
/* 1381 */         for (int i = 0; i < arrayOfType.length; i++) {
/* 1382 */           Type localType = unrollVariables((Map)localObject2, arrayOfType[i]);
/* 1383 */           if (localType != null) {
/* 1384 */             arrayOfType[i] = localType;
/*      */           }
/*      */         }
/* 1387 */         return parameterizeWithOwner(((ParameterizedType)localObject1).getOwnerType(), (Class)((ParameterizedType)localObject1).getRawType(), arrayOfType);
/*      */       }
/* 1389 */       if ((paramType instanceof WildcardType)) {
/* 1390 */         localObject1 = (WildcardType)paramType;
/* 1391 */         return wildcardType().withUpperBounds(unrollBounds(paramMap, ((WildcardType)localObject1).getUpperBounds())).withLowerBounds(unrollBounds(paramMap, ((WildcardType)localObject1).getLowerBounds())).build();
/*      */       }
/*      */     }
/*      */     
/* 1395 */     return paramType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Type[] unrollBounds(Map<TypeVariable<?>, Type> paramMap, Type[] paramArrayOfType)
/*      */   {
/* 1407 */     Type[] arrayOfType = paramArrayOfType;
/* 1408 */     for (int i = 0; 
/* 1409 */         i < arrayOfType.length; i++) {
/* 1410 */       Type localType = unrollVariables(paramMap, arrayOfType[i]);
/* 1411 */       if (localType == null) {
/* 1412 */         arrayOfType = (Type[])ArrayUtils.remove(arrayOfType, i--);
/*      */       } else {
/* 1414 */         arrayOfType[i] = localType;
/*      */       }
/*      */     }
/* 1417 */     return arrayOfType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean containsTypeVariables(Type paramType)
/*      */   {
/* 1428 */     if ((paramType instanceof TypeVariable)) {
/* 1429 */       return true;
/*      */     }
/* 1431 */     if ((paramType instanceof Class)) {
/* 1432 */       return ((Class)paramType).getTypeParameters().length > 0;
/*      */     }
/* 1434 */     if ((paramType instanceof ParameterizedType)) {
/* 1435 */       for (Type localType : ((ParameterizedType)paramType).getActualTypeArguments()) {
/* 1436 */         if (containsTypeVariables(localType)) {
/* 1437 */           return true;
/*      */         }
/*      */       }
/* 1440 */       return false;
/*      */     }
/* 1442 */     if ((paramType instanceof WildcardType)) {
/* 1443 */       ??? = (WildcardType)paramType;
/* 1444 */       return (containsTypeVariables(getImplicitLowerBounds(???)[0])) || (containsTypeVariables(getImplicitUpperBounds(???)[0]));
/*      */     }
/*      */     
/* 1447 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final ParameterizedType parameterize(Class<?> paramClass, Type... paramVarArgs)
/*      */   {
/* 1459 */     return parameterizeWithOwner(null, paramClass, paramVarArgs);
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
/*      */   public static final ParameterizedType parameterize(Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/* 1472 */     Validate.notNull(paramClass, "raw class is null", new Object[0]);
/* 1473 */     Validate.notNull(paramMap, "typeArgMappings is null", new Object[0]);
/* 1474 */     return parameterizeWithOwner(null, paramClass, extractTypeArgumentsFrom(paramMap, paramClass.getTypeParameters()));
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
/*      */   public static final ParameterizedType parameterizeWithOwner(Type paramType, Class<?> paramClass, Type... paramVarArgs)
/*      */   {
/* 1489 */     Validate.notNull(paramClass, "raw class is null", new Object[0]);
/*      */     Object localObject;
/* 1491 */     if (paramClass.getEnclosingClass() == null) {
/* 1492 */       Validate.isTrue(paramType == null, "no owner allowed for top-level %s", new Object[] { paramClass });
/* 1493 */       localObject = null;
/* 1494 */     } else if (paramType == null) {
/* 1495 */       localObject = paramClass.getEnclosingClass();
/*      */     } else {
/* 1497 */       Validate.isTrue(isAssignable(paramType, paramClass.getEnclosingClass()), "%s is invalid owner type for parameterized %s", new Object[] { paramType, paramClass });
/*      */       
/* 1499 */       localObject = paramType;
/*      */     }
/* 1501 */     Validate.noNullElements(paramVarArgs, "null type argument at index %s", new Object[0]);
/* 1502 */     Validate.isTrue(paramClass.getTypeParameters().length == paramVarArgs.length, "invalid number of type parameters specified: expected %s, got %s", new Object[] { Integer.valueOf(paramClass.getTypeParameters().length), Integer.valueOf(paramVarArgs.length) });
/*      */     
/*      */ 
/*      */ 
/* 1506 */     return new ParameterizedTypeImpl(paramClass, (Type)localObject, paramVarArgs, null);
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
/*      */   public static final ParameterizedType parameterizeWithOwner(Type paramType, Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
/*      */   {
/* 1520 */     Validate.notNull(paramClass, "raw class is null", new Object[0]);
/* 1521 */     Validate.notNull(paramMap, "typeArgMappings is null", new Object[0]);
/* 1522 */     return parameterizeWithOwner(paramType, paramClass, extractTypeArgumentsFrom(paramMap, paramClass.getTypeParameters()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Type[] extractTypeArgumentsFrom(Map<TypeVariable<?>, Type> paramMap, TypeVariable<?>[] paramArrayOfTypeVariable)
/*      */   {
/* 1532 */     Type[] arrayOfType = new Type[paramArrayOfTypeVariable.length];
/* 1533 */     int i = 0;
/* 1534 */     for (TypeVariable<?> localTypeVariable : paramArrayOfTypeVariable) {
/* 1535 */       Validate.isTrue(paramMap.containsKey(localTypeVariable), "missing argument mapping for %s", new Object[] { toString(localTypeVariable) });
/* 1536 */       arrayOfType[(i++)] = ((Type)paramMap.get(localTypeVariable));
/*      */     }
/* 1538 */     return arrayOfType;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static WildcardTypeBuilder wildcardType()
/*      */   {
/* 1547 */     return new WildcardTypeBuilder(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static GenericArrayType genericArrayType(Type paramType)
/*      */   {
/* 1559 */     return new GenericArrayTypeImpl((Type)Validate.notNull(paramType, "componentType is null", new Object[0]), null);
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
/*      */   public static boolean equals(Type paramType1, Type paramType2)
/*      */   {
/* 1572 */     if (ObjectUtils.equals(paramType1, paramType2)) {
/* 1573 */       return true;
/*      */     }
/* 1575 */     if ((paramType1 instanceof ParameterizedType)) {
/* 1576 */       return equals((ParameterizedType)paramType1, paramType2);
/*      */     }
/* 1578 */     if ((paramType1 instanceof GenericArrayType)) {
/* 1579 */       return equals((GenericArrayType)paramType1, paramType2);
/*      */     }
/* 1581 */     if ((paramType1 instanceof WildcardType)) {
/* 1582 */       return equals((WildcardType)paramType1, paramType2);
/*      */     }
/* 1584 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean equals(ParameterizedType paramParameterizedType, Type paramType)
/*      */   {
/* 1595 */     if ((paramType instanceof ParameterizedType)) {
/* 1596 */       ParameterizedType localParameterizedType = (ParameterizedType)paramType;
/* 1597 */       if ((equals(paramParameterizedType.getRawType(), localParameterizedType.getRawType())) && (equals(paramParameterizedType.getOwnerType(), localParameterizedType.getOwnerType()))) {
/* 1598 */         return equals(paramParameterizedType.getActualTypeArguments(), localParameterizedType.getActualTypeArguments());
/*      */       }
/*      */     }
/* 1601 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean equals(GenericArrayType paramGenericArrayType, Type paramType)
/*      */   {
/* 1612 */     return ((paramType instanceof GenericArrayType)) && (equals(paramGenericArrayType.getGenericComponentType(), ((GenericArrayType)paramType).getGenericComponentType()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean equals(WildcardType paramWildcardType, Type paramType)
/*      */   {
/* 1624 */     if ((paramType instanceof WildcardType)) {
/* 1625 */       WildcardType localWildcardType = (WildcardType)paramType;
/* 1626 */       return (equals(paramWildcardType.getLowerBounds(), localWildcardType.getLowerBounds())) && (equals(getImplicitUpperBounds(paramWildcardType), getImplicitUpperBounds(localWildcardType)));
/*      */     }
/*      */     
/* 1629 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean equals(Type[] paramArrayOfType1, Type[] paramArrayOfType2)
/*      */   {
/* 1640 */     if (paramArrayOfType1.length == paramArrayOfType2.length) {
/* 1641 */       for (int i = 0; i < paramArrayOfType1.length; i++) {
/* 1642 */         if (!equals(paramArrayOfType1[i], paramArrayOfType2[i])) {
/* 1643 */           return false;
/*      */         }
/*      */       }
/* 1646 */       return true;
/*      */     }
/* 1648 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toString(Type paramType)
/*      */   {
/* 1659 */     Validate.notNull(paramType);
/* 1660 */     if ((paramType instanceof Class)) {
/* 1661 */       return classToString((Class)paramType);
/*      */     }
/* 1663 */     if ((paramType instanceof ParameterizedType)) {
/* 1664 */       return parameterizedTypeToString((ParameterizedType)paramType);
/*      */     }
/* 1666 */     if ((paramType instanceof WildcardType)) {
/* 1667 */       return wildcardTypeToString((WildcardType)paramType);
/*      */     }
/* 1669 */     if ((paramType instanceof TypeVariable)) {
/* 1670 */       return typeVariableToString((TypeVariable)paramType);
/*      */     }
/* 1672 */     if ((paramType instanceof GenericArrayType)) {
/* 1673 */       return genericArrayTypeToString((GenericArrayType)paramType);
/*      */     }
/* 1675 */     throw new IllegalArgumentException(ObjectUtils.identityToString(paramType));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toLongString(TypeVariable<?> paramTypeVariable)
/*      */   {
/* 1686 */     Validate.notNull(paramTypeVariable, "var is null", new Object[0]);
/* 1687 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1688 */     GenericDeclaration localGenericDeclaration = paramTypeVariable.getGenericDeclaration();
/* 1689 */     if ((localGenericDeclaration instanceof Class)) {
/* 1690 */       Class localClass = (Class)localGenericDeclaration;
/*      */       for (;;) {
/* 1692 */         if (localClass.getEnclosingClass() == null) {
/* 1693 */           localStringBuilder.insert(0, localClass.getName());
/* 1694 */           break;
/*      */         }
/* 1696 */         localStringBuilder.insert(0, localClass.getSimpleName()).insert(0, '.');
/* 1697 */         localClass = localClass.getEnclosingClass();
/*      */       }
/* 1699 */     } else if ((localGenericDeclaration instanceof Type)) {
/* 1700 */       localStringBuilder.append(toString((Type)localGenericDeclaration));
/*      */     } else {
/* 1702 */       localStringBuilder.append(localGenericDeclaration);
/*      */     }
/* 1704 */     return ':' + typeVariableToString(paramTypeVariable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> Typed<T> wrap(Type paramType)
/*      */   {
/* 1716 */     new Typed()
/*      */     {
/*      */       public Type getType() {
/* 1719 */         return this.val$type;
/*      */       }
/*      */     };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> Typed<T> wrap(Class<T> paramClass)
/*      */   {
/* 1733 */     return wrap(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String classToString(Class<?> paramClass)
/*      */   {
/* 1743 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/* 1745 */     if (paramClass.getEnclosingClass() != null) {
/* 1746 */       localStringBuilder.append(classToString(paramClass.getEnclosingClass())).append('.').append(paramClass.getSimpleName());
/*      */     } else {
/* 1748 */       localStringBuilder.append(paramClass.getName());
/*      */     }
/* 1750 */     if (paramClass.getTypeParameters().length > 0) {
/* 1751 */       localStringBuilder.append('<');
/* 1752 */       appendAllTo(localStringBuilder, ", ", paramClass.getTypeParameters());
/* 1753 */       localStringBuilder.append('>');
/*      */     }
/* 1755 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String typeVariableToString(TypeVariable<?> paramTypeVariable)
/*      */   {
/* 1765 */     StringBuilder localStringBuilder = new StringBuilder(paramTypeVariable.getName());
/* 1766 */     Type[] arrayOfType = paramTypeVariable.getBounds();
/* 1767 */     if ((arrayOfType.length > 0) && ((arrayOfType.length != 1) || (!Object.class.equals(arrayOfType[0])))) {
/* 1768 */       localStringBuilder.append(" extends ");
/* 1769 */       appendAllTo(localStringBuilder, " & ", paramTypeVariable.getBounds());
/*      */     }
/* 1771 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String parameterizedTypeToString(ParameterizedType paramParameterizedType)
/*      */   {
/* 1781 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */     
/* 1783 */     Type localType = paramParameterizedType.getOwnerType();
/* 1784 */     Class localClass = (Class)paramParameterizedType.getRawType();
/* 1785 */     Type[] arrayOfType = paramParameterizedType.getActualTypeArguments();
/* 1786 */     if (localType == null) {
/* 1787 */       localStringBuilder.append(localClass.getName());
/*      */     } else {
/* 1789 */       if ((localType instanceof Class)) {
/* 1790 */         localStringBuilder.append(((Class)localType).getName());
/*      */       } else {
/* 1792 */         localStringBuilder.append(localType.toString());
/*      */       }
/* 1794 */       localStringBuilder.append('.').append(localClass.getSimpleName());
/*      */     }
/*      */     
/* 1797 */     appendAllTo(localStringBuilder.append('<'), ", ", arrayOfType).append('>');
/* 1798 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String wildcardTypeToString(WildcardType paramWildcardType)
/*      */   {
/* 1808 */     StringBuilder localStringBuilder = new StringBuilder().append('?');
/* 1809 */     Type[] arrayOfType1 = paramWildcardType.getLowerBounds();
/* 1810 */     Type[] arrayOfType2 = paramWildcardType.getUpperBounds();
/* 1811 */     if (arrayOfType1.length > 0) {
/* 1812 */       appendAllTo(localStringBuilder.append(" super "), " & ", arrayOfType1);
/* 1813 */     } else if ((arrayOfType2.length != 1) || (!Object.class.equals(arrayOfType2[0]))) {
/* 1814 */       appendAllTo(localStringBuilder.append(" extends "), " & ", arrayOfType2);
/*      */     }
/* 1816 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String genericArrayTypeToString(GenericArrayType paramGenericArrayType)
/*      */   {
/* 1826 */     return String.format("%s[]", new Object[] { toString(paramGenericArrayType.getGenericComponentType()) });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static StringBuilder appendAllTo(StringBuilder paramStringBuilder, String paramString, Type... paramVarArgs)
/*      */   {
/* 1838 */     Validate.notEmpty(Validate.noNullElements(paramVarArgs));
/* 1839 */     if (paramVarArgs.length > 0) {
/* 1840 */       paramStringBuilder.append(toString(paramVarArgs[0]));
/* 1841 */       for (int i = 1; i < paramVarArgs.length; i++) {
/* 1842 */         paramStringBuilder.append(paramString).append(toString(paramVarArgs[i]));
/*      */       }
/*      */     }
/* 1845 */     return paramStringBuilder;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\TypeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */