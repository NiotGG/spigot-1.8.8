/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ public class DiffBuilder
/*     */   implements Builder<DiffResult>
/*     */ {
/*     */   private final List<Diff<?>> diffs;
/*     */   private final boolean objectsTriviallyEqual;
/*     */   private final Object left;
/*     */   private final Object right;
/*     */   private final ToStringStyle style;
/*     */   
/*     */   public DiffBuilder(Object paramObject1, Object paramObject2, ToStringStyle paramToStringStyle)
/*     */   {
/*  98 */     if (paramObject1 == null) {
/*  99 */       throw new IllegalArgumentException("lhs cannot be null");
/*     */     }
/* 101 */     if (paramObject2 == null) {
/* 102 */       throw new IllegalArgumentException("rhs cannot be null");
/*     */     }
/*     */     
/* 105 */     this.diffs = new ArrayList();
/* 106 */     this.left = paramObject1;
/* 107 */     this.right = paramObject2;
/* 108 */     this.style = paramToStringStyle;
/*     */     
/*     */ 
/* 111 */     this.objectsTriviallyEqual = ((paramObject1 == paramObject2) || (paramObject1.equals(paramObject2)));
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
/*     */   public DiffBuilder append(String paramString, final boolean paramBoolean1, final boolean paramBoolean2)
/*     */   {
/* 131 */     if (paramString == null) {
/* 132 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 135 */     if (this.objectsTriviallyEqual) {
/* 136 */       return this;
/*     */     }
/* 138 */     if (paramBoolean1 != paramBoolean2) {
/* 139 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Boolean getLeft() {
/* 144 */           return Boolean.valueOf(paramBoolean1);
/*     */         }
/*     */         
/*     */         public Boolean getRight()
/*     */         {
/* 149 */           return Boolean.valueOf(paramBoolean2);
/*     */         }
/*     */       });
/*     */     }
/* 153 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final boolean[] paramArrayOfBoolean1, final boolean[] paramArrayOfBoolean2)
/*     */   {
/* 173 */     if (paramString == null) {
/* 174 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/* 176 */     if (this.objectsTriviallyEqual) {
/* 177 */       return this;
/*     */     }
/* 179 */     if (!Arrays.equals(paramArrayOfBoolean1, paramArrayOfBoolean2)) {
/* 180 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Boolean[] getLeft() {
/* 185 */           return ArrayUtils.toObject(paramArrayOfBoolean1);
/*     */         }
/*     */         
/*     */         public Boolean[] getRight()
/*     */         {
/* 190 */           return ArrayUtils.toObject(paramArrayOfBoolean2);
/*     */         }
/*     */       });
/*     */     }
/* 194 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final byte paramByte1, final byte paramByte2)
/*     */   {
/* 214 */     if (paramString == null) {
/* 215 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/* 217 */     if (this.objectsTriviallyEqual) {
/* 218 */       return this;
/*     */     }
/* 220 */     if (paramByte1 != paramByte2) {
/* 221 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Byte getLeft() {
/* 226 */           return Byte.valueOf(paramByte1);
/*     */         }
/*     */         
/*     */         public Byte getRight()
/*     */         {
/* 231 */           return Byte.valueOf(paramByte2);
/*     */         }
/*     */       });
/*     */     }
/* 235 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final byte[] paramArrayOfByte1, final byte[] paramArrayOfByte2)
/*     */   {
/* 255 */     if (paramString == null) {
/* 256 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 259 */     if (this.objectsTriviallyEqual) {
/* 260 */       return this;
/*     */     }
/* 262 */     if (!Arrays.equals(paramArrayOfByte1, paramArrayOfByte2)) {
/* 263 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Byte[] getLeft() {
/* 268 */           return ArrayUtils.toObject(paramArrayOfByte1);
/*     */         }
/*     */         
/*     */         public Byte[] getRight()
/*     */         {
/* 273 */           return ArrayUtils.toObject(paramArrayOfByte2);
/*     */         }
/*     */       });
/*     */     }
/* 277 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final char paramChar1, final char paramChar2)
/*     */   {
/* 297 */     if (paramString == null) {
/* 298 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 301 */     if (this.objectsTriviallyEqual) {
/* 302 */       return this;
/*     */     }
/* 304 */     if (paramChar1 != paramChar2) {
/* 305 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Character getLeft() {
/* 310 */           return Character.valueOf(paramChar1);
/*     */         }
/*     */         
/*     */         public Character getRight()
/*     */         {
/* 315 */           return Character.valueOf(paramChar2);
/*     */         }
/*     */       });
/*     */     }
/* 319 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final char[] paramArrayOfChar1, final char[] paramArrayOfChar2)
/*     */   {
/* 339 */     if (paramString == null) {
/* 340 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 343 */     if (this.objectsTriviallyEqual) {
/* 344 */       return this;
/*     */     }
/* 346 */     if (!Arrays.equals(paramArrayOfChar1, paramArrayOfChar2)) {
/* 347 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Character[] getLeft() {
/* 352 */           return ArrayUtils.toObject(paramArrayOfChar1);
/*     */         }
/*     */         
/*     */         public Character[] getRight()
/*     */         {
/* 357 */           return ArrayUtils.toObject(paramArrayOfChar2);
/*     */         }
/*     */       });
/*     */     }
/* 361 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final double paramDouble1, double paramDouble2)
/*     */   {
/* 381 */     if (paramString == null) {
/* 382 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 385 */     if (this.objectsTriviallyEqual) {
/* 386 */       return this;
/*     */     }
/* 388 */     if (Double.doubleToLongBits(paramDouble1) != Double.doubleToLongBits(paramDouble2)) {
/* 389 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Double getLeft() {
/* 394 */           return Double.valueOf(paramDouble1);
/*     */         }
/*     */         
/*     */         public Double getRight()
/*     */         {
/* 399 */           return Double.valueOf(this.val$rhs);
/*     */         }
/*     */       });
/*     */     }
/* 403 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final double[] paramArrayOfDouble1, final double[] paramArrayOfDouble2)
/*     */   {
/* 423 */     if (paramString == null) {
/* 424 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 427 */     if (this.objectsTriviallyEqual) {
/* 428 */       return this;
/*     */     }
/* 430 */     if (!Arrays.equals(paramArrayOfDouble1, paramArrayOfDouble2)) {
/* 431 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Double[] getLeft() {
/* 436 */           return ArrayUtils.toObject(paramArrayOfDouble1);
/*     */         }
/*     */         
/*     */         public Double[] getRight()
/*     */         {
/* 441 */           return ArrayUtils.toObject(paramArrayOfDouble2);
/*     */         }
/*     */       });
/*     */     }
/* 445 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final float paramFloat1, final float paramFloat2)
/*     */   {
/* 465 */     if (paramString == null) {
/* 466 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 469 */     if (this.objectsTriviallyEqual) {
/* 470 */       return this;
/*     */     }
/* 472 */     if (Float.floatToIntBits(paramFloat1) != Float.floatToIntBits(paramFloat2)) {
/* 473 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Float getLeft() {
/* 478 */           return Float.valueOf(paramFloat1);
/*     */         }
/*     */         
/*     */         public Float getRight()
/*     */         {
/* 483 */           return Float.valueOf(paramFloat2);
/*     */         }
/*     */       });
/*     */     }
/* 487 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final float[] paramArrayOfFloat1, final float[] paramArrayOfFloat2)
/*     */   {
/* 507 */     if (paramString == null) {
/* 508 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 511 */     if (this.objectsTriviallyEqual) {
/* 512 */       return this;
/*     */     }
/* 514 */     if (!Arrays.equals(paramArrayOfFloat1, paramArrayOfFloat2)) {
/* 515 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Float[] getLeft() {
/* 520 */           return ArrayUtils.toObject(paramArrayOfFloat1);
/*     */         }
/*     */         
/*     */         public Float[] getRight()
/*     */         {
/* 525 */           return ArrayUtils.toObject(paramArrayOfFloat2);
/*     */         }
/*     */       });
/*     */     }
/* 529 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final int paramInt1, final int paramInt2)
/*     */   {
/* 549 */     if (paramString == null) {
/* 550 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 553 */     if (this.objectsTriviallyEqual) {
/* 554 */       return this;
/*     */     }
/* 556 */     if (paramInt1 != paramInt2) {
/* 557 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Integer getLeft() {
/* 562 */           return Integer.valueOf(paramInt1);
/*     */         }
/*     */         
/*     */         public Integer getRight()
/*     */         {
/* 567 */           return Integer.valueOf(paramInt2);
/*     */         }
/*     */       });
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public DiffBuilder append(String paramString, final int[] paramArrayOfInt1, final int[] paramArrayOfInt2)
/*     */   {
/* 591 */     if (paramString == null) {
/* 592 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 595 */     if (this.objectsTriviallyEqual) {
/* 596 */       return this;
/*     */     }
/* 598 */     if (!Arrays.equals(paramArrayOfInt1, paramArrayOfInt2)) {
/* 599 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Integer[] getLeft() {
/* 604 */           return ArrayUtils.toObject(paramArrayOfInt1);
/*     */         }
/*     */         
/*     */         public Integer[] getRight()
/*     */         {
/* 609 */           return ArrayUtils.toObject(paramArrayOfInt2);
/*     */         }
/*     */       });
/*     */     }
/* 613 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final long paramLong1, long paramLong2)
/*     */   {
/* 633 */     if (paramString == null) {
/* 634 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 637 */     if (this.objectsTriviallyEqual) {
/* 638 */       return this;
/*     */     }
/* 640 */     if (paramLong1 != paramLong2) {
/* 641 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Long getLeft() {
/* 646 */           return Long.valueOf(paramLong1);
/*     */         }
/*     */         
/*     */         public Long getRight()
/*     */         {
/* 651 */           return Long.valueOf(this.val$rhs);
/*     */         }
/*     */       });
/*     */     }
/* 655 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final long[] paramArrayOfLong1, final long[] paramArrayOfLong2)
/*     */   {
/* 675 */     if (paramString == null) {
/* 676 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 679 */     if (this.objectsTriviallyEqual) {
/* 680 */       return this;
/*     */     }
/* 682 */     if (!Arrays.equals(paramArrayOfLong1, paramArrayOfLong2)) {
/* 683 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Long[] getLeft() {
/* 688 */           return ArrayUtils.toObject(paramArrayOfLong1);
/*     */         }
/*     */         
/*     */         public Long[] getRight()
/*     */         {
/* 693 */           return ArrayUtils.toObject(paramArrayOfLong2);
/*     */         }
/*     */       });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DiffBuilder append(String paramString, final short paramShort1, final short paramShort2)
/*     */   {
/* 717 */     if (paramString == null) {
/* 718 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 721 */     if (this.objectsTriviallyEqual) {
/* 722 */       return this;
/*     */     }
/* 724 */     if (paramShort1 != paramShort2) {
/* 725 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Short getLeft() {
/* 730 */           return Short.valueOf(paramShort1);
/*     */         }
/*     */         
/*     */         public Short getRight()
/*     */         {
/* 735 */           return Short.valueOf(paramShort2);
/*     */         }
/*     */       });
/*     */     }
/* 739 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final short[] paramArrayOfShort1, final short[] paramArrayOfShort2)
/*     */   {
/* 759 */     if (paramString == null) {
/* 760 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 763 */     if (this.objectsTriviallyEqual) {
/* 764 */       return this;
/*     */     }
/* 766 */     if (!Arrays.equals(paramArrayOfShort1, paramArrayOfShort2)) {
/* 767 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Short[] getLeft() {
/* 772 */           return ArrayUtils.toObject(paramArrayOfShort1);
/*     */         }
/*     */         
/*     */         public Short[] getRight()
/*     */         {
/* 777 */           return ArrayUtils.toObject(paramArrayOfShort2);
/*     */         }
/*     */       });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DiffBuilder append(String paramString, final Object paramObject1, final Object paramObject2)
/*     */   {
/* 800 */     if (this.objectsTriviallyEqual) {
/* 801 */       return this;
/*     */     }
/* 803 */     if (paramObject1 == paramObject2) {
/* 804 */       return this;
/*     */     }
/*     */     
/*     */     Object localObject;
/* 808 */     if (paramObject1 != null) {
/* 809 */       localObject = paramObject1;
/*     */     }
/*     */     else {
/* 812 */       localObject = paramObject2;
/*     */     }
/*     */     
/* 815 */     if (localObject.getClass().isArray()) {
/* 816 */       if ((localObject instanceof boolean[])) {
/* 817 */         return append(paramString, (boolean[])paramObject1, (boolean[])paramObject2);
/*     */       }
/* 819 */       if ((localObject instanceof byte[])) {
/* 820 */         return append(paramString, (byte[])paramObject1, (byte[])paramObject2);
/*     */       }
/* 822 */       if ((localObject instanceof char[])) {
/* 823 */         return append(paramString, (char[])paramObject1, (char[])paramObject2);
/*     */       }
/* 825 */       if ((localObject instanceof double[])) {
/* 826 */         return append(paramString, (double[])paramObject1, (double[])paramObject2);
/*     */       }
/* 828 */       if ((localObject instanceof float[])) {
/* 829 */         return append(paramString, (float[])paramObject1, (float[])paramObject2);
/*     */       }
/* 831 */       if ((localObject instanceof int[])) {
/* 832 */         return append(paramString, (int[])paramObject1, (int[])paramObject2);
/*     */       }
/* 834 */       if ((localObject instanceof long[])) {
/* 835 */         return append(paramString, (long[])paramObject1, (long[])paramObject2);
/*     */       }
/* 837 */       if ((localObject instanceof short[])) {
/* 838 */         return append(paramString, (short[])paramObject1, (short[])paramObject2);
/*     */       }
/*     */       
/* 841 */       return append(paramString, (Object[])paramObject1, (Object[])paramObject2);
/*     */     }
/*     */     
/*     */ 
/* 845 */     this.diffs.add(new Diff(paramString)
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public Object getLeft() {
/* 850 */         return paramObject1;
/*     */       }
/*     */       
/*     */       public Object getRight()
/*     */       {
/* 855 */         return paramObject2;
/*     */       }
/*     */       
/* 858 */     });
/* 859 */     return this;
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
/*     */   public DiffBuilder append(String paramString, final Object[] paramArrayOfObject1, final Object[] paramArrayOfObject2)
/*     */   {
/* 877 */     if (this.objectsTriviallyEqual) {
/* 878 */       return this;
/*     */     }
/*     */     
/* 881 */     if (!Arrays.equals(paramArrayOfObject1, paramArrayOfObject2)) {
/* 882 */       this.diffs.add(new Diff(paramString)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */         
/*     */         public Object[] getLeft() {
/* 887 */           return paramArrayOfObject1;
/*     */         }
/*     */         
/*     */         public Object[] getRight()
/*     */         {
/* 892 */           return paramArrayOfObject2;
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 897 */     return this;
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
/*     */   public DiffResult build()
/*     */   {
/* 911 */     return new DiffResult(this.left, this.right, this.diffs, this.style);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\DiffBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */