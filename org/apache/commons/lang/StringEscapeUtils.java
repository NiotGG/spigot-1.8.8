/*     */ package org.apache.commons.lang;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Locale;
/*     */ import org.apache.commons.lang.exception.NestableRuntimeException;
/*     */ import org.apache.commons.lang.text.StrBuilder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringEscapeUtils
/*     */ {
/*     */   private static final char CSV_DELIMITER = ',';
/*     */   private static final char CSV_QUOTE = '"';
/*  49 */   private static final String CSV_QUOTE_STR = String.valueOf('"');
/*  50 */   private static final char[] CSV_SEARCH_CHARS = { ',', '"', '\r', '\n' };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String escapeJava(String str)
/*     */   {
/*  90 */     return escapeJavaStyleString(str, false, false);
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
/*     */   public static void escapeJava(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 106 */     escapeJavaStyleString(out, str, false, false);
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
/*     */   public static String escapeJavaScript(String str)
/*     */   {
/* 131 */     return escapeJavaStyleString(str, true, true);
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
/*     */   public static void escapeJavaScript(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 147 */     escapeJavaStyleString(out, str, true, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes, boolean escapeForwardSlash)
/*     */   {
/* 159 */     if (str == null) {
/* 160 */       return null;
/*     */     }
/*     */     try {
/* 163 */       StringWriter writer = new StringWriter(str.length() * 2);
/* 164 */       escapeJavaStyleString(writer, str, escapeSingleQuotes, escapeForwardSlash);
/* 165 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 168 */       throw new UnhandledException(ioe);
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
/*     */ 
/*     */   private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote, boolean escapeForwardSlash)
/*     */     throws IOException
/*     */   {
/* 183 */     if (out == null) {
/* 184 */       throw new IllegalArgumentException("The Writer must not be null");
/*     */     }
/* 186 */     if (str == null) {
/* 187 */       return;
/*     */     }
/*     */     
/* 190 */     int sz = str.length();
/* 191 */     for (int i = 0; i < sz; i++) {
/* 192 */       char ch = str.charAt(i);
/*     */       
/*     */ 
/* 195 */       if (ch > '࿿') {
/* 196 */         out.write("\\u" + hex(ch));
/* 197 */       } else if (ch > 'ÿ') {
/* 198 */         out.write("\\u0" + hex(ch));
/* 199 */       } else if (ch > '') {
/* 200 */         out.write("\\u00" + hex(ch));
/* 201 */       } else if (ch < ' ') {
/* 202 */         switch (ch) {
/*     */         case '\b': 
/* 204 */           out.write(92);
/* 205 */           out.write(98);
/* 206 */           break;
/*     */         case '\n': 
/* 208 */           out.write(92);
/* 209 */           out.write(110);
/* 210 */           break;
/*     */         case '\t': 
/* 212 */           out.write(92);
/* 213 */           out.write(116);
/* 214 */           break;
/*     */         case '\f': 
/* 216 */           out.write(92);
/* 217 */           out.write(102);
/* 218 */           break;
/*     */         case '\r': 
/* 220 */           out.write(92);
/* 221 */           out.write(114);
/* 222 */           break;
/*     */         case '\013': default: 
/* 224 */           if (ch > '\017') {
/* 225 */             out.write("\\u00" + hex(ch));
/*     */           } else {
/* 227 */             out.write("\\u000" + hex(ch));
/*     */           }
/* 229 */           break;
/*     */         }
/*     */       } else {
/* 232 */         switch (ch) {
/*     */         case '\'': 
/* 234 */           if (escapeSingleQuote) {
/* 235 */             out.write(92);
/*     */           }
/* 237 */           out.write(39);
/* 238 */           break;
/*     */         case '"': 
/* 240 */           out.write(92);
/* 241 */           out.write(34);
/* 242 */           break;
/*     */         case '\\': 
/* 244 */           out.write(92);
/* 245 */           out.write(92);
/* 246 */           break;
/*     */         case '/': 
/* 248 */           if (escapeForwardSlash) {
/* 249 */             out.write(92);
/*     */           }
/* 251 */           out.write(47);
/* 252 */           break;
/*     */         default: 
/* 254 */           out.write(ch);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String hex(char ch)
/*     */   {
/* 269 */     return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
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
/*     */   public static String unescapeJava(String str)
/*     */   {
/* 282 */     if (str == null) {
/* 283 */       return null;
/*     */     }
/*     */     try {
/* 286 */       StringWriter writer = new StringWriter(str.length());
/* 287 */       unescapeJava(writer, str);
/* 288 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 291 */       throw new UnhandledException(ioe);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unescapeJava(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 311 */     if (out == null) {
/* 312 */       throw new IllegalArgumentException("The Writer must not be null");
/*     */     }
/* 314 */     if (str == null) {
/* 315 */       return;
/*     */     }
/* 317 */     int sz = str.length();
/* 318 */     StrBuilder unicode = new StrBuilder(4);
/* 319 */     boolean hadSlash = false;
/* 320 */     boolean inUnicode = false;
/* 321 */     for (int i = 0; i < sz; i++) {
/* 322 */       char ch = str.charAt(i);
/* 323 */       if (inUnicode)
/*     */       {
/*     */ 
/* 326 */         unicode.append(ch);
/* 327 */         if (unicode.length() == 4)
/*     */         {
/*     */           try
/*     */           {
/* 331 */             int value = Integer.parseInt(unicode.toString(), 16);
/* 332 */             out.write((char)value);
/* 333 */             unicode.setLength(0);
/* 334 */             inUnicode = false;
/* 335 */             hadSlash = false;
/*     */           } catch (NumberFormatException nfe) {
/* 337 */             throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 342 */       else if (hadSlash)
/*     */       {
/* 344 */         hadSlash = false;
/* 345 */         switch (ch) {
/*     */         case '\\': 
/* 347 */           out.write(92);
/* 348 */           break;
/*     */         case '\'': 
/* 350 */           out.write(39);
/* 351 */           break;
/*     */         case '"': 
/* 353 */           out.write(34);
/* 354 */           break;
/*     */         case 'r': 
/* 356 */           out.write(13);
/* 357 */           break;
/*     */         case 'f': 
/* 359 */           out.write(12);
/* 360 */           break;
/*     */         case 't': 
/* 362 */           out.write(9);
/* 363 */           break;
/*     */         case 'n': 
/* 365 */           out.write(10);
/* 366 */           break;
/*     */         case 'b': 
/* 368 */           out.write(8);
/* 369 */           break;
/*     */         
/*     */ 
/*     */         case 'u': 
/* 373 */           inUnicode = true;
/* 374 */           break;
/*     */         
/*     */         default: 
/* 377 */           out.write(ch);
/* 378 */           break;
/*     */         }
/*     */       }
/* 381 */       else if (ch == '\\') {
/* 382 */         hadSlash = true;
/*     */       }
/*     */       else {
/* 385 */         out.write(ch);
/*     */       } }
/* 387 */     if (hadSlash)
/*     */     {
/*     */ 
/* 390 */       out.write(92);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String unescapeJavaScript(String str)
/*     */   {
/* 406 */     return unescapeJava(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unescapeJavaScript(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 426 */     unescapeJava(out, str);
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
/*     */   public static String escapeHtml(String str)
/*     */   {
/* 458 */     if (str == null) {
/* 459 */       return null;
/*     */     }
/*     */     try {
/* 462 */       StringWriter writer = new StringWriter((int)(str.length() * 1.5D));
/* 463 */       escapeHtml(writer, str);
/* 464 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 467 */       throw new UnhandledException(ioe);
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
/*     */   public static void escapeHtml(Writer writer, String string)
/*     */     throws IOException
/*     */   {
/* 501 */     if (writer == null) {
/* 502 */       throw new IllegalArgumentException("The Writer must not be null.");
/*     */     }
/* 504 */     if (string == null) {
/* 505 */       return;
/*     */     }
/* 507 */     Entities.HTML40.escape(writer, string);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String unescapeHtml(String str)
/*     */   {
/* 528 */     if (str == null) {
/* 529 */       return null;
/*     */     }
/*     */     try {
/* 532 */       StringWriter writer = new StringWriter((int)(str.length() * 1.5D));
/* 533 */       unescapeHtml(writer, str);
/* 534 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 537 */       throw new UnhandledException(ioe);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unescapeHtml(Writer writer, String string)
/*     */     throws IOException
/*     */   {
/* 560 */     if (writer == null) {
/* 561 */       throw new IllegalArgumentException("The Writer must not be null.");
/*     */     }
/* 563 */     if (string == null) {
/* 564 */       return;
/*     */     }
/* 566 */     Entities.HTML40.unescape(writer, string);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void escapeXml(Writer writer, String str)
/*     */     throws IOException
/*     */   {
/* 590 */     if (writer == null) {
/* 591 */       throw new IllegalArgumentException("The Writer must not be null.");
/*     */     }
/* 593 */     if (str == null) {
/* 594 */       return;
/*     */     }
/* 596 */     Entities.XML.escape(writer, str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String escapeXml(String str)
/*     */   {
/* 617 */     if (str == null) {
/* 618 */       return null;
/*     */     }
/* 620 */     return Entities.XML.escape(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unescapeXml(Writer writer, String str)
/*     */     throws IOException
/*     */   {
/* 642 */     if (writer == null) {
/* 643 */       throw new IllegalArgumentException("The Writer must not be null.");
/*     */     }
/* 645 */     if (str == null) {
/* 646 */       return;
/*     */     }
/* 648 */     Entities.XML.unescape(writer, str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String unescapeXml(String str)
/*     */   {
/* 667 */     if (str == null) {
/* 668 */       return null;
/*     */     }
/* 670 */     return Entities.XML.unescape(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String escapeSql(String str)
/*     */   {
/* 693 */     if (str == null) {
/* 694 */       return null;
/*     */     }
/* 696 */     return StringUtils.replace(str, "'", "''");
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
/*     */   public static String escapeCsv(String str)
/*     */   {
/* 724 */     if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
/* 725 */       return str;
/*     */     }
/*     */     try {
/* 728 */       StringWriter writer = new StringWriter();
/* 729 */       escapeCsv(writer, str);
/* 730 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 733 */       throw new UnhandledException(ioe);
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
/*     */   public static void escapeCsv(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 761 */     if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
/* 762 */       if (str != null) {
/* 763 */         out.write(str);
/*     */       }
/* 765 */       return;
/*     */     }
/* 767 */     out.write(34);
/* 768 */     for (int i = 0; i < str.length(); i++) {
/* 769 */       char c = str.charAt(i);
/* 770 */       if (c == '"') {
/* 771 */         out.write(34);
/*     */       }
/* 773 */       out.write(c);
/*     */     }
/* 775 */     out.write(34);
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
/*     */   public static String unescapeCsv(String str)
/*     */   {
/* 801 */     if (str == null) {
/* 802 */       return null;
/*     */     }
/*     */     try {
/* 805 */       StringWriter writer = new StringWriter();
/* 806 */       unescapeCsv(writer, str);
/* 807 */       return writer.toString();
/*     */     }
/*     */     catch (IOException ioe) {
/* 810 */       throw new UnhandledException(ioe);
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
/*     */   public static void unescapeCsv(Writer out, String str)
/*     */     throws IOException
/*     */   {
/* 838 */     if (str == null) {
/* 839 */       return;
/*     */     }
/* 841 */     if (str.length() < 2) {
/* 842 */       out.write(str);
/* 843 */       return;
/*     */     }
/* 845 */     if ((str.charAt(0) != '"') || (str.charAt(str.length() - 1) != '"')) {
/* 846 */       out.write(str);
/* 847 */       return;
/*     */     }
/*     */     
/*     */ 
/* 851 */     String quoteless = str.substring(1, str.length() - 1);
/*     */     
/* 853 */     if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS))
/*     */     {
/* 855 */       str = StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR);
/*     */     }
/*     */     
/* 858 */     out.write(str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\StringEscapeUtils.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */