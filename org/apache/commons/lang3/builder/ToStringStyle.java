/*      */ package org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Collection;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import org.apache.commons.lang3.ClassUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.SystemUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ToStringStyle
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -2587890625525655916L;
/*   81 */   public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   95 */   public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  106 */   public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  118 */   public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  128 */   public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  136 */   private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY = new ThreadLocal();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static Map<Object, Object> getRegistry()
/*      */   {
/*  157 */     return (Map)REGISTRY.get();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static boolean isRegistered(Object paramObject)
/*      */   {
/*  172 */     Map localMap = getRegistry();
/*  173 */     return (localMap != null) && (localMap.containsKey(paramObject));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static void register(Object paramObject)
/*      */   {
/*  186 */     if (paramObject != null) {
/*  187 */       Map localMap = getRegistry();
/*  188 */       if (localMap == null) {
/*  189 */         REGISTRY.set(new WeakHashMap());
/*      */       }
/*  191 */       getRegistry().put(paramObject, null);
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
/*      */   static void unregister(Object paramObject)
/*      */   {
/*  208 */     if (paramObject != null) {
/*  209 */       Map localMap = getRegistry();
/*  210 */       if (localMap != null) {
/*  211 */         localMap.remove(paramObject);
/*  212 */         if (localMap.isEmpty()) {
/*  213 */           REGISTRY.remove();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  222 */   private boolean useFieldNames = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  227 */   private boolean useClassName = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  232 */   private boolean useShortClassName = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  237 */   private boolean useIdentityHashCode = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  242 */   private String contentStart = "[";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  247 */   private String contentEnd = "]";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  252 */   private String fieldNameValueSeparator = "=";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  257 */   private boolean fieldSeparatorAtStart = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  262 */   private boolean fieldSeparatorAtEnd = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  267 */   private String fieldSeparator = ",";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  272 */   private String arrayStart = "{";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  277 */   private String arraySeparator = ",";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  282 */   private boolean arrayContentDetail = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  287 */   private String arrayEnd = "}";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  293 */   private boolean defaultFullDetail = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  298 */   private String nullText = "<null>";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  303 */   private String sizeStartText = "<size=";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  308 */   private String sizeEndText = ">";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  313 */   private String summaryObjectStartText = "<";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  318 */   private String summaryObjectEndText = ">";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void appendSuper(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/*  342 */     appendToString(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void appendToString(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/*  356 */     if (paramString != null) {
/*  357 */       int i = paramString.indexOf(this.contentStart) + this.contentStart.length();
/*  358 */       int j = paramString.lastIndexOf(this.contentEnd);
/*  359 */       if ((i != j) && (i >= 0) && (j >= 0)) {
/*  360 */         String str = paramString.substring(i, j);
/*  361 */         if (this.fieldSeparatorAtStart) {
/*  362 */           removeLastFieldSeparator(paramStringBuffer);
/*      */         }
/*  364 */         paramStringBuffer.append(str);
/*  365 */         appendFieldSeparator(paramStringBuffer);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void appendStart(StringBuffer paramStringBuffer, Object paramObject)
/*      */   {
/*  377 */     if (paramObject != null) {
/*  378 */       appendClassName(paramStringBuffer, paramObject);
/*  379 */       appendIdentityHashCode(paramStringBuffer, paramObject);
/*  380 */       appendContentStart(paramStringBuffer);
/*  381 */       if (this.fieldSeparatorAtStart) {
/*  382 */         appendFieldSeparator(paramStringBuffer);
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
/*      */   public void appendEnd(StringBuffer paramStringBuffer, Object paramObject)
/*      */   {
/*  395 */     if (!this.fieldSeparatorAtEnd) {
/*  396 */       removeLastFieldSeparator(paramStringBuffer);
/*      */     }
/*  398 */     appendContentEnd(paramStringBuffer);
/*  399 */     unregister(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void removeLastFieldSeparator(StringBuffer paramStringBuffer)
/*      */   {
/*  409 */     int i = paramStringBuffer.length();
/*  410 */     int j = this.fieldSeparator.length();
/*  411 */     if ((i > 0) && (j > 0) && (i >= j)) {
/*  412 */       int k = 1;
/*  413 */       for (int m = 0; m < j; m++) {
/*  414 */         if (paramStringBuffer.charAt(i - 1 - m) != this.fieldSeparator.charAt(j - 1 - m)) {
/*  415 */           k = 0;
/*  416 */           break;
/*      */         }
/*      */       }
/*  419 */       if (k != 0) {
/*  420 */         paramStringBuffer.setLength(i - j);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, Object paramObject, Boolean paramBoolean)
/*      */   {
/*  439 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/*  441 */     if (paramObject == null) {
/*  442 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/*      */     else {
/*  445 */       appendInternal(paramStringBuffer, paramString, paramObject, isFullDetail(paramBoolean));
/*      */     }
/*      */     
/*  448 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendInternal(StringBuffer paramStringBuffer, String paramString, Object paramObject, boolean paramBoolean)
/*      */   {
/*  471 */     if ((isRegistered(paramObject)) && (!(paramObject instanceof Number)) && (!(paramObject instanceof Boolean)) && (!(paramObject instanceof Character)))
/*      */     {
/*  473 */       appendCyclicObject(paramStringBuffer, paramString, paramObject);
/*  474 */       return;
/*      */     }
/*      */     
/*  477 */     register(paramObject);
/*      */     try
/*      */     {
/*  480 */       if ((paramObject instanceof Collection)) {
/*  481 */         if (paramBoolean) {
/*  482 */           appendDetail(paramStringBuffer, paramString, (Collection)paramObject);
/*      */         } else {
/*  484 */           appendSummarySize(paramStringBuffer, paramString, ((Collection)paramObject).size());
/*      */         }
/*      */       }
/*  487 */       else if ((paramObject instanceof Map)) {
/*  488 */         if (paramBoolean) {
/*  489 */           appendDetail(paramStringBuffer, paramString, (Map)paramObject);
/*      */         } else {
/*  491 */           appendSummarySize(paramStringBuffer, paramString, ((Map)paramObject).size());
/*      */         }
/*      */       }
/*  494 */       else if ((paramObject instanceof long[])) {
/*  495 */         if (paramBoolean) {
/*  496 */           appendDetail(paramStringBuffer, paramString, (long[])paramObject);
/*      */         } else {
/*  498 */           appendSummary(paramStringBuffer, paramString, (long[])paramObject);
/*      */         }
/*      */       }
/*  501 */       else if ((paramObject instanceof int[])) {
/*  502 */         if (paramBoolean) {
/*  503 */           appendDetail(paramStringBuffer, paramString, (int[])paramObject);
/*      */         } else {
/*  505 */           appendSummary(paramStringBuffer, paramString, (int[])paramObject);
/*      */         }
/*      */       }
/*  508 */       else if ((paramObject instanceof short[])) {
/*  509 */         if (paramBoolean) {
/*  510 */           appendDetail(paramStringBuffer, paramString, (short[])paramObject);
/*      */         } else {
/*  512 */           appendSummary(paramStringBuffer, paramString, (short[])paramObject);
/*      */         }
/*      */       }
/*  515 */       else if ((paramObject instanceof byte[])) {
/*  516 */         if (paramBoolean) {
/*  517 */           appendDetail(paramStringBuffer, paramString, (byte[])paramObject);
/*      */         } else {
/*  519 */           appendSummary(paramStringBuffer, paramString, (byte[])paramObject);
/*      */         }
/*      */       }
/*  522 */       else if ((paramObject instanceof char[])) {
/*  523 */         if (paramBoolean) {
/*  524 */           appendDetail(paramStringBuffer, paramString, (char[])paramObject);
/*      */         } else {
/*  526 */           appendSummary(paramStringBuffer, paramString, (char[])paramObject);
/*      */         }
/*      */       }
/*  529 */       else if ((paramObject instanceof double[])) {
/*  530 */         if (paramBoolean) {
/*  531 */           appendDetail(paramStringBuffer, paramString, (double[])paramObject);
/*      */         } else {
/*  533 */           appendSummary(paramStringBuffer, paramString, (double[])paramObject);
/*      */         }
/*      */       }
/*  536 */       else if ((paramObject instanceof float[])) {
/*  537 */         if (paramBoolean) {
/*  538 */           appendDetail(paramStringBuffer, paramString, (float[])paramObject);
/*      */         } else {
/*  540 */           appendSummary(paramStringBuffer, paramString, (float[])paramObject);
/*      */         }
/*      */       }
/*  543 */       else if ((paramObject instanceof boolean[])) {
/*  544 */         if (paramBoolean) {
/*  545 */           appendDetail(paramStringBuffer, paramString, (boolean[])paramObject);
/*      */         } else {
/*  547 */           appendSummary(paramStringBuffer, paramString, (boolean[])paramObject);
/*      */         }
/*      */       }
/*  550 */       else if (paramObject.getClass().isArray()) {
/*  551 */         if (paramBoolean) {
/*  552 */           appendDetail(paramStringBuffer, paramString, (Object[])paramObject);
/*      */         } else {
/*  554 */           appendSummary(paramStringBuffer, paramString, (Object[])paramObject);
/*      */         }
/*      */         
/*      */       }
/*  558 */       else if (paramBoolean) {
/*  559 */         appendDetail(paramStringBuffer, paramString, paramObject);
/*      */       } else {
/*  561 */         appendSummary(paramStringBuffer, paramString, paramObject);
/*      */       }
/*      */     }
/*      */     finally {
/*  565 */       unregister(paramObject);
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
/*      */   protected void appendCyclicObject(StringBuffer paramStringBuffer, String paramString, Object paramObject)
/*      */   {
/*  582 */     ObjectUtils.identityToString(paramStringBuffer, paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Object paramObject)
/*      */   {
/*  595 */     paramStringBuffer.append(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Collection<?> paramCollection)
/*      */   {
/*  607 */     paramStringBuffer.append(paramCollection);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Map<?, ?> paramMap)
/*      */   {
/*  619 */     paramStringBuffer.append(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, Object paramObject)
/*      */   {
/*  632 */     paramStringBuffer.append(this.summaryObjectStartText);
/*  633 */     paramStringBuffer.append(getShortClassName(paramObject.getClass()));
/*  634 */     paramStringBuffer.append(this.summaryObjectEndText);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, long paramLong)
/*      */   {
/*  648 */     appendFieldStart(paramStringBuffer, paramString);
/*  649 */     appendDetail(paramStringBuffer, paramString, paramLong);
/*  650 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, long paramLong)
/*      */   {
/*  662 */     paramStringBuffer.append(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, int paramInt)
/*      */   {
/*  676 */     appendFieldStart(paramStringBuffer, paramString);
/*  677 */     appendDetail(paramStringBuffer, paramString, paramInt);
/*  678 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, int paramInt)
/*      */   {
/*  690 */     paramStringBuffer.append(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, short paramShort)
/*      */   {
/*  704 */     appendFieldStart(paramStringBuffer, paramString);
/*  705 */     appendDetail(paramStringBuffer, paramString, paramShort);
/*  706 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, short paramShort)
/*      */   {
/*  718 */     paramStringBuffer.append(paramShort);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, byte paramByte)
/*      */   {
/*  732 */     appendFieldStart(paramStringBuffer, paramString);
/*  733 */     appendDetail(paramStringBuffer, paramString, paramByte);
/*  734 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, byte paramByte)
/*      */   {
/*  746 */     paramStringBuffer.append(paramByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, char paramChar)
/*      */   {
/*  760 */     appendFieldStart(paramStringBuffer, paramString);
/*  761 */     appendDetail(paramStringBuffer, paramString, paramChar);
/*  762 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, char paramChar)
/*      */   {
/*  774 */     paramStringBuffer.append(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, double paramDouble)
/*      */   {
/*  788 */     appendFieldStart(paramStringBuffer, paramString);
/*  789 */     appendDetail(paramStringBuffer, paramString, paramDouble);
/*  790 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, double paramDouble)
/*      */   {
/*  802 */     paramStringBuffer.append(paramDouble);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, float paramFloat)
/*      */   {
/*  816 */     appendFieldStart(paramStringBuffer, paramString);
/*  817 */     appendDetail(paramStringBuffer, paramString, paramFloat);
/*  818 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, float paramFloat)
/*      */   {
/*  830 */     paramStringBuffer.append(paramFloat);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
/*      */   {
/*  844 */     appendFieldStart(paramStringBuffer, paramString);
/*  845 */     appendDetail(paramStringBuffer, paramString, paramBoolean);
/*  846 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, boolean paramBoolean)
/*      */   {
/*  858 */     paramStringBuffer.append(paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject, Boolean paramBoolean)
/*      */   {
/*  872 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/*  874 */     if (paramArrayOfObject == null) {
/*  875 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/*  877 */     else if (isFullDetail(paramBoolean)) {
/*  878 */       appendDetail(paramStringBuffer, paramString, paramArrayOfObject);
/*      */     }
/*      */     else {
/*  881 */       appendSummary(paramStringBuffer, paramString, paramArrayOfObject);
/*      */     }
/*      */     
/*  884 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject)
/*      */   {
/*  899 */     paramStringBuffer.append(this.arrayStart);
/*  900 */     for (int i = 0; i < paramArrayOfObject.length; i++) {
/*  901 */       Object localObject = paramArrayOfObject[i];
/*  902 */       if (i > 0) {
/*  903 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/*  905 */       if (localObject == null) {
/*  906 */         appendNullText(paramStringBuffer, paramString);
/*      */       }
/*      */       else {
/*  909 */         appendInternal(paramStringBuffer, paramString, localObject, this.arrayContentDetail);
/*      */       }
/*      */     }
/*  912 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void reflectionAppendArrayDetail(StringBuffer paramStringBuffer, String paramString, Object paramObject)
/*      */   {
/*  925 */     paramStringBuffer.append(this.arrayStart);
/*  926 */     int i = Array.getLength(paramObject);
/*  927 */     for (int j = 0; j < i; j++) {
/*  928 */       Object localObject = Array.get(paramObject, j);
/*  929 */       if (j > 0) {
/*  930 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/*  932 */       if (localObject == null) {
/*  933 */         appendNullText(paramStringBuffer, paramString);
/*      */       }
/*      */       else {
/*  936 */         appendInternal(paramStringBuffer, paramString, localObject, this.arrayContentDetail);
/*      */       }
/*      */     }
/*  939 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, Object[] paramArrayOfObject)
/*      */   {
/*  952 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfObject.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOfLong, Boolean paramBoolean)
/*      */   {
/*  968 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/*  970 */     if (paramArrayOfLong == null) {
/*  971 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/*  973 */     else if (isFullDetail(paramBoolean)) {
/*  974 */       appendDetail(paramStringBuffer, paramString, paramArrayOfLong);
/*      */     }
/*      */     else {
/*  977 */       appendSummary(paramStringBuffer, paramString, paramArrayOfLong);
/*      */     }
/*      */     
/*  980 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOfLong)
/*      */   {
/*  993 */     paramStringBuffer.append(this.arrayStart);
/*  994 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/*  995 */       if (i > 0) {
/*  996 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/*  998 */       appendDetail(paramStringBuffer, paramString, paramArrayOfLong[i]);
/*      */     }
/* 1000 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, long[] paramArrayOfLong)
/*      */   {
/* 1013 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfLong.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfInt, Boolean paramBoolean)
/*      */   {
/* 1029 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1031 */     if (paramArrayOfInt == null) {
/* 1032 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1034 */     else if (isFullDetail(paramBoolean)) {
/* 1035 */       appendDetail(paramStringBuffer, paramString, paramArrayOfInt);
/*      */     }
/*      */     else {
/* 1038 */       appendSummary(paramStringBuffer, paramString, paramArrayOfInt);
/*      */     }
/*      */     
/* 1041 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfInt)
/*      */   {
/* 1054 */     paramStringBuffer.append(this.arrayStart);
/* 1055 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 1056 */       if (i > 0) {
/* 1057 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1059 */       appendDetail(paramStringBuffer, paramString, paramArrayOfInt[i]);
/*      */     }
/* 1061 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, int[] paramArrayOfInt)
/*      */   {
/* 1074 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfInt.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfShort, Boolean paramBoolean)
/*      */   {
/* 1090 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1092 */     if (paramArrayOfShort == null) {
/* 1093 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1095 */     else if (isFullDetail(paramBoolean)) {
/* 1096 */       appendDetail(paramStringBuffer, paramString, paramArrayOfShort);
/*      */     }
/*      */     else {
/* 1099 */       appendSummary(paramStringBuffer, paramString, paramArrayOfShort);
/*      */     }
/*      */     
/* 1102 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfShort)
/*      */   {
/* 1115 */     paramStringBuffer.append(this.arrayStart);
/* 1116 */     for (int i = 0; i < paramArrayOfShort.length; i++) {
/* 1117 */       if (i > 0) {
/* 1118 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1120 */       appendDetail(paramStringBuffer, paramString, paramArrayOfShort[i]);
/*      */     }
/* 1122 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, short[] paramArrayOfShort)
/*      */   {
/* 1135 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfShort.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfByte, Boolean paramBoolean)
/*      */   {
/* 1151 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1153 */     if (paramArrayOfByte == null) {
/* 1154 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1156 */     else if (isFullDetail(paramBoolean)) {
/* 1157 */       appendDetail(paramStringBuffer, paramString, paramArrayOfByte);
/*      */     }
/*      */     else {
/* 1160 */       appendSummary(paramStringBuffer, paramString, paramArrayOfByte);
/*      */     }
/*      */     
/* 1163 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfByte)
/*      */   {
/* 1176 */     paramStringBuffer.append(this.arrayStart);
/* 1177 */     for (int i = 0; i < paramArrayOfByte.length; i++) {
/* 1178 */       if (i > 0) {
/* 1179 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1181 */       appendDetail(paramStringBuffer, paramString, paramArrayOfByte[i]);
/*      */     }
/* 1183 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, byte[] paramArrayOfByte)
/*      */   {
/* 1196 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfChar, Boolean paramBoolean)
/*      */   {
/* 1212 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1214 */     if (paramArrayOfChar == null) {
/* 1215 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1217 */     else if (isFullDetail(paramBoolean)) {
/* 1218 */       appendDetail(paramStringBuffer, paramString, paramArrayOfChar);
/*      */     }
/*      */     else {
/* 1221 */       appendSummary(paramStringBuffer, paramString, paramArrayOfChar);
/*      */     }
/*      */     
/* 1224 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfChar)
/*      */   {
/* 1237 */     paramStringBuffer.append(this.arrayStart);
/* 1238 */     for (int i = 0; i < paramArrayOfChar.length; i++) {
/* 1239 */       if (i > 0) {
/* 1240 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1242 */       appendDetail(paramStringBuffer, paramString, paramArrayOfChar[i]);
/*      */     }
/* 1244 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, char[] paramArrayOfChar)
/*      */   {
/* 1257 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfChar.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfDouble, Boolean paramBoolean)
/*      */   {
/* 1273 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1275 */     if (paramArrayOfDouble == null) {
/* 1276 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1278 */     else if (isFullDetail(paramBoolean)) {
/* 1279 */       appendDetail(paramStringBuffer, paramString, paramArrayOfDouble);
/*      */     }
/*      */     else {
/* 1282 */       appendSummary(paramStringBuffer, paramString, paramArrayOfDouble);
/*      */     }
/*      */     
/* 1285 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfDouble)
/*      */   {
/* 1298 */     paramStringBuffer.append(this.arrayStart);
/* 1299 */     for (int i = 0; i < paramArrayOfDouble.length; i++) {
/* 1300 */       if (i > 0) {
/* 1301 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1303 */       appendDetail(paramStringBuffer, paramString, paramArrayOfDouble[i]);
/*      */     }
/* 1305 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, double[] paramArrayOfDouble)
/*      */   {
/* 1318 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfDouble.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOfFloat, Boolean paramBoolean)
/*      */   {
/* 1334 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1336 */     if (paramArrayOfFloat == null) {
/* 1337 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1339 */     else if (isFullDetail(paramBoolean)) {
/* 1340 */       appendDetail(paramStringBuffer, paramString, paramArrayOfFloat);
/*      */     }
/*      */     else {
/* 1343 */       appendSummary(paramStringBuffer, paramString, paramArrayOfFloat);
/*      */     }
/*      */     
/* 1346 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOfFloat)
/*      */   {
/* 1359 */     paramStringBuffer.append(this.arrayStart);
/* 1360 */     for (int i = 0; i < paramArrayOfFloat.length; i++) {
/* 1361 */       if (i > 0) {
/* 1362 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1364 */       appendDetail(paramStringBuffer, paramString, paramArrayOfFloat[i]);
/*      */     }
/* 1366 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, float[] paramArrayOfFloat)
/*      */   {
/* 1379 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfFloat.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void append(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfBoolean, Boolean paramBoolean)
/*      */   {
/* 1395 */     appendFieldStart(paramStringBuffer, paramString);
/*      */     
/* 1397 */     if (paramArrayOfBoolean == null) {
/* 1398 */       appendNullText(paramStringBuffer, paramString);
/*      */     }
/* 1400 */     else if (isFullDetail(paramBoolean)) {
/* 1401 */       appendDetail(paramStringBuffer, paramString, paramArrayOfBoolean);
/*      */     }
/*      */     else {
/* 1404 */       appendSummary(paramStringBuffer, paramString, paramArrayOfBoolean);
/*      */     }
/*      */     
/* 1407 */     appendFieldEnd(paramStringBuffer, paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfBoolean)
/*      */   {
/* 1420 */     paramStringBuffer.append(this.arrayStart);
/* 1421 */     for (int i = 0; i < paramArrayOfBoolean.length; i++) {
/* 1422 */       if (i > 0) {
/* 1423 */         paramStringBuffer.append(this.arraySeparator);
/*      */       }
/* 1425 */       appendDetail(paramStringBuffer, paramString, paramArrayOfBoolean[i]);
/*      */     }
/* 1427 */     paramStringBuffer.append(this.arrayEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummary(StringBuffer paramStringBuffer, String paramString, boolean[] paramArrayOfBoolean)
/*      */   {
/* 1440 */     appendSummarySize(paramStringBuffer, paramString, paramArrayOfBoolean.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendClassName(StringBuffer paramStringBuffer, Object paramObject)
/*      */   {
/* 1452 */     if ((this.useClassName) && (paramObject != null)) {
/* 1453 */       register(paramObject);
/* 1454 */       if (this.useShortClassName) {
/* 1455 */         paramStringBuffer.append(getShortClassName(paramObject.getClass()));
/*      */       } else {
/* 1457 */         paramStringBuffer.append(paramObject.getClass().getName());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendIdentityHashCode(StringBuffer paramStringBuffer, Object paramObject)
/*      */   {
/* 1469 */     if ((isUseIdentityHashCode()) && (paramObject != null)) {
/* 1470 */       register(paramObject);
/* 1471 */       paramStringBuffer.append('@');
/* 1472 */       paramStringBuffer.append(Integer.toHexString(System.identityHashCode(paramObject)));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendContentStart(StringBuffer paramStringBuffer)
/*      */   {
/* 1482 */     paramStringBuffer.append(this.contentStart);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendContentEnd(StringBuffer paramStringBuffer)
/*      */   {
/* 1491 */     paramStringBuffer.append(this.contentEnd);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendNullText(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/* 1503 */     paramStringBuffer.append(this.nullText);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendFieldSeparator(StringBuffer paramStringBuffer)
/*      */   {
/* 1512 */     paramStringBuffer.append(this.fieldSeparator);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendFieldStart(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/* 1522 */     if ((this.useFieldNames) && (paramString != null)) {
/* 1523 */       paramStringBuffer.append(paramString);
/* 1524 */       paramStringBuffer.append(this.fieldNameValueSeparator);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendFieldEnd(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/* 1535 */     appendFieldSeparator(paramStringBuffer);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void appendSummarySize(StringBuffer paramStringBuffer, String paramString, int paramInt)
/*      */   {
/* 1554 */     paramStringBuffer.append(this.sizeStartText);
/* 1555 */     paramStringBuffer.append(paramInt);
/* 1556 */     paramStringBuffer.append(this.sizeEndText);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isFullDetail(Boolean paramBoolean)
/*      */   {
/* 1574 */     if (paramBoolean == null) {
/* 1575 */       return this.defaultFullDetail;
/*      */     }
/* 1577 */     return paramBoolean.booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getShortClassName(Class<?> paramClass)
/*      */   {
/* 1590 */     return ClassUtils.getShortClassName(paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isUseClassName()
/*      */   {
/* 1604 */     return this.useClassName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setUseClassName(boolean paramBoolean)
/*      */   {
/* 1613 */     this.useClassName = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isUseShortClassName()
/*      */   {
/* 1625 */     return this.useShortClassName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setUseShortClassName(boolean paramBoolean)
/*      */   {
/* 1635 */     this.useShortClassName = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isUseIdentityHashCode()
/*      */   {
/* 1646 */     return this.useIdentityHashCode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setUseIdentityHashCode(boolean paramBoolean)
/*      */   {
/* 1655 */     this.useIdentityHashCode = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isUseFieldNames()
/*      */   {
/* 1666 */     return this.useFieldNames;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setUseFieldNames(boolean paramBoolean)
/*      */   {
/* 1675 */     this.useFieldNames = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isDefaultFullDetail()
/*      */   {
/* 1687 */     return this.defaultFullDetail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setDefaultFullDetail(boolean paramBoolean)
/*      */   {
/* 1697 */     this.defaultFullDetail = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isArrayContentDetail()
/*      */   {
/* 1708 */     return this.arrayContentDetail;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setArrayContentDetail(boolean paramBoolean)
/*      */   {
/* 1717 */     this.arrayContentDetail = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getArrayStart()
/*      */   {
/* 1728 */     return this.arrayStart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setArrayStart(String paramString)
/*      */   {
/* 1740 */     if (paramString == null) {
/* 1741 */       paramString = "";
/*      */     }
/* 1743 */     this.arrayStart = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getArrayEnd()
/*      */   {
/* 1754 */     return this.arrayEnd;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setArrayEnd(String paramString)
/*      */   {
/* 1766 */     if (paramString == null) {
/* 1767 */       paramString = "";
/*      */     }
/* 1769 */     this.arrayEnd = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getArraySeparator()
/*      */   {
/* 1780 */     return this.arraySeparator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setArraySeparator(String paramString)
/*      */   {
/* 1792 */     if (paramString == null) {
/* 1793 */       paramString = "";
/*      */     }
/* 1795 */     this.arraySeparator = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getContentStart()
/*      */   {
/* 1806 */     return this.contentStart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setContentStart(String paramString)
/*      */   {
/* 1818 */     if (paramString == null) {
/* 1819 */       paramString = "";
/*      */     }
/* 1821 */     this.contentStart = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getContentEnd()
/*      */   {
/* 1832 */     return this.contentEnd;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setContentEnd(String paramString)
/*      */   {
/* 1844 */     if (paramString == null) {
/* 1845 */       paramString = "";
/*      */     }
/* 1847 */     this.contentEnd = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getFieldNameValueSeparator()
/*      */   {
/* 1858 */     return this.fieldNameValueSeparator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFieldNameValueSeparator(String paramString)
/*      */   {
/* 1870 */     if (paramString == null) {
/* 1871 */       paramString = "";
/*      */     }
/* 1873 */     this.fieldNameValueSeparator = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getFieldSeparator()
/*      */   {
/* 1884 */     return this.fieldSeparator;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFieldSeparator(String paramString)
/*      */   {
/* 1896 */     if (paramString == null) {
/* 1897 */       paramString = "";
/*      */     }
/* 1899 */     this.fieldSeparator = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isFieldSeparatorAtStart()
/*      */   {
/* 1912 */     return this.fieldSeparatorAtStart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFieldSeparatorAtStart(boolean paramBoolean)
/*      */   {
/* 1923 */     this.fieldSeparatorAtStart = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isFieldSeparatorAtEnd()
/*      */   {
/* 1936 */     return this.fieldSeparatorAtEnd;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setFieldSeparatorAtEnd(boolean paramBoolean)
/*      */   {
/* 1947 */     this.fieldSeparatorAtEnd = paramBoolean;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getNullText()
/*      */   {
/* 1958 */     return this.nullText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setNullText(String paramString)
/*      */   {
/* 1970 */     if (paramString == null) {
/* 1971 */       paramString = "";
/*      */     }
/* 1973 */     this.nullText = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getSizeStartText()
/*      */   {
/* 1987 */     return this.sizeStartText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setSizeStartText(String paramString)
/*      */   {
/* 2002 */     if (paramString == null) {
/* 2003 */       paramString = "";
/*      */     }
/* 2005 */     this.sizeStartText = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getSizeEndText()
/*      */   {
/* 2019 */     return this.sizeEndText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setSizeEndText(String paramString)
/*      */   {
/* 2034 */     if (paramString == null) {
/* 2035 */       paramString = "";
/*      */     }
/* 2037 */     this.sizeEndText = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getSummaryObjectStartText()
/*      */   {
/* 2051 */     return this.summaryObjectStartText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setSummaryObjectStartText(String paramString)
/*      */   {
/* 2066 */     if (paramString == null) {
/* 2067 */       paramString = "";
/*      */     }
/* 2069 */     this.summaryObjectStartText = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected String getSummaryObjectEndText()
/*      */   {
/* 2083 */     return this.summaryObjectEndText;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void setSummaryObjectEndText(String paramString)
/*      */   {
/* 2098 */     if (paramString == null) {
/* 2099 */       paramString = "";
/*      */     }
/* 2101 */     this.summaryObjectEndText = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final class DefaultToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object readResolve()
/*      */     {
/* 2136 */       return ToStringStyle.DEFAULT_STYLE;
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
/*      */   private static final class NoFieldNameToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     NoFieldNameToStringStyle()
/*      */     {
/* 2161 */       setUseFieldNames(false);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object readResolve()
/*      */     {
/* 2170 */       return ToStringStyle.NO_FIELD_NAMES_STYLE;
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
/*      */   private static final class ShortPrefixToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ShortPrefixToStringStyle()
/*      */     {
/* 2195 */       setUseShortClassName(true);
/* 2196 */       setUseIdentityHashCode(false);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object readResolve()
/*      */     {
/* 2204 */       return ToStringStyle.SHORT_PREFIX_STYLE;
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
/*      */   private static final class SimpleToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     SimpleToStringStyle()
/*      */     {
/* 2229 */       setUseClassName(false);
/* 2230 */       setUseIdentityHashCode(false);
/* 2231 */       setUseFieldNames(false);
/* 2232 */       setContentStart("");
/* 2233 */       setContentEnd("");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object readResolve()
/*      */     {
/* 2241 */       return ToStringStyle.SIMPLE_STYLE;
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
/*      */   private static final class MultiLineToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     MultiLineToStringStyle()
/*      */     {
/* 2265 */       setContentStart("[");
/* 2266 */       setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
/* 2267 */       setFieldSeparatorAtStart(true);
/* 2268 */       setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Object readResolve()
/*      */     {
/* 2277 */       return ToStringStyle.MULTI_LINE_STYLE;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\ToStringStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */