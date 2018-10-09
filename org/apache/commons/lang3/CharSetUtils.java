/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharSetUtils
/*     */ {
/*     */   public static String squeeze(String paramString, String... paramVarArgs)
/*     */   {
/*  65 */     if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
/*  66 */       return paramString;
/*     */     }
/*  68 */     CharSet localCharSet = CharSet.getInstance(paramVarArgs);
/*  69 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/*  70 */     char[] arrayOfChar = paramString.toCharArray();
/*  71 */     int i = arrayOfChar.length;
/*  72 */     int j = 32;
/*  73 */     char c = ' ';
/*  74 */     for (int k = 0; k < i; k++) {
/*  75 */       c = arrayOfChar[k];
/*     */       
/*  77 */       if ((c != j) || (k == 0) || (!localCharSet.contains(c)))
/*     */       {
/*     */ 
/*  80 */         localStringBuilder.append(c);
/*  81 */         j = c;
/*     */       } }
/*  83 */     return localStringBuilder.toString();
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
/*     */   public static boolean containsAny(String paramString, String... paramVarArgs)
/*     */   {
/* 108 */     if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
/* 109 */       return false;
/*     */     }
/* 111 */     CharSet localCharSet = CharSet.getInstance(paramVarArgs);
/* 112 */     for (char c : paramString.toCharArray()) {
/* 113 */       if (localCharSet.contains(c)) {
/* 114 */         return true;
/*     */       }
/*     */     }
/* 117 */     return false;
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
/*     */   public static int count(String paramString, String... paramVarArgs)
/*     */   {
/* 141 */     if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
/* 142 */       return 0;
/*     */     }
/* 144 */     CharSet localCharSet = CharSet.getInstance(paramVarArgs);
/* 145 */     int i = 0;
/* 146 */     for (char c : paramString.toCharArray()) {
/* 147 */       if (localCharSet.contains(c)) {
/* 148 */         i++;
/*     */       }
/*     */     }
/* 151 */     return i;
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
/*     */   public static String keep(String paramString, String... paramVarArgs)
/*     */   {
/* 176 */     if (paramString == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     if ((paramString.isEmpty()) || (deepEmpty(paramVarArgs))) {
/* 180 */       return "";
/*     */     }
/* 182 */     return modify(paramString, paramVarArgs, true);
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
/*     */   public static String delete(String paramString, String... paramVarArgs)
/*     */   {
/* 206 */     if ((StringUtils.isEmpty(paramString)) || (deepEmpty(paramVarArgs))) {
/* 207 */       return paramString;
/*     */     }
/* 209 */     return modify(paramString, paramVarArgs, false);
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
/*     */   private static String modify(String paramString, String[] paramArrayOfString, boolean paramBoolean)
/*     */   {
/* 222 */     CharSet localCharSet = CharSet.getInstance(paramArrayOfString);
/* 223 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length());
/* 224 */     char[] arrayOfChar = paramString.toCharArray();
/* 225 */     int i = arrayOfChar.length;
/* 226 */     for (int j = 0; j < i; j++) {
/* 227 */       if (localCharSet.contains(arrayOfChar[j]) == paramBoolean) {
/* 228 */         localStringBuilder.append(arrayOfChar[j]);
/*     */       }
/*     */     }
/* 231 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean deepEmpty(String[] paramArrayOfString)
/*     */   {
/* 242 */     if (paramArrayOfString != null) {
/* 243 */       for (String str : paramArrayOfString) {
/* 244 */         if (StringUtils.isNotEmpty(str)) {
/* 245 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 249 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\CharSetUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */