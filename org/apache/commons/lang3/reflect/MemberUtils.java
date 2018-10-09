/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Member;
/*     */ import java.lang.reflect.Modifier;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MemberUtils
/*     */ {
/*     */   private static final int ACCESS_TEST = 7;
/*  38 */   private static final Class<?>[] ORDERED_PRIMITIVE_TYPES = { Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean setAccessibleWorkaround(AccessibleObject paramAccessibleObject)
/*     */   {
/*  55 */     if ((paramAccessibleObject == null) || (paramAccessibleObject.isAccessible())) {
/*  56 */       return false;
/*     */     }
/*  58 */     Member localMember = (Member)paramAccessibleObject;
/*  59 */     if ((!paramAccessibleObject.isAccessible()) && (Modifier.isPublic(localMember.getModifiers())) && (isPackageAccess(localMember.getDeclaringClass().getModifiers()))) {
/*     */       try {
/*  61 */         paramAccessibleObject.setAccessible(true);
/*  62 */         return true;
/*     */       }
/*     */       catch (SecurityException localSecurityException) {}
/*     */     }
/*     */     
/*  67 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isPackageAccess(int paramInt)
/*     */   {
/*  76 */     return (paramInt & 0x7) == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static boolean isAccessible(Member paramMember)
/*     */   {
/*  85 */     return (paramMember != null) && (Modifier.isPublic(paramMember.getModifiers())) && (!paramMember.isSynthetic());
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
/*     */   static int compareParameterTypes(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2, Class<?>[] paramArrayOfClass3)
/*     */   {
/* 101 */     float f1 = getTotalTransformationCost(paramArrayOfClass3, paramArrayOfClass1);
/* 102 */     float f2 = getTotalTransformationCost(paramArrayOfClass3, paramArrayOfClass2);
/* 103 */     return f2 < f1 ? 1 : f1 < f2 ? -1 : 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static float getTotalTransformationCost(Class<?>[] paramArrayOfClass1, Class<?>[] paramArrayOfClass2)
/*     */   {
/* 114 */     float f = 0.0F;
/* 115 */     for (int i = 0; i < paramArrayOfClass1.length; i++)
/*     */     {
/* 117 */       Class<?> localClass1 = paramArrayOfClass1[i];
/* 118 */       Class<?> localClass2 = paramArrayOfClass2[i];
/* 119 */       f += getObjectTransformationCost(localClass1, localClass2);
/*     */     }
/* 121 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static float getObjectTransformationCost(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/* 133 */     if (paramClass2.isPrimitive()) {
/* 134 */       return getPrimitivePromotionCost(paramClass1, paramClass2);
/*     */     }
/* 136 */     float f = 0.0F;
/* 137 */     while ((paramClass1 != null) && (!paramClass2.equals(paramClass1))) {
/* 138 */       if ((paramClass2.isInterface()) && (ClassUtils.isAssignable(paramClass1, paramClass2)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 144 */         f += 0.25F;
/* 145 */         break;
/*     */       }
/* 147 */       f += 1.0F;
/* 148 */       paramClass1 = paramClass1.getSuperclass();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 154 */     if (paramClass1 == null) {
/* 155 */       f += 1.5F;
/*     */     }
/* 157 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static float getPrimitivePromotionCost(Class<?> paramClass1, Class<?> paramClass2)
/*     */   {
/* 168 */     float f = 0.0F;
/* 169 */     Object localObject = paramClass1;
/* 170 */     if (!((Class)localObject).isPrimitive())
/*     */     {
/* 172 */       f += 0.1F;
/* 173 */       localObject = ClassUtils.wrapperToPrimitive((Class)localObject);
/*     */     }
/* 175 */     for (int i = 0; (localObject != paramClass2) && (i < ORDERED_PRIMITIVE_TYPES.length); i++) {
/* 176 */       if (localObject == ORDERED_PRIMITIVE_TYPES[i]) {
/* 177 */         f += 0.1F;
/* 178 */         if (i < ORDERED_PRIMITIVE_TYPES.length - 1) {
/* 179 */           localObject = ORDERED_PRIMITIVE_TYPES[(i + 1)];
/*     */         }
/*     */       }
/*     */     }
/* 183 */     return f;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\MemberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */