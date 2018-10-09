/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AppendableCharSequence
/*     */   implements CharSequence, Appendable
/*     */ {
/*     */   private char[] chars;
/*     */   private int pos;
/*     */   
/*     */   public AppendableCharSequence(int paramInt)
/*     */   {
/*  26 */     if (paramInt < 1) {
/*  27 */       throw new IllegalArgumentException("length: " + paramInt + " (length: >= 1)");
/*     */     }
/*  29 */     this.chars = new char[paramInt];
/*     */   }
/*     */   
/*     */   private AppendableCharSequence(char[] paramArrayOfChar) {
/*  33 */     this.chars = paramArrayOfChar;
/*  34 */     this.pos = paramArrayOfChar.length;
/*     */   }
/*     */   
/*     */   public int length()
/*     */   {
/*  39 */     return this.pos;
/*     */   }
/*     */   
/*     */   public char charAt(int paramInt)
/*     */   {
/*  44 */     if (paramInt > this.pos) {
/*  45 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  47 */     return this.chars[paramInt];
/*     */   }
/*     */   
/*     */   public AppendableCharSequence subSequence(int paramInt1, int paramInt2)
/*     */   {
/*  52 */     return new AppendableCharSequence(Arrays.copyOfRange(this.chars, paramInt1, paramInt2));
/*     */   }
/*     */   
/*     */   public AppendableCharSequence append(char paramChar)
/*     */   {
/*  57 */     if (this.pos == this.chars.length) {
/*  58 */       char[] arrayOfChar = this.chars;
/*     */       
/*  60 */       int i = arrayOfChar.length << 1;
/*  61 */       if (i < 0) {
/*  62 */         throw new IllegalStateException();
/*     */       }
/*  64 */       this.chars = new char[i];
/*  65 */       System.arraycopy(arrayOfChar, 0, this.chars, 0, arrayOfChar.length);
/*     */     }
/*  67 */     this.chars[(this.pos++)] = paramChar;
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public AppendableCharSequence append(CharSequence paramCharSequence)
/*     */   {
/*  73 */     return append(paramCharSequence, 0, paramCharSequence.length());
/*     */   }
/*     */   
/*     */   public AppendableCharSequence append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/*  78 */     if (paramCharSequence.length() < paramInt2) {
/*  79 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  81 */     int i = paramInt2 - paramInt1;
/*  82 */     if (i > this.chars.length - this.pos) {
/*  83 */       this.chars = expand(this.chars, this.pos + i, this.pos);
/*     */     }
/*  85 */     if ((paramCharSequence instanceof AppendableCharSequence))
/*     */     {
/*  87 */       AppendableCharSequence localAppendableCharSequence = (AppendableCharSequence)paramCharSequence;
/*  88 */       char[] arrayOfChar = localAppendableCharSequence.chars;
/*  89 */       System.arraycopy(arrayOfChar, paramInt1, this.chars, this.pos, i);
/*  90 */       this.pos += i;
/*  91 */       return this;
/*     */     }
/*  93 */     for (int j = paramInt1; j < paramInt2; j++) {
/*  94 */       this.chars[(this.pos++)] = paramCharSequence.charAt(j);
/*     */     }
/*     */     
/*  97 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void reset()
/*     */   {
/* 105 */     this.pos = 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 110 */     return new String(this.chars, 0, this.pos);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String substring(int paramInt1, int paramInt2)
/*     */   {
/* 117 */     int i = paramInt2 - paramInt1;
/* 118 */     if ((paramInt1 > this.pos) || (i > this.pos)) {
/* 119 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 121 */     return new String(this.chars, paramInt1, i);
/*     */   }
/*     */   
/*     */   private static char[] expand(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
/* 125 */     int i = paramArrayOfChar.length;
/*     */     do
/*     */     {
/* 128 */       i <<= 1;
/*     */       
/* 130 */       if (i < 0) {
/* 131 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 134 */     } while (paramInt1 > i);
/*     */     
/* 136 */     char[] arrayOfChar = new char[i];
/* 137 */     System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramInt2);
/*     */     
/* 139 */     return arrayOfChar;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\AppendableCharSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */