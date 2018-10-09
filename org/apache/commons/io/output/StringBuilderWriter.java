/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class StringBuilderWriter
/*     */   extends Writer
/*     */   implements Serializable
/*     */ {
/*     */   private final StringBuilder builder;
/*     */   
/*     */   public StringBuilderWriter()
/*     */   {
/*  42 */     this.builder = new StringBuilder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuilderWriter(int paramInt)
/*     */   {
/*  51 */     this.builder = new StringBuilder(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuilderWriter(StringBuilder paramStringBuilder)
/*     */   {
/*  60 */     this.builder = (paramStringBuilder != null ? paramStringBuilder : new StringBuilder());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(char paramChar)
/*     */   {
/*  71 */     this.builder.append(paramChar);
/*  72 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Writer append(CharSequence paramCharSequence)
/*     */   {
/*  83 */     this.builder.append(paramCharSequence);
/*  84 */     return this;
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
/*     */   public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*     */   {
/*  97 */     this.builder.append(paramCharSequence, paramInt1, paramInt2);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(String paramString)
/*     */   {
/* 123 */     if (paramString != null) {
/* 124 */       this.builder.append(paramString);
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
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */   {
/* 137 */     if (paramArrayOfChar != null) {
/* 138 */       this.builder.append(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StringBuilder getBuilder()
/*     */   {
/* 148 */     return this.builder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 158 */     return this.builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\StringBuilderWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */