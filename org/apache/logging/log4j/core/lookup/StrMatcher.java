/*     */ package org.apache.logging.log4j.core.lookup;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  35 */   private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
/*     */   
/*     */ 
/*     */ 
/*  39 */   private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
/*     */   
/*     */ 
/*     */ 
/*  43 */   private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  48 */   private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
/*     */   
/*     */ 
/*     */ 
/*  52 */   private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
/*     */   
/*     */ 
/*     */ 
/*  56 */   private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
/*     */   
/*     */ 
/*     */ 
/*  60 */   private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher('"');
/*     */   
/*     */ 
/*     */ 
/*  64 */   private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
/*     */   
/*     */ 
/*     */ 
/*  68 */   private static final StrMatcher NONE_MATCHER = new NoMatcher();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher commaMatcher()
/*     */   {
/*  82 */     return COMMA_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher tabMatcher()
/*     */   {
/*  91 */     return TAB_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher spaceMatcher()
/*     */   {
/* 100 */     return SPACE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher splitMatcher()
/*     */   {
/* 110 */     return SPLIT_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher trimMatcher()
/*     */   {
/* 119 */     return TRIM_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher singleQuoteMatcher()
/*     */   {
/* 128 */     return SINGLE_QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher doubleQuoteMatcher()
/*     */   {
/* 137 */     return DOUBLE_QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher quoteMatcher()
/*     */   {
/* 146 */     return QUOTE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher noneMatcher()
/*     */   {
/* 155 */     return NONE_MATCHER;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charMatcher(char paramChar)
/*     */   {
/* 165 */     return new CharMatcher(paramChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charSetMatcher(char[] paramArrayOfChar)
/*     */   {
/* 175 */     if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
/* 176 */       return NONE_MATCHER;
/*     */     }
/* 178 */     if (paramArrayOfChar.length == 1) {
/* 179 */       return new CharMatcher(paramArrayOfChar[0]);
/*     */     }
/* 181 */     return new CharSetMatcher(paramArrayOfChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher charSetMatcher(String paramString)
/*     */   {
/* 191 */     if (Strings.isEmpty(paramString)) {
/* 192 */       return NONE_MATCHER;
/*     */     }
/* 194 */     if (paramString.length() == 1) {
/* 195 */       return new CharMatcher(paramString.charAt(0));
/*     */     }
/* 197 */     return new CharSetMatcher(paramString.toCharArray());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static StrMatcher stringMatcher(String paramString)
/*     */   {
/* 207 */     if (Strings.isEmpty(paramString)) {
/* 208 */       return NONE_MATCHER;
/*     */     }
/* 210 */     return new StringMatcher(paramString);
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
/*     */   public int isMatch(char[] paramArrayOfChar, int paramInt)
/*     */   {
/* 261 */     return isMatch(paramArrayOfChar, paramInt, 0, paramArrayOfChar.length);
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
/* 279 */       this.chars = ((char[])paramArrayOfChar.clone());
/* 280 */       Arrays.sort(this.chars);
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
/* 294 */       return Arrays.binarySearch(this.chars, paramArrayOfChar[paramInt1]) >= 0 ? 1 : 0;
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
/* 313 */       this.ch = paramChar;
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
/* 327 */       return this.ch == paramArrayOfChar[paramInt1] ? 1 : 0;
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
/* 346 */       this.chars = paramString.toCharArray();
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
/* 360 */       int i = this.chars.length;
/* 361 */       if (paramInt1 + i > paramInt3) {
/* 362 */         return 0;
/*     */       }
/* 364 */       for (int j = 0; j < this.chars.length; paramInt1++) {
/* 365 */         if (this.chars[j] != paramArrayOfChar[paramInt1]) {
/* 366 */           return 0;
/*     */         }
/* 364 */         j++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 369 */       return i;
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
/* 397 */       return 0;
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
/* 425 */       return paramArrayOfChar[paramInt1] <= ' ' ? 1 : 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\StrMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */