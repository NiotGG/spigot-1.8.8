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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumericEntityEscaper
/*     */   extends CodePointTranslator
/*     */ {
/*     */   private final int below;
/*     */   private final int above;
/*     */   private final boolean between;
/*     */   
/*     */   private NumericEntityEscaper(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  45 */     this.below = paramInt1;
/*  46 */     this.above = paramInt2;
/*  47 */     this.between = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NumericEntityEscaper()
/*     */   {
/*  54 */     this(0, Integer.MAX_VALUE, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NumericEntityEscaper below(int paramInt)
/*     */   {
/*  64 */     return outsideOf(paramInt, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NumericEntityEscaper above(int paramInt)
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
/*     */   public static NumericEntityEscaper between(int paramInt1, int paramInt2)
/*     */   {
/*  85 */     return new NumericEntityEscaper(paramInt1, paramInt2, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NumericEntityEscaper outsideOf(int paramInt1, int paramInt2)
/*     */   {
/*  96 */     return new NumericEntityEscaper(paramInt1, paramInt2, false);
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
/* 114 */     paramWriter.write("&#");
/* 115 */     paramWriter.write(Integer.toString(paramInt, 10));
/* 116 */     paramWriter.write(59);
/* 117 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\translate\NumericEntityEscaper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */