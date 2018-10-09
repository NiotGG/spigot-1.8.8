/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharSequenceReader
/*     */   extends Reader
/*     */   implements Serializable
/*     */ {
/*     */   private final CharSequence charSequence;
/*     */   private int idx;
/*     */   private int mark;
/*     */   
/*     */   public CharSequenceReader(CharSequence paramCharSequence)
/*     */   {
/*  43 */     this.charSequence = (paramCharSequence != null ? paramCharSequence : "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */   {
/*  51 */     this.idx = 0;
/*  52 */     this.mark = 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void mark(int paramInt)
/*     */   {
/*  62 */     this.mark = this.idx;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read()
/*     */   {
/*  83 */     if (this.idx >= this.charSequence.length()) {
/*  84 */       return -1;
/*     */     }
/*  86 */     return this.charSequence.charAt(this.idx++);
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
/*     */   public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 101 */     if (this.idx >= this.charSequence.length()) {
/* 102 */       return -1;
/*     */     }
/* 104 */     if (paramArrayOfChar == null) {
/* 105 */       throw new NullPointerException("Character array is missing");
/*     */     }
/* 107 */     if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length)) {
/* 108 */       throw new IndexOutOfBoundsException("Array Size=" + paramArrayOfChar.length + ", offset=" + paramInt1 + ", length=" + paramInt2);
/*     */     }
/*     */     
/* 111 */     int i = 0;
/* 112 */     for (int j = 0; j < paramInt2; j++) {
/* 113 */       int k = read();
/* 114 */       if (k == -1) {
/* 115 */         return i;
/*     */       }
/* 117 */       paramArrayOfChar[(paramInt1 + j)] = ((char)k);
/* 118 */       i++;
/*     */     }
/* 120 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 129 */     this.idx = this.mark;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long skip(long paramLong)
/*     */   {
/* 140 */     if (paramLong < 0L) {
/* 141 */       throw new IllegalArgumentException("Number of characters to skip is less than zero: " + paramLong);
/*     */     }
/*     */     
/* 144 */     if (this.idx >= this.charSequence.length()) {
/* 145 */       return -1L;
/*     */     }
/* 147 */     int i = (int)Math.min(this.charSequence.length(), this.idx + paramLong);
/* 148 */     int j = i - this.idx;
/* 149 */     this.idx = i;
/* 150 */     return j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 161 */     return this.charSequence.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\CharSequenceReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */