/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionToStringBuilder
/*     */   extends ToStringBuilder
/*     */ {
/*     */   public static String toString(Object paramObject)
/*     */   {
/* 118 */     return toString(paramObject, null, false, false, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toString(Object paramObject, ToStringStyle paramToStringStyle)
/*     */   {
/* 150 */     return toString(paramObject, paramToStringStyle, false, false, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean)
/*     */   {
/* 188 */     return toString(paramObject, paramToStringStyle, paramBoolean, false, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 234 */     return toString(paramObject, paramToStringStyle, paramBoolean1, paramBoolean2, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> String toString(T paramT, ToStringStyle paramToStringStyle, boolean paramBoolean1, boolean paramBoolean2, Class<? super T> paramClass)
/*     */   {
/* 287 */     return new ReflectionToStringBuilder(paramT, paramToStringStyle, null, paramClass, paramBoolean1, paramBoolean2).toString();
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
/*     */   public static String toStringExclude(Object paramObject, Collection<String> paramCollection)
/*     */   {
/* 301 */     return toStringExclude(paramObject, toNoNullStringArray(paramCollection));
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
/*     */   static String[] toNoNullStringArray(Collection<String> paramCollection)
/*     */   {
/* 314 */     if (paramCollection == null) {
/* 315 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*     */     }
/* 317 */     return toNoNullStringArray(paramCollection.toArray());
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
/*     */   static String[] toNoNullStringArray(Object[] paramArrayOfObject)
/*     */   {
/* 330 */     ArrayList localArrayList = new ArrayList(paramArrayOfObject.length);
/* 331 */     for (Object localObject : paramArrayOfObject) {
/* 332 */       if (localObject != null) {
/* 333 */         localArrayList.add(localObject.toString());
/*     */       }
/*     */     }
/* 336 */     return (String[])localArrayList.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
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
/*     */   public static String toStringExclude(Object paramObject, String... paramVarArgs)
/*     */   {
/* 350 */     return new ReflectionToStringBuilder(paramObject).setExcludeFieldNames(paramVarArgs).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 356 */   private boolean appendStatics = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 361 */   private boolean appendTransients = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String[] excludeFieldNames;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 373 */   private Class<?> upToClass = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReflectionToStringBuilder(Object paramObject)
/*     */   {
/* 390 */     super(paramObject);
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
/*     */   public ReflectionToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle)
/*     */   {
/* 410 */     super(paramObject, paramToStringStyle);
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
/*     */   public ReflectionToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer)
/*     */   {
/* 436 */     super(paramObject, paramToStringStyle, paramStringBuffer);
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
/*     */   public <T> ReflectionToStringBuilder(T paramT, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer, Class<? super T> paramClass, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 461 */     super(paramT, paramToStringStyle, paramStringBuffer);
/* 462 */     setUpToClass(paramClass);
/* 463 */     setAppendTransients(paramBoolean1);
/* 464 */     setAppendStatics(paramBoolean2);
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
/*     */   protected boolean accept(Field paramField)
/*     */   {
/* 480 */     if (paramField.getName().indexOf('$') != -1)
/*     */     {
/* 482 */       return false;
/*     */     }
/* 484 */     if ((Modifier.isTransient(paramField.getModifiers())) && (!isAppendTransients()))
/*     */     {
/* 486 */       return false;
/*     */     }
/* 488 */     if ((Modifier.isStatic(paramField.getModifiers())) && (!isAppendStatics()))
/*     */     {
/* 490 */       return false;
/*     */     }
/* 492 */     if ((this.excludeFieldNames != null) && (Arrays.binarySearch(this.excludeFieldNames, paramField.getName()) >= 0))
/*     */     {
/*     */ 
/* 495 */       return false;
/*     */     }
/* 497 */     return true;
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
/*     */   protected void appendFieldsIn(Class<?> paramClass)
/*     */   {
/* 514 */     if (paramClass.isArray()) {
/* 515 */       reflectionAppendArray(getObject());
/* 516 */       return;
/*     */     }
/* 518 */     Field[] arrayOfField1 = paramClass.getDeclaredFields();
/* 519 */     AccessibleObject.setAccessible(arrayOfField1, true);
/* 520 */     for (Field localField : arrayOfField1) {
/* 521 */       String str = localField.getName();
/* 522 */       if (accept(localField))
/*     */       {
/*     */         try
/*     */         {
/* 526 */           Object localObject = getValue(localField);
/* 527 */           append(str, localObject);
/*     */ 
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException)
/*     */         {
/*     */ 
/* 533 */           throw new InternalError("Unexpected IllegalAccessException: " + localIllegalAccessException.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String[] getExcludeFieldNames()
/*     */   {
/* 543 */     return (String[])this.excludeFieldNames.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class<?> getUpToClass()
/*     */   {
/* 554 */     return this.upToClass;
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
/*     */   protected Object getValue(Field paramField)
/*     */     throws IllegalArgumentException, IllegalAccessException
/*     */   {
/* 574 */     return paramField.get(getObject());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAppendStatics()
/*     */   {
/* 586 */     return this.appendStatics;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAppendTransients()
/*     */   {
/* 597 */     return this.appendTransients;
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
/*     */   public ReflectionToStringBuilder reflectionAppendArray(Object paramObject)
/*     */   {
/* 610 */     getStyle().reflectionAppendArrayDetail(getStringBuffer(), null, paramObject);
/* 611 */     return this;
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
/*     */   public void setAppendStatics(boolean paramBoolean)
/*     */   {
/* 624 */     this.appendStatics = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAppendTransients(boolean paramBoolean)
/*     */   {
/* 636 */     this.appendTransients = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReflectionToStringBuilder setExcludeFieldNames(String... paramVarArgs)
/*     */   {
/* 647 */     if (paramVarArgs == null) {
/* 648 */       this.excludeFieldNames = null;
/*     */     }
/*     */     else {
/* 651 */       this.excludeFieldNames = toNoNullStringArray(paramVarArgs);
/* 652 */       Arrays.sort(this.excludeFieldNames);
/*     */     }
/* 654 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUpToClass(Class<?> paramClass)
/*     */   {
/* 666 */     if (paramClass != null) {
/* 667 */       Object localObject = getObject();
/* 668 */       if ((localObject != null) && (!paramClass.isInstance(localObject))) {
/* 669 */         throw new IllegalArgumentException("Specified class is not a superclass of the object");
/*     */       }
/*     */     }
/* 672 */     this.upToClass = paramClass;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 684 */     if (getObject() == null) {
/* 685 */       return getStyle().getNullText();
/*     */     }
/* 687 */     Class localClass = getObject().getClass();
/* 688 */     appendFieldsIn(localClass);
/* 689 */     while ((localClass.getSuperclass() != null) && (localClass != getUpToClass())) {
/* 690 */       localClass = localClass.getSuperclass();
/* 691 */       appendFieldsIn(localClass);
/*     */     }
/* 693 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\ReflectionToStringBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */