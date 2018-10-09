/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Validate
/*      */ {
/*      */   private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
/*      */   private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
/*      */   private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
/*      */   private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
/*      */   private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
/*      */   private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
/*      */   private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
/*      */   private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
/*      */   private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
/*      */   private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
/*      */   private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
/*      */   private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
/*      */   private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
/*      */   private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";
/*      */   
/*      */   public static void isTrue(boolean paramBoolean, String paramString, long paramLong)
/*      */   {
/*  105 */     if (!paramBoolean) {
/*  106 */       throw new IllegalArgumentException(String.format(paramString, new Object[] { Long.valueOf(paramLong) }));
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
/*      */   public static void isTrue(boolean paramBoolean, String paramString, double paramDouble)
/*      */   {
/*  130 */     if (!paramBoolean) {
/*  131 */       throw new IllegalArgumentException(String.format(paramString, new Object[] { Double.valueOf(paramDouble) }));
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
/*      */   public static void isTrue(boolean paramBoolean, String paramString, Object... paramVarArgs)
/*      */   {
/*  154 */     if (!paramBoolean) {
/*  155 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
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
/*      */   public static void isTrue(boolean paramBoolean)
/*      */   {
/*  179 */     if (!paramBoolean) {
/*  180 */       throw new IllegalArgumentException("The validated expression is false");
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
/*      */   public static <T> T notNull(T paramT)
/*      */   {
/*  203 */     return (T)notNull(paramT, "The validated object is null", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T notNull(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  221 */     if (paramT == null) {
/*  222 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  224 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] notEmpty(T[] paramArrayOfT, String paramString, Object... paramVarArgs)
/*      */   {
/*  247 */     if (paramArrayOfT == null) {
/*  248 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  250 */     if (paramArrayOfT.length == 0) {
/*  251 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*  253 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] notEmpty(T[] paramArrayOfT)
/*      */   {
/*  273 */     return notEmpty(paramArrayOfT, "The validated array is empty", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Collection<?>> T notEmpty(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  296 */     if (paramT == null) {
/*  297 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  299 */     if (paramT.isEmpty()) {
/*  300 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*  302 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Collection<?>> T notEmpty(T paramT)
/*      */   {
/*  322 */     return notEmpty(paramT, "The validated collection is empty", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Map<?, ?>> T notEmpty(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  345 */     if (paramT == null) {
/*  346 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  348 */     if (paramT.isEmpty()) {
/*  349 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*  351 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Map<?, ?>> T notEmpty(T paramT)
/*      */   {
/*  371 */     return notEmpty(paramT, "The validated map is empty", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T notEmpty(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  394 */     if (paramT == null) {
/*  395 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  397 */     if (paramT.length() == 0) {
/*  398 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*  400 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T notEmpty(T paramT)
/*      */   {
/*  421 */     return notEmpty(paramT, "The validated character sequence is empty", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T notBlank(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  447 */     if (paramT == null) {
/*  448 */       throw new NullPointerException(String.format(paramString, paramVarArgs));
/*      */     }
/*  450 */     if (StringUtils.isBlank(paramT)) {
/*  451 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*  453 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T notBlank(T paramT)
/*      */   {
/*  476 */     return notBlank(paramT, "The validated character sequence is blank", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] noNullElements(T[] paramArrayOfT, String paramString, Object... paramVarArgs)
/*      */   {
/*  506 */     notNull(paramArrayOfT);
/*  507 */     for (int i = 0; i < paramArrayOfT.length; i++) {
/*  508 */       if (paramArrayOfT[i] == null) {
/*  509 */         Object[] arrayOfObject = ArrayUtils.add(paramVarArgs, Integer.valueOf(i));
/*  510 */         throw new IllegalArgumentException(String.format(paramString, arrayOfObject));
/*      */       }
/*      */     }
/*  513 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] noNullElements(T[] paramArrayOfT)
/*      */   {
/*  538 */     return noNullElements(paramArrayOfT, "The validated array contains null element at index: %d", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Iterable<?>> T noNullElements(T paramT, String paramString, Object... paramVarArgs)
/*      */   {
/*  568 */     notNull(paramT);
/*  569 */     int i = 0;
/*  570 */     for (Iterator localIterator = paramT.iterator(); localIterator.hasNext(); i++) {
/*  571 */       if (localIterator.next() == null) {
/*  572 */         Object[] arrayOfObject = ArrayUtils.addAll(paramVarArgs, new Object[] { Integer.valueOf(i) });
/*  573 */         throw new IllegalArgumentException(String.format(paramString, arrayOfObject));
/*      */       }
/*      */     }
/*  576 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Iterable<?>> T noNullElements(T paramT)
/*      */   {
/*  601 */     return noNullElements(paramT, "The validated collection contains null element at index: %d", new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] validIndex(T[] paramArrayOfT, int paramInt, String paramString, Object... paramVarArgs)
/*      */   {
/*  629 */     notNull(paramArrayOfT);
/*  630 */     if ((paramInt < 0) || (paramInt >= paramArrayOfT.length)) {
/*  631 */       throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
/*      */     }
/*  633 */     return paramArrayOfT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> T[] validIndex(T[] paramArrayOfT, int paramInt)
/*      */   {
/*  660 */     return validIndex(paramArrayOfT, paramInt, "The validated array index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Collection<?>> T validIndex(T paramT, int paramInt, String paramString, Object... paramVarArgs)
/*      */   {
/*  688 */     notNull(paramT);
/*  689 */     if ((paramInt < 0) || (paramInt >= paramT.size())) {
/*  690 */       throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
/*      */     }
/*  692 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends Collection<?>> T validIndex(T paramT, int paramInt)
/*      */   {
/*  716 */     return validIndex(paramT, paramInt, "The validated collection index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T validIndex(T paramT, int paramInt, String paramString, Object... paramVarArgs)
/*      */   {
/*  745 */     notNull(paramT);
/*  746 */     if ((paramInt < 0) || (paramInt >= paramT.length())) {
/*  747 */       throw new IndexOutOfBoundsException(String.format(paramString, paramVarArgs));
/*      */     }
/*  749 */     return paramT;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T extends CharSequence> T validIndex(T paramT, int paramInt)
/*      */   {
/*  777 */     return validIndex(paramT, paramInt, "The validated character sequence index is invalid: %d", new Object[] { Integer.valueOf(paramInt) });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void validState(boolean paramBoolean)
/*      */   {
/*  803 */     if (!paramBoolean) {
/*  804 */       throw new IllegalStateException("The validated state is false");
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
/*      */   public static void validState(boolean paramBoolean, String paramString, Object... paramVarArgs)
/*      */   {
/*  825 */     if (!paramBoolean) {
/*  826 */       throw new IllegalStateException(String.format(paramString, paramVarArgs));
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
/*      */   public static void matchesPattern(CharSequence paramCharSequence, String paramString)
/*      */   {
/*  850 */     if (!Pattern.matches(paramString, paramCharSequence)) {
/*  851 */       throw new IllegalArgumentException(String.format("The string %s does not match the pattern %s", new Object[] { paramCharSequence, paramString }));
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
/*      */   public static void matchesPattern(CharSequence paramCharSequence, String paramString1, String paramString2, Object... paramVarArgs)
/*      */   {
/*  874 */     if (!Pattern.matches(paramString1, paramCharSequence)) {
/*  875 */       throw new IllegalArgumentException(String.format(paramString2, paramVarArgs));
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
/*      */   public static <T> void inclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable)
/*      */   {
/*  899 */     if ((paramComparable.compareTo(paramT1) < 0) || (paramComparable.compareTo(paramT2) > 0)) {
/*  900 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { paramComparable, paramT1, paramT2 }));
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
/*      */   public static <T> void inclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable, String paramString, Object... paramVarArgs)
/*      */   {
/*  924 */     if ((paramComparable.compareTo(paramT1) < 0) || (paramComparable.compareTo(paramT2) > 0)) {
/*  925 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
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
/*      */   public static void inclusiveBetween(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/*  945 */     if ((paramLong3 < paramLong1) || (paramLong3 > paramLong2)) {
/*  946 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { Long.valueOf(paramLong3), Long.valueOf(paramLong1), Long.valueOf(paramLong2) }));
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
/*      */   public static void inclusiveBetween(long paramLong1, long paramLong2, long paramLong3, String paramString)
/*      */   {
/*  968 */     if ((paramLong3 < paramLong1) || (paramLong3 > paramLong2)) {
/*  969 */       throw new IllegalArgumentException(String.format(paramString, new Object[0]));
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
/*      */   public static void inclusiveBetween(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  989 */     if ((paramDouble3 < paramDouble1) || (paramDouble3 > paramDouble2)) {
/*  990 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified inclusive range of %s to %s", new Object[] { Double.valueOf(paramDouble3), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) }));
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
/*      */   public static void inclusiveBetween(double paramDouble1, double paramDouble2, double paramDouble3, String paramString)
/*      */   {
/* 1012 */     if ((paramDouble3 < paramDouble1) || (paramDouble3 > paramDouble2)) {
/* 1013 */       throw new IllegalArgumentException(String.format(paramString, new Object[0]));
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
/*      */   public static <T> void exclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable)
/*      */   {
/* 1037 */     if ((paramComparable.compareTo(paramT1) <= 0) || (paramComparable.compareTo(paramT2) >= 0)) {
/* 1038 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { paramComparable, paramT1, paramT2 }));
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
/*      */   public static <T> void exclusiveBetween(T paramT1, T paramT2, Comparable<T> paramComparable, String paramString, Object... paramVarArgs)
/*      */   {
/* 1062 */     if ((paramComparable.compareTo(paramT1) <= 0) || (paramComparable.compareTo(paramT2) >= 0)) {
/* 1063 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
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
/*      */   public static void exclusiveBetween(long paramLong1, long paramLong2, long paramLong3)
/*      */   {
/* 1083 */     if ((paramLong3 <= paramLong1) || (paramLong3 >= paramLong2)) {
/* 1084 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { Long.valueOf(paramLong3), Long.valueOf(paramLong1), Long.valueOf(paramLong2) }));
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
/*      */   public static void exclusiveBetween(long paramLong1, long paramLong2, long paramLong3, String paramString)
/*      */   {
/* 1106 */     if ((paramLong3 <= paramLong1) || (paramLong3 >= paramLong2)) {
/* 1107 */       throw new IllegalArgumentException(String.format(paramString, new Object[0]));
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
/*      */   public static void exclusiveBetween(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/* 1127 */     if ((paramDouble3 <= paramDouble1) || (paramDouble3 >= paramDouble2)) {
/* 1128 */       throw new IllegalArgumentException(String.format("The value %s is not in the specified exclusive range of %s to %s", new Object[] { Double.valueOf(paramDouble3), Double.valueOf(paramDouble1), Double.valueOf(paramDouble2) }));
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
/*      */   public static void exclusiveBetween(double paramDouble1, double paramDouble2, double paramDouble3, String paramString)
/*      */   {
/* 1150 */     if ((paramDouble3 <= paramDouble1) || (paramDouble3 >= paramDouble2)) {
/* 1151 */       throw new IllegalArgumentException(String.format(paramString, new Object[0]));
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
/*      */ 
/*      */   public static void isInstanceOf(Class<?> paramClass, Object paramObject)
/*      */   {
/* 1176 */     if (!paramClass.isInstance(paramObject)) {
/* 1177 */       throw new IllegalArgumentException(String.format("Expected type: %s, actual: %s", new Object[] { paramClass.getName(), paramObject == null ? "null" : paramObject.getClass().getName() }));
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
/*      */   public static void isInstanceOf(Class<?> paramClass, Object paramObject, String paramString, Object... paramVarArgs)
/*      */   {
/* 1201 */     if (!paramClass.isInstance(paramObject)) {
/* 1202 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
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
/*      */ 
/*      */   public static void isAssignableFrom(Class<?> paramClass1, Class<?> paramClass2)
/*      */   {
/* 1227 */     if (!paramClass1.isAssignableFrom(paramClass2)) {
/* 1228 */       throw new IllegalArgumentException(String.format("Cannot assign a %s to a %s", new Object[] { paramClass2 == null ? "null" : paramClass2.getName(), paramClass1.getName() }));
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
/*      */   public static void isAssignableFrom(Class<?> paramClass1, Class<?> paramClass2, String paramString, Object... paramVarArgs)
/*      */   {
/* 1252 */     if (!paramClass1.isAssignableFrom(paramClass2)) {
/* 1253 */       throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\Validate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */