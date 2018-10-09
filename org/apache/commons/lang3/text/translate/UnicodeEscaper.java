/*     */ package org.apache.commons.lang3.text.translate;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnicodeEscaper
/*     */   extends CodePointTranslator
/*     */ {
/*     */   private final int below;
/*     */   private final int above;
/*     */   private final boolean between;
/*     */   
/*     */   public UnicodeEscaper()
/*     */   {
/*  38 */     this(0, Integer.MAX_VALUE, true);
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
/*     */   protected UnicodeEscaper(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  52 */     this.below = paramInt1;
/*  53 */     this.above = paramInt2;
/*  54 */     this.between = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UnicodeEscaper below(int paramInt)
/*     */   {
/*  64 */     return outsideOf(paramInt, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UnicodeEscaper above(int paramInt)
/*     */   {
/*  74 */     return outsideOf(0, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UnicodeEscaper outsideOf(int paramInt1, int paramInt2)
/*     */   {
/*  85 */     return new UnicodeEscaper(paramInt1, paramInt2, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UnicodeEscaper between(int paramInt1, int paramInt2)
/*     */   {
/*  96 */     return new UnicodeEscaper(paramInt1, paramInt2, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean translate(int paramInt, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 104 */     if (this.between) {
/* 105 */       if ((paramInt < this.below) || (paramInt > this.above)) {
/* 106 */         return false;
/*     */       }
/*     */     }
/* 109 */     else if ((paramInt >= this.below) && (paramInt <= this.above)) {
/* 110 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 115 */     if (paramInt > 65535) {
/* 116 */       paramWriter.write(toUtf16Escape(paramInt));
/* 117 */     } else if (paramInt > 4095) {
/* 118 */       paramWriter.write("\\u" + hex(paramInt));
/* 119 */     } else if (paramInt > 255) {
/* 120 */       paramWriter.write("\\u0" + hex(paramInt));
/* 121 */     } else if (paramInt > 15) {
/* 122 */       paramWriter.write("\\u00" + hex(paramInt));
/*     */     } else {
/* 124 */       paramWriter.write("\\u000" + hex(paramInt));
/*     */     }
/* 126 */     return true;
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
/*     */   protected String toUtf16Escape(int paramInt)
/*     */   {
/* 139 */     return "\\u" + hex(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\UnicodeEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */