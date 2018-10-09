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
/*     */ public class CharSequenceUtils
/*     */ {
/*     */   public static CharSequence subSequence(CharSequence paramCharSequence, int paramInt)
/*     */   {
/*  56 */     return paramCharSequence == null ? null : paramCharSequence.subSequence(paramInt, paramCharSequence.length());
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
/*     */   static int indexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/*  70 */     if ((paramCharSequence instanceof String)) {
/*  71 */       return ((String)paramCharSequence).indexOf(paramInt1, paramInt2);
/*     */     }
/*  73 */     int i = paramCharSequence.length();
/*  74 */     if (paramInt2 < 0) {
/*  75 */       paramInt2 = 0;
/*     */     }
/*  77 */     for (int j = paramInt2; j < i; j++) {
/*  78 */       if (paramCharSequence.charAt(j) == paramInt1) {
/*  79 */         return j;
/*     */       }
/*     */     }
/*  82 */     return -1;
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
/*     */   static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*     */   {
/*  95 */     return paramCharSequence1.toString().indexOf(paramCharSequence2.toString(), paramInt);
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
/*     */   static int lastIndexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/* 117 */     if ((paramCharSequence instanceof String)) {
/* 118 */       return ((String)paramCharSequence).lastIndexOf(paramInt1, paramInt2);
/*     */     }
/* 120 */     int i = paramCharSequence.length();
/* 121 */     if (paramInt2 < 0) {
/* 122 */       return -1;
/*     */     }
/* 124 */     if (paramInt2 >= i) {
/* 125 */       paramInt2 = i - 1;
/*     */     }
/* 127 */     for (int j = paramInt2; j >= 0; j--) {
/* 128 */       if (paramCharSequence.charAt(j) == paramInt1) {
/* 129 */         return j;
/*     */       }
/*     */     }
/* 132 */     return -1;
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
/*     */   static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt)
/*     */   {
/* 145 */     return paramCharSequence1.toString().lastIndexOf(paramCharSequence2.toString(), paramInt);
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
/*     */   static char[] toCharArray(CharSequence paramCharSequence)
/*     */   {
/* 164 */     if ((paramCharSequence instanceof String)) {
/* 165 */       return ((String)paramCharSequence).toCharArray();
/*     */     }
/* 167 */     int i = paramCharSequence.length();
/* 168 */     char[] arrayOfChar = new char[paramCharSequence.length()];
/* 169 */     for (int j = 0; j < i; j++) {
/* 170 */       arrayOfChar[j] = paramCharSequence.charAt(j);
/*     */     }
/* 172 */     return arrayOfChar;
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
/*     */   static boolean regionMatches(CharSequence paramCharSequence1, boolean paramBoolean, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3)
/*     */   {
/* 189 */     if (((paramCharSequence1 instanceof String)) && ((paramCharSequence2 instanceof String))) {
/* 190 */       return ((String)paramCharSequence1).regionMatches(paramBoolean, paramInt1, (String)paramCharSequence2, paramInt2, paramInt3);
/*     */     }
/* 192 */     int i = paramInt1;
/* 193 */     int j = paramInt2;
/* 194 */     int k = paramInt3;
/*     */     
/* 196 */     while (k-- > 0) {
/* 197 */       char c1 = paramCharSequence1.charAt(i++);
/* 198 */       char c2 = paramCharSequence2.charAt(j++);
/*     */       
/* 200 */       if (c1 != c2)
/*     */       {
/*     */ 
/*     */ 
/* 204 */         if (!paramBoolean) {
/* 205 */           return false;
/*     */         }
/*     */         
/*     */ 
/* 209 */         if ((Character.toUpperCase(c1) != Character.toUpperCase(c2)) && (Character.toLowerCase(c1) != Character.toLowerCase(c2)))
/*     */         {
/* 211 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 215 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\CharSequenceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */