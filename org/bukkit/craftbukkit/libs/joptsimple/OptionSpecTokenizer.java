/*     */ package org.bukkit.craftbukkit.libs.joptsimple;
/*     */ 
/*     */ import java.util.NoSuchElementException;
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
/*     */ 
/*     */ 
/*     */ class OptionSpecTokenizer
/*     */ {
/*     */   private static final char POSIXLY_CORRECT_MARKER = '+';
/*     */   private String specification;
/*     */   private int index;
/*     */   
/*     */   OptionSpecTokenizer(String specification)
/*     */   {
/*  45 */     if (specification == null) {
/*  46 */       throw new NullPointerException("null option specification");
/*     */     }
/*  48 */     this.specification = specification;
/*     */   }
/*     */   
/*     */   boolean hasMore() {
/*  52 */     return this.index < this.specification.length();
/*     */   }
/*     */   
/*     */   AbstractOptionSpec<?> next() {
/*  56 */     if (!hasMore()) {
/*  57 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*  60 */     String optionCandidate = String.valueOf(this.specification.charAt(this.index));
/*  61 */     this.index += 1;
/*     */     
/*     */ 
/*  64 */     if ("W".equals(optionCandidate)) {
/*  65 */       AbstractOptionSpec<?> spec = handleReservedForExtensionsToken();
/*     */       
/*  67 */       if (spec != null) {
/*  68 */         return spec;
/*     */       }
/*     */     }
/*  71 */     ParserRules.ensureLegalOption(optionCandidate);
/*     */     AbstractOptionSpec<?> spec;
/*  73 */     AbstractOptionSpec<?> spec; if (hasMore()) {
/*  74 */       spec = this.specification.charAt(this.index) == ':' ? handleArgumentAcceptingOption(optionCandidate) : new NoArgumentOptionSpec(optionCandidate);
/*     */     }
/*     */     else
/*     */     {
/*  78 */       spec = new NoArgumentOptionSpec(optionCandidate);
/*     */     }
/*  80 */     return spec;
/*     */   }
/*     */   
/*     */   void configure(OptionParser parser) {
/*  84 */     adjustForPosixlyCorrect(parser);
/*     */     
/*  86 */     while (hasMore())
/*  87 */       parser.recognize(next());
/*     */   }
/*     */   
/*     */   private void adjustForPosixlyCorrect(OptionParser parser) {
/*  91 */     if ('+' == this.specification.charAt(0)) {
/*  92 */       parser.posixlyCorrect(true);
/*  93 */       this.specification = this.specification.substring(1);
/*     */     }
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> handleReservedForExtensionsToken() {
/*  98 */     if (!hasMore()) {
/*  99 */       return new NoArgumentOptionSpec("W");
/*     */     }
/* 101 */     if (this.specification.charAt(this.index) == ';') {
/* 102 */       this.index += 1;
/* 103 */       return new AlternativeLongOptionSpec();
/*     */     }
/*     */     
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> handleArgumentAcceptingOption(String candidate) {
/* 110 */     this.index += 1;
/*     */     
/* 112 */     if ((hasMore()) && (this.specification.charAt(this.index) == ':')) {
/* 113 */       this.index += 1;
/* 114 */       return new OptionalArgumentOptionSpec(candidate);
/*     */     }
/*     */     
/* 117 */     return new RequiredArgumentOptionSpec(candidate);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSpecTokenizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */