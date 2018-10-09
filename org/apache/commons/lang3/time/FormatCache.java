/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.Format;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class FormatCache<F extends Format>
/*     */ {
/*     */   static final int NONE = -1;
/*     */   private final ConcurrentMap<MultipartKey, F> cInstanceCache;
/*     */   
/*     */   FormatCache()
/*     */   {
/*  41 */     this.cInstanceCache = new ConcurrentHashMap(7);
/*     */   }
/*     */   
/*  44 */   private static final ConcurrentMap<MultipartKey, String> cDateTimeInstanceCache = new ConcurrentHashMap(7);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public F getInstance()
/*     */   {
/*  54 */     return getDateTimeInstance(3, 3, TimeZone.getDefault(), Locale.getDefault());
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
/*     */   public F getInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/*  70 */     if (paramString == null) {
/*  71 */       throw new NullPointerException("pattern must not be null");
/*     */     }
/*  73 */     if (paramTimeZone == null) {
/*  74 */       paramTimeZone = TimeZone.getDefault();
/*     */     }
/*  76 */     if (paramLocale == null) {
/*  77 */       paramLocale = Locale.getDefault();
/*     */     }
/*  79 */     MultipartKey localMultipartKey = new MultipartKey(new Object[] { paramString, paramTimeZone, paramLocale });
/*  80 */     Object localObject = (Format)this.cInstanceCache.get(localMultipartKey);
/*  81 */     if (localObject == null) {
/*  82 */       localObject = createInstance(paramString, paramTimeZone, paramLocale);
/*  83 */       Format localFormat = (Format)this.cInstanceCache.putIfAbsent(localMultipartKey, localObject);
/*  84 */       if (localFormat != null)
/*     */       {
/*     */ 
/*  87 */         localObject = localFormat;
/*     */       }
/*     */     }
/*  90 */     return (F)localObject;
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
/*     */   protected abstract F createInstance(String paramString, TimeZone paramTimeZone, Locale paramLocale);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private F getDateTimeInstance(Integer paramInteger1, Integer paramInteger2, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 121 */     if (paramLocale == null) {
/* 122 */       paramLocale = Locale.getDefault();
/*     */     }
/* 124 */     String str = getPatternForStyle(paramInteger1, paramInteger2, paramLocale);
/* 125 */     return getInstance(str, paramTimeZone, paramLocale);
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
/*     */   F getDateTimeInstance(int paramInt1, int paramInt2, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 143 */     return getDateTimeInstance(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramTimeZone, paramLocale);
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
/*     */   F getDateInstance(int paramInt, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 160 */     return getDateTimeInstance(Integer.valueOf(paramInt), null, paramTimeZone, paramLocale);
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
/*     */   F getTimeInstance(int paramInt, TimeZone paramTimeZone, Locale paramLocale)
/*     */   {
/* 177 */     return getDateTimeInstance(null, Integer.valueOf(paramInt), paramTimeZone, paramLocale);
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
/*     */   static String getPatternForStyle(Integer paramInteger1, Integer paramInteger2, Locale paramLocale)
/*     */   {
/* 191 */     MultipartKey localMultipartKey = new MultipartKey(new Object[] { paramInteger1, paramInteger2, paramLocale });
/*     */     
/* 193 */     Object localObject = (String)cDateTimeInstanceCache.get(localMultipartKey);
/* 194 */     if (localObject == null) {
/*     */       try {
/*     */         DateFormat localDateFormat;
/* 197 */         if (paramInteger1 == null) {
/* 198 */           localDateFormat = DateFormat.getTimeInstance(paramInteger2.intValue(), paramLocale);
/*     */         }
/* 200 */         else if (paramInteger2 == null) {
/* 201 */           localDateFormat = DateFormat.getDateInstance(paramInteger1.intValue(), paramLocale);
/*     */         }
/*     */         else {
/* 204 */           localDateFormat = DateFormat.getDateTimeInstance(paramInteger1.intValue(), paramInteger2.intValue(), paramLocale);
/*     */         }
/* 206 */         localObject = ((SimpleDateFormat)localDateFormat).toPattern();
/* 207 */         String str = (String)cDateTimeInstanceCache.putIfAbsent(localMultipartKey, localObject);
/* 208 */         if (str != null)
/*     */         {
/*     */ 
/*     */ 
/* 212 */           localObject = str;
/*     */         }
/*     */       } catch (ClassCastException localClassCastException) {
/* 215 */         throw new IllegalArgumentException("No date time pattern for locale: " + paramLocale);
/*     */       }
/*     */     }
/* 218 */     return (String)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class MultipartKey
/*     */   {
/*     */     private final Object[] keys;
/*     */     
/*     */ 
/*     */     private int hashCode;
/*     */     
/*     */ 
/*     */ 
/*     */     public MultipartKey(Object... paramVarArgs)
/*     */     {
/* 234 */       this.keys = paramVarArgs;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 245 */       return Arrays.equals(this.keys, ((MultipartKey)paramObject).keys);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 253 */       if (this.hashCode == 0) {
/* 254 */         int i = 0;
/* 255 */         for (Object localObject : this.keys) {
/* 256 */           if (localObject != null) {
/* 257 */             i = i * 7 + localObject.hashCode();
/*     */           }
/*     */         }
/* 260 */         this.hashCode = i;
/*     */       }
/* 262 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\time\FormatCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */