/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.lang3.exception.CloneFailedException;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.text.StrBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectUtils
/*     */ {
/*  63 */   public static final Null NULL = new Null();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T defaultIfNull(T paramT1, T paramT2)
/*     */   {
/*  96 */     return paramT1 != null ? paramT1 : paramT2;
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
/*     */   public static <T> T firstNonNull(T... paramVarArgs)
/*     */   {
/* 122 */     if (paramVarArgs != null) {
/* 123 */       for (T ? : paramVarArgs) {
/* 124 */         if (? != null) {
/* 125 */           return ?;
/*     */         }
/*     */       }
/*     */     }
/* 129 */     return null;
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
/*     */   @Deprecated
/*     */   public static boolean equals(Object paramObject1, Object paramObject2)
/*     */   {
/* 157 */     if (paramObject1 == paramObject2) {
/* 158 */       return true;
/*     */     }
/* 160 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 161 */       return false;
/*     */     }
/* 163 */     return paramObject1.equals(paramObject2);
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
/*     */   public static boolean notEqual(Object paramObject1, Object paramObject2)
/*     */   {
/* 186 */     return !equals(paramObject1, paramObject2);
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
/*     */   @Deprecated
/*     */   public static int hashCode(Object paramObject)
/*     */   {
/* 207 */     return paramObject == null ? 0 : paramObject.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 212 */     return super.toString();
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
/*     */   @Deprecated
/*     */   public static int hashCodeMulti(Object... paramVarArgs)
/*     */   {
/* 239 */     int i = 1;
/* 240 */     if (paramVarArgs != null) {
/* 241 */       for (Object localObject : paramVarArgs) {
/* 242 */         int m = hashCode(localObject);
/* 243 */         i = i * 31 + m;
/*     */       }
/*     */     }
/* 246 */     return i;
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
/*     */   public static String identityToString(Object paramObject)
/*     */   {
/* 268 */     if (paramObject == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     StringBuilder localStringBuilder = new StringBuilder();
/* 272 */     identityToString(localStringBuilder, paramObject);
/* 273 */     return localStringBuilder.toString();
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
/*     */   public static void identityToString(Appendable paramAppendable, Object paramObject)
/*     */     throws IOException
/*     */   {
/* 293 */     if (paramObject == null) {
/* 294 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 296 */     paramAppendable.append(paramObject.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(paramObject)));
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
/*     */   public static void identityToString(StrBuilder paramStrBuilder, Object paramObject)
/*     */   {
/* 317 */     if (paramObject == null) {
/* 318 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 320 */     paramStrBuilder.append(paramObject.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(paramObject)));
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
/*     */   public static void identityToString(StringBuffer paramStringBuffer, Object paramObject)
/*     */   {
/* 341 */     if (paramObject == null) {
/* 342 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 344 */     paramStringBuffer.append(paramObject.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(paramObject)));
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
/*     */   public static void identityToString(StringBuilder paramStringBuilder, Object paramObject)
/*     */   {
/* 365 */     if (paramObject == null) {
/* 366 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 368 */     paramStringBuilder.append(paramObject.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(paramObject)));
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
/*     */   @Deprecated
/*     */   public static String toString(Object paramObject)
/*     */   {
/* 397 */     return paramObject == null ? "" : paramObject.toString();
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
/*     */   @Deprecated
/*     */   public static String toString(Object paramObject, String paramString)
/*     */   {
/* 423 */     return paramObject == null ? paramString : paramObject.toString();
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
/*     */   public static <T extends Comparable<? super T>> T min(T... paramVarArgs)
/*     */   {
/* 442 */     Object localObject = null;
/* 443 */     if (paramVarArgs != null) {
/* 444 */       for (T ? : paramVarArgs) {
/* 445 */         if (compare(?, (Comparable)localObject, true) < 0) {
/* 446 */           localObject = ?;
/*     */         }
/*     */       }
/*     */     }
/* 450 */     return (T)localObject;
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
/*     */   public static <T extends Comparable<? super T>> T max(T... paramVarArgs)
/*     */   {
/* 467 */     Object localObject = null;
/* 468 */     if (paramVarArgs != null) {
/* 469 */       for (T ? : paramVarArgs) {
/* 470 */         if (compare(?, (Comparable)localObject, false) > 0) {
/* 471 */           localObject = ?;
/*     */         }
/*     */       }
/*     */     }
/* 475 */     return (T)localObject;
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
/*     */   public static <T extends Comparable<? super T>> int compare(T paramT1, T paramT2)
/*     */   {
/* 489 */     return compare(paramT1, paramT2, false);
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
/*     */   public static <T extends Comparable<? super T>> int compare(T paramT1, T paramT2, boolean paramBoolean)
/*     */   {
/* 506 */     if (paramT1 == paramT2)
/* 507 */       return 0;
/* 508 */     if (paramT1 == null)
/* 509 */       return paramBoolean ? 1 : -1;
/* 510 */     if (paramT2 == null) {
/* 511 */       return paramBoolean ? -1 : 1;
/*     */     }
/* 513 */     return paramT1.compareTo(paramT2);
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
/*     */   public static <T extends Comparable<? super T>> T median(T... paramVarArgs)
/*     */   {
/* 527 */     Validate.notEmpty(paramVarArgs);
/* 528 */     Validate.noNullElements(paramVarArgs);
/* 529 */     TreeSet localTreeSet = new TreeSet();
/* 530 */     Collections.addAll(localTreeSet, paramVarArgs);
/*     */     
/*     */ 
/* 533 */     Comparable localComparable = (Comparable)localTreeSet.toArray()[((localTreeSet.size() - 1) / 2)];
/* 534 */     return localComparable;
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
/*     */   public static <T> T median(Comparator<T> paramComparator, T... paramVarArgs)
/*     */   {
/* 549 */     Validate.notEmpty(paramVarArgs, "null/empty items", new Object[0]);
/* 550 */     Validate.noNullElements(paramVarArgs);
/* 551 */     Validate.notNull(paramComparator, "null comparator", new Object[0]);
/* 552 */     TreeSet localTreeSet = new TreeSet(paramComparator);
/* 553 */     Collections.addAll(localTreeSet, paramVarArgs);
/*     */     
/*     */ 
/* 556 */     Object localObject = localTreeSet.toArray()[((localTreeSet.size() - 1) / 2)];
/* 557 */     return (T)localObject;
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
/*     */   public static <T> T mode(T... paramVarArgs)
/*     */   {
/* 571 */     if (ArrayUtils.isNotEmpty(paramVarArgs)) {
/* 572 */       HashMap localHashMap = new HashMap(paramVarArgs.length);
/* 573 */       Object localObject2; for (localObject2 : paramVarArgs) {
/* 574 */         MutableInt localMutableInt = (MutableInt)localHashMap.get(localObject2);
/* 575 */         if (localMutableInt == null) {
/* 576 */           localHashMap.put(localObject2, new MutableInt(1));
/*     */         } else {
/* 578 */           localMutableInt.increment();
/*     */         }
/*     */       }
/* 581 */       ??? = null;
/* 582 */       ??? = 0;
/* 583 */       for (Iterator localIterator = localHashMap.entrySet().iterator(); localIterator.hasNext();) { localObject2 = (Map.Entry)localIterator.next();
/* 584 */         int k = ((MutableInt)((Map.Entry)localObject2).getValue()).intValue();
/* 585 */         if (k == ???) {
/* 586 */           ??? = null;
/* 587 */         } else if (k > ???) {
/* 588 */           ??? = k;
/* 589 */           ??? = ((Map.Entry)localObject2).getKey();
/*     */         }
/*     */       }
/* 592 */       return (T)???;
/*     */     }
/* 594 */     return null;
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
/*     */   public static <T> T clone(T paramT)
/*     */   {
/* 609 */     if ((paramT instanceof Cloneable)) { Object localObject1;
/*     */       Object localObject3;
/* 611 */       if (paramT.getClass().isArray()) {
/* 612 */         localObject1 = paramT.getClass().getComponentType();
/* 613 */         if (!((Class)localObject1).isPrimitive()) {
/* 614 */           localObject3 = ((Object[])paramT).clone();
/*     */         } else {
/* 616 */           int i = Array.getLength(paramT);
/* 617 */           localObject3 = Array.newInstance((Class)localObject1, i);
/* 618 */           while (i-- > 0) {
/* 619 */             Array.set(localObject3, i, Array.get(paramT, i));
/*     */           }
/*     */         }
/*     */       } else {
/*     */         try {
/* 624 */           localObject1 = paramT.getClass().getMethod("clone", new Class[0]);
/* 625 */           localObject3 = ((Method)localObject1).invoke(paramT, new Object[0]);
/*     */         } catch (NoSuchMethodException localNoSuchMethodException) {
/* 627 */           throw new CloneFailedException("Cloneable type " + paramT.getClass().getName() + " has no clone method", localNoSuchMethodException);
/*     */         }
/*     */         catch (IllegalAccessException localIllegalAccessException)
/*     */         {
/* 631 */           throw new CloneFailedException("Cannot clone Cloneable type " + paramT.getClass().getName(), localIllegalAccessException);
/*     */         }
/*     */         catch (InvocationTargetException localInvocationTargetException) {
/* 634 */           throw new CloneFailedException("Exception cloning Cloneable type " + paramT.getClass().getName(), localInvocationTargetException.getCause());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 639 */       Object localObject2 = localObject3;
/* 640 */       return (T)localObject2;
/*     */     }
/*     */     
/* 643 */     return null;
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
/*     */   public static <T> T cloneIfPossible(T paramT)
/*     */   {
/* 663 */     Object localObject = clone(paramT);
/* 664 */     return localObject == null ? paramT : localObject;
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
/*     */   public static class Null
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7092611880189329093L;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Object readResolve()
/*     */     {
/* 703 */       return ObjectUtils.NULL;
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
/*     */ 
/*     */   public static boolean CONST(boolean paramBoolean)
/*     */   {
/* 746 */     return paramBoolean;
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
/*     */   public static byte CONST(byte paramByte)
/*     */   {
/* 765 */     return paramByte;
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
/*     */   public static byte CONST_BYTE(int paramInt)
/*     */     throws IllegalArgumentException
/*     */   {
/* 788 */     if ((paramInt < -128) || (paramInt > 127)) {
/* 789 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + paramInt + "]");
/*     */     }
/* 791 */     return (byte)paramInt;
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
/*     */   public static char CONST(char paramChar)
/*     */   {
/* 811 */     return paramChar;
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
/*     */   public static short CONST(short paramShort)
/*     */   {
/* 830 */     return paramShort;
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
/*     */   public static short CONST_SHORT(int paramInt)
/*     */     throws IllegalArgumentException
/*     */   {
/* 853 */     if ((paramInt < 32768) || (paramInt > 32767)) {
/* 854 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + paramInt + "]");
/*     */     }
/* 856 */     return (short)paramInt;
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
/*     */   public static int CONST(int paramInt)
/*     */   {
/* 877 */     return paramInt;
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
/*     */   public static long CONST(long paramLong)
/*     */   {
/* 896 */     return paramLong;
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
/*     */   public static float CONST(float paramFloat)
/*     */   {
/* 915 */     return paramFloat;
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
/*     */   public static double CONST(double paramDouble)
/*     */   {
/* 934 */     return paramDouble;
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
/*     */   public static <T> T CONST(T paramT)
/*     */   {
/* 954 */     return paramT;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\ObjectUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */