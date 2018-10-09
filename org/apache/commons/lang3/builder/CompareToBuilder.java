/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.lang.reflect.AccessibleObject;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompareToBuilder
/*      */   implements Builder<Integer>
/*      */ {
/*      */   private int comparison;
/*      */   
/*      */   public CompareToBuilder()
/*      */   {
/*  104 */     this.comparison = 0;
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
/*      */   public static int reflectionCompare(Object paramObject1, Object paramObject2)
/*      */   {
/*  135 */     return reflectionCompare(paramObject1, paramObject2, false, null, new String[0]);
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
/*      */   public static int reflectionCompare(Object paramObject1, Object paramObject2, boolean paramBoolean)
/*      */   {
/*  167 */     return reflectionCompare(paramObject1, paramObject2, paramBoolean, null, new String[0]);
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
/*      */   public static int reflectionCompare(Object paramObject1, Object paramObject2, Collection<String> paramCollection)
/*      */   {
/*  200 */     return reflectionCompare(paramObject1, paramObject2, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
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
/*      */   public static int reflectionCompare(Object paramObject1, Object paramObject2, String... paramVarArgs)
/*      */   {
/*  233 */     return reflectionCompare(paramObject1, paramObject2, false, null, paramVarArgs);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int reflectionCompare(Object paramObject1, Object paramObject2, boolean paramBoolean, Class<?> paramClass, String... paramVarArgs)
/*      */   {
/*  275 */     if (paramObject1 == paramObject2) {
/*  276 */       return 0;
/*      */     }
/*  278 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/*  279 */       throw new NullPointerException();
/*      */     }
/*  281 */     Class localClass = paramObject1.getClass();
/*  282 */     if (!localClass.isInstance(paramObject2)) {
/*  283 */       throw new ClassCastException();
/*      */     }
/*  285 */     CompareToBuilder localCompareToBuilder = new CompareToBuilder();
/*  286 */     reflectionAppend(paramObject1, paramObject2, localClass, localCompareToBuilder, paramBoolean, paramVarArgs);
/*  287 */     while ((localClass.getSuperclass() != null) && (localClass != paramClass)) {
/*  288 */       localClass = localClass.getSuperclass();
/*  289 */       reflectionAppend(paramObject1, paramObject2, localClass, localCompareToBuilder, paramBoolean, paramVarArgs);
/*      */     }
/*  291 */     return localCompareToBuilder.toComparison();
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
/*      */   private static void reflectionAppend(Object paramObject1, Object paramObject2, Class<?> paramClass, CompareToBuilder paramCompareToBuilder, boolean paramBoolean, String[] paramArrayOfString)
/*      */   {
/*  313 */     Field[] arrayOfField = paramClass.getDeclaredFields();
/*  314 */     AccessibleObject.setAccessible(arrayOfField, true);
/*  315 */     for (int i = 0; (i < arrayOfField.length) && (paramCompareToBuilder.comparison == 0); i++) {
/*  316 */       Field localField = arrayOfField[i];
/*  317 */       if ((!ArrayUtils.contains(paramArrayOfString, localField.getName())) && (localField.getName().indexOf('$') == -1) && ((paramBoolean) || (!Modifier.isTransient(localField.getModifiers()))) && (!Modifier.isStatic(localField.getModifiers())))
/*      */       {
/*      */         try
/*      */         {
/*      */ 
/*  322 */           paramCompareToBuilder.append(localField.get(paramObject1), localField.get(paramObject2));
/*      */         }
/*      */         catch (IllegalAccessException localIllegalAccessException)
/*      */         {
/*  326 */           throw new InternalError("Unexpected IllegalAccessException");
/*      */         }
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
/*      */   public CompareToBuilder appendSuper(int paramInt)
/*      */   {
/*  342 */     if (this.comparison != 0) {
/*  343 */       return this;
/*      */     }
/*  345 */     this.comparison = paramInt;
/*  346 */     return this;
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
/*      */   public CompareToBuilder append(Object paramObject1, Object paramObject2)
/*      */   {
/*  370 */     return append(paramObject1, paramObject2, null);
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
/*      */   public CompareToBuilder append(Object paramObject1, Object paramObject2, Comparator<?> paramComparator)
/*      */   {
/*  399 */     if (this.comparison != 0) {
/*  400 */       return this;
/*      */     }
/*  402 */     if (paramObject1 == paramObject2) {
/*  403 */       return this;
/*      */     }
/*  405 */     if (paramObject1 == null) {
/*  406 */       this.comparison = -1;
/*  407 */       return this;
/*      */     }
/*  409 */     if (paramObject2 == null) {
/*  410 */       this.comparison = 1;
/*  411 */       return this;
/*      */     }
/*  413 */     if (paramObject1.getClass().isArray())
/*      */     {
/*      */ 
/*      */ 
/*  417 */       if ((paramObject1 instanceof long[])) {
/*  418 */         append((long[])paramObject1, (long[])paramObject2);
/*  419 */       } else if ((paramObject1 instanceof int[])) {
/*  420 */         append((int[])paramObject1, (int[])paramObject2);
/*  421 */       } else if ((paramObject1 instanceof short[])) {
/*  422 */         append((short[])paramObject1, (short[])paramObject2);
/*  423 */       } else if ((paramObject1 instanceof char[])) {
/*  424 */         append((char[])paramObject1, (char[])paramObject2);
/*  425 */       } else if ((paramObject1 instanceof byte[])) {
/*  426 */         append((byte[])paramObject1, (byte[])paramObject2);
/*  427 */       } else if ((paramObject1 instanceof double[])) {
/*  428 */         append((double[])paramObject1, (double[])paramObject2);
/*  429 */       } else if ((paramObject1 instanceof float[])) {
/*  430 */         append((float[])paramObject1, (float[])paramObject2);
/*  431 */       } else if ((paramObject1 instanceof boolean[])) {
/*  432 */         append((boolean[])paramObject1, (boolean[])paramObject2);
/*      */       }
/*      */       else
/*      */       {
/*  436 */         append((Object[])paramObject1, (Object[])paramObject2, paramComparator);
/*      */       }
/*      */     } else {
/*      */       Object localObject;
/*  440 */       if (paramComparator == null)
/*      */       {
/*  442 */         localObject = (Comparable)paramObject1;
/*  443 */         this.comparison = ((Comparable)localObject).compareTo(paramObject2);
/*      */       }
/*      */       else {
/*  446 */         localObject = paramComparator;
/*  447 */         this.comparison = ((Comparator)localObject).compare(paramObject1, paramObject2);
/*      */       }
/*      */     }
/*  450 */     return this;
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
/*      */   public CompareToBuilder append(long paramLong1, long paramLong2)
/*      */   {
/*  463 */     if (this.comparison != 0) {
/*  464 */       return this;
/*      */     }
/*  466 */     this.comparison = (paramLong1 > paramLong2 ? 1 : paramLong1 < paramLong2 ? -1 : 0);
/*  467 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompareToBuilder append(int paramInt1, int paramInt2)
/*      */   {
/*  479 */     if (this.comparison != 0) {
/*  480 */       return this;
/*      */     }
/*  482 */     this.comparison = (paramInt1 > paramInt2 ? 1 : paramInt1 < paramInt2 ? -1 : 0);
/*  483 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompareToBuilder append(short paramShort1, short paramShort2)
/*      */   {
/*  495 */     if (this.comparison != 0) {
/*  496 */       return this;
/*      */     }
/*  498 */     this.comparison = (paramShort1 > paramShort2 ? 1 : paramShort1 < paramShort2 ? -1 : 0);
/*  499 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompareToBuilder append(char paramChar1, char paramChar2)
/*      */   {
/*  511 */     if (this.comparison != 0) {
/*  512 */       return this;
/*      */     }
/*  514 */     this.comparison = (paramChar1 > paramChar2 ? 1 : paramChar1 < paramChar2 ? -1 : 0);
/*  515 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompareToBuilder append(byte paramByte1, byte paramByte2)
/*      */   {
/*  527 */     if (this.comparison != 0) {
/*  528 */       return this;
/*      */     }
/*  530 */     this.comparison = (paramByte1 > paramByte2 ? 1 : paramByte1 < paramByte2 ? -1 : 0);
/*  531 */     return this;
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
/*      */   public CompareToBuilder append(double paramDouble1, double paramDouble2)
/*      */   {
/*  548 */     if (this.comparison != 0) {
/*  549 */       return this;
/*      */     }
/*  551 */     this.comparison = Double.compare(paramDouble1, paramDouble2);
/*  552 */     return this;
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
/*      */   public CompareToBuilder append(float paramFloat1, float paramFloat2)
/*      */   {
/*  569 */     if (this.comparison != 0) {
/*  570 */       return this;
/*      */     }
/*  572 */     this.comparison = Float.compare(paramFloat1, paramFloat2);
/*  573 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompareToBuilder append(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  585 */     if (this.comparison != 0) {
/*  586 */       return this;
/*      */     }
/*  588 */     if (paramBoolean1 == paramBoolean2) {
/*  589 */       return this;
/*      */     }
/*  591 */     if (!paramBoolean1) {
/*  592 */       this.comparison = -1;
/*      */     } else {
/*  594 */       this.comparison = 1;
/*      */     }
/*  596 */     return this;
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
/*      */   public CompareToBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
/*      */   {
/*  621 */     return append(paramArrayOfObject1, paramArrayOfObject2, null);
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
/*      */   public CompareToBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2, Comparator<?> paramComparator)
/*      */   {
/*  648 */     if (this.comparison != 0) {
/*  649 */       return this;
/*      */     }
/*  651 */     if (paramArrayOfObject1 == paramArrayOfObject2) {
/*  652 */       return this;
/*      */     }
/*  654 */     if (paramArrayOfObject1 == null) {
/*  655 */       this.comparison = -1;
/*  656 */       return this;
/*      */     }
/*  658 */     if (paramArrayOfObject2 == null) {
/*  659 */       this.comparison = 1;
/*  660 */       return this;
/*      */     }
/*  662 */     if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
/*  663 */       this.comparison = (paramArrayOfObject1.length < paramArrayOfObject2.length ? -1 : 1);
/*  664 */       return this;
/*      */     }
/*  666 */     for (int i = 0; (i < paramArrayOfObject1.length) && (this.comparison == 0); i++) {
/*  667 */       append(paramArrayOfObject1[i], paramArrayOfObject2[i], paramComparator);
/*      */     }
/*  669 */     return this;
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
/*      */   public CompareToBuilder append(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
/*      */   {
/*  688 */     if (this.comparison != 0) {
/*  689 */       return this;
/*      */     }
/*  691 */     if (paramArrayOfLong1 == paramArrayOfLong2) {
/*  692 */       return this;
/*      */     }
/*  694 */     if (paramArrayOfLong1 == null) {
/*  695 */       this.comparison = -1;
/*  696 */       return this;
/*      */     }
/*  698 */     if (paramArrayOfLong2 == null) {
/*  699 */       this.comparison = 1;
/*  700 */       return this;
/*      */     }
/*  702 */     if (paramArrayOfLong1.length != paramArrayOfLong2.length) {
/*  703 */       this.comparison = (paramArrayOfLong1.length < paramArrayOfLong2.length ? -1 : 1);
/*  704 */       return this;
/*      */     }
/*  706 */     for (int i = 0; (i < paramArrayOfLong1.length) && (this.comparison == 0); i++) {
/*  707 */       append(paramArrayOfLong1[i], paramArrayOfLong2[i]);
/*      */     }
/*  709 */     return this;
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
/*      */   public CompareToBuilder append(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/*  728 */     if (this.comparison != 0) {
/*  729 */       return this;
/*      */     }
/*  731 */     if (paramArrayOfInt1 == paramArrayOfInt2) {
/*  732 */       return this;
/*      */     }
/*  734 */     if (paramArrayOfInt1 == null) {
/*  735 */       this.comparison = -1;
/*  736 */       return this;
/*      */     }
/*  738 */     if (paramArrayOfInt2 == null) {
/*  739 */       this.comparison = 1;
/*  740 */       return this;
/*      */     }
/*  742 */     if (paramArrayOfInt1.length != paramArrayOfInt2.length) {
/*  743 */       this.comparison = (paramArrayOfInt1.length < paramArrayOfInt2.length ? -1 : 1);
/*  744 */       return this;
/*      */     }
/*  746 */     for (int i = 0; (i < paramArrayOfInt1.length) && (this.comparison == 0); i++) {
/*  747 */       append(paramArrayOfInt1[i], paramArrayOfInt2[i]);
/*      */     }
/*  749 */     return this;
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
/*      */   public CompareToBuilder append(short[] paramArrayOfShort1, short[] paramArrayOfShort2)
/*      */   {
/*  768 */     if (this.comparison != 0) {
/*  769 */       return this;
/*      */     }
/*  771 */     if (paramArrayOfShort1 == paramArrayOfShort2) {
/*  772 */       return this;
/*      */     }
/*  774 */     if (paramArrayOfShort1 == null) {
/*  775 */       this.comparison = -1;
/*  776 */       return this;
/*      */     }
/*  778 */     if (paramArrayOfShort2 == null) {
/*  779 */       this.comparison = 1;
/*  780 */       return this;
/*      */     }
/*  782 */     if (paramArrayOfShort1.length != paramArrayOfShort2.length) {
/*  783 */       this.comparison = (paramArrayOfShort1.length < paramArrayOfShort2.length ? -1 : 1);
/*  784 */       return this;
/*      */     }
/*  786 */     for (int i = 0; (i < paramArrayOfShort1.length) && (this.comparison == 0); i++) {
/*  787 */       append(paramArrayOfShort1[i], paramArrayOfShort2[i]);
/*      */     }
/*  789 */     return this;
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
/*      */   public CompareToBuilder append(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
/*      */   {
/*  808 */     if (this.comparison != 0) {
/*  809 */       return this;
/*      */     }
/*  811 */     if (paramArrayOfChar1 == paramArrayOfChar2) {
/*  812 */       return this;
/*      */     }
/*  814 */     if (paramArrayOfChar1 == null) {
/*  815 */       this.comparison = -1;
/*  816 */       return this;
/*      */     }
/*  818 */     if (paramArrayOfChar2 == null) {
/*  819 */       this.comparison = 1;
/*  820 */       return this;
/*      */     }
/*  822 */     if (paramArrayOfChar1.length != paramArrayOfChar2.length) {
/*  823 */       this.comparison = (paramArrayOfChar1.length < paramArrayOfChar2.length ? -1 : 1);
/*  824 */       return this;
/*      */     }
/*  826 */     for (int i = 0; (i < paramArrayOfChar1.length) && (this.comparison == 0); i++) {
/*  827 */       append(paramArrayOfChar1[i], paramArrayOfChar2[i]);
/*      */     }
/*  829 */     return this;
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
/*      */   public CompareToBuilder append(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*      */   {
/*  848 */     if (this.comparison != 0) {
/*  849 */       return this;
/*      */     }
/*  851 */     if (paramArrayOfByte1 == paramArrayOfByte2) {
/*  852 */       return this;
/*      */     }
/*  854 */     if (paramArrayOfByte1 == null) {
/*  855 */       this.comparison = -1;
/*  856 */       return this;
/*      */     }
/*  858 */     if (paramArrayOfByte2 == null) {
/*  859 */       this.comparison = 1;
/*  860 */       return this;
/*      */     }
/*  862 */     if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
/*  863 */       this.comparison = (paramArrayOfByte1.length < paramArrayOfByte2.length ? -1 : 1);
/*  864 */       return this;
/*      */     }
/*  866 */     for (int i = 0; (i < paramArrayOfByte1.length) && (this.comparison == 0); i++) {
/*  867 */       append(paramArrayOfByte1[i], paramArrayOfByte2[i]);
/*      */     }
/*  869 */     return this;
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
/*      */   public CompareToBuilder append(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*      */   {
/*  888 */     if (this.comparison != 0) {
/*  889 */       return this;
/*      */     }
/*  891 */     if (paramArrayOfDouble1 == paramArrayOfDouble2) {
/*  892 */       return this;
/*      */     }
/*  894 */     if (paramArrayOfDouble1 == null) {
/*  895 */       this.comparison = -1;
/*  896 */       return this;
/*      */     }
/*  898 */     if (paramArrayOfDouble2 == null) {
/*  899 */       this.comparison = 1;
/*  900 */       return this;
/*      */     }
/*  902 */     if (paramArrayOfDouble1.length != paramArrayOfDouble2.length) {
/*  903 */       this.comparison = (paramArrayOfDouble1.length < paramArrayOfDouble2.length ? -1 : 1);
/*  904 */       return this;
/*      */     }
/*  906 */     for (int i = 0; (i < paramArrayOfDouble1.length) && (this.comparison == 0); i++) {
/*  907 */       append(paramArrayOfDouble1[i], paramArrayOfDouble2[i]);
/*      */     }
/*  909 */     return this;
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
/*      */   public CompareToBuilder append(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/*  928 */     if (this.comparison != 0) {
/*  929 */       return this;
/*      */     }
/*  931 */     if (paramArrayOfFloat1 == paramArrayOfFloat2) {
/*  932 */       return this;
/*      */     }
/*  934 */     if (paramArrayOfFloat1 == null) {
/*  935 */       this.comparison = -1;
/*  936 */       return this;
/*      */     }
/*  938 */     if (paramArrayOfFloat2 == null) {
/*  939 */       this.comparison = 1;
/*  940 */       return this;
/*      */     }
/*  942 */     if (paramArrayOfFloat1.length != paramArrayOfFloat2.length) {
/*  943 */       this.comparison = (paramArrayOfFloat1.length < paramArrayOfFloat2.length ? -1 : 1);
/*  944 */       return this;
/*      */     }
/*  946 */     for (int i = 0; (i < paramArrayOfFloat1.length) && (this.comparison == 0); i++) {
/*  947 */       append(paramArrayOfFloat1[i], paramArrayOfFloat2[i]);
/*      */     }
/*  949 */     return this;
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
/*      */   public CompareToBuilder append(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
/*      */   {
/*  968 */     if (this.comparison != 0) {
/*  969 */       return this;
/*      */     }
/*  971 */     if (paramArrayOfBoolean1 == paramArrayOfBoolean2) {
/*  972 */       return this;
/*      */     }
/*  974 */     if (paramArrayOfBoolean1 == null) {
/*  975 */       this.comparison = -1;
/*  976 */       return this;
/*      */     }
/*  978 */     if (paramArrayOfBoolean2 == null) {
/*  979 */       this.comparison = 1;
/*  980 */       return this;
/*      */     }
/*  982 */     if (paramArrayOfBoolean1.length != paramArrayOfBoolean2.length) {
/*  983 */       this.comparison = (paramArrayOfBoolean1.length < paramArrayOfBoolean2.length ? -1 : 1);
/*  984 */       return this;
/*      */     }
/*  986 */     for (int i = 0; (i < paramArrayOfBoolean1.length) && (this.comparison == 0); i++) {
/*  987 */       append(paramArrayOfBoolean1[i], paramArrayOfBoolean2[i]);
/*      */     }
/*  989 */     return this;
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
/*      */   public int toComparison()
/*      */   {
/* 1003 */     return this.comparison;
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
/*      */   public Integer build()
/*      */   {
/* 1018 */     return Integer.valueOf(toComparison());
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\CompareToBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */