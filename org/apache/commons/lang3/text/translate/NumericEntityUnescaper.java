/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumericEntityUnescaper
/*     */   extends CharSequenceTranslator
/*     */ {
/*     */   private final EnumSet<OPTION> options;
/*     */   
/*     */   public static enum OPTION
/*     */   {
/*  35 */     semiColonRequired,  semiColonOptional,  errorIfNoSemiColon;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private OPTION() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NumericEntityUnescaper(OPTION... paramVarArgs)
/*     */   {
/*  57 */     if (paramVarArgs.length > 0) {
/*  58 */       this.options = EnumSet.copyOf(Arrays.asList(paramVarArgs));
/*     */     } else {
/*  60 */       this.options = EnumSet.copyOf(Arrays.asList(new OPTION[] { OPTION.semiColonRequired }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSet(OPTION paramOPTION)
/*     */   {
/*  71 */     return this.options == null ? false : this.options.contains(paramOPTION);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/*  79 */     int i = paramCharSequence.length();
/*     */     
/*  81 */     if ((paramCharSequence.charAt(paramInt) == '&') && (paramInt < i - 2) && (paramCharSequence.charAt(paramInt + 1) == '#')) {
/*  82 */       int j = paramInt + 2;
/*  83 */       int k = 0;
/*     */       
/*  85 */       int m = paramCharSequence.charAt(j);
/*  86 */       if ((m == 120) || (m == 88)) {
/*  87 */         j++;
/*  88 */         k = 1;
/*     */         
/*     */ 
/*  91 */         if (j == i) {
/*  92 */           return 0;
/*     */         }
/*     */       }
/*     */       
/*  96 */       int n = j;
/*     */       
/*  98 */       while ((n < i) && (((paramCharSequence.charAt(n) >= '0') && (paramCharSequence.charAt(n) <= '9')) || ((paramCharSequence.charAt(n) >= 'a') && (paramCharSequence.charAt(n) <= 'f')) || ((paramCharSequence.charAt(n) >= 'A') && (paramCharSequence.charAt(n) <= 'F'))))
/*     */       {
/*     */ 
/*     */ 
/* 102 */         n++;
/*     */       }
/*     */       
/* 105 */       int i1 = (n != i) && (paramCharSequence.charAt(n) == ';') ? 1 : 0;
/*     */       
/* 107 */       if (i1 == 0) {
/* 108 */         if (isSet(OPTION.semiColonRequired)) {
/* 109 */           return 0;
/*     */         }
/* 111 */         if (isSet(OPTION.errorIfNoSemiColon)) {
/* 112 */           throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
/*     */         }
/*     */       }
/*     */       int i2;
/*     */       try
/*     */       {
/* 118 */         if (k != 0) {
/* 119 */           i2 = Integer.parseInt(paramCharSequence.subSequence(j, n).toString(), 16);
/*     */         } else {
/* 121 */           i2 = Integer.parseInt(paramCharSequence.subSequence(j, n).toString(), 10);
/*     */         }
/*     */       } catch (NumberFormatException localNumberFormatException) {
/* 124 */         return 0;
/*     */       }
/*     */       
/* 127 */       if (i2 > 65535) {
/* 128 */         char[] arrayOfChar = Character.toChars(i2);
/* 129 */         paramWriter.write(arrayOfChar[0]);
/* 130 */         paramWriter.write(arrayOfChar[1]);
/*     */       } else {
/* 132 */         paramWriter.write(i2);
/*     */       }
/*     */       
/* 135 */       return 2 + n - j + (k != 0 ? 1 : 0) + (i1 != 0 ? 1 : 0);
/*     */     }
/* 137 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\NumericEntityUnescaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */