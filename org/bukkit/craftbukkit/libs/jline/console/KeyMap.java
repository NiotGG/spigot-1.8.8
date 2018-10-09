/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyMap
/*     */ {
/*     */   public static final String VI_MOVE = "vi-move";
/*     */   public static final String VI_INSERT = "vi-insert";
/*     */   public static final String EMACS = "emacs";
/*     */   public static final String EMACS_STANDARD = "emacs-standard";
/*     */   public static final String EMACS_CTLX = "emacs-ctlx";
/*     */   public static final String EMACS_META = "emacs-meta";
/*     */   private static final int KEYMAP_LENGTH = 256;
/*  31 */   private static final Object NULL_FUNCTION = new Object();
/*     */   
/*  33 */   private Object[] mapping = new Object['Ā'];
/*  34 */   private Object anotherKey = null;
/*     */   private String name;
/*     */   private boolean isViKeyMap;
/*     */   
/*     */   public KeyMap(String name, boolean isViKeyMap) {
/*  39 */     this(name, new Object['Ā'], isViKeyMap);
/*     */   }
/*     */   
/*     */   protected KeyMap(String name, Object[] mapping, boolean isViKeyMap) {
/*  43 */     this.mapping = mapping;
/*  44 */     this.name = name;
/*  45 */     this.isViKeyMap = isViKeyMap;
/*     */   }
/*     */   
/*     */   public boolean isViKeyMap() {
/*  49 */     return this.isViKeyMap;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  53 */     return this.name;
/*     */   }
/*     */   
/*     */   public Object getAnotherKey() {
/*  57 */     return this.anotherKey;
/*     */   }
/*     */   
/*     */   public void from(KeyMap other) {
/*  61 */     this.mapping = other.mapping;
/*  62 */     this.anotherKey = other.anotherKey;
/*     */   }
/*     */   
/*     */   public Object getBound(CharSequence keySeq) {
/*  66 */     if ((keySeq != null) && (keySeq.length() > 0)) {
/*  67 */       KeyMap map = this;
/*  68 */       for (int i = 0; i < keySeq.length(); i++) {
/*  69 */         char c = keySeq.charAt(i);
/*  70 */         if (c > 'ÿ') {
/*  71 */           return Operation.SELF_INSERT;
/*     */         }
/*  73 */         if ((map.mapping[c] instanceof KeyMap)) {
/*  74 */           if (i == keySeq.length() - 1) {
/*  75 */             return map.mapping[c];
/*     */           }
/*  77 */           map = (KeyMap)map.mapping[c];
/*     */         }
/*     */         else {
/*  80 */           return map.mapping[c];
/*     */         }
/*     */       }
/*     */     }
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   public void bindIfNotBound(CharSequence keySeq, Object function)
/*     */   {
/*  89 */     bind(this, keySeq, function, true);
/*     */   }
/*     */   
/*     */   public void bind(CharSequence keySeq, Object function)
/*     */   {
/*  94 */     bind(this, keySeq, function, false);
/*     */   }
/*     */   
/*     */   private static void bind(KeyMap map, CharSequence keySeq, Object function)
/*     */   {
/*  99 */     bind(map, keySeq, function, false);
/*     */   }
/*     */   
/*     */ 
/*     */   private static void bind(KeyMap map, CharSequence keySeq, Object function, boolean onlyIfNotBound)
/*     */   {
/* 105 */     if ((keySeq != null) && (keySeq.length() > 0)) {
/* 106 */       for (int i = 0; i < keySeq.length(); i++) {
/* 107 */         char c = keySeq.charAt(i);
/* 108 */         if (c >= map.mapping.length) {
/* 109 */           return;
/*     */         }
/* 111 */         if (i < keySeq.length() - 1) {
/* 112 */           if (!(map.mapping[c] instanceof KeyMap)) {
/* 113 */             KeyMap m = new KeyMap("anonymous", false);
/* 114 */             if (map.mapping[c] != Operation.DO_LOWERCASE_VERSION) {
/* 115 */               m.anotherKey = map.mapping[c];
/*     */             }
/* 117 */             map.mapping[c] = m;
/*     */           }
/* 119 */           map = (KeyMap)map.mapping[c];
/*     */         } else {
/* 121 */           if (function == null) {
/* 122 */             function = NULL_FUNCTION;
/*     */           }
/* 124 */           if ((map.mapping[c] instanceof KeyMap)) {
/* 125 */             map.anotherKey = function;
/*     */           } else {
/* 127 */             Object op = map.mapping[c];
/* 128 */             if ((onlyIfNotBound) && (op != null) && (op != Operation.DO_LOWERCASE_VERSION) && (op == Operation.VI_MOVEMENT_MODE)) {}
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 135 */             map.mapping[c] = function;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setBlinkMatchingParen(boolean on) {
/* 143 */     if (on) {
/* 144 */       bind("}", Operation.INSERT_CLOSE_CURLY);
/* 145 */       bind(")", Operation.INSERT_CLOSE_PAREN);
/* 146 */       bind("]", Operation.INSERT_CLOSE_SQUARE);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static void bindArrowKeys(KeyMap map)
/*     */   {
/* 153 */     bind(map, "\033[0A", Operation.PREVIOUS_HISTORY);
/* 154 */     bind(map, "\033[0B", Operation.BACKWARD_CHAR);
/* 155 */     bind(map, "\033[0C", Operation.FORWARD_CHAR);
/* 156 */     bind(map, "\033[0D", Operation.NEXT_HISTORY);
/*     */     
/*     */ 
/* 159 */     bind(map, "à\000", Operation.KILL_WHOLE_LINE);
/* 160 */     bind(map, "àG", Operation.BEGINNING_OF_LINE);
/* 161 */     bind(map, "àH", Operation.PREVIOUS_HISTORY);
/* 162 */     bind(map, "àI", Operation.BEGINNING_OF_HISTORY);
/* 163 */     bind(map, "àK", Operation.BACKWARD_CHAR);
/* 164 */     bind(map, "àM", Operation.FORWARD_CHAR);
/* 165 */     bind(map, "àO", Operation.END_OF_LINE);
/* 166 */     bind(map, "àP", Operation.NEXT_HISTORY);
/* 167 */     bind(map, "àQ", Operation.END_OF_HISTORY);
/* 168 */     bind(map, "àR", Operation.OVERWRITE_MODE);
/* 169 */     bind(map, "àS", Operation.DELETE_CHAR);
/*     */     
/* 171 */     bind(map, "\000G", Operation.BEGINNING_OF_LINE);
/* 172 */     bind(map, "\000H", Operation.PREVIOUS_HISTORY);
/* 173 */     bind(map, "\000I", Operation.BEGINNING_OF_HISTORY);
/* 174 */     bind(map, "\000H", Operation.PREVIOUS_HISTORY);
/* 175 */     bind(map, "\000K", Operation.BACKWARD_CHAR);
/* 176 */     bind(map, "\000M", Operation.FORWARD_CHAR);
/* 177 */     bind(map, "\000O", Operation.END_OF_LINE);
/* 178 */     bind(map, "\000P", Operation.NEXT_HISTORY);
/* 179 */     bind(map, "\000Q", Operation.END_OF_HISTORY);
/* 180 */     bind(map, "\000R", Operation.OVERWRITE_MODE);
/* 181 */     bind(map, "\000S", Operation.DELETE_CHAR);
/*     */     
/* 183 */     bind(map, "\033[A", Operation.PREVIOUS_HISTORY);
/* 184 */     bind(map, "\033[B", Operation.NEXT_HISTORY);
/* 185 */     bind(map, "\033[C", Operation.FORWARD_CHAR);
/* 186 */     bind(map, "\033[D", Operation.BACKWARD_CHAR);
/* 187 */     bind(map, "\033[H", Operation.BEGINNING_OF_LINE);
/* 188 */     bind(map, "\033[F", Operation.END_OF_LINE);
/*     */     
/* 190 */     bind(map, "\033OA", Operation.PREVIOUS_HISTORY);
/* 191 */     bind(map, "\033OB", Operation.NEXT_HISTORY);
/* 192 */     bind(map, "\033OC", Operation.FORWARD_CHAR);
/* 193 */     bind(map, "\033OD", Operation.BACKWARD_CHAR);
/* 194 */     bind(map, "\033OH", Operation.BEGINNING_OF_LINE);
/* 195 */     bind(map, "\033OF", Operation.END_OF_LINE);
/*     */     
/* 197 */     bind(map, "\033[1~", Operation.BEGINNING_OF_LINE);
/* 198 */     bind(map, "\033[4~", Operation.END_OF_LINE);
/* 199 */     bind(map, "\033[3~", Operation.DELETE_CHAR);
/*     */     
/*     */ 
/* 202 */     bind(map, "\0340H", Operation.PREVIOUS_HISTORY);
/* 203 */     bind(map, "\0340P", Operation.NEXT_HISTORY);
/* 204 */     bind(map, "\0340M", Operation.FORWARD_CHAR);
/* 205 */     bind(map, "\0340K", Operation.BACKWARD_CHAR);
/*     */   }
/*     */   
/*     */ 
/*     */   public static final char CTRL_D = '\004';
/*     */   public static final char CTRL_G = '\007';
/*     */   public static final char CTRL_H = '\b';
/*     */   public static final char CTRL_I = '\t';
/*     */   public static final char CTRL_J = '\n';
/*     */   public static final char CTRL_M = '\r';
/*     */   public static boolean isMeta(char c)
/*     */   {
/* 217 */     return (c > '') && (c <= 'ÿ');
/*     */   }
/*     */   
/*     */   public static char unMeta(char c) {
/* 221 */     return (char)(c & 0x7F);
/*     */   }
/*     */   
/*     */   public static char meta(char c) {
/* 225 */     return (char)(c | 0x80);
/*     */   }
/*     */   
/*     */   public static Map<String, KeyMap> keyMaps() {
/* 229 */     Map<String, KeyMap> keyMaps = new HashMap();
/*     */     
/* 231 */     KeyMap emacs = emacs();
/* 232 */     bindArrowKeys(emacs);
/* 233 */     keyMaps.put("emacs", emacs);
/* 234 */     keyMaps.put("emacs-standard", emacs);
/* 235 */     keyMaps.put("emacs-ctlx", (KeyMap)emacs.getBound("\030"));
/* 236 */     keyMaps.put("emacs-meta", (KeyMap)emacs.getBound("\033"));
/*     */     
/* 238 */     KeyMap viMov = viMovement();
/* 239 */     bindArrowKeys(viMov);
/* 240 */     keyMaps.put("vi-move", viMov);
/* 241 */     keyMaps.put("vi-command", viMov);
/*     */     
/* 243 */     KeyMap viIns = viInsertion();
/* 244 */     bindArrowKeys(viIns);
/* 245 */     keyMaps.put("vi-insert", viIns);
/* 246 */     keyMaps.put("vi", viIns);
/*     */     
/* 248 */     return keyMaps;
/*     */   }
/*     */   
/*     */   public static KeyMap emacs() {
/* 252 */     Object[] map = new Object['Ā'];
/* 253 */     Object[] ctrl = { Operation.SET_MARK, Operation.BEGINNING_OF_LINE, Operation.BACKWARD_CHAR, Operation.INTERRUPT, Operation.EXIT_OR_DELETE_CHAR, Operation.END_OF_LINE, Operation.FORWARD_CHAR, Operation.ABORT, Operation.BACKWARD_DELETE_CHAR, Operation.COMPLETE, Operation.ACCEPT_LINE, Operation.KILL_LINE, Operation.CLEAR_SCREEN, Operation.ACCEPT_LINE, Operation.NEXT_HISTORY, null, Operation.PREVIOUS_HISTORY, Operation.QUOTED_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, emacsCtrlX(), Operation.YANK, null, emacsMeta(), null, Operation.CHARACTER_SEARCH, null, Operation.UNDO };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 288 */     System.arraycopy(ctrl, 0, map, 0, ctrl.length);
/* 289 */     for (int i = 32; i < 256; i++) {
/* 290 */       map[i] = Operation.SELF_INSERT;
/*     */     }
/* 292 */     map[127] = Operation.BACKWARD_DELETE_CHAR;
/* 293 */     return new KeyMap("emacs", map, false);
/*     */   }
/*     */   
/*     */ 
/*     */   public static final char CTRL_R = '\022';
/*     */   
/*     */   public static final char CTRL_S = '\023';
/*     */   
/*     */   public static final char CTRL_U = '\025';
/*     */   
/*     */   public static final char CTRL_X = '\030';
/*     */   
/*     */   public static final char CTRL_Y = '\031';
/*     */   
/*     */   public static final char ESCAPE = '\033';
/*     */   
/*     */   public static final char CTRL_OB = '\033';
/*     */   public static final char CTRL_CB = '\035';
/*     */   public static final int DELETE = 127;
/*     */   public static KeyMap emacsCtrlX()
/*     */   {
/* 314 */     Object[] map = new Object['Ā'];
/* 315 */     map[7] = Operation.ABORT;
/* 316 */     map[18] = Operation.RE_READ_INIT_FILE;
/* 317 */     map[21] = Operation.UNDO;
/* 318 */     map[24] = Operation.EXCHANGE_POINT_AND_MARK;
/* 319 */     map[40] = Operation.START_KBD_MACRO;
/* 320 */     map[41] = Operation.END_KBD_MACRO;
/* 321 */     for (int i = 65; i <= 90; i++) {
/* 322 */       map[i] = Operation.DO_LOWERCASE_VERSION;
/*     */     }
/* 324 */     map[101] = Operation.CALL_LAST_KBD_MACRO;
/* 325 */     map[127] = Operation.KILL_LINE;
/* 326 */     return new KeyMap("emacs-ctlx", map, false);
/*     */   }
/*     */   
/*     */   public static KeyMap emacsMeta() {
/* 330 */     Object[] map = new Object['Ā'];
/* 331 */     map[7] = Operation.ABORT;
/* 332 */     map[8] = Operation.BACKWARD_KILL_WORD;
/* 333 */     map[9] = Operation.TAB_INSERT;
/* 334 */     map[10] = Operation.VI_EDITING_MODE;
/* 335 */     map[13] = Operation.VI_EDITING_MODE;
/* 336 */     map[18] = Operation.REVERT_LINE;
/* 337 */     map[25] = Operation.YANK_NTH_ARG;
/* 338 */     map[27] = Operation.COMPLETE;
/* 339 */     map[29] = Operation.CHARACTER_SEARCH_BACKWARD;
/* 340 */     map[32] = Operation.SET_MARK;
/* 341 */     map[35] = Operation.INSERT_COMMENT;
/* 342 */     map[38] = Operation.TILDE_EXPAND;
/* 343 */     map[42] = Operation.INSERT_COMPLETIONS;
/* 344 */     map[45] = Operation.DIGIT_ARGUMENT;
/* 345 */     map[46] = Operation.YANK_LAST_ARG;
/* 346 */     map[60] = Operation.BEGINNING_OF_HISTORY;
/* 347 */     map[61] = Operation.POSSIBLE_COMPLETIONS;
/* 348 */     map[62] = Operation.END_OF_HISTORY;
/* 349 */     map[63] = Operation.POSSIBLE_COMPLETIONS;
/* 350 */     for (int i = 65; i <= 90; i++) {
/* 351 */       map[i] = Operation.DO_LOWERCASE_VERSION;
/*     */     }
/* 353 */     map[92] = Operation.DELETE_HORIZONTAL_SPACE;
/* 354 */     map[95] = Operation.YANK_LAST_ARG;
/* 355 */     map[98] = Operation.BACKWARD_WORD;
/* 356 */     map[99] = Operation.CAPITALIZE_WORD;
/* 357 */     map[100] = Operation.KILL_WORD;
/* 358 */     map[102] = Operation.FORWARD_WORD;
/* 359 */     map[108] = Operation.DOWNCASE_WORD;
/* 360 */     map[112] = Operation.NON_INCREMENTAL_REVERSE_SEARCH_HISTORY;
/* 361 */     map[114] = Operation.REVERT_LINE;
/* 362 */     map[116] = Operation.TRANSPOSE_WORDS;
/* 363 */     map[117] = Operation.UPCASE_WORD;
/* 364 */     map[121] = Operation.YANK_POP;
/* 365 */     map[126] = Operation.TILDE_EXPAND;
/* 366 */     map[127] = Operation.BACKWARD_KILL_WORD;
/* 367 */     return new KeyMap("emacs-meta", map, false);
/*     */   }
/*     */   
/*     */   public static KeyMap viInsertion() {
/* 371 */     Object[] map = new Object['Ā'];
/* 372 */     Object[] ctrl = { null, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.VI_EOF_MAYBE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.BACKWARD_DELETE_CHAR, Operation.COMPLETE, Operation.ACCEPT_LINE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.ACCEPT_LINE, Operation.MENU_COMPLETE, Operation.SELF_INSERT, Operation.MENU_COMPLETE_BACKWARD, Operation.SELF_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, Operation.SELF_INSERT, Operation.YANK, Operation.SELF_INSERT, Operation.VI_MOVEMENT_MODE, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.SELF_INSERT, Operation.UNDO };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 407 */     System.arraycopy(ctrl, 0, map, 0, ctrl.length);
/* 408 */     for (int i = 32; i < 256; i++) {
/* 409 */       map[i] = Operation.SELF_INSERT;
/*     */     }
/* 411 */     map[127] = Operation.BACKWARD_DELETE_CHAR;
/* 412 */     return new KeyMap("vi-insert", map, false);
/*     */   }
/*     */   
/*     */   public static KeyMap viMovement() {
/* 416 */     Object[] map = new Object['Ā'];
/* 417 */     Object[] low = { null, null, null, Operation.INTERRUPT, Operation.VI_EOF_MAYBE, Operation.EMACS_EDITING_MODE, null, Operation.ABORT, Operation.BACKWARD_CHAR, null, Operation.VI_MOVE_ACCEPT_LINE, Operation.KILL_LINE, Operation.CLEAR_SCREEN, Operation.VI_MOVE_ACCEPT_LINE, Operation.VI_NEXT_HISTORY, null, Operation.VI_PREVIOUS_HISTORY, Operation.QUOTED_INSERT, Operation.REVERSE_SEARCH_HISTORY, Operation.FORWARD_SEARCH_HISTORY, Operation.TRANSPOSE_CHARS, Operation.UNIX_LINE_DISCARD, Operation.QUOTED_INSERT, Operation.UNIX_WORD_RUBOUT, null, Operation.YANK, null, emacsMeta(), null, Operation.CHARACTER_SEARCH, null, Operation.UNDO, Operation.FORWARD_CHAR, null, null, Operation.VI_INSERT_COMMENT, Operation.END_OF_LINE, Operation.VI_MATCH, Operation.VI_TILDE_EXPAND, null, null, null, Operation.VI_COMPLETE, Operation.VI_NEXT_HISTORY, Operation.VI_CHAR_SEARCH, Operation.VI_PREVIOUS_HISTORY, Operation.VI_REDO, Operation.VI_SEARCH, Operation.VI_BEGNNING_OF_LINE_OR_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, Operation.VI_ARG_DIGIT, null, Operation.VI_CHAR_SEARCH, null, Operation.VI_COMPLETE, null, Operation.VI_SEARCH, null, Operation.VI_APPEND_EOL, Operation.VI_PREV_WORD, Operation.VI_CHANGE_TO_EOL, Operation.VI_DELETE_TO_EOL, Operation.VI_END_WORD, Operation.VI_CHAR_SEARCH, Operation.VI_FETCH_HISTORY, null, Operation.VI_INSERT_BEG, null, null, null, null, Operation.VI_SEARCH_AGAIN, null, Operation.VI_PUT, null, Operation.VI_REPLACE, Operation.VI_KILL_WHOLE_LINE, Operation.VI_CHAR_SEARCH, Operation.REVERT_LINE, null, Operation.VI_NEXT_WORD, Operation.VI_RUBOUT, Operation.VI_YANK_TO, null, null, Operation.VI_COMPLETE, null, Operation.VI_FIRST_PRINT, Operation.VI_YANK_ARG, Operation.VI_GOTO_MARK, Operation.VI_APPEND_MODE, Operation.VI_PREV_WORD, Operation.VI_CHANGE_TO, Operation.VI_DELETE_TO, Operation.VI_END_WORD, Operation.VI_CHAR_SEARCH, null, Operation.BACKWARD_CHAR, Operation.VI_INSERTION_MODE, Operation.NEXT_HISTORY, Operation.PREVIOUS_HISTORY, Operation.FORWARD_CHAR, Operation.VI_SET_MARK, Operation.VI_SEARCH_AGAIN, null, Operation.VI_PUT, null, Operation.VI_CHANGE_CHAR, Operation.VI_SUBST, Operation.VI_CHAR_SEARCH, Operation.UNDO, null, Operation.VI_NEXT_WORD, Operation.VI_DELETE, Operation.VI_YANK_TO, null, null, Operation.VI_COLUMN, null, Operation.VI_CHANGE_CASE, Operation.VI_DELETE };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 572 */     System.arraycopy(low, 0, map, 0, low.length);
/* 573 */     for (int i = 128; i < 256; i++) {
/* 574 */       map[i] = null;
/*     */     }
/* 576 */     return new KeyMap("vi-move", map, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\KeyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */