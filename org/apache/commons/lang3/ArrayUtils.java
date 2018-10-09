/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang3.builder.EqualsBuilder;
/*      */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*      */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*      */ import org.apache.commons.lang3.builder.ToStringStyle;
/*      */ import org.apache.commons.lang3.mutable.MutableInt;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ArrayUtils
/*      */ {
/*   49 */   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*      */   
/*      */ 
/*      */ 
/*   53 */   public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
/*      */   
/*      */ 
/*      */ 
/*   57 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */   
/*      */ 
/*      */ 
/*   61 */   public static final long[] EMPTY_LONG_ARRAY = new long[0];
/*      */   
/*      */ 
/*      */ 
/*   65 */   public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
/*      */   
/*      */ 
/*      */ 
/*   69 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/*      */   
/*      */ 
/*      */ 
/*   73 */   public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
/*      */   
/*      */ 
/*      */ 
/*   77 */   public static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*      */   
/*      */ 
/*      */ 
/*   81 */   public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
/*      */   
/*      */ 
/*      */ 
/*   85 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */   
/*      */ 
/*      */ 
/*   89 */   public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
/*      */   
/*      */ 
/*      */ 
/*   93 */   public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*      */   
/*      */ 
/*      */ 
/*   97 */   public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
/*      */   
/*      */ 
/*      */ 
/*  101 */   public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*      */   
/*      */ 
/*      */ 
/*  105 */   public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
/*      */   
/*      */ 
/*      */ 
/*  109 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/*      */   
/*      */ 
/*      */ 
/*  113 */   public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
/*      */   
/*      */ 
/*      */ 
/*  117 */   public static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*      */   
/*      */ 
/*      */ 
/*  121 */   public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toString(Object paramObject)
/*      */   {
/*  159 */     return toString(paramObject, "{}");
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
/*      */   public static String toString(Object paramObject, String paramString)
/*      */   {
/*  175 */     if (paramObject == null) {
/*  176 */       return paramString;
/*      */     }
/*  178 */     return new ToStringBuilder(paramObject, ToStringStyle.SIMPLE_STYLE).append(paramObject).toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int hashCode(Object paramObject)
/*      */   {
/*  190 */     return new HashCodeBuilder().append(paramObject).toHashCode();
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
/*      */   @Deprecated
/*      */   public static boolean isEquals(Object paramObject1, Object paramObject2)
/*      */   {
/*  207 */     return new EqualsBuilder().append(paramObject1, paramObject2).isEquals();
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
/*      */   public static Map<Object, Object> toMap(Object[] paramArrayOfObject)
/*      */   {
/*  238 */     if (paramArrayOfObject == null) {
/*  239 */       return null;
/*      */     }
/*  241 */     HashMap localHashMap = new HashMap((int)(paramArrayOfObject.length * 1.5D));
/*  242 */     for (int i = 0; i < paramArrayOfObject.length; i++) {
/*  243 */       Object localObject1 = paramArrayOfObject[i];
/*  244 */       Object localObject2; if ((localObject1 instanceof Map.Entry)) {
/*  245 */         localObject2 = (Map.Entry)localObject1;
/*  246 */         localHashMap.put(((Map.Entry)localObject2).getKey(), ((Map.Entry)localObject2).getValue());
/*  247 */       } else if ((localObject1 instanceof Object[])) {
/*  248 */         localObject2 = (Object[])localObject1;
/*  249 */         if (localObject2.length < 2) {
/*  250 */           throw new IllegalArgumentException("Array element " + i + ", '" + localObject1 + "', has a length less than 2");
/*      */         }
/*      */         
/*      */ 
/*  254 */         localHashMap.put(localObject2[0], localObject2[1]);
/*      */       } else {
/*  256 */         throw new IllegalArgumentException("Array element " + i + ", '" + localObject1 + "', is neither of type Map.Entry nor an Array");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  261 */     return localHashMap;
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
/*      */ 
/*      */   public static <T> T[] toArray(T... paramVarArgs)
/*      */   {
/*  304 */     return paramVarArgs;
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
/*      */   public static <T> T[] clone(T[] paramArrayOfT)
/*      */   {
/*  323 */     if (paramArrayOfT == null) {
/*  324 */       return null;
/*      */     }
/*  326 */     return (Object[])paramArrayOfT.clone();
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
/*      */   public static long[] clone(long[] paramArrayOfLong)
/*      */   {
/*  339 */     if (paramArrayOfLong == null) {
/*  340 */       return null;
/*      */     }
/*  342 */     return (long[])paramArrayOfLong.clone();
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
/*      */   public static int[] clone(int[] paramArrayOfInt)
/*      */   {
/*  355 */     if (paramArrayOfInt == null) {
/*  356 */       return null;
/*      */     }
/*  358 */     return (int[])paramArrayOfInt.clone();
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
/*      */   public static short[] clone(short[] paramArrayOfShort)
/*      */   {
/*  371 */     if (paramArrayOfShort == null) {
/*  372 */       return null;
/*      */     }
/*  374 */     return (short[])paramArrayOfShort.clone();
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
/*      */   public static char[] clone(char[] paramArrayOfChar)
/*      */   {
/*  387 */     if (paramArrayOfChar == null) {
/*  388 */       return null;
/*      */     }
/*  390 */     return (char[])paramArrayOfChar.clone();
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
/*      */   public static byte[] clone(byte[] paramArrayOfByte)
/*      */   {
/*  403 */     if (paramArrayOfByte == null) {
/*  404 */       return null;
/*      */     }
/*  406 */     return (byte[])paramArrayOfByte.clone();
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
/*      */   public static double[] clone(double[] paramArrayOfDouble)
/*      */   {
/*  419 */     if (paramArrayOfDouble == null) {
/*  420 */       return null;
/*      */     }
/*  422 */     return (double[])paramArrayOfDouble.clone();
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
/*      */   public static float[] clone(float[] paramArrayOfFloat)
/*      */   {
/*  435 */     if (paramArrayOfFloat == null) {
/*  436 */       return null;
/*      */     }
/*  438 */     return (float[])paramArrayOfFloat.clone();
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
/*      */   public static boolean[] clone(boolean[] paramArrayOfBoolean)
/*      */   {
/*  451 */     if (paramArrayOfBoolean == null) {
/*  452 */       return null;
/*      */     }
/*  454 */     return (boolean[])paramArrayOfBoolean.clone();
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
/*      */   public static Object[] nullToEmpty(Object[] paramArrayOfObject)
/*      */   {
/*  473 */     if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0)) {
/*  474 */       return EMPTY_OBJECT_ARRAY;
/*      */     }
/*  476 */     return paramArrayOfObject;
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
/*      */   public static Class<?>[] nullToEmpty(Class<?>[] paramArrayOfClass)
/*      */   {
/*  493 */     if ((paramArrayOfClass == null) || (paramArrayOfClass.length == 0)) {
/*  494 */       return EMPTY_CLASS_ARRAY;
/*      */     }
/*  496 */     return paramArrayOfClass;
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
/*      */   public static String[] nullToEmpty(String[] paramArrayOfString)
/*      */   {
/*  513 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/*  514 */       return EMPTY_STRING_ARRAY;
/*      */     }
/*  516 */     return paramArrayOfString;
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
/*      */   public static long[] nullToEmpty(long[] paramArrayOfLong)
/*      */   {
/*  533 */     if ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0)) {
/*  534 */       return EMPTY_LONG_ARRAY;
/*      */     }
/*  536 */     return paramArrayOfLong;
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
/*      */   public static int[] nullToEmpty(int[] paramArrayOfInt)
/*      */   {
/*  553 */     if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0)) {
/*  554 */       return EMPTY_INT_ARRAY;
/*      */     }
/*  556 */     return paramArrayOfInt;
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
/*      */   public static short[] nullToEmpty(short[] paramArrayOfShort)
/*      */   {
/*  573 */     if ((paramArrayOfShort == null) || (paramArrayOfShort.length == 0)) {
/*  574 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/*  576 */     return paramArrayOfShort;
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
/*      */   public static char[] nullToEmpty(char[] paramArrayOfChar)
/*      */   {
/*  593 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
/*  594 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/*  596 */     return paramArrayOfChar;
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
/*      */   public static byte[] nullToEmpty(byte[] paramArrayOfByte)
/*      */   {
/*  613 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/*  614 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/*  616 */     return paramArrayOfByte;
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
/*      */   public static double[] nullToEmpty(double[] paramArrayOfDouble)
/*      */   {
/*  633 */     if ((paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0)) {
/*  634 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/*  636 */     return paramArrayOfDouble;
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
/*      */   public static float[] nullToEmpty(float[] paramArrayOfFloat)
/*      */   {
/*  653 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0)) {
/*  654 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/*  656 */     return paramArrayOfFloat;
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
/*      */   public static boolean[] nullToEmpty(boolean[] paramArrayOfBoolean)
/*      */   {
/*  673 */     if ((paramArrayOfBoolean == null) || (paramArrayOfBoolean.length == 0)) {
/*  674 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/*  676 */     return paramArrayOfBoolean;
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
/*      */   public static Long[] nullToEmpty(Long[] paramArrayOfLong)
/*      */   {
/*  693 */     if ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0)) {
/*  694 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/*  696 */     return paramArrayOfLong;
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
/*      */   public static Integer[] nullToEmpty(Integer[] paramArrayOfInteger)
/*      */   {
/*  713 */     if ((paramArrayOfInteger == null) || (paramArrayOfInteger.length == 0)) {
/*  714 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/*  716 */     return paramArrayOfInteger;
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
/*      */   public static Short[] nullToEmpty(Short[] paramArrayOfShort)
/*      */   {
/*  733 */     if ((paramArrayOfShort == null) || (paramArrayOfShort.length == 0)) {
/*  734 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/*  736 */     return paramArrayOfShort;
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
/*      */   public static Character[] nullToEmpty(Character[] paramArrayOfCharacter)
/*      */   {
/*  753 */     if ((paramArrayOfCharacter == null) || (paramArrayOfCharacter.length == 0)) {
/*  754 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/*  756 */     return paramArrayOfCharacter;
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
/*      */   public static Byte[] nullToEmpty(Byte[] paramArrayOfByte)
/*      */   {
/*  773 */     if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
/*  774 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/*  776 */     return paramArrayOfByte;
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
/*      */   public static Double[] nullToEmpty(Double[] paramArrayOfDouble)
/*      */   {
/*  793 */     if ((paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0)) {
/*  794 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/*  796 */     return paramArrayOfDouble;
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
/*      */   public static Float[] nullToEmpty(Float[] paramArrayOfFloat)
/*      */   {
/*  813 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0)) {
/*  814 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/*  816 */     return paramArrayOfFloat;
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
/*      */   public static Boolean[] nullToEmpty(Boolean[] paramArrayOfBoolean)
/*      */   {
/*  833 */     if ((paramArrayOfBoolean == null) || (paramArrayOfBoolean.length == 0)) {
/*  834 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/*  836 */     return paramArrayOfBoolean;
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
/*      */   public static <T> T[] subarray(T[] paramArrayOfT, int paramInt1, int paramInt2)
/*      */   {
/*  871 */     if (paramArrayOfT == null) {
/*  872 */       return null;
/*      */     }
/*  874 */     if (paramInt1 < 0) {
/*  875 */       paramInt1 = 0;
/*      */     }
/*  877 */     if (paramInt2 > paramArrayOfT.length) {
/*  878 */       paramInt2 = paramArrayOfT.length;
/*      */     }
/*  880 */     int i = paramInt2 - paramInt1;
/*  881 */     Class localClass = paramArrayOfT.getClass().getComponentType();
/*  882 */     if (i <= 0)
/*      */     {
/*  884 */       arrayOfObject = (Object[])Array.newInstance(localClass, 0);
/*  885 */       return arrayOfObject;
/*      */     }
/*      */     
/*      */ 
/*  889 */     Object[] arrayOfObject = (Object[])Array.newInstance(localClass, i);
/*  890 */     System.arraycopy(paramArrayOfT, paramInt1, arrayOfObject, 0, i);
/*  891 */     return arrayOfObject;
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
/*      */   public static long[] subarray(long[] paramArrayOfLong, int paramInt1, int paramInt2)
/*      */   {
/*  915 */     if (paramArrayOfLong == null) {
/*  916 */       return null;
/*      */     }
/*  918 */     if (paramInt1 < 0) {
/*  919 */       paramInt1 = 0;
/*      */     }
/*  921 */     if (paramInt2 > paramArrayOfLong.length) {
/*  922 */       paramInt2 = paramArrayOfLong.length;
/*      */     }
/*  924 */     int i = paramInt2 - paramInt1;
/*  925 */     if (i <= 0) {
/*  926 */       return EMPTY_LONG_ARRAY;
/*      */     }
/*      */     
/*  929 */     long[] arrayOfLong = new long[i];
/*  930 */     System.arraycopy(paramArrayOfLong, paramInt1, arrayOfLong, 0, i);
/*  931 */     return arrayOfLong;
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
/*      */   public static int[] subarray(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/*  955 */     if (paramArrayOfInt == null) {
/*  956 */       return null;
/*      */     }
/*  958 */     if (paramInt1 < 0) {
/*  959 */       paramInt1 = 0;
/*      */     }
/*  961 */     if (paramInt2 > paramArrayOfInt.length) {
/*  962 */       paramInt2 = paramArrayOfInt.length;
/*      */     }
/*  964 */     int i = paramInt2 - paramInt1;
/*  965 */     if (i <= 0) {
/*  966 */       return EMPTY_INT_ARRAY;
/*      */     }
/*      */     
/*  969 */     int[] arrayOfInt = new int[i];
/*  970 */     System.arraycopy(paramArrayOfInt, paramInt1, arrayOfInt, 0, i);
/*  971 */     return arrayOfInt;
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
/*      */   public static short[] subarray(short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*      */   {
/*  995 */     if (paramArrayOfShort == null) {
/*  996 */       return null;
/*      */     }
/*  998 */     if (paramInt1 < 0) {
/*  999 */       paramInt1 = 0;
/*      */     }
/* 1001 */     if (paramInt2 > paramArrayOfShort.length) {
/* 1002 */       paramInt2 = paramArrayOfShort.length;
/*      */     }
/* 1004 */     int i = paramInt2 - paramInt1;
/* 1005 */     if (i <= 0) {
/* 1006 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/*      */     
/* 1009 */     short[] arrayOfShort = new short[i];
/* 1010 */     System.arraycopy(paramArrayOfShort, paramInt1, arrayOfShort, 0, i);
/* 1011 */     return arrayOfShort;
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
/*      */   public static char[] subarray(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 1035 */     if (paramArrayOfChar == null) {
/* 1036 */       return null;
/*      */     }
/* 1038 */     if (paramInt1 < 0) {
/* 1039 */       paramInt1 = 0;
/*      */     }
/* 1041 */     if (paramInt2 > paramArrayOfChar.length) {
/* 1042 */       paramInt2 = paramArrayOfChar.length;
/*      */     }
/* 1044 */     int i = paramInt2 - paramInt1;
/* 1045 */     if (i <= 0) {
/* 1046 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/*      */     
/* 1049 */     char[] arrayOfChar = new char[i];
/* 1050 */     System.arraycopy(paramArrayOfChar, paramInt1, arrayOfChar, 0, i);
/* 1051 */     return arrayOfChar;
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
/*      */   public static byte[] subarray(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1075 */     if (paramArrayOfByte == null) {
/* 1076 */       return null;
/*      */     }
/* 1078 */     if (paramInt1 < 0) {
/* 1079 */       paramInt1 = 0;
/*      */     }
/* 1081 */     if (paramInt2 > paramArrayOfByte.length) {
/* 1082 */       paramInt2 = paramArrayOfByte.length;
/*      */     }
/* 1084 */     int i = paramInt2 - paramInt1;
/* 1085 */     if (i <= 0) {
/* 1086 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/*      */     
/* 1089 */     byte[] arrayOfByte = new byte[i];
/* 1090 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, i);
/* 1091 */     return arrayOfByte;
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
/*      */   public static double[] subarray(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*      */   {
/* 1115 */     if (paramArrayOfDouble == null) {
/* 1116 */       return null;
/*      */     }
/* 1118 */     if (paramInt1 < 0) {
/* 1119 */       paramInt1 = 0;
/*      */     }
/* 1121 */     if (paramInt2 > paramArrayOfDouble.length) {
/* 1122 */       paramInt2 = paramArrayOfDouble.length;
/*      */     }
/* 1124 */     int i = paramInt2 - paramInt1;
/* 1125 */     if (i <= 0) {
/* 1126 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/*      */     
/* 1129 */     double[] arrayOfDouble = new double[i];
/* 1130 */     System.arraycopy(paramArrayOfDouble, paramInt1, arrayOfDouble, 0, i);
/* 1131 */     return arrayOfDouble;
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
/*      */   public static float[] subarray(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*      */   {
/* 1155 */     if (paramArrayOfFloat == null) {
/* 1156 */       return null;
/*      */     }
/* 1158 */     if (paramInt1 < 0) {
/* 1159 */       paramInt1 = 0;
/*      */     }
/* 1161 */     if (paramInt2 > paramArrayOfFloat.length) {
/* 1162 */       paramInt2 = paramArrayOfFloat.length;
/*      */     }
/* 1164 */     int i = paramInt2 - paramInt1;
/* 1165 */     if (i <= 0) {
/* 1166 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/*      */     
/* 1169 */     float[] arrayOfFloat = new float[i];
/* 1170 */     System.arraycopy(paramArrayOfFloat, paramInt1, arrayOfFloat, 0, i);
/* 1171 */     return arrayOfFloat;
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
/*      */   public static boolean[] subarray(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
/*      */   {
/* 1195 */     if (paramArrayOfBoolean == null) {
/* 1196 */       return null;
/*      */     }
/* 1198 */     if (paramInt1 < 0) {
/* 1199 */       paramInt1 = 0;
/*      */     }
/* 1201 */     if (paramInt2 > paramArrayOfBoolean.length) {
/* 1202 */       paramInt2 = paramArrayOfBoolean.length;
/*      */     }
/* 1204 */     int i = paramInt2 - paramInt1;
/* 1205 */     if (i <= 0) {
/* 1206 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/*      */     
/* 1209 */     boolean[] arrayOfBoolean = new boolean[i];
/* 1210 */     System.arraycopy(paramArrayOfBoolean, paramInt1, arrayOfBoolean, 0, i);
/* 1211 */     return arrayOfBoolean;
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
/*      */   public static boolean isSameLength(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
/*      */   {
/* 1228 */     if (((paramArrayOfObject1 == null) && (paramArrayOfObject2 != null) && (paramArrayOfObject2.length > 0)) || ((paramArrayOfObject2 == null) && (paramArrayOfObject1 != null) && (paramArrayOfObject1.length > 0)) || ((paramArrayOfObject1 != null) && (paramArrayOfObject2 != null) && (paramArrayOfObject1.length != paramArrayOfObject2.length)))
/*      */     {
/*      */ 
/* 1231 */       return false;
/*      */     }
/* 1233 */     return true;
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
/*      */   public static boolean isSameLength(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
/*      */   {
/* 1246 */     if (((paramArrayOfLong1 == null) && (paramArrayOfLong2 != null) && (paramArrayOfLong2.length > 0)) || ((paramArrayOfLong2 == null) && (paramArrayOfLong1 != null) && (paramArrayOfLong1.length > 0)) || ((paramArrayOfLong1 != null) && (paramArrayOfLong2 != null) && (paramArrayOfLong1.length != paramArrayOfLong2.length)))
/*      */     {
/*      */ 
/* 1249 */       return false;
/*      */     }
/* 1251 */     return true;
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
/*      */   public static boolean isSameLength(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1264 */     if (((paramArrayOfInt1 == null) && (paramArrayOfInt2 != null) && (paramArrayOfInt2.length > 0)) || ((paramArrayOfInt2 == null) && (paramArrayOfInt1 != null) && (paramArrayOfInt1.length > 0)) || ((paramArrayOfInt1 != null) && (paramArrayOfInt2 != null) && (paramArrayOfInt1.length != paramArrayOfInt2.length)))
/*      */     {
/*      */ 
/* 1267 */       return false;
/*      */     }
/* 1269 */     return true;
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
/*      */   public static boolean isSameLength(short[] paramArrayOfShort1, short[] paramArrayOfShort2)
/*      */   {
/* 1282 */     if (((paramArrayOfShort1 == null) && (paramArrayOfShort2 != null) && (paramArrayOfShort2.length > 0)) || ((paramArrayOfShort2 == null) && (paramArrayOfShort1 != null) && (paramArrayOfShort1.length > 0)) || ((paramArrayOfShort1 != null) && (paramArrayOfShort2 != null) && (paramArrayOfShort1.length != paramArrayOfShort2.length)))
/*      */     {
/*      */ 
/* 1285 */       return false;
/*      */     }
/* 1287 */     return true;
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
/*      */   public static boolean isSameLength(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
/*      */   {
/* 1300 */     if (((paramArrayOfChar1 == null) && (paramArrayOfChar2 != null) && (paramArrayOfChar2.length > 0)) || ((paramArrayOfChar2 == null) && (paramArrayOfChar1 != null) && (paramArrayOfChar1.length > 0)) || ((paramArrayOfChar1 != null) && (paramArrayOfChar2 != null) && (paramArrayOfChar1.length != paramArrayOfChar2.length)))
/*      */     {
/*      */ 
/* 1303 */       return false;
/*      */     }
/* 1305 */     return true;
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
/*      */   public static boolean isSameLength(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*      */   {
/* 1318 */     if (((paramArrayOfByte1 == null) && (paramArrayOfByte2 != null) && (paramArrayOfByte2.length > 0)) || ((paramArrayOfByte2 == null) && (paramArrayOfByte1 != null) && (paramArrayOfByte1.length > 0)) || ((paramArrayOfByte1 != null) && (paramArrayOfByte2 != null) && (paramArrayOfByte1.length != paramArrayOfByte2.length)))
/*      */     {
/*      */ 
/* 1321 */       return false;
/*      */     }
/* 1323 */     return true;
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
/*      */   public static boolean isSameLength(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*      */   {
/* 1336 */     if (((paramArrayOfDouble1 == null) && (paramArrayOfDouble2 != null) && (paramArrayOfDouble2.length > 0)) || ((paramArrayOfDouble2 == null) && (paramArrayOfDouble1 != null) && (paramArrayOfDouble1.length > 0)) || ((paramArrayOfDouble1 != null) && (paramArrayOfDouble2 != null) && (paramArrayOfDouble1.length != paramArrayOfDouble2.length)))
/*      */     {
/*      */ 
/* 1339 */       return false;
/*      */     }
/* 1341 */     return true;
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
/*      */   public static boolean isSameLength(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/* 1354 */     if (((paramArrayOfFloat1 == null) && (paramArrayOfFloat2 != null) && (paramArrayOfFloat2.length > 0)) || ((paramArrayOfFloat2 == null) && (paramArrayOfFloat1 != null) && (paramArrayOfFloat1.length > 0)) || ((paramArrayOfFloat1 != null) && (paramArrayOfFloat2 != null) && (paramArrayOfFloat1.length != paramArrayOfFloat2.length)))
/*      */     {
/*      */ 
/* 1357 */       return false;
/*      */     }
/* 1359 */     return true;
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
/*      */   public static boolean isSameLength(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
/*      */   {
/* 1372 */     if (((paramArrayOfBoolean1 == null) && (paramArrayOfBoolean2 != null) && (paramArrayOfBoolean2.length > 0)) || ((paramArrayOfBoolean2 == null) && (paramArrayOfBoolean1 != null) && (paramArrayOfBoolean1.length > 0)) || ((paramArrayOfBoolean1 != null) && (paramArrayOfBoolean2 != null) && (paramArrayOfBoolean1.length != paramArrayOfBoolean2.length)))
/*      */     {
/*      */ 
/* 1375 */       return false;
/*      */     }
/* 1377 */     return true;
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
/*      */   public static int getLength(Object paramObject)
/*      */   {
/* 1402 */     if (paramObject == null) {
/* 1403 */       return 0;
/*      */     }
/* 1405 */     return Array.getLength(paramObject);
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
/*      */   public static boolean isSameType(Object paramObject1, Object paramObject2)
/*      */   {
/* 1418 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 1419 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1421 */     return paramObject1.getClass().getName().equals(paramObject2.getClass().getName());
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
/*      */   public static void reverse(Object[] paramArrayOfObject)
/*      */   {
/* 1436 */     if (paramArrayOfObject == null) {
/* 1437 */       return;
/*      */     }
/* 1439 */     reverse(paramArrayOfObject, 0, paramArrayOfObject.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(long[] paramArrayOfLong)
/*      */   {
/* 1450 */     if (paramArrayOfLong == null) {
/* 1451 */       return;
/*      */     }
/* 1453 */     reverse(paramArrayOfLong, 0, paramArrayOfLong.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(int[] paramArrayOfInt)
/*      */   {
/* 1464 */     if (paramArrayOfInt == null) {
/* 1465 */       return;
/*      */     }
/* 1467 */     reverse(paramArrayOfInt, 0, paramArrayOfInt.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(short[] paramArrayOfShort)
/*      */   {
/* 1478 */     if (paramArrayOfShort == null) {
/* 1479 */       return;
/*      */     }
/* 1481 */     reverse(paramArrayOfShort, 0, paramArrayOfShort.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(char[] paramArrayOfChar)
/*      */   {
/* 1492 */     if (paramArrayOfChar == null) {
/* 1493 */       return;
/*      */     }
/* 1495 */     reverse(paramArrayOfChar, 0, paramArrayOfChar.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(byte[] paramArrayOfByte)
/*      */   {
/* 1506 */     if (paramArrayOfByte == null) {
/* 1507 */       return;
/*      */     }
/* 1509 */     reverse(paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(double[] paramArrayOfDouble)
/*      */   {
/* 1520 */     if (paramArrayOfDouble == null) {
/* 1521 */       return;
/*      */     }
/* 1523 */     reverse(paramArrayOfDouble, 0, paramArrayOfDouble.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(float[] paramArrayOfFloat)
/*      */   {
/* 1534 */     if (paramArrayOfFloat == null) {
/* 1535 */       return;
/*      */     }
/* 1537 */     reverse(paramArrayOfFloat, 0, paramArrayOfFloat.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(boolean[] paramArrayOfBoolean)
/*      */   {
/* 1548 */     if (paramArrayOfBoolean == null) {
/* 1549 */       return;
/*      */     }
/* 1551 */     reverse(paramArrayOfBoolean, 0, paramArrayOfBoolean.length);
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
/*      */   public static void reverse(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
/*      */   {
/* 1574 */     if (paramArrayOfBoolean == null) {
/* 1575 */       return;
/*      */     }
/* 1577 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1578 */     int j = Math.min(paramArrayOfBoolean.length, paramInt2) - 1;
/*      */     
/* 1580 */     while (j > i) {
/* 1581 */       int k = paramArrayOfBoolean[j];
/* 1582 */       paramArrayOfBoolean[j] = paramArrayOfBoolean[i];
/* 1583 */       paramArrayOfBoolean[i] = k;
/* 1584 */       j--;
/* 1585 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1609 */     if (paramArrayOfByte == null) {
/* 1610 */       return;
/*      */     }
/* 1612 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1613 */     int j = Math.min(paramArrayOfByte.length, paramInt2) - 1;
/*      */     
/* 1615 */     while (j > i) {
/* 1616 */       int k = paramArrayOfByte[j];
/* 1617 */       paramArrayOfByte[j] = paramArrayOfByte[i];
/* 1618 */       paramArrayOfByte[i] = k;
/* 1619 */       j--;
/* 1620 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 1644 */     if (paramArrayOfChar == null) {
/* 1645 */       return;
/*      */     }
/* 1647 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1648 */     int j = Math.min(paramArrayOfChar.length, paramInt2) - 1;
/*      */     
/* 1650 */     while (j > i) {
/* 1651 */       int k = paramArrayOfChar[j];
/* 1652 */       paramArrayOfChar[j] = paramArrayOfChar[i];
/* 1653 */       paramArrayOfChar[i] = k;
/* 1654 */       j--;
/* 1655 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(double[] paramArrayOfDouble, int paramInt1, int paramInt2)
/*      */   {
/* 1679 */     if (paramArrayOfDouble == null) {
/* 1680 */       return;
/*      */     }
/* 1682 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1683 */     int j = Math.min(paramArrayOfDouble.length, paramInt2) - 1;
/*      */     
/* 1685 */     while (j > i) {
/* 1686 */       double d = paramArrayOfDouble[j];
/* 1687 */       paramArrayOfDouble[j] = paramArrayOfDouble[i];
/* 1688 */       paramArrayOfDouble[i] = d;
/* 1689 */       j--;
/* 1690 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*      */   {
/* 1714 */     if (paramArrayOfFloat == null) {
/* 1715 */       return;
/*      */     }
/* 1717 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1718 */     int j = Math.min(paramArrayOfFloat.length, paramInt2) - 1;
/*      */     
/* 1720 */     while (j > i) {
/* 1721 */       float f = paramArrayOfFloat[j];
/* 1722 */       paramArrayOfFloat[j] = paramArrayOfFloat[i];
/* 1723 */       paramArrayOfFloat[i] = f;
/* 1724 */       j--;
/* 1725 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 1749 */     if (paramArrayOfInt == null) {
/* 1750 */       return;
/*      */     }
/* 1752 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1753 */     int j = Math.min(paramArrayOfInt.length, paramInt2) - 1;
/*      */     
/* 1755 */     while (j > i) {
/* 1756 */       int k = paramArrayOfInt[j];
/* 1757 */       paramArrayOfInt[j] = paramArrayOfInt[i];
/* 1758 */       paramArrayOfInt[i] = k;
/* 1759 */       j--;
/* 1760 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(long[] paramArrayOfLong, int paramInt1, int paramInt2)
/*      */   {
/* 1784 */     if (paramArrayOfLong == null) {
/* 1785 */       return;
/*      */     }
/* 1787 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1788 */     int j = Math.min(paramArrayOfLong.length, paramInt2) - 1;
/*      */     
/* 1790 */     while (j > i) {
/* 1791 */       long l = paramArrayOfLong[j];
/* 1792 */       paramArrayOfLong[j] = paramArrayOfLong[i];
/* 1793 */       paramArrayOfLong[i] = l;
/* 1794 */       j--;
/* 1795 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(Object[] paramArrayOfObject, int paramInt1, int paramInt2)
/*      */   {
/* 1819 */     if (paramArrayOfObject == null) {
/* 1820 */       return;
/*      */     }
/* 1822 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1823 */     int j = Math.min(paramArrayOfObject.length, paramInt2) - 1;
/*      */     
/* 1825 */     while (j > i) {
/* 1826 */       Object localObject = paramArrayOfObject[j];
/* 1827 */       paramArrayOfObject[j] = paramArrayOfObject[i];
/* 1828 */       paramArrayOfObject[i] = localObject;
/* 1829 */       j--;
/* 1830 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void reverse(short[] paramArrayOfShort, int paramInt1, int paramInt2)
/*      */   {
/* 1854 */     if (paramArrayOfShort == null) {
/* 1855 */       return;
/*      */     }
/* 1857 */     int i = paramInt1 < 0 ? 0 : paramInt1;
/* 1858 */     int j = Math.min(paramArrayOfShort.length, paramInt2) - 1;
/*      */     
/* 1860 */     while (j > i) {
/* 1861 */       int k = paramArrayOfShort[j];
/* 1862 */       paramArrayOfShort[j] = paramArrayOfShort[i];
/* 1863 */       paramArrayOfShort[i] = k;
/* 1864 */       j--;
/* 1865 */       i++;
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
/*      */ 
/*      */   public static int indexOf(Object[] paramArrayOfObject, Object paramObject)
/*      */   {
/* 1885 */     return indexOf(paramArrayOfObject, paramObject, 0);
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
/*      */   public static int indexOf(Object[] paramArrayOfObject, Object paramObject, int paramInt)
/*      */   {
/* 1903 */     if (paramArrayOfObject == null) {
/* 1904 */       return -1;
/*      */     }
/* 1906 */     if (paramInt < 0)
/* 1907 */       paramInt = 0;
/*      */     int i;
/* 1909 */     if (paramObject == null) {
/* 1910 */       for (i = paramInt; i < paramArrayOfObject.length; i++) {
/* 1911 */         if (paramArrayOfObject[i] == null) {
/* 1912 */           return i;
/*      */         }
/*      */       }
/* 1915 */     } else if (paramArrayOfObject.getClass().getComponentType().isInstance(paramObject)) {
/* 1916 */       for (i = paramInt; i < paramArrayOfObject.length; i++) {
/* 1917 */         if (paramObject.equals(paramArrayOfObject[i])) {
/* 1918 */           return i;
/*      */         }
/*      */       }
/*      */     }
/* 1922 */     return -1;
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
/*      */   public static int lastIndexOf(Object[] paramArrayOfObject, Object paramObject)
/*      */   {
/* 1936 */     return lastIndexOf(paramArrayOfObject, paramObject, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(Object[] paramArrayOfObject, Object paramObject, int paramInt)
/*      */   {
/* 1954 */     if (paramArrayOfObject == null) {
/* 1955 */       return -1;
/*      */     }
/* 1957 */     if (paramInt < 0)
/* 1958 */       return -1;
/* 1959 */     if (paramInt >= paramArrayOfObject.length)
/* 1960 */       paramInt = paramArrayOfObject.length - 1;
/*      */     int i;
/* 1962 */     if (paramObject == null) {
/* 1963 */       for (i = paramInt; i >= 0; i--) {
/* 1964 */         if (paramArrayOfObject[i] == null) {
/* 1965 */           return i;
/*      */         }
/*      */       }
/* 1968 */     } else if (paramArrayOfObject.getClass().getComponentType().isInstance(paramObject)) {
/* 1969 */       for (i = paramInt; i >= 0; i--) {
/* 1970 */         if (paramObject.equals(paramArrayOfObject[i])) {
/* 1971 */           return i;
/*      */         }
/*      */       }
/*      */     }
/* 1975 */     return -1;
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
/*      */   public static boolean contains(Object[] paramArrayOfObject, Object paramObject)
/*      */   {
/* 1988 */     return indexOf(paramArrayOfObject, paramObject) != -1;
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
/*      */   public static int indexOf(long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 2004 */     return indexOf(paramArrayOfLong, paramLong, 0);
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
/*      */   public static int indexOf(long[] paramArrayOfLong, long paramLong, int paramInt)
/*      */   {
/* 2022 */     if (paramArrayOfLong == null) {
/* 2023 */       return -1;
/*      */     }
/* 2025 */     if (paramInt < 0) {
/* 2026 */       paramInt = 0;
/*      */     }
/* 2028 */     for (int i = paramInt; i < paramArrayOfLong.length; i++) {
/* 2029 */       if (paramLong == paramArrayOfLong[i]) {
/* 2030 */         return i;
/*      */       }
/*      */     }
/* 2033 */     return -1;
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
/*      */   public static int lastIndexOf(long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 2047 */     return lastIndexOf(paramArrayOfLong, paramLong, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(long[] paramArrayOfLong, long paramLong, int paramInt)
/*      */   {
/* 2065 */     if (paramArrayOfLong == null) {
/* 2066 */       return -1;
/*      */     }
/* 2068 */     if (paramInt < 0)
/* 2069 */       return -1;
/* 2070 */     if (paramInt >= paramArrayOfLong.length) {
/* 2071 */       paramInt = paramArrayOfLong.length - 1;
/*      */     }
/* 2073 */     for (int i = paramInt; i >= 0; i--) {
/* 2074 */       if (paramLong == paramArrayOfLong[i]) {
/* 2075 */         return i;
/*      */       }
/*      */     }
/* 2078 */     return -1;
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
/*      */   public static boolean contains(long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 2091 */     return indexOf(paramArrayOfLong, paramLong) != -1;
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
/*      */   public static int indexOf(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2107 */     return indexOf(paramArrayOfInt, paramInt, 0);
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
/*      */   public static int indexOf(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2125 */     if (paramArrayOfInt == null) {
/* 2126 */       return -1;
/*      */     }
/* 2128 */     if (paramInt2 < 0) {
/* 2129 */       paramInt2 = 0;
/*      */     }
/* 2131 */     for (int i = paramInt2; i < paramArrayOfInt.length; i++) {
/* 2132 */       if (paramInt1 == paramArrayOfInt[i]) {
/* 2133 */         return i;
/*      */       }
/*      */     }
/* 2136 */     return -1;
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
/*      */   public static int lastIndexOf(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2150 */     return lastIndexOf(paramArrayOfInt, paramInt, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2168 */     if (paramArrayOfInt == null) {
/* 2169 */       return -1;
/*      */     }
/* 2171 */     if (paramInt2 < 0)
/* 2172 */       return -1;
/* 2173 */     if (paramInt2 >= paramArrayOfInt.length) {
/* 2174 */       paramInt2 = paramArrayOfInt.length - 1;
/*      */     }
/* 2176 */     for (int i = paramInt2; i >= 0; i--) {
/* 2177 */       if (paramInt1 == paramArrayOfInt[i]) {
/* 2178 */         return i;
/*      */       }
/*      */     }
/* 2181 */     return -1;
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
/*      */   public static boolean contains(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2194 */     return indexOf(paramArrayOfInt, paramInt) != -1;
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
/*      */   public static int indexOf(short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 2210 */     return indexOf(paramArrayOfShort, paramShort, 0);
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
/*      */   public static int indexOf(short[] paramArrayOfShort, short paramShort, int paramInt)
/*      */   {
/* 2228 */     if (paramArrayOfShort == null) {
/* 2229 */       return -1;
/*      */     }
/* 2231 */     if (paramInt < 0) {
/* 2232 */       paramInt = 0;
/*      */     }
/* 2234 */     for (int i = paramInt; i < paramArrayOfShort.length; i++) {
/* 2235 */       if (paramShort == paramArrayOfShort[i]) {
/* 2236 */         return i;
/*      */       }
/*      */     }
/* 2239 */     return -1;
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
/*      */   public static int lastIndexOf(short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 2253 */     return lastIndexOf(paramArrayOfShort, paramShort, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(short[] paramArrayOfShort, short paramShort, int paramInt)
/*      */   {
/* 2271 */     if (paramArrayOfShort == null) {
/* 2272 */       return -1;
/*      */     }
/* 2274 */     if (paramInt < 0)
/* 2275 */       return -1;
/* 2276 */     if (paramInt >= paramArrayOfShort.length) {
/* 2277 */       paramInt = paramArrayOfShort.length - 1;
/*      */     }
/* 2279 */     for (int i = paramInt; i >= 0; i--) {
/* 2280 */       if (paramShort == paramArrayOfShort[i]) {
/* 2281 */         return i;
/*      */       }
/*      */     }
/* 2284 */     return -1;
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
/*      */   public static boolean contains(short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 2297 */     return indexOf(paramArrayOfShort, paramShort) != -1;
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
/*      */   public static int indexOf(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 2314 */     return indexOf(paramArrayOfChar, paramChar, 0);
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
/*      */   public static int indexOf(char[] paramArrayOfChar, char paramChar, int paramInt)
/*      */   {
/* 2333 */     if (paramArrayOfChar == null) {
/* 2334 */       return -1;
/*      */     }
/* 2336 */     if (paramInt < 0) {
/* 2337 */       paramInt = 0;
/*      */     }
/* 2339 */     for (int i = paramInt; i < paramArrayOfChar.length; i++) {
/* 2340 */       if (paramChar == paramArrayOfChar[i]) {
/* 2341 */         return i;
/*      */       }
/*      */     }
/* 2344 */     return -1;
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
/*      */   public static int lastIndexOf(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 2359 */     return lastIndexOf(paramArrayOfChar, paramChar, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(char[] paramArrayOfChar, char paramChar, int paramInt)
/*      */   {
/* 2378 */     if (paramArrayOfChar == null) {
/* 2379 */       return -1;
/*      */     }
/* 2381 */     if (paramInt < 0)
/* 2382 */       return -1;
/* 2383 */     if (paramInt >= paramArrayOfChar.length) {
/* 2384 */       paramInt = paramArrayOfChar.length - 1;
/*      */     }
/* 2386 */     for (int i = paramInt; i >= 0; i--) {
/* 2387 */       if (paramChar == paramArrayOfChar[i]) {
/* 2388 */         return i;
/*      */       }
/*      */     }
/* 2391 */     return -1;
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
/*      */   public static boolean contains(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 2405 */     return indexOf(paramArrayOfChar, paramChar) != -1;
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
/*      */   public static int indexOf(byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 2421 */     return indexOf(paramArrayOfByte, paramByte, 0);
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
/*      */   public static int indexOf(byte[] paramArrayOfByte, byte paramByte, int paramInt)
/*      */   {
/* 2439 */     if (paramArrayOfByte == null) {
/* 2440 */       return -1;
/*      */     }
/* 2442 */     if (paramInt < 0) {
/* 2443 */       paramInt = 0;
/*      */     }
/* 2445 */     for (int i = paramInt; i < paramArrayOfByte.length; i++) {
/* 2446 */       if (paramByte == paramArrayOfByte[i]) {
/* 2447 */         return i;
/*      */       }
/*      */     }
/* 2450 */     return -1;
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
/*      */   public static int lastIndexOf(byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 2464 */     return lastIndexOf(paramArrayOfByte, paramByte, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(byte[] paramArrayOfByte, byte paramByte, int paramInt)
/*      */   {
/* 2482 */     if (paramArrayOfByte == null) {
/* 2483 */       return -1;
/*      */     }
/* 2485 */     if (paramInt < 0)
/* 2486 */       return -1;
/* 2487 */     if (paramInt >= paramArrayOfByte.length) {
/* 2488 */       paramInt = paramArrayOfByte.length - 1;
/*      */     }
/* 2490 */     for (int i = paramInt; i >= 0; i--) {
/* 2491 */       if (paramByte == paramArrayOfByte[i]) {
/* 2492 */         return i;
/*      */       }
/*      */     }
/* 2495 */     return -1;
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
/*      */   public static boolean contains(byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 2508 */     return indexOf(paramArrayOfByte, paramByte) != -1;
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
/*      */   public static int indexOf(double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 2524 */     return indexOf(paramArrayOfDouble, paramDouble, 0);
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
/*      */   public static int indexOf(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2)
/*      */   {
/* 2541 */     return indexOf(paramArrayOfDouble, paramDouble1, 0, paramDouble2);
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
/*      */   public static int indexOf(double[] paramArrayOfDouble, double paramDouble, int paramInt)
/*      */   {
/* 2559 */     if (isEmpty(paramArrayOfDouble)) {
/* 2560 */       return -1;
/*      */     }
/* 2562 */     if (paramInt < 0) {
/* 2563 */       paramInt = 0;
/*      */     }
/* 2565 */     for (int i = paramInt; i < paramArrayOfDouble.length; i++) {
/* 2566 */       if (paramDouble == paramArrayOfDouble[i]) {
/* 2567 */         return i;
/*      */       }
/*      */     }
/* 2570 */     return -1;
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
/*      */   public static int indexOf(double[] paramArrayOfDouble, double paramDouble1, int paramInt, double paramDouble2)
/*      */   {
/* 2591 */     if (isEmpty(paramArrayOfDouble)) {
/* 2592 */       return -1;
/*      */     }
/* 2594 */     if (paramInt < 0) {
/* 2595 */       paramInt = 0;
/*      */     }
/* 2597 */     double d1 = paramDouble1 - paramDouble2;
/* 2598 */     double d2 = paramDouble1 + paramDouble2;
/* 2599 */     for (int i = paramInt; i < paramArrayOfDouble.length; i++) {
/* 2600 */       if ((paramArrayOfDouble[i] >= d1) && (paramArrayOfDouble[i] <= d2)) {
/* 2601 */         return i;
/*      */       }
/*      */     }
/* 2604 */     return -1;
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
/*      */   public static int lastIndexOf(double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 2618 */     return lastIndexOf(paramArrayOfDouble, paramDouble, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2)
/*      */   {
/* 2635 */     return lastIndexOf(paramArrayOfDouble, paramDouble1, Integer.MAX_VALUE, paramDouble2);
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
/*      */   public static int lastIndexOf(double[] paramArrayOfDouble, double paramDouble, int paramInt)
/*      */   {
/* 2653 */     if (isEmpty(paramArrayOfDouble)) {
/* 2654 */       return -1;
/*      */     }
/* 2656 */     if (paramInt < 0)
/* 2657 */       return -1;
/* 2658 */     if (paramInt >= paramArrayOfDouble.length) {
/* 2659 */       paramInt = paramArrayOfDouble.length - 1;
/*      */     }
/* 2661 */     for (int i = paramInt; i >= 0; i--) {
/* 2662 */       if (paramDouble == paramArrayOfDouble[i]) {
/* 2663 */         return i;
/*      */       }
/*      */     }
/* 2666 */     return -1;
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
/*      */   public static int lastIndexOf(double[] paramArrayOfDouble, double paramDouble1, int paramInt, double paramDouble2)
/*      */   {
/* 2687 */     if (isEmpty(paramArrayOfDouble)) {
/* 2688 */       return -1;
/*      */     }
/* 2690 */     if (paramInt < 0)
/* 2691 */       return -1;
/* 2692 */     if (paramInt >= paramArrayOfDouble.length) {
/* 2693 */       paramInt = paramArrayOfDouble.length - 1;
/*      */     }
/* 2695 */     double d1 = paramDouble1 - paramDouble2;
/* 2696 */     double d2 = paramDouble1 + paramDouble2;
/* 2697 */     for (int i = paramInt; i >= 0; i--) {
/* 2698 */       if ((paramArrayOfDouble[i] >= d1) && (paramArrayOfDouble[i] <= d2)) {
/* 2699 */         return i;
/*      */       }
/*      */     }
/* 2702 */     return -1;
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
/*      */   public static boolean contains(double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 2715 */     return indexOf(paramArrayOfDouble, paramDouble) != -1;
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
/*      */   public static boolean contains(double[] paramArrayOfDouble, double paramDouble1, double paramDouble2)
/*      */   {
/* 2732 */     return indexOf(paramArrayOfDouble, paramDouble1, 0, paramDouble2) != -1;
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
/*      */   public static int indexOf(float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 2748 */     return indexOf(paramArrayOfFloat, paramFloat, 0);
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
/*      */   public static int indexOf(float[] paramArrayOfFloat, float paramFloat, int paramInt)
/*      */   {
/* 2766 */     if (isEmpty(paramArrayOfFloat)) {
/* 2767 */       return -1;
/*      */     }
/* 2769 */     if (paramInt < 0) {
/* 2770 */       paramInt = 0;
/*      */     }
/* 2772 */     for (int i = paramInt; i < paramArrayOfFloat.length; i++) {
/* 2773 */       if (paramFloat == paramArrayOfFloat[i]) {
/* 2774 */         return i;
/*      */       }
/*      */     }
/* 2777 */     return -1;
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
/*      */   public static int lastIndexOf(float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 2791 */     return lastIndexOf(paramArrayOfFloat, paramFloat, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(float[] paramArrayOfFloat, float paramFloat, int paramInt)
/*      */   {
/* 2809 */     if (isEmpty(paramArrayOfFloat)) {
/* 2810 */       return -1;
/*      */     }
/* 2812 */     if (paramInt < 0)
/* 2813 */       return -1;
/* 2814 */     if (paramInt >= paramArrayOfFloat.length) {
/* 2815 */       paramInt = paramArrayOfFloat.length - 1;
/*      */     }
/* 2817 */     for (int i = paramInt; i >= 0; i--) {
/* 2818 */       if (paramFloat == paramArrayOfFloat[i]) {
/* 2819 */         return i;
/*      */       }
/*      */     }
/* 2822 */     return -1;
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
/*      */   public static boolean contains(float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 2835 */     return indexOf(paramArrayOfFloat, paramFloat) != -1;
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
/*      */   public static int indexOf(boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 2851 */     return indexOf(paramArrayOfBoolean, paramBoolean, 0);
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
/*      */   public static int indexOf(boolean[] paramArrayOfBoolean, boolean paramBoolean, int paramInt)
/*      */   {
/* 2870 */     if (isEmpty(paramArrayOfBoolean)) {
/* 2871 */       return -1;
/*      */     }
/* 2873 */     if (paramInt < 0) {
/* 2874 */       paramInt = 0;
/*      */     }
/* 2876 */     for (int i = paramInt; i < paramArrayOfBoolean.length; i++) {
/* 2877 */       if (paramBoolean == paramArrayOfBoolean[i]) {
/* 2878 */         return i;
/*      */       }
/*      */     }
/* 2881 */     return -1;
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
/*      */   public static int lastIndexOf(boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 2896 */     return lastIndexOf(paramArrayOfBoolean, paramBoolean, Integer.MAX_VALUE);
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
/*      */   public static int lastIndexOf(boolean[] paramArrayOfBoolean, boolean paramBoolean, int paramInt)
/*      */   {
/* 2914 */     if (isEmpty(paramArrayOfBoolean)) {
/* 2915 */       return -1;
/*      */     }
/* 2917 */     if (paramInt < 0)
/* 2918 */       return -1;
/* 2919 */     if (paramInt >= paramArrayOfBoolean.length) {
/* 2920 */       paramInt = paramArrayOfBoolean.length - 1;
/*      */     }
/* 2922 */     for (int i = paramInt; i >= 0; i--) {
/* 2923 */       if (paramBoolean == paramArrayOfBoolean[i]) {
/* 2924 */         return i;
/*      */       }
/*      */     }
/* 2927 */     return -1;
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
/*      */   public static boolean contains(boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 2940 */     return indexOf(paramArrayOfBoolean, paramBoolean) != -1;
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
/*      */   public static char[] toPrimitive(Character[] paramArrayOfCharacter)
/*      */   {
/* 2958 */     if (paramArrayOfCharacter == null)
/* 2959 */       return null;
/* 2960 */     if (paramArrayOfCharacter.length == 0) {
/* 2961 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 2963 */     char[] arrayOfChar = new char[paramArrayOfCharacter.length];
/* 2964 */     for (int i = 0; i < paramArrayOfCharacter.length; i++) {
/* 2965 */       arrayOfChar[i] = paramArrayOfCharacter[i].charValue();
/*      */     }
/* 2967 */     return arrayOfChar;
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
/*      */   public static char[] toPrimitive(Character[] paramArrayOfCharacter, char paramChar)
/*      */   {
/* 2980 */     if (paramArrayOfCharacter == null)
/* 2981 */       return null;
/* 2982 */     if (paramArrayOfCharacter.length == 0) {
/* 2983 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 2985 */     char[] arrayOfChar = new char[paramArrayOfCharacter.length];
/* 2986 */     for (int i = 0; i < paramArrayOfCharacter.length; i++) {
/* 2987 */       Character localCharacter = paramArrayOfCharacter[i];
/* 2988 */       arrayOfChar[i] = (localCharacter == null ? paramChar : localCharacter.charValue());
/*      */     }
/* 2990 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Character[] toObject(char[] paramArrayOfChar)
/*      */   {
/* 3002 */     if (paramArrayOfChar == null)
/* 3003 */       return null;
/* 3004 */     if (paramArrayOfChar.length == 0) {
/* 3005 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/* 3007 */     Character[] arrayOfCharacter = new Character[paramArrayOfChar.length];
/* 3008 */     for (int i = 0; i < paramArrayOfChar.length; i++) {
/* 3009 */       arrayOfCharacter[i] = Character.valueOf(paramArrayOfChar[i]);
/*      */     }
/* 3011 */     return arrayOfCharacter;
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
/*      */   public static long[] toPrimitive(Long[] paramArrayOfLong)
/*      */   {
/* 3026 */     if (paramArrayOfLong == null)
/* 3027 */       return null;
/* 3028 */     if (paramArrayOfLong.length == 0) {
/* 3029 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 3031 */     long[] arrayOfLong = new long[paramArrayOfLong.length];
/* 3032 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/* 3033 */       arrayOfLong[i] = paramArrayOfLong[i].longValue();
/*      */     }
/* 3035 */     return arrayOfLong;
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
/*      */   public static long[] toPrimitive(Long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 3048 */     if (paramArrayOfLong == null)
/* 3049 */       return null;
/* 3050 */     if (paramArrayOfLong.length == 0) {
/* 3051 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 3053 */     long[] arrayOfLong = new long[paramArrayOfLong.length];
/* 3054 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/* 3055 */       Long localLong = paramArrayOfLong[i];
/* 3056 */       arrayOfLong[i] = (localLong == null ? paramLong : localLong.longValue());
/*      */     }
/* 3058 */     return arrayOfLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Long[] toObject(long[] paramArrayOfLong)
/*      */   {
/* 3070 */     if (paramArrayOfLong == null)
/* 3071 */       return null;
/* 3072 */     if (paramArrayOfLong.length == 0) {
/* 3073 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/* 3075 */     Long[] arrayOfLong = new Long[paramArrayOfLong.length];
/* 3076 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/* 3077 */       arrayOfLong[i] = Long.valueOf(paramArrayOfLong[i]);
/*      */     }
/* 3079 */     return arrayOfLong;
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
/*      */   public static int[] toPrimitive(Integer[] paramArrayOfInteger)
/*      */   {
/* 3094 */     if (paramArrayOfInteger == null)
/* 3095 */       return null;
/* 3096 */     if (paramArrayOfInteger.length == 0) {
/* 3097 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 3099 */     int[] arrayOfInt = new int[paramArrayOfInteger.length];
/* 3100 */     for (int i = 0; i < paramArrayOfInteger.length; i++) {
/* 3101 */       arrayOfInt[i] = paramArrayOfInteger[i].intValue();
/*      */     }
/* 3103 */     return arrayOfInt;
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
/*      */   public static int[] toPrimitive(Integer[] paramArrayOfInteger, int paramInt)
/*      */   {
/* 3116 */     if (paramArrayOfInteger == null)
/* 3117 */       return null;
/* 3118 */     if (paramArrayOfInteger.length == 0) {
/* 3119 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 3121 */     int[] arrayOfInt = new int[paramArrayOfInteger.length];
/* 3122 */     for (int i = 0; i < paramArrayOfInteger.length; i++) {
/* 3123 */       Integer localInteger = paramArrayOfInteger[i];
/* 3124 */       arrayOfInt[i] = (localInteger == null ? paramInt : localInteger.intValue());
/*      */     }
/* 3126 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Integer[] toObject(int[] paramArrayOfInt)
/*      */   {
/* 3138 */     if (paramArrayOfInt == null)
/* 3139 */       return null;
/* 3140 */     if (paramArrayOfInt.length == 0) {
/* 3141 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/* 3143 */     Integer[] arrayOfInteger = new Integer[paramArrayOfInt.length];
/* 3144 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 3145 */       arrayOfInteger[i] = Integer.valueOf(paramArrayOfInt[i]);
/*      */     }
/* 3147 */     return arrayOfInteger;
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
/*      */   public static short[] toPrimitive(Short[] paramArrayOfShort)
/*      */   {
/* 3162 */     if (paramArrayOfShort == null)
/* 3163 */       return null;
/* 3164 */     if (paramArrayOfShort.length == 0) {
/* 3165 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 3167 */     short[] arrayOfShort = new short[paramArrayOfShort.length];
/* 3168 */     for (int i = 0; i < paramArrayOfShort.length; i++) {
/* 3169 */       arrayOfShort[i] = paramArrayOfShort[i].shortValue();
/*      */     }
/* 3171 */     return arrayOfShort;
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
/*      */   public static short[] toPrimitive(Short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 3184 */     if (paramArrayOfShort == null)
/* 3185 */       return null;
/* 3186 */     if (paramArrayOfShort.length == 0) {
/* 3187 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 3189 */     short[] arrayOfShort = new short[paramArrayOfShort.length];
/* 3190 */     for (int i = 0; i < paramArrayOfShort.length; i++) {
/* 3191 */       Short localShort = paramArrayOfShort[i];
/* 3192 */       arrayOfShort[i] = (localShort == null ? paramShort : localShort.shortValue());
/*      */     }
/* 3194 */     return arrayOfShort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Short[] toObject(short[] paramArrayOfShort)
/*      */   {
/* 3206 */     if (paramArrayOfShort == null)
/* 3207 */       return null;
/* 3208 */     if (paramArrayOfShort.length == 0) {
/* 3209 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/* 3211 */     Short[] arrayOfShort = new Short[paramArrayOfShort.length];
/* 3212 */     for (int i = 0; i < paramArrayOfShort.length; i++) {
/* 3213 */       arrayOfShort[i] = Short.valueOf(paramArrayOfShort[i]);
/*      */     }
/* 3215 */     return arrayOfShort;
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
/*      */   public static byte[] toPrimitive(Byte[] paramArrayOfByte)
/*      */   {
/* 3230 */     if (paramArrayOfByte == null)
/* 3231 */       return null;
/* 3232 */     if (paramArrayOfByte.length == 0) {
/* 3233 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 3235 */     byte[] arrayOfByte = new byte[paramArrayOfByte.length];
/* 3236 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 3237 */       arrayOfByte[i] = paramArrayOfByte[i].byteValue();
/*      */     }
/* 3239 */     return arrayOfByte;
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
/*      */   public static byte[] toPrimitive(Byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 3252 */     if (paramArrayOfByte == null)
/* 3253 */       return null;
/* 3254 */     if (paramArrayOfByte.length == 0) {
/* 3255 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 3257 */     byte[] arrayOfByte = new byte[paramArrayOfByte.length];
/* 3258 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 3259 */       Byte localByte = paramArrayOfByte[i];
/* 3260 */       arrayOfByte[i] = (localByte == null ? paramByte : localByte.byteValue());
/*      */     }
/* 3262 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Byte[] toObject(byte[] paramArrayOfByte)
/*      */   {
/* 3274 */     if (paramArrayOfByte == null)
/* 3275 */       return null;
/* 3276 */     if (paramArrayOfByte.length == 0) {
/* 3277 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/* 3279 */     Byte[] arrayOfByte = new Byte[paramArrayOfByte.length];
/* 3280 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 3281 */       arrayOfByte[i] = Byte.valueOf(paramArrayOfByte[i]);
/*      */     }
/* 3283 */     return arrayOfByte;
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
/*      */   public static double[] toPrimitive(Double[] paramArrayOfDouble)
/*      */   {
/* 3298 */     if (paramArrayOfDouble == null)
/* 3299 */       return null;
/* 3300 */     if (paramArrayOfDouble.length == 0) {
/* 3301 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 3303 */     double[] arrayOfDouble = new double[paramArrayOfDouble.length];
/* 3304 */     for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 3305 */       arrayOfDouble[i] = paramArrayOfDouble[i].doubleValue();
/*      */     }
/* 3307 */     return arrayOfDouble;
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
/*      */   public static double[] toPrimitive(Double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 3320 */     if (paramArrayOfDouble == null)
/* 3321 */       return null;
/* 3322 */     if (paramArrayOfDouble.length == 0) {
/* 3323 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 3325 */     double[] arrayOfDouble = new double[paramArrayOfDouble.length];
/* 3326 */     for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 3327 */       Double localDouble = paramArrayOfDouble[i];
/* 3328 */       arrayOfDouble[i] = (localDouble == null ? paramDouble : localDouble.doubleValue());
/*      */     }
/* 3330 */     return arrayOfDouble;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Double[] toObject(double[] paramArrayOfDouble)
/*      */   {
/* 3342 */     if (paramArrayOfDouble == null)
/* 3343 */       return null;
/* 3344 */     if (paramArrayOfDouble.length == 0) {
/* 3345 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/* 3347 */     Double[] arrayOfDouble = new Double[paramArrayOfDouble.length];
/* 3348 */     for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 3349 */       arrayOfDouble[i] = Double.valueOf(paramArrayOfDouble[i]);
/*      */     }
/* 3351 */     return arrayOfDouble;
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
/*      */   public static float[] toPrimitive(Float[] paramArrayOfFloat)
/*      */   {
/* 3366 */     if (paramArrayOfFloat == null)
/* 3367 */       return null;
/* 3368 */     if (paramArrayOfFloat.length == 0) {
/* 3369 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 3371 */     float[] arrayOfFloat = new float[paramArrayOfFloat.length];
/* 3372 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 3373 */       arrayOfFloat[i] = paramArrayOfFloat[i].floatValue();
/*      */     }
/* 3375 */     return arrayOfFloat;
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
/*      */   public static float[] toPrimitive(Float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 3388 */     if (paramArrayOfFloat == null)
/* 3389 */       return null;
/* 3390 */     if (paramArrayOfFloat.length == 0) {
/* 3391 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 3393 */     float[] arrayOfFloat = new float[paramArrayOfFloat.length];
/* 3394 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 3395 */       Float localFloat = paramArrayOfFloat[i];
/* 3396 */       arrayOfFloat[i] = (localFloat == null ? paramFloat : localFloat.floatValue());
/*      */     }
/* 3398 */     return arrayOfFloat;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Float[] toObject(float[] paramArrayOfFloat)
/*      */   {
/* 3410 */     if (paramArrayOfFloat == null)
/* 3411 */       return null;
/* 3412 */     if (paramArrayOfFloat.length == 0) {
/* 3413 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/* 3415 */     Float[] arrayOfFloat = new Float[paramArrayOfFloat.length];
/* 3416 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 3417 */       arrayOfFloat[i] = Float.valueOf(paramArrayOfFloat[i]);
/*      */     }
/* 3419 */     return arrayOfFloat;
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
/*      */   public static boolean[] toPrimitive(Boolean[] paramArrayOfBoolean)
/*      */   {
/* 3434 */     if (paramArrayOfBoolean == null)
/* 3435 */       return null;
/* 3436 */     if (paramArrayOfBoolean.length == 0) {
/* 3437 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 3439 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
/* 3440 */     for (int i = 0; i < paramArrayOfBoolean.length; i++) {
/* 3441 */       arrayOfBoolean[i] = paramArrayOfBoolean[i].booleanValue();
/*      */     }
/* 3443 */     return arrayOfBoolean;
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
/*      */   public static boolean[] toPrimitive(Boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 3456 */     if (paramArrayOfBoolean == null)
/* 3457 */       return null;
/* 3458 */     if (paramArrayOfBoolean.length == 0) {
/* 3459 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 3461 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
/* 3462 */     for (int i = 0; i < paramArrayOfBoolean.length; i++) {
/* 3463 */       Boolean localBoolean = paramArrayOfBoolean[i];
/* 3464 */       arrayOfBoolean[i] = (localBoolean == null ? paramBoolean : localBoolean.booleanValue());
/*      */     }
/* 3466 */     return arrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Boolean[] toObject(boolean[] paramArrayOfBoolean)
/*      */   {
/* 3478 */     if (paramArrayOfBoolean == null)
/* 3479 */       return null;
/* 3480 */     if (paramArrayOfBoolean.length == 0) {
/* 3481 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/* 3483 */     Boolean[] arrayOfBoolean = new Boolean[paramArrayOfBoolean.length];
/* 3484 */     for (int i = 0; i < paramArrayOfBoolean.length; i++) {
/* 3485 */       arrayOfBoolean[i] = (paramArrayOfBoolean[i] != 0 ? Boolean.TRUE : Boolean.FALSE);
/*      */     }
/* 3487 */     return arrayOfBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(Object[] paramArrayOfObject)
/*      */   {
/* 3499 */     return (paramArrayOfObject == null) || (paramArrayOfObject.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(long[] paramArrayOfLong)
/*      */   {
/* 3510 */     return (paramArrayOfLong == null) || (paramArrayOfLong.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(int[] paramArrayOfInt)
/*      */   {
/* 3521 */     return (paramArrayOfInt == null) || (paramArrayOfInt.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(short[] paramArrayOfShort)
/*      */   {
/* 3532 */     return (paramArrayOfShort == null) || (paramArrayOfShort.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(char[] paramArrayOfChar)
/*      */   {
/* 3543 */     return (paramArrayOfChar == null) || (paramArrayOfChar.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(byte[] paramArrayOfByte)
/*      */   {
/* 3554 */     return (paramArrayOfByte == null) || (paramArrayOfByte.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(double[] paramArrayOfDouble)
/*      */   {
/* 3565 */     return (paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(float[] paramArrayOfFloat)
/*      */   {
/* 3576 */     return (paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isEmpty(boolean[] paramArrayOfBoolean)
/*      */   {
/* 3587 */     return (paramArrayOfBoolean == null) || (paramArrayOfBoolean.length == 0);
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
/*      */   public static <T> boolean isNotEmpty(T[] paramArrayOfT)
/*      */   {
/* 3600 */     return (paramArrayOfT != null) && (paramArrayOfT.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(long[] paramArrayOfLong)
/*      */   {
/* 3611 */     return (paramArrayOfLong != null) && (paramArrayOfLong.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(int[] paramArrayOfInt)
/*      */   {
/* 3622 */     return (paramArrayOfInt != null) && (paramArrayOfInt.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(short[] paramArrayOfShort)
/*      */   {
/* 3633 */     return (paramArrayOfShort != null) && (paramArrayOfShort.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(char[] paramArrayOfChar)
/*      */   {
/* 3644 */     return (paramArrayOfChar != null) && (paramArrayOfChar.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(byte[] paramArrayOfByte)
/*      */   {
/* 3655 */     return (paramArrayOfByte != null) && (paramArrayOfByte.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(double[] paramArrayOfDouble)
/*      */   {
/* 3666 */     return (paramArrayOfDouble != null) && (paramArrayOfDouble.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(float[] paramArrayOfFloat)
/*      */   {
/* 3677 */     return (paramArrayOfFloat != null) && (paramArrayOfFloat.length != 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isNotEmpty(boolean[] paramArrayOfBoolean)
/*      */   {
/* 3688 */     return (paramArrayOfBoolean != null) && (paramArrayOfBoolean.length != 0);
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
/*      */   public static <T> T[] addAll(T[] paramArrayOfT1, T... paramVarArgs)
/*      */   {
/* 3716 */     if (paramArrayOfT1 == null)
/* 3717 */       return clone(paramVarArgs);
/* 3718 */     if (paramVarArgs == null) {
/* 3719 */       return clone(paramArrayOfT1);
/*      */     }
/* 3721 */     Class localClass1 = paramArrayOfT1.getClass().getComponentType();
/*      */     
/*      */ 
/* 3724 */     Object[] arrayOfObject = (Object[])Array.newInstance(localClass1, paramArrayOfT1.length + paramVarArgs.length);
/* 3725 */     System.arraycopy(paramArrayOfT1, 0, arrayOfObject, 0, paramArrayOfT1.length);
/*      */     try {
/* 3727 */       System.arraycopy(paramVarArgs, 0, arrayOfObject, paramArrayOfT1.length, paramVarArgs.length);
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (ArrayStoreException localArrayStoreException)
/*      */     {
/*      */ 
/*      */ 
/* 3735 */       Class localClass2 = paramVarArgs.getClass().getComponentType();
/* 3736 */       if (!localClass1.isAssignableFrom(localClass2)) {
/* 3737 */         throw new IllegalArgumentException("Cannot store " + localClass2.getName() + " in an array of " + localClass1.getName(), localArrayStoreException);
/*      */       }
/*      */       
/* 3740 */       throw localArrayStoreException;
/*      */     }
/* 3742 */     return arrayOfObject;
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
/*      */   public static boolean[] addAll(boolean[] paramArrayOfBoolean1, boolean... paramVarArgs)
/*      */   {
/* 3763 */     if (paramArrayOfBoolean1 == null)
/* 3764 */       return clone(paramVarArgs);
/* 3765 */     if (paramVarArgs == null) {
/* 3766 */       return clone(paramArrayOfBoolean1);
/*      */     }
/* 3768 */     boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean1.length + paramVarArgs.length];
/* 3769 */     System.arraycopy(paramArrayOfBoolean1, 0, arrayOfBoolean, 0, paramArrayOfBoolean1.length);
/* 3770 */     System.arraycopy(paramVarArgs, 0, arrayOfBoolean, paramArrayOfBoolean1.length, paramVarArgs.length);
/* 3771 */     return arrayOfBoolean;
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
/*      */   public static char[] addAll(char[] paramArrayOfChar1, char... paramVarArgs)
/*      */   {
/* 3792 */     if (paramArrayOfChar1 == null)
/* 3793 */       return clone(paramVarArgs);
/* 3794 */     if (paramVarArgs == null) {
/* 3795 */       return clone(paramArrayOfChar1);
/*      */     }
/* 3797 */     char[] arrayOfChar = new char[paramArrayOfChar1.length + paramVarArgs.length];
/* 3798 */     System.arraycopy(paramArrayOfChar1, 0, arrayOfChar, 0, paramArrayOfChar1.length);
/* 3799 */     System.arraycopy(paramVarArgs, 0, arrayOfChar, paramArrayOfChar1.length, paramVarArgs.length);
/* 3800 */     return arrayOfChar;
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
/*      */   public static byte[] addAll(byte[] paramArrayOfByte1, byte... paramVarArgs)
/*      */   {
/* 3821 */     if (paramArrayOfByte1 == null)
/* 3822 */       return clone(paramVarArgs);
/* 3823 */     if (paramVarArgs == null) {
/* 3824 */       return clone(paramArrayOfByte1);
/*      */     }
/* 3826 */     byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramVarArgs.length];
/* 3827 */     System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
/* 3828 */     System.arraycopy(paramVarArgs, 0, arrayOfByte, paramArrayOfByte1.length, paramVarArgs.length);
/* 3829 */     return arrayOfByte;
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
/*      */   public static short[] addAll(short[] paramArrayOfShort1, short... paramVarArgs)
/*      */   {
/* 3850 */     if (paramArrayOfShort1 == null)
/* 3851 */       return clone(paramVarArgs);
/* 3852 */     if (paramVarArgs == null) {
/* 3853 */       return clone(paramArrayOfShort1);
/*      */     }
/* 3855 */     short[] arrayOfShort = new short[paramArrayOfShort1.length + paramVarArgs.length];
/* 3856 */     System.arraycopy(paramArrayOfShort1, 0, arrayOfShort, 0, paramArrayOfShort1.length);
/* 3857 */     System.arraycopy(paramVarArgs, 0, arrayOfShort, paramArrayOfShort1.length, paramVarArgs.length);
/* 3858 */     return arrayOfShort;
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
/*      */   public static int[] addAll(int[] paramArrayOfInt1, int... paramVarArgs)
/*      */   {
/* 3879 */     if (paramArrayOfInt1 == null)
/* 3880 */       return clone(paramVarArgs);
/* 3881 */     if (paramVarArgs == null) {
/* 3882 */       return clone(paramArrayOfInt1);
/*      */     }
/* 3884 */     int[] arrayOfInt = new int[paramArrayOfInt1.length + paramVarArgs.length];
/* 3885 */     System.arraycopy(paramArrayOfInt1, 0, arrayOfInt, 0, paramArrayOfInt1.length);
/* 3886 */     System.arraycopy(paramVarArgs, 0, arrayOfInt, paramArrayOfInt1.length, paramVarArgs.length);
/* 3887 */     return arrayOfInt;
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
/*      */   public static long[] addAll(long[] paramArrayOfLong1, long... paramVarArgs)
/*      */   {
/* 3908 */     if (paramArrayOfLong1 == null)
/* 3909 */       return clone(paramVarArgs);
/* 3910 */     if (paramVarArgs == null) {
/* 3911 */       return clone(paramArrayOfLong1);
/*      */     }
/* 3913 */     long[] arrayOfLong = new long[paramArrayOfLong1.length + paramVarArgs.length];
/* 3914 */     System.arraycopy(paramArrayOfLong1, 0, arrayOfLong, 0, paramArrayOfLong1.length);
/* 3915 */     System.arraycopy(paramVarArgs, 0, arrayOfLong, paramArrayOfLong1.length, paramVarArgs.length);
/* 3916 */     return arrayOfLong;
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
/*      */   public static float[] addAll(float[] paramArrayOfFloat1, float... paramVarArgs)
/*      */   {
/* 3937 */     if (paramArrayOfFloat1 == null)
/* 3938 */       return clone(paramVarArgs);
/* 3939 */     if (paramVarArgs == null) {
/* 3940 */       return clone(paramArrayOfFloat1);
/*      */     }
/* 3942 */     float[] arrayOfFloat = new float[paramArrayOfFloat1.length + paramVarArgs.length];
/* 3943 */     System.arraycopy(paramArrayOfFloat1, 0, arrayOfFloat, 0, paramArrayOfFloat1.length);
/* 3944 */     System.arraycopy(paramVarArgs, 0, arrayOfFloat, paramArrayOfFloat1.length, paramVarArgs.length);
/* 3945 */     return arrayOfFloat;
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
/*      */   public static double[] addAll(double[] paramArrayOfDouble1, double... paramVarArgs)
/*      */   {
/* 3966 */     if (paramArrayOfDouble1 == null)
/* 3967 */       return clone(paramVarArgs);
/* 3968 */     if (paramVarArgs == null) {
/* 3969 */       return clone(paramArrayOfDouble1);
/*      */     }
/* 3971 */     double[] arrayOfDouble = new double[paramArrayOfDouble1.length + paramVarArgs.length];
/* 3972 */     System.arraycopy(paramArrayOfDouble1, 0, arrayOfDouble, 0, paramArrayOfDouble1.length);
/* 3973 */     System.arraycopy(paramVarArgs, 0, arrayOfDouble, paramArrayOfDouble1.length, paramVarArgs.length);
/* 3974 */     return arrayOfDouble;
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
/*      */   public static <T> T[] add(T[] paramArrayOfT, T paramT)
/*      */   {
/*      */     Class localClass;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4008 */     if (paramArrayOfT != null) {
/* 4009 */       localClass = paramArrayOfT.getClass();
/* 4010 */     } else if (paramT != null) {
/* 4011 */       localClass = paramT.getClass();
/*      */     } else {
/* 4013 */       throw new IllegalArgumentException("Arguments cannot both be null");
/*      */     }
/*      */     
/*      */ 
/* 4017 */     Object[] arrayOfObject = (Object[])copyArrayGrow1(paramArrayOfT, localClass);
/* 4018 */     arrayOfObject[(arrayOfObject.length - 1)] = paramT;
/* 4019 */     return arrayOfObject;
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
/*      */   public static boolean[] add(boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 4044 */     boolean[] arrayOfBoolean = (boolean[])copyArrayGrow1(paramArrayOfBoolean, Boolean.TYPE);
/* 4045 */     arrayOfBoolean[(arrayOfBoolean.length - 1)] = paramBoolean;
/* 4046 */     return arrayOfBoolean;
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
/*      */   public static byte[] add(byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 4071 */     byte[] arrayOfByte = (byte[])copyArrayGrow1(paramArrayOfByte, Byte.TYPE);
/* 4072 */     arrayOfByte[(arrayOfByte.length - 1)] = paramByte;
/* 4073 */     return arrayOfByte;
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
/*      */   public static char[] add(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 4098 */     char[] arrayOfChar = (char[])copyArrayGrow1(paramArrayOfChar, Character.TYPE);
/* 4099 */     arrayOfChar[(arrayOfChar.length - 1)] = paramChar;
/* 4100 */     return arrayOfChar;
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
/*      */   public static double[] add(double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 4125 */     double[] arrayOfDouble = (double[])copyArrayGrow1(paramArrayOfDouble, Double.TYPE);
/* 4126 */     arrayOfDouble[(arrayOfDouble.length - 1)] = paramDouble;
/* 4127 */     return arrayOfDouble;
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
/*      */   public static float[] add(float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 4152 */     float[] arrayOfFloat = (float[])copyArrayGrow1(paramArrayOfFloat, Float.TYPE);
/* 4153 */     arrayOfFloat[(arrayOfFloat.length - 1)] = paramFloat;
/* 4154 */     return arrayOfFloat;
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
/*      */   public static int[] add(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 4179 */     int[] arrayOfInt = (int[])copyArrayGrow1(paramArrayOfInt, Integer.TYPE);
/* 4180 */     arrayOfInt[(arrayOfInt.length - 1)] = paramInt;
/* 4181 */     return arrayOfInt;
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
/*      */   public static long[] add(long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 4206 */     long[] arrayOfLong = (long[])copyArrayGrow1(paramArrayOfLong, Long.TYPE);
/* 4207 */     arrayOfLong[(arrayOfLong.length - 1)] = paramLong;
/* 4208 */     return arrayOfLong;
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
/*      */   public static short[] add(short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 4233 */     short[] arrayOfShort = (short[])copyArrayGrow1(paramArrayOfShort, Short.TYPE);
/* 4234 */     arrayOfShort[(arrayOfShort.length - 1)] = paramShort;
/* 4235 */     return arrayOfShort;
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
/*      */   private static Object copyArrayGrow1(Object paramObject, Class<?> paramClass)
/*      */   {
/* 4248 */     if (paramObject != null) {
/* 4249 */       int i = Array.getLength(paramObject);
/* 4250 */       Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), i + 1);
/* 4251 */       System.arraycopy(paramObject, 0, localObject, 0, i);
/* 4252 */       return localObject;
/*      */     }
/* 4254 */     return Array.newInstance(paramClass, 1);
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
/*      */   public static <T> T[] add(T[] paramArrayOfT, int paramInt, T paramT)
/*      */   {
/* 4287 */     Class localClass = null;
/* 4288 */     if (paramArrayOfT != null) {
/* 4289 */       localClass = paramArrayOfT.getClass().getComponentType();
/* 4290 */     } else if (paramT != null) {
/* 4291 */       localClass = paramT.getClass();
/*      */     } else {
/* 4293 */       throw new IllegalArgumentException("Array and element cannot both be null");
/*      */     }
/*      */     
/* 4296 */     Object[] arrayOfObject = (Object[])add(paramArrayOfT, paramInt, paramT, localClass);
/* 4297 */     return arrayOfObject;
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
/*      */   public static boolean[] add(boolean[] paramArrayOfBoolean, int paramInt, boolean paramBoolean)
/*      */   {
/* 4327 */     return (boolean[])add(paramArrayOfBoolean, paramInt, Boolean.valueOf(paramBoolean), Boolean.TYPE);
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
/*      */   public static char[] add(char[] paramArrayOfChar, int paramInt, char paramChar)
/*      */   {
/* 4359 */     return (char[])add(paramArrayOfChar, paramInt, Character.valueOf(paramChar), Character.TYPE);
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
/*      */   public static byte[] add(byte[] paramArrayOfByte, int paramInt, byte paramByte)
/*      */   {
/* 4390 */     return (byte[])add(paramArrayOfByte, paramInt, Byte.valueOf(paramByte), Byte.TYPE);
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
/*      */   public static short[] add(short[] paramArrayOfShort, int paramInt, short paramShort)
/*      */   {
/* 4421 */     return (short[])add(paramArrayOfShort, paramInt, Short.valueOf(paramShort), Short.TYPE);
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
/*      */   public static int[] add(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 4452 */     return (int[])add(paramArrayOfInt, paramInt1, Integer.valueOf(paramInt2), Integer.TYPE);
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
/*      */   public static long[] add(long[] paramArrayOfLong, int paramInt, long paramLong)
/*      */   {
/* 4483 */     return (long[])add(paramArrayOfLong, paramInt, Long.valueOf(paramLong), Long.TYPE);
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
/*      */   public static float[] add(float[] paramArrayOfFloat, int paramInt, float paramFloat)
/*      */   {
/* 4514 */     return (float[])add(paramArrayOfFloat, paramInt, Float.valueOf(paramFloat), Float.TYPE);
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
/*      */   public static double[] add(double[] paramArrayOfDouble, int paramInt, double paramDouble)
/*      */   {
/* 4545 */     return (double[])add(paramArrayOfDouble, paramInt, Double.valueOf(paramDouble), Double.TYPE);
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
/*      */   private static Object add(Object paramObject1, int paramInt, Object paramObject2, Class<?> paramClass)
/*      */   {
/* 4560 */     if (paramObject1 == null) {
/* 4561 */       if (paramInt != 0) {
/* 4562 */         throw new IndexOutOfBoundsException("Index: " + paramInt + ", Length: 0");
/*      */       }
/* 4564 */       Object localObject1 = Array.newInstance(paramClass, 1);
/* 4565 */       Array.set(localObject1, 0, paramObject2);
/* 4566 */       return localObject1;
/*      */     }
/* 4568 */     int i = Array.getLength(paramObject1);
/* 4569 */     if ((paramInt > i) || (paramInt < 0)) {
/* 4570 */       throw new IndexOutOfBoundsException("Index: " + paramInt + ", Length: " + i);
/*      */     }
/* 4572 */     Object localObject2 = Array.newInstance(paramClass, i + 1);
/* 4573 */     System.arraycopy(paramObject1, 0, localObject2, 0, paramInt);
/* 4574 */     Array.set(localObject2, paramInt, paramObject2);
/* 4575 */     if (paramInt < i) {
/* 4576 */       System.arraycopy(paramObject1, paramInt, localObject2, paramInt + 1, i - paramInt);
/*      */     }
/* 4578 */     return localObject2;
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
/*      */   public static <T> T[] remove(T[] paramArrayOfT, int paramInt)
/*      */   {
/* 4612 */     return (Object[])remove(paramArrayOfT, paramInt);
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
/*      */   public static <T> T[] removeElement(T[] paramArrayOfT, Object paramObject)
/*      */   {
/* 4642 */     int i = indexOf(paramArrayOfT, paramObject);
/* 4643 */     if (i == -1) {
/* 4644 */       return clone(paramArrayOfT);
/*      */     }
/* 4646 */     return remove(paramArrayOfT, i);
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
/*      */   public static boolean[] remove(boolean[] paramArrayOfBoolean, int paramInt)
/*      */   {
/* 4678 */     return (boolean[])remove(paramArrayOfBoolean, paramInt);
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
/*      */   public static boolean[] removeElement(boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/* 4707 */     int i = indexOf(paramArrayOfBoolean, paramBoolean);
/* 4708 */     if (i == -1) {
/* 4709 */       return clone(paramArrayOfBoolean);
/*      */     }
/* 4711 */     return remove(paramArrayOfBoolean, i);
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
/*      */   public static byte[] remove(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 4743 */     return (byte[])remove(paramArrayOfByte, paramInt);
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
/*      */   public static byte[] removeElement(byte[] paramArrayOfByte, byte paramByte)
/*      */   {
/* 4772 */     int i = indexOf(paramArrayOfByte, paramByte);
/* 4773 */     if (i == -1) {
/* 4774 */       return clone(paramArrayOfByte);
/*      */     }
/* 4776 */     return remove(paramArrayOfByte, i);
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
/*      */   public static char[] remove(char[] paramArrayOfChar, int paramInt)
/*      */   {
/* 4808 */     return (char[])remove(paramArrayOfChar, paramInt);
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
/*      */   public static char[] removeElement(char[] paramArrayOfChar, char paramChar)
/*      */   {
/* 4837 */     int i = indexOf(paramArrayOfChar, paramChar);
/* 4838 */     if (i == -1) {
/* 4839 */       return clone(paramArrayOfChar);
/*      */     }
/* 4841 */     return remove(paramArrayOfChar, i);
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
/*      */   public static double[] remove(double[] paramArrayOfDouble, int paramInt)
/*      */   {
/* 4873 */     return (double[])remove(paramArrayOfDouble, paramInt);
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
/*      */   public static double[] removeElement(double[] paramArrayOfDouble, double paramDouble)
/*      */   {
/* 4902 */     int i = indexOf(paramArrayOfDouble, paramDouble);
/* 4903 */     if (i == -1) {
/* 4904 */       return clone(paramArrayOfDouble);
/*      */     }
/* 4906 */     return remove(paramArrayOfDouble, i);
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
/*      */   public static float[] remove(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/* 4938 */     return (float[])remove(paramArrayOfFloat, paramInt);
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
/*      */   public static float[] removeElement(float[] paramArrayOfFloat, float paramFloat)
/*      */   {
/* 4967 */     int i = indexOf(paramArrayOfFloat, paramFloat);
/* 4968 */     if (i == -1) {
/* 4969 */       return clone(paramArrayOfFloat);
/*      */     }
/* 4971 */     return remove(paramArrayOfFloat, i);
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
/*      */   public static int[] remove(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 5003 */     return (int[])remove(paramArrayOfInt, paramInt);
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
/*      */   public static int[] removeElement(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 5032 */     int i = indexOf(paramArrayOfInt, paramInt);
/* 5033 */     if (i == -1) {
/* 5034 */       return clone(paramArrayOfInt);
/*      */     }
/* 5036 */     return remove(paramArrayOfInt, i);
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
/*      */   public static long[] remove(long[] paramArrayOfLong, int paramInt)
/*      */   {
/* 5068 */     return (long[])remove(paramArrayOfLong, paramInt);
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
/*      */   public static long[] removeElement(long[] paramArrayOfLong, long paramLong)
/*      */   {
/* 5097 */     int i = indexOf(paramArrayOfLong, paramLong);
/* 5098 */     if (i == -1) {
/* 5099 */       return clone(paramArrayOfLong);
/*      */     }
/* 5101 */     return remove(paramArrayOfLong, i);
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
/*      */   public static short[] remove(short[] paramArrayOfShort, int paramInt)
/*      */   {
/* 5133 */     return (short[])remove(paramArrayOfShort, paramInt);
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
/*      */   public static short[] removeElement(short[] paramArrayOfShort, short paramShort)
/*      */   {
/* 5162 */     int i = indexOf(paramArrayOfShort, paramShort);
/* 5163 */     if (i == -1) {
/* 5164 */       return clone(paramArrayOfShort);
/*      */     }
/* 5166 */     return remove(paramArrayOfShort, i);
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
/*      */   private static Object remove(Object paramObject, int paramInt)
/*      */   {
/* 5191 */     int i = getLength(paramObject);
/* 5192 */     if ((paramInt < 0) || (paramInt >= i)) {
/* 5193 */       throw new IndexOutOfBoundsException("Index: " + paramInt + ", Length: " + i);
/*      */     }
/*      */     
/* 5196 */     Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), i - 1);
/* 5197 */     System.arraycopy(paramObject, 0, localObject, 0, paramInt);
/* 5198 */     if (paramInt < i - 1) {
/* 5199 */       System.arraycopy(paramObject, paramInt + 1, localObject, paramInt, i - paramInt - 1);
/*      */     }
/*      */     
/* 5202 */     return localObject;
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
/*      */   public static <T> T[] removeAll(T[] paramArrayOfT, int... paramVarArgs)
/*      */   {
/* 5233 */     return (Object[])removeAll(paramArrayOfT, clone(paramVarArgs));
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
/*      */   public static <T> T[] removeElements(T[] paramArrayOfT1, T... paramVarArgs)
/*      */   {
/* 5265 */     if ((isEmpty(paramArrayOfT1)) || (isEmpty(paramVarArgs))) {
/* 5266 */       return clone(paramArrayOfT1);
/*      */     }
/* 5268 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5269 */     Object localObject3; for (localObject3 : paramVarArgs) {
/* 5270 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localObject3);
/* 5271 */       if (localMutableInt == null) {
/* 5272 */         localHashMap.put(localObject3, new MutableInt(1));
/*      */       } else {
/* 5274 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5277 */     ??? = new BitSet();
/* 5278 */     for (Object localObject2 = localHashMap.entrySet().iterator(); ((Iterator)localObject2).hasNext();) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject2).next();
/* 5279 */       localObject3 = localEntry.getKey();
/* 5280 */       int k = 0;
/* 5281 */       int m = 0; for (int n = ((MutableInt)localEntry.getValue()).intValue(); m < n; m++) {
/* 5282 */         k = indexOf(paramArrayOfT1, localObject3, k);
/* 5283 */         if (k < 0) {
/*      */           break;
/*      */         }
/* 5286 */         ((BitSet)???).set(k++);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 5291 */     localObject2 = (Object[])removeAll(paramArrayOfT1, (BitSet)???);
/* 5292 */     return (T[])localObject2;
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
/*      */   public static byte[] removeAll(byte[] paramArrayOfByte, int... paramVarArgs)
/*      */   {
/* 5325 */     return (byte[])removeAll(paramArrayOfByte, clone(paramVarArgs));
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
/*      */   public static byte[] removeElements(byte[] paramArrayOfByte1, byte... paramVarArgs)
/*      */   {
/* 5356 */     if ((isEmpty(paramArrayOfByte1)) || (isEmpty(paramVarArgs))) {
/* 5357 */       return clone(paramArrayOfByte1);
/*      */     }
/* 5359 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5360 */     for (byte b : paramVarArgs) {
/* 5361 */       Byte localByte2 = Byte.valueOf(b);
/* 5362 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localByte2);
/* 5363 */       if (localMutableInt == null) {
/* 5364 */         localHashMap.put(localByte2, new MutableInt(1));
/*      */       } else {
/* 5366 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5369 */     ??? = new BitSet();
/* 5370 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5371 */       Byte localByte1 = (Byte)localEntry.getKey();
/* 5372 */       int k = 0;
/* 5373 */       int m = 0; for (int n = ((MutableInt)localEntry.getValue()).intValue(); m < n; m++) {
/* 5374 */         k = indexOf(paramArrayOfByte1, localByte1.byteValue(), k);
/* 5375 */         if (k < 0) {
/*      */           break;
/*      */         }
/* 5378 */         ((BitSet)???).set(k++);
/*      */       }
/*      */     }
/* 5381 */     return (byte[])removeAll(paramArrayOfByte1, (BitSet)???);
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
/*      */   public static short[] removeAll(short[] paramArrayOfShort, int... paramVarArgs)
/*      */   {
/* 5414 */     return (short[])removeAll(paramArrayOfShort, clone(paramVarArgs));
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
/*      */   public static short[] removeElements(short[] paramArrayOfShort1, short... paramVarArgs)
/*      */   {
/* 5445 */     if ((isEmpty(paramArrayOfShort1)) || (isEmpty(paramVarArgs))) {
/* 5446 */       return clone(paramArrayOfShort1);
/*      */     }
/* 5448 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5449 */     for (short s : paramVarArgs) {
/* 5450 */       Short localShort2 = Short.valueOf(s);
/* 5451 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localShort2);
/* 5452 */       if (localMutableInt == null) {
/* 5453 */         localHashMap.put(localShort2, new MutableInt(1));
/*      */       } else {
/* 5455 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5458 */     ??? = new BitSet();
/* 5459 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5460 */       Short localShort1 = (Short)localEntry.getKey();
/* 5461 */       int k = 0;
/* 5462 */       int m = 0; for (int n = ((MutableInt)localEntry.getValue()).intValue(); m < n; m++) {
/* 5463 */         k = indexOf(paramArrayOfShort1, localShort1.shortValue(), k);
/* 5464 */         if (k < 0) {
/*      */           break;
/*      */         }
/* 5467 */         ((BitSet)???).set(k++);
/*      */       }
/*      */     }
/* 5470 */     return (short[])removeAll(paramArrayOfShort1, (BitSet)???);
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
/*      */   public static int[] removeAll(int[] paramArrayOfInt1, int... paramVarArgs)
/*      */   {
/* 5503 */     return (int[])removeAll(paramArrayOfInt1, clone(paramVarArgs));
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
/*      */   public static int[] removeElements(int[] paramArrayOfInt1, int... paramVarArgs)
/*      */   {
/* 5534 */     if ((isEmpty(paramArrayOfInt1)) || (isEmpty(paramVarArgs))) {
/* 5535 */       return clone(paramArrayOfInt1);
/*      */     }
/* 5537 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5538 */     for (int k : paramVarArgs) {
/* 5539 */       Integer localInteger2 = Integer.valueOf(k);
/* 5540 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localInteger2);
/* 5541 */       if (localMutableInt == null) {
/* 5542 */         localHashMap.put(localInteger2, new MutableInt(1));
/*      */       } else {
/* 5544 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5547 */     ??? = new BitSet();
/* 5548 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5549 */       Integer localInteger1 = (Integer)localEntry.getKey();
/* 5550 */       int m = 0;
/* 5551 */       int n = 0; for (int i1 = ((MutableInt)localEntry.getValue()).intValue(); n < i1; n++) {
/* 5552 */         m = indexOf(paramArrayOfInt1, localInteger1.intValue(), m);
/* 5553 */         if (m < 0) {
/*      */           break;
/*      */         }
/* 5556 */         ((BitSet)???).set(m++);
/*      */       }
/*      */     }
/* 5559 */     return (int[])removeAll(paramArrayOfInt1, (BitSet)???);
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
/*      */   public static char[] removeAll(char[] paramArrayOfChar, int... paramVarArgs)
/*      */   {
/* 5592 */     return (char[])removeAll(paramArrayOfChar, clone(paramVarArgs));
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
/*      */   public static char[] removeElements(char[] paramArrayOfChar1, char... paramVarArgs)
/*      */   {
/* 5623 */     if ((isEmpty(paramArrayOfChar1)) || (isEmpty(paramVarArgs))) {
/* 5624 */       return clone(paramArrayOfChar1);
/*      */     }
/* 5626 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5627 */     for (char c : paramVarArgs) {
/* 5628 */       Character localCharacter2 = Character.valueOf(c);
/* 5629 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localCharacter2);
/* 5630 */       if (localMutableInt == null) {
/* 5631 */         localHashMap.put(localCharacter2, new MutableInt(1));
/*      */       } else {
/* 5633 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5636 */     ??? = new BitSet();
/* 5637 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5638 */       Character localCharacter1 = (Character)localEntry.getKey();
/* 5639 */       int k = 0;
/* 5640 */       int m = 0; for (int n = ((MutableInt)localEntry.getValue()).intValue(); m < n; m++) {
/* 5641 */         k = indexOf(paramArrayOfChar1, localCharacter1.charValue(), k);
/* 5642 */         if (k < 0) {
/*      */           break;
/*      */         }
/* 5645 */         ((BitSet)???).set(k++);
/*      */       }
/*      */     }
/* 5648 */     return (char[])removeAll(paramArrayOfChar1, (BitSet)???);
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
/*      */   public static long[] removeAll(long[] paramArrayOfLong, int... paramVarArgs)
/*      */   {
/* 5681 */     return (long[])removeAll(paramArrayOfLong, clone(paramVarArgs));
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
/*      */   public static long[] removeElements(long[] paramArrayOfLong1, long... paramVarArgs)
/*      */   {
/* 5712 */     if ((isEmpty(paramArrayOfLong1)) || (isEmpty(paramVarArgs))) {
/* 5713 */       return clone(paramArrayOfLong1);
/*      */     }
/* 5715 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5716 */     for (long l : paramVarArgs) {
/* 5717 */       Long localLong1 = Long.valueOf(l);
/* 5718 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localLong1);
/* 5719 */       if (localMutableInt == null) {
/* 5720 */         localHashMap.put(localLong1, new MutableInt(1));
/*      */       } else {
/* 5722 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5725 */     ??? = new BitSet();
/* 5726 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5727 */       Long localLong2 = (Long)localEntry.getKey();
/* 5728 */       int n = 0;
/* 5729 */       int k = 0; for (int m = ((MutableInt)localEntry.getValue()).intValue(); k < m; k++) {
/* 5730 */         n = indexOf(paramArrayOfLong1, localLong2.longValue(), n);
/* 5731 */         if (n < 0) {
/*      */           break;
/*      */         }
/* 5734 */         ((BitSet)???).set(n++);
/*      */       }
/*      */     }
/* 5737 */     return (long[])removeAll(paramArrayOfLong1, (BitSet)???);
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
/*      */   public static float[] removeAll(float[] paramArrayOfFloat, int... paramVarArgs)
/*      */   {
/* 5770 */     return (float[])removeAll(paramArrayOfFloat, clone(paramVarArgs));
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
/*      */   public static float[] removeElements(float[] paramArrayOfFloat1, float... paramVarArgs)
/*      */   {
/* 5801 */     if ((isEmpty(paramArrayOfFloat1)) || (isEmpty(paramVarArgs))) {
/* 5802 */       return clone(paramArrayOfFloat1);
/*      */     }
/* 5804 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5805 */     for (float f : paramVarArgs) {
/* 5806 */       Float localFloat2 = Float.valueOf(f);
/* 5807 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localFloat2);
/* 5808 */       if (localMutableInt == null) {
/* 5809 */         localHashMap.put(localFloat2, new MutableInt(1));
/*      */       } else {
/* 5811 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5814 */     ??? = new BitSet();
/* 5815 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5816 */       Float localFloat1 = (Float)localEntry.getKey();
/* 5817 */       int k = 0;
/* 5818 */       int m = 0; for (int n = ((MutableInt)localEntry.getValue()).intValue(); m < n; m++) {
/* 5819 */         k = indexOf(paramArrayOfFloat1, localFloat1.floatValue(), k);
/* 5820 */         if (k < 0) {
/*      */           break;
/*      */         }
/* 5823 */         ((BitSet)???).set(k++);
/*      */       }
/*      */     }
/* 5826 */     return (float[])removeAll(paramArrayOfFloat1, (BitSet)???);
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
/*      */   public static double[] removeAll(double[] paramArrayOfDouble, int... paramVarArgs)
/*      */   {
/* 5859 */     return (double[])removeAll(paramArrayOfDouble, clone(paramVarArgs));
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
/*      */   public static double[] removeElements(double[] paramArrayOfDouble1, double... paramVarArgs)
/*      */   {
/* 5890 */     if ((isEmpty(paramArrayOfDouble1)) || (isEmpty(paramVarArgs))) {
/* 5891 */       return clone(paramArrayOfDouble1);
/*      */     }
/* 5893 */     HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 5894 */     for (double d : paramVarArgs) {
/* 5895 */       Double localDouble1 = Double.valueOf(d);
/* 5896 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localDouble1);
/* 5897 */       if (localMutableInt == null) {
/* 5898 */         localHashMap.put(localDouble1, new MutableInt(1));
/*      */       } else {
/* 5900 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5903 */     ??? = new BitSet();
/* 5904 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5905 */       Double localDouble2 = (Double)localEntry.getKey();
/* 5906 */       int n = 0;
/* 5907 */       int k = 0; for (int m = ((MutableInt)localEntry.getValue()).intValue(); k < m; k++) {
/* 5908 */         n = indexOf(paramArrayOfDouble1, localDouble2.doubleValue(), n);
/* 5909 */         if (n < 0) {
/*      */           break;
/*      */         }
/* 5912 */         ((BitSet)???).set(n++);
/*      */       }
/*      */     }
/* 5915 */     return (double[])removeAll(paramArrayOfDouble1, (BitSet)???);
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
/*      */   public static boolean[] removeAll(boolean[] paramArrayOfBoolean, int... paramVarArgs)
/*      */   {
/* 5944 */     return (boolean[])removeAll(paramArrayOfBoolean, clone(paramVarArgs));
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
/*      */   public static boolean[] removeElements(boolean[] paramArrayOfBoolean1, boolean... paramVarArgs)
/*      */   {
/* 5975 */     if ((isEmpty(paramArrayOfBoolean1)) || (isEmpty(paramVarArgs))) {
/* 5976 */       return clone(paramArrayOfBoolean1);
/*      */     }
/* 5978 */     HashMap localHashMap = new HashMap(2);
/* 5979 */     for (int k : paramVarArgs) {
/* 5980 */       Boolean localBoolean2 = Boolean.valueOf(k);
/* 5981 */       MutableInt localMutableInt = (MutableInt)localHashMap.get(localBoolean2);
/* 5982 */       if (localMutableInt == null) {
/* 5983 */         localHashMap.put(localBoolean2, new MutableInt(1));
/*      */       } else {
/* 5985 */         localMutableInt.increment();
/*      */       }
/*      */     }
/* 5988 */     ??? = new BitSet();
/* 5989 */     for (Map.Entry localEntry : localHashMap.entrySet()) {
/* 5990 */       Boolean localBoolean1 = (Boolean)localEntry.getKey();
/* 5991 */       int m = 0;
/* 5992 */       int n = 0; for (int i1 = ((MutableInt)localEntry.getValue()).intValue(); n < i1; n++) {
/* 5993 */         m = indexOf(paramArrayOfBoolean1, localBoolean1.booleanValue(), m);
/* 5994 */         if (m < 0) {
/*      */           break;
/*      */         }
/* 5997 */         ((BitSet)???).set(m++);
/*      */       }
/*      */     }
/* 6000 */     return (boolean[])removeAll(paramArrayOfBoolean1, (BitSet)???);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static Object removeAll(Object paramObject, int... paramVarArgs)
/*      */   {
/* 6012 */     int i = getLength(paramObject);
/* 6013 */     int j = 0;
/*      */     int m;
/* 6015 */     int n; if (isNotEmpty(paramVarArgs)) {
/* 6016 */       Arrays.sort(paramVarArgs);
/*      */       
/* 6018 */       int k = paramVarArgs.length;
/* 6019 */       m = i;
/* 6020 */       for (;;) { k--; if (k < 0) break;
/* 6021 */         n = paramVarArgs[k];
/* 6022 */         if ((n < 0) || (n >= i)) {
/* 6023 */           throw new IndexOutOfBoundsException("Index: " + n + ", Length: " + i);
/*      */         }
/* 6025 */         if (n < m)
/*      */         {
/*      */ 
/* 6028 */           j++;
/* 6029 */           m = n;
/*      */         }
/*      */       } }
/* 6032 */     Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), i - j);
/* 6033 */     if (j < i) {
/* 6034 */       m = i;
/* 6035 */       n = i - j;
/* 6036 */       for (int i1 = paramVarArgs.length - 1; i1 >= 0; i1--) {
/* 6037 */         int i2 = paramVarArgs[i1];
/* 6038 */         if (m - i2 > 1) {
/* 6039 */           int i3 = m - i2 - 1;
/* 6040 */           n -= i3;
/* 6041 */           System.arraycopy(paramObject, i2 + 1, localObject, n, i3);
/*      */         }
/*      */         
/* 6044 */         m = i2;
/*      */       }
/* 6046 */       if (m > 0) {
/* 6047 */         System.arraycopy(paramObject, 0, localObject, 0, m);
/*      */       }
/*      */     }
/* 6050 */     return localObject;
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
/*      */   static Object removeAll(Object paramObject, BitSet paramBitSet)
/*      */   {
/* 6063 */     int i = getLength(paramObject);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 6070 */     int j = paramBitSet.cardinality();
/* 6071 */     Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), i - j);
/* 6072 */     int k = 0;
/* 6073 */     int m = 0;
/*      */     
/*      */     int n;
/* 6076 */     while ((n = paramBitSet.nextSetBit(k)) != -1) {
/* 6077 */       i1 = n - k;
/* 6078 */       if (i1 > 0) {
/* 6079 */         System.arraycopy(paramObject, k, localObject, m, i1);
/* 6080 */         m += i1;
/*      */       }
/* 6082 */       k = paramBitSet.nextClearBit(n);
/*      */     }
/* 6084 */     int i1 = i - k;
/* 6085 */     if (i1 > 0) {
/* 6086 */       System.arraycopy(paramObject, k, localObject, m, i1);
/*      */     }
/* 6088 */     return localObject;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\ArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */