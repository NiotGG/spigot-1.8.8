/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StrMatcher
/*     */ {
/*  38 */   private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
/*     */   
/*     */ 
/*     */ 
/*  42 */   private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
/*     */   
/*     */ 
/*     */ 
/*  46 */   private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  51 */   private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
/*     */   
/*     */ 
/*     */ 
/*  55 */   private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
/*     */   
/*     */ 
/*     */ 
/*  59 */   private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
/*     */   
/*     */ 
/*     */ 
/*  63 */   private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
/*     */   
/*     */ 
/*     */ 
/*  67 */   private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
/*     */   
/*     */ 
/*     */ 
/*  71 */   private static final StrMatcher NONE_MATCHER = new NoMatcher();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher commaMatcher()
/*     */   {
/*  81 */     return COMMA_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher tabMatcher()
/*     */   {
/*  90 */     return TAB_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher spaceMatcher()
/*     */   {
/*  99 */     return SPACE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher splitMatcher()
/*     */   {
/* 109 */     return SPLIT_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher trimMatcher()
/*     */   {
/* 118 */     return TRIM_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher singleQuoteMatcher()
/*     */   {
/* 127 */     return SINGLE_QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher doubleQuoteMatcher()
/*     */   {
/* 136 */     return DOUBLE_QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher quoteMatcher()
/*     */   {
/* 145 */     return QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher noneMatcher()
/*     */   {
/* 154 */     return NONE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charMatcher(char paramChar)
/*     */   {
/* 164 */     return new CharMatcher(paramChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charSetMatcher(char... paramVarArgs)
/*     */   {
/* 174 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 175 */       return NONE_MATCHER;
/*     */     }
/* 177 */     if (paramVarArgs.length == 1) {
/* 178 */       return new CharMatcher(paramVarArgs[0]);
/*     */     }
/* 180 */     return new CharSetMatcher(paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charSetMatcher(String paramString)
/*     */   {
/* 190 */     if (StringUtils.isEmpty(paramString)) {
/* 191 */       return NONE_MATCHER;
/*     */     }
/* 193 */     if (paramString.length() == 1) {
/* 194 */       return new CharMatcher(paramString.charAt(0));
/*     */     }
/* 196 */     return new CharSetMatcher(paramString.toCharArray());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher stringMatcher(String paramString)
/*     */   {
/* 206 */     if (StringUtils.isEmpty(paramString)) {
/* 207 */       return NONE_MATCHER;
/*     */     }
/* 209 */     return new StringMatcher(paramString);
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
/*     */   public abstract int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int isMatch(char[] paramArrayOfChar, int paramInt)
/*     */   {
/* 268 */     return isMatch(paramArrayOfChar, paramInt, 0, paramArrayOfChar.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class CharSetMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char[] chars;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     CharSetMatcher(char[] paramArrayOfChar)
/*     */     {
/* 286 */       this.chars = ((char[])paramArrayOfChar.clone());
/* 287 */       Arrays.sort(this.chars);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 301 */       return Arrays.binarySearch(this.chars, paramArrayOfChar[paramInt1]) >= 0 ? 1 : 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class CharMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char ch;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     CharMatcher(char paramChar)
/*     */     {
/* 320 */       this.ch = paramChar;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 334 */       return this.ch == paramArrayOfChar[paramInt1] ? 1 : 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final class StringMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     private final char[] chars;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     StringMatcher(String paramString)
/*     */     {
/* 353 */       this.chars = paramString.toCharArray();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 367 */       int i = this.chars.length;
/* 368 */       if (paramInt1 + i > paramInt3) {
/* 369 */         return 0;
/*     */       }
/* 371 */       for (int j = 0; j < this.chars.length; paramInt1++) {
/* 372 */         if (this.chars[j] != paramArrayOfChar[paramInt1]) {
/* 373 */           return 0;
/*     */         }
/* 371 */         j++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 376 */       return i;
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
/*     */   static final class NoMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     public int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 404 */       return 0;
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
/*     */   static final class TrimMatcher
/*     */     extends StrMatcher
/*     */   {
/*     */     public int isMatch(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 432 */       return paramArrayOfChar[paramInt1] <= ' ' ? 1 : 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\StrMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */