/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FormattingInfo
/*     */ {
/*  27 */   private static final char[] SPACES = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  32 */   private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int minLength;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int maxLength;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean leftAlign;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FormattingInfo(boolean paramBoolean, int paramInt1, int paramInt2)
/*     */   {
/*  60 */     this.leftAlign = paramBoolean;
/*  61 */     this.minLength = paramInt1;
/*  62 */     this.maxLength = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static FormattingInfo getDefault()
/*     */   {
/*  71 */     return DEFAULT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLeftAligned()
/*     */   {
/*  80 */     return this.leftAlign;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMinLength()
/*     */   {
/*  89 */     return this.minLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMaxLength()
/*     */   {
/*  98 */     return this.maxLength;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void format(int paramInt, StringBuilder paramStringBuilder)
/*     */   {
/* 110 */     int i = paramStringBuilder.length() - paramInt;
/*     */     
/* 112 */     if (i > this.maxLength) {
/* 113 */       paramStringBuilder.delete(paramInt, paramStringBuilder.length() - this.maxLength);
/* 114 */     } else if (i < this.minLength) { int j;
/* 115 */       if (this.leftAlign) {
/* 116 */         j = paramStringBuilder.length();
/* 117 */         paramStringBuilder.setLength(paramInt + this.minLength);
/*     */         
/* 119 */         for (int k = j; k < paramStringBuilder.length(); k++) {
/* 120 */           paramStringBuilder.setCharAt(k, ' ');
/*     */         }
/*     */       } else {
/* 123 */         j = this.minLength - i;
/* 125 */         for (; 
/* 125 */             j > SPACES.length; j -= SPACES.length) {
/* 126 */           paramStringBuilder.insert(paramInt, SPACES);
/*     */         }
/*     */         
/* 129 */         paramStringBuilder.insert(paramInt, SPACES, 0, j);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 141 */     StringBuilder localStringBuilder = new StringBuilder();
/* 142 */     localStringBuilder.append(super.toString());
/* 143 */     localStringBuilder.append("[leftAlign=");
/* 144 */     localStringBuilder.append(this.leftAlign);
/* 145 */     localStringBuilder.append(", maxLength=");
/* 146 */     localStringBuilder.append(this.maxLength);
/* 147 */     localStringBuilder.append(", minLength=");
/* 148 */     localStringBuilder.append(this.minLength);
/* 149 */     localStringBuilder.append("]");
/* 150 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\FormattingInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */