/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class HashCodeBuilder
/*     */   implements Builder<Integer>
/*     */ {
/* 108 */   private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int iConstant;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Set<IDKey> getRegistry()
/*     */   {
/* 136 */     return (Set)REGISTRY.get();
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
/*     */   static boolean isRegistered(Object paramObject)
/*     */   {
/* 151 */     Set localSet = getRegistry();
/* 152 */     return (localSet != null) && (localSet.contains(new IDKey(paramObject)));
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
/*     */   private static void reflectionAppend(Object paramObject, Class<?> paramClass, HashCodeBuilder paramHashCodeBuilder, boolean paramBoolean, String[] paramArrayOfString)
/*     */   {
/* 173 */     if (isRegistered(paramObject)) {
/* 174 */       return;
/*     */     }
/*     */     try {
/* 177 */       register(paramObject);
/* 178 */       Field[] arrayOfField1 = paramClass.getDeclaredFields();
/* 179 */       AccessibleObject.setAccessible(arrayOfField1, true);
/* 180 */       for (Field localField : arrayOfField1) {
/* 181 */         if ((!ArrayUtils.contains(paramArrayOfString, localField.getName())) && (localField.getName().indexOf('$') == -1) && ((paramBoolean) || (!Modifier.isTransient(localField.getModifiers()))) && (!Modifier.isStatic(localField.getModifiers())))
/*     */         {
/*     */           try
/*     */           {
/*     */ 
/* 186 */             Object localObject1 = localField.get(paramObject);
/* 187 */             paramHashCodeBuilder.append(localObject1);
/*     */           }
/*     */           catch (IllegalAccessException localIllegalAccessException)
/*     */           {
/* 191 */             throw new InternalError("Unexpected IllegalAccessException");
/*     */           }
/*     */         }
/*     */       }
/*     */     } finally {
/* 196 */       unregister(paramObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int reflectionHashCode(int paramInt1, int paramInt2, Object paramObject)
/*     */   {
/* 238 */     return reflectionHashCode(paramInt1, paramInt2, paramObject, false, null, new String[0]);
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
/*     */   public static int reflectionHashCode(int paramInt1, int paramInt2, Object paramObject, boolean paramBoolean)
/*     */   {
/* 282 */     return reflectionHashCode(paramInt1, paramInt2, paramObject, paramBoolean, null, new String[0]);
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
/*     */   public static <T> int reflectionHashCode(int paramInt1, int paramInt2, T paramT, boolean paramBoolean, Class<? super T> paramClass, String... paramVarArgs)
/*     */   {
/* 335 */     if (paramT == null) {
/* 336 */       throw new IllegalArgumentException("The object to build a hash code for must not be null");
/*     */     }
/* 338 */     HashCodeBuilder localHashCodeBuilder = new HashCodeBuilder(paramInt1, paramInt2);
/* 339 */     Class localClass = paramT.getClass();
/* 340 */     reflectionAppend(paramT, localClass, localHashCodeBuilder, paramBoolean, paramVarArgs);
/* 341 */     while ((localClass.getSuperclass() != null) && (localClass != paramClass)) {
/* 342 */       localClass = localClass.getSuperclass();
/* 343 */       reflectionAppend(paramT, localClass, localHashCodeBuilder, paramBoolean, paramVarArgs);
/*     */     }
/* 345 */     return localHashCodeBuilder.toHashCode();
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
/*     */   public static int reflectionHashCode(Object paramObject, boolean paramBoolean)
/*     */   {
/* 381 */     return reflectionHashCode(17, 37, paramObject, paramBoolean, null, new String[0]);
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
/*     */   public static int reflectionHashCode(Object paramObject, Collection<String> paramCollection)
/*     */   {
/* 417 */     return reflectionHashCode(paramObject, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
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
/*     */   public static int reflectionHashCode(Object paramObject, String... paramVarArgs)
/*     */   {
/* 455 */     return reflectionHashCode(17, 37, paramObject, false, null, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void register(Object paramObject)
/*     */   {
/* 467 */     synchronized (HashCodeBuilder.class) {
/* 468 */       if (getRegistry() == null) {
/* 469 */         REGISTRY.set(new HashSet());
/*     */       }
/*     */     }
/* 472 */     getRegistry().add(new IDKey(paramObject));
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
/*     */   static void unregister(Object paramObject)
/*     */   {
/* 488 */     Set localSet = getRegistry();
/* 489 */     if (localSet != null) {
/* 490 */       localSet.remove(new IDKey(paramObject));
/* 491 */       synchronized (HashCodeBuilder.class)
/*     */       {
/* 493 */         localSet = getRegistry();
/* 494 */         if ((localSet != null) && (localSet.isEmpty())) {
/* 495 */           REGISTRY.remove();
/*     */         }
/*     */       }
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
/* 509 */   private int iTotal = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashCodeBuilder()
/*     */   {
/* 517 */     this.iConstant = 37;
/* 518 */     this.iTotal = 17;
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
/*     */   public HashCodeBuilder(int paramInt1, int paramInt2)
/*     */   {
/* 539 */     if (paramInt1 % 2 == 0) {
/* 540 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
/*     */     }
/* 542 */     if (paramInt2 % 2 == 0) {
/* 543 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
/*     */     }
/* 545 */     this.iConstant = paramInt2;
/* 546 */     this.iTotal = paramInt1;
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
/*     */   public HashCodeBuilder append(boolean paramBoolean)
/*     */   {
/* 571 */     this.iTotal = (this.iTotal * this.iConstant + (paramBoolean ? 0 : 1));
/* 572 */     return this;
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
/*     */   public HashCodeBuilder append(boolean[] paramArrayOfBoolean)
/*     */   {
/* 585 */     if (paramArrayOfBoolean == null) {
/* 586 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 588 */       for (int k : paramArrayOfBoolean) {
/* 589 */         append(k);
/*     */       }
/*     */     }
/* 592 */     return this;
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
/*     */   public HashCodeBuilder append(byte paramByte)
/*     */   {
/* 607 */     this.iTotal = (this.iTotal * this.iConstant + paramByte);
/* 608 */     return this;
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
/*     */   public HashCodeBuilder append(byte[] paramArrayOfByte)
/*     */   {
/* 623 */     if (paramArrayOfByte == null) {
/* 624 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 626 */       for (byte b : paramArrayOfByte) {
/* 627 */         append(b);
/*     */       }
/*     */     }
/* 630 */     return this;
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
/*     */   public HashCodeBuilder append(char paramChar)
/*     */   {
/* 643 */     this.iTotal = (this.iTotal * this.iConstant + paramChar);
/* 644 */     return this;
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
/*     */   public HashCodeBuilder append(char[] paramArrayOfChar)
/*     */   {
/* 657 */     if (paramArrayOfChar == null) {
/* 658 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 660 */       for (char c : paramArrayOfChar) {
/* 661 */         append(c);
/*     */       }
/*     */     }
/* 664 */     return this;
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
/*     */   public HashCodeBuilder append(double paramDouble)
/*     */   {
/* 677 */     return append(Double.doubleToLongBits(paramDouble));
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
/*     */   public HashCodeBuilder append(double[] paramArrayOfDouble)
/*     */   {
/* 690 */     if (paramArrayOfDouble == null) {
/* 691 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 693 */       for (double d : paramArrayOfDouble) {
/* 694 */         append(d);
/*     */       }
/*     */     }
/* 697 */     return this;
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
/*     */   public HashCodeBuilder append(float paramFloat)
/*     */   {
/* 710 */     this.iTotal = (this.iTotal * this.iConstant + Float.floatToIntBits(paramFloat));
/* 711 */     return this;
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
/*     */   public HashCodeBuilder append(float[] paramArrayOfFloat)
/*     */   {
/* 724 */     if (paramArrayOfFloat == null) {
/* 725 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 727 */       for (float f : paramArrayOfFloat) {
/* 728 */         append(f);
/*     */       }
/*     */     }
/* 731 */     return this;
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
/*     */   public HashCodeBuilder append(int paramInt)
/*     */   {
/* 744 */     this.iTotal = (this.iTotal * this.iConstant + paramInt);
/* 745 */     return this;
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
/*     */   public HashCodeBuilder append(int[] paramArrayOfInt)
/*     */   {
/* 758 */     if (paramArrayOfInt == null) {
/* 759 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 761 */       for (int k : paramArrayOfInt) {
/* 762 */         append(k);
/*     */       }
/*     */     }
/* 765 */     return this;
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
/*     */   public HashCodeBuilder append(long paramLong)
/*     */   {
/* 782 */     this.iTotal = (this.iTotal * this.iConstant + (int)(paramLong ^ paramLong >> 32));
/* 783 */     return this;
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
/*     */   public HashCodeBuilder append(long[] paramArrayOfLong)
/*     */   {
/* 796 */     if (paramArrayOfLong == null) {
/* 797 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 799 */       for (long l : paramArrayOfLong) {
/* 800 */         append(l);
/*     */       }
/*     */     }
/* 803 */     return this;
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
/*     */   public HashCodeBuilder append(Object paramObject)
/*     */   {
/* 816 */     if (paramObject == null) {
/* 817 */       this.iTotal *= this.iConstant;
/*     */ 
/*     */     }
/* 820 */     else if (paramObject.getClass().isArray())
/*     */     {
/*     */ 
/* 823 */       if ((paramObject instanceof long[])) {
/* 824 */         append((long[])paramObject);
/* 825 */       } else if ((paramObject instanceof int[])) {
/* 826 */         append((int[])paramObject);
/* 827 */       } else if ((paramObject instanceof short[])) {
/* 828 */         append((short[])paramObject);
/* 829 */       } else if ((paramObject instanceof char[])) {
/* 830 */         append((char[])paramObject);
/* 831 */       } else if ((paramObject instanceof byte[])) {
/* 832 */         append((byte[])paramObject);
/* 833 */       } else if ((paramObject instanceof double[])) {
/* 834 */         append((double[])paramObject);
/* 835 */       } else if ((paramObject instanceof float[])) {
/* 836 */         append((float[])paramObject);
/* 837 */       } else if ((paramObject instanceof boolean[])) {
/* 838 */         append((boolean[])paramObject);
/*     */       }
/*     */       else {
/* 841 */         append((Object[])paramObject);
/*     */       }
/*     */     } else {
/* 844 */       this.iTotal = (this.iTotal * this.iConstant + paramObject.hashCode());
/*     */     }
/*     */     
/* 847 */     return this;
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
/*     */   public HashCodeBuilder append(Object[] paramArrayOfObject)
/*     */   {
/* 860 */     if (paramArrayOfObject == null) {
/* 861 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 863 */       for (Object localObject : paramArrayOfObject) {
/* 864 */         append(localObject);
/*     */       }
/*     */     }
/* 867 */     return this;
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
/*     */   public HashCodeBuilder append(short paramShort)
/*     */   {
/* 880 */     this.iTotal = (this.iTotal * this.iConstant + paramShort);
/* 881 */     return this;
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
/*     */   public HashCodeBuilder append(short[] paramArrayOfShort)
/*     */   {
/* 894 */     if (paramArrayOfShort == null) {
/* 895 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 897 */       for (short s : paramArrayOfShort) {
/* 898 */         append(s);
/*     */       }
/*     */     }
/* 901 */     return this;
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
/*     */   public HashCodeBuilder appendSuper(int paramInt)
/*     */   {
/* 915 */     this.iTotal = (this.iTotal * this.iConstant + paramInt);
/* 916 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int toHashCode()
/*     */   {
/* 927 */     return this.iTotal;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Integer build()
/*     */   {
/* 939 */     return Integer.valueOf(toHashCode());
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
/*     */   public int hashCode()
/*     */   {
/* 953 */     return toHashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\HashCodeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */