/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConsoleKeys
/*     */ {
/*     */   private KeyMap keys;
/*     */   private Map<String, KeyMap> keyMaps;
/*  31 */   private Map<String, String> variables = new HashMap();
/*     */   
/*     */   public ConsoleKeys(String appName, URL inputrcUrl) {
/*  34 */     this.keyMaps = KeyMap.keyMaps();
/*  35 */     loadKeys(appName, inputrcUrl);
/*     */   }
/*     */   
/*     */   protected boolean isViEditMode() {
/*  39 */     return this.keys.isViKeyMap();
/*     */   }
/*     */   
/*     */   protected boolean setKeyMap(String name) {
/*  43 */     KeyMap map = (KeyMap)this.keyMaps.get(name);
/*  44 */     if (map == null) {
/*  45 */       return false;
/*     */     }
/*  47 */     this.keys = map;
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   protected Map<String, KeyMap> getKeyMaps() {
/*  52 */     return this.keyMaps;
/*     */   }
/*     */   
/*     */   protected KeyMap getKeys() {
/*  56 */     return this.keys;
/*     */   }
/*     */   
/*     */   protected void setKeys(KeyMap keys) {
/*  60 */     this.keys = keys;
/*     */   }
/*     */   
/*     */   protected boolean getViEditMode() {
/*  64 */     return this.keys.isViKeyMap();
/*     */   }
/*     */   
/*     */   protected void loadKeys(String appName, URL inputrcUrl) {
/*  68 */     this.keys = ((KeyMap)this.keyMaps.get("emacs"));
/*     */     try
/*     */     {
/*  71 */       InputStream input = inputrcUrl.openStream();
/*     */       try {
/*  73 */         loadKeys(input, appName);
/*  74 */         Log.debug(new Object[] { "Loaded user configuration: ", inputrcUrl });
/*     */       }
/*     */       finally {
/*     */         try {
/*  78 */           input.close();
/*     */         }
/*     */         catch (IOException e) {}
/*     */       }
/*     */       File file;
/*     */       return;
/*     */     } catch (IOException e) {
/*  85 */       if (inputrcUrl.getProtocol().equals("file")) {
/*  86 */         file = new File(inputrcUrl.getPath());
/*  87 */         if (file.exists()) {
/*  88 */           Log.warn(new Object[] { "Unable to read user configuration: ", inputrcUrl, e });
/*     */         }
/*     */       } else {
/*  91 */         Log.warn(new Object[] { "Unable to read user configuration: ", inputrcUrl, e });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void loadKeys(InputStream input, String appName) throws IOException {
/*  97 */     BufferedReader reader = new BufferedReader(new InputStreamReader(input));
/*     */     
/*  99 */     boolean parsing = true;
/* 100 */     List<Boolean> ifsStack = new ArrayList();
/* 101 */     String line; while ((line = reader.readLine()) != null) {
/*     */       try {
/* 103 */         line = line.trim();
/* 104 */         if ((line.length() != 0) && 
/*     */         
/*     */ 
/* 107 */           (line.charAt(0) != '#'))
/*     */         {
/*     */ 
/* 110 */           int i = 0;
/* 111 */           if (line.charAt(i) == '$')
/*     */           {
/*     */ 
/* 114 */             for (i++; (i < line.length()) && ((line.charAt(i) == ' ') || (line.charAt(i) == '\t')); i++) {}
/* 115 */             int s = i;
/* 116 */             while ((i < line.length()) && (line.charAt(i) != ' ') && (line.charAt(i) != '\t')) i++;
/* 117 */             String cmd = line.substring(s, i);
/* 118 */             while ((i < line.length()) && ((line.charAt(i) == ' ') || (line.charAt(i) == '\t'))) i++;
/* 119 */             s = i;
/* 120 */             while ((i < line.length()) && (line.charAt(i) != ' ') && (line.charAt(i) != '\t')) i++;
/* 121 */             String args = line.substring(s, i);
/* 122 */             if ("if".equalsIgnoreCase(cmd)) {
/* 123 */               ifsStack.add(Boolean.valueOf(parsing));
/* 124 */               if (!parsing) {
/*     */                 continue;
/*     */               }
/* 127 */               if (!args.startsWith("term="))
/*     */               {
/* 129 */                 if (args.startsWith("mode=")) {
/* 130 */                   if (args.equalsIgnoreCase("mode=vi")) {
/* 131 */                     parsing = isViEditMode();
/* 132 */                   } else if (args.equals("mode=emacs")) {
/* 133 */                     parsing = !isViEditMode();
/*     */                   } else {
/* 135 */                     parsing = false;
/*     */                   }
/*     */                 } else
/* 138 */                   parsing = args.equalsIgnoreCase(appName);
/*     */               }
/* 140 */             } else if ("else".equalsIgnoreCase(cmd)) {
/* 141 */               if (ifsStack.isEmpty()) {
/* 142 */                 throw new IllegalArgumentException("$else found without matching $if");
/*     */               }
/* 144 */               boolean invert = true;
/* 145 */               for (Iterator i$ = ifsStack.iterator(); i$.hasNext();) { boolean b = ((Boolean)i$.next()).booleanValue();
/* 146 */                 if (!b) {
/* 147 */                   invert = false;
/* 148 */                   break;
/*     */                 }
/*     */               }
/* 151 */               if (invert) {
/* 152 */                 parsing = !parsing;
/*     */               }
/* 154 */             } else if ("endif".equalsIgnoreCase(cmd)) {
/* 155 */               if (ifsStack.isEmpty()) {
/* 156 */                 throw new IllegalArgumentException("endif found without matching $if");
/*     */               }
/* 158 */               parsing = ((Boolean)ifsStack.remove(ifsStack.size() - 1)).booleanValue();
/* 159 */             } else if (!"include".equalsIgnoreCase(cmd)) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/* 164 */           else if (parsing)
/*     */           {
/*     */ 
/*     */ 
/* 168 */             String keySeq = "";
/* 169 */             if (line.charAt(i++) == '"') {
/* 170 */               boolean esc = false;
/* 171 */               for (;; i++) {
/* 172 */                 if (i >= line.length()) {
/* 173 */                   throw new IllegalArgumentException("Missing closing quote on line '" + line + "'");
/*     */                 }
/* 175 */                 if (esc) {
/* 176 */                   esc = false;
/* 177 */                 } else if (line.charAt(i) == '\\')
/* 178 */                   esc = true; else {
/* 179 */                   if (line.charAt(i) == '"') {
/*     */                     break;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 185 */             while ((i < line.length()) && (line.charAt(i) != ':') && (line.charAt(i) != ' ') && (line.charAt(i) != '\t'))
/* 186 */               i++;
/* 187 */             keySeq = line.substring(0, i);
/* 188 */             boolean equivalency = (i + 1 < line.length()) && (line.charAt(i) == ':') && (line.charAt(i + 1) == '=');
/* 189 */             i++;
/* 190 */             if (equivalency) {
/* 191 */               i++;
/*     */             }
/* 193 */             if (keySeq.equalsIgnoreCase("set"))
/*     */             {
/*     */ 
/* 196 */               while ((i < line.length()) && ((line.charAt(i) == ' ') || (line.charAt(i) == '\t'))) i++;
/* 197 */               int s = i;
/* 198 */               while ((i < line.length()) && (line.charAt(i) != ' ') && (line.charAt(i) != '\t')) i++;
/* 199 */               String key = line.substring(s, i);
/* 200 */               while ((i < line.length()) && ((line.charAt(i) == ' ') || (line.charAt(i) == '\t'))) i++;
/* 201 */               s = i;
/* 202 */               while ((i < line.length()) && (line.charAt(i) != ' ') && (line.charAt(i) != '\t')) i++;
/* 203 */               String val = line.substring(s, i);
/* 204 */               setVar(key, val);
/*     */             } else {
/* 206 */               while ((i < line.length()) && ((line.charAt(i) == ' ') || (line.charAt(i) == '\t'))) i++;
/* 207 */               int start = i;
/* 208 */               if ((i < line.length()) && ((line.charAt(i) == '\'') || (line.charAt(i) == '"'))) {
/* 209 */                 char delim = line.charAt(i++);
/* 210 */                 boolean esc = false;
/* 212 */                 for (; 
/* 212 */                     i < line.length(); i++)
/*     */                 {
/*     */ 
/*     */ 
/* 215 */                   if (esc) {
/* 216 */                     esc = false;
/* 217 */                   } else if (line.charAt(i) == '\\')
/* 218 */                     esc = true; else {
/* 219 */                     if (line.charAt(i) == delim)
/*     */                       break;
/*     */                   }
/*     */                 }
/*     */               }
/* 224 */               while ((i < line.length()) && (line.charAt(i) != ' ') && (line.charAt(i) != '\t')) i++;
/* 225 */               String val = line.substring(Math.min(start, line.length()), Math.min(i, line.length()));
/* 226 */               if (keySeq.charAt(0) == '"') {
/* 227 */                 keySeq = translateQuoted(keySeq);
/*     */               }
/*     */               else {
/* 230 */                 String keyName = keySeq.lastIndexOf('-') > 0 ? keySeq.substring(keySeq.lastIndexOf('-') + 1) : keySeq;
/* 231 */                 char key = getKeyFromName(keyName);
/* 232 */                 keyName = keySeq.toLowerCase();
/* 233 */                 keySeq = "";
/* 234 */                 if ((keyName.contains("meta-")) || (keyName.contains("m-"))) {
/* 235 */                   keySeq = keySeq + "\033";
/*     */                 }
/* 237 */                 if ((keyName.contains("control-")) || (keyName.contains("c-")) || (keyName.contains("ctrl-"))) {
/* 238 */                   key = (char)(Character.toUpperCase(key) & 0x1F);
/*     */                 }
/* 240 */                 keySeq = keySeq + key;
/*     */               }
/* 242 */               if ((val.length() > 0) && ((val.charAt(0) == '\'') || (val.charAt(0) == '"'))) {
/* 243 */                 this.keys.bind(keySeq, translateQuoted(val));
/*     */               } else {
/* 245 */                 String operationName = val.replace('-', '_').toUpperCase();
/*     */                 try {
/* 247 */                   this.keys.bind(keySeq, Operation.valueOf(operationName));
/*     */                 } catch (IllegalArgumentException e) {
/* 249 */                   Log.info(new Object[] { "Unable to bind key for unsupported operation: ", val });
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 254 */         } } catch (IllegalArgumentException e) { Log.warn(new Object[] { "Unable to parse user configuration: ", e });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private String translateQuoted(String keySeq)
/*     */   {
/* 261 */     String str = keySeq.substring(1, keySeq.length() - 1);
/* 262 */     keySeq = "";
/* 263 */     for (int i = 0; i < str.length(); i++) {
/* 264 */       char c = str.charAt(i);
/* 265 */       if (c == '\\') {
/* 266 */         boolean ctrl = (str.regionMatches(i, "\\C-", 0, 3)) || (str.regionMatches(i, "\\M-\\C-", 0, 6));
/* 267 */         boolean meta = (str.regionMatches(i, "\\M-", 0, 3)) || (str.regionMatches(i, "\\C-\\M-", 0, 6));
/* 268 */         i += (meta ? 3 : 0) + (ctrl ? 3 : 0) + ((!meta) && (!ctrl) ? 1 : 0);
/* 269 */         if (i >= str.length()) {
/*     */           break;
/*     */         }
/* 272 */         c = str.charAt(i);
/* 273 */         if (meta) {
/* 274 */           keySeq = keySeq + "\033";
/*     */         }
/* 276 */         if (ctrl) {
/* 277 */           c = c == '?' ? '' : (char)(Character.toUpperCase(c) & 0x1F);
/*     */         }
/* 279 */         if ((!meta) && (!ctrl)) {
/* 280 */           switch (c) {
/* 281 */           case 'a':  c = '\007'; break;
/* 282 */           case 'b':  c = '\b'; break;
/* 283 */           case 'd':  c = ''; break;
/* 284 */           case 'e':  c = '\033'; break;
/* 285 */           case 'f':  c = '\f'; break;
/* 286 */           case 'n':  c = '\n'; break;
/* 287 */           case 'r':  c = '\r'; break;
/* 288 */           case 't':  c = '\t'; break;
/* 289 */           case 'v':  c = '\013'; break;
/* 290 */           case '\\':  c = '\\'; break;
/*     */           case '0': case '1': case '2': case '3': 
/*     */           case '4': case '5': case '6': case '7': 
/* 293 */             c = '\000';
/* 294 */             for (int j = 0; j < 3; i++) {
/* 295 */               if (i >= str.length()) {
/*     */                 break;
/*     */               }
/* 298 */               int k = Character.digit(str.charAt(i), 8);
/* 299 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 302 */               c = (char)(c * '\b' + k);j++;
/*     */             }
/* 304 */             c = (char)(c & 0xFF);
/* 305 */             break;
/*     */           case 'x': 
/* 307 */             i++;
/* 308 */             c = '\000';
/* 309 */             for (int j = 0; j < 2; i++) {
/* 310 */               if (i >= str.length()) {
/*     */                 break;
/*     */               }
/* 313 */               int k = Character.digit(str.charAt(i), 16);
/* 314 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 317 */               c = (char)(c * '\020' + k);j++;
/*     */             }
/* 319 */             c = (char)(c & 0xFF);
/* 320 */             break;
/*     */           case 'u': 
/* 322 */             i++;
/* 323 */             c = '\000';
/* 324 */             for (int j = 0; j < 4; i++) {
/* 325 */               if (i >= str.length()) {
/*     */                 break;
/*     */               }
/* 328 */               int k = Character.digit(str.charAt(i), 16);
/* 329 */               if (k < 0) {
/*     */                 break;
/*     */               }
/* 332 */               c = (char)(c * '\020' + k);j++;
/*     */             }
/*     */           }
/*     */           
/*     */         }
/* 337 */         keySeq = keySeq + c;
/*     */       } else {
/* 339 */         keySeq = keySeq + c;
/*     */       }
/*     */     }
/* 342 */     return keySeq;
/*     */   }
/*     */   
/*     */   private char getKeyFromName(String name) {
/* 346 */     if (("DEL".equalsIgnoreCase(name)) || ("Rubout".equalsIgnoreCase(name)))
/* 347 */       return '';
/* 348 */     if (("ESC".equalsIgnoreCase(name)) || ("Escape".equalsIgnoreCase(name)))
/* 349 */       return '\033';
/* 350 */     if (("LFD".equalsIgnoreCase(name)) || ("NewLine".equalsIgnoreCase(name)))
/* 351 */       return '\n';
/* 352 */     if (("RET".equalsIgnoreCase(name)) || ("Return".equalsIgnoreCase(name)))
/* 353 */       return '\r';
/* 354 */     if (("SPC".equalsIgnoreCase(name)) || ("Space".equalsIgnoreCase(name)))
/* 355 */       return ' ';
/* 356 */     if ("Tab".equalsIgnoreCase(name)) {
/* 357 */       return '\t';
/*     */     }
/* 359 */     return name.charAt(0);
/*     */   }
/*     */   
/*     */   private void setVar(String key, String val)
/*     */   {
/* 364 */     if ("keymap".equalsIgnoreCase(key)) {
/* 365 */       if (this.keyMaps.containsKey(val)) {
/* 366 */         this.keys = ((KeyMap)this.keyMaps.get(val));
/*     */       }
/* 368 */     } else if ("editing-mode".equals(key)) {
/* 369 */       if ("vi".equalsIgnoreCase(val)) {
/* 370 */         this.keys = ((KeyMap)this.keyMaps.get("vi-insert"));
/* 371 */       } else if ("emacs".equalsIgnoreCase(key)) {
/* 372 */         this.keys = ((KeyMap)this.keyMaps.get("emacs"));
/*     */       }
/* 374 */     } else if ("blink-matching-paren".equals(key)) {
/* 375 */       if ("on".equalsIgnoreCase(val)) {
/* 376 */         this.keys.setBlinkMatchingParen(true);
/* 377 */       } else if ("off".equalsIgnoreCase(val)) {
/* 378 */         this.keys.setBlinkMatchingParen(false);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 387 */     this.variables.put(key, val);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getVariable(String var)
/*     */   {
/* 397 */     return (String)this.variables.get(var);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\ConsoleKeys.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */