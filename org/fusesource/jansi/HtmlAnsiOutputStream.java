/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class HtmlAnsiOutputStream
/*     */   extends AnsiOutputStream
/*     */ {
/*  32 */   private boolean concealOn = false;
/*     */   
/*     */   public void close() throws IOException
/*     */   {
/*  36 */     closeAttributes();
/*  37 */     super.close();
/*     */   }
/*     */   
/*  40 */   private static final String[] ANSI_COLOR_MAP = { "black", "red", "green", "yellow", "blue", "magenta", "cyan", "white" };
/*     */   
/*     */ 
/*  43 */   private static final byte[] BYTES_QUOT = "&quot;".getBytes();
/*  44 */   private static final byte[] BYTES_AMP = "&amp;".getBytes();
/*  45 */   private static final byte[] BYTES_LT = "&lt;".getBytes();
/*  46 */   private static final byte[] BYTES_GT = "&gt;".getBytes();
/*     */   
/*     */   public HtmlAnsiOutputStream(OutputStream os) {
/*  49 */     super(os);
/*     */   }
/*     */   
/*  52 */   private List<String> closingAttributes = new ArrayList();
/*     */   
/*     */   private void write(String s) throws IOException {
/*  55 */     this.out.write(s.getBytes());
/*     */   }
/*     */   
/*     */   private void writeAttribute(String s) throws IOException {
/*  59 */     write("<" + s + ">");
/*  60 */     this.closingAttributes.add(0, s.split(" ", 2)[0]);
/*     */   }
/*     */   
/*     */   private void closeAttributes() throws IOException {
/*  64 */     for (String attr : this.closingAttributes) {
/*  65 */       write("</" + attr + ">");
/*     */     }
/*  67 */     this.closingAttributes.clear();
/*     */   }
/*     */   
/*     */   public void write(int data) throws IOException {
/*  71 */     switch (data) {
/*     */     case 34: 
/*  73 */       this.out.write(BYTES_QUOT);
/*  74 */       break;
/*     */     case 38: 
/*  76 */       this.out.write(BYTES_AMP);
/*  77 */       break;
/*     */     case 60: 
/*  79 */       this.out.write(BYTES_LT);
/*  80 */       break;
/*     */     case 62: 
/*  82 */       this.out.write(BYTES_GT);
/*  83 */       break;
/*     */     default: 
/*  85 */       super.write(data);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeLine(byte[] buf, int offset, int len) throws IOException {
/*  90 */     write(buf, offset, len);
/*  91 */     closeAttributes();
/*     */   }
/*     */   
/*     */   protected void processSetAttribute(int attribute) throws IOException
/*     */   {
/*  96 */     switch (attribute) {
/*     */     case 8: 
/*  98 */       write("\033[8m");
/*  99 */       this.concealOn = true;
/* 100 */       break;
/*     */     case 1: 
/* 102 */       writeAttribute("b");
/* 103 */       break;
/*     */     case 22: 
/* 105 */       closeAttributes();
/* 106 */       break;
/*     */     case 4: 
/* 108 */       writeAttribute("u");
/* 109 */       break;
/*     */     case 24: 
/* 111 */       closeAttributes();
/* 112 */       break;
/*     */     case 7: 
/*     */       break;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   protected void processAttributeRest()
/*     */     throws IOException
/*     */   {
/* 122 */     if (this.concealOn) {
/* 123 */       write("\033[0m");
/* 124 */       this.concealOn = false;
/*     */     }
/* 126 */     closeAttributes();
/*     */   }
/*     */   
/*     */   protected void processSetForegroundColor(int color) throws IOException
/*     */   {
/* 131 */     writeAttribute("span style=\"color: " + ANSI_COLOR_MAP[color] + ";\"");
/*     */   }
/*     */   
/*     */   protected void processSetBackgroundColor(int color) throws IOException
/*     */   {
/* 136 */     writeAttribute("span style=\"background-color: " + ANSI_COLOR_MAP[color] + ";\"");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\HtmlAnsiOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */