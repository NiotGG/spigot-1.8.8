/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArgumentCompleter
/*     */   implements Completer
/*     */ {
/*     */   private final ArgumentDelimiter delimiter;
/*  34 */   private final List<Completer> completers = new ArrayList();
/*     */   
/*  36 */   private boolean strict = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArgumentCompleter(ArgumentDelimiter delimiter, Collection<Completer> completers)
/*     */   {
/*  45 */     this.delimiter = ((ArgumentDelimiter)Preconditions.checkNotNull(delimiter));
/*  46 */     Preconditions.checkNotNull(completers);
/*  47 */     this.completers.addAll(completers);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArgumentCompleter(ArgumentDelimiter delimiter, Completer... completers)
/*     */   {
/*  57 */     this(delimiter, Arrays.asList(completers));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArgumentCompleter(Completer... completers)
/*     */   {
/*  66 */     this(new WhitespaceArgumentDelimiter(), completers);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ArgumentCompleter(List<Completer> completers)
/*     */   {
/*  75 */     this(new WhitespaceArgumentDelimiter(), completers);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStrict(boolean strict)
/*     */   {
/*  83 */     this.strict = strict;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isStrict()
/*     */   {
/*  94 */     return this.strict;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArgumentDelimiter getDelimiter()
/*     */   {
/* 101 */     return this.delimiter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<Completer> getCompleters()
/*     */   {
/* 108 */     return this.completers;
/*     */   }
/*     */   
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates)
/*     */   {
/* 113 */     Preconditions.checkNotNull(candidates);
/*     */     
/* 115 */     ArgumentDelimiter delim = getDelimiter();
/* 116 */     ArgumentList list = delim.delimit(buffer, cursor);
/* 117 */     int argpos = list.getArgumentPosition();
/* 118 */     int argIndex = list.getCursorArgumentIndex();
/*     */     
/* 120 */     if (argIndex < 0) {
/* 121 */       return -1;
/*     */     }
/*     */     
/* 124 */     List<Completer> completers = getCompleters();
/*     */     
/*     */     Completer completer;
/*     */     Completer completer;
/* 128 */     if (argIndex >= completers.size()) {
/* 129 */       completer = (Completer)completers.get(completers.size() - 1);
/*     */     }
/*     */     else {
/* 132 */       completer = (Completer)completers.get(argIndex);
/*     */     }
/*     */     
/*     */ 
/* 136 */     for (int i = 0; (isStrict()) && (i < argIndex); i++) {
/* 137 */       Completer sub = (Completer)completers.get(i >= completers.size() ? completers.size() - 1 : i);
/* 138 */       String[] args = list.getArguments();
/* 139 */       String arg = (args == null) || (i >= args.length) ? "" : args[i];
/*     */       
/* 141 */       List<CharSequence> subCandidates = new LinkedList();
/*     */       
/* 143 */       if (sub.complete(arg, arg.length(), subCandidates) == -1) {
/* 144 */         return -1;
/*     */       }
/*     */       
/* 147 */       if (subCandidates.size() == 0) {
/* 148 */         return -1;
/*     */       }
/*     */     }
/*     */     
/* 152 */     int ret = completer.complete(list.getCursorArgument(), argpos, candidates);
/*     */     
/* 154 */     if (ret == -1) {
/* 155 */       return -1;
/*     */     }
/*     */     
/* 158 */     int pos = ret + list.getBufferPosition() - argpos;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 166 */     if ((cursor != buffer.length()) && (delim.isDelimiter(buffer, cursor))) {
/* 167 */       for (int i = 0; i < candidates.size(); i++) {
/* 168 */         CharSequence val = (CharSequence)candidates.get(i);
/*     */         
/* 170 */         while ((val.length() > 0) && (delim.isDelimiter(val, val.length() - 1))) {
/* 171 */           val = val.subSequence(0, val.length() - 1);
/*     */         }
/*     */         
/* 174 */         candidates.set(i, val);
/*     */       }
/*     */     }
/*     */     
/* 178 */     Log.trace(new Object[] { "Completing ", buffer, " (pos=", Integer.valueOf(cursor), ") with: ", candidates, ": offset=", Integer.valueOf(pos) });
/*     */     
/* 180 */     return pos;
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
/*     */   public static abstract interface ArgumentDelimiter
/*     */   {
/*     */     public abstract ArgumentCompleter.ArgumentList delimit(CharSequence paramCharSequence, int paramInt);
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
/*     */     public abstract boolean isDelimiter(CharSequence paramCharSequence, int paramInt);
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
/*     */   public static abstract class AbstractArgumentDelimiter
/*     */     implements ArgumentCompleter.ArgumentDelimiter
/*     */   {
/* 219 */     private char[] quoteChars = { '\'', '"' };
/*     */     
/* 221 */     private char[] escapeChars = { '\\' };
/*     */     
/*     */     public void setQuoteChars(char[] chars) {
/* 224 */       this.quoteChars = chars;
/*     */     }
/*     */     
/*     */     public char[] getQuoteChars() {
/* 228 */       return this.quoteChars;
/*     */     }
/*     */     
/*     */     public void setEscapeChars(char[] chars) {
/* 232 */       this.escapeChars = chars;
/*     */     }
/*     */     
/*     */     public char[] getEscapeChars() {
/* 236 */       return this.escapeChars;
/*     */     }
/*     */     
/*     */     public ArgumentCompleter.ArgumentList delimit(CharSequence buffer, int cursor) {
/* 240 */       List<String> args = new LinkedList();
/* 241 */       StringBuilder arg = new StringBuilder();
/* 242 */       int argpos = -1;
/* 243 */       int bindex = -1;
/* 244 */       int quoteStart = -1;
/*     */       
/* 246 */       for (int i = 0; (buffer != null) && (i < buffer.length()); i++)
/*     */       {
/*     */ 
/* 249 */         if (i == cursor) {
/* 250 */           bindex = args.size();
/*     */           
/*     */ 
/* 253 */           argpos = arg.length();
/*     */         }
/*     */         
/* 256 */         if ((quoteStart < 0) && (isQuoteChar(buffer, i)))
/*     */         {
/* 258 */           quoteStart = i;
/* 259 */         } else if (quoteStart >= 0)
/*     */         {
/* 261 */           if ((buffer.charAt(quoteStart) == buffer.charAt(i)) && (!isEscaped(buffer, i)))
/*     */           {
/* 263 */             args.add(arg.toString());
/* 264 */             arg.setLength(0);
/* 265 */             quoteStart = -1;
/* 266 */           } else if (!isEscapeChar(buffer, i))
/*     */           {
/* 268 */             arg.append(buffer.charAt(i));
/*     */           }
/*     */           
/*     */         }
/* 272 */         else if (isDelimiter(buffer, i)) {
/* 273 */           if (arg.length() > 0) {
/* 274 */             args.add(arg.toString());
/* 275 */             arg.setLength(0);
/*     */           }
/* 277 */         } else if (!isEscapeChar(buffer, i)) {
/* 278 */           arg.append(buffer.charAt(i));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 283 */       if (cursor == buffer.length()) {
/* 284 */         bindex = args.size();
/*     */         
/*     */ 
/* 287 */         argpos = arg.length();
/*     */       }
/* 289 */       if (arg.length() > 0) {
/* 290 */         args.add(arg.toString());
/*     */       }
/*     */       
/* 293 */       return new ArgumentCompleter.ArgumentList((String[])args.toArray(new String[args.size()]), bindex, argpos, cursor);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isDelimiter(CharSequence buffer, int pos)
/*     */     {
/* 306 */       return (!isQuoted(buffer, pos)) && (!isEscaped(buffer, pos)) && (isDelimiterChar(buffer, pos));
/*     */     }
/*     */     
/*     */     public boolean isQuoted(CharSequence buffer, int pos) {
/* 310 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isQuoteChar(CharSequence buffer, int pos) {
/* 314 */       if (pos < 0) {
/* 315 */         return false;
/*     */       }
/*     */       
/* 318 */       for (int i = 0; (this.quoteChars != null) && (i < this.quoteChars.length); i++) {
/* 319 */         if (buffer.charAt(pos) == this.quoteChars[i]) {
/* 320 */           return !isEscaped(buffer, pos);
/*     */         }
/*     */       }
/*     */       
/* 324 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isEscapeChar(CharSequence buffer, int pos)
/*     */     {
/* 335 */       if (pos < 0) {
/* 336 */         return false;
/*     */       }
/*     */       
/* 339 */       for (int i = 0; (this.escapeChars != null) && (i < this.escapeChars.length); i++) {
/* 340 */         if (buffer.charAt(pos) == this.escapeChars[i]) {
/* 341 */           return !isEscaped(buffer, pos);
/*     */         }
/*     */       }
/*     */       
/* 345 */       return false;
/*     */     }
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
/*     */     public boolean isEscaped(CharSequence buffer, int pos)
/*     */     {
/* 359 */       if (pos <= 0) {
/* 360 */         return false;
/*     */       }
/*     */       
/* 363 */       return isEscapeChar(buffer, pos - 1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract boolean isDelimiterChar(CharSequence paramCharSequence, int paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class WhitespaceArgumentDelimiter
/*     */     extends ArgumentCompleter.AbstractArgumentDelimiter
/*     */   {
/*     */     public boolean isDelimiterChar(CharSequence buffer, int pos)
/*     */     {
/* 389 */       return Character.isWhitespace(buffer.charAt(pos));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class ArgumentList
/*     */   {
/*     */     private String[] arguments;
/*     */     
/*     */ 
/*     */ 
/*     */     private int cursorArgumentIndex;
/*     */     
/*     */ 
/*     */ 
/*     */     private int argumentPosition;
/*     */     
/*     */ 
/*     */     private int bufferPosition;
/*     */     
/*     */ 
/*     */ 
/*     */     public ArgumentList(String[] arguments, int cursorArgumentIndex, int argumentPosition, int bufferPosition)
/*     */     {
/* 415 */       this.arguments = ((String[])Preconditions.checkNotNull(arguments));
/* 416 */       this.cursorArgumentIndex = cursorArgumentIndex;
/* 417 */       this.argumentPosition = argumentPosition;
/* 418 */       this.bufferPosition = bufferPosition;
/*     */     }
/*     */     
/*     */     public void setCursorArgumentIndex(int i) {
/* 422 */       this.cursorArgumentIndex = i;
/*     */     }
/*     */     
/*     */     public int getCursorArgumentIndex() {
/* 426 */       return this.cursorArgumentIndex;
/*     */     }
/*     */     
/*     */     public String getCursorArgument() {
/* 430 */       if ((this.cursorArgumentIndex < 0) || (this.cursorArgumentIndex >= this.arguments.length)) {
/* 431 */         return null;
/*     */       }
/*     */       
/* 434 */       return this.arguments[this.cursorArgumentIndex];
/*     */     }
/*     */     
/*     */     public void setArgumentPosition(int pos) {
/* 438 */       this.argumentPosition = pos;
/*     */     }
/*     */     
/*     */     public int getArgumentPosition() {
/* 442 */       return this.argumentPosition;
/*     */     }
/*     */     
/*     */     public void setArguments(String[] arguments) {
/* 446 */       this.arguments = arguments;
/*     */     }
/*     */     
/*     */     public String[] getArguments() {
/* 450 */       return this.arguments;
/*     */     }
/*     */     
/*     */     public void setBufferPosition(int pos) {
/* 454 */       this.bufferPosition = pos;
/*     */     }
/*     */     
/*     */     public int getBufferPosition() {
/* 458 */       return this.bufferPosition;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\ArgumentCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */