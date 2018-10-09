/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class MojangsonParser
/*     */ {
/*  14 */   private static final org.apache.logging.log4j.Logger a = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  19 */   private static final Pattern b = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
/*     */   
/*     */   public static NBTTagCompound parse(String paramString) throws MojangsonParseException {
/*  22 */     paramString = paramString.trim();
/*     */     
/*  24 */     if (!paramString.startsWith("{")) {
/*  25 */       throw new MojangsonParseException("Invalid tag encountered, expected '{' as first char.");
/*     */     }
/*     */     
/*  28 */     if (b(paramString) != 1) {
/*  29 */       throw new MojangsonParseException("Encountered multiple top tags, only one expected");
/*     */     }
/*     */     
/*  32 */     return (NBTTagCompound)a("tag", paramString).a();
/*     */   }
/*     */   
/*     */   static int b(String paramString) throws MojangsonParseException {
/*  36 */     int i = 0;
/*  37 */     int j = 0;
/*  38 */     Stack localStack = new Stack();
/*     */     
/*  40 */     int k = 0;
/*  41 */     while (k < paramString.length()) {
/*  42 */       char c = paramString.charAt(k);
/*  43 */       if (c == '"') {
/*  44 */         if (b(paramString, k)) {
/*  45 */           if (j == 0) {
/*  46 */             throw new MojangsonParseException("Illegal use of \\\": " + paramString);
/*     */           }
/*     */         } else {
/*  49 */           j = j == 0 ? 1 : 0;
/*     */         }
/*  51 */       } else if (j == 0) {
/*  52 */         if ((c == '{') || (c == '[')) {
/*  53 */           if (localStack.isEmpty()) {
/*  54 */             i++;
/*     */           }
/*  56 */           localStack.push(Character.valueOf(c));
/*  57 */         } else { if ((c == '}') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '{')))
/*  58 */             throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString);
/*  59 */           if ((c == ']') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '[')))
/*  60 */             throw new MojangsonParseException("Unbalanced square brackets []: " + paramString);
/*     */         }
/*     */       }
/*  63 */       k++;
/*     */     }
/*  65 */     if (j != 0) {
/*  66 */       throw new MojangsonParseException("Unbalanced quotation: " + paramString);
/*     */     }
/*  68 */     if (!localStack.isEmpty()) {
/*  69 */       throw new MojangsonParseException("Unbalanced brackets: " + paramString);
/*     */     }
/*     */     
/*  72 */     if ((i == 0) && (!paramString.isEmpty())) {
/*  73 */       i = 1;
/*     */     }
/*     */     
/*  76 */     return i;
/*     */   }
/*     */   
/*     */   static MojangsonTypeParser a(String... paramVarArgs) throws MojangsonParseException {
/*  80 */     return a(paramVarArgs[0], paramVarArgs[1]);
/*     */   }
/*     */   
/*     */   static MojangsonTypeParser a(String paramString1, String paramString2) throws MojangsonParseException {
/*  84 */     paramString2 = paramString2.trim();
/*     */     Object localObject;
/*  86 */     String str; int i; if (paramString2.startsWith("{")) {
/*  87 */       paramString2 = paramString2.substring(1, paramString2.length() - 1);
/*     */       
/*  89 */       localObject = new MojangsonCompoundParser(paramString1);
/*  90 */       while (paramString2.length() > 0) {
/*  91 */         str = b(paramString2, true);
/*  92 */         if (str.length() > 0) {
/*  93 */           boolean bool = false;
/*  94 */           ((MojangsonCompoundParser)localObject).b.add(a(str, bool));
/*     */         }
/*     */         
/*  97 */         if (paramString2.length() < str.length() + 1) {
/*     */           break;
/*     */         }
/*     */         
/* 101 */         i = paramString2.charAt(str.length());
/* 102 */         if ((i != 44) && (i != 123) && (i != 125) && (i != 91) && (i != 93)) {
/* 103 */           throw new MojangsonParseException("Unexpected token '" + i + "' at: " + paramString2.substring(str.length()));
/*     */         }
/* 105 */         paramString2 = paramString2.substring(str.length() + 1);
/*     */       }
/*     */       
/* 108 */       return (MojangsonTypeParser)localObject; }
/* 109 */     if ((paramString2.startsWith("[")) && (!b.matcher(paramString2).matches())) {
/* 110 */       paramString2 = paramString2.substring(1, paramString2.length() - 1);
/*     */       
/* 112 */       localObject = new MojangsonListParser(paramString1);
/* 113 */       while (paramString2.length() > 0) {
/* 114 */         str = b(paramString2, false);
/* 115 */         if (str.length() > 0) {
/* 116 */           i = 1;
/* 117 */           ((MojangsonListParser)localObject).b.add(a(str, i));
/*     */         }
/*     */         
/* 120 */         if (paramString2.length() < str.length() + 1) {
/*     */           break;
/*     */         }
/*     */         
/* 124 */         char c = paramString2.charAt(str.length());
/* 125 */         if ((c != ',') && (c != '{') && (c != '}') && (c != '[') && (c != ']')) {
/* 126 */           throw new MojangsonParseException("Unexpected token '" + c + "' at: " + paramString2.substring(str.length()));
/*     */         }
/* 128 */         paramString2 = paramString2.substring(str.length() + 1);
/*     */       }
/*     */       
/* 131 */       return (MojangsonTypeParser)localObject;
/*     */     }
/*     */     
/* 134 */     return new MojangsonPrimitiveParser(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   private static MojangsonTypeParser a(String paramString, boolean paramBoolean) throws MojangsonParseException {
/* 138 */     String str1 = c(paramString, paramBoolean);
/* 139 */     String str2 = d(paramString, paramBoolean);
/* 140 */     return a(new String[] { str1, str2 });
/*     */   }
/*     */   
/*     */   private static String b(String paramString, boolean paramBoolean)
/*     */     throws MojangsonParseException
/*     */   {
/* 146 */     int i = a(paramString, ':');
/* 147 */     int j = a(paramString, ',');
/*     */     
/* 149 */     if (paramBoolean) {
/* 150 */       if (i == -1) {
/* 151 */         throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */       }
/* 153 */       if ((j != -1) && (j < i)) {
/* 154 */         throw new MojangsonParseException("Name error at: " + paramString);
/*     */       }
/*     */     }
/* 157 */     else if ((i == -1) || (i > j)) {
/* 158 */       i = -1;
/*     */     }
/*     */     
/*     */ 
/* 162 */     return a(paramString, i);
/*     */   }
/*     */   
/*     */   private static String a(String paramString, int paramInt) throws MojangsonParseException {
/* 166 */     Stack localStack = new Stack();
/* 167 */     int i = paramInt + 1;
/* 168 */     int j = 0;
/* 169 */     int k = 0;
/* 170 */     int m = 0;
/* 171 */     int n = 0;
/*     */     
/* 173 */     while (i < paramString.length()) {
/* 174 */       char c = paramString.charAt(i);
/*     */       
/* 176 */       if (c == '"') {
/* 177 */         if (b(paramString, i)) {
/* 178 */           if (j == 0) {
/* 179 */             throw new MojangsonParseException("Illegal use of \\\": " + paramString);
/*     */           }
/*     */         } else {
/* 182 */           j = j == 0 ? 1 : 0;
/* 183 */           if ((j != 0) && (m == 0)) {
/* 184 */             k = 1;
/*     */           }
/* 186 */           if (j == 0) {
/* 187 */             n = i;
/*     */           }
/*     */         }
/* 190 */       } else if (j == 0) {
/* 191 */         if ((c == '{') || (c == '[')) {
/* 192 */           localStack.push(Character.valueOf(c));
/* 193 */         } else { if ((c == '}') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '{')))
/* 194 */             throw new MojangsonParseException("Unbalanced curly brackets {}: " + paramString);
/* 195 */           if ((c == ']') && ((localStack.isEmpty()) || (((Character)localStack.pop()).charValue() != '[')))
/* 196 */             throw new MojangsonParseException("Unbalanced square brackets []: " + paramString);
/* 197 */           if ((c == ',') && 
/* 198 */             (localStack.isEmpty())) {
/* 199 */             return paramString.substring(0, i);
/*     */           }
/*     */         }
/*     */       }
/* 203 */       if (!Character.isWhitespace(c)) {
/* 204 */         if ((j == 0) && (k != 0) && (n != i)) {
/* 205 */           return paramString.substring(0, n + 1);
/*     */         }
/* 207 */         m = 1;
/*     */       }
/*     */       
/* 210 */       i++;
/*     */     }
/*     */     
/* 213 */     return paramString.substring(0, i);
/*     */   }
/*     */   
/*     */   private static String c(String paramString, boolean paramBoolean) throws MojangsonParseException
/*     */   {
/* 218 */     if (paramBoolean) {
/* 219 */       paramString = paramString.trim();
/* 220 */       if ((paramString.startsWith("{")) || (paramString.startsWith("["))) {
/* 221 */         return "";
/*     */       }
/*     */     }
/*     */     
/* 225 */     int i = a(paramString, ':');
/* 226 */     if (i == -1) {
/* 227 */       if (paramBoolean) {
/* 228 */         return "";
/*     */       }
/* 230 */       throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */     }
/* 232 */     return paramString.substring(0, i).trim();
/*     */   }
/*     */   
/*     */   private static String d(String paramString, boolean paramBoolean) throws MojangsonParseException {
/* 236 */     if (paramBoolean) {
/* 237 */       paramString = paramString.trim();
/* 238 */       if ((paramString.startsWith("{")) || (paramString.startsWith("["))) {
/* 239 */         return paramString;
/*     */       }
/*     */     }
/*     */     
/* 243 */     int i = a(paramString, ':');
/* 244 */     if (i == -1) {
/* 245 */       if (paramBoolean) {
/* 246 */         return paramString;
/*     */       }
/* 248 */       throw new MojangsonParseException("Unable to locate name/value separator for string: " + paramString);
/*     */     }
/* 250 */     return paramString.substring(i + 1).trim();
/*     */   }
/*     */   
/*     */   private static int a(String paramString, char paramChar) {
/* 254 */     int i = 0;
/* 255 */     int j = 1;
/* 256 */     while (i < paramString.length()) {
/* 257 */       char c = paramString.charAt(i);
/* 258 */       if (c == '"') {
/* 259 */         if (!b(paramString, i)) {
/* 260 */           j = j == 0 ? 1 : 0;
/*     */         }
/* 262 */       } else if (j != 0) {
/* 263 */         if (c == paramChar) {
/* 264 */           return i;
/*     */         }
/* 266 */         if ((c == '{') || (c == '[')) {
/* 267 */           return -1;
/*     */         }
/*     */       }
/* 270 */       i++;
/*     */     }
/* 272 */     return -1;
/*     */   }
/*     */   
/*     */   private static boolean b(String paramString, int paramInt) {
/* 276 */     return (paramInt > 0) && (paramString.charAt(paramInt - 1) == '\\') && (!b(paramString, paramInt - 1));
/*     */   }
/*     */   
/*     */   static abstract class MojangsonTypeParser {
/*     */     protected String a;
/*     */     
/*     */     public abstract NBTBase a() throws MojangsonParseException;
/*     */   }
/*     */   
/*     */   static class MojangsonCompoundParser extends MojangsonParser.MojangsonTypeParser {
/* 286 */     protected List<MojangsonParser.MojangsonTypeParser> b = Lists.newArrayList();
/*     */     
/*     */     public MojangsonCompoundParser(String paramString) {
/* 289 */       this.a = paramString;
/*     */     }
/*     */     
/*     */     public NBTBase a() throws MojangsonParseException
/*     */     {
/* 294 */       NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */       
/* 296 */       for (MojangsonParser.MojangsonTypeParser localMojangsonTypeParser : this.b) {
/* 297 */         localNBTTagCompound.set(localMojangsonTypeParser.a, localMojangsonTypeParser.a());
/*     */       }
/*     */       
/* 300 */       return localNBTTagCompound;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MojangsonListParser extends MojangsonParser.MojangsonTypeParser {
/* 305 */     protected List<MojangsonParser.MojangsonTypeParser> b = Lists.newArrayList();
/*     */     
/*     */     public MojangsonListParser(String paramString) {
/* 308 */       this.a = paramString;
/*     */     }
/*     */     
/*     */     public NBTBase a() throws MojangsonParseException
/*     */     {
/* 313 */       NBTTagList localNBTTagList = new NBTTagList();
/*     */       
/* 315 */       for (MojangsonParser.MojangsonTypeParser localMojangsonTypeParser : this.b) {
/* 316 */         localNBTTagList.add(localMojangsonTypeParser.a());
/*     */       }
/*     */       
/* 319 */       return localNBTTagList;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MojangsonPrimitiveParser extends MojangsonParser.MojangsonTypeParser {
/* 324 */     private static final Pattern c = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
/* 325 */     private static final Pattern d = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
/* 326 */     private static final Pattern e = Pattern.compile("[-+]?[0-9]+[b|B]");
/* 327 */     private static final Pattern f = Pattern.compile("[-+]?[0-9]+[l|L]");
/* 328 */     private static final Pattern g = Pattern.compile("[-+]?[0-9]+[s|S]");
/* 329 */     private static final Pattern h = Pattern.compile("[-+]?[0-9]+");
/* 330 */     private static final Pattern i = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
/* 331 */     private static final Splitter j = Splitter.on(',').omitEmptyStrings();
/*     */     protected String b;
/*     */     
/*     */     public MojangsonPrimitiveParser(String paramString1, String paramString2)
/*     */     {
/* 336 */       this.a = paramString1;
/* 337 */       this.b = paramString2;
/*     */     }
/*     */     
/*     */ 
/*     */     public NBTBase a()
/*     */       throws MojangsonParseException
/*     */     {
/*     */       try
/*     */       {
/* 346 */         if (c.matcher(this.b).matches()) {
/* 347 */           return new NBTTagDouble(Double.parseDouble(this.b.substring(0, this.b.length() - 1)));
/*     */         }
/* 349 */         if (d.matcher(this.b).matches()) {
/* 350 */           return new NBTTagFloat(Float.parseFloat(this.b.substring(0, this.b.length() - 1)));
/*     */         }
/* 352 */         if (e.matcher(this.b).matches()) {
/* 353 */           return new NBTTagByte(Byte.parseByte(this.b.substring(0, this.b.length() - 1)));
/*     */         }
/* 355 */         if (f.matcher(this.b).matches()) {
/* 356 */           return new NBTTagLong(Long.parseLong(this.b.substring(0, this.b.length() - 1)));
/*     */         }
/* 358 */         if (g.matcher(this.b).matches()) {
/* 359 */           return new NBTTagShort(Short.parseShort(this.b.substring(0, this.b.length() - 1)));
/*     */         }
/* 361 */         if (h.matcher(this.b).matches()) {
/* 362 */           return new NBTTagInt(Integer.parseInt(this.b));
/*     */         }
/* 364 */         if (i.matcher(this.b).matches()) {
/* 365 */           return new NBTTagDouble(Double.parseDouble(this.b));
/*     */         }
/* 367 */         if ((this.b.equalsIgnoreCase("true")) || (this.b.equalsIgnoreCase("false"))) {
/* 368 */           return new NBTTagByte((byte)(Boolean.parseBoolean(this.b) ? 1 : 0));
/*     */         }
/*     */       } catch (NumberFormatException localNumberFormatException1) {
/* 371 */         this.b = this.b.replaceAll("\\\\\"", "\"");
/* 372 */         return new NBTTagString(this.b);
/*     */       }
/*     */       
/*     */ 
/* 376 */       if ((this.b.startsWith("[")) && (this.b.endsWith("]"))) {
/* 377 */         localObject = this.b.substring(1, this.b.length() - 1);
/*     */         
/* 379 */         String[] arrayOfString = (String[])Iterables.toArray(j.split((CharSequence)localObject), String.class);
/*     */         try {
/* 381 */           int[] arrayOfInt = new int[arrayOfString.length];
/* 382 */           for (int m = 0; m < arrayOfString.length; m++) {
/* 383 */             arrayOfInt[m] = Integer.parseInt(arrayOfString[m].trim());
/*     */           }
/* 385 */           return new NBTTagIntArray(arrayOfInt);
/*     */         } catch (NumberFormatException localNumberFormatException2) {
/* 387 */           return new NBTTagString(this.b);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 392 */       if ((this.b.startsWith("\"")) && (this.b.endsWith("\""))) {
/* 393 */         this.b = this.b.substring(1, this.b.length() - 1);
/*     */       }
/*     */       
/* 396 */       this.b = this.b.replaceAll("\\\\\"", "\"");
/*     */       
/* 398 */       Object localObject = new StringBuilder();
/* 399 */       for (int k = 0; k < this.b.length(); k++) {
/* 400 */         if ((k < this.b.length() - 1) && (this.b.charAt(k) == '\\') && (this.b.charAt(k + 1) == '\\')) {
/* 401 */           ((StringBuilder)localObject).append('\\');
/* 402 */           k++;
/*     */         } else {
/* 404 */           ((StringBuilder)localObject).append(this.b.charAt(k));
/*     */         }
/*     */       }
/* 407 */       return new NBTTagString(((StringBuilder)localObject).toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MojangsonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */