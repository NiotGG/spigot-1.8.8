/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EqualsBuilder
/*     */   implements Builder<Boolean>
/*     */ {
/*  92 */   private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static Set<Pair<IDKey, IDKey>> getRegistry()
/*     */   {
/* 121 */     return (Set)REGISTRY.get();
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
/*     */   static Pair<IDKey, IDKey> getRegisterPair(Object paramObject1, Object paramObject2)
/*     */   {
/* 135 */     IDKey localIDKey1 = new IDKey(paramObject1);
/* 136 */     IDKey localIDKey2 = new IDKey(paramObject2);
/* 137 */     return Pair.of(localIDKey1, localIDKey2);
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
/*     */   static boolean isRegistered(Object paramObject1, Object paramObject2)
/*     */   {
/* 154 */     Set localSet = getRegistry();
/* 155 */     Pair localPair1 = getRegisterPair(paramObject1, paramObject2);
/* 156 */     Pair localPair2 = Pair.of(localPair1.getLeft(), localPair1.getRight());
/*     */     
/* 158 */     return (localSet != null) && ((localSet.contains(localPair1)) || (localSet.contains(localPair2)));
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
/*     */   static void register(Object paramObject1, Object paramObject2)
/*     */   {
/* 172 */     synchronized (EqualsBuilder.class) {
/* 173 */       if (getRegistry() == null) {
/* 174 */         REGISTRY.set(new HashSet());
/*     */       }
/*     */     }
/*     */     
/* 178 */     ??? = getRegistry();
/* 179 */     Pair localPair = getRegisterPair(paramObject1, paramObject2);
/* 180 */     ((Set)???).add(localPair);
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
/*     */   static void unregister(Object paramObject1, Object paramObject2)
/*     */   {
/* 196 */     Set localSet = getRegistry();
/* 197 */     if (localSet != null) {
/* 198 */       Pair localPair = getRegisterPair(paramObject1, paramObject2);
/* 199 */       localSet.remove(localPair);
/* 200 */       synchronized (EqualsBuilder.class)
/*     */       {
/* 202 */         localSet = getRegistry();
/* 203 */         if ((localSet != null) && (localSet.isEmpty())) {
/* 204 */           REGISTRY.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 214 */   private boolean isEquals = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean reflectionEquals(Object paramObject1, Object paramObject2, Collection<String> paramCollection)
/*     */   {
/* 248 */     return reflectionEquals(paramObject1, paramObject2, ReflectionToStringBuilder.toNoNullStringArray(paramCollection));
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
/*     */   public static boolean reflectionEquals(Object paramObject1, Object paramObject2, String... paramVarArgs)
/*     */   {
/* 271 */     return reflectionEquals(paramObject1, paramObject2, false, null, paramVarArgs);
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
/*     */   public static boolean reflectionEquals(Object paramObject1, Object paramObject2, boolean paramBoolean)
/*     */   {
/* 295 */     return reflectionEquals(paramObject1, paramObject2, paramBoolean, null, new String[0]);
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
/*     */   public static boolean reflectionEquals(Object paramObject1, Object paramObject2, boolean paramBoolean, Class<?> paramClass, String... paramVarArgs)
/*     */   {
/* 326 */     if (paramObject1 == paramObject2) {
/* 327 */       return true;
/*     */     }
/* 329 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 330 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 336 */     Class localClass1 = paramObject1.getClass();
/* 337 */     Class localClass2 = paramObject2.getClass();
/*     */     Class localClass3;
/* 339 */     if (localClass1.isInstance(paramObject2)) {
/* 340 */       localClass3 = localClass1;
/* 341 */       if (!localClass2.isInstance(paramObject1))
/*     */       {
/* 343 */         localClass3 = localClass2;
/*     */       }
/* 345 */     } else if (localClass2.isInstance(paramObject1)) {
/* 346 */       localClass3 = localClass2;
/* 347 */       if (!localClass1.isInstance(paramObject2))
/*     */       {
/* 349 */         localClass3 = localClass1;
/*     */       }
/*     */     }
/*     */     else {
/* 353 */       return false;
/*     */     }
/* 355 */     EqualsBuilder localEqualsBuilder = new EqualsBuilder();
/*     */     try {
/* 357 */       if (localClass3.isArray()) {
/* 358 */         localEqualsBuilder.append(paramObject1, paramObject2);
/*     */       } else {
/* 360 */         reflectionAppend(paramObject1, paramObject2, localClass3, localEqualsBuilder, paramBoolean, paramVarArgs);
/* 361 */         while ((localClass3.getSuperclass() != null) && (localClass3 != paramClass)) {
/* 362 */           localClass3 = localClass3.getSuperclass();
/* 363 */           reflectionAppend(paramObject1, paramObject2, localClass3, localEqualsBuilder, paramBoolean, paramVarArgs);
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException)
/*     */     {
/* 372 */       return false;
/*     */     }
/* 374 */     return localEqualsBuilder.isEquals();
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
/*     */   private static void reflectionAppend(Object paramObject1, Object paramObject2, Class<?> paramClass, EqualsBuilder paramEqualsBuilder, boolean paramBoolean, String[] paramArrayOfString)
/*     */   {
/* 396 */     if (isRegistered(paramObject1, paramObject2)) {
/* 397 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 401 */       register(paramObject1, paramObject2);
/* 402 */       Field[] arrayOfField = paramClass.getDeclaredFields();
/* 403 */       AccessibleObject.setAccessible(arrayOfField, true);
/* 404 */       for (int i = 0; (i < arrayOfField.length) && (paramEqualsBuilder.isEquals); i++) {
/* 405 */         Field localField = arrayOfField[i];
/* 406 */         if ((!ArrayUtils.contains(paramArrayOfString, localField.getName())) && (localField.getName().indexOf('$') == -1) && ((paramBoolean) || (!Modifier.isTransient(localField.getModifiers()))) && (!Modifier.isStatic(localField.getModifiers())))
/*     */         {
/*     */           try
/*     */           {
/*     */ 
/* 411 */             paramEqualsBuilder.append(localField.get(paramObject1), localField.get(paramObject2));
/*     */           }
/*     */           catch (IllegalAccessException localIllegalAccessException)
/*     */           {
/* 415 */             throw new InternalError("Unexpected IllegalAccessException");
/*     */           }
/*     */         }
/*     */       }
/*     */     } finally {
/* 420 */       unregister(paramObject1, paramObject2);
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
/*     */   public EqualsBuilder appendSuper(boolean paramBoolean)
/*     */   {
/* 434 */     if (!this.isEquals) {
/* 435 */       return this;
/*     */     }
/* 437 */     this.isEquals = paramBoolean;
/* 438 */     return this;
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
/*     */   public EqualsBuilder append(Object paramObject1, Object paramObject2)
/*     */   {
/* 452 */     if (!this.isEquals) {
/* 453 */       return this;
/*     */     }
/* 455 */     if (paramObject1 == paramObject2) {
/* 456 */       return this;
/*     */     }
/* 458 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/* 459 */       setEquals(false);
/* 460 */       return this;
/*     */     }
/* 462 */     Class localClass = paramObject1.getClass();
/* 463 */     if (!localClass.isArray())
/*     */     {
/* 465 */       this.isEquals = paramObject1.equals(paramObject2);
/* 466 */     } else if (paramObject1.getClass() != paramObject2.getClass())
/*     */     {
/* 468 */       setEquals(false);
/*     */ 
/*     */ 
/*     */     }
/* 472 */     else if ((paramObject1 instanceof long[])) {
/* 473 */       append((long[])paramObject1, (long[])paramObject2);
/* 474 */     } else if ((paramObject1 instanceof int[])) {
/* 475 */       append((int[])paramObject1, (int[])paramObject2);
/* 476 */     } else if ((paramObject1 instanceof short[])) {
/* 477 */       append((short[])paramObject1, (short[])paramObject2);
/* 478 */     } else if ((paramObject1 instanceof char[])) {
/* 479 */       append((char[])paramObject1, (char[])paramObject2);
/* 480 */     } else if ((paramObject1 instanceof byte[])) {
/* 481 */       append((byte[])paramObject1, (byte[])paramObject2);
/* 482 */     } else if ((paramObject1 instanceof double[])) {
/* 483 */       append((double[])paramObject1, (double[])paramObject2);
/* 484 */     } else if ((paramObject1 instanceof float[])) {
/* 485 */       append((float[])paramObject1, (float[])paramObject2);
/* 486 */     } else if ((paramObject1 instanceof boolean[])) {
/* 487 */       append((boolean[])paramObject1, (boolean[])paramObject2);
/*     */     }
/*     */     else {
/* 490 */       append((Object[])paramObject1, (Object[])paramObject2);
/*     */     }
/* 492 */     return this;
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
/*     */   public EqualsBuilder append(long paramLong1, long paramLong2)
/*     */   {
/* 507 */     if (!this.isEquals) {
/* 508 */       return this;
/*     */     }
/* 510 */     this.isEquals = (paramLong1 == paramLong2);
/* 511 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EqualsBuilder append(int paramInt1, int paramInt2)
/*     */   {
/* 522 */     if (!this.isEquals) {
/* 523 */       return this;
/*     */     }
/* 525 */     this.isEquals = (paramInt1 == paramInt2);
/* 526 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EqualsBuilder append(short paramShort1, short paramShort2)
/*     */   {
/* 537 */     if (!this.isEquals) {
/* 538 */       return this;
/*     */     }
/* 540 */     this.isEquals = (paramShort1 == paramShort2);
/* 541 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EqualsBuilder append(char paramChar1, char paramChar2)
/*     */   {
/* 552 */     if (!this.isEquals) {
/* 553 */       return this;
/*     */     }
/* 555 */     this.isEquals = (paramChar1 == paramChar2);
/* 556 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EqualsBuilder append(byte paramByte1, byte paramByte2)
/*     */   {
/* 567 */     if (!this.isEquals) {
/* 568 */       return this;
/*     */     }
/* 570 */     this.isEquals = (paramByte1 == paramByte2);
/* 571 */     return this;
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
/*     */   public EqualsBuilder append(double paramDouble1, double paramDouble2)
/*     */   {
/* 588 */     if (!this.isEquals) {
/* 589 */       return this;
/*     */     }
/* 591 */     return append(Double.doubleToLongBits(paramDouble1), Double.doubleToLongBits(paramDouble2));
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
/*     */   public EqualsBuilder append(float paramFloat1, float paramFloat2)
/*     */   {
/* 608 */     if (!this.isEquals) {
/* 609 */       return this;
/*     */     }
/* 611 */     return append(Float.floatToIntBits(paramFloat1), Float.floatToIntBits(paramFloat2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EqualsBuilder append(boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 622 */     if (!this.isEquals) {
/* 623 */       return this;
/*     */     }
/* 625 */     this.isEquals = (paramBoolean1 == paramBoolean2);
/* 626 */     return this;
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
/*     */   public EqualsBuilder append(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
/*     */   {
/* 640 */     if (!this.isEquals) {
/* 641 */       return this;
/*     */     }
/* 643 */     if (paramArrayOfObject1 == paramArrayOfObject2) {
/* 644 */       return this;
/*     */     }
/* 646 */     if ((paramArrayOfObject1 == null) || (paramArrayOfObject2 == null)) {
/* 647 */       setEquals(false);
/* 648 */       return this;
/*     */     }
/* 650 */     if (paramArrayOfObject1.length != paramArrayOfObject2.length) {
/* 651 */       setEquals(false);
/* 652 */       return this;
/*     */     }
/* 654 */     for (int i = 0; (i < paramArrayOfObject1.length) && (this.isEquals); i++) {
/* 655 */       append(paramArrayOfObject1[i], paramArrayOfObject2[i]);
/*     */     }
/* 657 */     return this;
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
/*     */   public EqualsBuilder append(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
/*     */   {
/* 671 */     if (!this.isEquals) {
/* 672 */       return this;
/*     */     }
/* 674 */     if (paramArrayOfLong1 == paramArrayOfLong2) {
/* 675 */       return this;
/*     */     }
/* 677 */     if ((paramArrayOfLong1 == null) || (paramArrayOfLong2 == null)) {
/* 678 */       setEquals(false);
/* 679 */       return this;
/*     */     }
/* 681 */     if (paramArrayOfLong1.length != paramArrayOfLong2.length) {
/* 682 */       setEquals(false);
/* 683 */       return this;
/*     */     }
/* 685 */     for (int i = 0; (i < paramArrayOfLong1.length) && (this.isEquals); i++) {
/* 686 */       append(paramArrayOfLong1[i], paramArrayOfLong2[i]);
/*     */     }
/* 688 */     return this;
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
/*     */   public EqualsBuilder append(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*     */   {
/* 702 */     if (!this.isEquals) {
/* 703 */       return this;
/*     */     }
/* 705 */     if (paramArrayOfInt1 == paramArrayOfInt2) {
/* 706 */       return this;
/*     */     }
/* 708 */     if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null)) {
/* 709 */       setEquals(false);
/* 710 */       return this;
/*     */     }
/* 712 */     if (paramArrayOfInt1.length != paramArrayOfInt2.length) {
/* 713 */       setEquals(false);
/* 714 */       return this;
/*     */     }
/* 716 */     for (int i = 0; (i < paramArrayOfInt1.length) && (this.isEquals); i++) {
/* 717 */       append(paramArrayOfInt1[i], paramArrayOfInt2[i]);
/*     */     }
/* 719 */     return this;
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
/*     */   public EqualsBuilder append(short[] paramArrayOfShort1, short[] paramArrayOfShort2)
/*     */   {
/* 733 */     if (!this.isEquals) {
/* 734 */       return this;
/*     */     }
/* 736 */     if (paramArrayOfShort1 == paramArrayOfShort2) {
/* 737 */       return this;
/*     */     }
/* 739 */     if ((paramArrayOfShort1 == null) || (paramArrayOfShort2 == null)) {
/* 740 */       setEquals(false);
/* 741 */       return this;
/*     */     }
/* 743 */     if (paramArrayOfShort1.length != paramArrayOfShort2.length) {
/* 744 */       setEquals(false);
/* 745 */       return this;
/*     */     }
/* 747 */     for (int i = 0; (i < paramArrayOfShort1.length) && (this.isEquals); i++) {
/* 748 */       append(paramArrayOfShort1[i], paramArrayOfShort2[i]);
/*     */     }
/* 750 */     return this;
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
/*     */   public EqualsBuilder append(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
/*     */   {
/* 764 */     if (!this.isEquals) {
/* 765 */       return this;
/*     */     }
/* 767 */     if (paramArrayOfChar1 == paramArrayOfChar2) {
/* 768 */       return this;
/*     */     }
/* 770 */     if ((paramArrayOfChar1 == null) || (paramArrayOfChar2 == null)) {
/* 771 */       setEquals(false);
/* 772 */       return this;
/*     */     }
/* 774 */     if (paramArrayOfChar1.length != paramArrayOfChar2.length) {
/* 775 */       setEquals(false);
/* 776 */       return this;
/*     */     }
/* 778 */     for (int i = 0; (i < paramArrayOfChar1.length) && (this.isEquals); i++) {
/* 779 */       append(paramArrayOfChar1[i], paramArrayOfChar2[i]);
/*     */     }
/* 781 */     return this;
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
/*     */   public EqualsBuilder append(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 795 */     if (!this.isEquals) {
/* 796 */       return this;
/*     */     }
/* 798 */     if (paramArrayOfByte1 == paramArrayOfByte2) {
/* 799 */       return this;
/*     */     }
/* 801 */     if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null)) {
/* 802 */       setEquals(false);
/* 803 */       return this;
/*     */     }
/* 805 */     if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
/* 806 */       setEquals(false);
/* 807 */       return this;
/*     */     }
/* 809 */     for (int i = 0; (i < paramArrayOfByte1.length) && (this.isEquals); i++) {
/* 810 */       append(paramArrayOfByte1[i], paramArrayOfByte2[i]);
/*     */     }
/* 812 */     return this;
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
/*     */   public EqualsBuilder append(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*     */   {
/* 826 */     if (!this.isEquals) {
/* 827 */       return this;
/*     */     }
/* 829 */     if (paramArrayOfDouble1 == paramArrayOfDouble2) {
/* 830 */       return this;
/*     */     }
/* 832 */     if ((paramArrayOfDouble1 == null) || (paramArrayOfDouble2 == null)) {
/* 833 */       setEquals(false);
/* 834 */       return this;
/*     */     }
/* 836 */     if (paramArrayOfDouble1.length != paramArrayOfDouble2.length) {
/* 837 */       setEquals(false);
/* 838 */       return this;
/*     */     }
/* 840 */     for (int i = 0; (i < paramArrayOfDouble1.length) && (this.isEquals); i++) {
/* 841 */       append(paramArrayOfDouble1[i], paramArrayOfDouble2[i]);
/*     */     }
/* 843 */     return this;
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
/*     */   public EqualsBuilder append(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*     */   {
/* 857 */     if (!this.isEquals) {
/* 858 */       return this;
/*     */     }
/* 860 */     if (paramArrayOfFloat1 == paramArrayOfFloat2) {
/* 861 */       return this;
/*     */     }
/* 863 */     if ((paramArrayOfFloat1 == null) || (paramArrayOfFloat2 == null)) {
/* 864 */       setEquals(false);
/* 865 */       return this;
/*     */     }
/* 867 */     if (paramArrayOfFloat1.length != paramArrayOfFloat2.length) {
/* 868 */       setEquals(false);
/* 869 */       return this;
/*     */     }
/* 871 */     for (int i = 0; (i < paramArrayOfFloat1.length) && (this.isEquals); i++) {
/* 872 */       append(paramArrayOfFloat1[i], paramArrayOfFloat2[i]);
/*     */     }
/* 874 */     return this;
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
/*     */   public EqualsBuilder append(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
/*     */   {
/* 888 */     if (!this.isEquals) {
/* 889 */       return this;
/*     */     }
/* 891 */     if (paramArrayOfBoolean1 == paramArrayOfBoolean2) {
/* 892 */       return this;
/*     */     }
/* 894 */     if ((paramArrayOfBoolean1 == null) || (paramArrayOfBoolean2 == null)) {
/* 895 */       setEquals(false);
/* 896 */       return this;
/*     */     }
/* 898 */     if (paramArrayOfBoolean1.length != paramArrayOfBoolean2.length) {
/* 899 */       setEquals(false);
/* 900 */       return this;
/*     */     }
/* 902 */     for (int i = 0; (i < paramArrayOfBoolean1.length) && (this.isEquals); i++) {
/* 903 */       append(paramArrayOfBoolean1[i], paramArrayOfBoolean2[i]);
/*     */     }
/* 905 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEquals()
/*     */   {
/* 915 */     return this.isEquals;
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
/*     */   public Boolean build()
/*     */   {
/* 929 */     return Boolean.valueOf(isEquals());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setEquals(boolean paramBoolean)
/*     */   {
/* 939 */     this.isEquals = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 947 */     this.isEquals = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\EqualsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */