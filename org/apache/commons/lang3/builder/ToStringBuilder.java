/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ToStringBuilder
/*      */   implements Builder<String>
/*      */ {
/*   94 */   private static volatile ToStringStyle defaultStyle = ToStringStyle.DEFAULT_STYLE;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final StringBuffer buffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final Object object;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private final ToStringStyle style;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ToStringStyle getDefaultStyle()
/*      */   {
/*  117 */     return defaultStyle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void setDefaultStyle(ToStringStyle paramToStringStyle)
/*      */   {
/*  136 */     if (paramToStringStyle == null) {
/*  137 */       throw new IllegalArgumentException("The style must not be null");
/*      */     }
/*  139 */     defaultStyle = paramToStringStyle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String reflectionToString(Object paramObject)
/*      */   {
/*  152 */     return ReflectionToStringBuilder.toString(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String reflectionToString(Object paramObject, ToStringStyle paramToStringStyle)
/*      */   {
/*  165 */     return ReflectionToStringBuilder.toString(paramObject, paramToStringStyle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String reflectionToString(Object paramObject, ToStringStyle paramToStringStyle, boolean paramBoolean)
/*      */   {
/*  179 */     return ReflectionToStringBuilder.toString(paramObject, paramToStringStyle, paramBoolean, false, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <T> String reflectionToString(T paramT, ToStringStyle paramToStringStyle, boolean paramBoolean, Class<? super T> paramClass)
/*      */   {
/*  200 */     return ReflectionToStringBuilder.toString(paramT, paramToStringStyle, paramBoolean, false, paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder(Object paramObject)
/*      */   {
/*  226 */     this(paramObject, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle)
/*      */   {
/*  238 */     this(paramObject, paramToStringStyle, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder(Object paramObject, ToStringStyle paramToStringStyle, StringBuffer paramStringBuffer)
/*      */   {
/*  253 */     if (paramToStringStyle == null) {
/*  254 */       paramToStringStyle = getDefaultStyle();
/*      */     }
/*  256 */     if (paramStringBuffer == null) {
/*  257 */       paramStringBuffer = new StringBuffer(512);
/*      */     }
/*  259 */     this.buffer = paramStringBuffer;
/*  260 */     this.style = paramToStringStyle;
/*  261 */     this.object = paramObject;
/*      */     
/*  263 */     paramToStringStyle.appendStart(paramStringBuffer, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(boolean paramBoolean)
/*      */   {
/*  276 */     this.style.append(this.buffer, null, paramBoolean);
/*  277 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(boolean[] paramArrayOfBoolean)
/*      */   {
/*  290 */     this.style.append(this.buffer, null, paramArrayOfBoolean, null);
/*  291 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(byte paramByte)
/*      */   {
/*  304 */     this.style.append(this.buffer, null, paramByte);
/*  305 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(byte[] paramArrayOfByte)
/*      */   {
/*  318 */     this.style.append(this.buffer, null, paramArrayOfByte, null);
/*  319 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(char paramChar)
/*      */   {
/*  332 */     this.style.append(this.buffer, null, paramChar);
/*  333 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(char[] paramArrayOfChar)
/*      */   {
/*  346 */     this.style.append(this.buffer, null, paramArrayOfChar, null);
/*  347 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(double paramDouble)
/*      */   {
/*  360 */     this.style.append(this.buffer, null, paramDouble);
/*  361 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(double[] paramArrayOfDouble)
/*      */   {
/*  374 */     this.style.append(this.buffer, null, paramArrayOfDouble, null);
/*  375 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(float paramFloat)
/*      */   {
/*  388 */     this.style.append(this.buffer, null, paramFloat);
/*  389 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(float[] paramArrayOfFloat)
/*      */   {
/*  402 */     this.style.append(this.buffer, null, paramArrayOfFloat, null);
/*  403 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(int paramInt)
/*      */   {
/*  416 */     this.style.append(this.buffer, null, paramInt);
/*  417 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(int[] paramArrayOfInt)
/*      */   {
/*  430 */     this.style.append(this.buffer, null, paramArrayOfInt, null);
/*  431 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(long paramLong)
/*      */   {
/*  444 */     this.style.append(this.buffer, null, paramLong);
/*  445 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(long[] paramArrayOfLong)
/*      */   {
/*  458 */     this.style.append(this.buffer, null, paramArrayOfLong, null);
/*  459 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(Object paramObject)
/*      */   {
/*  472 */     this.style.append(this.buffer, null, paramObject, null);
/*  473 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(Object[] paramArrayOfObject)
/*      */   {
/*  486 */     this.style.append(this.buffer, null, paramArrayOfObject, null);
/*  487 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(short paramShort)
/*      */   {
/*  500 */     this.style.append(this.buffer, null, paramShort);
/*  501 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(short[] paramArrayOfShort)
/*      */   {
/*  514 */     this.style.append(this.buffer, null, paramArrayOfShort, null);
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
/*      */   public ToStringBuilder append(String paramString, boolean paramBoolean)
/*      */   {
/*  527 */     this.style.append(this.buffer, paramString, paramBoolean);
/*  528 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, boolean[] paramArrayOfBoolean)
/*      */   {
/*  540 */     this.style.append(this.buffer, paramString, paramArrayOfBoolean, null);
/*  541 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, boolean[] paramArrayOfBoolean, boolean paramBoolean)
/*      */   {
/*  560 */     this.style.append(this.buffer, paramString, paramArrayOfBoolean, Boolean.valueOf(paramBoolean));
/*  561 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, byte paramByte)
/*      */   {
/*  573 */     this.style.append(this.buffer, paramString, paramByte);
/*  574 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, byte[] paramArrayOfByte)
/*      */   {
/*  585 */     this.style.append(this.buffer, paramString, paramArrayOfByte, null);
/*  586 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
/*      */   {
/*  605 */     this.style.append(this.buffer, paramString, paramArrayOfByte, Boolean.valueOf(paramBoolean));
/*  606 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, char paramChar)
/*      */   {
/*  618 */     this.style.append(this.buffer, paramString, paramChar);
/*  619 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, char[] paramArrayOfChar)
/*      */   {
/*  631 */     this.style.append(this.buffer, paramString, paramArrayOfChar, null);
/*  632 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, char[] paramArrayOfChar, boolean paramBoolean)
/*      */   {
/*  651 */     this.style.append(this.buffer, paramString, paramArrayOfChar, Boolean.valueOf(paramBoolean));
/*  652 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, double paramDouble)
/*      */   {
/*  664 */     this.style.append(this.buffer, paramString, paramDouble);
/*  665 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, double[] paramArrayOfDouble)
/*      */   {
/*  677 */     this.style.append(this.buffer, paramString, paramArrayOfDouble, null);
/*  678 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, double[] paramArrayOfDouble, boolean paramBoolean)
/*      */   {
/*  697 */     this.style.append(this.buffer, paramString, paramArrayOfDouble, Boolean.valueOf(paramBoolean));
/*  698 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, float paramFloat)
/*      */   {
/*  710 */     this.style.append(this.buffer, paramString, paramFloat);
/*  711 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, float[] paramArrayOfFloat)
/*      */   {
/*  723 */     this.style.append(this.buffer, paramString, paramArrayOfFloat, null);
/*  724 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, float[] paramArrayOfFloat, boolean paramBoolean)
/*      */   {
/*  743 */     this.style.append(this.buffer, paramString, paramArrayOfFloat, Boolean.valueOf(paramBoolean));
/*  744 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, int paramInt)
/*      */   {
/*  756 */     this.style.append(this.buffer, paramString, paramInt);
/*  757 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, int[] paramArrayOfInt)
/*      */   {
/*  769 */     this.style.append(this.buffer, paramString, paramArrayOfInt, null);
/*  770 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, int[] paramArrayOfInt, boolean paramBoolean)
/*      */   {
/*  789 */     this.style.append(this.buffer, paramString, paramArrayOfInt, Boolean.valueOf(paramBoolean));
/*  790 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, long paramLong)
/*      */   {
/*  802 */     this.style.append(this.buffer, paramString, paramLong);
/*  803 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, long[] paramArrayOfLong)
/*      */   {
/*  815 */     this.style.append(this.buffer, paramString, paramArrayOfLong, null);
/*  816 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, long[] paramArrayOfLong, boolean paramBoolean)
/*      */   {
/*  835 */     this.style.append(this.buffer, paramString, paramArrayOfLong, Boolean.valueOf(paramBoolean));
/*  836 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, Object paramObject)
/*      */   {
/*  848 */     this.style.append(this.buffer, paramString, paramObject, null);
/*  849 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, Object paramObject, boolean paramBoolean)
/*      */   {
/*  863 */     this.style.append(this.buffer, paramString, paramObject, Boolean.valueOf(paramBoolean));
/*  864 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, Object[] paramArrayOfObject)
/*      */   {
/*  876 */     this.style.append(this.buffer, paramString, paramArrayOfObject, null);
/*  877 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, Object[] paramArrayOfObject, boolean paramBoolean)
/*      */   {
/*  896 */     this.style.append(this.buffer, paramString, paramArrayOfObject, Boolean.valueOf(paramBoolean));
/*  897 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, short paramShort)
/*      */   {
/*  909 */     this.style.append(this.buffer, paramString, paramShort);
/*  910 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, short[] paramArrayOfShort)
/*      */   {
/*  922 */     this.style.append(this.buffer, paramString, paramArrayOfShort, null);
/*  923 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder append(String paramString, short[] paramArrayOfShort, boolean paramBoolean)
/*      */   {
/*  942 */     this.style.append(this.buffer, paramString, paramArrayOfShort, Boolean.valueOf(paramBoolean));
/*  943 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder appendAsObjectToString(Object paramObject)
/*      */   {
/*  956 */     ObjectUtils.identityToString(getStringBuffer(), paramObject);
/*  957 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder appendSuper(String paramString)
/*      */   {
/*  975 */     if (paramString != null) {
/*  976 */       this.style.appendSuper(this.buffer, paramString);
/*      */     }
/*  978 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringBuilder appendToString(String paramString)
/*      */   {
/* 1009 */     if (paramString != null) {
/* 1010 */       this.style.appendToString(this.buffer, paramString);
/*      */     }
/* 1012 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object getObject()
/*      */   {
/* 1022 */     return this.object;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StringBuffer getStringBuffer()
/*      */   {
/* 1031 */     return this.buffer;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ToStringStyle getStyle()
/*      */   {
/* 1043 */     return this.style;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1058 */     if (getObject() == null) {
/* 1059 */       getStringBuffer().append(getStyle().getNullText());
/*      */     } else {
/* 1061 */       this.style.appendEnd(getStringBuffer(), getObject());
/*      */     }
/* 1063 */     return getStringBuffer().toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String build()
/*      */   {
/* 1078 */     return toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\ToStringBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */