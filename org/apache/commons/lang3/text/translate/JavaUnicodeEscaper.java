/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaUnicodeEscaper
/*     */   extends UnicodeEscaper
/*     */ {
/*     */   public static JavaUnicodeEscaper above(int paramInt)
/*     */   {
/*  37 */     return outsideOf(0, paramInt);
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
/*     */   public static JavaUnicodeEscaper below(int paramInt)
/*     */   {
/*  50 */     return outsideOf(paramInt, Integer.MAX_VALUE);
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
/*     */   public static JavaUnicodeEscaper between(int paramInt1, int paramInt2)
/*     */   {
/*  65 */     return new JavaUnicodeEscaper(paramInt1, paramInt2, true);
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
/*     */   public static JavaUnicodeEscaper outsideOf(int paramInt1, int paramInt2)
/*     */   {
/*  80 */     return new JavaUnicodeEscaper(paramInt1, paramInt2, false);
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
/*     */   public JavaUnicodeEscaper(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  98 */     super(paramInt1, paramInt2, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected String toUtf16Escape(int paramInt)
/*     */   {
/* 110 */     char[] arrayOfChar = Character.toChars(paramInt);
/* 111 */     return "\\u" + hex(arrayOfChar[0]) + "\\u" + hex(arrayOfChar[1]);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\JavaUnicodeEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */