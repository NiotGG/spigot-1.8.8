/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Range<T>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final Comparator<T> comparator;
/*     */   private final T minimum;
/*     */   private final T maximum;
/*     */   private transient int hashCode;
/*     */   private transient String toString;
/*     */   
/*     */   public static <T extends Comparable<T>> Range<T> is(T paramT)
/*     */   {
/*  76 */     return between(paramT, paramT, null);
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
/*     */   public static <T> Range<T> is(T paramT, Comparator<T> paramComparator)
/*     */   {
/*  94 */     return between(paramT, paramT, paramComparator);
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
/*     */   public static <T extends Comparable<T>> Range<T> between(T paramT1, T paramT2)
/*     */   {
/* 114 */     return between(paramT1, paramT2, null);
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
/*     */   public static <T> Range<T> between(T paramT1, T paramT2, Comparator<T> paramComparator)
/*     */   {
/* 135 */     return new Range(paramT1, paramT2, paramComparator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Range(T paramT1, T paramT2, Comparator<T> paramComparator)
/*     */   {
/* 147 */     if ((paramT1 == null) || (paramT2 == null)) {
/* 148 */       throw new IllegalArgumentException("Elements in a range must not be null: element1=" + paramT1 + ", element2=" + paramT2);
/*     */     }
/*     */     
/* 151 */     if (paramComparator == null) {
/* 152 */       this.comparator = ComparableComparator.INSTANCE;
/*     */     } else {
/* 154 */       this.comparator = paramComparator;
/*     */     }
/* 156 */     if (this.comparator.compare(paramT1, paramT2) < 1) {
/* 157 */       this.minimum = paramT1;
/* 158 */       this.maximum = paramT2;
/*     */     } else {
/* 160 */       this.minimum = paramT2;
/* 161 */       this.maximum = paramT1;
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
/*     */   public T getMinimum()
/*     */   {
/* 174 */     return (T)this.minimum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T getMaximum()
/*     */   {
/* 183 */     return (T)this.maximum;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Comparator<T> getComparator()
/*     */   {
/* 195 */     return this.comparator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isNaturalOrdering()
/*     */   {
/* 207 */     return this.comparator == ComparableComparator.INSTANCE;
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
/*     */   public boolean contains(T paramT)
/*     */   {
/* 220 */     if (paramT == null) {
/* 221 */       return false;
/*     */     }
/* 223 */     return (this.comparator.compare(paramT, this.minimum) > -1) && (this.comparator.compare(paramT, this.maximum) < 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAfter(T paramT)
/*     */   {
/* 233 */     if (paramT == null) {
/* 234 */       return false;
/*     */     }
/* 236 */     return this.comparator.compare(paramT, this.minimum) < 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStartedBy(T paramT)
/*     */   {
/* 246 */     if (paramT == null) {
/* 247 */       return false;
/*     */     }
/* 249 */     return this.comparator.compare(paramT, this.minimum) == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEndedBy(T paramT)
/*     */   {
/* 259 */     if (paramT == null) {
/* 260 */       return false;
/*     */     }
/* 262 */     return this.comparator.compare(paramT, this.maximum) == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBefore(T paramT)
/*     */   {
/* 272 */     if (paramT == null) {
/* 273 */       return false;
/*     */     }
/* 275 */     return this.comparator.compare(paramT, this.maximum) > 0;
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
/*     */   public int elementCompareTo(T paramT)
/*     */   {
/* 289 */     if (paramT == null)
/*     */     {
/* 291 */       throw new NullPointerException("Element is null");
/*     */     }
/* 293 */     if (isAfter(paramT))
/* 294 */       return -1;
/* 295 */     if (isBefore(paramT)) {
/* 296 */       return 1;
/*     */     }
/* 298 */     return 0;
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
/*     */   public boolean containsRange(Range<T> paramRange)
/*     */   {
/* 315 */     if (paramRange == null) {
/* 316 */       return false;
/*     */     }
/* 318 */     return (contains(paramRange.minimum)) && (contains(paramRange.maximum));
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
/*     */   public boolean isAfterRange(Range<T> paramRange)
/*     */   {
/* 332 */     if (paramRange == null) {
/* 333 */       return false;
/*     */     }
/* 335 */     return isAfter(paramRange.maximum);
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
/*     */   public boolean isOverlappedBy(Range<T> paramRange)
/*     */   {
/* 351 */     if (paramRange == null) {
/* 352 */       return false;
/*     */     }
/* 354 */     return (paramRange.contains(this.minimum)) || (paramRange.contains(this.maximum)) || (contains(paramRange.minimum));
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
/*     */   public boolean isBeforeRange(Range<T> paramRange)
/*     */   {
/* 369 */     if (paramRange == null) {
/* 370 */       return false;
/*     */     }
/* 372 */     return isBefore(paramRange.minimum);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Range<T> intersectionWith(Range<T> paramRange)
/*     */   {
/* 383 */     if (!isOverlappedBy(paramRange)) {
/* 384 */       throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", new Object[] { paramRange }));
/*     */     }
/*     */     
/* 387 */     if (equals(paramRange)) {
/* 388 */       return this;
/*     */     }
/* 390 */     Object localObject1 = getComparator().compare(this.minimum, paramRange.minimum) < 0 ? paramRange.minimum : this.minimum;
/* 391 */     Object localObject2 = getComparator().compare(this.maximum, paramRange.maximum) < 0 ? this.maximum : paramRange.maximum;
/* 392 */     return between(localObject1, localObject2, getComparator());
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 409 */     if (paramObject == this)
/* 410 */       return true;
/* 411 */     if ((paramObject == null) || (paramObject.getClass() != getClass())) {
/* 412 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 416 */     Range localRange = (Range)paramObject;
/* 417 */     return (this.minimum.equals(localRange.minimum)) && (this.maximum.equals(localRange.maximum));
/*     */   }
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
/* 429 */     int i = this.hashCode;
/* 430 */     if (this.hashCode == 0) {
/* 431 */       i = 17;
/* 432 */       i = 37 * i + getClass().hashCode();
/* 433 */       i = 37 * i + this.minimum.hashCode();
/* 434 */       i = 37 * i + this.maximum.hashCode();
/* 435 */       this.hashCode = i;
/*     */     }
/* 437 */     return i;
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
/* 449 */     String str = this.toString;
/* 450 */     if (str == null) {
/* 451 */       StringBuilder localStringBuilder = new StringBuilder(32);
/* 452 */       localStringBuilder.append('[');
/* 453 */       localStringBuilder.append(this.minimum);
/* 454 */       localStringBuilder.append("..");
/* 455 */       localStringBuilder.append(this.maximum);
/* 456 */       localStringBuilder.append(']');
/* 457 */       str = localStringBuilder.toString();
/* 458 */       this.toString = str;
/*     */     }
/* 460 */     return str;
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
/*     */   public String toString(String paramString)
/*     */   {
/* 476 */     return String.format(paramString, new Object[] { this.minimum, this.maximum, this.comparator });
/*     */   }
/*     */   
/*     */   private static enum ComparableComparator
/*     */     implements Comparator
/*     */   {
/* 482 */     INSTANCE;
/*     */     
/*     */ 
/*     */ 
/*     */     private ComparableComparator() {}
/*     */     
/*     */ 
/*     */ 
/*     */     public int compare(Object paramObject1, Object paramObject2)
/*     */     {
/* 492 */       return ((Comparable)paramObject1).compareTo(paramObject2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\Range.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */