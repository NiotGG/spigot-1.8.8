/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumUtils
/*     */ {
/*     */   private static final String NULL_ELEMENTS_NOT_PERMITTED = "null elements not permitted";
/*     */   private static final String CANNOT_STORE_S_S_VALUES_IN_S_BITS = "Cannot store %s %s values in %s bits";
/*     */   private static final String S_DOES_NOT_SEEM_TO_BE_AN_ENUM_TYPE = "%s does not seem to be an Enum type";
/*     */   private static final String ENUM_CLASS_MUST_BE_DEFINED = "EnumClass must be defined.";
/*     */   
/*     */   public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> paramClass)
/*     */   {
/*  59 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/*  60 */     for (Enum localEnum : (Enum[])paramClass.getEnumConstants()) {
/*  61 */       localLinkedHashMap.put(localEnum.name(), localEnum);
/*     */     }
/*  63 */     return localLinkedHashMap;
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
/*     */   public static <E extends Enum<E>> List<E> getEnumList(Class<E> paramClass)
/*     */   {
/*  76 */     return new ArrayList(Arrays.asList(paramClass.getEnumConstants()));
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
/*     */   public static <E extends Enum<E>> boolean isValidEnum(Class<E> paramClass, String paramString)
/*     */   {
/*  91 */     if (paramString == null) {
/*  92 */       return false;
/*     */     }
/*     */     try {
/*  95 */       Enum.valueOf(paramClass, paramString);
/*  96 */       return true;
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/*  98 */     return false;
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
/*     */   public static <E extends Enum<E>> E getEnum(Class<E> paramClass, String paramString)
/*     */   {
/* 114 */     if (paramString == null) {
/* 115 */       return null;
/*     */     }
/*     */     try {
/* 118 */       return Enum.valueOf(paramClass, paramString);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 120 */     return null;
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
/*     */   public static <E extends Enum<E>> long generateBitVector(Class<E> paramClass, Iterable<? extends E> paramIterable)
/*     */   {
/* 143 */     checkBitVectorable(paramClass);
/* 144 */     Validate.notNull(paramIterable);
/* 145 */     long l = 0L;
/* 146 */     for (Enum localEnum : paramIterable) {
/* 147 */       Validate.isTrue(localEnum != null, "null elements not permitted", new Object[0]);
/* 148 */       l |= 1 << localEnum.ordinal();
/*     */     }
/* 150 */     return l;
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
/*     */   public static <E extends Enum<E>> long[] generateBitVectors(Class<E> paramClass, Iterable<? extends E> paramIterable)
/*     */   {
/* 170 */     asEnum(paramClass);
/* 171 */     Validate.notNull(paramIterable);
/* 172 */     EnumSet localEnumSet = EnumSet.noneOf(paramClass);
/* 173 */     for (Object localObject1 = paramIterable.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Enum)((Iterator)localObject1).next();
/* 174 */       Validate.isTrue(localObject2 != null, "null elements not permitted", new Object[0]);
/* 175 */       localEnumSet.add(localObject2);
/*     */     }
/* 177 */     localObject1 = new long[(((Enum[])paramClass.getEnumConstants()).length - 1) / 64 + 1];
/* 178 */     for (Object localObject2 = localEnumSet.iterator(); ((Iterator)localObject2).hasNext();) { Enum localEnum = (Enum)((Iterator)localObject2).next();
/* 179 */       localObject1[(localEnum.ordinal() / 64)] |= 1 << localEnum.ordinal() % 64;
/*     */     }
/* 181 */     ArrayUtils.reverse((long[])localObject1);
/* 182 */     return (long[])localObject1;
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
/*     */   public static <E extends Enum<E>> long generateBitVector(Class<E> paramClass, E... paramVarArgs)
/*     */   {
/* 203 */     Validate.noNullElements(paramVarArgs);
/* 204 */     return generateBitVector(paramClass, Arrays.asList(paramVarArgs));
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
/*     */   public static <E extends Enum<E>> long[] generateBitVectors(Class<E> paramClass, E... paramVarArgs)
/*     */   {
/* 224 */     asEnum(paramClass);
/* 225 */     Validate.noNullElements(paramVarArgs);
/* 226 */     EnumSet localEnumSet = EnumSet.noneOf(paramClass);
/* 227 */     Collections.addAll(localEnumSet, paramVarArgs);
/* 228 */     long[] arrayOfLong = new long[(((Enum[])paramClass.getEnumConstants()).length - 1) / 64 + 1];
/* 229 */     for (Enum localEnum : localEnumSet) {
/* 230 */       arrayOfLong[(localEnum.ordinal() / 64)] |= 1 << localEnum.ordinal() % 64;
/*     */     }
/* 232 */     ArrayUtils.reverse(arrayOfLong);
/* 233 */     return arrayOfLong;
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
/*     */   public static <E extends Enum<E>> EnumSet<E> processBitVector(Class<E> paramClass, long paramLong)
/*     */   {
/* 250 */     checkBitVectorable(paramClass).getEnumConstants();
/* 251 */     return processBitVectors(paramClass, new long[] { paramLong });
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
/*     */   public static <E extends Enum<E>> EnumSet<E> processBitVectors(Class<E> paramClass, long... paramVarArgs)
/*     */   {
/* 268 */     EnumSet localEnumSet = EnumSet.noneOf(asEnum(paramClass));
/* 269 */     long[] arrayOfLong = ArrayUtils.clone((long[])Validate.notNull(paramVarArgs));
/* 270 */     ArrayUtils.reverse(arrayOfLong);
/* 271 */     for (Enum localEnum : (Enum[])paramClass.getEnumConstants()) {
/* 272 */       int k = localEnum.ordinal() / 64;
/* 273 */       if ((k < arrayOfLong.length) && ((arrayOfLong[k] & 1 << localEnum.ordinal() % 64) != 0L)) {
/* 274 */         localEnumSet.add(localEnum);
/*     */       }
/*     */     }
/* 277 */     return localEnumSet;
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
/*     */   private static <E extends Enum<E>> Class<E> checkBitVectorable(Class<E> paramClass)
/*     */   {
/* 290 */     Enum[] arrayOfEnum = (Enum[])asEnum(paramClass).getEnumConstants();
/* 291 */     Validate.isTrue(arrayOfEnum.length <= 64, "Cannot store %s %s values in %s bits", new Object[] { Integer.valueOf(arrayOfEnum.length), paramClass.getSimpleName(), Integer.valueOf(64) });
/*     */     
/*     */ 
/* 294 */     return paramClass;
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
/*     */   private static <E extends Enum<E>> Class<E> asEnum(Class<E> paramClass)
/*     */   {
/* 307 */     Validate.notNull(paramClass, "EnumClass must be defined.", new Object[0]);
/* 308 */     Validate.isTrue(paramClass.isEnum(), "%s does not seem to be an Enum type", new Object[] { paramClass });
/* 309 */     return paramClass;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\EnumUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */