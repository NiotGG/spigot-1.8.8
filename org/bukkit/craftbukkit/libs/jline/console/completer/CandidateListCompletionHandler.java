/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*     */ import org.bukkit.craftbukkit.libs.jline.console.CursorBuffer;
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
/*     */ public class CandidateListCompletionHandler
/*     */   implements CompletionHandler
/*     */ {
/*     */   public boolean complete(ConsoleReader reader, List<CharSequence> candidates, int pos)
/*     */     throws IOException
/*     */   {
/*  41 */     CursorBuffer buf = reader.getCursorBuffer();
/*     */     
/*     */ 
/*  44 */     if (candidates.size() == 1) {
/*  45 */       CharSequence value = (CharSequence)candidates.get(0);
/*     */       
/*     */ 
/*  48 */       if (value.equals(buf.toString())) {
/*  49 */         return false;
/*     */       }
/*     */       
/*  52 */       setBuffer(reader, value, pos);
/*     */       
/*  54 */       return true;
/*     */     }
/*  56 */     if (candidates.size() > 1) {
/*  57 */       String value = getUnambiguousCompletions(candidates);
/*  58 */       setBuffer(reader, value, pos);
/*     */     }
/*     */     
/*  61 */     printCandidates(reader, candidates);
/*     */     
/*     */ 
/*  64 */     reader.drawLine();
/*     */     
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public static void setBuffer(ConsoleReader reader, CharSequence value, int offset)
/*     */     throws IOException
/*     */   {
/*  72 */     while ((reader.getCursorBuffer().cursor > offset) && (reader.backspace())) {}
/*     */     
/*     */ 
/*     */ 
/*  76 */     reader.putString(value);
/*  77 */     reader.setCursorPosition(offset + value.length());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void printCandidates(ConsoleReader reader, Collection<CharSequence> candidates)
/*     */     throws IOException
/*     */   {
/*  89 */     Set<CharSequence> distinct = new HashSet(candidates);
/*     */     
/*  91 */     if (distinct.size() > reader.getAutoprintThreshold())
/*     */     {
/*  93 */       reader.print(Messages.DISPLAY_CANDIDATES.format(new Object[] { Integer.valueOf(candidates.size()) }));
/*  94 */       reader.flush();
/*     */       
/*     */ 
/*     */ 
/*  98 */       String noOpt = Messages.DISPLAY_CANDIDATES_NO.format(new Object[0]);
/*  99 */       String yesOpt = Messages.DISPLAY_CANDIDATES_YES.format(new Object[0]);
/* 100 */       char[] allowed = { yesOpt.charAt(0), noOpt.charAt(0) };
/*     */       int c;
/* 102 */       while ((c = reader.readCharacter(allowed)) != -1) {
/* 103 */         String tmp = new String(new char[] { (char)c });
/*     */         
/* 105 */         if (noOpt.startsWith(tmp)) {
/* 106 */           reader.println();
/* 107 */           return;
/*     */         }
/* 109 */         if (yesOpt.startsWith(tmp)) {
/*     */           break;
/*     */         }
/*     */         
/* 113 */         reader.beep();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 119 */     if (distinct.size() != candidates.size()) {
/* 120 */       Collection<CharSequence> copy = new ArrayList();
/*     */       
/* 122 */       for (CharSequence next : candidates) {
/* 123 */         if (!copy.contains(next)) {
/* 124 */           copy.add(next);
/*     */         }
/*     */       }
/*     */       
/* 128 */       candidates = copy;
/*     */     }
/*     */     
/* 131 */     reader.println();
/* 132 */     reader.printColumns(candidates);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String getUnambiguousCompletions(List<CharSequence> candidates)
/*     */   {
/* 141 */     if ((candidates == null) || (candidates.isEmpty())) {
/* 142 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 146 */     String[] strings = (String[])candidates.toArray(new String[candidates.size()]);
/*     */     
/* 148 */     String first = strings[0];
/* 149 */     StringBuilder candidate = new StringBuilder();
/*     */     
/* 151 */     for (int i = 0; i < first.length(); i++) {
/* 152 */       if (!startsWith(first.substring(0, i + 1), strings)) break;
/* 153 */       candidate.append(first.charAt(i));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 160 */     return candidate.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean startsWith(String starts, String[] candidates)
/*     */   {
/* 167 */     for (String candidate : candidates) {
/* 168 */       if (!candidate.startsWith(starts)) {
/* 169 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   private static enum Messages
/*     */   {
/* 178 */     DISPLAY_CANDIDATES, 
/* 179 */     DISPLAY_CANDIDATES_YES, 
/* 180 */     DISPLAY_CANDIDATES_NO;
/*     */     
/*     */ 
/*     */ 
/* 184 */     private static final ResourceBundle bundle = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName(), Locale.getDefault());
/*     */     
/*     */     private Messages() {}
/*     */     
/* 188 */     public String format(Object... args) { if (bundle == null) {
/* 189 */         return "";
/*     */       }
/* 191 */       return String.format(bundle.getString(name()), args);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\CandidateListCompletionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */