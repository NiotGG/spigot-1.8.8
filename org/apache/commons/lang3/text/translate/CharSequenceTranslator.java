/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CharSequenceTranslator
/*     */ {
/*     */   public abstract int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
/*     */     throws IOException;
/*     */   
/*     */   public final String translate(CharSequence paramCharSequence)
/*     */   {
/*  54 */     if (paramCharSequence == null) {
/*  55 */       return null;
/*     */     }
/*     */     try {
/*  58 */       StringWriter localStringWriter = new StringWriter(paramCharSequence.length() * 2);
/*  59 */       translate(paramCharSequence, localStringWriter);
/*  60 */       return localStringWriter.toString();
/*     */     }
/*     */     catch (IOException localIOException) {
/*  63 */       throw new RuntimeException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void translate(CharSequence paramCharSequence, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/*  76 */     if (paramWriter == null) {
/*  77 */       throw new IllegalArgumentException("The Writer must not be null");
/*     */     }
/*  79 */     if (paramCharSequence == null) {
/*  80 */       return;
/*     */     }
/*  82 */     int i = 0;
/*  83 */     int j = paramCharSequence.length();
/*  84 */     while (i < j) {
/*  85 */       int k = translate(paramCharSequence, i, paramWriter);
/*  86 */       if (k == 0) {
/*  87 */         char[] arrayOfChar = Character.toChars(Character.codePointAt(paramCharSequence, i));
/*  88 */         paramWriter.write(arrayOfChar);
/*  89 */         i += arrayOfChar.length;
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  94 */         for (int m = 0; m < k; m++) {
/*  95 */           i += Character.charCount(Character.codePointAt(paramCharSequence, i));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final CharSequenceTranslator with(CharSequenceTranslator... paramVarArgs)
/*     */   {
/* 108 */     CharSequenceTranslator[] arrayOfCharSequenceTranslator = new CharSequenceTranslator[paramVarArgs.length + 1];
/* 109 */     arrayOfCharSequenceTranslator[0] = this;
/* 110 */     System.arraycopy(paramVarArgs, 0, arrayOfCharSequenceTranslator, 1, paramVarArgs.length);
/* 111 */     return new AggregateTranslator(arrayOfCharSequenceTranslator);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String hex(int paramInt)
/*     */   {
/* 122 */     return Integer.toHexString(paramInt).toUpperCase(Locale.ENGLISH);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\CharSequenceTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */