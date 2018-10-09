/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ public class FieldUtils
/*     */ {
/*     */   public static Field getField(Class<?> paramClass, String paramString)
/*     */   {
/*  62 */     Field localField = getField(paramClass, paramString, false);
/*  63 */     MemberUtils.setAccessibleWorkaround(localField);
/*  64 */     return localField;
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
/*     */   public static Field getField(Class<?> paramClass, String paramString, boolean paramBoolean)
/*     */   {
/*  85 */     Validate.isTrue(paramClass != null, "The class must not be null", new Object[0]);
/*  86 */     Validate.isTrue(StringUtils.isNotBlank(paramString), "The field name must not be blank/empty", new Object[0]);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */     for (Object localObject = paramClass; localObject != null; localObject = ((Class)localObject).getSuperclass()) {
/*     */       try {
/* 103 */         Field localField1 = ((Class)localObject).getDeclaredField(paramString);
/*     */         
/*     */ 
/* 106 */         if (!Modifier.isPublic(localField1.getModifiers())) {
/* 107 */           if (paramBoolean) {
/* 108 */             localField1.setAccessible(true);
/*     */           } else {
/*     */             continue;
/*     */           }
/*     */         }
/* 113 */         return localField1;
/*     */       }
/*     */       catch (NoSuchFieldException localNoSuchFieldException1) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 121 */     localObject = null;
/* 122 */     for (Class localClass : ClassUtils.getAllInterfaces(paramClass)) {
/*     */       try {
/* 124 */         Field localField2 = localClass.getField(paramString);
/* 125 */         Validate.isTrue(localObject == null, "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", new Object[] { paramString, paramClass });
/*     */         
/* 127 */         localObject = localField2;
/*     */       }
/*     */       catch (NoSuchFieldException localNoSuchFieldException2) {}
/*     */     }
/*     */     
/* 132 */     return (Field)localObject;
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
/*     */   public static Field getDeclaredField(Class<?> paramClass, String paramString)
/*     */   {
/* 147 */     return getDeclaredField(paramClass, paramString, false);
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
/*     */   public static Field getDeclaredField(Class<?> paramClass, String paramString, boolean paramBoolean)
/*     */   {
/* 167 */     Validate.isTrue(paramClass != null, "The class must not be null", new Object[0]);
/* 168 */     Validate.isTrue(StringUtils.isNotBlank(paramString), "The field name must not be blank/empty", new Object[0]);
/*     */     try
/*     */     {
/* 171 */       Field localField = paramClass.getDeclaredField(paramString);
/* 172 */       if (!MemberUtils.isAccessible(localField)) {
/* 173 */         if (paramBoolean) {
/* 174 */           localField.setAccessible(true);
/*     */         } else {
/* 176 */           return null;
/*     */         }
/*     */       }
/* 179 */       return localField;
/*     */     }
/*     */     catch (NoSuchFieldException localNoSuchFieldException) {}
/*     */     
/* 183 */     return null;
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
/*     */   public static Field[] getAllFields(Class<?> paramClass)
/*     */   {
/* 197 */     List localList = getAllFieldsList(paramClass);
/* 198 */     return (Field[])localList.toArray(new Field[localList.size()]);
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
/*     */   public static List<Field> getAllFieldsList(Class<?> paramClass)
/*     */   {
/* 212 */     Validate.isTrue(paramClass != null, "The class must not be null", new Object[0]);
/* 213 */     ArrayList localArrayList = new ArrayList();
/* 214 */     Object localObject = paramClass;
/* 215 */     while (localObject != null) {
/* 216 */       Field[] arrayOfField1 = ((Class)localObject).getDeclaredFields();
/* 217 */       for (Field localField : arrayOfField1) {
/* 218 */         localArrayList.add(localField);
/*     */       }
/* 220 */       localObject = ((Class)localObject).getSuperclass();
/*     */     }
/* 222 */     return localArrayList;
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
/*     */   public static Object readStaticField(Field paramField)
/*     */     throws IllegalAccessException
/*     */   {
/* 237 */     return readStaticField(paramField, false);
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
/*     */   public static Object readStaticField(Field paramField, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 255 */     Validate.isTrue(paramField != null, "The field must not be null", new Object[0]);
/* 256 */     Validate.isTrue(Modifier.isStatic(paramField.getModifiers()), "The field '%s' is not static", new Object[] { paramField.getName() });
/* 257 */     return readField(paramField, (Object)null, paramBoolean);
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
/*     */   public static Object readStaticField(Class<?> paramClass, String paramString)
/*     */     throws IllegalAccessException
/*     */   {
/* 275 */     return readStaticField(paramClass, paramString, false);
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
/*     */   public static Object readStaticField(Class<?> paramClass, String paramString, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 297 */     Field localField = getField(paramClass, paramString, paramBoolean);
/* 298 */     Validate.isTrue(localField != null, "Cannot locate field '%s' on %s", new Object[] { paramString, paramClass });
/*     */     
/* 300 */     return readStaticField(localField, false);
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
/*     */   public static Object readDeclaredStaticField(Class<?> paramClass, String paramString)
/*     */     throws IllegalAccessException
/*     */   {
/* 319 */     return readDeclaredStaticField(paramClass, paramString, false);
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
/*     */   public static Object readDeclaredStaticField(Class<?> paramClass, String paramString, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 341 */     Field localField = getDeclaredField(paramClass, paramString, paramBoolean);
/* 342 */     Validate.isTrue(localField != null, "Cannot locate declared field %s.%s", new Object[] { paramClass.getName(), paramString });
/*     */     
/* 344 */     return readStaticField(localField, false);
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
/*     */   public static Object readField(Field paramField, Object paramObject)
/*     */     throws IllegalAccessException
/*     */   {
/* 361 */     return readField(paramField, paramObject, false);
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
/*     */   public static Object readField(Field paramField, Object paramObject, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 381 */     Validate.isTrue(paramField != null, "The field must not be null", new Object[0]);
/* 382 */     if ((paramBoolean) && (!paramField.isAccessible())) {
/* 383 */       paramField.setAccessible(true);
/*     */     } else {
/* 385 */       MemberUtils.setAccessibleWorkaround(paramField);
/*     */     }
/* 387 */     return paramField.get(paramObject);
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
/*     */   public static Object readField(Object paramObject, String paramString)
/*     */     throws IllegalAccessException
/*     */   {
/* 404 */     return readField(paramObject, paramString, false);
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
/*     */   public static Object readField(Object paramObject, String paramString, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 425 */     Validate.isTrue(paramObject != null, "target object must not be null", new Object[0]);
/* 426 */     Class localClass = paramObject.getClass();
/* 427 */     Field localField = getField(localClass, paramString, paramBoolean);
/* 428 */     Validate.isTrue(localField != null, "Cannot locate field %s on %s", new Object[] { paramString, localClass });
/*     */     
/* 430 */     return readField(localField, paramObject, false);
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
/*     */   public static Object readDeclaredField(Object paramObject, String paramString)
/*     */     throws IllegalAccessException
/*     */   {
/* 447 */     return readDeclaredField(paramObject, paramString, false);
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
/*     */   public static Object readDeclaredField(Object paramObject, String paramString, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 468 */     Validate.isTrue(paramObject != null, "target object must not be null", new Object[0]);
/* 469 */     Class localClass = paramObject.getClass();
/* 470 */     Field localField = getDeclaredField(localClass, paramString, paramBoolean);
/* 471 */     Validate.isTrue(localField != null, "Cannot locate declared field %s.%s", new Object[] { localClass, paramString });
/*     */     
/* 473 */     return readField(localField, paramObject, false);
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
/*     */   public static void writeStaticField(Field paramField, Object paramObject)
/*     */     throws IllegalAccessException
/*     */   {
/* 489 */     writeStaticField(paramField, paramObject, false);
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
/*     */   public static void writeStaticField(Field paramField, Object paramObject, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 509 */     Validate.isTrue(paramField != null, "The field must not be null", new Object[0]);
/* 510 */     Validate.isTrue(Modifier.isStatic(paramField.getModifiers()), "The field %s.%s is not static", new Object[] { paramField.getDeclaringClass().getName(), paramField.getName() });
/*     */     
/* 512 */     writeField(paramField, (Object)null, paramObject, paramBoolean);
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
/*     */   public static void writeStaticField(Class<?> paramClass, String paramString, Object paramObject)
/*     */     throws IllegalAccessException
/*     */   {
/* 531 */     writeStaticField(paramClass, paramString, paramObject, false);
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
/*     */   public static void writeStaticField(Class<?> paramClass, String paramString, Object paramObject, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 555 */     Field localField = getField(paramClass, paramString, paramBoolean);
/* 556 */     Validate.isTrue(localField != null, "Cannot locate field %s on %s", new Object[] { paramString, paramClass });
/*     */     
/* 558 */     writeStaticField(localField, paramObject, false);
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
/*     */   public static void writeDeclaredStaticField(Class<?> paramClass, String paramString, Object paramObject)
/*     */     throws IllegalAccessException
/*     */   {
/* 577 */     writeDeclaredStaticField(paramClass, paramString, paramObject, false);
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
/*     */   public static void writeDeclaredStaticField(Class<?> paramClass, String paramString, Object paramObject, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 600 */     Field localField = getDeclaredField(paramClass, paramString, paramBoolean);
/* 601 */     Validate.isTrue(localField != null, "Cannot locate declared field %s.%s", new Object[] { paramClass.getName(), paramString });
/*     */     
/* 603 */     writeField(localField, (Object)null, paramObject, false);
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
/*     */   public static void writeField(Field paramField, Object paramObject1, Object paramObject2)
/*     */     throws IllegalAccessException
/*     */   {
/* 620 */     writeField(paramField, paramObject1, paramObject2, false);
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
/*     */   public static void writeField(Field paramField, Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 643 */     Validate.isTrue(paramField != null, "The field must not be null", new Object[0]);
/* 644 */     if ((paramBoolean) && (!paramField.isAccessible())) {
/* 645 */       paramField.setAccessible(true);
/*     */     } else {
/* 647 */       MemberUtils.setAccessibleWorkaround(paramField);
/*     */     }
/* 649 */     paramField.set(paramObject1, paramObject2);
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
/*     */   public static void removeFinalModifier(Field paramField)
/*     */   {
/* 662 */     removeFinalModifier(paramField, true);
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
/*     */   public static void removeFinalModifier(Field paramField, boolean paramBoolean)
/*     */   {
/* 679 */     Validate.isTrue(paramField != null, "The field must not be null", new Object[0]);
/*     */     try
/*     */     {
/* 682 */       if (Modifier.isFinal(paramField.getModifiers()))
/*     */       {
/* 684 */         Field localField = Field.class.getDeclaredField("modifiers");
/* 685 */         int i = (paramBoolean) && (!localField.isAccessible()) ? 1 : 0;
/* 686 */         if (i != 0) {
/* 687 */           localField.setAccessible(true);
/*     */         }
/*     */         try {
/* 690 */           localField.setInt(paramField, paramField.getModifiers() & 0xFFFFFFEF);
/*     */         } finally {
/* 692 */           if (i != 0) {
/* 693 */             localField.setAccessible(false);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (NoSuchFieldException localNoSuchFieldException) {}catch (IllegalAccessException localIllegalAccessException) {}
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
/*     */   public static void writeField(Object paramObject1, String paramString, Object paramObject2)
/*     */     throws IllegalAccessException
/*     */   {
/* 720 */     writeField(paramObject1, paramString, paramObject2, false);
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
/*     */   public static void writeField(Object paramObject1, String paramString, Object paramObject2, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 744 */     Validate.isTrue(paramObject1 != null, "target object must not be null", new Object[0]);
/* 745 */     Class localClass = paramObject1.getClass();
/* 746 */     Field localField = getField(localClass, paramString, paramBoolean);
/* 747 */     Validate.isTrue(localField != null, "Cannot locate declared field %s.%s", new Object[] { localClass.getName(), paramString });
/*     */     
/* 749 */     writeField(localField, paramObject1, paramObject2, false);
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
/*     */   public static void writeDeclaredField(Object paramObject1, String paramString, Object paramObject2)
/*     */     throws IllegalAccessException
/*     */   {
/* 768 */     writeDeclaredField(paramObject1, paramString, paramObject2, false);
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
/*     */   public static void writeDeclaredField(Object paramObject1, String paramString, Object paramObject2, boolean paramBoolean)
/*     */     throws IllegalAccessException
/*     */   {
/* 792 */     Validate.isTrue(paramObject1 != null, "target object must not be null", new Object[0]);
/* 793 */     Class localClass = paramObject1.getClass();
/* 794 */     Field localField = getDeclaredField(localClass, paramString, paramBoolean);
/* 795 */     Validate.isTrue(localField != null, "Cannot locate declared field %s.%s", new Object[] { localClass.getName(), paramString });
/*     */     
/* 797 */     writeField(localField, paramObject1, paramObject2, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\reflect\FieldUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */