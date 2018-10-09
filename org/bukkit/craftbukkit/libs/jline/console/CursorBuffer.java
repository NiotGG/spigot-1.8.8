/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
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
/*     */ public class CursorBuffer
/*     */ {
/*  22 */   private boolean overTyping = false;
/*     */   
/*  24 */   public int cursor = 0;
/*     */   
/*  26 */   public final StringBuilder buffer = new StringBuilder();
/*     */   
/*     */   public CursorBuffer copy() {
/*  29 */     CursorBuffer that = new CursorBuffer();
/*  30 */     that.overTyping = this.overTyping;
/*  31 */     that.cursor = this.cursor;
/*  32 */     that.buffer.append(toString());
/*     */     
/*  34 */     return that;
/*     */   }
/*     */   
/*     */   public boolean isOverTyping() {
/*  38 */     return this.overTyping;
/*     */   }
/*     */   
/*     */   public void setOverTyping(boolean b) {
/*  42 */     this.overTyping = b;
/*     */   }
/*     */   
/*     */   public int length() {
/*  46 */     return this.buffer.length();
/*     */   }
/*     */   
/*     */   public char nextChar() {
/*  50 */     if (this.cursor == this.buffer.length()) {
/*  51 */       return '\000';
/*     */     }
/*  53 */     return this.buffer.charAt(this.cursor);
/*     */   }
/*     */   
/*     */   public char current()
/*     */   {
/*  58 */     if (this.cursor <= 0) {
/*  59 */       return '\000';
/*     */     }
/*     */     
/*  62 */     return this.buffer.charAt(this.cursor - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void write(char c)
/*     */   {
/*  73 */     this.buffer.insert(this.cursor++, c);
/*  74 */     if ((isOverTyping()) && (this.cursor < this.buffer.length())) {
/*  75 */       this.buffer.deleteCharAt(this.cursor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void write(CharSequence str)
/*     */   {
/*  83 */     Preconditions.checkNotNull(str);
/*     */     
/*  85 */     if (this.buffer.length() == 0) {
/*  86 */       this.buffer.append(str);
/*     */     }
/*     */     else {
/*  89 */       this.buffer.insert(this.cursor, str);
/*     */     }
/*     */     
/*  92 */     this.cursor += str.length();
/*     */     
/*  94 */     if ((isOverTyping()) && (this.cursor < this.buffer.length())) {
/*  95 */       this.buffer.delete(this.cursor, this.cursor + str.length());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean clear() {
/* 100 */     if (this.buffer.length() == 0) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     this.buffer.delete(0, this.buffer.length());
/* 105 */     this.cursor = 0;
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public String upToCursor() {
/* 110 */     if (this.cursor <= 0) {
/* 111 */       return "";
/*     */     }
/*     */     
/* 114 */     return this.buffer.substring(0, this.cursor);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 119 */     return this.buffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\CursorBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */