/*     */ package org.bukkit.craftbukkit.libs.jline.console.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.Enumeration;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
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
/*     */ class ConsoleReaderInputStream
/*     */   extends SequenceInputStream
/*     */ {
/*  30 */   private static InputStream systemIn = System.in;
/*     */   
/*     */   public static void setIn() throws IOException {
/*  33 */     setIn(new ConsoleReader());
/*     */   }
/*     */   
/*     */   public static void setIn(ConsoleReader reader) {
/*  37 */     System.setIn(new ConsoleReaderInputStream(reader));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void restoreIn()
/*     */   {
/*  44 */     System.setIn(systemIn);
/*     */   }
/*     */   
/*     */   public ConsoleReaderInputStream(ConsoleReader reader) {
/*  48 */     super(new ConsoleEnumeration(reader));
/*     */   }
/*     */   
/*     */   private static class ConsoleEnumeration
/*     */     implements Enumeration
/*     */   {
/*     */     private final ConsoleReader reader;
/*  55 */     private ConsoleReaderInputStream.ConsoleLineInputStream next = null;
/*  56 */     private ConsoleReaderInputStream.ConsoleLineInputStream prev = null;
/*     */     
/*     */     public ConsoleEnumeration(ConsoleReader reader) {
/*  59 */       this.reader = reader;
/*     */     }
/*     */     
/*     */     public Object nextElement() {
/*  63 */       if (this.next != null) {
/*  64 */         InputStream n = this.next;
/*  65 */         this.prev = this.next;
/*  66 */         this.next = null;
/*     */         
/*  68 */         return n;
/*     */       }
/*     */       
/*  71 */       return new ConsoleReaderInputStream.ConsoleLineInputStream(this.reader);
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements()
/*     */     {
/*  76 */       if ((this.prev != null) && (this.prev.wasNull == true)) {
/*  77 */         return false;
/*     */       }
/*     */       
/*  80 */       if (this.next == null) {
/*  81 */         this.next = ((ConsoleReaderInputStream.ConsoleLineInputStream)nextElement());
/*     */       }
/*     */       
/*  84 */       return this.next != null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ConsoleLineInputStream
/*     */     extends InputStream
/*     */   {
/*     */     private final ConsoleReader reader;
/*  92 */     private String line = null;
/*  93 */     private int index = 0;
/*  94 */     private boolean eol = false;
/*  95 */     protected boolean wasNull = false;
/*     */     
/*     */     public ConsoleLineInputStream(ConsoleReader reader) {
/*  98 */       this.reader = reader;
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 102 */       if (this.eol) {
/* 103 */         return -1;
/*     */       }
/*     */       
/* 106 */       if (this.line == null) {
/* 107 */         this.line = this.reader.readLine();
/*     */       }
/*     */       
/* 110 */       if (this.line == null) {
/* 111 */         this.wasNull = true;
/* 112 */         return -1;
/*     */       }
/*     */       
/* 115 */       if (this.index >= this.line.length()) {
/* 116 */         this.eol = true;
/* 117 */         return 10;
/*     */       }
/*     */       
/* 120 */       return this.line.charAt(this.index++);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\internal\ConsoleReaderInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */